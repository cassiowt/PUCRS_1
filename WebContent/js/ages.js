/**
 * 
 */

function popularComboCidades(comboEstados) {
	var idEstado = comboEstados.options[comboEstados.selectedIndex].value;
	// alert(comboEstados.options[comboEstados.selectedIndex].text)
	// alert(comboEstados.options[comboEstados.selectedIndex].value)

	location.href = 'main?acao=makeAdd&getCidades=true&idUF=' + idEstado;

}
