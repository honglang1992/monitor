package com.vrv.monitor.core.algorithm;

/**
 * KMP算法
 *
 * @author dendi
 * date 2021-12-02
 */
public class KmpAlgorithm {

    /**
     * is sourceStr contain targetStr
     *
     * @param sourceStr
     * @param targetStr
     */
    public boolean contains(String sourceStr, String targetStr) {
        char[] sourceChar = sourceStr.toCharArray();
        char[] targetChar = targetStr.toCharArray();

        int sourceIndex = 0, targetIndex = 0;

        int[] next = getNextArray(targetChar);
        //遍历
        while (sourceIndex < sourceChar.length && targetIndex < targetChar.length) {

            if (sourceChar[sourceIndex] != targetChar[targetIndex]) {

                targetIndex = next[targetIndex];

                if(targetIndex==-1){
                    sourceIndex++;
                    targetIndex=0;
                }
            } else {
                sourceIndex++;
                targetIndex++;
            }
        }

        if (targetIndex == targetChar.length) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 求出一个字符数组的next数组
     *
     * @param t 字符数组
     * @return next数组
     */
    public static int[] getNextArray(char[] t) {
        int[] next = new int[t.length];

        //初始条件
        int j = 0;

        next[0] = -1;

        int k = next[0];

        // 根据已知的前j位推测第j+1位
        while (j < t.length - 1)
        {
            if (k == -1 || t[j] == t[k])
            {
                // 如果str[j + 1] == str[k + 1]，回退后仍然失配，所以要继续回退
                if (t[j + 1] == t[k + 1])
                {
                    next[++j] = next[++k];
                }
                else
                {
                    next[++j] = ++k;
                }
            }
            else
            {
                k = next[k];
            }
        }

        return next;
    }

    /**
     * 求出一个字符数组的next数组
     * 这个是最基本的逻辑方式，getNextArray是优化之后的
     *
     * @param strKey 字符数组
     * @return next数组
     */
    public static int[] getNextArray2(char[] strKey) {

        int[] next = new int[strKey.length];

        // 初始条件
        int j = 0;
        int k = -1;
        next[0] = -1;

        // 根据已知的前j位推测第j+1位
        while (j < strKey.length - 1)
        {
            if (k == -1 || strKey[j] == strKey[k])
            {
                next[++j] = ++k;
            }
            else
            {
                k = next[k];
            }
        }

        return next;

    }
    public static void main(String[] args) {
        int[] index = KmpAlgorithm.getNextArray("abcabc".toCharArray());

        int[] index2 = KmpAlgorithm.getNextArray("abaaba".toCharArray());

        boolean isContain = new KmpAlgorithm().contains("abcde", "cde");

        System.out.println(index.toString() + "" + index2.toString() + isContain);
    }
}
