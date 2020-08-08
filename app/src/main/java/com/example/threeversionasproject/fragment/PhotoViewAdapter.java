package com.example.threeversionasproject.fragment;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.threeversionasproject.R;

import java.util.LinkedList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Ocean on 2018/5/17.
 */

public class PhotoViewAdapter extends PagerAdapter {


    private Context mContext;
    private List<Object> datas = null;

    public PhotoViewAdapter(Context mContext, List<Object> datas) {
        this.mContext = mContext;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Log.v("", "----------instantiateItem " + position);
        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.item_viewpager_view, null, false);
        PhotoView photoView = (PhotoView) view.findViewById(R.id.item_view_pager_view_photoview);
//        photoView.setImageResource(datas.get(position));
        Glide.with(mContext).load(datas.get(position)).into(photoView);
        //单击事件
        photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                pvOnclickListener.pvOnclick();
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Log.v("", "-----------destroyItem " + position);
        View view = (View) object;
        container.removeView(view);
    }

    public final class ViewHolder {
        public PhotoView photoView;
    }

    PvOnclickListener pvOnclickListener;

    public void setPvOnclickListener(PvOnclickListener pvOnclickListener) {
        this.pvOnclickListener = pvOnclickListener;
    }

    public interface PvOnclickListener{
        void pvOnclick();
    }

}
