<style>
/*
#shadowheaderffb {
	border: 1px solid #c0c0c0;
	-moz-border-radius: 10px 10px 0px 0px;
	width: 200px;
	height: 16px;
	background: #C0C0C0;
	position: absolute;
	top: 155px;
	left: 103px;
	z-index: 8900;
}
*/
#shadowbodyffb {
	border: 1px solid #c0c0c0;
	-moz-border-radius: 0px 0px 10px 10px;
	width: 200px;
	height: 300px;
	background: #C0C0C0;
	position: absolute;
	top: 172px;
	left: 103px;
	z-index: 8900;
}

#headerffb {
	border: 1px solid #000;
	-moz-border-radius: 10px 10px 0px 0px;
	width: 200px;	
	height: 16px;   
	padding-bottom: 2px;
	background: #e0e0e0;
	cursor: move;
	position: absolute;
	top: 150px;
	left: 100px;
	z-index: 9000;
}

#bodyffb {
	border: 1px solid #000;
	-moz-border-radius: 0px 0px 10px 10px;
	width: 200px;
	height: 300px;
	background: #FFF;
	position: absolute;
	top: 168px;
	left: 100px;
	z-index: 9000;
}

.bartext {
	float: left;
	width: 73%;
	text-align: left;
	font-size: 9pt;
	padding-left: 2px;
}

.bartipicon {
	float: left;
	width: 15%;
	text-align: center;
}

.barresizeicon {
	float: left;
	width: 9%;
	text-align: center;
}

.barresizeicon A {
	cursor: pointer;
	text-decoration: none;
	text-weight: bold;
}

#clearline { clear: both; }

</style>

<script type="text/javascript" src="<%=_pathEcar%>/js/wz_dragdrop.js"></script>
<script type="text/javascript" >
	/* -- 
	 * Global variables to control drag and drop of Float Filter Bar.
	 * -- */
	var _flagShowHeaderBody = true;

	function expandColapseFFB(close, boxname, cmdname, shadowboxname) {
		if( close ) {
			_flagShowHeaderBody = false;
			document.getElementById(boxname).className = 'naovisivel';
			document.getElementById(shadowboxname).className = 'naovisivel';
			document.getElementById(cmdname).innerHTML = '<img src="<%=_pathEcar%>/images/collapsed_button.gif" border="0">';
		} else {
			_flagShowHeaderBody = true;		
			document.getElementById(boxname).className = 'visivel';
			document.getElementById(shadowboxname).className = 'visivel';
			document.getElementById(cmdname).innerHTML = '<img src="<%=_pathEcar%>/images/expanded_button.gif" border="0">';
		}
	}
</script>

<!--<div id="shadowheaderffb"></div>-->
<div id="shadowbodyffb"></div>
<div id="headerffb" >
	<div class="bartext">Barra de Filtros</div>
	<div class="bartipicon">
		<label class="dica">
		<img src="<%=_pathEcar%>/images/dica.png" border="0">
		<span>Tip test about float filter bar</span></label>
	</div>
	<div class="barresizeicon barraFlutuanteDeFiltros">
		<a id="headercmd" href="javascript:expandColapseFFB(_flagShowHeaderBody, 'bodyffb', 'headercmd', 'shadowbodyffb');">
		<img src="<%=_pathEcar%>/images/expanded_button.gif" border="0"></a>
	</div>
	<div id="clearline"></div>
</div>
<div id="bodyffb">
	Aqui vão as caixas de filtros.
</div>

<script type="text/javascript">
	SET_DHTML("headerffb"+CURSOR_MOVE, "bodyffb"+NO_DRAG, "shadowheaderffb"+NO_DRAG, "shadowbodyffb"+NO_DRAG);
	
	function initWindow() {    
	    dd.elements.headerffb.addChild("bodyffb");
	    //dd.elements.headerffb.addChild("shadowheaderffb");
	    dd.elements.headerffb.addChild("shadowbodyffb");
	    dd.elements.headerffb.show();
	}
	
	initWindow();
</script>