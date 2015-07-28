<%@page import="java.util.Arrays"%>
<%@page import="java.util.List"%>
<%@page import="br.ages.crud.dto.PreferenciaDTO"%>
<%@page import="br.ages.crud.dto.UfDTO"%>
<%@page import="br.ages.crud.dto.CidadeDTO"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="stylesheet" href="./css/comum.css" />
		<title>AgES - Adds</title>
		<script type="text/javascript">
			
			function popularComboCidades(comboEstados) {
				var idEstado = comboEstados.options[comboEstados.selectedIndex].value;
				var formCadastro =document.forms[0]; 
				formCadastro.action = 'main?acao=add&getCidades=true&idUF=' + idEstado;
				formCadastro.submit();
			}
			
			function cadastrar() {
				var formCadastro =document.forms[0]; 
				formCadastro.action ="main?acao=addPeople";
				formCadastro.submit();
			}
		
		</script>
</head>
<body>
	<jsp:include page="head.jsp"></jsp:include>
	<h1>Add</h1>
	<div class="main">
		<form action="main?acao=addPeople" method="post">
			<jsp:include page="msg.jsp"></jsp:include>
			<fieldset>
				<legend>Add People</legend>
				<table cellpadding="5">
					<tr>
						<td>Name <sup class="red">*</sup></td>
						<td><input type="text" id="nome" name="nome" maxlength="45" value="${param.nome}" /></td>
					</tr>
					<tr>
						<td>e-Mail Address</td>
						<td><input type="text" id="email" name="email" maxlength="45" value="${param.email}" /></td>
					</tr>
					<tr>
						<td>CPF <sup class="red">*</sup></td>
						<td><input type="text" id="cpf" name="cpf" maxlength="11" value="${param.cpf}" /></td> 
					</tr>
					<tr>
						<td>Birth Date</td>
						<td><input type="text" id="dataNascimento" name="dataNascimento" maxlength="10" value="${param.dataNascimento}" /></td>
					</tr>
					<tr>
						<td>Sex <sup class="red">*</sup></td>
						<td><input type="radio" id="sexo" name="sexo" value="M" <%= "M".equals(request.getParameter("sexo")) ? "checked" : "" %>/>Male
						    <input type="radio" id="sexo" name="sexo" value="F" <%= "F".equals(request.getParameter("sexo")) ? "checked" : "" %>/>Female</td>
					</tr>
					<tr>
						<td>Preferences</td>
						<td>
								<%
									List<PreferenciaDTO> preferencias = (List<PreferenciaDTO>) session.getAttribute("listaPreferencias");
									String[] paramPrefs = request.getParameterValues("gostos");
									if(preferencias != null) {	
										for (PreferenciaDTO preferencia : preferencias) {
								%>
									<input type="checkbox" name="gostos" value="<%= preferencia.getIdPreferencia() %>" 
											<%= paramPrefs != null && Arrays.asList(paramPrefs).contains(String.valueOf(preferencia.getIdPreferencia())) ? "checked" : "" %> />
											<%= preferencia.getDescricao() %>
								<%
										}
									}
								%>
						 </td>
					</tr>
					<tr>
						<td>Mini-Resume</td>
						<td>
							<textarea cols="40" rows="5" id="mini" name="mini" ></textarea>  <%-- value="${param.mini}" --%>
						</td>
					</tr>
				</table>
				<fieldset>
					<legend>Address</legend>
					<table cellpadding="5">
						<tr>
							<td>UF <sup class="red">*</sup></td>
							<td>
								<select name="uf" id="uf"  onchange="popularComboCidades(this)">
								<option value="0">Selected..</option>
								<%
									List<UfDTO> listaUF = (List<UfDTO>) session.getAttribute("listaUF");
									for (UfDTO uf : listaUF){
								%>
									<option value="<%=uf.getIdUF()%>"
										<%=request.getParameter("uf") != null && String.valueOf(uf.getIdUF()).equals(request.getParameter("uf")) ? "selected = 'selected'" : "" %>>
											<%=uf.getDescricao()%></option>
								<%
									} 
								%>
								</select>
							</td>
						</tr>
						<tr>
							<td>City <sup class="red">*</sup></td>
							<td>
								<select name="cidade">
								<option value="0">Selected..</option>
								<%
									List<CidadeDTO> listaCidades = (List<CidadeDTO>) request.getAttribute("listaCidades");
									if (listaCidades != null) {
										for (CidadeDTO cidade : listaCidades){
								%>
									<option value="<%=cidade.getIdCidade()%>"
									<%=request.getParameter("cidade") != null && String.valueOf(cidade.getIdCidade()).equals(request.getParameter("cidade")) ? "selected = 'selected'" : "" %>>
										<%=cidade.getDescricao()%>
									</option>
								<%
										}
									}
								%>								
								</select>
							</td>
						</tr>
						<tr>
							<td>Street <sup class="red">*</sup></td>
							<td><input type="text" id="logradouro" width="350" name="logradouro" maxlength="35" value="${param.logradouro}" /></td>
						</tr>
					</table>
				</fieldset>
			</fieldset>
			<span><sup class="red">*</sup> campos obrigatórios</span>
			<input type="reset"  value="Clear"  id="limpar" name="limpar" />
			<input type="button" value="Submit" onclick="cadastrar()"/>
		</form>
	</div>
	<jsp:include page="foot.jsp"></jsp:include>
</body>
</html>