

STATISTICAL OPERATIONS ROADMAP - Migración de Oracle a PostgreSQL
==============================================================================================================
# Objetivo
Este módulo genera los scripts para migrar los datos presentes en la base de datos Oracle de la aplicación metamac-statistical-operations a la base de datos PostgreSQL de statistica-operations-roadmap.
Los scripts cargan los datos en la base de datos PostgreSQL, pero no crean las tablas ni el resto de estructuras de datos.

# Manual de uso
1. Compilar el proyecto con Maven. Como resultado de la compilación, se generará un jar ejecutable con el nombre `statistical-operations-roadmap-internal-db-migration-[version]-executable.jar`.
2. Ejecutar el jar para generar los scripts. Para ejecutar el jar es necesario ejecutar lo siguiente
` java -jar statistical-operations-roadmap-internal-db-migration-[version]-executable.jar [directorio de generación] [datasource]`
donde:
-- El `directorio de generación` hace referencia al directorio donde se van a generar los scripts
-- Y el `datasource` es un fichero con los datos de conexión a la base de datos Oracle de la aplicación metamac-statistical-operations. El fomato del fichero puede consultarse en `src/main/resources/datasource.properties`.
3. Ejecutar los scripts en la base de datos PostgreSQL de statistical-operations-roadmap. 
