package br.com.projeto.adapta;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.projeto.adapta.model.Fields;
import br.com.projeto.adapta.model.PrimaryFields;
import br.com.projeto.adapta.model.Tables;

public class Engine {
	
    private static Engine engine = null;
    private Connection connection;

    /**
     * construtor da class Engine
     * @param connection objeto de conexão com o banco de dados
     * @param configure objeto de configuração
     */
    private Engine(Connection connection) {
        this.setConnection(connection);
    }
    
    public static Engine getInstance(Connection connection) {
    	
        if (engine == null) {
            engine = new Engine(connection);
        }
        
        return engine;
    }

    /**
     * 
     * @return retorna uma List<Tables> com todas as tabelas de uma banco de dados
     */
    public List<Tables> getAllTables() {
        List<Tables> tables = new ArrayList<Tables>();
        Tables table;
        try {
            String[] types = {"TABLE"};

            DatabaseMetaData dbmd = getConnection().getMetaData();
            ResultSet rs = dbmd.getTables(null, null, "%", types);

            while (rs.next())
            {
                table = new Tables();
                table.setSchema(rs.getString(2));
                table.setName(rs.getString(3));
                tables.add(table);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return tables;

    }

    /**
     *
     * retorna o tipo de dado de um campo
     *
     * @param field
     * @param table
     * @return
     */
    public String getDataType(String field, String table) {
        
        String  sql = "SELECT \"" + field + "\" FROM " + "\""+ table + "\"";
        String type = "";
        try {
            Statement stm =  getConnection().createStatement();
            ResultSet rs = stm.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();

            for (int i = 1; i <= rsmd.getColumnCount(); i++ ) {

                 if (rsmd.getColumnName(i).equals(field)) {
                     type = getGenericType(rsmd.getColumnTypeName(i));
                     break;
                 }
            }

        } catch(SQLException e) {
            throw new Error(e.getMessage());
        }

        return type;
    }

    /**
     * gera um tipo de generico de dado para um tio real do banco
     * @param type
     * @return
     */
    private String getGenericType(String type) {

        String aType = "";
        
        if((type.equals("int")) || (type.equals("int2")) || (type.equals("int4")) || (type.equals("int8")) || (type.equals("integer")) || (type.equals("serial")) || (type.equals("bigint")) || (type.equals("bigserial"))) {
            aType = "integer";
        }
        else if ((type.equals("float")) || (type.equals("float4")) || (type.equals("float8")) || (type.equals("float")) || (type.equals("double")) || (type.equals("decimal")) || (type.equals("numeric")) || (type.equals("money"))) { //
            aType = "real";
        }
        else if ((type.equals("varchar")) || (type.equals("char")) || (type.equals("text")) || (type.equals("bit")) || (type.equals("character")) || (type.equals("bpchar"))) { 
            aType = "string";
        }
        else if ((type.equals("date"))) {
            aType = "date";
        }
        else if ((type.equals("bool")) || (type.equals("boolean"))) {
            aType = "bool";
        } 
        else if ((type.equals("timestamp"))) {
                aType = "timestamp";
        }
        else if ((type.equals("bytea"))) {
            aType = "bytea";
        } else {
            aType = "unsuported";
        }

        return aType;

    }

    /**
     * retorna uma lista de chaves primárias
     * @param conection
     * @param table
     * @return
     */
    public List<PrimaryFields> fieldsPrimary(String table) {

        List<PrimaryFields> pk = new ArrayList<PrimaryFields>();

        try {
          DatabaseMetaData meta = getConnection().getMetaData();
          PrimaryFields primaryFields;
          ResultSet rs = meta.getPrimaryKeys(null, null, table);

          while (rs.next()) {
            primaryFields = new PrimaryFields();
            primaryFields.setName(rs.getString("COLUMN_NAME"));
            primaryFields.setDataType(getDataType(rs.getString("COLUMN_NAME"),table));
            pk.add(primaryFields);
          }

        } catch (SQLException e) {
            throw new Error(e.getMessage());
        }

        return pk;
    }


    /**
     * retorna todos os campos e tipo de uma determinada tabela
     * @return
     */
    public List<Fields> fieldsTable(String table) {
        String sql = "SELECT * FROM " + ""+ table +"";
        
        List<Fields> listFields = new ArrayList<Fields>();
        Fields fields;
        try {
            Statement stm = getConnection().createStatement();
            ResultSet rs = stm.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();

            for (int i = 1; i <= rsmd.getColumnCount(); i++ ) {

                 if (!getGenericType(rsmd.getColumnTypeName(i)).equals("unsuported")) {
                     fields = new Fields();
                     fields.setDataType(getGenericType(rsmd.getColumnTypeName(i)));
                     fields.setName(rsmd.getColumnName(i));

                     List<PrimaryFields> primary = fieldsPrimary(table);

                     for(PrimaryFields pk : primary) {
                         if (pk.getName().equals(rsmd.getColumnName(i))) {
                             fields.setPrimary(true);
                         }
                     }

                     listFields.add(fields);
                 } else {
                	 System.out.println("Tipo n�o suportado : " + rsmd.getColumnTypeName(i) + " " + table + " " + rsmd.getColumnName(i));
                 }
            }

        } catch(SQLException e) {
            throw new Error(e.getMessage());
        }

        return listFields;
    }

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	
}
