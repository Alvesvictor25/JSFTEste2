package src.br.com.aula.domain;
import java.io.Serializable;

public class Produtos implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer codigo;
    private String descricao;
    private Double valor;
    private Integer quantidade;
    public Fornecedores fornecedores;

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Fornecedores getFornecedores() {
        return fornecedores;
    }

    public void setFornecedores(Fornecedores fornecedores) {
        this.fornecedores = fornecedores;
    }

    @Override
    public String toString() {
        return "Produtos{" +
                "codigo=" + codigo +
                ", descricao='" + descricao + '\'' +
                ", valor=" + valor +
                ", quantidade=" + quantidade +
                ", fornecedores=" + fornecedores +
                '}';
    }
}
