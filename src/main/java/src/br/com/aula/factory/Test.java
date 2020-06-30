package src.br.com.aula.factory;

import src.br.com.aula.dao.FornecedoresDAO;
import src.br.com.aula.domain.Fornecedores;
import src.br.com.aula.util.TratamentoErro;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Test {

    public static void main(String[] args) {
        try {
            Connection conexao = ConnectionFactory.getConnection();
            FornecedoresDAO dao = new FornecedoresDAO();

            Fornecedores novoFornecedor = new Fornecedores();
            Fornecedores novoFornecedor2 = new Fornecedores();
            novoFornecedor.setCodigo(34);
            novoFornecedor.setDescricao("Pão");
            novoFornecedor2.setDescricao("Queijo");
            novoFornecedor2.setCodigo(35);

            System.out.println("----- Teste Salvar Fornecedor -----");
            dao.salvar(novoFornecedor);
            dao.salvar(novoFornecedor2);
            System.out.println(novoFornecedor);
            System.out.println("------------------------------------\n");

            System.out.println("----- Teste Buscar Fornecedor -----");
            dao.buscarFornecedor(novoFornecedor);
            System.out.println(novoFornecedor);
            System.out.println("------------------------------------\n");

            System.out.println("----- Teste Buscar por descrição -----");
            dao.buscarPorDescricao(novoFornecedor);
            System.out.println(novoFornecedor);
            System.out.println("------------------------------------\n");

            System.out.println("----- Teste Buscar todos os Fornecedores -----");
            List<Fornecedores> lista = new ArrayList<>();
            lista = dao.buscarTodosFornecedores();
            for (Fornecedores x:lista) {
                System.out.println(x);
            }
            System.out.println("------------------------------------\n");

            System.out.println("----- Teste Editar Fornecedor -----");
            dao.editar(novoFornecedor);
            novoFornecedor.setDescricao("Coca-cola");
            System.out.println(novoFornecedor);
            System.out.println("------------------------------------\n");

            System.out.println("----- Teste Delete Fornecedor -----");
            dao.delete(novoFornecedor);
            System.out.println("------------------------------------\n");

        } catch (TratamentoErro tratamentoErro) {
            tratamentoErro.printStackTrace();
        }


    }
}
