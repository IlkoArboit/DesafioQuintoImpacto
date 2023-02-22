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
    public Alumno crear(String nombre, String apellido, int dni) throws Exception{

        Alumno newAlumno = AlumnoRepository.findByDni(dni);

        if(newAlumno == null){
            newAlumno = new Alumno();
            newAlumno.setNombre(nombre);
            newAlumno.setApellido(apellido);
            newAlumno.setDNI(dni);
            newAlumno.setEmail(Utilidades.generarEmail(nombre, apellido, "Alumnos"));
            newAlumno.setContrasenia(Utilidades.generarContrasena(8));
            return AlumnoRepository.save(newAlumno);
        } else {
            throw new Exception("Ya existe un alumno con este DNI");
        }

    }

    @Transactional
    public Alumno modificar(String id,String nombre, String apellido, int dni) throws Exception{

        Optional<Alumno> optionalAlumno = AlumnoRepository.findById(id);

        if(optionalAlumno.isPresent()){      
            Alumno modAlumno = optionalAlumno.get();
            if(AlumnoRepository.findByDni(dni).equals(modAlumno)){
                modAlumno.setNombre(nombre);
                modAlumno.setApellido(apellido);
            }

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
        AlumnoRepository.save(alumno);
        CursoService.inscribirAlumno(alumno, curso);
    }

    @Transactional
    public void eliminarAlumnoDeCurso(String alumnoID, String cursoID) throws Exception{
        Alumno alumno = buscarPorID(alumnoID);
        Curso curso = CursoService.buscarPorId(cursoID);

        if(!curso.getAlumnos().contains(alumno) || !alumno.getCursos().contains(curso)){
            throw new Exception("El alumno no está inscrito en este curso.");
        }

        alumno.removeCurso(curso);
        CursoService.eliminarAlumno(alumno, curso);
        AlumnoRepository.save(alumno);
    }

    public Alumno buscarPorID(String id){
        Optional<Alumno> optionalAlumno = AlumnoRepository.findById(id);

        if(!optionalAlumno.isPresent()){
            throw new IllegalArgumentException("No se encontró un alumno con ese id.");
        }

        return optionalAlumno.get();
    }

    public Alumno buscarPorDNI(int dni){
        Alumno alumno = AlumnoRepository.findByDni(dni);

        if(alumno == null){
            throw new IllegalArgumentException("No se encontró un alumno con ese id.");
        }

        return alumno;
    }

    public List<Alumno> buscarPorApellidoYNombre(String nombre, String apellido){
        return AlumnoRepository.findByApellidoAndNombre(apellido, nombre);
    }

    public Alumno buscarPorEmail(String email){
        return AlumnoRepository.findByEmail(email);
    }
}
