package com.iduck.common.util;

import com.iduck.common.constant.RegexConst;

import java.util.regex.Pattern;

/**
 * 正则工具类（网上摘录未实践,搭配RegexConst使用）
 *
 * @author SongYanBin
 * @Copyright 2022-2099 SongYanBin All Rights Reserved.
 * @since 2022/11/28
 **/
public class IRegexUtils {
    /**
     * 验证手机号（简单）
     *
     * @param input 待验证文本
     * @return 验证结果
     */
    public static boolean isMobileSimple(CharSequence input) {
        return isMatch(RegexConst.REGEX_MOBILE_SIMPLE, input);
    }

    /**
     * 验证手机号（精确）
     *
     * @param input 待验证文本
     * @return 验证结果
     */
    public static boolean isMobileExact(CharSequence input) {
        return isMatch(RegexConst.REGEX_MOBILE_EXACT, input);
    }

    /**
     * 验证电话号码
     *
     * @param input 待验证文本
     * @return 验证结果
     */
    public static boolean isTel(CharSequence input) {
        return isMatch(RegexConst.REGEX_TEL, input);
    }

    /**
     * 验证身份证号码15位
     *
     * @param input 待验证文本
     * @return 验证结果
     */
    public static boolean isIDCard15(CharSequence input) {
        return isMatch(RegexConst.REGEX_ID_CARD15, input);
    }

    /**
     * 验证身份证号码18位
     *
     * @param input 待验证文本
     * @return 验证结果
     */
    public static boolean isIDCard18(CharSequence input) {
        return isMatch(RegexConst.REGEX_ID_CARD18, input);
    }

    /**
     * 验证邮箱
     *
     * @param input 待验证文本
     * @return 验证结果
     */
    public static boolean isEmail(CharSequence input) {
        return isMatch(RegexConst.REGEX_EMAIL, input);
    }

    /**
     * 验证URL
     *
     * @param input 待验证文本
     * @return 验证结果
     */
    public static boolean isURL(CharSequence input) {
        return isMatch(RegexConst.REGEX_URL, input);
    }

    /**
     * 验证汉字
     *
     * @param input 待验证文本
     * @return 验证结果
     */
    public static boolean isZH(CharSequence input) {
        return isMatch(RegexConst.REGEX_ZH, input);
    }

    /**
     * 验证用户名
     * <p>取值范围为a-z,A-Z,0-9,"_",汉字，不能以"_"结尾,用户名必须是6-20位</p>
     *
     * @param input 待验证文本
     * @return 验证结果
     */
    public static boolean isUsername(CharSequence input) {
        return isMatch(RegexConst.REGEX_USERNAME, input);
    }

    /**
     * 验证yyyy-MM-dd格式的日期校验，已考虑平闰年
     *
     * @param input 待验证文本
     * @return 验证结果
     */
    public static boolean isDate(CharSequence input) {
        return isMatch(RegexConst.REGEX_DATE, input);
    }

    /**
     * 验证IP地址
     *
     * @param input 待验证文本
     * @return 验证结果
     */
    public static boolean isIP(CharSequence input) {
        return isMatch(RegexConst.REGEX_IP, input);
    }

    public static boolean isMatch(String regex, CharSequence input) {
        return input != null && input.length() > 0 && Pattern.matches(regex, input);
    }

    public static void main(String[] args) {
        System.out.println(IRegexUtils.isEmail("624142800@qq.com"));
    }

    private IRegexUtils() {

    }
}
