package com.tencent;

import com.tls.tls_sigature.tls_sigature;
import com.tls.tls_sigature.tls_sigature.CheckTLSSignatureResult;
import com.tls.tls_sigature.tls_sigature.GenTLSSignatureResult;

/**
 * 
 * 
 *
 */
public class TencentLiveSignature {

	private static TencentLiveSignature install;
	private long expire;
	private long skdAppid;
	private long accountType;
	private String strAppid3rd;

	private TencentLiveSignature() {
		expire = SigMessages.getLong(SigMessages.EXPIRE);

		skdAppid = SigMessages.getLong(SigMessages.SdKAPPID);

		accountType = SigMessages.getLong(SigMessages.ACCOUNTTYPE);

		strAppid3rd = SigMessages.getString(SigMessages.APPID3RD);
	}

	public static TencentLiveSignature install() {

		if (install == null) {
			return new TencentLiveSignature();
		}
		return install;
	}

	/**
	 * 生成签名
	 * @param identifier
	 * @return
	 */
	public String genTLSSignature(String identifier) {

		try {
			String privStr = SignatureUtil.instance().getPrivateContent();

			GenTLSSignatureResult result = tls_sigature.GenTLSSignature(expire,
					strAppid3rd, skdAppid, identifier, accountType, privStr);

			if (result.urlSig != null)
				return result.urlSig;

		} catch (Exception e) {
            
		}
		return null;

	}
	
	/**
	 * 签名对比
	 * @param privUrlSig
	 * @param identifier
	 * @return
	 */

	public boolean checkTLSSignature(String privUrlSig, String identifier) {
		try {
			String pubStr = SignatureUtil.instance().getPublicContent();

			CheckTLSSignatureResult checkResult = tls_sigature
					.CheckTLSSignature(privUrlSig, strAppid3rd, skdAppid,
							identifier, accountType, pubStr);

			return checkResult.verifyResult;
		} catch (Exception e) {

		}
		return false;
	}

}
