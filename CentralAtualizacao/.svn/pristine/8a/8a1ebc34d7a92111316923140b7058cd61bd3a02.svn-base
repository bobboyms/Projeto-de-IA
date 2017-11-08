package br.com.projeto.adapta;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Version;

import br.com.etico.ir.beans.PostagemTermos;
import br.com.etico.ir.beans.Termos;
import br.com.etico.modelo.beans.Postagem;

/**
 * 
 * Classe responsavel por gerar o script de cria��o de banco de dados, baseado
 * nas classes persistentes
 * 
 * 29/01/2012
 * 
 * @author Thiago Luiz Rodrigues
 * 
 * 
 */
public class GeraScript {

	public static final String SCHEMA = "public";

	private StringBuilder builder = new StringBuilder();
	private StringBuilder builderFK = new StringBuilder();
	private static int incFk = 0;

	// CREATE SCHEMA teste AUTHORIZATION postgres;

	/**
	 * 
	 * @param args
	 * 
	 */
	static final String HEXES = "0123456789ABCDEF";

	public static String getHex(byte[] raw) {
		if (raw == null) {
			return null;
		}
		final StringBuilder hex = new StringBuilder(2 * raw.length);
		for (final byte b : raw) {
			hex.append(HEXES.charAt((b & 0xF0) >> 4)).append(HEXES.charAt((b & 0x0F)));
		}
		return hex.toString();
	}

	
	/***
	 * 
	 * Gerador de Scripts de banco de dados
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		
		
		new GeraScript().geraCreateTable(Postagem.class);


	}

	public void geraCreateTable(Class<?> classe) {

		getBuilder().append(getScriptCreateTabela(classe));
		getBuilder().append(getScriptAddColumn(classe));

		System.out.println(getBuilder().toString());
		System.out.println(getBuilderFK().toString());

	}

	public void start() throws Exception {

		getBuilderFK().append("\n ------------------ FK --------------------- \n");

		// List<Class> classes = JPAUtil.registraBeansDir(new
		// File(AncoraBeans.class.getResource("").getPath()), null,
		// AncoraBeans.class, "");

		// for (Class<?> classe : classes) {
		// getBuilder().append(getScriptCreateTabela(classe));
		// getBuilder().append(getScriptAddColumn(classe));
		// }
		//
		// System.out.println(getBuilder().toString());
		// System.out.println(getBuilderFK().toString());

	}

	private String getScriptAddColumn(Class<?> classe) {

		String sql = "";

		String schema = "";

		StringBuilder stringBuilder = new StringBuilder();

		Annotation annotationTable = classe.getAnnotation(Table.class);

		if (annotationTable != null && annotationTable instanceof Table) {

			Table table = (Table) annotationTable;

			if (table.schema() != null && table.schema().trim().length() != 0) {

				schema = table.schema();

			} else if (SCHEMA != null) {

				schema = SCHEMA;
			}

			Field[] fields = classe.getDeclaredFields();

			for (Field field : fields) {

				sql = "";

				Annotation annotationId = field.getAnnotation(Id.class);

				Column annotationColumn = field.getAnnotation(Column.class);
				Annotation annotationVersion = field.getAnnotation(Version.class);

				Annotation annotationJoinColumn = field.getAnnotation(JoinColumn.class);
				// ALTER TABLE empresa.tb_financeira
				// ADD COLUMN teste BIGINT NOT NULL UNIQUE;
				if (annotationId != null) {

					sql = "ALTER TABLE " + schema + "." + table.name() + " ADD COLUMN " + field.getName()
							+ " BIGSERIAL NOT NULL PRIMARY KEY;";

				} else if (annotationVersion != null) {

					if (field.getType() == Integer.class) {
						sql = "ALTER TABLE " + schema + "." + table.name() + " ADD COLUMN " + field.getName()
								+ " INTEGER DEFAULT 0 NOT NULL;";
					} else {
						sql = "ALTER TABLE " + schema + "." + table.name() + " ADD COLUMN " + annotationColumn.name()
								+ " TIMESTAMP(4) DEFAULT now() NOT NULL;";
					}

				} else if (annotationColumn != null) {

					Column column = (Column) annotationColumn;

					sql = getCampoTabela(field, schema, table, column);

				} else if (annotationJoinColumn instanceof JoinColumn) {

					// Annotation annotationManyToOne = field
					// .getAnnotation(ManyToOne.class);

					JoinColumn column = (JoinColumn) annotationJoinColumn;

					sql = "ALTER TABLE " + schema + "." + table.name() + " ADD COLUMN " + column.name() + " BIGINT ";

					if (!column.nullable()) {

						sql += "NOT NULL";

					}
					if (column.unique()) {
						sql += " UNIQUE";
					}

					Class<?> tabela = field.getType();

					if (tabela != null && !tabela.toString().equals("void")) {

						Table tableRef = (Table) tabela.getAnnotation(Table.class);

						// if ((tableRef.schema().trim().length() == 0 && schema
						// .equals(SCHEMA))
						// || tableRef.schema().equals(schema)) {

						String schemaUse = tableRef.schema().trim().isEmpty() ? schema : tableRef.schema();

						String sqlFk = "ALTER TABLE " + schema + "." + table.name() + " ADD CONSTRAINT " + table.name()
								+ "_" + getIncFk() + "_fk FOREIGN KEY (" + column.name() + ") REFERENCES " + schemaUse
								+ "." + getNomeTabela(tabela)
								+ "(id) ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE; \n";

						setIncFk(getIncFk() + 1);

						getBuilderFK().append(sqlFk);

						// }

					}

					sql = sql.trim() + ";";

				}

				if (sql.trim().length() > 0) {
					stringBuilder.append(sql + "\n");
				}

			}

		}

		return stringBuilder.toString();
	}

	private String getCampoTabela(Field field, String schema, Table table, Column column) {

		String sql = "";

		if (field.getType().equals(String.class)) {

			sql = "ALTER TABLE " + schema + "." + table.name() + " ADD COLUMN " + column.name() + " ";

			if (column.length() > 0 && column.length() < 255) {

				sql += "VARCHAR(" + column.length() + ") ";

			} else {

				sql += "VARCHAR ";

			}

			if (!column.nullable()) {

				sql += "NOT NULL";

			}

			if (column.unique()) {
				sql += " UNIQUE";
			}

			sql = sql.trim() + ";";

		} else if (field.getType().equals(Boolean.class)) {

			sql = "ALTER TABLE " + schema + "." + table.name() + " ADD COLUMN " + column.name() + " BOOLEAN ";

			if (!column.nullable()) {

				sql += "NOT NULL";

			}

			if (column.unique()) {
				sql += " UNIQUE";
			}

			sql = sql.trim() + ";";

		} else if (field.getType().equals(Long.class)) {

			sql = "ALTER TABLE " + schema + "." + table.name() + " ADD COLUMN " + column.name() + " BIGINT ";

			if (!column.nullable()) {

				sql += "NOT NULL";

			}

			if (column.unique()) {
				sql += " UNIQUE";
			}

			sql = sql.trim() + ";";

		} else if (field.getType().equals(Integer.class)) {

			sql = "ALTER TABLE " + schema + "." + table.name() + " ADD COLUMN " + column.name() + " INTEGER ";

			if (!column.nullable()) {

				sql += "NOT NULL";

			}

			if (column.unique()) {
				sql += " UNIQUE";
			}

			sql = sql.trim() + ";";

		} else if (field.getType().equals(Date.class) || field.getType().equals(java.sql.Date.class)) {

			sql = "ALTER TABLE " + schema + "." + table.name() + " ADD COLUMN " + column.name()
					+ " TIMESTAMP(0) WITHOUT TIME ZONE ";

			if (!column.nullable()) {

				sql += "NOT NULL";

			}

			if (column.unique()) {
				sql += " UNIQUE";
			}

			sql = sql.trim() + ";";

		} else if (field.getType().equals(Byte[].class) || field.getType().equals(Byte.class)
				|| field.getType().equals(byte[].class)) {

			sql = "ALTER TABLE " + schema + "." + table.name() + " ADD COLUMN " + column.name() + " BYTEA ";

			if (!column.nullable()) {

				sql += "NOT NULL";

			}

			if (column.unique()) {
				sql += " UNIQUE";
			}

			sql = sql.trim() + ";";

		} else if (field.getType().equals(Double.class)) {

			sql = "ALTER TABLE " + schema + "." + table.name() + " ADD COLUMN " + column.name() + " DOUBLE PRECISION ";

			if (!column.nullable()) {

				sql += "NOT NULL";

			}

			if (column.unique()) {
				sql += " UNIQUE";
			}

			sql = sql.trim() + ";";

		}

		return sql;

	}

	/**
	 * 
	 * Cria o script de gera��o da tabela
	 * 
	 * @param classe
	 * @return
	 */
	private String getScriptCreateTabela(Class<?> classe) {

		Annotation annotation = classe.getAnnotation(Table.class);

		String sql = "";

		if (annotation != null && annotation instanceof Table) {

			Table table = (Table) annotation;

			sql = "\n------------------------ " + table.name() + " --------------------------\n";

			if (table.schema() != null && table.schema().trim().length() != 0) {

				sql += "CREATE TABLE " + table.schema() + "." + table.name() + " () WITHOUT OIDS;";

			} else {

				sql += "CREATE TABLE " + SCHEMA + "." + table.name() + " () WITHOUT OIDS;";
			}

		}

		return sql += "\n";
	}

	/**
	 * 
	 * Retorna o nome da tabela
	 * 
	 * @param classe
	 * @return
	 */
	private String getNomeTabela(Class<?> classe) {

		Annotation annotation = classe.getAnnotation(Table.class);

		if (annotation != null && annotation instanceof Table) {

			Table table = (Table) annotation;

			return table.name();

		}

		return null;
	}

	public static int getIncFk() {
		return incFk;
	}

	public static void setIncFk(int incFk) {
		GeraScript.incFk = incFk;
	}

	public StringBuilder getBuilder() {
		return builder;
	}

	public void setBuilder(StringBuilder builder) {
		this.builder = builder;
	}

	public StringBuilder getBuilderFK() {
		return builderFK;
	}

	public void setBuilderFK(StringBuilder builderFK) {
		this.builderFK = builderFK;
	}

}
