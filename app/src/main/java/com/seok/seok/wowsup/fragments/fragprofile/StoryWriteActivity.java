package com.seok.seok.wowsup.fragments.fragprofile;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.seok.seok.wowsup.R;
import com.seok.seok.wowsup.TranslateActivity;
import com.seok.seok.wowsup.retrofit.model.ResponseWriteObj;
import com.seok.seok.wowsup.retrofit.model.RespsonseImageObj;
import com.seok.seok.wowsup.retrofit.remote.ApiUtils;
import com.seok.seok.wowsup.utilities.Common;
import com.seok.seok.wowsup.utilities.GlobalWowToken;
import com.seok.seok.wowsup.utilities.ViewDialog;
import com.tylersuehr.chips.Chip;
import com.tylersuehr.chips.ChipDataSource;
import com.tylersuehr.chips.ChipsInputLayout;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoryWriteActivity extends AppCompatActivity {
    private final String TAG = StoryWriteActivity.class.getName();
    private ImageView imageView1, imageView2, imageView3, imageView4, imageView5,btnHelp, btnBack, btnPickImage;
    private LinearLayout layoutBackground;
    private String imageBackgroundURL, mediaPath;
    private Button btnSave, btnUpload;
    public static EditText editTextTitle, editTextBody;
    private ChipsInputLayout chipsInputLayout;
    private int imageOption, editOption;
    private static int RESULT_LOAD_IMAGE=0;
    private static final int WRITE_PERMISSION = 0x01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_write);
        requestWritePermission();

        btnSave = findViewById(R.id.story_write_btn_save);
        btnBack = findViewById(R.id.story_write_btn_back);
        editTextTitle = findViewById(R.id.story_write_edit_title);
        editTextBody = findViewById(R.id.story_write_edit_body);
        chipsInputLayout = findViewById(R.id.chips_input);
        btnPickImage = findViewById(R.id.story_write_btn_picture);
        //이미지뷰 아이디값 및 클릭 시 클릭이벤트로 넘어가게 만듬 (익명 클래스로 구현 시 코드 복잡해짐)
        layoutBackground = findViewById(R.id.story_write_layout_back);
        btnHelp = findViewById(R.id.story_write_btn_help);
        imageView1 = findViewById(R.id.story_write_imageview_back1);
        imageView2 = findViewById(R.id.story_write_imageview_back2);
        imageView3 = findViewById(R.id.story_write_imageview_back3);
        imageView4 = findViewById(R.id.story_write_imageview_back4);
        imageView5 = findViewById(R.id.story_write_imageview_back5);
        editTextTitle.setOnFocusChangeListener(onFocusChangeListener);
        editTextBody.setOnFocusChangeListener(onFocusChangeListener);
        btnHelp.setOnClickListener(onClickListener);
        imageView1.setOnClickListener(onClickListener);
        imageView2.setOnClickListener(onClickListener);
        imageView3.setOnClickListener(onClickListener);
        imageView4.setOnClickListener(onClickListener);
        imageView5.setOnClickListener(onClickListener);
        btnPickImage.setOnClickListener(onClickListener);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // Tag 공간 변경이일어났을 때
        chipsInputLayout.addChangeObserver(new ChipDataSource.ChangeObserver() {
            @Override
            public void onChipDataSourceChanged() {
                if (chipsInputLayout.getChipDataSource().getSelectedChips().size() <= 5)
                    chipsInputLayout.setCustomChipsEnabled(true);
            }
        });
        chipsInputLayout.addSelectionObserver(new ChipDataSource.SelectionObserver() {
            @Override
            // 사용자가 Enter 를 눌렀을 경우
            public void onChipSelected(Chip chip) {
                if (chipsInputLayout.getChipDataSource().getSelectedChips().size() >= 5)
                    chipsInputLayout.setCustomChipsEnabled(false);
            }

            @Override
            // 사용자가 X 표시를 눌렀을 경우
            public void onChipDeselected(Chip chip) {

            }
        });
        // 글쓰기 다하고 다이얼로그 띄워서 확인을 누를경우 서버에 올리기
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 다이얼로그 작성할것
                // 태그 5개를 생성해서 현재 있는 Tag들을 배열에 넣어둠
                String[] tag = new String[5];
                int tagSize = chipsInputLayout.getChipDataSource().getSelectedChips().size();
                for (int i = 0; i < tagSize; i++) {
                    tag[i] = chipsInputLayout.getChipDataSource().getSelectedChip(i).getTitle();
                }
                // 태그 5개가 안되었을 시 null 로 대체
                while (tagSize < 5) {
                    tag[tagSize++] = "";
                }
                ViewDialog viewDialog = new ViewDialog(StoryWriteActivity.this, 2);
                viewDialog.setButtonText("No", "Yes");
                viewDialog.requestStoryUpload(GlobalWowToken.getInstance().getId(), editTextTitle.getText().toString(), editTextBody.getText().toString(),
                        Common.STORY_IMAGE_BASE_URL + imageBackgroundURL, tag[0], tag[1], tag[2], tag[3], tag[4], imageOption);
                viewDialog.show();
            }
        });
    }
    View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            switch (v.getId()) {
                case R.id.story_write_edit_title:
                    if(hasFocus)
                        Common.translateOption = 1;
                    break;
                case R.id.story_write_edit_body:
                    if(hasFocus)
                        Common.translateOption = 2;
                    break;
            }
        }
    };

    View.OnClickListener onClickListener = new ImageView.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.story_write_imageview_back1:
                    layoutBackground.setBackgroundResource(R.drawable.basic_image_1_st);
                    imageBackgroundURL = "basic_image_1_st.png";
                    imageOption = 0;
                    break;
                case R.id.story_write_imageview_back2:
                    layoutBackground.setBackgroundResource(R.drawable.basic_image_2_nd);
                    imageBackgroundURL = "basic_image_2_nd.png";
                    imageOption = 0;
                    break;
                case R.id.story_write_imageview_back3:
                    layoutBackground.setBackgroundResource(R.drawable.basic_image_3_rd);
                    imageBackgroundURL = "basic_image_3_rd.png";
                    imageOption = 0;
                    break;
                case R.id.story_write_imageview_back4:
                    layoutBackground.setBackgroundResource(R.drawable.basic_image_4_th);
                    imageBackgroundURL = "basic_image_4_th.png";
                    imageOption = 0;
                    break;
                case R.id.story_write_imageview_back5:
                    layoutBackground.setBackgroundResource(R.drawable.unclick_color_1_st);
                    imageBackgroundURL = "basic_image_5_th.png";
                    imageOption = 0;
                    break;
                case R.id.story_write_btn_picture:
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE );
                    break;
                case R.id.story_write_btn_help:
                    startActivity(new Intent(StoryWriteActivity.this, TranslateActivity.class));
                    break;
            }
        }
    };
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if(requestCode == WRITE_PERMISSION){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Log.d(LOG_TAG, "Write Permission Failed");
                Toast.makeText(this, "You must allow permission write external storage to your mobile device.", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // 이미지 선택할떄.
            if (requestCode == RESULT_LOAD_IMAGE  && resultCode == RESULT_OK && null != data) {
                // 이미지 픽 밑 세부정도 얻어오기
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mediaPath = cursor.getString(columnIndex);
                imageView1.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                cursor.close();

            } else {
                Toast.makeText(this, "이미지선택이 안됨쓰코리아", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }
    }

    private void uploadFile() {
        // Map is used to multipart the file using okhttp3.RequestBody
        File file = new File(mediaPath);
        // Parsing any Media type file
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

        Call<RespsonseImageObj> call = ApiUtils.getImageService().uploadFile(fileToUpload, filename);


        call.enqueue(new Callback<RespsonseImageObj>() {
            @Override
            public void onResponse(Call<RespsonseImageObj> call, Response<RespsonseImageObj> response) {
                Log.d("aaa","0-1");
                RespsonseImageObj serverResponse = response.body();
                Log.d("aaa","0-2");
                Log.d("aaa",serverResponse.toString());
                if (serverResponse != null) {
                    if (serverResponse.getSuccess()) {
                        Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("aaa","1");
                    } else {
                        Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("aaa","12");
                    }
                } else {
                    assert serverResponse != null;
                    Log.d("aaa","0-3");
                    // Log.v("Response", serverResponse.toString());
                }
            }
            @Override
            public void onFailure(Call<RespsonseImageObj> call, Throwable t) {
                Toast.makeText(StoryWriteActivity.this,"통신실패",Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void requestWritePermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},WRITE_PERMISSION);
            }
        }
    }
}
