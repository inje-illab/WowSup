package com.seok.seok.wowsup.fragments.fragprofile;

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
import com.seok.seok.wowsup.retrofit.model.ResponseWriteObj;
import com.seok.seok.wowsup.retrofit.remote.ApiUtils;
import com.seok.seok.wowsup.utilities.Common;
import com.seok.seok.wowsup.utilities.GlobalWowToken;
import com.seok.seok.wowsup.utilities.ViewDialog;
import com.tylersuehr.chips.Chip;
import com.tylersuehr.chips.ChipDataSource;
import com.tylersuehr.chips.ChipsInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoryWriteActivity extends AppCompatActivity {
    private final String TAG = StoryWriteActivity.class.getName();
    private ImageView imageView1, imageView2, imageView3, imageView4, imageView5;
    private LinearLayout layoutBackground;
    private String imageBackgroundURL = "test_background3.jpg";
    private Button btnSave;
    private EditText editTextTitle, editTextBody;
    private ChipsInputLayout chipsInputLayout;
    private int imageOption = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_write);
        btnSave = findViewById(R.id.story_write_btn_save);
        editTextTitle = findViewById(R.id.story_write_edit_title);
        editTextBody = findViewById(R.id.story_write_edit_body);
        chipsInputLayout = findViewById(R.id.chips_input);

        //이미지뷰 아이디값 및 클릭 시 클릭이벤트로 넘어가게 만듬 (익명 클래스로 구현 시 코드 복잡해짐)
        layoutBackground = findViewById(R.id.story_write_layout_back);
        imageView1 = findViewById(R.id.story_write_imageview_back1);
        imageView2 = findViewById(R.id.story_write_imageview_back2);
        imageView3 = findViewById(R.id.story_write_imageview_back3);
        imageView4 = findViewById(R.id.story_write_imageview_back4);
        imageView5 = findViewById(R.id.story_write_imageview_back5);
        imageView1.setOnClickListener(onClickListener);
        imageView2.setOnClickListener(onClickListener);
        imageView3.setOnClickListener(onClickListener);
        imageView4.setOnClickListener(onClickListener);
        imageView5.setOnClickListener(onClickListener);

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
                        Common.API_IMAGE_BASE_URL + imageBackgroundURL, tag[0], tag[1], tag[2], tag[3], tag[4], imageOption);
                viewDialog.show();
            }
        });
    }

    ImageView.OnClickListener onClickListener = new ImageView.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.story_write_imageview_back1:
                    layoutBackground.setBackgroundResource(R.drawable.test_background);
                    imageBackgroundURL = "test_background.jpg";
                    imageOption = 0;
                    break;
                case R.id.story_write_imageview_back2:
                    layoutBackground.setBackgroundResource(R.drawable.test_background1);
                    imageBackgroundURL = "test_background1.jpg";
                    imageOption = 0;
                    break;
                case R.id.story_write_imageview_back3:
                    layoutBackground.setBackgroundResource(R.drawable.test_background2);
                    imageBackgroundURL = "test_background2.jpg";
                    imageOption = 0;
                    break;
                case R.id.story_write_imageview_back4:
                    layoutBackground.setBackgroundResource(R.drawable.test_background3);
                    imageBackgroundURL = "test_background3.jpg";
                    imageOption = 0;
                    break;
                case R.id.story_write_imageview_back5:
                    layoutBackground.setBackgroundResource(R.drawable.test);
                    imageBackgroundURL = "test.png";
                    imageOption = 0;
                    break;
            }
        }
    };
}
