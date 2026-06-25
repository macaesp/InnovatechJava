# 🚀 InnovatechJava

Sistema de gestión de proyectos empresariales desarrollado con arquitectura de microservicios en Java utilizando Spring Boot.

---

## 📌 Descripción

InnovatechJava es una aplicación basada en microservicios que permite gestionar proyectos, recursos y colaboraciones dentro de una organización.

El sistema está compuesto por cuatro microservicios:
- `msvc-autentificacion` → puerto 8080
- `msvc-proyecto` → puerto 8082
- `msvc-recursos` → puerto 8081
- `msvc-colaboracion` → puerto 8083

---

## 🛠️ Tecnologías utilizadas

- Java 17
- Spring Boot
- Spring Data JPA
- H2 Database
- Maven
- Docker & Docker Swarm
- GitHub Actions (CI/CD)
- AWS ECR, EC2
- AWS Lambda

---

## 🐳 Docker

### Construir imágenes localmente

```bash
docker build -t msvc-autentificacion ./msvc-autentificacion
docker build -t msvc-proyecto ./msvc-proyecto
docker build -t msvc-recursos ./msvc-recursos
docker build -t msvc-colaboracion ./msvc-colaboracion
```

### Levantar con Docker Compose

```bash
docker-compose up
```

---

## 🐝 Docker Swarm

### Requisitos
- Docker instalado en todos los nodos
- Puertos 2377, 7946 y 4789 abiertos entre nodos

### Inicializar el clúster

En el nodo **manager**:
```bash
docker swarm init --advertise-addr <IP_DEL_MANAGER>
```

### Obtener tokens para unir nodos

```bash
# Token para workers
docker swarm join-token worker

# Token para managers adicionales
docker swarm join-token manager
```

### Agregar nodos worker

En cada nodo worker:
```bash
docker swarm join --token <TOKEN_WORKER> <IP_DEL_MANAGER>:2377
```

### Agregar nodos manager adicionales

```bash
docker swarm join --token <TOKEN_MANAGER> <IP_DEL_MANAGER>:2377
```

### Verificar nodos del clúster

```bash
docker node ls
```

### Desplegar el stack

```bash
docker stack deploy -c docker-compose.yml innovatech
```

### Verificar servicios

```bash
docker service ls
docker stack ps innovatech
```

### Escalar un servicio

```bash
docker service scale innovatech_autentificacion=3
docker service scale innovatech_proyecto=2
```

### Eliminar el stack

```bash
docker stack rm innovatech
```

---

## ⚙️ CI/CD - GitHub Actions

El pipeline `.github/workflows/ci.yml` automatiza:

1. **Build** → compila el proyecto con Maven
2. **Test** → ejecuta pruebas unitarias
3. **Docker Build & Push** → construye imágenes y las sube a AWS ECR

### Secrets requeridos en GitHub

| Secret | Descripción |
|--------|-------------|
| `AWS_ACCESS_KEY_ID` | Clave de acceso IAM |
| `AWS_SECRET_ACCESS_KEY` | Clave secreta IAM |
| `AWS_REGION` | Región AWS (ej: us-east-1) |

---

## ☁️ Infraestructura Cloud (AWS)

- **AWS EC2** → servidor donde corre Docker Swarm
- **AWS ECR** → registro de imágenes Docker
- **AWS Lambda** → función serverless de notificaciones

---

## 🔔 Función Serverless (AWS Lambda)

Función que notifica automáticamente cuando un proyecto cambia de estado.

### Archivos

- `lambda/lambda_function.py` → código fuente
- `lambda/event-test.json` → evento de prueba

### Despliegue

```bash
cd lambda
Compress-Archive -Path lambda_function.py -DestinationPath function.zip

aws lambda create-function \
  --function-name innovatech-notificacion-proyecto \
  --runtime python3.11 \
  --role arn:aws:iam::<ACCOUNT_ID>:role/LabRole \
  --handler lambda_function.lambda_handler \
  --zip-file fileb://function.zip \
  --region us-east-1
```

### Probar la función

```bash
aws lambda invoke \
  --function-name innovatech-notificacion-proyecto \
  --payload file://event-test.json \
  --region us-east-1 \
  response.json
  hola

cat response.json
```