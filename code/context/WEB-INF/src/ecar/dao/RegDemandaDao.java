package ecar.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import comum.database.Dao;
import comum.util.ConstantesECAR;
import comum.util.Data;
import comum.util.FileUpload;
import comum.util.Pagina;
import comum.util.Util;

import ecar.exception.ECARException;
import ecar.login.SegurancaECAR;
import ecar.pojo.AtributoDemandaAtbdem;
import ecar.pojo.ConfiguracaoCfg;
import ecar.pojo.DemAtributoDema;
import ecar.pojo.DemandasGrpAcesso;
import ecar.pojo.EntidadeAtributoEnta;
import ecar.pojo.EntidadeEnt;
import ecar.pojo.LocalItemLit;
import ecar.pojo.ObjetoDemanda;
import ecar.pojo.PaiFilho;
import ecar.pojo.PrioridadePrior;
import ecar.pojo.RegApontamentoRegda;
import ecar.pojo.RegDemandaAnexoRegdan;
import ecar.pojo.RegDemandaRegd;
import ecar.pojo.SisAtributoSatb;
import ecar.pojo.SisGrupoAtributoSga;
import ecar.pojo.SitDemandaSitd;
import ecar.pojo.UsuarioAtributoUsua;
import ecar.pojo.UsuarioUsu;
import ecar.pojo.VisaoAtributoDemanda;
import ecar.pojo.VisaoDemandasVisDem;
import ecar.taglib.util.Input;
import ecar.util.Dominios;

/**
 * Classe de manipulação de objetos da classe AtributoAtt.
 * 
 * @author CodeGenerator - Esta classe foi gerada automaticamente
 * @since 1.0
 * @version 1.0, Fri Jan 27 07:54:28 BRST 2006
 *
 */
public class RegDemandaDao extends Dao {
	/**
	 * Construtor. Chama o Session factory do Hibernate
         *
         * @param request
         */
	public RegDemandaDao(HttpServletRequest request) {
		super();
		this.request = request;
	}
	
	/**
	 * Seta os valores do form no objeto RegDemandaRegd.
	 * @param regDemanda
	 * @param request
	 * @throws NumberFormatException
	 * @throws ECARException
	 */
	public void setRegDemanda(RegDemandaRegd regDemanda, HttpServletRequest request) 
				throws NumberFormatException, ECARException {
		
		//Usado o request.getParameter para verificar se o campo foi configurado para 
		//ser apresentado na visão
		//Só seta o valor caso esteja na tela, caso contrário continua a informação original
		if (request.getParameter("dataLimiteRegd") != null){
			regDemanda.setDataLimiteRegd(Pagina.getParamDataBanco(request, "dataLimiteRegd"));
		}
		
		if (request.getParameter("descricaoRegd") != null){
			regDemanda.setDescricaoRegd(Util.normalizaQuebraDeLinha(Pagina.getParamStr(request, "descricaoRegd").trim()));
		}
		
		if (request.getParameter("observacaoRegd") != null){
			regDemanda.setObservacaoRegd(Util.normalizaQuebraDeLinha(Pagina.getParamStr(request, "observacaoRegd").trim()));
		}
		
		if (request.getParameter("numeroDocOrigemRegd") != null){
			regDemanda.setNumeroDocOrigemRegd(Integer.valueOf(Pagina.getParamInt(request, "numeroDocOrigemRegd")));
		}
			
		if (request.getParameter("dataSolicitacaoRegd") != null){
			regDemanda.setDataSolicitacaoRegd(Pagina.getParamDataBanco(request, "dataSolicitacaoRegd"));
		}
		
		if (request.getParameter("indAtivoRegd") != null){
			regDemanda.setIndAtivoRegd(Pagina.getParamStr(request, "indAtivoRegd"));
		}
		
		if (request.getParameter("nomeSolicitanteRegd") != null){
			regDemanda.setNomeSolicitanteRegd(Pagina.getParamStr(request, "nomeSolicitanteRegd").trim());
		}
		
		if (request.getParameter("prioridadePrior") != null){
			if (!Pagina.getParamStr(request, "prioridadePrior").equals("")){
				regDemanda.setPrioridadePrior((PrioridadePrior) this.buscar(PrioridadePrior.class, Long.valueOf(Pagina.getParam(request, "prioridadePrior"))));
			} else {
				regDemanda.setPrioridadePrior(null);
			}
		}
		
		if (request.getParameter("sitDemandaSitd") != null){
			if (!Pagina.getParamStr(request, "sitDemandaSitd").equals("")){
				regDemanda.setSitDemandaSitd((SitDemandaSitd) this.buscar(SitDemandaSitd.class, Long.valueOf(Pagina.getParam(request, "sitDemandaSitd"))));
			} else {
				regDemanda.setSitDemandaSitd(null);
			}
		} 

		if (request.getParameter("regDemandaRegd") != null){
			if (!Pagina.getParamStr(request, "regDemandaRegd").equals("")){
				regDemanda.setRegDemandaRegd((RegDemandaRegd) this.buscar(RegDemandaRegd.class, Long.valueOf(Pagina.getParam(request, "regDemandaRegd"))));
			} else {
				regDemanda.setRegDemandaRegd(null);
			}
		}
		
		setLocais(regDemanda, request);
		setEntidadeOrgaos(regDemanda, request);
		setEntidades(regDemanda, request);
		setAtributosDemanda(request, regDemanda);
				
	}
	
