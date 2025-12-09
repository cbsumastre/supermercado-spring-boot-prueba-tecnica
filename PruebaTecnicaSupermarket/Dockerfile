# --- FASE 1: BUILDER (Compilación de Código y Dependencias) 🏗️ ---
FROM maven:3.9.11-amazoncorretto-25-debian-trixie AS builder

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia los archivos de configuración de Maven (pom.xml)
COPY pom.xml .

# Descarga todas las dependencias para cachearlas
RUN --mount=type=cache,target=/root/.m2 mvn dependency:go-offline -B

# Copia el código fuente del proyecto
COPY src ./src

# Solo compila.
RUN --mount=type=cache,target=/root/.m2 mvn compile

# --- FASE 2: TESTER (Ejecución de Tests) ✅ ---
FROM builder AS tester

# Ejecuta los tests unitarios y de integración. Si fallan, el build se detiene aquí.
# Esta fase tiene una única responsabilidad: validar el código.
RUN --mount=type=cache,target=/root/.m2 mvn test

# --- FASE 3: PACKAGER (Creación del JAR) 📦 ---
# Esta fase hereda la imagen de 'tester' (que incluye el código validado)
FROM tester AS packager

# Empaquetado final (genera el JAR). 
# Se utiliza -DskipTests=true para evitar re-ejecutar los tests.
RUN --mount=type=cache,target=/root/.m2 mvn package -DskipTests=true

# El JAR resultante estará en target/nombre_del_jar.jar

# --- FASE 4: RUNNER (Ejecución final) 🏃 ---
FROM eclipse-temurin:21-jre-ubi9-minimal AS runner

# Establece el directorio de trabajo
WORKDIR /app

# 🎯 OPTIMIZACIÓN DE MEMORIA DEL CONTENEDOR (Junto con las optimizaciones JVM existentes)
ENV JAVA_TOOL_OPTIONS="-XX:TieredStopAtLevel=1 -Duser.timezone=Europe/Madrid -Djava.security.egd=file:/dev/./urandom"
ENV JAVA_OPTS="-XX:MinRAMPercentage=50.0 -XX:MaxRAMPercentage=80.0"

# Copia el JAR generado de la fase packager
ARG JAR_FILE=target/*.jar
COPY --from=packager /app/${JAR_FILE} app_prueba_tecnica_super.jar

# Puerto que expone la aplicación
EXPOSE 9091

ENTRYPOINT java $JAVA_TOOL_OPTIONS $JAVA_OPTS -jar app_prueba_tecnica_super.jar
#ENTRYPOINT ["/bin/sh", "-c", "java $JAVA_TOOL_OPTIONS $JAVA_OPTS -jar app_prueba_tecnica_super.jar"]

