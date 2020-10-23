/**
 * Contiene el modelo de la aplicación
 */
package grupog5.signinsignupapplication.cliente.modelo;

import interfaz.Signable;

/**
 * Factoria de la Interface Signable.
 * @author Eneko, Endika, Markel
 */
public class SignFactory {
    /**
     * Recoge una instancia de la clase que implementa la interface.
     * @return Un Signable.
     */
    public static Signable getSignable(){
        return new SignImplementation();
    }
}
