# uam_app1 · OpenXava 7.4

![OpenXava](https://img.shields.io/badge/OpenXava-7.4-0099A8?logo=java&logoColor=white) ![Java](https://img.shields.io/badge/Java-1.8-0099A8?logo=openjdk&logoColor=white) ![Maven](https://img.shields.io/badge/Build-Maven-0099A8?logo=apachemaven&logoColor=white) ![IntelliJ](https://img.shields.io/badge/IDE-IntelliJ%20IDEA-0099A8?logo=intellijidea&logoColor=white) ![License](https://img.shields.io/badge/License-MIT-0099A8)

> Aplicación web construida con **OpenXava 7.4**, **Maven** y **IntelliJ IDEA**.  
> Provee módulos CRUD rápidos a partir de entidades JPA, con validaciones, búsqueda, listas, filtros y exportaciones nativas de OpenXava.

---

## 🚀 Funcionalidades principales

- **CRUD automático** para las entidades: `Estudiante, Carrera, TuPrimeraEntidad`.
- **Validaciones** con anotaciones (`@Required`, restricciones JPA).
- **List & Detail**: lista con filtros/ordenamiento y editor de detalle por módulo.
- **Exportación** desde lista (Excel/CSV/PDF) y **acciones** (nuevo, borrar, duplicar, etc.).
- **Navegación** y barra superior provistas por **OpenXava/NaviOX**.
- **Base de datos embebida** para desarrollo (HSQLDB vía `DBServer`) o **JNDI** para su BD real.
- Empaquetado **WAR** listo para Tomcat/Jakarta EE.

> **Nota:** La estructura del proyecto sigue la convención Maven y OpenXava, con vistas XML bajo `src/main/resources/xava` y entidades bajo `src/main/java`.

---

## 🧱 Estructura del proyecto

```
uam_app1/
├─ pom.xml
├─ src/
│  ├─ main/
│  │  ├─ java/
│  │  │  └─ org/example/uam_app1/
│  │  │     ├─ modelo/               # Entidades JPA (Estudiante, Carrera, TuPrimeraEntidad)
│  │  │     └─ run/                  # Clase de arranque AppServer/DBServer
│  │  ├─ resources/
│  │  │  └─ xava/                    # Definiciones de módulos y vistas OpenXava
│  │  └─ webapp/
│  │     ├─ META-INF/context.xml     # DataSource JNDI (producción)
│  │     └─ WEB-INF/web.xml
│  └─ test/                          # (opcional)
└─ target/                           # Artefactos de build (WAR)
```

---

## 🛠️ Requisitos

- **JDK 8+** (OpenXava 7.4 funciona bien con 8, 11, 17 o 21).
- **Maven 3.8+**
- **IntelliJ IDEA** (Community o Ultimate).
- (Opcional) **Apache Tomcat 9/10** para despliegue del WAR.

---

## ▶️ Ejecución en desarrollo (recomendada)

**Desde IntelliJ IDEA**

1. Importe el proyecto **Maven** (`pom.xml`).
2. Espere a que descargue dependencias.
3. Ejecute la clase `**org.example.uam_app1.run.uam_app1**` (Run ▶).  
   - Levanta **DB embebida (HSQLDB)** con `DBServer.start("uam_app1-db")` y el servidor web con `AppServer.run("uam_app1")`.
4. Abra: `http://localhost:8080/uam_app1`

**Desde consola (alternativa)**

```bash
# Compilar y empaquetar (omite tests)
mvn clean package -DskipTests
# Puede usar un contenedor externo o IntelliJ para arrancar la clase main
```

> Si desea usar **Java 11/17/21**, ajuste `maven.compiler.source/target` en `pom.xml` o el SDK del proyecto en IntelliJ.

---

## 🗄️ Configuración de base de datos

### Desarrollo (por defecto)
El arranque estándar usa **HSQLDB embebida** (sin configuración adicional). Los datos se almacenan en la carpeta del proyecto bajo el nombre del DS `"uam_app1-db"`.

### Producción / Integración con su BD
1. **Añada el driver JDBC** de su BD en `pom.xml` (MySQL, PostgreSQL, SQL Server, etc.).  
2. **Configure el DataSource JNDI** en `src/main/webapp/META-INF/context.xml` (nombre por defecto: `jdbc/uam_app1DS`).  
3. Ajuste `persistence.xml` para usar `<non-jta-data-source>java://comp/env/jdbc/uam_app1DS</non-jta-data-source>` (ya incluido).

Ejemplo (MySQL) en `context.xml`:

```xml
<Resource name="jdbc/uam_app1DS" auth="Container" type="javax.sql.DataSource"
          maxTotal="50" maxIdle="5" maxWaitMillis="10000"
          username="ux_user" password="ux_pass"
          driverClassName="com.mysql.cj.jdbc.Driver"
          url="jdbc:mysql://localhost:3306/uam_app1?serverTimezone=UTC"/>
```

Y agregue al `pom.xml`:

```xml
<dependency>
  <groupId>mysql</groupId>
  <artifactId>mysql-connector-j</artifactId>
  <version>8.4.0</version>
</dependency>
```

> Use su motor de BD preferido y credenciales seguras. Cree el esquema antes del despliegue si corresponde.

---

## 🌐 Despliegue como WAR en Tomcat

1. Construya el artefacto:
   ```bash
   mvn clean package -DskipTests
   ```
2. Copie `target/uam_app1.war` a `TOMCAT_HOME/webapps/`.
3. Configure `context.xml` con su DataSource.
4. Inicie Tomcat y abra `http://<host>:8080/uam_app1`.

> Si desea contexto raíz, renombre el WAR a `ROOT.war` o llame `AppServer.run("")` en la clase de arranque.

---

## 🧩 Módulos / Entidades

| Entidad              | Campos clave (resumen)                              | Notas |
|----------------------|------------------------------------------------------|-------|
| **Estudiante**       | `cif (Id)`, `nombre`, `apellidos`, `fechaNac`       | `@Required` en `nombre` y `apellidos`. |
| **Carrera**          | `id (Id)`, `nombre`, `descripcion`, `facultad`      | `@Required` en `nombre` y `facultad`.  |
| **TuPrimeraEntidad** | `descripcion`, `fecha`, `importe`                   | Extiende `Identifiable` (ID generado). `@Required` en `descripcion`. |

> OpenXava genera automáticamente **vistas** y **acciones** CRUD a partir de estas entidades. Puede personalizar vistas XML en `src/main/resources/xava` y anotar nuevas propiedades/relaciones en sus entidades JPA.

---

## 🧪 Pruebas (opcional)
El POM incluye `maven-surefire-plugin` con `skipTests=true`. Puede añadir pruebas JUnit y eliminar esa propiedad para activarlas.

---

## 🧰 Scripts útiles

```bash
# Compilar rápido
mvn -q -DskipTests package

# Limpiar artefactos
mvn clean

# Actualizar dependencias
mvn -U clean package -DskipTests
```

---

## 📦 Tecnologías

- **OpenXava 7.4**
- **Java 1.8**
- **Maven**
- **JPA/Hibernate**
- **HSQLDB (dev) / JNDI (prod)**
- **IntelliJ IDEA**

---

## 🗺️ Roadmap sugerido

- Autenticación/roles con **NaviOX** (organizaciones, perfiles).
- Internacionalización (i18n) de etiquetas y mensajes.
- Relacionar `Estudiante` ↔ `Carrera` y definir vistas con `@View`/`@Tabs`.
- Añadir acciones propias (`@Action`, controladores) y reglas de negocio.
- Migrar a JDK 17/21 si lo requiere su entorno.

---

## 🤝 Contribuciones
Siga *fork & pull request*. Acepte el formato de código y convenciones de nombres.

---

## 📄 Licencia
Este proyecto se distribuye bajo licencia **MIT**. Consulte `LICENSE` si aplica.

---

### 📣 Contacto
Si requiere soporte o una guía rápida para extender módulos y vistas, no dude en abrir un *issue* o contactar al responsable del proyecto.

---

> _Hecho con ❤️ y OpenXava. Color de marca: **#0099A8**._
