package com.company.librarymanagement.shared.decode;

import java.util.Base64;

public class Base64Decode {

    public static String decodePassword(String passwordInBase64) {
        byte[] decodeBytes = Base64.getDecoder().decode(passwordInBase64);
        return new String(decodeBytes);
    }
}
