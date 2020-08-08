package com.example.threeversionasproject.envntdispatch;

import android.util.Base64;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

/**
 * Created by Ocean on 2019/8/19.
 *
 * 使用这个类
 *
 */

public class RSASecurityServerUtil {

    public static String keyAlgorithm = "RSA";
    /**
     * 指定加密算法为RSA
     */
    private static final String ALGORITHM = "RSA/ECB/PKCS1Padding";
    /**
     * 密钥长度，用来初始化
     */
    private static final int KEYSIZE = 1024;
    /**
     * 公钥
     */
    private static PublicKey publicKey = null;
    /**
     * 私钥
     */
    private static PrivateKey privateKey = null;
    /**
     * 公钥字符串
     */
    private static String publicKeyString = null;
    /**
     * 私钥字符串
     */
    private static String privateKeyString = null;
    /**
     * 指定字符集
     */
    private static String CHARSET = "UTF-8";
    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;
    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    /**
     * 生成密钥对
     *
     * @throws Exception
     */
    public static void generateKeyPair() throws Exception {

        // /** RSA算法要求有一个可信任的随机数源 */
        SecureRandom secureRandom = new SecureRandom();
        /** 为RSA算法创建一个KeyPairGenerator对象 */
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(keyAlgorithm);
        /** 利用上面的随机数据源初始化这个KeyPairGenerator对象 */
        keyPairGenerator.initialize(KEYSIZE, secureRandom);
        /** 生成密匙对 */
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        /** 得到公钥 */
        publicKey = keyPair.getPublic();
        publicKeyString = new String(Base64.encode(publicKey.getEncoded(), Base64.DEFAULT));
        /** 得到私钥 */
        privateKey = keyPair.getPrivate();
        privateKeyString = new String(Base64.encode(privateKey.getEncoded(), Base64.DEFAULT));
    }

    /**
     * 生成公钥对象
     *
     * @param publicKeyStr
     * @throws Exception
     */
    public static void setPublicKey(String publicKeyStr) throws Exception {
        RSASecurityServerUtil.publicKey = getPublicKeyByStr(publicKeyStr);
    }

    /**
     * 生成私钥对象
     *
     * @param privateKeyStr
     * @throws Exception
     */
    public static void setPrivateKey(String privateKeyStr) throws Exception {
        RSASecurityServerUtil.privateKey = getPrivateKeyByStr(privateKeyStr);
    }

    public static String getPublicKeyString() {
        return publicKeyString;
    }

    public static String getPrivateKeyString() {
        return privateKeyString;
    }


    /**
     * 将给定的公钥字符串转换为公钥对象
     *
     * @param publicKeyStr
     * @return
     * @throws Exception
     */
    public static PublicKey getPublicKeyByStr(String publicKeyStr) throws Exception {
        publicKeyString = publicKeyStr;
        try {
            byte[] buffer = Base64.decode(publicKeyStr, Base64.DEFAULT);
            KeyFactory keyFactory = KeyFactory.getInstance(keyAlgorithm);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            publicKey = keyFactory.generatePublic(keySpec);
            return publicKey;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("公钥非法");
        } catch (NullPointerException e) {
            throw new Exception("公钥数据为空");
        }
    }

    /**
     * 将给定的私钥字符串转换为私钥对象
     *
     * @param privateKeyStr
     * @return
     * @throws Exception
     */
    public static PrivateKey getPrivateKeyByStr(String privateKeyStr) throws Exception {
        privateKeyString = privateKeyStr;
        try {
            byte[] buffer = Base64.decode(privateKeyStr, Base64.DEFAULT);
            KeyFactory keyFactory = KeyFactory.getInstance(keyAlgorithm);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            privateKey = keyFactory.generatePrivate(keySpec);
            return privateKey;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("私钥非法");
        } catch (NullPointerException e) {
            throw new Exception("私钥数据为空");
        }
    }



    /**
     * 通过公钥byte[](publicKey.getEncoded())将公钥还原，适用于RSA算法
     *
     * @param keyBytes
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static PublicKey getPublicKeyByByte(byte[] keyBytes) throws NoSuchAlgorithmException,
            InvalidKeySpecException {
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(keyAlgorithm);
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    /**
     * 通过私钥byte[]将私钥还原，适用于RSA算法
     *
     * @param keyBytes
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static PrivateKey getPrivateKeyByByte(byte[] keyBytes) throws NoSuchAlgorithmException,
            InvalidKeySpecException {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(keyAlgorithm);
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    /**
     * 从文件中输入流中加载公钥
     *
     * @param in 公钥输入流
     * @throws Exception 加载公钥时产生的异常
     */
    public static PublicKey getPublicKeyByFile(InputStream in) throws Exception {
        try {
            return getPublicKeyByStr(readKey(in));
        } catch (IOException e) {
            throw new Exception("公钥数据流读取错误");
        } catch (NullPointerException e) {
            throw new Exception("公钥输入流为空");
        }
    }

