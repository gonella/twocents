package com.twocents.test;

import com.twocents.repository.gmail.GmailUtilities;

public class Test01 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	
		//http://www.guj.com.br/posts/list/87637.java
		//http://jgmail.sourceforge.net/
		
		/*
		 FetchMail fm=new FetchMail();
	        try {
	            fm.receive("pop.gmail.com","twocents.suporte","hpcomplab");
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
		*/
		try {
            System.out.println("Gmail Utilities");
            String username="twocents.suporte@gmail.com";
            String password="hpcomplab";
            GmailUtilities gmail = new GmailUtilities(username,password);
            gmail.connect();
            
            /*     
            int totalMessages = gmail.getMessageCount();
            int newMessages = gmail.getNewMessageCount();
            
            System.out.println("Total messages = " + totalMessages);
            System.out.println("New messages = " + newMessages);
            System.out.println("-------------------------------");
            */
            gmail.printResultSearchForMessage("gonella");
            //gmail.printAllMessageEnvelopes();
            //gmail.printAllMessages();
            
        } catch(Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
	        
	        

	}

}
