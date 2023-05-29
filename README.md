---
title: OpenDoc
description: Launch a document in an external app
---
<!--
# Copyright 2021 Collabit Software Ltd
# Redistribution and use in source and binary forms, with or without
# modification, are permitted provided that the following conditions
# are met:
#
# 1. Redistributions of source code must retain the above copyright
# notice, this list of conditions and the following disclaimer.
#
# 2. Redistributions in binary form must reproduce the above
# copyright notice, this list of conditions and the following
# disclaimer in the documentation and/or other materials provided
# with the distribution.
#
# 3. Neither the name of the copyright holder nor the names of its
# contributors may be used to endorse or promote products derived
# from this software without specific prior written permission.
#
# THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
# "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
# LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
# FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
# COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
# INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
# (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
# SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
# HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
# STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
# ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
# OF THE POSSIBILITY OF SUCH DAMAGE.
-->

# OpenDoc

This plugin allows a file contained in a JavaScript byte array or ArrayBuffer to be opened in an external app after prompting the user.

```
	Opendoc.open("filename.pdf", pdf_data);
```

## Installation

	corodva plugin add com.collabitsoftware.opendoc

### Supported Platforms

- Android
- iOS

### Quick example

```
var req = new XMLHttpRequest();
req.open('GET','https://mydomain/myfile.pdf');
req.responseType='arraybuffer';
req.onload=function() {if (req.status==200) {
	let byteArray = new Uint8Array(req.response);
	Opendoc.open("myfile.pdf", byteArray);
	}
};
req.send();
```

### Parameters

- **filename** (the filename including extension presented to the external app)

- **data** (a byte array or ArrayBuffer)

- **mime_type** (optional as type is auto-detected based on file extension, used on Android only)

- success function (passed a json result string)
```
{
	'type':'application/pdf',
	'size':'123'
}
```
(type returned on Android only)

- failure function (never called)


### Further Example
```
Opendoc.open(
	"document.pdf",
	documentData,
	"application/pdf",
	(result) => console.log(result)
)
```
