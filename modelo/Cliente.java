package modelo;

public class Cliente {
    private String idCliente;
    private String nombre;
    private double limiteCredito;

    public Cliente(String idCliente, String nombre, double limiteCredito) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.limiteCredito = limiteCredito;
    }

    public String getIdCliente() { return idCliente; }
    public String getNombre() { return nombre; }
    public double getLimiteCredito() { return limiteCredito; }
    
    public void setLimiteCredito(double limiteCredito) { 
        this.limiteCredito = limiteCredito; 
    }
}