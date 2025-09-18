# Guía rápida: instalar y usar **JShell** en Linux y Windows

> **JShell** es el REPL oficial de Java (desde Java 9). Permite probar código Java de forma interactiva, línea a línea, sin compilar ni crear proyectos.

---

## 1) Requisitos y comprobaciones

* Necesitas **JDK 9+** (recomendado LTS actual, p. ej. 17 o 21).
* Comprueba si ya lo tienes:

```bash
# Linux / macOS
java -version
jshell --version
```

```powershell
# Windows (PowerShell)
java -version
jshell --version
```

Si `jshell` no aparece, instala un **JDK** y asegúrate de que el **PATH** incluye la carpeta `bin` del JDK.

---

## 2) Instalación del JDK (incluye JShell)

### 2.1 Linux (varias distros)

#### Ubuntu/Debian (APT)

```bash
sudo apt update
sudo apt install -y openjdk-21-jdk
```

#### Fedora/RHEL (DNF)

```bash
sudo dnf install -y java-21-openjdk-devel
```

#### Arch/Manjaro (Pacman)

```bash
sudo pacman -S --needed jdk21-openjdk
```

> Sustituye `21` por la versión LTS que prefieras (`17`/`21`).

Comprobar:

```bash
jshell --version
```

> Si `jshell` no se encuentra, añade la ruta del JDK a tu `PATH`. Por ejemplo (bash/zsh):

```bash
# Ajusta la ruta a tu instalación
export JAVA_HOME="/usr/lib/jvm/java-21-openjdk"
export PATH="$JAVA_HOME/bin:$PATH"
```

Para hacerlo permanente, añade esas líneas a `~/.bashrc` o `~/.zshrc`.

---

### 2.2 Windows

#### Opción A) Winget (Windows 10/11)

```powershell
winget install --id Oracle.JDK.21 --source winget
# o una distribución OpenJDK:
winget install --id EclipseAdoptium.Temurin.21.JDK --source winget
```

#### Opción B) Chocolatey

```powershell
choco install -y temurin21
```

#### Opción C) Scoop

```powershell
scoop install temurin-lts-jdk
```

Configura variables (si fuera necesario):

```powershell
# Ajusta la ruta a tu JDK
$env:JAVA_HOME = "C:\\Program Files\\Eclipse Adoptium\\jdk-21"  
$env:Path = "$env:JAVA_HOME\\bin;$env:Path"
```

Comprueba:

```powershell
jshell --version
```

---

## 3) Primeros pasos con JShell

Inicia el REPL:

```bash
jshell
```

Comandos útiles dentro de JShell (siempre comienzan con `/`):

* `/help` → ayuda general o `/help <comando>`
* `/list` → lista fragmentos (snippets) introducidos
* `/vars`, `/methods`, `/types` → variables, métodos y tipos definidos
* `/imports` → imports activos
* `/save <archivo.jsh>` → guarda la sesión
* `/open <archivo.jsh>` → abre y ejecuta un guion de JShell
* `/edit <snippet>` → abre editor sencillo para modificar un fragmento
* `/reset` → reinicia la sesión
* `/exit` → salir

> Consejo: Autocompletado con **TAB** y navegación de historial con flechas ↑/↓.

---

## 4) Java rápido en JShell: ejemplos esenciales

### 4.1 Hola mundo y operaciones

```jshell
jshell> System.out.println("Hola, JShell!");
Hola, JShell!

jshell> 2 + 3 * 4
$1 ==> 14

jshell> var x = 10
x ==> 10

jshell> x += 5
$2 ==> 15
```

### 4.2 Imports y librerías estándar

```jshell
jshell> import java.time.*;
jshell> LocalDate.now()
$1 ==> 2025-09-18

jshell> import java.util.*;
jshell> var nums = List.of(1,2,3,4,5);
nums ==> [1, 2, 3, 4, 5]

jshell> nums.stream().map(n -> n*n).toList()
$2 ==> [1, 4, 9, 16, 25]
```

### 4.3 Definir métodos, clases y records al vuelo

```jshell
jshell> int cuad(int n) { return n*n; }
|  created method cuad(int)

jshell> cuad(7)
$1 ==> 49

jshell> class Punto { int x,y; Punto(int x,int y){this.x=x;this.y=y;} }
|  created class Punto

jshell> var p = new Punto(3,4)
p ==> Punto@...

jshell> record Par(int a,int b){}
|  created record Par

jshell> new Par(10,20)
$2 ==> Par[a=10, b=20]
```

### 4.4 Manejo de excepciones y var multi-línea

```jshell
jshell> try {
   ...>   Integer.parseInt("abc");
   ...> } catch (NumberFormatException e) {
   ...>   System.out.println("Error: " + e.getMessage());
   ...> }
Error: For input string: "abc"
```

### 4.5 Guardar y reutilizar sesiones

