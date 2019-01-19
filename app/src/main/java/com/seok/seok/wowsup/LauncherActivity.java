package com.seok.seok.wowsup;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LauncherActivity extends AppCompatActivity {
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        mHandler.sendEmptyMessageDelayed(0, 500);
        mContext = getApplicationContext();
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            startActivity(new Intent(LauncherActivity.this, LoginActivity.class));
            LauncherActivity.this.finish();
        }
    };









    // 프로젝트의 해시키를 반환 Facebook 해시키 반환 코드 getHashKey();
//    private void getHashKey() {
//        try {
//            PackageInfo info = getPackageManager().getPackageInfo(this.getPackageName(), PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.d("facebook Key???", "key_hash=" + Base64.encodeToString(md.digest(), Base64.DEFAULT));
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//    }

    // 프로젝트의 해시키를 반환 Kakao 해시키 반환 코드 getHashKey(mContext);
//    @Nullable
//    public static String getHashKey(Context context) {
//        final String TAG = "KeyHash";
//        String keyHash = null;
//        try {
//            PackageInfo info =
//                    context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md;
//                md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                keyHash = new String(Base64.encode(md.digest(), 0));
//                Log.d(TAG, keyHash);
//            }
//        } catch (Exception e) {
//            Log.e("name not found", e.toString());
//        }
//        if (keyHash != null) {
//            return keyHash;
//        } else {
//            return null;
//        }
//    }
}

