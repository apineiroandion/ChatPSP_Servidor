package model;

public class Mensaje {
    private String mensaje;
    private User user;

    public Mensaje() {
    }

    public Mensaje(String mensaje, User user) {
        this.mensaje = mensaje;
        this.user = user;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
