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
        //No se porque necesito el load() no estatico.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("grupog5.signinsignupapplication.cliente.vista.FXMLDocumentSignIn.fxml"));
        Parent root = (Parent)loader.load();
        
        Signable signable = SignFactory.getSignable();
        
        FXMLDocumentControllerSignIn controladorSignIn = (FXMLDocumentControllerSignIn)loader.getController();
        controladorSignIn.setStage(stage);
        controladorSignIn.initStage(root,signable); 
        controladorSignIn.inicializarComponentesVentana();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
