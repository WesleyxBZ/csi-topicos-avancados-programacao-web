package br.ufsm.csi.atividades.aula03.criptografiaAssimetrica;

import javax.crypto.Cipher;
import java.security.*;

public class Utils {

    /**
     * Retorna um par de chave privada e pública usando 2048 bytes
     */
    public static KeyPair generateKey() {
        KeyPair key = null;

        try {

            final KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048);
            key = keyGen.generateKeyPair();

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Chave pública e privada gerada");
        return key;
    }

    /**
     * Decriptografa o texto puro usando chave privada
     */
    public static byte[] decrypt(byte[] file, PrivateKey key) {

        byte[] dectyptedText = null;

        try {

            final Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, key);
            dectyptedText = cipher.doFinal(file);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        System.out.println("Arquivo descriptografado");
        return dectyptedText;
    }

    /**
     * Criptografa o texto puro usando chave pública
     */
    public static byte[] encrypt(byte[] file, PublicKey key) {

        byte[] cipherText = null;

        try {

            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            cipherText = cipher.doFinal(file);

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Arquivo criptografado");
        return cipherText;
    }

}
