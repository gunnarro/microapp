package com.gunnarro.dietmanager.spring.custom.security;

import org.springframework.security.crypto.encrypt.TextEncryptor;

public final class NoOpTextEncryptor implements TextEncryptor {

    public String encrypt(String text) {
        return text;
    }

    public String decrypt(String encryptedText) {
        return encryptedText;
    }

}
