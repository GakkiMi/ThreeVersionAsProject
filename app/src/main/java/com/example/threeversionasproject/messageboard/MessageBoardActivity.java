package com.example.threeversionasproject.messageboard;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.threeversionasproject.R;
import com.example.threeversionasproject.application.MyApplication;
import com.example.threeversionasproject.widget.PointProcessBar;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class MessageBoardActivity extends AppCompatActivity implements
        View.OnClickListener, View.OnTouchListener {

    //底部面板View
    LinearLayout llBottomPanel;
    //输入框
    EditText et;
    Button btSpeak;
    //内容显示区域，当点击此区域时，隐藏软键盘和面板
    RelativeLayout rlContent;
    //监听软键盘View
    KeyboardLayout keyboardLayout;

    ImageView ivSwitch;
    ImageView ivAdd;
    ImageView ivCamera;

    RelativeLayout rlPanelOne;
    RelativeLayout rlPanelTwo;


    RecyclerView rvChatList;

    RelativeLayout audioLayout;
    Chronometer chronometer;
    TextView tvAudioTv;
    ImageView ivAudio;

    private int llBottomOtherHeight;

    private int screenHeight = 0;

    private boolean isShowingInput = false;//软键盘是否正在显示

    private int mKeyboardHeight = 400; // 输入法默认高度为400

    private String switchTag = "text";  //text为文字聊天  voice为语音聊天

    //聊天布局的适配器和数据源
    private ConvMsgAdapter conversationAdapter;
    private List<ConvMsgEntity> conversationList;


    private MediaUtils mediaUtils;
    private boolean isCancel;//语音说话时是否取消的标识
    private String mDuration;

    PointProcessBar pointProcessBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_board);
        initData();
        initView();


    }


    private void initData() {
        llBottomOtherHeight = 500;
        //获取屏幕高度  
        screenHeight = this.getWindowManager().getDefaultDisplay().getHeight();

        conversationList = new ArrayList<>();

        mediaUtils = new MediaUtils(this);
        mediaUtils.setRecorderType(MediaUtils.MEDIA_AUDIO);
//        mediaUtils.setTargetDir(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC));

        //创建音频文件夹 需要读写权限
        String path = Environment.getExternalStorageDirectory() + "/AUDIO/audio/";

        File folder = new File(path);
        if (folder != null && !folder.exists()) {
            folder.mkdirs();
        }
        mediaUtils.setTargetDir(folder);

    }

    private void initView() {
        pointProcessBar = (PointProcessBar) findViewById(R.id.point_process_bar);
        List<String> list = new ArrayList<>();

        Set<Integer> set = new HashSet<>();
        set.add(0);
        set.add(1);
        set.add(4);
        for (int i = 0; i < 5; i++) {
            list.add("任务" + i);
        }

        pointProcessBar.show(list, set);


        llBottomPanel = (LinearLayout) findViewById(R.id.message_board_rl_bottom_other);
        et = (EditText) findViewById(R.id.message_board_et_chat);
        btSpeak = (Button) findViewById(R.id.message_board_stv_speak);
        rlContent = (RelativeLayout) findViewById(R.id.message_board_rl_content);
        keyboardLayout = (KeyboardLayout) findViewById(R.id.message_board_keyboard_layout);

        rlPanelOne = (RelativeLayout) findViewById(R.id.panel_one);
        rlPanelTwo = (RelativeLayout) findViewById(R.id.panel_two);

        ivSwitch = (ImageView) findViewById(R.id.message_board_iv_switch);
        ivAdd = (ImageView) findViewById(R.id.message_board_iv_add);
        ivCamera = (ImageView) findViewById(R.id.message_board_iv_camera);

        audioLayout = (RelativeLayout) findViewById(R.id.conversation_audio_layout);
        chronometer = (Chronometer) findViewById(R.id.conversation_audio_chronometer);
        tvAudioTv = (TextView) findViewById(R.id.conversation_audio_tv_info);
        ivAudio = (ImageView) findViewById(R.id.conversation_audio_iv);


        ivSwitch.setOnClickListener(this);
        ivAdd.setOnClickListener(this);
        ivCamera.setOnClickListener(this);

        btSpeak.setOnTouchListener(this);

        rvChatList = (RecyclerView) findViewById(R.id.message_board_rv_list);

        conversationAdapter = new ConvMsgAdapter(this, conversationList);
        rvChatList.setLayoutManager(new LinearLayoutManager(this));
        rvChatList.setAdapter(conversationAdapter);


        et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击输入框时，软键盘会直接弹出。如果面板显示，则进行300ms的延时隐藏，防止跳闪。
                if (llBottomPanel.getVisibility() == View.VISIBLE) {
                    llBottomPanel.postDelayed(mHideEmotionPanelTask, 300);
                }
            }
        });

        //输入法右下角Action事件
        et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                sendText();
                return true;
            }
        });


        keyboardLayout.setKeyboardListener(new KeyboardLayout.KeyboardLayoutListener() {
            @Override
            public void onKeyboardStateChanged(boolean isActive, int keyboardHeight) {
                isShowingInput = isActive;
                if (isActive) { // 输入法打开
                    scrollRvToLast();
//                    Log.i("", "-----输入法打开--键盘高度：" + keyboardHeight);
                    if (mKeyboardHeight != keyboardHeight) { // 键盘发生改变时才设置底部面板的高度，因为会触发onGlobalLayoutChanged，导致onKeyboardStateChanged再次被调用
                        mKeyboardHeight = keyboardHeight;
                        updatePanelHeight(); // 每次输入法弹起时，设置底部面板的高度为键盘的高度，以便下次底部面板弹出时刚好等于键盘高度
                    }
                } else {
//                    Log.i("", "-----输入法关闭--键盘高度：" + keyboardHeight);
                }
            }
        });

        //触摸空白处进行软键盘和面板的隐藏
        rvChatList.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
                        hidePanelOrInput();
                    }
                }
                return false;
            }
        });
    }

    //发送文字
    private void sendText() {
        String text = et.getText().toString().trim();
        if (TextUtils.isEmpty(text)) {
            return;
        }
        et.setText("");

        ConvMsgEntity msgEntity = new ConvMsgEntity();
        msgEntity.setTalk_flag("text");
        String currentHm = formatDateToNeed(new Date(), "HH:mm");
        msgEntity.setTalk_time(currentHm);
        msgEntity.setTalk_url(text);
        msgEntity.setTalk_status(conversationList.size() % 2 == 0 ? "right" : "left");
        conversationList.add(msgEntity);
        rvChatListNotify();

    }


    public static String formatDateToNeed(Date date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        String str = simpleDateFormat.format(date);
        return str;
    }


    //刷新列表
    private void rvChatListNotify() {
        if (conversationList.size() > 0) {
            conversationAdapter.notifyItemInserted(conversationList.size());
            rvChatList.smoothScrollToPosition(conversationList.size() - 1);
        }
    }

    //滚动到最后一条
    private void scrollRvToLast() {
        if (conversationList.size() > 0) {
            rvChatList.scrollToPosition(conversationList.size() - 1);
        }
    }


    // 每当输入框高度发生变化时，去改变面板的高度
    private void updatePanelHeight() {
        ViewGroup.LayoutParams layoutParams = llBottomPanel.getLayoutParams();
        layoutParams.height = mKeyboardHeight;
        llBottomOtherHeight = mKeyboardHeight;//将面板的高度设置为软键盘高度
        llBottomPanel.setLayoutParams(layoutParams);
    }

    private Runnable mHideEmotionPanelTask = new Runnable() {
        @Override
        public void run() {
            hidePanel(false, 0);
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.message_board_iv_add:
                rlPanelOne.setVisibility(View.VISIBLE);
                rlPanelTwo.setVisibility(View.GONE);
                show();
                break;
            case R.id.message_board_iv_camera:
                rlPanelOne.setVisibility(View.GONE);
                rlPanelTwo.setVisibility(View.VISIBLE);
                show();
                break;
            case R.id.message_board_iv_switch:
                if ("text".equals(switchTag)) {//去语音
                    hidePanelOrInput();

                    switchTag = "voice";
                    ivSwitch.setImageResource(R.drawable.img_chat_text);
                    btSpeak.setVisibility(View.VISIBLE);
                    et.setVisibility(View.GONE);
                } else {//去文字输入
                    switchTag = "text";
                    ivSwitch.setImageResource(R.drawable.img_chat_voice);
                    btSpeak.setVisibility(View.GONE);
                    et.setVisibility(View.VISIBLE);
                    et.requestFocus();
                }
                break;
            default:
                break;
        }
    }


    private void show() {
        if (isShowingInput) {//如果键盘显示
            if (llBottomPanel.getVisibility() == View.GONE) {//面板隐藏,则显示面板
                showPanel(false, 0);
            }
        } else {//键盘不显示
            if (llBottomPanel.getVisibility() == View.VISIBLE) {//如果面板显示 ，则隐藏面板，显示键盘
                Log.v("", "-----------键盘不显示  面板显示");
            } else {
                Log.v("", "-----------键盘不显示  面板不显示");
                showPanel(true, 300);

            }
        }
    }


    /**
     * 显示底部面板 （如果键盘已经出现，此时展示面板不需用动画，直接展示即可，如果键盘未显示，则增加一个向上的动画）
     *
     * @param isAnimal 是否以动画形式展现
     * @param duration 动画时长（默认为300ms）
     */
    private void showPanel(boolean isAnimal, long duration) {
        //设置软键盘为覆盖模式
        updateSoftInputMethod(this, WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        if (isAnimal) {
            ValueAnimator animator = ValueAnimator.ofInt(0, llBottomOtherHeight);
            setAnimaUpdateListener(animator, llBottomPanel);
            animator.setDuration(duration);
            animator.addListener(new AnimatorListenerAdapter() {

                @Override
                public void onAnimationStart(Animator animation) {
                    llBottomPanel.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                }
            });
            animator.start();
        } else {
            //恢复面板高度
            ViewGroup.LayoutParams layoutParams = llBottomPanel.getLayoutParams();
            layoutParams.height = llBottomOtherHeight;
            llBottomPanel.setLayoutParams(layoutParams);

            llBottomPanel.setVisibility(View.VISIBLE);
            hideInput();
        }
    }


    /**
     * 隐藏底部面板 （如果键盘已经出现，此时隐藏面板不需用动画，直接隐藏即可，如果键盘未显示，则增加一个向下的动画）
     *
     * @param isAnimal 是否以动画形式展现
     * @param duration 动画时长（默认为300ms）
     */
    private void hidePanel(boolean isAnimal, long duration) {
        //设置软键盘为挤压模式
        updateSoftInputMethod(this, WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        if (isAnimal) {
            ValueAnimator animator = ValueAnimator.ofInt(llBottomOtherHeight, 0);
            setAnimaUpdateListener(animator, llBottomPanel);
            animator.setDuration(duration);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    llBottomPanel.setVisibility(View.GONE);
                }
            });
            animator.start();
        } else {
            if (llBottomPanel.getVisibility() == View.VISIBLE) {
                llBottomPanel.setVisibility(View.GONE);
            }
        }


    }

    //更改输入法软键盘弹出方式
    public static void updateSoftInputMethod(Activity activity, int softInputMode) {
        if (!activity.isFinishing()) {
            WindowManager.LayoutParams params = activity.getWindow().getAttributes();
            if (params.softInputMode != softInputMode) {
                params.softInputMode = softInputMode;
                activity.getWindow().setAttributes(params);
            }
        }
    }

    //隐藏软键盘
    protected void hideInput() {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
    }

    //显示软键盘
    protected void showInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(et, InputMethodManager.SHOW_FORCED);
    }


    //属性动画是可以直接改变view的宽高的 通过不断改变view的属性来产生动画的效果
    public void setAnimaUpdateListener(ValueAnimator valueAnimator, final View view) {
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator arg0) {
                int value = (int) arg0.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.height = value;
                view.setLayoutParams(layoutParams);
            }
        });
    }


    //隐藏输入框或底部面板
    public void hidePanelOrInput() {
        //点击空白区域 如果软键盘显示则隐藏
        if (isShowingInput) {//
            hideInput();
        }
        //如果面板显示，则进行下降动画的隐藏
        if (llBottomPanel.getVisibility() == View.VISIBLE) {
            hidePanel(true, 300);
        }
    }


    @Override
    public void onBackPressed() {
        if (llBottomPanel.getVisibility() == View.VISIBLE) {
            hidePanel(true, 300);
            return;
        }
        super.onBackPressed();
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        boolean ret = false;
        float downY = 0;

        boolean flag = actionRecordAudio();
        if (flag == false) {
            Toast.makeText(MyApplication.getContext(), "未开启相关权限", Toast.LENGTH_SHORT).show();

        } else {

            switch (view.getId()) {
                case R.id.message_board_stv_speak:
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            startAnim(true);
                            mediaUtils.setTargetName("chat_" + System.currentTimeMillis() + ".mp3");
                            mediaUtils.record();
                            ret = true;
                            break;
                        case MotionEvent.ACTION_UP:
                            stopAnim();
                            if (isCancel) {
                                isCancel = false;
                                mediaUtils.stopRecordUnSave();
                                Toast.makeText(this, "取消发送", Toast.LENGTH_SHORT).show();
                            } else {
                                int duration = getDuration(chronometer.getText().toString());
                                switch (duration) {
                                    case -1:
                                        break;
                                    case -2:
                                        mediaUtils.stopRecordUnSave();
                                        Toast.makeText(this, "时间太短", Toast.LENGTH_SHORT).show();
                                        break;
                                    default:
                                        mediaUtils.stopRecordSave();
                                        String path = mediaUtils.getTargetFilePath();
                                        Log.i("", "-------path:" + path);
                                        long time = (SystemClock.elapsedRealtime() - chronometer.getBase()) / 1000;

                                        ConvMsgEntity msgEntity = new ConvMsgEntity();
                                        msgEntity.setTalk_flag("voice");
                                        String currentHm = formatDateToNeed(new Date(), "HH:mm");
                                        msgEntity.setTalk_time(currentHm);
                                        msgEntity.setTalk_url(path);
                                        msgEntity.setTalk_status(conversationList.size() % 2 == 0 ? "left" : "right");
                                        msgEntity.setAudioImg(conversationList.size() % 2 == 0 ? R.drawable.voice_black_volume_three : R.drawable.voice_white_volume_three);
                                        msgEntity.setTalk_second(time + "");
                                        conversationList.add(msgEntity);
                                        rvChatListNotify();

                                        break;
                                }
                            }
                            break;
                        case MotionEvent.ACTION_MOVE:
                            float currentY = event.getY();
                            if (downY - currentY > 10) {
                                moveAnim();
                                isCancel = true;
                            } else {
                                isCancel = false;
                                startAnim(false);
                            }
                            break;
                        default:
                            break;
                    }
                    break;
                default:
                    break;
            }
        }
        return ret;
    }

    private void startAnim(boolean isStart) {
        audioLayout.setVisibility(View.VISIBLE);
        tvAudioTv.setText("上滑取消");

        ivAudio.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_mic_white_24dp));

        btSpeak.setText("松开结束");
        btSpeak.setBackground(ContextCompat.getDrawable(this, R.drawable.chat_stv_speak_press_shape));
        if (isStart) {
            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.setFormat("%S");
            chronometer.start();
        }
    }

    private void stopAnim() {
        audioLayout.setVisibility(View.GONE);
        btSpeak.setText("按住说话");
        btSpeak.setBackground(ContextCompat.getDrawable(this, R.drawable.chat_stv_speak_normal_shape));
        chronometer.stop();
    }

    private void moveAnim() {
        tvAudioTv.setText("松手取消");
        ivAudio.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_undo_black_24dp));
    }


    private boolean actionRecordAudio() {
        //创建音频文件夹 需要读写权限  文件夹必须创建出来 不然没法发送语音
        String path = Environment.getExternalStorageDirectory() + "/AUDIO/audio/";
        File folder = new File(path);
        if (folder != null && !folder.exists()) {
            folder.mkdirs();
        }
        String[] permissions = new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (AppPermissionUtils.checkPermisssion(this, permissions)) {
            return true;
        } else {
            AppPermissionUtils.requestPermission(MessageBoardActivity.this, null, permissions, AppPermissionUtils.PERMISSION_REQUEST_CODE_AUDIO);
            return false;
        }
    }

    private int getDuration(String str) {
        String a = str.substring(0, 1);
        String b = str.substring(1, 2);
        String c = str.substring(3, 4);
        String d = str.substring(4);
        if (a.equals("0") && b.equals("0")) {
            if (c.equals("0") && Integer.valueOf(d) < 1) {
                return -2;
            } else if (c.equals("0") && Integer.valueOf(d) > 1) {
                mDuration = d;
                return Integer.valueOf(d);
            } else {
                mDuration = c + d;
                return Integer.valueOf(c + d);
            }
        } else {
            mDuration = "60";
            return -1;
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (AppPermissionUtils.isPermissionAllAccess(grantResults)) {
            switch (requestCode) {
                case AppPermissionUtils.PERMISSION_REQUEST_CODE_AUDIO:
                    break;
                default:
                    break;
            }
        } else {
            Toast.makeText(MyApplication.getContext(), "你还没有授予相关权限", Toast.LENGTH_SHORT).show();
        }
    }


}
