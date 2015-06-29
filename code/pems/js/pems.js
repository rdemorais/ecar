// JavaScript Document
//window.console.log = function (){};
if(!window.console) {
window.console = {
log : function(str) {
//alert(str);
}
};
}
window.onerror=function(e){return true};

var anoExercicioSelecionado = '2013';

var dataset, searchObj=new Object(), baseUrl='http://localhost/pems/proxy.php?url=', pemsData=new Object(), resultsOffset=163, topreload= new Array(), preloadImages=new Array(), loginKey='', height, width; var oeData;

//var dataset, searchObj=new Object(), baseUrl='http://localhost/pems/proxy.php?url=', pemsData=new Object(), resultsOffset=163, topreload= new Array(), preloadImages=new Array(), loginKey='', height, width; var oeData;

//var dataset, searchObj=new Object(), baseUrl='http://localhost:8085/pems/proxy.php?url=', pemsData=new Object(), resultsOffset=163, topreload= new Array(), preloadImages=new Array(), loginKey='', height, width; var oeData;
pemsData.statusFilter = [1,1,1,1,1,1];
$(document).ready(function(e) {
	//alert('LOAD');
	Resize();
	
	MobileDetect();
	
   	$(window).resize(Resize);
//	$(window).jkey('alt+c', function() {})
//	.jkey('ctrl+right', function() {ChangeVideosIndex('+')})

	console.log('--ready');
	StartUp();
	preload();
	LocalStorage('load');
	
	if (navigator.userAgent.toLowerCase().indexOf('iphone')>=0){
	 $('<div id="iphoneWarning"><div>A visualização do PPE não é recomendada através de smartphones.</div><div>A compreensão das informações aqui apresentadas poderão sofrer ruídos de interpretação durante sua análise em função da ergonomia de tela do aparelho. <br/><br/>rECOMENDAMOS A UTILIZAÇÃO DE DISPOSITIVOS COM DIMENSÕES DE TELA SUPERIORES A 800 PIXELS. </div><div></div></div>').appendTo("body");
	}
	
	
});

function preload() {

	for (i = 0; i < topreload.length; i++) {
		preloadImages[i] = new Image();
		preloadImages[i].src = topreload[i];
		preloadImages[i].onload = function(e) {console.log('image done')};
	}
		console.log('PRELOAD IMAGES');
}

function StartUp(){
	Logout();
	EventListeners();
//	Resize();
	$('#body').removeClass('hidden').addClass('visible');
}

var ua='';
function MobileDetect(){
	
	ua = navigator.userAgent.toLowerCase();
	//alert(ua);
	//var isAndroid = ua.indexOf("android") > -1; //&& ua.indexOf("mobile");
	if (width<800) {
		$('head').append('<link href="css/imobile.css" rel="stylesheet">');
	}
	else{
		if(ua.indexOf("android") > -1) $('head').append('<link href="css/android.css" rel="stylesheet" />');
		if(ua.indexOf("ipad") > -1) $('head').append('<link href="css/ipad.css" rel="stylesheet" />');
		if(ua.indexOf("iphone") > -1 || ua.indexOf("ipod") > -1) $('head').append('<link href="css/ipad.css" rel="stylesheet" /><link href="css/imobile.css" rel="stylesheet" />');
		if(ua.indexOf("msie") > -1) $('head').append('<link href="css/ie.css" rel="stylesheet" />');
	}
	

	
}

function EventListeners(){
	tevent = (Modernizr.touch) ? "touchstart" : "click";	
	$(document).on(tevent, EventHandler);
	$('.headerSpace').on('mouseover mouseout', EventHandler);//.panelItem, 
	$('#user, #pass').jkey('enter', Login);
	$('#loginBtn').on(tevent, Login);
	$('.statusItem').on(tevent, StatusFilter);
	$('#tagCloudInput').on(tevent, EventHandler);
	$('#searchFieldInput').on('keyup', EventHandler);
	$('#comboExercicio').on('change', EventHandler);

}

function EventHandler(e){
	console.log('EventHandler');
	console.log(e.target);
	//console.log(JSON.stringify(e.target));

	if (e.target.id.indexOf('iphoneWarning')>=0 || e.target.parentElement.id.indexOf('iphoneWarning')>=0) $('#iphoneWarning').remove();
	
	if(typeof e.offsetX === "undefined" || typeof e.offsetY === "undefined") {
	//	alert( e.layerX);

		if (e.originalEvent.touches) {
			//alert(e.originalEvent.touches[0].pageX);			
			var ev = e.originalEvent;
			e.pageX	= ev.touches[0].pageX;
			e.pageY = ev.touches[0].pageY;
			e.type='click';
		}
	 	var targetOffset = $(e.target).offset();
		e.offsetX = e.pageX - targetOffset.left;
		e.offsetY = e.pageY - targetOffset.top;
		
	}
	
	
//	if (e.type=='touchstart'){
//		e.type='click';
//		e.offsetX = e.pageX;
//		e.offsetY = e.pageY;
		//alert('TOUCH');
//	}
	
	if (!e.currentTarget) return;
	if (e.currentTarget && e.currentTarget.nodeName.indexOf('document')>=0){
		$('#popup').html('');
	}
	if (e.type=='click' && e.target.id.indexOf('limparFiltros')>=0){
		console.log('LIMPAR FILTROS');
		
		$('#searchFieldInput').val('');
		
		tagInfoScan();
		
			
		$('.statusItem').css('opacity', 1);
		pemsData.statusFilter=[1,1,1,1,1,1];
		
		$('#filtroSubSel ul').html('<li>Nenhum</li>');
		
		for (var i=1;i<=16;i++){
			$('.obj'+i).removeClass('panelTitleSel');
		}
		$('.filtroSubBot').css('display', 'none')
		for (var j=0;j<=9;j++){
			
			var n, k;
			if ('.filtro'+j){
				n=($('.filtro'+j).css('background-image').indexOf('s.gif')>=0)?$('.filtro'+j).css('background-image').split('filtro')[1].split('s.gif')[0]:$('.filtro'+j).css('background-image').split('filtro')[1].split('.gif')[0];
				$('.filtro'+j).css('background-image','url(image/filtro'+j+'.gif)').removeClass('filSel'); 
				
				if (n.length>1) {if ($('.filtro'+j).css('background-image').indexOf('filtro'+n.split('i')[0]+'.gif')==-1) $('.filtro'+j).css('background-image','url(image/filtro'+n.split('i')[0]+'.gif)');}
				else {if ($('.filtro'+j).css('background-image').indexOf('filtro'+n+'.gif')==-1) $('.filtro'+j).css('background-image','url(image/filtro'+n+'.gif)');}
			}
			$('.filtro'+j).removeClass('filSel');
			//console.log('filtro'+n.split('i')[0]+'.gif');
						
			k=pemsData.filterInfo.filters;
			while(k>0){
				--k;
				
				$('.filtroSubItem').removeClass('filtroSubItemChecked');
		
			}

		}
		
		return;
	}
	if (e.type=='click' && e.target.className.indexOf('grayLine')>=0){
		console.log('GO TO TOP');
		animateStaticTop();
		return false;
	}
	if (e.type=='click' && e.target.id.indexOf('noticia')>=0){
		console.log('NOTICIA '+e.target.id);
		ShowStatic('html/'+e.target.id+'.html #data');//'+e.target.id+'
		return;
	}
	if (e.type=='click' && e.currentTarget.id && e.currentTarget.id.indexOf('notepadTitle')>=0) {
//		console.log('Height - '+$('#notepadText').css('display') );
		if ($('#notepadText').css('display')=='none'){
			$('#notepadTitle').css('background-image', 'url(image/notepad_close.gif)');
		}else{
			$('#notepadTitle').css('background-image', 'url(image/notepad_open.gif)');
		}
		$('#notepadText').slideToggle(200);
	}
	if (e.type=='click' && (e.target.id == 'planoBox' || e.target.id == 'logoSmall')){
		if(loginKey.length>0) ShowSearch();
		return;
	}
	if (e.type=='click' && e.target.id == 'navHome'){
		ShowSearch();
		return;
	}
	if (e.type=='click' && e.target.id == 'navBack'){
		//ShowResults();
		console.log(pemsData.last);

		if ($('#navTitle').text().indexOf('RESULTADO')>=0){
			 ShowResults();
			 return;
		}else if ($('#navTitle').text().indexOf('PRODUTOS')>=0) {
			if (pemsData.resultado)	ShowResultado();//resultadoView();
			else ShowResults();
			return;	
		}else if ($('#navTitle').text().indexOf('ERRO')>=0) {
			if (pemsData.last=='resultado') ShowResultado();//ShowResultado();
			else if (pemsData.last=='results') ShowResults();
			else ShowSearch();
			return;
		}else{
			ShowSearch();
		}
		
		
		
		if ($('#label').hasClass('visible')) ShowSearch();
//		else ShowResults();
		return;
	}
	
	/*
	if (e.type=='click' && e.target.outerHTML.indexOf('col')>=0){
		$($(e.target).parent()[0]).children().each(function(index, element) {
            if ($(element).text() == e.target.textContent) {
				$('.'+e.target.className + ':nth-child('+(index+1)+')').toggle(function() {
																			  $('.'+e.target.className + ':nth-child('+(index+1)+')').animate({width: 100});
																		}, function() {
																			 $('.'+e.target.className + ':nth-child('+(index+1)+')').animate({width: 350});
																		});
				
			}
			
        });
		return;
	}
	*/
	
//	if (e.type=='click' && e.target.parentNode.className.indexOf('objPanelHint')>=0) {
		//
//		$('.objPanelHint').stop().slideUp('fast');
//	}
	if (e.type=='click' && (e.target.parentNode.className.indexOf('objPanelHint')>=0 || e.target.className.indexOf('objPanelHint')>=0)) {
		$('.objPanelHint').stop().slideUp('fast');
		if (e.target.localName=='span') e.target = e.target.parentNode;
		if (e.target.className.indexOf('objPanelHint')>=0) $('.oe.obj'+$(e.target).attr('selObj')).addClass('panelTitleSel');
	}
	



	
	if (e.type=='click' && e.target.className.indexOf('popupInfo')>=0 ) {
		$(e.target).toggleClass('popupInfoOpen');
		return;
	}
	if (e.type=='click' && e.target.className.indexOf('popupArt')>=0 && e.target.className.indexOf('Info')==-1 &&  e.target.className.indexOf('Title')==-1) {
		$(e.target).toggleClass('popupArtOpen');
		return;
	}
	if (e.type=='click' && e.target.className.indexOf('popupIndicador')>=0) {
		$(e.target).toggleClass('popupIndicadorOpen');
		return;
	}
		
	if (e.target.className.indexOf('expandToggle')>=0 ) e.target = e.target.parentNode;	
	if (e.type=='click' && e.target.className.indexOf('row')>=0 && e.target.className.indexOf('subrow')==-1){
		var classId = 'id'+e.target.className.split('id')[1];
		if (classId.indexOf('row')>=0) classId = classId.split('row')[0];
		if (ua.indexOf('ipad')>=0) $('.'+classId).nextUntil('.row').css('display', ($('.'+classId).nextUntil('.row').css('display')=='none')?'block':'none');
		else $('.'+classId).nextUntil('.row').stop().slideToggle(500, 'easeOutQuart');	
		$('.'+classId).find('.expandToggle').toggleClass('expandToggleUp');
	}
	if (e.type=='click' && e.target.parentNode.className.indexOf('row')>=0 && e.target.parentNode.className.indexOf('rItemBox4P')>=0){
	    var classId = 'id'+e.target.parentNode.className.split('id')[1];
		if (ua.indexOf('ipad')>=0) $('.'+classId).nextUntil('.row').css('display', ($('.'+classId).nextUntil('.row').css('display')=='none')?'block':'none');
	    else $('.'+classId).nextUntil('.row').stop().slideToggle(500, 'easeOutQuart');	
        $('.'+classId).find('.expandToggle').toggleClass('expandToggleUp');
	}
	
	if (e.target.className.indexOf('header')>=0){
		switch(e.type){
			case "mouseout": $('#'+e.currentTarget.id).removeClass('headerSpaceOver'); break;
			case "mouseover": $('#'+e.currentTarget.id).addClass('headerSpaceOver'); break;
			case "click": ShowStatic('html/'+e.target.id+'.html #data'); break;
		}		
		console.log(e.currentTarget.className);
	}
	
	
	
	if (e.type=='click' && e.target.id == "searchBtn") {
		searchObj.filtros = '';
		searchObj.oe = '';
		searchObj.status = '';
		searchObj.tags='';
		searchObj.exercicio='';
		searchObj.painel='';

		$('#searchSplash .panelTitleSel, #searchSidebar .panelTitleSel').each(function(index, element) {

//			if (element.parentNode.parentNode.className.indexOf('searchSplash')>=0)
				if (searchObj.oe.indexOf(element.textContent+',')<0) searchObj.oe += element.textContent+',';
			
			console.log('textConent - '+element.textContent+' | '+index+' | '+element.className);
		});
		//if (searchObj.oe.length==0) searchObj.oe = '1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,';

		$('.filtroSubItemChecked').each(function(index, element) {
			searchObj.filtros += element.textContent+',';
		});
		searchObj.filtros = searchObj.filtros.slice(0, -1);
		
		$('.statusItem').each(function(index, element) {
        	if ($(element).css('opacity')>=1) searchObj.status+=element.className.split('si')[1].split(' po')[0]+',';//$(element).find(img).attr('src').split('status')[1].split('.gif')[0]+',';
			
        });
		searchObj.status = searchObj.status.slice(0, -1);
		if (searchObj.status=='1,2,3,4,5,0') searchObj.status='';

		if (searchObj.oe.length>0) searchObj.oe = searchObj.oe.slice(0, -1);
		
		searchObj.tags = trim($('#searchFieldInput').val());
		if (searchObj.tags.substring(searchObj.tags.length-1,searchObj.tags.length)==',')searchObj.tags =searchObj.tags.slice(0,-1);				

		searchObj.exercicio = $("#comboExercicio option:selected").val();

		searchObj.painel = 1;//$("#comboExercicio2 option:selected").val();
		
		console.log(searchObj);
		
		if ($('.visaoRadioChecked').attr('id') == 'ind')
			ShowPainelIndicadores();
		else
			ShowResults(true);		
		return;
	}



	
	if (e.type=='click' && e.target.className.indexOf("objResinfo")>=0){//|| "objId"

		pemsData.selOE=e.target.className.split('obj')[2];
		pemsData.prodNav=pemsData.selOE+'.'+$(e.target.parentNode).find('.objId').text();
		
//		if (e.target.parentNode.className.length==0) e.target = e.target.parentNode;
		if ($('#navTitle').text().indexOf('PRODUTO')>=0){
			return;
		}
		if ($('#navTitle').text().indexOf('RESULTADO')>=0){
			pemsData.last='resultado';			
			console.log('ShowProduto '+e.target.id.split('param')[1])
			pemsData.prodNav=$('#navTitle').text().split('RESULTADO ')[1]+'.'+$(e.target.parentNode).find('.objId').text();
			if(e.target.parentNode.parentNode.className.indexOf('subrow')==-1) ShowProduto(e.target.id.split('param')[1], true);
			return;
		}
		pemsData.last='results';
		if(e.target.parentNode.className.indexOf('subrow')>=0) ShowProduto(e.target.id.split('param')[1], true);
		else {
			pemsData.resNav=pemsData.selOE+'.'+$(e.target.parentNode).find('.objId').text();
			ShowResultado(e.target.id.split('param')[1], true);
		}
		return;
	}


	if (e.type=='click' && e.target.className.indexOf('filtroSubBot')>=0){
		$(e.target).css('display', 'none');
		FiltroUpdate(e.target.parentElement);
		return;
		
	}
	if (e.type=='click' && e.target.className.indexOf('filtroSubItem')>=0){
		$(e.target).toggleClass('filtroSubItemChecked');
		FiltroUpdate();
		return;
	}
	
	if (e.type=='click' && e.target.className.indexOf('visaoRadio')>=0){
		$('.visaoRadioChecked').toggleClass('visaoRadioChecked');
		$(e.target).toggleClass('visaoRadioChecked');
		
		return;
	}
	
	if (e.type=='click' && e.target.id.indexOf('imgStatus')>=0){
		var img = $('#'+e.target.id);
		var codArf = $('#'+e.target.id).attr('codArf');
		img.parents('.indRow').siblings('.indJustificativa').slideToggle("slow");
		var editor = CKEDITOR.inline( document.getElementById('divJustText'+codArf) );
	}
	
	if (e.type=='click' && e.target.id.indexOf('btnJustSave')>=0){
		var codArf = $("#"+e.target.id).attr('codArf');
		var justificativa = CKEDITOR.instances['divJustText'+codArf].getData();
		if (justificativa != null)
			GetData('justificativa',';codInd='+codArf+';justificativa='+escape(encodeURIComponent(justificativa)));
	}
	
	if (e.type=='click' && e.target.id.indexOf('encaminhaedit')>=0){
		if ($('.encEdit').length>0) {
			$('.encEditField').focus();
			$('#resultsBox').animate({ scrollTop: (($('.encEdit').offset().top-$('#resultsBox').height())-70)+$('#resultsBox').scrollTop() }, 500, 'easeOutExpo');
			return;
		}
		Show($('#encaminhax'));
		Show($('#encaminhaup'));
		var currentTime = new Date()
		currentTime = ((currentTime.getDate()>9)?currentTime.getDate():'0'+currentTime.getDate())+'/'+((currentTime.getMonth()>8)?currentTime.getMonth() + 1:'0'+(currentTime.getMonth() + 1))+'/'+currentTime.getFullYear();
		$('#rItemBox5m2').prepend("<div class='enc encEdit'><div><span class='po' id='icoAtencaoSwitch' title='Encaminhar para o Ministro'></span><textarea name='encEdit' class='encEditField' placeholder='Digite novo encaminhamento'></textarea></div><div>"+currentTime+"</div><div><input type=”text” class='encDeadline' value='"+currentTime+"'></div><div><p><input name='respFieldInput' id='respFieldInput' type='text' /></p></div></div></div>");
		$('.encEditField').focus();

		$('#resultsBox').animate({ scrollTop: (($('.encEdit').offset().top-$('#resultsBox').height())-70)+$('#resultsBox').scrollTop() }, 500, 'easeOutExpo');
		$('.Zebra_DatePicker').remove();
		$('.encDeadline').Zebra_DatePicker({
		  direction: true,
		  format: 'd/m/Y',
		  disabled_dates: ['* * * 0,6']   // all days, all monts, all years as long
										  // as the weekday is 0 or 6 (Sunday or Saturday)
		});
		GetData('responsavel');
		return;
	}
	if (e.type=='click' && e.target.id.indexOf('encaminhaup')>=0){
		if ($('.encEdit').length>0) {
			var atencao=($('#icoAtencaoSwitch').hasClass('on'))?';atencaoMinistro=true':';atencaoMinistro=false';
			if ($('.encEditField').val().length>0)	{
				console.log($('#respFieldInput').val().toLowerCase().split(' ').join(''));
				GetData('comment',atencao+';codR='+pemsData.resultado.codigo+';texto='+encodeURI($('.encEditField').val())+';prazo='+escape(encodeURIComponent($('.encDeadline').val()))+';codResponsavel='+pemsData.resultado.comentarios.codResp[$('#respFieldInput').val().toLowerCase().replace(/ /g,"")]);
			}
			return;
		}
	}
	
	if (e.type=='click' && e.target.id.indexOf('encaminhax')>=0){
		if ($('.encEdit').length>0) {
			$('.encEdit').remove();
			$('.ac_results').remove();
			Hide($('#encaminhax'));
			Hide($('#encaminhaup'));
		}
		//if ($('.enc').not('.encNone').length==0) $('.encNone').parent().css('display','block');
		return;
	}
	if (e.type=='click' && e.target.id.indexOf('icoAtencaoSwitch')>=0){
		console.log('Switch');
		$("#icoAtencaoSwitch").toggleClass('on');
	}
	
	
	if (e.type=='click' && e.target.className.indexOf('tagItem')>=0){
		console.log(e.target.textContent.replace(/\s/g,''));
		if (pemsData.tagCloud[e.target.textContent.replace(/\s/g,'')].active){
			//pemsData.tagCloud[e.target.textContent.replace(/\s/g,'')].active=false
			if ($('#searchFieldInput').val()==e.target.textContent) $('#searchFieldInput').val('');
			var content = $('#searchFieldInput').val().split(',');
			if (content.length>1) $.each(content, function(i,element){
				if (trim(element)==e.target.textContent) content[i]='';
				if (trim(element).toUpperCase()==e.target.textContent) content[i]='';
			});
			$('#searchFieldInput').val(content.join(', '));
		}
		else {
			$('#searchFieldInput').val($('#searchFieldInput').val().trim());
			$('#searchFieldInput').val($('#searchFieldInput').val()+( ($('#searchFieldInput').val().charAt($('#searchFieldInput').val().length-1)==',')?' ':($('#searchFieldInput').val().length==0)?'':', ' )+e.target.textContent+',');// pemsData.tagCloud[e.target.textContent.replace(/\s/g,'')].active=true;
		}

		tagInfoScan();
		return;
		
	}
	
	
	if (e.type=='keyup' && e.target.id.indexOf('searchFieldInput')>=0){

		console.log($('#searchFieldInput').val());
		tagInfoScan();
		return;
		
	}
	
	if (e.target.className.indexOf('rItemBoxCiclo')>=0){
		
		if (e.target.className.indexOf('rItemBoxCicloE')>=0) pemsData.resultado.ciclo++;
		if (e.target.className.indexOf('rItemBoxCicloD')>=0) pemsData.resultado.ciclo--;
		if (pemsData.resultado.ciclo >= pemsData.resultado.periodoAcompanhamento.length) pemsData.resultado.ciclo = pemsData.resultado.periodoAcompanhamento.length-1;
		if (pemsData.resultado.ciclo < 0) pemsData.resultado.ciclo =0;
		console.log('Ciclo - '+pemsData.resultado.ciclo);
		resultadoView();
	}
	
	
	if (e.target.className.indexOf('pItemBoxCiclo')>=0){
		
		if (e.target.className.indexOf('pItemBoxCicloE')>=0) pemsData.produto.ciclo++;
		if (e.target.className.indexOf('pItemBoxCicloD')>=0) pemsData.produto.ciclo--;
		if (pemsData.produto.ciclo >= pemsData.produto.periodoAcompanhamento.length) pemsData.produto.ciclo = pemsData.produto.periodoAcompanhamento.length-1;
		if (pemsData.produto.ciclo < 0) pemsData.produto.ciclo =0;
		console.log('Ciclo - '+pemsData.produto.ciclo);
		produtoView();
	}
	
	if (e.type=='click' && e.target.className.indexOf('icoAtencao')>=0){
		GetData('atencao',';codR='+pemsData.resultado.codigo);	
	}

	if (e.target.className.indexOf('panelItem')>=0 || e.target.parentElement.parentElement.className.indexOf('panel')>=0){
		console.log('X:'+e.offsetX+' xX:'+e.originalEvent.layerX+' width - '+e.target.offsetWidth);
		if (e.touches) console.log( e.touches[0].pageX - e.touches[0].target.offsetLeft);
		switch(e.type){
			//case "mouseout": $(e.currentTarget).css('opacity',1); break;
			//case "mouseover": $(e.currentTarget).css('opacity',.7); break;
			case "click": 
			if (e.target.offsetWidth - e.offsetX<27 && e.offsetY<27){
				$(e.target).toggleClass('panelTitleSel');
				if (e.target.className.indexOf('panelTitleSel')>=0)  $('.objPanelHint').stop().slideUp('fast');
				if (e.target.className.indexOf('filtro')>=0)  {
					$(e.target).removeClass('panelTitleSel');
					if ($('.filtro'+e.target.className.split('filtro')[1].split(' ')[0]).hasClass('filSel')) $('.filtro'+e.target.className.split('filtro')[1].split(' ')[0]).find('.filtroSubItem').removeClass('filtroSubItemChecked')
					else $('.filtro'+e.target.className.split('filtro')[1].split(' ')[0]).find('.filtroSubItem').addClass('filtroSubItemChecked');
					FiltroUpdate();
				}
				return;
			}
		

			if (e.target.parentElement.id=="objPanel"){
				//console.log($('.objPanelHint').css('top'));
				if ($(e.target).css('background-color')==$('.objPanelHint').css('background-color')) $('.objPanelHint').stop().slideToggle('fast');
				else $('.objPanelHint').stop().slideDown('fast');
				$('.objPanelHint').attr('selObj', e.target.className.split('obj')[1].split(' ')[0]).css({'background-color':$(e.target).css('background-color'), 'top':e.target.offsetTop+e.target.offsetHeight});
				if (pemsData.oe['OE'+e.target.textContent]) $('.objPanelHint span').text(pemsData.oe['OE'+e.target.textContent].info);
				else $('.objPanelHint span').text('OE Indisponivel');
				//return;
			}
			
			if (e.target.className.indexOf('filtro')>=0){
				var offset;
				if ($('#searchSidebar').html().length>10) offset = $('#searchSidebar').offset().left - $('#searchSidebar #filtrosPanel').offset().left-6;
				else offset = $('#searchSplash').offset().left - $('#searchSplash #filtrosPanel').offset().left-13;

				//console.log(offset);
				
				if ($(e.target).children('.filtroSubBot').css('display')=='none'){
					$('.filtroSubBot').css('display', 'none');
					$(e.target).children('.filtroSubBot').css({'display':'block', 'left':-offset, 'top': e.target.offsetTop+e.target.offsetHeight});
				}else{
					$(e.target).children('.filtroSubBot').css('display', 'none');
				}
				FiltroUpdate(e.target);
			}
			
			//FilterSplash();
			break;
		}		
		//console.log(e.currentTarget.className);
	}
	
	if (!e.target) return;
	if (e.type=='click' && e.target.id=='sair')Logout();
}
function FiltroUpdate(target){
	var n, i;
	if (target){
		n=($(target).css('background-image').indexOf('s.gif')>=0)?$(target).css('background-image').split('filtro')[1].split('s.gif')[0]:$(target).css('background-image').split('filtro')[1].split('.gif')[0];
		
		i=9;
		while (i>=0){
			
			if (n==i.toString())--i;
			//console.log($('.filtro'+i).css('background-image').split('/')[$('.filtro'+i).css('background-image').split('/').length-1]);
			$('.filtro'+i).css('background-image','url(image/filtro'+i+'.gif)').removeClass('panelTitleSel'); 
			--i;
		}
		
		if (n.length>1) {if ($(target).css('background-image').indexOf('filtro'+n.split('i')[0]+'.gif')==-1) $(target).css('background-image','url(image/filtro'+n.split('i')[0]+'.gif)');}
		else {if ($(target).css('background-image').indexOf('filtro'+n+'i.gif')==-1) $(target).css('background-image','url(image/filtro'+n+'i.gif)');}
	}
	//console.log('filtro'+n.split('i')[0]+'.gif');
	
	
	i=pemsData.filterInfo.filters;
	var filters = new Object();
	while(i>0){
		--i;
		var bg = $('#f'+i).parent().parent().css('background-image');
		if (bg){
		var filterN=bg.split('filtro')[1].split('.gif')[0];
		var digit=(filterN.length>1)?filterN.slice(0, 1):filterN;
		
		if (isNaN(filters['totals'+digit])) filters['totals'+digit]=0;
		if (isNaN(filters['checked'+digit]))filters['checked'+digit]=0;	
		
		if($('#f'+i).css('background-image').indexOf('checked')>=0) filters['checked'+digit]++;

		filters['totals'+digit]++;
		
		//Changes filter background image
		if (filters['checked'+digit]>0){
			bg = 'url(image/filtro'+filterN+'s.gif)'	
			if (bg.indexOf('ss')>=0) {
				bg = 'url('+bg.split('s')[0]+'.gif)';
			}
			if ($('#f'+i).parent().parent().css('background-image').indexOf(bg.split('/')[bg.split('/').length-1])==-1) $('#f'+i).parent().parent().css('background-image', bg);	
		}else{
			bg='url(image/filtro'+bg.split('filtro')[1].split('s.gif')[0]+'.gif)';
			if ($('#f'+i).parent().parent().css('background-image').indexOf(bg.split('/')[bg.split('/').length-1])==-1) $('#f'+i).parent().parent().css('background-image', bg);	
		}
		
		//changes filter checkbox
		if (filters['totals'+digit]==filters['checked'+digit]){
			$('#searchSplash .filtro'+digit+', #searchSidebar .filtro'+digit).addClass('filSel');
		}else{
			$('#searchSplash .filtro'+digit+', #searchSidebar .filtro'+digit).removeClass('filSel');
		}
		}
			
	}
	
	console.log('filters');
	console.log(filters);
	
	$('#filtroSubSel ul').html('');
	if ($('.filtroSubItemChecked').length>0) {
		$('.filtroSubItemChecked').each(function(index, element) {
			$('#filtroSubSel ul').append('<li>'+element.textContent+'</li>');
		});
	}else $('#filtroSubSel ul').html('<li>Nenhum</li>');
	
/*
	$('.filtroSubItemChecked').each(function(index, element) {
		var bgimage = $(element.parentElement.parentElement).css('background-image').split('filtro')[1].split('.')[0];
		if (bgimage.split('i').length>1)
		$(element.parentElement.parentElement).css('background','url(image/filtro'+bgimage+'.gif)');
	});
	*/
	
}