	/**
	 * Seta atributos Demanda
	 * @param request
	 * @param regDemanda
	 * @throws ECARException
	 */
	public void setAtributosDemanda(HttpServletRequest request,
			RegDemandaRegd regDemanda) throws ECARException {
		
		regDemanda.setDemAtributoDemas(null);
		
		List lAtributos;
		lAtributos =  new SisGrupoAtributoDao(request).getGruposAtributosCadastro("D");
        Iterator it = lAtributos.iterator();
        while (it.hasNext()) {
            SisGrupoAtributoSga grupoAtributo = (SisGrupoAtributoSga) it.next();           
            if (!"".equals(Pagina.getParamStr(request, "a" + grupoAtributo.getCodSga().toString())) || !"".equals(Pagina.getParamStr(request, "hidden_a" + grupoAtributo.getCodSga().toString()))) {
                DemAtributoDema demandaAtributo = new DemAtributoDema();
                demandaAtributo.setRegDemandaRegd(regDemanda);
                /*
                 * Caso o tipo de campo seja texto considera-se que o Grupo de
                 * Atributos possuirï¿½ apenas 1 atributo que o representa.
                 */
                if (SisTipoExibicGrupoDao.TEXT.equals(grupoAtributo.getSisTipoExibicGrupoSteg().getCodSteg().toString())
                   	|| SisTipoExibicGrupoDao.VALIDACAO.equals(grupoAtributo.getSisTipoExibicGrupoSteg().getCodSteg().toString())
                   	|| SisTipoExibicGrupoDao.TEXTAREA.equals(grupoAtributo.getSisTipoExibicGrupoSteg().getCodSteg().toString())
                	|| SisTipoExibicGrupoDao.IMAGEM.equals(grupoAtributo.getSisTipoExibicGrupoSteg().getCodSteg().toString())) {
                    if (grupoAtributo.getSisAtributoSatbs() != null && grupoAtributo.getSisAtributoSatbs().size() > 0) {
                    	if(Pagina.getParamStr(request,"a" + grupoAtributo.getCodSga().toString()) ==null || "".equals(Pagina.getParamStr(request,"a" + grupoAtributo.getCodSga().toString()))){
                    		demandaAtributo.setInformacao(Pagina.getParamStr(request,"hidden_a" + grupoAtributo.getCodSga().toString()));
                    	} else{
                    		demandaAtributo.setInformacao(Pagina.getParamStr(request,"a" + grupoAtributo.getCodSga().toString()));
                    	}
                    	demandaAtributo.setSisAtributoSatb((SisAtributoSatb) grupoAtributo.getSisAtributoSatbs().iterator().next());
                    	demandaAtributo.setDataInclusao(Data.getDataAtual());
                        if (regDemanda.getDemAtributoDemas() == null)
                        	regDemanda.setDemAtributoDemas(new HashSet());
                        if (regDemanda.getDemAtributoDemas()!=null && !regDemanda.getDemAtributoDemas().contains(demandaAtributo)) {
                            regDemanda.getDemAtributoDemas().add(demandaAtributo);                        	
                        }

                        String pathRaiz = request.getContextPath();
                        
                        // tratamento imagem
    					String caminhoAuxiliarImagem = Pagina.getParamStr(request, "hidImagem" + "a"
    							+ grupoAtributo.getCodSga().toString());
    					if (caminhoAuxiliarImagem!=null && caminhoAuxiliarImagem.length()>0) {
    						
    						String chave = demandaAtributo.getInformacao();
    						chave = chave.substring(chave.indexOf("RemoteFile=")+ "RemoteFile=".length());
                            UsuarioUsu usuario = ((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario();
                            if(usuario.getMapArquivosAtuaisUsuarios().containsKey(chave)){
//                            	demandaAtributo.setInformacao(usuario.getMapArquivosAtuaisUsuarios().get(chave));
                            	
                            	caminhoAuxiliarImagem = usuario.getMapArquivosAtuaisUsuarios().get(chave);
                            	caminhoAuxiliarImagem = pathRaiz +"/DownloadFile?RemoteFile=" + caminhoAuxiliarImagem;
                            }                            	
//                            else{
                            
	    						// salvar a imagem fisicamente que tem o caminho real no campo "a" + codigo de grupo de atributo
	    						try {									
	    							String nomeArquivoNovo = FileUpload.salvarArquivoSessaoFisicamente(request, "a" + grupoAtributo.getCodSga().toString(), caminhoAuxiliarImagem);
	    							if(nomeArquivoNovo != null && !nomeArquivoNovo.equals(""))
	    								demandaAtributo.setInformacao(nomeArquivoNovo);
	    						} catch (FileNotFoundException e) {
	    							throw new ECARException("erro.arquivoUrl",e, new String[]{caminhoAuxiliarImagem});
	    						} catch (Exception e) {
	    							throw new ECARException("erro.upload",e, new String[]{caminhoAuxiliarImagem});
	    						}
//                            }
    					}

                    }
                } else {

                    String[] atributos = request.getParameterValues("a"
                            + grupoAtributo.getCodSga().toString());
                    
                    if(atributos == null || atributos.length == 0){
                    	atributos = request.getParameterValues("hidden_a"
                                + grupoAtributo.getCodSga().toString());
                    }
                    	
                    for (int i = 0; i < atributos.length; i++) {
                        /*
                         * Tenho que criar novamente o usuï¿½rio atributo senï¿½o
                         * ele nï¿½o ï¿½ adicionado no set no final deste laï¿½o
                         */
                    	if(!"".equals(atributos[i])){
	                    	demandaAtributo = new DemAtributoDema();
	                    	demandaAtributo.setRegDemandaRegd(regDemanda);
	                    	demandaAtributo.setSisAtributoSatb((SisAtributoSatb) super.buscar(SisAtributoSatb.class, Long.valueOf(atributos[i])));
	                    	demandaAtributo.setDataInclusao(Data.getDataAtual());
	                        if (regDemanda.getDemAtributoDemas() == null)
	                        	regDemanda.setDemAtributoDemas(new HashSet());
	                        if (regDemanda.getDemAtributoDemas()!=null && !regDemanda.getDemAtributoDemas().contains(demandaAtributo)) {
	                            regDemanda.getDemAtributoDemas().add(demandaAtributo);                        	
	                        }
                    	}
                    }
                }
            }
            /* Foi necessário alterar o nome dos campos dos elementos multitexto, adicionando "-codSatb"
             * Assim, ficamos com o nome do campo no seguinte padrão: "a + codSteg + _ + codSatb" (ex.: a12_38)
             * Isto foi feito visto a diferença existente entre um grupo com suporte a 1 campo texto
             * e este, que suporta vários campos texto.
             */
            else{ 
                if (SisTipoExibicGrupoDao.MULTITEXTO.equals(grupoAtributo.getSisTipoExibicGrupoSteg().getCodSteg().toString()))
                {
                	Enumeration lAtrib = request.getParameterNames();
                	while(lAtrib.hasMoreElements())
                	{
                		String atrib = (String)lAtrib.nextElement();
                		if (atrib.lastIndexOf('_') > 0)
                		{
                			String nomeAtrib = atrib.substring(0,atrib.lastIndexOf('_'));
                			String nomeCampo = atrib.substring(atrib.lastIndexOf('_')+1);
                			if ( (nomeAtrib.equals("a"+grupoAtributo.getCodSga().toString())
                		         && !"".equals(Pagina.getParamStr(request, atrib))) || (nomeAtrib.equals("hidden_a"+grupoAtributo.getCodSga().toString())
                        		         && !"".equals(Pagina.getParamStr(request, atrib))))
                			{
                				DemAtributoDema demandaAtributo = new DemAtributoDema();
                				demandaAtributo.setRegDemandaRegd(regDemanda);
                				demandaAtributo.setInformacao(Pagina.getParamStr(request, atrib));
                				demandaAtributo.setSisAtributoSatb((SisAtributoSatb) super.buscar(SisAtributoSatb.class, Long.valueOf(nomeCampo)));
                				demandaAtributo.setDataInclusao(Data.getDataAtual());
                                if (regDemanda.getDemAtributoDemas() == null)
                                	regDemanda.setDemAtributoDemas(new HashSet());
                                if (regDemanda.getDemAtributoDemas()!=null && !regDemanda.getDemAtributoDemas().contains(demandaAtributo)) {
                                    regDemanda.getDemAtributoDemas().add(demandaAtributo);                        	
                                }
                			}
                		}
                	}
                }            
            }
        }
    }
	
	
	/**
	 * Seta a lista de locais em RegDemandaRgd
	 * @param regDemanda
	 * @param request
	 * @throws ECARException
	 */
	public void setLocais (RegDemandaRegd regDemanda, HttpServletRequest request)
				throws ECARException {
		int numLocais = -1;
		if(request.getParameter("contLocal")!=null){
			numLocais = Integer.parseInt(request.getParameter("contLocal"));
			regDemanda.setLocalDemandaLdems(new HashSet());
		}		
		if (numLocais > 0){
			for (int i = 1; i <= numLocais; i++) {
				if ("S".equals(Pagina.getParamStr(request, "adicionaLocal" + i))) {
					LocalItemLit local = new LocalItemLit();
					local = (LocalItemLit) super
							.buscar(LocalItemLit.class, Long.valueOf(Pagina
								.getParamStr(request, "codLit"+ i)));
					if (regDemanda.getLocalDemandaLdems()!=null && !regDemanda.getLocalDemandaLdems().contains(local)) {
						regDemanda.getLocalDemandaLdems().add(local);						
					}
				}
			}
		}
		
	}
	
	/**
	 * Seta a lista de entidades em RegDemandaRgd
	 * @param regDemanda
	 * @param request
	 * @throws ECARException
	 */
	public void setEntidades (RegDemandaRegd regDemanda, HttpServletRequest request)
				throws ECARException {
		int numEntidades = -1;
		if(request.getParameter("contEntidade")!=null){
			numEntidades = Integer.parseInt(request.getParameter("contEntidade"));
			regDemanda.setEntidadeDemandaEntds(new HashSet());
		}
		if (numEntidades > 0){
			for (int i = 1; i <= numEntidades; i++) {
				if ("S".equals(Pagina.getParamStr(request, "adicionaEntidade" + i))) {
					EntidadeEnt entidade = new EntidadeEnt();
					entidade = (EntidadeEnt) super.buscar(EntidadeEnt.class,
								Long.valueOf(Pagina.getParamStr(request, "codEnt"+ i)));
					if (regDemanda.getEntidadeDemandaEntds()!=null && !regDemanda.getEntidadeDemandaEntds().contains(entidade)) {
						regDemanda.getEntidadeDemandaEntds().add(entidade);
					}
				}
			}
		}
	}
	
	/**
	 * Seta a lista de orgãos (que também são entidades) em RegDemandaRgd
	 * @param regDemanda
	 * @param request
	 * @throws ECARException
	 */
	public void setEntidadeOrgaos (RegDemandaRegd regDemanda, HttpServletRequest request)
				throws ECARException {
		int numEntidadeOrgaos = -1;
		if(request.getParameter("contEntidadeOrgao")!=null){
			numEntidadeOrgaos = Integer.parseInt(request.getParameter("contEntidadeOrgao"));
			regDemanda.setEntidadeOrgaoDemandaEntorgds(new HashSet());
		}
		if (numEntidadeOrgaos > 0){
			for (int i = 1; i <= numEntidadeOrgaos; i++) {
				if ("S".equals(Pagina.getParamStr(request, "adicionaEntidadeOrgao" + i))) {
					EntidadeEnt entidadeOrgao = new EntidadeEnt();
					entidadeOrgao = (EntidadeEnt) super.buscar(EntidadeEnt.class,
								Long.valueOf(Pagina.getParamStr(request, "codEntOrg"+ i)));
					if (regDemanda.getEntidadeOrgaoDemandaEntorgds()!=null && !regDemanda.getEntidadeOrgaoDemandaEntorgds().contains(entidadeOrgao))
						regDemanda.getEntidadeOrgaoDemandaEntorgds().add(entidadeOrgao);
				}
			}
		}
		
	}
	
	/**
	 * Verifica a permissão do usuário logado para realizar alteração
	 * e/ou classificação no RegDemandaRegd.
	 * @param regDemanda
	 * @param request
	 * @return
	 */
	public boolean validaUsuarioAltExc (RegDemandaRegd regDemanda, HttpServletRequest request) {
		HttpSession session = request.getSession();
		boolean retorno = false;

		try{
			DemandasGrpAcessoDao demandasGrpAcessoDao = new DemandasGrpAcessoDao();
			DemandasGrpAcesso demandasGrpAcesso = null;
			SegurancaECAR seguranca = ((ecar.login.SegurancaECAR) session.getAttribute("seguranca"));			
			  
			if(seguranca != null){
				Iterator gruposAcessoIt = seguranca.getGruposAcesso().iterator();
				while (gruposAcessoIt.hasNext()) {
					SisAtributoSatb grpAcesso = (SisAtributoSatb)gruposAcessoIt.next();
					demandasGrpAcesso = (DemandasGrpAcesso) demandasGrpAcessoDao.getDemandasGrpAcesso(grpAcesso);
					if(demandasGrpAcesso != null && demandasGrpAcesso.getAcessoDemanda().equals(Dominios.SIM)) {
						break;
					} else {
						demandasGrpAcesso = null;
					}
				}
				// se é administrador de demandas
				if(demandasGrpAcesso != null){
					retorno = true;
				}
				// se não é administrador de demandas, 
				// verifica se a demanda foi cadastrada por algum usuario que pertenï¿½a a seu grupo de acesso
				else {
					Set gruposAcessoUsuarioLogado = seguranca.getGruposAcesso();
					Iterator gruposAcessoUsuarioInclusaoIt;
					gruposAcessoUsuarioInclusaoIt = regDemanda.getUsuarioUsuByCodUsuInclusaoRegd().getUsuarioAtributoUsuas().iterator();										
					while (gruposAcessoUsuarioInclusaoIt.hasNext()) {
						SisAtributoSatb grpAcesso = ((UsuarioAtributoUsua) gruposAcessoUsuarioInclusaoIt.next()).getSisAtributoSatb();
						if(gruposAcessoUsuarioLogado.contains(grpAcesso)){
							retorno = true;
							break;
						}
					}					
				}							
			}
			
		} catch (Exception e) {
			// Não precisa levantar exceção aqui.
		}				
		
		return retorno;
	}
	
	/**
	 * Salva 
	 * 
	 * @param regDemanda
	 * @throws ECARException
	 */
	public void salvar(RegDemandaRegd regDemanda) throws ECARException {
		
		regDemanda.setUsuarioUsuByCodUsuInclusaoRegd(((ecar.login.SegurancaECAR) request.getSession().getAttribute("seguranca")).getUsuario());
		regDemanda.setDataInclusaoRegd(Data.getDataAtual());
		regDemanda.setDataSituacaoRegd(Data.getDataAtual());
		if (regDemanda.getSitDemandaSitd() == null){
			//setar a situacao
			SitDemandaSitd situacao = new SitDemandaSitd();
			situacao.setIndPrimeiraSituacaoSitd("S");
			SitDemandaDao situacaoDao = new SitDemandaDao(request);
			List listaSit = situacaoDao.pesquisar(situacao, new String[] {"codSitd", "asc"});
			
			if (listaSit != null && listaSit.size() > 0) {
				regDemanda.setSitDemandaSitd((SitDemandaSitd) listaSit.iterator().next());
			} else {
				throw new ECARException("registroDemanda.inclusao.erro.sitDemandaSitd");
			}
		}
		
		List filhos = new ArrayList();
		if (regDemanda.getDemAtributoDemas() != null)
			filhos.addAll(regDemanda.getDemAtributoDemas());
		
		super.salvar(regDemanda, filhos);
		//super.salvar(regDemanda);
    }  
	
	/**
	 * Alterar RegDemandaRegd
	 * @param regDemanda
	 * @param request
	 * @throws Exception 
	 */
	public void alterar(RegDemandaRegd regDemanda, HttpServletRequest request) 
				throws Exception {
		Transaction tx = null;
		Long codVisao = ((VisaoDemandasVisDem)request.getSession().getAttribute(VisaoDemandasVisDem.VISAO_SELECIONADA)).getCodVisao();
		AtributoDemandaDao atributoDemandaDao = new AtributoDemandaDao(request);
		try {
			List atributosLivresVisao = atributoDemandaDao.getAtributosDemandaVisaoAtivosOrdenadosPorSequenciaTelaCampo(codVisao);
			List demandaAtributoNaoConfiguradosVisao = new ArrayList();
			List objetos = new ArrayList();
			super.inicializarLogBean();
			tx = session.beginTransaction();
			
			if (regDemanda.getDemAtributoDemas() != null) {
				Iterator itAtt = regDemanda.getDemAtributoDemas().iterator();
				while (itAtt.hasNext()) {
					DemAtributoDema demandaAtributo = (DemAtributoDema) itAtt.next();
					Iterator itAtributosLivresVisao = atributosLivresVisao.iterator();
					boolean achouAtributoLivre = false;
					while (itAtributosLivresVisao.hasNext()){
						AtributoDemandaAtbdem atributoDemandaAtbdem = (AtributoDemandaAtbdem) itAtributosLivresVisao.next();
						if (atributoDemandaAtbdem.getSisGrupoAtributoSga() != null){
							if (atributoDemandaAtbdem.getSisGrupoAtributoSga().equals(demandaAtributo.getSisAtributoSatb().getSisGrupoAtributoSga())){
								achouAtributoLivre = true;
								break;
							}
						}
					}
					if (!achouAtributoLivre){
						demandaAtributoNaoConfiguradosVisao.add(demandaAtributo);
					}
					
                    if(demandaAtributo.getSisAtributoSatb().getSisGrupoAtributoSga().getSisTipoExibicGrupoSteg().getCodSteg() == Input.IMAGEM){
                    	
                    	String nomeCampo = request.getParameter("a" + demandaAtributo.getSisAtributoSatb().getSisGrupoAtributoSga().getCodSga().toString());
                    	
                    	if (nomeCampo != null && nomeCampo.equals("")){
                    		
	    		    		String fullFile = demandaAtributo.getInformacao();
	    		    		
	    		    		if (fullFile.lastIndexOf("=") != -1)     
	    		    			fullFile = fullFile.substring(fullFile.lastIndexOf("=") + 1);
    		    		
	    		    		File f = new File(fullFile);
	    		    		if( f.exists() ) 
	    		    			FileUpload.apagarArquivo(fullFile);
                    	}
                    }
					
					session.delete(demandaAtributo);
					objetos.add(demandaAtributo);
				}
			}
			
			// setar valores vindos do request
			this.setRegDemanda(regDemanda, request);
			
			if (!demandaAtributoNaoConfiguradosVisao.isEmpty()){
				if (regDemanda.getDemAtributoDemas() == null){
					regDemanda.setDemAtributoDemas(new HashSet());
				}
				
				regDemanda.getDemAtributoDemas().addAll(demandaAtributoNaoConfiguradosVisao);
			}
						
			// guarda a lista de demandaAtributo vindo do request
			List filhos = new ArrayList();
			if (regDemanda.getDemAtributoDemas() != null)
				filhos.addAll(regDemanda.getDemAtributoDemas());
			
			// coloca a data da alteração
			regDemanda.setDataAlteracaoRegd(Data.getDataAtual());
			regDemanda.setUsuarioUsuByCodUsuAlteracaoRegd(((ecar.login.SegurancaECAR) request.getSession().getAttribute("seguranca")).getUsuario());
			// salva regDemanda
			session.update(regDemanda);
			objetos.add(regDemanda);
			
			// salva a lista de demandaAtributo
			Iterator it = filhos.iterator();
            while(it.hasNext()){                
				PaiFilho object = (PaiFilho) it.next();
				object.atribuirPKPai();
				//salva os filhos
				session.save(object);                  
				objetos.add(object);
            }
		
			tx.commit();
		
			if (super.logBean != null) {
				super.logBean.setCodigoTransacao(Data.getHoraAtual(false));
				super.logBean.setOperacao("INC_ALT_EXC");
				Iterator itObj = objetos.iterator();
		
				while (itObj.hasNext()) {
					super.logBean.setObj(itObj.next());
					super.loggerAuditoria.info(logBean.toString());
				}
			}
		
		} catch (HibernateException e) {
			if (tx != null)
				try {
					tx.rollback();
				} catch (HibernateException r) {
					this.logger.error(r);
					throw new ECARException("erro.hibernateException");
				}
			this.logger.error(e);
			throw new ECARException("erro.hibernateException");
		} catch (ECARException e) {
			this.logger.error(e);
			if (tx != null)
				try {
					tx.rollback();
				} catch (HibernateException r) {
					this.logger.error(r);
					throw new ECARException("erro.hibernateException");
				}
			throw e;
		}
	}

	/**
	 * Ordena a lista conforme os parâmetros passados.
	 * Esse método é mais genérico que o "classificarOrdenacao()" e
	 * pode ser aplicado a todos atributos da demanda. 
	 * 		codCampo : código do campo(chave primária do BD)
	 * 		ordemCampo : asc ou desc.
	 * @param codCampo - Campo pelo qual a lista será ordenada.
	 * @param ordemCampo - Ordem pela qual a lista será ordenada.
	 * @param lista - lista de demandas que será ordenada.
	 */
	@SuppressWarnings({"unchecked", "empty-statement"})
	public void ordenacaoDemandas (Long codCampo, String ordemCampo, List lista) {
		
		try {
			AtributoDemandaDao atributoDemandaDao = new AtributoDemandaDao(null);
			
			final AtributoDemandaAtbdem atributoDemanda = (AtributoDemandaAtbdem) atributoDemandaDao.buscar(AtributoDemandaAtbdem.class, codCampo);;			 
			final String ordenacaoCampo = ordemCampo;
			
			if(lista != null && lista.size()>1 && atributoDemanda!=null){		
				Collections.sort(lista,
	    	            new Comparator() {
	    	        		public int compare(Object o1, Object o2){		        		    	        		     	        			
	    	        			//Por default retorna que são iguais "retorno=0"
	    	        			int retorno=0;
	    	        			RegDemandaRegd demanda1 = (RegDemandaRegd) o1;
	    	        			RegDemandaRegd demanda2 = (RegDemandaRegd) o2;
	    	        			try{	    	        				
	    	        				
	    	        				//Caso sejam numéros fixos: "codRegd" e "numeroDocOrigemRegd"
	    	        				if(atributoDemanda.iGetNome().equals("codRegd") || atributoDemanda.iGetNome().equals("numeroDocOrigemRegd")){		    	        					
	    	        					
	    	        					String valorStringDemanda1 = atributoDemanda.iGetValor(demanda1);
	    	        					String valorStringDemanda2 = atributoDemanda.iGetValor(demanda2);
	    	        					
	    	        					if(valorStringDemanda1.equals("") && valorStringDemanda2.equals("")){
	    	        						retorno = 0;
	    	        					}
	    	        					else if(!valorStringDemanda1.equals("") && valorStringDemanda2.equals("")){
	    	        						retorno = 1;
	    	        					}
	    	        					else if(valorStringDemanda1.equals("") && !valorStringDemanda2.equals("")){
	    	        						retorno = -1;
	    	        					}	    	        					
	    	        					else{
		    	        					Integer valorInteiroDemanda1 = new Integer(Integer.parseInt(valorStringDemanda1)) ;
		    	        					Integer valorInteiroDemanda2 = new Integer(Integer.parseInt(valorStringDemanda2));
		    	        					retorno = valorInteiroDemanda1.compareTo(valorInteiroDemanda2);	    	        						
	    	        					}
	    	        				}
	    	        				
	    	        				//Caso sejam datas fixas
	    	        				else if(atributoDemanda.iGetNome().equals("dataLimiteRegd") || atributoDemanda.iGetNome().equals("dataSolicitacaoRegd")
	    	        						|| atributoDemanda.iGetNome().equals("dataInclusaoRegd") || atributoDemanda.iGetNome().equals("dataAlteracaoRegd")
	    	        						|| atributoDemanda.iGetNome().equals("dataSituacaoRegd")){

	    	        					
	    	        					String dataDem1 = atributoDemanda.iGetValor(demanda1);
	    	        					String dataDem2 = atributoDemanda.iGetValor(demanda2);
	    	        					
	    	        					if(dataDem1.equals("") && dataDem2.equals("")){
	    	        						retorno = 0;
	    	        					}
	    	        					else if(!dataDem1.equals("") && dataDem2.equals("")){
	    	        						retorno = 1;
	    	        					}
	    	        					else if(dataDem1.equals("") && !dataDem2.equals("")){
	    	        						retorno = -1;
	    	        					}	    	        					
	    	        					else{
		    	        					//Data no formato dd/mm/aaaa
		    	        					int diaDem1 , mesDem1, anoDem1;
		    	        					diaDem1 = Integer.parseInt(dataDem1.trim().substring(0, 2));
		    	        					mesDem1 = Integer.parseInt(dataDem1.trim().substring(3, 5));
		    	        					anoDem1 = Integer.parseInt(dataDem1.trim().substring(6, 10));	
		        					
		    	        					int diaDem2, mesDem2, anoDem2;
		    	        					diaDem2 = Integer.parseInt(dataDem2.trim().substring(0, 2));
		    	        					mesDem2 = Integer.parseInt(dataDem2.trim().substring(3, 5));
		    	        					anoDem2 = Integer.parseInt(dataDem2.trim().substring(6, 10));	
		    	        					
		    	        					GregorianCalendar valorDataDemanda1 = new GregorianCalendar(anoDem1, mesDem1, diaDem1);
		    	        					GregorianCalendar valorDataDemanda2 = new GregorianCalendar(anoDem2, mesDem2, diaDem2);
		    	        					
		    	        					retorno = valorDataDemanda1.compareTo(valorDataDemanda2);	    	        						
	    	        					}
	    	        						    	        					
	    	        				}

	    	        				//Caso seja atributo livre de validação
	    	        				else if(atributoDemanda.iGetGrupoAtributosLivres() != null 
	    	        						&& atributoDemanda.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg()
	    	        						.getCodSteg().equals(new Long(Input.VALIDACAO))){
	    	        					
	    	        					//Busca o valor da demanda1
	    	        					Iterator itRegDem1 =  demanda1.getDemAtributoDemas().iterator();
	    	        					String informacaoDemanda1Atbdem = "";
	    	        					DemAtributoDema demAtributoDemanda1Dema = null;
	    								while (itRegDem1.hasNext()) {
	    									demAtributoDemanda1Dema = (DemAtributoDema) itRegDem1.next();		    									
	    									if (demAtributoDemanda1Dema.getSisAtributoSatb().getSisGrupoAtributoSga().equals(atributoDemanda.iGetGrupoAtributosLivres())){
	    											informacaoDemanda1Atbdem = demAtributoDemanda1Dema.getInformacao();
	    											break;
	    									}
	    									demAtributoDemanda1Dema = null;
	    								}

	    	        					//Busca o valor da demanda2
	    	        					Iterator itRegDem2 =  demanda2.getDemAtributoDemas().iterator();
	    	        					String informacaoDemanda2Atbdem = "";
	    	        					DemAtributoDema demAtributoDemanda2Dema = null;
	    								while (itRegDem2.hasNext()) {
	    									demAtributoDemanda2Dema = (DemAtributoDema) itRegDem2.next();		    									
	    									if (demAtributoDemanda2Dema.getSisAtributoSatb().getSisGrupoAtributoSga().equals(atributoDemanda.iGetGrupoAtributosLivres())){		    										 
	    											informacaoDemanda2Atbdem = demAtributoDemanda2Dema.getInformacao();
	    											break;
	    									}
	    									demAtributoDemanda2Dema = null;
	    								}
	    								
	    								//Começa o processo de comparação da ordenação
	    								if(demAtributoDemanda1Dema==null && demAtributoDemanda2Dema==null){
	    									retorno = 0;
	    								}	    								
	    								else if(demAtributoDemanda1Dema!=null && demAtributoDemanda2Dema==null){
	    									retorno = 1;
	    								}	    								
	    								else if(demAtributoDemanda1Dema==null && demAtributoDemanda2Dema!=null){
	    									retorno = -1;
	    								}	    								
	    								else{
	    									if(demAtributoDemanda1Dema.getSisAtributoSatb().getAtribInfCompSatb().equals("dataScript")){
			    	        					
	    										//Data no formato dd/mm/aaaa
			    	        					String dataDem1 = informacaoDemanda1Atbdem;
			    	        					int diaDem1 , mesDem1, anoDem1;
			    	        					diaDem1 = Integer.parseInt(dataDem1.trim().substring(0, 2));
			    	        					mesDem1 = Integer.parseInt(dataDem1.trim().substring(3, 5));
			    	        					anoDem1 = Integer.parseInt(dataDem1.trim().substring(6, 10));	
	    	        					
			    	        					String dataDem2 = informacaoDemanda2Atbdem;
			    	        					int diaDem2, mesDem2, anoDem2;
			    	        					diaDem2 = Integer.parseInt(dataDem2.trim().substring(0, 2));
			    	        					mesDem2 = Integer.parseInt(dataDem2.trim().substring(3, 5));
			    	        					anoDem2 = Integer.parseInt(dataDem2.trim().substring(6, 10));	
			    	        					
			    	        					GregorianCalendar valorDataDemanda1 = new GregorianCalendar(anoDem1, mesDem1, diaDem1);
			    	        					GregorianCalendar valorDataDemanda2 = new GregorianCalendar(anoDem2, mesDem2, diaDem2);
			    	        					
			    	        					retorno = valorDataDemanda1.compareTo(valorDataDemanda2);
			    	        					
	    									}
	    									
	    									else if(demAtributoDemanda1Dema.getSisAtributoSatb().getAtribInfCompSatb().equals("numeroInteiroScript")){
	    										
			    	        					Integer valorInteiroDemanda1 = new Integer(Integer.parseInt(informacaoDemanda1Atbdem)) ;
			    	        					Integer valorInteiroDemanda2 = new Integer(Integer.parseInt(informacaoDemanda2Atbdem));
			    	        					retorno = valorInteiroDemanda1.compareTo(valorInteiroDemanda2);
			    	        					
	    									}
	    									
	    									else if(demAtributoDemanda1Dema.getSisAtributoSatb().getAtribInfCompSatb().equals("numeroRealScript")
	    											|| demAtributoDemanda1Dema.getSisAtributoSatb().getAtribInfCompSatb().equals("valorMonetarioScript")){

	    										DecimalFormat dff = (DecimalFormat) DecimalFormat.getInstance();	    										
	    										Double valorRealDemanda1 = new Double(dff.parse(informacaoDemanda1Atbdem).doubleValue()) ;
	    										Double valorRealDemanda2 = new Double(dff.parse(informacaoDemanda2Atbdem).doubleValue());
			    	        					retorno = valorRealDemanda1.compareTo(valorRealDemanda2);
	    										
	    									}
	    										    									
	    									else{
	    										retorno = informacaoDemanda1Atbdem.compareToIgnoreCase(informacaoDemanda2Atbdem);
	    									}
	    								}
	    								
	    	        				}

	    	        				//Caso seja atributo livre diferente de validação
	    	        				else if(atributoDemanda.iGetGrupoAtributosLivres() != null){
	    								
	    	        					//Busca o valor da demanda1
	    	        					Iterator itRegDem1 =  demanda1.getDemAtributoDemas().iterator();
	    	        					String informacaoDemanda1Atbdem = "";
	    								while (itRegDem1.hasNext()) {
	    									DemAtributoDema demAtributoDema = (DemAtributoDema) itRegDem1.next();
	    									
	    									if (demAtributoDema.getSisAtributoSatb().getSisGrupoAtributoSga().equals(atributoDemanda.iGetGrupoAtributosLivres())){
	    										if (atributoDemanda.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.TEXT)) ||
	    											atributoDemanda.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.TEXTAREA)) ||
	    											atributoDemanda.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.MULTITEXTO)) ) {
	    										 
	    											informacaoDemanda1Atbdem = informacaoDemanda1Atbdem + demAtributoDema.getInformacao() +  "; ";
	    										
	    										} else if (!atributoDemanda.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.IMAGEM))) {
	    											//se for do tipo imagem não faz nada, deixa em branco.
	    											informacaoDemanda1Atbdem = informacaoDemanda1Atbdem + demAtributoDema.getSisAtributoSatb().getDescricaoSatb() +  "; ";
	    										} 
	    									} 
	    								}
	    								if (informacaoDemanda1Atbdem.length() > 0){
	    									informacaoDemanda1Atbdem = informacaoDemanda1Atbdem.substring(0, informacaoDemanda1Atbdem.length() - 2); 
	    								}		    	        					

	    	        					//Busca o valor da demanda2
	    	        					Iterator itRegDem2 =  demanda2.getDemAtributoDemas().iterator();
	    	        					String informacaoDemanda2Atbdem = "";
	    								while (itRegDem2.hasNext()) {
	    									DemAtributoDema demAtributoDema = (DemAtributoDema) itRegDem2.next();
	    									
	    									if (demAtributoDema.getSisAtributoSatb().getSisGrupoAtributoSga().equals(atributoDemanda.iGetGrupoAtributosLivres())){
	    										if (atributoDemanda.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.TEXT)) ||
	    											atributoDemanda.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.TEXTAREA)) ||
	    											atributoDemanda.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.MULTITEXTO)) ) {
	    										 
	    											informacaoDemanda2Atbdem = informacaoDemanda2Atbdem + demAtributoDema.getInformacao() +  "; ";
	    										
	    										} else if (!atributoDemanda.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.IMAGEM))) {
	    											//se for do tipo imagem não faz nada, deixa em branco.
	    											informacaoDemanda2Atbdem = informacaoDemanda2Atbdem + demAtributoDema.getSisAtributoSatb().getDescricaoSatb() +  "; ";
	    										} 
	    									} 
	    								}
	    								if (informacaoDemanda2Atbdem.length() > 0){
	    									informacaoDemanda2Atbdem = informacaoDemanda2Atbdem.substring(0, informacaoDemanda2Atbdem.length() - 2); 
	    								}		    	        					

	    								//Faz a comparação String dos valores 
	    								retorno = informacaoDemanda1Atbdem.compareToIgnoreCase(informacaoDemanda2Atbdem);
	    	        				}
	    	        				
	    	        				//Caso geral
	    	        				else {
	    	        					retorno = atributoDemanda.iGetValor(demanda1).compareToIgnoreCase(atributoDemanda.iGetValor(demanda2));
	    	        				} 	
	    	        				
	    	        			} catch (Exception e){
	    	        				//Não faz nada, deixa o valor como foi setado inicialmente, "zero".
	    	        			}
	    	        					    	        			
	    	        			//Verifica se é crescente ou decrescente
	    	        			if(!ordenacaoCampo.equals("asc")){
	    	        				retorno = - retorno;
	    	        			}
	    	        			
	    	        			return retorno;	    	        			
	    					}
	    	   			}
	    		);					
	    			    						
			}						
				
		} catch (Exception e){
			//Em caso de exceção a lista permanece a mesma!
		}		
	}
	
	/**
	 * Classifica e ordena a lista conforme os parï¿½metros passados.
	 * 		clCampo : descricaoRegd, codRegd, dataSolicitacaoRegd, 
	 * 					entidadeEnt ou sitDemandaSitd.
	 * 		clOrden : asc ou desc.
	 * @param clCampo - Campo pelo qual a lista serï¿½ ordenada.
	 * @param clOrdem - Ordem pela qual a lista serï¿½ ordenada.
	 * @param lista - lista que serï¿½ ordenada.
	 */
	public void classificarOrdenacao (String clCampo, String clOrdem, List lista) {
		//testar cada string para realizar a ordenaï¿½ï¿½o
		if ("descricaoRegd".equals(clCampo)) {
			if ("asc".equals(clOrdem)) {
				Collections.sort(lista,
					new Comparator() {
		        		public int compare(Object o1, Object o2) {
		        		    return ( (RegDemandaRegd) o1 ).getDescricaoRegd().compareToIgnoreCase(( (RegDemandaRegd) o2 ).getDescricaoRegd());
		        		}
		    		} );
    		} else {
    			Collections.sort(lista,
					new Comparator() {
		        		public int compare(Object o1, Object o2) {
		        		    return -( (RegDemandaRegd) o1 ).getDescricaoRegd().compareToIgnoreCase(( (RegDemandaRegd) o2 ).getDescricaoRegd());
		        		}
		    		} );
    		}
		}
		if ("codRegd".equals(clCampo)) {
			if ("asc".equals(clOrdem)) {
				Collections.sort(lista,
					new Comparator() {
		        		public int compare(Object o1, Object o2) {
		        		    return ( (RegDemandaRegd) o1).getCodRegd().intValue() - ( (RegDemandaRegd) o2).getCodRegd().intValue();
		        		}
		    		} );
    		} else {
    			Collections.sort(lista,
					new Comparator() {
		        		public int compare(Object o1, Object o2) {
		        		    return -(( (RegDemandaRegd) o1).getCodRegd().intValue() - ( (RegDemandaRegd) o2).getCodRegd().intValue());
		        		}
		    		} );
    		}
		}
		if ("dataSolicitacaoRegd".equals(clCampo)) {
			if ("asc".equals(clOrdem)) {
				Collections.sort(lista,
					new Comparator() {
		        		public int compare(Object o1, Object o2) {
		        			RegDemandaRegd regD1 = (RegDemandaRegd) o1;
		        			RegDemandaRegd regD2 = (RegDemandaRegd) o2;
		        			//Quando a data estï¿½ nula, foi utilizado um artifï¿½cio para nï¿½o ocorrer Exception
		        			if (regD1.getDataSolicitacaoRegd() != null) {
		        				if (regD2.getDataSolicitacaoRegd() != null) {
		        					return regD1.getDataSolicitacaoRegd().compareTo( regD2.getDataSolicitacaoRegd() );
		        				} else {
		        					return "a".compareTo("");
		        				}
		        			} else {
		        				if (regD2.getDataSolicitacaoRegd() != null) {
		        					return "".compareTo("a");
		        				} else {
		        					return "".compareTo("");
		        				}
		        			}
		        		}
		    		} );
    		} else {
    			Collections.sort(lista,
					new Comparator() {
		        		public int compare(Object o1, Object o2) {
		        			RegDemandaRegd regD1 = (RegDemandaRegd) o1;
		        			RegDemandaRegd regD2 = (RegDemandaRegd) o2;
		        			//Quando a data estï¿½ nula, foi utilizado um artifï¿½cio para nï¿½o ocorrer Exception
		        			if (regD1.getDataSolicitacaoRegd() != null) {
		        				if (regD2.getDataSolicitacaoRegd() != null) {
		        					return -(regD1.getDataSolicitacaoRegd().compareTo( regD2.getDataSolicitacaoRegd() ));
		        				} else {
		        					return -("a".compareTo(""));
		        				}
		        			} else {
		        				if (regD2.getDataSolicitacaoRegd() != null) {
		        					return -("".compareTo("a"));
		        				} else {
		        					return -("".compareTo(""));
		        				}
		        			}
		        		}
		    		} );
    		}
		}
		if ("entidadeEnt".equals(clCampo)) {
			/* Mapeamento com Entidade foi alterado
			 * nï¿½o estï¿½ sendo feita classificaï¿½ï¿½o por Entidade.
			if ("asc".equals(clOrdem)) {
				Collections.sort(lista,
					new Comparator() {
		        		public int compare(Object o1, Object o2) {
		        			return ( (RegDemandaRegd) o1 ).getEntidadeEnt().getNomeEnt().compareToIgnoreCase(( (RegDemandaRegd) o2 ).getEntidadeEnt().getNomeEnt());
		        		}
		    		} );
    		} else {
    			Collections.sort(lista,
					new Comparator() {
		        		public int compare(Object o1, Object o2) {
		        			return -( (RegDemandaRegd) o1 ).getEntidadeEnt().getNomeEnt().compareToIgnoreCase(( (RegDemandaRegd) o2 ).getEntidadeEnt().getNomeEnt());
		        		}
		    		} );
    		}*/
		}
		if ("sitDemandaSitd".equals(clCampo)) {
			if ("asc".equals(clOrdem)) {
				Collections.sort(lista,
					new Comparator() {
		        		public int compare(Object o1, Object o2) {
		        		    return ( (RegDemandaRegd) o1 ).getSitDemandaSitd().getDescricaoSitd().compareToIgnoreCase(( (RegDemandaRegd) o2 ).getSitDemandaSitd().getDescricaoSitd());
		        		}
		    		} );
    		} else {
    			Collections.sort(lista,
					new Comparator() {
		        		public int compare(Object o1, Object o2) {
		        		    return -( (RegDemandaRegd) o1 ).getSitDemandaSitd().getDescricaoSitd().compareToIgnoreCase(( (RegDemandaRegd) o2 ).getSitDemandaSitd().getDescricaoSitd());
		        		}
		    		} );
    		}
		}
		
		//evitar problema de lazy
		Iterator itRes = lista.iterator();
		while (itRes.hasNext()) {
			RegDemandaRegd regDemandaResul = (RegDemandaRegd) itRes.next();
			regDemandaResul.getEntidadeDemandaEntds().size();
		}
	}
	
	/**
	 * Exclui
	 * @param codigosParaExcluir
         * @throws ECARException
         * @throws Exception
	 */
	public void excluir(String[] codigosParaExcluir) throws ECARException, Exception {
		ConfiguracaoCfg configura = new ConfiguracaoDao(request).getConfiguracao();
		
		String pathRaiz = configura.getRaizUpload();
		String pathAnexo = configura.getUploadAnexos();
		
		try {
			List exclusao = new ArrayList();
		
	        for (int i = 0; i < codigosParaExcluir.length; i++) {
	            RegDemandaRegd regDemanda = (RegDemandaRegd) buscar(RegDemandaRegd.class, Long.valueOf(codigosParaExcluir[i]));
	            
	            if (regDemanda.getRegDemandaRegds() == null || regDemanda.getRegDemandaRegds().size() == 0){
	            	
	            	if (regDemanda.getAnexos() != null && regDemanda.getAnexos().size() > 0){
	            		Iterator it = regDemanda.getAnexos().iterator();
	            		while (it.hasNext()){
	            			RegDemandaAnexoRegdan regDemandaAnexoRegdan = (RegDemandaAnexoRegdan) it.next();
	            			FileUpload.apagarArquivo(FileUpload.getPathFisicoCompleto(pathRaiz, pathAnexo, regDemandaAnexoRegdan.getSrcAnexo()));
	            			exclusao.add(regDemandaAnexoRegdan);
	            		}
	            		 
	            	}
	            
		            if (regDemanda.getRegApontamentoRegdas() != null 
		            			&& regDemanda.getRegApontamentoRegdas().size() > 0) {
		            	Iterator it = regDemanda.getRegApontamentoRegdas().iterator();
		            	while (it.hasNext()) {
		            		RegApontamentoRegda regAp = (RegApontamentoRegda) it.next();
		            		exclusao.add(regAp);
		            	}
		            }
		            
		            if (regDemanda.getDemAtributoDemas() != null) {
		                Iterator itAtb = regDemanda.getDemAtributoDemas().iterator();
		                while (itAtb.hasNext()) {
		                	DemAtributoDema demAtrib = (DemAtributoDema) itAtb.next();
		                	exclusao.add(demAtrib);
		                }
		            }
		            
		            exclusao.add(regDemanda);
	            }
	            else{
	            	
	            	throw new ECARException("registroDemanda.exclusao.erroDemandaRelacionada");
	            }
	        }
	        
	        if (exclusao.size() > 0)
	        	super.excluir(exclusao);
	    
		} catch (ECARException e) {
			this.logger.error(e);
			throw e;
		
		} catch (Exception e) {
			this.logger.error(e);
			throw e;
		}
    }
	

	
	/**
	 * Pesquisa RegDemandaRegd
         * @param num_registro
         * @param descricao
         * @param observacao
         * @param num_doc_origem
         * @param request
         * @param usu_sol
         * @param limiteInicial
	 * @param limiteFinal
	 * @param solicitacaoInicial			
         * @param nome_sigla
         * @param solicitacaoFinal
         * @param codLocais
         * @param codPrior
         * @param codSitd
         * @param codOrg
         * @param entidade
         * @param formasContato
         * @return
         * @throws Exception
	 */
	public List pesquisar(long num_registro, String descricao, String observacao, int num_doc_origem, String usu_sol, String nome_sigla, 
			Date limiteInicial, Date limiteFinal, Date solicitacaoInicial, Date solicitacaoFinal, String codLocais, 
			long codPrior, long codSitd, long codOrg, EntidadeEnt entidade, String formasContato, HttpServletRequest request)	
				throws Exception {
		
		String join = "";
		String where = "";
		
		List atributosEntidade = new ArrayList();
		
		if ((limiteInicial!=null && limiteFinal!=null) || (solicitacaoInicial!=null && solicitacaoFinal!=null)) {
			where += " where ";
			
			if (limiteInicial != null && limiteFinal != null)
				where += " (i.dataLimiteRegd >= :limiteInicial and i.dataLimiteRegd <= :limiteFinal)";
			
			if (solicitacaoInicial != null && solicitacaoFinal != null){
				if(!" where ".equals(where))
					where += " or ";
				
				where += " (i.dataSolicitacaoRegd >= :solicitacaoInicial and i.dataSolicitacaoRegd <= :solicitacaoInicial)";
			}
		}		
		
		if ((num_registro > 0)||(!"".equals(descricao))||(!"".equals(observacao))||(num_doc_origem > 0)||(!"".equals(usu_sol))||
				(!"".equals(nome_sigla))||(codPrior > 0)||(codSitd > 0)||(codOrg > 0)||
				(entidade.getEntidadeAtributoEntas() != null && entidade.getEntidadeAtributoEntas().size() > 0)) {
			
			if ("".equals(where))	where += " where "; 
						
			if (num_registro > 0)
				if(" where ".equals(where))	where += " i.codRegd = :num_registro ";
				else where += " and i.codRegd = :num_registro ";			
			if (!"".equals(descricao))
				if("where".equals(where))	where += " upper( i.descricaoRegd ) like :descricao ";
				else where += " and upper( i.descricaoRegd ) like :descricao ";			
			if (!"".equals(observacao))
				if("where".equals(where))	where += " upper( i.observacaoRegd ) like :observacao ";
				else where += " and upper( i.observacaoRegd ) like :observacao ";			
			if (num_doc_origem > 0)
				if("where".equals(where))	where += " i.numeroDocOrigemRegd = :num_doc_origem ";
				else where += " and i.numeroDocOrigemRegd = :num_doc_origem ";
			if (!"".equals(usu_sol))
				if("where".equals(where))	where += " upper( i.nomeSolicitanteRegd ) like :usu_sol ";
				else where += " and upper( i.nomeSolicitanteRegd ) like :usu_sol ";			
			if (!"".equals(nome_sigla)) {
				join += " join i.entidadeDemandaEntds as entidade ";
				if("where".equals(where))	
					where += " ( upper( entidade.nomeEnt ) like :nome_sigla or upper( entidade.siglaEnt ) like :nome_sigla )";
				else where += " and ( upper( entidade.nomeEnt ) like :nome_sigla or upper( entidade.siglaEnt ) like :nome_sigla )";
			}		
			if (codPrior > 0)
				if("where".equals(where))	where += " i.prioridadePrior.codPrior = :codPrior ";
				else where += " and i.prioridadePrior.codPrior = :codPrior ";	
			if (codSitd > 0)
				if("where".equals(where))	where += " i.sitDemandaSitd.codSitd = :codSitd ";
				else where += " and i.sitDemandaSitd.codSitd = :codSitd ";	
			if (codOrg > 0)
				if(where.equals(" where "))	where += " i.orgaoOrg.codOrg = :codOrg ";
				else where += " and i.orgaoOrg.codOrg = :codOrg ";	
			if (entidade.getEntidadeAtributoEntas() != null && entidade.getEntidadeAtributoEntas().size() > 0) {
				if ("".equals(nome_sigla)) {
					join += " join i.entidadeDemandaEntds as entidade ";
				}
				join += " join entidade.entidadeAtributoEntas as entAtributo ";
				
				Iterator it = entidade.getEntidadeAtributoEntas().iterator();
				while (it.hasNext()) {
					EntidadeAtributoEnta entAtributo = (EntidadeAtributoEnta) it.next();
					atributosEntidade.add(entAtributo.getSisAtributoSatb().getCodSatb());
				}
				
				if("where".equals(where))	where += " entAtributo.comp_id.codSatb in (:entAtributo) ";
				else where += " and entAtributo.comp_id.codSatb in (:entAtributo) ";	
			}
		}
		
		Query query = this.getSession().createQuery("select i from RegDemandaRegd i" + join + where);
				
		if (limiteInicial!=null)		query.setDate("limiteInicial", limiteInicial);
		if (limiteFinal!=null)			query.setDate("limiteFinal", limiteFinal);
		if (solicitacaoInicial!=null)	query.setDate("solicitacaoInicial", solicitacaoInicial);
		if (solicitacaoFinal!=null)		query.setDate("solicitacaoFinal", solicitacaoFinal);
		
		if (num_registro > 0)			query.setLong("num_registro", num_registro);	
		if (!"".equals(descricao))		query.setString("descricao", "%"+descricao.toUpperCase()+"%");
		if (!"".equals(observacao))		query.setString("observacao", "%"+observacao.toUpperCase()+"%");
		if (num_doc_origem > 0)			query.setInteger("num_doc_origem", num_doc_origem);
		if (!"".equals(usu_sol))		query.setString("usu_sol", "%"+usu_sol.toUpperCase()+"%");
		if (!"".equals(nome_sigla))		query.setString("nome_sigla", "%"+nome_sigla.toUpperCase()+"%");
		if (codPrior > 0)				query.setLong("codPrior", codPrior);	
		if (codSitd > 0)				query.setLong("codSitd", codSitd);	
		if (codOrg > 0)					query.setLong("codOrg", codOrg);
		
		if (entidade.getEntidadeAtributoEntas() != null && entidade.getEntidadeAtributoEntas().size() > 0) {
			query.setParameterList("entAtributo", atributosEntidade);
		}
				
		List ret = query.list();
			
		List locais = new ArrayList(); 
		if (!"".equals(codLocais)) locais = new LocalItemDao(request).pesquisarCodLocais(codLocais);
		
		request.getSession().setAttribute("listLocais", locais);
		if ((ret != null)&&(ret.size() > 0)&&(!"".equals(codLocais))) {    		
						
			List atributosPesquisa = new ArrayList();
    		Iterator itAtbPesquisa = locais.iterator();
            while (itAtbPesquisa.hasNext())
                atributosPesquisa.add(((LocalItemLit) itAtbPesquisa.next()));
			
            Iterator it = ret.iterator();
            while (it.hasNext()) {
                List atributosResultado = new ArrayList();
                Iterator itAtribResultado = ((RegDemandaRegd) it.next()).getLocalDemandaLdems().iterator();
                
                while (itAtribResultado.hasNext())
                	atributosResultado.add((LocalItemLit) itAtribResultado.next());
                
                if (!atributosResultado.containsAll(atributosPesquisa))
                    it.remove();
            }
    	}				
		return ret;
	}
	
	/**
	 * Pesquisa PopUp Reg Demanda
	 * @param regDemanda
         * @param gruposAcesso
         * @param usuario
         * @param visaoDemandasVisDem
         * @return List
	 * @throws ECARException
	 */
	public List pesquisarPopUpRegDemanda(RegDemandaRegd regDemanda, Set gruposAcesso, UsuarioUsu usuario, List visoesAcesso) throws ECARException {
		try {
			StringBuilder select = new StringBuilder();
			StringBuilder where = new StringBuilder();
			StringBuilder order = new StringBuilder();
			
			boolean bolWhere = false;
			
			DemandasGrpAcessoDao demandasGrpAcessoDao = new DemandasGrpAcessoDao();
			DemandasGrpAcesso demandasGrpAcesso = null;
			
			List<RegDemandaRegd> demandasComAcesso = new ArrayList<RegDemandaRegd>();
			Iterator<VisaoDemandasVisDem> itVisoes = visoesAcesso.iterator();
			
			while (itVisoes.hasNext()) {
				
				VisaoDemandasVisDem visaoDemanda = (VisaoDemandasVisDem) itVisoes.next();						
				List<RegDemandaRegd> listaDemandasComPermissaoVisao = getDemandasComPermissaoNosGruposAcessosUsuario(usuario, visaoDemanda, false);
								
				for(int i=0;i<listaDemandasComPermissaoVisao.size();i++) {
					if (!demandasComAcesso.contains(listaDemandasComPermissaoVisao.get(i))) {
						demandasComAcesso.add(listaDemandasComPermissaoVisao.get(i));
					}
				}
			}
			
			// era bom colocar isso num arquivo de propriedades
			select.append("select distinct regDemanda from RegDemandaRegd as regDemanda")
				.append(" join regDemanda.usuarioUsuByCodUsuInclusaoRegd as usuario ") 
				.append(" join usuario.usuarioAtributoUsuas as usuarioAtributo ");
						
			if (regDemanda.getDescricaoRegd() != null && 
						!"".equals(regDemanda.getDescricaoRegd())) {
								
				bolWhere = true;
				where.append(" where");
				
				where.append(" upper(regDemanda.descricaoRegd) like :descricao");
			}
			
			if (regDemanda.getEntidadeDemandaEntds() != null && regDemanda.getEntidadeDemandaEntds().size() > 0) {
				select.append(" join regDemanda.entidadeDemandaEntds as entidade");
				
				EntidadeEnt entidade = (EntidadeEnt) regDemanda.getEntidadeDemandaEntds().iterator().next();
				
				if (entidade.getSiglaEnt() != null && !"".equals(entidade.getSiglaEnt())) {
					if (bolWhere) {
						where.append(" and");
					} else {
						bolWhere = true;
						where.append(" where");
					}
					where.append(" upper(entidade.siglaEnt) like :siglaEnt");
				}
				
				if (entidade.getNomeEnt() != null && !"".equals(entidade.getNomeEnt())) {
					if (bolWhere) {
						where.append(" and");
					} else {
						bolWhere = true;
						where.append(" where");
					}
					where.append(" upper(entidade.nomeEnt) like :nomeEnt");
				}
			}
			if (regDemanda.getNomeSolicitanteRegd() != null &&
						!"".equals(regDemanda.getNomeSolicitanteRegd())) {
				if (bolWhere) {
					where.append(" and");
				} else {
					bolWhere = true;
					where.append(" where");
				}
				where.append(" upper(regDemanda.nomeSolicitanteRegd) like :usuario");
			}
			
//			if (!administradorDemandas && gruposAcesso != null){
			if (gruposAcesso != null){	
				if (bolWhere) {
					where.append(" and");
				} else {
					bolWhere = true;
					where.append(" where");
				}				
				
				where.append(" (usuarioAtributo.usuarioUsu.codUsu = usuario.codUsu ")
				.append(" and usuarioAtributo.comp_id.codSatb in (:gruposAcesso))");
			}
			
			order.append(" order by regDemanda.descricaoRegd asc");
			
			Query query = this.getSession().createQuery(select.toString() + where.toString() + order.toString());
			
//			if (!administradorDemandas){
			
			List<Long> listCodGruposAcesso = new ArrayList<Long>();
			if (gruposAcesso != null){
		        for (Iterator iter = gruposAcesso.iterator(); iter.hasNext();) {
		        	SisAtributoSatb grupoAcesso = (SisAtributoSatb) iter.next();
		        	listCodGruposAcesso.add(grupoAcesso.getCodSatb());
		        }
			}
	        
	        if(listCodGruposAcesso != null && listCodGruposAcesso.size() > 0){
	        	query.setParameterList("gruposAcesso", listCodGruposAcesso);
	        }
//			}
			
			if (regDemanda.getDescricaoRegd() != null && 
						!"".equals(regDemanda.getDescricaoRegd())) {
				query.setString("descricao", "%" + regDemanda.getDescricaoRegd().toUpperCase() + "%");
			}
			if (regDemanda.getEntidadeDemandaEntds() != null && regDemanda.getEntidadeDemandaEntds().size() > 0) {
				EntidadeEnt entidade = (EntidadeEnt) regDemanda.getEntidadeDemandaEntds().iterator().next();
				if (entidade.getSiglaEnt() != null && !"".equals(entidade.getSiglaEnt())) {
					query.setString("siglaEnt", "%" + entidade.getSiglaEnt().toUpperCase() + "%");
				}
				if (entidade.getNomeEnt() != null && !"".equals(entidade.getNomeEnt())) {
					query.setString("nomeEnt", "%" + entidade.getNomeEnt().toUpperCase() + "%");
				}
			}
			if (regDemanda.getNomeSolicitanteRegd() != null &&
						!"".equals(regDemanda.getNomeSolicitanteRegd())) {
				query.setString("usuario", "%" + regDemanda.getNomeSolicitanteRegd().toUpperCase() + "%");
			}
			
			List<RegDemandaRegd> demandasFiltro = query.list();
			
			Iterator itDemandasFiltro = demandasFiltro.iterator();
			
			while (itDemandasFiltro.hasNext()){
				
				RegDemandaRegd demanda = (RegDemandaRegd) itDemandasFiltro.next();
				if (!demandasComAcesso.contains(demanda)){
					
					itDemandasFiltro.remove();
				}
			}
			
			return demandasFiltro;
		} catch (HibernateException e) {
			this.logger.error(e);
            throw new ECARException(e);   
		}
	}
	
	/**
	 * 
	 * @param regDemanda
	 * @param grupo
	 * @return List
	 * @throws ECARException
	 */
	public List getDemandaAtributoByGrupo(RegDemandaRegd regDemanda, SisGrupoAtributoSga grupo) throws ECARException {
    	List retorno = new ArrayList();
        Set result = regDemanda.getDemAtributoDemas();
        if (result != null) {
            if (result.size() > 0) {
                Iterator it = result.iterator();
                while (it.hasNext()) {
                	DemAtributoDema demandaAtributo = (DemAtributoDema) it.next();
                	if (demandaAtributo.getSisAtributoSatb().getSisGrupoAtributoSga().equals(grupo))
                		retorno.add(demandaAtributo);
                }

            }
        }
        return retorno;
    }
	
	/**
	 * Retorna o valor de um atributo demanda em um regDemanda
	 * @param regDemanda
	 * @param nomeAtbdem
	 * @param FkAtbdem
	 * @return
	 * @throws ECARException
	 */
    public String getValorAtributoDemanda(
            RegDemandaRegd regDemanda, String nomeAtbdem,
            String FkAtbdem) throws ECARException {
    	try {
	        Object retorno = Util.invocaGet(regDemanda, nomeAtbdem);
	        if (retorno != null) {
	            if (FkAtbdem != null && !"".equals(FkAtbdem)) {
	                retorno = Util.invocaGet(retorno, FkAtbdem);
	                if (retorno != null)
	                    return retorno.toString();
	                else
	                    return "";
	            } else {
	                if (retorno.getClass().equals(Timestamp.class) || retorno.getClass().equals(java.sql.Date.class))
	                    retorno = Data.parseDate((Date) retorno);
	                
	                return retorno.toString();
	            }
	        }
    	} catch (Exception e) {
    		this.logger.error(e);
    		throw new ECARException(e);
        }
        return "";
    }
    
    
    /**
     * Pega um conjunto de valores de qualquer campo da tabela local_item, atravï¿½s
     * do valor da Fk (normalmente identificacaoLit) para uma determinada demanda. 
     * Retorna uma String onde os valores sï¿½o separados por vï¿½rgula
     * 
     * @param regDemanda
     * @param FkAtbdem
     * @return
     * @throws ECARException
     */
    public String getValorLocaisDemanda(RegDemandaRegd regDemanda,
            String FkAtbdem) throws ECARException {
    	
        LocalItemLit localItem = new LocalItemLit();        
        Set locais = regDemanda.getLocalDemandaLdems();
//          List identificacaoLocais = new ArrayList();
        Iterator it = locais.iterator();
        StringBuilder colunaLocais = new StringBuilder();
        Object colunaLocal;
        
        while (it.hasNext()) {
        	localItem = (LocalItemLit) it.next();
        	if (FkAtbdem != null && !"".equals(FkAtbdem)) {
        		colunaLocal = Util.invocaGet(localItem, FkAtbdem);
        		
        		if (colunaLocal.getClass().equals(Timestamp.class)
                        || colunaLocal.getClass().equals(java.sql.Date.class)){
        			colunaLocal = Data.parseDate((Date) colunaLocal);
        		}
        		colunaLocais.append(colunaLocal.toString());
        		colunaLocais.append(", ");
        	} 
        }
        if (colunaLocais.length() > 0){
        	colunaLocais.delete(colunaLocais.length()-2, colunaLocais.length());
        }
        
        return colunaLocais.toString();
        
    }
    
    /**
     *
     * @param regDemanda
     * @return
     * @throws ECARException
     */
    public String getHierarquiaLocaisDemanda(RegDemandaRegd regDemanda) throws ECARException {
    	
        LocalItemLit localItem = new LocalItemLit();        
        Set locais = regDemanda.getLocalDemandaLdems();
//          List identificacaoLocais = new ArrayList();
        Iterator it = locais.iterator();
        StringBuilder hierarquiaLocaisStr = new StringBuilder();
        Set hierarquiaLocais;
        String hierarquiaLocal;
        
        while (it.hasNext()) {
        	localItem = (LocalItemLit) it.next();
        	
        	hierarquiaLocais = (Set) Util.invocaGet(localItem, "localItemHierarquiaLithsByCodLit");
    		
        	Iterator itHierarquia = hierarquiaLocais.iterator();
    		        	
        	if(itHierarquia.hasNext()) {
	        	while(itHierarquia.hasNext()) {
	        		        		
		        	hierarquiaLocaisStr.append(
		        			localItem.getIdentificacaoLit() + " - " + 
		        			((LocalItemLit)itHierarquia.next()).getIdentificacaoLit());	        	
	        	}
        	} else {
        		hierarquiaLocaisStr.append(localItem.getIdentificacaoLit());
        	}
        	 
        	hierarquiaLocaisStr.append(", ");
        }
        if (hierarquiaLocaisStr.length() > 0){
        	hierarquiaLocaisStr.delete(hierarquiaLocaisStr.length()-2, hierarquiaLocaisStr.length());
        }
        
        return hierarquiaLocaisStr.toString();
        
    }
    
    /**
     * Pega um conjunto de valores de qualquer campo da tabela local_item, atravï¿½s
     * do valor da Fk (normalmente identificacaoLit) para uma determinada demanda. 
     * Retorna uma String onde os valores sï¿½o separados por vï¿½rgula
     * 
     * @param regDemanda
     * @param FkAtbdem
     * @return
     * @throws ECARException
     */
    public String getValorEntidadesDemanda(RegDemandaRegd regDemanda,
            String FkAtbdem) throws ECARException {
    	
    	ConfiguracaoCfg configura = new ConfiguracaoDao(request).getConfiguracao();
    	
    	EntidadeEnt entidade = new EntidadeEnt();        
        Set entidades = regDemanda.getEntidadeDemandaEntds();
//          List identificacaoLocais = new ArrayList();
        Iterator it = entidades.iterator();
        StringBuilder colunaEntidades = new StringBuilder();
        Object colunaEntidade;
        
        while (it.hasNext()) {
        	entidade = (EntidadeEnt) it.next();
        	if (FkAtbdem != null && !"".equals(FkAtbdem)) {
        		colunaEntidade = Util.invocaGet(entidade, FkAtbdem);
        		
        		if (colunaEntidade.getClass().equals(Timestamp.class)
                        || colunaEntidade.getClass().equals(java.sql.Date.class)){
        			colunaEntidade = Data.parseDate((Date) colunaEntidade);
        		}
        		colunaEntidades.append(colunaEntidade.toString());
        		colunaEntidades.append(configura.getSeparadorCampoMultivalor());
        	} 
        }
        if (colunaEntidades.length() > 0){
        	colunaEntidades.delete(colunaEntidades.length()- configura.getSeparadorCampoMultivalor().length(), colunaEntidades.length());
        }
        
        return colunaEntidades.toString();
        
    }
    
    /**
     * Pega um conjunto de valores de qualquer campo da tabela local_item, atravï¿½s
     * do valor da Fk (normalmente identificacaoLit) para uma determinada demanda. 
     * Retorna uma String onde os valores sï¿½o separados por vï¿½rgula
     * 
     * @param regDemanda
     * @param FkAtbdem
     * @return
     * @throws ECARException
     */
    public String getValorEntidadesOrgaosDemanda(RegDemandaRegd regDemanda,
            String FkAtbdem) throws ECARException {
    	
    	ConfiguracaoCfg configura = new ConfiguracaoDao(request).getConfiguracao();
    	
    	EntidadeEnt entidade = new EntidadeEnt();        
        Set entidadesOrgaos = regDemanda.getEntidadeOrgaoDemandaEntorgds();
//          List identificacaoLocais = new ArrayList();
        Iterator it = entidadesOrgaos.iterator();
        StringBuilder colunaOrgaos = new StringBuilder();
        Object colunaOrgao;
        
        while (it.hasNext()) {
        	entidade = (EntidadeEnt) it.next();
        	if (FkAtbdem != null && !"".equals(FkAtbdem)) {
        		colunaOrgao = Util.invocaGet(entidade, FkAtbdem);
        		
        		if (colunaOrgao.getClass().equals(Timestamp.class)
                        || colunaOrgao.getClass().equals(java.sql.Date.class)){
        			colunaOrgao = Data.parseDate((Date) colunaOrgao);
        		}
        		colunaOrgaos.append(colunaOrgao.toString());
        		colunaOrgaos.append(configura.getSeparadorCampoMultivalor());
        	} 
        }
        if (colunaOrgaos.length() > 0){
        	colunaOrgaos.delete(colunaOrgaos.length()-configura.getSeparadorCampoMultivalor().length(), colunaOrgaos.length());
        }
        
        return colunaOrgaos.toString();
        
    }
    
   /**
     * Aplica restriçoes nao implementadas na consulta do banco
     * 
     * @param regDemandas
	 * @param usuario
     * @param classificacao
     * @param gruposAcesso 
     * @param codVisao 
     * @return
     * @throws ECARException
     * @return
     */
    public List aplicarRestricoesRegDemanda(List regDemandas, UsuarioUsu usuario, Set gruposAcesso, boolean classificacao, Long codVisao) throws ECARException{
    	
    	Iterator itRegDemandas = regDemandas.iterator();
    	
    	while (itRegDemandas.hasNext()){
    		
    		RegDemandaRegd regDemandaRegd = (RegDemandaRegd) itRegDemandas.next();
    		
    		// pega os atributos restritivos
    		List atributosLivres = getAtributosLivresDemandaDeUmaVisaoEhRestritivo(codVisao);
    		   		    		 
    			// Verifica se as demandas possuem a regra de atriubtos restritivos  
				if (aplicarRestricaoAtributosLivresRestritivos(regDemandaRegd, atributosLivres, usuario)){
					
					// remove a demanda da lista
					itRegDemandas.remove();
				}
    		
    	}
    	
    	return regDemandas;
    }
        
    
    /******	METODO ADICIONADO POR JOSE ANDRE NO BRANCH ***********/
    /******	RESPONSAVEL PELO MERGE PATRICIA PESSOA ***********/
    /**
     * Aplica os filtros dinï¿½micos no registro de demanda
     * 
     * @param regDemandas 
     * @param codVisao
     * @return
     * @throws ECARException
     */
    public List aplicarFiltrosDinamicosRegDemanda(List regDemandas, Long codVisao) throws ECARException{
    	
    	Iterator itRegDemandas = regDemandas.iterator();    	
    	Map parametrosFiltroDemanda = new HashMap();  
    	String valorCampoArray[] = null;
    	boolean diferente = true;
    	boolean todosVazio = false;
    	
    	//Se existe parametros para filtrar
    	if(request.getSession().getAttribute("parametrosFiltroDemanda")!=null){
    		parametrosFiltroDemanda = (Map) request.getSession().getAttribute("parametrosFiltroDemanda");

        	while (itRegDemandas.hasNext()){
        		RegDemandaRegd regDemandaRegd = (RegDemandaRegd) itRegDemandas.next();
        		
        		List atributosLivres = getAtributosLivresDemandaEhFiltro(codVisao);
        		String valorCampo = Pagina.trocaNull(parametrosFiltroDemanda.get("descricaoRegd"));
        		
        		if (!"".equals(valorCampo)) {                 		
    				if (regDemandaRegd.getDescricaoRegd() == null || !regDemandaRegd.getDescricaoRegd().toUpperCase().contains((valorCampo.toUpperCase()))){
    					itRegDemandas.remove();
    	    			continue;
    	    		}
        		}    		

        		valorCampo = Pagina.trocaNull(parametrosFiltroDemanda.get("numeroDocOrigemRegd_Inicio"));
    			if (!"".equals(valorCampo)) {                 		
    				if (regDemandaRegd.getNumeroDocOrigemRegd() == null || regDemandaRegd.getNumeroDocOrigemRegd().doubleValue() < Double.valueOf(valorCampo).doubleValue()){
    					itRegDemandas.remove();
    	    			continue;
    	    		}
    			}		    		
    			
    			valorCampo = Pagina.trocaNull(parametrosFiltroDemanda.get("numeroDocOrigemRegd_Fim"));
    			if (!"".equals(valorCampo)) {                 		
    				if (regDemandaRegd.getNumeroDocOrigemRegd() == null || regDemandaRegd.getNumeroDocOrigemRegd().doubleValue() > Double.valueOf(valorCampo).doubleValue()){
    					itRegDemandas.remove();
    	    			continue;
    	    		}
    			}
        
    			valorCampo = Pagina.trocaNull(parametrosFiltroDemanda.get("codRegd"));
        		if (!"".equals(valorCampo)) {                 		
    				if (regDemandaRegd.getCodRegd() == null || !regDemandaRegd.getCodRegd().toString().equals(valorCampo)){
    					itRegDemandas.remove();
    	    			continue;
    	    		}
        		}    		
        		
    			valorCampo = Pagina.trocaNull(parametrosFiltroDemanda.get("observacaoRegd"));
    			if (!"".equals(valorCampo)) {                 		
    				if (regDemandaRegd.getObservacaoRegd() == null || !regDemandaRegd.getObservacaoRegd().toUpperCase().contains((valorCampo.toUpperCase()))){
    					itRegDemandas.remove();
    	    			continue;
    	    		}
    			}
        		    			
    			if (regDemandaRegd.getSitDemandaSitd()!=null 
    				&&
    				regDemandaRegd.getSitDemandaSitd().getCodSitd()!=null
    				&&
    				ehValorPropriedadeDiferenteTodosValoresFiltros(parametrosFiltroDemanda.get("sitDemandaSitd"), regDemandaRegd.getSitDemandaSitd().getCodSitd().toString())) {
    				itRegDemandas.remove();
	    			continue;
    			}
    			
    			if (regDemandaRegd.getUsuarioUsuByCodUsuAlteracaoRegd()!=null &&
    				regDemandaRegd.getUsuarioUsuByCodUsuAlteracaoRegd().getCodUsu()!=null &&
    				ehValorPropriedadeDiferenteTodosValoresFiltros(parametrosFiltroDemanda.get("usuarioUsuByCodUsuInclusaoRegd"), regDemandaRegd.getUsuarioUsuByCodUsuAlteracaoRegd().getCodUsu().toString())) {
    				itRegDemandas.remove();
	    			continue;
    			}

    			if (regDemandaRegd.getPrioridadePrior()!=null &&
        			regDemandaRegd.getPrioridadePrior().getCodPrior() !=null &&
        			ehValorPropriedadeDiferenteTodosValoresFiltros(parametrosFiltroDemanda.get("prioridadePrior"), regDemandaRegd.getPrioridadePrior().getCodPrior().toString())) {
        				itRegDemandas.remove();
    	    			continue;
        		}
    			

    			valorCampo = Pagina.trocaNull(parametrosFiltroDemanda.get("regDemandaRegd"));
    			if (!"".equals(valorCampo)) {
    				if (regDemandaRegd.getRegDemandaRegd() == null || !regDemandaRegd.getRegDemandaRegd().getCodRegd().equals(Long.valueOf(valorCampo))){
    					itRegDemandas.remove();
    	    			continue;
    	    		}
    			}

    			if (ehValorPropriedadeDiferenteTodosValoresFiltros(parametrosFiltroDemanda.get("indAtivoRegd"), regDemandaRegd.getIndAtivoRegd())) {
    				itRegDemandas.remove();
	    			continue;
    			}
    			
    			valorCampo = Pagina.trocaNull(parametrosFiltroDemanda.get("nomeSolicitanteRegd"));
    			if (!"".equals(valorCampo)) {
    				if (regDemandaRegd.getNomeSolicitanteRegd() == null || !regDemandaRegd.getNomeSolicitanteRegd().toUpperCase().contains((valorCampo.toUpperCase()))){
    					itRegDemandas.remove();
    	    			continue;
    	    		}
    			}
    			
    			valorCampo = Pagina.trocaNull(parametrosFiltroDemanda.get("dataLimiteRegd_Inicio"));
    			if (valorCampo != null && !"".equals(valorCampo)) {
    				
    				String dataLimiteRegdInicioStr = valorCampo;
    				
    				//Modifica as ordens de dia e mï¿½s na data, para ficar no formato MM/DD/YYYY
    				dataLimiteRegdInicioStr = 
    					dataLimiteRegdInicioStr.substring(3, dataLimiteRegdInicioStr.lastIndexOf("/")+1) +
    					dataLimiteRegdInicioStr.substring(0, dataLimiteRegdInicioStr.indexOf("/")+1) +
    					dataLimiteRegdInicioStr.substring(dataLimiteRegdInicioStr.lastIndexOf("/")+1);
    				    					
    				Date dataLimiteRegdInicio = new Date(dataLimiteRegdInicioStr);
    				    					    					
    				if (regDemandaRegd.getDataLimiteRegd() == null || 
    						//Remove da lista se a data estiver estiver fora do intervalo especificado no filtro
    						regDemandaRegd.getDataLimiteRegd().compareTo(dataLimiteRegdInicio) < 0){					
    					itRegDemandas.remove();
            			continue;
            		}
    			}
    			
    			valorCampo = Pagina.trocaNull(parametrosFiltroDemanda.get("dataLimiteRegd_Fim"));
    			if (valorCampo != null && !"".equals(valorCampo)) {
    				
    				String dataLimiteRegdFimStr = valorCampo;
    				
    				//Modifica as ordens de dia e mï¿½s na data, para ficar no formato MM/DD/YYYY
    				dataLimiteRegdFimStr = 
    					dataLimiteRegdFimStr.substring(3, dataLimiteRegdFimStr.lastIndexOf("/")+1) +
    					dataLimiteRegdFimStr.substring(0, dataLimiteRegdFimStr.indexOf("/")+1) +
    					dataLimiteRegdFimStr.substring(dataLimiteRegdFimStr.lastIndexOf("/")+1);
    				    					
    				Date dataLimiteRegdFim = new Date(dataLimiteRegdFimStr);
    				    					    					
    				if (regDemandaRegd.getDataLimiteRegd() == null || 
    						//Remove da lista se a data estiver estiver fora do intervalo especificado no filtro
    						regDemandaRegd.getDataLimiteRegd().compareTo(dataLimiteRegdFim) > 0){					
    					itRegDemandas.remove();
            			continue;
            		}
    			}
    		
    			valorCampo = Pagina.trocaNull(parametrosFiltroDemanda.get("dataSolicitacaoRegd_Inicio"));
    			if (valorCampo != null && !"".equals(valorCampo)) {
    				
    				String dataSolicitacaoRegdInicioStr = valorCampo;
    				
    				//Modifica as ordens de dia e mï¿½s na data, para ficar no formato MM/DD/YYYY
    				dataSolicitacaoRegdInicioStr = 
    					dataSolicitacaoRegdInicioStr.substring(3, dataSolicitacaoRegdInicioStr.lastIndexOf("/")+1) +
    					dataSolicitacaoRegdInicioStr.substring(0, dataSolicitacaoRegdInicioStr.indexOf("/")+1) +
    					dataSolicitacaoRegdInicioStr.substring(dataSolicitacaoRegdInicioStr.lastIndexOf("/")+1);
    				    					
    				Date dataSolicitacaoRegdInicio = new Date(dataSolicitacaoRegdInicioStr);
    				    					    					
    				if (regDemandaRegd.getDataSolicitacaoRegd() == null || 
    						//Remove da lista se a data estiver estiver fora do intervalo especificado no filtro
    						regDemandaRegd.getDataSolicitacaoRegd().compareTo(dataSolicitacaoRegdInicio) < 0){					
    					itRegDemandas.remove();
            			continue;
            		}
    			}

    			valorCampo = Pagina.trocaNull(parametrosFiltroDemanda.get("dataSolicitacaoRegd_Fim"));
    			if (valorCampo != null && !"".equals(valorCampo)) {
    				
    				String dataSolicitacaoRegdFimStr = valorCampo;
    				
    				//Modifica as ordens de dia e mï¿½s na data, para ficar no formato MM/DD/YYYY
    				dataSolicitacaoRegdFimStr = 
    					dataSolicitacaoRegdFimStr.substring(3, dataSolicitacaoRegdFimStr.lastIndexOf("/")+1) +
    					dataSolicitacaoRegdFimStr.substring(0, dataSolicitacaoRegdFimStr.indexOf("/")+1) +
    					dataSolicitacaoRegdFimStr.substring(dataSolicitacaoRegdFimStr.lastIndexOf("/")+1);
    				    					
    				Date dataSolicitacaoRegdFim = new Date(dataSolicitacaoRegdFimStr);
    				    					    					
    				if (regDemandaRegd.getDataSolicitacaoRegd() == null || 
    						//Remove da lista se a data estiver estiver fora do intervalo especificado no filtro
    						regDemandaRegd.getDataSolicitacaoRegd().compareTo(dataSolicitacaoRegdFim) > 0){					
    					itRegDemandas.remove();
            			continue;
            		}
    			}

    			valorCampo = Pagina.trocaNull(parametrosFiltroDemanda.get("dataInclusaoRegd_Inicio"));
    			if (valorCampo != null && !"".equals(valorCampo)) {
    				
    				String dataInclusaoRegdInicioStr = valorCampo;
    				
    				//Modifica as ordens de dia e mï¿½s na data, para ficar no formato MM/DD/YYYY
    				dataInclusaoRegdInicioStr = 
    					dataInclusaoRegdInicioStr.substring(3, dataInclusaoRegdInicioStr.lastIndexOf("/")+1) +
    					dataInclusaoRegdInicioStr.substring(0, dataInclusaoRegdInicioStr.indexOf("/")+1) +
    					dataInclusaoRegdInicioStr.substring(dataInclusaoRegdInicioStr.lastIndexOf("/")+1);
    				    					
    				Date dataInclusaoRegdInicio = new Date(dataInclusaoRegdInicioStr);
    				    					    					
    				if (regDemandaRegd.getDataInclusaoRegd() == null || 
    						//Remove da lista se a data estiver estiver fora do intervalo especificado no filtro
    						regDemandaRegd.getDataInclusaoRegd().compareTo(dataInclusaoRegdInicio) < 0){					
    					itRegDemandas.remove();
            			continue;
            		}
    			}

    			valorCampo = Pagina.trocaNull(parametrosFiltroDemanda.get("dataInclusaoRegd_Fim"));
    			if (valorCampo != null && !"".equals(valorCampo)) {
    				
    				String dataInclusaoRegdFimStr = valorCampo;
    				
    				//Modifica as ordens de dia e mï¿½s na data, para ficar no formato MM/DD/YYYY
    				dataInclusaoRegdFimStr = 
    					dataInclusaoRegdFimStr.substring(3, dataInclusaoRegdFimStr.lastIndexOf("/")+1) +
    					dataInclusaoRegdFimStr.substring(0, dataInclusaoRegdFimStr.indexOf("/")+1) +
    					dataInclusaoRegdFimStr.substring(dataInclusaoRegdFimStr.lastIndexOf("/")+1);
    				    					
    				Date dataInclusaoRegdFim = new Date(dataInclusaoRegdFimStr);
    				    					    					
    				if (regDemandaRegd.getDataInclusaoRegd() == null || 
    						//Remove da lista se a data estiver estiver fora do intervalo especificado no filtro
    						regDemandaRegd.getDataInclusaoRegd().compareTo(dataInclusaoRegdFim) > 0){					
    					itRegDemandas.remove();
            			continue;
            		}
    			}
    			
    			valorCampo = Pagina.trocaNull(parametrosFiltroDemanda.get("dataSituacaoRegd_Inicio"));
    			if (valorCampo != null && !"".equals(valorCampo)) {
    				
    				String dataSituacaoRegdInicioStr = valorCampo;
    				
    				//Modifica as ordens de dia e mï¿½s na data, para ficar no formato MM/DD/YYYY
    				dataSituacaoRegdInicioStr = 
    					dataSituacaoRegdInicioStr.substring(3, dataSituacaoRegdInicioStr.lastIndexOf("/")+1) +
    					dataSituacaoRegdInicioStr.substring(0, dataSituacaoRegdInicioStr.indexOf("/")+1) +
    					dataSituacaoRegdInicioStr.substring(dataSituacaoRegdInicioStr.lastIndexOf("/")+1);
    				    					
    				Date dataSituacaoRegdInicio = new Date(dataSituacaoRegdInicioStr);
    				    					    					
    				if (regDemandaRegd.getDataSituacaoRegd() == null || 
    						//Remove da lista se a data estiver estiver fora do intervalo especificado no filtro
    						regDemandaRegd.getDataSituacaoRegd().compareTo(dataSituacaoRegdInicio) < 0){					
    					itRegDemandas.remove();
            			continue;
            		}
    			}
    			
    			valorCampo = Pagina.trocaNull(parametrosFiltroDemanda.get("dataSituacaoRegd_Fim"));
    			if (valorCampo != null && !"".equals(valorCampo)) {
    				
    				String dataSituacaoRegdFimStr = valorCampo;
    				
    				//Modifica as ordens de dia e mï¿½s na data, para ficar no formato MM/DD/YYYY
    				dataSituacaoRegdFimStr = 
    					dataSituacaoRegdFimStr.substring(3, dataSituacaoRegdFimStr.lastIndexOf("/")+1) +
    					dataSituacaoRegdFimStr.substring(0, dataSituacaoRegdFimStr.indexOf("/")+1) +
    					dataSituacaoRegdFimStr.substring(dataSituacaoRegdFimStr.lastIndexOf("/")+1);
    				    					
    				Date dataSituacaoRegdFim = new Date(dataSituacaoRegdFimStr);
    				    					    					
    				if (regDemandaRegd.getDataSituacaoRegd() == null || 
    						//Remove da lista se a data estiver estiver fora do intervalo especificado no filtro
    						regDemandaRegd.getDataSituacaoRegd().compareTo(dataSituacaoRegdFim) > 0){					
    					itRegDemandas.remove();
            			continue;
            		}
    			}
    			
    			valorCampo = Pagina.trocaNull(parametrosFiltroDemanda.get("contLocal"));			
    			boolean localNaoEncontrado = false;
    			int numLocais = 0;
    			
    			if (valorCampo!=null && valorCampo.length()>0)
    				numLocais = Integer.parseInt(valorCampo);
    			for (int i = 1; i <= numLocais; i++) {
    				if ("S".equals(Pagina.trocaNull(parametrosFiltroDemanda.get("adicionaLocal" + i)))) {
    					LocalItemLit local = new LocalItemLit();
    					local = (LocalItemLit) super
    							.buscar(LocalItemLit.class, Long.valueOf(Pagina.trocaNull(parametrosFiltroDemanda.get("codLit" + i))));
    					
    					if (!regDemandaRegd.getLocalDemandaLdems().contains(local)){
    						localNaoEncontrado = true;
    						itRegDemandas.remove();
    						break;
    					}
    				}
    			}
    			if (localNaoEncontrado)
    				continue;
        	
    			
    			valorCampo = Pagina.trocaNull(parametrosFiltroDemanda.get("contEntidade"));			
    			boolean entidadeNaoEncontrada = false;
    			int numEntidades = 0;
    			if (valorCampo!=null && valorCampo.length()>0)
    				numEntidades = Integer.parseInt(valorCampo);
    			for (int i = 1; i <= numEntidades; i++) {
    				if ("S".equals(Pagina.trocaNull(parametrosFiltroDemanda.get("adicionaEntidade" + i)))) {
    					EntidadeEnt entidade = new EntidadeEnt();
    					entidade = (EntidadeEnt) super
    							.buscar(EntidadeEnt.class, Long.valueOf(Pagina.trocaNull(parametrosFiltroDemanda.get("codEnt" + i))));
    					
    					if (!regDemandaRegd.getEntidadeDemandaEntds().contains(entidade)){
    						entidadeNaoEncontrada = true;
    						itRegDemandas.remove();
    						break;
    					}
    				}
    			}
    			if (entidadeNaoEncontrada)
    				continue;

    			
    			valorCampo = Pagina.trocaNull(parametrosFiltroDemanda.get("contEntidadeOrgao"));			
    			boolean entidadeOrgaoNaoEncontrada = false;
    			int numEntidadeOrgaos = 0;
    			if (valorCampo!=null && valorCampo.length()>0)	
    					numEntidadeOrgaos = Integer.parseInt(valorCampo);
    			for (int i = 1; i <= numEntidadeOrgaos; i++) {
    				if ("S".equals(Pagina.trocaNull(parametrosFiltroDemanda.get("adicionaEntidadeOrgao" + i)))) {
    					EntidadeEnt entidadeOrgao = new EntidadeEnt();
    					entidadeOrgao = (EntidadeEnt) super
    							.buscar(EntidadeEnt.class, Long.valueOf(Pagina.trocaNull(parametrosFiltroDemanda.get("codEntOrg" + i))));
    					
    					if (!regDemandaRegd.getEntidadeOrgaoDemandaEntorgds().contains(entidadeOrgao)){
    						entidadeOrgaoNaoEncontrada = true;
    						itRegDemandas.remove();
    						break;
    					}
    				}
    			}
    			if (entidadeOrgaoNaoEncontrada){    				
    				continue;
    			}
    				    			
    			//Chama o mï¿½todo responsï¿½vel por efetuar os filtros dos atributos livres
    			if (filtrarAtributosLivres(parametrosFiltroDemanda, regDemandaRegd, itRegDemandas, atributosLivres)){
    				continue;
    			}
        	}    		
    	}
    		     		        	    	
    	return regDemandas;
    }
    
    /******	METODO ADICIONADO POR JOSE ANDRE NO BRANCH ***********/
    /******	RESPONSAVEL PELO MERGE PATRICIA PESSOA ***********/
    
    /**
     * 
     * 
     * @param request
     * @param regDemandaRegd
     * @param itensRemovidos
     * @param itRegDemandas
     * @param atributosLivres
     * @return
     */
       private boolean filtrarAtributosLivres(Map parametrosFiltroDemanda, RegDemandaRegd regDemandaRegd, Iterator itRegDemandas, List atributosLivres) {
       	boolean retorno = false;
       	try {
//       		ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);
//       		String codEtt = iett.getEstruturaEtt().getCodEtt().toString();
       		
       		    		
       		boolean filtrarAtributoLivre = true;
   			Iterator itAtributos = atributosLivres.iterator();
   			//Filtrar pelos atributos livres definidos como filtros
   			while (itAtributos.hasNext() && filtrarAtributoLivre){
   				ObjetoDemanda atributoDemanda = (ObjetoDemanda) itAtributos.next();
   				SisGrupoAtributoSga grupoAtributo = atributoDemanda.iGetGrupoAtributosLivres();
   				   				
   				String nomeCampo = "a" + (grupoAtributo!=null?grupoAtributo.getCodSga().toString():"");
   	    		String tipoCampo = grupoAtributo!=null?grupoAtributo.getSisTipoExibicGrupoSteg().getCodSteg().toString():"";
   	    		
   	    		//Se for CheckBox ou RadioButton ou ListBox, nao procura em InformacaoIettSatb
   	    		if(tipoCampo.equals(SisTipoExibicGrupoDao.CHECKBOX) || tipoCampo.equals(SisTipoExibicGrupoDao.LISTBOX) 
   	    		   || ( tipoCampo.equals(SisTipoExibicGrupoDao.COMBOBOX) )|| tipoCampo.equals(SisTipoExibicGrupoDao.RADIO_BUTTON)){
   	    			String[] atributos = null;
   	    			if (parametrosFiltroDemanda.get(nomeCampo) instanceof String) {
   	    				atributos = new String[1];
   	    				atributos[0] = (String)parametrosFiltroDemanda.get(nomeCampo);
					} else {
						atributos = (String[])parametrosFiltroDemanda.get(nomeCampo);
					}
   	    			   	    			
   	    			if (atributos== null || atributos.length==1) {
   	    				if(atributos!= null && 
	   	    					!"".equals(atributos[0])){
	   	    				if (!getSisAtributosRegDem(regDemandaRegd, grupoAtributo.getCodSga()).contains((SisAtributoSatb) this.buscar(SisAtributoSatb.class, Long.valueOf(Pagina.trocaNull(atributos[0]))))){

	   	    					itRegDemandas.remove();
	                   			filtrarAtributoLivre = false;
	                   			retorno = true;
	                   			break;
	   	    				} 		    				
	   		    		}
 	    				
   	    			} else {
   	    				
   	    				boolean diferente = true;
   	    				boolean todosValoresVazio = true;
   	    				for (int i=0; i<atributos.length;i++) {
	        				if (atributos[i]!=null && !"".equals(atributos[i])) {
			        			if (diferente && !getSisAtributosRegDem(regDemandaRegd, grupoAtributo.getCodSga()).contains((SisAtributoSatb) this.buscar(SisAtributoSatb.class, Long.valueOf(atributos[i])))) {
			        				diferente = true;
			        			} else {
			        				diferente = false;
			        			}
			        			todosValoresVazio = false;
			    			}	
	        			}
	        			if (diferente && !todosValoresVazio){
	        				itRegDemandas.remove();
	        				retorno = true;
	            		}   	    				
   	    			}
   	    			
   	    		}
   	    		//Se for TEXT Field ou TEXT AREA
   	    		else if (tipoCampo.equals(SisTipoExibicGrupoDao.TEXT) || tipoCampo.equals(SisTipoExibicGrupoDao.TEXTAREA)) {   	    			
   	    			if(parametrosFiltroDemanda.get(nomeCampo) != null){
   	    				String[] valores = null;
   	    				if (parametrosFiltroDemanda.get(nomeCampo) instanceof String) {
   	   	    				valores = new String[1];
   	   	    				valores[0] = (String)parametrosFiltroDemanda.get(nomeCampo);
   						} else {
   							valores = (String[])parametrosFiltroDemanda.get(nomeCampo);
   						}
   	    				if (valores[0]!=null && !"".equals(Pagina.trocaNull(valores[0]))) {
   	    					List atributosTextTextArea = getRegDemandaSisAtributoRegdSatbsRegd(regDemandaRegd, grupoAtributo.getCodSga());
   	   	    				if (atributosTextTextArea.size() == 0){

   	   	    					itRegDemandas.remove();
   	                   			filtrarAtributoLivre = false;
   	                   			retorno = true;
   	                   			break;
   	   	    				}
   	   	    				
   	   	    				Iterator itAtributosTextTextArea = atributosTextTextArea.iterator();

   	   	    				while (itAtributosTextTextArea.hasNext() && filtrarAtributoLivre){
   	   	    					DemAtributoDema demAtributoDema = (DemAtributoDema) itAtributosTextTextArea.next();
   	   	    					if (!demAtributoDema.getInformacao().toUpperCase().trim().contains((Pagina.trocaNull(valores[0])).toUpperCase().trim())){

   	   	    						itRegDemandas.remove();
   	   	                			filtrarAtributoLivre = false;
   	   	                			retorno = true;
   	   	                			break;
   	   	    					}
   	   	    					
   	   	    				}	
   	    				}
   	    			}
   	    		//Se for MULTITEXTO
   	    		} else if (tipoCampo.equals(SisTipoExibicGrupoDao.MULTITEXTO)) {
   					   	    			   	    			   					
   					List lAtrib = new ArrayList();
   					if(parametrosFiltroDemanda.get("listaNomesAtributosRequest") != null)
   						lAtrib = (ArrayList) parametrosFiltroDemanda.get("listaNomesAtributosRequest");
   					Iterator lAtribIt = lAtrib.iterator(); 
   					
   					while (lAtribIt.hasNext() && filtrarAtributoLivre) {
   						nomeCampo = Pagina.trocaNull(lAtribIt.next());
   						if (nomeCampo.startsWith("a" + grupoAtributo.getCodSga().toString())){
   							String[] valores = null;
   							if (parametrosFiltroDemanda.get(nomeCampo) instanceof String) {
   								valores = new String[1];
   								valores[0] = (String)parametrosFiltroDemanda.get(nomeCampo);
   							} else {
   								valores = (String[])parametrosFiltroDemanda.get(nomeCampo);
   							}
   							if(parametrosFiltroDemanda.get(nomeCampo) != null && valores[0]!=null && 
   									!"".equals(Pagina.trocaNull(valores[0]))){
   								String codSisAtrib = nomeCampo.substring(nomeCampo.lastIndexOf("_") + 1);
   								SisAtributoSatb sisAtributoSatb = (SisAtributoSatb) this.buscar(SisAtributoSatb.class, Long.valueOf(codSisAtrib));
   								List atributosMultiText = getRegDemandaSisAtributoRegdSatbsRegd(regDemandaRegd, grupoAtributo.getCodSga());
   								if (atributosMultiText.size() == 0 || !getSisAtributosRegDem(regDemandaRegd, grupoAtributo.getCodSga()).contains(sisAtributoSatb)){

   									itRegDemandas.remove();
       	                			filtrarAtributoLivre = false;
       	                			retorno = true;
       	                			break;
   								}
   								Iterator itAtributosMultiTexto = atributosMultiText.iterator();
   								
       		    				while (itAtributosMultiTexto.hasNext() && filtrarAtributoLivre){
       		    					DemAtributoDema demAtributoDema = (DemAtributoDema) itAtributosMultiTexto.next();
       		    					if (demAtributoDema.getSisAtributoSatb().getCodSatb().toString().equals(codSisAtrib)){
       		    						if (!demAtributoDema.getInformacao().toUpperCase().trim().contains((Pagina.trocaNull(parametrosFiltroDemanda.get(nomeCampo))).toUpperCase().trim())){

           		    						itRegDemandas.remove();
               	                			filtrarAtributoLivre = false;
               	                			retorno = true;
               	                			break;
           		    					}
       		    					}
       		    				}
   							}
   						}
   					}    				    			     			 			 
   				//Se for VALIDACAO
   				} else if (tipoCampo.equals(SisTipoExibicGrupoDao.VALIDACAO)) {

   					nomeCampo = "a" + grupoAtributo.getCodSga() + "_Inicio";
   					String filtroParametro = Pagina.trocaNull(parametrosFiltroDemanda.get(nomeCampo));
   					if(parametrosFiltroDemanda.get(nomeCampo) != null && 
   							!"".equals(Pagina.trocaNull(parametrosFiltroDemanda.get(nomeCampo)))){
       				
   						List atributosValidacao = getRegDemandaSisAtributoRegdSatbsRegd(regDemandaRegd, grupoAtributo.getCodSga());
   						
   						if (atributosValidacao.size() == 0){

   							itRegDemandas.remove();
                   			filtrarAtributoLivre = false;
                   			retorno = true;
                   			break;
   						}
   						
   	    				Iterator itAtributosValidacao = atributosValidacao.iterator();
   	    				
   	    				while (itAtributosValidacao.hasNext() && filtrarAtributoLivre){
   	    					DemAtributoDema demAtributoDema = (DemAtributoDema) itAtributosValidacao.next();
   	    					
   	    					if (demAtributoDema.getSisAtributoSatb().getAtribInfCompSatb().equals("dataScript")){
   	    						
   	    						//Modifica as ordens de dia e mï¿½s na data, para ficar no formato MM/DD/YYYY
   	    						filtroParametro = 
   	    							filtroParametro.substring(3, filtroParametro.lastIndexOf("/")+1) +
   	    							filtroParametro.substring(0, filtroParametro.indexOf("/")+1) +
   	    							filtroParametro.substring(filtroParametro.lastIndexOf("/")+1);
   	        					    					
   	        					Date dataFiltro = new Date(filtroParametro);
   	        					
   	        					String valorAtribLivre = demAtributoDema.getInformacao();
   		    					
   	        					//Modifica as ordens de dia e mï¿½s na data, para ficar no formato MM/DD/YYYY
   	        					valorAtribLivre = 
   	        						valorAtribLivre.substring(3, valorAtribLivre.lastIndexOf("/")+1) +
   	        						valorAtribLivre.substring(0, valorAtribLivre.indexOf("/")+1) +
   	        						valorAtribLivre.substring(valorAtribLivre.lastIndexOf("/")+1);
   	        					    					
   	        					Date dataAtribLivre = new Date(valorAtribLivre);
   	        					//Remove da lista se a data estiver estiver fora do intervalo especificado no filtro					    					
   	        					if (dataAtribLivre.compareTo(dataFiltro) < 0){
//   	        						itensRemovidos.add(regDemandaRegd.getCodRegd());
   	        						itRegDemandas.remove();
       	                			filtrarAtributoLivre = false;
       	                			retorno = true;
       	                			break;
   	                    		}
   	    						
   	    						
   	    					} else if (demAtributoDema.getSisAtributoSatb().getAtribInfCompSatb().equals("numeroInteiroScript") ||
   	    							demAtributoDema.getSisAtributoSatb().getAtribInfCompSatb().equals("numeroRealScript") ||
   	    							demAtributoDema.getSisAtributoSatb().getAtribInfCompSatb().equals("valorMonetarioScript") ){
   	        					
   	    						filtroParametro = filtroParametro.replace(".", "");
   	    						filtroParametro = filtroParametro.replace(",", ".");
   	    						
   	    						BigDecimal valorFiltro = new BigDecimal(filtroParametro);
   	    						  					
   	        					BigDecimal valorAtribLivre = new BigDecimal(demAtributoDema.getInformacao().replace(".", "").replace(",", "."));
   	        					    					    					
   	    						//Remove da lista se a data estiver estiver fora do intervalo especificado no filtro
   	        					if (valorAtribLivre.doubleValue() < valorFiltro.doubleValue()){
//   	        						itensRemovidos.add(regDemandaRegd.getCodRegd());
   	        						itRegDemandas.remove();
   	                    			filtrarAtributoLivre = false;
   	                    			retorno = true;
   	                    			break;
   	                    		}
   	        				}
   	    				}
   					}
   					
   					nomeCampo = "a" + grupoAtributo.getCodSga().toString() + "_Fim";
   					filtroParametro = Pagina.trocaNull(parametrosFiltroDemanda.get(nomeCampo));
   					if(parametrosFiltroDemanda.get(nomeCampo) != null && 
   							!"".equals(Pagina.trocaNull(parametrosFiltroDemanda.get(nomeCampo)))){
       				
   						List atributosValidacao = getRegDemandaSisAtributoRegdSatbsRegd(regDemandaRegd, grupoAtributo.getCodSga());
   						
   						if (atributosValidacao.size() == 0){

   							itRegDemandas.remove();
                   			filtrarAtributoLivre = false;
                   			retorno = true;
                   			break;
   						}
   						
   	    				Iterator itAtributosValidacao = atributosValidacao.iterator();
   	    				
   	    				while (itAtributosValidacao.hasNext() && filtrarAtributoLivre){
   	    					DemAtributoDema demAtributoDema = (DemAtributoDema) itAtributosValidacao.next();
   	    					
   	    					if (demAtributoDema.getSisAtributoSatb().getAtribInfCompSatb().equals("dataScript")){
   	    						
   	    						//Modifica as ordens de dia e mï¿½s na data, para ficar no formato MM/DD/YYYY
   	    						filtroParametro = 
   	    							filtroParametro.substring(3, filtroParametro.lastIndexOf("/")+1) +
   	    							filtroParametro.substring(0, filtroParametro.indexOf("/")+1) +
   	    							filtroParametro.substring(filtroParametro.lastIndexOf("/")+1);
   	        					    					
   	        					Date dataFiltro = new Date(filtroParametro);
   	        					
   	        					String valorAtribLivre = demAtributoDema.getInformacao();
   		    					
   	        					//Modifica as ordens de dia e mï¿½s na data, para ficar no formato MM/DD/YYYY
   	        					valorAtribLivre = 
   	        						valorAtribLivre.substring(3, valorAtribLivre.lastIndexOf("/")+1) +
   	        						valorAtribLivre.substring(0, valorAtribLivre.indexOf("/")+1) +
   	        						valorAtribLivre.substring(valorAtribLivre.lastIndexOf("/")+1);
   	        					    					
   	        					Date dataAtribLivre = new Date(valorAtribLivre);
   	        					//Remove da lista se a data estiver estiver fora do intervalo especificado no filtro					    					
   	        					if (dataAtribLivre.compareTo(dataFiltro) > 0){
//   	        						itensRemovidos.add(regDemandaRegd.getCodRegd());
   	        						itRegDemandas.remove();
       	                			filtrarAtributoLivre = false;
       	                			retorno = true;
       	                			break;
   	                    		}
   	        					
   	    					} else if (demAtributoDema.getSisAtributoSatb().getAtribInfCompSatb().equals("numeroInteiroScript") ||
   	    							demAtributoDema.getSisAtributoSatb().getAtribInfCompSatb().equals("numeroRealScript") ||
   	    							demAtributoDema.getSisAtributoSatb().getAtribInfCompSatb().equals("valorMonetarioScript") ){
   	        					
   	    						filtroParametro = filtroParametro.replace(".", "");
   	    						filtroParametro = filtroParametro.replace(",", ".");
   	    						
   	    						BigDecimal valorFiltro = new BigDecimal(filtroParametro);
   			  					
   	        					BigDecimal valorAtribLivre = new BigDecimal(demAtributoDema.getInformacao().replace(".", "").replace(",", "."));
   	        					    					    					
   	    						//Remove da lista se a data estiver estiver fora do intervalo especificado no filtro
   	        					if (valorAtribLivre.doubleValue() > valorFiltro.doubleValue()){
//   	        						itensRemovidos.add(regDemandaRegd.getCodRegd());
   	        						itRegDemandas.remove();
   	                    			filtrarAtributoLivre = false;
   	                    			retorno = true;
   	                    			break;
   	                    		}
   	        				}
   	    					
   	    				}
   					}
   					
       				nomeCampo ="a" + grupoAtributo.getCodSga();
   					String[] atributos = (String[]) parametrosFiltroDemanda.get(nomeCampo);
   					if(parametrosFiltroDemanda.get(nomeCampo) != null){
   	    				String[] valores = (String[])parametrosFiltroDemanda.get(nomeCampo);
   	    				if (valores[0]!=null && !"".equals(Pagina.trocaNull(valores[0]))) {
   	    					List atributosValidacao = getRegDemandaSisAtributoRegdSatbsRegd(regDemandaRegd, grupoAtributo.getCodSga());
   						
   	    					if (atributosValidacao.size() == 0){
   	    						itRegDemandas.remove();
   	    						filtrarAtributoLivre = false;
   	    						retorno = true;
   	    						break;
   	    					}
   						
   	    					Iterator itAtributosValidacao = atributosValidacao.iterator();
   	    				
   	    					while (itAtributosValidacao.hasNext() && filtrarAtributoLivre){
   	    						DemAtributoDema demAtributoDema = (DemAtributoDema) itAtributosValidacao.next();
   	   	    						if (!demAtributoDema.getInformacao().toUpperCase().contains(atributos[0].toUpperCase())){
   	   	    							itRegDemandas.remove();
   	   	    							filtrarAtributoLivre = false;
   	   	    							retorno = true;
   	   	    							break;
   	   	    						}   	    							
   	    					}	
   	    				}
   	    			}
   				}						
   			}
       		
       	} catch (Exception e) {
   			
       		e.printStackTrace();
   		}
       	
   		return retorno;		
   	}    

        
 		
	private boolean aplicarRestricaoGruposUsuario (RegDemandaRegd regDemandaRegd, UsuarioUsu usuario, Set gruposAcesso){
    	
    	boolean restringirPorUSuario = true;
    	
    	try{
    				
			List<Long> listCodGruposAcesso = new ArrayList<Long>();
	        for (Iterator iter = gruposAcesso.iterator(); iter.hasNext();) {
	        	SisAtributoSatb grupoAcesso = (SisAtributoSatb) iter.next();
	        	listCodGruposAcesso.add(grupoAcesso.getCodSatb());
	        }
		
			Set usuarioAtributos = regDemandaRegd.getUsuarioUsuByCodUsuInclusaoRegd().getUsuarioAtributoUsuas();
			
			Iterator itUsuarioAtributos = usuarioAtributos.iterator();
    		
			
			
			while (itUsuarioAtributos.hasNext()){
				
				UsuarioAtributoUsua usuarioAtributo = (UsuarioAtributoUsua) itUsuarioAtributos.next();
				
				if (listCodGruposAcesso.contains(usuarioAtributo.getComp_id().getCodSatb())){
					restringirPorUSuario = false;
				}
			}
    		
    	} catch (Exception e) {
			
       		e.printStackTrace();
    	}
        	
        	return restringirPorUSuario;
    }
    
    private boolean aplicarRestricaoUsuarioEntidadeOrgaoDemanda(RegDemandaRegd regDemandaRegd, UsuarioUsu usuario){
    	
    	boolean retorno = false;
    	
    	try{
    		
    		Set entidadesUsuario = usuario.getEntidadeEnts();
    		
    		Iterator itEntidadesOrgaosDemanda = regDemandaRegd.getEntidadeOrgaoDemandaEntorgds().iterator();
    		
    		boolean usuarioContemAlgumaEntidadeOrgaoDemanda = false;
    		
    		while (itEntidadesOrgaosDemanda.hasNext()){
    			
    			EntidadeEnt entidadeOrgaoDemanda = (EntidadeEnt) itEntidadesOrgaosDemanda.next();
    			
    			if (entidadesUsuario.contains(entidadeOrgaoDemanda)){
    				usuarioContemAlgumaEntidadeOrgaoDemanda = true;
    				break;
    			}    			
    			
    		}
    		
    		if (!usuarioContemAlgumaEntidadeOrgaoDemanda){             			
       			return true;

   			}
    	
    	} catch (Exception e) {
			
   		e.printStackTrace();
		}
    	
    	return retorno;
    }
    
    
    /**
     * Verifica se demanda possui atributo que pertence a mesmo grupo que definido na seção "usuário" com o grupo do atributo sendo do tipo "cadastro de usuario"  
     * 
     * @param regDemandaRegd
     * @param atributosLivresRestritivosVisao
     * @param Usuario
     * @return se true, remove demanda(nao possui atributo restritivo que é igual a atributo do usuario ), se false (possui restritivo que é igual ao do usuário)
     */
       private boolean aplicarRestricaoAtributosLivresRestritivos(RegDemandaRegd regDemandaRegd, List atributosLivresRestritivosVisao, UsuarioUsu usuario) {
       	
    	   boolean retorno = true;
       	
    	   try {
       		      		    		
   			boolean filtrarAtributoLivre = true;
   			
   			// atributos livres de demanda restritivos
   			Iterator itAtributos = atributosLivresRestritivosVisao.iterator();
   			
   			// pega os Atributos definidos para o usuario
   			Set usuarioAtributos = usuario.getUsuarioAtributoUsuas();
   			
   			// lista de atributos definidos para o usuario
   			List codAtributosUsuarios = new ArrayList();
   			Iterator itUsuarioAtributos = usuarioAtributos.iterator();
   			
   			while (itUsuarioAtributos.hasNext()){   
   				
   				UsuarioAtributoUsua usuarioAtributo = (UsuarioAtributoUsua)itUsuarioAtributos.next();   				
   				
   				codAtributosUsuarios.add(usuarioAtributo.getComp_id().getCodSatb());   				
   			
   			}
   			
   			// verifica se ao menos um dos atributos livres é restritivo e caso exista verifica se há atributo livre restritivo com mesmo valor definido no usuario
   			if (itAtributos.hasNext()) {
   			
   				boolean demandaPossuiAlgumAtributoUsuario = false;
	   			while (itAtributos.hasNext() && filtrarAtributoLivre){
	   				
//	   				ObjetoDemanda atributoDemanda = (ObjetoDemanda) itAtributos.next();
	   				VisaoAtributoDemanda atributoVisao = (VisaoAtributoDemanda) itAtributos.next();
	   				SisGrupoAtributoSga grupoAtributo = atributoVisao.getVisaoAtributoDemandaPk().getAtributoDemanda().iGetGrupoAtributosLivres();   				
	   				
	   				String nomeCampo = "a" + grupoAtributo.getCodSga().toString();// nome do campo na tela
	   	    		String tipoCampo = grupoAtributo.getSisTipoExibicGrupoSteg().getCodSteg().toString();// tipo do campo 
	   	    		String tipoCampoCadastroUsuario = "";
	   	    		
	   	    		// se for tipo de cadastro de usuario
	   	    		if(grupoAtributo.getSisTipoExibicGrupoCadUsuSteg()!=null )
	   	    			tipoCampoCadastroUsuario = grupoAtributo.getSisTipoExibicGrupoCadUsuSteg().getCodSteg().toString();
	   	    		
	   	    		//Se for CheckBox ou RadioButton ou ListBox, nï¿½o procura em InformacaoDema
	   	    		if((tipoCampo.equals(SisTipoExibicGrupoDao.CHECKBOX) || tipoCampo.equals(SisTipoExibicGrupoDao.LISTBOX) ||
	   	    				tipoCampo.equals(SisTipoExibicGrupoDao.RADIO_BUTTON) || tipoCampo.equals(SisTipoExibicGrupoDao.COMBOBOX))
	   	    				&& ( tipoCampoCadastroUsuario.equals(SisTipoExibicGrupoDao.CHECKBOX) || tipoCampoCadastroUsuario.equals(SisTipoExibicGrupoDao.LISTBOX) ||
	   	    				tipoCampoCadastroUsuario.equals(SisTipoExibicGrupoDao.RADIO_BUTTON) || tipoCampoCadastroUsuario.equals(SisTipoExibicGrupoDao.COMBOBOX)) ){
	   	    			List atributos = getSisAtributosRegDem(regDemandaRegd, grupoAtributo.getCodSga());
	   	    			//retorno = false;
	   	    	    	int numAtributos = 0;
	   	    			if (atributos != null) {
	   	    				numAtributos = atributos.size();
	   	    			}
	   	    			
	   	    			for(int i = 0; i < numAtributos && filtrarAtributoLivre; i++){
	   	    				// se o atributo do usuario posssui
	   	    				if(codAtributosUsuarios.contains(((SisAtributoSatb)atributos.get(i)).getCodSatb())){
	   	    					demandaPossuiAlgumAtributoUsuario = true;
	   	    					return false;
	   	    				}
	   	    			}   	    			
	   	    		}
		   		}
	   			
	   			// nao possui atributo restritivo algum com valor igual ao do usuario
	   			if (!demandaPossuiAlgumAtributoUsuario){              			
           			return true;
     			} 
	   			
	   		// não possui nenhum atributos livre restritivo
   			} else {
   				retorno = false;
   			}
       		
       	} catch (Exception e) {
   			
       		e.printStackTrace();
   		}
       	
   		return retorno;		
   	}
      
	/**
	 * Retorna todos atributos ativos de demanda ordenados por sequência de apresentação tela campo. 
         * @param codVisao
	 * @return
	 * @throws ECARException
	 */
	public List getAtributosLivresDemandaEhFiltro(Long codVisao) throws ECARException{				
		List retorno = new ArrayList();		
		ObjetoDemanda atributoDemanda = new AtributoDemandaAtbdem();

        try {                   	
			
        	String hql = MessageFormat.format(Util.getHql(ConstantesECAR.PESQUISA_ATRIBUTOS_DEMANDA_VISAO_ATIVOS_QUE_SAO_FILTROS, request.getSession().getServletContext()), codVisao.toString(),"\'S\'", "\'S\'");
		
			Query q = this.getSession().createQuery(hql);
									
			retorno = q.list();
			
        } catch(IOException e){
			this.logger.error(e);
	        throw new ECARException(e);	
        } catch (HibernateException e) {
            this.logger.error(e);
            throw new ECARException("erro.hibernateException");
        }
        return retorno;
	}
    
    /**
     * Devolve uma lista de visaoAtributoDemanda.
     * Essa lista contem objetos de atributo visao
     * @param codVisao
     * @return lista de objetos Atributo Visao ordenados pela ordem do campo na tela.
     * @throws ECARException
     */
    public List getAtributosLivresDemandaDeUmaVisaoEhRestritivo(Long codVisao) throws ECARException {
    	List retorno = new ArrayList();		

        try {                   	
        	StringBuilder query = new StringBuilder("select visaoAtributoDemanda from VisaoAtributoDemanda as visaoAtributoDemanda")
				.append(" where visaoAtributoDemanda.visaoAtributoDemandaPk.atributoDemanda.indAtivoAtbdem = 'S' and visaoAtributoDemanda.indRestritivo = 'S'")
				.append(" and visaoAtributoDemanda.visaoAtributoDemandaPk.visao.codVisao=").append(codVisao.toString())
				.append(" and visaoAtributoDemanda.visaoAtributoDemandaPk.atributoDemanda.sisGrupoAtributoSga is not null")				
				.append(" order by visaoAtributoDemanda.seqApresTelaCampoAtbvis asc");
			Query q = this.getSession().createQuery(query.toString());
									
			retorno = q.list();
			
        } catch (HibernateException e) {
            this.logger.error(e);
            throw new ECARException("erro.hibernateException");
        }
        return retorno;
    }
    
    
    
    /**
     * Retorna os SisAtributos do grupo e das demanda passadas.<br>
     * 
     * @param regDemandaRegd
     * @param codSisGrupoAtributo
     * @return List
     */
    public List getSisAtributosRegDem(RegDemandaRegd regDemandaRegd, Long codSisGrupoAtributo){
    	List listaRetorno = new ArrayList();
    	if (regDemandaRegd.getDemAtributoDemas() != null){
    		Iterator it = regDemandaRegd.getDemAtributoDemas().iterator();
    		while (it.hasNext()){
    			DemAtributoDema demAtributoDema = (DemAtributoDema) it.next();
    			if (demAtributoDema.getSisAtributoSatb().getSisGrupoAtributoSga().getCodSga().equals(codSisGrupoAtributo)){
    				listaRetorno.add(demAtributoDema.getSisAtributoSatb());
    			}
    		}
    	}
    	
    	return listaRetorno;
    	
    }
    
    /**
     * Retorna os ItemEstruturaSisAtributoIettSatbs do grupo e do Iett passados.<br>
     * 
     * @param regDemanda
     * @param codSisGrupoAtributo
     * @return
     */
    public List getRegDemandaSisAtributoRegdSatbsRegd(RegDemandaRegd regDemanda, Long codSisGrupoAtributo){
    	List listaRetorno = new ArrayList();
    	if (regDemanda.getDemAtributoDemas() != null){
    		Iterator it = regDemanda.getDemAtributoDemas().iterator();
    		while (it.hasNext()){
    			DemAtributoDema demAtributoDema = (DemAtributoDema) it.next();
    			if (demAtributoDema.getSisAtributoSatb().getSisGrupoAtributoSga().getCodSga().equals(codSisGrupoAtributo)){
    				listaRetorno.add(demAtributoDema);
    			}
    		}
    	}
    	
    	return listaRetorno;
    	
    }
    
