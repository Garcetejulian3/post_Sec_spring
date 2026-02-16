# 🚀 Post_Sec_Spring

API REST desarrollada en **Java con Spring Boot**, enfocada en la gestión de usuarios, perfiles, roles y publicaciones (posts), implementando un sistema de **seguridad stateless mediante JWT**.

El proyecto demuestra la integración de:

- Spring Boot
- Spring Security
- JWT (JSON Web Tokens)
- JPA / Hibernate
- Arquitectura en capas (Controller – Service – Repository – DTO)

---

## 📌 Descripción del Proyecto

**Post_Sec_Spring** es un backend que permite:

✔ Registro y autenticación de usuarios  
✔ Manejo de roles y permisos  
✔ Creación y asociación de perfiles  
✔ CRUD de publicaciones  
✔ Validaciones de seguridad por propietario  

La API está diseñada siguiendo buenas prácticas de separación de responsabilidades y seguridad moderna.

---

## 🔐 Seguridad

La aplicación utiliza **Spring Security + JWT**, implementando:

- Autenticación stateless
- Validación de token en cada request
- Control de acceso por roles
- Protección de endpoints

El flujo es:

1. Usuario inicia sesión
2. Se genera un JWT
3. El cliente envía el token en cada request
4. Un filtro valida el token antes de ejecutar la lógica

---

## 👥 Gestión de Usuarios y Perfiles

El sistema separa correctamente:

### **UserSec (Seguridad)**
Contiene:

- Username
- Password encriptada
- Flags de cuenta
- Roles / Authorities

### **UserProfile (Datos de negocio)**
Contiene:

- Información del perfil
- Asociación con `UserSec`

### ✅ Flujo de Registro

Durante el registro:

1. Se crea la entidad **UserSec**
2. Se asignan roles desde la base de datos
3. Se persiste el usuario
4. Se crea el **UserProfile**
5. Se asocia al usuario recién creado

👉 Esta separación permite mantener limpia la arquitectura y escalar fácilmente.

---

## 📝 Gestión de Posts

La API permite:

✔ Crear publicaciones  
✔ Listar publicaciones  
✔ Buscar por ID  
✔ Editar publicaciones  

Cada post está asociado a un **UserProfile (autor)**.

---

## 👤 Obtención del Usuario Autenticado

Para evitar depender del frontend, el backend obtiene el usuario desde el contexto de seguridad:

```java
Authentication auth = SecurityContextHolder.getContext().getAuthentication();
String username = auth.getName();
