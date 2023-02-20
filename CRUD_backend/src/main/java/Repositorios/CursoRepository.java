package Repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import Entidades.Curso;
import Entidades.Profesor;

@Repository
public interface CursoRepository extends JpaRepository<Curso, String> {

    //Buscar un curso por ID
    @Query("SELECT c FROM Curso c WHERE c.id = :id")
    Optional<Curso> findById(@Param("id") String id);

    // Buscar un cursopor Nombre
    @Query("SELECT c FROM Curso c WHERE c.nombre = :nombre")
    Curso findByNombre(@Param("nombre") String nombre);

    //Buscar un curso por Nombre y Turno
    @Query("SELECT c FROM Curso c WHERE c.nombre = :nombre AND c.turno = :turno")
    Curso findByNombreAndTurno(@Param("nombre") String nombre, @Param("turno") String turno);

    //Buscar un curso por dias
    @Query("SELECT c FROM Curso c WHERE c.dias LIKE %:dias%")
    List<Curso> findByDias(@Param("dias") String dias);

    //Buscar un curso por profesor
    @Query("SELECT c FROM Curso c WHERE c.profesor = :profesor")
    List<Curso> findByProfesor(@Param("profesor") Profesor profesor);

    //Buscar un curso por turno
    @Query("SELECT c FROM Curso c WHERE c.turno = :turno")
    List<Curso> findByTurno(@Param("turno") String turno);

    //Buscar un curso por Dia y turno
    @Query("SELECT c FROM Curso c WHERE c.dias LIKE %:dia% AND c.turno = :turno")
    List<Curso> findByDiaAndTurno(@Param("dia") String dia, @Param("turno") String turno);

    //Buscar un curso por Dia y profesor
    @Query("SELECT c FROM Curso c WHERE c.dias LIKE CONCAT('%',:dia,'%') AND c.profesor.nombre LIKE CONCAT('%',:nombreProfesor,'%')")
    List<Curso> findByDiaAndNombreProfesor(@Param("dia") String dia, @Param("nombreProfesor") String nombreProfesor);

    //Buscar un curso por Nombre del profesor y el turno
    @Query("SELECT c FROM Curso c WHERE c.turno=:turno AND c.profesor.nombre LIKE CONCAT('%',:nombreProfesor,'%')")
    List<Curso> findByTurnoAndNombreProfesor(@Param("turno") String turno, @Param("nombreProfesor") String nombreProfesor);

    // Busca cursos por profesor, turno y días
    @Query("SELECT c FROM Curso c WHERE c.profesor.id = :idProfesor AND c.turno = :turno AND c.dias LIKE %:dias%")
    Curso findByProfesorAndTurnoAndDias(@Param("idProfesor") String idProfesor, @Param("turno") String turno, @Param("dias") String dias);
}

