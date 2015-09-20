package com.twocents.service;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.twocents.exceptions.CoreException;
import com.twocents.model.Account;

@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
		Throwable.class, CoreException.class })
public interface AccountService extends BaseService<Account> {

	String BEAN_NAME = "com.twocents.service.AccountService";

	public List<Account> listAccountByUserId(Long userId) throws CoreException;

	public void removeAccount(Account account) throws CoreException;

	public Account getByAccountNumber(String accountNumber);

	public List<Account> listAccountByStockBrokerId(Long id);

}
