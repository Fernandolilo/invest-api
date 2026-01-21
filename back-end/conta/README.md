"# API- Invest" 

Aplicação de investimentos 

o link do h2, banco de dados em memoria.

http://localhost:8000/api/invest/h2-console/

adicionado swager há API.
link: http://localhost:8000/api/invest/swagger-ui/index.html#/

{
  "client": {
    "nome": "string",
    "cpfOuCnpj": "05207935000167",
    "celular": "string",
    "telefone": "string",
    "email": "string",
    "tipo": "PESSOA_JURIDICA",
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
}

"Adicione uma regra para distinguir entre pessoa física e pessoa jurídica:

Se o usuário quiser salvar uma pessoa física, deverá informar um CPF válido. Caso tente informar um CNPJ, deverá ser lançada uma exceção.

O mesmo vale para pessoa jurídica: deverá ser informado um CNPJ válido. Se for informado um CPF, uma exceção deverá ser lançada."**


para se autenticar deixei um usuario instanciado no bd.

{
  "email": "fernando.nandotaania@hotmail.com",
  "password": "1234"
}

authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmZXJuYW5kb0B3ZWZpdC5jb20uYnIiLCJpYXQiOjE3NDYyMzQ1NTgsImV4cCI6MTc0NjIzNDczOH0.dJIsQPgyYkofgdHsHJiL8h7miyZaDLd4PGxHvGSY8TI 
 cache-control: no-cache,no-store,max-age=0,must-revalidate 
 connection: keep-alive 
 date: Sat,03 May 2025 01:09:18 GMT 
 expires: 0 
 keep-alive: timeout=60 
 pragma: no-cache 
 vary: Origin,Access-Control-Request-Method,Access-Control-Request-Headers 
 x-content-type-options: nosniff 
 x-frame-options: SAMEORIGIN 
 x-xss-protection: 0 
 
 
 
 cobertura de codigo com jacoco, test 
 
 test-tech-wefit/back-end/target/site/jacoco/index.html
 
 
 Ambitente de desenvolvimento:
 
 
 para acesso ao client adminer, usado para o ambiente de desenvolvimento com Docker 
 
 http://localhost:8082/ neste link terá acesso ao client de BD.
 

Motor de Base de dados: PostgreSQL
Servidor: wefit-bd
Nome de utilizador:  root
Senha: root
Base de dados: wefit-db

caso queira testar a api, apensa abri um terminal na raiz da aplicação.

dar um docker-compose up --build 

lembrando que ainda não coloquei a stack de mensageria e monitoramento junto ao compose da aplicação, o compose da aplicação esta apenas para rodas em embiente de desenvolvimento.

para tanto é necessário criar um container para a parte de monitoramento 


version: '3.8'

services:
  # --------------------------
  # RabbitMQ
  # --------------------------
  rabbitmq:
    image: rabbitmq:3.8.3-management
    container_name: rabbitmq
    ports:
      - "5672:5672"       # AMQP
      - "15672:15672"     # Management UI
      - "15692:15692"     # Métricas Prometheus
    environment:
      RABBITMQ_DEFAULT_USER: nando.systempro@hotmail.com
      RABBITMQ_DEFAULT_PASS: Fe281244
      RABBITMQ_ERLANG_COOKIE: secret_pass
    
    restart: unless-stopped
    volumes:
      - D:/rabbitmq/rabbitmq:/var/lib/rabbitmq   # Persistência dos dados

  # --------------------------
  # Prometheus
  # --------------------------
  prometheus:
    image: prom/prometheus:latest
    container_name: prometheus
    ports:
      - "9090:9090"
    volumes:
      - D:/test-tech-wefit/back-end/gateway/prometheus.yml:/etc/prometheus/prometheus.yml:ro
    depends_on:
      - rabbitmq
    restart: unless-stopped

  # --------------------------
  # Grafana
  # --------------------------
  grafana:
    image: grafana/grafana:latest
    container_name: grafana
    ports:
      - "3000:3000"
    environment:
      GF_SECURITY_ADMIN_USER: admin
      GF_SECURITY_ADMIN_PASSWORD: admin
      GF_DASHBOARDS_JSON_ENABLED: "true"
    volumes:
      - D:/grafana/provisioning/dashboards:/etc/grafana/provisioning/dashboards
      - D:/grafana/dashboards:/var/lib/grafana/dashboards
    depends_on:
      - prometheus
    restart: unless-stopped


 