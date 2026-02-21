package gui;

import javax.swing.*;
import java.awt.*;

/**
 * Clase Vista que hereda de JFrame que permite la utilización de la interfaz
 */
public class Vista extends JFrame {
    private JPanel panel1;
    private JFrame frame;

    private JTabbedPane tabbedPane1;
    public JTextField txtModelo;

    public JButton nuevoCalzadoBtn;
    public JButton modificarCalzadoBtn;
    public JTextField txtNombreComprador;
    public JButton altaCompradorBtn;

    public JButton eliminarTiendaBtn;
    public JTextField txtNombreMarca;
    public JButton altaMarcaBtn;

    public JTextField txtSku;
    public JTextField txtPrecio;
    public JButton eliminarCalzadoBtn;
    public JTextField txtApellidos;
    public JTextField txtDNI;
    public JButton modificarCompradorBtn;
    public JButton eliminarCompradorBtn;
    public JButton altaTiendaBtn;
    public JButton modificarTiendaBtn;
    public JButton modificarMarcaBtn;
    public JButton eliminarMarcaBtn;
    public JTextField txtNombreTienda;
    public JTextField txtTlfTienda;

    public JList listCalzados;
    public JList listCompradores;
    public JList listTiendas;
    public JList listMarcas;

    public JButton listarMarcaCalzadosBtn;
    public JTextField txtTlfMarca;
    public JComboBox comboMarcas;
    public JComboBox comboTiendas;
    public JComboBox comboCalzados;
    public JButton altaVentaBtn;
    public JButton eliminarVentaBtn;
    public JButton modificarVentaBtn;
    public JList listMarcasCalzados;
    public JList listVentas;

    DefaultListModel dlmCalzados;
    DefaultListModel dlmCompradores;
    DefaultListModel dlmTiendas;
    DefaultListModel dlmMarcas;


    //Filtros
    DefaultListModel dlmMarcaCalzados;
    DefaultListModel dlmVentas;

    JMenuItem conexionItem;
    JMenuItem salirItem;


    /**
     * Constructor de clase vacío que inizializa lo necesario
     */
    public Vista(){
        frame = new JFrame("Tienda de Calzados");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setIconImage(
                new ImageIcon(getClass().getResource("/jordan.png")).getImage()
        );
        //Lo colocamos donde el Frame ya existe, pero todavía no es visible (después de configurar el frame básico)
        //Para que coja la imagen cuando creemos el JAR (la imagen tiene que ir en resources)
        //frame.pack();
        frame.setVisible(true);
        frame.setSize(new Dimension(800,430));
        frame.setLocationRelativeTo(null);

        crearMenu();
        crearModelos();

    }

    /**
     * Método que permite crear los modelos, es decir añadir DLM a las listas
     */
    private void crearModelos() {
        dlmCalzados = new DefaultListModel();
        listCalzados.setModel(dlmCalzados);

        dlmCompradores = new DefaultListModel();
        listCompradores.setModel(dlmCompradores);

        dlmTiendas = new DefaultListModel();
        listTiendas.setModel(dlmTiendas);

        dlmMarcas = new DefaultListModel();
        listMarcas.setModel(dlmMarcas);

        dlmMarcaCalzados = new DefaultListModel();
        listMarcasCalzados.setModel(dlmMarcaCalzados);

        dlmVentas = new DefaultListModel();
        listVentas.setModel(dlmVentas);

    }

    /**
     * Método que permite crear un menú y añadir actionCommands
     */
    private void crearMenu() {
        JMenuBar barra = new JMenuBar();
        JMenu menu = new JMenu("Archivo");

        conexionItem = new JMenuItem("Conectar");
        conexionItem.setActionCommand("Conectar");

        salirItem = new JMenuItem("Salir");
        salirItem.setActionCommand("Salir");

        menu.add(conexionItem);
        menu.add(salirItem);
        barra.add(menu);
        frame.setJMenuBar(barra);
    }
}
