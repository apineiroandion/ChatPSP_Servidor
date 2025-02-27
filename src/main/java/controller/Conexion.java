package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Conexion extends Thread {
    private String username;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private Controller controller;

    public Conexion(Socket socket, Controller controller) {
        this.socket = socket;
        this.controller = controller;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            username = in.readLine();
            System.out.println("Usuario conectado: " + username + ". Hay " + (controller.getUsers().size() + 1) + " usuarios conectados");
            controller.broadcast(username, "se ha conectado al servidor, hay " + (controller.getUsers().size() + 1) + " usuarios conectados");
            controller.getMensajes().addMensaje(username + " se ha conectado al servidor, hay " + (controller.getUsers().size() + 1) + " usuarios conectados");
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
                controller.broadcast(username, mensaje);
                controller.getMensajes().addMensaje(username + " : " + mensaje);

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            controller.removeUser(this, username);
        }
    }

    public void sendMessage(String remitente, String mensaje) {
        out.println(remitente + " : " + mensaje);
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public BufferedReader getIn() {
        return in;
    }

    public void setIn(BufferedReader in) {
        this.in = in;
    }

    public PrintWriter getOut() {
        return out;
    }

    public void setOut(PrintWriter out) {
        this.out = out;
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }
}