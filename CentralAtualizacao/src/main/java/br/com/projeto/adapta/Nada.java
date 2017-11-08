package br.com.projeto.adapta;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import br.com.projeto.adapta.model.Fields;
import br.com.projeto.adapta.model.Tables;

public class Nada {

	public static void main(String[] args) throws Exception {

		Class.forName("org.postgresql.Driver");
		Connection con =  DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/Empresa","postgres", "123456"); //DriverManager.getConnection("jdbc\\:postgresql\\:\\127.0.0.1\\:5432\\Empresa/?user=postgrest&password=123456");   //JPAUtil.getConnection();
		
		Engine engine = Engine.getInstance(con);

		// for (Tables table : engine.getAllTables()) {
		// // System.out.println(table.getName() + " " + table.getSchema());
		// for (Fields field : engine.fieldsTable(table.getSchema() + "." +
		// table.getName())) {
		// // System.out.println(field.getName() + " " + field.getdataType());
		// }
		// }

		SimpleDateFormat sp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		System.out.println("inicio :" + sp.format(new Date()));
		
		BufferedWriter out = new BufferedWriter(new FileWriter("C:\\dados.sql"));
		
		for (Tables table : engine.getAllTables()) {
			
			out.write("------------------" + table.getSchema() + "." +  table.getName() + "--------------------\n\n");
			
			Statement stme = con.createStatement();
			ResultSet res = stme.executeQuery("SELECT * FROM " + table.getSchema() + "." +  table.getName() + " order by id");
			
			while (res.next()) {
				
				List<Fields> fields = engine.fieldsTable(table.getSchema() + "." +  table.getName());
				
				String sql = "INSERT INTO " + table.getSchema() + "." +  table.getName() + " (";
				
				int i = 0;
				
				for (Fields field : fields) {
					
					if (i == 0) {
						
						sql += field.getName();
						
						i++;
					} else {
						
						sql += ", " + field.getName();
						
					}
					
				}
				
				sql += ") VALUES (";
				
				i = 0;
				
				for (Fields field : fields) {
					
					Statement stm = con.createStatement();
					ResultSet rs = stm
							.executeQuery("SELECT * FROM " + table.getSchema() + "." +  table.getName() + " where id = " + res.getString(1));
					
					registros: while (rs.next()) {
						
						if (i == 0) {
							
							i++;
							
							if (field.getdataType().equals("integer")) {
								
								sql += "" + rs.getString(field.getName()) + "";
								
								break registros;
							}
							
							if (field.getdataType().equals("real")) {
								
								sql += "" + rs.getString(field.getName()) + "";
								
								break registros;
							}
							
							if (field.getdataType().equals("string")) {
								
								sql += "'" + rs.getString(field.getName()) + "'";
								
								break registros;
							}
							
							if (field.getdataType().equals("bytea")) {
								
								byte[] img = rs.getBytes(field.getName());
								
								sql += "'\\x" + GeraScript.getHex(img) + "'";
								
								break registros;
							}
							
						} else {
							
							if (field.getdataType().equals("integer")) {
								
								sql += " ," + rs.getString(field.getName()) + "";
								
								break registros;
							}
							
							if (field.getdataType().equals("real")) {
								
								sql += " ," + rs.getString(field.getName()) + "";
								
								break registros;
							}
							
							if (field.getdataType().equals("string")) {
								
								String valor = rs.getString(field.getName());
								
								if (valor == null) {
									sql += " ,null";
								} else {
									sql += " ,'" + valor + "'";
								}
								
								break registros;
							}
							
							if (field.getdataType().equals("bytea")) {
								
								byte[] img = rs.getBytes(field.getName());
								
								if (img == null) {
									sql += " ,null";
								} else {
									sql += " ,'\\" + "\\x" + GeraScript.getHex(img)
											+ "'";
								}
								
								break registros;
							}
							
							if (field.getdataType().equals("date")) {
								
								String valor = rs.getString(field.getName());
								
								if (valor == null) {
									sql += " ,null";
								} else {
									sql += " ,'" + valor + "'";
								}
								
								break registros;
							}
							
							if (field.getdataType().equals("timestamp")) {
								
								String valor = rs.getString(field.getName());
								
								if (valor == null) {
									sql += " ,null";
								} else {
									sql += " ,'" + valor + "'";
								}
								
								break registros;
							}
							
							if (field.getdataType().equals("bool")
									|| field.getdataType().equals("boolean")) {
								
								if (rs.getBoolean(field.getName())) {
									sql += " ,true";
								} else {
									sql += " ,false";
								}
								
								break registros;
							}
							
							if (true) {
								
								throw new Error("encontrou nada : "
										+ field.getName() + " "
										+ field.getdataType());
								
							}
							
						}
						
					}
					
				}
				
				sql += ");";
//				System.out.println(sql);
				
				out.write(sql + "\r\n");
				out.flush();
				
			}
		}
		
		out.flush();
		out.close();
		
		System.out.println("Fim :" + sp.format(new Date()));
		
		// FileWriter fileWriter = new FileWriter(new File("C:\\sql.txt"));
		// fileWriter.write(sql);
		// fileWriter.flush();
		// fileWriter.close();

		// System.out.println(sql);

		// Configuracao configuracao = new Configuracao();
		//
		// Definicao definicao = new Definicao();
		// definicao.setApelido("empresa.1002");
		//
		// //CNPJ do emitente da nfe - obs : CNPJ somente numérico
		// definicao.setCnpjEmitente("34450460000133");
		//
		// //Código do IBGE da UF do emitente
		// definicao.setUfEmitente("11");
		//
		// //Tipo de ambiente (1 - producao 2 - homologacao)
		// definicao.setTipoAmbiente("2");
		//
		// //série na nfe
		// definicao.setSerie("1");
		//
		// //De qual servidor ele vai consumir os serviços
		// //exp : svrs = sefaz virtual do rs
		// definicao.setServidorWS("svrs");
		//
		// //local aonde os arquivos XML's gerados serão salvos
		// definicao.setLocalSalvarXmlsGerados("C:\\xmlSalvos\\");
		// configuracao.getDefinicaos().add(definicao);
		//
		// definicao.setSeguranca(new Seguranca());
		//
		// //Dados do certificado de assinatura
		// definicao.getSeguranca().setCaminhoCertificadoAssinaturaXml("C:\\Users\\Thiago\\Desktop\\DocNFe\\certificados\\certificado.pfx");
		// definicao.getSeguranca().setSenhaCertificadoAssinaturaXml("34450460");
		//
		// //Dados do certificado de envio pelo webservice
		// definicao.getSeguranca().setCaminhoCertificadoEnvioWebService("C:\\certificado2.pfx");
		// definicao.getSeguranca().setSenhaCertificadoEnvioWebService("123456");
		//
		// //dados do trustStore
		// definicao.getSeguranca().setLocaltrustStore("C:\\InstallCert\\jssecacerts");
		// definicao.getSeguranca().setSenhatrustStore("changeit");
		//
		// JAXBContext context2 = JAXBContext.newInstance(Configuracao.class);
		//
		// StringWriter writer = new StringWriter();
		//
		// context2.createMarshaller().marshal(configuracao, writer);
		//
		// System.out.println(writer.toString());
		//
		// //**************************************************************************//
		//
		// NFeContext context = NFeContextImp.newInstance(configuracao,
		// "empresa.1002");
		//
		// Danfe danfe = context.createDanfe();
		//
		// danfe.visualizarDanfe("11120534450460000133550010000000281001327924");

		// public static void main(String[] args) throws JAXBException,
		// InterruptedException {
		// Configuracao configuracao = new Configuracao();
		//
		// Definicao definicao = new Definicao();
		// definicao.setApelido("empresa.1002");
		//
		// //CNPJ do emitente da nfe - obs : CNPJ somente numérico
		// definicao.setCnpjEmitente("34450460000133");
		//
		// //Código do IBGE da UF do emitente
		// definicao.setUfEmitente("11");
		//
		// //Tipo de ambiente (1 - producao 2 - homologacao)
		// definicao.setTipoAmbiente("2");
		//
		// //Série da NFe
		// definicao.setSerie("1");
		//
		// //De qual servidor ele vai consumir os serviços
		// //exp : svrs = sefaz virtual do rs
		// definicao.setServidorWS("svrs");
		//
		// //local aonde os arquivos XML's gerados serão salvos
		// definicao.setLocalSalvarXmlsGerados("C:\\xmlSalvos\\");
		// configuracao.getDefinicaos().add(definicao);
		//
		// definicao.setSeguranca(new Seguranca());
		//
		// //Dados do certificado de assinatura
		// definicao.getSeguranca().setCaminhoCertificadoAssinaturaXml("C:\\Users\\Thiago\\Desktop\\DocNFe\\certificados\\certificado.pfx");
		// definicao.getSeguranca().setSenhaCertificadoAssinaturaXml("34450460");
		//
		// //Dados do certificado de envio pelo webservice
		// definicao.getSeguranca().setCaminhoCertificadoEnvioWebService("C:\\certificado2.pfx");
		// definicao.getSeguranca().setSenhaCertificadoEnvioWebService("123456");
		//
		// //dados do trustStore
		// definicao.getSeguranca().setLocaltrustStore("C:\\InstallCert\\jssecacerts");
		// definicao.getSeguranca().setSenhatrustStore("changeit");
		//
		// JAXBContext context2 = JAXBContext.newInstance(Configuracao.class);
		//
		// StringWriter writer = new StringWriter();
		//
		// context2.createMarshaller().marshal(configuracao, writer);
		//
		// System.out.println(writer.toString());
		//
		// //**************************************************************************//
		//
		// NFeContext context = NFeContextImp.newInstance(configuracao,
		// "empresa.1002");
		// NFeServicos nFeServicos = context.createNFeServicos();
		//
		// //Serviço de inutilização de numeros de NFe
		// // TProcInutNFe procInutNFe = nFeServicos.nfeInutilizacaoNF("3", "3",
		// "Teste Homologacao");
		// //
		// System.out.println(procInutNFe.getRetInutNFe().getInfInut().getXMotivo());
		//
		// // if (true) return;
		//
		// // Consulta estatus do serviço NFe
		// TRetConsStatServ consStatServ = nFeServicos.nFeStatusServico();
		// System.out.println(consStatServ.getXMotivo());
		//
		// //
		// // consulta status de uma NFe
		// TRetConsSitNFe consSitNFe =
		// nFeServicos.nfeConsulta("11120534450460000133550000000000241001327929");
		// System.out.println(consSitNFe.getXMotivo() + " " +
		// consSitNFe.getChNFe());
		//
		// // if (true) return;
		//
		// GeraXmlNFe geraXmlNfe = context.createGeraXmlNFe();
		//
		// geraXmlNfe.getTnFes().add(new GeraXmlNfeTeste().getNFe());
		//
		// List<String> xmlList = geraXmlNfe.getNFeXmls();
		//
		// //Retorno do envio da NFe
		// TRetEnviNFe retEnviNFe = nFeServicos.nfeRecepcaoLote("000022269",
		// xmlList);
		// System.out.println(retEnviNFe.getXMotivo() + " " +
		// retEnviNFe.getInfRec().getNRec());
		//
		// Thread.sleep(10000);
		//
		// //resultado do processamento nfe
		// TRetConsReciNFe consReciNFe =
		// nFeServicos.NfeRetRecepcao(retEnviNFe.getInfRec().getNRec());
		//
		// System.out.println(consReciNFe.getXMotivo());
		// System.out.println(consReciNFe.getCStat());
		//
		// for (TProtNFe protNFe : consReciNFe.getProtNFe()) {
		//
		// System.out.println(protNFe.getInfProt().getChNFe());
		// System.out.println(protNFe.getInfProt().getCStat());
		// System.out.println(protNFe.getInfProt().getNProt());
		// System.out.println(protNFe.getInfProt().getXMotivo());
		// }
		//
		//
		// // //cancela uma NFe
		// TProcCancNFe cancNFe =
		// nFeServicos.nfeCancelamentoNF("11120534450460000133550000000000231001327921",
		// "311120000004434", "TESTE HOMOLOGACAO");
		// System.out.println(cancNFe.getRetCancNFe().getInfCanc().getXMotivo());
		// }
	}
}