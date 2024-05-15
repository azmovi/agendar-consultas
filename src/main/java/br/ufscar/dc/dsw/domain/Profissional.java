package br.ufscar.dc.dsw.domain;

public class Profissional extends Usuario {
    private long idProfissional;
    private String especilidade;
    private byte[] pdfData;

    public long getIdProfissional() {
        return idProfissional;
    }
    public void setIdProfissional(long idProfissional) {
        this.idProfissional= idProfissional;
    }

    public String getEspecialidade() {
        return especilidade;
    }
    public void setEspecialidade(String especilidade) {
        this.especilidade = especilidade;
    }

    public byte[] getPdfData() {
        return pdfData;
    }
    public void setPdfData(byte[] pdfData) {
        this.pdfData = pdfData;
    }
}

