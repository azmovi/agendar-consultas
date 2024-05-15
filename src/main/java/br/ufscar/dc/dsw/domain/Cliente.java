package br.ufscar.dc.dsw.domain;

import java.util.Date;

public class Cliente extends Usuario {
    private long idCliente;
    private Sexo sexo;
    private Date dataNascimento;


    public Cliente(long idCliente) {
        this.idCliente = idCliente;
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

