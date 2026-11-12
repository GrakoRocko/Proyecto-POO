package modelo;

import excepciones.SaldoInsuficienteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cuenta {
    private final String numeroCuenta;
    private final Cliente titular;
    private double saldo;
    private boolean activa;
    private final List<Transaccion> historialValidas;
    private final List<Transaccion> historialRechazadas;

    public Cuenta(String numeroCuenta, Cliente titular) {
        if (numeroCuenta == null || numeroCuenta.trim().isEmpty()) {
            throw new IllegalArgumentException("El número de cuenta no puede estar vacío");
        }
        if (titular == null) {
            throw new IllegalArgumentException("La cuenta debe tener un titular.");
        }
        this.numeroCuenta = numeroCuenta.trim();
        this.titular = titular;
        this.saldo = 0.0;
        this.activa = true;
        this.historialValidas = new ArrayList<>();
        this.historialRechazadas = new ArrayList<>();
    }

    public String getNumeroCuenta() { return numeroCuenta; }
    public Cliente getTitular() { return titular; }
    public double getSaldo() { return saldo; }
    public boolean isActiva() { return activa; }

    public List<Transaccion> getHistorialValidas() {
        return Collections.unmodifiableList(historialValidas);
    }

    public List<Transaccion> getHistorialRechazadas() {
        return Collections.unmodifiableList(historialRechazadas);
    }

    public void cerrar() { this.activa = false; }

    // Comisión que cobra la cuenta por un retiro
    public double calcularComision(double monto) {
        return 0.0;
    }

    public String describirCondiciones() {
        return "Cuenta básica sin comisión por retiros";
    }

    public Transaccion depositar(double monto) {
        validarOperacion(TipoTransaccion.DEPOSITO, monto);
        saldo += monto;
        Transaccion tx = new Transaccion(this, TipoTransaccion.DEPOSITO, monto, 0.0);
        historialValidas.add(tx);
        return tx;
    }

    public Transaccion retirar(double monto) throws SaldoInsuficienteException {
        validarOperacion(TipoTransaccion.RETIRO, monto);
        double comision = calcularComision(monto);
        double total = monto + comision;
        if (saldo < total) {
            String motivo = "Saldo insuficiente (se requieren $" + String.format("%.2f", total)
                    + " y hay $" + String.format("%.2f", saldo) + ").";
            registrarRechazo(TipoTransaccion.RETIRO, monto, comision, motivo);
            throw new SaldoInsuficienteException(motivo);
        }
        saldo -= total;
        Transaccion tx = new Transaccion(this, TipoTransaccion.RETIRO, monto, comision);
        historialValidas.add(tx);
        return tx;
    }

    // Revierte una transacción
    public Transaccion revertir(Transaccion original) throws SaldoInsuficienteException {
        if (original == null || original.getCuenta() != this
                || !historialValidas.contains(original)) {
            throw new IllegalArgumentException("La transacción no se pudo completar");
        }
        validarOperacion(TipoTransaccion.REVERSO, original.getMonto());
        if (original.getTipo() == TipoTransaccion.REVERSO) {
            rechazarEstado(original, "No se puede revertir");
        }
        if (original.isRevertida()) {
            rechazarEstado(original, "La transacción " + original.getIdTransaccion()
                    + " ya fue revertida");
        }

        if (original.getTipo() == TipoTransaccion.DEPOSITO) {
            if (saldo < original.getMonto()) {
                String motivo = "Saldo insuficiente para revertir el depósito la cuenta solo tiene $"
                        + String.format("%.2f", saldo) + ")";
                registrarRechazo(TipoTransaccion.REVERSO, original.getMonto(), 0.0, motivo);
                throw new SaldoInsuficienteException(motivo);
            }
            saldo -= original.getMonto();
        } else {
            saldo += original.getMonto() + original.getComision();
        }

        original.setRevertida(true);
        Transaccion reverso = new Transaccion(this, TipoTransaccion.REVERSO, original.getMonto(), 0.0);
        historialValidas.add(reverso);
        return reverso;
    }

    private void validarOperacion(TipoTransaccion tipo, double monto) {
        if (!activa) {
            String motivo = "La cuenta " + numeroCuenta + " está cerrada";
            registrarRechazo(tipo, monto, 0.0, motivo);
            throw new IllegalStateException(motivo);
        }
        if (!(monto > 0) || Double.isInfinite(monto)) {
            String motivo = "El monto debe ser un número positivo";
            registrarRechazo(tipo, monto, 0.0, motivo);
            throw new IllegalArgumentException(motivo);
        }
    }

    private void rechazarEstado(Transaccion original, String motivo) {
        registrarRechazo(TipoTransaccion.REVERSO, original.getMonto(), 0.0, motivo);
        throw new IllegalStateException(motivo);
    }

    private void registrarRechazo(TipoTransaccion tipo, double monto, double comision, String motivo) {
        historialRechazadas.add(new Transaccion(this, tipo, monto, comision, motivo));
    }
}