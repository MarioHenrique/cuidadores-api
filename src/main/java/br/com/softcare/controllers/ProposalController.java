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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.softcare.dto.ProposalRequestDTO;
import br.com.softcare.dto.ProposalUpdateStatusRequestDTO;
import br.com.softcare.dto.UserInforDto;
import br.com.softcare.entities.ProcedureRecorded;
import br.com.softcare.entities.Proposal;
import br.com.softcare.entities.Symptom;
import br.com.softcare.enums.ProposalStatus;
import br.com.softcare.exceptions.NotAllowedOperationException;
import br.com.softcare.exceptions.ResourceNotFoundException;
import br.com.softcare.exceptions.UnauthorizedClientException;
import br.com.softcare.exceptions.UserNotFoundException;
import br.com.softcare.services.ProcedureService;
import br.com.softcare.services.ProposalService;
import br.com.softcare.services.SymptomService;
import io.swagger.annotations.ApiOperation;

@RestController
public class ProposalController extends Controller{

	@Autowired
	private ProposalService proposalService;
	
	@Autowired
	private ProcedureService procedureService;
	
	@Autowired
	private SymptomService symptomService;
	
	@ResponseStatus(code=HttpStatus.CREATED)
	@ApiOperation(value="Cria uma proposta",notes="Cria uma proposta pra um cuidador usando o usuario logado")
	@RequestMapping(value="/api/proposal",method=RequestMethod.POST)
	public Proposal createProposal(@RequestBody @Valid ProposalRequestDTO proposal,BindingResult result) throws UnauthorizedClientException, UserNotFoundException, NotAllowedOperationException, ResourceNotFoundException{
		verifyInvalidParam(result);
		return proposalService.create(proposal);
	}
	
	@ResponseStatus(code=HttpStatus.OK)
	@ApiOperation(value="Busca proposta",notes="Busca as propostas do usuario logado")
	@RequestMapping(value="/api/proposal",method=RequestMethod.GET)
	public List<Proposal> listAllProposal(@RequestParam(name="status",required=false) ProposalStatus status) throws UnauthorizedClientException, UserNotFoundException{
		return proposalService.findAll(status==null?ProposalStatus.PENDING:status);
	}
	
	@ResponseStatus(code=HttpStatus.OK)
	@ApiOperation(value="Busca proposta",notes="Busca uma proposta especifica")
	@RequestMapping(value="/api/proposal/{id}",method=RequestMethod.GET)
	public Proposal listOne(@PathVariable Long id) throws UnauthorizedClientException, UserNotFoundException, ResourceNotFoundException{
		return proposalService.find(id);
	}
	
	@ResponseStatus(code=HttpStatus.OK)
	@ApiOperation(value="Busca responsavel",notes="Busca o responsavel")
	@RequestMapping(value="/api/proposal/{id}/responsable",method=RequestMethod.GET)
	public UserInforDto findResponsable(@PathVariable Long id) throws UnauthorizedClientException, UserNotFoundException{
		return proposalService.findResponsable(id);
	}
	
	@ResponseStatus(code=HttpStatus.OK)
	@ApiOperation(value="Busca cuidador",notes="Busca o cuidador relacionado")
	@RequestMapping(value="/api/proposal/{id}/caregiver",method=RequestMethod.GET)
	public UserInforDto findCareGiver(@PathVariable Long id) throws UnauthorizedClientException, UserNotFoundException{
		return proposalService.findCareGiver(id);
	}
	
	@ResponseStatus(code=HttpStatus.OK)
	@ApiOperation(value="Atualização de status da proposta",notes="Na atualização do status da proposta "
    + "é levado em consideração que o cuidador so pode aceitar,negar e cancelar a proposta e o responsavel"
    + "iniciar a proposta e cancelar")
	@RequestMapping(value="/api/proposal/status",method=RequestMethod.PUT)
	public Proposal updateStatus(@RequestBody ProposalUpdateStatusRequestDTO proposalUpdate,BindingResult result) throws UnauthorizedClientException, UserNotFoundException, ResourceNotFoundException, NotAllowedOperationException{
		verifyInvalidParam(result);
		return proposalService.update(proposalUpdate);
		
	}

	@ResponseStatus(code=HttpStatus.CREATED)
	@ApiOperation(value="Criação de um sintoma",notes="O cuidador de um paciente a partir de um contrato firmado com o responsavel pode adicionar sintomas, que são acontecimentos graves que aconteceram com o paciente")
	@RequestMapping(value="/api/proposal/{id}/symptom",method=RequestMethod.POST)
	public Symptom createSymptom(@PathVariable Long id,@RequestBody @Valid Symptom symptom,BindingResult result) throws UnauthorizedClientException, UserNotFoundException, ResourceNotFoundException, NotAllowedOperationException{
		verifyInvalidParam(result);
		return symptomService.create(symptom,id);
	}
	
