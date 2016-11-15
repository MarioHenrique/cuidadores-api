package br.com.softcare.validator;

public interface StatusChange {

	public boolean accept();
	public boolean denie();
	public boolean cancel();
	public boolean initialize();
	
}
