package com.example.threeversionasproject;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;


import com.example.threeversionasproject.fragment.PhotoViewAdapter;

import java.util.List;

/**
 * Created by Ocean on 2018/5/17.
 */

public class PhotoViewDialog extends Dialog{


    public PhotoViewDialog( Context context) {
        super(context);
    }

    public PhotoViewDialog( Context context, int themeResId) {
        super(context, themeResId);
    }

    public static class Builder implements PhotoViewAdapter.PvOnclickListener{

        Context context;
        View contentView;
        List<Object> imageLists;
        PhotoViewDialog  dialog;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setContentView(View view){
            this.contentView=view;
            return this;
        }

        public Builder setImageList(List<Object> imageLists){
            this.imageLists=imageLists;
            return this;
        }

        public  PhotoViewDialog create(int index){
            dialog = new PhotoViewDialog(context, R.style.CustomDialog_fill);
            ViewPager viewPager = (ViewPager) contentView.findViewById(R.id.view_dialog_vp);
            final TextView textView = (TextView) contentView.findViewById(R.id.view_dialog_tv);
            TextView tvClose= (TextView) contentView.findViewById(R.id.view_dialog_tv_close);
            tvClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(dialog.isShowing()){
                        dialog.dismiss();
                    }
                }
            });
            textView.setText((index + 1) + "/" + imageLists.size());
            PhotoViewAdapter adapter = new PhotoViewAdapter(context, imageLists);
            viewPager.setAdapter(adapter);

            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    textView.setText((position + 1) + "/" + imageLists.size());
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

            dialog.setContentView(contentView);
            adapter.setPvOnclickListener(this);
            viewPager.setCurrentItem(index);
            setDialogWindow();
            return dialog;
        }

        @Override
        public void pvOnclick() {
            /*if(dialog.isShowing()){
                dialog.dismiss();
            }*/
        }

        public void setDialogWindow(){
            Window window = dialog.getWindow();
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.MATCH_PARENT;
            window.setGravity(Gravity.CENTER);
            window.setAttributes(lp);

            /*Window dialogWindow = dialog.getWindow();
            dialogWindow.setGravity(Gravity.LEFT | Gravity.BOTTOM);
            dialogWindow.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.x = 0;
            lp.y = 0;

            dialogWindow.setAttributes(lp);*/

        }

    }



}
