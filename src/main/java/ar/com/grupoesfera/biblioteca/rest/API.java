package ar.com.grupoesfera.biblioteca.rest;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import ar.com.grupoesfera.biblioteca.modelo.Libro;
import ar.com.grupoesfera.biblioteca.modelo.Prestamo;
import ar.com.grupoesfera.biblioteca.modelo.Usuario;
import ar.com.grupoesfera.biblioteca.repo.BaseDeLibros;
import ar.com.grupoesfera.biblioteca.repo.BaseDePrestamos;
import ar.com.grupoesfera.biblioteca.repo.BaseDeUsuarios;
import ar.com.grupoesfera.main.App;

import java.util.List;
import java.util.stream.Collectors;

@Path("/")
public class API {

	private BaseDeUsuarios usuarios = App.instancia().obtenerRepoUsuarios();
	private BaseDeLibros libros = App.instancia().obtenerRepoLibros();
	private BaseDePrestamos prestamos = App.instancia().obtenerRepoPrestamos();
	
    @GET
    @Path("/hola")
    @Produces(MediaType.TEXT_PLAIN)
    public Response hola() {
        
        return Response.ok("Hola, mundo!").build();
    }

    @GET
    @Path("/libros")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerLibros() {
        
        return Response.ok(libros.obtenerTodos()).build();
    }

    @GET
    @Path("/libros/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerLibroPorId(@PathParam("id") Long id) {
        try {
            return Response.ok(libros.obtenerLibroPorId(id)).build();
        } catch(Exception e) {
            return Response.status(Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/libros/autor/{autor}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerLibrosPorAutor(@PathParam("autor") String autor) {
        List<Libro> librosBuscados = libros.obtenerLibrosPorAutor(autor);

        if(librosBuscados.size() > 0)
            return Response.ok(librosBuscados).build();
        else
            return Response.status(Status.NOT_FOUND).build();
    }

    @GET
    @Path("/libros/titulo/{titulo}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerLibrosPorTitulo(@PathParam("titulo") String titulo) {

        List<Libro> librosBuscados = libros.obtenerLibrosPorTitulo(titulo);

        if(librosBuscados.size() > 0)
            return Response.ok(librosBuscados).build();
        else
            return Response.status(Status.NOT_FOUND).build();

    }

    @GET
    @Path("/usuarios")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerUsuarios() {
        
        return Response.ok(usuarios.obtenerTodos()).build();
    }

    @GET
    @Path("/usuarios/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerUsuarioPorId(@PathParam("id") Long idUsuario) {
        Usuario usuarioBuscado = usuarios.obtenerTodos().stream().filter(usuario -> usuario.getId().equals(idUsuario)).findFirst().orElse(null);
        return usuarioBuscado == null ? Response.status(Status.NOT_FOUND).build() : Response.ok(usuarioBuscado).build();
    }

    @GET
    @Path("/usuarios/dni/{dni}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerUsuarioPorDNI(@PathParam("dni") Long dni) {
        
        return Response.status(Status.NOT_IMPLEMENTED).build();
    }

    @GET
    @Path("/usuarios/{id}/prestamos")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerLibrosPrestadosAUsuario(@PathParam("id") Long idUsuario) {
        List<Prestamo> prestamosDelUsuario = prestamos.obtenerTodos().stream()
                .filter(prestamo -> prestamo.getIdUsuario().equals(idUsuario))
                .collect(Collectors.toList());

        return prestamosDelUsuario.size() == 0 ? Response.status(Status.NOT_FOUND).build() : Response.ok(prestamosDelUsuario).build();
    }

    @GET
    @Path("/prestamos")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerPrestamos() {
        
        return Response.ok(prestamos.obtenerTodos()).build();
    }

    @POST
    @Path("/prestamos")
    @Produces(MediaType.APPLICATION_JSON)
    public Response prestar(@QueryParam("idLibro") Long idLibro, @QueryParam("idUsuario") Long idUsuario) {
        
        return Response.status(Status.NOT_IMPLEMENTED).build();
    }

    @GET
    @Path("/prestamos/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerPrestamoPorId(@PathParam("id") Long idPrestamo) {
        
        return Response.status(Status.NOT_IMPLEMENTED).build();
    }
}
