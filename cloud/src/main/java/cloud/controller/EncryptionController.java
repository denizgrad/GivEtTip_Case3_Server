package cloud.controller;

import java.security.MessageDigest;
import java.security.spec.MGF1ParameterSpec;

public class EncryptionController {
	
	public static String sha1(String raw) {
		try {
			byte[] bytes = raw.getBytes("UTF-8");
			MessageDigest md = MessageDigest.getInstance(MGF1ParameterSpec.SHA1.getDigestAlgorithm());
			md.update(bytes);
			byte[] digest = md.digest();
			return new String(digest, "UTF-8");
		} catch (Exception e) {
			return "";
		}
	}

}
