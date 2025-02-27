package model;

import java.util.ArrayList;

public class Mensajes {
    private final ArrayList<String> MENSAJES = new ArrayList<>();

    public Mensajes() {
    }

    public void addMensaje(String mensaje) {
        MENSAJES.add(mensaje);
    }

    public ArrayList<String> getMENSAJES() {
        return MENSAJES;
    }
}
