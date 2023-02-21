package Servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Entidades.Curso;
import Entidades.Profesor;
import Repositorios.ProfesorRepository;

@Service
public class ProfesorService {
    
    @Autowired
    ProfesorRepository ProfesorRepository;

    @Autowired
    CursoService CursoService;

    @Transactional
    public Profesor crear(String nombre, String apellido, int dni) throws Exception{

        Profesor newProfesor = ProfesorRepository.findByDni(dni);

        if(newProfesor == null){
            newProfesor = new Profesor();
            newProfesor.setNombre(nombre);
            newProfesor.setApellido(apellido);
            newProfesor.setDNI(dni);
            newProfesor.setEmail(Utilidades.generarEmail(nombre, apellido, "Profesores"));
            newProfesor.setContrasenia(Utilidades.generarContrasena(8));
            return ProfesorRepository.save(newProfesor);
        } else {
            throw new Exception("Ya existe un profesor con este DNI");
        }
    }

    @Transactional
    public Profesor modificar(String id, String nombre, String apellido, int dni) throws Exception{

        Optional<Profesor> optionalProfesor = ProfesorRepository.findById(id);

        if(optionalProfesor.isPresent()){
            Profesor modProfesor = optionalProfesor.get();
            if(ProfesorRepository.findByDni(dni).equals(modProfesor)){
                modProfesor.setNombre(nombre);
                modProfesor.setApellido(apellido);
            }
            return ProfesorRepository.save(modProfesor);
        }else {
            throw new Exception("El profesor no se encuentra en la base de datos");
        }

    }
    
    @Transactional
    public void eliminar(String id) throws Exception{
        Profesor delProfesor = ProfesorRepository.findById(id).get();

        if(delProfesor != null){
            ProfesorRepository.delete(delProfesor);
        } else {
            throw new Exception("El profesor no se encuentra en la base de datos");
        }
    }

    @Transactional
    public void asignarCurso(String profesorID, String cursoID) throws Exception {
        Profesor profesor = buscarPorID(profesorID);
        Curso curso = CursoService.buscarPorId(cursoID);
        
        
        // Verificar que el curso no tenga ya al profesor asignado
        if (curso.getProfesor() != null && curso.getProfesor().equals(profesor)) {
            throw new Exception("El profesor ya está asignado a este curso");
        }
        
        // Verificar que el profesor esté disponible para el horario del curso
        if (!estaDisponible(profesorID, curso.getDias(), curso.getTurno())) {
            throw new Exception("El profesor no está disponible para el horario del curso.");
        }
        
        // Verificar que el curso no tenga un profesor asignado para ese horario
        if (CursoService.buscarPorProfesorTurnoYDia(profesor.getId(), curso.getDias(), curso.getTurno()) != null) {
            throw new Exception("Ya existe un profesor asignado para este horario del curso.");
        }
        
        profesor.agregarCurso(curso);
        CursoService.agregarProfesor(profesor, curso);
        ProfesorRepository.save(profesor);
    }

    @Transactional
    public void eliminarCurso(String profesorID, String cursoID) throws Exception{
        Profesor profesor = buscarPorID(profesorID);
        Curso curso = CursoService.buscarPorId(cursoID);

        // Verificar que el curso no tenga ya al profesor asignado
        if (curso.getProfesor() == null || !curso.getProfesor().equals(profesor)) {
            throw new Exception("Este profesor no está asignado a este curso");
        }

        profesor.eliminarCurso(curso);
        CursoService.eliminarProfesor(profesor, curso);
        ProfesorRepository.save(profesor);
    }

    public boolean estaDisponible(String idProfesor, String dia, String turno) {
        // Buscamos al profesor por su id
        Optional<Profesor> optionalProfesor = ProfesorRepository.findById(idProfesor);
    
        // Si no se encuentra el profesor, lanzamos una excepción o retornamos false
        if (!optionalProfesor.isPresent()) {
            throw new IllegalArgumentException("No se encontró un profesor con ese id.");
            // o bien: return false;
        }
    
        Profesor profesor = optionalProfesor.get();
    
        // Comprobamos si el profesor ya tiene asignado un curso en ese día y turno
        Curso cursos = CursoService.buscarPorProfesorTurnoYDia(profesor.getId(), turno, dia);
        return cursos.equals(null);
    }

    public Profesor buscarPorID(String id){
        Optional<Profesor> optionalProfesor = ProfesorRepository.findById(id);

        if (!optionalProfesor.isPresent()) {
            throw new IllegalArgumentException("No se encontró un profesor con ese id.");
        }

        return optionalProfesor.get();
    }

    public Profesor buscarPorDNI(int dni){
        Profesor profesor = ProfesorRepository.findByDni(dni);

        if(profesor == null){
            throw new IllegalArgumentException("No se encontró un profesor con ese id.");
        }

        return profesor;
    }

    public List<Profesor> buscarPorNombreYApellido(String nombre, String apellido){
        return ProfesorRepository.findByApellidoAndNombre(apellido, nombre);
    }

    public Profesor buscarPorEmail(String email){
        return ProfesorRepository.findByEmail(email);
    }

    
}
