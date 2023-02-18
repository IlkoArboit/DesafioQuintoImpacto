/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Entidades.Curso;
import Repositorios.CursoRepository;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cursos")
public class CursoController {
    @Autowired
    private CursoRepository CursoRepository;

    @GetMapping
    public List<Curso> getCursos() {
        return CursoRepository.findAll();
    }

    @GetMapping("/{id}")
    public Curso getCurso(@PathVariable String id) {
        return CursoRepository.findById(id).orElseThrow(() -> new NotFoundException("Curso no encontrado"));
    }

    @PostMapping
    public Curso crearCurso(@RequestBody Curso curso) {
        return CursoRepository.save(curso);
    }

    @PutMapping("/{id}")
    public Curso actualizarCurso(@PathVariable String id, @RequestBody Curso curso) {
        Curso cursoActual = CursoRepository.findById(id).orElseThrow(() -> new NotFoundException("Curso no encontrado"));
        cursoActual.setNombre(curso.getNombre());
        cursoActual.setDescripcion(curso.getDescripcion());
        cursoActual.setTurno(curso.getTurno());
        cursoActual.setProfesor(curso.getProfesor());
        cursoActual.setAlumnos(curso.getAlumnos());
        return CursoRepository.save(cursoActual);
    }

    @DeleteMapping("/{id}")
    public void eliminarCurso(@PathVariable String id) {
        Curso curso = CursoRepository.findById(id).orElseThrow(() -> new NotFoundException("Curso no encontrado"));
        CursoRepository.delete(curso);
    }
}
