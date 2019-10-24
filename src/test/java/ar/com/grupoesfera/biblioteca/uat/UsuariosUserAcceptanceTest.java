package ar.com.grupoesfera.biblioteca.uat;

import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import com.jayway.jsonpath.matchers.JsonPathMatchers;

public class UsuariosUserAcceptanceTest extends UserAcceptanceTest {

    /*@Test
    public void deberiaDarUsuariosAlLlamarAUsuariosSinParametros() throws Exception {

        RespuestaServicio respuesta = invocarServicio("usuarios");
        Assert.assertThat(respuesta.getCodigo(), Matchers.is(HttpStatus.SC_OK));
        Assert.assertThat(respuesta.getTexto(), JsonPathMatchers.isJson());
        Assert.assertThat(respuesta.getTexto(), JsonPathMatchers.hasJsonPath("$[*].id", Matchers.hasItems(1, 2, 3, 4, 5, 6, 7)));
        Assert.assertThat(respuesta.getTexto(), JsonPathMatchers.hasJsonPath("$[*].nombre", 
            Matchers.hasItems("Marcelo", "Brenda", "India", "Leon", "Alejandro", "Santiago", "Sebastian")));
    }

     */
    @Test
    public void deberiaDarNotFoundAlLlamarAUsuariosConIdValidoInexistente() throws Exception {

        RespuestaServicio respuesta = invocarServicio("usuarios/1000");
        Assert.assertThat(respuesta.getCodigo(), Matchers.is(HttpStatus.SC_NOT_FOUND));
    }

    //@Test
    public void deberiaDarNotFoundAlLlamarAUsuariosConIdInvalido() throws Exception {

        RespuestaServicio respuesta = invocarServicio("usuarios/id-invalido");
        Assert.assertThat(respuesta.getCodigo(), Matchers.is(HttpStatus.SC_NOT_FOUND));
    }
}
