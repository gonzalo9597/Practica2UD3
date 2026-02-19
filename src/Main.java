import gui.Controlador;
import gui.Modelo;
import gui.Vista;

/***
 * Clase Principal
 */
public class Main {
    public static void main(String[] args) {
        Vista vista = new Vista();
        Modelo modelo = new Modelo();
        Controlador controlador = new Controlador(modelo, vista);
    }
}