function BreadcrumUpdate(){
	var breadcrum = '';
	if (searchObj.oe.length>0) $.each(searchObj.oe.split(','), function(i,e){
			breadcrum += 'OE '+e+' + ';
	});
	if (searchObj.filtros.length>0) $.each(searchObj.filtros.split(','), function(i,e){
			breadcrum += e+' + ';
	});
	if (searchObj.tags.length>0) $.each(searchObj.tags.split(','), function(i,e){
			breadcrum += e+' + ';
	});
	breadcrum = breadcrum.substring(0, breadcrum.length - 3);
	$('#breadcrum').text(breadcrum);
}

function Login(e){
	if ( $('#user').val().length>0){
		if ( $('#pass').val().length>0){
			GetData('login');
		}else alert('Digite a senha');
	}else alert('Digite o nome do usuário');

}

function ShowSearch(){
	console.log('ShowSearch');
	$('#body').css('overflow-y', 'auto');
	$('head link').each(function(index, element) {
        if ($(element).attr('href').indexOf('sidebar.css')>=0) $(element).remove();// attr('href', 'css/pems.css');
    });
	Hide($('#searchSidebar'));
	Hide($('#results'));
	Hide($('#loginBg'));
	Show($('#searchSplash'));
	Show($('#status'));
	//Show($('#footer'));
	
	var displayed = ($('.filtroSubBot').filter(function() { return $(this).css("display") == "block" }).length>0)?$('.filtroSubBot').filter(function() { return $(this).css("display") == "block" })[0].parentElement:'';
	console.log(displayed);
	$('.objPanelHint, .filtroSubBot').css('display', 'none');//.stop().slideUp(0);
	if (displayed) FiltroUpdate(displayed);
	
	$("#searchBtn").remove().insertAfter($("#pe"));
	
	
	if ($('#body > #status').length==0) {
		$('#body').prepend($('#searchSidebar #status')[0]);
		$('#searchSidebar').remove('#status');
	}
	
	if ($('#searchSplash').html().length<10) {
		$('#searchSplash').html($('#searchSidebar').html());
		$('#searchSidebar').html('');
	}
//	console.log('#body #status '+$('#body #status').length);
	$('.ac_results').remove();
	//$("#searchFieldInput").autocompleteArray(pemsData.searchTags, {matchContains:1});
	
	$('.menuLogged').css('display','inline');
	if (!pemsData.oe) GetData('oeinfo');
	if (!pemsData.status) GetData('status');
	if (!pemsData.tagCloud) GetData('taginfo');
	if (!pemsData.filterInfo) GetData('filterinfo');
	if (!pemsData.exercicio) GetData('exercicio');
//	if ($('#searchFieldInput').val().length>0) tagInfoScan();
//	GetData('filtroinfo');
	//LoadSplash();
//	Resize();	

	$('#comboExercicio').change(function(){
			pemsData.exercicio=$(this).val();
			var totalStatus=0;
			anoExercicioSelecionado = $('#comboExercicio option:selected').text();
			$('.statusCount:visible').each(function(){totalStatus++;});
			if(totalStatus>0){
				$('.styled-select-load').css("visibility", "visible");
				$('#comboExercicio').attr('disabled', 'disabled');
				GetData('status');
			}	
		});		

	setTimeout(Resize,250);
}

function ShowSidebar(){
	$('head').append('<link href="css/sidebar.css" rel="stylesheet">');
	setTimeout(function(){
	console.log('LENGTH '+$('#searchSidebar').html().length);
	if ($('#searchSidebar').html().length<10) { 
		$('#searchSidebar').html($('#searchSplash').html());
		$('#searchSplash').html('');
	}
	if ($('#body > #status').length) {
		$('#searchSidebar').append($('#body #status')[0]); 
		$('#body').remove('#status');	
	}
	
	$("#searchBtn").remove().insertAfter($(".filtro9"));
	//$('#searchSidebar #objPanel').html($('#searchSplash #objPanel').html());
//	$('#searchSidebar #filtrosPanel').html($('#searchSplash #filtrosPanel').html());
	var displayed = ($('.filtroSubBot').filter(function() { return $(this).css("display") == "block" }).length>0)?$('.filtroSubBot').filter(function() { return $(this).css("display") == "block" })[0].parentElement:'';
	$('.objPanelHint, .filtroSubBot').css('display', 'none');//.stop().slideUp(0);
	console.log(displayed);
	if (displayed) FiltroUpdate(displayed);
	else FiltroUpdate();

	Show($('#searchSidebar'));
	$('#searchSidebar').animate({ scrollTop: 0}, 500, 'easeInOutExpo');
	//Resize(null);
	},100);
}


function ShowResults(update){
	
//	console.log('DENTRO DO ShowResults');
//	console.log(searchObj);
	
	resultsOffset=163;
	pemsData.resultado=null;
	pemsData.produto=null;
	pemsData.painel=null;
	ShowSidebar();
	Hide($('#searchSplash'));
	//Hide($('#footer'));
	
	Show($('#results'));
	Show($('#label'));
	Hide($('#resultsBox'));
	//Hide($('#status'));
	console.log('Results');
	
	
	$('#resultsBox').removeClass('resultsBoxStatic').addClass('resultsBoxcol8');
	$('#body').css('overflow-y', 'hidden');


	$('#navTitle, #breadcrum').removeClass();	
	$('#navTitle').text('RESULTADOS DA BUSCA');
	$('#breadcrum').html('<img src="image/loading.gif" width="150" height="20" />');
	$('#navInfo').text('');
	$('#statusMonitor, #label').css('display', 'none');
	$('#navInfo').css('text-indent', '0');
	
	$('#nav').css('max-width', '');
	$('#nav').css('margin', '');
	$('#nav').css('padding-left', '');
	$('#breadcrum').css('text-align','left');
	
	
	if (searchObj.oe.length!=0 && searchObj.oe.split(',').length==1){
		$('#navTitle').addClass('obj'+searchObj.oe+'t').addClass('navTitleOE');
		$('#breadcrum').addClass('breadcrumOE');			
		$('#navInfo').css('display', 'block');
	}else{
		$('#navTitle').addClass('fsel');
		$('#breadcrum').addClass('breadcrumM');
		$('#navInfo').css('display', 'none');
	}
	$('#rItemBox1, #pItemBox1').remove();
	
	if(pemsData.exercicio)
		$('#comboExercicio').val(pemsData.exercicio);
	else
		$('#comboExercicio').val(searchObj.exercicio);		

	if (update) GetData('search')
	else setTimeout(searchView,100);
	
	
	
	
	
	//Resize(null);
	setTimeout(Resize,250);
	
}

