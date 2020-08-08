package com.example.threeversionasproject.messageboard;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.threeversionasproject.R;
import com.example.threeversionasproject.widget.ShapeTextView;

import java.util.List;

/**
 * Created by Ocean on 2019/11/29.
 */

public class ConvMsgAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<ConvMsgEntity> mDatas;
    private MediaPlayerUtils mediaRightPlayerUtils;
    private Dialog mediaPlayerDialog;
    private int index;

    public ConvMsgAdapter(Context context, List<ConvMsgEntity> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
        mediaRightPlayerUtils = new MediaPlayerUtils();
        if (mediaPlayerDialog == null) {
            initProgressDialog();
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case Constants.ChatType.Right_TEXT:
                view = LayoutInflater.from(context).inflate(R.layout.item_chat_view_type_right_text, parent, false);
                return new MyDrTextViewHolder(view);
            case Constants.ChatType.Right_VOICE:
                view = LayoutInflater.from(context).inflate(R.layout.item_chat_view_type_right_audio, parent, false);
                return new MyDrAudioViewHolder(view);
            case Constants.ChatType.Left_TEXT:
                view = LayoutInflater.from(context).inflate(R.layout.item_chat_view_type_left_text, parent, false);
                return new MyPatientTextViewHolder(view);
            case Constants.ChatType.Left_VOICE:
                view = LayoutInflater.from(context).inflate(R.layout.item_chat_view_type_left_audio, parent, false);
                return new MyPatientAudioViewHolder(view);
            default:
                break;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        final ConvMsgEntity msgEntity = mDatas.get(position);

        if (holder instanceof MyDrTextViewHolder) {
            String text = msgEntity.getTalk_url();
            MyDrTextViewHolder textViewHolder = (MyDrTextViewHolder) holder;
            textViewHolder.tv.setText(text);
            setConversationTime(textViewHolder.tvTime, position);
        } else if (holder instanceof MyDrAudioViewHolder) {
            MyDrAudioViewHolder audioViewHolder = (MyDrAudioViewHolder) holder;
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) ((MyDrAudioViewHolder) holder).stv.getLayoutParams();

            setConversationTime(audioViewHolder.tvTime, position);

            int voiceTime = Integer.parseInt(msgEntity.getTalk_second());

            if (voiceTime == 0) {
                layoutParams.width = dp2px(context, 50);
                audioViewHolder.stv.setLayoutParams(layoutParams);
            } else if (voiceTime == 1) {
                layoutParams.width = dp2px(context, 70);
                audioViewHolder.stv.setLayoutParams(layoutParams);
            } else if (voiceTime > 1 && voiceTime <= 10) {
                layoutParams.width = dp2px(context, 70 + ((voiceTime - 1) * 10));
                audioViewHolder.stv.setLayoutParams(layoutParams);
            } else if (voiceTime > 10) {
                layoutParams.width = dp2px(context, 170);
                audioViewHolder.stv.setLayoutParams(layoutParams);
            }
            audioViewHolder.tv.setText(voiceTime + "''");
            audioViewHolder.tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    index = position;
                    //每次点击将iv图标设成默认
                    for (int i = 0; i < mDatas.size(); i++) {
                        if ("voice".equals(msgEntity.getTalk_flag())) {
                            msgEntity.setAudioImg(R.drawable.voice_white_volume_three);
                            notifyItemChanged(i, "localrefresh");
                        }
                    }
                    Log.i("", "----------医生点击" + position);
                    Uri uri = Uri.parse(msgEntity.getTalk_url());
                    mediaRightPlayerUtils.initMediaPlayer(context, uri, position);

                }
            });


        } else if (holder instanceof MyPatientTextViewHolder) {
            String text = msgEntity.getTalk_url();
            MyPatientTextViewHolder textViewHolder = (MyPatientTextViewHolder) holder;
            textViewHolder.tv.setText(text);
            setConversationTime(textViewHolder.tvTime, position);
        } else if (holder instanceof MyPatientAudioViewHolder) {
            MyPatientAudioViewHolder audioViewHolder = (MyPatientAudioViewHolder) holder;
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) ((MyPatientAudioViewHolder) holder).stv.getLayoutParams();

            setConversationTime(audioViewHolder.tvTime, position);

            int voiceTime = Integer.parseInt(msgEntity.getTalk_second());

            if (voiceTime == 0) {
                layoutParams.width = dp2px(context, 50);
                audioViewHolder.stv.setLayoutParams(layoutParams);
            } else if (voiceTime == 1) {
                layoutParams.width = dp2px(context, 70);
                audioViewHolder.stv.setLayoutParams(layoutParams);
            } else if (voiceTime > 1 && voiceTime <= 10) {
                layoutParams.width = dp2px(context, 70 + ((voiceTime - 1) * 10));
                audioViewHolder.stv.setLayoutParams(layoutParams);
            } else if (voiceTime > 10) {
                layoutParams.width = dp2px(context, 170);
                audioViewHolder.stv.setLayoutParams(layoutParams);
            }
            audioViewHolder.tv.setText(voiceTime + "''");
            audioViewHolder.tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    index = position;
                    //每次点击将iv图标设成默认
                    for (int i = 0; i < mDatas.size(); i++) {
                        if ("voice".equals(msgEntity.getTalk_flag())) {
                            msgEntity.setAudioImg(R.drawable.voice_black_volume_three);
                            notifyItemChanged(i, "localrefresh");
                        }
                    }
                    Uri uri = Uri.parse(msgEntity.getTalk_url());
                    Log.i("", "----------病人点击" + position);
                    mediaRightPlayerUtils.initMediaPlayer(context, uri, position);

                }
            });
        }

        mediaRightPlayerUtils.setControlAnimaState(new MediaPlayerUtils.ControlAnimaState() {
            @Override
            public void dialogShow() {
                if (!mediaPlayerDialog.isShowing()) {
                    mediaPlayerDialog.show();
                }
            }

            @Override
            public void dialogDismiss() {
                if (mediaPlayerDialog.isShowing()) {
                    mediaPlayerDialog.dismiss();
                }
            }

            @Override
            public void animStart() {
                Log.i("", "----------动画开始");
                for (int i = 0; i < mDatas.size(); i++) {
                    ConvMsgEntity entity = mDatas.get(i);
                    if ("voice".equals(entity.getTalk_flag()) && "right".equals(entity.getTalk_status())) {
                        if (i == index) {
                            entity.setAudioImg(R.drawable.voice_white_play_anima);
                            notifyItemChanged(i, "localrefresh");
                        } else {
                            entity.setAudioImg(R.drawable.voice_white_volume_three);
                            notifyItemChanged(i, "localrefresh");
                        }
                    }else if ("voice".equals(entity.getTalk_flag()) && "left".equals(entity.getTalk_status())) {
                        if (i == index) {

                            entity.setAudioImg(R.drawable.voice_black_play_anima);
                            notifyItemChanged(i, "localrefresh");
                        } else {

                            entity.setAudioImg(R.drawable.voice_black_volume_three);
                            notifyItemChanged(i, "localrefresh");
                        }
                    }
                }
            }

            @Override
            public void animStop() {
                Log.i("", "----------动画结束");
                ConvMsgEntity msgEntity = mDatas.get(index);

                if ("voice".equals(msgEntity.getTalk_flag()) && "left".equals(msgEntity.getTalk_status())) {
                    msgEntity.setAudioImg(R.drawable.voice_black_volume_three);
                }else{
                    msgEntity.setAudioImg(R.drawable.voice_white_volume_three);
                }

                notifyItemChanged(index, "localrefresh");
            }
        });


    }

    private void setConversationTime(TextView tvTime, int position) {
        String time = mDatas.get(position).getTalk_time();
        if (TextUtils.isEmpty(time)) {
            tvTime.setVisibility(View.GONE);
        } else {
            tvTime.setVisibility(View.VISIBLE);
            tvTime.setText(time);
        }

    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
        if (holder instanceof MyDrAudioViewHolder) {
            ConvMsgEntity msgEntity = mDatas.get(position);

            MyDrAudioViewHolder audioViewHolder = (MyDrAudioViewHolder) holder;
            ImageView imageView = audioViewHolder.ivAnima;
            imageView.setImageResource(msgEntity.getAudioImg());

            if (msgEntity.getAudioImg() == R.drawable.voice_white_play_anima) {
                AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getDrawable();
                animationDrawable.start();
            }
        } else if (holder instanceof MyPatientAudioViewHolder) {

            ConvMsgEntity msgEntity = mDatas.get(position);

            MyPatientAudioViewHolder audioViewHolder = (MyPatientAudioViewHolder) holder;
            ImageView imageView = audioViewHolder.ivAnima;
            imageView.setImageResource(msgEntity.getAudioImg());

            if (msgEntity.getAudioImg() == R.drawable.voice_black_play_anima) {
                Log.i("", "----------病人动画刷新了");
                AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getDrawable();
                animationDrawable.start();
            }
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public int getItemViewType(int position) {
        String talkStatus = mDatas.get(position).getTalk_status();
        String talkFlag = mDatas.get(position).getTalk_flag();
        int type = 0;
        if ("right".equals(talkStatus)) {
            //返回医生的消息类型
            if ("text".equals(talkFlag)) {
                type = Constants.ChatType.Right_TEXT;
            } else if ("voice".equals(talkFlag)) {
                type = Constants.ChatType.Right_VOICE;
            }
        } else {
            //返回病人的消息类型
            if ("text".equals(talkFlag)) {
                type = Constants.ChatType.Left_TEXT;
            } else if ("voice".equals(talkFlag)) {
                type = Constants.ChatType.Left_VOICE;
            }
        }
        return type;
    }


    private void resetVoiceImg() {
        for (int i = 0; i < mDatas.size(); i++) {

            ConvMsgEntity msgEntity = mDatas.get(i);

            msgEntity.setAudioImg(R.drawable.voice_white_volume_three);
            notifyItemChanged(i, "localrefresh");//局部刷新
        }
    }

    private int dp2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        int px = (int) (dpValue * scale + 0.5f);
        return px;
    }


    public void initProgressDialog() {
        View v = LayoutInflater.from(context).inflate(R.layout.media_player_dialog, null);
        mediaPlayerDialog = new Dialog(context, R.style.http_request_dialog_style);
//        progressDialog.setCancelable(false);
        //设置点击空白部分不消失  点击返回键可消失
        mediaPlayerDialog.setCanceledOnTouchOutside(false);
        mediaPlayerDialog.setContentView(v);
        mediaPlayerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }


    public void dismissDialog() {
        if (mediaPlayerDialog.isShowing()) {
            mediaPlayerDialog.dismiss();
        }
    }


    public class MyDrTextViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout rlRoot;
        private TextView tv;
        private TextView tvTime;

        public MyDrTextViewHolder(View itemView) {
            super(itemView);
            tvTime = (TextView) itemView.findViewById(R.id.item_chat_view_type_text_tv_time);
            rlRoot = (RelativeLayout) itemView.findViewById(R.id.item_chat_view_type_text_rl_root);
            tv = (TextView) itemView.findViewById(R.id.item_chat_view_type_text_tv);
        }
    }

    public class MyDrAudioViewHolder extends RecyclerView.ViewHolder {

        private TextView tv;
        private ShapeTextView stv;
        private ImageView ivAnima;
        private TextView tvTime;

        public MyDrAudioViewHolder(View itemView) {
            super(itemView);
            tvTime = (TextView) itemView.findViewById(R.id.item_chat_view_type_audio_tv_time);
            tv = (TextView) itemView.findViewById(R.id.item_chat_view_type_audio_stv);
            stv = (ShapeTextView) itemView.findViewById(R.id.item_chat_view_type_audio_stv);
            ivAnima = (ImageView) itemView.findViewById(R.id.item_chat_view_type_audio_iv_anima);
        }
    }


    public class MyPatientTextViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout rlRoot;
        private TextView tv;
        private TextView tvTime;

        public MyPatientTextViewHolder(View itemView) {
            super(itemView);
            tvTime = (TextView) itemView.findViewById(R.id.item_chat_view_type_patient_text_tv_time);
            rlRoot = (RelativeLayout) itemView.findViewById(R.id.item_chat_view_type_patient_text_rl_root);
            tv = (TextView) itemView.findViewById(R.id.item_chat_view_type_patient_text_tv);
        }
    }

    public class MyPatientAudioViewHolder extends RecyclerView.ViewHolder {

        private TextView tv;
        private ShapeTextView stv;
        private ImageView ivAnima;
        private TextView tvTime;

        public MyPatientAudioViewHolder(View itemView) {
            super(itemView);
            tvTime = (TextView) itemView.findViewById(R.id.item_chat_view_type_patient_audio_tv_time);
            tv = (TextView) itemView.findViewById(R.id.item_chat_view_type_patient_audio_stv);
            stv = (ShapeTextView) itemView.findViewById(R.id.item_chat_view_type_patient_audio_stv);
            ivAnima = (ImageView) itemView.findViewById(R.id.item_chat_view_type_patient_audio_iv_anima);
        }
    }


}