package ar.com.grupoesfera.biblioteca.uat;

import ar.com.grupoesfera.biblioteca.uat.UserAcceptanceTest;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

public class PrestamosUserAcceptanceTest extends UserAcceptanceTest {

    @Test
    public void deberiaDarLaListaDePrestamosDelUsuarioPorId() throws Exception {
        UserAcceptanceTest.RespuestaServicio respuesta = invocarServicio("usuarios/1/prestamos");
        Assert.assertThat(respuesta.getCodigo(), Matchers.is(HttpStatus.SC_OK));

    }

    @Test
    public void deberiaDarQueNoExistenPrestamosDelUsuarioEspecificado() throws Exception {
        UserAcceptanceTest.RespuestaServicio respuesta = invocarServicio("/usuarios/1/prestamos");
        Assert.assertThat(respuesta.getCodigo(), Matchers.is(HttpStatus.SC_NOT_FOUND));
    }
}
