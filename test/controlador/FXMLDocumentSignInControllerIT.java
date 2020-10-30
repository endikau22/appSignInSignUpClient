/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import application.GrupoG52020ApplicationsigninsignupClientapp;
import javafx.stage.Stage;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import  org.testfx.matcher.control.TextInputControlMatchers;
import  org.testfx.matcher.control.LabeledMatchers;



/**
 *
 * @author 2dam
 */
 @FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FXMLDocumentSignInControllerIT extends ApplicationTest{
    
     /**
     * Starts application to be tested.
     * @param stage Primary Stage object
     * @throws Exception If there is any error
     */
    @Override public void start(Stage stage) throws Exception {
       new GrupoG52020ApplicationsigninsignupClientapp().start(stage);
    }

    /**
     * Constructor
     */
    public FXMLDocumentSignInControllerIT() {
    }

    /**
     * 
     */
    @Test
    public void testA_InicioVentana() {
        verifyThat("#txtFieldUsuario", TextInputControlMatchers.hasText(""));
        verifyThat("#pswFieldContrasena",TextInputControlMatchers.hasText(""));
        verifyThat("#btnEntrar", isDisabled());
        verifyThat("#hplRegistrate", isEnabled());
    }
   
    /**
    * Test botón Entrar está deshabilitado si el campo usuario o password están desinformados.
    */ 
    @Test
    public void testB_BotonEntrarDeshabilitado() {
        clickOn("#txtFieldUsuario");
        write("username");
        verifyThat("#btnEntrar", isDisabled());
        eraseText(8);
        clickOn("#pswFieldContrasena");
        write("password");
        verifyThat("#btnEntrar", isDisabled());
        eraseText(8);
        verifyThat("#btnEntrar", isDisabled());
    }

    /**
    * Test para comprobar que el botón Entrar está habilitado cuando los campos usuario y contraseña están informados.
    */ 
    @Test
    public void testC_BotonEntrarHabilitado() {
        clickOn("#txtFieldUsuario");
        write("username");
        clickOn("#pswFieldContrasena");
        write("password");
        verifyThat("#btnEntrar", isEnabled());
    }

    /**
    * Test para comprobar que cuando se clicka sobre el hyperlink se abre la ventana de signUp
    */ 
    @Test
    public void testD_HyperlinkClickado() {
        clickOn("#hplRegistrate");
        verifyThat("#borderPaneRegistro", isVisible());
    }

    /**
     * Test para comprobar que el String del campo Usuario es muy larga.
     */
    @Test
    public void testE_IntroducirStringMuyLargoUsuario(){
        clickOn("#txtFieldUsuario");
        write("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        clickOn("#pswFieldContrasena");
        write("password");
        clickOn("#btnEntrar");
        verifyThat("#lblErrorUsuarioContrasena",LabeledMatchers.hasText("Usuario incorrecto."));
    }
    
    /**
     * Test para comprobar que el String del campo Password es muy larga.
     */
    @Test
    public void testF_IntroducirStringMuyLargoPassword(){
        clickOn("#txtFieldUsuario");
        write("usuario");
        clickOn("#pswFieldContrasena");
        write("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        clickOn("#btnEntrar");
        verifyThat("#lblErrorUsuarioContrasena",LabeledMatchers.hasText("Contraseña incorrecta."));
    }
    
    /**
     * Test para comprobar que el String del campo Usuario es muy corta.
     */
    @Test
    public void testG_IntroducirStringMuyCortoUsuario(){
        clickOn("#txtFieldUsuario");
        write("x");
        clickOn("#pswFieldContrasena");
        write("password");
        clickOn("#btnEntrar");
        verifyThat("#lblErrorUsuarioContrasena",LabeledMatchers.hasText("Usuario incorrecto."));
    }
    
    /**
     * Test para comprobar que el String del campo Password es muy corta.
     */
    @Test
    public void testH_IntroducirStringMuyCortoPassword(){
        clickOn("#txtFieldUsuario");
        write("usuario");
        clickOn("#pswFieldContrasena");
        write("x");
        clickOn("#btnEntrar");
        verifyThat("#lblErrorUsuarioContrasena",LabeledMatchers.hasText("Contraseña incorrecta."));
    }
    
    /**
     * Test para comprobar que el String del campo Usuario tiene espacios.
     */
    @Test
    public void testI_IntroducirStringEspaciosUsuario(){
        clickOn("#txtFieldUsuario");
        write("usu ario");
        clickOn("#pswFieldContrasena");
        write("password");
        clickOn("#btnEntrar");
        verifyThat("#lblErrorUsuarioContrasena",LabeledMatchers.hasText("Usuario incorrecto."));
    }
    
    /**
     * Test para comprobar que el String del campo Password tiene espacios.
     */
    @Test
    public void testJ_IntroducirStringEspaciosPassword(){
        clickOn("#txtFieldUsuario");
        write("usuario");
        clickOn("#pswFieldContrasena");
        write("pass word");
        clickOn("#btnEntrar");
        verifyThat("#lblErrorUsuarioContrasena",LabeledMatchers.hasText("Contraseña incorrecta."));
    }
    
    /**
     * Test para comprobar que el String del campo Password es muy corta.
     */
    @Test
    public void testK_AccederVentanaUsuarioCorrecto(){
        clickOn("#txtFieldUsuario");
        write("usuario");
        clickOn("#pswFieldContrasena");
        write("password");
        clickOn("#btnEntrar");
        verifyThat("#panelLogOut", isVisible());
     
    }
    
    
}
