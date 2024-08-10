package com.ticketland.ticketland.global.util;

import org.springframework.security.crypto.encrypt.AesBytesEncryptor;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AesUtil {
    private static AesBytesEncryptor aesBytesEncryptor;
    public static void setAesBytesEncryptor(AesBytesEncryptor aesBytesEncryptor) {
        if (AesUtil.aesBytesEncryptor == null) {
            AesUtil.aesBytesEncryptor = aesBytesEncryptor;
        }
    }

    public static String encode(String input) {
        return Base64.getEncoder().encodeToString(aesBytesEncryptor.encrypt(input.getBytes()));
    }

    public static String decode(String input) {
        byte[] baseDecoded = Base64.getDecoder().decode(input); // 먼저 base64 디코딩
        return new String(aesBytesEncryptor.decrypt(baseDecoded), StandardCharsets.UTF_8);
    }


}
