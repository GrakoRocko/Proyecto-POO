package modelo;

//Cuenta de nómina cobra una comisión fija por retiro
public class CuentaNomina extends Cuenta {
    private static final double COMISION_FIJA = 5.0;

    public CuentaNomina(String numeroCuenta, Cliente titular) {
        super(numeroCuenta, titular);
    }

    @Override
    public double calcularComision(double monto) {
        return COMISION_FIJA;
    }

    @Override
    public String describirCondiciones() {
        return "Cuenta de nómina --> comisión fija de $5.00 por retiro";
    }
}
