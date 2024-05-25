package br.ufscar.dc.dsw.controller;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

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
import br.ufscar.dc.dsw.dao.ProfissionalDAO;

@WebServlet(urlPatterns = {"/agendamento"})

public class AgendamentoController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private AgendamentoDAO agendamentoDAO;
    private ProfissionalDAO profissionalDAO;

    @Override
    public void init() {
        agendamentoDAO = new AgendamentoDAO();
        profissionalDAO = new ProfissionalDAO();
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

            case "minhasConsultas":
                minhasConsultas(request, response, session);
                break;

            default:
                invalidar(request, response, session);
                break;
        }
    }


    protected void consultarPerfil(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException
    {
        Long idUsuarioProfissionalEscolhido =  Long.parseLong(request.getParameter("IdProfissionalEscolhido"));
        Profissional profissionalEscolhido = profissionalDAO.getProfissional(idUsuarioProfissionalEscolhido);

        if (profissionalEscolhido != null)
        {
            session.setAttribute("profissionalEscolhido", profissionalEscolhido);
            response.sendRedirect("/AgendarConsultas/forms_agendamento.jsp");
        }
        else
        {
            session.setAttribute("errorProfissinalNaoExiste", "Nao foi possivel encontrar esse Profissional");
            response.sendRedirect("/AgendarConsultas");
        }

    }


    protected void minhasConsultas(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException
    {
        Long idUsuario =  Long.parseLong(request.getParameter("idUsuario"));

        List<Agendamento> listaAgendamentoPorUsuario = agendamentoDAO.agendamentoPorUsuario(idUsuario);

        if (listaAgendamentoPorUsuario.size() > 0)
        {
            session.setAttribute("listaAgendamentoPorUsuario", listaAgendamentoPorUsuario);
        }
        else
        {
            session.setAttribute("semConsultas", "Voce não tem nenhuma consulta agendada :(");
        }
        response.sendRedirect("/AgendarConsultas/perfil/consultas.jsp");
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
        
        Object objProfissional = session.getAttribute("profissionalEscolhido");
        
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

        if (agendamentoDAO.agendamentoValido(data, horario))
        {
            long idAgendamento = agendamentoDAO.cadastrarConsulta(agendamento);

            if (idAgendamento != 0){
                agendamento.setIdAgendamento(idAgendamento);
                session.setAttribute("agendamento", agendamento);
                session.setAttribute("agendamentoFeito", "Seu agendamento foi cadastrado");
                response.sendRedirect("/AgendarConsultas");
                return;
            }
            else
            {
                session.setAttribute("erroAgendamento", "Não foi possivel fazer o agendamento");
            }
        }
        else
        {
            session.setAttribute("erroAgendamento", "Data ou horário invalidos/indisponiveis");
        }

        session.setAttribute("data", data);
        session.setAttribute("horario", horario);
        response.sendRedirect("/AgendarConsultas/forms_agendamento.jsp");
    }

    protected void invalidar(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException {
        session.invalidate();
        response.sendRedirect("index.jsp");
    }
}
