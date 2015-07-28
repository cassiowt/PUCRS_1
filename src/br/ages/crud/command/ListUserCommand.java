package br.ages.crud.command;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import br.ages.crud.bo.PessoaBO;
import br.ages.crud.bo.UsuarioBO;
import br.ages.crud.dto.PessoaDTO;
import br.ages.crud.dto.UsuarioDTO;
import br.ages.crud.exception.NegocioException;

public class ListUserCommand implements Command {

	private String proxima;
	private UsuarioBO usuarioBO;
	
	@Override
	public String execute(HttpServletRequest request) throws SQLException {
		this.usuarioBO = new UsuarioBO();
		proxima = "listUser.jsp";

		try {
			List<UsuarioDTO> listaUsuarios = usuarioBO.listarUsuario();
			request.setAttribute("listaUsuarios", listaUsuarios);
		} catch (NegocioException e) {
			e.printStackTrace();
			request.setAttribute("msgErro", e.getMessage());
		}

		return proxima;
	}

}
