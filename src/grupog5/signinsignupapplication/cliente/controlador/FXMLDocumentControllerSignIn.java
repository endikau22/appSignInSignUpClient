/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grupog5.signinsignupapplication.cliente.controlador;

import interfaz.Signable;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author 2dam
 */
public class FXMLDocumentControllerSignIn implements Initializable {
    
    private final Logger LOGGER = Logger.getLogger("grupog5.signinsignupapplication.cliente.controlador.FXMLDocumentControllerSignIn");
    private Stage stage;
    private final int MAX_LENGTH_USUARIO = 20;
    private final int MAX_LENGTH_CONTRASENA = 20;
    
    @FXML
    private TextField txtFieldUsuario;
    @FXML
    private PasswordField pswFieldContrasena;
    @FXML
    private Button btnEntrar;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    /**
     * Asigna al atributo Stage de la clase una Stage recibida como parámetro.
     * @param stage Una ventana JavaFx.
     */
    public void setStage(Stage stage) {
        this.stage = stage;    
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
    public void initStage(Parent root,Signable signable) {
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
        //Ejecutar método cambioTexto cuando el texto de el campo txtFieldUsuario cambie.
        txtFieldUsuario.textProperty().addListener(this::cambioTexto);
        //Ejecutar método cambioTexto cuando el texto de el campo pswFieldContraseña cambie.
        pswFieldContrasena.textProperty().addListener(this::cambioTexto);
        //Hace visible la ventana
        //Ejecutar método evento acción clickar botón
        //btnEntrar.setOnAction(this::accionBoton); LoHemos Hecho Con SceneBuider.
        //btnEntrar.setOnAction(this::eventoClickBoton);
        stage.show();
    }
    
    private void manejarInicioVentana(WindowEvent event){
        LOGGER.info("Iniciando ControllerSignIn::handleWindowShowing");
        ////El boton está inhabilitado al arrancar la ventana.
        btnEntrar.setDisable(true);  
    }
    
    private void cambioTexto(ObservableValue observable,String oldValue,String newValue){
        LOGGER.info("Iniciando ControllerSignIn::cambioTexto");
     
        if(this.txtFieldUsuario.getText().trim().equals("")||
                this.pswFieldContrasena.getText().trim().equals("")){
            btnEntrar.setDisable(true);  
        }else{
            btnEntrar.setDisable(false);
            btnEntrar.setTooltip(new Tooltip("Pulsa para acceder"));
        }
    }
    
    @FXML
    private void accionBoton(ActionEvent event){
        
    }

    public void inicializarComponentesVentana() {
        LOGGER.info("Iniciando ControllerSignIn::inicializarComponentesVentana");
        //Asignar un texto de fondo en el campo contraseña.
        pswFieldContrasena.setPromptText("Introduce tu contraseña.");
        txtFieldUsuario.setPromptText("Introduce tu nombre.");
    }
    
    private void muestraMensajeAlertaCamposVacios(){
            Alert alert = new Alert(Alert.AlertType.ERROR,"Los campos Usuario y contraseña \n"
                    + "deben estar informados.",ButtonType.OK);
            alert.showAndWait();
    }
}
