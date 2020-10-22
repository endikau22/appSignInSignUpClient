/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grupog5.signinsignupapplication.cliente.modelo;

import interfaz.Signable;

/**
 *
 * @author endika
 */
public class SignFactory {
    
    public Signable getSignable(){
        return new SignImplementation();
    }
}