function UpdatePainelIndicadores(){
	resultsOffset=90;
	ShowSidebar();
	Hide($('#searchSplash'));
	//Hide($('#footer'));
	
	Show($('#results'));
	Show($('#label'));
	Hide($('#resultsBox'));
	//Hide($('#status'));
	console.log('Results');
	
	
	$('#resultsBox').removeClass('resultsBoxStatic').addClass('resultsBoxcol8');
	$('#body').css('overflow-y', 'hidden');


	$('#navTitle, #breadcrum').removeClass();	
	$('#navTitle').text('RESULTADOS DA BUSCA');
	$('#breadcrum').html('<img src="image/loading.gif" width="150" height="20" />');
	$('#navInfo').text('');
	$('#statusMonitor, #label').css('display', 'none');
	$('#navInfo').css('text-indent', '0');
	
	$('#nav').css('max-width', '');
	$('#nav').css('margin', '');
	$('#nav').css('padding-left', '');
	$('#breadcrum').css('text-align','left');

	$('#navTitle').addClass('fsel');
	$('#breadcrum').addClass('breadcrumOE');
	$('#navInfo').css('display', 'block');

	$('#rItemBox1, #pItemBox1').remove();
	
	GetData('PainelIndicadores');
	
	//Resize(null);
	setTimeout(Resize,250);
}

function ShowPainelIndicadores(){
	resultsOffset=90;
	pemsData.resultado=null;
	pemsData.produto=null;
	pemsData.results=null;
	ShowSidebar();
	Hide($('#searchSplash'));
	//Hide($('#footer'));
	
	Show($('#results'));
	Show($('#label'));
	Hide($('#resultsBox'));
	//Hide($('#status'));
	console.log('Results');
	
	
	$('#resultsBox').removeClass('resultsBoxStatic').addClass('resultsBoxcol8');
	$('#body').css('overflow-y', 'hidden');


	$('#navTitle, #breadcrum').removeClass();	
	$('#navTitle').text('RESULTADOS DA BUSCA');
	$('#breadcrum').html('<img src="image/loading.gif" width="150" height="20" />');
	$('#navInfo').text('');
	$('#statusMonitor, #label').css('display', 'none');
	$('#navInfo').css('text-indent', '0');
	
	$('#nav').css('max-width', '');
	$('#nav').css('margin', '');
	$('#nav').css('padding-left', '');
	$('#breadcrum').css('text-align','left');

	$('#navTitle').addClass('fsel');
	$('#navTitle').addClass('obj'+pemsData.selOE+'t').addClass('navTitleOE');
	$('#breadcrum').addClass('breadcrumOE');
	$('#navInfo').css('display', 'block');

	$('#rItemBox1, #pItemBox1').remove();
	
	GetData('PainelIndicadores');
	
	//Resize(null);
	setTimeout(Resize,250);
}

function ShowResultado(param, update){
	
	resultsOffset=103;
	
	ShowSidebar();
	Hide($('#searchSplash'));
	//Hide($('#footer'));
	
	Show($('#results'));
	Hide($('#label'));
	Hide($('#resultsBox'));
	//Hide($('#status'));
	
//	$('head').append('<link href="css/sidebar.css" rel="stylesheet">');
	$('#resultsBox').removeClass('resultsBoxStatic').addClass('resultsBoxcol8');
	$('#body').css('overflow-y', 'hidden');
	$('#label').css('display', 'none').html('');

	$('#navTitle, #breadcrum').removeClass();	
	$('#navInfo').text('');
//	$('#statusMonitor').css('display', 'block');
	$('#statusMonitor').css('display', 'none');
	
	console.log('OE ----'+searchObj.oe);
	
	$('#navTitle').text('CARREGANDO');
//	$('#navTitle').addClass('obj'+searchObj.oe+'t').addClass('navTitleOE');
	$('#navTitle').addClass('obj'+pemsData.selOE+'t').addClass('navTitleOE');
	$('#breadcrum').addClass('breadcrumOE');			
	$('#navInfo').css('display', 'block');
	$('#rItemBox1, #pItemBox1').remove();
	$('.ac_results').remove();
	
	if (update) GetData('resultado',param);
	else setTimeout(resultadoView,100);
	
	Resize(null);
}

function ShowProduto(param, update){
	
	resultsOffset=103;
	
	ShowSidebar();
	Hide($('#searchSplash'));
	//Hide($('#footer'));
	
	Show($('#results'));
	Hide($('#label'));
	Hide($('#resultsBox'));
	//Hide($('#status'));
	
//	$('head').append('<link href="css/sidebar.css" rel="stylesheet">');
	$('#resultsBox').removeClass('resultsBoxStatic').addClass('resultsBoxcol8');
	$('#body').css('overflow-y', 'hidden');
	$('#label').css('display', 'none').html('');

	$('#navTitle, #breadcrum').removeClass();	
	$('#navInfo').text('');
	$('#statusMonitor').css('display', 'block').slideUp(0);
//	$('#statusMonitor').css('display', 'none');
	
	console.log('OE ----'+searchObj.oe);
	
	$('#navTitle').text('CARREGANDO');
//	$('#navTitle').addClass('obj'+searchObj.oe+'t').addClass('navTitleOE');
	$('#navTitle').addClass('obj'+pemsData.selOE+'t').addClass('navTitleOE');
	$('#breadcrum').addClass('breadcrumOE');			
	$('#navInfo').css('display', 'block');
	$('#rItemBox1, #pItemBox1').remove();
	
	if (update) GetData('produto',param);
	else setTimeout(produtoView,100);

	Resize(null);
}

function ShowStatic(page){
	Hide($('#searchSplash'));
	//Hide($('#footer'));
	
	//ShowSidebar();

	Show($('#results'));
	Hide($('#label'));
	Hide($('#status'));
	$('#statusMonitor').css('display', 'none');
	Hide($('#searchSidebar'));
	
	
	
	//$('head').remove('<link href="css/sidebar.css" rel="stylesheet">');
	$('#body').css('overflow-y', 'auto');
	$('#resultsBox').removeClass('resultsBoxcol8').addClass('resultsBoxStatic');
	$('#navTitle, #breadcrum').removeClass();
	$('#navTitle').addClass('fsel');
	$('#breadcrum').addClass('breadcrumOE');
	$('#rItemBox1, #pItemBox1').remove();
	
	$.get(page,  function(data){
		console.log('data');
		$('#resultsBox').html($(data).find('#text').html()); 
		$('#navTitle').html($(data).find('#title').html());
		$('#breadcrum').html($(data).find('#subtitle').html());
		$('#navInfo').css('text-indent', '-9999px');
		
		
		$('#nav').css('margin', '0 auto');
		$('#nav').css('padding-left', '43px');
		
		
	
		
	}); 
	/*
	$('#resultsBox').html('<span style="text-align:justify"><img src="image/materia1.jpg" align="left" style="padding:25px 25px 0 0;"><p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc id felis lacus, nec fringilla odio. Donec vel tortor ac nisl volutpat pulvinar. Nullam dapibus, dolor rutrum dignissim porta, justo odio rutrum purus, pulvinar lobortis ligula lorem non eros. Curabitur blandit faucibus pretium. Integer pretium est sit amet sem porttitor eu rhoncus massa congue. Maecenas interdum posuere iaculis. Pellentesque sed mauris sed risus hendrerit pharetra sit amet eget odio. In hac habitasse platea dictumst.</p><p>Proin eget est ac nunc molestie hendrerit non sit amet ligula. Phasellus non orci lectus, vel dignissim sem. Proin ac purus a augue ornare dapibus. Pellentesque at vulputate sapien. Suspendisse potenti. Etiam a pretium justo. Nulla eleifend malesuada tellus in sagittis. Phasellus vel est ipsum. Morbi eget augue non elit iaculis tincidunt. Donec quis dolor et urna aliquam ultricies vel eget neque. Aenean semper arcu in mauris pharetra sit amet accumsan justo scelerisque. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Duis in tellus vel turpis eleifend convallis. Praesent ornare, purus id convallis euismod, augue nisl aliquet turpis, sed tincidunt dui mi et sem. Integer at nibh in ligula aliquam viverra. Nam eleifend tortor vel nibh tincidunt tristique.</p><p>Maecenas luctus suscipit iaculis. Sed et tempus purus. Cras vehicula dictum leo ut condimentum. Etiam nec imperdiet purus. Nullam sollicitudin, nisl ut bibendum rutrum, enim est congue lacus, at faucibus nisi lacus in ipsum. Donec condimentum, nunc nec lobortis interdum, justo orci sodales arcu, ut venenatis dolor eros vitae ante. Suspendisse condimentum ullamcorper odio quis facilisis. Donec dapibus mi a purus convallis cursus pellentesque nisl molestie. Donec sed ante massa, in suscipit erat. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Ut lacinia tellus a neque faucibus scelerisque. Duis vulputate augue eu quam tincidunt vel convallis urna hendrerit. Etiam at erat eu neque sollicitudin facilisis non eget nibh. Vivamus ultrices eros ac tortor convallis tincidunt. Aliquam erat volutpat.</p><p><ul><li>Lorem ipsum dolor sit amet, consectetur adipiscing elit.</li><li>Aliquam mattis sapien in massa eleifend faucibus.</li><li>Donec elementum venenatis mi, vel blandit orci pretium eu.</li><li>Curabitur tempus enim vitae est luctus a faucibus sapien elementum.</li><li>Proin dapibus justo ut tortor lacinia at ornare eros aliquam.</li></ul></p><p>Morbi porta ante ac ligula tincidunt vel ultrices quam sagittis. Duis consectetur adipiscing risus at volutpat. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Vestibulum ac augue dolor, eget pulvinar justo. Donec non felis lorem. Integer justo dui, elementum quis tempus eu, egestas commodo erat. Donec quis quam eros. Vivamus auctor ante nec mauris adipiscing pretium et non purus. Aliquam ut lorem metus. Suspendisse et augue lectus, nec mattis neque. Aliquam ullamcorper, ipsum et sollicitudin hendrerit, mauris velit interdum dui, sit amet fermentum sem nunc congue diam. Aliquam varius nisl eu magna varius lacinia. Quisque quis neque augue, vel iaculis sapien. Vivamus commodo est vel sem tempor ut ultricies neque gravida.</p></span>');
	*/
//	$('#body').css('overflow-y', 'none');
	Resize(null);
}

function Logout(){
	Hide($('#status'));
	Hide($('#searchSplash'));
	//Hide($('#footer'));
	Hide($('#searchSidebar'));
	//Hide($('#results'));
	$('.menuLogged').css('display','none');
	$('#body').css('overflow-y', 'hidden');
	Show($('#loginBg'));
	GetData('logoff');
	
}


function StatusFilter(e){

	
	var si=e.currentTarget.className.split('si')[1].split(' ')[0];
	
	var total=0;
	$.each(pemsData.statusFilter, function(i,v){
		total+=v;
	});
	if (total==6) {
		$('.statusItem').css('opacity', .3);
		pemsData.statusFilter=[.3,.3,.3,.3,.3,.3];
	}

	pemsData.statusFilter[si]= (pemsData.statusFilter[si]==1)?.3:1;	
		
	$.each(pemsData.statusFilter, function(i,v){
		$('.si'+i).css('opacity', v);
	});
	
	total=0;
	$.each(pemsData.statusFilter, function(i,v){
		total+=v;
	});

	if (total==1.8) {
		$('.statusItem').css('opacity', 1);
		pemsData.statusFilter=[1,1,1,1,1,1];
	}	
}


function LoadSplash(){
	$.get('generator.php',  function(data){
			dataset = JSON.parse(data);
			FilterSplash();
	});
}

function FilterSplash(){
	
			var status = [0,0,0,0,0,0];
			var sq = new Object();
			sq.o = new Array();
			

			$.each($('.panelItem'), function(index,value){ if(value.className.indexOf('panelTitleSel')>=0) sq.o.push(value.innerHTML); });
						
			var i = dataset.length-1;
			while(i>0){
				if (sq.o.length==0) {
					status[dataset[i].s]++;
				}else{
					for (var j=0; j<sq.o.length; j++){
					    if (dataset[i].o.indexOf(' '+sq.o[j]+' ')>=0) status[dataset[i].s]++;
					}
				}
				--i;
			}
			$('.statusCount').each(function(i,v){AnimateVal(v, parseInt(v.innerHTML,10), parseInt(status[i-6])); });

}

function GetData(type,params){
	//if (!params) params = new Object;
	//params.usuario = 'teste';
	//params.senha='teste';
	
	
	var interfaceSearch, paramString='';
//	console.log('Selected OEs '+searchObj.oe);	
	switch(type){
		case 'login':
			interfaceSearch='loginWS';
			var code = "KBV5I61HF3RWV9C01LG246TVD";
			var key = CryptoJS.MD5(code+CryptoJS.MD5($('#user').val()+$('#pass').val()));
			paramString=';usuario='+$('#user').val()+';senha='+key;
			$('#pass').val('');
		break;
		case 'logoff':
			interfaceSearch='logOffWS';
			paramString=';uuid='+loginKey;
		break;
		case 'search':
			interfaceSearch='buscaObjetivoEstrategicoFiltro';
//			paramString = ';codOE='+pemsData.oe['OE'+searchObj.oe].codigo;
			if (searchObj.oe.length>0) $.each(searchObj.oe.split(','), function(i,e){
				paramString += (pemsData.oe['OE'+e])?';codOE='+pemsData.oe['OE'+e].codigo:'';
			})
			if (searchObj.status.length>0) $.each(searchObj.status.split(','), function(i,e){
				if (parseInt(e)>=4)e=parseInt(e)+6;
				paramString += ';rStatus='+e;
			})
			if (searchObj.tags.length>0) $.each(searchObj.tags.split(','), function(i,e){
				if (e.length) paramString += ';etiqueta='+trim(e);
			})
			if (searchObj.filtros.length>0) $.each(searchObj.filtros.split(','), function(i,e){
				if (e.length) paramString += ';etiquetaP='+trim(e);
			})
			if (searchObj.exercicio.length>0) {
				paramString += ';exercicio='+searchObj.exercicio;
			}
			
			paramString += ';painel=0';
			
		break;
		case 'PainelIndicadores':
			interfaceSearch='buscaPainelIndicadores';
			if (searchObj.oe.length>0) $.each(searchObj.oe.split(','), function(i,e){
				paramString += (pemsData.oe['OE'+e])?';codOE='+pemsData.oe['OE'+e].codigo:'';
			})
			if (searchObj.status.length>0) $.each(searchObj.status.split(','), function(i,e){
				if (parseInt(e)>=4)e=parseInt(e)+6;
				paramString += ';rStatus='+e;
			})
			if (searchObj.tags.length>0) $.each(searchObj.tags.split(','), function(i,e){
				if (e.length) paramString += ';etiqueta='+trim(e);
			})
			if (searchObj.filtros.length>0) $.each(searchObj.filtros.split(','), function(i,e){
				if (e.length) paramString += ';etiquetaP='+trim(e);
			})
			if (searchObj.exercicio.length>0) {
				paramString += ';exercicio='+searchObj.exercicio;
			}
			
			paramString += ';painel=1';
			
		break;		
		case 'status':
			interfaceSearch='buscaResultadoStatus';
			if(pemsData.exercicio)
				paramString += ';exercicio='+pemsData.exercicio;
		break;
		case 'oeinfo':
			interfaceSearch='buscaObjetivoEstrategicoLista';
		break;
		case 'filterinfo':
			interfaceSearch='buscaEtiquetaPrioritaria';
		break;
		case 'taginfo':
			interfaceSearch='buscaEtiqueta';
		break;
		case 'resultado':
			interfaceSearch='buscaResultado';
			if(pemsData.exercicio){
				paramString=';exercicio='+pemsData.exercicio;
				paramString+=';codR='+params;
			}
			else
				paramString=';codR='+params;
		break;
		case 'produto':
			interfaceSearch='buscaProduto';
			//if(pemsData.exercicio){
				paramString=';exercicio='+pemsData.exercicio;
				paramString+=';codP='+params;
			//}			
			//paramString=';codP='+params;
		break;
		case 'comment':
			interfaceSearch='gravarComentario';
			paramString=params;
		break;
		case 'responsavel':
			interfaceSearch='buscaResponsavel';
			paramString=';nome=';
		break;
		case 'atencao':
			interfaceSearch='marcarAtencaoMinistro';
			paramString=params;
		break;
		case 'exercicio':
			interfaceSearch='buscaExercicio';
		break;
		case 'justificativa':
			interfaceSearch='gravarJustificativa';
			paramString=params;
		break;		
		
	}
	
	console.log(paramString);
	console.log(baseUrl+interfaceSearch+paramString);
	
	
	
	$.getJSON(baseUrl+interfaceSearch+paramString+';uuid='+loginKey,function(json) { 
		console.log('server-');
		console.log(json);
		
	   window[type+'Data'](json);
	   
	})
	.success(function() { $('.styled-select-load').css("visibility", "hidden"); $('#comboExercicio').removeAttr('disabled');})
	.error(function(e){window[type+'Data'](''); $('.styled-select-load').css("visibility", "hidden"); $('#comboExercicio').removeAttr('disabled');});

}

function loginData(data){
	console.log('LOGING')
	console.log(data);

	if (data.autenticado=='true'){
		loginKey = data.uuid;
		
    if (!$.browser.opera) {
        $('select.select').each(function(){
            var title = $(this).attr('title');
            if( $('option:selected', this).val() != ''  ) title = $('option:selected',this).text();
            $(this)
                .css({'z-index':10,'opacity':0,'-khtml-appearance':'none'})
                .after('<span class="select">' + title + '</span>')
                .change(function(){
                    val = $('option:selected',this).text();
                    $(this).next().text(val);
                    })
        });

    };			
		
		
		
		ShowSearch();
		

	}else{
		alert('Usuário ou senha inválidos');
		return;	
	}
	
}
function logoffData(data){
	console.log(data);	
}

function PainelIndicadoresData(data){
	if (!data) {
		$('#navTitle').text('NENHUM DADO RETORNADO');
		pemsData.painel='';
		BreadcrumUpdate();
		return;
	}
	pemsData.painel = data;
	PainelIndicadoresView();
}

