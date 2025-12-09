# [🧾 Prueba Técnica Spring Boot](https://www.youtube.com/watch?v=l-Bl45I6UEY)

## API REST para la gestión de ventas en una cadena de supermercados

### 🎯 Objetivo
El objetivo de esta prueba es evaluar conocimiento en **Java + Spring Boot**, incluyendo el desarrollo de una **API RESTful** completa que implemente operaciones CRUD con JPA, relaciones entre entidades, control de errores y excepciones, uso de DTOs, buenas prácticas REST y programación funcional (uso de lambdas y streams) donde aplique.

### 📘 Descripción del caso
Una reconocida cadena de supermercados desea digitalizar su sistema de control de ventas. Para ello necesita una API que permita (de forma básica):

- Registrar productos con sus respectivos precios
- Gestionar las sucursales donde se venden los productos
- Registrar ventas realizadas en una sucursal, especificando los productos vendidos y cantidades.
  
La empresa desea consultar luego las ventas por sucursal, totalizar ingresos, filtrar productos más vendidos, etc.

### 📚 Entidades principales
- **Sucursal**: representa una ubicación fisica del supermercado (una por cada ubicación).
- **Producto**: representa un artículo que puede venderse (ejemplo arroz, botella de agua, etc)
- **Venta**: contiene una o más líneas de productos, asociadas a una sucursal.

**Relaciones**:
- Una **Sucursal** puede tener **muchas ventas**.
- Una **Venta** tiene **muchos productos asociados.**
- Un mismo **Producto** puede estar en **muchas ventas**.


### ✅ Requisitos técnicos
- Utilizar **Spring Boot con** JPA para manejo de bases de datos
- Base de datos relacional (por ejemplo H2 o Mysql)
- Exponer endpoints RESTful para realizar CRUDS (GET, POST, PUT, DELETE o los métodos que se consideren necesarios).
- Utilizar **DTOs** para separar modelo de dominio y representación externa.
- Manejo adecuado de errores con **ResponseEntity**, códigos HTTP correctos (status code) y mensajes claros.
- Uso de **lambdas o streams** en al menos **una operación del backend**.
- Organización modular del proyecto (service, repository, controller).


### 🗒️ Historias de usuario (Requerimientos funcionales)

#### Productos

1. Obtener listado de productos
   - Método: **GET**
   - Path: **/api/productos**
   - Descripción: Listar todos los productos registrados
2. Registrar un nuevo producto
   - Método: **POST**
   - Path: **/api/productos**
   - Descripción: Crear un nuevo producto con nombre, precio y categoría
3. Actualizar producto existente
   - Método: **PUT**
   - Path: **/api/productos/{id}**
   - Descripción: Modificar los datos de un producto específico.
4. Eliminar un producto
   - Método: **DELETE**
   - Path: **/api/productos/{id}**
   - Descripción: Eliminar un producto del sistema.
  
#### Sucursales
1. Obtener listado de sucursales
   - Método: **GET**
   - Path: **/api/sucursales**
   - Descripción: Listar todas las sucursales del sistema.
2. Registrar nueva sucursal
   - Método: **POST**
   - Path: **/api/sucursales**
   - Descripción: Crear una nueva sucursal con dirección, nombre, etc
3. Actualizar sucursal existente
   - Método: **PUT**
   - Path: **/api/sucursales/{id}**
   - Descripción: Modificar los datos de una sucursal específica.
4. Eliminar una sucursal
   - Método: **DELETE**
   - Path: **/api/sucursales/{id}**
   - Descripción: Eliminar una sucursal del sistema

#### Ventas
1. Registrar nueva venta
   - Método: **POST**
   - Path: **/api/ventas**
   - Payload:
   {
    "sucursalId": 1,
    "detalle": [
      {"productoId": 10, "cantidad": 2},
      {"productoId": 5, "cantidad": 1},
    ]
   } 
   - Descripción: Crear una nueva venta para una sucursal con productos y cantidades.
2. Obtener ventas por sucursal y fecha
   - Método: **GET**
   - Path: **/api/ventas?sucursalId=1&fecha=2025-06-01**
   - Descripción: Listar ventas realizadas en una fecha especifica para una sucursal.
3. Eliminar/Anular una venta
   - Método: **DELETE**
   - Path: **/api/ventas/{id}**
   - Descripción: Eliminar una venta registrada
   - Se valorará uso de borrado lógico.

Las ventas NO SE PUEDEN MODIFICAR sin permisos de superusuario (no es necesario implementar esto).

### EXTRA - Estadísticas (opcional no obligatorio)
1. Obtener producto más vendido
   - Método: **GET**
   - Path: **/api/estadisticas/producto-mas-vendido**
   - Descripción: Calcular el producto más vendido utilizando Java Streams.
  

# [🚢 Dockerizar Prueba Técnica Spring Boot](https://www.youtube.com/watch?v=aaTWiVD8mro)

## 🚀 Explicación de las Optimizaciones y Fases

### 1. Fases del Build (Multi-stage Build)

- `builder` **(Fase 1)**: Usa la imagen JDK completa (`...-jdk-...`) porque es necesaria para compilar y ejecutar los tests con Maven.

  - **Cache de dependencias**: Copiar `pom.xml` y ejecutar `mvn dependency:go-offline` por separado asegura que si solo cambias el código de tu aplicación, Docker no tiene que descargar todas las dependencias de Maven de nuevo.

  - **Tests**: Al ejecutar `mvn package -DskipTests=false`, te aseguras de que la suite de tests unitarios y de integración se ejecute antes de generar el JAR final. Si fallan, el *build* fallará.

- `layers` **(Fase 2)**: Esta es la clave de la optimización de Spring Boot.

  - Usa java `-Djarmode=layertools -jar app.jar extract` para descomponer el JAR ejecutable de Spring Boot en capas lógicas: `dependencies`, `spring-boot-loader`, `snapshot-dependencies`, y `application`.

- *`runner` **(Fase 3)**: Es la imagen de producción final y más pequeña.

  - Usa la imagen **JRE** (`...-jre-...`) sin el compilador, lo que reduce drásticamente el tamaño final del contenedor (Principio de **Least Privilege**).

  - **Cache de Capas**: Copiar las capas de Spring Boot en el **orden específico** (`dependencies` primero) aprovecha al máximo el **cache de capas de Docker**. Si solo cambias el código de la aplicación (la capa `application`), solo esa capa debe ser reconstruida, no todas las dependencias.

### 2. Comandos de Optimización de Java
- `XX:TieredStopAtLevel=1`: Le dice a la **JVM** que compile el código JIT (Just-In-Time) con solo el primer nivel de optimización. Esto reduce el tiempo de **arranque** del *cold start* de Spring Boot a expensas de la máxima optimización a largo plazo, lo cual es ideal para contenedores que se escalan y se reinician con frecuencia.

- `Djava.security.egd=file:/dev/./urandom`: Mejora el rendimiento al acelerar la generación de números aleatorios (importante para sesiones, seguridad, etc.) que a menudo es un cuello de botella en entornos virtuales.

- `Duser.timezone=UTC`: Establece la zona horaria en UTC, lo cual es una buena práctica en contenedores para evitar problemas de localización y asegurar la uniformidad en los logs.