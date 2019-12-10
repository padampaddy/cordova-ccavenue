package com.espranza.ccavenue.cordova;


import android.os.Bundle;
import android.util.Log;

import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.json.JSONException;
import org.json.JSONObject;

import com.ccavenue.indiasdk.AvenueOrder;
import com.ccavenue.indiasdk.AvenuesApplication;
import com.ccavenue.indiasdk.AvenuesTransactionCallback;

import java.util.Set;


public class CCAvenueCordova extends CordovaPlugin implements AvenuesTransactionCallback {

    private static final String TAG = "CCAvenueCordovaPlugin";
    private CallbackContext mCallbackContext;

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
    }

    @Override
    public boolean execute(final String action, final JSONArray args, final CallbackContext callbackContext) throws JSONException {
        Log.i(TAG, "execute(): " + action + ", args = " + args);

        switch (action) {
            case "showPaymentView":
                Log.d(TAG, "to showPaymentView()");
                final JSONObject paymentJsonObject = args.getJSONObject(0);
                cordova.getActivity().runOnUiThread(() -> launchPayUMoneyFlow(paymentJsonObject, callbackContext));

                return true;

            case "orderDetails":
                try {
                    final String orderDetails = args.getString(0);
                    Log.d(TAG, "setOrderDetails(): setting orderDetails to " + orderDetails);
//                    setOrderDetails( orderDetails, callbackContext );
                } catch (Exception e) {
                    e.printStackTrace();
                    callbackContext.error(e.getMessage());
                }
                return true;

        }

        return false;
    }


    private void launchPayUMoneyFlow(final JSONObject jsonObject, final CallbackContext callbackContext) {
        mCallbackContext = callbackContext;
        Log.d(TAG, "launchCCAvenueMoneyFlow(): " + jsonObject);
        try {
            AvenueOrder orderDetails = new AvenueOrder();
            orderDetails.setOrderId(jsonObject.getString("orderId"));
            orderDetails.setResponseHash(jsonObject.getString("responseHash"));
            orderDetails.setRsaKeyUrl(jsonObject.getString("rsaKeyUrl"));
            orderDetails.setRedirectUrl(jsonObject.getString("redirectUrl"));
            orderDetails.setCancelUrl(jsonObject.getString("cancelUrl"));
            orderDetails.setAccessCode(jsonObject.getString("accessCode"));
            orderDetails.setMerchantId(jsonObject.getString("merchentId"));
            orderDetails.setCurrency(jsonObject.getString("currency"));
            orderDetails.setAmount(jsonObject.getString("amount"));
            orderDetails.setCustomerId(jsonObject.getString("customerId"));
            orderDetails.setPaymentType(jsonObject.getString("paymentType"));
            orderDetails.setMerchantLogo(jsonObject.getString("merchantLogo"));
            orderDetails.setBillingName(jsonObject.getString("billingName"));
            orderDetails.setBillingAddress(jsonObject.getString("billingAddress"));
            orderDetails.setBillingCountry(jsonObject.getString("billingCountry"));
            orderDetails.setBillingState(jsonObject.getString("billingState"));
            orderDetails.setBillingCity(jsonObject.getString("billingCity"));
            orderDetails.setBillingZip(jsonObject.getString("billingZip"));
            orderDetails.setBillingTel(jsonObject.getString("billingTel"));
            orderDetails.setBillingEmail(jsonObject.getString("billingEmail"));
            orderDetails.setDeliveryName(jsonObject.getString("deliveryName"));
            orderDetails.setDeliveryAddress(jsonObject.getString("deliveryAddress"));
            orderDetails.setDeliveryCountry(jsonObject.getString("deliveryCountry"));
            orderDetails.setDeliveryState(jsonObject.getString("deliveryState"));
            orderDetails.setDeliveryCity(jsonObject.getString("deliveryCity"));
            orderDetails.setDeliveryZip(jsonObject.getString("deliveryZip"));
            orderDetails.setDeliveryTel(jsonObject.getString("deliveryTel"));
            orderDetails.setMerchant_param1(jsonObject.getString("merchantData")); //total 5 parameters
            orderDetails.setMobileNo(jsonObject.getString("mobileNo"));
            orderDetails.setPaymentEnviroment(jsonObject.getString("appEnvironment"));
            AvenuesApplication.startTransaction(cordova.getContext(), orderDetails);
        } catch (Exception e) {
            e.printStackTrace();
            callbackContext.error(e.getMessage());
        }

    }

    @Override
    public void onTransactionResponse(Bundle bundle) {
        if (mCallbackContext != null) {
            JSONObject json = new JSONObject();
            Set<String> keys = bundle.keySet();
            for (String key : keys) {
                try {
                    json.put(key, JSONObject.wrap(bundle.get(key)));
                } catch (JSONException e) {
                    e.printStackTrace();
                    mCallbackContext.error(e.getMessage());
                }
            }
            mCallbackContext.success(json);
        }
    }

    @Override
    public void onErrorOccurred(String s) {
        if (mCallbackContext != null) {
            mCallbackContext.error(s);
        }

    }

    @Override
    public void onCancelTransaction() {
        if (mCallbackContext != null) {
            mCallbackContext.error("Transaction Cancelled.");
        }
    }
}



