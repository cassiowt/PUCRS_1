package br.ages.crud.dto;

import java.io.Serializable;

public class EnderecoDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Integer idEndereco;
	
	private String Logradouro;
	
	private CidadeDTO cidade;

	public Integer getIdEndereco() {
		return idEndereco;
	}

	public void setIdEndereco(Integer idEndereco) {
		this.idEndereco = idEndereco;
	}

	public String getLogradouro() {
		return Logradouro;
	}

	public void setLogradouro(String logradouro) {
		Logradouro = logradouro;
	}

	public CidadeDTO getCidade() {
		return cidade;
	}

	public void setCidade(CidadeDTO cidadeDTO) {
		this.cidade = cidadeDTO;
	}
	
	
}
