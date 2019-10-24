package ar.com.grupoesfera.biblioteca.repo;

import java.util.List;

import ar.com.grupoesfera.biblioteca.modelo.Libro;
import ar.com.grupoesfera.main.App;

public class BaseDeLibros {

    @SuppressWarnings("unchecked")
    public List<Libro> obtenerTodos() {
        
        return App.instancia().obtenerEntityManager().createQuery("select l from Libro l").getResultList();
    }

    public Libro obtenerLibroPorId(long id) throws Exception {
        List<Libro> libros = App.instancia()
                .obtenerEntityManager()
                .createQuery(String.format("select l from Libro l where id = %d", id))
                .getResultList();
        if (libros.size() > 0) {
            return libros.get(0);
        } else {
            throw new Exception("Libro no encontrado");
        }
    }
}
