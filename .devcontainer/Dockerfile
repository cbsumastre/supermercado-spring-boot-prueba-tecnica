FROM ubuntu:24.04


ENV TZ="Europe/Madrid"
ENV LANG=es_ES.UTF-8
ENV EDITOR="vim"    
ENV LC_CTYPE=en_US.UTF-8
ENV DEBIAN_FRONTEND=noninteractive

RUN apt-get update \
    && apt-get -y install --no-install-recommends  \ 
    && apt-get install -y subversion git-all iputils-ping curl telnet  vim zip unzip wget apt-transport-https gnupg2 ca-certificates lsb-release

# Paso 2: Importa la clave GPG del repositorio de Eclipse Temurin
RUN wget -qO - https://packages.adoptium.net/artifactory/api/gpg/key/public | gpg --dearmor | tee /etc/apt/trusted.gpg.d/adoptium.gpg > /dev/null

# Paso 3: Agrega el repositorio de Eclipse Temurin a las fuentes de APT
RUN echo "deb https://packages.adoptium.net/artifactory/deb/ jammy main" | tee /etc/apt/sources.list.d/adoptium.list

# Paso 4: Actualiza la lista de paquetes e instala el JDK 21
RUN apt-get update && apt-get install -y temurin-21-jdk
ENV JAVA_HOME=/usr/lib/jvm/temurin-21-jdk-amd64

# Añadir la clave GPG oficial de Docker
RUN install -m 0755 -d /etc/apt/keyrings && \
    curl -fsSL https://download.docker.com/linux/ubuntu/gpg | tee /etc/apt/keyrings/docker.asc > /dev/null && \
    chmod a+r /etc/apt/keyrings/docker.asc


# Añadir el repositorio oficial de Docker
RUN echo "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.asc] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable" | tee /etc/apt/sources.list.d/docker.list > /dev/null

# Actualizar los paquetes de nuevo después de añadir el repositorio
RUN apt-get update

# Instalar los paquetes de Docker Engine
RUN apt-get install -y docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin    


# Limpia los archivos temporales para reducir el tamaño de la imagen
RUN apt-get autoremove -y && apt-get clean && rm -rf /var/lib/apt/lists/*

# Add VS Code user and group
RUN useradd -ms /bin/bash vscode && \
    su vscode -c 'curl -s "https://get.sdkman.io" | bash' && \
    su vscode -c "umask 0002 && source /home/vscode/.sdkman/bin/sdkman-init.sh && sdk install maven '3.6.3'"

RUN usermod -aG docker vscode


# Set workspace directory
WORKDIR /workspace

# Switch to VS Code user
USER vscode

EXPOSE 9090

CMD ["/bin/bash"]