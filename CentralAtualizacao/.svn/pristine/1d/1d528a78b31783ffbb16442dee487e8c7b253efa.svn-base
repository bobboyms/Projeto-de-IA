package br.com.etico.arquitetura.formulario;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.primefaces.event.SelectEvent;

import br.com.etico.persistencia.EntidadePersistence;
import br.com.etico.persistencia.dao.GenericDAO;

/**
 * 
 * @author Thiago Luiz Rodrigues
 * 
 * @param <T>
 */
public abstract class Formulario<T> {

	private T entidade;
	private GenericDAO<T> daoPrincipal;
	private boolean estadoCadastrando = false;
	private boolean estadoEditando = false;
	private boolean estadoSelecionado = false;

	public abstract boolean checaRestricoes() throws Exception;

	public void checaRestricoesExcluir() throws Exception {}

	public abstract void limparTela();
	
	public abstract void inicializaDAO();
	
	public Formulario() {
		limparTela();
	}
	
	public boolean getRestricaoMensagem(String mensagem) {
		FacesContext.getCurrentInstance().addMessage("", new FacesMessage(
				FacesMessage.SEVERITY_WARN,
				mensagem,""));
		
		return false;
	}

	@SuppressWarnings("unchecked")
	public List<T> getTodos() {
		
		if (filtroModal() == null) {
			return getDaoPrincipal().getRegistros();
		}
		
		return filtroModal().list();
		
	}

	public FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}
	
	public void inserir() {


		try {

			if (isEstadoEditando()) {
				
				getDaoPrincipal().editar((EntidadePersistence)getEntidade()); 
				getFacesContext().addMessage("", new FacesMessage("Atualizado com sucesso"));

			} else {

				getDaoPrincipal().inserir((EntidadePersistence)getEntidade()); 
				getFacesContext().addMessage("", new FacesMessage("Inserido com sucesso"));
			}

		} catch (Exception e) {

			e.printStackTrace();

//			getDaoPrincipal().getSession().getTransaction().rollback();

			getFacesContext().addMessage("", new FacesMessage("Erro no processo! Comunique o suporte"));

		} 
//		finally {
//
//			getDaoPrincipal().close();
//
//		}

	}

	public void excluir() {

		FacesContext context = FacesContext.getCurrentInstance();

		try {

			getDaoPrincipal().deletar((EntidadePersistence)getEntidade());

//			getDaoPrincipal().getSession().flush();
//			getDaoPrincipal().getSession().clear();
//
//			getDaoPrincipal().deleteFisico(getEntidade());
//
//			getDaoPrincipal().commit();

			getFacesContext().addMessage("", new FacesMessage("Excluido com sucesso"));

		} catch (org.hibernate.exception.ConstraintViolationException e) {

			e.printStackTrace();

//			getDaoPrincipal().rollback();

			getFacesContext().addMessage(
					"",
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Registro não pode ser excluido! Existem registros que dependem desse!",
							""));

		} catch (Exception e) {

			e.printStackTrace();

//			getDaoPrincipal().rollback();

			getFacesContext().addMessage("", new FacesMessage(
					"Erro no processo! Comunique o suporte"));

		} 
