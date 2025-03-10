package co.edu.javeriana.jpa_example2.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import co.edu.javeriana.jpa_example2.dto.ProductoDTO;
import co.edu.javeriana.jpa_example2.service.ProductoService;

@Controller
@RequestMapping("/producto")
public class ProductoController {
    @Autowired
    private ProductoService productoService;

    private Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping("/lista")
    public ModelAndView listarProductos() {
        log.info("Lista de Productos");
        List<ProductoDTO> productos = productoService.listarProductos();
        ModelAndView modelAndView = new ModelAndView("producto-lista");
        modelAndView.addObject("listaProductos", productos);
        return modelAndView;
    }

    @GetMapping("/view/{id}")
    public ModelAndView listarProducto(@PathVariable Long id) {
        ProductoDTO producto = productoService.buscarProducto(id).orElseThrow();
        ModelAndView modelAndView = new ModelAndView("producto-view");
        modelAndView.addObject("producto", producto);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView formularioCrearProducto() {
        ModelAndView modelAndView = new ModelAndView("producto-edit");
        modelAndView.addObject("producto", new ProductoDTO());
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView formularioEditarProducto(@PathVariable Long id) {
        ProductoDTO productoDTO = productoService.buscarProducto(id).orElseThrow();
        ModelAndView modelAndView = new ModelAndView("producto-edit");
        modelAndView.addObject("producto", productoDTO);
        return modelAndView;
    }
    
    @PostMapping("/save")
    public RedirectView guardarProducto(@ModelAttribute ProductoDTO productoDTO) {
        productoService.guardarProducto(productoDTO);
        return new RedirectView("/producto/lista");
    }

    @GetMapping("/delete/{id}")
    public RedirectView borrarProducto(@PathVariable Long id) {
        productoService.borrarProducto(id);
        return new RedirectView("/producto/lista");
    }
}
