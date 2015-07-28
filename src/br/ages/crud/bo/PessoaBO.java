package br.ages.crud.bo;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.ages.crud.dao.PessoaDAO;
import br.ages.crud.dto.PessoaDTO;
import br.ages.crud.exception.NegocioException;
import br.ages.crud.exception.PersistenciaException;
import br.ages.crud.util.MensagemContantes;
import br.ages.crud.validator.CPFValidator;
import br.ages.crud.validator.DataValidator;

/**
 * Gerencia os comportamentos de negócio do Usuário Associa os parâmetros da
 * tela as propriedades da classe
 * 
 * @author Cássio Trindade
 * 
 */
public class PessoaBO {
	
	private PessoaDAO pessoaDAO;
	
	public PessoaBO() {
		pessoaDAO = new PessoaDAO();
	}
	
	/**
	 * Valida pessoa no sistema
	 * 
	 * @param request
	 * @return
	 * @throws NegocioException
	 */
	public boolean validaPessoa(PessoaDTO pessoaDTO) throws NegocioException {
		boolean isValido = true;
		try {
			// valida campos estão preenchidos corretamente
			// Nome
			if (pessoaDTO.getNome() == null || "".equals(pessoaDTO.getNome())) {
				throw new NegocioException(MensagemContantes.MSG_ERR_CAMPO_NOME_OBRIGATORIO);
			}
			
			// CPF
			Map<String, Object> valores = new HashMap<>();
			valores.put("CPF", pessoaDTO.getCpf());
			if (new CPFValidator().validar(valores)) {
				isValido = true;
			}
			// Data de Nascimento
			valores = new HashMap<>();
			valores.put("Data Nascimento", pessoaDTO.getDataNascimento());
			if (new DataValidator().validar(valores)) {
				isValido = true;
			}
		
			// Sexo
			if (pessoaDTO.getSexo() == null || "".equals(pessoaDTO.getSexo())) {
				throw new NegocioException(MensagemContantes.MSG_ERR_CAMPO_SEXO_OBRIGATORIO);
			}
			

			// Endereço
			Integer idUF = pessoaDTO.getEndereco().getCidade().getUfDTO().getIdUF();
			if (idUF == null || idUF == 0 ) {
				throw new NegocioException(MensagemContantes.MSG_ERR_CAMPO_ESTADO_OBRIGATORIO);
			}

			Integer idCidade = pessoaDTO.getEndereco().getCidade().getIdCidade();
			if (idCidade == null || idCidade == 0) {
				throw new NegocioException(MensagemContantes.MSG_ERR_CAMPO_CIDADE_OBRIGATORIO);
			}

			String logradouro = pessoaDTO.getEndereco().getLogradouro();
			if (logradouro == null || "".equals(logradouro)) {
				throw new NegocioException(MensagemContantes.MSG_ERR_CAMPO_LOGRADOURO_OBRIGATORIO);
			}
			// valida se Pessoa esta ok 
			if (!isValido) {
				throw new NegocioException(MensagemContantes.MSG_ERR_PESSOA_DADOS_INVALIDOS);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new NegocioException(e);
		}

		return isValido;

	}
	
	/**
	 * Cadastra pessoa em nível de negócio, chamando o DAO
	 * 
	 * @param pessoaDTO
	 * @throws NegocioException
	 * @throws SQLException
	 */
	public void cadastraPessoa(PessoaDTO pessoaDTO) throws NegocioException, SQLException {
		
		try {
			pessoaDAO.cadastrarPessoa(pessoaDTO);
		} catch (PersistenciaException e) {
			e.printStackTrace();
			throw new NegocioException(e);
		}
		
	}
	/**
	 * Lista as pessoas a partir das classes de DAO
	 * @return
	 * @throws NegocioException
	 */
	public List<PessoaDTO> listarPessoa() throws NegocioException {
		
		List<PessoaDTO> listPessoas  = null;
		
		try {
			listPessoas = pessoaDAO.listarPessoa();
		} catch (PersistenciaException | SQLException e) {
			e.printStackTrace();
			throw new NegocioException(e);
		}
		
		return listPessoas;
		
		
	}
}
