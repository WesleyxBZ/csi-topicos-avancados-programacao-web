package br.ufsm.csi.atividades.aula06.integConf;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.*;

public class Utils {

    /**
     * Retorna um par de chave RSA, privada e pública usando 2048 bytes
     */
    public static KeyPair generateRSAKey() {
        KeyPair key = null;
        try {
            final KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048);
            key = keyGen.generateKeyPair();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Par de chave RSA gerado");
        return key;
    }

    /**
     * Retorna chave de sessão AES
     */
    public static SecretKey generateAESKey() {
        SecretKey keyAES = null;
        try {
            keyAES = KeyGenerator.getInstance("AES").generateKey();
            System.out.println("Chave AES gerado");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return keyAES;
    }

    /**
     * Descriptografa byte[] usando chave pública
     */
    public static byte[] decryptWithPublicKey(byte[] byteArray, PublicKey key) {
        byte[] byteArrayDectypted = null;
        try {
            final Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byteArrayDectypted = cipher.doFinal(byteArray);
            System.out.println("Byte[] descriptografado com chave pública");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return byteArrayDectypted;
    }

    /**
     * Decriptografa byte[] usando chave privada
     */
    public static byte[] decryptWithPrivateKey(byte[] byteArray, PrivateKey key) {
        byte[] byteArrayDectypted = null;
        try {
            final Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byteArrayDectypted = cipher.doFinal(byteArray);
            System.out.println("Byte[] descriptografado com chave privada");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return byteArrayDectypted;
    }

    /**
     * Decriptografa byte[] usando chave AES
     */
    public static byte[] decryptWithAESKey(byte[] byteArray, SecretKey keyAES) {
        byte[] byteArrayDectypted = null;
        try {
            Cipher cipherAES = Cipher.getInstance("AES");
            cipherAES.init(Cipher.DECRYPT_MODE, keyAES);
            byteArrayDectypted = cipherAES.doFinal(byteArray);
            System.out.println("Byte[] descriptografado com chave AES");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return byteArrayDectypted;
    }

    /**
     * Criptografa byte[] usando chave pública
     */
    public static byte[] encryptWithPublickey(byte[] byteArray, PublicKey publicKey) {
        byte[] byteArrayEncryted = null;
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byteArrayEncryted = cipher.doFinal(byteArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Byte[] criptografado com chave pública");
        return byteArrayEncryted;
    }

    /**
     * Criptografa byte[] usando chave privada
     */
    public static byte[] encryptWithPrivatekey(byte[] byteArray, PrivateKey privateKey) {
        byte[] byteArrayEncryted = null;
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            byteArrayEncryted = cipher.doFinal(byteArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Byte[] criptografado com chave privada");
        return byteArrayEncryted;
    }

    /**
     * Criptografa byteArray usando chave AES
     */
    public static byte[] encryptWithAESKey(byte[] byteArray, SecretKey keyAES) {
        byte[] encryptByteArray = null;
        try {
            Cipher cipherAES = Cipher.getInstance("AES");
            cipherAES.init(Cipher.ENCRYPT_MODE, keyAES);
            encryptByteArray = cipherAES.doFinal(byteArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Byte[] criptografado com chave AES");
        return encryptByteArray;
    }

    /**
     * Gera o hash a partir de objeto
     */
    public static byte[] generateHash(TransferObject object) {
        byte[] hash = null;
        try {
            ByteArrayOutputStream bOut = new ByteArrayOutputStream();
            new ObjectOutputStream(bOut).writeObject(object);
            byte[] bArrayObject = bOut.toByteArray();
            hash = MessageDigest.getInstance("SHA-256").digest(bArrayObject);
            System.out.println("Hash gerado");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hash;
    }

    /**
     * Envia objeto
     */
    public static void sendObject(TransferObject object, int port) {
        try {
            Socket socket = new Socket("localhost", port);
            ObjectOutputStream oout = new ObjectOutputStream(socket.getOutputStream());
            oout.writeObject(object);
            socket.close();
            System.out.println("Objeto enviado");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Recebe objeto
     */
    public static TransferObject receivedObject(int port) {
        TransferObject objectReceived = null;
        try {
            System.out.println("Aguardando conexão");
            ServerSocket serverSocket = new ServerSocket(port);
            Socket socket = serverSocket.accept();
            System.out.println("Conexão recebida");
            ObjectInputStream oin = new ObjectInputStream(socket.getInputStream());
            objectReceived = (TransferObject) oin.readObject();
            System.out.println("Objeto recebido");
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objectReceived;
    }

}
