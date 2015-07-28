package br.ages.crud.command;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import br.ages.crud.bo.PessoaBO;
import br.ages.crud.dto.CidadeDTO;
import br.ages.crud.dto.EnderecoDTO;
import br.ages.crud.dto.PessoaDTO;
import br.ages.crud.dto.PreferenciaDTO;
import br.ages.crud.dto.UfDTO;
import br.ages.crud.exception.NegocioException;
import br.ages.crud.util.MensagemContantes;

public class UpdatePeopleCommand implements Command {

	private String proxima;
	
	private PessoaBO pessoaBO;

	public String execute(HttpServletRequest request) throws SQLException {
		String idPessoa = (String) request.getParameter("id_pessoa");
		proxima = "main?acao=editPeople&id_pessoa=" + idPessoa;
		this.pessoaBO = new PessoaBO();
		
		String nome = request.getParameter("nome");
		String email = request.getParameter("email");
		String cpf = request.getParameter("cpf");
		String dataNascimento = request.getParameter("dataNascimento");
		String sexo = request.getParameter("sexo");
		String mini = request.getParameter("miniBio");
		String idUF = request.getParameter("uf");
		String idCidade = request.getParameter("cidade");
		String idEndereco = request.getParameter("id_endereco");
		String logradouro = request.getParameter("logradouro");

		// recupera as preferências para uma array de strings
		String[] preferencias = request.getParameterValues("gostos");
		List<PreferenciaDTO> listaPresf = new ArrayList<>();
		if (preferencias != null) {
			for (String pref : preferencias) {
				PreferenciaDTO preferenciaDTO = new PreferenciaDTO();
				preferenciaDTO.setIdPreferencia(Integer.parseInt(pref));

				listaPresf.add(preferenciaDTO);
			}
		}
		try {
			PessoaDTO pessoaDTO = new PessoaDTO();
			pessoaDTO.setIdPessoa(Integer.parseInt(idPessoa));
			pessoaDTO.setNome(nome);
			pessoaDTO.setEmail(email);
			pessoaDTO.setCpf(cpf);
			pessoaDTO.setDataNascimento(dataNascimento);
			pessoaDTO.setSexo(sexo);
			pessoaDTO.setMiniBio(mini);
			pessoaDTO.setPreferencias(listaPresf);

			EnderecoDTO enderecoDTO = new EnderecoDTO();
			enderecoDTO.setLogradouro(logradouro);
			enderecoDTO.setIdEndereco(Integer.parseInt(idEndereco));

			CidadeDTO cidadeDTO = new CidadeDTO();
			cidadeDTO.setIdCidade(idCidade != null ? Integer.parseInt(idCidade) : null);

			UfDTO ufDTO = new UfDTO();
			ufDTO.setIdUF(idUF != null ? Integer.parseInt(idUF) : null);

			cidadeDTO.setUfDTO(ufDTO);
			enderecoDTO.setCidade(cidadeDTO);
			pessoaDTO.setEndereco(enderecoDTO);

			boolean isValido = pessoaBO.validaPessoa(pessoaDTO);
			if (!isValido) {
				request.setAttribute("msgErro", MensagemContantes.MSG_ERR_PESSOA_DADOS_INVALIDOS);
			} else { // atualização de pessoa com sucesso
				pessoaBO.atualizarPessoa(pessoaDTO);
				proxima = "main?acao=list";
				request.setAttribute("msgSucesso", MensagemContantes.MSG_SUC_ATUALIZADA_PESSOA);

			}
		} catch (NegocioException | ParseException e) {
			request.setAttribute("msgErro", e.toString());
		}
		
		return proxima;
	}

}
