Invest API

API para gerenciamento de investimentos, construída com Spring Boot no backend e Angular no frontend.
O projeto segue princípios de Clean Architecture, SOLID e DDD, garantindo escalabilidade, manutenção e segurança.

Tecnologias
Backend

Spring Boot
 (MVC, Data JPA, Security)

JWT
 para autenticação

Swagger
 para documentação interativa

Banco de dados H2 (desenvolvimento)

Frontend

Angular

Bootstrap
 + SCSS

Nginx
 como reverse proxy

DevOps

Docker
 para containerização

GitHub Actions
 para CI/CD 

 invest-api/
│── backend/         # API em Spring Boot
│── frontend/        # Aplicação Angular
│── docker/          # Configurações Docker e Nginx
│── docs/            # Documentações e diagramas

Funcionalidades

Cadastro e autenticação de usuários com JWT

CRUD de contas e transações

Gestão de investimentos

Documentação da API via Swagger

Interface web responsiva em Angular

Logs estruturados e monitoramento básico

Como rodar o projeto
Pré-requisitos

Java 17+

Node.js 18+

Docker

Documentação da API

Acesse o Swagger UI para explorar os endpoints:

gateway http://localhost:8765/api/invest/swagger-ui/index.html#
gateway http://localhost:8765/api/rendafixa/swagger-ui/index.html#

o gateway chama ambas as apis.

Roadmap / Melhorias Futuras

 Migrar banco H2 para PostgreSQL

 Adicionar testes unitários e de integração

 Implementar monitoramento com Prometheus/Grafana

 Deploy em ambiente orquestrado (Kubernetes)

 Criar mais endpoints para investimentos


 Autor

Fernando da Silva
📧 fernando.nandotania@hotmail.com

LinkedIn : https://www.linkedin.com/in/fernando-silva-0881b672/
