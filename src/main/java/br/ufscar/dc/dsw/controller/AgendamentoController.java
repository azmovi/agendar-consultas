package br.ufscar.dc.dsw.controller;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.ufscar.dc.dsw.domain.Agendamento;
import br.ufscar.dc.dsw.domain.Cliente;
import br.ufscar.dc.dsw.domain.Profissional;
import br.ufscar.dc.dsw.util.Conversor;
import br.ufscar.dc.dsw.dao.AgendamentoDAO;

@WebServlet(urlPatterns = {"/agendamento"})

public class AgendamentoController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private AgendamentoDAO agendamentoDAO;

    @Override
    public void init() {
        agendamentoDAO = new AgendamentoDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();

        switch (action) {
            case "consultarPerfil":
                consultarPerfil(request, response, session);
                break;

            default:
                invalidar(request, response, session);
                break;
        }
    }


    protected void consultarPerfil(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException
    {
        Object objProfissional =  session.getAttribute("ProfissionalEscolhido");

        if (objProfissional instanceof Profissional) {
            Profissional profissionalEscolhido = (Profissional) objProfissional;
        }
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        HttpSession session = request.getSession();

        Object objCliente = session.getAttribute("cliente");

        Long idUsuarioCliente = null;
        Long idUsuarioProfissional = null;

        if (objCliente instanceof Cliente) {
            Cliente cliente = (Cliente) objCliente;
            idUsuarioCliente = cliente.getIdUsuario();
        }
        
        Object objProfissional = session.getAttribute("profissionalPerfil");
        
        if (objProfissional instanceof Profissional) {
            Profissional profissional = (Profissional) objProfissional;
            idUsuarioProfissional = profissional.getIdUsuario();
        }


        String dataString = request.getParameter("data");
        String horarioString = request.getParameter("horario"); 

        Conversor conversor = new Conversor();

        Date data = conversor.StringParaData(dataString);
        Time horario = conversor.StringParaHorario(horarioString);

        Agendamento agendamento = new Agendamento(idUsuarioCliente, idUsuarioProfissional, data, horario);

        long idAgendamento = agendamentoDAO.cadastrarConsulta(agendamento);

        if (idAgendamento != 0){
            agendamento.setIdAgendamento(idAgendamento);
            session.setAttribute("agendamento", agendamento);
        }

        else
        {
            session.setAttribute("data", data);
            session.setAttribute("horario", horario);
            session.setAttribute("erroCriarAgendamento", "Não foi possivel realizar esse Agendamento");
        }
            response.sendRedirect("/AgendarConsultas");
    }

    protected void invalidar(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException {
        session.invalidate();
        response.sendRedirect("index.jsp");
    }
}
