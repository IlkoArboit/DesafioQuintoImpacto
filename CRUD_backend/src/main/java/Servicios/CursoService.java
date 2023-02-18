package Servicios;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import Entidades.Alumno;
import Entidades.Curso;
import Entidades.Profesor;

@Repository
public interface CursoService extends JpaRepository<Curso, String> {

    @Query("SELECT c FROM Curso c JOIN c.profesor p WHERE p = :profesor AND c.turno = :turno")
    List<Curso> findByProfesorAndTurno(@Param("profesor") Profesor profesor, @Param("turno") String turno);

    @Query("SELECT a FROM Alumno a JOIN a.cursos c WHERE c = :curso")
    List<Alumno> findByCursosContains(@Param("curso") Curso curso);
}

