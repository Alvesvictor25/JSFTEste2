package src.br.com.aula.bean;


import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import src.br.com.aula.dao.ProdutosDAO;
import src.br.com.aula.domain.Fornecedores;
import src.br.com.aula.domain.Produtos;
import src.br.com.aula.util.TratamentoErro;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;

@Named
@SessionScoped
public class ProdutosBean implements Serializable {
    private static final long serialVersionUID = 1L;

    List<Produtos> listaTodosProdutos = new ArrayList<Produtos>();

    @Inject
    ProdutosDAO produtosDAO;
    @Inject
    Produtos produtos;
    @Inject
    Produtos produtosEditado;

    public Produtos getProdutosEditado() {
        return produtosEditado;
    }

    public void setProdutosEditado(Produtos produtosEditado) {
        this.produtosEditado = produtosEditado;
    }

    public List<Produtos> getListaTodosProdutos() {
        return listaTodosProdutos;
    }

    public void setListaTodosProdutos(List<Produtos> listaTodosProdutos) {
        this.listaTodosProdutos = listaTodosProdutos;
    }

    public Produtos getProdutos() {
        return produtos;
    }

    public void setProdutos(Produtos produtos) {
        this.produtos = produtos;
    }

    @PostConstruct
    public void init() {
        try {
            listaTodosProdutos = produtosDAO.buscarTodosProdutos();
        } catch (TratamentoErro e) {
            System.out.println("Erro ao iniciar ao buscar fornecedores");
        }

    }

    public void adicionar() {
        listaTodosProdutos.add(produtos);
        try {
            produtosDAO.salvar(this.produtos);
            adicionarMensagem("", "Fornecedor salvo com sucesso!", FacesMessage.SEVERITY_INFO);
            produtos = new Produtos();
        } catch (TratamentoErro e) {
            adicionarMensagem(e.getMessage(), e.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }

    }

    public void delete(Produtos produtos) {
        try {
            produtosDAO.delete(produtos);
            adicionarMensagem("Deletado!", "Produto deletado com sucesso!", FacesMessage.SEVERITY_INFO);
            listaTodosProdutos.remove(produtos);
            produtos = new Produtos();
        } catch (TratamentoErro e) {
            adicionarMensagem(e.getMessage(), e.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void listar() {
        try {
            this.listaTodosProdutos = produtosDAO.buscarTodosProdutos();
            if (listaTodosProdutos == null || listaTodosProdutos.size() == 0) {
                adicionarMensagem("Nenhum dado encontrado!", "Sua busca não retornou nenhum fornecedor.", FacesMessage.SEVERITY_WARN);
            }
            adicionarMensagem("Salvo!", "Fornecedor salvo com sucesso!", FacesMessage.SEVERITY_INFO);
        } catch (TratamentoErro e) {
            adicionarMensagem(e.getMessage(), e.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void editar(Produtos c) {
        produtos = c;
        try {
            produtosDAO.editar(produtos);
            adicionarMensagem("Editado!", "Fornecedor editado com sucesso!", FacesMessage.SEVERITY_INFO);
        } catch (TratamentoErro e) {
            adicionarMensagem(e.getMessage(), e.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }


    public void adicionarMensagem(String mensagem, String sumario, FacesMessage.Severity tipoErro) {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage facesMessage = new FacesMessage(tipoErro, sumario, mensagem);
        context.addMessage(null, facesMessage);

    }

    public void abrirDialog() {
        Map<String, Object> dialog = new HashMap<>();

        dialog.put("draggable", false);
        dialog.put("resizable", false);

        RequestContext.getCurrentInstance().openDialog("selecionarFornecedor", dialog, null);
    }

    public void fornecedorSelecionado(SelectEvent event) {
        Fornecedores fornecedor = (Fornecedores) event.getObject();
        produtos.setFornecedores(fornecedor);
    }

    public void abrirDialogEditar(Produtos produtos) {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("draggable", false);
        opcoes.put("contentHeight", 300);
        opcoes.put("contentWidth", 400);

        produtosEditado.setCodigo(produtos.getCodigo());
        produtosEditado.setDescricao(produtos.getDescricao());
        produtosEditado.setQuantidade(produtos.getQuantidade());
        produtosEditado.setValor(produtos.getValor());
        produtosEditado.setFornecedores(produtos.getFornecedores());

        Map<String, List<String>> params = new HashMap<>();
        params.put("meuParametro", Arrays.asList("" + produtos.getCodigo()));

        RequestContext.getCurrentInstance().openDialog("editarproduto", opcoes, params);
    }

    public void fecharDialog() {
        RequestContext.getCurrentInstance().closeDialog(null);
    }
}

