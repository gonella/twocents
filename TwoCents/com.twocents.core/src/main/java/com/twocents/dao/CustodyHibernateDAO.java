package com.twocents.dao;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Query;

import com.twocents.model.Account;
import com.twocents.model.Custody;
import com.twocents.model.CustodyView;
import com.twocents.model.Stock;

@SuppressWarnings("unchecked")
public class CustodyHibernateDAO extends BaseDAOImpl<Custody> implements
CustodyDAO {

    public Custody findCustodyByStockAndAccount(Stock stock, Account account) {
        Query q = getEntityManager().createNamedQuery(
                Custody.FIND_CUSTODY_BY_STOCK_ACCOUNT);
        q.setParameter("stockId", stock.getId());
        q.setParameter("accountId", account.getId());
        return (Custody) q.getSingleResult();
    }

    public List<Custody> findCustodyByAccount(Account account) {
        Query q = getEntityManager().createNamedQuery(
                Custody.FIND_CUSTODY_BY_ACCOUNT);
        q.setParameter("accountId", account.getId());

        return q.getResultList();
    }

    public Custody findCustodyByAStockAndAccountBeforeADate(Stock stock,
            Account account, Timestamp beforeDate) {
        Query q = getEntityManager().createNamedQuery(
                Custody.FIND_CUSTODY_BY_STOCK_ACCOUNT_AND_BEFORE_A_DATE);
        q.setParameter("accountId", account.getId());
        q.setParameter("stockId", stock.getId());
        q.setParameter("beforeDate", beforeDate);
        return (Custody) q.getSingleResult();
    }

    public List<CustodyView> findCustodyViewByAccount(Account account) {
        Query q = getEntityManager().createNamedQuery(
                Custody.FIND_CUSTODY_VIEW_BY_STOCK_ACCOUNT);
        q.setParameter("accountId", account.getId());
        return q.getResultList();
    }


    public double getInvestmentMadeByAccount(Account account) {
        Query q = getEntityManager().createNamedQuery(
                Custody.INVESTMENT_MADE_BY_ACCOUNT);
        q.setParameter("accountId", account.getId());

        Object obj = q.getSingleResult();

        if (obj != null) {
            return (Double) obj;
        }

        return 0d;
    }


    public double getCurrentPositionValue(Account account) {
        Query q = getEntityManager().createNamedQuery(
                Custody.INVESTMENT_POSITION_BY_ACCOUNT);
        q.setParameter("accountId", account.getId());

        Object obj = q.getSingleResult();

        if (obj != null) {
            return (Double) obj;
        }

        return 0d;

    }

    public void updateCustodyLastPrice(Stock stock, Double lastPrice) {
        Query q = getEntityManager().createNamedQuery(
                Custody.UPDATE_CUSTODY_STOCK_LAST_PRICE);
        q.setParameter("lastPrice", lastPrice);
        q.setParameter("stockId", stock.getId());

        q.executeUpdate();

    }


}
