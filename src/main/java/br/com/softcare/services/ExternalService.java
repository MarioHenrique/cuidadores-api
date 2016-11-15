package br.com.softcare.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import br.com.softcare.dto.AddressDTO;

@Service
public class ExternalService {

	private RestTemplate restTemplate = new RestTemplate(); 
	
	public AddressDTO getAddress(String cep){
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Accept",MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<String> httpEntity = new HttpEntity<String>(httpHeaders);
		try{
			ResponseEntity<AddressDTO> exchange = restTemplate.exchange("http://api.postmon.com.br/v1/cep/{cep}", HttpMethod.GET, httpEntity, AddressDTO.class,cep);
			return exchange.getBody();
		}catch(RestClientException e){
			return null;
		}
	}
	
}
