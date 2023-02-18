package Servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Entidades.Profesor;
import Repositorios.ProfesorRepository;

@Service
public class ProfesorService {
    
    @Autowired
    ProfesorRepository ProfesorRepository;

    @Transactional
    public Profesor guardar(String nombre, String apellido){

        Profesor newProfesor = new Profesor();

        newProfesor.setNombre(nombre);
        newProfesor.setApellido(apellido);
        newProfesor.setEmail(Utilidades.generarEmail(nombre, apellido, "Profesores"));
        newProfesor.setContrasenia(Utilidades.generarContrasena(8));

        return ProfesorRepository.save(newProfesor);
    }
}
