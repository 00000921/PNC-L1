package model;

public class Doctor extends Usuario {
    private String fechaReclutamiento;
    private String especialidad;
    private String codigo;

    public Doctor(String nombre, String apellido, String dui, String fechaReclutamiento, String especialidad, String codigo) {
        super(nombre, apellido, dui);
        this.fechaReclutamiento = fechaReclutamiento;
        this.especialidad = especialidad;
        this.codigo = codigo;
    }

    // Getters y Setters
    public String getFechaReclutamiento() {
        return fechaReclutamiento;
    }

    public void setFechaReclutamiento(String fechaReclutamiento) {
        this.fechaReclutamiento = fechaReclutamiento;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getCodigo() {
        return codigo;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "nombre='" + getNombre() + '\'' +
                ", apellido='" + getApellido() + '\'' +
                ", dui='" + getDui() + '\'' +
                ", fechaReclutamiento='" + fechaReclutamiento + '\'' +
                ", especialidad='" + especialidad + '\'' +
                ", codigo='" + codigo + '\'' +
                '}';
    }
}