package br.com.softcare.enums;

public enum Security {

	TOKEN("token");
	
	private String info;

	private Security(String info){
		this.setInfo(info);
		
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
	
}
