package com.demo.back.utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.*;

/**
 * MD5加密组件
 */
public abstract class ToolMD5 {


	/**
	 * MD5加密
	 * 
	 * @param data
	 *            待加密数据
	 * @return byte[] 消息摘要
	 * @throws Exception
	 */
	public static byte[] encodeMD5(String data) {
		return DigestUtils.md5(data);
	}

	/**
	 * MD5加密
	 * 
	 * @param data
	 *            待加密数据
	 * @return String 消息摘要
	 * @throws Exception
	 */
	public static String encodeMD5Hex(String data) {
		return DigestUtils.md5Hex(data);
	}

	/**
	 * MD5加密
	 * 
	 * @param filePath
	 *            待加密文件路径
	 * @return String 消息摘要
	 * @throws Exception
	 */
	public static String encodeMD5HexFile(String filePath) {
		FileInputStream fis = null;
		String hex = null;
		try {
			fis = new FileInputStream(new File(filePath));
			hex = DigestUtils.md5Hex(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return hex;
	}

	/**
	 * MD5加密
	 * 
	 * @param filePath
	 *            待加密文件路径
	 * @return String 消息摘要
	 * @throws Exception
	 */
	public static String encodeSha256HexFile(String filePath) {
		FileInputStream fis = null;
		String hex = null;
		try {
//			filePath=Utils.pathManipulation(filePath);
			fis = new FileInputStream(new File(filePath));
			hex = DigestUtils.sha256Hex(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return hex;
	}

	/**
	 * MD5加密
	 *
	 * @param inputStream
	 *            待加密文件流
	 * @return String 消息摘要
	 * @throws Exception
	 */
	public static String encodeSha256HexInputStream(InputStream inputStream) {
		String hex = null;
		try {
			hex = DigestUtils.sha256Hex(inputStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return hex;
	}

	public static void main(String[] args) {
		String s1 = encodeSha256HexFile("log/ChromeSetup.exe");
		String s2 = encodeSha256HexFile("log/ChromeSetup1.exe");
		String s3 = encodeSha256HexFile("log/ChromeSetupwqwq.exe");
		System.out.println(s1);
		System.out.println(s2);
		System.out.println(s3);
	}
}
