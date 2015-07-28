package br.ages.crud.command;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import br.ages.crud.bo.PessoaBO;
import br.ages.crud.dto.CidadeDTO;
import br.ages.crud.dto.EnderecoDTO;
import br.ages.crud.dto.PessoaDTO;
import br.ages.crud.dto.PreferenciaDTO;
import br.ages.crud.dto.UfDTO;
import br.ages.crud.util.MensagemContantes;

public class AddPeopleCommand implements Command {

	private String proxima;

	private PessoaBO pessoaBO;

	@Override
	public String execute(HttpServletRequest request) {
		pessoaBO = new PessoaBO();
		proxima = "add.jsp";

		String nome = request.getParameter("nome");
		String email = request.getParameter("email");
		String cpf = request.getParameter("cpf");
		String dataNascimento = request.getParameter("dataNascimento");
		String sexo = request.getParameter("sexo");
		String mini = request.getParameter("mini");
		String idUF = request.getParameter("uf");
		String idCidade = request.getParameter("cidade");
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
			pessoaDTO.setNome(nome);
			pessoaDTO.setEmail(email);
			pessoaDTO.setCpf(cpf);
			pessoaDTO.setDataNascimento(dataNascimento);
			pessoaDTO.setSexo(sexo);
			pessoaDTO.setMiniBio(mini);
			pessoaDTO.setPreferencias(listaPresf);

			EnderecoDTO enderecoDTO = new EnderecoDTO();
			enderecoDTO.setLogradouro(logradouro);

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
			} else { // cadastro de pessoa com sucesso
				pessoaBO.cadastraPessoa(pessoaDTO);
				proxima = "main?acao=list";
				request.setAttribute("msgSucesso", MensagemContantes.MSG_SUC_CADASTRO_PESSOA);

			}

		} catch (Exception e) {
			request.setAttribute("msgErro", e.getMessage());
			String iduf = request.getParameter("uf");
			proxima = "main?acao=add&getCidades=true&idUF=" + iduf ;
		}

		return proxima;
	}
}
