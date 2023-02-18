package Servicios;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import Entidades.Alumno;
import Entidades.Curso;
import Repositorios.AlumnoRepository;

@Service
public class AlumnoService {

    @Autowired
    AlumnoRepository AlumnoRepository;
    @Autowired
    CursoService CursoService;

    @Transactional
    public Alumno guardar(String nombre, String apellido){

        Alumno newAlumno = new Alumno();

        newAlumno.setNombre(nombre);
        newAlumno.setApellido(apellido);
        newAlumno.setEmail(Utilidades.generarEmail(nombre, apellido, "Alumnos"));
        newAlumno.setContrasenia(Utilidades.generarContrasena(8));

        return AlumnoRepository.save(newAlumno);
    }

    @Transactional
    public Alumno modificar(String id,String nombre, String apellido) throws Exception{

        Alumno modAlumno = AlumnoRepository.findById(id).get();

        if(modAlumno != null){            
            modAlumno.setNombre(nombre);
            modAlumno.setApellido(apellido);

            return AlumnoRepository.save(modAlumno);
        }else {
            throw new Exception("El alumno no se encuentra en la base de datos");
        }

    }
    
    @Transactional
    public void eliminar(String id) throws Exception{
        Alumno delAlumno = AlumnoRepository.findById(id).get();

        if(delAlumno != null){
            AlumnoRepository.delete(delAlumno);
        } else {
            throw new Exception("El alumno no se encuentra en la base de datos");
        }
    }

    public Alumno inscribirseEnCurso(@PathVariable String alumnoId, @PathVariable String cursoId) throws Exception{
        
        Alumno alumno = AlumnoRepository.findById(cursoId).get();

        if(alumno != null){
            Curso curso = CursoService.findById(cursoId).get();
        }
    }
}
