package br.com.softcare.validator;

public class StatusInitialized implements StatusChange{

	@Override
	public boolean accept() {
		return false;
	}

	@Override
	public boolean denie() {
		return false;
	}

	@Override
	public boolean cancel() {
		return true;
	}

	@Override
	public boolean initialize() {
		return false;
	}
	
	@Override
	public boolean finish() {
		return true;
	}

}
