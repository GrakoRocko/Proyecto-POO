# Banco Digital – Proyecto integrador de POO

## Cuarto avance (entrega final): integración, pruebas y presentación del sistema
Simulación en consola del rol **administrador** de un banco digital. Los datos se mantienen en memoria durante la sesión.

**Equipo:** 
- González Alba Carlos Alberto 
- Martínez Cruz Yaek Alejandro

**Repositorio:** https://github.com/GrakoRocko/Proyecto-POO

**Commit de entrega:** `e93894d`

**Versión de Java:** 17 o superior

**Herramientas:** VS Code y PowerShell en Windows. No se usan bibliotecas externas, patrones de diseño ni hilos.

### Presentación
Link del Canva: https://canva.link/37quyxi797ruhjo 

### Estructura del proyecto
```
proyecto/
├── app/          Main.java 
├── excepciones/  SaldoInsuficienteException.java 
├── modelo/       Cuenta, CuentaAhorro, CuentaNomina, Cliente, Transaccion, TipoTransaccion
└── servicio/     Administrador.java
```

### Ejecutar el programa en Windows
Para compilar y ejecutar en la terminal (PowerShell):
1. Clonar el repositorio con `git clone https://github.com/GrakoRocko/Proyecto-POO.git`
2. Ubicarse en la carpeta raíz del proyecto descargado (la que contiene `app`, `excepciones`, `modelo` y `servicio`)
3. Compilar con el comando `javac -encoding UTF-8 -d out (Get-ChildItem -Recurse -Filter *.java | ForEach-Object FullName)`
4. Ejecutar con el comando `java -cp out app.Main`

> Nota: si las tildes se ven mal en la consola, ejecutar antes `chcp 65001`.

### Ejecutar el programa en Mac y Linux
1. Clonar el repositorio con `git clone https://github.com/GrakoRocko/Proyecto-POO.git`
2. Ubicarse en la carpeta raíz del proyecto descargado
3. Compilar con el comando `javac -encoding UTF-8 -d out $(find . -name "*.java")`
4. Ejecutar con el comando `java -cp out app.Main`

### Cómo usar el menú
Se escribe el número de la opción y se responden los datos que pide el programa. Al terminar cada operación se imprime "Intento finalizado" y vuelve a aparecer el menú. Un dato incorrecto no cierra el programa, se muestra el error y el programa sigue ejecutandose. La opción `0` termina el programa.

**Datos de ejemplo**

| Cuenta | Tipo | Id del cliente | Nombre | Límite de crédito |
|--------|------|----------------|--------|-------------------|
| `319265299` | Ahorro | 1221 | Kobito | 10600 |
| `12212330` | Nómina | 2330 | Kira | 50000 |

### Funcionalidades
**Principales:**
- Depositar (opción 2)
- Retirar con comisión según el tipo de cuenta (opción 3)
- Revertir una transacción, solo una vez (opción 4)
- Ver historial, con las transacciones válidas separadas de los intentos inválidos (opción 6)

**De apoyo:** abrir cuenta (1), marcar transacción sospechosa (5), cambiar límite de crédito (7), cerrar cuenta (8) y comparar comisiones de retiro (9) en donde se muestra el polimorfismo entre `CuentaAhorro` y `CuentaNomina` y omite las cuentas cerradas.

### Correcciones respecto al tercer avance
| Problema | Ajuste | Cómo se comprobó |
|----------|--------|------------------|
| El reporte y el UML usaban nombres distintos al código (`CuentaBancaria`, `Banco`, `calcularComision()` sin parámetros) | Se actualizaron a `Cuenta`, `Administrador` y `calcularComision(double monto)` | Revisión del UML y del reporte contra el código |
| La opción 9 incluía cuentas cerradas | Ahora omite las cuentas inactivas | Cerrar una cuenta y ejecutar la opción 9 |
| Los comandos de compilación con `*.java` no funcionaban en PowerShell | Se usa `Get-ChildItem` para listar los archivos y `-d out` | Compilar desde cero siguiendo este README |
| Los casos de prueba solo estaban en el README | Se trasladaron al reporte con sus evidencias | P1 a P8 ejecutadas sobre la versión entregada |

Las correcciones del segundo avance se conservan y las transacciones se guardan solo si la operación tuvo éxito, el historial separa válidas e inválidas y una transacción solo se revierte una vez.

### Excepciones utilizadas
`NumberFormatException` (texto en lugar de número), `IllegalArgumentException` (dato inválido), `IllegalStateException` (cuenta cerrada o transacción ya revertida) y la propia `SaldoInsuficienteException` (retiro o reversión sin saldo). Se capturan en `Main` con `try-catch-finally`.

### Casos de prueba
Antes de empezar: abrir una cuenta de ahorro y una de nómina y depositar $2000 en cada una.

| Caso | Pasos | Resultado esperado |
|------|-------|--------------------|
| P1 | Opción 9 con monto 1000 | Cuenta de ahorro: comisión de $20.00 |
| P2 | La misma opción 9 | Cuenta de nómina: comisión fija de $5.00 |
| P3 | Depositar escribiendo `abc` como monto | Error de formato y el menú sigue disponible |
| P4 | Depositar `-50` o `0` | `IllegalArgumentException` capturada, el saldo no cambia y el intento aparece como inválido en el historial |
| P5 | Retirar $2000 (todo el saldo) en cada cuenta | `SaldoInsuficienteException`: la comisión impide el retiro y el saldo se conserva |
| P6 | Depositar un monto válido justo después de P5 | La operación se procesa con normalidad |
| P7 | Un depósito válido y uno inválido | "Intento finalizado" aparece en ambos; la confirmación de éxito solo en el válido |
| P8 | Flujo completo en una cuenta de ahorro nueva: abrir, depositar 1500, retirar 500, revertir ese retiro, intentar revertirlo otra vez y ver el historial | El saldo queda en $1500.00, el segundo reverso se rechaza y el historial separa válidas e inválidas |

### Limitaciones conocidas
- Al cerrar el programa se pierden las cuentas: no hay base de datos ni archivos.
- Se usa `double` para el dinero.
- El límite de crédito se puede consultar y cambiar, pero no afecta a los retiros.
- No hay autenticación: el único rol es el administrador.