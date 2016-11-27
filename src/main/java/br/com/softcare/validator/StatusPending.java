package br.com.softcare.validator;

public class StatusPending implements StatusChange{

	@Override
	public boolean accept() {
		return true;
	}

	@Override
	public boolean denie() {
		return true;
	}

	@Override
	public boolean cancel() {
		return false;
	}

	@Override
	public boolean initialize() {
		return false;
	}
	
	@Override
	public boolean finish() {
		return false;
	}

}
