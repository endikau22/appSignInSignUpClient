/**
 * Contiene el controlador de la aplicación cliente del proyecto SignInSignUp.
 */
package controlador;

import interfaz.Signable;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import user.User;


/**
 * FXML Controller class para la scena LogOut
 * @version 1.0
 * @since 23/10/2020
 * @author Eneko, Endika, Markel
 */
public class FXMLDocumentLogOutController{
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
     * Un usuario que ha accedido a la aplicación.
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
     * @param root Un nodo FXML
     */
    public void initStage(Parent root) {
        LOGGER.log(Level.INFO,"Inicializando la ventana logOut. ");
        //Creación de  una nueva escena
        Scene scene = new Scene(root);
        //Añadir escena a la ventana
        stage.setScene(scene);
        //Asignación de un título a la ventana
        stage.setTitle("Bienvenido a la aplicación");
        //Asignar tamaño fijo a la ventana
        stage.setResizable(false); 
  
        //Ejecutar método evento acción clickar botón
        btnSalir.setOnAction(this::accionBoton);
        //Hace visible la pantalla
        stage.show();
    }

       /**
     * Tratar el click del botón
     * @param event Un evento del botón
     */
    private void accionBoton(ActionEvent event){
        LOGGER.info("Iniciando ControllerLogOut.accionBoton");
        try {
            //Pasar datos del usuario para que el signable lo envíe a la base de datos para que registre la hora de salida.
            signable.logOut(usuario);
            //Abrir ventana signin
            abrirVentanaSignIn();
        //El método logout lanza una excepción. Tratarla a continuación
        } catch (Exception ex) {
            LOGGER.log(Level.INFO,"Execepción SQL al intentar LogOut");
            Alert alert = new Alert(Alert.AlertType.ERROR,"Error al cerrar sesión, espera unos segundos."
                    ,ButtonType.OK);
            alert.showAndWait();
        }
        
    }
    /**
     * Carga la ventana signin para que el usuario se loguee si el registro ha sido correcto o se clicka el Hyperlink.
     */
    private void abrirVentanaSignIn() {
        LOGGER.log(Level.INFO,"Abriendo ventana SignIn. ");
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
            Alert alert = new Alert(Alert.AlertType.ERROR,"Error al cerrar sesión, espera unos segundos."
                    ,ButtonType.OK);
            alert.showAndWait();
        }
    }
    
}
