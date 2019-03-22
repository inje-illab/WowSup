package com.seok.seok.wowsup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import static com.seok.seok.wowsup.fragments.fragchat.ChatActivity.txtText;

public class TranslateActivity extends AppCompatActivity {

    private Button btnTrans, btnSendtext;
    private ImageView btnBack;
    private EditText editText;
    private TextView textView;
    private String clientId = "_8DFZRFsspTZCrfikUzn";//애플리케이션 클라이언트 아이디값";
    private String clientSecret = "XLgmPOQ04n";//애플리케이션 클라이언트 시크릿값";
    private String strTrans;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);editText = (EditText)findViewById(R.id.editText);
        textView = findViewById(R.id.txtTrans);

        btnBack = findViewById(R.id.translate_btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnSendtext = findViewById(R.id.btnSendtext);
        btnSendtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtText.setText(textView.getText().toString());
                finish();
            }
        });
        btnTrans = findViewById(R.id.btnTrans);
        btnTrans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(){
                    public void run()
                    {
                        String strText = editText.getText().toString();
                        try {
                            String text = URLEncoder.encode(strText, "UTF-8");
                            String apiURL = "https://openapi.naver.com/v1/language/translate";
                            URL url = new URL(apiURL);
                            Log.d("aaa",url+"");
                            HttpURLConnection con = (HttpURLConnection)url.openConnection();
                            con.setRequestMethod("POST");
                            con.setRequestProperty("X-Naver-Client-Id", clientId);
                            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
                            // post request
                            String postParams = "source=ko&target=en&text=" + text;
                            con.setDoOutput(true);
                            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                            wr.writeBytes(postParams);
                            wr.flush();
                            wr.close();
                            int responseCode = con.getResponseCode();
                            BufferedReader br;
                            if(responseCode==200) { // 정상 호출
                                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                            } else {  // 에러 발생
                                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                            }
                            String inputLine;
                            StringBuffer response = new StringBuffer();
                            while ((inputLine = br.readLine()) != null) {
                                response.append(inputLine);
                            }
                            br.close();
                            strTrans = parsing(response.toString());
                            textView.setText(strTrans);
                            System.out.println(response.toString());
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    }
                }.start();
            }
        });


    }
    public String parsing(String txtTrans)
    {
        String strReturn = txtTrans;
        String array1[] = strReturn.split(":");
        String array2[] =array1[6].split("\"");
        return array2[1];
    }
}
