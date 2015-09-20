package com.twocents.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Document extends Persistent {

	private static final long serialVersionUID = 1L;

	private String name;
	private String mimeType;
	private byte[] content;
	private Operation operation;

	public Document() {
	}

	@Column(nullable=false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	@Column(nullable=false)
	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] fileContent) {
		this.content = fileContent;
	}

	@ManyToOne
	@JoinColumn(nullable=false)
	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	
}
