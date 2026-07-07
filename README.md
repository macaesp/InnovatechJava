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

`docker-compose.yml` referencia las imágenes vía `${ECR_REGISTRY}` (la misma
variable que usa el pipeline de CI/CD), así que para levantarlo en local hay
que exportarla apuntando a un registro accesible, por ejemplo:

```bash
export ECR_REGISTRY=<tu_cuenta>.dkr.ecr.us-east-1.amazonaws.com
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

1. **Build** (job `build`) → compila el proyecto con Maven, corre las pruebas,
   construye las 4 imágenes Docker y las sube a AWS ECR.
2. **Deploy** (job `deploy`, depende de `build`) → copia el `docker-compose.yml`
   al nodo manager del clúster de Docker Swarm por SSH, hace `docker pull` de
   las imágenes recién publicadas y ejecuta
   `docker stack deploy -c docker-compose.yml innovatech --with-registry-auth`.

Es decir, el pipeline ya no termina en el `docker push`: cada push a
`main`/`master` que pasa el build se despliega automáticamente en el clúster.

### Secrets requeridos en GitHub

| Secret | Descripción |
|--------|-------------|
| `AWS_ACCESS_KEY_ID` | Clave de acceso IAM |
| `AWS_SECRET_ACCESS_KEY` | Clave secreta IAM |
| `AWS_SESSION_TOKEN` | Token de sesión (si usas credenciales temporales) |
| `AWS_REGION` | Región AWS (ej: us-east-1) |
| `EC2_HOST` | IP o DNS del nodo manager del clúster Swarm |
| `EC2_USER` | Usuario SSH del nodo manager (ej: `ec2-user`) |
| `EC2_SSH_KEY` | Clave privada SSH para conectarse al nodo manager |

> El nodo manager debe tener `/opt/innovatech` creado y el CLI de AWS
> configurado (o un rol de IAM adjunto a la instancia) para poder hacer
> `aws ecr get-login-password`.

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

cat response.json
```

### Integración con el flujo de negocio

`msvc-proyecto` invoca esta Lambda automáticamente (invocación asíncrona,
`InvocationType=Event`) desde `NotificacionService` cada vez que se llama a:

```
PATCH /api/v1/proyectos/{id}/estado?estado=<NUEVO_ESTADO>
```

Es decir, el cambio de estado de un proyecto es lo que dispara la
notificación real hacia SQS — ya no es necesario invocar la función a mano
con un evento de prueba para demostrarla end-to-end.

Variables de entorno relevantes en `msvc-proyecto`:

| Variable | Descripción | Default |
|---|---|---|
| `AWS_REGION` | Región donde vive la Lambda | `us-east-1` |
| `LAMBDA_NOTIFICACION_FUNCTION` | Nombre de la función Lambda | `innovatech-notificacion-proyecto` |
| `LAMBDA_NOTIFICACION_HABILITADA` | Permite apagar la notificación (útil en tests locales) | `true` |