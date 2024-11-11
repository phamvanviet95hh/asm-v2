package com.example.thanh_toan_asm.signature;
import com.example.thanh_toan_asm.dtos.GlobalValue;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import javax.crypto.Cipher;

public class RSADigitalSignature {
    // Method to sign the data using a private key
    public static byte[] signData(byte[] data, PrivateKey privateKey) throws Exception {
        Signature rsa = Signature.getInstance("SHA256withRSA");
        rsa.initSign(privateKey);
        rsa.update(data);
        return rsa.sign();  // This is the binary signature
    }

    // Method to verify the signature using a public key
    public static boolean verifySignature(byte[] data, byte[] signature, PublicKey publicKey) throws Exception {
        Signature rsa = Signature.getInstance("SHA256withRSA");
        rsa.initVerify(publicKey);
        rsa.update(data);
        return rsa.verify(signature);
    }

    // Helper method to convert binary signature to Base64 string
    public static String toBase64(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    // Helper method to convert Base64 string to binary signature
    public static byte[] fromBase64(String base64) {
        return Base64.getDecoder().decode(base64);
    }



    public static PrivateKey loadPrivateKey(String filePath) throws Exception {
        // Step 1: Read the private key bytes from the file
        String keyData = new String(Files.readAllBytes(Paths.get(filePath)));

        // Step 2: Remove the header and footer of the PEM file
        keyData = keyData.replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s+", ""); // Remove any extra whitespace or new lines

        // Step 3: Decode the Base64-encoded key
        byte[] privateKeyBytes = Base64.getDecoder().decode(keyData);

        // Step 4: Generate the PrivateKey object using PKCS8 format
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        // Step 5: Return the PrivateKey object
        return keyFactory.generatePrivate(keySpec);
    }
}
