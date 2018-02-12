package com.gunnarro.dietmanager.spring.custom.security;

import org.springframework.security.crypto.encrypt.TextEncryptor;

public final class NoOpTextEncryptor implements TextEncryptor {

    @Override
    public String encrypt(String text) {
        return text;
    }

    @Override
    public String decrypt(String encryptedText) {
        return encryptedText;
    }

}
