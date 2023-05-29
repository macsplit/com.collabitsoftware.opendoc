/*

Copyright 2021 Collabit Software Ltd

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.

2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.

3. Neither the name of the copyright holder nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

*/

package com.collabitsoftware.plugin;

import org.apache.cordova.*;
import android.content.*;
import org.json.*;
import android.net.*;
import java.io.*;
import java.util.*;
import android.webkit.MimeTypeMap;

public class Opendoc extends CordovaPlugin {

    Context context;

    // Create a temporary file and return its path

    private String saveDocument(String filename, byte[] document) throws IOException {
        String tempfilepath = context.getExternalCacheDir().toString() + "/" +filename;
        FileOutputStream stream = new FileOutputStream(tempfilepath, false);
        stream.write(document,0,document.length);
        stream.close(); 
        return tempfilepath;
    }

    private static String getExtension(String filename) {
        String extension = "";

        int i = filename.lastIndexOf('.');
        if (i > 0) {
            extension = filename.substring(i+1);
        }

        return extension;
    }

    public static String detectMimeType(String filename) {

        String extension = getExtension(filename);

        String mimeType = "application/pdf";

        if (!extension.isEmpty()) {
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }

        return mimeType;
    }

	@Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

        context = this.cordova.getActivity().getApplicationContext();

		if (action.equals("open")) {

            // Get filename and data

            Integer length = 0;

            String filename = args.getString(0);
            JSONArray data = args.getJSONArray(1);
            String mimeType = args.getString(2);

            if (mimeType.isEmpty()) {
                mimeType = detectMimeType(filename);
            }

            length = data.length();

			byte[] document = new byte[length];

			for (int i=0; i<length; i++) {
				document[i] = (byte)data.getInt(i);
			}

            String path = "";

            // Save as temporary file

            try {

                path = saveDocument(filename, document);

            } catch (IOException e) {

                return false;
            }

            // Remember the file path

            FileList files = new FileList(context);
            files.rememberFile(path);

            // Request for system to open the file

            String authority = context.getPackageName()+".opendoc";

            Uri uri = Uri.parse("content://"+authority+path);

            Intent intent = new Intent(Intent.ACTION_VIEW);

            intent.setDataAndType(uri, mimeType);
            
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            
            Intent fileChooser = Intent.createChooser(intent, "Open File");

            this.cordova.getActivity().startActivity(fileChooser);

            // Finish

            String result = String.format("{\"type\":\"%s\",\"size\":\"%d\"}",mimeType, length);

            callbackContext.success(result);

            return true;

        } 

    	return false;

    }

}