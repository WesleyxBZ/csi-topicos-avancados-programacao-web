package br.ufsm.csi.avaliacoesPraticas.primeira.certificados;

import java.security.PublicKey;
import java.time.LocalDate;

/**
 * Classe que representa um certificado público
 */
public class Certificado implements Assinavel {

    public enum TipoCertificado {USUARIO_FINAL, CA, CA_RAIZ}

    private PublicKey chavePublica;
    private String nome;
    private String cpfCnpj;
    private String email;
    private String hostname;
    private LocalDate validade;
    private TipoCertificado tipoCertificado;
    private Certificado certificadoPor;
    private byte[] assinatura;
    private AutoridadeCertificadora autoridadeCertificadora;

    /**
     * Constrói um certificado a partir dos dados passados por parâmetro.
     *
     * @param chavePublica
     * @param nome
     * @param cpfCnpj
     * @param email
     * @param hostname
     * @param tipoCertificado
     */
    public Certificado(PublicKey chavePublica,
                       String nome,
                       String cpfCnpj,
                       String email,
                       String hostname,
                       TipoCertificado tipoCertificado) {
        this.chavePublica = chavePublica;
        this.nome = nome;
        this.cpfCnpj = cpfCnpj;
        this.email = email;
        this.hostname = hostname;
        this.tipoCertificado = tipoCertificado;
    }

    /**
     * Verificação de autenticidade deste certificado, onde deve ser verificado: autenticidade da assinatura
     * por parte de quem certificou (CA), data de validade e ausência deste na lista de certificados revogados
     * da CA. Além disto deve verificar também a autenticidade de todos os certificados na cadeia de
     * certificação.
     *
     * @return booleano que representa a autenticidade ou não deste certificado.
     */
    public boolean verificaAutenticidade() {
        if (this.getCertificadoPor() != null) {
            if (this.getCertificadoPor().getValidade().isAfter(LocalDate.now())
                    && autoridadeCertificadora.estaRevogado(this.certificadoPor)
                    && autoridadeCertificadora.verificaAssinaturaCertificado(this)) {
                verificaAutenticidade();
            } else {
                autoridadeCertificadora.revogaCertificado(this);
                return false;
            }
        }
        return true;
    }

    public PublicKey getChavePublica() {
        return chavePublica;
    }

    public void setChavePublica(PublicKey chavePublica) {
        this.chavePublica = chavePublica;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public LocalDate getValidade() {
        return validade;
    }

    public void setValidade(LocalDate validade) {
        this.validade = validade;
    }

    public TipoCertificado getTipoCertificado() {
        return tipoCertificado;
    }

    public void setTipoCertificado(TipoCertificado tipoCertificado) {
        this.tipoCertificado = tipoCertificado;
    }

    public Certificado getCertificadoPor() {
        return certificadoPor;
    }

    public void setCertificadoPor(Certificado certificadoPor) {
        this.certificadoPor = certificadoPor;
    }

    @Override
    public byte[] getAssinatura() {
        return assinatura;
    }

    @Override
    public void setAssinatura(byte[] assinatura) {
        this.assinatura = assinatura;
    }

    public AutoridadeCertificadora getAutoridadeCertificadora() {
        return autoridadeCertificadora;
    }

    public void setAutoridadeCertificadora(AutoridadeCertificadora autoridadeCertificadora) {
        this.autoridadeCertificadora = autoridadeCertificadora;
    }


}
