package br.com.softcare.validator;

public class StatusDenied implements StatusChange{

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
		return false;
	}

	@Override
	public boolean initialize() {
		return false;
	}

}
