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
public interface ProfesorRepository extends JpaRepository<Profesor, String> {

    @Query("SELECT p FROM Profesor p ORDER BY apellido")
    public List<Profesor> buscarProfesores();

    @Query("SELECT a FROM profesor a WHERE a.id = :id")
    Optional<Profesor> findById(@Param("id") String id);

    @Query("SELECT p FROM Profesor p WHERE p.apellido = :apellido")
    List<Profesor> findByApellido(@Param("apellido") String apellido);

    @Query("SELECT p FROM Profesor p WHERE p.dni = :dni")
    Profesor findByDni(@Param("dni") int dni);

    @Query("SELECT p FROM Profesor p WHERE p.apellido = :apellido AND p.nombre = :nombre")
    List<Profesor> findByApellidoAndNombre(@Param("apellido") String apellido, @Param("nombre") String nombre);

    @Query("SELECT p FROM Profesor p WHERE p.email = :email")
    Profesor findByEmail(@Param("email") String email);

    @Query("SELECT p FROM Profesor p JOIN p.cursos c WHERE c = :curso AND p.turno = :turno")
    List<Profesor> findByCursoAndTurno(@Param("curso") Curso curso, @Param("turno") String turno);

    @Query("SELECT p FROM Profesor p JOIN p.cursos c WHERE p.id = :id AND c.turno = :turno AND c.dias LIKE %:dias%")
    Optional<Profesor> findByIdAndTurnoAndDias(@Param("id") String id, @Param("turno") String turno, @Param("dias") String dias);
}
