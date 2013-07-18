/**
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: 联动优势科技有限公司</p>
 * <p>2013-6-28下午2:37:35</p>
 * @author Zhang Wensheng
 * @version 1.0
 */
package com.jan.beta.util.string;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.StringReader;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import org.apache.commons.lang.StringUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


/** 
 * desc:文本签名验签工具类
 * <p>创建人：Zhang Wensheng 创建日期：2013-6-28 </p>
 * @version V1.0  
 */
public class SignUtil {

	/**
	 * desc:文本签名 <p>创建人：Zhang Wensheng , 2013-6-28 下午2:44:00</p>
	 * @param plain 输入的明文
	 * @param priCertFilePath 签名证书的路径
	 * @return 签名后的密文
	 * @throws Exception 异常
	 */
	public static String sign(String plain, String priCertFilePath) throws Exception {
		// 判断证书路径是否为空
		if(StringUtils.isEmpty(priCertFilePath) ) {
			throw new Exception("the private cert file path is empty [" + priCertFilePath + " ]");
		}
		FileInputStream fis = null;
		byte[] kb = (byte[]) null;
		try {
			File f = new File(priCertFilePath);
			kb = new byte[(int) f.length()];
			fis = new FileInputStream(f);
			fis.read(kb);
		} catch (Exception e) {// 载入证书文件异常
			throw new Exception("load the primary key failed [" + priCertFilePath + "]");
		} finally {// 文件流finally处理
			if(fis != null ) {
				try {
					fis.close();
				} catch (Exception localException1) {
				} finally {
					fis = null;
				}
			}
		}
		// 返回签名密文
		return sign(plain, kb);
	}

	/**
	 * desc:文本签名 <p>创建人：Zhang Wensheng , 2013-6-28 下午2:45:32</p>
	 * @param plain 输入的明文
	 * @param priBytes 签名证书字节数组
	 * @return 签名后的密文
	 * @throws Exception 异常
	 */
	public static String sign(String plain, byte[] priBytes) throws Exception {
		// 判断输入的明文是否为空
		if(StringUtils.isEmpty(plain) ) {
			throw new Exception("the to sign:plain string is empty");
		}
		PKCS8EncodedKeySpec peks = null;
		KeyFactory kf = null;
		PrivateKey pk = null;
		try {
			// 加载签名证书
			peks = new PKCS8EncodedKeySpec(priBytes);
			kf = KeyFactory.getInstance("RSA");
			pk = kf.generatePrivate(peks);
		} catch (Exception e) {// 加载签名证书异常
			throw new Exception("invalid primary key format");
		}
		Signature sig = null;
		byte[] sb = (byte[]) null;
		try {
			// 获取签名实例
			sig = Signature.getInstance("SHA1withRSA");
			sig.initSign(pk);
			sig.update(plain.getBytes("gb2312"));
			sb = sig.sign();
		} catch (Exception e) {
			throw new Exception("sign procedure failed");
		}
		String b64Str = null;
		try {
			// Base64 转码
			BASE64Encoder base64 = new BASE64Encoder();
			b64Str = base64.encode(sb);
		} catch (Exception e) {
			throw new Exception("base64 generation failed");
		}
		try {
			BufferedReader br = new BufferedReader(new StringReader(b64Str));
			String tmpStr = "";
			String tmpStr1 = "";
			while((tmpStr = br.readLine()) != null ) {
				tmpStr1 = tmpStr1 + tmpStr;
			}
			b64Str = tmpStr1;
			return b64Str;
		} catch (Exception br) {
			throw new Exception("base64 generation failed");
		}
	}

	/**
	 * desc:文本签名验签 <p>创建人：Zhang Wensheng , 2013-6-28 下午2:55:58</p>
	 * @param plainString 验签的明文
	 * @param signString 待验签的密文
	 * @param pubCertFilePath 公钥证书路径
	 * @return 是否签名通过
	 * @throws Exception 异常
	 */
	public static boolean verify(String plainString, String signString, String pubCertFilePath) throws Exception {
		FileInputStream fis = null;
		byte[] cb = (byte[]) null;
		try {
			File f = new File(pubCertFilePath);
			cb = new byte[(int) f.length()];
			fis = new FileInputStream(f);
			fis.read(cb);
		} catch (Exception e) {
			throw new Exception("load the cert failed");
		} finally {
			if(fis != null ) {
				try {
					fis.close();
				} catch (Exception localException1) {
				} finally {
					fis = null;
				}
			}
		}
		return verify(plainString, signString, cb);
	}

	/**
	 * desc:文本签名验签 <p>创建人：Zhang Wensheng , 2013-6-28 下午3:09:03</p>
	 * @param plainString 验签的明文
	 * @param signString 待验签的密文
	 * @param pubBytes 公钥证书路径字节
	 * @return 是否签名通过
	 * @throws Exception 异常
	 */
	public static boolean verify(String plainString, String signString, byte[] pubBytes) throws Exception {
		ByteArrayInputStream bais = new ByteArrayInputStream(pubBytes);
		CertificateFactory cf = null;
		X509Certificate cert = null;
		try {
			cf = CertificateFactory.getInstance("X.509");
			cert = (X509Certificate) cf.generateCertificate(bais);
		} catch (Exception e) {
			throw new Exception("load the cert failed");
		}
		try {
			BASE64Decoder base64 = new BASE64Decoder();
			byte[] signed = base64.decodeBuffer(signString);
			Signature sig = Signature.getInstance("SHA1withRSA");
			sig.initVerify(cert);
			sig.update(plainString.getBytes());
			return sig.verify(signed);
		} catch (Exception base64) {
			throw new Exception("verify procedure failed");
		}
	}
}
