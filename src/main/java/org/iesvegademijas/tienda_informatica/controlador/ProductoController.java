package org.iesvegademijas.tienda_informatica.controlador;

import org.iesvegademijas.tienda_informatica.modelo.Fabricante;
import org.iesvegademijas.tienda_informatica.modelo.Producto;
import org.iesvegademijas.tienda_informatica.servicio.FabricanteService;
import org.iesvegademijas.tienda_informatica.servicio.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private FabricanteService fabricanteService;

    @GetMapping("/productos")
    public String listar(Model model) {

        List<Fabricante> listFab = fabricanteService.listAll();
        List<Producto> listAllPro =  productoService.listAll();
        model.addAttribute("listaProductos", listAllPro);
        model.addAttribute("listFab",listFab);

        return "productos";
    }

    @GetMapping("/productos/{id}")
    public String detalle(Model model, @PathVariable Integer id ) {

        Producto producto = productoService.one(id);
        model.addAttribute("producto", producto);
        List<Fabricante> listFab = fabricanteService.listAll();
        model.addAttribute("listFab",listFab);

        return "detalle-producto";
    }

    @GetMapping("/productos/crear")
    public String crear(Model model) {

        List<Fabricante> listFab = fabricanteService.listAll();
        Producto producto = new Producto();
        model.addAttribute("producto", producto);
        model.addAttribute("listFab",listFab);

        return "crear-producto";
    }

    @PostMapping("/productos/crear")
    public RedirectView submitCrear(@ModelAttribute("producto") Producto producto) {

        productoService.newProducto(producto);
        return new RedirectView("/productos") ;

    }

    @GetMapping("/productos/editar/{id}")
    public String editar(Model model, @PathVariable Integer id) {

        Producto producto = productoService.one(id);
        model.addAttribute("producto", producto);
        List<Fabricante> listFab = fabricanteService.listAll();
        model.addAttribute("listFab",listFab);

        return "editar-producto";
    }

    @PostMapping("/productos/editar/{id}")
    public RedirectView submitEditar(@ModelAttribute("producto") Producto producto) {

        productoService.replaceProducto(producto);

        return new RedirectView("/productos");
    }

    @PostMapping("/productos/borrar/{id}")
    public RedirectView submitBorrar(@PathVariable Integer id) {

        productoService.deleteProducto(id);

        return new RedirectView("/productos");
    }



}

