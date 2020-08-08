package com.example.threeversionasproject.envntdispatch;

/**
 * Created by Ocean on 2020/6/9.
 */

public class OutClass {


    public interface innImp{
        void ets();
    }

    private class InnerClass implements innImp{

        @Override
        public void ets() {

        }
    }

    public innImp getInner(){
        return new InnerClass();
    }



}
