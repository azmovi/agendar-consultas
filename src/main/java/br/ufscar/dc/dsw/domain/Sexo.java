package br.ufscar.dc.dsw.domain;

public enum Sexo {
    MASCULINO(1),
    FEMININO(2),
    OUTRO(3);
    
    private final int codigo;

    Sexo(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }
}
