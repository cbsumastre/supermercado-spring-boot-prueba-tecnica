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

# --- FASE 2: RUNNER (Ejecución final) ---
FROM eclipse-temurin:21-jre-ubi9-minimal AS runner

# Establece el directorio de trabajo
WORKDIR /app

# Copia el JAR generado de la fase builder
ARG JAR_FILE=target/*.jar
COPY --from=builder /app/${JAR_FILE} app_prueba_tecnica_super.jar

# Puerto que expone la aplicación
EXPOSE 9091

ENTRYPOINT ["java","-Duser.timezone=Europe/Madrid","-jar", "-XX:TieredStopAtLevel=1", "-Djava.security.egd=file:/dev/./urandom", "app_prueba_tecnica_super.jar"]

