package com.twocents.dao;

import java.util.List;

import com.twocents.model.Account;

public interface AccountDAO extends BaseDAO<Account> {

	public Account findAccountByAccountNumber(String accountNumber);

	public List<Account> listAccountByUserId(Long userId);

	public List<Account> listAccountByStockBrokerId(Long stockBrokerId);

}
