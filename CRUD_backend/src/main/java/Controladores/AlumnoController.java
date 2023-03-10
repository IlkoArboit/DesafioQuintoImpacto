/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import Entidades.Alumno;
import Entidades.Curso;
import Servicios.AlumnoService;
import Servicios.CursoService;

@Controller
@RequestMapping("/Alumnos")
public class AlumnoController {

    @Autowired
    private AlumnoService AlumnoService;

    @Autowired
    private CursoService cursoService;

    @GetMapping("/Alumnos")
    public String mostrarAlumnos(ModelMap modelo){
        List<Alumno> listaAlumnos = AlumnoService.listarAlumnos();

        modelo.addAttribute("Alumnos", listaAlumnos);
        return "Alumnos.html";
    }

    @GetMapping("/perfil/{id}")
    public String perfil(ModelMap modelo, @RequestParam String id){
        Alumno alumno = AlumnoService.buscarPorID(id);
        modelo.put("alumno", alumno);
        modelo.put("cursos", alumno.getCursos());
        return "PerfilAlumnos.html";
    }

    @GetMapping("/CrearAlumno")
    public String crearAlumno(ModelMap modelo){
        return "CargaAlumno.html";
    }

    @PostMapping("/CrearAlumno")
    public String crearAlumno(@RequestParam String nombre, @RequestParam String apellido,@RequestParam int dni, ModelMap modelo) {
        try{
            AlumnoService.crear(nombre, apellido, dni);
            modelo.put("exito", "Registro exitoso");
            return "redirect:/Alumnos";
        }catch (Exception e){
            
            modelo.put("error", "No se pudo cargar");
            return "CrearAlumno";
        }
    }

    @GetMapping("/ModificarAlumno/{id}")
    public String modificarAlumno(@PathVariable String id, ModelMap modelo){
        
        modelo.put("Alumno", AlumnoService.buscarPorID(id));

        return "ModificarAlumno.html";
    }

    @PostMapping("/ModificarAlumno/{id}")
    public String modificarAlumno(ModelMap modelo, @PathVariable String id, @RequestParam String nombre, @RequestParam String apellido, @RequestParam int dni){
        try{
            AlumnoService.modificar(id, nombre, apellido, dni);
            modelo.put("exito", "Modificado Existosamente");
            return "redirect:/Alumnos/";
        }catch (Exception e){
            modelo.put("error", "No se ha podido modificar");
            return "ModificarAlumno.html";
        }
    }

    @PostMapping("/EliminarAlumno/{id}")
    public String eliminarAlumno(ModelMap modelo, @PathVariable String id) throws Exception{

        try{
            AlumnoService.eliminar(id);
            modelo.put("exito", "Eliminado Existosamente");
            return "redirect:/Alumnos/";
        }catch (Exception e){
            modelo.put("error", "No se ha podido eliminar");
            return "ModificarAlumno.html";
        }
    }

    @GetMapping("/InscribirseACurso/{id}")
    public String insrcibirse(ModelMap modelo, @RequestParam String id){
        List<Curso> listaCursos = cursoService.listarCursos();
        Alumno alumno = AlumnoService.buscarPorID(id);

        modelo.put("alumno", alumno);
        modelo.put("cursos", listaCursos);

        return "CursosInscripcion.html";
    }

    @PostMapping("/InscribirseACurso/{id}/{cursoId}")
    public String inscribirse(ModelMap modelo, @PathVariable String id, @PathVariable String cursoId){
        try{
            AlumnoService.inscribirAlumnoACurso(id, cursoId);
            modelo.put("exito", "Inscripto Existosamente");
            return "redirect:/InscribirseACurso/{id}";
        }catch (Exception e){
            modelo.put("error", "No se ha podido inscribir");
            return "CursoInscripci??n.html";
        }
    }

    @PostMapping("/EliminarCurso/{id}/{cursoId}")
    public String darseDeBaja(ModelMap modelo, @PathVariable String id, @PathVariable String cursoId){
        try{
            AlumnoService.eliminarAlumnoDeCurso(id, cursoId);
            modelo.put("exito", "Eliminado exitosamente");
            return "redirect:/InscribirseACurso/{id}";
        }catch (Exception e){
            modelo.put("error", e.getMessage());
            return "CursosInscripcion.html";
        }
    }
    
}

