package com.seok.seok.wowsup;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.seok.seok.wowsup.retrofit.model.ResponseLoginObj;
import com.seok.seok.wowsup.retrofit.model.ResponseRegisterObj;
import com.seok.seok.wowsup.retrofit.remote.ApiUtils;

import java.util.Hashtable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private Button btnJoin, btnConfirmID;
    private EditText edtID, edtPW, edtEmail;
    private Callback retrofitCallback;
    private boolean confirmID = false;

    //sein Test
    private String TAG = "RegisterActivity";
    private FirebaseAuth mAuth;
    private String emailTest, passwordTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();




        edtID = findViewById(R.id.register_edittext_id);
        edtPW = findViewById(R.id.register_edittext_pwd);
        edtEmail = findViewById(R.id.register_edittext_email);
        btnJoin = findViewById(R.id.register_button_join);
        btnConfirmID = findViewById(R.id.register_button_confirm_id);


        //콜백 메서드 구현 틀 : ResponseRegisterObj.class
        retrofitCallback = new Callback<ResponseRegisterObj>() {
            @Override
            public void onResponse(Call<ResponseRegisterObj> call, Response<ResponseRegisterObj> response) {
                // Server와 통신 성공 시
                if (response.isSuccessful()) {
                    ResponseRegisterObj body = response.body();
                    if (body.getState() == 0) {
                        Toast.makeText(RegisterActivity.this, "사용 가능한 ID", Toast.LENGTH_SHORT).show();
                        confirmID = true;
                    } else if (body.getState() == 1) {
                        Toast.makeText(RegisterActivity.this, "이미 사용 중", Toast.LENGTH_SHORT).show();
                        confirmID = false;
                    } else if (body.getState() == 2) {
                        //sein Test
                        emailTest = edtEmail.getText().toString();
                        passwordTest = edtPW.getText().toString();
                        userRegist(emailTest, passwordTest);

                        Toast.makeText(RegisterActivity.this, "가입 성공", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        finish();
                    } else if (body.getState() == 3) {
                        Toast.makeText(RegisterActivity.this, "통신 오류", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseRegisterObj> call, Throwable t) {
                // Server와 통신 실패시
                Toast.makeText(RegisterActivity.this, "통신오류", Toast.LENGTH_SHORT).show();
                Log.d("register_err", t.getMessage() + " < ");
            }
        };

        // Confirm 버튼 눌렀을 경우
        btnConfirmID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiUtils.getUserService().requestConfirmID(edtID.getText().toString()).enqueue(retrofitCallback);
            }
        });
        // Confirm 버튼 눌렀을 경우
        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userPassword = edtPW.getText().toString();
                if(userPassword.isEmpty() || userPassword.length() < 6)
                {
                    Toast.makeText(RegisterActivity.this, "비밀번호는 필히 6자 이상이어야 합니다.", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (confirmID) {
                        ApiUtils.getUserService().requestRegister(edtID.getText().toString(), edtPW.getText().toString(), edtEmail.getText().toString()).enqueue(retrofitCallback);
                    } else {
                        Toast.makeText(RegisterActivity.this, "ID 확인 요망", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
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

    public void userRegist(String email, String password){
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
}
