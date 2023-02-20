/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import Entidades.Alumno;
import Entidades.Curso;
import Repositorios.AlumnoRepository;
import Servicios.CursoService;

@Controller
@RequestMapping("/alumnos")
public class AlumnoController {

    @Autowired
    private AlumnoRepository alumnoRepository;

    @Autowired
    private CursoService cursoService;

    // Endpoint para obtener información de un alumno por su ID
    @GetMapping("/{alumnoId}")
    public Alumno getAlumnoById(@PathVariable String alumnoId) throws Exception{
        return alumnoRepository.findById(alumnoId)
                .orElseThrow(() -> new Exception("Alumno no encontrado con id: " + alumnoId));
    }

    // Endpoint para crear un nuevo alumno
    @PostMapping
    public Alumno crearAlumno(@RequestBody Alumno alumno) {
        return alumnoRepository.save(alumno);
    }

    // Endpoint para actualizar la información de un alumno
    @PutMapping("/{alumnoId}")
    public Alumno actualizarAlumno(@PathVariable String alumnoId, @RequestBody Alumno alumnoActualizado) throws Exception{
        return alumnoRepository.findById(alumnoId)
                .map(alumno -> {
                    alumno.setNombre(alumnoActualizado.getNombre());
                    alumno.setEmail(alumnoActualizado.getEmail());
                    alumno.setCursos(alumnoActualizado.getCursos());
                    return alumnoRepository.save(alumno);
                })
                .orElseThrow(() -> new Exception("Alumno no encontrado con id: " + alumnoId));
    }

    // Endpoint para inscribirse en un curso
    @PostMapping("/{alumnoId}/cursos/{cursoId}/inscribirse")
    public Alumno inscribirseEnCurso(@PathVariable String alumnoId, @PathVariable String cursoId) throws Exception{
        return alumnoRepository.findById(alumnoId)
                .map(alumno -> {
                    Curso curso = cursoService.findById(cursoId)
                            .orElseThrow(() -> new Exception("Curso no encontrado con id: " + cursoId));
                    alumno.getCursos().add(curso);
                    return alumnoRepository.save(alumno);
                })
                .orElseThrow(() -> new Exception("Alumno no encontrado con id: " + alumnoId));
    }

    // Endpoint para darse de baja de un curso
    @PostMapping("/{alumnoId}/cursos/{cursoId}/darseDeBaja")
    public Alumno darseDeBajaDeCurso(@PathVariable String alumnoId, @PathVariable String cursoId) throws Exception{
        return alumnoRepository.findById(alumnoId)
                .map(alumno -> {
                    Curso curso = cursoService.findById(cursoId)
                            .orElseThrow(() -> new Exception("Curso no encontrado con id: " + cursoId));
                    alumno.getCursos().remove(curso);
                    return alumnoRepository.save(alumno);
                })
                .orElseThrow(() -> new Exception("Alumno no encontrado con id: " + alumnoId));
    }
}

