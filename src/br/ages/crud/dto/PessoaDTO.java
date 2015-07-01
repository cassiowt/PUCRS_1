package br.ages.crud.dto;

import java.io.Serializable;
import java.util.List;

public class PessoaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idPessoa;

	private String nome;

	private String email;

	private String cpf;

	private String  dataNascimento;

	private String sexo;

	private List<PreferenciaDTO> preferencias;

	private String miniBio;
	
	private EnderecoDTO endereco;
	
	public PessoaDTO() {
	}

	public Integer getIdPessoa() {
		return idPessoa;
	}

	public void setIdPessoa(Integer idPessoa) {
		this.idPessoa = idPessoa;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}



	public List<PreferenciaDTO> getPreferencias() {
		return preferencias;
	}

	public void setPreferencias(List<PreferenciaDTO> preferencias) {
		this.preferencias = preferencias;
	}

	public String getMiniBio() {
		return miniBio;
	}

	public void setMiniBio(String miniBio) {
		this.miniBio = miniBio;
	}

	public EnderecoDTO getEndereco() {
		return endereco;
	}

	public void setEndereco(EnderecoDTO endereco) {
		this.endereco = endereco;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	
	
}
