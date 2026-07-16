> [!NOTE]  
> See English version [here](README_en.md)

# noClick 
*Zero Clicks. Full access.*

# Desarrollador

Gonzalo Vattino

# Descripción Corta del Videojuego

Este juego busca enseñar conceptos técnicos y realistas de la forma más amigable posible. El jugador será un hacker que aprenderá conceptos y técnicas de cyberseguridad de manera progresiva a medida que avanzan los niveles.

El jugador deberá analizar situaciones, tomar decisiones y aplicar los conceptos reales dentro de escenarios inspirados en la realidad, con pruebas de penetración e ingeniería social.

# Tecnologías Principales

**Version de Java:** [Java 21 LTS](https://www.oracle.com/java/technologies/downloads/#jdk21-windows)  
**Framework:** [libGDX 1.14.2](https://github.com/libgdx/libgdx/releases/tag/1.14.2)  
**Plataformas de desarrollo objetivo:** Computadoras de Escritorio

# Wiki

[Propuesta completa](https://github.com/GonzaStd/noClick/wiki/Propuesta-%5BES%5D) en la Wiki de Github

# Como descargar y ejecutar el proyecto
## Windows (x64)

> [!NOTE]
> Para abrir una terminal en Windows basta con hacer click derecho en el explorador y luego click en "Abrir en terminal".
> Deberías ver algo similar a esto: `PS C:\ruta\a\carpeta>`.

### 1. Descargar proyecto
Realizar alguna de las dos siguientes opciones:

#### 1.1 Descargar e instalar Git

1. Descargar el `Git-{version}-64-bit.exe` de la [latest release](https://github.com/git-for-windows/git/releases/latest) del repositorio oficial de git para windows (github.com/git-for-windows/git).
2. Hacer doble click y seguir los [pasos de instalación](https://www.youtube.com/watch?v=4sw0ROVaoQ0) (es intuitivo, no es necesario ver el link del hipervínculo)
3. En la carpeta que quieras (Documentos, Escritorio) Es necesario abrir una terminal (cmd o powershell).
4. Una vez la terminal en la carpeta destino, ejecutá el siguiente comando: `git clone https://github.com/GonzaStd/noClick` y presioná enter, para clonar este repositorio en la misma carpeta.


#### 1.2 Descargar y descomprimir .zip

1. En la parte superior del repositorio hay un boton desplegable verde que dice "Code". Hacé click en él. 
2. Hacé click en la última opción que dice "Download ZIP" o "Descargar ZIP".
3. Hacé click derecho en el `.zip` que descargaste y seleccioná "Descomprimir en `\noClick`", o algo similar.

### 2. Descargar e instalar dependencias y ejecutar proyecto
#### 2.1 Descargar e instalar Java
Descargá el [Kit de Desarrollo de Java 21](https://download.oracle.com/java/21/latest/jdk-21_windows-x64_bin.exe) y seguí las [instrucciones para instalarlo](https://www.youtube.com/watch?v=i7JWf6Os8rI).

#### 2.2 Abrir terminal
Es necesario abrir una terminal (cmd o powershell) en la carpeta raíz del repositorio local. Deberías ver algo similar a esto: `PS C:\Users\<USUARIO>\Documents\noClick>`.

#### 2.3 Ejecutar proyecto con gradlew
Ejecutar en la terminal una gradlew task con el siguiente comando `gradlew.bat lwjgl3:run`, el mismo compila y ejecuta el proyecto.

# Registro de cambios

Accedé al [changelog](./CHANGELOG.md)

---

<a href="https://github.com/GonzaStd/noClick">noClick</a> © 2026 by <a href="https://github.com/GonzaStd">Gonzalo Vattino</a> is licensed under <a href="[https://creativecommons.org/licenses/by-nc-sa/4.0/](https://polyformproject.org/licenses/noncommercial/1.0.0)">PolyForm Noncommercial License 1.0.0</a>
