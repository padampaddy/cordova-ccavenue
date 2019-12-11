package com.espranza.ccavenue.cordova;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.ccavenue.indiasdk.AvenueOrder;
import com.ccavenue.indiasdk.AvenuesApplication;
import com.ccavenue.indiasdk.AvenuesTransactionCallback;
import com.google.gson.Gson;

import org.json.JSONObject;

public class CCAvenueActivity extends FragmentActivity implements AvenuesTransactionCallback {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Gson gson = new Gson();
            JSONObject jsonObject = gson.fromJson(getIntent().getStringExtra("orderDetails"), JSONObject.class);
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
            AvenuesApplication.startTransaction(this, orderDetails);
        } catch (Exception e) {
            e.printStackTrace();
            Intent intent = new Intent();
            intent.putExtra("result", e.getMessage());
            setResult(2, intent);
            finish();
        }

    }

    @Override
    public void onTransactionResponse(Bundle bundle) {
        Intent i = new Intent();
        i.putExtra("result", bundle);
        setResult(1, i);
        finish();
    }

    @Override
    public void onErrorOccurred(String s) {
        Intent i = new Intent();
        i.putExtra("result", s);
        setResult(2, i);
        finish();

    }

    @Override
    public void onCancelTransaction() {
        setResult(3);
        finish();

    }

}
