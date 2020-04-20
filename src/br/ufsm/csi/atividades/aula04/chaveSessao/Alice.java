package br.ufsm.csi.atividades.aula04.chaveSessao;

import javax.crypto.SecretKey;
import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.PublicKey;

public class Alice {

    public static void main(String[] args) {

        PublicKey publicKey = receivePublicKey();

        SecretKey sessionKey = Utils.generateSessionAESKey();

        TransferObject object = getFile();

        object.setEncryptedFile(Utils.encryptWithKeyAES(object.getEncryptedFile(), sessionKey));

        object.setKeyAES(Utils.encryptAESKeyWithPublicKey(sessionKey, publicKey));

        sendObject(object);

    }

    /**
     * Recebe chave pública de Bob
     */
    public static PublicKey receivePublicKey() {
        PublicKey publicKey = null;
        try {
            System.out.println("[ALICE] Aguardando conexão de Bob");
            ServerSocket serverSocket = new ServerSocket(5555);
            Socket socket = serverSocket.accept();
            System.out.println("[ALICE] Conexão recebida");

            ObjectInputStream oin = new ObjectInputStream(socket.getInputStream());
            TransferObject object = (TransferObject) oin.readObject();
            publicKey = object.getPublicKey();

            serverSocket.close();
            System.out.println("[ALICE] Chave pública recebida");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return publicKey;
    }

    /**
     * Pega arquivo selecionado do seu diretório
     */
    public static TransferObject getFile() {
        TransferObject object = null;
        try {
            JFileChooser fc = new JFileChooser("");
            System.out.println("[ALICE] Selecione um arquivo");
            if (fc.showDialog(new JFrame(), "OK") == JFileChooser.APPROVE_OPTION) {
                File f = fc.getSelectedFile();
                FileInputStream fin = new FileInputStream(f);
                byte[] bArray = new byte[(int) fin.getChannel().size()];
                fin.read(bArray);
                object = new TransferObject(f.getName(), bArray, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }

    /**
     * Envia arquivo criptografado para Bob
     */
    public static void sendObject(TransferObject object) {
        try {
            Socket socket = new Socket("localhost", 6666);
            ObjectOutputStream oout = new ObjectOutputStream(socket.getOutputStream());
            oout.writeObject(object);
            socket.close();
            System.out.println("[ALICE] Arquivo criptografado enviado para Bob");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
