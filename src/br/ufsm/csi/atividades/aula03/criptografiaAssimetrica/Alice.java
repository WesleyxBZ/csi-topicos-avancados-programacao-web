package br.ufsm.csi.atividades.aula03.criptografiaAssimetrica;

import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.PublicKey;

public class Alice {

    private static PublicKey publicKey = null;

    public static void main(String[] args) {

        try {

            receivedObject();

            // Selecionando arquivo
            JFileChooser fc = new JFileChooser("");
            System.out.println("[ALICE] Selecione um arquivo");

            if (fc.showDialog(new JFrame(), "OK") == JFileChooser.APPROVE_OPTION) {

                // Transformando arquivo em byte array
                File f = fc.getSelectedFile();
                FileInputStream fin = new FileInputStream(f);
                byte[] bArray = new byte[(int) fin.getChannel().size()];
                fin.read(bArray);

                // Criptografando a Mensagem usando a Chave Pública
                TransferObject object = new TransferObject(f.getName(), null, publicKey);
                byte[] encryptedFile = Utils.encrypt(bArray, object.getPublicKey());
                object.setEncryptedFile(encryptedFile);

                sendObject(object);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Recebe a chave pública de Bob
     */
    public static void receivedObject() throws IOException, ClassNotFoundException {
        // Aguardando conexão
        System.out.println("[ALICE] Aguardando conexão");
        ServerSocket serverSocket = new ServerSocket(5555);
        Socket socket = serverSocket.accept();
        System.out.println("[ALICE] Conexão recebida");

        // Recebendo a chave pública
        ObjectInputStream oin = new ObjectInputStream(socket.getInputStream());
        TransferObject object = (TransferObject) oin.readObject();
        publicKey = object.getPublicKey();

        serverSocket.close();
        System.out.println("[ALICE] Chave pública recebida");
    }

    /**
     * Envia arquivo criptografado para Bob
     */
    public static void sendObject(TransferObject object) throws IOException {
        Socket socket = new Socket("localhost", 8090);
        ObjectOutputStream oout = new ObjectOutputStream(socket.getOutputStream());
        oout.writeObject(object);
        socket.close();
        System.out.println("[ALICE] Arquivo criptografado enviado");
    }

}
