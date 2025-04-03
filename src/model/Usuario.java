package model;

public class Usuario {
    private String nombre;
    private String apellido;
    private String dui;

    public Usuario(String nombre, String apellido, String dui) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dui = dui;
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDui() {
        return dui;
    }

    public void setDui(String dui) {
        this.dui = dui;
    }
}