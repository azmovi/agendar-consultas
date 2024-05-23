package br.ufscar.dc.dsw.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import br.ufscar.dc.dsw.domain.Agendamento;

public class AgendamentoDAO extends GenericDAO {

    public long cadastrarConsulta(Agendamento agendamento) {

        String sql= "INSERT INTO AgendamentoDAO(id_usuario_cliente, id_usuario_profissional, data, horario) VALUES (?, ?, ?, ?)";
        long idAgendamento = 0;

        try
        {
            Connection  conn = this.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setLong(1, agendamento.getIdUsuarioCliente());
            statement.setLong(2, agendamento.getIdUsuarioProfissional());
            statement.setDate(3, agendamento.getData());
            statement.setTime(5, agendamento.getHorario());

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
}
