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
     * @return pinyin string without spaces
     */
    public static String toPinyin(String chinese) {
        if (chinese == null || chinese.isEmpty()) {
            return "";
        }
        
        StringBuilder pinyinBuilder = new StringBuilder();
        for (int i = 0; i < chinese.length(); i++) {
            char c = chinese.charAt(i);
            // Convert each character to pinyin
            String[] pinyinArray = Pinyin.toPinyinArray(c);
            if (pinyinArray != null && pinyinArray.length > 0) {
                // Use the first pinyin variant (most common one)
                pinyinBuilder.append(pinyinArray[0]);
            } else {
                // Keep non-Chinese characters as-is
                pinyinBuilder.append(c);
            }
        }
        return pinyinBuilder.toString();
    }
    
    /**
     * Convert Chinese characters to pinyin short form (first letters only).
     * For example: "淘宝" -> "tb", "阿里巴巴" -> "albb"
     * Non-Chinese characters are kept as-is.
     *
     * @param chinese the Chinese string to convert
     * @return pinyin short form (first letters)
     */
    public static String toPinyinShort(String chinese) {
        if (chinese == null || chinese.isEmpty()) {
            return "";
        }
        
        StringBuilder pinyinShortBuilder = new StringBuilder();
        for (int i = 0; i < chinese.length(); i++) {
            char c = chinese.charAt(i);
            // Convert each character to pinyin
            String[] pinyinArray = Pinyin.toPinyinArray(c);
            if (pinyinArray != null && pinyinArray.length > 0) {
                // Take only the first letter of the pinyin
                pinyinShortBuilder.append(pinyinArray[0].charAt(0));
            } else {
                // Keep non-Chinese characters as-is
                pinyinShortBuilder.append(c);
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