```jshell
jshell> /save practica1.jsh
jshell> /exit
$ jshell /open practica1.jsh
```

### 4.6 Cargar JARs y usar classpath

* **Temporal (solo durante la sesión):**

```jshell
jshell> /env -class-path libs/mi-lib.jar
jshell> /reload
```

* **Al iniciar JShell:**

```bash
jshell --class-path libs/mi-lib.jar
```

* **Múltiples rutas:** separa por `:` en Linux y por `;` en Windows.

### 4.7 Usar módulos (module-path)

Si tu proyecto está modularizado:

```bash
jshell --module-path mods --add-modules com.ejemplo.app
```

Dentro de JShell puedes ajustar el entorno:

```jshell
jshell> /env -module-path mods --add-modules com.ejemplo.app
jshell> /reload
```

### 4.8 Habilitar características de vista previa

Para probar APIs o sintaxis en **preview** (según JDK):

```bash
jshell --enable-preview
```

---

## 5) Ejemplos prácticos completos

### 5.1 Mini utilidades de consola

```jshell
jshell> String rep(String s, int n) { return s.repeat(n); }
|  created method rep(String,int)

jshell> rep("na", 4) + " Batman!"
$1 ==> "nananana Batman!"
```

### 5.2 Trabajar con ficheros (NIO)

```jshell
jshell> import java.nio.file.*; import java.io.IOException;
jshell> var ruta = Path.of("datos.txt");
jshell> Files.writeString(ruta, "linea1\nlinea2\n");
jshell> Files.readAllLines(ruta)
$1 ==> [linea1, linea2]
```

### 5.3 HTTP rápido (Java 11+)

```jshell
jshell> import java.net.http.*; import java.net.*; import java.time.*;
jshell> var cliente = HttpClient.newHttpClient();
jshell> var req = HttpRequest.newBuilder(URI.create("https://httpbin.org/get")).build();
jshell> var res = cliente.send(req, HttpResponse.BodyHandlers.ofString());
jshell> res.statusCode()
$1 ==> 200
jshell> res.body().substring(0,60)
$2 ==> "{\n  \"args\": {}, \n  \"headers\": {\n    \"Accept-Enco"
```

### 5.4 Pruebas rápidas de colecciones y streams

```jshell
jshell> import java.util.*; import java.util.stream.*;
jshell> var rnd = new Random(7);
jshell> IntStream.generate(() -> rnd.nextInt(100)).limit(5).sorted().boxed().toList()
$1 ==> [0, 13, 14, 63, 76]
```

### 5.5 Expresiones regulares

```jshell
jshell> import java.util.regex.*;
jshell> var m = Pattern.compile("\\d+").matcher("abc123xyz");
jshell> m.find(); m.group()
$1 ==> true
$2 ==> "123"
```

---

## 6) Integración en scripts y alias

### 6.1 Linux (bash/zsh)

Alias útil:

```bash
echo 'alias jsh="jshell --enable-preview"' >> ~/.bashrc
source ~/.bashrc
```

Ejecutar un archivo `.jsh`:

```bash
jshell /open practica1.jsh
```

### 6.2 Windows (PowerShell)

Perfil de PowerShell (si no existe, se crea):

```powershell
if (!(Test-Path $PROFILE)) { New-Item -ItemType File -Path $PROFILE -Force }
Add-Content $PROFILE 'function jsh { jshell --enable-preview @args }'
. $PROFILE
jsh --version
```

---

## 7) Diagnóstico de problemas (FAQ)

* **`jshell: command not found` / `El término 'jshell' no se reconoce`:**
  No está en el PATH. Ajusta `JAVA_HOME` y añade `$JAVA_HOME/bin` al PATH.

* **Tengo varias versiones de Java y se abre la que no quiero:**
  Prioriza el PATH o usa rutas absolutas, p. ej.:
  `"/usr/lib/jvm/java-21-openjdk/bin/jshell"` o `"C:\\Program Files\\Eclipse Adoptium\\jdk-21\\bin\\jshell.exe"`.

* **No puedo cargar mi JAR:**
  Asegúrate de usar `--class-path` (o `/env -class-path`) y que no haya conflictos de módulos.

* **Errores con codificación de caracteres:**
  Ejecuta con `-Dfile.encoding=UTF-8` si lo necesitas:
  `jshell -J-Dfile.encoding=UTF-8`.

* **Permisos en Linux:**
  Si el `JDK` está en una ruta del sistema, quizás requieras `sudo` para instalar, pero no para ejecutar JShell.

---

## 8) Recursos y buenas prácticas

* Usa **JShell** para prototipar, explicar en clase y validar APIs rápidas.
* Guarda fragmentos reutilizables con `/save` y compártelos (`.jsh`).
* Mantén un **JDK LTS** (17/21) para estabilidad.

> ¡Listo! Con esto puedes instalar, iniciar y sacarle partido a **JShell** en minutos, tanto en Linux como en Windows.
