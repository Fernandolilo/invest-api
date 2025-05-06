# Build stage
FROM node:22 AS builder

WORKDIR /usr/src/app
COPY . /usr/src/app

RUN npm install
RUN npm run build --prod

# Production stage
FROM nginx:alpine

# Criar diretório para Let's Encrypt antes de iniciar o Nginx
RUN mkdir -p /usr/share/nginx/html/.well-known/acme-challenge/ \
    && chown -R nginx:nginx /usr/share/nginx/html/.well-known/acme-challenge/

# Copiar aplicação Angular para o diretório padrão do NGINX
COPY --from=builder /usr/src/app/dist/app/browser /usr/share/nginx/html

# Copiar configuração do NGINX
COPY nginx.conf /etc/nginx/nginx.conf
COPY default.conf /etc/nginx/conf.d/default.conf

# Expor as portas HTTP e HTTPS
EXPOSE 80 

# Iniciar o Nginx
CMD ["nginx", "-g", "daemon off;"]