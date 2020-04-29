package br.ufsm.csi.atividades.aula06.integConf;

import java.io.Serializable;
import java.security.PublicKey;

public class TransferObject implements Serializable {

    private String fileName;
    private byte[] encryptedFile;
    private PublicKey key;
    private byte[] sessionKey;
    private byte[] signature;

    public TransferObject(String fileName, byte[] encryptedFile, PublicKey key, byte[] sessionKey, byte[] signature) {
        this.fileName = fileName;
        this.encryptedFile = encryptedFile;
        this.key = key;
        this.sessionKey = sessionKey;
        this.signature = signature;
    }

    public TransferObject() {
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

    public PublicKey getKey() {
        return key;
    }

    public void setKey(PublicKey key) {
        this.key = key;
    }

    public byte[] getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(byte[] sessionKey) {
        this.sessionKey = sessionKey;
    }

    public byte[] getSignature() {
        return signature;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

}
