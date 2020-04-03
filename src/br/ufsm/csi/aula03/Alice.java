package br.ufsm.csi.aula03;

import javax.crypto.*;
import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Alice {

    public static void main(String[] args) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        JFileChooser fc = new JFileChooser("");
        System.out.println("Selecionando arquivo");

        if (fc.showDialog(new JFrame(), "OK") == JFileChooser.APPROVE_OPTION) {
            File f = fc.getSelectedFile();
            FileInputStream fin = new FileInputStream(f);
            byte[] bArray = new byte[(int) fin.getChannel().size()];
            fin.read(bArray);
            System.out.println("Arquivo selecionado");

            Cipher cipherAES = Cipher.getInstance("AES");
            SecretKey keyAES = KeyGenerator.getInstance("AES").generateKey();
            cipherAES.init(Cipher.ENCRYPT_MODE, keyAES);

            byte[] textoCifrado = cipherAES.doFinal(bArray);

            Socket s = new Socket("localhost", 5555);
            ObjetoTroca objetoTroca = new ObjetoTroca();
            objetoTroca.setArquivoCriptografado(textoCifrado);
            objetoTroca.setKey(keyAES);
            objetoTroca.setNomeArquivo(f.getName());

            ObjectOutputStream oout = new ObjectOutputStream(s.getOutputStream());
            oout.writeObject(objetoTroca);
            s.close();

        }
    }

}
