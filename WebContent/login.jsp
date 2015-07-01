<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="./css/comum.css" />
<title>AgES Login</title>
</head>
<body>

	<form method="post" class="login_form" action="main?acao=login">
		<jsp:include page="msg.jsp"></jsp:include>	
		<fieldset class="fieldset_login">
			<legend>System Access</legend>
			<div class="campo">
				<label for="login">Login</label>
				<input type="text" id="login" name="login" maxlength="15" value="${param.login}" />
			</div>

			<div class="campo">
				<label for="senha">Password</label> 
				<input type="password" id="senha" name="senha" maxlength="15" value="${param.senha}"/>
			</div>

			<div class="campo">
				<input type="submit" value="Enter" id="logar" name="logar" />
			</div>

			<div class="campo">
				<a href="">I forgot my password</a>
			</div>
		</fieldset>
	</form>

</body>
</html>