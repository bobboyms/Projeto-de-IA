package br.com.etico.persistencia;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.service.ServiceRegistry;

import br.com.etico.modelo.AncoraBeans;

public class JPAUtill_outro {

	private static final String IP_BANCO = "127.0.0.1"; // 200.98.149.190
	private static final String USUARIO = "postgres";
	private static final String SENHA = "123456789";
	private static SessionFactory sessionFactory;

	static {

		Configuration cfg = new Configuration();

		String url = "jdbc:postgresql://" + IP_BANCO + ":5432/conhecimento";

		System.out.println("URL DB: " + url);

		cfg.getProperties().put(Environment.URL, url);
		cfg.getProperties().put(Environment.USER, USUARIO);
		cfg.getProperties().put(Environment.PASS, SENHA);
		cfg.getProperties().put(Environment.CACHE_REGION_FACTORY,
				"org.hibernate.cache.internal.NoCachingRegionFactory");
		cfg.getProperties().put(Environment.TRANSACTION_STRATEGY,
				"org.hibernate.engine.transaction.internal.jdbc.JdbcTransactionFactory");
		cfg.getProperties().put(Environment.GENERATE_STATISTICS, "false");
		cfg.getProperties().put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
		// cfg.getProperties().put( Environment.DEFAULT_SCHEMA, "empresa" );
		cfg.getProperties().put(Environment.CONNECTION_PROVIDER,
				"org.hibernate.service.jdbc.connections.internal.C3P0ConnectionProvider");
		cfg.getProperties().put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQL82Dialect");
		cfg.getProperties().put(Environment.STATEMENT_BATCH_SIZE, "50");
		cfg.getProperties().put(Environment.FORMAT_SQL, "true");
		cfg.getProperties().put(Environment.AUTOCOMMIT, "false");
		cfg.getProperties().put(Environment.USE_GET_GENERATED_KEYS, "true");
		cfg.getProperties().put("hibernate.connection.driver_class", "org.postgresql.Driver");

		// Este campo por padrao � true, porem ele deixa o start da factory
		// MUITO lento, e segundo algumas pesquisas feitas,
		// ele n�o tem real necessidade de ser habilitado, pois � uma estrategia
		// experimental do hibernate ainda n�o usada nesta versao.
		cfg.getProperties().put("hibernate.temp.use_jdbc_metadata_defaults", "false");

		cfg.getProperties().put(Environment.SHOW_SQL, true);

		// Add classes
		registraBeans(cfg);

		StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder();

		// If you miss the below line then it will complaing about a missing
		// dialect setting
		serviceRegistryBuilder.applySettings(cfg.getProperties());
		ServiceRegistry serviceRegistry = serviceRegistryBuilder.build();
		sessionFactory = cfg.buildSessionFactory(serviceRegistry);

		// serviceRegistry = new
		// ServiceRegistryBuilder().applySettings(cfg.getProperties()).build();
		// sessionFactory = cfg.buildSessionFactory(serviceRegistry);
	}

	public static Session getSession() {
		synchronized (sessionFactory) {

			return sessionFactory.openSession();
		}
	}

	private static void registraBeans(Configuration cfg) {

		try {
			registraBeansDir(new File(AncoraBeans.class.getResource("").getPath()), cfg, AncoraBeans.class, "");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static List<Class> registraBeansDir(File dir, Configuration cfg, Class<?> classBase, String pastaAtual)
			throws Exception {

		List<Class> listaClasses = new ArrayList<Class>();

		for (File arquivo : dir.listFiles()) {

			if (arquivo.isDirectory()) {
				listaClasses.addAll(registraBeansDir(arquivo, cfg, classBase, arquivo.getName()));
				continue;
			}

			StringBuilder nome = new StringBuilder(classBase.getPackage().getName());

			nome.append(".");

			if (!pastaAtual.isEmpty()) {
				nome.append(pastaAtual).append(".");
			}

			nome.append(arquivo.getName().substring(0, arquivo.getName().lastIndexOf(".")));

			if (nome.toString().equals(classBase.getCanonicalName())) {
				continue;
			}

			try {
				Class<?> classe = Class.forName(nome.toString());

				boolean isOk = false;
				for (Annotation annotation : classe.getDeclaredAnnotations()) {
					if (annotation instanceof Entity) {
						isOk = true;
						break;
					}
				}

				if (!isOk) {
					continue;
				}

				// System.out.println("Registrando:
				// "+pastaAtual+"."+arquivo.getName());

				listaClasses.add(classe);

				if (cfg != null) {
					cfg.addAnnotatedClass(classe);
				}
			} catch (ClassNotFoundException e) {
				continue;
			}

		}

		return listaClasses;
	}

	public static Connection getConnection() throws SQLException {

		synchronized (sessionFactory) {
			SessionFactoryImpl sessionFactoryImpl = (SessionFactoryImpl) sessionFactory;
			return sessionFactoryImpl.getConnectionProvider().getConnection();
		}
	}

	public static void main(String[] args) throws IOException {
		
		
		
		
		
//		List<Gravadora> gravadoras = new ArrayList<>();
//		
//		try {
//			
//			String EMAIL_PATTERN = 
//					"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
//					+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
//			
//			Pattern pattern = Pattern.compile(EMAIL_PATTERN);
//			
//			
//			int i = 0;
//			
//			BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("D:\\gravadoras.txt")));
//			
//			String line;
//			
//			while ((line = bufferedReader.readLine()) != null) {
//				
//				
//				Matcher matcher = pattern.matcher(line.trim()); 
//				
//				if (matcher.matches()) {
//					System.out.println(line);
//					
//					Gravadora gravadora = new Gravadora();
//					gravadora.setEmail(line.trim());
//					
//					gravadoras.add(gravadora);
//				} 
//				
//			}
//			
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//		
//		System.out.println("xxx");
//
//		Session session = getSession(); //sessionFactory.openSession();
//		
//		
//		try {
//			session.beginTransaction();
//			
//			for (Gravadora gravadora : gravadoras) {
//				session.save(gravadora);
//			}
//			
//			session.getTransaction().commit();
//			
//			getConnection().close();
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			try {
//				JPAUtil.getConnection().rollback();
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
//		}
		
		
	}
}
