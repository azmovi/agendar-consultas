package br.ufscar.dc.dsw.controller;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.ufscar.dc.dsw.domain.Cliente;
import br.ufscar.dc.dsw.domain.Usuario;
import br.ufscar.dc.dsw.dao.UsuarioDAO;

@WebServlet(urlPatterns = {"/usuario"})
public class UsuarioController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private UsuarioDAO usuarioDAO;

    @Override
    public void init() {
        usuarioDAO = new UsuarioDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();

        switch (action) {
            case "login":
                logar(request, response, session);
                break;

            case "logout":
                invalidar(request, response, session);
                break;

            default:
                invalidar(request, response, session);
                break;
        }
    }

    protected void logar(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException
    {
        session.removeAttribute("erroLogarUsuario");

        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        Usuario usuario = usuarioDAO.getUsuario(email, senha);

        String tipo_usuario = "profissional";
        if (usuario != null)
        {
            if (usuario instanceof Cliente)
            {
                tipo_usuario = "cliente";
            }

            session.setAttribute(tipo_usuario, usuario);
            response.sendRedirect("/AgendarConsultas");
        } 
        else
        {
            session.setAttribute("erroLogarUsuario", "Email ou Senha incorretos");
            response.sendRedirect("/AgendarConsultas/login/login.jps");
        }
    }

    protected void invalidar(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException {
        session.invalidate();
        response.sendRedirect("index.jsp");
    }
}
