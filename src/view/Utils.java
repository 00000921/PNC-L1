package view;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Utils {
    public static boolean isValidDate(String dateStr) {
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
        // Implementa aquí la validación del formato de tiempo, si es necesario
        return true; // Placeholder; reemplazar con lógica real
    }
}