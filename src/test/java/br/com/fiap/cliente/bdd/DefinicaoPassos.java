package br.com.fiap.cliente.bdd;

import br.com.fiap.cliente.api.dto.request.ClienteRequest;
import br.com.fiap.cliente.api.dto.response.ClienteResponse;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class DefinicaoPassos {

    private Response response;

    private ClienteResponse clienteResponse;

    private final String ENDPOINT_BASE = "http://localhost:8080/lanchonete";

    @Quando("requisitar o registro de um novo cliente com email invalido")
    public void requisitarRegistroDeNovoClienteComEmailInvalido() {
        var clienteRequest = new ClienteRequest("Diego","teste.com","002.546.450-70");
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(clienteRequest)
                .when().post(ENDPOINT_BASE + "/clientes");
    }
    @Então("um erro de email invalido é retornada")
    public void erroDeEmailEnvalidoRetornado() {
        response.then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Quando("requisitar o registro de um novo cliente com cpf invalido")
    public void requisitarRegistroDeNovoClienteComCpfInvalido() {
        var clienteRequest = new ClienteRequest("Teste","teste@teste.com","00000000000");
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(clienteRequest)
                .when().post(ENDPOINT_BASE + "/clientes");
    }
    @Então("um erro de cpf invalido é retornada")
    public void erroDeCpfEnvalidoRetornado() {
        response.then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Quando("requisitar o registro de um novo cliente")
    public void requisitarRegistroDeNovoCliente() {
        var clienteRequest = new ClienteRequest("Diego","diego.teste@teste.com","15212027020");
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(clienteRequest)
                .when().post(ENDPOINT_BASE + "/clientes");
        clienteResponse = response.then().extract().as(ClienteResponse.class);
    }
    @Então("um novo cliente é registrado com sucesso")
    public void novoClienteRegistradoComSucesso() {
        response.then()
                .statusCode(HttpStatus.CREATED.value())
                .body(matchesJsonSchemaInClasspath("./schemas/ClienteResponseSchema.json"));
    }
    @Quando("requisitar o registro de um novo cliente já existente")
    public void requisitarRegistroDeNovoClienteExistente() {
        var clienteRequest = new ClienteRequest("Diego","diego.teste@teste.com","15212027020");
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(clienteRequest)
                .when().post(ENDPOINT_BASE + "/clientes");
    }
    @Então("um erro de cliente já registrado é retornada")
    public void retornaErroDeClienteRegistrado() {
        response.then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Quando("requisitar um cliente por cpf")
    public void requisitar_um_cliente_por_cpf() {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get(ENDPOINT_BASE + "/clientes/{cpf}", "15212027020");
    }
    @Então("o cliente é exibido com sucesso")
    public void clienteExibidoComSucesso() {
        response.then()
                .statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath("./schemas/ClienteResponseSchema.json"));
    }

    @Quando("requisitar a lista de clientes")
    public void requisitarListaDeClientes() {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get(ENDPOINT_BASE + "/clientes");
    }
    @Então("a lista de clientes é exibida com sucesso")
    public void listaDeClientesExibidaComSucesso() {
        response.then()
                .statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath("./schemas/ClientesResponseSchema.json"));
    }


}
