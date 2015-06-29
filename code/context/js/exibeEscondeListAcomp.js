	function exibeEscondeListFuncaoAcomp (indPodeBloquear, lstFuncAcomp, tdLiberado){
		indbPodeBloq = document.getElementById(indPodeBloquear);
		lstFuncAcomp  = document.getElementById(lstFuncAcomp);
		if (tdLiberado==undefined) tdLiberado='tdLiberado';
		
		if (indbPodeBloq.checked){
			lstFuncAcomp.style.display = "inline";
			if (document.getElementById(tdLiberado))
			 	document.getElementById(tdLiberado).style.display="inline";
		} else {
			lstFuncAcomp.style.display = "none";
			if (document.getElementById(tdLiberado))
			 	document.getElementById(tdLiberado).style.display="none";
			for (i =0; i< document.form.limbTipoFuncAcompTpfa.length; i++){
				document.form.limbTipoFuncAcompTpfa[i].checked = false;
			}
		}
	}
