package mybaiduuse.mybaiduuse.cn.bavoice.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**md5
 */
public class Md5Utils {
	
	/**
	 * @param filePath
	 *            文件路径
	 * @return 文件的MD5值
	 */
	public static String getFileMd5(String filePath) {
		StringBuilder mess = new StringBuilder();
		try {
			MessageDigest md = MessageDigest.getInstance("md5");
			// 读取文件
			FileInputStream fis = new FileInputStream(new File(filePath));
			byte[] buffer = new byte[1024 * 10];
			int len = fis.read(buffer);
			while (len != -1) {
				md.update(buffer, 0, len);// 不停的读取文件内容
				len = fis.read(buffer);// 继续读取
			}

			// 读取文件完毕
			byte[] digest = md.digest();
			// byte 8bit 4bit 十六进制 2位十六进制
			for (byte b : digest) {// 数组 Iterable
				// 把一个字节转成十六进制 8 >> 2
				// 去掉一个int类型前3个字节 0a
				int d = b & 0x000000ff;
				String s = Integer.toHexString(d);
				if (s.length() == 1) {
					s = "0" + s;
				}
				mess.append(s);
			}

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (mess + "").toUpperCase();
	}
	/**
	 * @param str
	 *     需要加密的字符串
	 * @return
	 * 		字符串加密后的md5值
	 */
	public static String encode(String str) {
		String res = "";
		String s = "";
		//javase api
		try {
			MessageDigest md = MessageDigest.getInstance("md5");
			//MD5加密后的字节数组
			byte[] digest = md.digest(str.getBytes());
			//把字节数组转成字符串
			// byte 8bit  4bit 十六进制   2位十六进制
			for (byte b: digest) {//数组 Iterable
				//把一个字节转成十六进制 8 >> 2
				//去掉一个int类型前3个字节 0a
				int d = b & 0x000000ff;
				s = Integer.toHexString(d);
				if (s.length() == 1) {
					s = "0" + s;
				}
				res = res + s;
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return res;
	}
}
