package com.demo.utils.encrypt;



import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Util {

	public final static String MD5(String s) {
		char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
		try {
			byte[] btInput = s.getBytes();
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char[] str = new char[j * 2];
			int k = 0;

			for (byte byte0 : md) {
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @param str
	 * @return
	 * @Description: 32位小写MD5
	 */
	public static String parseStrToMd5L32(String str) {
		String reStr = null;
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] bytes = md5.digest(str.getBytes());
			StringBuffer stringBuffer = new StringBuffer();
			for (byte b : bytes) {
				int bt = b & 0xff;
				if (bt < 16) {
					stringBuffer.append(0);
				}
				stringBuffer.append(Integer.toHexString(bt));
			}
			reStr = stringBuffer.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return reStr;
	}

	/**
	 * @param str
	 * @return
	 * @Description: 32位大写MD5
	 */
	public static String parseStrToMd5U32(String str) {
		String reStr = parseStrToMd5L32(str);
		if (reStr != null) {
			reStr = reStr.toUpperCase();
		}
		return reStr;
	}

	/**
	 * @param str
	 * @return
	 * @Description: 16位小写MD5
	 */
	public static String parseStrToMd5U16(String str) {
		String reStr = parseStrToMd5L32(str);
		if (reStr != null) {
			reStr = reStr.toUpperCase().substring(8, 24);
		}
		return reStr;
	}

	/**
	 * @param str
	 * @return
	 * @Description: 16位大写MD5
	 */
	public static String parseStrToMd5L16(String str) {
		String reStr = parseStrToMd5L32(str);
		if (reStr != null) {
			reStr = reStr.substring(8, 24);
		}
		return reStr;
	}

	/**
	 * 对文本进行32位小写MD5加密
	 * @param plainText 要进行加密的文本
	 * @return 加密后的内容
	 */
	public static String textToMD5L32(String plainText) {
		String result = null;
		// 首先判断是否为空
		if (StringUtils.hasLength(plainText)) {
			return null;
		}
		try {
			// 首先进行实例化和初始化
			MessageDigest md = MessageDigest.getInstance("MD5");
			// 得到一个操作系统默认的字节编码格式的字节数组
			byte[] btInput = plainText.getBytes();
			// 对得到的字节数组进行处理
			md.update(btInput);
			// 进行哈希计算并返回结果
			byte[] btResult = md.digest();
			// 进行哈希计算后得到的数据的长度
			StringBuffer sb = new StringBuffer();
			for (byte b : btResult) {
				int bt = b & 0xff;
				if (bt < 16) {
					sb.append(0);
				}
				sb.append(Integer.toHexString(bt));
			}
			result = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 对文本进行32位MD5大写加密
	 * @param plainText 要进行加密的文本
	 * @return 加密后的内容
	 */
	public static String textToMD5U32(String plainText) {
		if (StringUtils.hasLength(plainText)) {
			return null;
		}
		String result = textToMD5L32(plainText);
		return result.toUpperCase();
	}

    /**
     * 对文件进行32位MD5大写
     * @param file 要进行md5的文件
     * @return md5摘要
     */
    public static String getMd5ByFileU32(File file) {
	    return getMd5ByFile(file).toUpperCase();
    }

    /**
     * 对文件进行32位MD5小写
     * @param file 要进行md5的文件
     * @return md5摘要
     */
    public static String getMd5ByFileL32(File file) {
        return getMd5ByFile(file).toLowerCase();
    }

    private static String getMd5ByFile(File file) {
        String value = null;
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        try {
            MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(byteBuffer);

            value = toHexString(md5.digest());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }

    public static String toHexString(byte[] b) {

		char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        StringBuilder sb = new StringBuilder(b.length * 2);

		for (byte value : b) {
			sb.append(hexDigits[(value & 0xf0) >>> 4]);
			sb.append(hexDigits[value & 0x0f]);
		}

        return sb.toString();
    }

	public static void main(String[] args) {
		System.out.println(Md5Util.MD5("123456adminsdp"));
		System.out.println(Md5Util.parseStrToMd5L32("123456adminsdp"));
		System.out.println(Md5Util.parseStrToMd5U32("123456adminsdp"));
		System.out.println(Md5Util.parseStrToMd5L16("123456adminsdp"));
		System.out.println(Md5Util.parseStrToMd5U16("123456adminsdp"));
		System.out.println(Md5Util.parseStrToMd5U16("123456adminsdp"));
        String path="D:\\a.txt";
        System.out.println("MD5:"+Md5Util.getMd5ByFile(new File(path)).toUpperCase());
	}
}
