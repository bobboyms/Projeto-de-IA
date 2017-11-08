package br.com.etico.controle;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.primefaces.event.SelectEvent;

import br.com.etico.arquitetura.formulario.Formulario;
import br.com.etico.arquitetura.formulario.LOV;
import br.com.etico.arquitetura.formulario.ListaLOV;
import br.com.etico.arquitetura.formulario.TemLOV;
import br.com.etico.arquitetura.formulario.TemListaLOV;
import br.com.etico.ir.utils.Utils;
import br.com.etico.modelo.beans.Empresa;
import br.com.etico.modelo.beans.EmpresaUsuario;
import br.com.etico.modelo.beans.Perfil;
import br.com.etico.modelo.beans.Usuario;
import br.com.etico.persistencia.dao.EmpresaDAO;
import br.com.etico.persistencia.dao.EmpresaUsuarioDAO;
import br.com.etico.persistencia.dao.GenericDAO;
import br.com.etico.persistencia.dao.SetorDAO;
import br.com.etico.persistencia.dao.UsuarioDAO;
import br.com.etico.persistencia.jpa.JPAUtil;

@Named
@SessionScoped
@SuppressWarnings("serial")
public class UsuarioForm extends Formulario<Usuario>implements TemLOV, TemListaLOV, Serializable {

	@Inject
	private UsuarioDAO usuarioDAO;

	@Inject
	private SetorDAO perfilDAO;

	@Inject
	private EmpresaDAO empresaDAO;

	@Inject
	private EmpresaUsuarioDAO empresaUsuarioDAO;

	private final LOV<Perfil> lovPerfil = new LOV<>(this, Perfil.class);
	private final LOV<Empresa> lovEmpresa = new LOV<>(this, Empresa.class);
	private final ListaLOV<Empresa> listaLovEmpresa = new ListaLOV<>(this, Empresa.class);

	@Override
	public void onRowSelect(SelectEvent event) {

		super.onRowSelect(event);

		try {

			@SuppressWarnings("unchecked")
			List<EmpresaUsuario> empresaUsuarios = getEmpresaUsuarioDAO().getCriteria()
					.createCriteria(EmpresaUsuario.strUsuario)
					.add(Restrictions.eq(Usuario.strId, getEntidade().getId())).list();

			getListaLovEmpresa().getListaDeRegistros().clear();

			for (EmpresaUsuario empresaUsuario : empresaUsuarios) {

				getListaLovEmpresa().getListaDeRegistros().add(empresaUsuario.getEmpresa());

			}

		} catch (Exception e) {
			addMsgError("ERRO NO PROCESSO", "comunique o suporte");
		}

	}

	@PostConstruct
	public void inicializaDAO() {

		setDaoPrincipal(getUsuarioDAO());
		getLovEmpresa().setDaoPrincipal(getEmpresaDAO());
		getLovPerfil().setDaoPrincipal(getPerfilDAO());

		// lista LOV's
		getListaLovEmpresa().setDaoPrincipal(getEmpresaDAO());

	}

	@Override
	public void iniciarCadastrar(ActionEvent ev) {
		getListaLovEmpresa().getListaDeRegistros().clear();
		super.iniciarCadastrar(ev);
	}

	public boolean isPerfilCliente() {
		
		if (getEntidade().getPerfil() != null && getEntidade().getPerfil().getIndTipoSetor().equals(Perfil.strIndTipoSetor_CLIENTE)) {
			return true;
		}
		
		return false;
		
	}
	
	@Override
	public void inserir() {

		EntityManager entityManager = null;
		EntityTransaction tx = null;

		if (getEntidade().getPerfil().getIndTipoSetor().equals(Perfil.strIndTipoSetor_CLIENTE)) {
			getEntidade().setPermiteEmpresa(false);
			getEntidade().setPermiteUsuario(false);
		}
		
		if (isEstadoCadastrando()) {

			try {

				entityManager = JPAUtil.getEntityManager();

				tx = entityManager.getTransaction();
				tx.begin();

				entityManager.persist(getEntidade());

				if (getEntidade().getPerfil().getIndTipoSetor().equals(Perfil.strIndTipoSetor_CLIENTE)) {
					
					getListaLovEmpresa().getListaDeRegistros().clear();
					getListaLovEmpresa().getListaDeRegistros().add(getEntidade().getEmpresa());
					
				}
				
				// pega da lista LOV e cadastra em Empresas do usuario
				for (Empresa empresa : getListaLovEmpresa().getListaDeRegistros()) {

					EmpresaUsuario empresaUsuario = new EmpresaUsuario();
					empresaUsuario.setUsuario(getEntidade());
					empresaUsuario.setEmpresa(empresa);

					entityManager.persist(empresaUsuario);

				}

				////
				tx.commit();

				addMsgInfo("SALVO", "registro salvo com sucesso");

			} catch (Exception e) {
				tx.rollback();
				e.printStackTrace();
				addMsgError("ERRO NO PROCESSO", "Comunique o suporte");

			} finally {
				entityManager.close();
			}

		} else {
			
			////////////// ATUALIZANDO REGISTRO ///////////////
			
			try {

				entityManager = JPAUtil.getEntityManager();

				tx = entityManager.getTransaction();
				tx.begin();

				entityManager.merge(getEntidade());
				
				// pega da lista LOV e remove
				for (Empresa empresa : getListaLovEmpresa().getListaDeRegistrosRemovidos()) {
					
				 	EmpresaUsuario empresaUsuario = (EmpresaUsuario) getEmpresaUsuarioDAO().getCriteria()
						.add(Restrictions.eq(EmpresaUsuario.strEmpresa, empresa))
						.add(Restrictions.eq(EmpresaUsuario.strUsuario, getEntidade())).uniqueResult();
					
				 	if (empresaUsuario != null) {
				 		
				 		EmpresaUsuario entidadePersistence = entityManager.find(EmpresaUsuario.class, empresaUsuario.getId());
				 		
				 		entityManager.remove(entidadePersistence);
				 		
				 	}
					
				}
				
				getListaLovEmpresa().getListaDeRegistrosRemovidos().clear();

				
				
				// pega da lista LOV e cadastra em Empresas do usuario
				for (Empresa empresa : getListaLovEmpresa().getListaDeRegistros()) {

					EmpresaUsuario empresaUsua= (EmpresaUsuario) getEmpresaUsuarioDAO().getCriteria()
							.add(Restrictions.eq(EmpresaUsuario.strEmpresa, empresa))
							.add(Restrictions.eq(EmpresaUsuario.strUsuario, getEntidade())).uniqueResult();
					
					System.out.println("EMPRESA : " + empresa);
					
					if (empresaUsua == null) {
						EmpresaUsuario empresaUsuario = new EmpresaUsuario();
						empresaUsuario.setUsuario(getEntidade());
						empresaUsuario.setEmpresa(empresa);
						
						entityManager.persist(empresaUsuario);
					}
					

				}

				////
				tx.commit();
				
				getListaLovEmpresa().getListaDeRegistrosRemovidos().clear();

				addMsgInfo("ATUALIZADO", "registro atualizado com sucesso");

			} catch (Exception e) {
				tx.rollback();
				e.printStackTrace();
				addMsgError("ERRO NO PROCESSO", "Comunique o suporte");

			} finally {
				entityManager.close();
			}
			
		}
	}

	

