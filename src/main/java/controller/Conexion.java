package controller;

import model.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Conexion extends Thread {
    private User usuarioConectado;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private Controller controller;

    public Conexion(Socket socket, Controller controller, User usuarioConectado) {
        this.socket = socket;
        this.controller = controller;
        this.usuarioConectado = usuarioConectado;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            String message;
            while ((message = in.readLine()) != null) {
                System.out.println("Mensaje recibido: " + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            controller.removeUser(this);
        }
    }

    public User getUsuarioConectado() {
        return usuarioConectado;
    }

    public void setUsuarioConectado(User usuarioConectado) {
        this.usuarioConectado = usuarioConectado;
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