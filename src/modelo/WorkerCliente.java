
package modelo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import mensaje.Mensaje;

/**
 * Encargada de comunicar a la aplicación cliente con el servidor a traves del socket. Es un hilo.
 * @since 30/10/2020
 * @version 1.0
 * @author Endika, Markel
 */
public class WorkerCliente extends Thread{
    /**
     * Atributo Logger para rastrear los pasos de ejecución del programa.
     */
    private static final Logger LOGGER = 
            Logger.getLogger("grupog5.signinsignupapplication.cliente.workerCliente.thread");
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
     * @param mensaje Un mensaje. Compuesto de un usuario y  una acción a realizar.
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
    }
    /**
     * Método que ejecuta el hilo. Arranca cuando un objeto de la clase ejecuta el método start(). 
     */
    public void run(){       
        //Mensaje Logger al acceder al método
        LOGGER.log(Level.INFO, "Método run del hilo de la clase WorkerCliente");
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
                if(salidaMensaje!=null && lecturaMensaje != null){
                    salidaMensaje.close();
                    lecturaMensaje.close();
                }
                socketWorker.close();
            }catch(IOException e){
               Logger.getLogger(WorkerCliente.class.getName()).log(Level.SEVERE, null, e); 
            }
        }
    }
}
