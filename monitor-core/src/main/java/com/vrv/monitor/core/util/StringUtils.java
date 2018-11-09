package com.vrv.monitor.core.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * 字符串公用类
 *
 * @author DanRui
 */
public class StringUtils {

    /**
     * 验证可能为空格或者""及null的字符串
     *
     * <pre>
     *   StringUtils.isBlank(null)      = true
     *   StringUtils.isBlank(&quot;&quot;)        = true
     *   StringUtils.isBlank(&quot; &quot;)       = true
     *   StringUtils.isBlank(&quot;bob&quot;)     = false
     *   StringUtils.isBlank(&quot;  bob  &quot;) = false
     * </pre>
     *
     * @param cs
     *            可能为空格或者""及null的字符序列
     * @return
     */
    public static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(cs.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }


    /**
     * Description:验证字符串数组是否为空
     *
     * @author DanRui
     * @Version 1.0
     * @param css
     * @return
     */
    public static boolean isBlank(final CharSequence ... css) {
        if (css == null)
            return true;

        for (CharSequence cs : css) {
            if(isNotBlank(cs)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 验证可能为""或者null的字符串
     *
     * @param cs
     *            可能为空的字符串
     * @return
     */
    public static boolean isNotBlank(final CharSequence cs) {
        return !isBlank(cs);
    }

    /**
     * 把数组内的对象通过分隔符连接为字符串
     *
     * @param array
     *            对象数组
     * @param separator
     *            分隔符
     * @return
     */
    public static String join(final Object[] array, final String separator) {
        return join(Arrays.asList(array), separator);
    }

    /**
     * 连接迭代器内对象为字符串
     *
     * @param it
     *            实现迭代接口的集合
     * @param separator
     *            分隔符
     * @return
     */
    public static String join(final Iterable<?> it, final String separator) {
        Iterator<?> iterator = null;
        if ((iterator = it.iterator()) == null || !iterator.hasNext()) {
            return null;
        }
        Object first = iterator.next();
        if (!iterator.hasNext()) {
            return String.valueOf(first);
        }

        StringBuilder buf = new StringBuilder().append(first);
        while (iterator.hasNext()) {
            Object obj = iterator.next();
            if (obj != null) {
                buf.append(separator).append(obj);
            }
        }
        return buf.toString();
    }

    /**
     * 截取指定长度字符串，超长部分截取后加"..."
     *
     * @param str
     *            源字符串
     * @param length
     *            截取长度
     * @param dot
     *            是否为未显示内容添加"..."
     * @return
     */
    public static String substring(String str, final int length, final boolean dot) {
        return substring(str, 0, length, dot);
    }

    /**
     * 截取指定长度字符串，超长部分截取后加"..."
     *
     * @param str
     *            源字符串
     * @param beginIndex
     *            开始下标(包含本身)
     * @param endIndex
     *            结束下标(不包含本身)
     * @param dot
     *            是否为未显示内容添加"..."
     * @return
     */
    public static String substring(String str, int beginIndex, int endIndex, final boolean dot) {
        if (str != null) {
            if (str.length() >= (endIndex + 1)) {
                str = str.substring(beginIndex, endIndex);
                return dot ? str + "..." : str;
            }
        }
        return str;
    }

    /**
     * 以分隔标记分隔字符串为字符串数组
     *
     * @param str
     *            源字符串
     * @param separator
     *            分隔符,字符串或正则表达式对象，它标识了分隔字符串时使用的是一个还是多个字符。
     * @return
     */
    public static String[] split(final String str, final String separator) {
        return split(str, separator, false);
    }

    /**
     * 以分,隔标记分,隔字符串,为字符串数组
     *
     * @param str
     *            源字符串
     * @param separator
     *            分隔符
     * @param bool
     *            是否把分隔符添加到字符串尾部返回
     * @return
     */
    public static String[] split(String str, String separator, boolean bool) {
        String[] strs = null;
        if (str != null) {
            strs = str.split(separator);
            if (bool) {
                for (int i = 0, length = strs.length; i < length; i++) {
                    strs[i] += separator;
                }
            }
        }
        return strs;
    }

    /**
     * 将字符串以相同步长分隔生成List返回
     *
     * <pre>
     * 		StringUtil.split("我的世界因为有你才会美",3);   --> "[我的世, 界因为, 有你才, 会美]"
     * 		StringUtil.split("我的世界因为有你才会美",2);   --> "[我的, 世界, 因为, 有你, 才会, 美]"
     * </pre>
     *
     * @param str
     *            源字符串
     * @param step
     *            拆分步长
     * @return
     */
    public static List<String> split(final String str, int step) {
        if (str != null && step > 0) {
            List<String> list;
            if (step > str.length()) {
                list = new ArrayList<String>(1);
                list.add(str);
            }
            else {
                list = new ArrayList<String>();

                int i = 0;
                do {
                    list.add(str.substring(i, i += step));
                }
                while ((i + step) < str.length());

                if (i < str.length()) {
                    list.add(str.substring(i, str.length()));
                }
            }
            return list;
        }
        return null;
    }

    /**
     * 对字符串处理:将指定位置到指定位置的字符以星号代替
     *
     * @param content
     *            传入的字符串
     * @param begin
     *            开始位置
     * @param end
     *            结束位置
     * @return
     */
    public static String getStarString(String content, int begin, int end) {

        if (begin >= content.length() || begin < 0) {
            return content;
        }
        if (end >= content.length() || end < 0) {
            return content;
        }
        if (begin >= end) {
            return content;
        }
        String starStr = "";
        for (int i = begin; i < end; i++) {
            starStr = starStr + "*";
        }
        return content.substring(0, begin) + starStr + content.substring(end, content.length());

    }

    /**
     * 对字符加星号处理：除前面几位和后面几位外，其他的字符以星号代替
     *
     * @param content
     *            传入的字符串
     * @param frontNum
     *            保留前面字符的位数
     * @param endNum
     *            保留后面字符的位数
     * @return 带星号的字符串
     */

    public static String getStarString2(String content, int frontNum, int endNum) {

        if (frontNum >= content.length() || frontNum < 0) {
            return content;
        }
        if (endNum >= content.length() || endNum < 0) {
            return content;
        }
        if (frontNum + endNum >= content.length()) {
            return content;
        }
        String starStr = "";
        for (int i = 0; i < (content.length() - frontNum - endNum); i++) {
            starStr = starStr + "*";
        }
        return content.substring(0, frontNum) + starStr
                + content.substring(content.length() - endNum, content.length());

    }

    /**
     * 字符串比较（不区分大小写）
     * @param a
     * @param b
     * @return
     */
    public static boolean equalsIgnoreCase(String a, String b) {
        if (a == null) {
            return b == null;
        } else {
            return a.equalsIgnoreCase(b);
        }
    }
}
