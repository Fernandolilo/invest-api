API- Investimento

o link do h2, banco de dados em memoria.

http://localhost:8000/api/invest/h2-console/

adicionado swager há API. link: http://localhost:8000/api/invest/swagger-ui/index.html#/

Back-end:
Framework: Spring Boot (MVC)
Segurança: Spring Security com autenticação JWT
Documentação: Swagger UI para explorar e testar endpoints
Banco de Dados: H2 em ambiente de desenvolvimento (com console ativo)
Gateway: Spring Cloud Gateway atuando como proxy e camada extra de segurança

Princípios aplicados:
SOLID
Clean Architecture
DDD (Domain-Driven Design) em evolução, visando desacoplamento e escalabilidade

Front-end:
 Framework: Angular
 Estilização: SCSS + Bootstrap
 Integração: Consumo de APIs protegidas com JWT
 Nginx: Configurado como proxy reverso para otimizar entrega e segurança

DevOps e Infraestrutura
CI/CD: Integração contínua configurada via GitHub Actions
Containerização: Docker para padronizar ambientes
Deploy Automatizado: Pipeline configurado para build, testes e publicação
Reverse Proxy: Nginx no front-end para segurança e performance

Funcionalidades Principais
Cadastro e autenticação de usuários
Gestão de contas e transações
Consultas seguras via API documentada no Swagger
Interface intuitiva para interação com os dados
Monitoramento e logs estruturados


