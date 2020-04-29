### Enunciado 

Garantia de confidencialidade e integridade

Alice:
* Gera o par de chaves RSA (integridade)
* Conectar no Bob e receber a chave pública de Bob
* Gerar a chave AES (chave de sessão)
* Criptografar o arquivo com a chave AES
* Criptografar a chave AES com a chave públic Bob
* Empacotar num ObjetoTroca toda a informação que vai ao Bob (arquivo criptografado, chave sessão critografada, 
  chave pública Alice) e converter para byte[]
* Gerar o hash SHA-256 do objeto byte[]
* Criptografar o resumo/hash com a chave privada Alice (assinatura)
* Colocar a assinatura junto no ObjetoTroca
* Enviar o ObjetoTroca ao Bob.

Bob (recebe o arquivo):
* Gera o par de chaves RSA (confidencialidade)
* Recebe a conexão da Alice e envia sua chave pública
* Recebe o ObjetoTroca
* Retira a assinatura do ObjetoTroca e descriptografa com a chave pública da Alice (que veio junto)
* Converte o ObjetoTroca para byte[] e gera o hash SHA-256 deste byte[]
* Comparar o hash gerado por Bob com a assinatura descriptografada (se forem diferentes, termina o programa)
* Descriptografa a chave de sessão com a chave privada do Bob
* Descriptografa o arquivo com a chave de sessão
* Salvar o arquivo
