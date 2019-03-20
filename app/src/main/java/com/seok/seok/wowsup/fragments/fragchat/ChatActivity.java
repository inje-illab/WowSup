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

public class ChatActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Button btnTrans, btnSend, btnBack;
    public static EditText txtText;
    private String email, strUid, strFriendUid, delimiter;
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
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final Intent intent  = getIntent();
        strFriendUid = intent.getStringExtra("friendUid");

        /*if (user != null) {
            email = user.getEmail();
            strUid = user.getUid();
        }*/

        email = GlobalWowToken.getInstance().getUserEmail();
        strUid = GlobalWowToken.getInstance().getId();

        //UserInfomation
        txtText = (EditText)findViewById(R.id.txtText);
        btnTrans = (Button)findViewById(R.id.btnTrans);
        btnSend = (Button)findViewById(R.id.btnSend);
        btnBack =(Button)findViewById(R.id.btnBack);

        btnTrans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatActivity.this, TranslateActivity.class);
                startActivity(intent);
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strText = txtText.getText().toString();
                if(strText.equals("") || strText.isEmpty())
                {
                    Toast.makeText(ChatActivity.this, "내용을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    sentenceSend(strText);

                    Calendar c = Calendar.getInstance();
                    System.out.println("Current time => " + c.getTime());

                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String formattedDate = df.format(c.getTime());


                    DatabaseReference myRef = database.getReference("users").child(GlobalWowToken.getInstance().getId()).child("friends").child(strFriendUid).child("chat").child(formattedDate);
                    DatabaseReference myRef1 = database.getReference("users").child(strFriendUid).child("friends").child(GlobalWowToken.getInstance().getId()).child("chat").child(formattedDate);
                    Hashtable<String, String> chat = new Hashtable<String, String>();
                    chat.put("email", email);
                    chat.put("text", strText);

                    myRef.setValue(chat);
                    myRef1.setValue(chat);

                    txtText.setText("");
                }
            }
        });

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

        mChat= new ArrayList<>();
        mAdapter = new MyAdapter(mChat, email);
        mRecyclerView.setAdapter(mAdapter);


        DatabaseReference myRef = database.getReference("users").child(GlobalWowToken.getInstance().getId()).child("friends").child(strFriendUid).child("chat");
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Chat chat = dataSnapshot.getValue(Chat.class);

                mChat.add(chat);
                mRecyclerView.scrollToPosition(mChat.size()-1);
                mAdapter.notifyItemInserted(mChat.size()-1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void sentenceSend(String sentence){
        String[] words = sentence.replaceAll("[^a-zA-Z]", " ").split(delimiter);
        for (String word : words) {
            if (!word.equals("")) {
                wordMap.put((wordCount++) + "", word);
            }
        }

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
