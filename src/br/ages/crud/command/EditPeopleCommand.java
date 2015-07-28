package br.ages.crud.command;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import br.ages.crud.bo.PessoaBO;
import br.ages.crud.dao.PessoaDAO;
import br.ages.crud.dto.CidadeDTO;
import br.ages.crud.dto.PessoaDTO;
import br.ages.crud.exception.NegocioException;
import br.ages.crud.exception.PersistenciaException;

public class EditPeopleCommand implements Command {

	private String proximo;
	
	private PessoaBO pessoaBO;
	
	private PessoaDAO cadastroDAO;

	public String execute(HttpServletRequest request) {
		proximo = "editPeople.jsp";
		this.cadastroDAO = new PessoaDAO();
		this.pessoaBO = new PessoaBO();
		
		try {
			Integer idPessoa = Integer.parseInt(request.getParameter("id_pessoa"));
			PessoaDTO pessoa = pessoaBO.consultarPessoaPorId(idPessoa);
			
			Integer idUF = pessoa.getEndereco().getCidade().getUfDTO().getIdUF();
			List<CidadeDTO> listaCidades = cadastroDAO.consultarCidadesPorUf(idUF);
			request.setAttribute("listaCidades", listaCidades);
			
			request.setAttribute("pessoa", pessoa);
		} catch (NegocioException | PersistenciaException | SQLException e) {
			request.setAttribute("msgErro", e.getMessage());
		}
		
		return proximo;
	}

}
