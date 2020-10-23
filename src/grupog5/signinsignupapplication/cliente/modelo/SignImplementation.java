/**
 * Contiene el modelo de la aplicación.
 */
package grupog5.signinsignupapplication.cliente.modelo;

import interfaz.Signable;
import mensaje.Accion;
import mensaje.Mensaje;
import user.User;

/**
 * Clase que implementa la Interface Signable.
 * @author Eneko, Endika, Markel
 */
public class SignImplementation implements Signable {

    /**
     * Comunica con la aplicación servidor para autenticar al usuario.
     * @param user Un usuario de la aplicación
     */
    @Override
    public void signIn(User user) {
        Mensaje mensaje = new Mensaje(user,Accion.SIGNIN);
    }

    /**
     * Comunica con la aplicación servidor para dar de alta al usuario.
     * @param user Un usuario de la aplicación
     */
    @Override
    public void signUp(User user) {
        Mensaje mensaje = new Mensaje(user,Accion.SIGNUP);
    }

    /**
     * Comunica con la aplicación servidor para comprobar la validación del usuario.
     * @param user Un usuario de la aplicación
     */
    @Override
    public void logOut(User user) {
        Mensaje mensaje = new Mensaje(user,Accion.LOGOUT);
    }

    
}
