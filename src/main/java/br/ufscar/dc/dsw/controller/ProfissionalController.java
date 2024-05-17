package br.ufscar.dc.dsw.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.ufscar.dc.dsw.domain.Profissional;
import br.ufscar.dc.dsw.util.Conversor;
import br.ufscar.dc.dsw.dao.UsuarioDAO;

@WebServlet(urlPatterns = {"/criar_profissional"})

public class ProfissionalController extends HttpServlet {

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

        String especialidade = request.getParameter("especialidade");

        Conversor conversor = new Conversor();

        String pdfString = request.getParameter("pdfData");
        byte[] pdfData = conversor.StringParaPdf(pdfString);


        HttpSession session = request.getSession();

        if (usuarioDAO.usuarioValido(email, cpf))
        {
            Profissional profissional = new Profissional(nome, email, senha, cpf, especialidade, pdfData);
            usuarioDAO.insertUsuario(profissional);

            session.setAttribute("profissional", profissional);
            response.sendRedirect("index.jsp");
        }

        else
        {
            session.setAttribute("ErrorCriarNovoUsuario", "Este EMAIL ou CPF já está em uso.");
            session.setAttribute("nome", nome);
            session.setAttribute("email", email);
            session.setAttribute("senha", senha);
            session.setAttribute("cpf", cpf);
            session.setAttribute("especialidade", especialidade);

            RequestDispatcher dispatcher = request.getRequestDispatcher("cadastro/profissional.jsp");
            dispatcher.forward(request, response);
        }
    }
}
