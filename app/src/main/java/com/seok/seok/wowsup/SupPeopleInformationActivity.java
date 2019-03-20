package com.seok.seok.wowsup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.seok.seok.wowsup.retrofit.model.ResponseProfileObj;
import com.seok.seok.wowsup.retrofit.remote.ApiUtils;
import com.seok.seok.wowsup.utilities.GlobalWowToken;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SupPeopleInformationActivity extends AppCompatActivity {

    private LinearLayout[] layoutSet;
    private ImageView profileImage, iBtnBack;
    private TextView textUserID;
    private EditText editInfo;
    private Button btnModify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sup_people_information);

        initFindViewID();
        initData();

    }
    public void initData(){
        textUserID.setText(GlobalWowToken.getInstance().getId());
        ApiUtils.getProfileService().requestMyProfile(GlobalWowToken.getInstance().getId()).enqueue(new Callback<ResponseProfileObj>() {
            @Override
            public void onResponse(Call<ResponseProfileObj> call, Response<ResponseProfileObj> response) {
                Log.d("SupPeopleInfoActivity_HTTP_GETPROFILE", "HTTP Transfer Success");
                if(response.isSuccessful()){
                    Log.d("SupPeopleInfoActivity_HTTP_GETPROFILE", "HTTP Response Success");
                    ResponseProfileObj body = response.body();
                    Glide.with(getApplicationContext()).load(body.getImageURL()).centerCrop().crossFade().bitmapTransform(new CropCircleTransformation(getApplicationContext())).into(profileImage);
                }
            }
            @Override
            public void onFailure(Call<ResponseProfileObj> call, Throwable t) {
                Log.d("SupPeopleInfoActivity_HTTP_GETPROFILE", "HTTP Transfer Failed");
            }
        });
    }
    public void initFindViewID(){
        layoutSet = new LinearLayout[10];
        layoutSet[0] = findViewById(R.id.info_layout_set1);
        layoutSet[1] = findViewById(R.id.info_layout_set2);
        layoutSet[2] = findViewById(R.id.info_layout_set3);
        layoutSet[3] = findViewById(R.id.info_layout_set4);
        layoutSet[4] = findViewById(R.id.info_layout_set5);
        layoutSet[5] = findViewById(R.id.info_layout_set6);
        layoutSet[6] = findViewById(R.id.info_layout_set7);
        layoutSet[7] = findViewById(R.id.info_layout_set8);
        layoutSet[8] = findViewById(R.id.info_layout_set9);
        layoutSet[9] = findViewById(R.id.info_layout_set10);
        profileImage = findViewById(R.id.info_img_profile);
        iBtnBack = findViewById(R.id.info_ibtn_back);
        textUserID = findViewById(R.id.info_text_userid);
        editInfo = findViewById(R.id.info_edit_info);
        btnModify = findViewById(R.id.info_btn_modify);
    }
}
