package br.ages.crud.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import br.ages.crud.bo.PessoaBO;
import br.ages.crud.dto.PessoaDTO;
import br.ages.crud.exception.NegocioException;

public class ListPeopleCommand implements Command {

	private String proxima;

	private PessoaBO pessoaBO;

	@Override
	public String execute(HttpServletRequest request) {

		this.pessoaBO = new PessoaBO();
		proxima = "list.jsp";

		try {
			List<PessoaDTO> listaPessoas = pessoaBO.listarPessoa();
			request.setAttribute("listaPessoas", listaPessoas);
		} catch (NegocioException e) {
			e.printStackTrace();
			request.setAttribute("msgErro", e.getMessage());
		}

		return proxima;
	}
}
