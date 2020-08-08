package com.gengchao.chat.activity.myscroltextview.scroll;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gengchao.chat.R;

import java.util.ArrayList;
import java.util.List;

public class ScrollActivity extends AppCompatActivity {

    ScrollTextView scrollTextView;
    List<String> strs;
    List<LinearLayout.LayoutParams> layoutParams;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll);
        scrollTextView = (ScrollTextView)findViewById(R.id.scroll);
        strs = new ArrayList<String>();
        strs.add("本来是想做一个显示文字信息的，当文字很多时View的高度不能超过一个固定的值，当文字很少时View的高度小于那个固定值时，按View的高度显示。因为ScrollView没有maxHeight，无法满足需求，只好另找方法了。 \n" +
                "View本身是可以设置ScrollBar，这样就不一定需要依赖ScrollView了。TextView有个属性maxLine，这样也就满足了需求了，只要设置一个TextView带ScrollBar的，然后设置maxLine就可以了。 ");
//        strs.add("The Internet site you have attempted to access is prohibited. Accenture's internal webfilters indicate that the site likely contains content considered inappropriate according to Policy 57: Information Security");
//        strs.add("这是测试文字!");
//        strs.add("this is a test!");
//        strs.add("setContentView(R.layout.activity_main);final ScrollTextView scrollTextView = (ScrollTextView)findViewById(R.id.scroll);");
//        strs.add("02-08 01:39:24.585: D/dalvikvm(4948): GC_CONCURRENT freed 511K, 10% free 14542K/16007K, paused 1ms+12ms");
//        strs.add("good morning 它们晨量力而里曼城蛟龙得水影The Internet site you have attempted to access响力里它们晨量力而good morning of class里曼城蛟龙得水影响力里good morning of classgood morning 它们晨量力而里曼城蛟龙得水影The Internet site you have attempted to access响力里它们晨量力而good morning of class里曼城蛟龙得水影响力里good morning of classgood morning 它们晨量力而里曼城蛟龙得水影The Internet site you have attempted to access响力里它们晨量力而good morning of class里曼城蛟龙得水影响力里good morning of classgood morning 它们晨量力而里曼城蛟龙得水影The Internet site you have attempted to access响力里它们晨量力而good morning of class里曼城蛟龙得水影响力里good morning of classgood morning 它们晨量力而里曼城蛟龙得水影The Internet site you have attempted to access响力里它们晨量力而good morning of class里曼城蛟龙得水影响力里good morning of classgood morning 它们晨量力而里曼城蛟龙得水影The Internet site you have attempted to access响力里它们晨量力而good morning of class里曼城蛟龙得水影响力里good morning of classgood morning 它们晨量力而里曼城蛟龙得水影The Internet site you have attempted to access响力里它们晨量力而good morning of class里曼城蛟龙得水影响力里good morning of classgood morning 它们晨量力而里曼城蛟龙得水影The Internet site you have attempted to access响力里它们晨量力而good morning of class里曼城蛟龙得水影响力里good morning of classgood morning 它们晨量力而里曼城蛟龙得水影The Internet site you have attempted to access响力里它们晨量力而good morning of class里曼城蛟龙得水影响力里good morning of classgood morning 它们晨量力而里曼城蛟龙得水影The Internet site you have attempted to access响力里它们晨量力而good morning of class里曼城蛟龙得水影响力里good morning of classgood morning 它们晨量力而里曼城蛟龙得水影The Internet site you have attempted to access响力里它们晨量力而good morning of class里曼城蛟龙得水影响力里good morning of classgood morning 它们晨量力而里曼城蛟龙得水影The Internet site you have attempted to access响力里它们晨量力而good morning of class里曼城蛟龙得水影响力里good morning of classgood morning 它们晨量力而里曼城蛟龙得水影The Internet site you have attempted to access响力里它们晨量力而good morning of class里曼城蛟龙得水影响力里good morning of classgood morning 它们晨量力而里曼城蛟龙得水影The Internet site you have attempted to access响力里它们晨量力而good morning of class里曼城蛟龙得水影响力里good morning of classgood morning 它们晨量力而里曼城蛟龙得水影The Internet site you have attempted to access响力里它们晨量力而good morning of class里曼城蛟龙得水影响力里good morning of classgood morning 它们晨量力而里曼城蛟龙得水影The Internet site you have attempted to access响力里它们晨量力而good morning of class里曼城蛟龙得水影响力里good morning of classgood morning 它们晨量力而里曼城蛟龙得水影The Internet site you have attempted to access响力里它们晨量力而good morning of class里曼城蛟龙得水影响力里good morning of classgood morning 它们晨量力而里曼城蛟龙得水影The Internet site you have attempted to access响力里它们晨量力而good morning of class里曼城蛟龙得水影响力里good morning of classgood morning 它们晨量力而里曼城蛟龙得水影The Internet site you have attempted to access响力里它们晨量力而good morning of class里曼城蛟龙得水影响力里good morning of class");
        layoutParams = new ArrayList<LinearLayout.LayoutParams>();
        layoutParams.add(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        layoutParams.add(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        layoutParams.add(new LinearLayout.LayoutParams(120, LinearLayout.LayoutParams.WRAP_CONTENT));
        layoutParams.add(new LinearLayout.LayoutParams(550, LinearLayout.LayoutParams.WRAP_CONTENT));
        layoutParams.add(new LinearLayout.LayoutParams(200, LinearLayout.LayoutParams.WRAP_CONTENT));
        layoutParams.add(new LinearLayout.LayoutParams(550, 300));
        layoutParams.add(new LinearLayout.LayoutParams(350, 500));
        layoutParams.add(new LinearLayout.LayoutParams(550, 900));
        layoutParams.add(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 80));
        layoutParams.add(new LinearLayout.LayoutParams(200, 200));
        layoutParams.add(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 300));
        layoutParams.add(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 200));
        layoutParams.add(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 130));
        layoutParams.add(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.FILL_PARENT));
        layoutParams.add(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.FILL_PARENT));
        layoutParams.add(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.FILL_PARENT));
        scrollTextView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                scrollTextView.updateScrollStatus();
            }
        });
        final TextView layout = (TextView)findViewById(R.id.layout);
        Button button = (Button)findViewById(R.id.update_text);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                int i = (int)(strs.size() * Math.random());
                scrollTextView.setScrollText(strs.get(i));
            }
        });
        Button button2 = (Button)findViewById(R.id.update_layout);
        button2.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                int i = (int)(layoutParams.size() * Math.random());
                scrollTextView.setLayoutParams(layoutParams.get(i));
                String width = layoutParamsToString(layoutParams.get(i).width);
                String height = layoutParamsToString(layoutParams.get(i).height);
                layout.setText("width=" + width + "  height=" + height);
            }
        });
    }

    String layoutParamsToString(int size) {

        String result = "";
        if (size == LinearLayout.LayoutParams.FILL_PARENT) {
            result = "fill_parent";
        } else if (size == LinearLayout.LayoutParams.WRAP_CONTENT) {
            result = "wrap_content";
        } else {
            result = "" + size;
        }
        return result;
    }


}
