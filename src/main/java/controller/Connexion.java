package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Connexion extends Thread {
    private final Controller CONTROLLER;
    private final Socket SOCKET;
    private String username;
    private BufferedReader in;
    private PrintWriter out;


    public Connexion(Socket socket, Controller CONTROLLER) {
        this.SOCKET = socket;
        this.CONTROLLER = CONTROLLER;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            username = in.readLine();
            System.out.println("Usuario conectado: " + username + ". Hay " + (CONTROLLER.getUsers().size() + 1) + " usuarios conectados");
            CONTROLLER.broadcast(username, "se ha conectado al servidor, hay " + (CONTROLLER.getUsers().size() + 1) + " usuarios conectados");
            CONTROLLER.getMensajes().addMensaje(username + " se ha conectado al servidor, hay " + (CONTROLLER.getUsers().size() + 1) + " usuarios conectados");
        } catch (IOException e) {
            System.out.println("Error al conectar con el servidor (Constructor Conexión): " + e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            String mensaje;
            while ((mensaje = in.readLine()) != null) {
                System.out.println(username + " : " + mensaje);
                CONTROLLER.broadcast(username, mensaje);
                CONTROLLER.getMensajes().addMensaje(username + " : " + mensaje);

            }
        } catch (IOException e) {
            System.out.println("Error al conectar con el servidor (Run Conexión): " + e.getMessage());
        } finally {
            try {
                SOCKET.close();
            } catch (IOException e) {
                System.out.println("Error al cerrar el socket: " + e.getMessage());
            }
            CONTROLLER.broadcast(username, "se ha desconectado del servidor, hay " + (CONTROLLER.getUsers().size()-1) + " usuarios conectados");
            CONTROLLER.getMensajes().addMensaje(username + " se ha desconectado del servidor, hay " + (CONTROLLER.getUsers().size()-1) + " usuarios conectados");
            CONTROLLER.removeUser(this, username);
        }
    }

    public void sendMessage(String remitente, String mensaje) {
        out.println(remitente + " : " + mensaje);
    }

}