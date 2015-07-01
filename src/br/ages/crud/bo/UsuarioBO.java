package br.ages.crud.bo;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.ages.crud.dao.UsuarioDAO;
import br.ages.crud.dto.UsuarioDTO;
import br.ages.crud.exception.NegocioException;
import br.ages.crud.exception.PersistenciaException;
import br.ages.crud.util.MensagemContantes;
import br.ages.crud.validator.LoginValidator;

/**
 * Gerencia os comportamentos de negócio do Usuário Associa os parâmetros da
 * tela as propriedades da classe
 * 
 * @author Cássio Trindade
 * 
 */
public class UsuarioBO {
	UsuarioDAO usuarioDAO = null;
	
	public UsuarioBO() {
		usuarioDAO = new UsuarioDAO();
	}
	
	/**
	 * Valida Usuário no sistema
	 * 
	 * @param request
	 * @return
	 * @throws NegocioException
	 */
	public boolean validaUsuario(UsuarioDTO usuarioDTO) throws NegocioException {
		boolean isValido = true;
		try {

			// valida campos estão preenchidos
			Map<String, Object> valores = new HashMap<>();
			valores.put("Usuario", usuarioDTO.getUsuario());
			valores.put("Senha", usuarioDTO.getSenha());
			if (new LoginValidator().validar(valores)) {
				isValido = true;
			}
			// valida se o usuário está na base
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			isValido = usuarioDAO.validarUsuario(usuarioDTO);
			if (!isValido) {
				throw new NegocioException(MensagemContantes.MSG_ERR_USUARIO_SENHA_INVALIDOS);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new NegocioException(e);
		}

		return isValido;

	}
	
	/**
	 * Lista as pessoas a partir das classes de DAO
	 * @return
	 * @throws NegocioException
	 */
	public List<UsuarioDTO> listarUsuario() throws NegocioException {
		
		List<UsuarioDTO> listUser  = null;
		
		try {
			listUser = usuarioDAO.listarUsuarios();
		} catch (PersistenciaException | SQLException e) {
			e.printStackTrace();
			throw new NegocioException(e);
		}
		
		return listUser;
		
		
	}
}
