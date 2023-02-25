/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Entidades.Alumno;
import Entidades.Curso;
import Entidades.Profesor;
import Repositorios.CursoRepository;
import Servicios.CursoService;

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
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    CursoService CursoService;

    @GetMapping("/")
    public String listarCursos(ModelMap modelo){
        
        List<Curso> listaCursos = CursoService.listarCursos();

        modelo.addAttribute("cursos", listaCursos);
        return "Cursos.html";
    }

    @GetMapping("/crear")
    public String guardar(){
        return "CargaCurso.html";
    }

    @PostMapping("/crear")
    public String guardar(@RequestParam String nombre, @RequestParam String turno, @RequestParam String dias, ModelMap modelo){
        try{
            CursoService.crear(nombre, turno, dias);
            modelo.put("exito", "Creado exitosamente");
            return "redirect:/cursos";
        }catch (Exception e){
            modelo.put("error", "No se pudo crear");
            return "CargaCurso.html";
        }
    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo){

        modelo.put("curso", CursoService.buscarPorId(id));
        return "ModificarCurso.html";
    }

    @PostMapping("/modificar/{id}")
    public String modificar(ModelMap modelo, @PathVariable String id, @RequestParam String nombre, @RequestParam String turno, @RequestParam String dias){
        try{
            CursoService.modificar(id, nombre, dias, turno);
            modelo.put("exito", "Modificado Exitosamente");
            return "redirect:/cursos";
        }catch(Exception e){
            modelo.put("error", "Error al modificar");
            return "ModificarCurso.html";
        }
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(ModelMap modelo, @PathVariable String id){
        try{
            CursoService.eliminar(id);
            modelo.put("exito", "Eliminado Existosamente");
            return "redirect:/cursos";
        }catch(Exception e){
            modelo.put("error", "Error al eliminar");
            return "Cursos.html";
        }
    }

}