function PainelIndicadoresView(){
	$('#resultsBox').html('');
	$('#rItemBox1, #pItemBox1').remove();
	
	var navDisplay = 	"<div id='rItemBox1'>\
							<div id='rItemBox1m1'>$icoDiv$nome</div>\
							<div id='rItemBox1m2'><p>STATUS</p><p>CICLO</p><div class='rItemBoxCicloE po'></div><div class='rItemBoxCicloD po'></div></div>\
							<div id='rItemBox1m3'><p>RESPONSÁVEL</p><p>CORRESPONSÁVEL</p></div>\
							<div id='rItemBox1m4'>Articulação</div>\
							<div id='rItemBox1m5'><img src='image/status$statusCode.gif' width='46' height='46' /><br/><span class='statusCaption'>$statusCaption</span></div>\
							<div id='rItemBox1m6'><p>$respinst - $respnome</p><p>$corespinst - $corespnome</p></div>\
							<div id='rItemBox1m7'>$articula</div>\
						</div>";	
	
	var page = ''
	var statusSum = [0,0,0,0,0,0];
	var tselOE = '';
	var selOE = '';
	$(pemsData.painel.objetivoEstrategico).each(function(indexOe, elementOe){
		tselOE = elementOe.siglaIett.split('OE ')[1];
		selOE = tselOE.split('0')[1];
		if(selOE == undefined) {
			selOE = tselOE;
		} 
		$(elementOe.estrategias).each(function(indexE, elementE){
			$(elementE.resultados).each(function(indexR, elementR){	
				
				var newItem = "<div id='rItemBox1'>\
									<div id='rItemBox01m1'>$icoDiv "+selOE+"."+elementR.siglaResultado+" - $nome</div>\
									<div id='rItemBox1m2'><p>STATUS</p><p>CICLO</p><div class='rItemBoxCicloE po'></div><div class='rItemBoxCicloD po'></div></div>\
									<div id='rItemBox1m3'><p>RESPONSÁVEL</p></div>\
									<div id='rItemBox1m4'></div>\
									<div id='rItemBox1m5'><img src='image/status$statusCode.gif' width='46' height='46' /><br/><span class='statusCaption'>$statusCaption</span></div>\
									<div id='rItemBox1m6'><p>$respinst - $respnome</p></div>\
									<div id='rItemBox1m7'></div>\
								</div>\
								<div id='rItem' class='obj"+selOE+"'>\
									<div id='rItemBox3'>\
										<div id='rItemBox3m1' class='itemTitle'><div>INDICADORES</div><div>2011</div><div>2012</div><div>2013</div><div>2014</div></div>\
										<div id='rItemBox3m2'>$indContent</div>\
									</div>\
								</div>";
				
				var indRow= "<div>\
								<div class='indRow'>\
									<div class='indDesc'>$nomeIndicador</div>\
									$indResul\
								</div>\
								<div class='indJustificativa'>Justificativa</div>\
							</div> ";
				var indResul = "<div class='indResultado'>\
									<div class='pr'>Meta/Realizado<br><div>M<br>R</div><div>$previsto<br>$realizado</div></div>\
									<div class='atingimento'><img src='image/$cor'><br>$atingimento%</div>\
								</div>"
				var indContent='';
				
				newItem = newItem.split('$nome').join(elementR.nome);
				var iclass = '';
				if (elementR.prioritario=="S") iclass+="<div class='icoPrioritario' title='Prioritário'></div>";
				if (elementR.atencao=='true') iclass+="<div class='icoAtencao po' title='Atenção ao Ministro'></div>";
				newItem = newItem.split('$icoDiv').join(iclass);
				newItem = newItem.split('$respinst').join(elementR.responsavel.orgao.nome);
				newItem = newItem.split('$respnome').join(elementR.responsavel.nome);
				//if (isNaN(pemsData.resultado.ciclo)) pemsData.resultado.ciclo = 0;
				newItem = newItem.split('$statusCode').join((elementR.periodoAcompanhamento[0].statusPeriodoAcompanhamento.codigo>=10)?elementR.periodoAcompanhamento[0].statusPeriodoAcompanhamento.codigo-6:elementR.periodoAcompanhamento[0].statusPeriodoAcompanhamento.codigo);
				newItem = newItem.split('$statusCaption').join(elementR.periodoAcompanhamento[0].mes+'/'+elementR.periodoAcompanhamento[0].ano);
				//if (elementR.periodoAcompanhamento[0].parecer) newItem = newItem.split('$parecerant').join(elementR.periodoAcompanhamento[0].parecer/*.replace(/(\r\n|[\r\n])/g, "<br />")/*.replace(/(<([^>]+)>)/ig,"")*/);				
				
				if (elementR.periodoAcompanhamento[0].indMonitoramento && elementR.periodoAcompanhamento[0].indMonitoramento.length) $.each(elementR.periodoAcompanhamento[0].indMonitoramento, function(indexI, elementI){	
					var temp='';
					if (elementI.valores && elementI.valores.length) $.each(elementI.valores, function(indexV, elementV){
						//TODO: adicionar um campo na tabela tb_cor para armazenar o caminho da imagem no site do PPE que é diferente do eCar
						switch(elementV.cor)
						{
							case 'sVerde9.png':
							  elementV.cor = 'status1.gif';
							  break;
							case 'sAmarelo9.png':
							  elementV.cor = 'status2.gif';
							  break;
							case 'sVermelho9.png':
							  elementV.cor = 'status3.gif';
							  break;
							case 'sBranco9.png':
							  elementV.cor = 'status0.gif';
							  break;  
							case 'nar.gif':
							  elementV.cor = 'status0.gif';
							  break;
						}
						temp += indResul.split('$previsto').join(elementV.previsto).split('$realizado').join(elementV.realizado).split('$cor').join(elementV.cor).split('$atingimento').join(elementV.atingimento);
					});
					indContent+=indRow.split('$nomeIndicador').join(elementI.nomeIndicador).split('$indResul').join(temp);
				});
				newItem = newItem.split('$indContent').join(indContent);
					
				//newItem = newItem.split('$cronoItems').join(cronoItemSum);
				
				//$('#nav').append(navDisplay);
				page += newItem;
			});
		});
	});

	$('#resultsBox').html(page);
	Show($('#resultsBox'));
	//$('#resultsScroll').fadeIn('2000');
	//$('.statusMCount').fadeIn('2000');
	$('#resultsBox').scrollTop(0);
	BreadcrumUpdate();
		
	$('.s1 .statusMCount').text(statusSum[0]);
	$('.s2 .statusMCount').text(statusSum[1]);
	$('.s3 .statusMCount').text(statusSum[2]);
	$('.s4 .statusMCount').text(statusSum[3]);
	$('.s5 .statusMCount').text(statusSum[4]);
	$('.s0 .statusMCount').text(statusSum[5]);
	$('#statusMonitor').slideDown(1000, 'easeOutExpo', Resize);	

	Resize(null);
}

function searchData(data){

	$('#comboExercicio').change(function(){
		pemsData.exercicio=$(this).val();
		anoExercicioSelecionado = $('#comboExercicio option:selected').text();
		var totalStatus=0;
		$('.statusCount:visible').each(function(){totalStatus++;});
		if(totalStatus>0){
			$('.styled-select-load').css("visibility", "visible");
			$('#comboExercicio').attr('disabled', 'disabled');
			GetData('status');
		}	
	});

	if (!data) {
		$('#navTitle').text('ERRO - NENHUM DADO RETORNADO');
		pemsData.results='';
		return;
	}

	pemsData.results = data.objetivoEstrategico;
	searchView();
}

function exercicioData(data){

	
	$('#comboExercicio').empty();

	$('#comboExercicio').append('<option value="0" >Todos os Exercícios</option>');
	var selecionado = '';
	$.each(data.exercicioExe, function(i, item) {
		if(pemsData.exercicio&&item.codExe==pemsData.exercicio){
			selecionado = 'selected="selected"';
		}
		else if(item.cicloCorrenteExe=="true"){
			selecionado = 'selected="selected"';
			pemsData.exercicio=item.codExe;
		}
		else{
			selecionado = '';
		}
		
		$('#comboExercicio').append('<option value="'+item.codExe+'" '+selecionado+'>'+item.descricaoExe+'</option>');
	});
	
	
	
	console.log('exercicioData');
	console.log(data);
}


function searchView(){
	//$item $objN &oe $oeinfo $id $oeres $statusCode $respinst $respname $corespinst $corespname
	$('#resultsBox').html('');
	 $('html').animate({scrollTop:0}, 200); 
	var mainItem = '<div class="resultItem $objN"><div class="resultItemFrame">$item</div><div class="popupInfo po"><span class="popupTitle">$oe</span><br /><span class="popupDesc">$oeInfo</span></div></div>';
	var col= '<div class="col8">$content</div>';
	var col1='<div class="col8 row"><span class="objIdFirst">$oe</span><span class="objOEinfo">$oeinfo</span></div>';
	var colRow= '<div class="row $id">$content</div>';
	var colRowBold= '<div class="row bold $id">$content</div>';
	var colSubrow='<div class="subrow $id">$content</div>';
	
	var col2Content='<span class="objId">$code</span><span class="objResinfo $oeId" id="param$itemId">$info</span>';
	var col3Content='<img src="image/status$statusCode.gif" width="46" height="46" /><span class="statusCaption">$statusCaption</span>';
	var col4Content='<p>$respinst<br />$respname</p><p>$corespinst<br />$corespname</p><div class="popupArt po"><span class="popupArtTitle">ARTICULADOR</span><br /><span class="popupArtInfo">$artinst<br />$artname</span></div>';
	var col5ContentItem='<div class="indicador">$indicator</div>';
//	var col6Content='<p>$respinst<br />$respname</p><p>$corespinst<br />$corespname</p>';
	
	console.log('searchView');
	console.log(pemsData.results);
	
	var statusSum = [0,0,0,0,0,0];

	$('#comboExercicio').val(searchObj.exercicio);
	
	var oeNumber = pemsData.results[0].siglaIett.split(' ')[1]<<0;
	
	$.each(pemsData.results, function(index, element) {
		//console.log(element);
		
		
		oeNumber = element.siglaIett.split(' ')[1]<<0;

		if (element.estrategias) $.each(element.estrategias, function(indexE, elementE){
			//console.log(elementE);
			
			var ncolSum='';
			var scolSum='';
			var rcolSum='';
			var icolSum='';
			var mcolSum='';
			var vcolSum='';
			var ccolSum='';
			
			
			var newItem = mainItem;
			newItem = newItem.split('$objN').join('obj'+oeNumber);
			newItem = newItem.split('$oeInfo').join(element.nome);
			newItem = newItem.split('$oe').join(element.siglaIett);
			var ncol = col1;
			ncol = ncol.split('$oeinfo').join(elementE.nome);
			ncol = ncol.split('$oe').join(elementE.siglaEstrategia<<0);
	
			if (elementE.resultados && elementE.resultados.length) $.each(elementE.resultados, function(indexR, elementR){
				
				var ncontent = col2Content
				
				ncontent = ncontent.split('$code').join((elementE.siglaEstrategia<<0)+'.'+((elementR.siglaResultado.indexOf('.')>=0)?elementR.siglaResultado.split('.')[1]<<0:elementR.siglaResultado<<0));
				//ncontent = ncontent.split('$code').join(elementR.siglaResultado);
				ncontent = ncontent.split('$info').join(elementR.nome);
				ncontent = ncontent.split('$itemId').join(elementR.codigo);
				ncontent = ncontent.split('$oeId').join('obj'+oeNumber);			
				
				var flags = '';
				
				if (elementR.atencao=='true') {
					flags = ' rowAtencao';
					ncontent="<div class='icoAtencao' title='Atenção ao Ministro'></div>"+ncontent;
				}			

				if (elementR.prioritario=="S") {
					flags += ' rowPrioritario';
					ncontent="<div class='icoPrioritario' title='Prioritário'></div>"+ncontent;
				}
				


				
				if (elementR.produtos && elementR.produtos.length>=1) ncontent="<div class='expandToggle'></div>"+ncontent;
				var row2 = colRowBold;
				row2 = row2.split('$id').join('id'+oeNumber+'_'+(elementE.siglaEstrategia<<0)+'_'+((elementR.siglaResultado.indexOf('.')>=0)?elementR.siglaResultado.split('.')[1]<<0:elementR.siglaResultado<<0)+flags);
				row2 = row2.split('$content').join(ncontent);
				
				var statusContent = col3Content;
				if (elementR.periodoAcompanhamento) {
					var statusCodeR = elementR.periodoAcompanhamento[0].statusPeriodoAcompanhamento.codigo;
					if (statusCodeR>=10) statusCodeR-=6;
					statusSum[statusCodeR]++;
					statusContent = col3Content.split('$statusCode').join(statusCodeR);
					statusContent = statusContent.split('$statusCaption').join(elementR.periodoAcompanhamento[0].mes+'/'+elementR.periodoAcompanhamento[0].ano);
				}

				scolSum+= colRow.split('$id').join('id'+oeNumber+'_'+(elementE.siglaEstrategia<<0)+'_'+((elementR.siglaResultado.indexOf('.')>=0)?elementR.siglaResultado.split('.')[1]<<0:elementR.siglaResultado<<0)+flags).split('$content').join(statusContent);
				
				var respR = colRow.split('$content').join(col4Content);
				respR = respR.split('$id').join('id'+oeNumber+'_'+(elementE.siglaEstrategia<<0)+'_'+((elementR.siglaResultado.indexOf('.')>=0)?elementR.siglaResultado.split('.')[1]<<0:elementR.siglaResultado<<0)+flags)
				if (elementR.responsavel) respR = respR.split('$respinst').join(elementR.responsavel.orgao.nome).split('$respname').join(elementR.responsavel.nome)
				if (elementR.coResponsavel) {
					respR=respR.split('$corespinst').join(elementR.coResponsavel[0].orgao.nome).split('$corespname').join(elementR.coResponsavel[0].nome);
					if (elementR.coResponsavel.length>1) respR=respR.split('$artinst').join(elementR.coResponsavel[1].orgao.nome).split('$artname').join(elementR.coResponsavel[1].nome);	
				}
				
				
				var fillrow = colRow.split('$id').join('id'+oeNumber+'_'+(elementE.siglaEstrategia<<0)+'_'+((elementR.siglaResultado.indexOf('.')>=0)?elementR.siglaResultado.split('.')[1]<<0:elementR.siglaResultado<<0));

				ncolSum+=row2;
				rcolSum+=respR;
				icolSum+=fillrow;
				mcolSum+=fillrow;
				vcolSum+=fillrow;
				ccolSum+=fillrow;
				
				if (elementR.produtos && elementR.produtos.length) $.each(elementR.produtos, function(indexP, elementP){
				
				
					//console.log(elementR);
					var rcontent = col2Content;

					rcontent = rcontent.split('$code').join((elementE.siglaEstrategia<<0)+'.'+((elementR.siglaResultado.indexOf('.')>=0)?elementR.siglaResultado.split('.')[1]<<0:elementR.siglaResultado<<0)+'.'+(elementP.siglaProduto<<0));
					rcontent = rcontent.split('$info').join(elementP.nome);
					rcontent = rcontent.split('$itemId').join(elementP.codigo);
					rcontent = rcontent.split('$oeId').join('obj'+oeNumber);
					
					if (elementP.prioritario=="S") rcontent="<div class='icoPrioritario' title='Prioritario'></div>"+rcontent;
					if (elementP.atencao=='true') rcontent="<div class='icoAtencao' title='Atenção ao Ministro'></div>"+rcontent;
					var subrow2 = colSubrow;
					subrow2 = subrow2.split('$id').join('id'+oeNumber+'_'+(elementE.siglaEstrategia<<0)+'_'+((elementR.siglaResultado.indexOf('.')>=0)?elementR.siglaResultado.split('.')[1]<<0:elementR.siglaResultado<<0)+'_'+(elementP.siglaProduto<<0));
					subrow2 = subrow2.split('$content').join(rcontent);
					
					var statusContentP = col3Content;//.split('$statusCode').join(elementR.periodoAcompanhamento.statusPeriodoAcompanhamento.codigo);
					
					if (elementP.periodoAcompanhamento) {
						var statusCodeP = elementP.periodoAcompanhamento[0].statusPeriodoAcompanhamento.codigo;
						if (statusCodeP>=10) statusCodeP-=6;
						//statusSum[statusCodeP]++;
						statusContentP  = col3Content.split('$statusCode').join(statusCodeP);
						statusContentP  = statusContentP.split('$statusCaption').join(elementP.periodoAcompanhamento[0].mes+'/'+elementP.periodoAcompanhamento[0].ano);
					}
	
					scolSum+= colSubrow.split('$id').join('id'+oeNumber+'_'+(elementE.siglaEstrategia<<0)+'_'+((elementR.siglaResultado.indexOf('.')>=0)?elementR.siglaResultado.split('.')[1]<<0:elementR.siglaResultado<<0)+'_'+(elementP.siglaProduto<<0)).split('$content').join(statusContentP);
					
					var respP = colSubrow.split('$content').join(col4Content);
					respP = respP.split('$id').join('id'+oeNumber+'_'+(elementE.siglaEstrategia<<0)+'_'+((elementR.siglaResultado.indexOf('.')>=0)?elementR.siglaResultado.split('.')[1]<<0:elementR.siglaResultado<<0)+'_'+(elementP.siglaProduto<<0))
					//respP = respP.split('$respinst').join('RESP/INST Produto').split('$respname').join('RESP/NOME  Produto').split('$corespinst').join('CORESP/INST Produto').split('$corespname').join('CORESP/NOME Produto');
					if (elementP.responsavel) respP = respP.split('$respinst').join(elementP.responsavel.orgao.nome).split('$respname').join(elementP.responsavel.nome)
					if (elementP.coResponsavel){
						 respP=respP.split('$corespinst').join(elementP.coResponsavel[0].orgao.nome).split('$corespname').join(elementP.coResponsavel[0].nome);
						 if (elementP.coResponsavel.length>1) respP=respP.split('$artinst').join(elementP.coResponsavel[1].orgao.nome).split('$artname').join(elementP.coResponsavel[1].nome);
					}
					
					
					var fillrowR = colSubrow.split('$id').join('id'+oeNumber+'_'+(elementE.siglaEstrategia<<0)+'_'+(elementR.siglaResultado<<0)+'_'+(elementP.siglaProduto<<0));
		//			var fillcol = col.split('$content').join(fillrow);
					ncolSum+=subrow2;
					rcolSum+=respP;
					icolSum+=fillrowR;
					mcolSum+=fillrowR;
					vcolSum+=fillrowR;
					ccolSum+=fillrowR;		
				
				});
				
				
			});
			
			var ncol2 = col.split('$content').join(ncolSum);
			var ncol3 = col.split('$content').join(scolSum);
			var ncol4 = col.split('$content').join(rcolSum);
			var ncol5 = col.split('$content').join(icolSum);
			var ncol6 = col.split('$content').join(mcolSum);
			var ncol7 = col.split('$content').join(vcolSum);
			var ncol8 = col.split('$content').join(ccolSum);
	
			
			
			newItem = newItem.split('$item').join(ncol+ncol2+ncol3+ncol4+ncol5+ncol6+ncol7+ncol8);
	
			//console.log(newItem);
			$('#resultsBox').append(newItem);
			
		});
		
    }); 
	
	
	$('.s0 .statusMCount').text(statusSum[0]);
	$('.s1 .statusMCount').text(statusSum[1]);
	$('.s2 .statusMCount').text(statusSum[2]);
	$('.s3 .statusMCount').text(statusSum[3]);
	$('.s4 .statusMCount').text(statusSum[4]);
	$('.s5 .statusMCount').text(statusSum[5]);
	$('#statusMonitor').slideDown(1000, 'easeOutExpo', Resize);	
	
	BreadcrumUpdate();
	$('#navInfo').text(pemsData.results[0].nome);

	$('#label').css('display', 'block').html('<div class="col8">ESTRATÉGIAS</div>\
                <div class="col8">RESULTADO '+anoExercicioSelecionado+'</div>\
                <div class="col8">STATUS</div>\
                <div class="col8"><p>RESPONSÁVEL<br />CORRESPONSÁVEL</p></div>\
                <div class="col8">INDICADORES</div>\
                <div class="col8">META<br /><span><span>2012</span><span>2013</span><span>2014</span><span>2015</span></span></div>\
                <div class="col8">VALOR REALIZADO</div>\
                <div class="col8">CONSOLIDADO ANUAL</div>');
		
	if (searchObj.oe.length!=0 && searchObj.oe.split(',').length==1){
		$('#navTitle').text('Objetivo Estratégico '+searchObj.oe);
		$('#navTitle').addClass('obj'+searchObj.oe+'t').addClass('navTitleOE');
		$('#breadcrum').addClass('breadcrumOE');			
		$('#navInfo').css('display', 'block');
	}else{
		$('#navTitle').text('Filtros Selecionados');
		$('#navTitle').addClass('fsel');
		$('#breadcrum').addClass('breadcrumM');
		$('#navInfo').css('display', 'none');
	}
			
	Show($('#resultsBox'));
	

	
}



