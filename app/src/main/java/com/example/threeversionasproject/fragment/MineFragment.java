package com.example.threeversionasproject.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.threeversionasproject.PhotoViewDialog;
import com.example.threeversionasproject.R;
import com.example.threeversionasproject.TabLayoutActivity;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by Ocean on 2018/4/16.
 */

public class MineFragment extends Fragment implements PhotoViewAdapter.PvOnclickListener{

    TextView tv;
    View view;
    String TAG = "MineFragment";

    ViewPager viewPager;
    PhotoView photoView;

    Button bt;

    List<Object> list;

    private Dialog dialog;

    public static MineFragment newInstance(String param1) {
        MineFragment fragment = new MineFragment();
        Bundle args = new Bundle();
        args.putString("agrs1", param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof UpdateBadgeImpl){
            updateBadge= (UpdateBadgeImpl) context;
        }else{
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mine, container, false);
//        Bundle bundle = getArguments();
//        String text = bundle.getString("agrs1");
        initView();
//        initData();
        return view;

    }

    private void initView() {
        viewPager = (ViewPager) view.findViewById(R.id.fragment_mine_vp);
        bt = (Button) view.findViewById(R.id.fg_mine_bt);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                look(5);
                updateBadge.update();
            }
        });
    }

    public interface UpdateBadgeImpl{
        void update();
    }

    public UpdateBadgeImpl updateBadge;

    public void setUpdateBadge(UpdateBadgeImpl updateBadge) {
        this.updateBadge = updateBadge;
    }


    private void initData() {
        list = new ArrayList<>();
        list.add(R.mipmap.bt_quanyaohaocaizhanbi);
        list.add(R.mipmap.bt_menzhenzhixingkeshishouru);
        list.add(R.mipmap.bt_quanyuanshouru);
        list.add(R.drawable.center);
        list.add(R.drawable.comm);

        list.add("http://zyline-photo.qiniudn.com/1392705051156.jpg");
        list.add("http://zyline-photo.qiniudn.com/1392705051156.jpg");
        list.add("http://zyline-photo.qiniudn.com/1392705051156.jpg");
        list.add("http://zyline-photo.qiniudn.com/1392705051156.jpg");
        list.add("http://zyline-photo.qiniudn.com/1392705051156.jpg");

        PhotoViewAdapter adapter = new PhotoViewAdapter(getActivity(), list);
        viewPager.setAdapter(adapter);
    }


    public void look(int index) {

        List<Object> list = new ArrayList<>();
        list.add("http://zyline-photo.qiniudn.com/1392705051156.jpg");
        list.add("http://zyline-photo.qiniudn.com/1392705051156.jpg");
        list.add("http://zyline-photo.qiniudn.com/1392705051156.jpg");
        list.add("http://zyline-photo.qiniudn.com/1392705051156.jpg");
        list.add("http://zyline-photo.qiniudn.com/1392705051156.jpg");
        PhotoViewDialog.Builder builder = new PhotoViewDialog.Builder(getActivity());
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.view_dialog, null, false);
        builder.setContentView(contentView).setImageList(list).create(3).show();



        /*dialog = new Dialog(getActivity(), R.style.CustomDialog_fill);
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.view_dialog, null);
        ViewPager viewPager = (ViewPager) v.findViewById(R.id.view_dialog_vp);
        final TextView textView = (TextView) v.findViewById(R.id.view_dialog_tv);
        final TextView tvClose= (TextView) v.findViewById(R.id.view_dialog_tv_close);

        textView.setText((index + 1) + "/" + list.size());
        PhotoViewAdapter adapter = new PhotoViewAdapter(getActivity(), list);
        viewPager.setAdapter(adapter);
        dialog.setContentView(v);
        adapter.setPvOnclickListener(this);
        viewPager.setCurrentItem(index);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.FILL_PARENT;
        lp.height = WindowManager.LayoutParams.FILL_PARENT;
        window.setGravity(Gravity.CENTER);
        window.setAttributes(lp);
        dialog.show();



//        Display display = getActivity().getWindowManager().getDefaultDisplay();
//        int width = display.getWidth();
//        int height = display.getHeight();
//        //设置dialog的宽高为屏幕的宽高
//        ViewGroup.LayoutParams layoutParams = new  ViewGroup.LayoutParams(width, height);
//        dialog.setContentView(v, layoutParams);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                textView.setText((position + 1) + "/" + list.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });*/
    }

    @Override
    public void pvOnclick() {
        Log.v(TAG, "--------onclick");
        if(dialog.isShowing()){
            dialog.dismiss();
        }
    }

    public void update(){
        Log.i("","-----------更新MineFragment");
    }

}
