package com.github.zhygtx.napcat.util;

/**
 * NapCat SDK 通用工具方法。
 */
public final class NapCatUtils {

    private NapCatUtils() {
        // 工具类禁止实例化
    }

    /**
     * 将字节数组转换为十六进制字符串（用于调试打印）。
     *
     * @param bytes 字节数组
     * @return 十六进制字符串，每字节之间用空格分隔
     */
    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 3);
        for (byte b : bytes) {
            sb.append(String.format("%02X ", b));
        }
        return sb.toString();
    }
}