function statusData(data){
	pemsData.status = new Object();
	$(data.resultadoStatusContar).each(function(index, element) {
//		console.log(element.codigo);
		var s = 'status'+element.codigo;
		if (element.codigo==10) s = 'status4';
		if (element.codigo==11) s = 'status5';
        pemsData.status[s] = new Object;
		pemsData.status[s].count=element.contar;
		pemsData.status[s].label=element.nome;
    });
	
	$('.statusCount').each(function(){
		$(this).text('0');
	});
	
	statusView();
}

function statusView(){
	console.log(pemsData.status);
	$.each(pemsData.status,function(index,element){
		AnimateVal($('#status .si'+index.split('status')[1]+' .statusCount'), parseInt($('#status .si'+index.split('status')[1]+' .statusCount').text(),10), parseInt(element.count,10));
		//$('#status .si'+index.split('status')[1]+' .statusCount').text(element.count);
		$('#status .si'+index.split('status')[1]+' .statusTitle').text(element.label);
		$('#statusMonitor .s'+index.split('status')[1]+' .statusMTitle').text(element.label);
		
		
	});
	//FilterSplash();
}

function oeinfoData(data){
	pemsData.oe = new Object();
	console.log('OE info Data' );
	$(data.objetivoEstrategico).each(function(index, element) {
		//var sigla  = element.siglaIett.split(' ')[0]+parseInt(element.siglaIett.split(' ')[1], 10);
		var sigla  = element.siglaIett.split(' ')[0]+(element.siglaIett.split(' ')[1]<<0);
        pemsData.oe[sigla] = new Object;
		pemsData.oe[sigla].codigo=element.codigo;
		pemsData.oe[sigla].info=element.nome;
    });
	console.log(' ---- ');
}

function taginfoData(data){
	pemsData.tagCloud = new Object();
//	pemsData.searchTags = new Object();
	var tags = new Array();
	//.replace(/\s/g,'');
	$.each(data.etiqueta,function(index,element){
		tags.push(trim(element.nome));
		pemsData.tagCloud[element.nome.replace(/\s/g,'')] = new Object();
		pemsData.tagCloud[element.nome.replace(/\s/g,'')].active = false;
		pemsData.tagCloud[element.nome.replace(/\s/g,'')].name = trim(element.nome);
		//$('#tagCloudInput').append('<option value='+element.nome+'>'+element.nome+'</option>');
	});
	tagInfoView();
//	pemsData = tags;
	$("#searchFieldInput").autocompleteArray(tags, {matchContains:1});
//	$('#tagCloudInput option').attr("selected","selected");  ;
	//$('#tagCloudInput').val(list);
}

function tagInfoScan(){

	$('#searchFieldInput').val($('#searchFieldInput').val().split(', ,').join(',').split(',,').join(',').split('  ').join(' '));
	if ($('#searchFieldInput').val().indexOf(',')==0) $('#searchFieldInput').val($('#searchFieldInput').val().substring(1, $('#searchFieldInput').val().length).trim());
	if ($('#searchFieldInput').val()==(','||', '))$('#searchFieldInput').val('');
	
	
	 tagInfoReset();		
	var data = $('#searchFieldInput').val();
		var tags = [data];
		if (data.indexOf(',')>=0) tags = data.split(',');
		$.each(tags,function(i,e){
			if (pemsData.tagCloud[e.replace(/\s/g,'').toUpperCase()]) pemsData.tagCloud[e.replace(/\s/g,'').toUpperCase()].active=true;
		});
	tagInfoView();
}

function tagInfoReset(){
  $.each(pemsData.tagCloud, function(i,e){
	  e.active=false;
  })
}
function tagInfoView(){
		
	$('#tags').html('');
	$.each(pemsData.tagCloud, function(i,e){
		var item = '<div class="tagItem';
		if (e.active) {
			item+=' sel';
			//$('#searchFieldInput').val($('#searchFieldInput').val()+', '+e.name);
		}
		$('#tags').append(item+'">'+e.name+'</div>');	
	});
}

function filterinfoData(data){
	pemsData.filterInfo = new Array();
	console.log('filter info Data' );
	$(data.etiqueta).each(function(index, element) {
		if (element.categoria.nome.length>0) {
			if (!pemsData.filterInfo[element.categoria.codigo<<0]) pemsData.filterInfo[element.categoria.codigo<<0] = {'nome':element.categoria.nome, subitem:[]};
			pemsData.filterInfo[element.categoria.codigo<<0].subitem.push(element.nome);
		}
		
    });
	console.log(pemsData.filterInfo);
	filterinfoView();
}

function filterinfoView(){
	var filters=0;
	$.each(pemsData.filterInfo, function(index,element){
		if (element){
			$.each(element.subitem, function (i,si){
				if (index==10) index=0;
				$('.filtro'+index+' .filtroSubBot').append('<p class="filtroSubItem" id="f'+filters+'">'+si+'</p>');
				filters++;
			})		
		}
	});
	pemsData.filterInfo.filters = filters;
}
function animateStaticTop(e){
	// 'catTopPosition' is the amount of pixels #cat
	// is from the top of the document
	var TopPosition = $('#topoStatic').offset().top;
	$('#resultsBox').animate({scrollTop:TopPosition}, 'slow');
		
}
function goStaticTop(e){
	// 'catTopPosition' is the amount of pixels #cat
	// is from the top of the document
	var TopPosition = $('#topoStatic').offset().top;
	$('#resultsBox').animate({scrollTop:TopPosition}, 'fast');
		
}
function resultadoData(data){
	console.log('Resultado');
	console.log(data);
	pemsData.resultado = data;
	resultadoView();
	LocalStorage('save');
}
function resultadoView(){
	$('#resultsBox').html('');	
	$('#rItemBox1, #pItemBox1').remove();
	console.log(pemsData.resultado);
	//<strong>Data Limite:</strong> 11/03/2012<br /><strong>Atualização:</strong> 10/03/2012<br /><br />Construção de UBS: 245 novas propostas contempladas e pré-cadastro foi aberto para seleção de mais unidades. Destaca-se cadastro de 80 propostas para construção de UBS fluviais, das quais 15 unidades já foram concluídas em 2011. 
	//<div>1.1.1 Identificado vazios assistenciais e insuficiências da Assistência Básica e Assistência Especializada e definidas prioridades de intervenção e financiamentos.</div><div>ABR MAI JUN JUL AGO</div><div>ABR</div><div>SAS/DAB Heider<div class=popupArt po><span class=popupArtTitle>ARTICULADOR</span><br /><span class=popupArtInfo>$artinst<br />$artname</span></div></div>
	var navDisplay = 	"<div id='rItemBox1'>\
							<div id='rItemBox1m1'>$icoDiv$nome</div>\
							<div id='rItemBox1m2'><p>STATUS</p><p>CICLO</p><div class='rItemBoxCicloE po'></div><div class='rItemBoxCicloD po'></div></div>\
							<div id='rItemBox1m3'><p>RESPONSÁVEL</p><p>CORRESPONSÁVEL</p></div>\
							<div id='rItemBox1m4'>Articulação</div>\
							<div id='rItemBox1m5'><img src='image/status$statusCode.gif' width='46' height='46' /><br/><span class='statusCaption'>$statusCaption</span></div>\
							<div id='rItemBox1m6'><p>$respinst - $respnome</p><p>$corespinst - $corespnome</p></div>\
							<div id='rItemBox1m7'>$articula</div>\
						</div>";
	var newItem = 	"<div id='rItem' class='obj"+pemsData.selOE+"'>\
						<div id='rItemBox2'>\
							<div id='rItemBox2m1' class='itemTitle'><div>PARECER - SITUAÇÃO ATUAL</div></div>\
							<div id='rItemBox2m2'>$parecerant<div class='popupExpand po'></div></div>\
						</div>\
						<div id='rItemBox3'>\
							<div id='rItemBox3m1' class='itemTitle'><div>INDICADORES</div><div>2011</div><div>2012</div><div>2013</div><div>2014</div></div>\
							<div id='rItemBox3m2'>$indContent</div>\
						</div>\
						<div id='rItemBox4'>\
							<div id='rItemBox4m1' class='itemTitle'><div>PRODUTOS / MARCOS INTERMEDIÁRIOS</div><div>CRONOGRAMA</div><div>STATUS</div><div><p>RESPONSÁVEL</p><p>CORRESPONSAVEL</p></div></div>\
							<div id='rItemBox4m2' class='obj1'>$cronoItems</div>\
						</div>\
						<div id='rItemBox5'>\
							<div id='rItemBox5m1' class='itemTitle'><div>PLANO DE CONTINGÊNCIA - ENCAMINHAMENTOS<span id='encaminhaedit' class='po '></span><span id='encaminhaup' class='po hidden animate'></span><span id='encaminhax' class='po hidden animate'></span></div><div>DATA<br/>INSERÇÃO</div><div>PRAZO</div><div>RESPONSÁVEL</div></div>\
							<div id='rItemBox5m2'>$encContent</div>\
						</div>\
					</div>";
	if (pemsData.resultado){
		navDisplay = navDisplay.split('$nome').join(pemsData.resultado.nome);

		var iclass = '';
		if (pemsData.resultado.prioritario=="S") iclass+="<div class='icoPrioritario' title='Prioritário'></div>";
		if (pemsData.resultado.atencao=='true') iclass+="<div class='icoAtencao po' title='Atenção ao Ministro'></div>";
		navDisplay = navDisplay.split('$icoDiv').join(iclass);
		
		navDisplay = navDisplay.split('$respinst').join(pemsData.resultado.responsavel.orgao.nome);
		navDisplay = navDisplay.split('$respnome').join(pemsData.resultado.responsavel.nome);
		if (pemsData.resultado.coResponsavel){
			navDisplay = navDisplay.split('$corespinst').join(pemsData.resultado.coResponsavel[0].orgao.nome);
			navDisplay = navDisplay.split('$corespnome').join(pemsData.resultado.coResponsavel[0].nome);
		}
		if (pemsData.resultado.articulacao)	{
			var art='';
			$.each(pemsData.resultado.articulacao, function(i,e){
				art+=e.nome+', ';
			});
			art = art.slice(0, -2);
			navDisplay = navDisplay.split('$articula').join(art);
		}
		
		if (isNaN(pemsData.resultado.ciclo)) pemsData.resultado.ciclo = 0;
		navDisplay = navDisplay.split('$statusCode').join((pemsData.resultado.periodoAcompanhamento[pemsData.resultado.ciclo].statusPeriodoAcompanhamento.codigo>=10)?pemsData.resultado.periodoAcompanhamento[pemsData.resultado.ciclo].statusPeriodoAcompanhamento.codigo-6:pemsData.resultado.periodoAcompanhamento[pemsData.resultado.ciclo].statusPeriodoAcompanhamento.codigo);
		navDisplay = navDisplay.split('$statusCaption').join(pemsData.resultado.periodoAcompanhamento[pemsData.resultado.ciclo].mes+'/'+pemsData.resultado.periodoAcompanhamento[pemsData.resultado.ciclo].ano);
		if (pemsData.resultado.periodoAcompanhamento[pemsData.resultado.ciclo].parecer) {
			newItem = newItem.split('$parecerant').join(pemsData.resultado.periodoAcompanhamento[pemsData.resultado.ciclo].parecer)
			/*.replace(/(\r\n|[\r\n])/g, "<br />")/*.replace(/(<([^>]+)>)/ig,""));*/
		}	
		console.log('PERIODO ACOMPANHAMENTO');
		console.log(pemsData.resultado.periodoAcompanhamento[pemsData.resultado.ciclo].parecer);
		$('#navTitle').text('RESULTADO '+pemsData.resNav/*pemsData.resultado.codigo*/);
	}else{
		$('#navTitle').text('ERRO - ITEM INEXISTENTE');
		return;
	}

	
	var row= '<div class="rItemBox4P row $id">$content</div>';
	var subRow= '<div class="rItemBox4P subrow $id">$content</div>';
	var colRow= '<div>$content</div>';
	var colRowBold= '<div class="bold">$content</div>';
	var colSubrow='<div>$content</div>';
	
	var col1Content='<span class="objId">$code</span><span class="objResinfo $oeId" id="param$itemId">$info</span>';
	var col2Title="<div class='croMes'>$month</div>";
	var col2Grid="<div class='croMesData'>$fill</div>";
	var col2Fill="<span class='croMesFill'></span>";
	var col3Content='<img src="image/status$statusCode.gif" width="46" height="46" /><span class="statusCaption">$statusCaption</span>';
	var col4Content='<p>$respinst<br />$respname</p><p>$corespinst<br />$corespname</p><div class="popupArt po"><span class="popupArtTitle">ARTICULADOR</span><br /><span class="popupArtInfo">$artinst<br />$artname</span></div>';
	var encaminhaContent="<div class='enc'><div>$info</div><div>$date</div><div>$deadline</div><div>$resp</div></div>"
	var encaminhaContentNone="<div class='enc'><div class='encNone'>Nenhum Encaminhamento</div></div>"
	var cronoItemSum = '';

	var month = ['JAN', 'FEV', 'MAR', 'ABR', 'MAI', 'JUN', 'JUL', 'AGO', 'SET', 'OUT', 'NOV', 'DEZ'];
	var statusSum = [0,0,0,0,0,0];
	
	if (pemsData.resultado.produtos && pemsData.resultado.produtos.length) $.each(pemsData.resultado.produtos, function(indexP, elementP){	
//		console.log(elementP);

		var productId = 'id_1_1_'+(elementP.siglaProduto<<0);
		
		var ocontent = col1Content;
		ocontent = ocontent.split('$code').join((elementP.siglaProduto<<0))
		ocontent = ocontent.split('$info').join(elementP.nome);
		ocontent = ocontent.split('$itemId').join(elementP.codigo);
		ocontent = ocontent.split('$oeId').join('obj'+pemsData.selOE);
		if (elementP.acoes && elementP.acoes.length>=1) ocontent="<span class='expandToggle'></span>"+ocontent;
		ocontent =  colRowBold.split('$content').join(ocontent);
		
		var startDate=0, endDate=1;
		
		if (elementP.dataInicio) startDate = (elementP.dataInicio.split('-')[1]<<0);
		if (elementP.dataFim) endDate = (elementP.dataFim.split('-')[1]<<0);
		
		var ccontent ='';
		for (var i=0; i<12; i++){
			ccontent+=col2Title.split('$month').join(month[i]);
		}
		for (var i=0; i<12; i++){
			var g = col2Grid;
			if (i+1>=startDate && i+1<=endDate)	g=g.split('$fill').join(col2Fill);
			else g=g.split('$fill').join('');
			ccontent+=g;
		}
		ccontent = colRow.split('$content').join(ccontent);
		
		var scontent = colRow.split('$content').join(col3Content);//.split('$statusCode').join(elementR.periodoAcompanhamento.statusPeriodoAcompanhamento.codigo);
		
		if (elementP.periodoAcompanhamento[0]) {
			var statusCodeP = elementP.periodoAcompanhamento[0].statusPeriodoAcompanhamento.codigo;
			if (statusCodeP>=10) statusCodeP-=6;
			statusSum[statusCodeP]++;
			scontent = scontent.split('$statusCode').join(statusCodeP);
			scontent = scontent.split('$statusCaption').join(elementP.periodoAcompanhamento[0].mes+'/'+elementP.periodoAcompanhamento[0].ano);
		}
				
		var rcontent = colRow.split('$content').join(col4Content);

		if (elementP.responsavel) rcontent = rcontent.split('$respinst').join(elementP.responsavel.orgao.nome).split('$respname').join(elementP.responsavel.nome)
		if (elementP.coResponsavel){
			 rcontent=rcontent.split('$corespinst').join(elementP.coResponsavel[0].orgao.nome).split('$corespname').join(elementP.coResponsavel[0].nome);
			 if (elementP.coResponsavel.length>1) rcontent=rcontent.split('$artinst').join(elementP.coResponsavel[1].orgao.nome).split('$artname').join(elementP.coResponsavel[1].nome);
		}
		
		cronoItemSum += row.split('$id').join(productId)
		cronoItemSum = cronoItemSum.split('$content').join(ocontent+ccontent+scontent+rcontent);
		//cronoItemSum += ocontent+ccontent+scontent+rcontent;

		if (elementP.acoes && elementP.acoes.length) $.each(elementP.acoes, function(indexA, elementA){
			
			productIdA = productId+'_'+(elementA.siglaAcao<<0);
		
			ocontent = colSubrow.split('$content').join(col1Content);
			ocontent = ocontent.split('$code').join((elementP.siglaProduto<<0)+'.'+(elementA.siglaAcao<<0))
			ocontent = ocontent.split('$info').join(elementA.nome);
			ocontent = ocontent.split('$itemId').join(elementA.codigo);
			ocontent = ocontent.split('$oeId').join('obj'+pemsData.selOE);
			startDate=0;
			endDate=1;
			if (elementA.dataInicio) startDate = (elementA.dataInicio.split('-')[1]<<0);
			if (elementA.dataFim) endDate = (elementA.dataFim.split('-')[1]<<0);
			
			ccontent ='';
			for (var i=0; i<12; i++){
				ccontent+=col2Title.split('$month').join(month[i]);
			}
			for (var i=0; i<12; i++){
				var g = col2Grid;
				if (i+1>=startDate && i+1<=endDate)	g=g.split('$fill').join(col2Fill);
				else g=g.split('$fill').join('');
				ccontent+=g;
			}
			ccontent = colSubrow.split('$content').join(ccontent);

			
			scontent = colSubrow.split('$content').join(col3Content);

			
			if (elementA.periodoAcompanhamento) {
				var statusCodeA = elementA.periodoAcompanhamento[0].statusPeriodoAcompanhamento.codigo;
				if (statusCodeA>=10) statusCodeA-=6;
				//statusSum[statusCodeA]++;
				scontent = scontent.split('$statusCode').join(statusCodeA);
				scontent = scontent.split('$statusCaption').join(elementA.periodoAcompanhamento[0].mes+'/'+elementA.periodoAcompanhamento[0].ano);
			}
					
			var rcontent = colSubrow.split('$content').join(col4Content);
			if (elementA.responsavel) rcontent = rcontent.split('$respinst').join(elementA.responsavel.orgao.nome).split('$respname').join(elementA.responsavel.nome)
			if (elementA.coResponsavel){
				 rcontent=rcontent.split('$corespinst').join(elementA.coResponsavel[0].orgao.nome).split('$corespname').join(elementA.coResponsavel[0].nome);
				 if (elementA.coResponsavel.length>1) rcontent=rcontent.split('$artinst').join(elementA.coResponsavel[1].orgao.nome).split('$artname').join(elementA.coResponsavel[1].nome);
			}
			
			cronoItemSum += subRow.split('$id').join(productIdA).split('$content').join(ocontent+ccontent+scontent+rcontent);
			//cronoItemSum += ocontent+ccontent+scontent+rcontent;
		});
});
		var comments='';
		if (pemsData.resultado.comentarios && pemsData.resultado.comentarios.length) $.each(pemsData.resultado.comentarios, function(indexC, elementC){
			console.log(elementC);
			var newC = encaminhaContent;
			if (elementC.texto)	newC = newC.split('$info').join(elementC.texto.replace(/(\r\n|[\r\n])/g, "<br />")); 
			if (elementC.responsavel && elementC.responsavel.nome) {
				var sum = '<p>'+elementC.responsavel.nome;
				if (elementC.responsavel.orgao && elementC.responsavel.orgao.nome) sum+='<br/>'+elementC.responsavel.orgao.nome;
				newC = newC.split('$resp').join(sum+'</p>'); 
			}
			
			if (elementC.prazo){
				var date = elementC.prazo.split('T')[0];
				date = date.split('-');
				newC = newC.split('$deadline').join(date[2]+'/'+date[1]+'/'+date[0]);
			}
			
			if (elementC.dataInclusao){
				var date = elementC.dataInclusao.split('T')[0];
				date = date.split('-');
				newC = newC.split('$date').join(date[2]+'/'+date[1]+'/'+date[0]);
			}
			comments +=newC;
		}); else{
			comments = encaminhaContentNone;
		}
		newItem = newItem.split('$encContent').join(comments);

	
	
	var indRow= "<div>\
					<div class='indRow'>\
						<div class='indDesc'>$nomeIndicador</div>\
						$indResul\
					</div>\
					<div class='indJustificativa'>Justificativa</div>\
				</div> ";
	var indResul = "<div class='indResultado'>\
						<div class='pr'>Meta/Realizado<br><div>M<br>R</div><div>$previsto<br>$realizado</div></div>\
						<div class='atingimento'><img src='image/$cor'><br>$atingimento%</div>\
					</div>"
	var indContent='';
	if (pemsData.resultado.periodoAcompanhamento[pemsData.resultado.ciclo].indMonitoramento && pemsData.resultado.periodoAcompanhamento[pemsData.resultado.ciclo].indMonitoramento.length) $.each(pemsData.resultado.periodoAcompanhamento[pemsData.resultado.ciclo].indMonitoramento, function(indexI, elementI){	
		var temp='';
		if (elementI.valores && elementI.valores.length) $.each(elementI.valores, function(indexV, elementV){
			//TODO: adicionar um campo na tabela tb_cor para armazenar o caminho da imagem no site do PPE que é diferente do eCar
			switch(elementV.cor)
			{
				case 'sVerde9.png':
				  elementV.cor = 'status1.gif';
				  break;
				case 'sAmarelo9.png':
				  elementV.cor = 'status2.gif';
				  break;
				case 'sVermelho9.png':
				  elementV.cor = 'status3.gif';
				  break;
				case 'sBranco9.png':
				  elementV.cor = 'status0.gif';
				  break;  
				case 'nar.gif':
				  elementV.cor = 'status0.gif';
				  break;
			}
			temp += indResul.split('$previsto').join(elementV.previsto).split('$realizado').join(elementV.realizado).split('$cor').join(elementV.cor).split('$atingimento').join(elementV.atingimento);
		});
		indContent+=indRow.split('$nomeIndicador').join(elementI.nomeIndicador).split('$indResul').join(temp);
	});
	newItem = newItem.split('$indContent').join(indContent);
		
	newItem = newItem.split('$cronoItems').join(cronoItemSum);
	
	$('#nav').append(navDisplay);
	$('#resultsBox').html(newItem);
	console.log(pemsData.resultado);
	
	Show($('#resultsBox'));
	$('#resultsBox').scrollTop(0);
	
	$('.s0 .statusMCount').text(statusSum[0]);
	$('.s1 .statusMCount').text(statusSum[1]);
	$('.s2 .statusMCount').text(statusSum[2]);
	$('.s3 .statusMCount').text(statusSum[3]);
	$('.s4 .statusMCount').text(statusSum[4]);
	$('.s5 .statusMCount').text(statusSum[5]);
	$('#statusMonitor').slideDown(1000, 'easeOutExpo', Resize);	
	
	Resize(null);
}

