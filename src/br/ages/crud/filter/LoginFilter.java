package br.ages.crud.filter;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class LoginFilter
 */
@WebFilter("/*")
public class LoginFilter implements Filter {

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		System.out.println("Filtro de Login Finalizado " + new Date());
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = ((HttpServletRequest) request);
		
	/*	if (httpRequest.getRequestURI().endsWith("login.jsp")){
			System.out.println("� uma jsp: " + httpRequest.getRequestURI());
		} else {
			System.out.println("N�O � uma JSP: " + httpRequest.getRequestURL());
		}*/
		
		if (!httpRequest.getRequestURI().endsWith("login.jsp")){
			HttpSession session = httpRequest.getSession();
			if(session.getAttribute("usuario") == null) {
				System.out.println("Acesso negado - Logue primeiro");
			}
		}
		
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		System.out.println("Filtro de Login Inicializado " + new Date());
	}

}
