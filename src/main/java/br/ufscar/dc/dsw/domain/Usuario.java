package br.ufscar.dc.dsw.domain;

public class Usuario{
    private long idUsuario;
    private String nome;
    private String email;
    private String senha;
    private String cpf;


    public Usuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	public Usuario(String nome, String email, String senha, String cpf) {
		this.nome = nome;
		this.email = email;
		this.senha = senha;
        this.cpf = cpf;
	}
	
	public Usuario(Long idUsuario, String nome, String email, String senha, String cpf) {
		this.idUsuario = idUsuario;
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.cpf = cpf;
	}

	public Usuario(String nome) {
		this.nome = nome;
	}

    public long getIdUsuario() {
        return idUsuario;
    }
    public void setIdUsuario(long idUsuario) {
        this.idUsuario= idUsuario;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
