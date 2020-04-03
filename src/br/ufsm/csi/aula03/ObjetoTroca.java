package br.ufsm.csi.aula03;

import javax.crypto.SecretKey;
import java.io.Serializable;

public class ObjetoTroca implements Serializable {

    private String nomeArquivo;
    private byte[] arquivoCriptografado;
    private SecretKey key;

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public byte[] getArquivoCriptografado() {
        return arquivoCriptografado;
    }

    public void setArquivoCriptografado(byte[] arquivoCriptografado) {
        this.arquivoCriptografado = arquivoCriptografado;
    }

    public SecretKey getKey() {
        return key;
    }

    public void setKey(SecretKey key) {
        this.key = key;
    }

}
