# Banco Digital – Proyecto integrador de POO

## Tercer Avance: herencia, polimorfismo y manejo de excepciones
Para este proyecto se hara una simulación del rol **administrador** de un banco digital.

**Versión de Java:** 17 o superior

**Commit de entrega:** `0c167f5`

### Estructura del proyecto
```
proyecto/
├── app/          Main.java 
├── excepciones/  SaldoInsuficienteException.java 
├── modelo/       Cuenta, CuentaAhorro, CuentaNomina, Cliente, Transaccion, TipoTransaccion
└── servicio/     Administrador.java
```

### Ejecutar el programa en Windows
Para compilar y ejecutar este avance en la terminal (PowerShell):
1. Clonar el repositorio con `git clone https://github.com/GrakoRocko/Proyecto-POO.git`
2. Ubicarse en la carpeta raíz del proyecto descargado
3. Compilar con el comando `javac -encoding UTF-8 -d out (Get-ChildItem -Recurse -Filter *.java | ForEach-Object FullName)`
4. Ejecutar con el comando `java -cp out app.Main`

>Nota: Si las tildes se ven mal en la consola, ejecutar antes `chcp 65001`.

### Ejecutar el programa en Mac y Linux
1. Clonar el repositorio con `git clone https://github.com/GrakoRocko/Proyecto-POO.git`
2. Ubicarse en la carpeta raíz del proyecto descargado
3. Compilar con el comando `javac -encoding UTF-8 -d out $(find . -name "*.java")`
4. Ejecutar con el comando `java -cp out app.Main`

### Funciones del Menú
1. Abrir cuenta
2. Depositar
3. Retirar 
4. Revertir transacción
5. Marcar transacción sospechosa
6. Ver historial
7. Cambiar límite de crédito
8. Cerrar cuenta
9. Comparar comisiones de retiro

### Cambios respecto al segundo avance
- Las transacciones se guardan en el historial solo si la operación tuvo éxito
- El historial separa las transacciones válidas de los intentos inválidos
- Una transacción solo puede revertirse una vez
- Se creo la clase excepciones con `NumberFormatException`, `IllegalArgumentException`, `IllegalStateException` y la propia `SaldoInsuficienteException`, con `try-catch-finally`.

### Casos de prueba
Antes de empezar, abrir una cuenta de ahorro (`319265299`) y una de nómina (`12212330`) y depositar $2000 en cada una

| Caso | Pasos | Resultado esperado |
|------|-------|--------------------|
| P1 | Opción 9 con monto 1000 | Comisión de $20.00 |
| P2 | Opción 9 pero con `CuentaNomina` | Comisión fija de $5.00 |
| P3 | Depositar escribiendo `abc` como monto | Error de formato, se puede seguir usando el menú |
| P4 | Depositar -50 o 0 | `IllegalArgumentException` capturada, el saldo no cambia y el intento aparece como inválido |
| P5 | Retirar más que el saldo | `SaldoInsuficienteException`, el saldo es el mismo antes y después |
| P6 | Depositar un monto válido justo después de P5 | La operación se procesa con normalidad |
| P7 | Un depósito válido y uno inválido | "Intento finalizado" aparece en ambos |

> **Prueba extra:** revertir dos veces la misma transacción. La segunda vez se rechaza.

### Limitaciones
- Al cerrar el programa se pierden las cuentas, no tenemos una base de datos
- Se usa `double` para el dinero
- El límite de crédito se puede consultar y cambiar pero no afecta en nada a los retiros