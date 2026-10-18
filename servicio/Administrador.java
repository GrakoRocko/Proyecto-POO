package servicio;
import modelo.Cliente;
import modelo.Cuenta;
import modelo.Transaccion;

public class Administrador {
    
    // 1. Abrir cuenta
    public Cuenta abrirCuenta(String numero, String idCliente, String nombreCliente, double limiteCredito) {
        Cliente nuevoCliente = new Cliente(idCliente, nombreCliente, limiteCredito);
        Cuenta nuevaCuenta = new Cuenta(numero, nuevoCliente);
        System.out.println("Cuenta " + numero + " abierta exitosamente para " + nombreCliente);
        return nuevaCuenta;
    }

    // 2. Cerrar cuenta
    public void cerrarCuenta(Cuenta cuenta) {
        cuenta.setActiva(false);
        System.out.println("La cuenta " + cuenta.getNumeroCuenta() + " ha sido cerrada.");
    }

    // 3. Ver historial
    public void verHistorial(Cuenta cuenta) {
        System.out.println("\n--- Historial de Cuenta: " + cuenta.getNumeroCuenta() + " ---");
        for (Transaccion tx : cuenta.getHistorial()) {
            System.out.println(tx);
        }
        System.out.println("---------------------------------------");
    }

    // 4. Marcar transacción sospechosa
    public void marcarSospechosa(Transaccion tx) {
        tx.setSospechosa(true);
        System.out.println("La transacción " + tx.getIdTransaccion() + " ha sido marcada como SOSPECHOSA.");
    }

    // 5. Revertir transacción
    public void revertirTransaccion(Transaccion tx, Cuenta cuenta, String idReverso) {
        if (tx.getTipo().equals("DEPOSITO")) {
            cuenta.retirar(tx.getMonto());
            Transaccion reverso = new Transaccion(idReverso, cuenta, tx.getMonto(), "REVERSO");
            cuenta.registrarTransaccion(reverso);
            System.out.println("Transacción " + tx.getIdTransaccion() + " revertida. Nuevo saldo: $" + String.format("%.2f", cuenta.getSaldo()));
        }
    }

    // 6. Cambiar límite de crédito
    public void cambiarLimiteCredito(Cliente cliente, double nuevoLimite) {
        cliente.setLimiteCredito(nuevoLimite);
        System.out.println("El límite de crédito de " + cliente.getNombre() + " se actualizó a $" + String.format("%.2f", nuevoLimite));
    }

    // Procesar un depósito normal
    public Transaccion procesarDeposito(String idTx, Cuenta cuenta, double monto) {
        cuenta.depositar(monto);
        Transaccion tx = new Transaccion(idTx, cuenta, monto, "DEPOSITO");
        if (cuenta.isActiva()) {
            cuenta.registrarTransaccion(tx);
            System.out.println("Depósito de $" + String.format("%.2f", monto) + " procesado. Saldo actual: $" + String.format("%.2f", cuenta.getSaldo()));
        }
        return tx;
    }
}