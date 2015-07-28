<%@page import="java.util.ArrayList"%>
<%@page import="br.ages.crud.dto.PessoaDTO"%>
<%@page import="java.util.Arrays"%>
<%@page import="br.ages.crud.dto.PreferenciaDTO"%>
<%@page import="br.ages.crud.dto.UfDTO"%>
<%@page import="br.ages.crud.dto.CidadeDTO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>AGES - Edit</title>
<link rel="stylesheet" href="./css/comum.css" />
<script type="text/javascript">
	function popularComboCidades(comboEstados) {
		var idEstado = comboEstados.options[comboEstados.selectedIndex].value;
		var formCadastro = document.forms[0];
		formCadastro.action = 'main?acao=add&isEdit=true&getCidades=true&idUF=' + idEstado;
		formCadastro.submit();
	}
	
	function atualizar() {
		var formCadastro = document.forms[0];
		formCadastro.action='main?acao=updatePeople';
		formCadastro.submit();
	}
</script>
</head>
<body>
	<jsp:include page="head.jsp"/>
		<h1>Atualização</h1>
	
		<div class="main">
			<form method="post">
				<jsp:include page="msg.jsp"/>
				<fieldset>
					<legend>Atualização de Pessoa</legend>
					 <input type="hidden" name="id_pessoa" value="${pessoa != null ? pessoa.idPessoa: param.id_pessoa}"/>
					 <input type="hidden" name="id_endereco" value="${pessoa != null ? pessoa.endereco.idEndereco: param.id_endereco}"/>
					<table cellpadding="5">
						<tr>
							<td>Nome*:</td>
							<td><input type="text" name="nome" maxlength="45" value="${pessoa != null ? pessoa.nome : param.nome}"/></td>
						</tr>
						<tr>
							<td>e-Mail Address</td>
							<td><input type="text" id="email" name="email" maxlength="45" value="${pessoa != null ? pessoa.email : param.email}" /></td>
						</tr>
						<tr>
							<td>CPF*:</td>
							<td><input type="text" name="cpf" maxlength="11" value="${pessoa != null ? pessoa.cpf : param.cpf}"/></td>
						</tr>
						<tr>
							<td>Data Nascimento:</td>
							<td><input type="text" name="dataNascimento" maxlength="10" value="${pessoa != null ? pessoa.dataNascimento : param.dataNascimento}"/></td>
						</tr>
						<tr>
							<td>Sexo*:</td>
							<td><input type="radio" name="sexo" value="M" ${'M' eq (pessoa != null ? pessoa.sexo : param.sexo) ? 'checked' : ''}/> Masculino
							    <input type="radio" name="sexo" value="F" ${'F' eq (pessoa != null ? pessoa.sexo : param.sexo) ? 'checked' : ''}/> Feminino</td>
						</tr>
						<tr>
							<td>Preferências:</td>
							<td>
								<%
									List<PreferenciaDTO> preferencias = (List<PreferenciaDTO>) session.getAttribute("listaPreferencias");
									PessoaDTO pessoaDTO = (PessoaDTO) request.getAttribute("pessoa");
									String[] paramPrefs = request.getParameterValues("gostos");
									List<Integer> idsPrefs = new ArrayList<Integer>();	
									if (pessoaDTO != null && pessoaDTO.getPreferencias() != null)
									for (PreferenciaDTO p : pessoaDTO.getPreferencias()){
										idsPrefs.add(p.getIdPreferencia());
									}
									if (preferencias != null) {
										for (PreferenciaDTO preferencia : preferencias) {
								%>
									<input type="checkbox" name="gostos" value="<%= preferencia.getIdPreferencia() %>" 
										<%
											if (pessoaDTO != null) {
										  		out.print(idsPrefs.contains(preferencia.getIdPreferencia()) ? "checked " : "");
											} else {						
												out.print(paramPrefs != null && Arrays.asList(paramPrefs).contains(String.valueOf(preferencia.getIdPreferencia())) ? "checked " : "");
										  } 
										 %>
									/><%= preferencia.getDescricao() %>
									
								<%
										}
									}
								%>
							</td>
						</tr>
						<tr>
							<td>Mini-biografia:</td>
							<td>
								<textarea rows="5" cols="35" name="miniBio">${pessoa != null ? pessoa.miniBio : param.miniBio}</textarea>
							</td>
						</tr>
					</table>
					
					<fieldset>
						<legend>Endereço</legend>
						
						<table cellpadding="5">
							<tr>
								<td>UF*:</td>
								<td>
									<select name="uf" id="uf" onchange="popularComboCidades(this)">
										<option value="0">Selecione...</option>
									<%
										List<UfDTO> listaUF = (List<UfDTO>) session.getAttribute("listaUF");
										UfDTO ufDTO = null;
										if (pessoaDTO != null)
											ufDTO = pessoaDTO.getEndereco().getCidade().getUfDTO();
										for (UfDTO uf : listaUF) {
									%>
										<option value="<%=uf.getIdUF()%>" 
										<% if (ufDTO != null) { %>
											<%= ufDTO != null && uf.getIdUF().equals(ufDTO.getIdUF()) ? "selected='selected'" : "" %>>
										<%} else { %>
											<%=request.getParameter("uf") != null && String.valueOf(uf.getIdUF()).equals(request.getParameter("uf")) ? "selected = 'selected'" : "" %>>
										<% } %>
										<%=uf.getDescricao()%></option>
									<%
										}
									%>
									</select>
								</td>
							</tr>
							<tr>
								<td>Cidade*:</td>
								<td>
									<select name="cidade">
										<option value="0">Selecione...</option>
									<%
										List<CidadeDTO> listaCidades = (List<CidadeDTO>) request.getAttribute("listaCidades");
										CidadeDTO cidadeDTO = null;
										if (pessoaDTO != null)
											cidadeDTO =	pessoaDTO.getEndereco().getCidade();
										if (listaCidades != null) {
											for (CidadeDTO cidade : listaCidades) {
									%>
										<option value="<%= cidade.getIdCidade() %>"
											<%= cidadeDTO != null && cidade.getIdCidade().equals(cidadeDTO.getIdCidade()) ? "selected='selected'" : "" %>>
											<%= cidade.getDescricao() %>
										</option>
									<%
											}
										}
									%>
									</select>
								</td>
							</tr>
							<tr>
								<td>Logradouro*:</td>
								<td>
									<input type="text" name="logradouro" value="${pessoa != null ? pessoa.endereco.logradouro : param.logradouro}"/>
								</td>
							</tr>
						</table>
					</fieldset>
				</fieldset>
				<span>* Campos obrigatórios</span>
				<input type="reset" value="Limpar"/>
				<input type="button" value="Atualizar" onclick="atualizar()"/>
			</form>
		</div>
	
	<jsp:include page="foot.jsp"/>

</body>
</html>