package util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Importador {

    public static List<String[]> leer(File archivo) throws IOException {
        String texto = Files.readString(archivo.toPath(), StandardCharsets.UTF_8);
        if (texto.startsWith("\uFEFF")) {
            texto = texto.substring(1);
        }

        char separador = detectarSeparador(texto);
        List<String[]> filas = new ArrayList<>();
        List<String> campos = new ArrayList<>();
        StringBuilder actual = new StringBuilder();
        boolean comillas = false;

        for (int i = 0; i < texto.length(); i++) {
            char ch = texto.charAt(i);
            if (comillas) {
                if (ch == '"') {
                    if (i + 1 < texto.length() && texto.charAt(i + 1) == '"') {
                        actual.append('"');
                        i++;
                    } else {
                        comillas = false;
                    }
                } else {
                    actual.append(ch);
                }
            } else if (ch == '"') {
                comillas = true;
            } else if (ch == separador) {
                campos.add(actual.toString());
                actual.setLength(0);
            } else if (ch == '\n' || ch == '\r') {
                if (ch == '\r' && i + 1 < texto.length() && texto.charAt(i + 1) == '\n') {
                    i++;
                }
                campos.add(actual.toString());
                actual.setLength(0);
                agregar(filas, campos);
                campos = new ArrayList<>();
            } else {
                actual.append(ch);
            }
        }
        if (actual.length() > 0 || !campos.isEmpty()) {
            campos.add(actual.toString());
            agregar(filas, campos);
        }
        return filas;
    }

    private static void agregar(List<String[]> filas, List<String> campos) {
        if (campos.size() == 1 && campos.get(0).isBlank()) {
            return;
        }
        filas.add(campos.stream().map(String::trim).toArray(String[]::new));
    }

    private static char detectarSeparador(String texto) {
        int fin = texto.indexOf('\n');
        String primera = fin < 0 ? texto : texto.substring(0, fin);
        long comas = primera.chars().filter(c -> c == ',').count();
        long puntoComa = primera.chars().filter(c -> c == ';').count();
        return puntoComa > comas ? ';' : ',';
    }
}


