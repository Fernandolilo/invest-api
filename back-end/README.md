"# test-tech-wefit" 

Teste tecnico para a empresa Webfit, neste teste faremos um endpoint para cadastro de pessoa, preciso saber se vender ou compra?

será um prazer imenso atuar convosco.


apos finalizar entidades, inciamos a parte de persistencia a dados, 

o link do h2, banco de dados em memoria.

http://localhost:8000/api/webfit/h2-console/

adicionado swager há API.
link: http://localhost:8000/api/webfit/swagger-ui/index.html#/

````{
  "client": {
    "nome": "string",
    "cpfOuCnpj": "05207935000167",
    "celular": "string",
    "telefone": "string",
    "email": "string",
    "tipo": "PESSOA_JURIDICA",
    "perfil": "COMPRADOR",
    "senha": "string",
    "confirme": true
  },
  "endereco": {
    "cep": "string",
    "logradouro": "string",
    "numero": "string",
    "complemento": "string",
    "bairro": "string",
    "cidade": "string",
    "estado": "string"
  }
}````

"Adicione uma regra para distinguir entre pessoa física e pessoa jurídica:

Se o usuário quiser salvar uma pessoa física, deverá informar um CPF válido. Caso tente informar um CNPJ, deverá ser lançada uma exceção.

O mesmo vale para pessoa jurídica: deverá ser informado um CNPJ válido. Se for informado um CPF, uma exceção deverá ser lançada."**
