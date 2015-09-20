package com.twocents.repository.gmail.zip;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

class  ZipUtilities {
    
	byte[] buf = new byte[1024];
	
	private static final int BUFFER=1024;
	
	public static void doUnzip(String fileNameToUnzip){
		
	}
	
	public static void doZip(String[] filename,String zipfilename) throws Exception{
		        
        CRC32 crc = new CRC32();
        
        byte b[] = new byte[BUFFER];
        ZipOutputStream zout = new ZipOutputStream((OutputStream)new FileOutputStream(zipfilename));
        zout.setLevel(6);
        crc.reset();
        for(int i = 0; i < filename.length; i ++) {
        	InputStream in = new FileInputStream(filename[i]);
        	ZipEntry e = new ZipEntry(filename[i]);
        	zout.putNextEntry(e);
            
        	int len=0;
        	while((len=in.read(b)) != -1) {
        		zout.write(b,0,len);
            }
        	zout.closeEntry();
        }
        zout.finish();
        zout.close();
    }
	
    public static void doZip(String filename,String zipfilename) throws Exception{
    	byte[] buf = new byte[1024];
        FileInputStream fis = new FileInputStream(filename);
        fis.read(buf,0,buf.length);
            
        CRC32 crc = new CRC32();
        ZipOutputStream s = new ZipOutputStream(
                    (OutputStream)new FileOutputStream(zipfilename));
            
        s.setLevel(6);
            
        ZipEntry entry = new ZipEntry(filename);
        entry.setSize((long)buf.length);
        crc.reset();
        crc.update(buf);
        entry.setCrc( crc.getValue());
        s.putNextEntry(entry);
        s.write(buf, 0, buf.length);
        s.finish();
        s.close();
    }
}