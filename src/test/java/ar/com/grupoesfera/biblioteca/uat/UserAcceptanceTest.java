package ar.com.grupoesfera.biblioteca.uat;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.jayway.jsonpath.matchers.JsonPathMatchers;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import ar.com.grupoesfera.main.PioServer;

public class UserAcceptanceTest {

    protected static final Log log = LogFactory.getLog(UserAcceptanceTest.class);

    @BeforeClass
    public static void preparar() {

        log.info("Iniciando un conjunto de pruebas de aceptación");
        PioServer.instancia().iniciarServer();
    }
    
    @AfterClass
    public static void detener() {
        
        PioServer.instancia().detenerServer();
        log.info("Un conjunto de pruebas de aceptación ha finalizado");
    }

    //@Test
    public void deberiaDecirHolaMundo() throws Exception {

        RespuestaServicio respuesta = invocarServicio("hola");
        Assert.assertThat(respuesta.getCodigo(), Matchers.is(HttpStatus.SC_OK));
        Assert.assertThat(respuesta.getTexto(), Matchers.is("Hola, mundo!"));
    }

    public RespuestaServicio invocarServicio(String urlServicio, String... params) throws Exception {

        final String urlBase = "http://localhost:8080/api/";

        RespuestaServicio respuesta = new RespuestaServicio();

        try {

            URL url = null;
            HttpURLConnection connection = null;

            if (params.length > 0) {

                String query = "";

                for (String param : params) {

                    query = query + "&" + param;
                }

                query = query.substring(1);

                url = new URL(urlBase + urlServicio + "?" + query);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");

            } else {

                url = new URL(urlBase + urlServicio);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
            }

            respuesta.setCodigo(connection.getResponseCode());

            if (respuesta.getCodigo() >= HttpStatus.SC_OK && respuesta.getCodigo() < HttpStatus.SC_MOVED_PERMANENTLY) {

                BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                respuesta.setTexto(input.readLine());

            } else {

                InputStream errorStream = connection.getErrorStream();

                if (errorStream != null) {

                    BufferedReader input = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                    respuesta.setTexto(input.readLine());
                }
            }

        } catch (Exception e) {

            LogFactory.getLog(UserAcceptanceTest.class).error(e);
            Assert.fail("La prueba falló por un error de conexión");
        }

        return respuesta;
    }

    public class RespuestaServicio {

        private Integer codigo;
        private String texto;

        public Integer getCodigo() {

            return codigo;
        }

        public void setCodigo(Integer codigo) {

            this.codigo = codigo;
        }

        public String getTexto() {

            return texto;
        }

        public void setTexto(String texto) {

            this.texto = texto;
        }
    }

        @Test
    public void deberiaDarUsuariosAlLlamarAUsuariosSinParametros() throws Exception {

        RespuestaServicio respuesta = invocarServicio("usuarios");
        Assert.assertThat(respuesta.getCodigo(), Matchers.is(HttpStatus.SC_OK));
        Assert.assertThat(respuesta.getTexto(), JsonPathMatchers.isJson());
        Assert.assertThat(respuesta.getTexto(), JsonPathMatchers.hasJsonPath("$[*].id", Matchers.hasItems(1, 2, 3, 4, 5, 6, 7)));
        Assert.assertThat(respuesta.getTexto(), JsonPathMatchers.hasJsonPath("$[*].nombre",
                Matchers.hasItems("Marcelo", "Brenda", "India", "Leon", "Alejandro", "Santiago", "Sebastian")));
    }


    @Test
    public void deberiaDarNotFoundAlLlamarAUsuariosConIdValidoInexistente() throws Exception {

        RespuestaServicio respuesta = invocarServicio("usuarios/1000");
        Assert.assertThat(respuesta.getCodigo(), Matchers.is(HttpStatus.SC_NOT_FOUND));
    }

    @Test
    public void deberiaDarNotFoundAlLlamarAUsuariosConIdInvalido() throws Exception {

        RespuestaServicio respuesta = invocarServicio("usuarios/id-invalido");
        Assert.assertThat(respuesta.getCodigo(), Matchers.is(HttpStatus.SC_NOT_FOUND));
    }

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

    @Test
    public void deberiaDarOKAlLlamarALibrosConIdValidoExistente() throws Exception {

        RespuestaServicio respuesta = invocarServicio("libros/1");
        Assert.assertThat(respuesta.getCodigo(), Matchers.is(HttpStatus.SC_OK));
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
