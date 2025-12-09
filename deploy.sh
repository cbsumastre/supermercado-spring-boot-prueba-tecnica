#!/bin/sh
echo "cd PruebaTecnicaSupermarket"
cd PruebaTecnicaSupermarket
echo "rm -rf target"
rm -rf target
echo "mvn clean install -DskipTests=true"
mvn clean install -DskipTests=true

echo "cd .."
cd ..

echo  "docker compose up --build --detach"
docker compose up --build --detach

