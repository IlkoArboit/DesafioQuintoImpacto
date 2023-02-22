/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Entidades.Administrador;
import Entidades.Alumno;
import Entidades.Curso;
import Entidades.Profesor;
import Servicios.AdministradorService;
import Servicios.AlumnoService;
import Servicios.CursoService;
import Servicios.ProfesorService;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/Administradores")
public class AdministradorController {

    @Autowired
    private AdministradorService AdministradorService;
    @Autowired
    private ProfesorService ProfesorService;
    @Autowired
    private CursoService CursoService;
    @Autowired
    private AlumnoService AlumnoService;

    @GetMapping("/")
    public String admin(ModelMap modelo){
        List<Administrador> listaAdministradores = AdministradorService.listarAdministradores();
        
        modelo.addAttribute("Administradores", listaAdministradores);
        return "Administradores.html";
    }

    @GetMapping("/CrearAdmin")
    public String crearAdmin() throws Exception{
        return "CargaAdministrador.html";
    }

    @PostMapping("/CrearAdmin")
    public String crearAdmin(@RequestParam String nombre, @RequestParam String apellido, ModelMap modelo){
        try{
            AdministradorService.crear(nombre, apellido);
            modelo.put("exito", "Creado exitosamente");
            return "redirect:/Administradores/";
        }catch (Exception e){
            
            modelo.put("error", "No se pudo crear");
            return "CrearAdmin";
        }
    }

    @GetMapping("/ModificarAdmin/{id}")
    public String modificarAdmin(@PathVariable String id, ModelMap modelo){
        
        modelo.put("Admin", AdministradorService.buscarPorID(id));

        return "ModificarAdministrador.html";
    }

    @PostMapping("/ModificarAdmin/{id}")
    public String modificarAdmin(ModelMap modelo, @PathVariable String id, @RequestParam String nombre, @RequestParam String apellido){
        try{
            AdministradorService.modificar(id, nombre, apellido);
            modelo.put("exito", "Modificado Existosamente");
            return "redirect:/Administradores/";
        }catch (Exception e){
            modelo.put("error", "No se ha podido modificar");
            return "ModificarAdministrador.html";
        }
    }

    @PostMapping("/EliminarAdmin/{id}")
    public String eliminarAdmin(ModelMap modelo, @PathVariable String id) throws Exception{

        try{
            AdministradorService.eliminar(id);
            modelo.put("exito", "Eliminado Existosamente");
            return "redirect:/Administradores/";
        }catch (Exception e){
            modelo.put("error", "No se ha podido eliminar");
            return "ModificarAdministrador.html";
        }
    }


}
