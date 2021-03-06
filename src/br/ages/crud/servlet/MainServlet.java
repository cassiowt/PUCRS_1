package br.ages.crud.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.ages.crud.command.AddPeopleCommand;
import br.ages.crud.command.Command;
import br.ages.crud.command.CreateScreenPeopleCommand;
import br.ages.crud.command.EditPeopleCommand;
import br.ages.crud.command.ListPeopleCommand;
import br.ages.crud.command.ListUserCommand;
import br.ages.crud.command.LoginCommand;
import br.ages.crud.command.LogoutCommand;
import br.ages.crud.command.RemovePessoaCommand;
import br.ages.crud.command.UpdatePeopleCommand;

@WebServlet("/main")
public class MainServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private Map<String, Command> comandos = new HashMap<String, Command>();

	@Override
	public void init() throws ServletException {
		comandos.put("login", new LoginCommand());
		comandos.put("logout", new LogoutCommand());
		comandos.put("add", new CreateScreenPeopleCommand());
		comandos.put("addPeople", new AddPeopleCommand());
		comandos.put("list", new ListPeopleCommand());
		comandos.put("listUser", new ListUserCommand());
		comandos.put("removerPessoa", new RemovePessoaCommand());
		comandos.put("editPeople", new EditPeopleCommand());
		comandos.put("updatePeople", new UpdatePeopleCommand());
	
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse reponse) throws ServletException, IOException {

		String acao = request.getParameter("acao");
		String proxima = null;

		try {
			Command comando = verificarComando(acao);
			proxima = comando.execute(request);
		} catch (Exception e) {
			request.setAttribute("msgErro", e.getMessage());
		}
		request.getRequestDispatcher(proxima).forward(request, reponse);
		
	}

	private Command verificarComando(String acao) {
		Command comando = null;
		for (String key : comandos.keySet()) {
			if (key.equalsIgnoreCase(acao)) {
				comando = comandos.get(key);
			}
		}
		return comando;
	}
}

