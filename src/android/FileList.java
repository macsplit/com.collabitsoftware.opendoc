/*

Copyright 2021 Collabit Software Ltd

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.

2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.

3. Neither the name of the copyright holder nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

*/

package com.collabitsoftware.plugin;

import java.util.*;
import android.content.*;


public class FileList {

	private static final String PREF = "com.collabitsoftware.Opendoc.FileList";
	private static final String PREF_FILES = PREF + ".files";

	private SharedPreferences prefs;

	public FileList (Context context) {
		prefs = context.getSharedPreferences(PREF, Context.MODE_PRIVATE);
	}

	private Set<String> fileList () {
		return new HashSet<String>(
	        prefs.getStringSet(PREF_FILES, new HashSet<String>())
	    );
	}

	private void rememberList ( Set<String> list) {
		SharedPreferences.Editor editor = prefs.edit();
	    editor.putStringSet(PREF_FILES, list);
	    editor.apply();
	}

	public void rememberFile (String path) {
	    Set<String> list = fileList();
	    list.add(path);
	    rememberList(list);
	}

	private void forgetFile (String path) {
	    Set<String> list = fileList();
	    list.remove(path);
	    rememberList(list);
	}

	public Boolean fileExists(String path) {
		 Set<String> list = fileList();
		 return list.contains(path);
	}

}