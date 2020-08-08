package com.example.threeversionasproject.messageboard;

/**
 * Created by Ocean on 2019/11/29.
 */

public class ConvMsgEntity {

    private String talk_flag;//  消息类型  text为文字类型  voice为语音类型
    private String talk_url;//  消息主题   文字/语音url             ,
    private String talk_time;//  消息时间
    private String talk_status;//   消息发送方  left为左边布局  right为右边布局
    private String talk_second;//   语音时长

    private int audioImg;


    public String getTalk_flag() {
        return talk_flag;
    }

    public void setTalk_flag(String talk_flag) {
        this.talk_flag = talk_flag;
    }

    public String getTalk_url() {
        return talk_url;
    }

    public void setTalk_url(String talk_url) {
        this.talk_url = talk_url;
    }

    public String getTalk_time() {
        return talk_time;
    }

    public void setTalk_time(String talk_time) {
        this.talk_time = talk_time;
    }

    public String getTalk_status() {
        return talk_status == null ? "patient" : talk_status.trim();
    }

    public void setTalk_status(String talk_status) {
        this.talk_status = talk_status;
    }


    public int getAudioImg() {
        return audioImg;
    }

    public void setAudioImg(int audioImg) {
        this.audioImg = audioImg;
    }


    public String getTalk_second() {
        return talk_second == null ? "0" : talk_second.trim();
    }

    public void setTalk_second(String talk_second) {
        this.talk_second = talk_second;
    }
}
