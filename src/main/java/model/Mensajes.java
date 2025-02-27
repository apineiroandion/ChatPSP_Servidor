package model;

import java.util.ArrayList;

public class Mensajes {
    private ArrayList<String> mensajes = new ArrayList<>();

    public Mensajes() {
    }

    public void addMensaje(String mensaje) {
        mensajes.add(mensaje);
    }

    public ArrayList<String> getMensajes() {
        return mensajes;
    }

    public void setMensajes(ArrayList<String> mensajes) {
        this.mensajes = mensajes;
    }

    public void clearMensajes() {
        mensajes.clear();
    }
}
