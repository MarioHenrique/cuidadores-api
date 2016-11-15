package br.com.softcare.controllers;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.softcare.dto.AddressDTO;
import br.com.softcare.dto.SpecialtyToUpdateDTO;
import br.com.softcare.dto.UserDTO;
import br.com.softcare.entities.Specialty;
import br.com.softcare.enums.Availability;
import br.com.softcare.enums.Period;
import br.com.softcare.exceptions.NotAllowedOperationException;
import br.com.softcare.exceptions.ResourceNotFoundException;
import br.com.softcare.exceptions.UnauthorizedClientException;
import br.com.softcare.exceptions.UserNotFoundException;
import br.com.softcare.services.CareGiverService;
import br.com.softcare.services.SpecialtyService;
import io.swagger.annotations.ApiOperation;

@RestController
public class CareGiverController extends Controller{

	@Autowired
	private CareGiverService careGiverService;
	
	@Autowired
	private SpecialtyService specialtyService;
	
	@ResponseStatus(code=HttpStatus.OK)
	@ApiOperation(value="Busca de cuidador",notes="Realiza a busca de um cuidador no sistema")
	@RequestMapping(value="/api/caregiver/{id}",method=RequestMethod.GET)
	public UserDTO findCareGiver(@PathVariable Long id) throws ResourceNotFoundException{
		return careGiverService.find(id);
	}
	
	@ResponseStatus(code=HttpStatus.OK)
	@ApiOperation(value="Buscaa avançada de cuidador",notes="Realiza uma busca cuidadores para verificar proximidade entre o responsavel")
	@RequestMapping(value="/api/caregiver",method=RequestMethod.GET)
	public Page<UserDTO> findAll(@RequestParam(name="zipcode",required=false) String zipcode,
							  @RequestParam(name="district",required=false) String district,
							  @RequestParam(name="city",required=false) String city,
							  @RequestParam(name="street",required=false) String street,
							  @RequestParam(name="state",required=false) String state,
							  @RequestParam(name="email",required=false) String email,
							  @RequestParam(name="contact",required=false) String contact,
							  @RequestParam(name="availability",required=false) Set<Availability> availability,
							  @RequestParam(name="period",required=false) Set<Period> period,
							  @RequestParam(name="page",required=false,defaultValue="1") Integer page,
							  @RequestParam(name="pageSize",required=false,defaultValue="25") Integer pageSize){
		AddressDTO addressDTO = new AddressDTO();
		addressDTO.setZipcode(zipcode);
		addressDTO.setDistrict(district);
		addressDTO.setCity(city);
		addressDTO.setStreet(street);
		addressDTO.setState(state);
		return careGiverService.findAll(addressDTO,email,contact,availability,period,page,pageSize);
	}
	
	@ResponseStatus(code=HttpStatus.CREATED)
	@ApiOperation(value="Adiciona especalidade",notes="Realiza a inclusão de uma especialidade ao usuario logado, isso se este for um cuidador")
	@RequestMapping(value="/api/caregiver/specialty",method=RequestMethod.POST)
	public Specialty createSpecialty(@RequestBody @Valid Specialty speciality,BindingResult result) throws NotAllowedOperationException, UnauthorizedClientException, UserNotFoundException{
		verifyInvalidParam(result);
		return specialtyService.createSpecialty(speciality);
	}
	
	@ResponseStatus(code=HttpStatus.OK)
	@ApiOperation(value="Busca de especalidade",notes="Realiza a busca de todas as especialidades do usuario logado")
	@RequestMapping(value="/api/caregiver/specialty",method=RequestMethod.GET)
	public List<Specialty> getAllSpecialty() throws NotAllowedOperationException, UnauthorizedClientException, UserNotFoundException{
		return specialtyService.getAllSpecialty();
	}
	
	@ResponseStatus(code=HttpStatus.OK)
	@ApiOperation(value="Busca de especalidade",notes="Realiza a busca individual de uma especialidade")
	@RequestMapping(value="/api/caregiver/specialty/{id}",method=RequestMethod.GET)
	public Specialty getSpecialty(@PathVariable Long id) throws UnauthorizedClientException, UserNotFoundException, ResourceNotFoundException{
		return specialtyService.find(id);
	}
	
	@ResponseStatus(code=HttpStatus.NO_CONTENT)
	@ApiOperation(value="Remove uma especialidade",notes="Faz a remoção de uma especialidade")
	@RequestMapping(value="/api/caregiver/specialty/{id}",method=RequestMethod.DELETE)
	public void removeSpecialty(@PathVariable Long id) throws UnauthorizedClientException, UserNotFoundException, ResourceNotFoundException{
		specialtyService.remove(id);
	}
	
	@ResponseStatus(code=HttpStatus.OK)
	@ApiOperation(value="Atualiza uma especialidade",notes="Faz a atualização de uma especialidade")
	@RequestMapping(value="/api/caregiver/specialty/{id}",method=RequestMethod.PUT)
	public Specialty removeSpecialty(@PathVariable Long id, @RequestBody @Valid SpecialtyToUpdateDTO speciality,BindingResult result) throws UnauthorizedClientException, UserNotFoundException, ResourceNotFoundException{
		verifyInvalidParam(result);
		return specialtyService.update(id,speciality);
	}
	
	
}
