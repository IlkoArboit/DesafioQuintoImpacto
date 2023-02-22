/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Entidades.Profesor;
import Servicios.ProfesorService;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/profesores")
public class ProfesorController {
    @Autowired
    private ProfesorService ProfesorService;

    @GetMapping("/")
    public String profesor(ModelMap modelo){
        List<Profesor> listaProfesores = ProfesorService.listarProfesores();
        
        modelo.addAttribute("Profesores", listaProfesores);
        return "Profesores.html";
    }

    @GetMapping("/CrearProfesor")
    public String crearProfesor() throws Exception{
        return "CargaProfesor.html";
    }

    @PostMapping("/CrearProfesor")
    public String crearProfesor(@RequestParam String nombre, @RequestParam String apellido, @RequestParam int dni, ModelMap modelo){
        try{
            ProfesorService.crear(nombre, apellido, dni);
            modelo.put("exito", "Creado exitosamente");
            return "redirect:/Profesores/";
        }catch (Exception e){
            
            modelo.put("error", "No se pudo crear");
            return "CrearProfesor";
        }
    }

    @GetMapping("/ModificarProfesor/{id}")
    public String modificarProfesor(@PathVariable String id, ModelMap modelo){
        
        modelo.put("Profesor", ProfesorService.buscarPorID(id));

        return "ModificarProfesor.html";
    }

    @PostMapping("/ModificarProfesor/{id}")
    public String modificarProfesor(ModelMap modelo, @PathVariable String id, @RequestParam String nombre, @RequestParam String apellido, @RequestParam int dni){
        try{
            ProfesorService.modificar(id, nombre, apellido, dni);
            modelo.put("exito", "Modificado Existosamente");
            return "redirect:/Profesores/";
        }catch (Exception e){
            modelo.put("error", "No se ha podido modificar");
            return "ModificarProfesor.html";
        }
    }

    @PostMapping("/EliminarProfesor/{id}")
    public String eliminarProfesor(ModelMap modelo, @PathVariable String id) throws Exception{

        try{
            ProfesorService.eliminar(id);
            modelo.put("exito", "Eliminado Existosamente");
            return "redirect:/Profesores/";
        }catch (Exception e){
            modelo.put("error", "No se ha podido eliminar");
            return "ModificarProfesor.html";
        }
    }
}