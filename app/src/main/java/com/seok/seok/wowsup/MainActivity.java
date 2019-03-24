package com.seok.seok.wowsup;


import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.seok.seok.wowsup.fragments.fragchat.FragmentChat;
import com.seok.seok.wowsup.fragments.fraghelp.FragmentHelp;
import com.seok.seok.wowsup.fragments.fragprofile.FragmentProfile;
import com.seok.seok.wowsup.fragments.fragstory.FragmentStory;
import com.seok.seok.wowsup.retrofit.model.ResponseStoryObj;
import com.seok.seok.wowsup.retrofit.remote.ApiUtils;
import com.seok.seok.wowsup.utilities.Common;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private Random randTag;
    private FragmentManager fragmentManager = getSupportFragmentManager();

    private FragmentProfile menu1Fragment = new FragmentProfile();
    private FragmentChat menu2Fragment = new FragmentChat();
    private FragmentStory menu3Fragment = new FragmentStory();
    private FragmentHelp menu4Fragment = new FragmentHelp();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);

        // 첫 화면 지정
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout, menu1Fragment).commitAllowingStateLoss();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                FragmentTransaction transaction = fragmentManager.beginTransaction();

                switch (item.getItemId()) {
                    case R.id.navigation_menu1: {
                        transaction.setCustomAnimations(R.anim.fade_in,R.anim.fade_out);
                        transaction.addToBackStack(null);
                        transaction.replace(R.id.frame_layout, menu1Fragment).commitAllowingStateLoss();
                        break;
                    }
                    case R.id.navigation_menu2: {
                        transaction.setCustomAnimations(R.anim.fade_in,R.anim.fade_out);
                        transaction.addToBackStack(null);
                        transaction.replace(R.id.frame_layout, menu2Fragment).commitAllowingStateLoss();
                        break;
                    }
                    case R.id.navigation_menu3: {
                        transaction.setCustomAnimations(R.anim.fade_in,R.anim.fade_out);
                        transaction.addToBackStack(null);
                        transaction.replace(R.id.frame_layout, menu3Fragment).commitAllowingStateLoss();
                        break;
                    }
                    case R.id.navigation_menu4: {
                        transaction.setCustomAnimations(R.anim.fade_in,R.anim.fade_out);
                        transaction.addToBackStack(null);
                        transaction.replace(R.id.frame_layout, menu4Fragment).commitAllowingStateLoss();
                        break;
                    }
                }
                return true;
            }
        });
        randTag = new Random();
        ApiUtils.getStoryService().requestRecommendTag(randTag.nextInt(5)).enqueue(new Callback<ResponseStoryObj>() {
            @Override
            public void onResponse(Call<ResponseStoryObj> call, Response<ResponseStoryObj> response) {
                if(response.isSuccessful()){
                    ResponseStoryObj body = response.body();
                    Common.searchTagText = body.getTag1();
                }
            }
            @Override
            public void onFailure(Call<ResponseStoryObj> call, Throwable t) {
                Log.d("MainActivity_ERROR ", t.getMessage() + " << ");
            }
        });
    }
}
