package ar.com.grupoesfera.biblioteca.uat;

import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import com.jayway.jsonpath.matchers.JsonPathMatchers;

public class LibrosUserAcceptanceTest extends UserAcceptanceTest {

    @Test
    public void deberiaDarOKAlLlamarALibrosConIdValidoExistente() throws Exception {

        RespuestaServicio respuesta = invocarServicio("libros/1");
        Assert.assertThat(respuesta.getCodigo(), Matchers.is(HttpStatus.SC_OK));
    }

    @Test
    public void deberiaDarNotFoundAlLlamarAUsuariosConIdValidoInexistente() throws Exception {

        RespuestaServicio respuesta = invocarServicio("libros/1000");
        Assert.assertThat(respuesta.getCodigo(), Matchers.is(HttpStatus.SC_NOT_FOUND));
    }
}
