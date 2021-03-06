package br.ufsm.csi.atividades.aula03.criptografia.assimetrica;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

public class Bob {

    private static PrivateKey privateKey = null;
    private static PublicKey publicKey = null;

    public static void main(String[] args) {

        KeyPair key = Utils.generateKey();
        privateKey = key.getPrivate();
        publicKey = key.getPublic();

        try {

            Bob.shareKey();
            Bob.receivedObject();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Bob compartilha chave pública com Alice
     */
    public static void shareKey() throws IOException {
        TransferObject transferObject = new TransferObject(null, null, publicKey);
        Socket socket = new Socket("localhost", 5555);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectOutputStream.writeObject(transferObject);
        socket.close();
        System.out.println("[BOB] Compartilhado chave pública com Alice");
    }

    /**
     * Bob recebe arquivo criptografado de Alice
     */
    public static void receivedObject() throws IOException, ClassNotFoundException {
        ServerSocket serverSocket = new ServerSocket(8090);
        Socket socket = serverSocket.accept();
        ObjectInputStream oin = new ObjectInputStream(socket.getInputStream());
        TransferObject objectReceived = (TransferObject) oin.readObject();
        System.out.println("[BOB] Arquivo criptografado recebido de Alice");
        printObjectReceived(objectReceived);
    }

    private static void printObjectReceived(TransferObject objectReceived) {
        byte[] dectyptedText = Utils.decrypt(objectReceived.getEncryptedFile(), privateKey);
        System.out.println("[BOB] Texto plano decifrado: " + new String(dectyptedText));
    }

}
