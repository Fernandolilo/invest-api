Este é um test tecnico para uma vaga de desenvolvedor Java Junior

"# test-tech-wefit"

Teste tecnico para a empresa Webfit, neste teste faremos um endpoint para cadastro de pessoa, preciso saber se vender ou compra?

será um prazer imenso atuar convosco.

apos finalizar entidades, inciamos a parte de persistencia a dados,

o link do h2, banco de dados em memoria.

http://localhost:8000/api/wefit/h2-console/

adicionado swager há API. link: http://localhost:8000/api/wefit/swagger-ui/index.html#/

{ "client": { "nome": "string", "cpfOuCnpj": "05207935000167", "celular": "string", "telefone": "string", "email": "string", "tipo": "PESSOA_JURIDICA", "perfil": "COMPRADOR", "senha": "string", "confirme": true }, "endereco": { "cep": "string", "logradouro": "string", "numero": "string", "complemento": "string", "bairro": "string", "cidade": "string", "estado": "string" } }

"Adicione uma regra para distinguir entre pessoa física e pessoa jurídica:

Se o usuário quiser salvar uma pessoa física, deverá informar um CPF válido. Caso tente informar um CNPJ, deverá ser lançada uma exceção.

O mesmo vale para pessoa jurídica: deverá ser informado um CNPJ válido. Se for informado um CPF, uma exceção deverá ser lançada."**

para se autenticar deixei um usuario instanciado no bd.

{ "email": "fernando@wefit.com.br", "password": "1234" }

authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmZXJuYW5kb0B3ZWZpdC5jb20uYnIiLCJpYXQiOjE3NDYyMzQ1NTgsImV4cCI6MTc0NjIzNDczOH0.dJIsQPgyYkofgdHsHJiL8h7miyZaDLd4PGxHvGSY8TI cache-control: no-cache,no-store,max-age=0,must-revalidate connection: keep-alive date: Sat,03 May 2025 01:09:18 GMT expires: 0 keep-alive: timeout=60 pragma: no-cache vary: Origin,Access-Control-Request-Method,Access-Control-Request-Headers x-content-type-options: nosniff x-frame-options: SAMEORIGIN x-xss-protection: 0

cobertura de codigo com jacoco, test

test-tech-wefit/back-end/target/site/jacoco/index.html

Ambitente de desenvolvimento:

para acesso ao client adminer, usado para o ambiente de desenvolvimento com Docker

http://localhost:8082/ neste link terá acesso ao client de BD.

Motor de Base de dados: PostgreSQL Servidor: wefit-bd Nome de utilizador: root Senha: root Base de dados: wefit-db

caso queira testar a api, apensa abri um terminal na raiz da aplicação.

dar um docker-compose up --build
