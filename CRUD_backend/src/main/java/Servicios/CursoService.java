package Servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import Entidades.Alumno;
import Entidades.Curso;
import Entidades.Profesor;
import Repositorios.CursoRepository;

public class CursoService {

    @Autowired
    private CursoRepository CursoRepository;

    @Autowired
    private ProfesorService ProfesorService;

    public Curso findById(String id) {

        return CursoRepository.findById(id).get();

    }

    @Transactional
    public Curso crear(String nombre, String dias, String turno, Profesor profesor, List<Alumno> alumnos) {

        Curso newCurso = new Curso();

        newCurso.setNombre(nombre);
        newCurso.setDias(dias);
        newCurso.setTurno(turno);
        newCurso.setAlumnos(alumnos);
        newCurso.setProfesor(profesor);

        return CursoRepository.save(newCurso);
    }

    @Transactional
    public Curso modificar(String id, String nombre, String dias, String turno, Profesor profesor, List<Alumno> alumnos) throws Exception {

        Curso modCurso = CursoRepository.findById(id).get();

        if (modCurso != null) {
            modCurso.setNombre(nombre);
            modCurso.setDias(dias);
            modCurso.setTurno(turno);
            modCurso.setAlumnos(alumnos);
            modCurso.setProfesor(profesor);
            return CursoRepository.save(modCurso);
        } else {
            throw new Exception("El curso no se encuentra en la base de datos");
        }

    }

    @Transactional
    public void eliminar(String id) throws Exception {
        Curso delCurso = CursoRepository.findById(id).get();

        if (delCurso != null) {
            CursoRepository.delete(delCurso);
        } else {
            throw new Exception("El curso no se encuentra en la base de datos");
        }
    }

    @Transactional
    public Curso agregarProfesor(String cursoID, String profesorID) throws Exception{
        Curso curso = buscarPorId(cursoID);
        Profesor profesor = ProfesorService.buscarPorID(profesorID);
        
        
        // Verificar que el curso no tenga ya un profesor asignado
        if (curso.getProfesor() != null && curso.getProfesor().equals(profesor)) {
            return curso;
        }
        
        // Verificar que el profesor esté disponible para el horario del curso
        if (!ProfesorService.estaDisponible(profesorID, curso.getDias(), curso.getTurno())) {
            throw new RuntimeException("El profesor no está disponible para el horario del curso.");
        }
        
        // Verificar que el curso no tenga un profesor asignado para ese horario
        if (buscarPorProfesorTurnoYDia(profesor.getId(), curso.getDias(), curso.getTurno()) != null) {
            throw new RuntimeException("Ya existe un profesor asignado para este horario del curso.");
        }

        curso.setProfesor(profesor);
        return CursoRepository.save(curso);
    }

    @Transactional
    public void inscribirAlumno (Alumno alumno, Curso curso){
        curso.addAlumno(alumno);
        CursoRepository.save(curso);
    }

    @Transactional
    public void eliminarAlumno (Alumno alumno, Curso curso){
        curso.removeAlumno(alumno);
        CursoRepository.save(curso);
    }

    @Transactional
    public void agregarProfesor(Profesor profesor, Curso curso){
        curso.setProfesor(profesor);
        CursoRepository.save(curso);
    }

    @Transactional
    public void eliminarProfesor (Profesor profesor, Curso curso){
        curso.setProfesor(null);
        CursoRepository.save(curso);
    }

    public Curso buscarPorId(String id){
        Optional<Curso> optionalCurso = CursoRepository.findById(id);

        if(!optionalCurso.isPresent()){
            throw new IllegalArgumentException("No se encontró un profesor con ese id.");
        }

        return optionalCurso.get();
    }

    public Curso buscarPorNombre(String nombre){
        return CursoRepository.findByNombre(nombre);
    }

    public Curso buscarPorNombreYTurno(String nombre, String turno){
        return CursoRepository.findByNombreAndTurno(nombre, turno);
    }

    public Curso buscarPorProfesorTurnoYDia(String idProfesor, String turno, String dias){
        return CursoRepository.findByProfesorAndTurnoAndDias(idProfesor, turno, dias);
    }
}
