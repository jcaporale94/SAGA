# 🐾 SAGA - Sistema de Adopción y Gestión Animal 🐾 # 

**SAGA** es un prototipo funcional desarrollado en Java con conexión a una base de datos MySQL. 
El sistema permite registrar animales, adoptar mascotas y gestionar la información de los adoptantes mediante una interfaz simple por consola.

---

## 💡 Funcionalidades implementadas

- Conexión a base de datos MySQL mediante JDBC.
- Creación automática de tablas: 'Animal', 'Adoptante' y 'SolicitudAdopcion'.
- Menú interactivo por consola con opciones para:
  - Ver lista de animales.
  - Ver lista de adoptantes.
  - Insertar nuevos animales.
  - Insertar nuevos adoptantes.
  - Cargar datos de ejemplo.
- Validaciones funcionales para evitar errores:
  - Solo letras en campos de texto.
  - Solo números en DNI y teléfono.
  - Email con formato correcto ('@' obligatorio).

---

## 🛠️ Tecnologías usadas:

- Java 17
- MySQL Server 8.0
- IntelliJ IDEA Community Edition
- JDBC Connector ('mysql-connector-j-9.3.0.jar')

---

## 🚀 Cómo ejecutar el sistema:

1. Clonar o descargar el repositorio.
2. Abrir el proyecto en IntelliJ IDEA.
3. Verificar el conector de MySQL agregado como dependencia.
4. Ejecutar el archivo 'Main.java'.
5. Interactuar con el sistema mediante el menú por consola.

📌 Requisitos:
- MySQL instalado y corriendo en 'localhost'.
- Una base de datos llamada 'SAGA' creada previamente. // CREATE DATABASE SAGA;
- Usuario 'root' con contraseña configurada en el archivo 'ConexionDB.java'.


---

## 👩‍💻 Autora

**Joana Caporale**  
Estudiante de Licenciatura en Informática - Universidad Siglo 21  
TP3 - Seminario de Práctica Informática (2025)

---

Proyecto desarrollado con fines educativos.
