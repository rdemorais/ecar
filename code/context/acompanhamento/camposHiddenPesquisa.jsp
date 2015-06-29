
<%@page import="ecar.pojo.ConfiguracaoCfg"%><jsp:directive.page import="ecar.pojo.EstruturaEtt"/>
<jsp:directive.page import="ecar.pojo.SisGrupoAtributoSga"/>
<jsp:directive.page import="ecar.taglib.util.Input"/>
<jsp:directive.page import="ecar.dao.SisTipoExibicGrupoDao"/>
<jsp:directive.page import="ecar.pojo.SisAtributoSatb"/>
<jsp:directive.page import="ecar.pojo.ItemEstruturaSisAtributoIettSatb"/>
<%@page import="ecar.pojo.AtributoLivre"%>
<%@page import="ecar.dao.ConfiguracaoDao"%><% 
	
		List listEstruturas = new ArrayList();
		listEstruturas = estAtribTipoAcompEataDao.getEstruturaEhFiltro(tipoAcompanhamento);
							
		Iterator itEstrutura = listEstruturas.iterator();
    	estruturaDao = new EstruturaDao(request);
    	
    	String nomeHidden = "";
    	StringBuffer hiddens = new StringBuffer();
    	while (itEstrutura.hasNext()) {
    		EstruturaEtt ett = (EstruturaEtt) itEstrutura.next();
    		String codEtt = ett.getCodEtt().toString();
    	
    		
    		List atributos = estruturaDao.getAtributosEstruturaDadosGeraisEhFiltro(ett,tipoAcompanhamento);
			Iterator itAtributos = atributos.iterator();
			while (itAtributos.hasNext()){
						
				ObjetoEstrutura objetoEstrutura = (ObjetoEstrutura) itAtributos.next();
			
    				
				/*******************************************ATRIBUTO FIXO ************************************************************************/
				if (objetoEstrutura.iGetGrupoAtributosLivres() == null){
				
    				if(objetoEstrutura.iGetNome().equals("areaAre")
    				  || objetoEstrutura.iGetNome().equals("subAreaSare")
    				  || objetoEstrutura.iGetNome().equals("unidadeOrcamentariaUO")
    				  || objetoEstrutura.iGetNome().equals("orgaoOrgByCodOrgaoResponsavel1Iett")
    				  || objetoEstrutura.iGetNome().equals("orgaoOrgByCodOrgaoResponsavel2Iett")
    				  || objetoEstrutura.iGetNome().equals("periodicidadePrdc")
    				  || objetoEstrutura.iGetNome().equals("situacaoSit")
    				  || objetoEstrutura.iGetNome().equals("indCriticaIett")) {
    					  
    					  
    				  	nomeHidden = ett.getCodEtt() + "_" + objetoEstrutura.iGetNome();
    				  	String valorHidden[] = Pagina.getParamLista(request, nomeHidden);
    					  	 
    					  
    				  	if (valorHidden!=null && !(valorHidden.length==1 && valorHidden[0].equals(""))) {                 		
		    				for (int i=0; i<valorHidden.length;i++) {
				       			if (valorHidden[i]!=null && !"".equals(valorHidden[i])) {
					        		hiddens.append("<input type=\"hidden\" name=\"" + nomeHidden + "\" value=\"" + valorHidden[i] + "\"" + ">\n" );
					    		}	
				       		}
				      	} 
					
					// se for string
					} else if(
						objetoEstrutura.iGetNome().equals("siglaIett")
						||	objetoEstrutura.iGetNome().equals("nomeIett")
						||	objetoEstrutura.iGetNome().equals("descricaoIett")
						||	objetoEstrutura.iGetNome().equals("origemIett")
						||	objetoEstrutura.iGetNome().equals("objetivoGeralIett")
						||	objetoEstrutura.iGetNome().equals("beneficiosIett")
						||	objetoEstrutura.iGetNome().equals("indCriticaIett")
						||	objetoEstrutura.iGetNome().equals("descricaoR1")
						||	objetoEstrutura.iGetNome().equals("descricaoR2")
						||	objetoEstrutura.iGetNome().equals("descricaoR3")
						||	objetoEstrutura.iGetNome().equals("descricaoR4")
						||	objetoEstrutura.iGetNome().equals("descricaoR5")
						||  objetoEstrutura.iGetNome().equals("usuarioUsuByCodUsuIncIett") 
						||  objetoEstrutura.iGetNome().equals("usuarioUsuByCodUsuUltManutIett")
						||  objetoEstrutura.iGetNome().equals("objetivoEspecificoIett")) {
					
						nomeHidden = ett.getCodEtt() + "_" + objetoEstrutura.iGetNome();
						String valorHidden = Pagina.getParamStr(request, nomeHidden);
						if(valorHidden != null && !valorHidden.equals(""))
        					hiddens.append("<input type=\"hidden\" name=\"" + nomeHidden + "\" value=\"" + valorHidden + "\"" + ">\n" );
    			
    				// se for ItemStringComoListaChecks
    				} else if( 
    					objetoEstrutura.iGetNome().equals("indAtivoIett")
    					|| objetoEstrutura.iGetNome().equals("indMonitoramentoIett")
    					|| objetoEstrutura.iGetNome().equals("indBloqPlanejamentoIett")){
    				
    				
    					nomeHidden = ett.getCodEtt() + "_" + objetoEstrutura.iGetNome();
    				  	String valorHidden[] = Pagina.getParamLista(request, nomeHidden);
    					  	 
    					  
    				  	if (valorHidden!=null && !(valorHidden.length==1 && valorHidden[0].equals(""))) {                 		
		    				for (int i=0; i<valorHidden.length;i++) {
				       			if (valorHidden[i]!=null && !"".equals(valorHidden[i])) {
					        		hiddens.append("<input type=\"hidden\" name=\"" + nomeHidden + "\" value=\"" + valorHidden[i] + "\"" + ">\n" );
					    		}	
				       		}
				      	} 
					
    				
    				
    				// se for ItemValor
    				} else if(objetoEstrutura.iGetNome().equals("valPrevistoFuturoIett")) {
    				
    					//Inicio e  fim
    					String nomeHiddenInicio = ett.getCodEtt() + "_" + objetoEstrutura.iGetNome() + "_Inicio";
    					String valorHiddenInicio = Pagina.getParamStr(request, nomeHiddenInicio);
    					if(valorHiddenInicio != null && !valorHiddenInicio.equals(""))
							hiddens.append("<input type=\"hidden\" name=\"" + nomeHiddenInicio + "\" value=\"" + valorHiddenInicio + "\"" + ">\n" );
							
						String nomeHiddenFim = ett.getCodEtt() + "_" + objetoEstrutura.iGetNome() + "_Fim";
						String valorHiddenFim = Pagina.getParamStr(request, nomeHiddenFim);
						if(valorHiddenFim != null && !valorHiddenFim.equals(""))
							hiddens.append("<input type=\"hidden\" name=\"" + nomeHiddenFim + "\" value=\"" + Pagina.getParamStr(request, nomeHiddenFim)+ "\"" + ">\n" );
    				
    				
					//se for data
					} else if(objetoEstrutura.iGetNome().equals("dataInicioIett") 
							|| objetoEstrutura.iGetNome().equals("dataTerminoIett")
							|| objetoEstrutura.iGetNome().equals("dataUltManutencaoIett")
							|| objetoEstrutura.iGetNome().equals("dataR1")
							|| objetoEstrutura.iGetNome().equals("dataR2")
							|| objetoEstrutura.iGetNome().equals("dataR3")
							|| objetoEstrutura.iGetNome().equals("dataR4")
							|| objetoEstrutura.iGetNome().equals("dataR5")
							|| objetoEstrutura.iGetNome().equals("dataInclusaoIett") 
							|| objetoEstrutura.iGetNome().equals("dataInicioMonitoramentoIett")) {
					
						//recupera a data inicial e final do filtro	
						String nomeHiddenInicio = ett.getCodEtt() + "_" + objetoEstrutura.iGetNome() + "_Inicio";
						String valorHiddenInicio = Pagina.getParamStr(request, nomeHiddenInicio);
						if(valorHiddenInicio != null && !valorHiddenInicio.equals(""))
							hiddens.append("<input type=\"hidden\" name=\"" + nomeHiddenInicio + "\" value=\"" + valorHiddenInicio + "\"" + ">\n" );
						
						String nomeHiddenFim = ett.getCodEtt() + "_" + objetoEstrutura.iGetNome() + "_Fim";
						String valorHiddenFim = Pagina.getParamStr(request, nomeHiddenFim);
						if(valorHiddenFim != null && !valorHiddenFim.equals(""))
							hiddens.append("<input type=\"hidden\" name=\"" + nomeHiddenFim + "\" value=\"" + valorHiddenFim+ "\"" + ">\n" );
					
					} else {
					
					/*	nomeHidden = ett.getCodEtt() + "_" + objetoEstrutura.iGetNome();
						String valorHidden = Pagina.getParamStr(request, nomeHidden);
						if(valorHidden != null && !valorHidden.equals(""))
							hiddens.append("<input type=\"hidden\" name=\"" + nomeHidden + "\" value=\"" + valorHidden + "\"" + ">\n" );
					
					}*/
					
						
	       				String nomeHiddenNivelPlanejamento = codEtt + "_a" + configuracao.getSisGrupoAtributoSgaByCodSgaGrAtrNvPlan().getCodSga().toString();        
	       				String valorHiddenNivelPlanejamento[] = Pagina.getParamLista(request, nomeHiddenNivelPlanejamento);
	       				
	       				if (valorHiddenNivelPlanejamento!=null && !(valorHiddenNivelPlanejamento.length==1 && valorHiddenNivelPlanejamento[0].equals(""))) {                 		
		    				for (int i=0; i<valorHiddenNivelPlanejamento.length;i++) {
				       			if (valorHiddenNivelPlanejamento[i]!=null && !"".equals(valorHiddenNivelPlanejamento[i])) {
					        		hiddens.append("<input type=\"hidden\" name=\"" + nomeHiddenNivelPlanejamento + "\" value=\"" + valorHiddenNivelPlanejamento[i] + "\"" + ">\n" );
					    		}	
				       		}
				      	} 
	    	
					}
					
					
		
        		} else {
        		
        		
        		
        		/*******************************************ATRIBUTO LIVRE ************************************************************************/
        		
               		SisGrupoAtributoSga grupoAtributo = objetoEstrutura.iGetGrupoAtributosLivres();
					
					nomeHidden = codEtt + "_a" + grupoAtributo.getCodSga().toString();
		    		String tipoCampoHidden = grupoAtributo.getSisTipoExibicGrupoSteg().getCodSteg().toString();
		    		
		    		//Se for CheckBox ou RadioButton ou ListBox, não procura em InformacaoIettSatb
		    		if(tipoCampoHidden.equals(SisTipoExibicGrupoDao.CHECKBOX) || tipoCampoHidden.equals(SisTipoExibicGrupoDao.LISTBOX)){
		    			
		    			String valorHidden[] = Pagina.getParamLista(request, nomeHidden);
		    		  	if (valorHidden!=null && !(valorHidden.length==1 && valorHidden[0].equals(""))) {                 		
		    				for (int i=0; i<valorHidden.length;i++) {
				       			if (valorHidden[i]!=null && !"".equals(valorHidden[i])) {
					        		hiddens.append("<input type=\"hidden\" name=\"" + nomeHidden + "\" value=\"" + valorHidden[i] + "\"" + ">\n" );
					    		}	
				       		}
				      	} 
					
					
					//Se for Radio Button
					} else if(tipoCampoHidden.equals(SisTipoExibicGrupoDao.RADIO_BUTTON) || tipoCampoHidden.equals(SisTipoExibicGrupoDao.COMBOBOX)){

		    			String valorHidden[] = Pagina.getParamLista(request, nomeHidden);
		    			if (valorHidden!=null && !(valorHidden.length==1 && valorHidden[0].equals(""))) {                 		
		    				for (int i=0; i<valorHidden.length;i++) {
				       			if (valorHidden[i]!=null && !"".equals(valorHidden[i])) {
					        		hiddens.append("<input type=\"hidden\" name=\"" + nomeHidden + "\" value=\"" + valorHidden[i] + "\"" + ">\n" );
					    		}	
				       		}
				      	} 
					
					//Se for TEXT Field ou TEXT AREA	
		    		} else if (tipoCampoHidden.equals(SisTipoExibicGrupoDao.TEXT) || tipoCampoHidden.equals(SisTipoExibicGrupoDao.TEXTAREA)) {
		    			String valorHidden = Pagina.getParamStr(request, nomeHidden);
		    		    if(valorHidden != null && !valorHidden.equals(""))
		    		    	hiddens.append("<input type=\"hidden\" name=\"" + nomeHidden + "\" value=\"" + valorHidden + "\"" + ">\n" );
						
		    		//Se for MULTITEXTO
		    		} else if (tipoCampoHidden.equals(SisTipoExibicGrupoDao.MULTITEXTO)) {
		    		
		    			//1_a27_108
		    			if (objetoEstrutura.iGetGrupoAtributosLivres().getSisAtributoSatbs() != null) {
							Iterator itAtribLivres = objetoEstrutura.iGetGrupoAtributosLivres().getSisAtributoSatbs().iterator();
							while (itAtribLivres.hasNext()) {
								SisAtributoSatb atributoSis = (SisAtributoSatb) itAtribLivres.next();
								AtributoLivre atributoLivre = new AtributoLivre();
								String atrib = "";
								nomeHidden = codEtt + "_a"+ atributoSis.getSisGrupoAtributoSga().getCodSga().toString() + "_" +
																									atributoSis.getCodSatb().toString();
								String valorHidden = Pagina.getParamStr(request, nomeHidden);
								if(valorHidden != null && !valorHidden.equals(""))
									hiddens.append("<input type=\"hidden\" name=\"" + nomeHidden + "\" value=\"" + valorHidden + "\"" + ">\n" );
							}
						}	
		    			
					
					//Se for VALIDACAO
					} else if (tipoCampoHidden.equals(SisTipoExibicGrupoDao.VALIDACAO)) {
					
						String nomeHiddenInicio = codEtt + "_a" + grupoAtributo.getCodSga() + "_Inicio";
						String valorHidden = "";
						
						valorHidden = Pagina.getParamStr(request, nomeHiddenInicio);
						if(valorHidden != null && !valorHidden.equals(""))
							hiddens.append("<input type=\"hidden\" name=\"" + nomeHidden + "\" value=\"" + valorHidden + "\"" + ">\n" );
						
						String nomeHiddenFim = codEtt + "_a" + grupoAtributo.getCodSga() + "_Fim";
						valorHidden = Pagina.getParamStr(request, nomeHiddenFim);
						if(valorHidden != null && !valorHidden.equals(""))
							hiddens.append("<input type=\"hidden\" name=\"" + nomeHidden + "\" value=\"" + valorHidden + "\"" + ">\n" );
						
						nomeHidden = codEtt + "_a" + grupoAtributo.getCodSga();
						valorHidden = Pagina.getParamStr(request, nomeHidden);
						
						if(valorHidden != null && !valorHidden.equals(""))
	    					hiddens.append("<input type=\"hidden\" name=\"" + nomeHidden + "\" value=\"" + valorHidden + "\"" + ">\n" );
	    				
					}						
			
        		} // final de atributo livre
    		}// final do while
   		}
		
%>	


<%=hiddens.toString()%>
	