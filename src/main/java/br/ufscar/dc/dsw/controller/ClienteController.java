package br.ufscar.dc.dsw.controller;

import java.io.IOException;
import java.sql.Date;

//import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.ufscar.dc.dsw.domain.Cliente;
import br.ufscar.dc.dsw.util.Sexo;
import br.ufscar.dc.dsw.util.Conversor;
import br.ufscar.dc.dsw.dao.UsuarioDAO;

@WebServlet(urlPatterns = {"/cliente"})
public class ClienteController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private UsuarioDAO usuarioDAO;

    @Override
    public void init() {
        usuarioDAO = new UsuarioDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String path = request.getHeader("Referer");
        String[] parts = path.split("/");
        String action = parts[parts.length - 2];
        HttpSession session = request.getSession();

        try{
            switch (action) {
                case "cadastro":
                    registrar(request, response, session);
                    break;

                case "atualizar":
                    atualizar(request, response, session);
                    break;

                default:
                    invalidar(request, response, session);
                    break;
            }
        }
        catch (ServletException e)
        {
            throw new ServletException(e);
        }
    }

    protected void registrar(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException {

        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        String cpf = request.getParameter("cpf");
        String dataNascimentoString = request.getParameter("nascimento");
        String sexoString = request.getParameter("sexo");

        Conversor conversor = new Conversor();
        Date dataNascimento = conversor.StringParaData(dataNascimentoString);
        Sexo sexo = conversor.StringParaSexo(sexoString);

        Cliente cliente = new Cliente(nome, email, senha, cpf, sexo, dataNascimento);

        long idUsuario = usuarioDAO.inserirUsuario(cliente);

        if (idUsuario != 0){
            cliente.setIdUsuario(idUsuario);
            session.setAttribute("cliente", cliente);
            response.sendRedirect("index.jsp");
        }

        else
        {
            session.setAttribute("nome", nome);
            session.setAttribute("email", email);
            session.setAttribute("senha", senha);
            session.setAttribute("cpf", cpf);
            session.setAttribute("dataNascimentoString", dataNascimentoString);
            session.setAttribute("sexoString", sexoString);

            session.setAttribute("erroNovoUsuario", "Este EMAIL ou CPF já está em uso.");
            response.sendRedirect("/AgendarConsultas/cadastro/cliente.jsp");
        }
    }

    protected void atualizar(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException {

        long idUsuario = 0;
        Object obj = session.getAttribute("cliente");

        if (obj instanceof Cliente) {
            Cliente cliente = (Cliente) obj;
            idUsuario = cliente.getIdUsuario();
        }

        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        String cpf = request.getParameter("cpf");
        String dataNascimentoString = request.getParameter("nascimento");
        String sexoString = request.getParameter("sexo");

        Conversor conversor = new Conversor();
        Date dataNascimento = conversor.StringParaData(dataNascimentoString);
        Sexo sexo = conversor.StringParaSexo(sexoString);


        Cliente cliente = new Cliente(idUsuario, nome, email, senha, cpf, sexo, dataNascimento);

        boolean deuCerto = usuarioDAO.updateUsuario(cliente);
        if (deuCerto)
        {
            session.setAttribute("cliente", cliente);
            response.sendRedirect("/AgendarConsultas/perfil/usuario.jsp");
        }

        else
        {
            session.setAttribute("erroAtualizarCliente", "Este EMAIL ou CPF já está em uso.");
            response.sendRedirect("/AgendarConsultas/perfil/atualizar/cliente.jsp");
        }
    }

    protected void invalidar(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException {
        session.invalidate();
        response.sendRedirect("index.jsp");
    }
}
