/**
 * Contiene el modelo de la aplicación.
 */
package modelo;

import excepciones.ExcepcionPasswdIncorrecta;
import excepciones.ExcepcionUserNoExiste;
import excepciones.ExcepcionUserYaExiste;
import interfaz.Signable;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import mensaje.Accion;
import mensaje.Mensaje;
import user.User;

/**
 * Clase que implementa la Interface Signable.
 * @author Eneko, Endika, Markel
 */
public class SignImplementation implements Signable {
    
    private Socket unSocket;

    /**
     * Comunica con la aplicación servidor para autenticar al usuario.
     * @param user Un usuario de la aplicación
     * @throws excepciones.ExcepcionPasswdIncorrecta
     */
    @Override
    public User signIn(User user) throws ExcepcionPasswdIncorrecta,ExcepcionUserNoExiste{
        Mensaje mensaje = new Mensaje(user,Accion.SIGNIN);
        this.enviarInformacion(mensaje);
        return user;
        
    }

    /**
     * Comunica con la aplicación servidor para dar de alta al usuario.
     * @param user Un usuario de la aplicación
     * @throws excepciones.ExcepcionUserYaExiste
     */
    @Override
    public void signUp(User user) throws ExcepcionUserYaExiste{
        Mensaje mensaje = new Mensaje(user,Accion.SIGNUP);
        this.enviarInformacion(mensaje);
    }

    /**
     * Comunica con la aplicación servidor para comprobar la validación del usuario.
     * @param user Un usuario de la aplicación
     */
    @Override
    public void logOut(User user) {
        Mensaje mensaje = new Mensaje(user,Accion.LOGOUT);
        this.enviarInformacion(mensaje);
    }

    public  void setSocket(){
        this.unSocket = new Socket();
    }
    
    public void enviarInformacion(Mensaje mensaje){
        try {
            ObjectOutputStream salidaMensaje = new ObjectOutputStream(unSocket.getOutputStream());
            salidaMensaje.writeObject(mensaje);
        } catch (IOException ex) {
            Logger.getLogger(SignImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void recibirInformacion(){
        
    }
}
