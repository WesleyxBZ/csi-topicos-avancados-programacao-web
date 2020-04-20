package br.ufsm.csi.atividades.aula04.chaveSessao;

import java.io.Serializable;
import java.security.PublicKey;

public class TransferObject implements Serializable {

    private String fileName;
    private byte[] encryptedFile;
    private PublicKey publicKey;
    private byte[] keyAES;

    public TransferObject(String fileName, byte[] encryptedFile, PublicKey publicKey) {
        this.fileName = fileName;
        this.encryptedFile = encryptedFile;
        this.publicKey = publicKey;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getEncryptedFile() {
        return encryptedFile;
    }

    public void setEncryptedFile(byte[] encryptedFile) {
        this.encryptedFile = encryptedFile;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public byte[] getKeyAES() {
        return keyAES;
    }

    public void setKeyAES(byte[] keyAES) {
        this.keyAES = keyAES;
    }
}
