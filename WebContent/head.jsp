<div class="head">
	<div class="logo">
		<a href="index.jsp">
			<img alt="Logo AgES" src="img/FACIN-AGES-poli.jpg">
		</a>
	</div>
	<ul>
		<li><a href="index.jsp"  class="${param.acao eq 'index' ? 'selected' : ''}">Home</a></li>
		<li><a href="main?acao=add"    class="${param.acao eq 'add' ? 'selected' : ''}">Add</a></li>
		<li><a href="main?acao=list"   class="${param.acao eq 'list' ? 'selected' : ''}">List</a></li>
		<li><a href="main?acao=listUser"   class="${param.acao eq 'listUser' ? 'selected' : ''}">List User</a></li>
		<li><a href="main?acao=logout" class="${param.acao eq 'logout' ? 'selected' : ''}">Exit</a></li>
	</ul>
	<div class="welcome">
		Welcome <b>${sessionScope.usuario.usuario}</b>!
	</div>
</div>