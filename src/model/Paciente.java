package model;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

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

    private static String calcularDui(String fechaNacimiento) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fechaNac = LocalDate.parse(fechaNacimiento, formatter);
        int edad = Period.between(fechaNac, LocalDate.now()).getYears();
        return (edad < 18) ? "00000000-0" : ""; // Operador ternario para verificar edad
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