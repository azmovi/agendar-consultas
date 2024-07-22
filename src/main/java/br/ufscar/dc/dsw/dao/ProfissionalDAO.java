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

        String sql = "SELECT Usuario.id_usuario, Usuario.nome, Profissional.especialidade FROM Profissional JOIN Usuario ON Usuario.id_usuario = Profissional.id_usuario";

        try
        {
            Connection conn = this.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);

            try (ResultSet resultSet = statement.executeQuery())
            {
                while (resultSet.next())
                {
                    Long idUsuario = resultSet.getLong("id_usuario");
                    String nome = resultSet.getString("nome");
                    String especialidade = resultSet.getString("especialidade");
                    Profissional profissional = new Profissional(idUsuario, nome, especialidade);
                    listaProfissionais.add(profissional);
                }

                statement.close();
            }
            conn.close();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        return listaProfissionais;
    }

    public List<Profissional> getByFilter(String pesquisa) {

        List<Profissional> listaProfissionais = new ArrayList<>();

        String sql = "SELECT Usuario.id_usuario, Usuario.nome, Profissional.especialidade FROM Profissional JOIN Usuario ON Usuario.id_usuario = Profissional.id_usuario WHERE UPPER(Profissional.especialidade) LIKE ?";

        try
        {
            Connection conn = this.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);

            String pesquisaFormatada = "%" + pesquisa.toUpperCase() + "%";

            statement.setString(1, pesquisaFormatada);

            try (ResultSet resultSet = statement.executeQuery())
            {
                while (resultSet.next())
                {
                    Long idUsuario = resultSet.getLong("id_usuario");
                    String nome = resultSet.getString("nome");
                    String especialidade = resultSet.getString("especialidade");
                    Profissional profissional = new Profissional(idUsuario, nome, especialidade);
                    listaProfissionais.add(profissional);
                }

                statement.close();
            }
            conn.close();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        return listaProfissionais;
    }

    public Profissional getProfissional(long idUsuario) {

        String sql = "SELECT nome, email, cpf, especialidade FROM Usuario JOIN Profissional ON Usuario.id_usuario = Profissional.id_usuario WHERE Usuario.id_usuario = ?";
        Profissional profissional = null;

        try
        {
            Connection conn = this.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setLong(1, idUsuario);

            try (ResultSet resultSet = statement.executeQuery())
            {
                if (resultSet.next())
                {
                    String nome = resultSet.getString("nome");
                    String email = resultSet.getString("email");
                    String cpf = resultSet.getString("cpf");
                    String especialidade = resultSet.getString("especialidade");
                    profissional = new Profissional(idUsuario, nome, email, cpf, especialidade);
                }
            }
            statement.close();
            conn.close();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        return profissional;
    }
}
