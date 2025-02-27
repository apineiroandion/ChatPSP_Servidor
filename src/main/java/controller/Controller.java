package controller;

import model.Mensajes;

import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private final List<Connexion> users = new ArrayList<>();
    private final Mensajes mensajes = new Mensajes();

    public Controller() {
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Servidor iniciado");
            usuariosConectados();
            while (true) {
                System.out.println("Esperando conexiones");
                int MAX_USERS = 10;
                if (users.size() < MAX_USERS) {
                    Connexion connexion = new Connexion(serverSocket.accept(), this);
                    users.add(connexion);
                    connexion.start();
                    System.out.println("Usuario conectado al servidor");
                    enviarListaMensajes(connexion);
                }else {
                    System.out.println("Sala llena");
                }
            }
        } catch (Exception e) {
            System.out.println("Error al conectarse al servidor: " + e.getMessage());
        }
    }

    public synchronized void removeUser(Connexion connexion, String username) {
        users.remove(connexion);
        System.out.println("Usuario " + username + " desconectado del servidor");
        usuariosConectados();
    }

    public synchronized void broadcast(String remitente, String mensaje) {
        for (Connexion connexion : users) {
            connexion.sendMessage(remitente, mensaje);
        }
    }

    public synchronized void enviarListaMensajes(Connexion connexion) {
        for (String mensaje : mensajes.getMENSAJES()) {
            connexion.sendMessage("", mensaje);
        }
    }

    public void usuariosConectados() {
        if (users.isEmpty()) {System.out.println("No hay usuarios conectados");}
    }

    public List<Connexion> getUsers() {
        return users;
    }

    public Mensajes getMensajes() {
        return mensajes;
    }

}
