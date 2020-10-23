/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grupog5.signinsignupapplication.cliente.application;

import grupog5.signinsignupapplication.cliente.controlador.FXMLDocumentControllerSignIn;
import grupog5.signinsignupapplication.cliente.modelo.SignFactory;
import interfaz.Signable;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author 2dam
 */
public class GrupoG52020ApplicationsigninsignupClientapp extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        //New FXMLLoader Añadir el fxml de signin que es la ventana principal
        FXMLLoader loader = new FXMLLoader(getClass().getResource("grupog5.signinsignupapplication.cliente.vista.FXMLDocumentSignIn.fxml"));
        //
        Parent root = (Parent)loader.load();
        //Nueva Instancia signable 
        Signable signable = SignFactory.getSignable();
        //Relacionamos el documento FXML con el controlador.
        FXMLDocumentControllerSignIn controladorSignIn = (FXMLDocumentControllerSignIn)loader.getController();
        //Llamada al método setStage del controlador de la ventana signIn
        controladorSignIn.setStage(stage);
        //
        controladorSignIn.setSignable(signable);
        controladorSignIn.initStage(root); 
        controladorSignIn.inicializarComponentesVentana();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
