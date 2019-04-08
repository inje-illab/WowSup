package com.seok.seok.wowsup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.seok.seok.wowsup.utilities.Common;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import static com.seok.seok.wowsup.fragments.fragchat.ChatActivity.txtText;
import static com.seok.seok.wowsup.fragments.fragprofile.StoryWriteActivity.editTextTitle;
import static com.seok.seok.wowsup.fragments.fragprofile.StoryWriteActivity.editTextBody;

//번역 클래스 엑티비티
public class TranslateActivity extends AppCompatActivity {

    private Button btnTrans, btnSendtext;
    private ImageView btnBack;
    private EditText editText;
    private TextView textView;
    //애플리케이션 클라이언트 아이디값";
    private String clientId = "_8DFZRFsspTZCrfikUzn";
    //애플리케이션 클라이언트 시크릿값";
    private String clientSecret = "XLgmPOQ04n";
    private String strTrans;

    //레이아웃엑티비티 생성
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);
        editText = (EditText) findViewById(R.id.editText);
        textView = findViewById(R.id.txtTrans);
        btnBack = findViewById(R.id.translate_btn_back);
        //뒤로가기 버튼 클릭시 액티비티 종료
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnSendtext = findViewById(R.id.btnSendtext);
        //인덱스 값으로 어느 페이지에서 Help 창을 열었는지 판별
        btnSendtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Common.translateOption == 1)
                    editTextTitle.setText(editTextBody.getText().toString() + textView.getText().toString());
                else if (Common.translateOption == 2)
                    editTextBody.setText(editTextBody.getText().toString() + textView.getText().toString());
                else if(Common.translateOption == 3)
                    txtText.setText(textView.getText().toString());
                finish();
            }
        });
        btnTrans = findViewById(R.id.btnTrans);
        //번역버튼을 눌렀을경우 쓰레드 돌림
        btnTrans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("");
                new Thread() {
                    public void run() {
                        String strText = editText.getText().toString();
                        try {
                            //해당 API를 받아오기위해 다음 코드 작성
                            String text = URLEncoder.encode(strText, "UTF-8");
                            String apiURL = "https://openapi.naver.com/v1/language/translate";
                            URL url = new URL(apiURL);
                            Log.d("aaa", url + "");
                            HttpURLConnection con = (HttpURLConnection) url.openConnection();
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
                            if (responseCode == 200) { // 정상 호출
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

                            String strTransReplace = strTrans.replace("\\n", "");

                            textView.setText(strTransReplace);
                            System.out.println(response.toString());
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    }
                }.start();
            }
        });
    }
    //서버에서 받아온 데이터값 바꾸기
    public String parsing(String txtTrans) {
        String strReturn = txtTrans;
        String array1[] = strReturn.split(":");
        String array2[] = array1[6].split("\"");
        return array2[1];
    }
}
