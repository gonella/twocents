package com.twocents.core.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicMatch;

import org.apache.log4j.Logger;

import com.twocents.exceptions.CoreException;

public class ResourceUtil {
	
	private static Logger logger = Logger.getLogger(ResourceUtil.class);
	
	public static final String UNKNOWN_MIME_TYPE="application/x-unknown-mime-type";
	
	public static byte[] getBytesFromFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);
    
        // Get the size of the file
        long length = file.length();
    
        if (length > Integer.MAX_VALUE) {
            // File is too large
        }
    
        // Create the byte array to hold the data
        byte[] bytes = new byte[(int)length];
    
        // Read in the bytes
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
               && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
            offset += numRead;
        }
    
        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file "+file.getName());
        }
    
        // Close the input stream and return bytes
        is.close();
        return bytes;
    }


	public static String getMimeType(byte[] file) {
		try {
			MagicMatch match = Magic.getMagicMatch(file, true);
			return match.getMimeType();
		} catch (Exception e) {
			logger.error(e);
			return UNKNOWN_MIME_TYPE;
		} 
	}
	
	/*public static File getFileByURL(String pathWithFileName) throws CoreException{
		logger.debug("Core: Getting the file by URL :"+pathWithFileName);
	
		URL resource;
		try{
			resource = ClassLoader.getSystemResource(pathWithFileName);			
			String decode = UrlUtil.decode(resource.getFile());
			
			File file=new File(decode);
			if(file!=null){				
				if(file.exists()){
					
					logger.info("Opening file: "+decode);
					return file;
				}
			}
			else{
				logger.info("File could not be find");
				throw new CoreException(6001);
			}
		
			return file;
			
		}catch(Exception e){
			logger.error("Opening file failed ",e);
			throw new CoreException(6001);
		}		
	}*/
	
	public static URL getURL(String pathWithFileName) throws CoreException{
		logger.debug("Core: Getting the url path :"+pathWithFileName);
	
		URL resource;
		try{
			resource = ClassLoader.getSystemResource(pathWithFileName);			
			return resource;
		}catch(Exception e){
			logger.error("Opening file failed ",e);
			throw new CoreException(6001);
		}	
	}
		
	public static InputStream getResourceAsStream(String pathWithFileName) throws CoreException{
		logger.debug("Core: Getting the path as stream :"+pathWithFileName);
		
		InputStream systemResourceAsStream=null;
		try{
			systemResourceAsStream = ClassLoader.getSystemResourceAsStream(pathWithFileName);
			return systemResourceAsStream;
		}catch(Exception e){
			logger.error("Opening file failed ",e);
			throw new CoreException(6001);
		}
	}
	
	public static File getFile(String pathWithFileName) throws CoreException{
		logger.debug("Core: Getting the file :"+pathWithFileName+" based on root context");
		
		File file=null;
		try{
			file=new File(pathWithFileName);
			if(file!=null){				
				if(file.exists()){
					logger.info("Opening file: "+file.getPath());
					return file;
				}
			}
			else{
				logger.info("File could not be find");
				throw new CoreException(6001);
			}
		}catch(Exception e){
			logger.error("Opening file failed ",e);
			throw new CoreException(6001);
		}		
		return file;
	}
	
	public static String getContents(File aFile) throws CoreException {
		StringBuffer contents = new StringBuffer();

		try {
			BufferedReader input = new BufferedReader(new FileReader(aFile));
			try {
				String line = null;
				while ((line = input.readLine()) != null) {
					contents.append(line);
					//contents.append(System.getProperty("line.separator"));
				}
			} finally {
				input.close();
			}
		} catch (IOException ex) {
			throw new CoreException(6000);
		}
		return contents.toString();
	}
	
	public static boolean saveContentsToFile(String filename,String content) throws CoreException {
		
		try {
			logger.info("Saving content to file :"+filename);
			FileWriter file = new FileWriter(filename);			
			BufferedWriter output = new BufferedWriter(file);
			try {
				output.write(content);
			} finally {
				output.close();
			}
			return true;
		} catch (IOException ex) {
			throw new CoreException(6000);
		}
	}
	
	/**
	 * Vai pegar o arquivo baseado no root da aplicação
	 * @param fileName
	 * @return
	 */
	public static InputStream getInputStream(String fileName){
		InputStream ret=null;
		
		File file=new File(fileName);
		try {
			if(file!=null && file.exists()){
				ret=new FileInputStream(file);				
			}else{
				logger.info("Não foi possível encontar o arquivo: "+fileName);
			}
		} catch (FileNotFoundException e) {
			logger.error("Não foi possível encontar o arquivo: "+fileName);
		}
		return ret;
	}
}
