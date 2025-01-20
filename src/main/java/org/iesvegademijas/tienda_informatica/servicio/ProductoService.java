package org.iesvegademijas.tienda_informatica.servicio;

import org.iesvegademijas.tienda_informatica.dao.ProductoDAO;
import org.iesvegademijas.tienda_informatica.modelo.Fabricante;
import org.iesvegademijas.tienda_informatica.modelo.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoDAO productoDAO;

    public List<Producto> listAll(){
        return productoDAO.getAll();
    };

    public Producto one(Integer id) {
        Optional<Producto> optPro = productoDAO.find(id);
        if (optPro.isPresent())
            return optPro.get();
        else
            return null;
    }

    public void newProducto(Producto producto) {

        productoDAO.create(producto);
    }

    public void replaceProducto(Producto producto) {
        productoDAO.update(producto);

    }

    public void deleteProducto(int id) {

        productoDAO.delete(id);

    }

}
