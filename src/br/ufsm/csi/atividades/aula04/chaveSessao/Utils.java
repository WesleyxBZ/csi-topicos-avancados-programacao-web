package br.ufsm.csi.atividades.aula04.chaveSessao;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;

public class Utils {

    /**
     * Retorna um par de chave privada e pública usando 2048 bytes
     */
    public static KeyPair generateRSAKey() {
        KeyPair key = null;
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048);
            key = keyGen.generateKeyPair();
            System.out.println("Chave RSA pública e privada gerada");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return key;
    }

    /**
     * Retorna chave de sessão AES
     */
    public static SecretKey generateSessionAESKey() {
        SecretKey keyAES = null;
        try {
            keyAES = KeyGenerator.getInstance("AES").generateKey();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return keyAES;
    }

    /**
     * Criptografa o texto puro usando chave AES de sessão
     */
    public static byte[] encryptWithKeyAES(byte[] file, SecretKey keyAES) {
        byte[] encryptText = null;
        try {
            Cipher cipherAES = Cipher.getInstance("AES");
            cipherAES.init(Cipher.ENCRYPT_MODE, keyAES);
            encryptText = cipherAES.doFinal(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Arquivo criptografado com chave de sessão AES");
        return encryptText;
    }

    /**
     * Criptografa com RSA a chave AES usando chave pública
     */
    public static byte[] encryptAESKeyWithPublicKey(SecretKey keyAES, PublicKey keyPublic) {
        byte[] key = null;
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, keyPublic);
            key = cipher.doFinal(keyAES.getEncoded());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return key;
    }

    /**
     * Decriptografa chave AES usando chave privada
     */
    public static SecretKey decryptKeyAESWithkeyPrivate(byte[] keyAES, PrivateKey privateKey) {
        SecretKey key = null;
        try {
            final Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] dectyptedKey = cipher.doFinal(keyAES);
            key = new SecretKeySpec(dectyptedKey, 0, dectyptedKey.length, "AES");
            System.out.println("Chave AES descriptografada");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return key;
    }

    /**
     * Decriptografa texto usando chave AES
     */
    public static byte[] decryptWithKeyAES(byte[] encryptedFile, SecretKey keyAES) {
        byte[] dectypted = null;
        try {
            Cipher cipherAES = Cipher.getInstance("AES");
            cipherAES.init(Cipher.DECRYPT_MODE, keyAES);
            dectypted = cipherAES.doFinal(encryptedFile);
            System.out.println("Texto descriptografado com chave AES");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return dectypted;
    }

}
