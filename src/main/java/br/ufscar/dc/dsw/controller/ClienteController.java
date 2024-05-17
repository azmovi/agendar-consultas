package br.ufscar.dc.dsw.controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
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

@WebServlet(urlPatterns = {"/criar_cliente"})

public class ClienteController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private UsuarioDAO usuarioDAO;

    @Override
    public void init() {
        usuarioDAO = new UsuarioDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        String cpf = request.getParameter("cpf");

        Conversor conversor = new Conversor();

        String dataNascimentoString = request.getParameter("nascimento");
        Date dataNascimento = conversor.ConverterStringData(dataNascimentoString);

        String sexoString = request.getParameter("sexo");
        Sexo sexo = conversor.ConverterStringSexo(sexoString);

        HttpSession session = request.getSession();

        if (usuarioDAO.usuarioValido(email, cpf))
        {
            Cliente cliente = new Cliente(nome, email, senha, cpf, sexo, dataNascimento);
            usuarioDAO.insertUsuario(cliente);

            session.setAttribute("cliente", cliente);
            response.sendRedirect("index.jsp");
        }

        else
        {
            session.setAttribute("ErrorCriarNovoUsuario", "Este EMAIL ou CPF já está em uso.");
            session.setAttribute("nome", nome);
            session.setAttribute("email", email);
            session.setAttribute("senha", senha);
            session.setAttribute("cpf", cpf);
            session.setAttribute("dataNascimentoString", dataNascimentoString);
            session.setAttribute("sexoString", sexoString);

            RequestDispatcher dispatcher = request.getRequestDispatcher("cadastro/cliente/formulario.jsp");
            dispatcher.forward(request, response);
        }
    }

    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("AgendarConsultas");
        dispatcher.forward(request, response);
    }
}
