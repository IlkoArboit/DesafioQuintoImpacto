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
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    //Endpoints para la gestion de cursos como administrador

    @GetMapping("/cursos")
    public String listarCursos(ModelMap modelo){
        
        List<Curso> listaCursos = CursoService.listarCursos();

        modelo.addAttribute("cursos", listaCursos);
        return "Cursos.html";
    }

    @GetMapping("/cursos/crear")
    public String guardar(){
        return "CargaCurso.html";
    }

    @PostMapping("/cursos/crear")
    public String guardar(@RequestParam String nombre, @RequestParam String turno, @RequestParam String dias, ModelMap modelo){
        try{
            CursoService.crear(nombre, turno, dias);
            modelo.put("exito", "Creado exitosamente");
            return "redirect:/Administradores/cursos";
        }catch (Exception e){
            modelo.put("error", "No se pudo crear");
            return "CargaCurso.html";
        }
    }

    @GetMapping("/cursos/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo){

        modelo.put("curso", CursoService.buscarPorId(id));
        return "ModificarCurso.html";
    }

    @PostMapping("/cursos/modificar/{id}")
    public String modificar(ModelMap modelo, @PathVariable String id, @RequestParam String nombre, @RequestParam String turno, @RequestParam String dias){
        try{
            CursoService.modificar(id, nombre, dias, turno);
            modelo.put("exito", "Modificado Exitosamente");
            return "redirect:/Administradores/cursos";
        }catch(Exception e){
            modelo.put("error", "Error al modificar");
            return "ModificarCurso.html";
        }
    }

    @PostMapping("/cursos/eliminar/{id}")
    public String eliminar(ModelMap modelo, @PathVariable String id){
        try{
            CursoService.eliminar(id);
            modelo.put("exito", "Eliminado Existosamente");
            return "redirect:/Administradores/cursos";
        }catch(Exception e){
            modelo.put("error", "Error al eliminar");
            return "Cursos.html";
        }
    }

    //Endpoints para gestion de alumnos como Administrador

    @GetMapping("/Alumnos")
    public String mostrarAlumnos(ModelMap modelo){
        List<Alumno> listaAlumnos = AlumnoService.listarAlumnos();

        modelo.addAttribute("Alumnos", listaAlumnos);
        return "Alumnos.html";
    }

    @GetMapping("/Alumnos/CrearAlumno")
    public String crearAlumno(ModelMap modelo){
        return "CargaAlumno.html";
    }

    @PostMapping("/Alumnos/CrearAlumno")
    public String crearAlumno(@RequestParam String nombre, @RequestParam String apellido,@RequestParam int dni, ModelMap modelo) {
        try{
            AlumnoService.crear(nombre, apellido, dni);
            modelo.put("exito", "Registro exitoso");
            return "redirect:/Administradores/Alumnos";
        }catch (Exception e){
            
            modelo.put("error", "No se pudo cargar");
            return "CrearAlumno";
        }
    }

    @GetMapping("/Alumnos/ModificarAlumno/{id}")
    public String modificarAlumno(@PathVariable String id, ModelMap modelo){
        
        modelo.put("Alumno", AlumnoService.buscarPorID(id));

        return "ModificarAlumno.html";
    }

    @PostMapping("/Alumnos/ModificarAlumno/{id}")
    public String modificarAlumno(ModelMap modelo, @PathVariable String id, @RequestParam String nombre, @RequestParam String apellido, @RequestParam int dni){
        try{
            AlumnoService.modificar(id, nombre, apellido, dni);
            modelo.put("exito", "Modificado Existosamente");
            return "redirect:/Administrador/Alumnos/";
        }catch (Exception e){
            modelo.put("error", "No se ha podido modificar");
            return "ModificarAlumno.html";
        }
    }

    @PostMapping("/Alumnos/EliminarAlumno/{id}")
    public String eliminarAlumno(ModelMap modelo, @PathVariable String id) throws Exception{

        try{
            AlumnoService.eliminar(id);
            modelo.put("exito", "Eliminado Existosamente");
            return "redirect:/Administrador/Alumnos/";
        }catch (Exception e){
            modelo.put("error", "No se ha podido eliminar");
            return "ModificarAlumno.html";
        }
    }

    //Endpoints para gestión de profesores como administrador

    @GetMapping("/Profesores")
    public String profesor(ModelMap modelo){
        List<Profesor> listaProfesores = ProfesorService.listarProfesores();
        
        modelo.addAttribute("Profesores", listaProfesores);
        return "Profesores.html";
    }

    @GetMapping("/Profesores/CrearProfesor")
    public String crearProfesor() throws Exception{
        return "CargaProfesor.html";
    }

    @PostMapping("/Profesores/CrearProfesor")
    public String crearProfesor(@RequestParam String nombre, @RequestParam String apellido, @RequestParam int dni, ModelMap modelo){
        try{
            ProfesorService.crear(nombre, apellido, dni);
            modelo.put("exito", "Creado exitosamente");
            return "redirect:/Administradores/Profesores/";
        }catch (Exception e){
            
            modelo.put("error", "No se pudo crear");
            return "CrearProfesor";
        }
    }

    @GetMapping("/Profesores/ModificarProfesor/{id}")
    public String modificarProfesor(@PathVariable String id, ModelMap modelo){
        
        modelo.put("profesor", ProfesorService.buscarPorID(id));

        return "ModificarProfesor.html";
    }

    @PostMapping("/Profesores/ModificarProfesor/{id}")
    public String modificarProfesor(ModelMap modelo, @PathVariable String id, @RequestParam String nombre, @RequestParam String apellido, @RequestParam int dni){
        try{
            ProfesorService.modificar(id, nombre, apellido, dni);
            modelo.put("exito", "Modificado Existosamente");
            return "redirect:/Administradores/Profesores/";
        }catch (Exception e){
            modelo.put("error", "No se ha podido modificar");
            return "ModificarProfesor.html";
        }
    }

    @PostMapping("/Profesores/EliminarProfesor/{id}")
    public String eliminarProfesor(ModelMap modelo, @PathVariable String id) throws Exception{

        try{
            ProfesorService.eliminar(id);
            modelo.put("exito", "Eliminado Existosamente");
            return "redirect:/Administradores/Profesores/";
        }catch (Exception e){
            modelo.put("error", "No se ha podido eliminar");
            return "ModificarProfesor.html";
        }
    }

}
