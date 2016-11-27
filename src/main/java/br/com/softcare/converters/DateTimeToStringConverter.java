package br.com.softcare.converters;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;

public class DateTimeToStringConverter implements Converter<Date,String>{

	@Override
	public String convert(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		return sdf.format(date);
	}

	@Override
	public JavaType getInputType(TypeFactory type) {
		return type.constructType(Date.class);
	}

	@Override
	public JavaType getOutputType(TypeFactory type) {
		return type.constructType(String.class);
	}

}
