package com.adactin.components;

import java.util.Base64;

public class EncryptionUtil {

    public static String encrypt(String strToEncrypt) {
        return Base64.getEncoder().encodeToString(strToEncrypt.getBytes());
    }

    public static String decrypt(String strToDecrypt) {
        return new String(Base64.getDecoder().decode(strToDecrypt));
    }
}
