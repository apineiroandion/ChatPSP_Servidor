package controller;

import model.Mensajes;

import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private final int MAX_USERS = 10;
    private final List<Conexion> users = new ArrayList<>();
    private final Mensajes mensajes = new Mensajes();

    public Controller() {
    }

    public void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(5000);
            System.out.println("Servidor iniciado");
            usuariosConectados();
            while (true) {
                System.out.println("Esperando conexiones");
                if (users.size() < MAX_USERS) {
                    Conexion conexion = new Conexion(serverSocket.accept(), this);
                    users.add(conexion);
                    conexion.start();
                    System.out.println("Usuario conectado al servidor");
                    enviarListaMensajes(conexion);
                }else {
                    System.out.println("Sala llena");
                }
            }
        } catch (Exception e) {
            System.out.println("Error al conectarse al servidor: " + e.getMessage());;
        }
    }

    public synchronized void removeUser(Conexion conexion, String username) {
        users.remove(conexion);
        System.out.println("Usuario " + username + " desconectado del servidor");
        usuariosConectados();
    }

    public synchronized void broadcast(String remitente, String mensaje) {
        for (Conexion conexion : users) {
            conexion.sendMessage(remitente, mensaje);
        }
    }

    public synchronized void enviarListaMensajes(Conexion conexion) {
        for (String mensaje : mensajes.getMensajes()) {
            conexion.sendMessage("", mensaje);
        }
    }

    public void usuariosConectados() {
        if (users.size() == 0) {System.out.println("No hay usuarios conectados");}
    }

    public List<Conexion> getUsers() {
        return users;
    }

    public Mensajes getMensajes() {
        return mensajes;
    }

    public void setMensaje(String mensaje) {
        mensajes.addMensaje(mensaje);
    }

}
