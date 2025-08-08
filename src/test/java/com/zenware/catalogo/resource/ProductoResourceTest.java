package com.zenware.catalogo.resource;

import com.zenware.catalogo.model.Producto;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

import jakarta.ws.rs.core.MediaType;

@QuarkusTest
public class ProductoResourceTest {

    @Test
    public void testCrearYListar() {
        Producto producto = new Producto(null, "Lapicero My Little Pony", 2.5);

        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(producto)
                .when().post("/productos")
                .then()
                .statusCode(201)
                .body("nombre", is("Lapicero My Little Pony"))
                .body("precio", is(2.5f));

        RestAssured.when().get("/productos")
                .then()
                .statusCode(200)
                .body("size()", greaterThanOrEqualTo(1));
    }

    @Test
    public void testObtenerNoExistente() {
        RestAssured.when().get("/productos/999999")
                .then()
                .statusCode(404)
                .body("code", is(404))
                .body("message", is("No encontrado"));
    }
}