	@Override
	public boolean checaRestricoes() throws Exception {
		
		if (Utils.isStringVazia(getEntidade().getNomeUsuario())) {
			return getRestricaoMensagem("Escreva o nome completo do usuario");
		}
		
		if (Utils.isStringVazia(getEntidade().getLogin())) {
			return getRestricaoMensagem("Escreva o login");
		}
		
		if (Utils.isStringVazia(getEntidade().getSenha())) {
			return getRestricaoMensagem("Escreva o senha");
		}
		
		if (!Utils.isEmailValido(getEntidade().getEmail())) {
			return getRestricaoMensagem("Escreva um email valido");
		}
		
		if (getEntidade().getEmpresa() == null) {
			return getRestricaoMensagem("Selecione a empresa que o usuario trabalha");
		}
		
		if (getEntidade().getPerfil() == null) {
			return getRestricaoMensagem("Selecione o perfil do usuario");
		}
		
		
		//
		// if (getEntidade().getNome() == null ||
		// getEntidade().getNome().trim().isEmpty()) {
		// return getRestricaoMensagem("Nome invalido");
		// }
		//
		// if (getEntidade().getClienteTeste() == null) {
		// return getRestricaoMensagem("Selecione o cliente");
		// }

		return true;

	}

	// **********************************************//
	// //
	// LOVS //
	// //
	// **********************************************//

	@Override
	public Criteria filtroNaLov(LOV<?> lov, GenericDAO<?> daoPrincipal) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void preencheCampoLov(Object object, LOV<?> lov) {

		if (lov == getLovEmpresa()) {
			getEntidade().setEmpresa((Empresa) object);
		}

		if (lov == getLovPerfil()) {
			getEntidade().setPerfil((Perfil) object);
		}

	}

	@Override
	public void removeCampoLov(LOV<?> lov) {

		if (lov == getLovEmpresa()) {
			getEntidade().setEmpresa(null);
		}

		if (lov == getLovPerfil()) {
			getEntidade().setPerfil(null);
		}

	}

	// ********************** FIM LOV ********************//

	// ****************** LISTA LOV **********************//
	@Override
	public Criteria filtroNaLov(ListaLOV<?> listaLOV, GenericDAO<?> daoPrincipal) {
		return null;
	}

	@Override
	public void preencheCampoLov(Object object, ListaLOV<?> lov) {

		// if (lov == getListaLovEmpresa()) {
		//
		// getListaLovEmpresa().getListaDeRegistros().add((Empresa) object);
		//
		// }

	}

	@Override
	public void removeCampoLov(ListaLOV<?> lov, Object object) {

		// if (lov == getListaLovEmpresa()) {
		//
		// getListaLovEmpresa().getListaDeRegistros().remove((Empresa)object);
		//
		// }

	}

	// ******************** FIM LISTA LOV ****************//

	@Override
	public void limparTela() {
		setEntidade(new Usuario());
	}

	public UsuarioDAO getUsuarioDAO() {
		return usuarioDAO;
	}

	public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}

	public LOV<Empresa> getLovEmpresa() {
		return lovEmpresa;
	}

	public EmpresaDAO getEmpresaDAO() {
		return empresaDAO;
	}

	public void setEmpresaDAO(EmpresaDAO empresaDAO) {
		this.empresaDAO = empresaDAO;
	}

	public SetorDAO getPerfilDAO() {
		return perfilDAO;
	}

	public void setPerfilDAO(SetorDAO perfilDAO) {
		this.perfilDAO = perfilDAO;
	}

	public LOV<Perfil> getLovPerfil() {
		return lovPerfil;
	}

	public EmpresaUsuarioDAO getEmpresaUsuarioDAO() {
		return empresaUsuarioDAO;
	}

	public void setEmpresaUsuarioDAO(EmpresaUsuarioDAO empresaUsuarioDAO) {
		this.empresaUsuarioDAO = empresaUsuarioDAO;
	}

	public ListaLOV<Empresa> getListaLovEmpresa() {
		return listaLovEmpresa;
	}

}
