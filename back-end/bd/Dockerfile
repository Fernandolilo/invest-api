# Base da imagem oficial do PostgreSQL
FROM postgres:15

# Define o fuso horário
ENV TZ=America/Sao_Paulo

# Define o diretório de dados do PostgreSQL
ENV PGDATA=/data/postgre

# Cria o diretório de dados
RUN mkdir -p /data/postgre && chown -R postgres:postgres /data/postgre

# Expõe a porta padrão do PostgreSQL
EXPOSE 5432
