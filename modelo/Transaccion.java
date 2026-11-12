package modelo;

public class Transaccion {
    private static int contador = 0;

    private final String idTransaccion;
    private final Cuenta cuenta;
    private final TipoTransaccion tipo;
    private final double monto;
    private final double comision;
    private final String motivoRechazo; // null cuando la transacción fue exitosa
    private boolean sospechosa;
    private boolean revertida;

    // Transacción exitosa
    public Transaccion(Cuenta cuenta, TipoTransaccion tipo, double monto, double comision) {
        this(cuenta, tipo, monto, comision, null);
    }

    // Transacción rechazada (intento inválido)
    public Transaccion(Cuenta cuenta, TipoTransaccion tipo, double monto, double comision, String motivoRechazo) {
        this.cuenta = cuenta;
        this.tipo = tipo;
        this.monto = monto;
        this.comision = comision;
        this.motivoRechazo = motivoRechazo;
        this.sospechosa = false;
        this.revertida = false;
        contador++;
        String prefijo = (motivoRechazo == null) ? "TX-" : "ERR-";
        this.idTransaccion = prefijo + String.format("%03d", contador);
    }

    public String getIdTransaccion() { return idTransaccion; }
    public Cuenta getCuenta() { return cuenta; }
    public TipoTransaccion getTipo() { return tipo; }
    public double getMonto() { return monto; }
    public double getComision() { return comision; }
    public String getMotivoRechazo() { return motivoRechazo; }

    public boolean isSospechosa() { return sospechosa; }
    public void setSospechosa(boolean sospechosa) { this.sospechosa = sospechosa; }

    public boolean isRevertida() { return revertida; }
    public void setRevertida(boolean revertida) { this.revertida = revertida; }

    @Override
    public String toString() {
        String base = "Tx: " + idTransaccion + " | Tipo: " + tipo
                + " | Monto: $" + String.format("%.2f", monto);
        if (motivoRechazo != null) {
            return base + " | Motivo del rechazo: " + motivoRechazo;
        }
        return base + " | Comisión: $" + String.format("%.2f", comision)
                + " | Cuenta: " + cuenta.getNumeroCuenta()
                + (sospechosa ? " [SOSPECHOSA]" : "")
                + (revertida ? " [REVERTIDA]" : "");
    }
}