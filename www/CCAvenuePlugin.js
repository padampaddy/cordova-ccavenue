var exec = require('cordova/exec');

exports.showPaymentView = function (success, error, txnParams) {
    exec(success, error, 'CCAvenueCordova', 'showPaymentView', [txnParams]);
};


