package com.twocents.dao;

import java.util.List;

import javax.persistence.Query;

import com.twocents.model.Account;
import com.twocents.model.BrokerageNote;
import com.twocents.model.BrokerageNoteItem;
import com.twocents.model.Custody;
import com.twocents.model.Operation;
import com.twocents.model.TwUser;

public class AccountHibernateDAO extends BaseDAOImpl<Account> implements
		AccountDAO {

	public Account findAccountByAccountNumber(String accountNumber) {
		Query q = getEntityManager().createNamedQuery(
				Account.FIND_ACCOUNT_BY_ACCOUNT_NUMBER);
		q.setParameter("accountNumber", accountNumber);
		return (Account) q.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Account> listAccountByUserId(Long userId) {
		Query q = getEntityManager().createNamedQuery(
				Account.FIND_ACCOUNT_BY_USER_ID);
		q.setParameter("userId", userId);
		return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Account> listAccountByStockBrokerId(Long stockBrokerId) {
		Query q = getEntityManager().createNamedQuery(
				Account.FIND_ACCOUNT_BY_STOCKBROKER_ID);
		q.setParameter("stockBrokerId", stockBrokerId);
		return q.getResultList();
	}	

	@Override
	public void delete(Account account) {
		Query removeUser = getEntityManager().createNamedQuery(
				TwUser.REMOVE_BY_ACCOUNT_NUMBER);
		removeUser.setParameter("accountNumber", account.getNumber());
		removeUser.executeUpdate();

		Query removeBrokerageNoteItem = getEntityManager().createNamedQuery(
				BrokerageNoteItem.REMOVE_BY_ACCOUNT);
		removeBrokerageNoteItem.setParameter("accountId", account.getId());
		removeBrokerageNoteItem.executeUpdate();
		
		Query removeOperations = getEntityManager().createNamedQuery(
				Operation.REMOVE_BY_ACCOUNT);
		removeOperations.setParameter("accountId", account.getId());
		removeOperations.executeUpdate();

		Query removeBrokerageNote = getEntityManager().createNamedQuery(
				BrokerageNote.REMOVE_BY_ACCOUNT);
		removeBrokerageNote.setParameter("accountId", account.getId());
		removeBrokerageNote.executeUpdate();


		Query removeCustody = getEntityManager().createNamedQuery(
				Custody.REMOVE_CUSTODY_BY_ACCOUNT);
		removeCustody.setParameter("accountId", account.getId());
		removeCustody.executeUpdate();

		Query removeAccount = getEntityManager().createNamedQuery(
				Account.REMOVE_BY_ACCOUNT_ID);
		removeAccount.setParameter("id", account.getId());
		removeAccount.executeUpdate();

	}

}
