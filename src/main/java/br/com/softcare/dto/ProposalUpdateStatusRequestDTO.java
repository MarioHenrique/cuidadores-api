package br.com.softcare.dto;

import javax.validation.constraints.NotNull;
import br.com.softcare.enums.ProposalStatus;

public class ProposalUpdateStatusRequestDTO {

	@NotNull
	private Long proposalId;
	
	@NotNull
	private ProposalStatus status;

	public Long getProposalId() {
		return proposalId;
	}

	public void setProposalId(Long proposalId) {
		this.proposalId = proposalId;
	}

	public ProposalStatus getStatus() {
		return status;
	}

	public void setStatus(ProposalStatus status) {
		this.status = status;
	}
	
}
