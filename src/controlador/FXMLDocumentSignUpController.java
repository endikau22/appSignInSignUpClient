/**
 * Contiene el controlador de la aplicación cliente del proyecto SignInSignUp. 
 */
package controlador;

import excepciones.ExcepcionUserYaExiste;
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
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import user.User;

/**
 * FXML Controller class para la escena SignUp
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
    private static final int TEXTFIELD_MAX_LENGTH = 20;
    /**
     * Longitud minima de los campos de texto permitida.
     */
    private static final int TEXTFIELD_MIN_LENGTH = 4;
    /**
     * Botón de confirmación de crear cuenta.
     */
    @FXML private Button btnCrearCuenta;
    /**
     * Hyperlink a la escena SignIn
     */
    @FXML private Hyperlink hplIniciaSesion;
    /**
     * Campo de texto del nombre del usuario
     */
    @FXML private TextField txtFieldNombre;
    /**
     * Campo de texto del email del usuario
     */
    @FXML private TextField txtFieldEmail;
    /**
     * Campo de texto del nombre de usuario o login.
     */
    @FXML private TextField txtFieldUsuario;
    /**
     * Campo de texto de tipo contraseña.
     */
    @FXML private PasswordField pswFieldContrasena;
    /**
     * Campo de texto de tipo contraseña para la confirmación de la contraseña.
     */
    @FXML private PasswordField pswFieldRepetirContrasena;
    /**
     * Label informátivo gráfico de los distintos errores.
     */
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
        
        //Iniciar los componentes de la escena. TextFields y botones.
        iniciarComponentesEscena();
        //Ejecutar método handleWindowShowing cuando se produzca el evento setOnShowing
        //Este evento se lanza cuando la ventana está a punto de aparecer.
        stage.setOnShowing(this::manejarInicioVentana);
        //Añadir un evento para el cambio de texto en cada uno de los campos de texto.     
        txtFieldUsuario.textProperty().addListener(this::cambioTexto);
        txtFieldEmail.textProperty().addListener(this::cambioTexto);
        txtFieldNombre.textProperty().addListener(this::cambioTexto);
        txtFieldNombre.textProperty().addListener(this::cambioTexto);
        pswFieldContrasena.textProperty().addListener(this::cambioTexto);
        pswFieldRepetirContrasena.textProperty().addListener(this::cambioTexto);
        //Ejecutar método evento acción clickar botón
        btnCrearCuenta.setOnAction(this::accionBoton);
        //Ejecutar método Hyperlink clickado
        hplIniciaSesion.setOnAction(this::hyperlinkClickado);
        //Hace visible la pantalla
        stage.show();
    }
    
    public void iniciarComponentesEscena(){
        LOGGER.log(Level.INFO,"Iniciar componentes de la escena. ");
        //Asignar un texto de fondo en el campo contraseña, se muestra cuando el campo está desenfocado.
        pswFieldContrasena.setPromptText("Introduce tu contraseña. ("+TEXTFIELD_MIN_LENGTH+ " a "+TEXTFIELD_MAX_LENGTH+" caracteres)");
        //Asignar un texto de fondo en el campo repetir contraseña, se muestra cuando el campo está desenfocado.
        pswFieldRepetirContrasena.setPromptText("Introduce tu contraseña. ("+TEXTFIELD_MIN_LENGTH+ " a "+TEXTFIELD_MAX_LENGTH+" caracteres)");
        //Asignar texto cuando el campo está desenfocado.
        txtFieldUsuario.setPromptText("Introduce tu nombre de usuario. ("+TEXTFIELD_MIN_LENGTH+ " a "+TEXTFIELD_MAX_LENGTH+" caracteres)");
        //Asignar texto en el textField Nombre cuando el campo está desenfocado.
        txtFieldNombre.setPromptText("Introduce tu nombre.");
        //Asignar texto en el textField email cuando el campo está desenfocado.
        txtFieldEmail.setPromptText(" @ Introduce tu email."); 
        //El boton está inhabilitado al arrancar la ventana.
        btnCrearCuenta.setDisable(true);  
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
     * Acciones que se realizan en el momento previo a que se muestra la ventana. Inicializa los componentes.
     * @param event Evento de ventana.
     */
    private void manejarInicioVentana(WindowEvent event){
        LOGGER.info("Iniciando ControllerSignUp::handleWindowShowing manejarInicioVentana");
        //Asignar un texto de fondo en el campo contraseña, se muestra cuando el campo está desenfocado.
        pswFieldContrasena.setPromptText("Introduce tu contraseña. ("+TEXTFIELD_MIN_LENGTH+ " a "+TEXTFIELD_MAX_LENGTH+" caracteres)");
        //Asignar un texto de fondo en el campo repetir contraseña, se muestra cuando el campo está desenfocado.
        pswFieldRepetirContrasena.setPromptText("Introduce tu contraseña. ("+TEXTFIELD_MIN_LENGTH+ " a "+TEXTFIELD_MAX_LENGTH+" caracteres)");
        //Asignar texto cuando el campo está desenfocado.
        txtFieldUsuario.setPromptText("Introduce tu nombre de usuario. ("+TEXTFIELD_MIN_LENGTH+ " a "+TEXTFIELD_MAX_LENGTH+" caracteres)");
        //Asignar texto en el textField Nombre cuando el campo está desenfocado.
        txtFieldNombre.setPromptText("Introduce tu nombre.");
        //Asignar texto en el textField email cuando el campo está desenfocado.
        txtFieldEmail.setPromptText(" @ Introduce tu email."); 
        //El boton está inhabilitado al arrancar la ventana.
        btnCrearCuenta.setDisable(true);  
    }

    /**
     * Registra los cambios de texto en los textField o passwordField.
     * @param observable 
     * @param oldValue Valor anterior al lanzamiento del evento.
     * @param newValue Nuevo valor tras el cambio.
     */
    private void cambioTexto(ObservableValue observable,String oldValue,String newValue){
        LOGGER.info("Iniciando ControllerSignUp::cambioTexto");
        //Vaciar label de error tras escribir en cualquiera de los campos.
        lblMensajeError.setText("");
        //Vaciar label de error tras escribir en cualquiera de los campos.
        lblMensajeError.setText("");
        //Si está vacío cualquiera de los cinco campos.
        if(this.txtFieldNombre.getText().trim().equals("")||this.txtFieldUsuario.getText().trim().equals("")
            ||this.txtFieldEmail.getText().trim().equals("")||this.pswFieldContrasena.getText().trim().equals("")||
                this.pswFieldRepetirContrasena.getText().trim().equals("")){
            //Deshabilitar botón
            btnCrearCuenta.setDisable(true);  
        }else{//Los cinco campos están informados.
            //Habilitar botón 
            btnCrearCuenta.setDisable(false);
            //Añadir tooltip al botón. (Muestra el mensaje al tener el ratón encima del botón)
            btnCrearCuenta.setTooltip(new Tooltip("Pulsa para registrarte"));
            //Habilitar el uso del teclado para moverse por la ventana.
            btnCrearCuenta.setMnemonicParsing(true);
        }
    }

    /**
     * Carga la ventana signIn para que el usuario se loguee si el registro ha sido correcto o se clicka el Hyperlink.
     */
    private void abrirVentanaSignIn() {
        LOGGER.log(Level.INFO,"Metodo del controladorSignUp, Abriendo ventana SignIn.");
        try{
            //New FXMLLoader Añadir el fxml de logout que es la ventana a la que se redirige si todo va bien
            FXMLLoader loader = new FXMLLoader(getClass().
                getResource("/vista/FXMLDocumentSignIn.fxml"));
            //Parent es una clase gráfica de nodos xml son nodos.
            Parent root = (Parent)loader.load();
            //Relacionamos el documento FXML con el controlador que le va a controlar.
            FXMLDocumentSignInController controladorSignIn = (FXMLDocumentSignInController)loader.getController();
            //Llamada al método setSignable del controlador de la ventana signIn. Pasa instancia SignImplementation.
            controladorSignIn.setStage(stage);
            //Pasar el objeto signable al controlador SignIn.
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
        LOGGER.info("Iniciando ControllerSignUp.accionBoton");
        //Si cumplen las condiciones los cuatro campos de texto..
        if(validarCampoUsuario()&&validarCampoEmail()&&validarCampoLogin()&&validarContraseñas())
        //Todos los campos cumplen la condición validar datos en la base de datos
            enviarDatosServidorBBDD();  
        else{
           //si al pulsar el botín los campos no cumplen las condiciones requeridas  
           lblMensajeError.setText("El campo no es válido.");
           //Selecciona el texto del campo de texto.
           txtFieldUsuario.selectAll();
           //Pone el foco en el campo de texto.
           txtFieldUsuario.requestFocus();
        }
    }
    
    /**
     * Enviar datos del usuario a la BBDD con el objeto signable
     */
    private void enviarDatosServidorBBDD() {
        LOGGER.info("Iniciando ControllerSignUp.EnviarDatosServidorBBDD");
        //Almacenar en objeto de User los datos recogidos de los campos de la ventana.
        User myUser = new User (txtFieldUsuario.getText(),pswFieldContrasena.getText(),
                txtFieldNombre.getText(),txtFieldEmail.getText());
        try {
            //Pasa el usuario a la instancia signable a su método signUp.
            signable.signUp(myUser);
            //Llamada al método para redireccionar aplicación a la siguiente ventana.
            abrirVentanaSignIn();
        //El método signUp de signable lanza 2 excepciones. Tratarlas en el catch.    
        } catch (ExcepcionUserYaExiste ex1) {
            LOGGER.info("Iniciando ControllerSignUp.EnviarDatosServidorBBDD.Entra al catch ExcepcionUserYaExiste");
            //Colocar el texto de la excepción en el label
            lblMensajeError.setText(ex1.getMessage());
            //VAciar campos de texto
            txtFieldNombre.setText("");
            txtFieldEmail.setText("");
            txtFieldUsuario.setText("");
            pswFieldContrasena.setText("");
            pswFieldRepetirContrasena.setText("");
        } catch (Exception ex3){
            LOGGER.info("Iniciando ControllerSignUp.EnviarDatosServidorBBDD.Entra al catch Excepcion");
            //Colocar el texto de la excepción en el label
            lblMensajeError.setText(ex3.getMessage());
            //VAciar campos de texto
            txtFieldNombre.setText("");
            txtFieldEmail.setText("");
            txtFieldUsuario.setText("");
            pswFieldContrasena.setText("");
            pswFieldRepetirContrasena.setText("");
        }
    }

    /**
     * 
     * @return 
     */
    private boolean validarCampoUsuario() {
        return maximoCaracteres(txtFieldNombre)&&minimoCaracteres(txtFieldNombre)
                &&comprobarEspaciosBlancos(txtFieldNombre);
    }

    /**
     * 
     * @return 
     */
    private boolean validarCampoEmail() {
        String email = txtFieldEmail.getText();
        return emailCorrecto(email);
    }

    /**
     * 
     * @return 
     */
    private boolean validarCampoLogin() {
        return maximoCaracteres(txtFieldNombre)&&minimoCaracteres(txtFieldNombre)
                &&comprobarEspaciosBlancos(txtFieldNombre);
    }
    
    /**
     * Comprueba que el texto de un campo no tenga más caracteres que el preestablecido.
     * @param field Un campo de texto.
     * @return Un boolean true si contiene los caracteres deseados, false si los caracteres superan el preestablecido.
     */
    private Boolean maximoCaracteres(TextField field) {
        LOGGER.info("Iniciando ControllerSignIn.maximoCaracteres");
        //booleano iniciado a true. Por defecto cumple la condición
        Boolean numeroCaracteresCorrectos = true;
        //si el texto del textfield quitados los espacios delante y detrás su longitud mayor a lo preestablecido error
        if(field.getText().trim().length() > TEXTFIELD_MAX_LENGTH){
            //booleana a false
                numeroCaracteresCorrectos = false;
        }
        return numeroCaracteresCorrectos;
    }
    
    /**
     * Comprueba que el texto de un campo no tenga menos caracteres que el preestablecido.
     * @param field Un campo de texto.
     * @return Un boolean true si contiene los caracteres deseados, false si los caracteres son menos que el preestablecido.
     */
    private Boolean minimoCaracteres(TextField field) {
        LOGGER.info("Iniciando ControllerSignUp.minimoCaracteres");
        //booleano iniciado a true.
        Boolean numeroCaracteresCorrectos = true;
        if(field.getText().trim().length() < TEXTFIELD_MIN_LENGTH){
                numeroCaracteresCorrectos = false;
        }
        return numeroCaracteresCorrectos;
    }
    
     /**
     * Comprueba que el texto de un campo no tenga espacios intermedios.
     * @param field Un campo de texto.
     * @return Un boolean true si hay espacios en blanco en el texto, false si por el contrario no los hay.
     */
    private Boolean comprobarEspaciosBlancos(TextField field) {
        LOGGER.info("Iniciando ControllerSignUp.ComprobaEspaciosBlancos");
        //Guardamos valos textField en string sin espacios delante ni detras.
        String textoSinEspacios = field.getText().trim();
        //VAriable de retorno. Inicializar a false
        Boolean textoCorrecto = true;
        //ForEach de character. Recorremos letra a letra
        for(Character t:textoSinEspacios.toCharArray()){
            LOGGER.info("Recorrer el texto para buscar espacios. ControllerSignIn.ComprobaEspaciosBlancos");
            //Condición de igualdad. Propiedad equals de Character. Si el caracter actual igual a espacio.
            if(t.equals(' '))
                textoCorrecto = false;
        }
        return textoCorrecto;
    }

    /**
     * Validación del campo contraseña y repetir contraseña. Deben ser iguales ambos campos y máximo 30 caracteres.
     * @return Un booleano. True si la contraseña es correcta.
     */
    private boolean validarContraseñas() {
      //Comparar los dos campos contraseña. Si son iguales y además tiene una longitud superior a 4 e inferior a 20.
      return pswFieldContrasena.getText().equals(pswFieldRepetirContrasena.getText()) 
              && maximoCaracteres((TextField)pswFieldContrasena)&& minimoCaracteres((TextField)pswFieldContrasena)
              && comprobarEspaciosBlancos((TextField)pswFieldContrasena);
    }
    
    /**
     * Comprueba que el String recibido como parámetro cumple las condiciones de un email.
     * @param email El email del campo contraseña.
     * @return Un booleano.  True si la contraseña es correcta.
     */
    private boolean emailCorrecto(String email) {
        //Boolean, va a ser el return. Inicializar a true, hasta que se demuestre lo contrario.
        boolean ok=true;
        if(email.length()>45)
            ok = false;
        else{
            //Dividir el string por el caracter indicado.
            String emailArray [] = email.split("@");
            //Declaración array de caracteres para guardar la parte del nombre del email
            char [] nombre;
            //Si la longitud del array es distintode 2 significa que no hay un arroba.
            if(emailArray.length!=2) {
                //El email no es correcto
                ok=false;
                //System.out.println("Has metido "+(emailArray.length -1)+" @s");
            }
            else {//Vamos bien hay un arroba. Estudiar el nombre. Posición 0 del array.
                String nomUsuario = emailArray[0];
                nombre = nomUsuario.toCharArray();
                //Confirmar que los caracteres son mínimo 3
                if(nomUsuario.length()<3) {//Error no cumple el mínimo de carcteres en el nombre.
                    //System.out.println("Nombre usuario tiene que tener minimo 3.");
                    ok = false;
                }		
                else {//Vamos bien. Cumple la longitud de caracteres del nombre.
                    //Recorrer el nombre letra a letra.Solo puede tener letra numero y _ .
                    for(int i=0;i<nombre.length;i++) {
                        //Si no se cumple error.
                        if((Character.isLetterOrDigit(nombre[i])||Character.compare(nombre[i],'.')==0||Character.compare(nombre[i], '_')==0)==false) {
                            ok=false;
                            //System.out.println("\"Caracter incorrecto en el nombre de usuario solo letras numeros, . o _\"");
                            break;
                        }
                    }//Si va bien por ahra mira el dominio.
                    if(ok) {//Dividir el dominio Nombre y extensión
                        String dominio [] = emailArray[1].split("\\.");//o('.');
                        if(dominio.length!=2) {//Si no hay solo un punto. 
                            ok=false;
                            //System.out.println("Hay que separar el nombre del dominio y la extension con solo un punto");
                        }
                        else {
                            for(int j=0;j<2;j++) {
                                nombre =  dominio[j].toCharArray();
                                if(j==1){//La extensión del dominio 2 o 3 letras.
                                    if(nombre.length>3 || nombre.length<2){
                                        ok= false;
                                    }
                                }
                                for(int letra=0;letra<nombre.length;letra++) {
                                    if(Character.isAlphabetic(nombre[letra])==false) {
                                        ok=false;
                                        //System.out.println("En el dominio solo letras nada mas");
                                        break;
                                    }	
                                }
                            }
                        }
                    }
                }
            }
        }

	return ok;
    }
}
