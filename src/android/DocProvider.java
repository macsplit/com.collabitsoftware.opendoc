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
import android.database.*;
import org.json.*;
import android.net.*;
import android.os.*;
import java.io.*;
import java.util.*;
import java.nio.charset.*;


public class DocProvider extends ContentProvider  {

    private static String removePrefix(String s, String prefix)
    {
        if (s != null && prefix != null && s.startsWith(prefix)){
            return s.substring(prefix.length());
        }
        return s;
    }

    private String getFilePath(Uri uri) {

        String provider = getContext().getPackageName()+".opendoc";

        Uri content_uri = Uri.parse("content://" + provider);

        return removePrefix(uri.toString(), content_uri.toString());

    }


    @Override
    public boolean onCreate() {
        
        return true;
    }


    @Override
    public ParcelFileDescriptor openFile(Uri uri, String mode)
            throws FileNotFoundException {

        FileList files = new FileList(getContext());
        
        String path = getFilePath(uri);

        final File file = new File(path);

        // Make sure file being opened is one created by this plugin

        if (files.fileExists(path)) {

            final int accessMode = ParcelFileDescriptor.parseMode(mode);
      
            return ParcelFileDescriptor.open(file, accessMode);

        } else {

            throw new FileNotFoundException();

        }
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
            String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
            String[] selectionArgs) {
        return 0;
    }

    @Override
    public String getType(Uri uri) {

        return Opendoc.detectMimeType(getFilePath(uri));

    }

}
