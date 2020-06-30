package src.br.com.aula.dao;

import src.br.com.aula.domain.Fornecedores;
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

public class FornecedoresDAO implements Serializable {
    private static final long serialVersionUID = 1L;

    public int salvar(Fornecedores fornecedores) throws TratamentoErro {
        Connection conn = ConnectionFactory.getConnection();
        String sql = "INSERT INTO tb_fornecedor(descricao) values (?)";
        int idGerado = 0;
        try {
            PreparedStatement stmt = ConnectionFactory.getPreparedStatement(conn, sql, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, fornecedores.getDescricao());

            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();

            if (rs.next()) {
                idGerado = rs.getInt(1);
                fornecedores.setCodigo(idGerado);
            }
        } catch (SQLException e) {
            throw new TratamentoErro("Erro ao salvar fornecedor.", e);
        }
        return idGerado;
    }

    public void delete(Fornecedores fornecedores) throws TratamentoErro {
        Connection conn = ConnectionFactory.getConnection();
        String sql = "DELETE FROM tb_fornecedor WHERE codigo = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, fornecedores.getCodigo());
            stmt.executeUpdate();
            System.out.println("Deletado");
        } catch (SQLException e) {
            throw new TratamentoErro("Erro ao deletar fornecedor. Erro: ", e);
        }
    }

    public void editar(Fornecedores fornecedores) throws TratamentoErro {
        Connection conn = ConnectionFactory.getConnection();
        String sql = "UPDATE tb_fornecedor SET descricao = ? WHERE codigo = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, fornecedores.getDescricao());
            stmt.setInt(2, fornecedores.getCodigo());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new TratamentoErro("Erro ao editar fornecedor. Erro: ", e);
        }
    }

    public Fornecedores buscarFornecedor(Fornecedores fornecedores) throws TratamentoErro {
        Connection conn = ConnectionFactory.getConnection();
        String sql = "SELECT codigo, descricao FROM tb_fornecedor WHERE codigo = ?";
        ResultSet rs = null;
        Fornecedores buscaFornecedor = new Fornecedores();
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

    public Fornecedores buscarFornecedor(int id) throws TratamentoErro {
        Connection conn = ConnectionFactory.getConnection();
        String sql = "SELECT codigo, descricao FROM tb_fornecedor WHERE codigo = ?";
        ResultSet rs = null;
        Fornecedores buscaFornecedor = new Fornecedores();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                buscaFornecedor = criarResultSet(rs);
            }
        } catch (SQLException e) {
            throw new TratamentoErro("Erro busca de fornecedor. Erro: ", e);
        }
        return buscaFornecedor;
    }

    public List<Fornecedores> buscarTodosFornecedores() throws TratamentoErro {
        Connection conn = ConnectionFactory.getConnection();
        String sql = "SELECT * FROM tb_fornecedor";
        List<Fornecedores> listaFornecedores = new ArrayList<Fornecedores>();
        ResultSet rs = null;
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Fornecedores resultSet = criarResultSet(rs);
                listaFornecedores.add(resultSet);

            }

        } catch (SQLException e) {
            throw new TratamentoErro("Erro ao buscar todos os fornecedores. Erro: ", e);
        }

        return listaFornecedores;
    }

    public List<Fornecedores> buscarPorDescricao(Fornecedores fornecedores) throws TratamentoErro {
        Connection conn = ConnectionFactory.getConnection();
        String sql = "SELECT codigo, descricao FROM tb_fornecedor WHERE descricao LIKE ?";
        List<Fornecedores> listaDescricao = new ArrayList<>();
        ResultSet rs = null;
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + fornecedores.getDescricao() + "%");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Fornecedores resultDescricao = criarResultSet(rs);
                listaDescricao.add(resultDescricao);
                listaDescricao.sort(Comparator.comparing(Fornecedores::getDescricao));
            }

        } catch (SQLException e) {
            throw new TratamentoErro("Erro ao buscar todas as descrições. Erro: ", e);
        }
        return listaDescricao;
    }
    public List<Fornecedores> buscarPorDescricaoString(String desc) throws TratamentoErro {
        Connection conn = ConnectionFactory.getConnection();
        String sql = "SELECT codigo, descricao FROM tb_fornecedor WHERE descricao LIKE ?";
        List<Fornecedores> listaDescricao = new ArrayList<>();
        ResultSet rs = null;
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + desc + "%");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Fornecedores resultDescricao = criarResultSet(rs);
                listaDescricao.add(resultDescricao);
                listaDescricao.sort(Comparator.comparing(Fornecedores::getDescricao));
            }

        } catch (SQLException e) {
            throw new TratamentoErro("Erro ao buscar todas as descrições. Erro: ", e);
        }
        return listaDescricao;
    }

    private Fornecedores criarResultSet(ResultSet rs) {
        Fornecedores fornecedoresResult = new Fornecedores();

        try {
            fornecedoresResult.setCodigo(rs.getInt("codigo"));
            fornecedoresResult.setDescricao(rs.getString("descricao"));
        } catch (SQLException e) {
            System.out.println("Erro ao criar resultSet. Erro : " + e.getMessage());
        }
        return fornecedoresResult;
    }
}

