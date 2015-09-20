package com.twocents.model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.SQLException;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@javax.persistence.MappedSuperclass
public abstract class Persistent implements Serializable {

	private Long id;

	public Persistent() {
		super();
	}

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}



	protected byte[] toByteArray(Blob fromImageBlob) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			return toByteArrayImpl(fromImageBlob, baos);
		} catch (Exception e) {
		}
		return null;
	}

	protected byte[] toByteArrayImpl(Blob fromImageBlob, 
			ByteArrayOutputStream baos) throws SQLException, IOException {
		byte buf[] = new byte[4000];
		int dataSize;
		InputStream is = fromImageBlob.getBinaryStream(); 

		try {
			while((dataSize = is.read(buf)) != -1) {
				baos.write(buf, 0, dataSize);
			}    
		} finally {
			if(is != null) {
				is.close();
			}
		}
		return baos.toByteArray();
	}


}