    /**
     * 从文件中加载私钥
     *
     * @throws Exception
     */
    public static PrivateKey getPrivateKeyByFile(InputStream in) throws Exception {
        try {
            return getPrivateKeyByStr(readKey(in));
        } catch (IOException e) {
            throw new Exception("私钥数据读取错误");
        } catch (NullPointerException e) {
            throw new Exception("私钥输入流为空");
        }
    }

    /**
     * 读取密钥信息
     *
     * @param in
     * @return
     * @throws IOException
     */
    public static String readKey(InputStream in) {
        InputStreamReader inputReader = null;
        BufferedReader br = null;
        try {
            inputReader = new InputStreamReader(in);
            br = new BufferedReader(inputReader);
            String line;
            String result = "";
            while ((line = br.readLine()) != null) {
                result += line;
            }
            inputReader.close();
            br.close();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputReader != null) {
                    inputReader.close();
                }
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return "";

    }


    /**
     * 公钥加密方法
     *
     * @param source 源数据
     * @return
     * @throws Exception
     */
    public static String encryptByPublicKey(String source, String pubString) throws Exception {
        setPublicKey(pubString);
        /** 得到Cipher对象来实现对源数据的RSA加密 */
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] data = source.getBytes();
        /** 执行分组加密操作 */
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();

        return Base64.encodeToString(encryptedData, Base64.DEFAULT);
    }


    /**
     * 私钥解密算法
     *
     * @param cryptoSrc 密文
     * @return
     * @throws Exception
     */
    public static String decryptByPrivateKey(String cryptoSrc, String privatekey) throws Exception {
        //生成私钥对象
        setPrivateKey(privatekey);
        /** 得到Cipher对象对已用公钥加密的数据进行RSA解密 */
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] encryptedData = Base64.decode(cryptoSrc, Base64.DEFAULT);
        System.out.println(cryptoSrc);
        System.out.println(encryptedData);

        /** 执行解密操作 */
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return new String(decryptedData);
    }


    /**
     * 私钥加密方法
     *
     * @param source 源数据
     * @return
     * @throws Exception
     */
    public static String encryptByPrivateKey(String source, String privatekey) throws Exception {
        getPrivateKeyByStr(privatekey);
        /** 得到Cipher对象来实现对源数据的RSA加密 */
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] data = source.getBytes();
        /** 执行数据分组加密操作 */
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return Base64.encodeToString(encryptedData, Base64.DEFAULT);
    }

    /**
     * 使用公钥解密算法
     *
     * @param cryptoSrc 密文
     * @return
     * @throws Exception
     */
    public static String decryptByPublicKey(String cryptoSrc, String publicStr) throws Exception {
        setPublicKey(publicStr);
        /** 得到Cipher对象对已用公钥加密的数据进行RSA解密 */
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        byte[] encryptedData = Base64.decode(cryptoSrc, Base64.DEFAULT);
        /** 执行解密操作 */

        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();

        return new String(decryptedData);
    }







    /**
     * 打印公钥信息
     *
     * @param publicKey
     */
    public static void printPublicKeyInfo(PublicKey publicKey) {
        RSAPublicKey rsaPublicKey = (RSAPublicKey) publicKey;
        Log.i("","----------RSAPublicKey----------");
        Log.i("","----------Modulus.length=" + rsaPublicKey.getModulus().bitLength());
        Log.i("","----------Modulus=" + rsaPublicKey.getModulus().toString());
        Log.i("","----------PublicExponent.length=" + rsaPublicKey.getPublicExponent().bitLength());
        Log.i("","----------PublicExponent=" + rsaPublicKey.getPublicExponent().toString());
    }


    /**
     * 打印私钥信息
     *
     * @param privateKey
     */

    public static void printPrivateKeyInfo(PrivateKey privateKey) {
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) privateKey;
        Log.i("","----------RSAPrivateKey ----------");
        Log.i("","----------Modulus.length=" + rsaPrivateKey.getModulus().bitLength());
        Log.i("","----------Modulus=" + rsaPrivateKey.getModulus().toString());
        Log.i("","----------PrivateExponent.length=" + rsaPrivateKey.getPrivateExponent().bitLength());
        Log.i("","----------PrivatecExponent=" + rsaPrivateKey.getPrivateExponent().toString());

    }


}
