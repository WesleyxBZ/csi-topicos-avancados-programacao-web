package br.ufsm.csi.avaliacoesPraticas.primeira.impl;


import br.ufsm.csi.certificados.AutoridadeCertificadora;
import br.ufsm.csi.certificados.Certificado;
import br.ufsm.csi.certificados.CertificadoPrivado;
import br.ufsm.csi.certificados.Util;

import javax.crypto.Cipher;
import java.util.Arrays;
import java.util.List;

public class AutoridadeCertificadoraImpl implements AutoridadeCertificadora {

    private CertificadoPrivado certificadoPrivado;
    private List<Certificado> certificadosRevogados;

    public AutoridadeCertificadoraImpl(CertificadoPrivado certificadoPrivado) {
        this.certificadoPrivado = certificadoPrivado;
    }

    @Override
    public void assinaCertificado(Certificado certificado) {
        try {
            byte[] hash = Util.getHash(certificado);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, this.certificadoPrivado.getChavePrivada());
            byte[] chaveCript = cipher.doFinal(hash);
            certificado.setAssinatura(chaveCript);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean verificaAssinaturaCertificado(Certificado certificado) {
        try {
            byte[] hash = Util.getHash(certificado);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, certificado.getCertificadoPor().getChavePublica());
            byte[] assinaturaDecript = cipher.doFinal(certificado.getAssinatura());
            return Arrays.equals(hash, assinaturaDecript);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean estaRevogado(Certificado certificado) {
        return this.certificadosRevogados.contains(certificado);
    }

    @Override
    public void revogaCertificado(Certificado certificado) {
        this.certificadosRevogados.add(certificado);
    }

    @Override
    public Certificado getCertificado() {
        return this.certificadoPrivado.getCertificado();
    }

}
