/**
 * Funcao utilizada para o destaque de linhas em uma tr. Para utiliza-lo com
 * onmouseover modo=over Para utiliza-lo com onmouseout modo=out Para utiliza-lo
 * com onclick modo=click Exemplo:<br>
 * <tr class="<%=cor%>" onmouseover="javascript:destacaLinha(this,'over','')"
 * onmouseout="javascript: destacaLinha(this,'out','<%=cor%>')"
 * onClick="javascript: destacaLinha(this,'click','<%=cor%>')"
 * class="linha_subtitulo2">
 * 
 * @author yuricooper
 * @since 07/MAIO/2007
 * @param obj
 * @param modo
 * @param cor
 * 
 */
function destacaLinha(obj, modo, cor) {

	if (modo == 'over') {
		if (obj.className == 'cor_clicked') {
			obj.className = 'cor_over_clicked';
		} else if (obj.className == 'cor_sim_financeiro') {
			obj.className = 'cor_over_negrito';
		} else {
			obj.className = 'cor_over';
		}
	}

	if (modo == 'out') {
		if (obj.className == 'cor_over_clicked')
			obj.className = 'cor_clicked';
		if (obj.className == 'cor_over')
			obj.className = cor;
	}

	if (modo == 'click') {
		if ((obj.className == 'cor_over_clicked')
				|| (obj.className == 'cor_clicked'))
			obj.className = cor;
		else
			obj.className = 'cor_clicked';
	}
}

function destacaLinhaDisplayTag(idDisplayTag) {
	var table = document.getElementById(idDisplayTag);
	if (table != null) {
		var rows = table.getElementsByTagName("tr");
		for (i = 0; i < rows.length; i++) {

			if (i == 0) {
				rows[i].className = 'linha_subtitulo';
			} else {

				rows[i].onmouseover = function() {
					destacaLinha(this, 'over', '');
				};

				rows[i].onmouseout = function() {
					destacaLinha(this, 'out', 'cor_nao');
				};

				rows[i].onclick = function() {
					destacaLinha(this, 'click', 'cor_nao');
				};
			}
			;
		}
		;
	}
}
