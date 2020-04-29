package br.ufsm.csi.atividades.aula06.integConf;

import javax.crypto.SecretKey;
import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyPair;
import java.security.PublicKey;

public class Alice {

    public static void main(String[] args) {

        // gerar um par de chaves RSA
        KeyPair keyPair = Utils.generateRSAKey();

        // receber a chave pública de Bob
        PublicKey publicKey = Utils.receivedObject(5000).getKey();

        // gerar a chave de sessão AES
        SecretKey secretKey = Utils.generateAESKey();

        // pegar o arquivo do seu diretório
        TransferObject object = getFile();

        // criptografar a chave de sessão com a chave pública de Bob
        byte[] keyEncrypted = Utils.encryptWithPublickey(secretKey.getEncoded(), publicKey);

        // criptografar o arquivo com a chave de sessão
        object.setEncryptedFile(Utils.encryptWithAESKey(object.getEncryptedFile(), secretKey));

        // gerar a assinatura digital
        TransferObject transferObject = new TransferObject(
                object.getFileName(),
                object.getEncryptedFile(),
                keyPair.getPublic(),
                keyEncrypted,
                null
        );

        // gerar o byte[] deste objeto troca
        byte[] hashTransfObj = Utils.generateHash(transferObject);

        // gerar a assinatura da mensagem criptografando o hash com a chave privada de Alice
        byte[] signature = Utils.encryptWithPrivatekey(hashTransfObj, keyPair.getPrivate());
        transferObject.setSignature(signature);

        // envia objeto troca para Bob
        Utils.sendObject(transferObject, 5555);

    }

    /**
     * Pega arquivo selecionado do seu diretório
     */
    public static TransferObject getFile() {
        TransferObject object = null;
        try {
            JFileChooser fc = new JFileChooser("");
            System.out.println("Selecione um arquivo");
            if (fc.showDialog(new JFrame(), "OK") == JFileChooser.APPROVE_OPTION) {
                File f = fc.getSelectedFile();
                FileInputStream fin = new FileInputStream(f);
                byte[] bArray = new byte[(int) fin.getChannel().size()];
                fin.read(bArray);
                object = new TransferObject(f.getName(), bArray, null, null, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }

}
