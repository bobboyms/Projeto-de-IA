package br.com.etico.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;

import javax.faces.FacesException;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.event.ExceptionQueuedEvent;

import br.com.etico.arquitetura.exception.RestricaoException;

public class ExceptionHandlerEtico extends ExceptionHandlerWrapper {

	private ExceptionHandler wrapped;
	private static PrintWriter pwLog;
	
	public ExceptionHandlerEtico(ExceptionHandler wrapped) {
		this.wrapped = wrapped;
	}

	@Override
	public ExceptionHandler getWrapped() {
		return wrapped;
	}
	
	static {
		String local = new File("").getAbsolutePath();
		
		String strBase = File.separator+"etico"+File.separator;
		String nomArquivo = null;
		
		if (local.indexOf(strBase) >= 0) {
			local = local.substring(local.indexOf(strBase)+strBase.length());
			
			if (local.indexOf(File.separator) > 0) {
				local = local.substring(0,local.indexOf(File.separator));
			}
			
			//nomArquivo = Utils.isWindows() ? "C:"+File.separator+"logs"+File.separator+local+File.separator : File.separator+"etico"+File.separator+"logs"+File.separator+local+File.separator;
			nomArquivo = nomArquivo + "etico_erroHandle_log.txt";
			
			System.out.println("PRODU��O LOGS ERRO: "+nomArquivo);
			
			try {
				pwLog = new PrintWriter(new FileOutputStream(nomArquivo, true));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("DEV, SEM ARQUIVO DE LOG DE ERRO.");
		}
		
	}

	public static void registraLog(String msg, Throwable t) {
		
		if (pwLog == null) {
			return;
		}
		
		if (msg != null) {
			pwLog.println(msg);
		}
		
		if (t != null) {
			t.printStackTrace(pwLog);
			pwLog.println("\nData/Hora: "+new Date());
		}
		
		pwLog.flush();
	}
	
	@Override
	public void handle() throws FacesException {
		
		long id = new Random().nextLong();
		
		try {
			
			boolean isSessionExpired = false;
			
			for (final Iterator<ExceptionQueuedEvent> it = getUnhandledExceptionQueuedEvents().iterator(); it.hasNext();) {
				System.out.println("GERADA NO HANDLE "+id+" = "+new Date());
				registraLog("GERADA NO HANDLE "+id+" = "+new Date(), null);
				
				Throwable t = it.next().getContext().getException();
				
				System.out.println("XXXXXXXXXXXXXXXXXXXX " + t);
				
				if (true) {
					continue;
				}
				
				if (t instanceof RestricaoException) {
					continue;
				}
				
				while (t.getCause() != null) {
					
					if (t instanceof ViewExpiredException) {
						isSessionExpired = true;
						continue;
					}
					
					t.printStackTrace();
					registraLog(null, t);
					
					t = t.getCause();
				}
				
				if (t instanceof ViewExpiredException) {
					isSessionExpired = true;
					break;
				}
				
				t.printStackTrace();
				registraLog(null, t);
				
//				if (t instanceof ViewExpiredException) {
//					isSessionExpired = true;
//				}
				
				
				
//				final FacesContext facesContext = FacesContext.getCurrentInstance();
//				final ExternalContext externalContext = facesContext.getExternalContext();
//				HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();			
//				
//				if (t instanceof ViewExpiredException
//						|| t instanceof javax.faces.view.facelets.TagAttributeException) {
//					
//					System.err.println("Expirou "+id+": "+t+" = "+isIoExceptionOuClientException);
//					
//					if (t instanceof javax.faces.view.facelets.TagAttributeException) {
//						t.printStackTrace();
//					}
//				} else {
//					System.out.println("GERADA NO HANDLE: "+id+" = "+isIoExceptionOuClientException);
//					t.printStackTrace();
//				}
//				
//				if (SessaoUsuario.getUsuarioConectado() != null && !isIoExceptionOuClientException) {
//					Autenticacao.mapaUsersConectado.remove(SessaoUsuario.getUsuarioConectado().getNomLoginUsuario());
//				}
//				
//				if (!isIoExceptionOuClientException) {
//					ELutil.killSessionBean(request.getSession());
//					
//					ELutil.limparTodaSessao(request.getSession());
//				}
//				
//				System.out.println(new Date()+" - Sessao foi invalidada. (Handle) "+id+" = "+isIoExceptionOuClientException);
//				
////				request.getSession().invalidate();
//				
//				if (!isIoExceptionOuClientException) {
//					try {
//						externalContext.redirect(Autenticacao.getContextNameStatic()+"/error.jsf");
//					} catch (final Exception e) {
//						
//						if (!(e instanceof IllegalStateException)) {
//							e.printStackTrace();
//						} else {
//							System.err.println("IllegalStateException "+id+": "+e.getMessage()+" = "+isIoExceptionOuClientException);
//						}
//					}
//					facesContext.responseComplete();
//					
//					break;
//				}
			}
			
//			if (isSessionExpired) {
//				try {
//
//					if (SessaoUsuario.getUsuarioConectado() != null) {
//						Autenticacao.mapaUsersConectado.remove(SessaoUsuario.getUsuarioConectado().getNomLoginUsuario());
//					}
//					
//					ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
//					HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();			
//					HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();			
//					
//					ELutil.killSessionBean(request.getSession());
//					
//					ELutil.limparTodaSessao(request.getSession());
//					
//					response.sendRedirect(request.getContextPath() + "/error.jsf");
//				} catch (final Exception e) {
//					if (!(e instanceof IllegalStateException)) {
//						e.printStackTrace();
//					} else {
//						System.err.println("IllegalStateException "+id+": "+e.getMessage());
//					}
//				}
//			}
			
			if (isSessionExpired) {
				System.err.println("SESSAO EXPIRADA... "+id);
				registraLog("SESSAO EXPIRADA... "+id, null);
			}
			
			getWrapped().handle();
		} catch (Exception e) {
			System.err.println("ERRO NO HANDLE "+id);
			e.printStackTrace();
			registraLog(null, e);
		}
		
	}
}
