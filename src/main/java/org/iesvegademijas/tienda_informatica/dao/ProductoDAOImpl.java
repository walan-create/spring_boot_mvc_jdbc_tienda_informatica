package org.iesvegademijas.tienda_informatica.dao;

import lombok.extern.slf4j.Slf4j;
import org.iesvegademijas.tienda_informatica.modelo.Fabricante;
import org.iesvegademijas.tienda_informatica.modelo.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class ProductoDAOImpl implements ProductoDAO {

    @Autowired
    private JdbcClient jdbcClient;


    @Override
    public void create(Producto producto) {

        // Consulta para verificar si el id_fabricante existe en la tabla fabricante
//        boolean fabricanteExiste = jdbcClient.sql("SELECT COUNT(*) FROM fabricante WHERE codigo = ?")
//                .param(producto.getId_fabricante())
//                .query(rs -> rs.getInt(1) > 0);
//
//        //Metodo 2
//        Optional<Fabricante> optFab = new FabricanteDAOImpl().find(producto.getId_fabricante());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        int rowsUpdated = jdbcClient.sql("""
                            INSERT INTO producto (nombre, precio, id_fabricante)
                            VALUES (?,?,?)
                            """)
                .param(producto.getNombre())
                .param(producto.getPrecio())
                .param(producto.getId_fabricante())
                .update(keyHolder);

        producto.setCodigo(keyHolder.getKey().intValue());
        log.info("Insertados {} registrados",rowsUpdated);

    }

    @Override
    public List<Producto> getAll() {

        String query = """
                SELECT * FROM producto
                """;

        RowMapper<Producto> rowMapperProducto = (rs, rowNum) -> new Producto(
                rs.getInt("codigo"),
                rs.getString("nombre"),
                rs.getDouble("precio"),
                rs.getInt("id_fabricante")
        );

        List<Producto> productos = jdbcClient.sql(query)
                .query(rowMapperProducto)
                .list();

        return productos;
    }

    @Override
    public Optional<Producto> find(int id) {
        String query = """
                SELECT * FROM producto WHERE codigo = :codigo
                """;

        Optional<Producto> OptProd = jdbcClient.sql(query)
                .param("codigo", id)
                .query(Producto.class)
                .optional();

        return OptProd;
    }


    @Override
    public void update(Producto producto) {

        String query = """
                UPDATE producto
                SET
                nombre = :nombre,
                precio = :precio,
                id_fabricante = :id_fabricante
                WHERE
                codigo = :codigo
                """;
        int rowsUpdated = jdbcClient.sql(query)
                .paramSource(producto)
                .update();

        log.info("Actualizados {} registros",rowsUpdated);

    }

    @Override
    public void delete(int id) {
        String query = """
                DELETE FROM producto WHERE codigo = :codigo
                """;
        int rowsUpdated = jdbcClient.sql(query)
                .param("codigo",id)
                .update();

        log.info("Borrados {} registros",rowsUpdated);
    }
}
