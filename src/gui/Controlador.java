package gui;

import com.calzados.practica2ud3.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/***
 * Clase controlador
 */
public class Controlador implements ActionListener, ListSelectionListener {
    private Vista vista;
    private Modelo modelo;
    private boolean conectado;

    /***
     * Constructor de la clase
     * @param modelo modelo
     * @param vista vista
     */
    public Controlador(Modelo modelo, Vista vista) {
        this.vista = vista;
        this.modelo = modelo;
        this.conectado = false;

        addActionListeners(this);
        addListSelectionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        String comando = e.getActionCommand();

        if (!conectado && !comando.equalsIgnoreCase("Conectar")) {
            JOptionPane.showMessageDialog(null, "No has conectado con la BBDD",
                    "Error de conexión", JOptionPane.ERROR_MESSAGE);
            return;
        }
        switch (comando) {
            case "Salir":
                modelo.desconectar();
                System.exit(0);
                break;
            case "Conectar":
                vista.conexionItem.setEnabled(false);
                modelo.conectar();
                conectado = true;
                break;

            case "nuevoCalzadoBtn":
                Calzado nuevoCalzado = new Calzado();
                nuevoCalzado.setModelo(vista.txtModelo.getText());
                nuevoCalzado.setSku(vista.txtSku.getText());
                nuevoCalzado.setMarca((Marca) vista.comboMarcas.getSelectedItem());
                modelo.insertar(nuevoCalzado);
                break;

            case "listarCalzados":
                listarCalzados(modelo.getCalzados());
                break;

            case "modificarCalzadoBtn": {
                Calzado c = (Calzado) vista.listCalzados.getSelectedValue();
                c.setModelo(vista.txtModelo.getText());
                c.setSku(vista.txtSku.getText());
                c.setMarca((Marca) vista.comboMarcas.getSelectedItem());
                modelo.modificar(c);
                break;
            }

            case "eliminarCalzadoBtn": {
                Calzado c = (Calzado) vista.listCalzados.getSelectedValue();
                if (!comprobarCalzadoVenta(c.getIdcalzado())) {
                    JOptionPane.showMessageDialog(null, "Este calzado está ligado a una venta, elimina primero la venta.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    break;
                }
                modelo.eliminar(c);
                break;
            }
            case "altaCompradorBtn": {
                Comprador co = new Comprador();
                co.setNombre(vista.txtNombreComprador.getText());
                co.setApellidos(vista.txtApellidos.getText());
                co.setDni(vista.txtDNI.getText());
                modelo.insertar(co);
                break;
            }
            case "modificarCompradorBtn": {
                Comprador co = (Comprador) vista.listCompradores.getSelectedValue();
                co.setNombre(vista.txtNombreComprador.getText());
                co.setApellidos(vista.txtApellidos.getText());
                co.setDni(vista.txtDNI.getText());
                modelo.modificar(co);
            }
            break;
            case "eliminarCompradorBtn": {
                Comprador co = (Comprador) vista.listCompradores.getSelectedValue();
                modelo.eliminar(co);
                break;
            }
            case "altaTiendaBtn": {
                Tienda t = new Tienda();
                t.setNombre(vista.txtNombreTienda.getText());
                t.setTelefono(vista.txtTlfTienda.getText());
                modelo.insertar(t);
                break;
            }
            case "modificarTiendaBtn": {
                Tienda t = (Tienda) vista.listTiendas.getSelectedValue();
                t.setNombre(vista.txtNombreTienda.getText());
                t.setTelefono(vista.txtTlfTienda.getText());
                modelo.modificar(t);
            }
            break;
            case "eliminarTiendaBtn": {
                Tienda t = (Tienda) vista.listTiendas.getSelectedValue();
                if (!comprobarTiendaVenta(t.getIdtienda())) {
                    JOptionPane.showMessageDialog(null, "Esta tienda está ligada a una venta, elimina primero la venta.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    break;
                }
                modelo.eliminar(t);
                break;
            }

            case "altaMarcaBtn": {
                Marca m = new Marca();
                m.setNombre(vista.txtNombreMarca.getText());
                m.setTelefono(vista.txtTlfMarca.getText());
                modelo.insertar(m);
                break;
            }
            case "modificarMarcaBtn": {
                Marca m = (Marca) vista.listMarcas.getSelectedValue();
                m.setNombre(vista.txtNombreMarca.getText());
                m.setTelefono(vista.txtTlfMarca.getText());
                modelo.modificar(m);
            }
            break;
            case "eliminarMarcaBtn": {
                Marca m = (Marca) vista.listMarcas.getSelectedValue();
                modelo.eliminar(m);
                break;
            }
            case "altaVentaBtn": {
                VentaCalzado v = new VentaCalzado();
                v.setPrecio(Integer.valueOf(vista.txtPrecio.getText()));
                v.setCalzado((Calzado) vista.comboCalzados.getSelectedItem());
                v.setTienda((Tienda) vista.comboTiendas.getSelectedItem());
                modelo.insertar(v);
                break;
            }
            case "modificarVentaBtn": {
                VentaCalzado v = (VentaCalzado) vista.listVentas.getSelectedValue();
                v.setPrecio(Integer.valueOf(vista.txtPrecio.getText()));
                v.setCalzado((Calzado) vista.comboCalzados.getSelectedItem());
                v.setTienda((Tienda) vista.comboTiendas.getSelectedItem());
                modelo.modificar(v);
                break;
            }
            case "eliminarVentaBtn": {
                VentaCalzado v = (VentaCalzado) vista.listVentas.getSelectedValue();
                modelo.eliminar(v);
                break;
            }
            case "listarMarcaCalzadosBtn": {
                Marca mar = (Marca) vista.listMarcas.getSelectedValue();
                listarMarcaCalzados(modelo.getMarcaCalzados(mar));
            }
        }
        limpiarCampos();
        actualizar();
    }

    /***
     * Comprobar si un calzado está en el listado de ventas
     * @param id del calzado
     * @return true si se puede borrar, false si no
     */
    private boolean comprobarCalzadoVenta(int id) {
        for (VentaCalzado venta : modelo.getVentas()) {
            Calzado c = venta.getCalzado();
            if (c.getIdcalzado() == id) {
                return false;
            }
        }
        return true;
    }

    /***
     * Comprobar si una tienda está en el listado de ventas
     * @param id de la tienda
     * @return true si se puede borrar, false si no
     */
    private boolean comprobarTiendaVenta(int id) {
        for (VentaCalzado venta : modelo.getVentas()) {
            Tienda t = venta.getTienda();
            if (t.getIdtienda() == id) {
                return false;
            }
        }
        return true;
    }

    /***
     * Limpiar los campos
     */
    private void limpiarCampos() {
        limpiarCamposCalzados();
        limpiarCamposCompradores();
        limpiarCamposMarcas();
        limpiarCamposTiendas();
        limpiarCamposVentas();
    }

    /***
     * Limpiar los campos de las ventas
     */
    private void limpiarCamposVentas() {
        vista.comboCalzados.setSelectedItem(-1);
        vista.comboTiendas.setSelectedItem(-1);
        vista.txtPrecio.setText("");
    }

    /***
     * Limpiar los campos de las tiendas
     */
    private void limpiarCamposTiendas() {
        vista.txtNombreTienda.setText("");
        vista.txtTlfTienda.setText("");
    }

    /***
     * Limpiar los campos de las marcas
     */
    private void limpiarCamposMarcas() {
        vista.txtNombreMarca.setText("");
        vista.txtTlfMarca.setText("");
    }

    /***
     * Limpiar los campos de los compradores
     */
    private void limpiarCamposCompradores() {
        vista.txtNombreComprador.setText("");
        vista.txtApellidos.setText("");
        vista.txtDNI.setText("");
    }

    /***
     * Limpiar los campos de los calzados
     */
    private void limpiarCamposCalzados() {
        vista.txtModelo.setText("");
        vista.txtSku.setText("");
        vista.comboMarcas.setSelectedItem(-1);
    }

    /***
     * Actualizar los campos
     */
    private void actualizar() {
        listarCalzados(modelo.getCalzados());
        listarCompradores(modelo.getCompradores());
        listarTiendas(modelo.getTiendas());
        listarMarcas(modelo.getMarcas());
        listarVentas(modelo.getVentas());
    }

    /***
     * Listar los compradores
     * @param lista de compradores
     */
    public void listarCompradores(ArrayList<Comprador> lista) {
        vista.dlmCompradores.clear();
        for (Comprador c : lista) {
            vista.dlmCompradores.addElement(c);
        }
    }

    /***
     * Listar las tiendas
     * @param lista de tiendas
     */
    private void listarTiendas(ArrayList<Tienda> lista) {
        vista.dlmTiendas.clear();
        for (Tienda t : lista) {
            vista.dlmTiendas.addElement(t);
        }
        vista.comboTiendas.removeAllItems();
        ArrayList<Tienda> tie = modelo.getTiendas();

        for (Tienda t : tie) {
            vista.comboTiendas.addItem(t);
        }
        vista.comboTiendas.setSelectedIndex(-1);
    }

    /***
     * Listar las marcas
     * @param lista de marcas
     */
    private void listarMarcas(ArrayList<Marca> lista) {
        vista.dlmMarcas.clear();
        for (Marca m : lista) {
            vista.dlmMarcas.addElement(m);
        }
        vista.comboMarcas.removeAllItems();
        ArrayList<Marca> ma = modelo.getMarcas();

        for (Marca m : ma) {
            vista.comboMarcas.addItem(m);
        }
        vista.comboMarcas.setSelectedIndex(-1);
    }

    /***
     * Listar los calzados
     * @param lista de calzados
     */
    public void listarCalzados(ArrayList<Calzado> lista) {
        vista.dlmCalzados.clear();
        for (Calzado unCalzado : lista) {
            vista.dlmCalzados.addElement(unCalzado);
        }
        vista.comboCalzados.removeAllItems();
        ArrayList<Calzado> calz = modelo.getCalzados();

        for (Calzado c : calz) {
            vista.comboCalzados.addItem(c);
        }
        vista.comboCalzados.setSelectedIndex(-1);
    }

    /***
     * Listar las ventas
     * @param ventas lista de ventas
     */
    public void listarVentas(List<VentaCalzado> ventas) {
        vista.dlmVentas.clear();
        for (VentaCalzado venta : ventas) {
            vista.dlmVentas.addElement(venta);
        }

        vista.comboCalzados.removeAllItems();
        ArrayList<Calzado> c = modelo.getCalzados();
        vista.comboTiendas.removeAllItems();
        ArrayList<Tienda> t = modelo.getTiendas();


        for (Calzado calzado : c) {
            vista.comboCalzados.addItem(calzado);
        }
        for (Tienda tienda : t) {
            vista.comboTiendas.addItem(tienda);
        }
        vista.comboCalzados.setSelectedIndex(-1);
        vista.comboTiendas.setSelectedIndex(-1);
    }

    /***
     * Listar los calzados que tiene una marca
     * @param lista de calzados
     */
    public void listarMarcaCalzados(List<Calzado> lista) {
        vista.dlmMarcaCalzados.clear();
        for (Calzado unCalzado : lista) {
            vista.dlmMarcaCalzados.addElement(unCalzado);
        }
    }

    /***
     * Añadir los listener
     * @param listener
     */
    private void addActionListeners(ActionListener listener) {
        vista.conexionItem.addActionListener(listener);
        vista.salirItem.addActionListener(listener);
        vista.nuevoCalzadoBtn.addActionListener(listener);
        vista.eliminarCalzadoBtn.addActionListener(listener);
        vista.modificarCalzadoBtn.addActionListener(listener);
        vista.altaCompradorBtn.addActionListener(listener);
        vista.modificarCompradorBtn.addActionListener(listener);
        vista.eliminarCompradorBtn.addActionListener(listener);
        vista.altaTiendaBtn.addActionListener(listener);
        vista.modificarTiendaBtn.addActionListener(listener);
        vista.eliminarTiendaBtn.addActionListener(listener);
        vista.altaMarcaBtn.addActionListener(listener);
        vista.modificarMarcaBtn.addActionListener(listener);
        vista.eliminarMarcaBtn.addActionListener(listener);
        vista.altaVentaBtn.addActionListener(listener);
        vista.modificarVentaBtn.addActionListener(listener);
        vista.eliminarVentaBtn.addActionListener(listener);
        vista.listarMarcaCalzadosBtn.addActionListener(listener);
    }

    /**
     * Añadir los listeners de las listas
     * @param listener
     */
    private void addListSelectionListener(ListSelectionListener listener) {
        vista.listCalzados.addListSelectionListener(listener);
        vista.listCompradores.addListSelectionListener(listener);
        vista.listTiendas.addListSelectionListener(listener);
        vista.listMarcas.addListSelectionListener(listener);
        vista.listVentas.addListSelectionListener(listener);
        vista.listMarcasCalzados.addListSelectionListener(listener);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) {
            if (e.getSource() == vista.listCalzados) {
                Calzado calzadoSeleccion = (Calzado) vista.listCalzados.getSelectedValue();
                vista.txtModelo.setText(String.valueOf(calzadoSeleccion.getModelo()));
                vista.txtSku.setText(String.valueOf(calzadoSeleccion.getSku()));
                vista.comboMarcas.setSelectedItem(calzadoSeleccion.getMarca());
            }
            if (e.getSource() == vista.listCompradores) {
                Comprador compradorSeleccionado = (Comprador) vista.listCompradores.getSelectedValue();
                vista.txtNombreComprador.setText(String.valueOf(compradorSeleccionado.getNombre()));
                vista.txtApellidos.setText(String.valueOf(compradorSeleccionado.getApellidos()));
                vista.txtDNI.setText(String.valueOf(compradorSeleccionado.getDni()));
            }
            if (e.getSource() == vista.listTiendas) {
                Tienda t = (Tienda) vista.listTiendas.getSelectedValue();
                vista.txtNombreTienda.setText(String.valueOf(t.getNombre()));
                vista.txtTlfTienda.setText(String.valueOf(t.getTelefono()));
            }
            if (e.getSource() == vista.listMarcas) {
                Marca m = (Marca) vista.listMarcas.getSelectedValue();
                vista.txtNombreMarca.setText(String.valueOf(m.getNombre()));
                vista.txtTlfMarca.setText(String.valueOf(m.getTelefono()));
            }
            if (e.getSource() == vista.listVentas) {
                VentaCalzado vc = (VentaCalzado) vista.listVentas.getSelectedValue();
                vista.comboCalzados.setSelectedItem(vc.getCalzado());
                vista.comboTiendas.setSelectedItem(vc.getTienda());
                vista.txtPrecio.setText(String.valueOf(vc.getPrecio()));
            }
        }

    }
}

