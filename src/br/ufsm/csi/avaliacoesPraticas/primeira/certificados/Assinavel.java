package br.ufsm.csi.avaliacoesPraticas.primeira.certificados;

import java.io.Serializable;

/**
 * Interface que marca um objeto que admite ser assinado.
 */
public interface Assinavel extends Serializable {

    byte[] getAssinatura();

    void setAssinatura(byte[] assinatura);

    Certificado getCertificadoPor();

    void setCertificadoPor(Certificado certificado);

}
