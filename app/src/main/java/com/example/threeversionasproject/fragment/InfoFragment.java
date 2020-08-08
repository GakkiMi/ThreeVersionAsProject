package com.example.threeversionasproject.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.threeversionasproject.R;

/**
 * Created by Ocean on 2018/4/16.
 */

public class InfoFragment extends Fragment {

    Button bt;
    View view;
    String TAG = "InfoFragment";



    public static InfoFragment newInstance(String param1) {
        InfoFragment fragment = new InfoFragment();
        Bundle args = new Bundle();
        args.putString("agrs1", param1);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.v(TAG, "--------onAttach");
        //Fragment attach到Activity时，将Activity强转为Callbacks保存
        if (context instanceof InfoCallback) {//如果宿主Activity实现了该接口
            infoCallback = (InfoCallback) context; //infoCallback 指向该Activity
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "--------onCreate");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.v(TAG, "--------onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.v(TAG, "--------onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.v(TAG, "--------onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.v(TAG, "--------onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.v(TAG, "--------onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.v(TAG, "--------onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v(TAG, "--------onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.v(TAG, "--------onDetach");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_info, container, false);
        initView();
        Log.v(TAG, "--------onCreateView");
        time = new TimeCount(10000, 1000);

        Bundle bundle = getArguments();
        String text = bundle.getString("agrs1");
        return view;
    }

    boolean flag = true;

    public void reload() {
       /* new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();*/
        if (flag) {
            time.start();
        }
    }

    private TimeCount time;

    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {
            flag = false;
            num++;
            infoCallback.updateBadgeCount(num);
        }

        @Override
        public void onFinish() {
            flag = true;
        }
    }


    int num = 0;

    public InfoCallback infoCallback;

    public interface InfoCallback {
        void updateBadgeCount(int num);
    }

    public void initView() {
        bt = (Button) view.findViewById(R.id.fragment_info_bt);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                num++;
                infoCallback.updateBadgeCount(num);
            }
        });
    }


}
