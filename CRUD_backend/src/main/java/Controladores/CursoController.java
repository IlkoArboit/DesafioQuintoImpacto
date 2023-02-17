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

@RestController
@RequestMapping("/cursos")
public class CursoController {
    @Autowired
    private CursoRepository cursoRepository;

    @GetMapping
    public List<Curso> getCursos() {
        return cursoRepository.findAll();
    }

    @GetMapping("/{id}")
    public Curso getCurso(@PathVariable Long id) {
        return cursoRepository.findById(id).orElseThrow(() -> new NotFoundException("Curso no encontrado"));
    }

    @PostMapping
    public Curso createCurso(@RequestBody Curso curso) {
        return cursoRepository.save(curso);
    }

    @PutMapping("/{id}")
    public Curso updateCurso(@PathVariable Long id, @RequestBody Curso curso) {
        Curso cursoActual = cursoRepository.findById(id).orElseThrow(() -> new NotFoundException("Curso no encontrado"));
        cursoActual.setNombre(curso.getNombre());
        cursoActual.setDescripcion(curso.getDescripcion());
        cursoActual.setTurno(curso.getTurno());
        cursoActual.setProfesor(curso.getProfesor());
        cursoActual.setAlumnos(curso.getAlumnos());
        return cursoRepository.save(cursoActual);
    }

    @DeleteMapping("/{id}")
    public void deleteCurso(@PathVariable Long id) {
        Curso curso = cursoRepository.findById(id).orElseThrow(() -> new NotFoundException("Curso no encontrado"));
        cursoRepository.delete(curso);
    }
}
