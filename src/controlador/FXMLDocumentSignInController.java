
package controlador;

import excepciones.ExcepcionPasswdIncorrecta;
import excepciones.ExcepcionUserNoExiste;
import contenedormetodos.MetodosUtiles;
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
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import user.User;

/**
 * FXML Controller class para la escena SignIn
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
     * Una ventana sobre la que se coloca una escena.
     */
    private Stage stage;
    /**
     * Atributo del tipo de la interfaz Signable.
     */
    private Signable signable;
    /**
     * Longitud máxima de los campos de texto permitida.
     */
    private static final Integer TEXTFIELD_MAX_LENGTH = 20;
    /**
     * Longitud minima de los campos de texto permitida.
     */
    private static final Integer TEXTFIELD_MIN_LENGTH = 4;
    
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
     * Hyperlink de registro. Enlace a la escena SignUp.
     */
    @FXML private Hyperlink hplRegistrate;
    
    /**
     * Label Mensajes Error usuario,contraseña.
     */ 
    @FXML private Label lblErrorUsuarioContrasena;
    
    /**
     * LAbel Mensajes Error genéricos o ajenos a la aplicación.
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
     * Método que añade la escena en la ventana e inicializa los distintos componenetes de la escena.
     * @param root Un nodo FXML en formato gráfico.
     */
    public void initStage(Parent root) {
        LOGGER.log(Level.INFO,"Inicializando la ventana SignIn. ");
        //Creación de  una nueva escena
        Scene scene = new Scene(root);
        //Añadir escena a la ventana
        stage.setScene(scene);
        //Asignación de un título a la ventana
        stage.setTitle("Sign In");
        //Asignar tamaño fijo a la ventana
        stage.setResizable(false); 
        
        //Asignar un texto de fondo en el campo contraseña, se muestra cuando el campo está desenfocado.
        pswFieldContrasena.setPromptText("Introduce tu contraseña. ("+TEXTFIELD_MIN_LENGTH+ " a "+TEXTFIELD_MAX_LENGTH+" caracteres)");
        //Asignar texto cuando el campo está desenfocado.
        txtFieldUsuario.setPromptText("Introduce tu nombre de usuario. ("+TEXTFIELD_MIN_LENGTH+ " a "+TEXTFIELD_MAX_LENGTH+" caracteres)");
        //El boton está inhabilitado al arrancar la ventana.
        btnEntrar.setDisable(true);

        //Ejecutar método handleWindowShowing cuando se produzca el evento setOnShowing
        //Este evento se lanza cuando la ventana esta a punto de aparecer. Este evento no se lanza al volver de otra escena porque no abrimos una stage cambiamos de scene
        stage.setOnShowing(this::manejarInicioVentana);
        //Ejecutar método cambioTexto cuando el texto de el campo txtFieldUsuario cambie.
        txtFieldUsuario.textProperty().addListener(this::cambioTexto);
        //Ejecutar método cambioTexto cuando el texto de el campo pswFieldContraseña cambie.
        pswFieldContrasena.textProperty().addListener(this::cambioTexto);
        //Ejecutar método evento acción clickar botón
        btnEntrar.setOnAction(this::accionBoton);
        //Ejecutar método Hyperlink clickado
        hplRegistrate.setOnAction(this::hyperlinkClickado);
        //Hace visible la pantalla
        stage.show();
    }

    /**
     * Acciones que se realizan en el momento previo a que se muestre la ventana.
     * @param event Evento de ventana.
     */
    private void manejarInicioVentana(WindowEvent event){
        LOGGER.info("Iniciando ControllerSignIn::handleWindowShowing.Metodo_ManejarInicioVentana");
            //Asignar un texto de fondo en el campo contraseña, se muestra cuando el campo está desenfocado.
        pswFieldContrasena.setPromptText("Introduce tu contraseña. ("+TEXTFIELD_MIN_LENGTH+ " a "+TEXTFIELD_MAX_LENGTH+" caracteres)");
        //Asignar texto cuando el campo está desenfocado.
        txtFieldUsuario.setPromptText("Introduce tu nombre de usuario. ("+TEXTFIELD_MIN_LENGTH+ " a "+TEXTFIELD_MAX_LENGTH+" caracteres)");
        //El boton está inhabilitado al arrancar la ventana.
        btnEntrar.setDisable(true); 
    }
    
     /**
     * Evento del Hyperlink clickado. Redirige a la escena SignUp.
     * @param event Un evento del Hyperlink.
     */
    public void hyperlinkClickado(ActionEvent event){
        LOGGER.log(Level.INFO,"Evento del Hyperlink clickado. ");
        try{
            //Llamada a método abrirVentanaSignUp
           abrirVentanaSignUp(); 
        }catch(Exception e){
            //Escribir en el label.
            lblErrorExcepcion.setText("Intentalo mas tarde. Fallo la conexión");
        }       
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
        //Condición.Si está vacío cualquiera de los dos campos. Trim Quita espacios delante y detrás del texto del campo.
        if(this.txtFieldUsuario.getText().trim().equals("")||
                this.pswFieldContrasena.getText().trim().equals("")){
            //Deshabilitar botón
            btnEntrar.setDisable(true);  
        }else{//Los dos campos están informados.
            //Habilitar botón 
            btnEntrar.setDisable(false);
            //Añadir tooltip al botón.
            btnEntrar.setTooltip(new Tooltip("Pulsa para acceder"));
            //Habilitar el uso del teclado para moverse por la ventana.
            btnEntrar.setMnemonicParsing(true);
        }
    }
    /**
     * Tratar el click del botón
     * @param event Un evento del botón
     */
    private void accionBoton(ActionEvent event){
        LOGGER.info("Iniciando ControllerSignIn.accionBoton");
        //Si cumplen las condiciones enviar datos.
        //El campo usuario tiene una longitud que no está entre 4 y 20 caracteres y no tiene espacios.
        if(! MetodosUtiles.maximoCaracteres(txtFieldUsuario,TEXTFIELD_MAX_LENGTH)|| 
                ! MetodosUtiles.minimoCaracteres(txtFieldUsuario,TEXTFIELD_MIN_LENGTH) ||
                ! MetodosUtiles.comprobarEspaciosBlancos(txtFieldUsuario)){
            LOGGER.info("Longitud del textfield erronea y espacios blancos ControllerSignIn.accionBoton");
            //El foco lo pone en el campo usuario
            txtFieldUsuario.requestFocus();
            //Selecciona el texto para borrar.
            txtFieldUsuario.selectAll();
            //Mostrar un mensaje de error en el label.
            //Vaciar el campo contraseña
            pswFieldContrasena.setText("");
            lblErrorUsuarioContrasena.setText("Usuario incorrecto.");
            //Cambia de color el texto del label, en este caso a rojo
            lblErrorUsuarioContrasena.setTextFill(Color.web("#ff0000"));
            //El campo contraseña tiene una longitud que no está entre 4 y 20 caracteres y no tiene espacios.
        }else if (!MetodosUtiles.maximoCaracteres((TextField)pswFieldContrasena, TEXTFIELD_MAX_LENGTH) 
                ||! MetodosUtiles.minimoCaracteres((TextField)pswFieldContrasena, TEXTFIELD_MIN_LENGTH)
                ||!MetodosUtiles.comprobarEspaciosBlancos((TextField)pswFieldContrasena)){
            LOGGER.info("Longitud del passwordField erronea y espacios blancos ControllerSignIn.accionBoton");
            pswFieldContrasena.requestFocus();
            pswFieldContrasena.selectAll();
            //Mostrar un mensaje de error en el label.
            lblErrorUsuarioContrasena.setText("Contraseña incorrecta.");
            //Cambia de color el texto del label, en este caso a rojo
            lblErrorUsuarioContrasena.setTextFill(Color.web("#ff0000"));
        }else
            //Todos los campos cumplen la condición validar datos en la base de datos
            enviarDatosServidorBBDD();       
    }
  
    /**
     * Enviar datos del usuario a la BBDD haciendo uso de los métodos de la interfaz Signable
     * y el objeto de la clase SignImplementation que tiene como atributo esta clase y que implementa la Interfaz.
     */
    private void enviarDatosServidorBBDD() {
        //Cuando entra a este método sabemos que los campos usuario y contraseña cumplen todas las condiciones preestablecidas.
        LOGGER.info("Iniciando ControllerSignIn.EnviarDatosServidorBBDD");
        //Almacenar en el objeto de la clase User los datos recogidos de los campos de la ventana.
        User myUser = new User (txtFieldUsuario.getText().trim(),pswFieldContrasena.getText().trim());
        try {
            //Ejecutar método signIn de la clase SignImplemetation a traves de la instancia que tiene la propia clase como atributo.
            signable.signIn(myUser);
            //Llamada al método abrirVentanaLogOut para redireccionar aplicación a la siguiente escena.
            abrirVentanaLogOut(myUser);
            //Si se produce un error mirar los distintos throws del método signIn de la clase SignImplementation.
        } catch (ExcepcionUserNoExiste ex1) {
            //Vaciar campos de texto
            txtFieldUsuario.setText("");
            pswFieldContrasena.setText("");
            //Colocar el texto de la excepción en el label
            lblErrorUsuarioContrasena.setText(ex1.getMessage());
            //Cambia de color el texto del label, en este caso a rojo
            lblErrorUsuarioContrasena.setTextFill(Color.web("#ff0000"));
        } catch (ExcepcionPasswdIncorrecta ex2) {
            //Vaciar campos de texto
            pswFieldContrasena.setText("");
            pswFieldContrasena.requestFocus();
            //Colocar el texto de la excepción en el label
            lblErrorUsuarioContrasena.setText(ex2.getMessage());
            //Cambia de color el texto del label, en este caso a rojo
            lblErrorUsuarioContrasena.setTextFill(Color.web("#ff0000"));
        } catch (Exception ex3){
            //Vaciar campos de texto
            txtFieldUsuario.setText("");
            pswFieldContrasena.setText("");
            //Colocar el texto de la excepción en el label
            lblErrorExcepcion.setText("Se ha producido un error. Lo sentimos. Inténtalo mas tarde");
            //Cambia de color el texto del label, en este caso a rojo
            lblErrorExcepcion.setTextFill(Color.web("#ff0000"));
        }
    }

    /**
     * Cargar la escena de LogOut en la ventana. Se le pasa el usuario para que pueda cerrar la sesión.
     */
    private void abrirVentanaLogOut(User usuario) throws Exception{
        //Se ha pulsado el botón y los datos han sido validados en la BBDD.
        LOGGER.log(Level.INFO,"Abriendo ventana LogOut. ");
        try{
            //New FXMLLoader Añadir el fxml de logout que es la escena a la que se redirige si todo va bien
            FXMLLoader loader = new FXMLLoader(getClass().
                    getResource("/vista/FXMLDocumentLogOut.fxml"));
            //Parent es una clase gráfica de nodos. xml son nodos.
            Parent root = (Parent)loader.load();
            //Relacionamos el documento FXML con el controlador que le va a controlar.
            FXMLDocumentLogOutController controladorLogOut = (FXMLDocumentLogOutController)loader.getController();
            //Pasar el usuario al controlador de la ventana logOut.
            controladorLogOut.setUsuario(usuario);
            //Llamada al método setStage del controlador de la ventana signIn. Pasa la ventana.
            controladorLogOut.setStage(stage);
            //Llamada al método setSignable del controlador de la escena logOut. Pasa instancia SignImplementation.
            controladorLogOut.setSignable(signable);
            //Llamada al método initStage del controlador de la ventana LogOut. Pasa el documento fxml en un nodo.
            controladorLogOut.initStage(root);
        //Error al cargar la nueva escena, mostrar mensaje.
        }catch(IOException e){
            lblErrorExcepcion.setText("Se ha producido un error. Lo sentimos. Inténtalo mas tarde");
            //Cambia de color el texto del label, en este caso a rojo
            lblErrorExcepcion.setTextFill(Color.web("#ff0000"));
        }
    }

    /**
     * Carga la ventana signUup para que el usuario se registre.
     */
    private void abrirVentanaSignUp() {
        //A este método llegamos cuando se clicka en el Hyperlink.
        LOGGER.log(Level.INFO,"Abriendo ventana SignUp. ");
        try{
            //New FXMLLoader Añadir el fxml de signUp que es la escena a la que se redirige si todo va bien
            FXMLLoader loader = new FXMLLoader(getClass().
                getResource("/vista/FXMLDocumentSignUp.fxml"));
            //Parent es una clase gráfica de nodos xml son nodos.
            Parent root = (Parent)loader.load();
            //Relacionamos el documento FXML con el controlador que le va a controlar.
            FXMLDocumentSignUpController controladorSignUp = (FXMLDocumentSignUpController)loader.getController();   
            //Llamada al método setSignable del controlador de la ventana signUp. Pasa instancia SignImplementation.
            controladorSignUp.setSignable(signable);
            //Pasa la ventana al controlador de la ventana signUp
            controladorSignUp.setStage(stage);
            //Llamada al método initStage del controlador de la ventana signUp. Pasa el documento fxml en un nodo.
            controladorSignUp.initStage(root);
        //Error al cargar la nueva escenamostrar mensaje.
        }catch(IOException e){
            lblErrorExcepcion.setText("Se ha producido un error. Lo sentimos. Inténtalo mas tarde");
            //Cambia de color el texto del label, en este caso a rojo
            lblErrorExcepcion.setTextFill(Color.web("#ff0000"));
        }
    }
}
