package com.example.cab302javaproject;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class EncryptionUtilsTest {
    @Test
    void testEncryptAndDecrypt() throws Exception {
        String originalData = "Test Data";
        String encryptedData = EncryptionUtils.encrypt(originalData);
        String decryptedData = EncryptionUtils.decrypt(encryptedData);
        assertEquals(originalData, decryptedData);
    }
}
