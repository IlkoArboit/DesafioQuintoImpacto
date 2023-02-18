    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Entidades.Curso;
import Entidades.Profesor;
import Repositorios.AlumnoRepository;
import Repositorios.CursoRepository;
import Repositorios.ProfesorRepository;
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
@RequestMapping("/admin")
public class AdministradorController {
    @Autowired
    private ProfesorRepository profesorRepository;
    @Autowired
    private CursoRepository cursoRepository;
    @Autowired
    private AlumnoRepository alumnoRepository;

    // Endpoints para gestión de profesores
    @GetMapping("/profesores")
    public List<Profesor> getAllProfesores() {
        return profesorRepository.findAll();
    }

    @GetMapping("/profesores/{id}")
    public Profesor getProfesorById(@PathVariable String id) {
        return profesorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Profesor no encontrado con id: " + id));
    }

    @PostMapping("/profesores")
    public Profesor createProfesor(@RequestBody Profesor profesor) {
        return profesorRepository.save(profesor);
    }

    @PutMapping("/profesores/{id}")
    public Profesor updateProfesor(@PathVariable String id, @RequestBody Profesor profesorActualizado) {
        return profesorRepository.findById(id)
                .map(profesor -> {
                    profesor.setNombre(profesorActualizado.getNombre());
                    profesor.setEmail(profesorActualizado.getEmail());
                    return profesorRepository.save(profesor);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Profesor no encontrado con id: " + id));
    }

    @DeleteMapping("/profesores/{id}")
    public ResponseEntity<?> deleteProfesor(@PathVariable String id) {
        return profesorRepository.findById(id)
                .map(profesor -> {
                    profesorRepository.delete(profesor);
                    return ResponseEntity.ok().build();
                })
                .orElseThrow(() -> new ResourceNotFoundException("Profesor no encontrado con id: " + id));
    }

    // Endpoints para gestión de cursos
    @GetMapping("/cursos")
    public List<Curso> getAllCursos() {
        return cursoRepository.findAll();
    }

    @GetMapping("/cursos/{id}")
    public Curso getCursoById(@PathVariable String id) {
        return cursoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado con id: " + id));
    }

    @PostMapping("/cursos")
    public Curso createCurso(@RequestBody Curso curso) {
        return cursoRepository.save(curso);
    }

    @PutMapping("/cursos/{id}")
    public Curso updateCurso(@PathVariable String id, @RequestBody Curso cursoActualizado) {
        return cursoRepository.findById(id)
                .map(curso -> {
                    curso.setNombre(cursoActualizado.getNombre());
                    curso.setDescripcion(cursoActualizado.getDescripcion());
                    curso.setTurno(cursoActualizado.getTurno());
                    curso.setProfesor(profesorRepository.findById(cursoActualizado.getProfesor().getId())
                            .orElseThrow(() -> new ResourceNotFoundException("Profesor no encontrado con id: " + cursoActualizado.getProfesor().getId())));
                    return cursoRepository.save(curso);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado con id: " + id));
    }

    @DeleteMapping("/cursos/{id}")
    public ResponseEntity<?> deleteCurso(@PathVariable String id) {
        return cursoRepository.findById(id)
                .map(curso -> {
                    cursoRepository.delete(curso);
                    return ResponseEntity.ok().build();
                })
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado con id: " + id));
    }
}
