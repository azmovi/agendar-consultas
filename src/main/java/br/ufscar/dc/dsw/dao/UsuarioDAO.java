package br.ufscar.dc.dsw.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import br.ufscar.dc.dsw.domain.Cliente;
import br.ufscar.dc.dsw.domain.Profissional;
import br.ufscar.dc.dsw.domain.Usuario;

public class UsuarioDAO extends GenericDAO {
    public void insertUsuario(Usuario usuario) {

        String sqlUsuario = "INSERT INTO Usuario(nome, email, senha, cpf) VALUES (?, ?, ?, ?)";
        try
        {
            Connection  conn = this.getConnection();
            PreparedStatement statementUsuario = conn.prepareStatement(sqlUsuario, Statement.RETURN_GENERATED_KEYS);

            statementUsuario.setString(1, usuario.getNome());
            statementUsuario.setString(2, usuario.getEmail());
            statementUsuario.setString(3, usuario.getSenha());
            statementUsuario.setString(4, usuario.getCpf());

            statementUsuario.executeUpdate();

            ResultSet generatedKeys = statementUsuario.getGeneratedKeys();
            if (generatedKeys.next()) {
                long idUsuario = generatedKeys.getLong(1);

                if (usuario instanceof Cliente)
                {
                    Cliente cliente = (Cliente) usuario;
                    insertCliente(conn, idUsuario, cliente);
                }
                else if (usuario instanceof Profissional)
                {
                    insertProfissional(conn, idUsuario, (Profissional) usuario);
                }
            }
            statementUsuario.close();
            conn.close();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void insertCliente(Connection conn, Long idUsuario, Cliente cliente) {
        String sqlCliente = "INSERT INTO Cliente(id_usuario, sexo, data_nascimento) VALUES (?, ?, ?)";

        try
        {
            PreparedStatement statementCliente = conn.prepareStatement(sqlCliente);

            statementCliente.setLong(1, idUsuario);
            statementCliente.setString(2, cliente.getSexo().toString());

            Date sqlDate = new java.sql.Date(cliente.getDataNascimento().getTime());
            statementCliente.setDate(3, sqlDate);

            statementCliente.executeUpdate();

            statementCliente.close();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }
    public void insertProfissional(Connection conn, Long idUsuario, Profissional profissional) {
        String sqlProfissional = "INSERT INTO Profissional(id_usuario, especialidade, pdf_data) VALUES (?, ?, ?)";

        try
        {
            PreparedStatement statementProfissional = conn.prepareStatement(sqlProfissional);

            statementProfissional.setLong(1, idUsuario);
            statementProfissional.setString(2, profissional.getEspecialidade());
            statementProfissional.setBytes(3, profissional.getPdfData());

            statementProfissional.executeUpdate();

            statementProfissional.close();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    public Usuario getUsuario(String email) {
        String sql = "SELECT * FROM Usuario WHERE email = ?";
        Usuario usuario = null;

        try
        {
            Connection  conn = this.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setString(1, email);

            try (ResultSet resultSet = statement.executeQuery())
            {
                if (resultSet.next()) {
                    long id = resultSet.getLong("id_usuario");
                    String nome = resultSet.getString("nome");
                    String senha = resultSet.getString("senha");
                    String cpf = resultSet.getString("cpf");

                    usuario = new Usuario(id, nome, email, senha, cpf);
                }
            }
            statement.close();
            conn.close();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

        return usuario;
    }

    public boolean usuarioValido(String email, String cpf) {
        String sql = "SELECT 1 FROM Usuario WHERE email = ? OR cpf = ?";
        boolean validUser = true;

        try
        {
            Connection  conn = this.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setString(1, email);
            statement.setString(2, cpf);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    validUser = false;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return validUser;
    }
}
