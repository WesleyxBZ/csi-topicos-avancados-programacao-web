package br.ufsm.csi.atividades.aula04.chaveSessao;

import javax.crypto.SecretKey;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

public class Bob {

    public static void main(String[] args) {

        KeyPair key = Utils.generateRSAKey();
        PrivateKey privateKey = key.getPrivate();
        PublicKey publicKey = key.getPublic();

        Bob.sharePublicKey(publicKey);

        TransferObject object = receivedObject();

        SecretKey keyAES = Utils.decryptKeyAESWithkeyPrivate(object.getKeyAES(), privateKey);

        byte[] file = Utils.decryptWithKeyAES(object.getEncryptedFile(), keyAES);
        System.out.println("\n\nTesto descriptografado: " + new String(file));

    }

    /**
     * Bob compartilha chave pública com Alice
     */
    public static void sharePublicKey(PublicKey publicKey) {
        try {
            TransferObject transferObject = new TransferObject(null, null, publicKey);
            Socket socket = new Socket("localhost", 5555);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(transferObject);
            socket.close();
            System.out.println("[BOB] Chave pública compartilhada com Alice");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Bob recebe arquivo criptografado de Alice
     */
    public static TransferObject receivedObject() {
        TransferObject objectReceived = null;
        try {
            System.out.println("[BOB] Aguardando conexão de Alice");
            ServerSocket serverSocket = new ServerSocket(6666);
            Socket socket = serverSocket.accept();
            System.out.println("[BOB] Conexão recebida");
            ObjectInputStream oin = new ObjectInputStream(socket.getInputStream());
            objectReceived = (TransferObject) oin.readObject();
            System.out.println("[BOB] Arquivo criptografado recebido de Alice");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objectReceived;
    }

}
