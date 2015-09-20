package com.twocents.model;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;


@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
    name="configurationtype",
    discriminatorType=DiscriminatorType.STRING
)

public class Configuration extends Persistent {
	
	private String description;
	
	public Configuration() {
		super();
	}

	
	

}
