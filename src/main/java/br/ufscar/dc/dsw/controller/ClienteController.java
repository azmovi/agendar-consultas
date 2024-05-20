package br.ufscar.dc.dsw.controller;

import java.io.IOException;
import java.util.Date;

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

        HttpSession session = request.getSession();

        String path = request.getHeader("Referer");
        String[] parts = path.split("/");
        String service = parts[parts.length - 2];

        Cliente cliente = configCliente(request, session, service);

        try{
            switch (service) {
                case "cadastro":
                    registrar(response, session, cliente);
                    break;

                case "atualizar":
                    atualizar(response, session, cliente);
                    break;

                default:
                    session.invalidate();
                    response.sendRedirect("index.jsp");
                    break;
            }
        }
        catch (ServletException e)
        {
            throw new ServletException(e);
        }
    }

    protected Cliente configCliente(HttpServletRequest request, HttpSession session, String service) throws ServletException, IOException {
        Cliente cliente = null;

        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        String cpf = request.getParameter("cpf");
        String dataNascimentoString = request.getParameter("nascimento");
        String sexoString = request.getParameter("sexo");

        Conversor conversor = new Conversor();
        Date dataNascimento = conversor.StringParaData(dataNascimentoString);
        Sexo sexo = conversor.StringParaSexo(sexoString);

        long idUsuario = usuarioDAO.getIdUsuario(email, cpf);

        if ((service.equals("cadastro") && idUsuario == 0) || (service.equals("atualizar") && idUsuario != 0))
        {
            cliente = new Cliente(idUsuario, nome, email, senha, cpf, sexo, dataNascimento);
        }

        else
        {
            session.setAttribute("nome", nome);
            session.setAttribute("email", email);
            session.setAttribute("senha", senha);
            session.setAttribute("cpf", cpf);
            session.setAttribute("dataNascimentoString", dataNascimentoString);
            session.setAttribute("sexoString", sexoString);
        }

        return cliente;
    }

    protected void registrar(HttpServletResponse response, HttpSession session, Cliente cliente) throws ServletException, IOException {
        if (cliente != null)
        {
            usuarioDAO.insertUsuario(cliente);
            session.setAttribute("cliente", cliente);
            response.sendRedirect("index.jsp");
        }
        else
        {
            session.setAttribute("erroNovoUsuario", "Este EMAIL ou CPF já está em uso.");
            response.sendRedirect("/AgendarConsultas/cadastro/cliente.jsp");

            //RequestDispatcher dispatcher = request.getRequestDispatcher("cadastro/cliente.jsp");
            //dispatcher.forward(request, response);
        }
    }

    protected void atualizar(HttpServletResponse response, HttpSession session, Cliente cliente) throws ServletException, IOException {
        if (cliente != null)
        {
            usuarioDAO.updateUsuario(cliente);
            session.setAttribute("cliente", cliente);
            response.sendRedirect("/AgendarConsultas/perfil/usuario.jsp");
        }
        else
        {
            session.setAttribute("erroAtualizarCliente", "Este EMAIL ou CPF já está em uso.");
            response.sendRedirect("/AgendarConsultas/perfil/atualizar/cliente.jsp");

            //RequestDispatcher dispatcher = request.getRequestDispatcher("cadastro/cliente.jsp");
            //dispatcher.forward(request, response);
        }
    }
}
