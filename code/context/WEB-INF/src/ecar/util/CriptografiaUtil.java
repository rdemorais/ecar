package ecar.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import ecar.util.CriptografiaException;

public class CriptografiaUtil {

	private static final String METODO_ENCRIPTACAO = "AES";
	public static final byte[] CHAVE = { 71, -93, 29, 52, 102, 32, -114, -57,
			88, 24, -25, -69, 96, -116, -19, 48 };

	public static String encriptar(byte[] key, String value)
			throws CriptografiaException {
		try {
			SecretKeySpec skeySpec = new SecretKeySpec(key, METODO_ENCRIPTACAO);

			Cipher cipher = Cipher.getInstance(METODO_ENCRIPTACAO);
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			byte[] encrypted = cipher.doFinal(value.getBytes("UTF-8"));

			return new BASE64Encoder().encode(encrypted);
		} catch (Exception e) {
			throw new CriptografiaException("Erro ao criptografar informações "
					+ e.getMessage());
		}
	}

	public static String decriptar(byte[] key, String encrypted)
			throws CriptografiaException {
		byte[] decrypted = null;
		try {
			SecretKeySpec skeySpec = new SecretKeySpec(key, METODO_ENCRIPTACAO);

			byte[] decoded = new BASE64Decoder().decodeBuffer(encrypted);

			Cipher cipher = Cipher.getInstance(METODO_ENCRIPTACAO);
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			decrypted = cipher.doFinal(decoded);
		} catch (Exception e) {
			throw new CriptografiaException(
					"Erro ao descriptografar informações " + e.getMessage());
		}
		return new String(decrypted);
	}

}
