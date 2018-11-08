/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import models.ModelSucursal;
import views.viewSucursal;
import controllers.ControllerSucursal;

/**
 *
 * @author Abi Montes
 */
public class Main {
    
     ModelSucursal modelSucursal = new ModelSucursal();
        viewSucursal viewSucursal = new viewSucursal();
        ControllerSucursal controllerSucursal = new ControllerSucursal(modelSucursal, viewSucursal);
}
