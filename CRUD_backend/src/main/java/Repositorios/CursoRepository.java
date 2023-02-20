package Repositorios;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import Entidades.Alumno;
import Entidades.Curso;
import Entidades.Profesor;

@Repository
public interface CursoRepository extends JpaRepository<Curso, String> {

    @Query("SELECT c FROM Curso c JOIN c.profesor p WHERE p = :profesor AND c.turno = :turno")
    List<Curso> findByProfesorAndTurno(@Param("profesor") Profesor profesor, @Param("turno") String turno);

    @Query("SELECT c FROM Curso c WHERE c.dias LIKE %:dias%")
    List<Curso> findByDias(@Param("dias") String dias);

    @Query("SELECT c FROM Curso c WHERE c.profesor = :profesor")
    List<Curso> findByProfesor(@Param("profesor") Profesor profesor);

    @Query("SELECT c FROM Curso c WHERE c.turno = :turno")
    List<Curso> findByTurno(@Param("turno") String turno);

    @Query("SELECT c FROM Curso c WHERE c.dias LIKE %:dia% AND c.turno = :turno")
    List<Curso> findByDiaAndTurno(@Param("dia") String dia, @Param("turno") String turno);

    @Query("SELECT c FROM Curso c WHERE c.dias LIKE CONCAT('%',:dia,'%') AND c.profesor.nombre LIKE CONCAT('%',:nombreProfesor,'%')")
    List<Curso> findByDiaAndNombreProfesor(@Param("dia") String dia, @Param("nombreProfesor") String nombreProfesor);

    @Query("SELECT c FROM Curso c WHERE c.turno=:turno AND c.profesor.nombre LIKE CONCAT('%',:nombreProfesor,'%')")
    List<Curso> findByTurnoAndNombreProfesor(@Param("turno") String turno, @Param("nombreProfesor") String nombreProfesor);

}

