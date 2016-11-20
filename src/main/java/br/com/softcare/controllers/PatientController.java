package br.com.softcare.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.softcare.dto.TreatmentUpdateDTO;
import br.com.softcare.entities.Patient;
import br.com.softcare.entities.Treatment;
import br.com.softcare.exceptions.NotAllowedOperationException;
import br.com.softcare.exceptions.ResourceNotFoundException;
import br.com.softcare.exceptions.UnauthorizedClientException;
import br.com.softcare.exceptions.UserNotFoundException;
import br.com.softcare.services.PatientService;
import br.com.softcare.services.TreatmentService;
import io.swagger.annotations.ApiOperation;

@RestController
public class PatientController extends Controller{

	@Autowired
	private PatientService patientService;
	
	@Autowired 
	private TreatmentService treatmentService;
	
	@ResponseStatus(code=HttpStatus.OK)
	@ApiOperation(value="Inclusão de paciente no sistema",notes="Após estar logado no sistema o responsavel pode cadastrar os pacientes no sistema")
	@RequestMapping(value="/api/patient",method=RequestMethod.POST)
	public Patient create(@RequestBody @Valid Patient patient,BindingResult result) throws UnauthorizedClientException, UserNotFoundException, NotAllowedOperationException{
		verifyInvalidParam(result);
		return patientService.create(patient);
	}
	
	@ResponseStatus(code=HttpStatus.OK)
	@ApiOperation(value="Lista dos pacientes",notes="Lista todos os pacientes do usuario logado")
	@RequestMapping(value="/api/patient",method=RequestMethod.GET)
	public List<Patient> listAll() throws UserNotFoundException, UnauthorizedClientException{
		return patientService.findAll();
	}
	
	@ResponseStatus(code=HttpStatus.OK)
	@ApiOperation(value="Pesquisa um paciente",notes="Realiza um pesquisa a um paciente pelo id, é apenas possivel pesquisar paciente que tenha cadastrado")
	@RequestMapping(value="/api/patient/{id}",method=RequestMethod.GET)
	public Patient findById(@PathVariable Long id) throws UnauthorizedClientException, UserNotFoundException, ResourceNotFoundException{
		return patientService.findById(id);
	}
	
	@ResponseStatus(code=HttpStatus.NO_CONTENT)
	@ApiOperation(value="Deleta um paciente",notes="Deleta um paciente a partir de um id passado")
	@RequestMapping(value="/api/patient/{id}",method=RequestMethod.DELETE)
	public void deleteById(@PathVariable Long id) throws UnauthorizedClientException, UserNotFoundException, ResourceNotFoundException{
		patientService.deleteById(id);
	}
	
	@ResponseStatus(code=HttpStatus.OK)
	@ApiOperation(value="Atualiza os dados de um paciente",notes="Atualiza os dados de um determinado paciente, apenas o criador do paciente pode alterar suas informações")
	@RequestMapping(value="/api/patient/{id}",method=RequestMethod.PUT)
	public Patient update(@RequestBody @Valid Patient patient,BindingResult result,@PathVariable Long id) throws UnauthorizedClientException, UserNotFoundException, ResourceNotFoundException{
		verifyInvalidParam(result);
		return patientService.update(patient,id);
	}
	
	@ResponseStatus(code=HttpStatus.CREATED)
	@ApiOperation(value="Cria um tratamento",notes="Criação da lista de tratamentos que devem ser submetidos no paciente")
	@RequestMapping(value="/api/patient/{id}/treatment",method=RequestMethod.POST)
	public Treatment createTreatment(@PathVariable Long id,@RequestBody @Valid Treatment treatment,BindingResult result) throws UnauthorizedClientException, UserNotFoundException, ResourceNotFoundException{
		return treatmentService.create(treatment,id);
	}
	
	@ResponseStatus(code=HttpStatus.OK)
	@ApiOperation(value="Atualiza os dados de um paciente",notes="Atualiza os dados de um determinado paciente, apenas o criador do paciente pode alterar suas informações")
	@RequestMapping(value="/api/patient/{id}/treatment",method=RequestMethod.GET)
	public List<Treatment> createTreatment(@PathVariable Long id) throws UnauthorizedClientException, UserNotFoundException, ResourceNotFoundException{
		return treatmentService.getTreatmentByPatientId(id);
	}
	
	@ResponseStatus(code=HttpStatus.OK)
	@ApiOperation(value="Atualiza um tratamento",notes="Atualiza um tratamento do paciente")
	@RequestMapping(value="/api/patient/{id}/treatment/{treatmentId}",method=RequestMethod.PUT)
	public Treatment updateTreatment(@PathVariable Long id,@PathVariable Long treatmentId,@RequestBody @Valid TreatmentUpdateDTO treatment,BindingResult result) throws UnauthorizedClientException, UserNotFoundException, ResourceNotFoundException{
		return treatmentService.update(treatment,id,treatmentId);
	}
	
	@ResponseStatus(code=HttpStatus.OK)
	@ApiOperation(value="Busca tratamento",notes="Busca um determinado tratamento")
	@RequestMapping(value="/api/patient/{id}/treatment/{treatmentId}",method=RequestMethod.GET)
	public Treatment findOneTreatment(@PathVariable Long id,@PathVariable Long treatmentId) throws UnauthorizedClientException, UserNotFoundException, ResourceNotFoundException{
		return treatmentService.findOne(id,treatmentId);
	}
	
	@ResponseStatus(code=HttpStatus.NO_CONTENT)
	@ApiOperation(value="Deleta tratamento",notes="Deleta um tratamento de um paciente")
	@RequestMapping(value="/api/patient/{id}/treatment/{treatmentId}",method=RequestMethod.DELETE)
	public void deteleTreatment(@PathVariable Long id,@PathVariable Long treatmentId) throws UnauthorizedClientException, UserNotFoundException, ResourceNotFoundException{
		treatmentService.deletePatient(id,treatmentId);
	}
	
}
