package Controladores;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import Entidades.Administrador;
import Entidades.Alumno;
import Entidades.Profesor;
import Servicios.AdministradorService;
import Servicios.AlumnoService;
import Servicios.ProfesorService;

@Controller
@RequestMapping("/Login")
public class LoginController {

    @Autowired
    AdministradorService AdministradorService;

    @Autowired
    ProfesorService ProfesorService;

    @Autowired
    AlumnoService AlumnoService;

    @GetMapping("/Login")
    public String mostrarLogin() {
        return "Login.html";
    }

    @PostMapping("/Login")
    public String Login(@RequestParam("username") String username,
            @RequestParam("password") String password,
            HttpSession session, ModelMap modelo) throws Exception {
                boolean autenticado = false;
                if(username.contains("administradores")){
                    Administrador admin = AdministradorService.buscarPorEmail(username);
                    if(admin != null && admin.getContrasenia().equals(password)){
                        autenticado = true;
                    }
                } else if(username.contains("profesores")){
                    Profesor profesor = ProfesorService.buscarPorEmail(username);
                    if(profesor != null && profesor.getContrasenia().equals(password)){
                        autenticado = true;
                    }
                } else if(username.contains("alumnos")){
                    Alumno alumno = AlumnoService.buscarPorEmail(username);
                    if(alumno != null && alumno.getContrasenia().equals(password)){
                        autenticado = true;
                    }
                }

        if (autenticado) {
            session.setAttribute("username", username);
            return "redirect:/perfil";
        } else {
            return "redirect:/Login?error=true";
        }
    }

}
