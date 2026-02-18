package fr.neamar.kiss.utils;

import com.github.promeg.pinyinhelper.Pinyin;

/**
 * Utility class for converting Chinese characters to Pinyin.
 * Supports both full pinyin (e.g., "taobao") and short form (e.g., "tb").
 */
public class PinyinUtils {

    private PinyinUtils() {
        // Private constructor to prevent instantiation
    }

    /**
     * Convert Chinese characters to pinyin without spaces.
     * For example: "淘宝" -> "taobao"
     * Non-Chinese characters are kept as-is.
     *
     * @param chinese the Chinese string to convert
     * @return pinyin string without spaces (lowercase)
     */
    public static String toPinyin(String chinese) {
        if (chinese == null || chinese.isEmpty()) {
            return "";
        }

        StringBuilder pinyinBuilder = new StringBuilder();
        for (int i = 0; i < chinese.length(); i++) {
            char c = chinese.charAt(i);
            // Pinyin.toPinyin(char) returns uppercase pinyin for Chinese characters
            // or the original character for non-Chinese characters
            String pinyin = Pinyin.toPinyin(c);
            // Convert to lowercase for case-insensitive search
            pinyinBuilder.append(pinyin.toLowerCase());
        }
        return pinyinBuilder.toString();
    }

    /**
     * Convert Chinese characters to pinyin short form (first letters only).
     * For example: "淘宝" -> "tb", "阿里巴巴" -> "albb"
     * Non-Chinese characters are kept as-is.
     *
     * @param chinese the Chinese string to convert
     * @return pinyin short form (first letters, lowercase)
     */
    public static String toPinyinShort(String chinese) {
        if (chinese == null || chinese.isEmpty()) {
            return "";
        }

        StringBuilder pinyinShortBuilder = new StringBuilder();
        for (int i = 0; i < chinese.length(); i++) {
            char c = chinese.charAt(i);
            // Pinyin.toPinyin(char) returns uppercase pinyin for Chinese characters
            // Take only the first letter and convert to lowercase
            String pinyin = Pinyin.toPinyin(c);
            if (!pinyin.isEmpty()) {
                pinyinShortBuilder.append(Character.toLowerCase(pinyin.charAt(0)));
            }
        }
        return pinyinShortBuilder.toString();
    }

    /**
     * Check if a string contains Chinese characters.
     *
     * @param str the string to check
     * @return true if the string contains at least one Chinese character
     */
    public static boolean containsChinese(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            // Check if character is in the CJK Unified Ideographs block
            if (Pinyin.isChinese(c)) {
                return true;
            }
        }
        return false;
    }
}
