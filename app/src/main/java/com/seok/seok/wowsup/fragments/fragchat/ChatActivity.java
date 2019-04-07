package com.seok.seok.wowsup.fragments.fragchat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.seok.seok.wowsup.R;
import com.seok.seok.wowsup.TranslateActivity;
import com.seok.seok.wowsup.retrofit.model.ResponseChatWordObj;
import com.seok.seok.wowsup.retrofit.remote.ApiUtils;
import com.seok.seok.wowsup.utilities.Common;
import com.seok.seok.wowsup.utilities.GlobalWowToken;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
//채팅 엑티비티
public class ChatActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Button btnSend;
    private ImageView btnTrans, btnBack;
    public static EditText txtText;
    private String email, strFriendUid, delimiter;
    private int wordCount;
    private Map<String, String> wordMap;
    private FirebaseDatabase database;
    private List<Chat> mChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        wordMap = new HashMap<>();
        wordCount = 0;
        delimiter = " ";

        database = FirebaseDatabase.getInstance();
        final Intent intent = getIntent();

        //선택된 포지션의 친구아이디, 유저아이디    FireBase Hashtable NullPoint발생시 확인요망
        strFriendUid = intent.getStringExtra("friendUid");
        email = GlobalWowToken.getInstance().getUserEmail();

        //UserInfomation
        txtText = findViewById(R.id.txtText);
        btnTrans = findViewById(R.id.btnTrans);
        btnSend = findViewById(R.id.btnSend);
        btnBack = findViewById(R.id.btnBack);

        //채팅시 모르는 어휘가 나왔을 경우 번역페이지로 이동
        btnTrans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Common.translateOption = 3;
                startActivity(new Intent(ChatActivity.this, TranslateActivity.class));
            }
        });
        //채팅 보내기
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //채팅에 어떤 언어를 쳤는지 데이터베이스에 넣기위한 알고리즘
                String strText = txtText.getText().toString();
                boolean result = strText.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*");
                if (strText.equals("") || strText.isEmpty()) {
                    Toast.makeText(ChatActivity.this, "Please enter your details.", Toast.LENGTH_SHORT).show();
                } else {
                    if(result){
                        Toast.makeText(ChatActivity.this, "Contains non-English characters.", Toast.LENGTH_SHORT).show();
                    }else {
                        sentenceSend(strText);

                        Calendar c = Calendar.getInstance();
                        System.out.println("Current time => " + c.getTime());

                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String formattedDate = df.format(c.getTime());
                        //파이어베이스 연동
                        DatabaseReference myRef = database.getReference("users").child(GlobalWowToken.getInstance().getId()).child("friends").child(strFriendUid).child("chat").child(formattedDate);
                        DatabaseReference myRef1 = database.getReference("users").child(strFriendUid).child("friends").child(GlobalWowToken.getInstance().getId()).child("chat").child(formattedDate);
                        Hashtable<String, String> chat = new Hashtable<String, String>();
                        //이메일과 텍스트를 넘김
                        chat.put("email", email);
                        chat.put("text", strText);

                        myRef.setValue(chat);
                        myRef1.setValue(chat);

                        txtText.setText("");
                    }
                }
            }
        });
        //뒤로가기 버튼튼
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)

        mChat = new ArrayList<>();
        mAdapter = new MyAdapter(mChat, email);
        mRecyclerView.setAdapter(mAdapter);

        //파이어베이스에 넣기위한 응답
        DatabaseReference myRef = database.getReference("users").child(GlobalWowToken.getInstance().getId()).child("friends").child(strFriendUid).child("chat");
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                //채팅이 하나 늘었을 경우
                Chat chat = dataSnapshot.getValue(Chat.class);

                mChat.add(chat);
                mRecyclerView.scrollToPosition(mChat.size() - 1);
                mAdapter.notifyItemInserted(mChat.size() - 1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                //채팅이 하나 바뀌었을경우
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                //삭제했을 경우
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                //옮겨졌을 경우
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //취소했을 경우
            }
        });
    }

    public void sentenceSend(String sentence) {
        //a-Z 까지 알파벳이아닌경우 잘라내어 서버에 등록
        String[] words = sentence.replaceAll("[^a-zA-Z]", " ").split(delimiter);
        for (String word : words) {
            if (!word.equals("")) {
                wordMap.put((wordCount++) + "", word);
            }
        }
        // 서버등록 통신
        ApiUtils.getWordService().requestChatWord(wordMap).enqueue(new Callback<List<ResponseChatWordObj>>() {
            @Override
            public void onResponse(Call<List<ResponseChatWordObj>> call, Response<List<ResponseChatWordObj>> response) {
                Log.d("mapTrans", "map trans success");
            }

            @Override
            public void onFailure(Call<List<ResponseChatWordObj>> call, Throwable t) {
                Log.d("mapTrans", t.getMessage());
            }
        });
        wordCount = 0;
        wordMap.clear();
    }
}
