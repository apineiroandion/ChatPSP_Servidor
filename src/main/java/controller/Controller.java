package controller;

import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private final int MAX_USERS = 10;
    private final List<Conexion> users = new ArrayList<>();

    public Controller() {
    }

    public void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(5000);
            System.out.println("Servidor iniciado");
            while (true) {
                System.out.println("Esperando conexiones");
                if (users.size() < MAX_USERS) {
                    Conexion conexion = new Conexion(serverSocket.accept(), this);
                    users.add(conexion);
                    conexion.start();
                    System.out.println("Usuario conectado al servidor");
                }else {
                    System.out.println("Sala llena");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void removeUser(Conexion conexion) {
        users.remove(conexion);
        System.out.println("Usuario desconectado del servidor");
    }
}
