package model;

import controller.Conexion;

public class Mensaje {
    private String mensaje;
    private Conexion conexion;

    public Mensaje() {
    }

    public Mensaje(String mensaje, Conexion conexion) {
        this.mensaje = mensaje;
        this.conexion = conexion;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Conexion getConexion() {
        return conexion;
    }

    public void setConexion(Conexion conexion) {
        this.conexion = conexion;
    }
}
