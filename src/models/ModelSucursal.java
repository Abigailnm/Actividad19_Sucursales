/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import java.sql.*;

/**
 *
 * @author oscar
 */
public class ModelSucursal {

    private Connection conexion;
    private Statement st;
    private ResultSet rs;
    private PreparedStatement ps;

    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    public Statement getSt() {
        return st;
    }

    public void setSt(Statement st) {
        this.st = st;
    }

    public ResultSet getRs() {
        return rs;
    }

    public void setRs(ResultSet rs) {
        this.rs = rs;
    }

    public PreparedStatement getPs() {
        return ps;
    }

    public void setPs(PreparedStatement ps) {
        this.ps = ps;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getNo_ext() {
        return no_ext;
    }

    public void setNo_ext(String no_ext) {
        this.no_ext = no_ext;
    }

    public String getNo_int() {
        return no_int;
    }

    public void setNo_int(String no_int) {
        this.no_int = no_int;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    private String id;
    private String calle;
    private String colonia;
    private String no_ext;
    private String no_int;
    private String cp;
    private String telefono;


    //COMIENZA EL CODIGO A BASE DE DATOS Y TERMINAN SETTERS Y GETTERS 

    public Connection ConectarBD() { //metodo para conexion a la base de datos 
        try {
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/quetzalstock", "quetzal", "quetzal.2018");
            actualizarSucursal();
            setValues();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en conexion" + e.getMessage());
        }
        return conexion;
    }

    public void actualizarSucursal() {
        try {
            String sql = "SELECT * FROM sucursal;";
            ps = conexion.prepareStatement(sql);
            System.out.println(sql);
            rs = ps.executeQuery(sql);
            rs.next();
            setValues();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error_Actualizar_tabla" + e.getMessage());
        }
    }

    public void setValues() {
        try {
            id = rs.getString("id");
            calle = rs.getString("calle");
            colonia = rs.getString("colonia");
            no_ext = rs.getString("no_ext");
            no_int = rs.getString("no_int");
            cp = rs.getString("cp");
            telefono = rs.getString("telefono");
            

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error model 102: " + e.getMessage());

        }
    }

    public void guardarRegistro( String calle, String colonia, String no_ext, String no_int, String cp, String telefono) {
        try {
            conexion = null;
            conexion = ConectarBD();
            ps = conexion.prepareStatement("INSERT INTO sucursal (calle, colonia, no_ext, no_int, cp,telefono) VALUES (?,?,?,?,?,?)");
            ps.setString(1, calle);
            ps.setString(2, colonia);
            ps.setString(3, no_ext);
            ps.setString(4, no_int);
            ps.setString(5, cp);
            ps.setString(6, telefono);
            int devuelto = ps.executeUpdate();
            if (devuelto > 0) {
                JOptionPane.showMessageDialog(null, "Datos guardados...");

                actualizarSucursal();

            } else {
                JOptionPane.showMessageDialog(null, "Datos NO registrados");
            }

        } catch (SQLException e) {
            Logger.getLogger(ModelSucursal.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void eliminarRegistro(String id) {
        int des = JOptionPane.showConfirmDialog(null, "Realmente desea eliminar esta sucursal?", "Eliminar sucursal", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (des != JOptionPane.YES_NO_OPTION) {
        } else {
            try {
                conexion = null;
                conexion = ConectarBD();
                ps = conexion.prepareStatement("DELETE FROM sucursal WHERE id = ?");
                ps.setString(1, id);
                int res = ps.executeUpdate();
                actualizarSucursal();
                JOptionPane.showMessageDialog(null, "Sucursal eliminada ");
            } catch (SQLException ex) {
                Logger.getLogger(ModelSucursal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void editarRegistro(String calle, String colonia, String no_ext, String no_int, String cp,String telefono, String id) {
        try {
            conexion = null;
            conexion = ConectarBD();
            ps = conexion.prepareStatement("UPDATE sucursal SET calle=?, colonia=?, no_ext=? , no_int=? , cp=? , telefono= ? WHERE id=?");
            ps.setString(1, calle);
            ps.setString(2, colonia);
            ps.setString(3, no_ext);
            ps.setString(4, no_int);
            ps.setString(5, cp);
            ps.setString(6, telefono);
            ps.setString(9, id);
            int resultado = ps.executeUpdate();
            if (resultado > 0) {
                JOptionPane.showMessageDialog(null, "Datos de Actualizados");
                actualizarSucursal();
            } else {
                JOptionPane.showMessageDialog(null, "Error 001-guardar");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ModelSucursal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void moverPrimerRegistro() {
        System.out.println("moverPrimerRegistro");
        try {
            if (rs.isFirst() == false) {
                rs.first();
                setValues();
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error_S" + e.getMessage());
        }
    }

    public void moverSiguienteRegistro() {
        System.out.println("moverSiguienteRegistro");
        try {
            if (rs.isLast() == false) {
                rs.next();
                setValues();
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error_S" + e.getMessage());
        }
    }

    /**
     * Método que realiza las siguiente acciones: 1.- Moverse al anterior
     * registro 2.- obtener el valor del nombre de rs y guardarlo en la variable
     * nombre 3.- obtener el valor del email de rs y guardarlo en la variable
     * email
     */
    public void moverAnteriorRegistro() {
        System.out.println("moverAnteriorRegistro");
        try {
            if (rs.isFirst() == false) {
                rs.previous();
                setValues();
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error_S" + e.getMessage());
        }
    }

    /**
     * Método que realiza las siguiente acciones: 1.- Moverse al ultimo registro
     * 2.- obtener el valor del nombre de rs y guardarlo en la ariable nombre
     * 3.- obtener el valor del email de rs y guardarlo en la variable email
     */
    public void moverUltimoRegistro() {
        System.out.println("moverUltimoRegistro");
        try {
            if (rs.isLast() == false) {
                rs.last();
                setValues();
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error_S" + e.getMessage());
        }
    }

}

