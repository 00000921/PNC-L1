package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
}