//    public List getDemandasAClassificar(RegDemandaRegd regDemandaPesq, boolean ehFiltro, UsuarioUsu usuario)
//    	throws HibernateException, ECARException {
//    	    
//    	List retorno = null;
//    	try{
//	    	StringBuilder query = new StringBuilder(
//	    		" select regDemanda from RegDemandaRegd as regDemanda ")
//	    		.append(" join regDemanda.sitDemandaSitd as situacaoDemanda ")	    		 
//	    		.append(" where situacaoDemanda.indPrimeiraSituacaoSitd = 'S'")	    		
//	    		.append(" order by regDemanda.codRegd asc ");
//	    	
//			Query q = this.getSession().createQuery(query.toString());
//										
//			retorno = q.list();
//			
////			aplicarRestricaoUsuarioAtributoRegDemanda(retorno, usuario);
//			
//			if(ehFiltro)
//				retorno = aplicarFiltrosDinamicosRegDemanda(retorno);
//		
//	    } catch(HibernateException e){
//			this.logger.error(e);
//	        throw new ECARException(e);
//	    }
//    	
//    	return retorno;
//    }
    
    /**
     * 
     * @param codVisao
     * @return
     * @throws HibernateException
     * @throws ECARException
     */
    public List<RegDemandaRegd> getDemandasVisao(Long codVisao) throws HibernateException, ECARException {
    
    	List<RegDemandaRegd> listaDemandas = null;
    	
    	Criteria crit = session.createCriteria(RegDemandaRegd.class);
    	
    	crit.add(Restrictions.eq("visaoDemandasVisDem.codVisao", codVisao));
    	
    	listaDemandas = crit.list();
    	
    	return listaDemandas;
    }
    
    /*
     * CÓDIGOS CRIADOS PARA MUDANÇA DE VISÃO DE DEMANDAS
     * 
     * 1 - VISUALIZA DEMANDAS QUE PERTENCEM A GRUPOS DE ACESSOS DO USUARIO E QUE PERTECEM A UMA VISAO ESPECIFICA
     * 
     */
	/**
	 * 
         * @param visao
         * @param usuario
	 * @param ehFiltro
	 * @return
	 * @throws HibernateException
	 * @throws ECARException
	 */
    public List getDemandasComPermissaoNosGruposAcessosUsuario(
    		UsuarioUsu usuario,
    		VisaoDemandasVisDem visao, 
    		boolean ehFiltro
    		) throws HibernateException, ECARException {
    	    
    	List retorno = null;
    	try{
    		
    		Set<SisAtributoSatb> gruposAcesso = ((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")).getGruposAcesso();
    		UsuarioUsu usuarioSessao = ((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario();
    		VisaoSituacaoDemandaDao visaoSituacaoDemandaDao = new VisaoSituacaoDemandaDao(request);
    		String codStabString = "";
    		String codSitString = "";
    		String codUsuarioString = "";
    		String hqlPrefixo = "";
    		String hql = null;
    		String codEntUsu = "";
    		
    		Iterator<SisAtributoSatb> it = gruposAcesso.iterator();
    		
    		int counter = 0;
    		while(it.hasNext()) {
    			if (counter==0)
    				codStabString += it.next().getCodSatb().toString();
    			else
    				codStabString += "," + it.next().getCodSatb().toString();
    			counter++;
    		}
    		
    		if (codStabString.length()>0) {
    			codStabString = "(" + codStabString + ")";
    			try { 
    				
    				if (visao!=null) {    				
    					
    					codSitString  = "(" + visaoSituacaoDemandaDao.getSituacoesVisaoIdsString(visao, VisaoSituacaoDemandaDao.SITUACAO_VISAO_FILTRO) + ")";
    					codUsuarioString =  "(" + usuario.getCodUsu().toString() + ")";
    					if (!usuario.recuperarEntidadesUsuarioIdsString().equals("")){
    						codEntUsu = "(" + usuario.recuperarEntidadesUsuarioIdsString() + ")";
    					}
    					
    					//se a demanda está sem situação
    					if (!codSitString.equals("()")){
    						if (hqlPrefixo.length()>0)
    							hqlPrefixo += " and ";    							
    						hqlPrefixo += MessageFormat.format(Util.getHql(ConstantesECAR.PESQUISA_DEMANDAS_DADO_GRUPOS_ACESSOS_E_VISAO_COM_SITUACAO, request.getSession().getServletContext()), codSitString);
    					}else{
						if (hqlPrefixo.length()>0)
							hqlPrefixo += " and ";    							
						hqlPrefixo += MessageFormat.format(Util.getHql(ConstantesECAR.PESQUISA_DEMANDAS_DADO_GRUPOS_ACESSOS_E_VISAO_SEM_SITUACAO, request.getSession().getServletContext()), visao.getCodVisao());
    					}

    					// se for ver somente demandas que o usuario incluiu
	    				if (visao.getVisualizarDemandasIncluidasUsuario()!=null && visao.getVisualizarDemandasIncluidasUsuario().equals("S")) {
	    					if (hqlPrefixo.length()>0)
    							hqlPrefixo += " and ";
	    					hqlPrefixo += MessageFormat.format(Util.getHql(ConstantesECAR.PESQUISA_DEMANDAS_DADO_GRUPOS_ACESSOS_E_VISAO_USUARIO_INCLUSAO, request.getSession().getServletContext()), codUsuarioString);
	    				} 
	    				
	    				// se tiver regra de entidade solucionadora
	    				if(visao.getUtilizarRegraEntidadeSolucionadora()!=null && visao.getUtilizarRegraEntidadeSolucionadora().equals("S")) {
	    					if (!codEntUsu.equals("")){
		    					if (hqlPrefixo.length()>0)
	    							hqlPrefixo += " and ";
		    					hqlPrefixo += MessageFormat.format(Util.getHql(ConstantesECAR.PESQUISA_DEMANDAS_DADO_GRUPOS_ACESSOS_E_VISAO_ENTIDADE_SOLUCIONADORA, request.getSession().getServletContext()), codEntUsu);
	    					}
	    					else{
	    						return new ArrayList();
	    					}
	    				} 		
	    				
	    				if (hqlPrefixo.length()>0) {
	    					hqlPrefixo += " and ";
	    				}
	    				
	    				hql = MessageFormat.format(Util.getHql(ConstantesECAR.PESQUISA_DEMANDAS_DADO_GRUPOS_ACESSOS_E_VISAO, request.getSession().getServletContext()), hqlPrefixo, codStabString); 

	    				
	    				StringBuilder query = new StringBuilder(hql);
	
	    				Query q = this.getSession().createQuery(query.toString());
	
	    				retorno = q.list();
    				} else {
    					return null;
    				}
    				
    			} catch(IOException e){
    				this.logger.error(e);
    		        throw new ECARException(e);
    		    }
    			
    			
    		} else {
    			return null;
    		}
			
			aplicarRestricoesRegDemanda(retorno, usuario, gruposAcesso, true, visao.getCodVisao());
			
			if(ehFiltro && visao!=null)
				retorno = aplicarFiltrosDinamicosRegDemanda(retorno, visao.getCodVisao());
		
	    } catch(HibernateException e){
			this.logger.error(e);
	        throw new ECARException(e);
	    }
    	
	    return retorno;
    }
    
//    public List getDemandasComPermissaoNosGruposAcessosUsuario(
//    		UsuarioUsu usuario,
//    		List<VisaoDemandasVisDem> visoes, 
//    		boolean ehFiltro
//    		) throws HibernateException, ECARException {
//    	    
//    	List retorno = null;
//    	try{
//    		
//    		Set<SisAtributoSatb> gruposAcesso = ((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")).getGruposAcesso();
//    		UsuarioUsu usuarioSessao = ((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario();
//    		
//    		String codStabString = "";
//    		String codSitString = "";
//    		String codUsuarioString = "";
//    		String hqlPrefixo = "";
//    		String hql = null;
//    		
//    		Iterator<SisAtributoSatb> it = gruposAcesso.iterator();
//    		
//    		int counter = 0;
//    		while(it.hasNext()) {
//    			if (counter==0)
//    				codStabString += it.next().getCodSatb().toString();
//    			else
//    				codStabString += "," + it.next().getCodSatb().toString();
//    			counter++;
//    		}
//    		
//    		if (codStabString.length()>0) {
//    			codStabString = "(" + codStabString + ")";
//    			try { 
//    				
//    				if (visoes!=null) {    				
//    					
//    					codSitString  = "(" + VisaoDemandasVisDem.recuperarSituacoesDemandasIdsString(visoes) + ")";
//    					codUsuarioString =  "(" + usuario.getCodUsu().toString() + ")";
//    					
//    					//se a demanda está sem situação
//    					if (!codSitString.equals("()")){
//    						if (hqlPrefixo.length()>0)
//    							hqlPrefixo += " and ";    							
//    						hqlPrefixo += MessageFormat.format(Util.getHql(ConstantesECAR.PESQUISA_DEMANDAS_DADO_GRUPOS_ACESSOS_E_VISAO_COM_SITUACAO, request.getSession().getServletContext()), codSitString);
//    					}
//
//    					// se for ver somente demandas que o usuario incluiu
//	    				if (visao.getVisualizarDemandasIncluidasUsuario()!=null && visao.getVisualizarDemandasIncluidasUsuario().equals("S")) {
//	    					if (hqlPrefixo.length()>0)
//    							hqlPrefixo += " and ";
//	    					hqlPrefixo += MessageFormat.format(Util.getHql(ConstantesECAR.PESQUISA_DEMANDAS_DADO_GRUPOS_ACESSOS_E_VISAO_USUARIO_INCLUSAO, request.getSession().getServletContext()), codUsuarioString);
//	    				} 
//	    				
//	    				// se tiver regra de entidade solucionadora
//	    				if(visao.getVisualizarDemandasIncluidasUsuario()!=null && visao.getUtilizarRegraEntidadeSolucionadora().equals("S")) {
//	    					if (hqlPrefixo.length()>0)
//    							hqlPrefixo += " and ";
//	    					hqlPrefixo += MessageFormat.format(Util.getHql(ConstantesECAR.PESQUISA_DEMANDAS_DADO_GRUPOS_ACESSOS_E_VISAO_ENTIDADE_SOLUCIONADORA, request.getSession().getServletContext()), new String[]{});
//	    				} 		
//	    				
//	    				if (hqlPrefixo.length()>0) {
//	    					hqlPrefixo += " and ";
//	    				}
//	    				
//	    				hql = MessageFormat.format(Util.getHql(ConstantesECAR.PESQUISA_DEMANDAS_DADO_GRUPOS_ACESSOS_E_VISAO, request.getSession().getServletContext()), hqlPrefixo, codStabString); 
//
//	    				
//	    				StringBuilder query = new StringBuilder(hql);
//	
//	    				Query q = this.getSession().createQuery(query.toString());
//	
//	    				retorno = q.list();
//    				} else {
//    					return null;
//    				}
//    				
//    			} catch(IOException e){
//    				this.logger.error(e);
//    		        throw new ECARException(e);
//    		    }
//    			
//    			
//    		} else {
//    			return null;
//    		}
//			
//			aplicarRestricoesRegDemanda(retorno, usuario, gruposAcesso, true);
//			
//			if(ehFiltro && visao!=null)
//				retorno = aplicarFiltrosDinamicosRegDemanda(retorno, visao.getCodVisao());
//		
//	    } catch(HibernateException e){
//			this.logger.error(e);
//	        throw new ECARException(e);
//	    }
//    	
//	    return retorno;
//    }
    
    /**
     *
     * @param valoresFiltroObjeto
     * @param valorPropriedade
     * @return
     */
    public boolean ehValorPropriedadeDiferenteTodosValoresFiltros(Object valoresFiltroObjeto, String valorPropriedade) {
    	
    	boolean diferente = true;
    	boolean todosValoresVazio = true;
    	String[] valoresFiltro = null;
    	
    	if (valoresFiltroObjeto instanceof String[]) {
    		valoresFiltro = (String[])valoresFiltroObjeto;
		} else  {
			valoresFiltro = new String[1];
			valoresFiltro[0] = (String)valoresFiltroObjeto;
		}
    	
    	// filtro com um valor
    	if (valoresFiltro.length==1) {
			if (valoresFiltro[0]!=null && valoresFiltro[0].length()>0) {
				if (valorPropriedade != null) {
					if(!valorPropriedade.toString().toUpperCase().equals((valoresFiltro[0].toUpperCase()))){
						diferente = true;
					} else {
						diferente = false;
					}
				} else {
					diferente = false;
				}
				todosValoresVazio = false;
			} 
			
		// filtro com mais de um valor
		} else {
			for (int i=0; i<valoresFiltro.length;i++) {
				if (!"".equals(valoresFiltro[i])) {
					if (valorPropriedade != null) {
	        			if (valorPropriedade.toString().toUpperCase().equals((valoresFiltro[i].toUpperCase()))) {
	        				diferente = false;
	        			}						
					} else {
						diferente = false;
					}
        			todosValoresVazio = false;
    			}
			}	
		}	
    	
    	if(todosValoresVazio) {
			diferente = false;			
		}
    	
    	return diferente;	
    }

    
}