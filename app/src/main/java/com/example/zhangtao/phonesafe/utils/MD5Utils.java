package com.example.zhangtao.phonesafe.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by zhangtao on 2016/5/15.
 */
public class MD5Utils {


        /**
         * 获取加密后的字符串
         * @param content:输入的内容
         * @return
         */
        public static String getMD5(String content) {
            try {

                // 拿到一个MD5转换器（如果想要SHA1参数换成”SHA1”）
                MessageDigest messageDigest =MessageDigest.getInstance("MD5");
                // 输入的字符串转换成字节数组
                byte[] inputByteArray = content.getBytes();
                // inputByteArray是输入字符串转换得到的字节数组
                messageDigest.update(inputByteArray);
                return getHashString(messageDigest);
            } catch (NoSuchAlgorithmException e) {
                return null;
            }
        }

        private static String getHashString(MessageDigest digest) {

           StringBuilder builder=new StringBuilder();
          for (byte b:digest.digest()){
              builder.append(Integer.toHexString((b>>4)&0xf));
              builder.append(Integer.toHexString(b&0xf));
          }
            return builder.toString();
        }
    }

