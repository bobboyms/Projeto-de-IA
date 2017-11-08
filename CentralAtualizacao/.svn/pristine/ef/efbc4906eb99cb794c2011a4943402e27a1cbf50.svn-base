package br.com.etico.arquitetura.formulario.util.filter;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.criterion.Restrictions;

import br.com.etico.arquitetura.formulario.util.ELutil;
import br.com.etico.arquitetura.formulario.util.SessaoUsuario;
import br.com.etico.arquitetura.formulario.util.criptografia.ControlaChaveAcesso;
import br.com.etico.modelo.beans.Chamado;
import br.com.etico.modelo.beans.Usuario;
import br.com.etico.persistencia.jpa.JPAUtil;

public class AuthorizationFilter implements Filter {

	public AuthorizationFilter() {
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {

			// System.out.println("XXXXXXXXXXX Filtro XXXXXXXXXXX");

			HttpServletRequest reqt = (HttpServletRequest) request;
			HttpServletResponse resp = (HttpServletResponse) response;
			HttpSession sessao = reqt.getSession(false);

			String reqURI = reqt.getRequestURI();

			System.out.println("KEY : " + reqt.getParameter("key"));

			System.out.println("URI : " + reqURI);

			String key = reqt.getParameter("key");

			if (key != null) {

				if (ControlaChaveAcesso.validaChaveDeAcesso(key)) {
					
					String[] valores = ControlaChaveAcesso.informaçoesDecriptografada(key);
					
					System.out.println("USUARIO : " + valores[0]);
					System.out.println("SENHA : " + valores[1]);
					System.out.println("CHAMADO : " + valores[2]);
					
					Usuario usuario = (Usuario)JPAUtil.getSession().createCriteria(Usuario.class).add(Restrictions.eq(Usuario.strLogin, valores[0]))
			 				  .add(Restrictions.eq(Usuario.strSenha, valores[1]))
			 				  .uniqueResult();
					
					
					if (usuario != null) {
						
						Chamado chamadoExterno = (Chamado) JPAUtil.getSession().createCriteria(Chamado.class)
								.add(Restrictions.eq(Chamado.strId, new Long(valores[2]))).uniqueResult();
						
						usuario.setChamadoExterno(chamadoExterno);
						reqt.getSession().setAttribute(SessaoUsuario.USUARIO, usuario);
						
						chain.doFilter(request, response);
						
					} else {
						resp.sendRedirect(reqt.getContextPath() + "/login.jsf");
					}
					
					
					
				} else {
					resp.sendRedirect(reqt.getContextPath() + "/login.jsf");
				}

			} else {

				if (sessao != null) {

					Usuario usuario = (Usuario) sessao.getAttribute(SessaoUsuario.USUARIO);

					if (usuario != null) {
						chain.doFilter(request, response);
					} else {
						resp.sendRedirect(reqt.getContextPath() + "/login.jsf");
					}

				} else {
					resp.sendRedirect(reqt.getContextPath() + "/login.jsf");
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
