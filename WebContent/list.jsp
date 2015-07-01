<%@page import="br.ages.crud.dto.PreferenciaDTO"%>
<%@page import="br.ages.crud.dto.PessoaDTO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="./css/comum.css" />
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">

<title>AgES - Pagina Inicial</title>
</head>
<body>
	<jsp:include page="head.jsp"></jsp:include>
	<div class="main">
		<form action="main?acao=addPeople" method="post">
			<jsp:include page="msg.jsp"></jsp:include>
			<fieldset>
				<legend>Lists</legend>
				<h1>List</h1>
				<table width="100%" border="1" cellspacing="0" cellpadding="4">
					<thead>
						<tr>
							<th>Id</th>
							<th>Nome</th>
							<th>Email</th>
							<th>CPF</th>
							<th>Sexo</th>
							<th>Data</th>
							<th>Lograduro</th>
							<th>Cidade</th>
							<th>UF</th>
							<th colspan="3">Ações</th> 
						
						</tr>
					</thead>
					<tbody>
					<%
						List<PessoaDTO> listaPessoas = (List<PessoaDTO>) request.getAttribute("listaPessoas");
					 	for (PessoaDTO pessoa : listaPessoas) {
					 		
					%>
						<tr>
							<td class="alignCenter"> <%=pessoa.getIdPessoa() %></td>
							<td class="alignLeft"> 	 <%=pessoa.getNome() %></td>
							<td class="alignLeft"> 	 <%=pessoa.getEmail() %></td>
							<td class="alignLeft">   <%=pessoa.getCpf() %></td>
							<td class="alignCenter"> <%=pessoa.getSexo() %></td>
							<td class="alignCenter"> <%=pessoa.getDataNascimento() %></td>
							<td class="alignLeft">   <%=pessoa.getEndereco().getLogradouro() %></td>							
							<td class="alignLeft">   <%=pessoa.getEndereco().getCidade().getDescricao() %></td>
							<td class="alignLeft">   <%=pessoa.getEndereco().getCidade().getUfDTO().getDescricao() %></td>

							<td class="alignCenter">
								<a href="" title="Editar">
									<i class="fa fa-pencil fa-2x"></i>
								</a>
							</td>
							<td class="alignCenter">
								<a href="" title="Deletear" >
									<i class="fa fa-trash-o fa-2x"></i>
								</a>
							</td>
							<td class="alignCenter">
								<%
				     				String preferencias="";
									for (PreferenciaDTO preferencia: pessoa.getPreferencias()){
										preferencias += preferencia.getDescricao();
									}
								%>
								<a href="javascript:void(0)" title="Musicas" onclick="alert('<%=preferencias %>');">
									<i class="fa fa-music fa-2x"></i>
								</a>
								
							</td>
						</tr>
					<%
					}
					%>
					</tbody>
				</table>
			</fieldset>
		</form>
	</div>
	<jsp:include page="foot.jsp"></jsp:include>
</body>
</html>