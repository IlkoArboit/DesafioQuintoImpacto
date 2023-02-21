/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Entidades.Alumno;
import Entidades.Curso;
import Entidades.Profesor;
import Servicios.AlumnoService;
import Servicios.CursoService;
import Servicios.ProfesorService;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdministradorController {

    // @Autowired
    // private ProfesorService ProfesorService;
    // @Autowired
    // private CursoService CursoService;
    // @Autowired
    // private AlumnoService AlumnoService;

    // @GetMapping("/")
    // Public String


}
