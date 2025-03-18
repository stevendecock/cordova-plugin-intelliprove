#import <Cordova/CDV.h>
#import <IntelliProveSDK/IntelliProveSDK.h>  // Adjust this import based on your SDK integration

@interface IntelliProvePlugin : CDVPlugin <IntelliWebViewDelegate>
@property (nonatomic, strong) CDVInvokedUrlCommand *command;
@end

@implementation IntelliProvePlugin

- (void)startFaceScan:(CDVInvokedUrlCommand*)command {
    self.command = command;
    NSString *urlString = [command.arguments objectAtIndex:0];

    // Create the web view controller using the IntelliProve SDK factory method.
    // (The method name may differ. Confirm with the official iOS docs.)
    UIViewController *webViewController = [IntelliWebViewFactory newWebViewWithUrlString:urlString delegate:self];
    webViewController.modalPresentationStyle = UIModalPresentationFullScreen;

    // Present the web view modally.
    [self.viewController presentViewController:webViewController animated:YES completion:nil];
}

// Delegate callback from the IntelliProve SDK (face scan result or post message)
- (void)didReceivePostMessage:(NSString *)postMessage {
    CDVPluginResult *result = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:postMessage];
    [self.commandDelegate sendPluginResult:result callbackId:self.command.callbackId];
}

@end
