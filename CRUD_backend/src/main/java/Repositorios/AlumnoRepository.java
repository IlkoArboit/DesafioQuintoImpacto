package Repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import Entidades.Alumno;

@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, String> {

    @Query("SELECT a FROM Alumo a ORDER BY apellido")
    public List<Alumno> buscarAlumnos();

    @Query("SELECT a FROM Alumno a WHERE a.id = :id")
    Optional<Alumno> findById(@Param("id") String id);

    @Query("SELECT a FROM Alumno a WHERE a.dni = :dni")
    Alumno findByDni(@Param("dni") int dni);

    @Query("SELECT a FROM Alumno a WHERE a.email = :email")
    Alumno findByEmail(@Param("email") String email);

    @Query("SELECT a FROM Alumno a WHERE a.apellido = :apellido AND a.nombre = :nombre")
    List<Alumno> findByApellidoAndNombre(@Param("apellido") String apellido, @Param("nombre") String nombre);

    @Query("SELECT a FROM Alumno a JOIN a.cursos c WHERE c.id = :cursoId")
    List<Alumno> findByCursoId(@Param("cursoId") String cursoId);
}