/*function resultadoView(){
	$('#resultsBox').html('');	
	$('#rItemBox1, #pItemBox1').remove();
	console.log(pemsData.resultado);
	//<strong>Data Limite:</strong> 11/03/2012<br /><strong>Atualização:</strong> 10/03/2012<br /><br />Construção de UBS: 245 novas propostas contempladas e pré-cadastro foi aberto para seleção de mais unidades. Destaca-se cadastro de 80 propostas para construção de UBS fluviais, das quais 15 unidades já foram concluídas em 2011. 
	//<div>1.1.1 Identificado vazios assistenciais e insuficiências da Assistência Básica e Assistência Especializada e definidas prioridades de intervenção e financiamentos.</div><div>ABR MAI JUN JUL AGO</div><div>ABR</div><div>SAS/DAB Heider<div class=popupArt po><span class=popupArtTitle>ARTICULADOR</span><br /><span class=popupArtInfo>$artinst<br />$artname</span></div></div>
	var navDisplay = 	"<div id='rItemBox1'>\
							<div id='rItemBox1m1'>$icoDiv$nome</div>\
							<div id='rItemBox1m2'><p>STATUS</p><p>CICLO</p><div class='rItemBoxCicloE po'></div><div class='rItemBoxCicloD po'></div></div>\
							<div id='rItemBox1m3'><p>RESPONSÁVEL</p><p>CORRESPONSÁVEL</p></div>\
							<div id='rItemBox1m4'>Articulação</div>\
							<div id='rItemBox1m5'><img src='image/status$statusCode.gif' width='46' height='46' /><br/><span class='statusCaption'>$statusCaption</span></div>\
							<div id='rItemBox1m6'><p>$respinst - $respnome</p><p>$corespinst - $corespnome</p></div>\
							<div id='rItemBox1m7'>$articula</div>\
						</div>";
	var newItem = 	"<div id='rItem' class='obj"+pemsData.selOE+"'>\
						<div id='rItemBox2'>\
							<div id='rItemBox2m1' class='itemTitle'><div>PARECER - SITUAÇÃO ATUAL</div></div>\
							<div id='rItemBox2m2'>$parecerant<div class='popupExpand po'></div></div>\
						</div>\
						<div id='rItemBox3'>\
							<div id='rItemBox3m1' class='itemTitle'><div>INDICADORES</div><div>META 2012</div><div>VALOR REALIZADO</div><div>CONSOLIDADO ANUAL</div></div>\
							<div id='rItemBox3m2'><div>$ind</div><div>$meta</div><div>$valor</div><div>$cons</div></div>\
						</div>\
						<div id='rItemBox4'>\
							<div id='rItemBox4m1' class='itemTitle'><div>PRODUTOS / MARCOS INTERMEDIÁRIOS</div><div>CRONOGRAMA</div><div>STATUS</div><div><p>RESPONSÁVEL</p><p>CORRESPONSAVEL</p></div></div>\
							<div id='rItemBox4m2' class='obj1'>$cronoItems</div>\
						</div>\
						<div id='rItemBox5'>\
							<div id='rItemBox5m1' class='itemTitle'><div>PLANO DE CONTINGÊNCIA - ENCAMINHAMENTOS<span id='encaminhaedit' class='po '></span><span id='encaminhaup' class='po hidden animate'></span><span id='encaminhax' class='po hidden animate'></span></div><div>DATA<br/>INSERÇÃO</div><div>PRAZO</div><div>RESPONSÁVEL</div></div>\
							<div id='rItemBox5m2'>$encContent</div>\
						</div>\
					</div>";
	if (pemsData.resultado){
		navDisplay = navDisplay.split('$nome').join(pemsData.resultado.nome);

		var iclass = '';
		if (pemsData.resultado.prioritario=="S") iclass+="<div class='icoPrioritario' title='Prioritário'></div>";
		if (pemsData.resultado.atencao=='true') iclass+="<div class='icoAtencao po' title='Atenção ao Ministro'></div>";
		navDisplay = navDisplay.split('$icoDiv').join(iclass);
		
		navDisplay = navDisplay.split('$respinst').join(pemsData.resultado.responsavel.orgao.nome);
		navDisplay = navDisplay.split('$respnome').join(pemsData.resultado.responsavel.nome);
		if (pemsData.resultado.coResponsavel){
			navDisplay = navDisplay.split('$corespinst').join(pemsData.resultado.coResponsavel[0].orgao.nome);
			navDisplay = navDisplay.split('$corespnome').join(pemsData.resultado.coResponsavel[0].nome);
		}
		if (pemsData.resultado.articulacao)	{
			var art='';
			$.each(pemsData.resultado.articulacao, function(i,e){
				art+=e.nome+', ';
			});
			art = art.slice(0, -2);
			navDisplay = navDisplay.split('$articula').join(art);
		}
		
		if (isNaN(pemsData.resultado.ciclo)) pemsData.resultado.ciclo = 0;
		navDisplay = navDisplay.split('$statusCode').join((pemsData.resultado.periodoAcompanhamento[pemsData.resultado.ciclo].statusPeriodoAcompanhamento.codigo>=10)?pemsData.resultado.periodoAcompanhamento[pemsData.resultado.ciclo].statusPeriodoAcompanhamento.codigo-6:pemsData.resultado.periodoAcompanhamento[pemsData.resultado.ciclo].statusPeriodoAcompanhamento.codigo);
		navDisplay = navDisplay.split('$statusCaption').join(pemsData.resultado.periodoAcompanhamento[pemsData.resultado.ciclo].mes+'/'+pemsData.resultado.periodoAcompanhamento[pemsData.resultado.ciclo].ano);
		if (pemsData.resultado.periodoAcompanhamento[pemsData.resultado.ciclo].parecer) newItem = newItem.split('$parecerant').join(pemsData.resultado.periodoAcompanhamento[pemsData.resultado.ciclo].parecer.replace(/(\r\n|[\r\n])/g, "<br />")/*.replace(/(<([^>]+)>)/ig,""));
		$('#navTitle').text('RESULTADO '+pemsData.resNav/*pemsData.resultado.codigo);
	}else{
		$('#navTitle').text('ERRO - ITEM INEXISTENTE');
		return;
	}

	
	var row= '<div class="rItemBox4P row $id">$content</div>';
	var subRow= '<div class="rItemBox4P subrow $id">$content</div>';
	var colRow= '<div>$content</div>';
	var colRowBold= '<div class="bold">$content</div>';
	var colSubrow='<div>$content</div>';
	
	var col1Content='<span class="objId">$code</span><span class="objResinfo $oeId" id="param$itemId">$info</span>';
	var col2Title="<div class='croMes'>$month</div>";
	var col2Grid="<div class='croMesData'>$fill</div>";
	var col2Fill="<span class='croMesFill'></span>";
	var col3Content='<img src="image/status$statusCode.gif" width="46" height="46" /><span class="statusCaption">$statusCaption</span>';
	var col4Content='<p>$respinst<br />$respname</p><p>$corespinst<br />$corespname</p><div class="popupArt po"><span class="popupArtTitle">ARTICULADOR</span><br /><span class="popupArtInfo">$artinst<br />$artname</span></div>';
	var encaminhaContent="<div class='enc'><div>$info</div><div>$date</div><div>$deadline</div><div>$resp</div></div>"
	var encaminhaContentNone="<div class='enc'><div class='encNone'>Nenhum Encaminhamento</div></div>"
	var cronoItemSum = '';

	var month = ['JAN', 'FEV', 'MAR', 'ABR', 'MAI', 'JUN', 'JUL', 'AGO', 'SET', 'OUT', 'NOV', 'DEZ'];
	var statusSum = [0,0,0,0,0,0];
	
	if (pemsData.resultado.produtos && pemsData.resultado.produtos.length) $.each(pemsData.resultado.produtos, function(indexP, elementP){	
//		console.log(elementP);

		var productId = 'id_1_1_'+(elementP.siglaProduto<<0);
		
		var ocontent = col1Content;
		ocontent = ocontent.split('$code').join((elementP.siglaProduto<<0))
		ocontent = ocontent.split('$info').join(elementP.nome);
		ocontent = ocontent.split('$itemId').join(elementP.codigo);
		ocontent = ocontent.split('$oeId').join('obj'+pemsData.selOE);
		if (elementP.acoes && elementP.acoes.length>=1) ocontent="<span class='expandToggle'></span>"+ocontent;
		ocontent =  colRowBold.split('$content').join(ocontent);
		
		var startDate=0, endDate=1;
		
		if (elementP.dataInicio) startDate = (elementP.dataInicio.split('-')[1]<<0);
		if (elementP.dataFim) endDate = (elementP.dataFim.split('-')[1]<<0);
		
		var ccontent ='';
		for (var i=0; i<12; i++){
			ccontent+=col2Title.split('$month').join(month[i]);
		}
		for (var i=0; i<12; i++){
			var g = col2Grid;
			if (i+1>=startDate && i+1<=endDate)	g=g.split('$fill').join(col2Fill);
			else g=g.split('$fill').join('');
			ccontent+=g;
		}
		ccontent = colRow.split('$content').join(ccontent);
		
		var scontent = colRow.split('$content').join(col3Content);//.split('$statusCode').join(elementR.periodoAcompanhamento.statusPeriodoAcompanhamento.codigo);
		
		if (elementP.periodoAcompanhamento[0]) {
			var statusCodeP = elementP.periodoAcompanhamento[0].statusPeriodoAcompanhamento.codigo;
			if (statusCodeP>=10) statusCodeP-=6;
			statusSum[statusCodeP]++;
			scontent = scontent.split('$statusCode').join(statusCodeP);
			scontent = scontent.split('$statusCaption').join(elementP.periodoAcompanhamento[0].mes+'/'+elementP.periodoAcompanhamento[0].ano);
		}
				
		var rcontent = colRow.split('$content').join(col4Content);

		if (elementP.responsavel) rcontent = rcontent.split('$respinst').join(elementP.responsavel.orgao.nome).split('$respname').join(elementP.responsavel.nome)
		if (elementP.coResponsavel){
			 rcontent=rcontent.split('$corespinst').join(elementP.coResponsavel[0].orgao.nome).split('$corespname').join(elementP.coResponsavel[0].nome);
			 if (elementP.coResponsavel.length>1) rcontent=rcontent.split('$artinst').join(elementP.coResponsavel[1].orgao.nome).split('$artname').join(elementP.coResponsavel[1].nome);
		}
		
		cronoItemSum += row.split('$id').join(productId)
		cronoItemSum = cronoItemSum.split('$content').join(ocontent+ccontent+scontent+rcontent);
		//cronoItemSum += ocontent+ccontent+scontent+rcontent;

		if (elementP.acoes && elementP.acoes.length) $.each(elementP.acoes, function(indexA, elementA){
			
			productIdA = productId+'_'+(elementA.siglaAcao<<0);
		
			ocontent = colSubrow.split('$content').join(col1Content);
			ocontent = ocontent.split('$code').join((elementP.siglaProduto<<0)+'.'+(elementA.siglaAcao<<0))
			ocontent = ocontent.split('$info').join(elementA.nome);
			ocontent = ocontent.split('$itemId').join(elementA.codigo);
			ocontent = ocontent.split('$oeId').join('obj'+pemsData.selOE);
			startDate=0;
			endDate=1;
			if (elementA.dataInicio) startDate = (elementA.dataInicio.split('-')[1]<<0);
			if (elementA.dataFim) endDate = (elementA.dataFim.split('-')[1]<<0);
			
			ccontent ='';
			for (var i=0; i<12; i++){
				ccontent+=col2Title.split('$month').join(month[i]);
			}
			for (var i=0; i<12; i++){
				var g = col2Grid;
				if (i+1>=startDate && i+1<=endDate)	g=g.split('$fill').join(col2Fill);
				else g=g.split('$fill').join('');
				ccontent+=g;
			}
			ccontent = colSubrow.split('$content').join(ccontent);

			
			scontent = colSubrow.split('$content').join(col3Content);

			
			if (elementA.periodoAcompanhamento) {
				var statusCodeA = elementA.periodoAcompanhamento[0].statusPeriodoAcompanhamento.codigo;
				if (statusCodeA>=10) statusCodeA-=6;
				//statusSum[statusCodeA]++;
				scontent = scontent.split('$statusCode').join(statusCodeA);
				scontent = scontent.split('$statusCaption').join(elementA.periodoAcompanhamento[0].mes+'/'+elementA.periodoAcompanhamento[0].ano);
			}
					
			var rcontent = colSubrow.split('$content').join(col4Content);
			if (elementA.responsavel) rcontent = rcontent.split('$respinst').join(elementA.responsavel.orgao.nome).split('$respname').join(elementA.responsavel.nome)
			if (elementA.coResponsavel){
				 rcontent=rcontent.split('$corespinst').join(elementA.coResponsavel[0].orgao.nome).split('$corespname').join(elementA.coResponsavel[0].nome);
				 if (elementA.coResponsavel.length>1) rcontent=rcontent.split('$artinst').join(elementA.coResponsavel[1].orgao.nome).split('$artname').join(elementA.coResponsavel[1].nome);
			}
			
			cronoItemSum += subRow.split('$id').join(productIdA).split('$content').join(ocontent+ccontent+scontent+rcontent);
			//cronoItemSum += ocontent+ccontent+scontent+rcontent;
		});
});
		var comments='';
		if (pemsData.resultado.comentarios && pemsData.resultado.comentarios.length) $.each(pemsData.resultado.comentarios, function(indexC, elementC){
			console.log(elementC);
			var newC = encaminhaContent;
			if (elementC.texto)	newC = newC.split('$info').join(elementC.texto.replace(/(\r\n|[\r\n])/g, "<br />")); 
			if (elementC.responsavel && elementC.responsavel.nome) {
				var sum = '<p>'+elementC.responsavel.nome;
				if (elementC.responsavel.orgao && elementC.responsavel.orgao.nome) sum+='<br/>'+elementC.responsavel.orgao.nome;
				newC = newC.split('$resp').join(sum+'</p>'); 
			}
			
			if (elementC.prazo){
				var date = elementC.prazo.split('T')[0];
				date = date.split('-');
				newC = newC.split('$deadline').join(date[2]+'/'+date[1]+'/'+date[0]);
			}
			
			if (elementC.dataInclusao){
				var date = elementC.dataInclusao.split('T')[0];
				date = date.split('-');
				newC = newC.split('$date').join(date[2]+'/'+date[1]+'/'+date[0]);
			}
			comments +=newC;
		}); else{
			comments = encaminhaContentNone;
		}
		newItem = newItem.split('$encContent').join(comments);

	});
	
	
	var i='',m='',v='',c='';
	if (pemsData.resultado.indicador && pemsData.resultado.indicador.length) $.each(pemsData.resultado.indicador, function(indexI, elementI){	
		//elementI.nome = 'Class is impossible, says Lyotard. Therefore, Foucault uses the term ‘Lyotardist narrative’ to denote the bridge between sexuality and class.The primary theme of the works of Stone is not dedeconstructivism, but subdedeconstructivism. Porter[1] implies that we have to choose between the dialectic paradigm of expression and Baudrillardist hyperreality. In a sense, Marx suggests the use of Lyotardist narrative to attack capitalism. In the works of Stone, a predominant concept is the distinction between opening and closing. If subpatriarchial narrative holds, we have to choose between Lyotardist narrative and capitalist desituationism. Thus, many theories concerning postdialectic libertarianism exist.';
		//elementI.meta = elementI.valorRealizado2012 = elementI.cosolidadoAnual = '999.999,00';
		i+='<div class="indicadorItem" title="'+elementI.nome+'"><div class="indicadorDescr">'+elementI.nome+'</div><div class="popupIndicador po">'+elementI.nome+'</div></div>';
		m+='<div class="indicadorItem">'+elementI.meta+'</div>';
		v+='<div class="indicadorItem"><div class="indicadorItemMes">JUN</div><div class="rItemBoxCicloE po"></div><div class="rItemBoxCicloD po"></div><div class="indicadorItemVal">'+elementI.valorRealizado2012+'</div></div>';
		c+='<div class="indicadorItem">'+elementI.cosolidadoAnual+'</div>';

	});
	newItem = newItem.split('$ind').join(i).split('$meta').join(m).split('$valor').join(v).split('$cons').join(c);
		
	newItem = newItem.split('$cronoItems').join(cronoItemSum);
	
	$('#nav').append(navDisplay);
	$('#resultsBox').html(newItem);
	console.log(pemsData.resultado);
	
	Show($('#resultsBox'));
	$('#resultsBox').scrollTop(0);
	
	$('.s0 .statusMCount').text(statusSum[0]);
	$('.s1 .statusMCount').text(statusSum[1]);
	$('.s2 .statusMCount').text(statusSum[2]);
	$('.s3 .statusMCount').text(statusSum[3]);
	$('.s4 .statusMCount').text(statusSum[4]);
	$('.s5 .statusMCount').text(statusSum[5]);
	$('#statusMonitor').slideDown(1000, 'easeOutExpo', Resize);	
	
	Resize(null);
}*/

