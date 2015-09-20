package com.twocents.dao;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import com.twocents.model.Quote;

@SuppressWarnings("unchecked")
public class QuoteHibernateDAO extends BaseDAOImpl<Quote> implements QuoteDAO {

	public Map<String, Object> findLastQuoteMapByStockId(Long stockId) {
		Query q = getEntityManager().createNamedQuery(
				Quote.FIND_LAST_QUOTE_BY_STOCK);
		q.setParameter("stockId", stockId);
		List l = q.getResultList();
		if (l.isEmpty()) {
			return Collections.EMPTY_MAP;
		}
		return (Map<String, Object>) l.get(0);
	}

	public Quote getCurrentQuote(Long stockId) {
		Query q = getEntityManager()
				.createNamedQuery(Quote.FIND_QUOTE_BY_STOCK);
		q.setParameter("stockId", stockId);

		try {
			return (Quote) q.getSingleResult();
		} catch (Exception e) {
			return null;
		}

	}

}