	@ResponseStatus(code=HttpStatus.OK)
	@ApiOperation(value="Busca os sintomas relacionado a proposta",notes="Busca todos os sintomas apresentados pelo paciente quando havia uma proposta valida")
	@RequestMapping(value="/api/proposal/{id}/symptom",method=RequestMethod.GET)
	public List<Symptom> getSymptom(@PathVariable Long id) throws UnauthorizedClientException, UserNotFoundException, ResourceNotFoundException, NotAllowedOperationException{
		return symptomService.getAll(id);
	}

	@ResponseStatus(code=HttpStatus.OK)
	@ApiOperation(value="Busca de um sintoma",notes="Realiza a busca de um sintoma")
	@RequestMapping(value="/api/proposal/{id}/symptom/{symptomId}",method=RequestMethod.GET)
	public Symptom getOneSymptom(@PathVariable Long id,@PathVariable Long symptomId) throws UnauthorizedClientException, UserNotFoundException, ResourceNotFoundException, NotAllowedOperationException{
		return symptomService.getOne(id,symptomId);
	}
	
	@ResponseStatus(code=HttpStatus.NO_CONTENT)
	@ApiOperation(value="Remove sintomas",notes="Remove um sintomado sistema")
	@RequestMapping(value="/api/proposal/{id}/symptom/{symptomId}",method=RequestMethod.DELETE)
	public void deteleSymptom(@PathVariable Long id,@PathVariable Long symptomId) throws UnauthorizedClientException, UserNotFoundException, ResourceNotFoundException, NotAllowedOperationException{
		symptomService.delete(id,symptomId);
	}
	
	@ResponseStatus(code=HttpStatus.OK)
	@ApiOperation(value="Atualiza um sintoma",notes="Atualiza um sintoma")
	@RequestMapping(value="/api/proposal/{id}/symptom/{symptomId}",method=RequestMethod.PUT)
	public Symptom updateSymptom(@PathVariable Long id,@PathVariable Long symptomId,@RequestBody @Valid Symptom symptom,BindingResult result) throws UnauthorizedClientException, UserNotFoundException, ResourceNotFoundException, NotAllowedOperationException{
		verifyInvalidParam(result);
		return symptomService.update(symptom,id,symptomId);
	}

	@ResponseStatus(code=HttpStatus.CREATED)
	@ApiOperation(value="Registra um procedimento realizado no paciente",notes="Todas as atividades realizadas no paciente deve ser registrado pelo cuidador")
	@RequestMapping(value="/api/proposal/{id}/procedure",method=RequestMethod.POST)
	public ProcedureRecorded createProcedure(@PathVariable Long id,@RequestBody @Valid ProcedureRecorded procedure,BindingResult result) throws UnauthorizedClientException, UserNotFoundException, ResourceNotFoundException, NotAllowedOperationException{
		verifyInvalidParam(result);
		return procedureService.create(id,procedure);
	}
	
	@ResponseStatus(code=HttpStatus.OK)
	@ApiOperation(value="Recupera todas os procedimentos realizados no paciente",notes="Recupera todas as atividades registradas pelo cuidador")
	@RequestMapping(value="/api/proposal/{id}/procedure",method=RequestMethod.GET)
	public List<ProcedureRecorded> listProcedure(@PathVariable Long id) throws UnauthorizedClientException, UserNotFoundException, ResourceNotFoundException, NotAllowedOperationException{
		return procedureService.list(id);
	}
	
	@ResponseStatus(code=HttpStatus.OK)
	@ApiOperation(value="Recupera um procedimento",notes="Realiza a busca de apenas um procedimento registrado no paciente")
	@RequestMapping(value="/api/proposal/{id}/procedure/{procedureId}",method=RequestMethod.GET)
	public ProcedureRecorded listProcedure(@PathVariable Long id,@PathVariable Long procedureId) throws UnauthorizedClientException, UserNotFoundException, ResourceNotFoundException, NotAllowedOperationException{
		return procedureService.getOne(id,procedureId);
	}
	
	@ResponseStatus(code=HttpStatus.NO_CONTENT)
	@ApiOperation(value="Deleta um procedimento",notes="Remove um procedimento registrado no paciente")
	@RequestMapping(value="/api/proposal/{id}/procedure/{procedureId}",method=RequestMethod.DELETE)
	public void removeProcedure(@PathVariable Long id,@PathVariable Long procedureId) throws UnauthorizedClientException, UserNotFoundException, ResourceNotFoundException, NotAllowedOperationException{
		procedureService.delete(id,procedureId);
	}
	
	@ResponseStatus(code=HttpStatus.OK)
	@ApiOperation(value="Atualiza um procedimento",notes="Realiza a atualização de um procedimento registrado no paciente")
	@RequestMapping(value="/api/proposal/{id}/procedure/{procedureId}",method=RequestMethod.PUT)
	public ProcedureRecorded updateProcedure(@PathVariable Long id,@PathVariable Long procedureId,@RequestBody @Valid ProcedureRecorded procedure,BindingResult result) throws UnauthorizedClientException, UserNotFoundException, ResourceNotFoundException, NotAllowedOperationException{
		verifyInvalidParam(result);
		return procedureService.update(id,procedureId,procedure);
	}
	
}
