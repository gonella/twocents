package com.twocents.test.hibernate;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.twocents.model.Broker;
import com.twocents.model.FixedTax;

public class TestHibernate {

	public static void main(String[] args) {

		Session session = HibernateUtility.getSession(); // Abrindo uma sess�o
		Transaction transaction = session.beginTransaction(); // Iniciando uma
																// transa��o

		Broker b = new Broker();
		b.setName("broker 1");
		FixedTax tax = new FixedTax();
		tax.setValue(33d);

		session.save(b);
		
		// sessao.save(curso); //Transformando o objeto transiente em um objeto
		// persistente no banco de dados
		transaction.commit(); // Finalizando a transa��o
		session.close(); // Fechando a sess�o

	}

}
