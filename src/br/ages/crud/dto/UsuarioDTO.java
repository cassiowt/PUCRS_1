package br.ages.crud.dto;

import java.io.Serializable;

/**
 * Data Transfer Objeto Usuario
 * @author cassio
 *
 */
public class UsuarioDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Integer idUsuario;
	private String usuario;
	private String senha;
	public Integer getIdUsuario() {
		return idUsuario;
	}
	
	public UsuarioDTO() {
	}
	
	public UsuarioDTO(String usuario, String senha) {
		super();
		this.usuario = usuario;
		this.senha = senha;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	
}
