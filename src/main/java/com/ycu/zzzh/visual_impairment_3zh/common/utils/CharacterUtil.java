package com.ycu.zzzh.visual_impairment_3zh.common.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName CharacterUtil
 * @description: TODO
 * @Date 2022/6/30 20:16
 * @Version 1.0
 **/
public class CharacterUtil {
    /**
     * 将字符串按照指定长度分割成字符串数组
     *
     * @param src
     * @param length
     * @return
     */
    public static List<String> stringToStringArray(String src, int length) {
        //检查参数是否合法
        if (null == src || src.equals("")) {
            return null;
        }

        if (length <= 0) {
            return null;
        }
        int n = (src.length() + length - 1) / length; //获取整个字符串可以被切割成字符子串的个数
        List<String> split = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (i < (n - 1)) {
                split.add(src.substring(i * length, (i + 1) * length));
            } else {
                split.add(src.substring(i * length));
            }
        }
        return split;
    }

}
