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

    public long inserirUsuario(Usuario usuario) {

        String sqlUsuario = "INSERT INTO Usuario(nome, email, senha, cpf) VALUES (?, ?, ?, ?)";
        long idUsuario = 0;
        try
        {
            Connection  conn = this.getConnection();
            PreparedStatement statementUsuario = conn.prepareStatement(sqlUsuario, Statement.RETURN_GENERATED_KEYS);

            statementUsuario.setString(1, usuario.getNome());
            statementUsuario.setString(2, usuario.getEmail());
            statementUsuario.setString(3, usuario.getSenha());
            statementUsuario.setString(4, usuario.getCpf());
            if (insercaoValida(conn, usuario.getEmail(), usuario.getCpf()))
            {
                statementUsuario.executeUpdate();
                ResultSet generatedKeys = statementUsuario.getGeneratedKeys();
                if (generatedKeys.next()) {
                    idUsuario = generatedKeys.getLong(1);

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
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        return idUsuario;
    }


    public boolean insercaoValida(Connection conn, String email, String cpf)
    {
        String sql = "SELECT email, cpf FROM Usuario WHERE email = ? or cpf = ? LIMIT 1";
        boolean credenciaisValida = true;

        try
        {
            PreparedStatement statement= conn.prepareStatement(sql);
            statement.setString(1, email);
            statement.setString(2, cpf);

            try (ResultSet resultSet = statement.executeQuery())
            {
                if (resultSet.next()) {
                    credenciaisValida = false;
                }
                statement.close();
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

        return credenciaisValida;
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
                resultSet.close();
            }
            statement.close();
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
                    long idUsuario = resultSet.getLong("id_usuario");
                    String nome = resultSet.getString("nome");
                    String cpf = resultSet.getString("cpf");

                    if (eUmCliente(conn, idUsuario))
                    {
                        usuario = getCliente(conn, idUsuario, nome, email, senha, cpf);
                    }
                    else
                    {
                        usuario = getProfissional(conn, idUsuario, nome, email, senha, cpf);
                    }
                }
                resultSet.close();
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
    public boolean eUmCliente(Connection conn, long idUsuario)
    {
        boolean encontrando = false;
        String sql = "SELECT * FROM Cliente WHERE Cliente.id_usuario = ?";
        try
        {

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setLong(1, idUsuario);
            try(ResultSet resultSet = statement.executeQuery())
            {
                if (resultSet.next())
                {
                    encontrando = true;
                }
                resultSet.close();
            }
            statement.close();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        return encontrando;
    }

    public Cliente getCliente(Connection conn, Long idUsuario, String nome, String email, String senha, String cpf)
    {
        Cliente cliente = null;
        String sql = "SELECT * FROM Cliente WHERE id_usuario = ?";
        try
        {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setLong(1, idUsuario);
            try(ResultSet resultSet = statement.executeQuery())
            {
                if (resultSet.next())
                {
                    String sexoString = resultSet.getString("sexo");
                    Conversor conversor = new Conversor();
                    Sexo sexo = conversor.StringParaSexo(sexoString);

                    Date dataNascimento = resultSet.getDate("data_nascimento");

                    cliente = new Cliente(idUsuario, nome, email, senha, cpf, sexo, dataNascimento);
                }
                resultSet.close();
            }
            statement.close();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

        return cliente;
    }

    public Profissional getProfissional(Connection conn, Long idUsuario, String nome, String email, String senha, String cpf)
    {
        Profissional profissional = null;
        String sql = "SELECT * FROM Profissional WHERE id_usuario = ?";
        try
        {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setLong(1, idUsuario);
            try(ResultSet resultSet = statement.executeQuery())
            {
                if (resultSet.next())
                {
                    String especialidade = resultSet.getString("especialidade");
                    byte[] pdfData = resultSet.getBytes("pdf_data");

                    profissional = new Profissional(idUsuario, nome, email, senha, cpf, especialidade, pdfData);
                }
                resultSet.close();
            }
            statement.close();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        return profissional;
    }

    public boolean updateUsuario(Usuario usuario) {

        String sqlUsuario= "UPDATE Usuario SET nome = ?, email = ?, senha = ?, cpf = ? WHERE id_usuario = ?";
        boolean deuCerto = false;

        try
        {
            Connection  conn = this.getConnection();
            PreparedStatement statementUsuario = conn.prepareStatement(sqlUsuario);

            long idUsuario = usuario.getIdUsuario();

            if (updateValido(conn, usuario, idUsuario)){
                statementUsuario.setString(1, usuario.getNome());
                statementUsuario.setString(2, usuario.getEmail());
                statementUsuario.setString(3, usuario.getSenha());
                statementUsuario.setString(4, usuario.getCpf());

                statementUsuario.setLong(5, idUsuario);

                int linhaAlteradas = statementUsuario.executeUpdate();
                deuCerto = (linhaAlteradas > 0);

                if (usuario instanceof Cliente)
                {
                    updateCliente(conn, (Cliente) usuario, idUsuario);
                }
            }

            statementUsuario.close();
            conn.close();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        return deuCerto;
    }

    public void updateCliente(Connection conn,  Cliente cliente, long idUsuario) {
        String sqlCliente = "UPDATE Cliente SET sexo = ?, data_nascimento = ? WHERE id_usuario = ?";

        try
        {
            PreparedStatement statementCliente = conn.prepareStatement(sqlCliente);

            statementCliente.setString(1, cliente.getSexo().toString());
            Date sqlDate = new java.sql.Date(cliente.getDataNascimento().getTime());
            statementCliente.setDate(2, sqlDate);

            statementCliente.setLong(3, idUsuario);

            statementCliente.executeUpdate();

            statementCliente.close();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    public boolean updateValido(Connection conn, Usuario usuario, long idUsuario) {
        return (!existeAtributo(conn, "email", usuario.getEmail(), idUsuario) && !existeAtributo(conn, "cpf", usuario.getCpf(), idUsuario));
    }

    public boolean existeAtributo(Connection conn, String coluna, String valor, long idUsuario) {
        boolean flag = false;
        String sql = "SELECT * FROM Usuario WHERE " + coluna + " = ? AND id_usuario != ? LIMIT 1";
        try
        {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, valor);
            statement.setLong(2, idUsuario);

            try(ResultSet resultSet = statement.executeQuery())
            {
                 if (resultSet.next())
                 {
                    flag = true;
                 }
                resultSet.close();
            }
            statement.close();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        return flag;
    }

    public boolean deletarUsuario(long idUsuario)
    {
        String sql = "DELETE FROM Usuario WHERE id_usuario = ?";
        boolean deletado = false;
        try
        {
            Connection  conn = this.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setLong(1, idUsuario);
            int rowsAffected = statement.executeUpdate();
        
            if (rowsAffected > 0) 
            {
                deletado = true;
            } 

            statement.close();
            conn.close();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        return deletado;
    }
}
