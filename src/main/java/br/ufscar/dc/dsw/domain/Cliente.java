package br.ufscar.dc.dsw.domain;

import java.sql.Date;
import br.ufscar.dc.dsw.util.Sexo;

public class Cliente extends Usuario {
    private long idCliente;
    private Date dataNascimento;
    private Sexo sexo;

    public Cliente(String nome, String email, String senha, String cpf, Sexo sexo, Date dataNascimento) {
        super(nome, email, senha, cpf);
        this.sexo = sexo;
        this.dataNascimento = dataNascimento;

    }

    public Cliente(Long idUsusario, String nome, String email, String senha, String cpf, Sexo sexo, Date dataNascimento) {
        super(idUsusario, nome, email, senha, cpf);
        this.sexo = sexo;
        this.dataNascimento = dataNascimento;

    }
    public long getIdCliente() {
        return idCliente;
    }
    public void setIdCliente(long idCliente) {
        this.idCliente = idCliente;
    }

    public Sexo getSexo() {
        return sexo;
    }
    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }
    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
}

