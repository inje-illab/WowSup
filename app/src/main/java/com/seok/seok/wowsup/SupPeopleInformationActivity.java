package com.seok.seok.wowsup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.seok.seok.wowsup.utilities.SpinnerAdapter;
import com.seok.seok.wowsup.utilities.ResponseCountry;
import com.seok.seok.wowsup.retrofit.model.ResponseProfileObj;
import com.seok.seok.wowsup.retrofit.remote.ApiUtils;
import com.seok.seok.wowsup.utilities.Common;
import com.seok.seok.wowsup.utilities.GlobalWowToken;
import com.seok.seok.wowsup.utilities.ResponseAge;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//회원정보 수정하기 위한 엑티비티
public class SupPeopleInformationActivity extends AppCompatActivity {

    //회원정보를 나타낼 필드값
    private SpinnerAdapter sAdapterAge, sAdapterCountry;
    private ArrayList<ResponseCountry> countries;
    private Spinner spinnerAge, spinnerCountry;
    private LinearLayout[] layoutSet;
    private ImageView profileImage, iBtnBack;
    private TextView textUserID;
    private EditText editInfo;
    private Button btnModify;
    private String strGender, strCountry;
    private int setColor, intAge;
    private RadioGroup genderGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sup_people_information);

        initFindViewID();
        initData();

        AgeList();
        GenderType();
        CountryList();

    }

    public void initData() {
        //유저 아이디값을 통해 서버와 통신하여 개별 정보를 뿌려줌
        textUserID.setText(GlobalWowToken.getInstance().getId());
        ApiUtils.getProfileService().requestMyProfile(GlobalWowToken.getInstance().getId()).enqueue(new Callback<ResponseProfileObj>() {
            @Override
            public void onResponse(Call<ResponseProfileObj> call, Response<ResponseProfileObj> response) {
                Log.d("SupPeopleInfoActivity_HTTP_GETPROFILE", "HTTP Transfer Success");
                if (response.isSuccessful()) {
                    Log.d("SupPeopleInfoActivity_HTTP_GETPROFILE", "HTTP Response Success");
                    ResponseProfileObj body = response.body();
                    editInfo.setText(body.getSelfish());
                    strCountry = body.getNationality();
                    for (int i = 0; i < countries.size(); i++) {
                        if (countries.get(i).getName().equals(body.getNationality()))
                            spinnerCountry.setSelection(i);
                    }
                    if (strCountry.isEmpty() | strCountry.equals("") | strCountry.length() == 0) {
                        spinnerCountry.setSelection(0);
                    }
                    if (body.getGender().equals("Male")) {
                        genderGroup.check(R.id.male);
                    } else if (body.getGender().equals("Female")) {
                        genderGroup.check(R.id.female);
                    } else {
                        genderGroup.check(R.id.male);
                    }
                    if (body.getAge() == 0) {
                        spinnerAge.setSelection(19);
                    } else
                        spinnerAge.setSelection(body.getAge() - 1);
                    setLayoutSet(body.getBanner());
                    Glide.with(getApplicationContext()).load(body.getImageURL()).centerCrop().crossFade().bitmapTransform(new CropCircleTransformation(getApplicationContext())).into(profileImage);
                }
            }

            @Override
            public void onFailure(Call<ResponseProfileObj> call, Throwable t) {
                Log.d("SupPeopleInfoActivity_HTTP_GETPROFILE", "HTTP Transfer Failed");
            }
        });
    }

    public void initFindViewID() {
        //배너 변경을 위한 ID값
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

        genderGroup = findViewById(R.id.gendergroup);

        spinnerAge = findViewById(R.id.agespinner);
        spinnerCountry = findViewById(R.id.countryspinner);


        btnModify.setOnClickListener(onBtnClickListener);
        iBtnBack.setOnClickListener(onBtnClickListener);
        for (int i = 0; i < layoutSet.length; i++) {
            layoutSet[i].setOnClickListener(onClickListener);
        }
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.info_layout_set1:
                    setColor = 0;
                    break;
                case R.id.info_layout_set2:
                    setColor = 1;
                    break;
                case R.id.info_layout_set3:
                    setColor = 2;
                    break;
                case R.id.info_layout_set4:
                    setColor = 3;
                    break;
                case R.id.info_layout_set5:
                    setColor = 4;
                    break;
                case R.id.info_layout_set6:
                    setColor = 5;
                    break;
                case R.id.info_layout_set7:
                    setColor = 6;
                    break;
                case R.id.info_layout_set8:
                    setColor = 7;
                    break;
                case R.id.info_layout_set9:
                    setColor = 8;
                    break;
                case R.id.info_layout_set10:
                    setColor = 9;
                    break;
            }
            setLayoutSet(setColor);
        }
    };
    //버튼을 클릭했을 경우 리스너
    View.OnClickListener onBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.info_ibtn_back:
                    finish();
                    break;
                case R.id.info_btn_modify:
                    ApiUtils.getProfileService().requestUpdateMyProfile(GlobalWowToken.getInstance().getId(), editInfo.getText().toString(),
                            intAge, strGender, strCountry, setColor).enqueue(new Callback<ResponseProfileObj>() {
                        @Override
                        public void onResponse(Call<ResponseProfileObj> call, Response<ResponseProfileObj> response) {
                            Log.d("SupPeopleInformationActivity_HTTP_UPDATE", "HTTP Transfer Success");
                            if (response.isSuccessful()) {
                                Log.d("SupPeopleInformationActivity_HTTP_UPDATE", "HTTP Response Success");
                                ResponseProfileObj body = response.body();
                                Toast.makeText(SupPeopleInformationActivity.this, body.getUserMessage(), Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseProfileObj> call, Throwable t) {
                            Log.d("SupPeopleInformationActivity_HTTP_UPDATE", "HTTP Transfer Fail");
                        }
                    });
                    break;
            }
        }
    };

    //레이아웃 선택했을 경우
    public void setLayoutSet(int layoutNum) {
        for (int i = 0; i < layoutSet.length; i++) {
            if (i == layoutNum) {
                layoutSet[i].setBackgroundResource(Common.PICK_BANNER[i]);
            } else {
                layoutSet[i].setBackgroundColor(Common.NONPICK_BANNER[i]);
            }
        }
    }


    //나이데이터 삽입
    private void AgeList() {
        ArrayList age = new ArrayList<Integer>();
        for (int i = 1; i <= 80; i++) {
            age.add(Integer.toString(i));
        }
        ArrayAdapter<Integer> spinnerArrayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, age);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerAge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int postion, long l) {
                ResponseAge ag = new ResponseAge(postion);
                Spinner spinner = findViewById(R.id.agespinner);
                if (spinner.getSelectedItem() != null) {
                    intAge = Integer.parseInt((String) spinner.getItemAtPosition(ag.getAge()));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spinnerAge.setAdapter(spinnerArrayAdapter);
    }
    //성별 데이터 삽입
    private void GenderType() {
        if (genderGroup != null) {
            genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    strGender = (R.id.male == checkedId) ? "Male" : "Female";
                }
            });
        }
    }

    //국가 데이터 삽입
    private void CountryList() {
        countries = new ArrayList<>();
        countries.add(new ResponseCountry("Korea", R.drawable.flag_south_korea));
        countries.add(new ResponseCountry("UnitedKingdom(UK)", R.drawable.flag_uk));
        countries.add(new ResponseCountry("United States of America(USA)", R.drawable.flag_usa));
        countries.add(new ResponseCountry("Japan", R.drawable.flag_japan));
        countries.add(new ResponseCountry("China", R.drawable.flag_china));
        countries.add(new ResponseCountry("Taiwan", R.drawable.flag_taiwan));
        countries.add(new ResponseCountry("Canada", R.drawable.flag_canada));

        sAdapterCountry = new SpinnerAdapter(getApplicationContext(), countries);
        spinnerCountry.setAdapter(sAdapterCountry);
        spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ResponseCountry countryName = (ResponseCountry) sAdapterCountry.getItem(position);
                strCountry = countryName.getName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.countryset, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
