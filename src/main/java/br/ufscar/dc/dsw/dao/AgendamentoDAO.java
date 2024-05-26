package br.ufscar.dc.dsw.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import br.ufscar.dc.dsw.domain.Agendamento;

public class AgendamentoDAO extends GenericDAO {

    public List<Agendamento> agendamentoPorUsuario(long idUsuario) {

        String sql = "SELECT Agendamento.id_agendamento, Cliente.nome as cliente_nome, Profissional.nome as profissional_nome, Agendamento.data, Agendamento.horario " +
            "FROM Agendamento " +
            "LEFT JOIN Usuario Cliente ON Agendamento.id_usuario_cliente = Cliente.id_usuario " +
            "LEFT JOIN Usuario Profissional ON Agendamento.id_usuario_profissional = Profissional.id_usuario " +
            "WHERE Agendamento.id_usuario_cliente = ? OR Agendamento.id_usuario_profissional = ?";

        List<Agendamento> listaAgendamentosPorUsuario = new ArrayList<>();

        try
        {
            Connection  conn = this.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setLong(1, idUsuario);
            statement.setLong(2, idUsuario);

            try (ResultSet resultSet = statement.executeQuery())
            {
                while (resultSet.next())
                {
                    Long idAgendamento = resultSet.getLong("id_agendamento");
                    String nomeCliente = resultSet.getString("cliente_nome");
                    String nomeProfissional = resultSet.getString("profissional_nome");
                    Date data = resultSet.getDate("data");
                    Time horario = resultSet.getTime("horario");
                    Agendamento agendamento = new Agendamento(idAgendamento, nomeCliente, nomeProfissional, data, horario);
                    listaAgendamentosPorUsuario.add(agendamento);
                }

                statement.close();
            }
            conn.close();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        return listaAgendamentosPorUsuario;
    }

    public long cadastrarConsulta(Agendamento agendamento) {

        String sql= "INSERT INTO Agendamento(id_usuario_cliente, id_usuario_profissional, data, horario) VALUES (?, ?, ?, ?)";
        long idAgendamento = 0;

        try
        {
            Connection  conn = this.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setLong(1, agendamento.getIdUsuarioCliente());
            statement.setLong(2, agendamento.getIdUsuarioProfissional());
            statement.setDate(3, agendamento.getData());
            statement.setTime(4, agendamento.getHorario());

            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                idAgendamento = generatedKeys.getLong(1);
            }
            statement.close();
            conn.close();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        return idAgendamento;
    }

    public boolean agendamentoValido(Date data, Time horario)
    {
        LocalDate diaAtual = LocalDate.now();
        LocalTime horarioAtual = LocalTime.now();

        LocalTime horarioAgendamento = horario.toLocalTime();
        LocalDate dataAgendamento = data.toLocalDate();

        boolean dataValida = false;

        if (dataAgendamento.isAfter(diaAtual) || ( dataAgendamento.equals(diaAtual) && horarioAgendamento.isAfter(horarioAtual)))
        {
            dataValida = !existeConsulta(data, horario);
        }

        return dataValida;
    }

    public boolean existeConsulta(Date data, Time horario)
    {
        String sql= "SELECT data, horario FROM Agendamento WHERE data = ? and horario = ? LIMIT 1";
        boolean existeConsulta = false;
        try
        {
            Connection  conn = this.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setDate(1, data);
            statement.setTime(2, horario);

            try (ResultSet resultSet = statement.executeQuery())
            {
                if (resultSet.next())
                {
                    existeConsulta = true;
                }
                statement.close();
            }
            conn.close();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

        return existeConsulta;
    }

}
