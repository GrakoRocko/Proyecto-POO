package app;
import modelo.Cuenta;
import modelo.Transaccion;
import servicio.Administrador;

public class Main {
    public static void main(String[] args) {
        Administrador admin = new Administrador();

        // Abrimos una cuenta
        System.out.println("\n*** ABRIR CUENTA ***");
        Cuenta cuenta1 = admin.abrirCuenta("100200300", "C-001", "Kobu", 500000.0);

        // Hacer un deposito
        System.out.println("\n*** PROCESAR MOVIMIENTOS ***");
        Transaccion tx1 = admin.procesarDeposito("TX-001", cuenta1, 2000.0);
        System.out.println("Deposito: " + tx1);
        Transaccion tx2 = admin.procesarDeposito("TX-002", cuenta1, 15000.0); // Una muy grande

        // Se detcta como una transacción sospechosa
        System.out.println("\n*** REVISIÓN ADMINISTRATIVA ***");
        admin.marcarSospechosa(tx2);

        // Se ve el historial de transacciones
        admin.verHistorial(cuenta1);

        // Revertimos la transacción sospechosa
        System.out.println("\n*** REVERTIR MOVIMIENTO ***");
        admin.revertirTransaccion(tx2, cuenta1, "TX-REV-002");

        // Ajustamos el límite de crédito
        System.out.println("\n*** ACTUALIZAR LÍMITE DE CRÉDITO ***");
        admin.cambiarLimiteCredito(cuenta1.getTitular(), 25000.0);

        // Cerramos la cuenta
        System.out.println("\n*** CERRAR CUENTA ***");
        admin.cerrarCuenta(cuenta1);

        // Validamos que la cuenta se haya cerrado haciendo un deposito
        admin.procesarDeposito("TX-003", cuenta1, 500.0);
    }
}