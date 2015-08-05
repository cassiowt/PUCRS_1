package br.ages.crud.command;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import br.ages.crud.dao.PessoaDAO;
import br.ages.crud.dto.CidadeDTO;
import br.ages.crud.dto.PreferenciaDTO;
import br.ages.crud.dto.UfDTO;
import br.ages.crud.exception.PersistenciaException;

public class CreateScreenPeopleCommand implements Command {

	private String proxima;

	private PessoaDAO cadastroDao;

	public String execute(HttpServletRequest request) throws SQLException {

		cadastroDao = new PessoaDAO();
		
		// Verifica se abre tela edição de pessoa ou de adição de pessoa.
		String isEdit = request.getParameter("isEdit");
		if (isEdit != null && !"".equals(isEdit)) {
			proxima = "editPeople.jsp";
		} else {
			proxima = "add.jsp";
		}
		
		String getCidades = request.getParameter("getCidades");

		try {
			// Monta lista de Cidades
			if (getCidades != null && !"".equals(getCidades)) {
				String id = request.getParameter("idUF");
				int idUF = Integer.parseInt(id);

				List<CidadeDTO> listaCidades = cadastroDao.consultarCidadesPorUf(idUF);
				request.setAttribute("listaCidades", listaCidades);

			} else {
				// Monta as lista de Estados e Preferências
				List<UfDTO> listaUFs = cadastroDao.listarUFs();
				List<PreferenciaDTO> listaPreferencias = cadastroDao.listarPreferencias();
				request.getSession().setAttribute("listaUF", listaUFs);
				request.getSession().setAttribute("listaPreferencias", listaPreferencias);
			}

		} catch (PersistenciaException e) {
			request.setAttribute("msgErro", e.getMessage());
		}

		return proxima;
	}
}
