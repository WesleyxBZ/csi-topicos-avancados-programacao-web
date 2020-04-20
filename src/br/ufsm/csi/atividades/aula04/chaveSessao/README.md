### Enunciado  

Implementar o envio de arquivo entre Bob e Alice com garantia de confidencialidade utilizando chave de sessão.

Resumo de execução:
* ALICE conecta no BOB;
* BOB compartilha chave pública;
* ALICE:
  * cria chave de sessão;
  * criptografa arquivo com chave de sessão (AES);
  * criptografa chave AES com chave pública do BOB;
  * manda para o BOB pelo objeto troca;
* BOB desfaz tudo.
