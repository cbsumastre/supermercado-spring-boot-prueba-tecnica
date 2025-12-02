FROM ubuntu:22.04


ENV TZ="Europe/Madrid"
ENV LANG=es_ES.UTF-8
ENV EDITOR="vim"    
ENV LC_CTYPE=en_US.UTF-8
ENV DEBIAN_FRONTEND=noninteractive

RUN apt-get update \
    && apt-get -y install --no-install-recommends  \ 
    && apt-get install -y subversion git-all iputils-ping curl telnet  vim zip unzip wget apt-transport-https gnupg2

# Paso 2: Importa la clave GPG del repositorio de Eclipse Temurin
RUN wget -qO - https://packages.adoptium.net/artifactory/api/gpg/key/public | gpg --dearmor | tee /etc/apt/trusted.gpg.d/adoptium.gpg > /dev/null

# Paso 3: Agrega el repositorio de Eclipse Temurin a las fuentes de APT
RUN echo "deb https://packages.adoptium.net/artifactory/deb/ jammy main" | tee /etc/apt/sources.list.d/adoptium.list

# Paso 4: Actualiza la lista de paquetes e instala el JDK 21
RUN apt-get update && apt-get install -y temurin-21-jdk

ENV JAVA_HOME=/usr/lib/jvm/temurin-21-jdk-amd64

# Limpia los archivos temporales para reducir el tamaño de la imagen
RUN apt-get autoremove -y && apt-get clean && rm -rf /var/lib/apt/lists/*

# Add VS Code user and group
RUN useradd -ms /bin/bash vscode && \
    su vscode -c 'curl -s "https://get.sdkman.io" | bash' && \
    su vscode -c "umask 0002 && source /home/vscode/.sdkman/bin/sdkman-init.sh && sdk install maven '3.6.3'"



# Set workspace directory
WORKDIR /workspace

# Switch to VS Code user
USER vscode

EXPOSE 9090

CMD ["/bin/bash"]