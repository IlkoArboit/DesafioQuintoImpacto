package Controladores;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import Servicios.AdministradorService;
import Servicios.AlumnoService;
import Servicios.ProfesorService;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    AdministradorService AdministradorService;

    @Autowired
    ProfesorService ProfesorService;

    @Autowired
    AlumnoService AlumnoService;

    @GetMapping("/login")
    public String mostrarFormularioLogin() {
        return "Login.html";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
            @RequestParam("password") String password,
            HttpSession session, ModelMap modelo) {
                boolean autenticado = false;
                if(username.contains("administradores")){
                    autenticado = true;
                }

        if (autenticado) {
            session.setAttribute("username", username);
            return "redirect:/perfil";
        } else {
            return "redirect:/login?error=true";
        }
    }

    @GetMapping("/perfil")
    public String perfil(HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username != null) {
            // Tu lógica para obtener información del usuario y mostrarla en la página de
            // perfil
            return "perfil";
        } else {
            return "redirect:/login";
        }
    }
}
