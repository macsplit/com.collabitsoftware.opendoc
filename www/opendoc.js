/*global cordova, module*/

module.exports = {
    open: function (filename, data, mimetype= "", successCallback, errorCallback) {
      cordova.exec(successCallback, errorCallback, "Opendoc", "open", [filename, Array.from(data), mimetype]);
    }

};
