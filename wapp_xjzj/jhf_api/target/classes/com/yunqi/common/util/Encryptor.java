package com.yunqi.common.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * BlowFish 加解密工具
 */
public class Encryptor {

	/**
	 * 根据系统当前时间的时分秒生成密钥
	 * @author ZhangQ
	 * @return 密钥
	 */
	public String getKey(){
		SimpleDateFormat dateFormat = new SimpleDateFormat("HHmmss");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		String key = dateFormat.format(timestamp);
		return key;
	}


	/**
	 * 输入密钥加密
	 * @param str 明文
	 * @param key 密钥
	 * @return 密文
	 */
	public String encrypt(String str, Timestamp key){
		SimpleDateFormat dateFormat = new SimpleDateFormat("HHmmss");
		String k = dateFormat.format(key);
		byte[] keyByte = k.getBytes();
		Blowfish blowfish = new Blowfish();
		blowfish.init(true,keyByte);
		return Blowfish.byteArr2HexStr(blowfish.encrypt(str));
	}

	/**
	 *解密器
	 * @param str 密文
	 * @param key 密钥
	 * @return 明文
	 */
	public  String decrypt(String str,String key) {
		byte[] keyByte = key.getBytes();
		Blowfish bf2 = new Blowfish();
		bf2.init(true, keyByte);
		byte[] bytes = Blowfish.hexStr2ByteArr(str);
		return bf2.decryptString(bytes);
	}

}
