package modelo;

public class Transaccion {
    private String idTransaccion;
    private Cuenta cuenta;
    private double monto;
    private String tipo; 
    private boolean esSospechosa;

    public Transaccion(String idTransaccion, Cuenta cuenta, double monto, String tipo) {
        this.idTransaccion = idTransaccion;
        this.cuenta = cuenta;
        this.monto = monto;
        this.tipo = tipo;
        this.esSospechosa = false;
    }

    public String getIdTransaccion() { return idTransaccion; }
    public double getMonto() { return monto; }
    public String getTipo() { return tipo; }
    
    public void setSospechosa(boolean estado) { this.esSospechosa = estado; }
    public boolean isSospechosa() { return esSospechosa; }

    @Override
    public String toString() {
        String estadoSospechoso = esSospechosa ? " [SOSPECHOSA]" : "";
        return "Tx: " + idTransaccion + " | Tipo: " + tipo + " | Monto: $" + monto + " | Cuenta: " + cuenta.getNumeroCuenta() + estadoSospechoso;
    }
}