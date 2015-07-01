package br.ages.crud.dto;

import java.io.Serializable;

public class UfDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idUF;

	private String sigla;

	private String descricao;
	
	public UfDTO() {
	}

	public UfDTO(int idUF, String descricao) {
		this.idUF = idUF;
		this.descricao = descricao;
	}

	public UfDTO(String sigla, String descricao) {
		
		this.sigla = sigla;
		this.descricao = descricao;
	}

	public UfDTO(int idUF) {
		this.idUF = idUF;
	}

	public Integer getIdUF() {
		return idUF;
	}

	public void setIdUF(Integer idUF) {
		this.idUF = idUF;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
