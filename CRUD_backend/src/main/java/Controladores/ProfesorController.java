/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Entidades.Profesor;
import Repositorios.ProfesorRepository;

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
@RequestMapping("/profesores")
public class ProfesorController {
    @Autowired
    private ProfesorRepository ProfesorRepository;

    // @GetMapping("")
    // public List<Profesor> getProfesores() {
    //     return ProfesorRepository.findAll();
    // }

    // @GetMapping("/{id}")
    // public Profesor getProfesorById(@PathVariable String id) {
    //     return ProfesorRepository.findById(id)
    //             .orElseThrow(() -> new Exception("Profesor no encontrado con id: " + id));
    // }

    // @PostMapping("")
    // public Profesor crearProfesor(@RequestBody Profesor profesor) {
    //     return ProfesorRepository.save(profesor);
    // }

    // @PutMapping("/{id}")
    // public Profesor actualizarProfesor(@PathVariable String id, @RequestBody Profesor profesorActualizado) {
    //     return ProfesorRepository.findById(id)
    //             .map(profesor -> {
    //                 profesor.setNombre(profesorActualizado.getNombre());
    //                 profesor.setEmail(profesorActualizado.getEmail());
    //                 return ProfesorRepository.save(profesor);
    //             })
    //             .orElseThrow(() -> new Exception("Profesor no encontrado con id: " + id));
    // }

    // @DeleteMapping("/{id}")
    // public ResponseEntity<?> eliminarProfesor(@PathVariable String id) {
    //     return ProfesorRepository.findById(id)
    //             .map(profesor -> {
    //                 ProfesorRepository.delete(profesor);
    //                 return ResponseEntity.ok().build();
    //             })
    //             .orElseThrow(() -> new Exception("Profesor no encontrado con id: " + id));
    // }
}