package view;

import java.util.Random;

public class GenerateCode {

    public static String generarCodigo() {
        Random random = new Random();
        return String.format("ZNH-%d%s%d-MD-%d%s",
                random.nextInt(10),
                (char) (random.nextInt(26) + 'A'),
                random.nextInt(10),
                random.nextInt(10),
                (char) (random.nextInt(26) + 'A')
        );
    }
}