/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import interfaz.Signable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author endika
 */
public class FXMLDocumentSignUpController{
  /**
     * Logger para trazar los pasos del código.
     */
    private static final Logger LOGGER = Logger.getLogger("grupog5.signinsignupapplication.cliente.controlador.FXMLDocumentSignUpController");
    /**
     * Una ventana
     */
    private Stage stage;
    /**
     * Un signImplementation
     */
    private Signable signable;
    /**
     * Longitud máxima de los campos de texto permitida.
     */
    private static final int MAX_LENGTH = 20;
    /**
     * Longitud minima de los campos de texto permitida.
     */
    private static final int MIN_LENGTH = 4;
    
    @FXML private Button btnCrearCuenta;
    @FXML private Hyperlink hplIniciaSesion;
    
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

    public void initStage(Parent root) {
        LOGGER.log(Level.INFO,"Inicializando la ventana signUp. ");
        //Creación de  una nueva escena
        Scene scene = new Scene(root);
        //Añadir escena a la ventana
        stage.setScene(scene);
        //Asignación de un título a la ventana
        stage.setTitle("Sign Up");
        //Asignar tamaño fijo a la ventana
        stage.setResizable(false); 
        
        //Ejecutar método handleWindowShowing cuando se produzca el evento setOnShowing
        //Este evento se lanza cuando la ventana esta a punto de aparecer.
        stage.setOnShowing(this::manejarInicioVentana);
        //Ejecutar método evento acción clickar botón
        //btnCrearCuenta.setOnAction(this::accionBoton);
        //Ejecutar método Hyperlink clickado
        hplIniciaSesion.setOnAction(this::hyperlinkClickado);
        //Llamada al método inicializarComponenentesVentana del controlador de la ventana signIn.
        //inicializarComponentesVentana();
        //Hace visible la pantalla
        stage.show();
    }
    
    public void hyperlinkClickado(ActionEvent event){
        LOGGER.log(Level.INFO,"Evento del Hyperlink clickado. ");
        try{
           //abrirVentanaSignIn(); 
        }catch(Exception e){
            e.getMessage();
        }
        
    }
     /**
     * Acciones que se realizan en el momento previo a que se muestra la ventana.
     * @param event Evento de ventana.
     */
    private void manejarInicioVentana(WindowEvent event){
        LOGGER.info("Iniciando ControllerSignIn::handleWindowShowing");
        //El boton está inhabilitado al arrancar la ventana.
        btnCrearCuenta.setDisable(true);  
    }
    
}
