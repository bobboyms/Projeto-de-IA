package br.com.etico.ir.utils;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import br.com.etico.ir.beans.PostagemTermos;
import br.com.etico.ir.indexador.Indexador;
import br.com.etico.ir.preditor.Preditor;
import br.com.etico.modelo.beans.Postagem;
import br.com.etico.persistencia.jpa.JPAUtil;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;
import java.awt.event.ActionEvent;

public class Tela extends JFrame {

	private JPanel contentPane;
	private JTextField textSite;
	private JTextField textEsporte;
	private JLabel lblEsporte;
	private JTextField textEconomia;
	private JLabel lblEconomia;
	private JButton btnDownload;
	private JButton btnIndexar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Tela frame = new Tela();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	Postagem postagem = null;

	/**
	 * Create the frame.
	 */
	public Tela() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textSite = new JTextField();
		textSite.setBounds(17, 44, 412, 26);
		contentPane.add(textSite);
		textSite.setColumns(10);

		JButton btnClassificar = new JButton("Classificar");
		btnClassificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (textSite.getText().trim().length() == 0) {
					return;
				}

				String site = textSite.getText().trim();


				// PEGA AS CARACTERISTICAS DO ARTIGO
				Session session = JPAUtil.getSession();
				Criteria crit = session.createCriteria(PostagemTermos.class);

				ProjectionList proList = Projections.projectionList();

				proList.add(Projections.sum(PostagemTermos.strFrequenciaDoTermoTF));
				proList.add(Projections.sum(PostagemTermos.strFrequenciaDoIndiceReversoTermoIDF));
				proList.add(Projections.sum(PostagemTermos.strTfIDF));
				proList.add(Projections.groupProperty(PostagemTermos.strCategoria));

				crit.add(Restrictions.eq(PostagemTermos.strPostagem, postagem));
				crit.setProjection(proList);
				List sumResult = crit.list();

				Iterator it = sumResult.iterator();

				String tf = null, idf = null, tfidf = null;
				
				while (it.hasNext()) {
					Object[] row = (Object[]) it.next();
					
					tf = (String) row[0];
					idf = (String) row[1];
					tfidf = (String) row[2];
					
//					System.out.println(row[0] + "," + row[1] + "," + row[2] + "," + row[3]);

				}
				
				double[] resultado = new Preditor().fazerPrevisaoIBk(tf, idf, tfidf);

				DecimalFormat df = new DecimalFormat("#,###.0000");
//				
				
//				System.out.println("ECONOMIA:" + df.format(resultado[0] * 100));
//				System.out.println("ESPORTE:" + df.format(resultado[1] * 100));
				
				textEconomia.setText(df.format(resultado[0] * 100));
				textEsporte.setText(df.format(resultado[1] * 100));
				
			}
		});
		btnClassificar.setBounds(17, 82, 117, 29);
		contentPane.add(btnClassificar);
		
		textEsporte = new JTextField();
		textEsporte.setText("0,0%");
		textEsporte.setBounds(17, 139, 130, 26);
		contentPane.add(textEsporte);
		textEsporte.setColumns(10);
		
		lblEsporte = new JLabel("Esporte");
		lblEsporte.setBounds(17, 123, 61, 16);
		contentPane.add(lblEsporte);
		
		textEconomia = new JTextField();
		textEconomia.setText("0,0%");
		textEconomia.setBounds(154, 139, 130, 26);
		contentPane.add(textEconomia);
		textEconomia.setColumns(10);
		
		lblEconomia = new JLabel("Economia");
		lblEconomia.setBounds(158, 123, 61, 16);
		contentPane.add(lblEconomia);
		
		btnDownload = new JButton("download");
		btnDownload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// FAZ O DOWNLOAD DO SITE
				Document doc2 = null;
				try {
					doc2 = Jsoup.connect(textSite.getText().trim()).get();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				String artigo = doc2.getElementsByTag("article").text();

				Session session = JPAUtil.getSession();
				session.getTransaction().begin();

				Postagem post = null;

				if (artigo.trim().length() > 0) {

					// System.out.println(artigo);

					post = new Postagem();
					post.setIndexado(false);
					post.setTituloPostagem(artigo);
					post.setCategoria("DESCONHECIDA");

					session.persist(post);
					
					postagem = post;

					System.out.println("DOWNLOAD EFETUADO");

				}

				session.getTransaction().commit();
				session.close();
				
			}
		});
		btnDownload.setBounds(276, 82, 117, 29);
		contentPane.add(btnDownload);
		
		btnIndexar = new JButton("indexar");
		btnIndexar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// INDEXA O SITE NA BASE
				Indexador.main(null);
				System.out.println("INDEXADO NA BASE");
			}
		});
		btnIndexar.setBounds(146, 82, 117, 29);
		contentPane.add(btnIndexar);
	}
}
