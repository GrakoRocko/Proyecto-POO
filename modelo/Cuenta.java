package modelo;
import java.util.ArrayList;
import java.util.List;

public class Cuenta {
    private String numeroCuenta;
    private Cliente titular;
    private double saldo;
    private boolean activa;
    private List<Transaccion> historial;

    public Cuenta(String numeroCuenta, Cliente titular) {
        this.numeroCuenta = numeroCuenta;
        this.titular = titular;
        this.saldo = 0.0;
        this.activa = true; // Al crearla, está abierta
        this.historial = new ArrayList<>();
    }

    public String getNumeroCuenta() { return numeroCuenta; }
    public Cliente getTitular() { return titular; }
    public double getSaldo() { return saldo; }
    public boolean isActiva() { return activa; }
    public List<Transaccion> getHistorial() { return historial; }

    public void setActiva(boolean activa) { this.activa = activa; }

    public void registrarTransaccion(Transaccion tx) {
        this.historial.add(tx);
    }

    public void depositar(double monto) {
        if (!activa) {
            System.out.println("Error: La cuenta " + numeroCuenta + " está cerrada.");
            return;
        }
        if (monto > 0) {
            this.saldo += monto;
        } else {
            System.out.println("El monto a depositar debe ser positivo.");
        }
    }

    public void retirar(double monto) {
        if (!activa) {
            System.out.println("Error: La cuenta " + numeroCuenta + " está cerrada.");
            return;
        }
        if (monto > 0 && this.saldo >= monto) {
            this.saldo -= monto;
        } else {
            System.out.println("Saldo insuficiente o monto inválido.");
        }
    }
}