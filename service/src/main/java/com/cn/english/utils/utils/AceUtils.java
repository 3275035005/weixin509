package com.cn.english.utils.utils;

import com.alibaba.fastjson.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 工具类
 *
 * @version 1.0
 */
public class AceUtils {

    public static String uuid32() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /***
     * MD5加码 生成32位md5码
     */
    public static String string2MD5(String inStr) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();

    }

    public static Map<String, String[]> createRequestParams(String data) {
        String from = "zh-CHS";
        String to = "en";
        return new HashMap<String, String[]>() {{
            put("q", new String[]{data});
            put("from", new String[]{from});
            put("to", new String[]{to});
        }};
    }

    /**
     * 网易翻译接口
     */
    public static String wyTranslate(String data) {
        // 添加请求参数
        Map<String, String[]> params = AceUtils.createRequestParams(data);
        // 添加鉴权相关参数
        try {
            AuthV3Util.addAuthParams("153fe46e8d9b7c83", "X9BTEUnezLWd5C7DSDPnPEpibZVIAbaD", params);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        // 请求api服务
        byte[] result = HttpUtil.doPost("https://openapi.youdao.com/api", null, params, "application/json");
        // 打印返回结果
        if (result != null) {
            return new String(result, StandardCharsets.UTF_8);
        } else {
            return "";
        }
    }

    public static boolean isChinese(String str){
        int len = str.length();
        for (int i = 0; i < len; i++) {
            String temp = null;
            try {
                temp = URLEncoder.encode(str.charAt(i) + "", "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if (temp.equals(str.charAt(i) + ""))
                continue;
            String[] codes = temp.split("%");
            //判断是中文还是字符(下面判断不精确，部分字符没有包括)
            for (String code : codes) {
                if (code.compareTo("40") > 0)
                    return true;
            }
        }
        return false;
    }

    /**
     * 获取近七天日期
     *
     * @return
     */
    public static List<String> getDay8() {

        List<String> list = new ArrayList<>();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -8);

        // 循环打印近一周的日期
        for (int i = 0; i < 8; i++) {
            calendar.add(Calendar.DAY_OF_YEAR, +1);
            Date date = calendar.getTime();
            list.add(sdf1.format(date));
        }

        return list;
    }

}
