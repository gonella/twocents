package com.twocents.repository.gmail;


import com.messners.mail.*;

/**
 * This class tests the com.messners.mail.SMTP mail classes.
 */
class SMTPClient implements SMTPStatusListener {

	public static void main (String args[]) {

		/*
		 * Get the command line parameters
		 */
		if (args.length < 5) {
			System.err.println(
				"usage: SMTPClient host from to subject body " +
					"[attachments]");
			System.exit(1);
		}

		String host = args[0];
		String from = args[1];
		String to   = args[2];
		String subject  = args[3];
		String body = args[4];

		

	   	try {
			SMTP smtp = new SMTP(host);
			smtp.addStatusListener(new SMTPClient());

			/*
			 * Put our parameters into the message
			 */
			smtp.setFrom(null, from);
			smtp.setSubject(subject);
			smtp.addTo(null, to); 
			smtp.setBody(body);

			for (int i = 5; i < args.length; i++) {
				smtp.addAttachment(args[i]);
			}

			/*
			 * Now send the message
			 */
			smtp.send();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * This class simply implements the smtpStatus () method 
	 * of SMTPStatusListener.
	 */ 
	private SMTPClient () {
	}

	/*
	 * This method is the method called when commands are sent to and 
	 * responses read from the SMTP server.
	 */
	public void smtpStatus (MailStatusEvent evt) {

		if (evt.getType() == MailStatusEvent.COMMAND) {
			System.out.println("<-- " + evt.getMessage());
		} else {
			System.out.println("--> " + evt.getMessage());
		}
	}
}



