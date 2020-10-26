/**
 * Contiene el modelo de datos de la aplicación cliente.
 */
package controlador;

import excepciones.ExcepcionPasswdIncorrecta;
import excepciones.ExcepcionUserNoExiste;
import interfaz.Signable;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import user.User;

/**
 * FXML Controller class para la scena SignIn
 * @version 1
 * @since 23/10/2020
 * @author Eneko, Endika, Markel
 */
public class FXMLDocumentSignInController {
    /**
     * Logger para trazar los pasos del código.
     */
    private static final Logger LOGGER = Logger.getLogger("grupog5.signinsignupapplication.cliente.controlador.FXMLDocumentControllerSignIn");
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
     * Campo de texto Usuario
     */
    @FXML private TextField txtFieldUsuario;
    /**
     * Campo de texto contraseña 
     */
    @FXML private PasswordField pswFieldContrasena;
    /**
     * Botón Acceso a la aplicación
     */
    @FXML private Button btnEntrar;
    /**
     * Hyperlink de registro.
     */
    @FXML private Hyperlink hplRegistrate;
    
    /**
     * Label Mensajes Error usuario,contraseña.
     */ 
    @FXML private Label lblErrorUsuarioContrasena;
    
    /**
     * LAbel Mensajes Error genéricos
     */
    @FXML private Label lblErrorExcepcion;
    


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
        //Ejecutar método cambioTexto cuando el texto de el campo txtFieldUsuario cambie.
        txtFieldUsuario.textProperty().addListener(this::cambioTexto);
        //Ejecutar método cambioTexto cuando el texto de el campo pswFieldContraseña cambie.
        pswFieldContrasena.textProperty().addListener(this::cambioTexto);
        //Ejecutar método evento acción clickar botón
        btnEntrar.setOnAction(this::accionBoton);
        //Ejecutar método Hyperlink clickado
        hplRegistrate.setOnAction(this::hyperlinkClickado);
        //Llamada al método inicializarComponenentesVentana del controlador de la ventana signIn.
        inicializarComponentesVentana();
        //Hace visible la pantalla
        stage.show();
    }
    
    /**
     * Inicializa los componentes de la ventana.
     */
    public void inicializarComponentesVentana() {
        LOGGER.info("Iniciando ControllerSignIn::inicializarComponentesVentana");
        //Asignar un texto de fondo en el campo contraseña, se muestra cuando el campo está desenfocado.
        pswFieldContrasena.setPromptText("Introduce tu contraseña. (4 a 20 caracters)");
        //Asignar texto cuando el campo está desenfocado.
        txtFieldUsuario.setPromptText("Introduce tu nombre de usuario. (4 a 20 caracters)");
    }
    
    /**
     * Acciones que se realizan en el momento previo a que se muestra la ventana.
     * @param event Evento de ventana.
     */
    private void manejarInicioVentana(WindowEvent event){
        LOGGER.info("Iniciando ControllerSignIn::handleWindowShowing");
        //El boton está inhabilitado al arrancar la ventana.
        btnEntrar.setDisable(true);  
    }
    
    /**
     * Registra los cambios de texto en los textField o passwordField.
     * @param observable 
     * @param oldValue Valor anterior al lanzamiento del evento.
     * @param newValue Nuevo valor tras el cambio.
     */
    private void cambioTexto(ObservableValue observable,String oldValue,String newValue){
        LOGGER.info("Iniciando ControllerSignIn::cambioTexto");
        //Vaciar label de error tras escribir en cualquiera de los campos.
        lblErrorUsuarioContrasena.setText("");
        //Vaciar label de error tras escribir en cualquiera de los campos.
        lblErrorExcepcion.setText("");
        //Si está vacío cualquiera de los dos campos.
        if(this.txtFieldUsuario.getText().trim().equals("")||
                this.pswFieldContrasena.getText().trim().equals("")){
            //Deshabilitar botón
            btnEntrar.setDisable(true);  
        }else{//Los dos campos están informados.
            //Habilitar botón 
            btnEntrar.setDisable(false);
            //Añadir tooltip al botón.
            btnEntrar.setTooltip(new Tooltip("Pulsa para acceder"));
        }
    }
    /**
     * Tratar el click del botón
     * @param event Un evento del botón
     */
    private void accionBoton(ActionEvent event){
        LOGGER.info("Iniciando ControllerSignIn.accionBoton");
        //Si cumplen las condiciones enviar datos.
        //El campo usuario tiene una longitud que no está entre 4 y 20 caracteres
        if(! maximoCaracteres(txtFieldUsuario)|| ! minimoCaracteres(txtFieldUsuario)){
            //Mostrar un alert de error
            muestraMensajeAlertaLongitudCampo();
            //El foco lo pone en el campo usuario
            txtFieldUsuario.requestFocus();
            //Selecciona el texto para borrar.
            txtFieldUsuario.selectAll();
            //El campo contraseña tiene una longitud que no está entre 4 y 20 caracteres
        }else if (!maximoCaracteres((TextField)pswFieldContrasena) ||! comprobarEspaciosBlancos((TextField)pswFieldContrasena)){
            muestraMensajeAlertaLongitudCampo();
            pswFieldContrasena.requestFocus();
            pswFieldContrasena.selectAll();
            //Hay espacios en blanco en el campo usuario
        }else if(!comprobarEspaciosBlancos(txtFieldUsuario)){
            muestraMensajeAlertaEspacios();
            txtFieldUsuario.requestFocus();
            txtFieldUsuario.selectAll();
            //Hay espacios en blanco en la contraseña
        }else if(! comprobarEspaciosBlancos((TextField)pswFieldContrasena)){
            muestraMensajeAlertaEspacios();
            pswFieldContrasena.requestFocus();
            pswFieldContrasena.selectAll();
        }else
            //Todos los campos cumplen la condición validar datos en la base de datos
            enviarDatosServidorBBDD();       
    }
    /**
     * Evento del Hyperlink clickado. Redirige a la escena SignUp.
     * @param event 
     */
    public void hyperlinkClickado(ActionEvent event){
        LOGGER.log(Level.INFO,"Evento del Hyperlink clickado. ");
        try{
           abrirVentanaSignUp(); 
        }catch(Exception e){
            lblErrorExcepcion.setText("Intentalo mas tarde. Fallo la conexión");
        }
        
    }
    /**
     * Muestra un Alert Error, si el campo es superior en longitud al preestablecido en una Constante.
     */
    private void muestraMensajeAlertaLongitudCampo(){
            Alert alert = new Alert(Alert.AlertType.ERROR,"Los campos Usuario y contraseña \n"
                    + "deben tener una longitud entre "+MIN_LENGTH+" y "+MAX_LENGTH+".",ButtonType.OK);
            alert.showAndWait();
    }
    /**
     * Comprueba que el texto de un campo no tenga espacios intermedios.
     * @param field Un campo de texto.
     * @return Un boolean true si hay espacios en blanco en el texto, false si por el contrario no los hay.
     */
    private boolean comprobarEspaciosBlancos(TextField field) {
        LOGGER.info("Iniciando ControllerSignIn.ComprobaEspaciosBlancos");
        //Guardamos valos textField en string sin espacios delante ni detras.
        String textoSinEspacios = field.getText().trim();
        //VAriable de retorno.
        Boolean hayEspacios = false;
        //ForEach de character. Recorremos letra a letra
        for(Character t:textoSinEspacios.toCharArray()){
            //Condición de igualdad. Propiedad equals de Character. Si el caracter actual igual a espacio.
            if(t.equals(' '))
                hayEspacios = true;
        }
        return hayEspacios;
    }

    /**
     * Muestra un Alert Error, si el campo contiene espacios entre la información.
     */
    private void muestraMensajeAlertaEspacios() {
        LOGGER.info("Iniciando ControllerSignIn.muestraMensajeAlertaEspacios");
        Alert alert = new Alert(Alert.AlertType.ERROR,"Los campos Usuario y contraseña \n"
                    + " no deben tener espacios.",ButtonType.OK);
            alert.showAndWait();
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
            //Llamada método para redireccionar aplicación a la siguiente ventana.
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

    /**
     * Comprueba que el texto de un campo no tenga más caracteres que el preestablecido.
     * @param field Un campo de texto.
     * @return Un boolean true si contiene los caracteres deseados, false si los caracteres superan el preestablecido.
     */
    private Boolean maximoCaracteres(TextField field) {
        LOGGER.info("Iniciando ControllerSignIn.maximoCaracteres");
        //booleano iniciado a true.
        Boolean numeroCaracteresCorrectos = true;
        //si el texto del textfield quitados los espacios delante y detrás su longitud mayor a lo preestablecido error
        if(field.getText().trim().length()>MAX_LENGTH){
            //booleana a false
                numeroCaracteresCorrectos = false;
                //mostrar una alert
                muestraMensajeAlertaLongitudCampo();
        }
        return numeroCaracteresCorrectos;
    }
    
    /**
     * Comprueba que el texto de un campo no tenga menos caracteres que el preestablecido.
     * @param field Un campo de texto.
     * @return Un boolean true si contiene los caracteres deseados, false si los caracteres son menos que el preestablecido.
     */
    private Boolean minimoCaracteres(TextField field) {
        LOGGER.info("Iniciando ControllerSignIn.minimoCaracteres");
        
        Boolean numeroCaracteresCorrectos = true;
        if(field.getText().trim().length()< MIN_LENGTH){
                numeroCaracteresCorrectos = false;
                muestraMensajeAlertaLongitudCampo();
        }
        return numeroCaracteresCorrectos;
    }

    /**
     * Cargar siguiente ventana.
     */
    private void abrirVentanaLogOut() throws Exception{
        LOGGER.log(Level.INFO,"Abriendo ventana LogOut. ");
        try{
            //New FXMLLoader Añadir el fxml de logout que es la ventana a la que se redirige si todo va bien
            FXMLLoader loader = new FXMLLoader(getClass().
                    getResource("../grupog5.signinsignupapplication.cliente.vista/FXMLDocumentLogOut.fxml"));
            //Parent es una clase gráfica de nodos xml son nodos.
            Parent root = (Parent)loader.load();
            //Relacionamos el documento FXML con el controlador que le va a controlar.
            FXMLDocumentLogOutController controladorLogOut = (FXMLDocumentLogOutController)loader.getController();
            //Llamada al método setStage del controlador de la ventana signIn. Pasa la ventana.
            controladorLogOut.setStage(stage);
            //Llamada al método setSignable del controlador de la ventana signIn. Pasa instancia SignImplementation.
            controladorLogOut.setSignable(signable);
            //Llamada al método initStage del controlador de la ventana signIn. Pasa el documento fxml en un nodo.
            controladorLogOut.initStage(root);
            }catch(IOException e){
            lblErrorExcepcion.setText("Se ha producido un error. Lo sentimos. Inténtalo mas tarde");
        }
    }

    /**
     * Carga la ventana signup para que el usuario se registre.
     */
    private void abrirVentanaSignUp() {
        LOGGER.log(Level.INFO,"Abriendo ventana SignUp. ");
        try{
            //New FXMLLoader Añadir el fxml de logout que es la ventana a la que se redirige si todo va bien
            FXMLLoader loader = new FXMLLoader(getClass().
                getResource("/vista/FXMLDocumentSignUp.fxml"));
            //Parent es una clase gráfica de nodos xml son nodos.
            Parent root = (Parent)loader.load();
            //Relacionamos el documento FXML con el controlador que le va a controlar.
            FXMLDocumentSignUpController controladorSignUp = (FXMLDocumentSignUpController)loader.getController();
            //Llamada al método setStage del controlador de la ventana signIn. Pasa la ventana.
            Scene scene = new Scene(root);
            //Añadir escena a la ventana
            stage.setScene(scene);
            //controladorSignUp.setStage(stage);
            //Llamada al método setSignable del controlador de la ventana signIn. Pasa instancia SignImplementation.
            controladorSignUp.setSignable(signable);
            //Llamada al método initStage del controlador de la ventana signIn. Pasa el documento fxml en un nodo.
            controladorSignUp.initStage(root);
        }catch(IOException e){
            lblErrorExcepcion.setText("Se ha producido un error. Lo sentimos. Inténtalo mas tarde");
        }
    }

}
