package modelo;

/** Subclase: una cuenta de ahorro desincentiva los retiros con una comisión del 2%. */
public class CuentaAhorro extends Cuenta {
    private static final double PORCENTAJE_COMISION = 0.02;

    public CuentaAhorro(String numeroCuenta, Cliente titular) {
        super(numeroCuenta, titular);
    }

    @Override
    public double calcularComision(double monto) {
        return monto * PORCENTAJE_COMISION;
    }

    @Override
    public String describirCondiciones() {
        return "Cuenta de ahorro: comisión del 2% sobre cada retiro.";
    }
}
