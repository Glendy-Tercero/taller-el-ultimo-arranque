package taller;

import view.MenuPrincipal;

public class Taller {

    public static void main(String[] args) {
        MenuPrincipal viewMenu = new MenuPrincipal();
        viewMenu.setVisible(true); 
        
        new Thread(() -> Conexion.desconectar(Conexion.conectar())).start();
    }
    
}
