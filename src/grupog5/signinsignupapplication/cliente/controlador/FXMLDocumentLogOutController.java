/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grupog5.signinsignupapplication.cliente.controlador;

import interfaz.Signable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import user.User;


/**
 * FXML Controller class para la scena LogOut
 * @version 1.0
 * @since 23/10/2020
 * @author Eneko, Endika, Markel
 */
public class FXMLDocumentLogOutController extends Controller {
   /**
     * Logger para trazar los pasos del código.
     */
    private static final Logger LOGGER = Logger.getLogger("grupog5.signinsignupapplication.cliente.controlador.FXMLDocumentLogOutController");
    /**
     * Una ventana
     */
    private Stage stage;
    /**
     * Un signImplementation
     */
    private Signable signable;
    /**
     * Un usuario que ha accedidio a la aplicación.
     */
    private User usuario;
    /**
     * Botón Salida de la aplicación
     */
    @FXML private Button btnSalir;
    
    /**
     * Asigna al atributo Stage de la clase una Stage recibida como parámetro.
     * @param stage Una ventana JavaFx.
     */
    public void setStage(Stage stage) {
        this.stage = stage;    
    }

    /**
     * Recoge una instancia de Signable
     * @return Un signable
     */
    public Signable getSignable() {
        return signable;
    }

    /**
     * Asigna al atributo Signable un Signable.
     * @param signable Un signable.
     */
    public void setSignable(Signable signable) {
        this.signable = signable;
    }
    
    /**
     * Retorna una Stage (ventana).
     * @return Una Stage (ventana)
     */
    public Stage getStage(){
        return stage;
    }

    /**
     * Inicializa una Stage.
     * @param root Un nodo 
     */
    public void initStage(Parent root) {
        LOGGER.log(Level.INFO,"Inicializando la ventana logOut. ");
        //Creación de  una nueva escena
        Scene scene = new Scene(root);
        //Añadir escena a la ventana
        stage.setScene(scene);
        //Asignación de un título a la ventana
        stage.setTitle("Sign In");
        //Asignar tamaño fijo a la ventana
        stage.setResizable(false); 
  
        //Ejecutar método evento acción clickar botón
        btnSalir.setOnAction(this::accionBoton);
        inicializarComponentesVentana();
        //Hace visible la pantalla
        stage.show();
    }

       /**
     * Tratar el click del botón
     * @param event Un evento del botón
     */
    private void accionBoton(ActionEvent event){
        LOGGER.info("Iniciando ControllerLogOut.accionBoton");
        //Pasar datos del usuario para que el signable lo envíe a la base de datos para que registre la hora de salida.
        signable.logOut(usuario);
        //Abrir ventana signin
        
    }
    
}
