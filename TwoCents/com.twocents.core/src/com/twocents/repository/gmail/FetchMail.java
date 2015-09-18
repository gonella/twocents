package com.twocents.repository.gmail;

 
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
 
 
public class FetchMail {
    public static void receive(String popServer, String popUser, String popPassword)
    throws Exception {
        Store store=null;
        Folder folder=null;
 
        try {
        	System.out.println("Starting the FetchMail");
            Properties props = System.getProperties();
            props.setProperty( "mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.setProperty( "mail.pop3.socketFactory.fallback", "false");
            props.setProperty("mail.pop3.port", "995");
            props.setProperty("mail.pop3.socketFactory.port", "995");
            Session session = Session.getInstance(props);
 
            store = session.getStore("pop3");
            store.connect(popServer, popUser, popPassword);
 
            folder = store.getDefaultFolder();
            if (folder == null) throw new Exception("No default folder");
 
            folder = folder.getFolder("INBOX");
            if (folder == null) {
                throw new Exception("No POP3 INBOX");
            }
 
            folder.open(Folder.READ_ONLY);
 
            Message[] msgs = folder.getMessages();
        	System.out.println((msgs!=null)?msgs.length:"Nada");
            for (int msgNum = 0; msgNum < msgs.length; msgNum++) {
                String subject=msgs[msgNum].getSubject();;
                
                printMessage(msgs[msgNum]);
                //msg.invalidate(true);
            }
 
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (folder!=null) folder.close(false);
                if (store!=null) store.close();
            } catch (Exception ex2) {
                ex2.printStackTrace();
            }
        }
    }
 
 
    public static void printMessage(Message message) {
        try {
            String from = ((InternetAddress)message.getFrom()[0]).getPersonal();
            if (from==null) {
                from = ((InternetAddress)message.getFrom()[0]).getAddress();
            }
            System.out.println("FROM: "+from);
 
            String subject = message.getSubject();
            System.out.println("SUBJECT: "+subject);
 
            
            Part messagePart = message;
            Object content = messagePart.getContent();
 
			
				if (content instanceof Multipart)
				{
					 messagePart=((Multipart)content).getBodyPart(0);
				}
            				
			    String contentType = messagePart.getDisposition();
 
				if ((contentType != null) && ((contentType.equals(Part.ATTACHMENT)  || contentType.equals(Part.INLINE)))) 
				{
					String fileName = messagePart.getFileName();
					System.out.println("File name :"+fileName);
					 
					ByteArrayOutputStream bos=new ByteArrayOutputStream();
					InputStream ip = messagePart.getInputStream();
				   
					byte[] buffer=new byte[8192];
					int count=0; 
					while((count=ip.read(buffer))>=0)
						bos.write(buffer,0,count);
					ip.close();
						         
				}
			
            
            System.out.println("-----------------------------");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
   
}
