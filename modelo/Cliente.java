package modelo;

public class Cliente {
    private final String idCliente;
    private final String nombre;
    private double limiteCredito;

    public Cliente(String idCliente, String nombre, double limiteCredito) {
        if (idCliente == null || idCliente.trim().isEmpty()) {
            throw new IllegalArgumentException("Ingresa el ID del cliente");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("Ingresa el nombre del cliente");
        }
        validarLimite(limiteCredito);
        this.idCliente = idCliente.trim();
        this.nombre = nombre.trim();
        this.limiteCredito = limiteCredito;
    }

    private static void validarLimite(double limite) {
        if (!(limite >= 0) || Double.isInfinite(limite)) {
            throw new IllegalArgumentException("Ingresa un limite de credito valido");
        }
    }

    public String getIdCliente() { return idCliente; }
    public String getNombre() { return nombre; }
    public double getLimiteCredito() { return limiteCredito; }

    public void setLimiteCredito(double limiteCredito) {
        validarLimite(limiteCredito);
        this.limiteCredito = limiteCredito;
    }
}