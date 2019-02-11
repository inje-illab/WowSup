package com.seok.seok.wowsup.fragments.fragprofile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.seok.seok.wowsup.R;
import com.seok.seok.wowsup.retrofit.model.ResponseWriteObj;
import com.seok.seok.wowsup.retrofit.remote.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WriteActivity extends AppCompatActivity {

    private Button btnSave,btnGet;
    private EditText writeTest_id, writeTest_text;
    private ListView lvWrite;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        btnSave = (Button)findViewById(R.id.btnSave);
        btnGet = (Button)findViewById(R.id.btnGet);
        writeTest_id = (EditText)findViewById(R.id.wirteTest_id);
        writeTest_text = (EditText)findViewById(R.id.wirteTest_text);
       // lvWrite = (v)findViewById(R.id.lvWrite);

//        btnSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ApiUtils.getWriteServie().requestWriteStory(writeTest_id.getText().toString(),writeTest_text.getText().toString()).enqueue(new Callback<ResponseWriteObj>(){
//                    @Override
//                    public void onResponse(Call<ResponseWriteObj> call, Response<ResponseWriteObj> response) {
//                        ResponseWriteObj Rw = response.body();
//                        Log.d("onResponse ", Rw.getId() + " <<< " + Rw.getText());
//                        if (Rw.getId()+"" != "") {
//                            Toast.makeText(WriteActivity.this, "저장성공", Toast.LENGTH_SHORT).show();
//                        }
//                        Log.i("wirteSave_Ok", "OK");
//                    }
//                    @Override
//                    public void onFailure(Call<ResponseWriteObj> call, Throwable t) {
//                        Toast.makeText(WriteActivity.this, "저장실패", Toast.LENGTH_SHORT).show();
//                        Log.i("wirteSave_err", t.getMessage());
//                    }
//                });
//            }
//        });
//        btnGet.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                lvWrite.setVisibility(View.VISIBLE);
//            }
//        });
    }
}

