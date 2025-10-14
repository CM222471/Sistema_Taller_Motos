package com.sistema.taller.demo.controller;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sistema.taller.demo.model.CuentaPorCobrar;
import com.sistema.taller.demo.model.Pagos;
import com.sistema.taller.demo.model.Servicios;
import com.sistema.taller.demo.model.Usuario;
import com.sistema.taller.demo.service.CuentaPorCobrarService;
import com.sistema.taller.demo.service.PagoService;
import com.sistema.taller.demo.service.ServicioService;







@Controller

public class CuentasPorCobrarController {

    @Autowired
    private CuentaPorCobrarService cuentaPorCobrarService;
    @Autowired
    private PagoService pagoService;
    @Autowired
    private ServicioService servicioService;
    @Autowired
    private com.sistema.taller.demo.service.UsuarioService usuarioService;


    @GetMapping("/movimiento")
    public String listarCuentasPendientes(Model model){
        List<CuentaPorCobrar> cuentas = cuentaPorCobrarService.obtenerTodos();
        model.addAttribute("cuentas", cuentas);
        return "pagos/lista_cuentaspendientes";
    }

    @GetMapping("/movimiento/editar/{id}")
    public String getMethodName(@PathVariable Integer id, Model model) {
       CuentaPorCobrar cuenta = cuentaPorCobrarService.obtenerPorId(id);

    if (cuenta == null) {
        return "redirect:/cuentas?error=CuentaNoEncontrada";
    }

    model.addAttribute("cuenta", cuenta);
    return "pagos/generar_pago";
    }

    @PostMapping("/movimiento/pago")
public String generarPago(@RequestParam("idCuenta") Integer idCuenta,
        @RequestParam("montoAbono") Double montoAbono,
        RedirectAttributes redirectAttributes) {
    try {
        // 🔹 Obtener la cuenta pendiente relacionada al servicio
        CuentaPorCobrar cuenta = cuentaPorCobrarService.obtenerPorId(idCuenta);
        if (cuenta == null) {
            redirectAttributes.addFlashAttribute("mensaje", "No se encontró la cuenta pendiente asociada al servicio.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
            return "redirect:/cuentas";
        }

        // 🔹 Validar monto
        if (montoAbono > cuenta.getSaldoPendiente()) {
            redirectAttributes.addFlashAttribute("mensaje", "El monto pagado no puede ser mayor al saldo pendiente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
            return "redirect:/movimiento/editar/" + cuenta.getIdCuenta();
        }

       
         // 🔹 Obtener usuario logueado desde Spring Security
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); // nombre de usuario logueado
        Usuario usuario = usuarioService.obtenerPorUsername(username);
        if (usuario == null) {
            redirectAttributes.addFlashAttribute("mensaje", "Debe iniciar sesión para registrar un pago.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
            return "redirect:/login";
        }
        
        // 🔹 Registrar el pago
        Pagos pago = new Pagos();
        pago.setIdServicio(cuenta.getServicio());
        pago.setMontoPagado(montoAbono);
        pago.setMetodoPago("Efectivo");
        pago.setIdUsuario(usuario);
        pago.setUltimaFechaPago(LocalDate.now());
        pagoService.guardar(pago);

        // 🔹 Actualizar cuenta
        double nuevoMontoPagado = cuenta.getMontoPagado() + pago.getMontoPagado();
        double nuevoSaldo = cuenta.getMontoTotal() - nuevoMontoPagado;

        cuenta.setMontoPagado(nuevoMontoPagado);
        cuenta.setSaldoPendiente(nuevoSaldo);
        cuenta.setUltimaFechaPago(LocalDate.now());

        // 🔹 Actualizar estados según el saldo
        Servicios servicio = cuenta.getServicio();

        if (nuevoSaldo <= 0) {
            cuenta.setEstado("Pagado");
            servicio.setEstadoPago("Pagado");
            servicio.setEstado("Finalizado");
        } else {
            cuenta.setEstado("Pendiente");
            servicio.setEstadoPago("Parcial");
        }

        // 🔹 Guardar actualizaciones
        cuentaPorCobrarService.guardar(cuenta);
        servicioService.guardar(servicio);

        // 🔹 Mensaje de éxito
        redirectAttributes.addFlashAttribute("mensaje", "Pago registrado correctamente.");
        redirectAttributes.addFlashAttribute("tipoMensaje", "success");

    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Error al registrar el pago: " + e.getMessage());
        redirectAttributes.addFlashAttribute("mensaje", "Ocurrió un error al registrar el pago.");
        redirectAttributes.addFlashAttribute("tipoMensaje", "error");
    }

    return "redirect:/movimiento";
}

    
    
    

    
}