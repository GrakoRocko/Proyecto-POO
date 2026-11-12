package app;

import excepciones.SaldoInsuficienteException;
import modelo.Cuenta;
import modelo.Transaccion;
import servicio.Administrador;
import java.util.Scanner;

public class Main {
    private static final Scanner entrada = new Scanner(System.in);
    private static final Administrador admin = new Administrador();

    public static void main(String[] args) {
        boolean salir = false;
        while (!salir) {
            mostrarMenu();
            try {
                int opcion = Integer.parseInt(leer("Elige una opción: "));
                switch (opcion) {
                    case 1: abrirCuenta(); break;
                    case 2: depositar(); break;
                    case 3: retirar(); break;
                    case 4: revertir(); break;
                    case 5: marcarSospechosa(); break;
                    case 6: admin.verHistorial(leer("Número de cuenta: ")); break;
                    case 7: cambiarLimite(); break;
                    case 8: cerrarCuenta(); break;
                    case 9: compararComisiones(); break;
                    case 0: salir = true; System.out.println("Hasta luego."); break;
                    default: System.out.println("Opción fuera del menú (usa un número del 0 al 9).");
                }
            } catch (NumberFormatException e) {
                // Va antes que IllegalArgumentException porque es su subclase
                System.out.println("Error de formato: escribe un número válido (ejemplo: 1500.50).");
            } catch (IllegalArgumentException e) {
                System.out.println("Dato inválido: " + e.getMessage());
            } catch (IllegalStateException e) {
                System.out.println("Operación no permitida: " + e.getMessage());
            } catch (SaldoInsuficienteException e) {
                System.out.println("Operación rechazada: " + e.getMessage());
            } finally {
                System.out.println("Intento finalizado.");
            }
        }
    }

    private static void mostrarMenu() {
        System.out.println("\n===== BANCO DIGITAL - ADMINISTRADOR =====");
        System.out.println("1. Abrir cuenta");
        System.out.println("2. Depositar");
        System.out.println("3. Retirar");
        System.out.println("4. Revertir transacción");
        System.out.println("5. Marcar transacción sospechosa");
        System.out.println("6. Ver historial");
        System.out.println("7. Cambiar límite de crédito");
        System.out.println("8. Cerrar cuenta");
        System.out.println("9. Comparar comisiones de retiro (polimorfismo)");
        System.out.println("0. Salir");
    }

    private static String leer(String mensaje) {
        System.out.print(mensaje);
        return entrada.nextLine();
    }

    private static void abrirCuenta() {
        String tipo = leer("Tipo de cuenta (1 = Ahorro, 2 = Nómina): ").trim();
        String nombreTipo;
        if (tipo.equals("1")) {
            nombreTipo = "AHORRO";
        } else if (tipo.equals("2")) {
            nombreTipo = "NOMINA";
        } else {
            throw new IllegalArgumentException("Tipo de cuenta no válido; elige 1 o 2.");
        }
        String numero = leer("Número de cuenta: ");
        String idCliente = leer("Id del cliente: ");
        String nombre = leer("Nombre del cliente: ");
        double limite = Double.parseDouble(leer("Límite de crédito: "));
        Cuenta cuenta = admin.abrirCuenta(nombreTipo, numero, idCliente, nombre, limite);
        System.out.println("Cuenta " + cuenta.getNumeroCuenta() + " abierta para " + nombre + ".");
        System.out.println(cuenta.describirCondiciones());
    }

    private static void depositar() {
        String numero = leer("Número de cuenta: ");
        double monto = Double.parseDouble(leer("Monto a depositar: "));
        Transaccion tx = admin.depositar(numero, monto);
        System.out.println("Depósito exitoso. " + tx);
        System.out.println("Saldo actual: $" + String.format("%.2f", admin.buscarCuenta(numero).getSaldo()));
    }

    private static void retirar() throws SaldoInsuficienteException {
        String numero = leer("Número de cuenta: ");
        double monto = Double.parseDouble(leer("Monto a retirar: "));
        Transaccion tx = admin.retirar(numero, monto);
        System.out.println("Retiro exitoso. " + tx);
        System.out.println("Saldo actual: $" + String.format("%.2f", admin.buscarCuenta(numero).getSaldo()));
    }

    private static void revertir() throws SaldoInsuficienteException {
        String numero = leer("Número de cuenta: ");
        String id = leer("Id de la transacción a revertir (ej. TX-001): ");
        Transaccion reverso = admin.revertirTransaccion(numero, id);
        System.out.println("Transacción " + id.trim() + " revertida. " + reverso);
        System.out.println("Saldo actual: $" + String.format("%.2f", admin.buscarCuenta(numero).getSaldo()));
    }

    private static void marcarSospechosa() {
        String numero = leer("Número de cuenta: ");
        String id = leer("Id de la transacción (ej. TX-001): ");
        Transaccion tx = admin.marcarSospechosa(numero, id);
        System.out.println("Marcada como SOSPECHOSA: " + tx);
    }

    private static void cambiarLimite() {
        String numero = leer("Número de cuenta: ");
        double nuevo = Double.parseDouble(leer("Nuevo límite de crédito: "));
        admin.cambiarLimiteCredito(numero, nuevo);
        System.out.println("Límite de crédito actualizado a $" + String.format("%.2f", nuevo) + ".");
    }

    private static void cerrarCuenta() {
        String numero = leer("Número de cuenta a cerrar: ");
        admin.cerrarCuenta(numero);
        System.out.println("La cuenta " + numero.trim() + " ha sido cerrada.");
    }

    /** Polimorfismo: la referencia es de tipo Cuenta, pero se ejecuta la versión de cada subclase. */
    private static void compararComisiones() {
    double monto = Double.parseDouble(leer("Monto del retiro a simular: "));
    boolean hayActivas = false;
    for (Cuenta c : admin.getCuentas().values()) { // referencia de tipo base
        if (!c.isActiva()) {
            continue; // las cuentas cerradas no participan en la comparación
        }
        hayActivas = true;
        System.out.println(c.getNumeroCuenta() + " [" + c.getClass().getSimpleName() + "] -> comisión: $"
                + String.format("%.2f", c.calcularComision(monto)) + " | " + c.describirCondiciones());
    }
    if (!hayActivas) {
        System.out.println("No hay cuentas activas para comparar. Abre una cuenta primero.");
    }
}
}