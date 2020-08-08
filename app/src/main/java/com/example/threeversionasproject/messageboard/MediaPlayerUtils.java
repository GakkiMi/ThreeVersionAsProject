package com.example.threeversionasproject.messageboard;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.widget.Toast;

import com.example.threeversionasproject.R;
import com.example.threeversionasproject.application.MyApplication;
import com.example.threeversionasproject.retrofit.utils.NetUtils;

import java.io.IOException;

/**
 * Created by Ocean on 2018/5/20.
 */

public class MediaPlayerUtils {

    public int currentPosition;

    private static MediaPlayer mMediaPlayer;

    private static boolean isLock = false;

    public void initMediaPlayer(final Context context, Uri uri, int selectPosition) {
//        Logger.i("", "-----------isLock:" + isLock);
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
        }
        if (!mMediaPlayer.isPlaying()) {
            if (isLock == false) {
                playAudio(context, uri, selectPosition);
            }
        } else {
            mMediaPlayer.reset();
            controlAnimaState.animStop();
            //如果点击的音频的selectPosition不是currentPosition  则让当前音频暂停，并播放新的音频，否则直接暂停即可
            if (currentPosition == selectPosition) {
            } else {
                playAudio(context, uri, selectPosition);
            }
        }
    }

    public void playAudio(final Context context, Uri uri, int selectPosition) {
        currentPosition = selectPosition;
//        if (NetUtils.isNetWorkAvailable(context)) {
            try {
                mMediaPlayer.setDataSource(context, uri);
                mMediaPlayer.prepareAsync();
                controlAnimaState.dialogShow();
                mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
//                    ToastUtils.show(context, "准备完毕");
                        mMediaPlayer.start();
                        isLock = false;
                        controlAnimaState.dialogDismiss();
                        controlAnimaState.animStart();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
            isLock = true;
//        } else {
//            String str = "网络异常，请检查您的网络状态";
//            Toast.makeText(MyApplication.getContext(), str,Toast.LENGTH_SHORT).show();
//        }
        mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                mMediaPlayer.reset();
                isLock = false;
                String str = "音频资源加载失败";
                Toast.makeText(MyApplication.getContext(), str,Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mMediaPlayer.reset();
                controlAnimaState.animStop();
            }
        });
    }


    public void pauseAudio() {
        if (!mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
        }
    }


    public void releaseAudio() {
        if (mMediaPlayer != null) {
            isLock = false;
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    public ControlAnimaState controlAnimaState;

    public void setControlAnimaState(ControlAnimaState controlAnimaState) {
        this.controlAnimaState = controlAnimaState;
    }

    public interface ControlAnimaState {

        void dialogShow();

        void dialogDismiss();

        void animStart();

        void animStop();
    }


    public boolean isMediaPlaying() {
        if (mMediaPlayer.isPlaying()) {
            return true;
        } else {
            return false;
        }
    }


}
