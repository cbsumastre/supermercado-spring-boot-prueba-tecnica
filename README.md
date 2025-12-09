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
  
# 🚢 Dockerfile para Aplicación Spring Boot (Build de Cuatro Etapas)

Este `Dockerfile` implementa un **build multi-stage** (de múltiples etapas) con una estricta **separación de responsabilidades** por fase, optimizando el cache y la lógica de validación.

---

## 🚀 Explicación de las Optimizaciones y Fases

Se utilizan **cuatro fases** clave para garantizar la eficiencia, validación y un tamaño final mínimo.

### 1. Fases del Build (Multi-stage Build) 

- **`builder` (Fase 1: Compilación de Código y Dependencias) 🏗️**
  * **Responsabilidad:** Gestión de dependencias y compilación inicial del código (`mvn compile`).
  * **Cache:** Los pasos de `dependency:go-offline` aseguran que la capa de dependencias de Maven se cachee eficientemente. Si el código fuente cambia, solo se reconstruyen los pasos siguientes, no la descarga de dependencias.

- **`tester` (Fase 2: Ejecución de Tests) ✅**
  * **Responsabilidad Única:** **Validación del código**. Ejecuta `mvn test`. Si algún test falla, el proceso de `docker build` se detiene inmediatamente.
  * **Optimización del Cache:** Al aislar `mvn test` de `mvn package`, Docker puede cachear este paso de manera independiente.

- **`packager` (Fase 3: Creación del JAR) 📦**
  * **Responsabilidad Única:** **Generación del artefacto final**. Esta fase se ejecuta *solo si* la fase `tester` ha pasado.
  * **Optimización:** Utiliza `mvn package -DskipTests=true` para evitar la costosa re-ejecución de los tests que ya han sido validados en la fase anterior.

- **`runner` (Fase 4: Ejecución Final Optimizada) 🏃**
  * **Imagen Base Minimizada:** Utiliza solo el **JRE** (`eclipse-temurin:21-jre-ubi9-minimal`), resultando en la imagen de producción más pequeña y segura posible.
  * **Transferencia:** Solo copia el JAR ejecutable final desde la fase `packager`.
  * **Optimización JVM:** Ver la sección siguiente.

---

### 2. Comandos de Optimización de Java y Memoria

Se utilizan variables de entorno para optimizar el arranque y el consumo de recursos de la JVM dentro del entorno de contenedor (cgroups).

| Variable | Configuración | Propósito |
| :--- | :--- | :--- |
| **`XX:TieredStopAtLevel=1`** | `JAVA_TOOL_OPTIONS` | Le indica a la **JVM** que use solo el primer nivel de optimización del compilador JIT (Just-In-Time). Esto **reduce el tiempo de arranque** (*cold start*). |
| **`Djava.security.egd=file:/dev/./urandom`** | `JAVA_TOOL_OPTIONS` | Acelera la generación de números aleatorios. |
| **`Duser.timezone=Europe/Madrid`** | `JAVA_TOOL_OPTIONS` | Establece la zona horaria por defecto. |
| **`MinRAMPercentage=50.0`** | `JAVA_OPTS` | El tamaño inicial del Heap debe ser el 50% de la memoria total asignada al contenedor. |
| **`MaxRAMPercentage=80.0`** | `JAVA_OPTS` | El tamaño máximo del Heap debe ser el 80% de la memoria total del contenedor, respetando los **límites de memoria del contenedor (cgroups)**. |

> **Nota:** Para que las optimizaciones de `MinRAMPercentage` y `MaxRAMPercentage` sean efectivas, es **obligatorio** establecer límites de memoria explícitos para el contenedor (ej: `memory: 512m`) en el orquestador (Docker Compose, Kubernetes, etc.).