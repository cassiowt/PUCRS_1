package br.ages.crud.dto;

import java.io.Serializable;

public class CidadeDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idCidade;

	private String descricao;

	private UfDTO ufDTO;

	public CidadeDTO() {
	}
	public CidadeDTO(Integer idCidade, String descricao) {
		super();
		this.idCidade = idCidade;
		this.descricao = descricao;
	}


	public Integer getIdCidade() {
		return idCidade;
	}

	public void setIdCidade(Integer idCidade) {
		this.idCidade = idCidade;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public UfDTO getUfDTO() {
		return ufDTO;
	}

	public void setUfDTO(UfDTO ufDTO) {
		this.ufDTO = ufDTO;
	}

}
