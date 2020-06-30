package src.br.com.aula.dao;

import src.br.com.aula.domain.Fornecedores;
import src.br.com.aula.domain.Produtos;
import src.br.com.aula.factory.ConnectionFactory;
import src.br.com.aula.util.TratamentoErro;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ProdutosDAO implements Serializable {
    private static final long serialVersionUID = 1L;

    public int salvar(Produtos produto) throws TratamentoErro {
        Connection conn = ConnectionFactory.getConnection();
        String sql = "INSERT INTO tb_produto(descricao,quantidade,preco,fk_fornecedores ) values (?,?,?,?)";
        int idGerado = 0;
        try {
            PreparedStatement stmt = ConnectionFactory.getPreparedStatement(conn,sql, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, produto.getDescricao());
            stmt.setInt(2, produto.getQuantidade());
            stmt.setDouble(3, produto.getValor());
            stmt.setObject(4, produto.getFornecedores().getCodigo());

            ResultSet rs = stmt.executeQuery();

            if (rs.next()){
                idGerado = rs.getInt(1);
                produto.setCodigo(idGerado);
            }

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new TratamentoErro("Erro ao salvar fornecedor.", e);
        }
        return idGerado;
    }

    public void delete(Produtos produtos) throws TratamentoErro {
        Connection conn = ConnectionFactory.getConnection();
        String sql = "DELETE FROM tb_produto WHERE codigo = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, produtos.getCodigo());
            stmt.executeUpdate();
            System.out.println("Deletado");
        } catch (SQLException e) {
            throw new TratamentoErro("Erro ao deletar produto. Erro: ");
        }
    }

    public void editar(Produtos produtos) throws TratamentoErro {
        Connection conn = ConnectionFactory.getConnection();
        String sql = "UPDATE tb_fornecedor SET descricao = ? WHERE codigo = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, produtos.getDescricao());
            stmt.setInt(2, produtos.getCodigo());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new TratamentoErro("Erro ao editar fornecedor. Erro: ", e);
        }
    }

    public Produtos buscarFornecedor(Fornecedores fornecedores) throws TratamentoErro {
        Connection conn = ConnectionFactory.getConnection();
        String sql = "SELECT codigo, descricao FROM tb_fornecedor WHERE codigo = ?";
        ResultSet rs = null;
        Produtos buscaFornecedor = new Produtos();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, fornecedores.getCodigo());
            rs = stmt.executeQuery();

            if (rs.next()) {
                buscaFornecedor = criarResultSet(rs);
            }
        } catch (SQLException e) {
            throw new TratamentoErro("Erro busca de fornecedor. Erro: ", e);
        }
        return buscaFornecedor;
    }

    public List<Produtos> buscarTodosProdutos() throws TratamentoErro {
        Connection conn = ConnectionFactory.getConnection();
        String sql = "SELECT tp.codigo, tp.descricao, tp.preco, tp.quantidade, f.codigo, f.descricao  FROM tb_produto tp INNER JOIN tb_fornecedor f on f.codigo = fk_fornecedores";
        List<Produtos> listaProdutos = new ArrayList<Produtos>();
        ResultSet rs = null;
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Produtos resultSet = criarResultSet(rs);
                listaProdutos.add(resultSet);

            }

        } catch (SQLException e) {
            throw new TratamentoErro("Erro ao buscar todos os fornecedores. Erro: ", e);
        }

        return listaProdutos;
    }

    public List<Produtos> buscarPorDescricao(Fornecedores fornecedores) throws TratamentoErro {
        Connection conn = ConnectionFactory.getConnection();
        String sql = "SELECT codigo, descricao FROM tb_fornecedor WHERE descricao LIKE ?";
        List<Produtos> listaDescricao = new ArrayList<>();
        ResultSet rs = null;
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + fornecedores.getDescricao() + "%");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Produtos resultDescricao = criarResultSet(rs);
                listaDescricao.add(resultDescricao);
                listaDescricao.sort(Comparator.comparing(Produtos::getDescricao));
            }

        } catch (SQLException e) {
            throw new TratamentoErro("Erro ao buscar todas as descrições. Erro: ", e);
        }
        return listaDescricao;
    }

    private Produtos criarResultSet(ResultSet rs) {
        Produtos produtosRS = new Produtos();
        Fornecedores f = new Fornecedores();

        try {
            f.setCodigo(rs.getInt("f.codigo"));
            f.setDescricao(rs.getString("f.descricao"));

            produtosRS.setCodigo(rs.getInt("codigo"));
            produtosRS.setDescricao(rs.getString("descricao"));
            produtosRS.setValor(rs.getDouble("preco"));
            produtosRS.setQuantidade(rs.getInt("quantidade"));
            produtosRS.setFornecedores(f);

            FornecedoresDAO dao = new FornecedoresDAO();
        } catch (SQLException e) {
            System.out.println("Erro ao criar resultSet. Erro : " + e.getMessage());
        }
        return produtosRS;
    }
}

