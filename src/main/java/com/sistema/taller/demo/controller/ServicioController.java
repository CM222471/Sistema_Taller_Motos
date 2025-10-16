package com.sistema.taller.demo.controller;
import java.time.LocalDate;
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

import com.sistema.taller.demo.model.CuentaPorCobrar;
import com.sistema.taller.demo.model.DetalleServicio;
import com.sistema.taller.demo.model.Productos;
import com.sistema.taller.demo.model.Servicios;
import com.sistema.taller.demo.service.ClienteService;
import com.sistema.taller.demo.service.CuentaPorCobrarService;
import com.sistema.taller.demo.service.DetalleServicioService;
import com.sistema.taller.demo.service.ProductoService;
import com.sistema.taller.demo.service.ServicioService;


@Controller
public class ServicioController {
    
    @Autowired
    private ServicioService servicioService;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private ProductoService productoService;
    @Autowired
    private DetalleServicioService detalleService;
    @Autowired
    private CuentaPorCobrarService cuentaPorCobrarService;
    
    

    //pantalla Servicio
    @GetMapping("/servicios")
    public String listarServicios(Model model){
        List<Servicios> servicios = servicioService.obtenerTodos();
        model.addAttribute("servicios", servicios);
        return "servicios/servicios";
    }
    @GetMapping("/servicios/detalle/{id}")
    public String detalleServicio(@PathVariable("id") Integer id, Model model) {
    Servicios servicio = servicioService.obtenerPorId(id);
    if (servicio == null) {
        // Redirigir a otra página o mostrar mensaje de error
        return "redirect:/servicios?error=ServicioNoEncontrado";
    }
    model.addAttribute("servicio", servicio);
    return "servicios/servicio_detalle";
    }
    

    // Formulario nuevo servicio
    @GetMapping("/servicios/nuevo")
    public String mostrarFormularioServicio(Model model) {
        model.addAttribute("clientes", clienteService.obtenerTodos());
        model.addAttribute("productos", productoService.obtenerTodos());
        model.addAttribute("servicios", new Servicios());
        return "servicios/crear_editar_servicio";
    }

    

    //guardar servicio
   @PostMapping("/servicios/crear")
    public String guardarServicio(@ModelAttribute Servicios servicio , @RequestParam(value = "productosIds", required = false) List<Long> productosIds,
        @RequestParam(value = "cantidades", required = false) List<Integer> cantidades, RedirectAttributes redirectAttributes) {
        try {
            double Total =0;
            double manodeobra = servicio.getPrecioBase();

            servicio.setEstadoPago( "Pendiente");
            servicioService.guardar(servicio);

            if (productosIds != null && cantidades != null) {
            for (int i = 0; i < productosIds.size(); i++) {
                Long idProducto = productosIds.get(i);
                Integer cantidad = cantidades.get(i);
                DetalleServicio detalle = new DetalleServicio();

                Productos productousado = productoService.obtenerPorId(idProducto.intValue());
                Total = Total + ( productousado.getPrecio() * cantidad);
                detalle.setIdServicio(servicio);
                detalle.setIdProducto(productousado);
                detalle.setCantidadUsada(cantidad);
                detalle.setPrecioUnitario(productousado.getPrecio());
                detalle.setSubtotal(productousado.getPrecio() * cantidad);
                detalleService.guardar(detalle);

                // 🔹 Actualizar inventario
                 int nuevoStock = productousado.getStockActual() - cantidad;
                  if (nuevoStock < 0) {
                // Evita stock negativo
                    throw new IllegalArgumentException("Stock insuficiente para el producto: " + productousado.getNombreProducto());
                    }
                  productousado.setStockActual(nuevoStock);
                 productoService.guardar(productousado);
            }
            }
            Servicios servicioexistente = servicioService.obtenerPorId(servicio.getIdServicio());
            servicioexistente.setPrecioBase(Total + manodeobra);
            servicioService.guardar(servicioexistente);

            CuentaPorCobrar cuenta = new CuentaPorCobrar();
            cuenta.setServicio(servicioexistente);
            cuenta.setCliente(servicioexistente.getIdCliente());
            cuenta.setMontoTotal(servicioexistente.getPrecioBase()); // Total + mano de obra
            cuenta.setMontoPagado(0.0);
            cuenta.setSaldoPendiente(cuenta.getMontoTotal());
            cuenta.setUltimaFechaPago(LocalDate.now());
            cuenta.setEstado("Pendiente");

            cuentaPorCobrarService.guardar(cuenta);
            redirectAttributes.addFlashAttribute("mensaje", "El servicio se registró exitosamente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (Exception e) {

             e.printStackTrace(); // imprime stack trace completo
    // También puedes mostrar solo el mensaje
    System.out.println("Error al registrar el servicio: " + e.getMessage());
            redirectAttributes.addFlashAttribute("mensaje",
                    "Ocurrió un error al registrar el servicio.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
        }
        return "redirect:/servicios";
    }   
}
