package com.example.threeversionasproject.application;

import android.content.Context;
import android.support.annotation.Keep;
import android.util.Log;
import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixApplication;
import com.taobao.sophix.SophixEntry;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;

/**
 * Sophix入口类，专门用于初始化Sophix，不应包含任何业务逻辑。
 * 此类必须继承自SophixApplication，onCreate方法不需要实现。
 * 此类不应与项目中的其他类有任何互相调用的逻辑，必须完全做到隔离。
 * AndroidManifest中设置application为此类，而SophixEntry中设为原先Application类。
 * 注意原先Application里不需要再重复初始化Sophix，并且需要避免混淆原先Application类。
 * 如有其它自定义改造，请咨询官方后妥善处理。
 */
public class SophixStubApplication extends SophixApplication {

    private final String appKey="24968515";
    private final String appSecret="ca7a38958876af07e24badf3f7640b18";
    private final String rsaSecret="MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCSS26Zk7eIGt9+Sv6gq4it2cYNgVaJY74opgRWmaWjP1n7SvU8vzT4cY4rsVmAhBhWOWiLWHad5NjflXAFj1Jr4xB8PXhSFV+0iQdIPquxbV8Wj1ARDQyvux7DAJTOquKH7PL3v//8npBtXZNhSLPM46Fj63QPOwYBv1toR7Z9In5tu+XmU/4/FPZMvtFYxkgrGYd9qE/rj8+ANYS1EVrpWzXcKxQSklULMXG427Bt5iYaCrIpIWr0gGSOZ67aKpMgSQlyhdZ0QFdccxun8KampmPXV2n2I6w1RFaiMEmThZs2Dzg09mvDtH/thf6Sxk2K3h5+riguF2oXcvQRI8ZTAgMBAAECggEAYzZOyk44UFBRMxfu+M17wtMGkpAdPM1s36o/FO/cOgqznk0mZU0Swk5YaV874IE08/y43o9e2hOh1HdG4i5hahMyUd9qItil8Axh6Oe/jnMq4mTMmtOrv915ritNv556RE44NDZ8hziNn+2vDnG8pf3VrKq4KcAQgczhU6YWp2NexujCaFmX8LxpM3Gretrv6yHOWfRCWPAvJiKn7RqCxVL05D+W5L2Qgw2k+d2/ZEGkn4Gq25SKX20QMKXzDGRIqfODinzMY6KU/8U4ODp+9+uxp+W0jE4PWy5c53q9XCVfho+7rX+gIhJLJzpwod7QB4TDlX46xMIx0waYyKFQ4QKBgQDSndkAwVjhp0grkaF/s/UveJelcBIJ8mqAmJNKQ3zTBsMqwAHp6pIoL4Tt6PpigIskV73Iir+BA/yxDACymDR2LwDlXsU2KQuEO2lPeH1TOjg0NfdzES0RDhHExfCMpCR9nI0Ug8evuiMF/jxaAZDFP2pHIbN6PP9robtA/DciwwKBgQCx0WvOh8T48tnTtiOBaePr1TNz+3OEJQ5t5auyf84BC9G9RmDYVyAy7+D55LQt/QQ798wGvUyqbMs94b1kTZPGAydhoi0dd031F+PnMOpHXZm9Q3ruCpPwwXqUdAZu9TRFTZ6sN+ZXEU0oENve9adoIR+9bjbIZa4+fd8HiFF1MQKBgQC6jJVo1dikBkcmUBd7xSmmommvX1Sa+tzaiknrvVsSjyrO8sK/LcXHUk+ranBcTv7vccfnmP2GiMGJD9iOV8If7AIfJ5IGJtTMitL8UPyweyDNHf/PK6d/M7cJU0l9SOYJXGCZix+Txoq/BDDwh/Jyw3ifCHqwCtcxvk7dgIhVgQKBgHKUlblRt1ULd916QkrD5wAyM6OK5hbCoheipQ6yWBZIfXBPh1wgWDk6ZWfhPVFc9nuF40x8cohm7lgDvDyD2LMBmUDKdfPEytEiAdMZq/0sjUJt1yrIWigLJGfAk9yv2GzH5XTn2F9GYMduhZ+X/7WTkpZIDLj4/Fx8U1jROFwhAoGBAKTqMnlvgnva8xFyhJVRCg8tlcGwBF2ECJ70UZrHmPUzWrHWplnS8jRq9PgPkL4bbLMO8DxWaozwOsvZcMjesyZUoiUWduC3EK9QsqY24ajGES1hBg3j1cI34V15nqdEppxp8M2ON8OdHkHFdvjhaC2yhhT8BaHx2rs82UhI4Q62";

    private final String TAG = "SophixStubApplication";
    // 此处SophixEntry应指定真正的Application，并且保证RealApplicationStub类名不被混淆。
    @Keep
    @SophixEntry(MyApplication.class)
    static class RealApplicationStub {}
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//         如果需要使用MultiDex，需要在此处调用。
//         MultiDex.install(this);
        initSophix();
    }
    private void initSophix() {
        String appVersion = "0.0.0";
        try {
            appVersion = this.getPackageManager()
                    .getPackageInfo(this.getPackageName(), 0)
                    .versionName;
        } catch (Exception e) {
        }
        final SophixManager instance = SophixManager.getInstance();
        instance.setContext(this)
                .setAppVersion(appVersion)
                .setSecretMetaData(appKey, appSecret, rsaSecret)
                .setEnableDebug(true)
                .setEnableFullLog()
                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                    @Override
                    public void onLoad(final int mode, final int code, final String info, final int handlePatchVersion) {
                        Log.i(TAG, "---------补丁信息====code:"+code+"---info:"+info+"---handlePatchVersion:"+handlePatchVersion);

                        if (code == PatchStatus.CODE_LOAD_SUCCESS) {
                            Log.i(TAG, "---------热修复： 加载补丁成功");
                        } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
                            // 如果需要在后台重启，建议此处用SharePreference保存状态。
                            Log.i(TAG, "---------热修复：sophix预加载补丁成功。 重启应用程序才能生效.");
//                            SophixManager.getInstance().killProcessSafely();
                        }
                    }
                }).initialize();
    }
}
