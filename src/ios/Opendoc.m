/*

Copyright 2021 Collabit Software Ltd

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.

2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.

3. Neither the name of the copyright holder nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

*/

#import "Opendoc.h"

@implementation Opendoc

- (void) open:(CDVInvokedUrlCommand*)command {

	NSString* filename = [[command arguments] objectAtIndex:0];
  	NSArray* arr = [[command arguments] objectAtIndex:1];

  	NSInteger length = [arr count];

  	NSMutableData* data = [NSMutableData dataWithCapacity:length];

  	for (int b=0;b<length;b++) {

  		NSInteger i = [(NSNumber *)[arr objectAtIndex:b] intValue];
  		unsigned char byte = (unsigned char) i;
  		[data appendBytes:(void*)&byte length:1];
  	}

	NSArray *paths = [[NSFileManager defaultManager] URLsForDirectory:NSDocumentDirectory inDomains:NSUserDomainMask];
    NSURL *docDirURL = [paths lastObject];
    NSURL *fileURL = [docDirURL URLByAppendingPathComponent:filename isDirectory:NO];
	[data writeToURL:fileURL atomically:YES];

	UIActivityViewController *activity = [[UIActivityViewController alloc] initWithActivityItems:@[fileURL] applicationActivities:nil];
	
  if (UI_USER_INTERFACE_IDIOM() == UIUserInterfaceIdiomPhone) {
    [self.viewController presentViewController:activity animated:YES completion:nil];
  }
  // iPad
  else {
    UIPopoverController *popup = [[UIPopoverController alloc] initWithContentViewController:activity];
    [popup presentPopoverFromRect:CGRectMake(self.viewController.view.frame.size.width/2, self.viewController.view.frame.size.height/2,0,0) inView:self.viewController.view permittedArrowDirections:0 animated:YES];
  }

	NSString* message = [NSString stringWithFormat:@"{\"size\":\"%ld\"}", length];

	CDVPluginResult* result = [CDVPluginResult
  	resultWithStatus:CDVCommandStatus_OK
  	messageAsString:message];

	[self.commandDelegate sendPluginResult:result callbackId:command.callbackId];

}


@end
