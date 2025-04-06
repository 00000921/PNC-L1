package utils;

import controller.CitaController;
import model.Cita;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Utils {

    public static boolean isDateValid(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false);
            Date inputDate = sdf.parse(dateStr);
            Date today = new Date(); // Fecha y hora actual

            return !inputDate.after(today);
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isValidFormat(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false);
            sdf.parse(dateStr);
            return true;
        } catch (ParseException e) {
            return false;
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
            Date ahora = new Date(); // Fecha y hora actual

            return !fechaIngresada.before(ahora);
        } catch (ParseException e) {
            return false;
        }
    }

    public static boolean validarDUI(String dui) {
        return dui.matches("^\\d{8}-\\d$");
    }

    public static void marcarLlegada(CitaController citaController) {
        List<Cita> citas = citaController.listarCitas();

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