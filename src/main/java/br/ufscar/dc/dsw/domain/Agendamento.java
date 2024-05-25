package br.ufscar.dc.dsw.domain;

import java.sql.Date;
import java.sql.Time;

public class Agendamento{
    private long idAgendamento;
    private long idUsuarioCliente;
    private long idUsuarioProfissional;
    private String nomeCliente;
    private String nomeProfissional;
    private Date data;
    private Time horario;

    public Agendamento(long idAgendamento)
    {
        this.idAgendamento = idAgendamento;
    }

    public Agendamento(long idAgendamento, long idUsuarioCliente, long idUsuarioProfissional, Date data, Time horario)
    {
        this.idAgendamento = idAgendamento;
        this.idUsuarioCliente = idUsuarioCliente;
        this.idUsuarioProfissional = idUsuarioProfissional;
        this.data = data;
        this.horario = horario;
    }

    public Agendamento(long idUsuarioCliente, long idUsuarioProfissional, Date data, Time horario)
    {
        this.idUsuarioCliente = idUsuarioCliente;
        this.idUsuarioProfissional = idUsuarioProfissional;
        this.data = data;
        this.horario = horario;
    }

    public Agendamento(Long idAgendamento, String nomeCliente, String nomeProfissional, Date data, Time horario)
    {
        this.idAgendamento = idAgendamento;
        this.nomeCliente = nomeCliente;
        this.nomeProfissional = nomeProfissional;
        this.data = data;
        this.horario = horario;
    }

    public long getIdAgendamento() {
        return idAgendamento;
    }
    public void setIdAgendamento(long idAgendamento) {
        this.idAgendamento = idAgendamento;
    }

    public long getIdUsuarioCliente() {
        return idUsuarioCliente;
    }
    public void setIdUsuarioCliente(long idUsuarioCliente) {
        this.idUsuarioCliente = idUsuarioCliente;
    }

    public long getIdUsuarioProfissional() {
        return idUsuarioProfissional;
    }

    public void setIdUsuarioProfissional(long idUsuarioProfissional) {
        this.idUsuarioProfissional = idUsuarioProfissional;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getNomeProfissional() {
        return nomeProfissional;
    }

    public void setNomeProfissional(String nomeProfissional) {
        this.nomeProfissional = nomeProfissional;
    }

    public Date getData() {
        return data;
    }
    public void getData(Date data) {
        this.data = data;
    }

    public Time getHorario() {
        return horario;
    }
    public void setHorario(Time horario) {
        this.horario = horario;
    }
}

