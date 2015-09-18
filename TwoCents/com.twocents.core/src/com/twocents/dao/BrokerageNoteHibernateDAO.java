package com.twocents.dao;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import com.twocents.model.Account;
import com.twocents.model.BrokerageNote;

@SuppressWarnings("unchecked")
public class BrokerageNoteHibernateDAO extends BaseDAOImpl<BrokerageNote>
		implements BrokerageNoteDAO {

	public List<Map<String, Object>> findBrokerageNoteForPeriodAndGroupByYearAndMonth(
			Account account, Timestamp min, Timestamp max) {

		String queryProjection = "select new map(" + " month(b.date) as "
				+ BrokerageNoteKey.MONTH.toString() + ", year(b.date) as "
				+ BrokerageNoteKey.YEAR.toString() + ", sum(b.ISS) as "
				+ BrokerageNoteKey.ISS.toString() + ", sum(b.corretagem)	 as "
				+ BrokerageNoteKey.CORRETAGEM.toString()
				+ ", sum(b.emolumentos) as "
				+ BrokerageNoteKey.EMOLUMENTOS.toString()
				+ ", sum(b.taxaDeLiquidacao) as "
				+ BrokerageNoteKey.TAXADELIQUIDACAO.toString()
				+ ", sum(b.valorDasOperacoes) as "
				+ BrokerageNoteKey.VALORDASOPERACOES.toString()
				+ ", sum(b.valorLiquidoDasOperacoes) as "
				+ BrokerageNoteKey.VALORLIQUIDODASOPERACOES.toString()
				+ ", sum(b.totalBovespaESoma) as "
				+ BrokerageNoteKey.TOTALBOVESPAESOMA.toString()
				+ ", sum(b.totalCBLC) as "
				+ BrokerageNoteKey.TOTALCBLC.toString()
				+ ", sum(b.totalCorretagemEDespesas) as "
				+ BrokerageNoteKey.CORRETAGEMEDESPESAS.toString()

				+ ")";

		// select
		// to_char(b.date,'MM-YYYY'),sum(b.iss),sum(b.corretagem),sum(b.emolumentos),sum(b.taxadeliquidacao),sum(b.valordasoperacoes),sum(b.valorliquidodasoperacoes),sum(b.totalbovespaesoma),sum(b.totalcblc),sum(b.totalcorretagemedespesas)
		// from brokeragenote b where b.account_id=1 and b.date between
		// '2010-11-01' and '2010-12-30 ' group by year(b.date),month(b.date);
		// select
		// month(date)+'-'+year(date),sum(iss),sum(corretagem),sum(emolumentos),sum(taxadeliquidacao),sum(valordasoperacoes),sum(valorliquidodasoperacoes),sum(totalbovespaesoma),sum(totalcblc),sum(totalcorretagemedespesas)
		// from brokeragenote where account_id=1 and date between '2010-11-01'
		// and '2010-12-30 ' group by year(date),month(date);
		// select
		// sum(iss),sum(corretagem),sum(emolumentos),sum(taxadeliquidacao),sum(valordasoperacoes),sum(valorliquidodasoperacoes),sum(totalbovespaesoma),sum(totalcblc),sum(totalcorretagemedespesas)
		// from brokeragenote where account_id=1 and date between '2010-11-01'
		// and '2010-12-30 ' group by date;
		Query q = getEntityManager()
				.createQuery(
						queryProjection
								+ " from "
								+ BrokerageNote.class.getName()
								+ " b "
								+ " where b.account.id=:accountId and  b.date between :startDate and :endDate"
								+ " group by year(b.date),month(b.date)");

		q.setParameter("accountId", account.getId());
		q.setParameter("startDate", min);
		q.setParameter("endDate", max);

		return q.getResultList();
	}

	public BrokerageNote findBrokerageNoteByDate(Account account,
			Timestamp min, Timestamp max) {

		Query q = getEntityManager()
				.createQuery(
						"select b from "
								+ BrokerageNote.class.getName()
								+ " b "
								+ " where b.account.id=:accountId and  b.date between :startDate and :endDate");

		q.setParameter("accountId", account.getId());
		q.setParameter("startDate", min);
		q.setParameter("endDate", max);

		return (BrokerageNote) q.getSingleResult();
	}

	public List<BrokerageNote> findBrokerageNoteByDateWithDateOrderedAsc(
			Account account, Timestamp min, Timestamp max) {
		Query q = getEntityManager()
				.createQuery(
						"select b from "
								+ BrokerageNote.class.getName()
								+ " b "
								+ " where b.account.id=:accountId and  b.date between :startDate and :endDate"
								//+ " order by b.date"
								);

		q.setParameter("accountId", account.getId());
		q.setParameter("startDate", min);
		q.setParameter("endDate", max);

		return q.getResultList();
	}

	public List<BrokerageNote> findAllBrokerageNoteByAccount(Account account) {

		Query q = getEntityManager().createQuery(
				"select b from " + BrokerageNote.class.getName() + " b "
				// + Account.class.getName()+" a "
						+ " where b.account.id=:accountId");

		q.setParameter("accountId", account.getId());

		return q.getResultList();
	}

}
