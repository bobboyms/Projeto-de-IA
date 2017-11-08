package br.com.etico.servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;

import javax.inject.Inject;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.etico.modelo.beans.AnexoMensagemDoChamado;
import br.com.etico.persistencia.dao.AnexoMensagemDoChamadoDAO;

/**
 * Servlet implementation class AnexoDownloader
 */

@WebServlet(name="anexoDownloader")
public class AnexoDownloader extends HttpServlet implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Inject
	private AnexoMensagemDoChamadoDAO anexoMensagemDoChamadoDAO;
	
	private String anexoPath;
	
	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);
		
		ServletContext context = getServletContext();
		anexoPath = context.getInitParameter("anexosPath");
		
	}
	
	/**
     * Default constructor. 
     */
    public AnexoDownloader() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String id = request.getParameter("id");

		AnexoMensagemDoChamado anexo = (AnexoMensagemDoChamado) getAnexoMensagemDoChamadoDAO().getRegistro(new Long(id));
		
        String fileName = anexo.getNomeArquivo();
        String fileType = anexo.getContentType();
        // Find this file id in database to get file name, and file type

        // You must tell the browser the file type you are going to send
        // for example application/pdf, text/plain, text/html, image/jpg
        response.setContentType(fileType);

        // Make sure to show the download dialog
        response.setHeader("Content-disposition","attachment; filename=" + fileName);

        // Assume file name is retrieved from database
        // For example D:\\file\\test.pdf

        File myFile = new File(getAnexoPath() + "/" + fileName);

        // This should send the file to browser
        OutputStream out = response.getOutputStream();
        FileInputStream in = new FileInputStream(myFile);
        byte[] buffer = new byte[4096];
        int length;
        while ((length = in.read(buffer)) > 0){
           out.write(buffer, 0, length);
        }
        in.close();
        out.flush();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	public AnexoMensagemDoChamadoDAO getAnexoMensagemDoChamadoDAO() {
		return anexoMensagemDoChamadoDAO;
	}

	public void setAnexoMensagemDoChamadoDAO(AnexoMensagemDoChamadoDAO anexoMensagemDoChamadoDAO) {
		this.anexoMensagemDoChamadoDAO = anexoMensagemDoChamadoDAO;
	}

	public String getAnexoPath() {
		return anexoPath;
	}

	public void setAnexoPath(String anexoPath) {
		this.anexoPath = anexoPath;
	}

}
