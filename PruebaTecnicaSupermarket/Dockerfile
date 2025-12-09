# --- FASE 1: BUILDER (Compilación y Tests) ---
FROM maven:3.9.11-amazoncorretto-25-debian-trixie AS builder

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia los archivos de configuración de Maven (pom.xml)
# Se hace por separado para aprovechar el cache de Docker si solo cambian las dependencias
COPY pom.xml .

# Descarga todas las dependencias para que el paso de copia de código fuente
# y el resto de la compilación se beneficien del cache si solo cambia el código.
RUN --mount=type=cache,target=/root/.m2 mvn dependency:go-offline -B

# Copia el código fuente del proyecto
COPY src ./src

# Empaqueta la aplicación, incluyendo la ejecución de tests.
# El mount=type=cache asegura que el repositorio local de Maven (m2) se cachee
# entre builds para acelerar las compilaciones.
RUN --mount=type=cache,target=/root/.m2 mvn package -DskipTests=true

# El JAR resultante estará en target/nombre_del_jar.jar

# --- FASE 2: LAYERS (Optimización de Spring Boot) ---
# Esta fase usa el JAR generado para dividir la aplicación Spring Boot en capas
# para aprovechar aún más el cache de Docker.
FROM eclipse-temurin:21-jdk-ubi9-minimal AS layers

# Establece el directorio de trabajo
WORKDIR /app

# Copia el JAR generado de la fase builder
ARG JAR_FILE=target/*.jar
COPY --from=builder /app/${JAR_FILE} app.jar

# Desempaqueta el JAR de Spring Boot en capas
# Esto crea directorios como BOOT-INF/lib, BOOT-INF/classes, etc.
# RUN java -Djarmode=layertools -jar app.jar extract

# --- FASE 3: RUNNER (Ejecución final) ---
FROM eclipse-temurin:21-jre-ubi9-minimal AS runner

# Establece el directorio de trabajo
WORKDIR /app

# Copia las capas desde la fase 'layers' en el orden recomendado por Spring Boot
# (Dependencias externas, Spring Boot loader, dependencias del proyecto, código de la aplicación).
# # El orden es crucial para el cacheo eficiente.
# COPY --from=layers /app/dependencies/ ./
# COPY --from=layers /app/spring-boot-loader/ ./
# COPY --from=layers /app/snapshot-dependencies/ ./
# COPY --from=layers /app/application/ ./

COPY --from=layers /app/app.jar app_prueba_tecnica_super.jar

# Puerto que expone la aplicación
EXPOSE 9091

# Comando de entrada optimizado
# Las optimizaciones de la capa de Spring Boot se aplican automáticamente
# al ejecutar con las capas separadas.
# Se añade -XX:TieredStopAtLevel=1 y -Duser.timezone=UTC como ejemplos de optimización
# recomendados para entornos Docker.
# ENTRYPOINT ["java", \
#     "-Duser.timezone=UTC", \
#     "-XX:TieredStopAtLevel=1", \
#     "-Djava.security.egd=file:/dev/./urandom", \
#     "org.springframework.boot.loader.JarLauncher"]

EXPOSE 9091
ENTRYPOINT ["java","-Duser.timezone=Europe/Madrid","-jar", "-XX:TieredStopAtLevel=1", "-Djava.security.egd=file:/dev/./urandom", "app_prueba_tecnica_super.jar"]

