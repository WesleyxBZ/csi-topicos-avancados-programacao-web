package br.ufsm.csi.atividades.aula05.diffieHellman;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;

public class Bob {

    public static void main(String[] args) {

        try {

            ServerSocket serverSocket = new ServerSocket(5000);
            Socket socket = serverSocket.accept();
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            BigInteger[] QA = (BigInteger[]) in.readObject();
            BigInteger Yana = (BigInteger) in.readObject();

            BigInteger x = Util.geraNumeroMenorQue(QA[0]);
            BigInteger y = QA[1].modPow(x, QA[0]);

            out.writeObject(y);

            BigInteger k = Yana.modPow(x, QA[0]);

            System.out.println("k = " + k);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
