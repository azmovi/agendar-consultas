package br.ufscar.dc.dsw.domain;

public class Profissional extends Usuario {
    private long idProfissional;
    private String especialidade;
    private byte[] pdfData;

    public Profissional(String nome, String email, String senha, String cpf, String especialidade, byte[] pdfData) {
        super(nome, email, senha, cpf);
        this.especialidade = especialidade;
        this.pdfData = pdfData;
    }

    public Profissional(Long idUsuario, String nome, String email, String senha, String cpf, String especialidade, byte[] pdfData) {
        super(idUsuario, nome, email, senha, cpf);
        this.especialidade = especialidade;
        this.pdfData = pdfData;
    }

    public Profissional(String nome, String especialidade) {
        super(nome);
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

    public byte[] getPdfData() {
        return pdfData;
    }
    public void setPdfData(byte[] pdfData) {
        this.pdfData = pdfData;
    }
}

