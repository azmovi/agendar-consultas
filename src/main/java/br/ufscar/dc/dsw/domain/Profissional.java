package br.ufscar.dc.dsw.domain;

public class Profissional extends Usuario {
    private long idProfissional;
    private String especialidade;

    public Profissional(String nome, String email, String senha, String cpf, String especialidade) {
        super(nome, email, senha, cpf);
        this.especialidade = especialidade;
    }

    public Profissional(Long idUsuario, String nome, String email, String senha, String cpf, String especialidade) {
        super(idUsuario, nome, email, senha, cpf);
        this.especialidade = especialidade;
    }

    public Profissional(Long idUsuario, String nome, String especialidade) {
        super(idUsuario, nome);
        this.especialidade = especialidade;
    }

    public Profissional(Long idUsuario, String nome, String email, String cpf, String especialidade)
    {
        super(idUsuario, nome, email, cpf);
        this.especialidade = especialidade;
    }

    public long getIdProfissional() {
        return idProfissional;
    }
    public void setIdProfissional(long idProfissional) {
        this.idProfissional= idProfissional;
    }

    public String getEspecialidade() {
        return especialidade;
    }
    public void setEspecialidade(String especialidade) {
        this.especialidade= especialidade;
    }
}