function produtoData(data){
	pemsData.produto = data;
	produtoView();
	LocalStorage('save');
}

function produtoView(){
	$('#resultsBox').html('');
	$('#rItemBox1, #pItemBox1').remove();
	console.log(pemsData.produto);
	
	var navDisplay = "<div id='rItemBox1'>\
						 <div id='rItemBox1m1'>$icoDiv$nome</div>\
						 <div id='rItemBox1m2'><p>CICLO</p><p>$statusCaption</p><div class='pItemBoxCicloE po'></div><div class='pItemBoxCicloD po'></div></div>\
						 <div id='rItemBox1m3'><p>RESPONSÁVEL</p><p>$respnome</p></div>\
					 </div>";
	var newItem = 	"<div id='pItem' class='obj"+pemsData.selOE+"'>\
						<div id='rItemBox3'>\
							<div id='rItemBox3m1' class='itemTitle'><div>INDICADORES</div><div>NO MÊS</div><div>ATÉ O PERÍODO - 2012</div><div>HISTÓRICO - 2011</div></div>\
							<div id='rItemBox3m2'>$indContent</div>\
						</div>\
					</div>";
						/* \
						<div id='rItemBox5'>\
							<div id='rItemBox5m1' class='itemTitle'><div>PLANO DE CONTINGÊNCIA - ENCAMINHAMENTOS<span id='encaminhaedit' class='po '></span><span id='encaminhaup' class='po hidden animate'></span><span id='encaminhax' class='po hidden animate'></span></div><div>DATA</div><div>PRAZO</div><div>RESPONSÁVEL</div></div>\
							<div id='rItemBox5m2'>$encContent</div>\
						</div>\
						*/ 
	if (pemsData.produto){
		navDisplay = navDisplay.split('$nome').join(pemsData.produto.nome);
		
		var iclass = '';
		if (pemsData.produto.prioritario=="S") iclass+="<div class='icoPrioritario' title='Prioritário'></div>";
		if (pemsData.produto.atencao=='true') iclass+="<div class='icoAtencao po' title='Atenção ao Ministro'></div>";
		navDisplay = navDisplay.split('$icoDiv').join(iclass);
		
		navDisplay = navDisplay.split('$respnome').join(pemsData.produto.responsavel.nome);
		
		if (isNaN(pemsData.produto.ciclo)) pemsData.produto.ciclo = 0;
		navDisplay = navDisplay.split('$statusCaption').join(pemsData.produto.periodoAcompanhamento[pemsData.produto.ciclo].mes+'/'+pemsData.produto.periodoAcompanhamento[pemsData.produto.ciclo].ano);
		$('#navTitle').text('PRODUTO');
	}else{
		$('#navTitle').text('ERRO - ITEM INEXISTENTE');
		return;
	}
	
	var indRow= "<div>\
					<div class='indRow'>\
						<div class='indDesc'>$nomeIndicador</div>\
						$indResul\
					</div>\
					$indJustif\
				</div> ";	
	var indResul = "<div class='indResultado'>\
						<div class='pr'>Meta/Realizado<br><div>M<br>R</div><div>$previsto<br>$realizado</div></div>\
						<div class='atingimento'><img src='image/$cor' $imgParams><br>$atingimento%</div>\
					</div>";				
	var indJustif = "<div class='indJustificativa'> \
						Justificativa: \
						<div id='divJustText$codArf' contenteditable='$edicao'>$justText</div> \
						<div>\
							$indJustifToolbar \
							<div>\
								Autor: Márcio Roberto de Mello <br>\
								Data: 07/02/2013\
							</div>\
						</div>\
					</div>";
	var indJustifToolbar = "<div> \
								<img src='image/salvar.gif' id='btnJustSave$codArf' class='po' codArf='$codArf' /> \
							</div>";
	var indContent='';
	if (pemsData.produto.periodoAcompanhamento[pemsData.produto.ciclo].indMonitoramento) $(pemsData.produto.periodoAcompanhamento[pemsData.produto.ciclo].indMonitoramento).each(function(indexI, elementI){	
		var indResulTemp='';
		var indJustifTemp='';
		if (elementI.valores && elementI.valores.length) $.each(elementI.valores, function(indexV, elementV){
			//TODO: adicionar um campo na tabela tb_cor para armazenar o caminho da imagem no site do PPE que é diferente do eCar
			switch(elementV.cor)
			{
				case 'sVerde9.png':
				  elementV.cor = 'status1.gif';
				  break;
				case 'sAmarelo9.png':
				  elementV.cor = 'status2.gif';
				  break;
				case 'sVermelho9.png':
				  elementV.cor = 'status3.gif';
				  break;
				case 'sBranco9.png':
				  elementV.cor = 'status0.gif';
				  break;
			}

			var imgParams='';
			if (elementV.exigeJustificativa=='true')
			{
				imgParams = "id='imgStatus_"+ elementI.codArf + "' codArf='" + elementI.codArf + "' class='po'";
				indJustifTemp = indJustif;
				indJustifTemp = indJustifTemp.split('$justText').join(elementV.justificativa);
				indJustifTemp = indJustifTemp.split('$edicao').join('true');
				if (elementV.edicaoJustificativa=='false')
					indJustifTemp = indJustifTemp.split('$indJustifToolbar').join(indJustifToolbar).split('$codArf').join(elementI.codArf);
				else
					indJustifTemp = indJustifTemp.split('$indJustifToolbar').join("");
			}

			indResulTemp += indResul.split('$previsto').join(elementV.previsto).split('$realizado').join(elementV.realizado).split('$cor').join(elementV.cor).split('$imgParams').join(imgParams).split('$atingimento').join(elementV.atingimento);
		});
		indContent+=indRow.split('$index').join(indexI).split('$nomeIndicador').join(elementI.nomeIndicador).split('$indResul').join(indResulTemp).split('$indJustif').join(indJustifTemp);
	});
	newItem = newItem.split('$indContent').join(indContent);
	
	var encaminhaContent="<div class='enc'><div>$info</div><div>$date</div><div>$deadline</div><div>$resp</div></div>"
	var encaminhaContentNone="<div class='enc'><div class='encNone'>Nenhum Encaminhamento</div></div>"
	var comments='';
	if (pemsData.produto.comentarios && pemsData.produto.comentarios.length) $.each(pemsData.produto.comentarios, function(indexC, elementC){
		console.log(elementC);
		var newC = encaminhaContent;
		if (elementC.texto)	newC = newC.split('$info').join(elementC.texto.replace(/(\r\n|[\r\n])/g, "<br />")); 
		if (elementC.responsavel && elementC.responsavel.nome) {
			var sum = '<p>'+elementC.responsavel.nome;
			if (elementC.responsavel.orgao && elementC.responsavel.orgao.nome) sum+='<br/>'+elementC.responsavel.orgao.nome;
			newC = newC.split('$resp').join(sum+'</p>'); 
		}
		
		if (elementC.prazo){
			var date = elementC.prazo.split('T')[0];
			date = date.split('-');
			newC = newC.split('$deadline').join(date[2]+'/'+date[1]+'/'+date[0]);
		}
		
		if (elementC.dataInclusao){
			var date = elementC.dataInclusao.split('T')[0];
			date = date.split('-');
			newC = newC.split('$date').join(date[2]+'/'+date[1]+'/'+date[0]);
		}
		comments +=newC;
	}); else{
		comments = encaminhaContentNone;
	}
	newItem = newItem.split('$encContent').join(comments);
	
	var statusSum = [0,0,0,0,0,0];
	$('.s0 .statusMCount').text(statusSum[0]);
	$('.s1 .statusMCount').text(statusSum[1]);
	$('.s2 .statusMCount').text(statusSum[2]);
	$('.s3 .statusMCount').text(statusSum[3]);
	$('.s4 .statusMCount').text(statusSum[4]);
	$('.s5 .statusMCount').text(statusSum[5]);
	$('#statusMonitor').slideDown(1000, 'easeOutExpo', Resize());	
	
	$('#resultsBox').html(newItem);
	$('#nav').append(navDisplay);
	Show($('#resultsBox'));	
	$('#resultsBox').scrollTop(0);
	Resize(null);
}

