/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
 *
 * @author 2dam
 */
public class ClientWorker extends Thread{
    private Mensaje mensaje;
    private Socket socketWorker;

    public Mensaje getMensaje() {
        return mensaje;
    }

    public void setMensaje(Mensaje mensaje) {
        this.mensaje = mensaje;
    }
    
    /**
     * Constructor de la clase. 
     * @param socket Un socket.
     */
    public ClientWorker (Socket socket){
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
        ObjectInputStream lecturaMensaje = null;
        try {
            salidaMensaje = new ObjectOutputStream(socketWorker.getOutputStream());
            //Escribe el mensaje en el tubo del socket.
            salidaMensaje.writeObject(mensaje);
            //Crear un objeto de la clase ObjectInputStream para recibir y leer datos.
            lecturaMensaje = new ObjectInputStream(socketWorker.getInputStream());
            //Guarda el objeto recibido en un atributo de la clase Mensaje, antes Castear de papá Object a Mensaje
            mensaje = (Mensaje) lecturaMensaje.readObject(); 
        } catch (Exception ex) {
            Logger.getLogger(ClientWorker.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try{
                salidaMensaje.close();
                lecturaMensaje.close();
            }catch(IOException e){
               Logger.getLogger(ClientWorker.class.getName()).log(Level.SEVERE, null, e); 
            }
        }
    }
}
