package com.twocents.model;

public enum BrokerType {
	
	BANIFINVEST("BanifInvest"),
	SOCOPA("SOCOPA");
	
	private String Type;
	
	private BrokerType(String Type) {
		this.Type = Type;
	}
	
	public String toString(){
		return this.Type;
	}
	
	public static BrokerType toType(String str){
		BrokerType type=null;
		BrokerType[] values = values();
		
		for (BrokerType brokerType : values) {
			if(brokerType.toString().equals(str)){
				type = brokerType;
			}
		}
		return type;
	}
}
