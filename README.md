# SpringBootJWT
# Desarrollar un microservicio, cuyo proyecto debe ser SpringBoot 2.5.14 / Gradle hasta 7.4, para la creación y consulta de usuarios.

# Para probar aplicación Spring Boot, puedes seguir estos pasos:

# para obtener el proyecto lo bajas de git rama llamada TEST3IT git clone https://github.com/LGaedo/SpringBootJWT.git

lo dejas en carpeta o workspace deseado

# PARA VER LA BASE DE DATOS H2 (embebida)
http://localhost:8080/h2-console/login.jsp

ver credenciales db en application.properties

# Compilación y Construcción:

Ejecuta el siguiente comando en la terminal desde la raíz de tu proyecto:

./gradlew build
Este comando compilará tu proyecto y ejecutará las pruebas si las tienes configuradas.

Ejecución de la Aplicación:

Puedes ejecutar la aplicación Spring Boot utilizando el siguiente comando en la terminal:

./gradlew bootRun
Esto iniciará tu aplicación en el servidor embebido Tomcat.

# por ide intellij

levantar proyecto y dar click a btn RUN


# probar endpoint sign-up:
curl -X POST -H "Content-Type: application/json" -d '{
"name": "NombreUsuario",
"email": "usuario@example.com",
"password": "Contraseñ1a2",
"phones": [
{
"number": 123456789,
"citycode": 123,
"countrycode": "US"
}
]
}' http://localhost:8080/sign-up


response ejemplo:
{
"id": 1,
"created": "2023-11-19T20:20:14.135",
"lastLogin": "2023-11-19T20:20:14.135",
"token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c3VhcmlvQGV4YW1wbGUuY29tIiwiZXhwIjoxNzAwNTIyNDE0LCJpYXQiOjE3MDA0MzYwMTR9.ygl-WoT0HfT6EcYvqTpiZdm0tpGy6CRlQQ9OGqN5XMz8VCZKYpNM4a29Wq2qf2n1cKfbCSVRl-hDUWhXdBw7pw",
"name": "NombreUsuario",
"email": "usuario@example.com",
"phones": [
{
"id": null,
"number": 123456789,
"citycode": 123,
"countrycode": "US"
}
],
"active": true
} 

# probar endpoint Login
aca pegamos el token anterior o desde la base de datos.
curl -X GET -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE3MDA3OTM0NDMsImlhdCI6MTcwMDcwNzA0M30.ycwcbHJjHPnhLDnUOEhnP6EtQuIYR6dEfg20SEZfrbjbJj2joxvyCqUm2spsjbgg0mAMRUM2TMnM_44e1-de-Q" http://localhost:8080/login

# El ideal es usar Postman, porque por ejemplo probe el endpoint y tenia problemas con las comillas al usar curl, en cambio en postman no pasa ese problema

# Test
le agregue jacoco para ver la cobertura como extra
En IntelliJ IDEA:

Abre la pestaña "Gradle" en el lado derecho de IntelliJ IDEA.
Encuentra tu proyecto en la sección "Gradle Projects".
Dentro de tu proyecto, expande "Tasks" y luego "verification".
Haz doble clic en la tarea llamada "jacocoTestReport".\

ve el reporte en build/reports/jacoco/test/html o en mi caso
/Users/nombrexxx/IdeaProjects/BCIUserMicroservice/build/reports/tests/test/index.html

la otra manera es por consola, ir a la raiz y ejecutar este comando ./gradlew clean test
