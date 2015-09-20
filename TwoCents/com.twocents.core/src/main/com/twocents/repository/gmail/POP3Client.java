package com.twocents.repository.gmail;




import com.messners.mail.MailStatusEvent;
import com.messners.mail.MessagePart;
import com.messners.mail.POP3;
import com.messners.mail.POP3MailMessage;
import com.messners.mail.POP3MailboxInfo;
import com.messners.mail.POP3MessageInfo;
import com.messners.mail.POP3StatusListener;

/**
 * This class tests the com.messners.mail.POP3 mail classes.
 */
class POP3Client implements POP3StatusListener {

	public static void main (String args[]) {
		
		String host = "pop.gmail.com";
		String username = "twocents.suporte";
		String password = "hpcomplab";

	   	try {
			POP3 pop3 = new POP3();

			/*
			 * Set up a listener for the POP3 commands and responses
			 */
			pop3.addStatusListener(new POP3Client());

			/*
			 * Connect and login to the POP3 server
			 */
			pop3.connect(host);
			pop3.login(username, password);

			/*
			 * Get the mailbox status (returns an object
			 * which contains the count of messages and the
			 * size in bytes of the mailbox)
			 */
			POP3MailboxInfo mailbox_info = pop3.status();

			if (mailbox_info.getMessageCount() < 1) {
				System.out.println("No messages on server");
				//EnvironmentAction.exit();
			}


			/*
			 * Get the information on the messages and retrieve the
			 * content of each message.
			 */
			POP3MessageInfo msg_info[] = pop3.listMessages();

			for (int i = 0; i < msg_info.length; i++) {

				POP3MailMessage msg = 
					pop3.retrieveMessage(msg_info[i]);

				System.out.println("\nMessage: " + i);
				dump(msg);

			}

			/*
			 * Logout and disconnect from the POP3 server
			 */
			pop3.logout();
			pop3.disconnect();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	static void dump (POP3MailMessage msg) throws Exception {

		msg.dumpHeader();
		MessagePart msgs[] = msg.getBody();
		for (int i = 0; i < msgs.length; i++) {
			System.out.println("");
			System.out.println("content-type: " +
				msgs[i].getContentType());

			if (msgs[i].getType() == MessagePart.TEXT) {
				System.out.println("charset: " +
					msgs[i].getCharset());
				System.out.println(msgs[i].getText());
			} else {
				System.out.println("name: " +
					msgs[i].getName());
				System.out.println("filename: " +
					msgs[i].getFilename());
				System.out.println("encoding: " +
					msgs[i].getEncoding());
			}
		}
	}

	/*
	 * This class simply implements the pop3Status() method 
	 * for MailStatusEvents
	 */ 
	private POP3Client () {
	}

	/*
	 * This method is the method called when commands are sent to and 
	 * responses read from the POP3 server.
	 */
	int last_percent = -1;
	public void pop3Status (MailStatusEvent evt) {

		if (evt.getType() == MailStatusEvent.PROGRESS) {
			int percent = (evt.getTotalBytes() * 100 + 99) / 
				evt.getMessageSize();
			if (last_percent != percent) {
				last_percent = percent;
				if (percent > 99) {
					System.err.println(".");
				} else {
					System.err.print(".");
				}
			}
		} else if (evt.getType() == MailStatusEvent.COMMAND) {
			System.err.println("<-- " + evt.getMessage());
		} else {
			System.err.println("--> " + evt.getMessage());
		}
	}
}



