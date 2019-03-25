package com.seok.seok.wowsup;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.SkuDetails;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.facebook.appevents.internal.InAppPurchaseActivityLifecycleTracker;
import com.seok.seok.wowsup.retrofit.model.ResponseProfileObj;
import com.seok.seok.wowsup.retrofit.model.ResponseStoryObj;
import com.seok.seok.wowsup.retrofit.remote.ApiUtils;
import com.seok.seok.wowsup.utilities.GlobalWowToken;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoreActivity extends AppCompatActivity implements BillingProcessor.IBillingHandler {
    private ImageView iBtnBack, iBtnBuy1000, iBtnBuy2000, iBtnBuy3000;
    private BillingProcessor billingProcessor;
    public static SkuDetails products1, products2, products3;
    private int token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        billingProcessor = new BillingProcessor(this, getString(R.string.in_app_license_key), this);
        billingProcessor.initialize();
        initFindViewID();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.info_store_ibtn_back:
                    finish();
                    break;
                case R.id.info_store_ibtn_1000:
                    token = 10;
                    purchaseProduct(products1.productId);
                    break;
                case R.id.info_store_ibtn_2000:
                    token = 30;
                    purchaseProduct(products1.productId);
                    break;
                case R.id.info_store_ibtn_3000:
                    token = 50;
                    purchaseProduct(products1.productId);
                    break;
            }
        }
    };

    public void initFindViewID() {
        iBtnBack = findViewById(R.id.info_store_ibtn_back);
        iBtnBuy1000 = findViewById(R.id.info_store_ibtn_1000);
        iBtnBuy2000 = findViewById(R.id.info_store_ibtn_2000);
        iBtnBuy3000 = findViewById(R.id.info_store_ibtn_3000);
        iBtnBack.setOnClickListener(onClickListener);
        iBtnBuy1000.setOnClickListener(onClickListener);
        iBtnBuy2000.setOnClickListener(onClickListener);
        iBtnBuy3000.setOnClickListener(onClickListener);
    }

    @Override
    public void onProductPurchased(@NonNull String productId, @Nullable TransactionDetails details) {
        ApiUtils.getProfileService().requestPurchaseToken(GlobalWowToken.getInstance().getId(), token).enqueue(new Callback<ResponseProfileObj>() {
            @Override
            public void onResponse(Call<ResponseProfileObj> call, Response<ResponseProfileObj> response) {
                if(response.isSuccessful()){
                    ResponseProfileObj body = response.body();
                    if(body.getState()==1){
                        Toast.makeText(StoreActivity.this, token + " tokens are charged. Thank you!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseProfileObj> call, Throwable t) {

            }
        });
    }

    @Override
    public void onPurchaseHistoryRestored() {

    }

    @Override
    public void onBillingError(int errorCode, @Nullable Throwable error) {

    }

    @Override
    public void onBillingInitialized() {
        products1 = (SkuDetails) billingProcessor.getPurchaseListingDetails("sup_token_1000");
        products2 = (SkuDetails) billingProcessor.getPurchaseListingDetails("sup_token_2000");
        products3 = (SkuDetails) billingProcessor.getPurchaseListingDetails("sup_token_3000");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (!billingProcessor.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    public void purchaseProduct(final String productId) {
        if (billingProcessor.isPurchased(productId)) {
            billingProcessor.consumePurchase(productId);
        }
        billingProcessor.purchase(StoreActivity.this, productId); // 결제창 띄움
    }
}
