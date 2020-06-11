package br.ufsm.csi.avaliacoesPraticas.primeira.certificados;

import javax.crypto.Cipher;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;

/**
 * Classe que permite assinar documentos e armazenar/obter a chave privada.
 */
public class CertificadoPrivado implements Serializable {

    private final PrivateKey chavePrivada;
    private final Certificado certificado;

    /**
     * Encontra e recupera um certificado completo (privado e público) a partir de um arquivo do sistema de arquivos.
     *
     * @param nomeArquivo nome do arquivo
     * @return certificado completo (privado e público)
     */
    public static CertificadoPrivado findCertificado(String nomeArquivo) {
        try {
            FileInputStream fileIS = new FileInputStream(Util.basePath + "/certificado/" + nomeArquivo + ".txt");
            ObjectInputStream oIS = new ObjectInputStream(fileIS);
            CertificadoPrivado certificado = (CertificadoPrivado) oIS.readObject();
            oIS.close();
            fileIS.close();
            return certificado;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean saveCertificado(CertificadoPrivado certificadoPrivado) {
        try {
            ByteArrayOutputStream bOut = new ByteArrayOutputStream();
            new ObjectOutputStream(bOut).writeObject(certificadoPrivado);
            byte[] doc = bOut.toByteArray();
            Path pathBase = Paths.get(Util.basePath + "/certificado/");
            Files.createDirectories(pathBase);
            Path path = pathBase.resolve(certificadoPrivado.certificado.getNome() + ".txt");
            Files.write(path, doc);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Cria um certificado completo novo (público e privado) a partir dos dados passados.
     * Este certificado não será válido pois precisa receber a assinatura de uma autoridade
     * certificadora.
     */
    public CertificadoPrivado(String nome,
                              String cpfCnpj,
                              String email,
                              String hostname,
                              Certificado.TipoCertificado tipoCertificado) throws NoSuchAlgorithmException {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(1024);
        KeyPair kp = kpg.generateKeyPair();
        this.certificado = new Certificado(kp.getPublic(), nome, cpfCnpj, email, hostname, tipoCertificado);
        this.chavePrivada = kp.getPrivate();
    }

    /**
     * Cria um documento assinado com base nos dados recebidos.
     *
     * @param dados       dados do documento
     * @param nomeArquivo nome do arquivo deste documento
     * @param mimeType    tipificação do arquivo (RFC 2046)
     * @return o documento assinado
     */
    public DocumentoAssinado assinaDocumento(byte[] dados, String nomeArquivo, String mimeType) {
        try {
            DocumentoAssinado documentoAssinado = new DocumentoAssinado();
            documentoAssinado.setDadosDocumento(dados);
            documentoAssinado.setMimeType(mimeType);
            documentoAssinado.setNomeDocumento(nomeArquivo);

            documentoAssinado.setCertificado(this.certificado);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, this.chavePrivada);
            byte[] assCript = cipher.doFinal(Util.getHash(documentoAssinado));

            documentoAssinado.setAssinatura(assCript);
            DocumentoAssinado.saveDocumento(documentoAssinado);
            return documentoAssinado;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Certificado getCertificado() {
        return this.certificado;
    }

    public PrivateKey getChavePrivada() {
        return chavePrivada;
    }

}
