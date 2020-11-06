/**
 * Contiene el modelo de la aplicación cliente.
 */
package modelo;

import excepciones.ExcepcionPasswdIncorrecta;
import excepciones.ExcepcionUserNoExiste;
import excepciones.ExcepcionUserYaExiste;
import interfaz.Signable;
import java.io.IOException;
import java.net.Socket;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import mensaje.Accion;
import mensaje.Mensaje;
import user.User;

/**
 * Clase que implementa la Interface Signable.
 * @version 1.0
 * @since 26/10/2020
 * @author Eneko, Endika, Markel
 */
public class SignImplementation implements Signable {
    /**
     * Logger para controlar la actividad dentro de la clase SignImplemetation
     */
    private static final Logger LOGGER = Logger.getLogger("grupog5.signinsignupapplication.cliente.modelo.SignImplementation.");
    /**
     * REsourceBundle Clase lectora de Strings de un fichero. Recoger dirección Ip del cliente
     */
    private static final String DIRECCION_IP = ResourceBundle.getBundle("socket.infoSocket").getString("ipLocal");
    /**
     * Numero de puerto libre para la comunicación con el servidor por medio de un socket.
     */
    private static final Integer NUMERO_PUERTO = 
            Integer.parseInt(ResourceBundle.getBundle("socket.infoSocket").getString("portNumber"));
    /**
     * Un Socket para abrir extremo de comunicación con el cliente
     */
    private Socket unSocket;
    /**
     * Mensaje que envia la aplicación cliente a la aplicación servidor.
     */
    private Mensaje mensaje;
    /**
     * Inicia el Socket.
     * @throws IOException 
     */
    private void setSocket() throws IOException{
        //Inicializa el socket con la dirección y el número de puerto establecidos..
        unSocket = new Socket(DIRECCION_IP,NUMERO_PUERTO);
    }
    
    /**
     * Comunica con la aplicación servidor para autenticar al usuario.
     * @param user Un usuario de la aplicación
     * @throws java.io.IOException
     * @throws excepciones.ExcepcionPasswdIncorrecta
     */
    @Override
    public User signIn(User user)throws Exception,ExcepcionPasswdIncorrecta, ExcepcionUserNoExiste {
        //Mensaje informativo del logger de ejecución del método
        LOGGER.info("Metodo SignIn de la clase SignImplementation.");
        //Almacenar en el atributo mensaje un nuevo meensaje. Este será enviado al servidor.
        mensaje = new Mensaje(user,Accion.SIGNIN);
        //Inicializa el socket con los valores necesarios.
        this.setSocket();
        //Crear el hilo.
        ClientWorker workerCliente = new ClientWorker(unSocket);
        //Pasar el mensaje a la clase hilo para que lo envie a la base de datos
        workerCliente.setMensaje(mensaje); 
        //Iniciar ejecución del Hilo
        workerCliente.start();               
        //Esperar a que el hilo acabe para seguir con la ejecución.
        workerCliente.join();
        //Recoge el mensaje del Hilo que ha recibido del servidor con la respuesta.
        mensaje = workerCliente.getMensaje();
        //Cerrar el socket 
        unSocket.close();
        //Mirar las distintas opciones de mensaje recibidas.
        switch (mensaje.getAccion()) {
        //Leer los mensajes si es ok todo está bien sino lanzar excepciones.
            case OK:
                //Devolver usuario para logout posterior.
                return user;
            case USUARIO_NO_EXISTE:
                //Lanzar excepcion de usuario no existe
                throw new ExcepcionUserNoExiste();
            case PASSWORD_INCORRECTA:
                //lanzar excepción de contraseña incorrecta.
                throw new ExcepcionPasswdIncorrecta();
            default:
                //Si no es ninguno de los anteriores lanzar la excepción general.
                throw new Exception(); 
        } 
    }

    /**
     * Comunica con la aplicación servidor para dar de alta al usuario.
     * @param user Un usuario de la aplicación
     * @throws excepciones.ExcepcionUserYaExiste
     */
    @Override
    public void signUp(User user) throws Exception,ExcepcionUserYaExiste{
        //Mensaje informativo del logger de ejecución del método
        LOGGER.info("Metodo SignUp de la clase SignImplementation.");
        //Almacenar en el atributo mensaje un nuevo meensaje. Este será enviado al servidor.
        mensaje = new Mensaje(user,Accion.SIGNUP);
        //Inicializa el socket con los valores necesarios.
        this.setSocket();
        //Crear el hilo.
        ClientWorker workerCliente = new ClientWorker(unSocket);
        //Pasar el mensaje a la clase hilo para que lo envie a la base de datos
        workerCliente.setMensaje(mensaje); 
        //Iniciar ejecución del Hilo
        workerCliente.start();       
        //Esperar a que el hilo acabe para seguir con la ejecución.
        workerCliente.join();
        //Recoge el mensaje del Hilo que ha recibido del servidor con la respuesta.
        mensaje = workerCliente.getMensaje();
        //Cerrar el socket 
        unSocket.close();
        //Mirar las distintas opciones de mensaje recibidas.
        switch (mensaje.getAccion()) {
            case OK:
                //Todo bien cerrar el método sin errores.
                break;
            case USUARIO_YA_EXISTE:
                throw new ExcepcionUserYaExiste();
            //Por defecto se lanza excepcion general
            default:
                throw new Exception(); 
        }
    }

    /**
     * Comunica con la aplicación servidor para comprobar la validación del usuario.
     * @param user Un usuario de la aplicación
     * @throws java.lang.Exception 
     */
    @Override
    public void logOut(User user) throws Exception{
        //Mensaje informativo del logger de ejecución del método
        LOGGER.info("Metodo LogOut de la clase SignImplementation.");
        //Guardar en el atributo mensaje el mensaje recibido con la llamada al método de la clase recibir información.
        mensaje = new Mensaje(user,Accion.LOGOUT);
        //Inicializa el socket con los valores necesarios.
        this.setSocket();
        //Crear el hilo.
        ClientWorker workerCliente = new ClientWorker(unSocket);
        //Pasar el mensaje a la clase hilo para que lo envie a la base de datos
        workerCliente.setMensaje(mensaje); 
        //Iniciar ejecución del Hilo
        workerCliente.start();       
        //Esperar a que el hilo acabe para seguir con la ejecución.
        workerCliente.join();
        //Recoge el mensaje del Hilo que ha recibido del servidor con la respuesta.
        mensaje = workerCliente.getMensaje();
        unSocket.close();
    }   
}
