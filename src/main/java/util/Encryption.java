package util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Encryption {

    public static byte[] genAESKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        SecretKey secretKey = keyGenerator.generateKey();
        return secretKey.getEncoded();
    }
    private static final String ALGORITHM = "AES";

    public static String encrypt(String str, byte[] SECRET_KEY) throws Exception {
        SecretKey key = new SecretKeySpec(SECRET_KEY, ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedByteValue = cipher.doFinal(str.getBytes("utf-8"));
        String encodedString = Base64.getEncoder().encodeToString(encryptedByteValue);
        return encodedString;
    }

    public static String decrypt(String str, byte[] SECRET_KEY) throws Exception {
        SecretKey key = new SecretKeySpec(SECRET_KEY, ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedValue64 = Base64.getDecoder().decode(str);
        byte[] decryptedByteValue = cipher.doFinal(decryptedValue64);
        String decryptedString = new String(decryptedByteValue, "utf-8");
        return decryptedString;
    }

    public static void main(String[] args) throws Exception {

        byte[] SECRET_KEY = genAESKey();
        System.out.println("Secret Key: " + SECRET_KEY);
        String str = "tung";
        String encryptedStr = encrypt(str, SECRET_KEY);
        System.out.println("Encrypted String: " + encryptedStr);

        String decryptedStr = decrypt(encryptedStr, SECRET_KEY);
        System.out.println("Decrypted String: " + decryptedStr);
    }

}
