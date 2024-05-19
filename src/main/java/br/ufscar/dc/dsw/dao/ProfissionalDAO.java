package br.ufscar.dc.dsw.dao;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.ufscar.dc.dsw.domain.Profissional;

public class ProfissionalDAO extends GenericDAO {

    public List<Profissional> getAll() {

        List<Profissional> listaProfissionais = new ArrayList<>();

        String sql = "SELECT Profissional.especialidade, Usuario.nome FROM Profissional JOIN Usuario ON Usuario.id_usuario = Profissional.id_usuario";

        try
        {
            Connection conn = this.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);

            try (ResultSet resultSet = statement.executeQuery())
            {
                while (resultSet.next())
                {
                    String nome = resultSet.getString("nome");
                    String especialidade = resultSet.getString("especialidade");
                    Profissional profissional = new Profissional(nome, especialidade);
                    listaProfissionais.add(profissional);
                }
            }
            statement.close();
            conn.close();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        return listaProfissionais;
    }
}
