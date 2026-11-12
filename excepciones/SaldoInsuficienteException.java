package excepciones;

/**
 * Excepción propia (verificada): se lanza cuando una cuenta no tiene
 * saldo suficiente para completar una operación.
 */
public class SaldoInsuficienteException extends Exception {
    public SaldoInsuficienteException(String mensaje) {
        super(mensaje);
    }
}
