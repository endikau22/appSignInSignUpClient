
package application;

import controlador.FXMLDocumentSignInController;
import modelo.SignFactory;
import interfaz.Signable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

/**
 * @version 1.0
 * @since 23/10/2020
 * @author Endika, Eneko, Markel
 */
public class GrupoG52020ApplicationsigninsignupClientapp extends Application {
    /**
     * Atributo Logger para rastrear los pasos de ejecución del programa.
     */
    private static final Logger LOGGER = 
            Logger.getLogger("grupog5.signinsignupapplication.cliente.application");
    
    /**
     * Entrada principal a la aplicación
     * @param stage Una ventana
     * @throws Exception 
     */
    @Override
    public void start(Stage stage) throws Exception {
        //Mensaje Logger al acceder al método
        LOGGER.log(Level.INFO, "Método start de la aplicación");
        //New FXMLLoader Añadir el fxml de signin que es la escena inicial
        FXMLLoader loader = new FXMLLoader(getClass().
                getResource("/vista/FXMLDocumentSignIn.fxml"));
        //Parent es una clase gráfica de nodos xml son nodos. Añadir el FXML.
        Parent root = (Parent)loader.load();
        //Variable de tipo de la interfaz Signable. Es una instancia de la clase SignImplementation que implmenta la interfaz. 
        Signable signable = SignFactory.getSignable();
        //Relacionamos el documento FXML con el controlador que le va a controlar.
        FXMLDocumentSignInController controladorSignIn = (FXMLDocumentSignInController)loader.getController();
        //Llamada al método setStage del controlador de la ventana signIn. Pasa la ventana.
        controladorSignIn.setStage(stage);
        //Llamada al método setSignable del controlador de la ventana signIn. Pasa instancia SignImplementation.
        controladorSignIn.setSignable(signable);
        //Llamada al método initStage del controlador de la ventana signIn. Pasa el documento fxml en un nodo.
        controladorSignIn.initStage(root);
        }

    /**
     * Arranca una aplicación JAvaFX
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Este método arranca la aplicación llamando al método start.
        launch(args);
    }
    
}
