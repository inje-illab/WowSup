package com.seok.seok.wowsup;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.seok.seok.wowsup.retrofit.model.ResponseMailObj;
import com.seok.seok.wowsup.retrofit.model.ResponseRegisterObj;
import com.seok.seok.wowsup.retrofit.remote.ApiMailUtils;
import com.seok.seok.wowsup.retrofit.remote.ApiUtils;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, Dialog.OnCancelListener {

    private int randNum = 106254;
    private Random rand;
    final int MILLISINFUTURE = 300 * 1000; //총 시간 (300초 = 5분)
    final int COUNT_DOWN_INTERVAL = 1000; //onTick 메소드를 호출할 간격 (1초)

    private Button btnJoin, btnConfirmID, btnConfirmEmail;
    private EditText edtID, edtPW, edtEmail;
    private boolean confirmID = false;
    private boolean confirmEmail = false;

    //sein Test
    private String TAG = "RegisterActivity";
    private FirebaseAuth mAuth;
    private String emailTest, passwordTest;

    /*Dialog에 관련된 필드*/

    private LayoutInflater dialog; //LayoutInflater
    private View dialogLayout; //layout View
    private Dialog authDialog; //dialog Object

    /*카운트 다운 타이머에 관련된 필드*/

    private TextView timeCounter; //시간을 보여주는 TextView
    private EditText emailAuthNumber; //인증 번호를 입력 하는 칸
    private Button btnEmailAuth; // 인증버튼
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        init();

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {

    }

    public void userRegist(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("aaa", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("aaa", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                        // ...
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_button_confirm_email:
                String userEmail = edtEmail.getText().toString();
                if (!TextUtils.isEmpty(userEmail) && Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
                    // 이메일 인증 안되어있을 시
                    if (!confirmEmail) {
                        rand = new Random();
                        randNum = rand.nextInt(999999 - 100000 + 1) + 100000;
                        dialog = LayoutInflater.from(this);
                        dialogLayout = dialog.inflate(R.layout.layout_email_auth_dialog, null); // LayoutInflater를 통해 XML에 정의된 Resource들을 View의 형태로 반환 시켜 줌
                        authDialog = new Dialog(this); //Dialog 객체 생성
                        authDialog.setContentView(dialogLayout); //Dialog에 inflate한 View를 탑재 하여줌
                        authDialog.setCanceledOnTouchOutside(false); //Dialog 바깥 부분을 선택해도 닫히지 않게 설정함.
                        authDialog.setOnCancelListener(this); //다이얼로그를 닫을 때 일어날 일을 정의하기 위해 onCancelListener 설정
                        authDialog.show(); //Dialog를 나타내어 준다.
                        countDownTimer();
                        ApiMailUtils.getEmailService().requestEmailAuthentication(userEmail, randNum).enqueue(new Callback<ResponseMailObj>() {
                            @Override
                            public void onResponse(Call<ResponseMailObj> call, Response<ResponseMailObj> response) {
                                Log.d("RegisterActivity_HTTP_CONFIRMEMAIL", "HTTP Transfer Success");
                                if (response.isSuccessful()) {
                                    Log.d("RegisterActivity_HTTP_CONFIRMEMAIL", "HTTP response Success");
                                    ResponseMailObj body = response.body();
                                    if (body.getState() == 0) {
                                        Toast.makeText(RegisterActivity.this, "Email not Send!", Toast.LENGTH_SHORT).show();
                                    } else if (body.getState() == 1) {
                                        Toast.makeText(RegisterActivity.this, "Email Send!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                            @Override
                            public void onFailure(Call<ResponseMailObj> call, Throwable t) {
                                Log.d("RegisterActivity_HTTP_CONFIRMEMAIL", "HTTP Transfer Fail");
                            }
                        });
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "Please enter a valid email format.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.emailAuth_btn: //다이얼로그 내의 인증번호 인증 버튼을 눌렀을 시
                try {
                    int user_answer = Integer.parseInt(emailAuthNumber.getText().toString());
                    if (user_answer == randNum) {
                        Toast.makeText(this, "Email authentication successful", Toast.LENGTH_SHORT).show();
                        confirmEmail = true;
                        countDownTimer.cancel();
                        authDialog.cancel();
                    } else {
                        Toast.makeText(this, "Email authentication failed", Toast.LENGTH_SHORT).show();
                        confirmEmail = false;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.register_button_join: //Join 버튼 클릭시
                String userPassword = edtPW.getText().toString();
                if (!confirmID) {
                    Toast.makeText(RegisterActivity.this, "Please check your ID", Toast.LENGTH_SHORT).show();
                } else if (!confirmEmail) {
                    Toast.makeText(RegisterActivity.this, "Please check your E-Mail", Toast.LENGTH_SHORT).show();
                } else {
                    if (userPassword.isEmpty() || userPassword.length() < 6) {
                        Toast.makeText(RegisterActivity.this, "The PW field requires at least six characters.", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    ApiUtils.getUserService().requestRegister(edtID.getText().toString(), edtPW.getText().toString(), edtEmail.getText().toString()).enqueue(new Callback<ResponseRegisterObj>() {
                        @Override
                        public void onResponse(Call<ResponseRegisterObj> call, Response<ResponseRegisterObj> response) {
                            Log.d("RegisterActivity_HTTP_JOIN", "HTTP Transfer Success");
                            Toast.makeText(RegisterActivity.this, "You have successfully signed up.", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        @Override
                        public void onFailure(Call<ResponseRegisterObj> call, Throwable t) {
                            Log.d("RegisterActivity_HTTP_JOIN", "HTTP Transfer Fail");
                            Toast.makeText(RegisterActivity.this, "You have been unsuccessfully signed up.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                break;
            case R.id.register_button_confirm_id:   //ID 확인
                String userID = edtID.getText().toString();
                if (userID.isEmpty() || userID.length() < 4) {
                    Toast.makeText(RegisterActivity.this, "The ID field requires at least four characters.", Toast.LENGTH_SHORT).show();
                } else {
                    ApiUtils.getUserService().requestConfirmID(userID).enqueue(new Callback<ResponseRegisterObj>() {
                        @Override
                        public void onResponse(Call<ResponseRegisterObj> call, Response<ResponseRegisterObj> response) {
                            Log.d("RegisterActivity_HTTP_CONFIRMID", "HTTP Transfer Success");
                            if (response.isSuccessful()) {
                                ResponseRegisterObj body = response.body();
                                if (body.getState() == 0) {
                                    Toast.makeText(RegisterActivity.this, "Available ID", Toast.LENGTH_SHORT).show();
                                    confirmID = true;
                                } else if (body.getState() == 1) {
                                    Toast.makeText(RegisterActivity.this, "Already in use", Toast.LENGTH_SHORT).show();
                                    confirmID = false;
                                } else if (body.getState() == 2) {
                                    emailTest = edtEmail.getText().toString();
                                    passwordTest = edtPW.getText().toString();
                                    userRegist(emailTest, passwordTest);
                                    Toast.makeText(RegisterActivity.this, "Successful Sign Up", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                    finish();
                                } else if (body.getState() == 3) {
                                    Toast.makeText(RegisterActivity.this, "HTTP_ERROR", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseRegisterObj> call, Throwable t) {
                            Log.d("RegisterActivity_HTTP_CONFIRMID", "HTTP Transfer Fail");
                        }
                    });
                }
                break;
        }
    }

    public void countDownTimer() { //카운트 다운 메소드
        timeCounter = dialogLayout.findViewById(R.id.emailAuth_time_counter);
        //줄어드는 시간을 나타내는 TextView
        emailAuthNumber = dialogLayout.findViewById(R.id.emailAuth_number);
        //사용자 인증 번호 입력창
        btnEmailAuth = dialogLayout.findViewById(R.id.emailAuth_btn);
        //인증하기 버튼
        countDownTimer = new CountDownTimer(MILLISINFUTURE, COUNT_DOWN_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) { //(300초에서 1초 마다 계속 줄어듬)
                long emailAuthCount = millisUntilFinished / 1000;
                if ((emailAuthCount - ((emailAuthCount / 60) * 60)) >= 10) { //초가 10보다 크면 그냥 출력
                    timeCounter.setText((emailAuthCount / 60) + " : " + (emailAuthCount - ((emailAuthCount / 60) * 60)));
                } else { //초가 10보다 작으면 앞에 '0' 붙여서 같이 출력. ex) 02,03,04...
                    timeCounter.setText((emailAuthCount / 60) + " : 0" + (emailAuthCount - ((emailAuthCount / 60) * 60)));
                }
                //emailAuthCount은 종료까지 남은 시간임. 1분 = 60초 되므로,
                // 분을 나타내기 위해서는 종료까지 남은 총 시간에 60을 나눠주면 그 몫이 분이 된다.
                // 분을 제외하고 남은 초를 나타내기 위해서는, (총 남은 시간 - (분*60) = 남은 초) 로 하면 된다.
            }

            @Override
            public void onFinish() { //시간이 다 되면 다이얼로그 종료
                authDialog.cancel();
            }
        }.start();
        btnEmailAuth.setOnClickListener(this);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        countDownTimer.cancel();
    }

    public void init() {
        edtID = findViewById(R.id.register_edittext_id);
        edtPW = findViewById(R.id.register_edittext_pwd);
        edtEmail = findViewById(R.id.register_edittext_email);
        btnJoin = findViewById(R.id.register_button_join);
        btnConfirmID = findViewById(R.id.register_button_confirm_id);
        btnConfirmEmail = findViewById(R.id.register_button_confirm_email);
        btnConfirmEmail.setOnClickListener(this);
        btnConfirmID.setOnClickListener(this);
        btnJoin.setOnClickListener(this);
    }
}
