package com.twocents.test.hibernate;

import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.twocents.dao.BrokerHibernateDAO;
import com.twocents.exceptions.CoreException;
import com.twocents.model.Broker;
import com.twocents.model.StockBroker;
import com.twocents.test.ServiceLocatorUT;

public class HibernateBrokerDaoTest {

	private BrokerHibernateDAO dao;

	@Before
	public void setUp() {
		dao = (BrokerHibernateDAO) ServiceLocatorUT
				.getBean("com.twocents.dao.BrokerDAO");
	}

	@Test
	public void testFindBrokerByName() throws CoreException, ParseException {
		String str = "3:09pm";
		String str2 = "12/3/2010";
		
		SimpleDateFormat df =  new SimpleDateFormat("h:mma", Locale.getDefault());
		System.out.println(df.parse(str).toString());
		
		SimpleDateFormat df2 =  new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
		System.out.println(df2.parse(str2).toString());
		
		SimpleDateFormat df3 =  new SimpleDateFormat("dd/MM/yyyy h:mma", Locale.getDefault());
		System.out.println(df3.parse(str2 + " " + str).toString());

		Broker b = new Broker();
		b.setName("brk Test");
		
		StockBroker stb = new StockBroker();
		stb.setName("alex");
		b.setStockBroker(stb);
		
		dao.persist(b);
		
		Broker retrieved = dao.findBrokerByName("brk Test", stb);
		
		Assert.assertEquals(b.getName(), retrieved.getName());
	}

	@Test
	public void testDelete() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteById() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindAll() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindById() {
		fail("Not yet implemented");
	}

	@Test
	public void testMerge() {
		fail("Not yet implemented");
	}

	@Test
	public void testPersist() {
		fail("Not yet implemented");
	}

}
