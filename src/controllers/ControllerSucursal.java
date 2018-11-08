/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import models.ModelSucursal;
import views.viewSucursal;
import java.sql.*;
/**
 *
 * @author abi
 */
public class ControllerSucursal {
    

    ModelSucursal modelSucursal;
    viewSucursal viewSucursal;
    String des = null;

    public ControllerSucursal(ModelSucursal modelSucursal, viewSucursal viewSucursal) {
        this.modelSucursal = modelSucursal;
        this.viewSucursal = viewSucursal;
        setActionlistener();
        initDB();
        initComponents();

    }

    public void initComponents() {

        viewSucursal.jtf_id.setEditable(false);
        viewSucursal.jtf_calle.setEditable(false);
        viewSucursal.jtf_colonia.setEditable(false);
        viewSucursal.jtf_noexterior.setEditable(false);
        viewSucursal.jtf_nointerior.setEditable(false);
        viewSucursal.jtf_cp.setEditable(false);
        viewSucursal.jtf_telefono.setEditable(false);
        viewSucursal.jb_cancelar.setEnabled(false);
        viewSucursal.jb_guardar.setEnabled(false);

    }

    public void initDB() {
        modelSucursal.ConectarBD();
        getValues();
    }

    public void setActionlistener() {
        viewSucursal.jb_actualizar.addActionListener(actionListener);
        viewSucursal.jb_eliminar.addActionListener(actionListener);
        viewSucursal.jb_nuevo.addActionListener(actionListener);
        viewSucursal.jb_guardar.addActionListener(actionListener);
        viewSucursal.jb_cancelar.addActionListener(actionListener);
        
       viewSucursal.jb_anterior.addActionListener(actionListener);
       viewSucursal.jb_primero.addActionListener(actionListener);
       viewSucursal.jb_ultimo.addActionListener(actionListener);
       viewSucursal.jb_siguiente.addActionListener(actionListener);
    }
    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == viewSucursal.jb_guardar) {

                guardar();

            } else if (e.getSource() == viewSucursal.jb_cancelar) {

                cancelarActionPerformed();

            } else if (e.getSource() == viewSucursal.jb_actualizar) {

                actualizar();

            } else if (e.getSource() == viewSucursal.jb_eliminar) {

                eliminar();

            } else if (e.getSource() == viewSucursal.jb_nuevo) {

                nuevo();

            } else if (e.getSource() == viewSucursal.jb_primero){
                modelSucursal.moverPrimerRegistro();
                getValues();
                
            } else if (e.getSource() == viewSucursal.jb_anterior){
                modelSucursal.moverAnteriorRegistro();
                getValues();
            } else if (e.getSource() == viewSucursal.jb_siguiente){
                modelSucursal.moverSiguienteRegistro();
                getValues();
            } else if (e.getSource() == viewSucursal.jb_ultimo){
                modelSucursal.moverUltimoRegistro();
                getValues();
            }
        }
    };

    //metodos action performend de cada acccion en cada boton
    public void cancelarActionPerformed() {
        deshabilitarCampos();
        getValues();
        viewSucursal.jb_actualizar.setEnabled(true);
        viewSucursal.jb_eliminar.setEnabled(true);
        viewSucursal.jb_nuevo.setEnabled(true);
        viewSucursal.jb_cancelar.setEnabled(false);
        viewSucursal.jb_guardar.setEnabled(false);
    }

    public void guardar() {
        enviarDatos();
        if ("nuevo".equals(des)) {
            modelSucursal.guardarRegistro(viewSucursal.jtf_calle.getText(),viewSucursal.jtf_colonia.getText(),
                    viewSucursal.jtf_noexterior.getText(), viewSucursal.jtf_nointerior.getText(),
                    viewSucursal.jtf_cp.getText(), viewSucursal.jtf_telefono.getText());
                    

        } else if ("actualizar".equals(des)) {
            modelSucursal.editarRegistro(viewSucursal.jtf_calle.getText(),viewSucursal.jtf_colonia.getText(),
                    viewSucursal.jtf_noexterior.getText(), viewSucursal.jtf_nointerior.getText(),
                    viewSucursal.jtf_cp.getText(), viewSucursal.jtf_telefono.getText(),viewSucursal.jtf_id.getText());
        }
        getValues();
        deshabilitarCampos();
        viewSucursal.jb_actualizar.setEnabled(true);
        viewSucursal.jb_eliminar.setEnabled(true);
        viewSucursal.jb_nuevo.setEnabled(true);
        viewSucursal.jb_cancelar.setEnabled(false);
        viewSucursal.jb_guardar.setEnabled(false);

    }

    public void nuevo() {
        des = "nuevo";
        habilitarCampos();
        limpiarCampos();
        viewSucursal.jb_actualizar.setEnabled(false);
        viewSucursal.jb_eliminar.setEnabled(false);
        viewSucursal.jb_nuevo.setEnabled(false);
        viewSucursal.jb_cancelar.setEnabled(true);
        viewSucursal.jb_guardar.setEnabled(true);
    }

    public void eliminar() {
        enviarDatos();
        modelSucursal.eliminarRegistro(viewSucursal.jtf_id.getText());
        modelSucursal.actualizarSucursal();
        getValues();
    }

    public void actualizar() {
        des = "actualizar";
        habilitarCampos();
        viewSucursal.jb_actualizar.setEnabled(false);
        viewSucursal.jb_eliminar.setEnabled(false);
       
        viewSucursal.jb_nuevo.setEnabled(false);
        viewSucursal.jb_cancelar.setEnabled(true);
        viewSucursal.jb_guardar.setEnabled(true);

    }

    

    private void enviarDatos() {
        modelSucursal.setCalle(viewSucursal.jtf_calle.getText());
        modelSucursal.setColonia(viewSucursal.jtf_colonia.getText());
        modelSucursal.setNo_ext(viewSucursal.jtf_noexterior.getText());
        modelSucursal.setNo_int(viewSucursal.jtf_nointerior.getText());
        modelSucursal.setCp(viewSucursal.jtf_cp.getText());
        modelSucursal.setTelefono(viewSucursal.jtf_telefono.getText());
        modelSucursal.setId(viewSucursal.jtf_id.getText());
    }

    private void getValues() {

        viewSucursal.jtf_id.setText(modelSucursal.getId());
        viewSucursal.jtf_calle.setText(modelSucursal.getCalle());
        viewSucursal.jtf_colonia.setText(modelSucursal.getColonia());
        viewSucursal.jtf_noexterior.setText(modelSucursal.getNo_ext());
        viewSucursal.jtf_nointerior.setText(modelSucursal.getNo_int());
        viewSucursal.jtf_cp.setText(modelSucursal.getCp());
        viewSucursal.jtf_telefono.setText(modelSucursal.getTelefono());
        

    }

    private void limpiarCampos() {
        viewSucursal.jtf_calle.setText("");
        viewSucursal.jtf_colonia .setText("");
        viewSucursal.jtf_noexterior.setText("");
        viewSucursal.jtf_nointerior.setText("");
        viewSucursal.jtf_id.setText("");
        viewSucursal.jtf_cp.setText("");
        viewSucursal.jtf_telefono.setText("");
        
    }

    public void habilitarCampos() {
        viewSucursal.jtf_calle.setEditable(true);
        viewSucursal.jtf_colonia.setEditable(true);
        viewSucursal.jtf_noexterior.setEditable(true);
        viewSucursal.jtf_nointerior.setEditable(true);
        viewSucursal.jtf_cp.setEditable(true);
        viewSucursal.jtf_telefono.setEditable(true);
       
    }

    public void deshabilitarCampos() {
        viewSucursal.jtf_calle.setEditable(false);
        viewSucursal.jtf_colonia.setEditable(false);
        viewSucursal.jtf_noexterior.setEditable(false);
        viewSucursal.jtf_nointerior.setEditable(false);
        viewSucursal.jtf_cp.setEditable(false);
        viewSucursal.jtf_telefono.setEditable(false);
        
    }

}

