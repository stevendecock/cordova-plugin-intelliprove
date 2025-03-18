var exec = require('cordova/exec');

exports.startFaceScan = function(url, success, error) {
  // 'IntelliProvePlugin' matches the native class registration in plugin.xml
  exec(success, error, 'IntelliProvePlugin', 'startFaceScan', [url]);
};
