package com.zenware.catalogo.resource;

import com.zenware.catalogo.model.Producto;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Path("/productos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class ProductoResource {
    private final Map<Long, Producto> repo = new LinkedHashMap<>();
    private final AtomicLong seq = new AtomicLong(1);

    public ProductoResource() {
        repo.put(1L, new Producto(1L, "Producto Coca Cola Ejemplo", 10.0));
        seq.set(2);
    }

    @GET
    public Response listar() {
        return Response.ok(new ArrayList<>(repo.values())).build();
    }

    @POST
    public Response crear(@Valid Producto producto, @Context UriInfo uriInfo) {
        long id = seq.getAndIncrement();
        producto.setId(id);
        repo.put(id, producto);
        UriBuilder ub = uriInfo.getAbsolutePathBuilder().path(String.valueOf(id));
        return Response.created(ub.build()).entity(producto).build();
    }

    @GET
    @Path("{id}")
    public Response obtener(@PathParam("id") Long id) {
        Producto producto = repo.get(id);
        if (producto == null) {
            throw new NotFoundException("Producto no encontrado");
        }
        return Response.ok(producto).build();
    }

    @DELETE
    @Path("{id}")
    public Response eliminar(@PathParam("id") Long id) {
        Producto removed = repo.remove(id);
        if (removed == null) {
            throw new NotFoundException("Producto no encontrado");
        }
        return Response.noContent().build();
    }

}
