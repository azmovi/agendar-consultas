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

@WebServlet(urlPatterns = {"/profissional"})

public class ProfissionalController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private UsuarioDAO usuarioDAO;
    private ProfissionalDAO profissionalDAO;

    @Override
    public void init() {
        usuarioDAO = new UsuarioDAO();
        profissionalDAO = new ProfissionalDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Profissional> listaProfissionais = profissionalDAO.getAll();

        HttpSession session = request.getSession();
        session.setAttribute("listaProfissionais", listaProfissionais);
        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
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
    protected void registrar(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException
    {
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        String cpf = request.getParameter("cpf");

        String especialidade = request.getParameter("especialidade");

        Conversor conversor = new Conversor();

        String pdfString = request.getParameter("pdfData");
        byte[] pdfData = conversor.StringParaPdf(pdfString);

        Profissional profissional = new Profissional(nome, email, senha, cpf, especialidade, pdfData);
        long idUsuario = usuarioDAO.inserirUsuario(profissional);
        if (idUsuario != 0)
        {
            profissional.setIdUsuario(idUsuario);
            session.setAttribute("profissional", profissional);
            response.sendRedirect("/AgendarConsultas");
        }
        else
        {
            session.setAttribute("nome", nome);
            session.setAttribute("email", email);
            session.setAttribute("senha", senha);
            session.setAttribute("cpf", cpf);
            session.setAttribute("especialidade", especialidade);
            session.setAttribute("pdfString", pdfString);

            session.setAttribute("ErrorCriarNovoUsuario", "Este EMAIL ou CPF já está em uso.");
            response.sendRedirect("/AgendarConsultas/cadastro/profissional.jsp");
        }
    }

    protected void atualizar(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException {

        long idUsuario = 0;
        Object obj = session.getAttribute("cliente");

        if (obj instanceof Profissional) {
            Profissional profissional = (Profissional) obj;
            idUsuario = profissional.getIdUsuario();
        }

        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        String cpf = request.getParameter("cpf");
        String especialidade = request.getParameter("especialidade");
        String pdfString = request.getParameter("pdfData");

        Conversor conversor = new Conversor();
        byte[] pdfData = conversor.StringParaPdf(pdfString);


        Profissional profissional = new Profissional(idUsuario, nome, email, senha, cpf, especialidade, pdfData);

        boolean deuCerto = usuarioDAO.updateUsuario(profissional);
        if (deuCerto)
        {
            session.setAttribute("profissional", profissional);
            response.sendRedirect("/AgendarConsultas/perfil/usuario.jsp");
        }

        else
        {
            session.setAttribute("erroAtualizarProfissional", "Este EMAIL ou CPF já está em uso.");
            response.sendRedirect("/AgendarConsultas/perfil/atualizar/profissional.jsp");
        }
    }

    protected void invalidar(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException {
        session.invalidate();
        response.sendRedirect("index.jsp");
    }
}
