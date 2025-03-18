package com.example.intelliprove;

import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;
import com.intelliprove.webview.IntelliWebViewActivity;
import com.intelliprove.webview.IntelliWebViewDelegate;

public class IntelliProvePlugin extends CordovaPlugin implements IntelliWebViewDelegate {

    private CallbackContext callbackContext;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if ("startFaceScan".equals(action)) {
            String url = args.getString(0);
            this.callbackContext = callbackContext;
            startFaceScan(url);
            return true;
        }
        return false;
    }

    private void startFaceScan(final String url) {
        cordova.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // Call the IntelliProve SDK's activity to start the face scan.
                // The delegate is 'this' (i.e., this plugin) to capture post messages.
                IntelliWebViewActivity.start(cordova.getActivity(), url, IntelliProvePlugin.this);
            }
        });
    }

    // Callback method from the IntelliProve SDK
    @Override
    public void didReceivePostMessage(String postMessage) {
        if (callbackContext != null) {
            // Pass the result back to the JavaScript side.
            callbackContext.success(postMessage);
        }
    }
}
