package com.twocents.ui.client.components.listener;

import com.twocents.model.Account;

public interface UpdateUserAccountListener {
	
	public void onAccountUpdate(Account acount);
	
	public void onAccountRemove(Account acount);
	
}
