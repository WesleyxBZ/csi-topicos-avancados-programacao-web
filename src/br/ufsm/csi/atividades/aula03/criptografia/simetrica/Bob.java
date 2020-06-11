package br.ufsm.csi.atividades.aula03.criptografia.simetrica;

import javax.crypto.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Bob {

    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        ServerSocket ss = new ServerSocket(5555);
        System.out.println("[BOB] Aguardando conexão na porta 5555.");
        Socket s = ss.accept();
        System.out.println("[BOB] Conexão recebida.");

        ObjectInputStream oin = new ObjectInputStream(s.getInputStream());
        ObjetoTroca objetoTroca = (ObjetoTroca) oin.readObject();

        Cipher cipherAES = Cipher.getInstance("AES");
        cipherAES.init(Cipher.DECRYPT_MODE, objetoTroca.getKey());

        byte[] textoPlano = cipherAES.doFinal(objetoTroca.getArquivoCriptografado());

        System.out.println("[BOB] Texto plano decifrado: " + new String(textoPlano));

    }


}
