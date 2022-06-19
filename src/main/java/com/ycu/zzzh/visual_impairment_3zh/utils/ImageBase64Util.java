package com.ycu.zzzh.visual_impairment_3zh.utils;

import org.apache.commons.codec.binary.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * 图片转Base64
 */
public class ImageBase64Util {
////测试
//public static void main(String[] args) {
//        String imageFile= "D:\\desktop\\2713c54c19713c1bb738b153086ca2b.jpeg";// 待处理的图片
//System.out.println(getImageString(imageFile));
//    }

public static String getImageString(String imageFile){
        InputStream is = null;
try {
byte[] data = null;
            is = new FileInputStream(new File(imageFile));
            data = new byte[is.available()];
            is.read(data);
return new String(Base64.encodeBase64(data));
        } catch (Exception e) {
    return "";
        } finally {
if (null != is) {
try {
                    is.close();
                    is = null;
                } catch (Exception e) {
                    return "";
                }
            }
        }
    }
}
