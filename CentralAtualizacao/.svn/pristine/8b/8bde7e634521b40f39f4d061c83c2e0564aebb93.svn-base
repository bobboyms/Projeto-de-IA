package br.com.etico.persistencia;

import java.io.Serializable;
import java.lang.reflect.Field;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.hibernate.Hibernate;
import org.junit.Test;

@SuppressWarnings("serial")
public abstract class EntidadePersistence implements Serializable {

	public static final String strId = "id";

	public abstract Long getId();

	public String getSingleNameOfClass() {
		return getClass().getSimpleName();
	}

	public abstract void setId(Long id);

	public abstract void setValoresPadroes();

	@Test
	public void geraStrCampo() {
		Field[] fileds = this.getClass().getDeclaredFields();

		for (Field field : fileds) {
			if (field.getAnnotation(Column.class) != null
					|| field.getAnnotation(JoinColumn.class) != null
					|| field.getAnnotation(OneToMany.class) != null) {

				boolean exists = false;

				String nomeCampo = "str"
						+ (field.getName().substring(0, 1).toUpperCase() + field
								.getName().substring(1));

				for (Field fieldPesq : fileds) {
					if (nomeCampo.equals(fieldPesq.getName())) {
						exists = true;
						break;
					}
				}

				if (exists) {
					continue;
				}

				System.out.println("public static final String " + nomeCampo
						+ " = \"" + field.getName() + "\";");
			}
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}

		if (Hibernate.getClass(this) != Hibernate.getClass(obj)) {
			return false;
		}

		EntidadePersistence other = (EntidadePersistence) obj;

		if (getId() == null || other.getId() == null
				|| !getId().equals(other.getId())) {
			return false;
		}

		return true;
	}
}
