/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grupog5.signinsignupapplication.cliente.modelo;

import interfaz.Signable;
import mensaje.Accion;
import mensaje.Mensaje;
import user.User;

/**
 *
 * @author endika
 */
public class SignImplementation implements Signable {

    @Override
    public void signIn(User user) {
        Mensaje mensaje = new Mensaje(user,Accion.SIGNIN);
    }

    @Override
    public void signUp(User user) {
        Mensaje mensaje = new Mensaje(user,Accion.SIGNUP);
    }

    @Override
    public void logOut(User user) {
        Mensaje mensaje = new Mensaje(user,Accion.LOGOUT);
    }

    
}
