package ar.com.grupoesfera.biblioteca.repo;

import java.util.List;
import java.util.stream.Collectors;

import ar.com.grupoesfera.biblioteca.modelo.Libro;
import ar.com.grupoesfera.main.App;

public class BaseDeLibros {

    @SuppressWarnings("unchecked")
    public List<Libro> obtenerTodos() {
        
        return App.instancia().obtenerEntityManager().createQuery("select l from Libro l").getResultList();
    }

    public List<Libro> obtenerLibrosPorAutor(String autor) {

        return obtenerTodos().stream().filter(l -> l.getAutor().contains(autor)).collect(Collectors.toList());
        //return App.instancia().obtenerEntityManager().createQuery("select l from Libro l where l.autor Like '%" + autor + "%'").getResultList();


    }

    public List<Libro> obtenerLibrosPorTitulo (String titulo) {

        return obtenerTodos().stream().filter(l -> l.getTitulo().contains(titulo)).collect(Collectors.toList());
        //return App.instancia().obtenerEntityManager().createQuery("select l from Libro l where l.titulo Like '%" + titulo + "%'").getResultList();

    }
}
