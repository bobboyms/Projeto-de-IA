package br.com.etico.controle;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import br.com.etico.arquitetura.formulario.Formulario;
import br.com.etico.arquitetura.formulario.util.SessaoUsuarioManager;
import br.com.etico.arquitetura.formulario.util.criptografia.ControlaChaveAcesso;
import br.com.etico.ir.utils.Utils;
import br.com.etico.modelo.beans.AnexoMensagemDoChamado;
import br.com.etico.modelo.beans.Chamado;
import br.com.etico.modelo.beans.Empresa;
import br.com.etico.modelo.beans.EmpresaUsuario;
import br.com.etico.modelo.beans.MensagemDoChamado;
import br.com.etico.modelo.beans.Perfil;
import br.com.etico.modelo.beans.Usuario;
import br.com.etico.persistencia.dao.AnexoMensagemDoChamadoDAO;
import br.com.etico.persistencia.dao.ChamadoDAO;
import br.com.etico.persistencia.dao.MensagemDoChamadoDAO;
import br.com.etico.persistencia.dao.SetorDAO;
import br.com.etico.persistencia.dao.UsuarioDAO;
import br.com.etico.persistencia.jpa.JPAUtil;
import org.junit.*;

@Named
@SessionScoped
public class ChamadoForm extends Formulario<Chamado> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private SessaoUsuarioManager sessaoUsuarioManager;
	
	@Inject
	private ChamadoDAO chamadoDAO;
	
	@Inject
	private MensagemDoChamadoDAO mensagemDoChamadoDAO;
	
	@Inject
	private AnexoMensagemDoChamadoDAO anexoMensagemDoChamadoDAO;
	
	@Inject
	private SetorDAO setorDAO;
	
	@Inject
	private UsuarioDAO usuarioDAO;
	
	private MensagemDoChamado mensagemDoChamado;
	
	private boolean respondendoChamado = false;
	
	@PostConstruct
	public void inicializaDAO() {
		
//		System.out.println("XXXXXXXXXXXXXXXXXXXXX");
//		
//		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
//		
//		String key = (String) request.getParameter("key");
//		
//		System.out.println("KEY EM CHAMADO : " + key);
		
		setDaoPrincipal(getChamadoDAO());
		
	}
	
	public String getChamadoExterno() {
		
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		
		String key = (String) request.getParameter("key");
		
//		if (getSessaoUsuarioManager().getUsuarioMemoria().getChamadoExterno() != null) {
//			
//			Chamado chamado = (Chamado) getChamadoDAO().getCriteria().add(Restrictions.eq(Chamado.strId, getSessaoUsuarioManager().getUsuarioMemoria().getChamadoExterno().getId())).uniqueResult();
//			
//			setEntidade(chamado);
//			setEstadoSelecionado(true);
//			
//		}
		
		if (key != null) {

			String[] valores = ControlaChaveAcesso.informaçoesDecriptografada(key);
			
			Chamado chamadoExterno = (Chamado) JPAUtil.getSession().createCriteria(Chamado.class)
					.add(Restrictions.eq(Chamado.strId, new Long(valores[2]))).uniqueResult();
			
			setEntidade(chamadoExterno);
			setEstadoSelecionado(true);
			
		}
		
		
		return null;
	}

	private List<SelectItem> itemsStatus = new ArrayList<>();
	
	public List<SelectItem> getItemsStatus() {
		
		itemsStatus.clear();
		
		if (getEntidade().getId() == null) {
			itemsStatus.add(new SelectItem("AB", "ABERTO"));
		} else {
			itemsStatus.add(new SelectItem("AN", "ANDAMENTO"));
			itemsStatus.add(new SelectItem("FE", "FECHADO"));
		}
		
		
		return itemsStatus;
	}
	
	public boolean isFechado() {
		
		if (getEntidade() ==null || getEntidade().getIndStatus() == null) {
			return false;
		}
		
		if (getEntidade().getIndStatus().equals("FE")) {
			return true;
		}
		
		return false;
		
	}
	
	private List<SelectItem> itemsAvaliacao = new ArrayList<>();
	
	public List<SelectItem> getItemsAvaliacao() {
		
		itemsStatus.clear();
		
		itemsStatus.add(new SelectItem("NE", "NENHUMA"));
		itemsStatus.add(new SelectItem("OT", "OTIMO"));
		itemsStatus.add(new SelectItem("SA", "SATISFATÓRIO"));
		itemsStatus.add(new SelectItem("RU", "RUIM"));
		itemsStatus.add(new SelectItem("PE", "PÉSSIMO"));
		
		return itemsStatus;
	}
	
	public List<SelectItem> getItemsStatusFiltro() {
		
		itemsStatus.clear();
		itemsStatus.add(new SelectItem("ABERTO", "ABERTO"));
		itemsStatus.add(new SelectItem("ANDAMENTO", "ANDAMENTO"));
		itemsStatus.add(new SelectItem("FECHADO", "FECHADO"));

		return itemsStatus;
	}
	
	@SuppressWarnings("unchecked")
	public List<MensagemDoChamado> getMensagens() {
		
		if (getEntidade() == null || getEntidade().getId() ==null || getEntidade().getId() == 0l) return null;
		
		return getMensagemDoChamadoDAO().getCriteria()
				.add(Restrictions.eq(MensagemDoChamado.strChamado, getEntidade()))
				.addOrder(Order.desc(MensagemDoChamado.strId))
				.list();
	}
	
	public void iniciarResponder(ActionEvent ev) {
		setRespondendoChamado(true);
		setMensagemDoChamado(new MensagemDoChamado());
		getMensagemDoChamado().setChamado(getEntidade());
		setAnexoMensagemDoChamados(new ArrayList<>());
		iniciarEditar(ev);
	}
	
	@Override
	@SuppressWarnings({ "unchecked" })
	public Criteria filtroModal() {
		
		Session session = getDaoPrincipal().getSession();
		
		Criteria ctEmpresaUsuario = session.createCriteria(EmpresaUsuario.class);
		List<EmpresaUsuario> empresaUsuarios = ctEmpresaUsuario
				.add(Restrictions.eq(EmpresaUsuario.strUsuario, getSessaoUsuarioManager().getUsuarioMemoria())).list();
		
		List<Empresa> empresas = new ArrayList<>();
		
		for (EmpresaUsuario registro : empresaUsuarios) {
			empresas.add(registro.getEmpresa());
		}
		
		if (getSessaoUsuarioManager().getUsuarioMemoria().getPerfil().getIndTipoSetor().equals(Perfil.strIndTipoSetor_SUPORTE)) {
			
			return getDaoPrincipal().getCriteria()
					.add(Restrictions.in(Usuario.strEmpresa, empresas));
			
		} else {

			return getDaoPrincipal().getCriteria()
					.add(Restrictions.eq(Chamado.strUsuarioQueAbriu, getSessaoUsuarioManager().getUsuarioMemoria()))
					.add(Restrictions.in(Usuario.strEmpresa, empresas));
			
		}
		
	}
  	
	@Override
	public void inserir() {
		
		EntityManager entityManager = null;
		EntityTransaction tx = null;
		
		if (getSessaoUsuarioManager().getUsuarioMemoria().getPerfil().getIndTipoSetor().equals(Perfil.strIndTipoSetor_SUPORTE)) {
			getEntidade().setIndStatusRespostaChamado("PC");
			getEntidade().setSetorAtual(getSessaoUsuarioManager().getUsuarioMemoria().getPerfil());
		} else
		if (getSessaoUsuarioManager().getUsuarioMemoria().getPerfil().getIndTipoSetor().equals(Perfil.strIndTipoSetor_CLIENTE)) {
			getEntidade().setIndStatusRespostaChamado("PE");
			
			Perfil setor = (Perfil) getSetorDAO().getCriteria()
			.add(Restrictions.eq(Perfil.strIndTipoSetor, Perfil.strIndTipoSetor_SUPORTE)).uniqueResult();
			getEntidade().setSetorAtual(setor);
		}
		
		//Data e hora da resposta
		getMensagemDoChamado().setDthResposta(new Date());
		getMensagemDoChamado().setUsuarioQueRespondeu(getSessaoUsuarioManager().getUsuarioMemoria());
		
		
		if (isEstadoCadastrando()) {
			
			//seta da empresa do usuario no chamado
			getEntidade().setEmpresa(getSessaoUsuarioManager().getUsuarioMemoria().getEmpresa());
			
			getEntidade().setValoresPadroes();
			getEntidade().setUsuarioQueAbriu(getSessaoUsuarioManager().getUsuarioMemoria());
			
			try {
				
				entityManager = JPAUtil.getEntityManager();
				tx = entityManager.getTransaction();
				
				tx.begin();
				
				entityManager.persist(getEntidade());
				getMensagemDoChamado().setChamado(getEntidade());
				
				if (getAnexoMensagemDoChamados().size() > 0) {
					getMensagemDoChamado().setPossuiAnexo(true);
				}
				
				System.out.println("MENSSAGEM : " + getMensagemDoChamado().getMensagem());
				
				entityManager.persist(getMensagemDoChamado());
				
				for (AnexoMensagemDoChamado anexo : getAnexoMensagemDoChamados()) {
					anexo.setMensagemDoChamado(getMensagemDoChamado());
					entityManager.persist(anexo);
				}
				
				if (getSessaoUsuarioManager().getUsuarioMemoria().getPerfil().getIndTipoSetor().equals(Perfil.strIndTipoSetor_CLIENTE)) {
					
					Chamado chamado = entityManager.find(Chamado.class, getEntidade().getId());
					
					Usuario usuario = chamado.getUsuarioQueAbriu();
					
					Session session = (Session)entityManager.getDelegate();
					
					List<EmpresaUsuario> empresaUsuarios = session.createCriteria(EmpresaUsuario.class)
						.add(Restrictions.eq(EmpresaUsuario.strEmpresa, getEntidade().getEmpresa())).list();
					
					List<String> eMails = new ArrayList<>();
					
					for (EmpresaUsuario empresaUsuario : empresaUsuarios) {
						eMails.add(empresaUsuario.getUsuario().getEmail());
					}
					
					//remove o email do usuario que está respondendo para não receber email
					eMails.remove(getEntidade().getUsuarioQueAbriu().getEmail());
					
//					ControlaChaveAcesso.enviaChaveDeAcessoCliente(usuario, getEntidade(), Perfil.strIndTipoSetor_SUPORTE,eMails);
					
				}
				
				tx.commit();
				
				addMsgInfo("INSERIDO", "Chamado cadastrado com sucesso");
				setRespondendoChamado(false);
				
			} catch (Exception e) {
				tx.rollback();
				e.printStackTrace();
				addMsgError("ERRO NO PROCESSO", "Comunique o suporte");
			} finally {
				entityManager.close();
			}
			
		} else {
			
			if (isRespondendoChamado()) {
				
				try {
					
					entityManager = JPAUtil.getEntityManager();
					tx = entityManager.getTransaction();
					
					tx.begin();
					
					//atualiza a entidade principal
					getEntidade().setIndStatus("AN");
					entityManager.merge(getEntidade());
					
					MensagemDoChamado mensagemDoChamadoAnterior = (MensagemDoChamado) getMensagemDoChamadoDAO().getCriteria().add(Restrictions.eq(MensagemDoChamado.strChamado, getEntidade()))
							.addOrder(Order.desc(MensagemDoChamado.strId)).list().get(0);
					
					mensagemDoChamadoAnterior.setRespondido(true);
					entityManager.merge(mensagemDoChamadoAnterior);
							
					getMensagemDoChamado().setChamado(getEntidade());
					
					if (getAnexoMensagemDoChamados().size() > 0) {
						getMensagemDoChamado().setPossuiAnexo(true);
					}
					
					getMensagemDoChamado().setUsuarioQueRespondeu(getSessaoUsuarioManager().getUsuarioMemoria());
					entityManager.persist(getMensagemDoChamado());
					
					for (AnexoMensagemDoChamado anexo : getAnexoMensagemDoChamados()) {
						
						if (anexo.getId() == null) {
							anexo.setMensagemDoChamado(getMensagemDoChamado());
							entityManager.persist(anexo);
						}
						
					}
					
					//TODO: EMAIL DEVE FICAR EM UM POOL DE ENVIO PARA NÃO COMPROMETER A PERFORMACE DO SOFTWARE
					//ENVIA EMAIL PARA O CLIENTE OU SUPORTE DA ETICO
					Chamado chamado = entityManager.find(Chamado.class, getEntidade().getId());
					Usuario usuario = chamado.getUsuarioQueAbriu();
					
					System.out.println("usuario respondendo : " + getSessaoUsuarioManager().getUsuarioMemoria().getNomeUsuario());
					System.out.println("usuario criou : " + usuario.getNomeUsuario());
					
					if (getSessaoUsuarioManager().getUsuarioMemoria().getPerfil().getIndTipoSetor().equals(Perfil.strIndTipoSetor_SUPORTE)) {
						
						//SE QUEM RESPONDE É DIFERENTE DE QUEM CRIOU O CHAMADO
						if (!usuario.getId().equals(getSessaoUsuarioManager().getUsuarioMemoria().getId())) {
							
							List<String> eMails = new ArrayList<>();
							eMails.add(usuario.getEmail());
							
//							ControlaChaveAcesso.enviaChaveDeAcessoCliente(usuario, getEntidade(), Perfil.strIndTipoSetor_CLIENTE,eMails);
						}
						
					} else if (getSessaoUsuarioManager().getUsuarioMemoria().getPerfil().getIndTipoSetor().equals(Perfil.strIndTipoSetor_CLIENTE)) {
						
						Session session = (Session)entityManager.getDelegate();
						
						List<EmpresaUsuario> empresaUsuarios = session.createCriteria(EmpresaUsuario.class)
							.add(Restrictions.eq(EmpresaUsuario.strEmpresa, getEntidade().getEmpresa())).list();
						
						List<String> eMails = new ArrayList<>();
						
						for (EmpresaUsuario empresaUsuario : empresaUsuarios) {
							eMails.add(usuario.getEmail());
						}
						
						Chamado chamado2 = entityManager.find(Chamado.class, getEntidade().getId());
						
						//remove o email do usuario que está respondendo para não receber email
						eMails.remove(chamado2.getUsuarioQueAbriu().getEmail());
						
//						ControlaChaveAcesso.enviaChaveDeAcessoCliente(usuario, chamado2, Perfil.strIndTipoSetor_SUPORTE,eMails);
						
					}
					
					
					tx.commit();
					
					addMsgInfo("RESPOSTA", "Respondido com sucesso");
					setRespondendoChamado(false);
					
					
					
				} catch (Exception e) {
					tx.rollback();
					e.printStackTrace();
					addMsgError("ERRO NO PROCESSO", "Comunique o suporte");
				} finally {
					entityManager.close();
				}
				
			} else {
				super.inserir();
			}
			
		}
		
		
	}
	
	@Override
	public boolean checaRestricoes() throws Exception {
		
		if (Utils.isStringVazia(getEntidade().getTituloChamado())) {
			return getRestricaoMensagem("Escreva o titulo do chamado");
		}
		
//		if (getEntidade().getNome() == null || getEntidade().getNome().trim().isEmpty()) {
//			return getRestricaoMensagem("Nome invalido");
//		}
//		
//		if (getEntidade().getClienteTeste() == null) {
//			return getRestricaoMensagem("Selecione o cliente");
//		}
		
		return true;
		
	}
	

    public void setSelectedAnexo(AnexoMensagemDoChamado selecionado) {
    	getAnexoMensagemDoChamados().remove(selecionado);
    }
    
    public void setSelectedMensagemDoChamado(MensagemDoChamado selecionado) {
    	
    	setAnexoMensagemDoChamados(getAnexoMensagemDoChamadoDAO().getCriteria().add(Restrictions.eq(AnexoMensagemDoChamado.strMensagemDoChamado, selecionado)).list());
    	
    	for (AnexoMensagemDoChamado anexo : getAnexoMensagemDoChamados()) {
    		System.out.println(anexo.getNomeArquivo());
    	}
    	
    }
	
	private List<AnexoMensagemDoChamado> anexoMensagemDoChamados = new ArrayList<>();
	
	public void handleFileUpload(FileUploadEvent event) throws IOException {
		
		UploadedFile uploadedFile = event.getFile();
		
		AnexoMensagemDoChamado anexoMensagemDoChamado = new AnexoMensagemDoChamado();
		anexoMensagemDoChamado.setNomeArquivo(getNomeArquivoNovo(uploadedFile.getFileName()));
		anexoMensagemDoChamado.setContentType(uploadedFile.getContentType());
		
		getAnexoMensagemDoChamados().add(anexoMensagemDoChamado);
		
		InputStream inputStream = uploadedFile.getInputstream();
		
		FileOutputStream fileOutputStream = null;
		
		try {
			
			FacesContext context = FacesContext.getCurrentInstance();
			String pattern = context.getExternalContext().getInitParameter("anexosPath");
			
			fileOutputStream = new FileOutputStream(pattern + "/" + anexoMensagemDoChamado.getNomeArquivo());
			
			int read = 0;
			byte[] bytes = new byte[1024];
			
			while ((read = inputStream.read(bytes)) != -1) {
				fileOutputStream.write(bytes, 0, read);
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			inputStream.close();
			fileOutputStream.close();
		}
		
	}
	
	private String getNomeArquivoNovo(String nomeArquivoOriginal) {
		
		String[] tmp = nomeArquivoOriginal.split("\\.");
		String extencao = tmp[tmp.length -1];
		
		String nomeTmp = "" + Calendar.HOUR + Calendar.MINUTE + Calendar.SECOND + "" + Math.random() + "";
		
//		getEntidade().setIdFilme(nomeTmp.replaceAll("\\.", ""));
		
		nomeTmp = (nomeTmp.replaceAll("\\.", "")) + "." + extencao;
		
		return nomeTmp;
		
	}
	
	@Override
	public void limparTela() {
		
		System.out.println("LIMPA TELA");
		
		setEntidade(new Chamado());
		setMensagemDoChamado(new MensagemDoChamado());
		setRespondendoChamado(false);
		setAnexoMensagemDoChamados(new ArrayList<>());
	}

	public ChamadoDAO getChamadoDAO() {
		return chamadoDAO;
	}

	public void setChamadoDAO(ChamadoDAO chamadoDAO) {
		this.chamadoDAO = chamadoDAO;
	}

	public MensagemDoChamadoDAO getMensagemDoChamadoDAO() {
		return mensagemDoChamadoDAO;
	}

	public void setMensagemDoChamadoDAO(MensagemDoChamadoDAO mensagemDoChamadoDAO) {
		this.mensagemDoChamadoDAO = mensagemDoChamadoDAO;
	}

	public MensagemDoChamado getMensagemDoChamado() {
		return mensagemDoChamado;
	}

	public void setMensagemDoChamado(MensagemDoChamado mensagemDoChamado) {
		this.mensagemDoChamado = mensagemDoChamado;
	}

	public boolean isRespondendoChamado() {
		return respondendoChamado;
	}

	public void setRespondendoChamado(boolean respondendoChamado) {
		this.respondendoChamado = respondendoChamado;
	}

	public List<AnexoMensagemDoChamado> getAnexoMensagemDoChamados() {
		return anexoMensagemDoChamados;
	}

	public void setAnexoMensagemDoChamados(List<AnexoMensagemDoChamado> anexoMensagemDoChamados) {
		this.anexoMensagemDoChamados = anexoMensagemDoChamados;
	}

	public AnexoMensagemDoChamadoDAO getAnexoMensagemDoChamadoDAO() {
		return anexoMensagemDoChamadoDAO;
	}

	public void setAnexoMensagemDoChamadoDAO(AnexoMensagemDoChamadoDAO anexoMensagemDoChamadoDAO) {
		this.anexoMensagemDoChamadoDAO = anexoMensagemDoChamadoDAO;
	}

	public UsuarioDAO getUsuarioDAO() {
		return usuarioDAO;
	}

	public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}

	public SetorDAO getSetorDAO() {
		return setorDAO;
	}

	public void setSetorDAO(SetorDAO setorDAO) {
		this.setorDAO = setorDAO;
	}

	public static void main(String[] args) {
//		EntityManager entityManager = JPAUtil.getEntityManager();
//		EntityTransaction tx = entityManager.getTransaction();
//		
//		tx.begin();
//		
//		entityManager.de
		
	}

	public SessaoUsuarioManager getSessaoUsuarioManager() {
		return sessaoUsuarioManager;
	}

	public void setSessaoUsuarioManager(SessaoUsuarioManager sessaoUsuarioManager) {
		this.sessaoUsuarioManager = sessaoUsuarioManager;
	}

	public void setItemsStatus(List<SelectItem> itemsStatus) {
		this.itemsStatus = itemsStatus;
	}

}
