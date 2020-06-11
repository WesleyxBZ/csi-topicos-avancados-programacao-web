package br.ufsm.csi.avaliacoesPraticas.primeira.certificados;

import javax.crypto.Cipher;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * Classe que representa um documento assinado, juntamente com o certificado associado.
 */
public class DocumentoAssinado implements Assinavel {

    private byte[] dadosDocumento;
    private String mimeType;
    private String nomeDocumento;
    private byte[] assinatura;
    private Certificado certificado;

    /**
     * Verificação de autenticidade deste documento, onde deve ser verificado: autenticidade da assinatura
     * e autenticidade do certificado utilizado.
     *
     * @return booleano que representa a autenticidade ou não deste documento.
     */
    public boolean verificaAutenticidade() {
        return certificado.verificaAutenticidade() && verificaAssinaturaDocumento(this);
    }

    public boolean verificaAssinaturaDocumento(DocumentoAssinado documentoAssinado) {
        try {
            byte[] hash = Util.getHash(documentoAssinado);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, documentoAssinado.certificado.getChavePublica());
            byte[] assinaturaDecript = cipher.doFinal(documentoAssinado.getAssinatura());
            return Arrays.equals(hash, assinaturaDecript);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static DocumentoAssinado findDocumentoo(String nomeArquivo) {
        try {
            FileInputStream fileIS = new FileInputStream(Util.basePath + "/doc/" + nomeArquivo + ".txt");
            ObjectInputStream oIS = new ObjectInputStream(fileIS);
            DocumentoAssinado doc = (DocumentoAssinado) oIS.readObject();
            oIS.close();
            fileIS.close();
            return doc;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean saveDocumento(DocumentoAssinado documentoAssinado) {
        try {
            ByteArrayOutputStream bOut = new ByteArrayOutputStream();
            new ObjectOutputStream(bOut).writeObject(documentoAssinado);
            byte[] doc = bOut.toByteArray();
            Path pathBase = Paths.get(Util.basePath + "/doc/");
            Files.createDirectories(pathBase);
            Path path = pathBase.resolve(documentoAssinado.getNomeDocumento() + ".txt");
            Files.write(path, doc);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public byte[] getDadosDocumento() {
        return dadosDocumento;
    }

    public void setDadosDocumento(byte[] dadosDocumento) {
        this.dadosDocumento = dadosDocumento;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getNomeDocumento() {
        return nomeDocumento;
    }

    public void setNomeDocumento(String nomeDocumento) {
        this.nomeDocumento = nomeDocumento;
    }

    public byte[] getAssinatura() {
        return assinatura;
    }

    public void setAssinatura(byte[] assinatura) {
        this.assinatura = assinatura;
    }

    @Override
    public Certificado getCertificadoPor() {
        return this.certificado.getCertificadoPor();
    }

    @Override
    public void setCertificadoPor(Certificado certificado) {
        this.certificado.setCertificadoPor(certificado);
    }

    public Certificado getCertificado() {
        return certificado;
    }

    public void setCertificado(Certificado certificado) {
        this.certificado = certificado;
    }

}