function produtoView(){
	$('#resultsBox').html('');
	$('#rItemBox1, #pItemBox1').remove();
	console.log(pemsData.produto);
	//<strong>Data Limite:</strong> 11/03/2012<br /><strong>Atualização:</strong> 10/03/2012<br /><br />Construção de UBS: 245 novas propostas contempladas e pré-cadastro foi aberto para seleção de mais unidades. Destaca-se cadastro de 80 propostas para construção de UBS fluviais, das quais 15 unidades já foram concluídas em 2011. 
	//<div>1.1.1 Identificado vazios assistenciais e insuficiências da Assistência Básica e Assistência Especializada e definidas prioridades de intervenção e financiamentos.</div><div>ABR MAI JUN JUL AGO</div><div>ABR</div><div>SAS/DAB Heider<div class=popupArt po><span class=popupArtTitle>ARTICULADOR</span><br /><span class=popupArtInfo>$artinst<br />$artname</span></div></div>
	var navDisplay = "<div id='pItemBox1'>\
							<div id='pItemBox1m1'>$nome<div class='popupExpand po'></div></div>\
							<div id='pItemBox1m2'>CRONOGRAMA</div>\
							<div id='pItemBox1m3'>STATUS</div>\
							<div id='pItemBox1m4'><p>RESPONSÁVEL</p><p>CORRESPONSÁVEL</p></div>\
							<div id='pItemBox1m5'>$cronoProduto</div>\
							<div id='pItemBox1m6'><img src='image/status$statusCode.gif' width='46' height='46' /><br/><span class='statusCaption'>$statusCaption</span></div>\
							<div id='pItemBox1m7'><p>$respinst<br/>$respnome</p><p>$corespinst<br/>$corespnome</p></div>\
						</div>";
	var newItem = 	"<div id='pItem' class='obj"+pemsData.selOE+"'>\
						<div id='pItemBox2'>\
							<div id='pItemBox2m1' class='itemTitle'><div>PARECER - SITUAÇÃO ATUAL</div></div>\
							<div id='pItemBox2m2'>$parecerant<div class='popupExpand po'></div></div>\
						</div>\
						<div id='pItemBox4'>\
							<div id='pItemBox4m1' class='itemTitle'><div>AÇÕES</div><div>CRONOGRAMA</div><div>STATUS</div><div><p>RESPONSÁVEL</p><p>CORRESPONSAVEL</p></div></div>\
							<div id='pItemBox4m2' class='obj1'>$cronoItems</div>\
						</div>\
						<!--<div id='pItemBox5'>\
							<div id='pItemBox5m1' class='itemTitle'><div>PLANO DE CONTINGÊNCIA - ENCAMINHAMENTOS<span id='encaminhaedit' class='po'></span><span id='encaminhaup' class='po'></span><span id='encaminhax' class='po'></span></div><div>PRAZO</div></div>\
							<div id='pItemBox5m2'><div>Construção de UBS: 245 novas propostas contempladas e pré-cadastro foi aberto para seleção de mais unidades. Destaca-se cadastro de 80 propostas para construção de UBS fluviais, das quais 15 unidades já foram concluídas em 2011.</div><div>17/08/2012</div></div></div>\
						</div>-->\
					</div>";

	if (pemsData.produto){
		navDisplay = navDisplay.split('$nome').join(pemsData.produto.nome);
		navDisplay = navDisplay.split('$respinst').join(pemsData.produto.responsavel.orgao.nome);
		navDisplay = navDisplay.split('$respnome').join(pemsData.produto.responsavel.nome);
		if (pemsData.produto.coResponsavel){
			navDisplay = navDisplay.split('$corespinst').join(pemsData.produto.coResponsavel[0].orgao.nome);
			navDisplay = navDisplay.split('$corespnome').join(pemsData.produto.coResponsavel[0].nome);
		}
		navDisplay = navDisplay.split('$statusCode').join((pemsData.produto.periodoAcompanhamento[0].statusPeriodoAcompanhamento.codigo>=10)?pemsData.produto.periodoAcompanhamento[0].statusPeriodoAcompanhamento.codigo-6:pemsData.produto.periodoAcompanhamento[0].statusPeriodoAcompanhamento.codigo);
		navDisplay = navDisplay.split('$statusCaption').join(pemsData.produto.periodoAcompanhamento[0].mes+'/'+pemsData.produto.periodoAcompanhamento[0].ano);
		newItem = newItem.split('$parecerant').join(pemsData.produto.periodoAcompanhamento[0].parecer)/*.replace(/(\r\n|[\r\n])/g, "<br />"))*/;
		$('#navTitle').text('PRODUTOS/MARCOS INTERMEDIÁRIOS '+pemsData.prodNav);
	}else{
		$('#navTitle').text('ERRO - ITEM INEXISTENTE');
		return;
	}
	
	var row= '<div class="rItemBox4P row $id">$content</div>';
	var subRow= '<div class="rItemBox4P subrow $id">$content</div>';
	var colRow= '<div>$content</div>';
	var colRowBold= '<div class="bold">$content</div>';
	var colSubrow='<div>$content</div>';
	
	var col1Content='<span class="objId">$code</span><span class="objResinfo $oeId" id="param$itemId">$info</span>';
	var col2Title="<div class='croMes'>$month</div>";
	var col2Grid="<div class='croMesData'>$fill</div>";
	var col2Fill="<span class='croMesFill'></span>";
	var col3Content='<img src="image/status$statusCode.gif" width="46" height="46" /><span class="statusCaption">$statusCaption</span>';
	var col4Content='<p>$respinst<br />$respname</p><p>$corespinst<br />$corespname</p><div class="popupArt po"><span class="popupArtTitle">ARTICULADOR</span><br /><span class="popupArtInfo">$artinst<br />$artname</span></div>';
	var cronoItemSum = '';

	var month = ['JAN', 'FEV', 'MAR', 'ABR', 'MAI', 'JUN', 'JUL', 'AGO', 'SET', 'OUT', 'NOV', 'DEZ'];
	var statusSum = [0,0,0,0,0,0];
	
	
	var startDate=0, endDate=1;
		
	if (pemsData.produto.dataInicio) startDate = (pemsData.produto.dataInicio.split('-')[1]<<0);
	if (pemsData.produto.dataFim) endDate = (pemsData.produto.dataFim.split('-')[1]<<0);
	
	var cronoP ='';
	for (var i=0; i<12; i++){
		cronoP+=col2Title.split('$month').join(month[i]);
	}
	for (var i=0; i<12; i++){
		var g = col2Grid;
		if (i+1>=startDate && i+1<=endDate)	g=g.split('$fill').join(col2Fill);
		else g=g.split('$fill').join('');
		cronoP+=g;
	}
	navDisplay = navDisplay.split('$cronoProduto').join(cronoP);
	
//	if (pemsData.produtos.acoes && pemsData.produtos.acoes.length) $.each(pemsData.produtos.acoes, function(indexA, elementA){	//Correct
	if (pemsData.produto.acoes && pemsData.produto.acoes.length) $.each(pemsData.produto.acoes, function(indexA, elementA){	
//		console.log(elementP);

		var productId = 'id_1_1_'+(elementA.siglaAcao<<0);
		
		var ocontent = col1Content;
		ocontent = ocontent.split('$code').join((elementA.siglaAcao<<0))
		ocontent = ocontent.split('$info').join(elementA.nome);
		ocontent = ocontent.split('$itemId').join(elementA.codigo);
		ocontent = ocontent.split('$oeId').join('obj'+pemsData.selOE);
//		if (elementP.acoes && elementP.acoes.length>=1) ocontent="<span class='expandToggle'></span>"+ocontent;
		ocontent =  colRowBold.split('$content').join(ocontent);
		
		startDate=0, endDate=1;
		
		if (elementA.dataInicio) startDate = (elementA.dataInicio.split('-')[1]<<0);
		if (elementA.dataFim) endDate = (elementA.dataFim.split('-')[1]<<0);
		
		var ccontent ='';
		for (var i=0; i<12; i++){
			ccontent+=col2Title.split('$month').join(month[i]);
		}
		for (var i=0; i<12; i++){
			var g = col2Grid;
			if (i+1>=startDate && i+1<=endDate)	g=g.split('$fill').join(col2Fill);
			else g=g.split('$fill').join('');
			ccontent+=g;
		}
		ccontent = colRow.split('$content').join(ccontent);
		
		var scontent = colRow.split('$content').join(col3Content);//.split('$statusCode').join(elementR.periodoAcompanhamento.statusPeriodoAcompanhamento.codigo);
		
		if (elementA.periodoAcompanhamento) {
			var statusCodeA = elementA.periodoAcompanhamento[0].statusPeriodoAcompanhamento.codigo;
			if (statusCodeA>=10) statusCodeA-=6;
			statusSum[statusCodeA]++;
			scontent = scontent.split('$statusCode').join(statusCodeA);
			scontent = scontent.split('$statusCaption').join(elementA.periodoAcompanhamento[0].mes+'/'+elementA.periodoAcompanhamento[0].ano);
		}
				
		var rcontent = colRow.split('$content').join(col4Content);

		if (elementA.responsavel) rcontent = rcontent.split('$respinst').join(elementA.responsavel.orgao.nome).split('$respname').join(elementA.responsavel.nome)
		if (elementA.coResponsavel){
			 rcontent=rcontent.split('$corespinst').join(elementA.coResponsavel[0].orgao.nome).split('$corespname').join(elementA.coResponsavel[0].nome);
			 if (elementA.coResponsavel.length>1) rcontent=rcontent.split('$artinst').join(elementA.coResponsavel[1].orgao.nome).split('$artname').join(elementA.coResponsavel[1].nome);
		}
		
		cronoItemSum += row.split('$id').join(productId)
		cronoItemSum = cronoItemSum.split('$content').join(ocontent+ccontent+scontent+rcontent);
		//cronoItemSum += ocontent+ccontent+scontent+rcontent;
/*
		if (elementP.acoes && elementP.acoes.length) $.each(elementP.acoes, function(indexA, elementA){
			
			productIdA = productId+'_'+parseInt(elementA.siglaAcao);
		
			ocontent = colSubrow.split('$content').join(col1Content);
			ocontent = ocontent.split('$code').join(parseInt(elementP.siglaProduto)+'.'+parseInt(elementA.siglaAcao))
			ocontent = ocontent.split('$info').join(elementA.nome);
			startDate=0;
			endDate=1;
			if (elementA.dataInicio) startDate = parseInt(elementA.dataInicio.split('-')[1]);
			if (elementA.dataFim) endDate = parseInt(elementA.dataFim.split('-')[1]);
			
			ccontent ='';
			for (var i=0; i<9; i++){
				ccontent+=col2Title.split('$month').join(month[i]);
			}
			for (var i=0; i<9; i++){
				var g = col2Grid;
				if (i+1>=startDate && i+1<=endDate)	g=g.split('$fill').join(col2Fill);
				else g=g.split('$fill').join('');
				ccontent+=g;
			}
			ccontent = colSubrow.split('$content').join(ccontent);

			
			scontent = colSubrow.split('$content').join(col3Content);

			
			if (elementA.periodoAcompanhamento) {
				var statusCodeA = elementA.periodoAcompanhamento.statusPeriodoAcompanhamento.codigo;
				if (statusCodeA>=10) statusCodeA-=6;
				statusSum[statusCodeA]++;
				scontent = scontent.split('$statusCode').join(statusCodeA);
				scontent = scontent.split('$statusCaption').join(elementA.periodoAcompanhamento.mes+'/'+elementA.periodoAcompanhamento.ano);
			}
					
			var rcontent = colSubrow.split('$content').join(col4Content);
			if (elementA.responsavel) rcontent = rcontent.split('$respinst').join(elementA.responsavel.orgao.nome).split('$respname').join(elementA.responsavel.nome)
			if (elementA.coResponsavel){
				 rcontent=rcontent.split('$corespinst').join(elementA.coResponsavel[0].orgao.nome).split('$corespname').join(elementA.coResponsavel[0].nome);
				 if (elementA.coResponsavel.length>1) rcontent=rcontent.split('$artinst').join(elementA.coResponsavel[1].orgao.nome).split('$artname').join(elementA.coResponsavel[1].nome);
			}
			
			cronoItemSum += subRow.split('$id').join(productIdA).split('$content').join(ocontent+ccontent+scontent+rcontent);
			//cronoItemSum += ocontent+ccontent+scontent+rcontent;
		})
*/
	});
	
	newItem = newItem.split('$cronoItems').join(cronoItemSum);
	
	
	$('.s0 .statusMCount').text(statusSum[0]);
	$('.s1 .statusMCount').text(statusSum[1]);
	$('.s2 .statusMCount').text(statusSum[2]);
	$('.s3 .statusMCount').text(statusSum[3]);
	$('.s4 .statusMCount').text(statusSum[4]);
	$('.s5 .statusMCount').text(statusSum[5]);
	$('#statusMonitor').slideDown(1000, 'easeOutExpo', Resize());	
	
	$('#resultsBox').html(newItem);
	$('#nav').append(navDisplay);
	Show($('#resultsBox'));	
	$('#resultsBox').scrollTop(0);
	Resize(null);
}

function commentData(data){
	if (data){
		alert('Enviado');
		$('.ac_results, .Zebra_DatePicker').remove();
		ShowResultado(data.codResultado, true);
	}else{
		alert('Tente Novamente');
	}
	console.log(data);	
}

var users = [];
function responsavelData(data){
    
	console.log('RESPONSAVEIS AUTOCOMPLETE', data.responsavel);
	if(pemsData.resultado.comentarios === undefined) { 
		pemsData.resultado.comentarios = new Array();
	} 
	pemsData.resultado.comentarios.codResp = new Object();
        users = []
	$.each(data.responsavel, function(e,v){
		pemsData.resultado.comentarios.codResp[v.nome.toLowerCase().replace(/ /g,"")] = v.codigo;
		users.push(v.nome);
	});
//		var ar = ['Gekson Cinaty','Giovanni Ferreira de Sousa','Daniel Siqueira','Sergio de Arruda Sampaio','Paulo de Tarso Ribeiro de Oliveira Oliveira','Paulo Sellera','Semilla Oliveira'];
	$("#respFieldInput").autocompleteArray(users, {matchContains:1});	
}

function atencaoData(data){
	console.log('Marcar Atencao',data);
	ShowResultado(pemsData.resultado.codigo, true);
	
}

function AnimateVal(ov, fval, tval){
	var from = {property: fval};
	var to = {property: tval};
	 
	jQuery(from).stop().animate(to, {
		duration: 1000,
		easing:'easeOutExpo',
		step: function() {
			ov.text(Math.ceil(this.property));
		}
	});
	

}

function LocalStorage(action){
	/*
	console.log('Local Storage');
	if (typeof(localStorage) == 'undefined' ) {
		console.log('Your browser does not support HTML5 localStorage. Try upgrading.');
		return;
	}

	try {
		if (action=='save') {
			LocalStorage('delete');
			var userObj = new Object();
			userObj.pemsData = pemsData;
			oeData[pemsData.results[0].siglaIett.split(' ')[1]<<0] = pemsData.results[0];
			userObj.oeData = oeData;
			localStorage.setItem('pems', JSON.stringify(userObj));
			console.log('Saved');
		}
		if (action=='load' && localStorage.getItem('pems')) {
			var userObj = JSON.parse(localStorage.getItem('pems'));
			pemsData = userObj.pemsData;
			oeData = userObj.oeData;
			console.log(userObj);
			console.log('Loaded');	
		}
		if (action=='load' && !localStorage.getItem('pems')) {
			var userObj = new Object();
			localStorage.setItem('pems', JSON.stringify(userObj));
		}
		if (action=='delete') {
			localStorage.removeItem('pems'); //deletes the matching item from the database
			console.log('Deleted');
		}
	} catch (e) {
		console.log(e)
//	 	 if (e == QUOTA_EXCEEDED_ERR) {
//	 	 	 alert('Quota exceeded!'); //data wasn't successfully saved due to quota exceed so throw an error
//		}
	}
	*/
}

function trim(str) {
    return str.replace(/^\s\s*/, '').replace(/\s\s*$/, '');
}


function Show(element){

	element.css('display', 'block');
	setTimeout(function(){element.removeClass('hidden').addClass('visible')}, 10);
}
function Hide(element){
	element.removeClass('visible').addClass('hidden');
	setTimeout(function(){element.css('display', 'none'), 300});
}

function Resize(event){
	
	height = $(window).innerHeight();
	width = $(window).innerWidth();
	
	if (isNaN(window.innerHeight)) {
		width = document.documentElement.clientWidth;
		height = document.documentElement.clientHeight;
	}
	
	$('#login').css('top', height*.5-115);
	$('#body').css('height', height-55);

	if (ua.indexOf('ipad')>=0){
		$('#resultsBox, #body').height('auto');
		
	}else{
		$('#resultsBox').height(height-$('#nav').height()-$('#statusMonitor').height()-resultsOffset);
		$('#searchSplash').height(height-$('#header').height()-$('#status').height()-70);	
		if ($('#resultsBox').hasClass('resultsBoxStatic')) $('#resultsBox').height(height-$('#header').height()-115);
	}

};

var Utf8 = {
 
	// public method for url encoding
	encode : function (string) {
		string = string.replace(/\r\n/g,"\n");
		var utftext = "";
 
		for (var n = 0; n < string.length; n++) {
 
			var c = string.charCodeAt(n);
 
			if (c < 128) {
				utftext += String.fromCharCode(c);
			}
			else if((c > 127) && (c < 2048)) {
				utftext += String.fromCharCode((c >> 6) | 192);
				utftext += String.fromCharCode((c & 63) | 128);
			}
			else {
				utftext += String.fromCharCode((c >> 12) | 224);
				utftext += String.fromCharCode(((c >> 6) & 63) | 128);
				utftext += String.fromCharCode((c & 63) | 128);
			}
 
		}
 
		return utftext;
	},
 
	// public method for url decoding
	decode : function (utftext) {
		var string = "";
		var i = 0;
		var c = c1 = c2 = 0;
 
		while ( i < utftext.length ) {
 
			c = utftext.charCodeAt(i);
 
			if (c < 128) {
				string += String.fromCharCode(c);
				i++;
			}
			else if((c > 191) && (c < 224)) {
				c2 = utftext.charCodeAt(i+1);
				string += String.fromCharCode(((c & 31) << 6) | (c2 & 63));
				i += 2;
			}
			else {
				c2 = utftext.charCodeAt(i+1);
				c3 = utftext.charCodeAt(i+2);
				string += String.fromCharCode(((c & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63));
				i += 3;
			}
 
		}
 
		return string;
	}
 
}


$('#comboExercicio').change(function(){
		pemsData.exercicio=$(this).val();
		anoExercicioSelecionado = $('#comboExercicio option:selected').text();
		var totalStatus=0;
		$('.statusCount:visible').each(function(){totalStatus++;});
		if(totalStatus>0){
			$('.styled-select-load').css("visibility", "visible");
			$('#comboExercicio').attr('disabled', 'disabled');
			GetData('status');
		}	
	});

topreload=["image/favicon.ico","image/loginbtn.gif","image/logo_small.gif","image/fundoinicio.png","image/header_logos.gif","image/loginbtn-mobile.gif","css/ipad.css","image/home.gif","image/busca.gif","image/check.png","image/checked.png","image/column_sep.gif","image/contract.png","image/fechar.png","image/filtro0.gif","image/filtro0i.gif","image/filtro0is.gif","image/filtro0s.gif","image/filtro1.gif","image/filtro1i.gif","image/filtro1is.gif","image/filtro1s.gif","image/filtro2.gif","image/filtro2i.gif","image/filtro2is.gif","image/filtro2s.gif","image/filtro3.gif","image/filtro3i.gif","image/filtro3is.gif","image/filtro3s.gif","image/filtro4.gif","image/filtro4i.gif","image/filtro4is.gif","image/filtro4s.gif","image/filtro5.gif","image/filtro5i.gif","image/filtro5is.gif","image/filtro5s.gif","image/filtro6.gif","image/filtro6i.gif","image/filtro6is.gif","image/filtro6s.gif","image/filtro7.gif","image/filtro7i.gif","image/filtro7is.gif","image/filtro7s.gif","image/filtro8.gif","image/filtro8i.gif","image/filtro8is.gif","image/filtro8s.gif","image/filtro9.gif","image/filtro9i.gif","image/filtro9is.gif","image/filtro9s.gif","image/footer1.jpg","image/footer2.jpg","image/footer3.jpg","image/materia1.jpg","image/plus.gif","image/retornar.gif","image/row_sep.gif","image/row_sep_white.gif","image/search-side.gif","image/search.gif","image/separator.gif","image/separator.png","image/seta.png","image/splash_sep.gif","image/status0.gif","image/status1.gif","image/status2.gif","image/status3.gif","image/status4.gif","image/status5.gif","image/statusselecao.gif","image/subitem_check.png","image/subitem_checked.png","image/tagcloud-side.gif","image/busca-side.gif","image/encaminhaedit.png","image/encaminhaup.png","image/encaminhax.png","image/expand.png","image/icoprioritario.png","image/itemborder.gif","image/tagcloud.gif"];
