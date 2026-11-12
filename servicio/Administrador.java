package servicio;

import excepciones.SaldoInsuficienteException;
import modelo.Cliente;
import modelo.Cuenta;
import modelo.CuentaAhorro;
import modelo.CuentaNomina;
import modelo.Transaccion;
import java.util.LinkedHashMap;
import java.util.Map;

public class Administrador {
    private final Map<String, Cuenta> cuentas = new LinkedHashMap<>();

    public Cuenta abrirCuenta(String tipo, String numero, String idCliente,
                              String nombreCliente, double limiteCredito) {
        if (numero == null || cuentas.containsKey(numero.trim())) {
            throw new IllegalArgumentException("Ya existe una cuenta con este número");
        }
        Cliente cliente = new Cliente(idCliente, nombreCliente, limiteCredito);
        Cuenta cuenta;
        if (tipo.equals("AHORRO")) {
            cuenta = new CuentaAhorro(numero, cliente);
        } else if (tipo.equals("NOMINA")) {
            cuenta = new CuentaNomina(numero, cliente);
        } else {
            throw new IllegalArgumentException("Tipo de cuenta desconocido: " + tipo);
        }
        cuentas.put(cuenta.getNumeroCuenta(), cuenta);
        return cuenta;
    }

    public Cuenta buscarCuenta(String numero) {
        Cuenta cuenta = (numero == null) ? null : cuentas.get(numero.trim());
        if (cuenta == null) {
            throw new IllegalArgumentException("No existe la cuenta " + numero);
        }
        return cuenta;
    }

    public Map<String, Cuenta> getCuentas() { return cuentas; }

    public void cerrarCuenta(String numero) {
        buscarCuenta(numero).cerrar();
    }

    public Transaccion depositar(String numero, double monto) {
        return buscarCuenta(numero).depositar(monto);
    }

    public Transaccion retirar(String numero, double monto) throws SaldoInsuficienteException {
        return buscarCuenta(numero).retirar(monto);
    }

    public Transaccion revertirTransaccion(String numero, String idTransaccion)
            throws SaldoInsuficienteException {
        Cuenta cuenta = buscarCuenta(numero);
        return cuenta.revertir(buscarTransaccion(cuenta, idTransaccion));
    }

    public Transaccion marcarSospechosa(String numero, String idTransaccion) {
        Transaccion tx = buscarTransaccion(buscarCuenta(numero), idTransaccion);
        tx.setSospechosa(true);
        return tx;
    }

    public void cambiarLimiteCredito(String numero, double nuevoLimite) {
        buscarCuenta(numero).getTitular().setLimiteCredito(nuevoLimite);
    }

    public void verHistorial(String numero) {
        Cuenta cuenta = buscarCuenta(numero);
        System.out.println("\n--- Historial de la cuenta " + cuenta.getNumeroCuenta()
                + " (saldo: $" + String.format("%.2f", cuenta.getSaldo()) + ") ---");
        System.out.println("Transacciones VÁLIDAS:");
        if (cuenta.getHistorialValidas().isEmpty()) {
            System.out.println("  (ninguna)");
        }
        for (Transaccion tx : cuenta.getHistorialValidas()) {
            System.out.println("  " + tx);
        }
        System.out.println("Intentos INVÁLIDOS:");
        if (cuenta.getHistorialRechazadas().isEmpty()) {
            System.out.println("  (ninguno)");
        }
        for (Transaccion tx : cuenta.getHistorialRechazadas()) {
            System.out.println("  " + tx);
        }
        System.out.println("---------------------------------------");
    }

    private Transaccion buscarTransaccion(Cuenta cuenta, String idTransaccion) {
        for (Transaccion tx : cuenta.getHistorialValidas()) {
            if (tx.getIdTransaccion().equalsIgnoreCase(idTransaccion.trim())) {
                return tx;
            }
        }
        throw new IllegalArgumentException("No existe la transacción válida " + idTransaccion
                + " en la cuenta " + cuenta.getNumeroCuenta());
    }
}