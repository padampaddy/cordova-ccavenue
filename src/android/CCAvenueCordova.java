public class CCAvenueCordova extends CordovaPlugin implements AvenuesTransactionCallBack {

    private static final String TAG = "CCAvenueCordovaPlugin" ;

    private HashMap<String, CallbackContext> contextHashMap;
    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        Log.v(TAG, "initialize(): cordova = "+cordova +", webView = "+webView );
        contextHashMap = new HashMap<>();
    }

    @Override
    public boolean execute( final String action, final JSONArray args, final CallbackContext callbackContext) throws JSONException {
        Log.i(TAG, "execute(): "+action +", args = "+args );

        switch ( action ){
            case  "showPaymentView":
                Log.d( TAG, "to showPaymentView()" );
                final JSONObject paymentJsonObject = args.getJSONObject(0);
                cordova.getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        launchPayUMoneyFlow(paymentJsonObject, callbackContext);
                    }
                });

                return true;
                
            case "orderDetails":
                try {
                    final String orderDetails = args.getString(0);
                    Log.d( TAG, "setOrderDetails(): setting orderDetails to " +orderDetails );
                    setOrderDetails( orderDetails, callbackContext );
                } catch ( Exception e) {
                    e.printStackTrace();
                    callbackContext.error( e.getMessage() );
                }
                return true;

        }

        return false;
    }


    private void launchPayUMoneyFlow( final JSONObject jsonObject , final CallbackContext callbackContext){

        Log.d(TAG, "launchCCAvenueMoneyFlow(): "+jsonObject );
        try {
            AvenueOrder orderDetails=new AvenueOrder();
            orderDetails.setOrderId(jsonObject.getString( "orderId"));
            orderDetails.setResponseHash(jsonObject.getString( "responseHash"));
            orderDetails.setRsaKeyUrl(jsonObject.getString( "rsaKeyUrl"));
            orderDetails.setRedirectUrl(jsonObject.getString( "redirectUrl"));
            orderDetails.setCancelUrl(jsonObject.getString( "cancelUrl"));
            orderDetails.setAccessCode(jsonObject.getString( "accessCode"));
            orderDetails.setMerchantId(jsonObject.getString( "merchentId"));
            orderDetails.setCurrency(jsonObject.getString( "currency"));
            orderDetails.setAmount(jsonObject.getString( "amount"));
            orderDetails.setCustomerId(jsonObject.getString( "customerId"));
            orderDetails.setPaymentType(jsonObject.getString( "paymentType"));
            orderDetails.setMerchantLogo(jsonObject.getString( "merchantLogo"));
            orderDetails.setBillingName(jsonObject.getString( "billingName"));
            orderDetails.setBillingAddress(jsonObject.getString( "billingAddress"));
            orderDetails.setBillingCountry(jsonObject.getString( "billingCountry"));
            orderDetails.setBillingState(jsonObject.getString( "billingState"));
            orderDetails.setBillingCity(jsonObject.getString( "billingCity"));
            orderDetails.setBillingZip(jsonObject.getString( "billingZip"));
            orderDetails.setBillingTel(jsonObject.getString( "billingTel"));
            orderDetails.setBillingEmail(jsonObject.getString( "billingEmail"));
            orderDetails.setDeliveryName(jsonObject.getString( "deliveryName"));
            orderDetails.setDeliveryAddress(jsonObject.getString( "deliveryAddress"));
            orderDetails.setDeliveryCountry(jsonObject.getString( "deliveryCountry"));
            orderDetails.setDeliveryState(jsonObject.getString( "deliveryState"));
            orderDetails.setDeliveryCity(jsonObject.getString( "deliveryCity"));
            orderDetails.setDeliveryZip(jsonObject.getString( "deliveryZip"));
            orderDetails.setDeliveryTel(jsonObject.getString( "deliveryTel"));
            orderDetails.setMerchant_param1(jsonObject.getString( "merchantData")); //total 5 parameters
            orderDetails.setMobileNo(jsonObject.getString( "mobileNo"));
            orderDetails.setPaymentEnviroment(jsonObject.getString( "appEnvironment"));
            AvenuesApplication.startTransaction(this, orderDetails);
        }catch ( Exception e){
            e.printStackTrace();
            callbackContext.error( e.getMessage() );
        }

    }

}



