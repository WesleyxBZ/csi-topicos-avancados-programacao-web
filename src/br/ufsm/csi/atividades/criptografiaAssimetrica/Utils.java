package br.ufsm.csi.atividades.criptografiaAssimetrica;

import javax.crypto.Cipher;
import java.security.*;

public class Utils {

    /**
     * Retorna um par de chave privada e pública usando 2048 bytes
     */
    public static Object[] generateKey() {

        Object[] keys = new Object[2];

        try {

            final KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048);
            final KeyPair key = keyGen.generateKeyPair();

            PrivateKey privateKey = key.getPrivate();
            PublicKey publicKey = key.getPublic();

            keys[0] = privateKey;
            keys[1] = publicKey;

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Chave pública e privada gerada");
        return keys;
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
