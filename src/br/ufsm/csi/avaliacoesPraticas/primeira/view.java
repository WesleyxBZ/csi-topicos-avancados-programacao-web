package br.ufsm.csi.avaliacoesPraticas.primeira;

import br.ufsm.csi.certificados.Certificado;
import br.ufsm.csi.certificados.CertificadoPrivado;
import br.ufsm.csi.certificados.DocumentoAssinado;
import br.ufsm.csi.impl.AutoridadeCertificadoraImpl;

import java.time.LocalDate;

public class view {

    public static void main(String[] args) {
        try {

            LocalDate today = LocalDate.now();

            CertificadoPrivado certificado1 = new CertificadoPrivado(
                    "CA Raiz",
                    "11111111111",
                    "caraiz@email",
                    "192.168.001",
                    Certificado.TipoCertificado.CA_RAIZ
            );
            certificado1.getCertificado().setValidade(today);

            AutoridadeCertificadoraImpl caRaiz = new AutoridadeCertificadoraImpl(certificado1);

            CertificadoPrivado certificado2 = new CertificadoPrivado(
                    "CA",
                    "2222222222",
                    "ca@email",
                    "192.168.002",
                    Certificado.TipoCertificado.CA
            );
            certificado2.getCertificado().setAutoridadeCertificadora(caRaiz);
            certificado2.getCertificado().setCertificadoPor(caRaiz.getCertificado());
            certificado2.getCertificado().setValidade(today.plusYears(1));

            caRaiz.assinaCertificado(certificado2.getCertificado());

            CertificadoPrivado.saveCertificado(certificado2);
            System.out.print("Cpf/Cnpj do certificado recuperado:" + CertificadoPrivado.findCertificado("CA").getCertificado().getCpfCnpj());

            AutoridadeCertificadoraImpl ca = new AutoridadeCertificadoraImpl(certificado2);

            System.out.print("\nCertificado válido: ");
            System.out.print(ca.verificaAssinaturaCertificado(certificado2.getCertificado()));

            DocumentoAssinado doc2 = certificado2.assinaDocumento("teste2".getBytes(), "doc2", "text/plain");
            System.out.print("\nDocumento recuperado: ");
            System.out.print(DocumentoAssinado.findDocumentoo("doc2").getNomeDocumento());

            System.out.print("\nDocumento válido: ");
            System.out.print(doc2.verificaAssinaturaDocumento(doc2));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
