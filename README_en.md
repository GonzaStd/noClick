> [!NOTE]  
> Ver versión en español [aquí](README.md)

# noClick 
*Zero Clicks. Full access.*

# Developer

Gonzalo Vattino

# Short Video Game Description

This game aims to teach technical and realistic concepts in the friendliest way possible. The player will be a hacker who progressively learns cybersecurity concepts and techniques as they advance through the levels.

The player must analyze situations, make decisions, and apply real concepts within scenarios inspired by reality, with penetration testing and social engineering.

# Main Technologies

**Java Version:** [Java 21 LTS](https://www.oracle.com/java/technologies/downloads/#jdk21-windows)  
**Framework:** [libGDX 1.14.2](https://github.com/libgdx/libgdx/releases/tag/1.14.2)  
**Target development platforms:** Desktop Computers

# Wiki

[Complete proposal](https://github.com/GonzaStd/noClick/wiki/Propuesta-%5BES%5D) on the Github Wiki

# How to download and run the project
## Windows (x64)

> [!NOTE]
> To open a terminal in Windows, simply right-click in the file explorer and then click "Open in terminal".
> You should see something similar to this: `PS C:\path\to\folder>`.

### 1. Download project
Choose one of the following two options:

#### 1.1 Download and install Git

1. Download the `Git-{version}-64-bit.exe` from the [latest release](https://github.com/git-for-windows/git/releases/latest) of the official git repository for windows (github.com/git-for-windows/git).
2. Double-click and follow the [installation steps](https://www.youtube.com/watch?v=4sw0ROVaoQ0) (it's intuitive, watching the hyperlink video is not necessary).
3. In the folder of your choice (Documents, Desktop) you need to open a terminal (cmd or powershell).
4. Once the terminal is in the destination folder, run the following command: `git clone https://github.com/GonzaStd/noClick` and press enter, to clone this repository into the same folder.

#### 1.2 Download and unzip .zip

1. At the top of the repository there is a green dropdown button that says "Code". Click on it.
2. Click on the last option that says "Download ZIP".
3. Right-click on the `.zip` file you downloaded and select "Extract to `\noClick`", or something similar.

### 2. Download and install dependencies and run the project
#### 2.1 Download and install Java
Download the [Java 21 Development Kit](https://download.oracle.com/java/21/latest/jdk-21_windows-x64_bin.exe) and follow the [instructions to install it](https://www.youtube.com/watch?v=i7JWf6Os8rI).

#### 2.2 Open terminal
You need to open a terminal (cmd or powershell) in the root folder of the local repository. You should see something similar to this: `PS C:\Users\<USER>\Documents\noClick>`.

#### 2.3 Run the project with gradlew
Run a gradlew task in the terminal with the following command `gradlew.bat lwjgl3:run`, which compiles and runs the project.

# Changelog

Access the [changelog](./CHANGELOG.md)

---

<a href="https://github.com/GonzaStd/noClick">noClick</a> © 2026 by <a href="https://github.com/GonzaStd">Gonzalo Vattino</a> is licensed under <a href="[https://creativecommons.org/licenses/by-nc-sa/4.0/](https://polyformproject.org/licenses/noncommercial/1.0.0)">PolyForm Noncommercial License 1.0.0</a>
