/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import excepciones.ExcepcionPasswdIncorrecta;
import excepciones.ExcepcionUserNoExiste;
import interfaz.Signable;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import user.User;

/**
 * FXML Controller class
 * @version 1.0
 * @since 27/10/2020
 * @author Eneko, Endika, Markel
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
    @FXML private TextField txtFieldNombre;
    @FXML private TextField txtFieldEmail;
    @FXML private TextField txtFieldUsuario;
    @FXML private PasswordField pswFieldContrasena;
    @FXML private PasswordField pswFieldRepetirContrasena;
    @FXML private Label lblMensajeError; 
    
    
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
        btnCrearCuenta.setOnAction(this::accionBoton);
        //Ejecutar método Hyperlink clickado
        hplIniciaSesion.setOnAction(this::hyperlinkClickado);
        //Llamada al método inicializarComponenentesVentana del controlador de la ventana signIn.
        inicializarComponentesVentana();
        //Hace visible la pantalla
        stage.show();
    }
    
    /**
     * Evento del Hyperlink clickado. Redirige a la escena SignUp.
     * @param event Un evento del Hyperlink.
     */
    public void hyperlinkClickado(ActionEvent event){
        LOGGER.log(Level.INFO,"Evento del Hyperlink clickado de la clase controlador SignUp. ");
        try{
            //Llamada al método de esta clase.
           abrirVentanaSignIn(); 
        }catch(Exception e){
            //Si se produce un error mostrar un mensaje.
            lblMensajeError.setText("Intentalo mas tarde. Fallo la conexión");
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
    
    /**
     * Inicializa los componentes de la ventana.
     */
    public void inicializarComponentesVentana() {
        LOGGER.info("Iniciando ControllerSignIn::inicializarComponentesVentana");
        //Asignar un texto de fondo en el campo contraseña, se muestra cuando el campo está desenfocado.
        pswFieldContrasena.setPromptText("Introduce tu contraseña. ("+MIN_LENGTH+ " a "+MAX_LENGTH+" caracteres)");
        //Asignar un texto de fondo en el campo repetir contraseña, se muestra cuando el campo está desenfocado.
        pswFieldRepetirContrasena.setPromptText("Introduce tu contraseña. ("+MIN_LENGTH+ " a "+MAX_LENGTH+" caracteres)");
        //Asignar texto cuando el campo está desenfocado.
        txtFieldUsuario.setPromptText("Introduce tu nombre de usuario. ("+MIN_LENGTH+ " a "+MAX_LENGTH+" caracteres)");
        //Asignar texto en el textField Nombre cuando el campo está desenfocado.
        txtFieldNombre.setPromptText("Introduce tu nombre.");
        //Asignar texto en el textField email cuando el campo está desenfocado.
        txtFieldEmail.setPromptText(" @ Introduce tu email.");     
    }

    /**
     * Carga la ventana signin para que el usuario se loguee.
     */
    private void abrirVentanaSignIn() {
        LOGGER.log(Level.INFO,"Abriendo ventana SignUp. ");
        try{
            //New FXMLLoader Añadir el fxml de logout que es la ventana a la que se redirige si todo va bien
            FXMLLoader loader = new FXMLLoader(getClass().
                getResource("/vista/FXMLDocumentSignIn.fxml"));
            //Parent es una clase gráfica de nodos xml son nodos.
            Parent root = (Parent)loader.load();
            //Relacionamos el documento FXML con el controlador que le va a controlar.
            FXMLDocumentSignInController controladorSignIn = (FXMLDocumentSignInController)loader.getController();
            //Llamada al método setStage del controlador de la ventana signIn. Pasa la ventana.
            Scene scene = new Scene(root);
            //Añadir escena a la ventana
            stage.setScene(scene);
            //controladorSignUp.setStage(stage);
            //Llamada al método setSignable del controlador de la ventana signIn. Pasa instancia SignImplementation.
            controladorSignIn.setSignable(signable);
            //Llamada al método initStage del controlador de la ventana signIn. Pasa el documento fxml en un nodo.
            controladorSignIn.initStage(root);
        //Error al cargar la nueva escenamostrar mensaje.
        }catch(IOException e){
            lblMensajeError.setText("Se ha producido un error. Lo sentimos. Inténtalo mas tarde");
        }
    }
    
    /**
     * Tratar el click del botón
     * @param event Un evento del botón
     */
    private void accionBoton(ActionEvent e){
        LOGGER.info("Iniciando ControllerSignIn.accionBoton");
        //Si cumplen las condiciones enviar datos.
        //El campo usuario tiene una longitud que no está entre 4 y 20 caracteres
        if(! maximoCaracteres(txtFieldUsuario)|| ! minimoCaracteres(txtFieldUsuario)){
            LOGGER.info("Longitud del textfield erronea ControllerSignIn.accionBoton");
            //Mostrar un mensaje de error en el label.
            lblErrorUsuarioContrasena.setText("Los campos Usuario y contraseña deben tener entre "+MIN_LENGTH+ " a "+MAX_LENGTH+" caracteres.");
            //El foco lo pone en el campo usuario
            txtFieldUsuario.requestFocus();
            //Selecciona el texto para borrar.
            txtFieldUsuario.selectAll();
            //El campo contraseña tiene una longitud que no está entre 4 y 20 caracteres
        }else if (!maximoCaracteres((TextField)pswFieldContrasena) ||! minimoCaracteres((TextField)pswFieldContrasena)){
            LOGGER.info("Longitud del passwordField erronea ControllerSignIn.accionBoton");
            //Mostrar un mensaje de error en el label.
            lblErrorUsuarioContrasena.setText("Los campos Usuario y contraseña deben tener entre "+MIN_LENGTH+ " a "+MAX_LENGTH+" caracteres.");
            pswFieldContrasena.requestFocus();
            pswFieldContrasena.selectAll();
            //Hay espacios en blanco en el campo usuario
        }else if(!comprobarEspaciosBlancos(txtFieldUsuario)){
            LOGGER.info("Hay espacios en blanco en el texfield ControllerSignIn.accionBoton");
            //Mostrar un mensaje de error en el label.
            lblErrorUsuarioContrasena.setText("Los campos Usuario y contraseña no deben tener espacios.");           
            System.out.println(pswFieldContrasena.getText());
            txtFieldUsuario.requestFocus();
            txtFieldUsuario.selectAll();
            //Hay espacios en blanco en la contraseña
        }else if(! comprobarEspaciosBlancos((TextField)pswFieldContrasena)){
            LOGGER.info("Hay espacios en blanco en el passwordfield ControllerSignIn.accionBoton");
            //Mostrar un mensaje de error en el label.
            lblErrorUsuarioContrasena.setText("Los campos Usuario y contraseña no deben tener espacios.");
            pswFieldContrasena.requestFocus();
            pswFieldContrasena.selectAll();
        }else
            //Todos los campos cumplen la condición validar datos en la base de datos
            enviarDatosServidorBBDD();       
    }
    
    /**
     * Enviar datos del usuario a la BBDD con el objeto signable
     */
    private void enviarDatosServidorBBDD() {
        LOGGER.info("Iniciando ControllerSignIn.EnviarDatosServidorBBDD");
        User myUser = new User (txtFieldUsuario.getText(),pswFieldContrasena.getText());
        try {
            //Pasa el usuario a la instancia signable a su método sign in.
            signable.signIn(myUser);
            //Llamada al método para redireccionar aplicación a la siguiente ventana.
            abrirVentanaLogOut();              
        } catch (ExcepcionUserNoExiste ex1) {
            //Colocar el texto de la excepción en el label
            lblErrorUsuarioContrasena.setText(ex1.getMessage());
            //VAciar campos de texto
            txtFieldUsuario.setText("");
            pswFieldContrasena.setText("");
            Logger.getLogger(FXMLDocumentSignInController.class.getName()).log(Level.SEVERE, null, ex1);
        } catch (ExcepcionPasswdIncorrecta ex2) {
            //Colocar el texto de la excepción en el label
            lblErrorUsuarioContrasena.setText(ex2.getMessage());
            //VAciar campos de texto
            txtFieldUsuario.setText("");
            pswFieldContrasena.setText("");
            Logger.getLogger(FXMLDocumentSignInController.class.getName()).log(Level.SEVERE, null, ex2);
        } catch (Exception ex3){
            //Colocar el texto de la excepción en el label
            lblErrorExcepcion.setText("Se ha producido un error. Lo sentimos. Inténtalo mas tarde");
            //VAciar campos de texto
            txtFieldUsuario.setText("");
            pswFieldContrasena.setText("");
            Logger.getLogger(FXMLDocumentSignInController.class.getName()).log(Level.SEVERE, null, ex3);
        }
    }
    
}
