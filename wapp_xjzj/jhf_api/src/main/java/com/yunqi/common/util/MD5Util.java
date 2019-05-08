package com.yunqi.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5校验码工具类，可以生成文件或字符串的MD5校验码，并可对已知校验码进行验证
 *
 *
 */
public class MD5Util {
    /**
     * 默认的密码字符串组合，用来将字节转换成 16 进制表示的字符,apache校验下载的文件的正确性用的就是默认的这个组合
     */
    private static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    private static MessageDigest messagedigest = null;
    static {
        try {
            messagedigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException nsaex) {
            System.err.println(MD5Util.class.getName() + "初始化失败，MessageDigest不支持MD5Util。");
            nsaex.printStackTrace();
        }
    }

    /**
     * 生成字符串的md5校验值
     * 
     * @param s 明文
     * @return 密文
     */
    public static String getMD5String(String s) {
        return getMD5String(s.getBytes());
    }

    /**
     * 生成包含中文字符串的md5校验值
     * 
     * @param s 明文
     * @return  md5字符串
     * @throws UnsupportedEncodingException  编码异常
     */
    public static String getMD5ChineseString(String s) throws UnsupportedEncodingException {
        return getMD5String(s.getBytes("UTF-8"));
    }

    /**
     * 判断字符串的md5校验码是否与一个已知的md5码相匹配
     * 
     * @param password
     *            要校验的字符串
     * @param md5PwdStr
     *            已知的md5校验码
     * @return   比对是否一致
     */
    public static boolean checkPassword(String password, String md5PwdStr) {
        String s = getMD5String(password);
        return s.equals(md5PwdStr);
    }

    /**
     * 判断文件的md5校验码是否与一个已知的md5码相匹配
     * 
     * @param file
     *            要校验的文件
     * @param md5PwdStr
     *            已知的md5校验码
     * @return 比对是否一致
     * @throws IOException   io异常
     */
    public static boolean checkPassword(File file, String md5PwdStr) throws IOException {
        String s = getFileMD5String(file);
        return s.equals(md5PwdStr);
    }

    /**
     * 生成文件的md5校验值
     * 
     * @param file 文件
     * @return md5字符串
     * @throws IOException   io异常
     */
    private static String getFileMD5String(File file) throws IOException {
        InputStream fis;
        fis = new FileInputStream(file);
        byte[] buffer = new byte[1024];
        int numRead = 0;
        while ((numRead = fis.read(buffer)) > 0) {
            messagedigest.update(buffer, 0, numRead);
        }
        fis.close();
        return bufferToHex(messagedigest.digest());
    }

    private static String getMD5String(byte[] bytes) {
        messagedigest.update(bytes);
        return bufferToHex(messagedigest.digest());
    }

    private static String bufferToHex(byte bytes[]) {
        return bufferToHex(bytes, 0, bytes.length);
    }

    private static String bufferToHex(byte bytes[], int m, int n) {
        StringBuffer stringbuffer = new StringBuffer(2 * n);
        int k = m + n;
        for (int l = m; l < k; l++) {
            appendHexPair(bytes[l], stringbuffer);
        }
        return stringbuffer.toString();
    }

    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
        char c0 = hexDigits[(bt & 0xf0) >> 4];// 取字节中高 4 位的数字转换, >>>
                                              // 为逻辑右移，将符号位一起右移,此处未发现两种符号有何不同
        char c1 = hexDigits[bt & 0xf];// 取字节中低 4 位的数字转换
        stringbuffer.append(c0);
        stringbuffer.append(c1);
    }
    
    public static void main(String[] args){
    	String str = getMD5String("123456abc");
    	
    	System.out.println(str);
    }

}