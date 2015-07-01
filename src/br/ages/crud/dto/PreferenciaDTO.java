package br.ages.crud.dto;

import java.io.Serializable;

public class PreferenciaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idPreferencia;

	private String descricao;

	public PreferenciaDTO() {
	}
	
	public PreferenciaDTO(Integer idPreferencia, String descricao) {
		this.idPreferencia = idPreferencia;
		this.descricao = descricao;
	}

	public Integer getIdPreferencia() {
		return idPreferencia;
	}

	public void setIdPreferencia(Integer idPreferencia) {
		this.idPreferencia = idPreferencia;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
