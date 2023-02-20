package Servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional
    public void inscribirAlumnoACurso(String alumnoID, String cursoID) throws Exception {
        Alumno alumno = buscarPorID(alumnoID);
        Curso curso = CursoService.buscarPorId(cursoID);


        if (curso.getAlumnos().contains(alumno) || alumno.getCursos().contains(curso)) {
            throw new Exception("El alumno ya está inscrito en este curso.");
        }

        alumno.addCurso(curso);
        CursoService.inscribirAlumno(alumno, curso);
    }

    public Alumno buscarPorID(String id){
        Optional<Alumno> optionalAlumno = AlumnoRepository.findById(id);

        if(!optionalAlumno.isPresent()){
            throw new IllegalArgumentException("No se encontró un alumno con ese id.");
        }

        return optionalAlumno.get();
    }

    public List<Alumno> buscarPorApellidoYNombre(String nombre, String apellido){
        return AlumnoRepository.findByApellidoAndNombre(apellido, nombre);
    }
}
