package model;

public class Paciente extends Usuario {
    private String fechaNacimiento;

    public Paciente(String nombre, String apellido, String dui, String fechaNacimiento) {
        super(nombre, apellido, dui);
        this.fechaNacimiento = fechaNacimiento;
    }

    // Getters y Setters
    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    @Override
    public String toString() {
        return "Paciente{" +
                "nombre='" + getNombre() + '\'' +
                ", apellido='" + getApellido() + '\'' +
                ", dui='" + getDui() + '\'' +
                ", fechaNacimiento='" + fechaNacimiento + '\'' +
                '}';
    }
}