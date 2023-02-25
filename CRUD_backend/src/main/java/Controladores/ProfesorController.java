/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Entidades.Curso;
import Entidades.Profesor;
import Servicios.CursoService;
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

    @Autowired
    private CursoService CursoService;

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
        
        modelo.put("profesor", ProfesorService.buscarPorID(id));

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

    @GetMapping("/perfil/{id}")
    public String mostrarPrefil(@PathVariable String id, ModelMap modelo){
        Profesor profesor = ProfesorService.buscarPorID(id);

        List<Curso> cursosProfesor = profesor.getCursos();
        modelo.put("profesor", profesor);
        modelo.put("cursos", cursosProfesor);

        return "PerfilProfesor.html";
    }

    @GetMapping("/inscribirCurso/{id}")
    public String inscripcionCursos(@PathVariable String id, ModelMap modelo){
        List<Curso> cursos = CursoService.listarCursos();
        modelo.put("cursos", cursos);
        modelo.put("idProfesor", id);

        return "InscripcionProfesores.html";
    }

    @PostMapping("/inscribir/{id}/{cursoId}")
    public String inscripcionCurso(@PathVariable String id, @PathVariable String cursoId, ModelMap modelo){
        try{
            ProfesorService.asignarCurso(id, cursoId);
            modelo.put("exito", "Inscripto Exitosamente");
            return "redirect:/inscribirCursos/{id}";
        }catch (Exception e){
            modelo.put("error", e.getMessage());
            return "InscripcionProfesor.html";
        }
    }

    @PostMapping("/DarDeBaja/{id}/{cursoId}")
    public String darDeBaja(@PathVariable String id, @PathVariable String cursoId, ModelMap modelo){
        try{
            ProfesorService.eliminarCurso(id, cursoId);
            modelo.put("exito", "Eliminado Correctamente");
            return "redirect:/incribirCurso/{id}";
        }catch(Exception e){
            modelo.put("error", e.getMessage());
            return "InscripcionProfesor.html";
        }
    }

}