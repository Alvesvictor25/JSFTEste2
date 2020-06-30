package src.br.com.aula.bean;

import org.primefaces.context.RequestContext;
import src.br.com.aula.dao.FornecedoresDAO;
import src.br.com.aula.domain.Fornecedores;
import src.br.com.aula.util.TratamentoErro;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.*;

@Named
@SessionScoped
public class FornecedoresBean implements Serializable {
    private static final long serialVersionUID = 1L;

    List<Fornecedores> listaTodosOsFornecedores = new ArrayList<Fornecedores>();
    List<Fornecedores> fornecedoresFiltrados = new ArrayList<>();
    private String pesquisaDescricao;
    @Inject
    FornecedoresDAO fornecedoresDAO;
    @Inject
    Fornecedores fornecedores;
    @Inject
    Fornecedores fornecedoresEditado;


    @PostConstruct
    public void init(){
        try{

            listaTodosOsFornecedores = fornecedoresDAO.buscarTodosFornecedores();
        }catch (TratamentoErro e){
            System.out.println("Erro ao iniciar ao buscar fornecedores");
        }

    }

    public Fornecedores getFornecedoresEditado() {
        return fornecedoresEditado;
    }

    public void setFornecedoresEditado(Fornecedores fornecedoresEditado) {
        this.fornecedoresEditado = fornecedoresEditado;
    }

    public String getPesquisaDescricao() {
        return pesquisaDescricao;
    }

    public void setPesquisaDescricao(String pesquisaDescricao) {
        this.pesquisaDescricao = pesquisaDescricao;
    }
    public Fornecedores getFornecedores() {
        return fornecedores;
    }

    public void setFornecedores(Fornecedores fornecedores) {
        this.fornecedores = fornecedores;
    }

    public List<Fornecedores> getListaTodosOsFornecedores() {
        return listaTodosOsFornecedores;
    }

    public void setListaTodosOsFornecedores(List<Fornecedores> listaTodosOsFornecedores) {
        this.listaTodosOsFornecedores = listaTodosOsFornecedores;
    }

    public void adicionar() {
        listaTodosOsFornecedores.add(fornecedores);
        try {
            int id = fornecedoresDAO.salvar(this.fornecedores);
            fornecedores.setCodigo(id);
            adicionarMensagem("Salvo!","Fornecedor salvo com sucesso!",FacesMessage.SEVERITY_INFO );
            fornecedores = new Fornecedores();
        } catch (TratamentoErro e) {
            adicionarMensagem(e.getMessage(),e.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }

    }

    public void delete(Fornecedores fornecedores) {
        try {
            fornecedoresDAO.delete(fornecedores);
            adicionarMensagem("Deletado!","Fornecedor deletado com sucesso!",FacesMessage.SEVERITY_INFO );
            listaTodosOsFornecedores.remove(fornecedores);
            fornecedores = new Fornecedores();
        } catch (TratamentoErro e) {
            adicionarMensagem("Erro ao deletar fornecedor. ","Fornecedor associado a produto.", FacesMessage.SEVERITY_ERROR);
        }
    }

    public void listar() {
        try {
            this.listaTodosOsFornecedores = fornecedoresDAO.buscarTodosFornecedores();
            if(listaTodosOsFornecedores == null || listaTodosOsFornecedores.size() == 0) {
                adicionarMensagem("Nenhum dado encontrado!","Sua busca não retornou nenhum fornecedor.",FacesMessage.SEVERITY_WARN );
            }
            adicionarMensagem("Salvo!","Fornecedor salvo com sucesso!",FacesMessage.SEVERITY_INFO );
        } catch (TratamentoErro e) {
            adicionarMensagem(e.getMessage(),e.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void editar() {
        try {
            fornecedoresDAO.editar(fornecedoresEditado);
            adicionarMensagem("Editado!","Fornecedor editado com sucesso!",FacesMessage.SEVERITY_INFO );
            RequestContext.getCurrentInstance().update("frmfornecedores:tbfornecedores");
        } catch (TratamentoErro e) {
            adicionarMensagem(e.getMessage(),e.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void adicionarMensagem(String mensagem, String sumario, FacesMessage.Severity tipoErro) {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage facesMessage = new FacesMessage(tipoErro,sumario,mensagem);
        context.addMessage(null, facesMessage);

    }

    public void abrirDialog(){
        Map<String,Object> dialog = new HashMap<>();
        dialog.put("modal",true);
        dialog.put("draggable", false);
        dialog.put("resizable",false);

        RequestContext.getCurrentInstance().openDialog("editarfornecedor", dialog, null);
    }


    public void selecionar(Fornecedores fornecedores){
            RequestContext.getCurrentInstance().closeDialog(fornecedores);
    }

    public void pesquisar(){
        try {
            listaTodosOsFornecedores = fornecedoresDAO.buscarPorDescricaoString(pesquisaDescricao);
            pesquisaDescricao = "";
        } catch (TratamentoErro tratamentoErro) {
            tratamentoErro.printStackTrace();
        }
    }

    public void abrirDialogEditar(Fornecedores fornecedores){
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("draggable", false);
        opcoes.put("contentHeight", 110);
        opcoes.put("contentWidth", 250);

        fornecedoresEditado.setCodigo(fornecedores.getCodigo());
        fornecedoresEditado.setDescricao(fornecedores.getDescricao());

        Map<String, List<String>> params = new HashMap<>();
        params.put("meuParametro", Arrays.asList(""+fornecedores.getCodigo()));

        RequestContext.getCurrentInstance().openDialog("editarfornecedor", opcoes, params);
    }

    public void fecharDialog(){
        RequestContext.getCurrentInstance().closeDialog(null);
    }
}
