/**
 * Contiene el modelo de la aplicación cliente.
 */
package modelo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import mensaje.Mensaje;

/**
 * Encargada de comunicar a la aplicación cliente con el servidor a traves del socket.
 * @since 30/10/2020
 * @version 1.0
 * @author Endika, Markel
 */
public class WorkerCliente extends Thread{
    /**
     * Un mensaje. Contiene un usuario y una acción.
     */
    private Mensaje mensaje;
    /**
     * Un socket. Extremo del socket en el lado servidor, se comunica con el cliente a traves de él.
     */
    private Socket socketWorker;

    /**
     * Recoger un mensaje.
     * @return Un mensaje.
     */
    public Mensaje getMensaje() {
        return mensaje;
    }

    /**
     * Asigna un mensaje pasado como parámetro al atributo de la clase.
     * @param mensaje 
     */
    public void setMensaje(Mensaje mensaje) {
        this.mensaje = mensaje;
    }
    
    /**
     * Constructor de la clase. Asigna el socket recibido como parámetro al atributo de la clase.
     * @param socket Un socket.
     */
    public WorkerCliente (Socket socket){
        //Guardar el parámetro en el atributo de la clase.
        socketWorker = socket;
        //Iniciar el Hilo. Llamada al método run del hilo.
        
    }
    /**
     * Método que ejecuta el hilo.
     */
    public void run(){
        //Crear un objeto de la clase ObjectOutputStream para escritura y salida de datos.
        ObjectOutputStream salidaMensaje = null;
        //Crear un objeto de la clase ObjectInput para lectura de datos.
        ObjectInputStream lecturaMensaje = null;
        //Toda operación de entrada y salida de datos lanza una excepción de entrada salida.
        try {
            //Inicializa el tubo por el que envia los datos a traves del socket.
            salidaMensaje = new ObjectOutputStream(socketWorker.getOutputStream());
            //Escribe el mensaje en el tubo del socket.
            salidaMensaje.writeObject(mensaje);
            //Crear un objeto de la clase ObjectInputStream para recibir y leer datos.
            lecturaMensaje = new ObjectInputStream(socketWorker.getInputStream());
            //Guarda el objeto recibido en un atributo de la clase Mensaje, antes Castear de Object a Mensaje
            mensaje = (Mensaje) lecturaMensaje.readObject(); 
        } catch (Exception ex) {
            Logger.getLogger(WorkerCliente.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try{
                //Cerrar los flujos de datos y socket.
                salidaMensaje.close();
                lecturaMensaje.close();
                socketWorker.close();
            }catch(IOException e){
               Logger.getLogger(WorkerCliente.class.getName()).log(Level.SEVERE, null, e); 
            }
        }
    }
}
