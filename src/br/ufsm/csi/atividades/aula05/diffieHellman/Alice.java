package br.ufsm.csi.atividades.aula05.diffieHellman;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.Socket;

public class Alice {

    public static void main(String[] args) {

        try {

            Socket socket = new Socket("localhost", 5000);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            BigInteger[] QA = Util.geraQA(64);
            out.writeObject(QA);

            BigInteger x = Util.geraNumeroMenorQue(QA[0]);
            BigInteger y = QA[1].modPow(x, QA[0]);

            out.writeObject(y);

            BigInteger Ybob = (BigInteger) in.readObject();

            BigInteger k = Ybob.modPow(x, QA[0]);

            System.out.println("K = " + k);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
