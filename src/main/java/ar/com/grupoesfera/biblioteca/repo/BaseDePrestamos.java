package ar.com.grupoesfera.biblioteca.repo;

import java.util.List;

import ar.com.grupoesfera.biblioteca.modelo.Prestamo;
import ar.com.grupoesfera.main.App;

public class BaseDePrestamos {

    @SuppressWarnings("unchecked")
    public List<Prestamo> obtenerTodos() {
        
        return App.instancia().obtenerEntityManager().createQuery("select p from Prestamo p").getResultList();
    }


}
