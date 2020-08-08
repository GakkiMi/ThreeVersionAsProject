package com.example.threeversionasproject.envntdispatch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.threeversionasproject.R;

import java.security.PrivateKey;
import java.security.PublicKey;

public class EventDispatchActivity extends AppCompatActivity {

    Button eventViewA;
    Button eventViewB;

    String str = "READ_EXTERNAL_STORAGE读取外置存储权限强制检查，此权限在低于4.4的版本默认获取。如果应用只在内部存储数据或者如下特定目录读取/写入文件，则不需要WRITE_EXTERNAL_STORAGE或者READ_EXTERNAL_STORAGE权限。如果app没有在别的地方读写存储但是minSdkVersion低于19，则可以在uses-permission标签内加入android:maxSdkVersion适配：<uses-permission ... android:maxSdkVersion=\"18\"/>。支持的文件位置如下：\n" +
            "\n" +
            "作者：鹈鹕醍醐\n" +
            "链接：https://www.jianshu.com/p/21bd742e394c\n" +
            "来源：简书\n" +
            "著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。";

    PublicKey publicKey;
    PrivateKey privateKey;

    String publicKeyStr="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCwXACPLu3jSLG7bvtMr/mhcE9V\n" +
            "1eeXu18oj0OKTGTWSG22+1CxAW40w9ZVZERmVY+T0GlgWzIIEQyT5rLk0uLfyGjN\n" +
            "iAiXVh95DhPTWp+V8a2N0eiywPvLzD3WNbCaF18VVsrTrThNaG6rkatSRiwCuRth\n" +
            "2mp5F/b5pLbnwIGfJwIDAQAB";
    String privateKeyStr="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALBcAI8u7eNIsbtu\n" +
            "+0yv+aFwT1XV55e7XyiPQ4pMZNZIbbb7ULEBbjTD1lVkRGZVj5PQaWBbMggRDJPm\n" +
            "suTS4t/IaM2ICJdWH3kOE9Nan5XxrY3R6LLA+8vMPdY1sJoXXxVWytOtOE1obquR\n" +
            "q1JGLAK5G2HaankX9vmktufAgZ8nAgMBAAECgYBRXhFP/on5LEf8P2coaaSWDHg+\n" +
            "FRPmGKf90aOKwlZf20jrfKYtFIjhRV21Ri6cvGsi+qwzrb+2ow8XigANvRK5AgOv\n" +
            "yYRwjtLeCv+9tNLE/rsN5GQjqxcvqZwFXky7fjpeKrYAtEXjBZte5B17KG91/cdJ\n" +
            "HRePrrlIWqiqVxOmWQJBAOoYx8QTTDITR0L8EHE9iBogyTDCKgg38kMDyfZRqT2R\n" +
            "j3xznrlU3ad0svOLY3hKQxBQ6NhUerN/QMvoN473fPMCQQDA3EMwFM4UpuuDtJ8r\n" +
            "hNX1jfcNSKLsZzQYS6Qs3MMxCtM3+Dw0wwZEIqXyeQo1+LkWXIC+R+fEhz2Kw3QK\n" +
            "pRH9AkEA3dk2F9+h6iLS+ewfOPHzNa+0evzHEJrUgCXrFAA3vx1fObXoygJfPP8f\n" +
            "SCbk0W6vN5s1zy3gSpNiONuNK3PneQJAYQv1OzKc6MN9645uMeXsDF5dnO+aHD9q\n" +
            "dXdKq9CC4CDEXHurQBmLA6ozuymxO6tm55uwpQ9lzv0GuNbVg63AuQJBALnI+kLB\n" +
            "ISWGPxZbNC88/fkpPZXxsrxv+dZyDzm/IA7iqKylcrHJ0BmWceUPAyd+FXZuj66i\n" +
            "28IDapya1RwGJNI=";

    String encryptStrByPublickey;//公钥加密后的数据
    String decyptStrByPublickey;//私钥解密后的数据


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_dispatch);
        Log.i("", "-----------shabi：");
        eventViewA= (Button) findViewById(R.id.jiami);
        eventViewB= (Button) findViewById(R.id.jiemi);

        try {
            publicKey = MyRsaUtils.loadPublicKey(publicKeyStr);
            privateKey = MyRsaUtils.loadPrivateKey(privateKeyStr);
            MyRsaUtils.printPublicKeyInfo(publicKey);
            MyRsaUtils.printPrivateKeyInfo(privateKey);
        } catch (Exception e) {
            e.printStackTrace();
        }


        eventViewA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //MyRsaUtils
                byte[] data = str.getBytes();
                try {
                    byte encryptData[] = MyRsaUtils.encryptByPublicKeyForSpilt(data, publicKey);
                    encryptStrByPublickey = Base64.encodeToString(encryptData, Base64.DEFAULT);
                    Log.i("", "-----------公钥加密后的数据：" + encryptStrByPublickey);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //RSASecurityServerUtil
               /* try {
                    encryptStrByPublickey = RSASecurityServerUtil.encryptByPublicKey(str, publicKeyStr);
                    Log.i("", "-----------公钥加密后的数据" + encryptStrByPublickey);
                } catch (Exception e) {
                    e.printStackTrace();
                }*/


            }
        });
        eventViewB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //MyRsaUtils
                byte[] data = Base64.decode(encryptStrByPublickey, Base64.DEFAULT);

                try {
                    byte decryptData[] = MyRsaUtils.decryptByPrivateKeyForSpilt(data,privateKey);
                    decyptStrByPublickey= new String(decryptData);
                    Log.i("", "-----------私钥解密后的数据：" + decyptStrByPublickey);

                } catch (Exception e) {
                    e.printStackTrace();
                }


                //RSASecurityServerUtil
                /*try {
                    decyptStrByPublickey = RSASecurityServerUtil.decryptByPrivateKey(encryptStrByPublickey, privateKeyStr);
                    Log.i("", "-----------私钥解密后的数据：" + decyptStrByPublickey);

                } catch (Exception e) {
                    e.printStackTrace();
                }*/
            }
        });
    }
}
