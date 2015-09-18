package com.twocents.service;

import java.util.List;

import com.twocents.dao.AccountDAO;
import com.twocents.exceptions.CoreException;
import com.twocents.model.Account;

public class AccountServiceImpl extends BaseServiceImpl<Account, AccountDAO>
		implements AccountService {

	public void setAccountDAO(AccountDAO dao) {
		super.setDao(dao);
	}

	public AccountDAO getAccountDAO() {
		return super.getDao();
	}

	public List<Account> listAccountByUserId(Long userId) throws CoreException {
		return getAccountDAO().listAccountByUserId(userId);
	}

	public void removeAccount(Account account) throws CoreException {
		getAccountDAO().delete(account);
	}

	public Account getByAccountNumber(String accountNumber) {
		return getAccountDAO().findAccountByAccountNumber(accountNumber);
	}

	public List<Account> listAccountByStockBrokerId(Long id) {
		return getAccountDAO().listAccountByStockBrokerId(id);
	}

}
