package app;
import modelo.Cuenta;
import modelo.Transaccion;
import servicio.Administrador;

public class Main {
    public static void main(String[] args) {
        Administrador admin = new Administrador();

        // 1. Abrir cuenta
        System.out.println("\n*** ABRIR CUENTA ***");
        Cuenta cuenta1 = admin.abrirCuenta("100200300", "C-001", "Yaek", 5000.0);

        // 2. Procesar transacciones
        System.out.println("\n*** PROCESAR MOVIMIENTOS ***");
        Transaccion tx1 = admin.procesarDeposito("TX-001", cuenta1, 2000.0);
        System.out.println("Deposito: " + tx1);
        Transaccion tx2 = admin.procesarDeposito("TX-002", cuenta1, 15000.0); // Una muy grande

        // 3. Marcar sospechosa
        System.out.println("\n*** REVISIÓN ADMINISTRATIVA ***");
        admin.marcarSospechosa(tx2);

        // 4. Ver historial
        admin.verHistorial(cuenta1);

        // 5. Revertir transacción sospechosa
        System.out.println("\n*** REVERTIR MOVIMIENTO ***");
        admin.revertirTransaccion(tx2, cuenta1, "TX-REV-002");

        // 6. Cambiar límite de crédito
        System.out.println("\n*** ACTUALIZAR LÍMITE DE CRÉDITO ***");
        admin.cambiarLimiteCredito(cuenta1.getTitular(), 25000.0);

        // 7. Cerrar cuenta
        System.out.println("\n*** CERRAR CUENTA ***");
        admin.cerrarCuenta(cuenta1);

        // Intentar depositar después de cerrada (validación)
        admin.procesarDeposito("TX-003", cuenta1, 500.0);
    }
}