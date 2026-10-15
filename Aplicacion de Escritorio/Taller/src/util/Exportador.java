package util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.table.TableModel;

public class Exportador {

    public static void aCSV(TableModel modelo, File archivo) throws IOException {
        try (PrintWriter out = new PrintWriter(archivo, "UTF-8")) {
            out.print('\uFEFF');
            for (int c = 0; c < modelo.getColumnCount(); c++) {
                out.print((c > 0 ? "," : "") + csv(modelo.getColumnName(c)));
            }
            out.println();
            for (int r = 0; r < modelo.getRowCount(); r++) {
                for (int c = 0; c < modelo.getColumnCount(); c++) {
                    out.print((c > 0 ? "," : "") + csv(modelo.getValueAt(r, c)));
                }
                out.println();
            }
        }
    }

    public static void aHTML(TableModel modelo, File archivo, String titulo) throws IOException {
    try (PrintWriter out = new PrintWriter(archivo, "UTF-8")) {
        out.println("<html><head><meta charset='UTF-8'><title>" + html(titulo) + "</title><style>"
                + "body{background:#fff;font-family:Arial,sans-serif;margin:24px}"
                + "h2{color:#081d56;font-family:Arial,sans-serif}"
                + "table{border-collapse:collapse;width:100%}"
                + "th{background:#fff;color:#081d56;text-align:left;vertical-align:top;"
                + "font:bold 13px Arial,sans-serif;height:40px;padding:0 12px 0 0}"
                + "td{background:#fff;color:#333;font:bold 12px Bahnschrift,'Segoe UI',Arial,sans-serif;"
                + "height:40px;padding:0 12px 0 0}"
                + "</style></head><body><h2>" + html(titulo) + "</h2><table><tr>");
        for (int c = 0; c < modelo.getColumnCount(); c++) {
            out.println("<th>" + html(modelo.getColumnName(c)) + "</th>");
        }
        out.println("</tr>");
        for (int r = 0; r < modelo.getRowCount(); r++) {
            out.println("<tr>");
            for (int c = 0; c < modelo.getColumnCount(); c++) {
                Object valor = modelo.getValueAt(r, c);
                out.println("<td>" + html(valor == null ? "" : valor.toString()) + "</td>");
            }
            out.println("</tr>");
        }
        out.println("</table></body></html>");
    }
}

    private static String csv(Object valor) {
        String texto = valor == null ? "" : valor.toString();
        return "\"" + texto.replace("\"", "\"\"") + "\"";
    }

    public static String html(String texto) {
        return texto.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }
}

