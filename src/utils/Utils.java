package utils;

import controller.CitaController;
import model.Cita;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Utils {

    public static boolean isDateValid(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false); // Formato estricto
            Date inputDate = sdf.parse(dateStr);

            // Obtener la fecha actual sin la hora
            Date today = new Date();
            today = sdf.parse(sdf.format(today)); // Resetea la hora a 00:00:00

            return !inputDate.after(today); // Verifica si la fecha ingresada es anterior o igual a hoy
        } catch (Exception e) {
            return false; // Fecha no válida
        }
    }

    public static boolean isValidFormat(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false); // Para que sea estricto
            sdf.parse(dateStr);
            return true; // La fecha es válida
        } catch (ParseException e) {
            return false; // No se pudo parsear, la fecha es inválida
        }
    }

    public static boolean isValidTime(String timeStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
            sdf.setLenient(false);
            sdf.parse(timeStr);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }


    public static boolean isFutureDate(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false);
            Date fechaIngresada = sdf.parse(dateStr);

            Date hoy = sdf.parse(sdf.format(new Date()));

            return fechaIngresada.after(hoy); // Debe ser mayor a fecha actual
        } catch (ParseException e) {
            return false;
        }
    }

    public static boolean validarDUI(String dui) {
        return dui.matches("^\\d{8}-\\d$");
    }



    public static void marcarLlegada(CitaController citaController) {
        List<Cita> citas = citaController.listarCitas(); // Ya imprime citas también

        if (citas.isEmpty()) {
            return;
        }

        System.out.print("Ingrese el ID de la cita para marcar como asistida: ");
        int citaId;
        try {
            citaId = Integer.parseInt(citaController.getScanner().nextLine());
        } catch (NumberFormatException e) {
            System.out.println("❌ ID inválido. Debe ser un número.");
            return;
        }

        for (Cita cita : citas) {
            if (cita.getId() == citaId) {
                cita.setLlego(true);
                System.out.println("✅ Cita marcada como asistida.");
                return;
            }
        }

        System.out.println("❌ No se encontró una cita con ese ID.");
    }



}