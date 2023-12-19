package com.mrsnow.utils;

import cn.hutool.core.util.StrUtil;

/**
 * @Author MrSnow *** dz
 * @CreateTime: 2023-05-02  11:06
 **/
public class StrHelper {

    /**
     * mybatis plus like查询转换
     */
    public static String keywordConvert(String value) {
        if (StrUtil.isBlank(value)) {
            return "";
        }
        value = value.replaceAll("%", "\\\\%");
        value = value.replaceAll("_", "\\\\_");
        return value;
    }
}
