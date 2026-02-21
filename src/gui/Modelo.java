package gui;

import com.calzados.practica2ud3.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import javax.persistence.Query;
import java.util.ArrayList;

/***
 * Clase Modelo
 */
public class Modelo {

    SessionFactory sessionFactory;

    /***
     * Método desconectar
     */
    public void desconectar() {
        //Cierro la factoria de sessiones
        if (sessionFactory != null && sessionFactory.isOpen())
            sessionFactory.close();
    }

    /***
     * Método conectar
     */
    public void conectar() {
        Configuration configuracion = new Configuration();
        //Cargo el fichero Hibernate.cfg.xml
        configuracion.configure("hibernate.cfg.xml");

        //Indico la clase mapeada con anotaciones
        configuracion.addAnnotatedClass(Tienda.class);
        configuracion.addAnnotatedClass(Calzado.class);
        configuracion.addAnnotatedClass(Comprador.class);
        configuracion.addAnnotatedClass(Marca.class);
        configuracion.addAnnotatedClass(CompradorCalzado.class);
        configuracion.addAnnotatedClass(CompradorTienda.class);
        configuracion.addAnnotatedClass(VentaCalzado.class); // o CalzadoTienda.class si tu entidad se llama así

        //Creamos un objeto ServiceRegistry a partir de los parámetros de configuración
        StandardServiceRegistry ssr = new StandardServiceRegistryBuilder()
                .applySettings(configuracion.getProperties()).build();

        //finalmente creamos un objeto sessionfactory a partir de la configuracion y del registro de servicios
        sessionFactory = configuracion.buildSessionFactory(ssr);
    }

    /***
     * Devuelve los calzados que tiene una marca
     * @param marca marca
     * @return lista de calzados
     */
    ArrayList<Calzado> getMarcaCalzados(Marca marca) {
        Session sesion = sessionFactory.openSession();
        Query query = sesion.createQuery("FROM Calzado WHERE marca = :m");
        query.setParameter("m", marca);
        ArrayList<Calzado> lista = (ArrayList<Calzado>) query.getResultList();
        sesion.close();
        return lista;
    }

    /***
     * Devuelve los calzados disponibles
     * @return lista de calzados
     */
    ArrayList<Calzado> getCalzados() {
        Session sesion = sessionFactory.openSession();
        Query query = sesion.createQuery("FROM Calzado");
        ArrayList<Calzado> lista = (ArrayList<Calzado>) query.getResultList();
        sesion.close();
        return lista;
    }

    /***
     * Devuelve los compradores disponibles
     * @return lista de compradores
     */
    ArrayList<Comprador> getCompradores() {
        Session sesion = sessionFactory.openSession();
        Query query = sesion.createQuery("FROM Comprador");
        ArrayList<Comprador> listaCompradores = (ArrayList<Comprador>) query.getResultList();
        sesion.close();
        return listaCompradores;
    }

    /***
     * Devuelve las tiendas disponibles
     * @return lista de tiendas
     */
    ArrayList<Tienda> getTiendas() {
        Session sesion = sessionFactory.openSession();
        Query query = sesion.createQuery("FROM Tienda");
        ArrayList<Tienda> listaTiendas = (ArrayList<Tienda>) query.getResultList();
        sesion.close();
        return listaTiendas;
    }

    /***
     * Devuelve las marcas disponibles
     * @return lista de marcas
     */
    ArrayList<Marca> getMarcas() {
        Session sesion = sessionFactory.openSession();
        Query query = sesion.createQuery("FROM Marca");
        ArrayList<Marca> listaMarcas = (ArrayList<Marca>) query.getResultList();
        sesion.close();
        return listaMarcas;
    }

    /***
     * Devuelve las ventas disponibles
     * @return lista de ventas
     */
    ArrayList<VentaCalzado> getVentas() {
        Session sesion = sessionFactory.openSession();
        Query query = sesion.createQuery("FROM VentaCalzado"); // o FROM CalzadoTienda
        ArrayList<VentaCalzado> ventas = (ArrayList<VentaCalzado>) query.getResultList();
        sesion.close();
        return ventas;
    }

    /***
     * Insertar un objeto en la BBDD
     * @param o objeto a insertar en la BBDD
     */
    void insertar(Object o) {
        //Obtengo una session a partir de la factoria de sesiones
        Session sesion = sessionFactory.openSession();

        sesion.beginTransaction();
        sesion.save(o);
        sesion.getTransaction().commit();

        sesion.close();
    }

    /***
     * Modificar un objeto de la BBDD
     * @param o objeto a modificar en la BBDD
     */
    void modificar(Object o) {
        Session sesion = sessionFactory.openSession();
        sesion.beginTransaction();
        sesion.saveOrUpdate(o);
        sesion.getTransaction().commit();
        sesion.close();
    }

    /***
     * Eliminar un objeto de la BBDD
     * @param o objeto a eliminar en la BBDD
     */
    void eliminar(Object o) {
        Session sesion = sessionFactory.openSession();
        sesion.beginTransaction();
        sesion.delete(o);
        sesion.getTransaction().commit();
        sesion.close();
    }
}
