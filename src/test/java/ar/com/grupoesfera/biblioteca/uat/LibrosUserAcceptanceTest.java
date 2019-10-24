package ar.com.grupoesfera.biblioteca.uat;

import org.apache.http.HttpStatus;
import org.codehaus.jackson.map.jsontype.impl.AsExternalTypeDeserializer;
import org.hamcrest.Matchers;
import org.jboss.weld.context.http.Http;
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

    @Test
    public void deberiaDarOKAlLamarALibrosConAutorExistente() throws Exception {

        RespuestaServicio respuesta = invocarServicio("libros/autor/Kent");
        Assert.assertThat(respuesta.getCodigo(), Matchers.is(HttpStatus.SC_OK));

    }

    @Test
    public void deberiaDarNotFoundAlLlamarALibrosConAutorInexistente() throws Exception {

        RespuestaServicio respuesta = invocarServicio("libros/autor/asd");
        Assert.assertThat(respuesta.getCodigo(), Matchers.is(HttpStatus.SC_NOT_FOUND));

    }

    @Test
    public void deberiaDarOKAlLlamarALibrosConTituloExistente() throws Exception {

        RespuestaServicio respuesta = invocarServicio("libros/titulo/Design");
        Assert.assertThat(respuesta.getCodigo(), Matchers.is(HttpStatus.SC_OK));

    }

    @Test
    public void deberiaDarNotFoundAlLlamarALibrosConTituloInxistente() throws Exception {

        RespuestaServicio respuesta = invocarServicio("libros/titulo/asd");
        Assert.assertThat(respuesta.getCodigo(), Matchers.is(HttpStatus.SC_NOT_FOUND));

    }


}
