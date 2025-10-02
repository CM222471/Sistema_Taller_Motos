package com.sistema.taller.demo.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sistema.taller.demo.model.Categorias;
import com.sistema.taller.demo.model.Productos;
import com.sistema.taller.demo.service.CategoriaService;
import com.sistema.taller.demo.service.ProductoService;


@Controller
public class ProductoController {
    @Autowired
    private ProductoService productoService;
     @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/productos")
    public String listarProductos(Model model){
        List<Productos> productos = productoService.obtenerTodos();
        model.addAttribute("productos", productos);
        return "productos/listar_productos";
    }

    // Formulario nuevo producto
    @GetMapping("/productos/nuevo")
    public String mostrarFormularioProducto(Model model) {
        model.addAttribute("categorias", categoriaService.obtenerTodos());
        model.addAttribute("productos", new Productos());
        return "productos/crear_editar_producto";
    }
    //guardar producto
    @PostMapping("/productos/crear")
    public String guardarProducto(@ModelAttribute Productos producto, RedirectAttributes redirectAttributes) {
        try {            
            productoService.guardar(producto);
            redirectAttributes.addFlashAttribute("mensaje", "El producto se registró exitosamente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje",
                    "Ocurrió un error al registrar el producto.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
        }
        return "redirect:/productos";
    }

    // Formulario editar producto
    @GetMapping("/productos/editar/{id}")
    public String editarProductoForm(@PathVariable Integer id, Model model) {
        Productos producto = productoService.obtenerPorId(id);
        if (producto == null) {
            return "redirect:/productos";
        }
        model.addAttribute("productos", producto);
        model.addAttribute("categorias", categoriaService.obtenerTodos());
        System.out.println("Estado del producto: " + producto.getEstado());
        return "productos/crear_editar_producto";
    }

    // Editar producto
    @PostMapping("/productos/editar/{id}")
    public String editarProducto(@PathVariable Integer id, @ModelAttribute Productos producto,
            RedirectAttributes redirectAttributes) {
        try {
            Productos productoExistente = productoService.obtenerPorId(id);
            if (productoExistente == null) {
                return "redirect:/productos";
            }

            productoExistente.setNombreProducto(producto.getNombreProducto());
            productoExistente.setStockActual(producto.getStockActual());
            productoExistente.setPrecio(producto.getPrecio());
            productoExistente.setDescripcion(producto.getDescripcion());
            productoExistente.setEstado(producto.getEstado());
            productoExistente.setIdCategoria(producto.getIdCategoria());

            productoService.guardar(productoExistente);
            redirectAttributes.addFlashAttribute("mensaje", "El producto se actualizó correctamente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Ocurrió un error al editar el producto.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
        }
        return "redirect:/productos";
    }

    // Eliminar producto
    @GetMapping("/productos/eliminar/{id}")
    public String eliminarProducto(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            productoService.eliminar(id);
            redirectAttributes.addFlashAttribute("mensaje", "El producto se eliminó correctamente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Ocurrió un error al eliminar el producto.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
        }
        return "redirect:/productos";
    }


    


}
