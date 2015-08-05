package br.ages.crud.command;

import javax.servlet.http.HttpServletRequest;

import br.ages.crud.bo.PessoaBO;
import br.ages.crud.exception.NegocioException;

public class RemovePessoaCommand implements Command {

	private String proximo;
	
	private PessoaBO pessoaBO;

	public String execute(HttpServletRequest request) {
		proximo = "main?acao=list";
		this.pessoaBO = new PessoaBO();
		
		try {
			Integer idPessoa = Integer.parseInt(request.getParameter("id_pessoa"));
			pessoaBO.removerPessoa(idPessoa);
		} catch (NegocioException e) {
			request.setAttribute("msgErro", e.getMessage());
		}
		
		return proximo;
	}

}
