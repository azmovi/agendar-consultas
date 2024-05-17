package br.ufscar.dc.dsw.controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

        if (usuarioDAO.usuarioValido(email, cpf))
        {
            Cliente cliente = new Cliente(nome, email, senha, cpf, sexo, dataNascimento);
            usuarioDAO.insertUsuario(cliente);

            request.getSession().setAttribute("cliente", cliente);
            response.sendRedirect("index.jsp");
        }

        else
        {
            request.setAttribute("ErrorCriarNovoUsuario", "Este EMAIL ou CPF já está em uso.");
            request.setAttribute("nome", nome);
            request.setAttribute("email", email);
            request.setAttribute("senha", senha);
            request.setAttribute("cpf", cpf);
            request.setAttribute("dataNascimentoString", dataNascimentoString);
            request.setAttribute("sexoString", sexoString);
            RequestDispatcher dispatcher = request.getRequestDispatcher("cliente_cadastro.jsp");
            dispatcher.forward(request, response);
        }
    }

    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("AgendarConsultas");
        dispatcher.forward(request, response);
    }
}
