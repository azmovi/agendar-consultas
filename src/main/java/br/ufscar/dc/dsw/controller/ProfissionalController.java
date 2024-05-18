package br.ufscar.dc.dsw.controller;

import java.io.IOException;
import java.util.List;

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
import br.ufscar.dc.dsw.dao.ProfissionalDAO;

@WebServlet(urlPatterns = {"/criar_profissional", "/get_profissionais"})

public class ProfissionalController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private UsuarioDAO usuarioDAO;
    private ProfissionalDAO profissionalDAO;

    @Override
    public void init() {
        usuarioDAO = new UsuarioDAO();
        profissionalDAO = new ProfissionalDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
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
            response.sendRedirect("/AgendarConsultas");
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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Profissional> listaProfissionais = profissionalDAO.getAll();

        HttpSession session = request.getSession();
        session.setAttribute("listaProfissionais", listaProfissionais);
        session.setAttribute("teste", "teste");
        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        dispatcher.forward(request, response);
    }
}