//		finally {
//
//			getDaoPrincipal().close();
//
//		}

	}

	@SuppressWarnings("unchecked")
	public void iniciarAtualizar() {

		FacesContext context = FacesContext.getCurrentInstance();

		try {

			Criteria ct = getDaoPrincipal().getCriteria();

			ct.add(Restrictions.eq(EntidadePersistence.strId,
					((EntidadePersistence) getEntidade()).getId()));

			setEntidade((T) ct.uniqueResult());

		} catch (Exception e) {

			e.printStackTrace();

//			getDaoPrincipal().close();

			getFacesContext().addMessage("", new FacesMessage(
					"Erro no processo! Comunique o suporte"));

		} 
//		finally {
//
//			getDaoPrincipal().close();
//
//		}

	}

	@SuppressWarnings("unchecked")
	public void selecionar(Object rowData) {

		System.out.println("Selecionado");
		
		setEntidade((T) rowData);

	}

	public void onRowSelect(SelectEvent event) {

		FacesContext context = FacesContext.getCurrentInstance();

		try {

			setEstadoCadastrando(false);
			setEstadoEditando(false);
			setEstadoSelecionado(true);
			selecionar(event.getObject());

		} catch (Exception e) {

			e.printStackTrace();

			getFacesContext().addMessage("", new FacesMessage(
					"Erro no processo! Comunique o suporte"));

		}

	}

	public void iniciarCadastrar(ActionEvent ev) {
		limparTela();
		setEstadoCadastrando(true);
		setEstadoEditando(false);
		setEstadoSelecionado(false);
	}
	
	public void iniciarLimparTela(ActionEvent ev) {
		limparTela();
	}

	public void iniciarInserir(ActionEvent ev) throws Exception {
		
//		if (isEstadoCadastrando()) {
//			getValidar().processaValidacao(getEntidade(), EstadoFormulario.CADASTRANDO);
//		} else {
//			getValidar().processaValidacao(getEntidade(), EstadoFormulario.EDITANDO);
//		}
		
		if (checaRestricoes()) {
			
			inserir();
			setEstadoCadastrando(false);
			setEstadoEditando(false);
			setEstadoSelecionado(true);
			
		}
	}

	public void iniciarEditar(ActionEvent ev) {
		setEstadoEditando(true);
		setEstadoCadastrando(false);
		setEstadoSelecionado(false);
	}

	public void iniciarExcluir(ActionEvent ev) throws Exception {
		checaRestricoesExcluir();
		excluir();
		setEstadoCadastrando(false);
		setEstadoEditando(false);
		setEstadoSelecionado(false);
		limparTela();
	}

	public void cancelar(ActionEvent ev) {
		limparTela();
//		getDaoPrincipal().close();
		setEstadoCadastrando(false);
		setEstadoEditando(false);
		setEstadoSelecionado(false);
	}

	public boolean isCampoBloqueado() {

		return (!isEstadoCadastrando() && !isEstadoEditando());

	}

	public T getEntidade() {
		return entidade;
	}

	public void setEntidade(T entidade) {
		this.entidade = entidade;
	}

	public boolean isEstadoCadastrando() {
		return estadoCadastrando;
	}

	public void setEstadoCadastrando(boolean estadoCadastrando) {
		this.estadoCadastrando = estadoCadastrando;
	}

	public boolean isEstadoEditando() {
		return estadoEditando;
	}

	public void setEstadoEditando(boolean estadoEditando) {
		this.estadoEditando = estadoEditando;
	}

	public GenericDAO<T> getDaoPrincipal() {
		return daoPrincipal;
	}

	public void setDaoPrincipal(GenericDAO<T> daoPrincipal) {
		this.daoPrincipal = daoPrincipal;
	}

	public boolean isEstadoSelecionado() {
		return estadoSelecionado;
	}

	public void setEstadoSelecionado(boolean estadoSelecionado) {
		this.estadoSelecionado = estadoSelecionado;
	}

	protected void addMsgInfo(String msg, String detail) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, msg, detail));
	}

	protected void addMsgWarn(String msg, String detail) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_WARN, msg, detail));
	}

	protected void addMsgError(String msg, String detail) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, detail));
	}

	protected void addMsgFatal(String msg, String detail) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_FATAL, msg, detail));
	}


	public Criteria filtroModal() {
		return null;
	}

	public boolean isPermiteNovo() {

		
//		if (SessaoUsuario.getUsuarioConectado() != null) {
//
//			Criteria ct = new ModuloPerfilDAO().getCriteria();
//
//			ct.add(Restrictions.eq(ModulosPerfil.strModuloEmpresa,
//					SessaoUsuario.getModuloAtual()));
//			ct.add(Restrictions.eq(ModulosPerfil.strPerfilUsuario,
//					SessaoUsuario.getUsuarioConectado().getPerfilUsuario()));
//
//			if (((ModulosPerfil) ct.uniqueResult()) != null) {
//
//				return ((ModulosPerfil) ct.uniqueResult()).getIndCadastra()
//						&& (!isEstadoCadastrando() && !isEstadoEditando());
//
//			}
//			
//		}
//
//		return true && (!isEstadoCadastrando() && !isEstadoEditando());
		
		return true && (!isEstadoCadastrando() && !isEstadoEditando());

	}

	public boolean isPermiteEditar() {

//		if (SessaoUsuario.getUsuarioConectado() != null) {
//
//			Criteria ct = new ModuloPerfilDAO().getCriteria();
//
//			ct.add(Restrictions.eq(ModulosPerfil.strModuloEmpresa,
//					SessaoUsuario.getModuloAtual()));
//			ct.add(Restrictions.eq(ModulosPerfil.strPerfilUsuario,
//					SessaoUsuario.getUsuarioConectado().getPerfilUsuario()));
//
//			if (((ModulosPerfil) ct.uniqueResult()) != null) {
//				return ((ModulosPerfil) ct.uniqueResult()).getIndAtualiza()
//						&& (isEstadoSelecionado() && !isEstadoEditando());
//			}
//
//		}
//
//		return true && (isEstadoSelecionado() && !isEstadoEditando());
		
		return true && (isEstadoSelecionado() && !isEstadoEditando());

	}

	public boolean isPermiteExcluir() {
//
//		if (SessaoUsuario.getUsuarioConectado() != null) {
//
//			Criteria ct = new ModuloPerfilDAO().getCriteria();
//
//			ct.add(Restrictions.eq(ModulosPerfil.strModuloEmpresa,
//					SessaoUsuario.getModuloAtual()));
//			ct.add(Restrictions.eq(ModulosPerfil.strPerfilUsuario,
//					SessaoUsuario.getUsuarioConectado().getPerfilUsuario()));
//
//			if (((ModulosPerfil) ct.uniqueResult()) != null) {
//				return ((ModulosPerfil) ct.uniqueResult()).getIndExclui()
//						&& (isEstadoSelecionado() && !isEstadoEditando());
//			}
//
//		}
//
		
		return true && (isEstadoSelecionado() && !isEstadoEditando());
	}

	public boolean isPermiteBuscar() {

//		if (SessaoUsuario.getUsuarioConectado() != null) {
//
//			Criteria ct = new ModuloPerfilDAO().getCriteria();
//
//			ct.add(Restrictions.eq(ModulosPerfil.strModuloEmpresa,
//					SessaoUsuario.getModuloAtual()));
//			ct.add(Restrictions.eq(ModulosPerfil.strPerfilUsuario,
//					SessaoUsuario.getUsuarioConectado().getPerfilUsuario()));
//
//			if (((ModulosPerfil) ct.uniqueResult()) != null) {
//				return ((ModulosPerfil) ct.uniqueResult()).getIndBusca()
//						&& (!isEstadoCadastrando() && !isEstadoEditando());
//			}
//
//		}

		return true && (!isEstadoCadastrando() && !isEstadoEditando());

	}


}
