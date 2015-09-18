package com.twocents.test;

import com.twocents.repository.gmail.FetchMail;

public class Test02 {

	
	public static void  main(String args[]){
		
		FetchMail fm=new FetchMail();
        try {
            fm.receive("pop.gmail.com","twocents.suporte@gmail.com","hpcomplab");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
	
	}
}
