package src.br.com.aula.domain;

import java.io.Serializable;

public class Fornecedores implements Serializable {
    private static final long serialVersionUID = 1L;

    private int codigo;
    private String descricao;

    public Fornecedores() {

    }

    public Fornecedores(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "Fornecedores{" +
                "codigo=" + codigo +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
