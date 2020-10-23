/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grupog5.signinsignupapplication.cliente.controlador;

import interfaz.Signable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author endika
 */
public class Controller {
    
    private static final Logger LOGGER = Logger.getLogger("grupog5.signinsignupapplication.cliente.controlador.Controller");
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
        LOGGER.log(Level.INFO,"Inicializando la ventana login. ");
        //Creación de  una nueva escena
        Scene scene = new Scene(root);
        //Añadir escena a la ventana
        stage.setScene(scene);
        //Asignación de un título a la ventana
        stage.setTitle("Sign In");
        //Asignar tamaño fijo a la ventana
        stage.setResizable(false); 
        
        //Ejecutar método handleWindowShowing cuando se produzca el evento setOnShowing
        //Este evento se lanza cuando la ventana esta a punto de aparecer.
        stage.setOnShowing(this::manejarInicioVentana);
        //Hace visible la pantalla
        stage.show();
    }
    
    
    public void inicializarComponentesVentana() {

    }
    
    /**
     * Acciones que se realizan en el momento previo a que se muestra la ventana.
     * @param event Evento de ventana.
     */
    private void manejarInicioVentana(WindowEvent event){

    }
               
    private void muestraMensajeAlertaLongitudCampo(){
            Alert alert = new Alert(Alert.AlertType.ERROR,"Los campos Usuario y contraseña \n"
                    + "deben tener una longitud entre "+MIN_LENGTH+" y "+MAX_LENGTH+".",ButtonType.OK);
            alert.showAndWait();
    }
    
    private boolean comprobarEspaciosBlancos(TextField field) {
        LOGGER.info("Iniciando ControllerSignIn.ComprobaEspaciosBlancos");
        String textoSinEspacios = field.getText().trim();
        Boolean hayEspacios = false;
        for(Character t:textoSinEspacios.toCharArray()){
            if(t.equals(' '))
                hayEspacios = true;
        }
        return hayEspacios;
    }

    private void muestraMensajeAlertaEspacios() {
        LOGGER.info("Iniciando ControllerSignIn.muestraMensajeAlertaEspacios");
        Alert alert = new Alert(Alert.AlertType.ERROR,"Los campos Usuario y contraseña \n"
                    + " no deben tener espacios.",ButtonType.OK);
            alert.showAndWait();
    }

    private void enviarDatosServidorBBDD() {

    }

    private boolean maximoCaracteres(TextField field) {
        LOGGER.info("Iniciando ControllerSignIn.maximoCaracteres");
        boolean numeroCaracteresCorrectos = true;
        if(field.getText().trim().length()>MAX_LENGTH){
                numeroCaracteresCorrectos = false;
                muestraMensajeAlertaLongitudCampo();
        }
        return numeroCaracteresCorrectos;
    }

    private boolean minimoCaracteres(TextField field) {
        LOGGER.info("Iniciando ControllerSignIn.minimoCaracteres");
        boolean numeroCaracteresCorrectos = true;
        if(field.getText().trim().length()< MIN_LENGTH){
                numeroCaracteresCorrectos = false;
                muestraMensajeAlertaLongitudCampo();
        }
        return numeroCaracteresCorrectos;
    }
}
