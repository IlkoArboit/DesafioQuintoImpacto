package Controladores;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/perfiles")
public class PerfilController {

    @GetMapping("/perfil")
    public String perfil(HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username != null) {
            // Tu lógica para obtener información del usuario y mostrarla en la página de
            // perfil
            return "Perfil.html";
        } else {
            return "redirect:/login";
        }
    }

}
