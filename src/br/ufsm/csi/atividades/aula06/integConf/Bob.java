package br.ufsm.csi.atividades.aula06.integConf;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.KeyPair;
import java.util.Arrays;

public class Bob {

    public static void main(String[] args) {

        // gerar par de chaves
        KeyPair key = Utils.generateRSAKey();

        // enviar chave pública para Alice
        Utils.sendObject(
                new TransferObject(null, null, key.getPublic(), null, null),
                5000
        );

        // receber objeto de Alice
        TransferObject object = Utils.receivedObject(5555);

        // descriptografar assinatura com chave pública de Alice
        byte[] signatureDecrypted = Utils.decryptWithPublicKey(object.getSignature(), object.getKey());

        // converter o objeto para byte[] e gerar o hash
        object.setSignature(null);
        byte[] hash = Utils.generateHash(object);

        if (Arrays.equals(signatureDecrypted, hash)) {

            // descriptografar a chave de sessão com chave privada de Bob
            byte[] sessionKey = Utils.decryptWithPrivateKey(object.getSessionKey(), key.getPrivate());
            SecretKey secretKey = new SecretKeySpec(sessionKey, 0, sessionKey.length, "AES");

            // descriptografa o arquivo com chave de sessão
            byte[] file = Utils.decryptWithAESKey(object.getEncryptedFile(), secretKey);

            System.out.println("\n\nTexto descriptografado: " + new String(file));

        } else {
            System.out.println("\n\nArquivo não integro!");
        }

    }

}
