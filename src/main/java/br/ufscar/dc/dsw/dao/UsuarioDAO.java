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
import br.ufscar.dc.dsw.util.Conversor;
import br.ufscar.dc.dsw.util.Sexo;

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

    public long getIdUsuario (String email, String cpf) {
        String sql = "SELECT id_usuario FROM Usuario WHERE email = ? OR cpf = ?";
        long idUser = 0;

        try
        {
            Connection  conn = this.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setString(1, email);
            statement.setString(2, cpf);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    idUser = resultSet.getLong("id_usuario");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return idUser;
    }

    public Usuario getUsuario(String email, String senha) {
        String sql = "SELECT * FROM Usuario WHERE email = ? AND senha = ?";
        Usuario usuario = null;

        try
        {
            Connection  conn = this.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setString(1, email);
            statement.setString(2, senha);
            try (ResultSet resultSet = statement.executeQuery())
            {
                if (resultSet.next()) {
                    long id = resultSet.getLong("id_usuario");
                    String nome = resultSet.getString("nome");
                    String cpf = resultSet.getString("cpf");

                    if (eUmCliente(conn, id))
                    {
                        usuario = getCliente(conn, id, nome, email, senha, cpf);
                    }
                    else
                    {
                        usuario = getProfissional(conn, id, nome, email, senha, cpf);
                    }
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
    public boolean eUmCliente(Connection conn, long id)
    {
        boolean encontrando = false;
        String sql = "SELECT * FROM Cliente WHERE Cliente.id_usuario = ?";
        try
        {

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setLong(1, id);
            try(ResultSet resultSet = statement.executeQuery())
            {
                if (resultSet.next())
                {
                    encontrando = true;
                }
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        return encontrando;
    }

    public Cliente getCliente(Connection conn, Long id, String nome, String email, String senha, String cpf)
    {
        Cliente cliente = null;
        String sql = "SELECT * FROM Cliente WHERE id_usuario = ?";
        try
        {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setLong(1, id);
            try(ResultSet resultSet = statement.executeQuery())
            {
                if (resultSet.next())
                {
                    String sexoString = resultSet.getString("sexo");
                    Conversor conversor = new Conversor();
                    Sexo sexo = conversor.StringParaSexo(sexoString);

                    Date dataNascimento = resultSet.getDate("data_nascimento");

                    cliente = new Cliente(nome, email, senha, cpf, sexo, dataNascimento);
                }
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

        return cliente;
    }

    public Profissional getProfissional(Connection conn, Long id, String nome, String email, String senha, String cpf)
    {
        Profissional profissional = null;
        String sql = "SELECT * FROM Profissional WHERE id_usuario = ?";
        try
        {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setLong(1, id);
            try(ResultSet resultSet = statement.executeQuery())
            {
                if (resultSet.next())
                {
                    String especialidade = resultSet.getString("especialidade");
                    byte[] pdfData = resultSet.getBytes("pdf_data");

                    profissional = new Profissional(nome, email, senha, cpf, especialidade, pdfData);
                }
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        return profissional;
    }

    public void updateUsuario(Usuario usuario) {

        String sqlUsuario= "UPDATE Usuario SET nome = ?, email = ?, senha = ?, cpf = ? WHERE id_usuario = ?";

        try
        {
            Connection  conn = this.getConnection();
            PreparedStatement statementUsuario = conn.prepareStatement(sqlUsuario, Statement.RETURN_GENERATED_KEYS);

            statementUsuario.setString(1, usuario.getNome());
            statementUsuario.setString(2, usuario.getEmail());
            statementUsuario.setString(3, usuario.getSenha());
            statementUsuario.setString(4, usuario.getCpf());

            statementUsuario.setLong(5, usuario.getIdUsuario());

            statementUsuario.executeUpdate();

            if (usuario instanceof Cliente)
            {
                updateCliente(conn, (Cliente) usuario);
            }

            statementUsuario.close();
            conn.close();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void updateCliente(Connection conn,  Cliente cliente) {
        String sqlCliente = "UPDATE Cliente SET sexo = ?, data_nascimento = ? WHERE id_usuario = ?";

        try
        {
            PreparedStatement statementCliente = conn.prepareStatement(sqlCliente);

            statementCliente.setString(1, cliente.getSexo().toString());
            Date sqlDate = new java.sql.Date(cliente.getDataNascimento().getTime());
            statementCliente.setDate(2, sqlDate);

            statementCliente.setLong(3, cliente.getIdUsuario());

            statementCliente.executeUpdate();

            statementCliente.close();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }
}
