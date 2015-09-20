package com.twocents.dao;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import com.twocents.model.Account;
import com.twocents.model.BrokerageNoteItem;
import com.twocents.model.Operation;
import com.twocents.model.OperationKey;
import com.twocents.model.OperationView;
import com.twocents.model.Stock;
import com.twocents.model.StockOperation;

@SuppressWarnings("unchecked")
public class OperationHibernateDAO extends BaseDAOImpl<Operation> implements
OperationDAO {

    @Deprecated
    /*
     * * Use findOperationMapByAccount()
     */
    public List<Operation> findOperationByAccount(Account account) {
        Query q = getEntityManager().createNamedQuery(
                Operation.FIND_OPERATION_BY_ACCOUNT);
        q.setParameter("accountId", account.getId());
        return q.getResultList();
    }

    @Deprecated
    /*
     * * Use findOperationMapByAccount()
     */
    public List<? extends StockOperation> findOperationByAccount(
            Account account, Class<?> classe) {
        Query q = getEntityManager()
                .createQuery(
                        "select o from "
                                + classe.getName()
                                + " o where o.account.id = :accountId order by o.operationDate desc");
        q.setParameter("accountId", account.getId());
        return q.getResultList();
    }

    public List<Map<String, Object>> findOperationMapByAccount(Account account,
            Timestamp starDate, Timestamp endDate) {
        Query q = getEntityManager().createNamedQuery(
                Operation.FIND_OPERATION_MAP_BY_ACCOUNT);
        q.setParameter("accountId", account.getId());
        q.setParameter("startDate", starDate);
        q.setParameter("endDate", endDate);
        return q.getResultList();
    }

    public List<OperationView> findOperationViewByAccount(Account account,
            Timestamp starDate, Timestamp endDate) {
        Query q = getEntityManager().createNamedQuery(
                Operation.FIND_OPERATION_VIEW_BY_ACCOUNT);
        q.setParameter("accountId", account.getId());
        q.setParameter("startDate", starDate);
        q.setParameter("endDate", endDate);
        return q.getResultList();
    }

    public List<Map<String, Object>> findOperationMapByAccount(Account account,
            Timestamp starDate, Timestamp endDate, Class<?> classe) {
        Query q = getEntityManager()
                .createQuery(
                        getResultAttributeForQueryOperation()
                        + " from Operation o left join o.stock as s where o.account.id = :accountId "
                        + " and o.class = "
                        + classe.getName()
                        + " and o.operationDate between :startDate and :endDate "
                        + "order by o.operationDate desc");
        q.setParameter("accountId", account.getId());
        q.setParameter("startDate", starDate);
        q.setParameter("endDate", endDate);
        return q.getResultList();
    }

    /**
     *
     * Ordena a lista pela data(operações com datas mais velhas são as primeiras
     * da lista)
     *
     */
    public List<Map<String, Object>> findOperationByAccountWithDateOrdered(
            Account account, Timestamp starDate, Timestamp endDate) {
        Query q = getEntityManager()
                .createQuery(
                        getResultAttributeForQueryOperation()
                        + " from Operation o left join o.stock as s where o.account.id = :accountId "
                        + " and o.operationDate between :startDate and :endDate "
                        + "order by o.operationDate");
        q.setParameter("accountId", account.getId());
        q.setParameter("startDate", starDate);
        q.setParameter("endDate", endDate);
        return q.getResultList();
    }

    public List<Map<String, Object>> findOperationByAccountWithDateOrderedAsc(
            Account account, Timestamp starDate, Timestamp endDate) {
        Query q = getEntityManager()
                .createQuery(
                        getResultAttributeForQueryOperation()
                        + " from Operation o left join o.stock as s where o.account.id = :accountId "
                        + " and o.operationDate between :startDate and :endDate "
                        + "order by o.operationDate ASC");
        q.setParameter("accountId", account.getId());
        q.setParameter("startDate", starDate);
        q.setParameter("endDate", endDate);
        return q.getResultList();
    }

    public List<Map<String, Object>> findOperationByAccountWithDateOrderedDesc(
            Account account, Timestamp starDate, Timestamp endDate) {
        Query q = getEntityManager()
                .createQuery(
                        getResultAttributeForQueryOperation()
                        + " from Operation o left join o.stock as s where o.account.id = :accountId "
                        + " and o.operationDate between :startDate and :endDate "
                        + "order by o.operationDate DESC");
        q.setParameter("accountId", account.getId());
        q.setParameter("startDate", starDate);
        q.setParameter("endDate", endDate);
        return q.getResultList();
    }

    public String getResultAttributeForQueryOperation() {
        String str = "select new map(o.id as " + OperationKey.ID.toString()
        + ", o.description as " + OperationKey.DESCRIPTION.toString()
        + ", " + "o.operationDate as "
        + OperationKey.OPERATION_DATE.toString() + ", o.class as "
        + OperationKey.OPERATION_TYPE.toString() + ", o.price as "
        + OperationKey.PRICE.toString() + ", o.amount as "
        + OperationKey.AMOUNT.toString() + ", " + "s.code as "
        + OperationKey.STOCK.toString() + ", s.id as "
        + OperationKey.STOCK_ID.toString()

        + ", o.broker as " + OperationKey.BROKER.toString()

        + ", " + "s.type as " + OperationKey.STOCK_TYPE.toString()

        + ")";

        return str;
    }

    public Long findTotalByStockDate(Account account, Stock stock,
            Timestamp startDate, Timestamp endDate, Class<?> classe) {
        Query q = getEntityManager()
                .createQuery(
                        "select sum(o.amount) as totalAmout from Operation o "
                                + "left join o.stock as s where o.account.id = :accountId and s.id = :stockId "
                                + " and o.class = "
                                + classe.getName()
                                + " and o.operationDate between :startDate and :endDate");
        q.setParameter("accountId", account.getId());
        q.setParameter("stockId", stock.getId());
        q.setParameter("startDate", startDate);
        q.setParameter("endDate", endDate);
        return (Long) q.getSingleResult();
    }

    public Long findAmountOfStock(Account account, Stock stock,
            Timestamp startDate, Timestamp endDate) {
        Query q = getEntityManager()
                .createQuery(
                        "select sum(o.amount) as totalAmout from Operation o "
                                + "left join o.stock as s where o.account.id = :accountId and s.id = :stockId "
                                + " and not(o.operationDate between :startDate and :endDate)");
        q.setParameter("accountId", account.getId());
        q.setParameter("stockId", stock.getId());
        q.setParameter("startDate", startDate);
        q.setParameter("endDate", endDate);
        return (Long) q.getSingleResult();
    }

    public List<Map<String, Object>> findOperationByAccountWithDateOrderedAsc(
            Account account) {
        Query q = getEntityManager()
                .createQuery(
                        getResultAttributeForQueryOperation()
                        + " from Operation o left join o.stock as s where o.account.id = :accountId "
                        // +
                        // " and o.operationDate between :startDate and :endDate "
                        + "order by o.operationDate ASC");
        q.setParameter("accountId", account.getId());
        return q.getResultList();
    }

    public List<Integer> findYearOfOperations(Account account) {

        Query q = getEntityManager().createQuery(
                "select year(o.operationDate) " + " from "
                        + Operation.class.getName()
                        + " o where o.account.id = :accountId "
                        + " group by year(o.operationDate)"
                        // +" order by o.operationDate ASC"
                );

        q.setParameter("accountId", account.getId());
        return q.getResultList();
    }

    @Override
    public void delete(Operation operation) {

        Query removeBrokerageNoteItem = getEntityManager().createNamedQuery(
                BrokerageNoteItem.REMOVE_BY_OPERATION);
        removeBrokerageNoteItem.setParameter("operationId", operation.getId());
        removeBrokerageNoteItem.executeUpdate();

        super.delete(operation);
    }
}
