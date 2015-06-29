/*
 * Created on 15/02/2005
 *
 */
package ecar.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import comum.database.Dao;
import comum.util.Data;
import comum.util.FileUpload;
import comum.util.Pagina;
import comum.util.Util;

import ecar.bean.AcessoRelatorio;
import ecar.exception.ECARException;
import ecar.pojo.AcompRefItemLimitesArli;
import ecar.pojo.AcompReferenciaAref;
import ecar.pojo.AcompReferenciaItemAri;
import ecar.pojo.AcompRelatorioArel;
import ecar.pojo.Cor;
import ecar.pojo.ItemEstUsutpfuacIettutfa;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.SituacaoSit;
import ecar.pojo.TipoAcompFuncAcompTafc;
import ecar.pojo.TipoFuncAcompTpfa;
import ecar.pojo.UsuarioAtributoUsua;
import ecar.pojo.UsuarioUsu;
import ecar.util.Dominios;

/**
 * @author evandro
 * @author felipe
 */
public class AcompRelatorioDao extends Dao {

    /**
     *
     */
    public static int OPERACAO_PERMITIDA = 1;
    /**
     *
     */
    public static int OPERACAO_NEGADA_USUARIO_SEM_PERMISSAO = 2;
    /**
     *
     */
    public static int OPERACAO_NEGADA_ACOMPANHAMENTO_REFERENCIA_LIBERADO = 3;
    /**
     *
     */
    public static int OPERACAO_NEGADA_POSICAO_LIBERADA = 4;
    /**
     *
     */
    public static int OPERACAO_NEGADA_FUNCAO_SUPERIOR_LIBERADA = 5;
    /**
     *
     */
    public static int OPERACAO_NEGADA_POSICAO_EM_EDICAO = 6;
    /**
     *
     */
    public static int OPERACAO_NEGADA_DATA_ULTRAPASSADA = 7;
    
    /**
     *
     * @param request
     */
    public AcompRelatorioDao(HttpServletRequest request) {
		super();
		this.request = request;
	}

    
    
    /**
     * Verifica se a data limite para emiss�o de um parecer venceu ( ou seja, � maior que a data atual)
     * @param funcao
     * @param acompReferenciaItem
     * @return
     * @throws ECARException
     */
    public boolean isDataLimiteParecerVencida(TipoFuncAcompTpfa funcao, AcompReferenciaItemAri acompReferenciaItem) throws ECARException{
        //Date dataLimite = new AcompReferenciaItemDao(request).getAcompRefItemLimitesByAcompReferenciaItemTipoFuncAcomp(funcao, acompReferenciaItem).getDataLimiteArli();
    	
        AcompRefItemLimitesArli arli = new AcompReferenciaItemDao(request).getAcompRefItemLimitesByAcompReferenciaItemTipoFuncAcomp(funcao, acompReferenciaItem);
        
        if(arli != null && arli.getDataLimiteArli() != null){
        	Date dataLimite = arli.getDataLimiteArli();
        	
	        // alterado devido ao BUG 3670
	    	Calendar calendarDataAtual = Calendar.getInstance();
	
	    	calendarDataAtual.clear(Calendar.HOUR);
	    	calendarDataAtual.clear(Calendar.HOUR_OF_DAY);
	    	calendarDataAtual.clear(Calendar.MINUTE);
	    	calendarDataAtual.clear(Calendar.SECOND);
	    	calendarDataAtual.clear(Calendar.MILLISECOND);
	    	calendarDataAtual.clear(Calendar.AM_PM);
	
	    	Calendar calendarDataLimite = Calendar.getInstance();
	    	calendarDataLimite.setTime(dataLimite);
	    	
	    	calendarDataLimite.clear(Calendar.HOUR);
	    	calendarDataLimite.clear(Calendar.HOUR_OF_DAY);
	    	calendarDataLimite.clear(Calendar.MINUTE);
	    	calendarDataLimite.clear(Calendar.SECOND);
	    	calendarDataLimite.clear(Calendar.MILLISECOND);
	    	calendarDataLimite.clear(Calendar.AM_PM);
	        
	    	/* PODE SER EDITADO AT� A DATA LIMITE */
	    	if(calendarDataAtual.compareTo(calendarDataLimite) > 0) {
	            return true;
	    	}
        }
    	return false;

        /*
        C�digo anterior:
        
        if(Data.getDataAtual().after(dataLimite))
            return true;
        return false;
        */
    }
    
    /**
     * M�todo que verifica se um Relat�rio de Acompanhamento (Posi��o da Fun��o de Acompanhamento) pode ser acessado.
     * @param usuario
     * @param acompRelatorio Objeto AcompRelatorioRel
     *  @param funcao
     * @return Situa��o utilizando vari�veis est�ticas declardas nesta classe
     * @throws ECARException
     * @author felipe
     */
    public int podeAcessarRelatorio(UsuarioUsu usuario, TipoFuncAcompTpfa funcao, AcompRelatorioArel acompRelatorio) throws ECARException{
    	ItemEstUsutpfuacDao itemEstUsutpfuacDao = new ItemEstUsutpfuacDao(request);
    	List usuariosPermitidos = itemEstUsutpfuacDao.getUsuariosAcompanhamento(acompRelatorio.getAcompReferenciaItemAri().getItemEstruturaIett());
    	
    	if(!usuariosPermitidos.contains(usuario)) {
    		return OPERACAO_NEGADA_USUARIO_SEM_PERMISSAO;
    	} else {
			if(acompRelatorio != null && "S".equals(acompRelatorio.getIndLiberadoArel())) {
				return OPERACAO_PERMITIDA;
			} else {
	    		try {
	    			
	    			itemEstUsutpfuacDao.buscar(acompRelatorio.getAcompReferenciaItemAri().getItemEstruturaIett().getCodIett(), funcao.getCodTpfa());
	    			return OPERACAO_PERMITIDA;
	    		} catch(ECARException e) {
	    			this.logger.error(e);
					return OPERACAO_NEGADA_POSICAO_EM_EDICAO;
	    		}
			}
    	}
    }
    
    /**
     * M�todo que verifica se um Relat�rio de Acompanhamento (Posi��o da Fun��o de Acompanhamento) pode ser gravado.
     * As condi��es para a grava��o s�o as sequintes:
     * (1) Se a Posi��o n�o foi liberada, pode ser gravada
     * (2) Se o Acompanhamento de Refer�ncia a qual pertence o Relat�rio de Acompanhamento possuir status LIBERADO, Relat�rio 
     * N�O PODE ser gravado
     * @param usuario
     * @param acompRelatorio Objeto AcompRelatorioRel
     *  @param funcao
     * @param acompReferenciaItem
     * @return Situa��o utilizando vari�veis est�ticas declardas nesta classe
     * @author felipe
     */
    public int podeGravarRelatorio(UsuarioUsu usuario, TipoFuncAcompTpfa funcao, AcompReferenciaItemAri acompReferenciaItem, AcompRelatorioArel acompRelatorio){
        try{

        	boolean usuarioSemPermissao = true;
        	ItemEstUsutpfuacIettutfa itemEstUsutpfuacIettutfa = new ItemEstUsutpfuacDao(request).buscar(acompReferenciaItem.getItemEstruturaIett().getCodIett(), funcao.getCodTpfa());
        
        	if (itemEstUsutpfuacIettutfa != null){
        		if (itemEstUsutpfuacIettutfa.getUsuarioUsu() != null && itemEstUsutpfuacIettutfa.getUsuarioUsu().equals(usuario)){
        			usuarioSemPermissao = false;
        		} else if(itemEstUsutpfuacIettutfa.getUsuarioUsu() != null && !itemEstUsutpfuacIettutfa.getUsuarioUsu().equals(usuario)){
    				List listaEstruturas = new ItemEstUsutpfuacDao(request).buscarSuperiores(acompReferenciaItem.getItemEstruturaIett().getCodIett(), acompRelatorio.getTipoFuncAcompTpfa().getCodTpfa());
    				Iterator itEstruturas = listaEstruturas.iterator();
    				while (itEstruturas.hasNext()){
    					ItemEstUsutpfuacIettutfa itemEst = (ItemEstUsutpfuacIettutfa) itEstruturas.next();
    			        if(itemEst != null) {							 							
    						if (itemEst.getUsuarioUsu() != null) {
    							if(itemEst.getUsuarioUsu().equals(usuario)){
    								usuarioSemPermissao = false;
    								break;	
    							}
    						} else if (itemEst.getSisAtributoSatb() != null) {
								Iterator itUsuarios = new UsuarioDao().getUsuariosBySisAtributoSatb(itemEst.getSisAtributoSatb()).iterator(); 
			        			while (itUsuarios.hasNext()){
			        				UsuarioUsu usu = (UsuarioUsu) itUsuarios.next(); 
			        				if (usu.equals(usuario)){
			        					usuarioSemPermissao = false;
			        					break;
			        				}
			        			}					        	
					        }
    			        }
    				}   			
        		} else if (itemEstUsutpfuacIettutfa.getSisAtributoSatb() != null){
        			Iterator itUsuarios = new UsuarioDao().getUsuariosBySisAtributoSatb(itemEstUsutpfuacIettutfa.getSisAtributoSatb()).iterator(); 
        			while (itUsuarios.hasNext()){
        				UsuarioUsu usu = (UsuarioUsu) itUsuarios.next(); 
        				if (usu.equals(usuario)){
        					usuarioSemPermissao = false;
        				}
        			}
        			
    				if(usuarioSemPermissao){
    					//adiciona a parte de buscar superiores
    					List listaEstruturas = new ItemEstUsutpfuacDao(request).buscarSuperiores(acompReferenciaItem.getItemEstruturaIett().getCodIett(), acompRelatorio.getTipoFuncAcompTpfa().getCodTpfa());
    					Iterator itEstruturas = listaEstruturas.iterator();
    					while (itEstruturas.hasNext()){
    						ItemEstUsutpfuacIettutfa itemEst = (ItemEstUsutpfuacIettutfa) itEstruturas.next();
    				        if(itemEst != null) {							 							
    							if (itemEst.getUsuarioUsu() != null) {
    								usuarioSemPermissao = !itemEst.getUsuarioUsu().getCodUsu().equals(usuario.getCodUsu());
    								if(!usuarioSemPermissao){
    									break;	
    								}
    							} else if (itemEst.getSisAtributoSatb().getUsuarioAtributoUsuas() != null) {
    								Iterator itUsuarios2 = itemEst.getSisAtributoSatb().getUsuarioAtributoUsuas().iterator();
    								while (itUsuarios2.hasNext()) {
    									UsuarioAtributoUsua usuarioAtributoUsua = (UsuarioAtributoUsua) itUsuarios2.next();
    									if (usuarioAtributoUsua.getUsuarioUsu() != null && usuarioAtributoUsua.getUsuarioUsu().getCodUsu().equals(usuario.getCodUsu())){
    										usuarioSemPermissao = false;
    										break;
    									}
    								}
    							}

    				        }
    					}					
    				}
        		}
        	}
        	if (usuarioSemPermissao){
        		return OPERACAO_NEGADA_USUARIO_SEM_PERMISSAO;
        	}
        	
        } catch(ECARException e){
            return OPERACAO_NEGADA_USUARIO_SEM_PERMISSAO;
        }
        if(acompReferenciaItem.getStatusRelatorioSrl() != null 
                && acompReferenciaItem.getStatusRelatorioSrl().getCodSrl().intValue() == AcompReferenciaItemDao.STATUS_LIBERADO){
            /* se o acompanhamento do item j� foi liberado n�o pode gravar */
            return OPERACAO_NEGADA_ACOMPANHAMENTO_REFERENCIA_LIBERADO;
        }        
        if("S".equals(acompRelatorio.getIndLiberadoArel())){
            return OPERACAO_NEGADA_POSICAO_LIBERADA;
        } else {
            return OPERACAO_PERMITIDA;
        }
    }
    
    
    
    /**
     * M�todo que verifica se um Relat�rio de Acompanhamento (Posi��o da Fun��o de Acompanhamento) pode ser liberado.
     * A condi��o para a libera��o �:
     * (1) Se o Acompanhamento de Refer�ncia a qual pertence o Relat�rio de Acompanhamento possuir status LIBERADO, Relat�rio 
     * N�O PODE ser liberado
     * 	
     * @param usuario
     * @param acompRelatorio Objeto AcompRelatorioRel
     * @param funcao
     * @param acompReferenciaItem
     * @return Situa��o utilizando vari�veis est�ticas declardas nesta classe
     * @throws ECARException
     * @author felipe
     */
    public int podeLiberarRelatorio(UsuarioUsu usuario, TipoFuncAcompTpfa funcao,AcompReferenciaItemAri acompReferenciaItem, AcompRelatorioArel acompRelatorio) throws ECARException{
        try{
        	
        	boolean usuarioSemPermissao = true;
        	ItemEstUsutpfuacIettutfa itemEstUsutpfuacIettutfa = new ItemEstUsutpfuacDao(request).buscar(acompReferenciaItem.getItemEstruturaIett().getCodIett(), funcao.getCodTpfa());
        
        	if (itemEstUsutpfuacIettutfa != null){
        		if (itemEstUsutpfuacIettutfa.getUsuarioUsu() != null && itemEstUsutpfuacIettutfa.getUsuarioUsu().equals(usuario)){
        			usuarioSemPermissao = false;
        		} else if(itemEstUsutpfuacIettutfa.getUsuarioUsu() != null && !itemEstUsutpfuacIettutfa.getUsuarioUsu().equals(usuario)){
    				List listaEstruturas = new ItemEstUsutpfuacDao(request).buscarSuperiores(acompReferenciaItem.getItemEstruturaIett().getCodIett(), acompRelatorio.getTipoFuncAcompTpfa().getCodTpfa());
    				Iterator itEstruturas = listaEstruturas.iterator();
    				while (itEstruturas.hasNext()){
    					ItemEstUsutpfuacIettutfa itemEst = (ItemEstUsutpfuacIettutfa) itEstruturas.next();
    			        if(itemEst != null) {							 							
    						if (itemEst.getUsuarioUsu() != null) {
    							if(itemEst.getUsuarioUsu().equals(usuario)){
    								usuarioSemPermissao = false;
    								break;	
    							}
    						} else if (itemEst.getSisAtributoSatb() != null) {
								Iterator itUsuarios = new UsuarioDao().getUsuariosBySisAtributoSatb(itemEst.getSisAtributoSatb()).iterator(); 
			        			while (itUsuarios.hasNext()){
			        				UsuarioUsu usu = (UsuarioUsu) itUsuarios.next(); 
			        				if (usu.equals(usuario)){
			        					usuarioSemPermissao = false;
			        					break;
			        				}
			        			}					        	
					        }
    			        }
    				}  
        		} else if (itemEstUsutpfuacIettutfa.getSisAtributoSatb() != null){
        			Iterator itUsuarios = new UsuarioDao().getUsuariosBySisAtributoSatb(itemEstUsutpfuacIettutfa.getSisAtributoSatb()).iterator(); 
        			while (itUsuarios.hasNext()){
        				UsuarioUsu usu = (UsuarioUsu) itUsuarios.next(); 
        				if (usu.equals(usuario)){
        					usuarioSemPermissao = false;
        				}
        			}
        			
    				if(usuarioSemPermissao){
    					//adiciona a parte de buscar superiores
    					List listaEstruturas = new ItemEstUsutpfuacDao(request).buscarSuperiores(acompReferenciaItem.getItemEstruturaIett().getCodIett(), acompRelatorio.getTipoFuncAcompTpfa().getCodTpfa());
    					Iterator itEstruturas = listaEstruturas.iterator();
    					while (itEstruturas.hasNext()){
    						ItemEstUsutpfuacIettutfa itemEst = (ItemEstUsutpfuacIettutfa) itEstruturas.next();
    				        if(itemEst != null) {							 							
    							if (itemEst.getUsuarioUsu() != null) {
    								usuarioSemPermissao = !itemEst.getUsuarioUsu().getCodUsu().equals(usuario.getCodUsu());
    								if(!usuarioSemPermissao){
    									break;	
    								}
    							} else if (itemEst.getSisAtributoSatb().getUsuarioAtributoUsuas() != null) {
    								Iterator itUsuarios2 = itemEst.getSisAtributoSatb().getUsuarioAtributoUsuas().iterator();
    								while (itUsuarios2.hasNext()) {
    									UsuarioAtributoUsua usuarioAtributoUsua = (UsuarioAtributoUsua) itUsuarios2.next();
    									if (usuarioAtributoUsua.getUsuarioUsu() != null && usuarioAtributoUsua.getUsuarioUsu().getCodUsu().equals(usuario.getCodUsu())){
    										usuarioSemPermissao = false;
    										break;
    									}
    								}
    							}

    				        }
    					}					
    				}
        			
        		}
        	}
        	if (usuarioSemPermissao){
        		return OPERACAO_NEGADA_USUARIO_SEM_PERMISSAO;
        	}
        	
        } catch(ECARException e){
            return OPERACAO_NEGADA_USUARIO_SEM_PERMISSAO;
        }        
        if(acompReferenciaItem.getStatusRelatorioSrl() != null 
                && acompReferenciaItem.getStatusRelatorioSrl().getCodSrl().intValue() == AcompReferenciaItemDao.STATUS_LIBERADO){
            //se o acompanhamento do item j� foi liberado n�o pode liberar
            return OPERACAO_NEGADA_ACOMPANHAMENTO_REFERENCIA_LIBERADO;
        }        
        /* item j� possui algum status. deve validar para liberar */
        if(acompRelatorio.getIndLiberadoArel() != null && "S".equals(acompRelatorio.getIndLiberadoArel())){
            /* status S (j� est� liberado) */
            return OPERACAO_NEGADA_POSICAO_LIBERADA;      
        }  else {
            return OPERACAO_PERMITIDA;
        }
    }
  
    /**
     * M�todo que verifica se um Relat�rio de Acompanhamento (Posi��o da Fun��o de Acompanhamento) pode ser recuperado.
     * As condi��es para a recupera��o s�o as sequintes:
     * (1) Se o Acompanhamento de Refer�ncia a qual pertence o Relat�rio de Acompanhamento possuir status LIBERADO, Relat�rio 
     * N�O PODE ser recuperado
     * sen�o
     * (2) Se o parecer do relat�rio for S verifica a Posi��o gravada pela fun��o pai:
     * 3.1 Se n�o existir registro gravado pela fun��o pai, ou se a fun��o pai n�o exisitir, filho PODE RECUPERAR posi��o
     * 3.2 Se existir fun��o pai: se o pai tiver liberado posi��o, filho N�O PODE recuperar posi��o. Se o pai n�o tiver liberado 
     * posi��o filho PODE RECUPERAR posi��o
     * 	
     * @param usuario
     * @param funcao
     * @param acompRelatorio Objeto AcompRelatorioRel
     * @param acompReferenciaItem
     * @return Situa��o utilizando vari�veis est�ticas declardas nesta classe
     * @throws ECARException
     * @author felipe
     */
    public int podeRecuperarRelatorio(UsuarioUsu usuario, TipoFuncAcompTpfa funcao, AcompReferenciaItemAri acompReferenciaItem, AcompRelatorioArel acompRelatorio) throws ECARException{
        try{
        	boolean usuarioSemPermissao = true;
        	ItemEstUsutpfuacIettutfa itemEstUsutpfuacIettutfa = new ItemEstUsutpfuacDao(request).buscar(acompReferenciaItem.getItemEstruturaIett().getCodIett(), funcao.getCodTpfa());
        
        	if (itemEstUsutpfuacIettutfa != null){
        		if (itemEstUsutpfuacIettutfa.getUsuarioUsu() != null && itemEstUsutpfuacIettutfa.getUsuarioUsu().equals(usuario)){
        			usuarioSemPermissao = false;
        		} else if(itemEstUsutpfuacIettutfa.getUsuarioUsu() != null && !itemEstUsutpfuacIettutfa.getUsuarioUsu().equals(usuario)){
    				List listaEstruturas = new ItemEstUsutpfuacDao(request).buscarSuperiores(acompReferenciaItem.getItemEstruturaIett().getCodIett(), acompRelatorio.getTipoFuncAcompTpfa().getCodTpfa());
    				Iterator itEstruturas = listaEstruturas.iterator();
    				while (itEstruturas.hasNext()){
    					ItemEstUsutpfuacIettutfa itemEst = (ItemEstUsutpfuacIettutfa) itEstruturas.next();
    			        if(itemEst != null) {							 							
    						if (itemEst.getUsuarioUsu() != null) {
    							if(itemEst.getUsuarioUsu().equals(usuario)){
    								usuarioSemPermissao = false;
    								break;	
    							}
    						} else if (itemEst.getSisAtributoSatb() != null) {
								Iterator itUsuarios = new UsuarioDao().getUsuariosBySisAtributoSatb(itemEst.getSisAtributoSatb()).iterator(); 
			        			while (itUsuarios.hasNext()){
			        				UsuarioUsu usu = (UsuarioUsu) itUsuarios.next(); 
			        				if (usu.equals(usuario)){
			        					usuarioSemPermissao = false;
			        					break;
			        				}
			        			}					        	
					        }
    			        }
    				}			
        		} else if (itemEstUsutpfuacIettutfa.getSisAtributoSatb() != null){
        			Iterator itUsuarios = new UsuarioDao().getUsuariosBySisAtributoSatb(itemEstUsutpfuacIettutfa.getSisAtributoSatb()).iterator(); 
        			while (itUsuarios.hasNext()){
        				UsuarioUsu usu = (UsuarioUsu) itUsuarios.next(); 
        				if (usu.equals(usuario)){
        					usuarioSemPermissao = false;
        				}
        			}
        			
    				if(usuarioSemPermissao){
    					//adiciona a parte de buscar superiores
    					List listaEstruturas = new ItemEstUsutpfuacDao(request).buscarSuperiores(acompReferenciaItem.getItemEstruturaIett().getCodIett(), acompRelatorio.getTipoFuncAcompTpfa().getCodTpfa());
    					Iterator itEstruturas = listaEstruturas.iterator();
    					while (itEstruturas.hasNext()){
    						ItemEstUsutpfuacIettutfa itemEst = (ItemEstUsutpfuacIettutfa) itEstruturas.next();
    				        if(itemEst != null) {							 							
    							if (itemEst.getUsuarioUsu() != null) {
    								usuarioSemPermissao = !itemEst.getUsuarioUsu().getCodUsu().equals(usuario.getCodUsu());
    								if(!usuarioSemPermissao){
    									break;	
    								}
    							} else if (itemEst.getSisAtributoSatb().getUsuarioAtributoUsuas() != null) {
    								Iterator itUsuarios2 = itemEst.getSisAtributoSatb().getUsuarioAtributoUsuas().iterator();
    								while (itUsuarios2.hasNext()) {
    									UsuarioAtributoUsua usuarioAtributoUsua = (UsuarioAtributoUsua) itUsuarios2.next();
    									if (usuarioAtributoUsua.getUsuarioUsu() != null && usuarioAtributoUsua.getUsuarioUsu().getCodUsu().equals(usuario.getCodUsu())){
    										usuarioSemPermissao = false;
    										break;
    									}
    								}
    							}

    				        }
    					}					
    				}
        		}
        	}
        	if (usuarioSemPermissao){
        		return OPERACAO_NEGADA_USUARIO_SEM_PERMISSAO;
        	}
        	
        } catch(ECARException e){
            return OPERACAO_NEGADA_USUARIO_SEM_PERMISSAO;
        }     
        if(acompReferenciaItem.getStatusRelatorioSrl() != null 
                && acompReferenciaItem.getStatusRelatorioSrl().getCodSrl().intValue() == AcompReferenciaItemDao.STATUS_LIBERADO){
            /* se o acompanhamento do item j� foi liberado n�o pode recuperar */
            return OPERACAO_NEGADA_ACOMPANHAMENTO_REFERENCIA_LIBERADO;
        }        
        /* item j� possui algum status. deve validar para recuperar */ 
        if(acompRelatorio.getIndLiberadoArel() != null && "S".equals(acompRelatorio.getIndLiberadoArel())){
            /* procura o acompanhamento do antecedente */
            AcompRelatorioArel acompPai = acompRelatorio;
            /* antecedente n�o foi liberado, pode recuperar*/
            int intRetorno = OPERACAO_PERMITIDA;
            do{
            	TipoFuncAcompTpfa tipoFuncao = acompPai.getTipoFuncAcompTpfa();
                if(tipoFuncao.getTipoFuncAcompTpfa() == null){
                    /* fun��o n�o possui pai, pode recuperar a qualquer hora */
                	break;
                }
            	acompPai = getAcompRelatorio(tipoFuncao.getTipoFuncAcompTpfa(), acompPai.getAcompReferenciaItemAri());
            	if (acompPai != null)
                    if(acompPai.getIndLiberadoArel() != null && "S".equals(acompPai.getIndLiberadoArel())){
                            /* antecedente j� foi liberado, n�o pode recuperar */
                    		intRetorno = OPERACAO_NEGADA_FUNCAO_SUPERIOR_LIBERADA;
                    		break;
                    	}
            	}while(acompPai != null);
               	return intRetorno;
        } else {
            /* status nulo ou S n�o pode recuperar (j� est� em edi��o)*/
            return OPERACAO_NEGADA_POSICAO_EM_EDICAO;            
        }  
    }

    
    /**
     * Cria um objeto AcompRelatorioArel a partir de par�metros passados por request
     * @param acompRelatorio
     * @param request
     * @param campos
     * @throws ECARException
     */
    @SuppressWarnings({"empty-statement", "empty-statement", "empty-statement", "empty-statement"})
    public void setAcompRelatorioArel(AcompRelatorioArel acompRelatorio, HttpServletRequest request, List campos) throws ECARException{

    	boolean isFormUpload = FileUpload.isMultipartContent(request);
    	String cor = null;
    	String situacaoSit = null;
    	String codTpfa = null;
    	String codAri = null;
    	String codUsuario = null;
    	String descricaoArel = null;
    	String complementoArel = null;
    	String indLiberado = null;
    	
    	
    	try { 
	    	if(isFormUpload){
	    		cor = FileUpload.verificaValorCampo(campos,"cor");
				situacaoSit = FileUpload.verificaValorCampo(campos,"situacaoSit");
	    		codTpfa = FileUpload.verificaValorCampo(campos,"codTpfa");
	    		codAri = FileUpload.verificaValorCampo(campos,"codAri");
	    		codUsuario = FileUpload.verificaValorCampo(campos,"codUsuario");;
	        	descricaoArel = FileUpload.verificaValorCampo(campos,"descricaoArel");;
	        	complementoArel = FileUpload.verificaValorCampo(campos,"complementoArel");
	        	indLiberado = FileUpload.verificaValorCampo(campos,"indLiberado");
	    	} else {
	    		cor = Pagina.getParamStr(request,"cor");
				situacaoSit = Pagina.getParamStr(request,"situacaoSit");
	    		codTpfa = Pagina.getParamStr(request,"codTpfa");
	    		codAri = Pagina.getParamStr(request,"codAri");
	    		codUsuario = Pagina.getParamStr(request,"codUsuario");;
	        	descricaoArel = Pagina.getParamStr(request,"descricaoArel");;
	        	complementoArel = Pagina.getParamStr(request,"complementoArel");
	        	indLiberado = Pagina.getParamStr(request,"indLiberado");
	    	}
    	} catch (Exception e) {
			// TODO: handle exception
		}
    	
        if(!"".equals(cor)){
            acompRelatorio.setCor((Cor)this.buscar(Cor.class, Long.valueOf(cor)));
        }
        
        TipoFuncAcompTpfa funcaoAcomp = (TipoFuncAcompTpfa) this.buscar(TipoFuncAcompTpfa.class, Long.valueOf(codTpfa));
        AcompReferenciaItemAri acompReferenciaItem = (AcompReferenciaItemAri) this.buscar(AcompReferenciaItemAri.class, Long.valueOf(codAri));
        
        if(!"".equals(situacaoSit)){
        	SituacaoSit s = (SituacaoSit)this.buscar(SituacaoSit.class, Long.valueOf(situacaoSit));
            acompRelatorio.setSituacaoSit(s);
            if(funcaoAcomp.getIndAtualizaSituacaoCadastro().equals(Dominios.SIM)) {
            	acompReferenciaItem.getItemEstruturaIett().setSituacaoSit(s);
            	System.out.println("Mudar situa��o em Cadastro");
            }
        }        
        
        UsuarioUsu usuario = (UsuarioUsu)this.buscar(UsuarioUsu.class, Long.valueOf(codUsuario));                
        acompRelatorio.setUsuarioUsuUltimaManutencao(usuario);
        
        acompRelatorio.setTipoFuncAcompTpfa(funcaoAcomp);
        acompRelatorio.setAcompReferenciaItemAri(acompReferenciaItem);
        acompRelatorio.setDescricaoArel(Util.normalizaQuebraDeLinha(descricaoArel));
        acompRelatorio.setComplementoArel(Util.normalizaQuebraDeLinha(complementoArel));
        acompRelatorio.setIndLiberadoArel(indLiberado);
    }
    
    /**
     * Grava uma posi��o para um acompanhamento e volta a mensagem a ser exibida para o usu�rio.
     * @param acompRelatorio
     * @param request
     * @param campos
     * @return
     * @throws ECARException
     */
    public String salvar(AcompRelatorioArel acompRelatorio, HttpServletRequest request, List campos) throws ECARException{        
        setAcompRelatorioArel(acompRelatorio, request, campos);                        
        acompRelatorio.setDataInclusaoArel(Data.getDataAtual()); 
        
        //ECAR-93 15/08/2013
        if(acompRelatorio.getSituacaoSit().getCodSit().equals(2L)){
        	
        	CorDao corDao = new CorDao(request);
        	
        	Cor cor = (Cor) corDao.buscar(Cor.class, 10L);
        	
        	acompRelatorio.setCor(cor);
        }
        
        super.salvar(acompRelatorio);
        if("S".equals(acompRelatorio.getIndLiberadoArel()))
            return "acompanhamento.acompRelatorio.liberacao.sucesso";
        else            
            return "acompanhamento.acompRelatorio.inclusao.sucesso";    
    }

    /**
     * Altera uma posi��o para um acompanhamento e volta a mensagem a ser exibida para o usu�rio
     * @param acompRelatorio
     * @param request
     * @param campos
     * @return
     * @throws ECARException
     */
    public String alterar(AcompRelatorioArel acompRelatorio, HttpServletRequest request, List campos) throws ECARException{
        AcompRelatorioArel acompAtual = (AcompRelatorioArel) this.buscar(AcompRelatorioArel.class, acompRelatorio.getCodArel());
        String indLiberadoAcompAtual = acompAtual.getIndLiberadoArel();
        setAcompRelatorioArel(acompRelatorio, request, campos);                
        acompRelatorio.setDataUltManutArel(Data.getDataAtual());
        
        //ECAR-93 15/08/2013
        if(acompRelatorio.getSituacaoSit().getCodSit().equals(2L)){
        	
        	CorDao corDao = new CorDao(request);
        	
        	Cor cor = (Cor) corDao.buscar(Cor.class, 10L);
        	
        	acompRelatorio.setCor(cor);
        }
        
        super.alterar(acompRelatorio);
        if("S".equals(acompRelatorio.getIndLiberadoArel()))
            return "acompanhamento.acompRelatorio.liberacao.sucesso";
        else{
            if("N".equals(indLiberadoAcompAtual) && "N".equals(acompRelatorio.getIndLiberadoArel())){
                return "acompanhamento.acompRelatorio.alteracao.sucesso";
            } else {
                if("S".equals(indLiberadoAcompAtual) && "N".equals(acompRelatorio.getIndLiberadoArel()))
                    return "acompanhamento.acompRelatorio.recuperacao.sucesso";
                else
                    return "acompanhamento.acompRelatorio.alteracao.sucesso";              
            }
                
        }
    }

    
    /**
     * Devolve um acompRelatorioArel, a partir dos objetos passados
     * 
     * @param tipoFuncao
     * @param acompRefItem
     * @return String nomeUsu 
     * @throws ECARException
     */
    public AcompRelatorioArel getAcompRelatorio (TipoFuncAcompTpfa tipoFuncao, 
    						AcompReferenciaItemAri acompRefItem) throws ECARException{
    	        
        AcompRelatorioArel acompRelatorio = new AcompRelatorioArel();
    	              
    	acompRelatorio.setAcompReferenciaItemAri(acompRefItem);
    	acompRelatorio.setTipoFuncAcompTpfa(tipoFuncao);
    	
		/* buscar em AcompRelatorio a partir de ItemEstUsu e de AcompReferenciaItem*/
		List lista = pesquisar(acompRelatorio, new String[] {"codArel","asc"});
		
		for (Iterator it = lista.iterator(); it.hasNext();) {
			acompRelatorio = (AcompRelatorioArel) it.next();
			return acompRelatorio;
		}
    	
    	return null;
    }
    
    /**
     *
     * @param acompReferenciaItem
     * @param usuario
     * @param funcAcomp
     * @return
     * @throws ECARException
     */
    public AcompRelatorioArel criaNovoAcompRelatorio(AcompReferenciaItemAri acompReferenciaItem, UsuarioUsu usuario, TipoFuncAcompTpfa funcAcomp) throws ECARException{
        AcompRelatorioArel acompRelatorio = new AcompRelatorioArel();
        acompRelatorio.setAcompReferenciaItemAri(acompReferenciaItem);
        acompRelatorio.setDataInclusaoArel(Data.getDataAtual());                   
        acompRelatorio.setTipoFuncAcompTpfa(funcAcomp);
        acompRelatorio.setUsuarioUsu(usuario);       
        return acompRelatorio;                    
    }

    
    /**
     * Obter os AcompRelatorioArel de uma lista de aris
     * 
     * @param listAris
     * @return List
     * @throws ECARException
     */
    public List getArelsOfAris (List listAris) throws ECARException{
    	List retorno = new ArrayList();
    	
    	for (Iterator it = listAris.iterator(); it.hasNext();) {
    		AcompReferenciaItemAri ari = (AcompReferenciaItemAri)it.next();
    		if(ari.getAcompRelatorioArels() != null) {
    			retorno.addAll(ari.getAcompRelatorioArels());
    		}
    	}
		return retorno;
    }

    /**
     * Obt�m uma lista de permiss�es de acesso a uma lista de relat�rio de acompanhamento de um usu�rio
     * @param usuario UsuarioUsu
     * @param listTipoFuncAcompTpfa List de TipoFuncAcompTpfa
     * @param listAcompRelatorioArel List de AcompRelatorioArel
     * @param listTipoAcompFuncAcomp 
     * @return List de AcessoRelatorio
     * @throws ECARException
     */
    public List getListaAcessoRelatorio(UsuarioUsu usuario, List listTipoFuncAcompTpfa, List listAcompRelatorioArel, List listTipoAcompFuncAcomp) throws ECARException{
    	List retorno = new ArrayList();
    	ItemEstUsutpfuacDao itemEstUsutpfuacDao = new ItemEstUsutpfuacDao(request);
    	
    	for (Iterator itArel = listAcompRelatorioArel.iterator(); itArel.hasNext();) {
    		AcompRelatorioArel arel = (AcompRelatorioArel)itArel.next();

    		List usuariosPermitidos = itemEstUsutpfuacDao.getUsuariosAcompanhamento(arel.getAcompReferenciaItemAri().getItemEstruturaIett());

    		for (Iterator itTpfa = listTipoFuncAcompTpfa.iterator(); itTpfa.hasNext();) {
        		TipoFuncAcompTpfa tpfa = (TipoFuncAcompTpfa)itTpfa.next();
        		
	    		AcessoRelatorio acessoRelatorio = new AcessoRelatorio();
	    		acessoRelatorio.setArel(arel);
	    		acessoRelatorio.setTpfa(tpfa);
	    	
		    	if(!usuariosPermitidos.contains(usuario)) {
		    		acessoRelatorio.setPermissao(OPERACAO_NEGADA_USUARIO_SEM_PERMISSAO);
		    	} else {
					if(arel != null && Dominios.SIM.equals(arel.getIndLiberadoArel())) {
			    		acessoRelatorio.setPermissao(OPERACAO_PERMITIDA);
					} else {
						
						TipoAcompFuncAcompTafc tafc = null;
						for(Iterator it = listTipoAcompFuncAcomp.iterator(); it.hasNext();){
							TipoAcompFuncAcompTafc tafcTemp = (TipoAcompFuncAcompTafc) it.next();
							if(tafcTemp.getTipoFuncAcompTpfa().equals(tpfa)){
								tafc = tafcTemp;
								break;
							}
						}
						
			    		try {
							if(tafc != null){
								/*
								 * Nova regra (mantis 11289):
								 * Ao exibir as abas de tipos de fun��es de acompanhamento:
								 * Para registro de posi��o OBRIGAT�RIO continua com est� atualmente.
								 */
								if(Dominios.OBRIGATORIO.equals(tafc.getIndRegistroPosicaoTafc())){
									
									boolean usuarioSemPermissao = true;
						        	ItemEstUsutpfuacIettutfa itemEstUsutpfuacIettutfa = new ItemEstUsutpfuacDao(request).buscar(arel.getAcompReferenciaItemAri().getItemEstruturaIett().getCodIett(), tpfa.getCodTpfa());
						        
						        	if (itemEstUsutpfuacIettutfa != null){
						        		if (itemEstUsutpfuacIettutfa.getUsuarioUsu() != null && itemEstUsutpfuacIettutfa.getUsuarioUsu().equals(usuario)){
						        			usuarioSemPermissao = false;
						        		} else if (itemEstUsutpfuacIettutfa.getSisAtributoSatb() != null){
						        			Iterator itUsuarios = new UsuarioDao().getUsuariosBySisAtributoSatb(itemEstUsutpfuacIettutfa.getSisAtributoSatb()).iterator(); 
						        			while (itUsuarios.hasNext()){
						        				UsuarioUsu usu = (UsuarioUsu) itUsuarios.next(); 
						        				if (usu.equals(usuario)){
						        					usuarioSemPermissao = false;
						        				}
						        			}
						        		}
						        	}
						        	if (usuarioSemPermissao){
						        		acessoRelatorio.setPermissao(OPERACAO_NEGADA_POSICAO_EM_EDICAO);
						        	} else {
						        		acessoRelatorio.setPermissao(OPERACAO_PERMITIDA);
						        	}

								}
								/* 
								 * Para tipo de fun��o de acompanhamento que seja OPCIONAL o registro de posi��o no tipo de 
								 * acompanhamento selecionado (TipoAcompFuncAcompTafc.indRegistroPosicaoTafc), 
								 * somente apresentar a aba para o usu�rio que possuir permiss�o 
								 * ou que o acompanhamento esteja liberado.
								 */
								else if(Dominios.OPCIONAL.equals(tafc.getIndRegistroPosicaoTafc())){
									if(usuariosPermitidos.contains(usuario)){										
										acessoRelatorio.setPermissao(OPERACAO_PERMITIDA);										
									}else{
										acessoRelatorio.setPermissao(OPERACAO_NEGADA_USUARIO_SEM_PERMISSAO);
									}
								}
							}
			    		} catch(ECARException e) {
			        		acessoRelatorio.setPermissao(OPERACAO_NEGADA_POSICAO_EM_EDICAO);
			    		}
					}
		    	}
	    		retorno.add(acessoRelatorio);
        	}
    	}
    	return retorno;
    }

    /**
     * Verificar se falta registro para informar parecer quando � informado nova fun��o de acompanhamento no item
     * Mantis: 10715
     * 
     * @param aref
     * @param listItens
     * @return boolean
     * @throws ECARException
     */
    public boolean verificarInexistenciaArel(AcompReferenciaAref aref, List listItens) throws ECARException {
    	AcompReferenciaItemDao acompReferenciaItemDao = new AcompReferenciaItemDao(null);
    	ItemEstUsutpfuacDao itemEstUsutpfuacDao = new ItemEstUsutpfuacDao(null);
    	
    	for (Iterator it = listItens.iterator(); it.hasNext();) {
	        ItemEstruturaIett iett = (ItemEstruturaIett) it.next();
	        AcompReferenciaItemAri acompReferenciaItemAlterar = acompReferenciaItemDao.getAcompReferenciaItemByItemEstruturaIett(aref, iett);
	        Set arelsGravados = acompReferenciaItemAlterar.getAcompRelatorioArels();
	        
	        // obtem as fun��es de acompanhamento do item que emitem posi��o
	        for (Iterator itFuac = itemEstUsutpfuacDao.getFuacEmitePosicaoOrderByFuncAcomp(iett).iterator(); itFuac.hasNext();) {
	        	TipoFuncAcompTpfa funcAcomp = ((ItemEstUsutpfuacIettutfa)itFuac.next()).getTipoFuncAcompTpfa();
	        	
	        	for (Iterator itTafc = aref.getTipoAcompanhamentoTa().getTipoAcompFuncAcompTafcs().iterator(); itTafc.hasNext();) {
	        		TipoAcompFuncAcompTafc tafc = (TipoAcompFuncAcompTafc) itTafc.next();
	        		if(tafc.getTipoFuncAcompTpfa().equals(funcAcomp)){
		        		boolean existeArelParaFuncao = false;
	        			for (Iterator itArelsGravados = arelsGravados.iterator(); itArelsGravados.hasNext();) {
	        				AcompRelatorioArel arel = (AcompRelatorioArel)itArelsGravados.next();
	        				if(arel.getTipoFuncAcompTpfa().equals(funcAcomp)) {
	        	        		existeArelParaFuncao = true;
	        				}
	        			}
			        	if(!existeArelParaFuncao) {
			        		return true;
			        	}
	                }
	        	}
	    	}
	    }
	    return false;
    }
    
        /**
     * Verifica se houve mudan�a na fun��o de acompanhamento do item do Parecer!
     * 
     * @author luanaoliveira
     * @since 27/07/2007
     * @param arel
     * @return boolean - se houve mudan�a retorna true.
     */
    public boolean verificaMudancaFuncaoAcomp(AcompRelatorioArel arel){
    	
    	TipoFuncAcompTpfa funcaoArel = arel.getTipoFuncAcompTpfa();
    	
    	Set listaFuncoesItem = arel.getAcompReferenciaItemAri().getItemEstruturaIett().getItemEstUsutpfuacIettutfas();
    	
    	List listaFuncoes = new ArrayList();
    	
    	if(listaFuncoesItem != null){
    		
    		Iterator it = listaFuncoesItem.iterator();
    		
    		while (it.hasNext()) {
    			ItemEstUsutpfuacIettutfa utfa = (ItemEstUsutpfuacIettutfa)it.next();
    	
    			listaFuncoes.add(utfa.getTipoFuncAcompTpfa());    			
			}   		
    	}
    	
    	return !listaFuncoes.contains(funcaoArel);
    	
    }
    
    /**
     * Verifica se o usu�rio � acompanhamento habilitado a emitir Relat�rio / Parecer 
     * @param usuario
     * @param ari
     * @return
     * @throws ECARException
     */
    public boolean emiteRelatorio(UsuarioUsu usuario, AcompReferenciaItemAri ari) throws ECARException{
    	AcompReferenciaItemDao ariDao = new AcompReferenciaItemDao(request);
    	
    	List listAcompRef = ariDao.getAcompRelatorioArelOrderByFuncaoAcomp(ari);
    	AcompRelatorioDao arelDao = new AcompRelatorioDao(request);
    	
    	boolean emiterelatorio = false;
	
        if (listAcompRef != null)
   			for (Iterator<AcompRelatorioArel> it = listAcompRef.iterator(); it.hasNext();) {
   				AcompRelatorioArel arel = (AcompRelatorioArel) it.next();
   				TipoFuncAcompTpfa tpfa = arel.getTipoFuncAcompTpfa();
   				
   	        	if (arelDao.podeGravarRelatorio(usuario, tpfa, ari , arel ) == AcompRelatorioDao.OPERACAO_PERMITIDA){
   	        		emiterelatorio  = true;
	        		break;
   	        	}
   	        	
   	        	
   	        	/*
   	        	 * Caso o item na estrutura esteja bloqueado, 
   	        	 * verificar para quais fun��es de acompanhamento ela 
   	        	 * est� liberada, mesmo que bloqueada.
   	        	 */
/*   	        if (emiterelatorio){
   	        		//Verificar na tabela do pode bloquear
   	        	} else {
   	        		return emiterelatorio;
   	        	} */   			}

    	return emiterelatorio;
    }
    /***
     * Conta os pareceres relacionados a um ari das funcoes de acompanhamento passadas por par�metro.  
     * @param ari
     * @param listaTpfa - funcoes de acompanhamento
     * @return
     */
    public Long ContaArelsDasFuncoesDoAri(AcompReferenciaItemAri ari, List listaTpfa){
    	List<Long> listaFuncAcomp = new ArrayList<Long>();    	
    	try
    	{        	
        	if (listaTpfa == null | listaTpfa.isEmpty()){
        		return 0l;
        	}

    		for (Object object : listaTpfa) {
				listaFuncAcomp.add(((TipoFuncAcompTpfa)object).getCodTpfa());
			}
    		
    		StringBuilder sb = new StringBuilder(  );
        	sb.append( " select count(*) from AcompRelatorioArel as AREL " );    		
        	sb.append( " where AREL.acompReferenciaItemAri.codAri = :codAri ");
        	sb.append( " and AREL.tipoFuncAcompTpfa.codTpfa in (:listaFuncAcomp)");
        	
        	
//        	StringBuilder sb = new StringBuilder("select estruturaAtributo from EstruturaAtributoEttat as estruturaAtributo ");
//        	sb.append(" where estruturaAtributo.comp_id.codAtb in (select atributo from AtributosAtb as atributo where atributo.funcaoFun.codFun = :codFun) ");
//        	sb.append(" and estruturaAtributo.estruturaEtt.codEtt = :codEtt order by estruturaAtributo.seqApresentTelaCampoEttat asc");	

        	
        	
			Query query = this.getSession().createQuery(sb.toString());
			query.setParameterList("listaFuncAcomp", listaFuncAcomp);
			query.setLong("codAri", ari.getCodAri());
			query.setMaxResults(1);

			Long valor = (Long)query.uniqueResult();
			
			return valor;
        	
    		
    	} catch ( HibernateException e )
        {
            e.printStackTrace( System.out );
            this.logger.error( e );

            return 0l;
        }
    	
/*    	
        try
        {	
        	StringBuilder sb = new StringBuilder(  );
        	sb.append( " select count(*) from AcompRealFisicoLocalArfl as ARFL " );			
			sb.append( " where ARFL.localItemLit.codLit in (:locais) " );			
			sb.append( " and ARFL.acompRealFisicoArf.itemEstrtIndResulIettr.itemEstruturaIett.codIett = :codIett " );
			Query query = this.getSession().createQuery(sb.toString());
			query.setParameterList("locais", codigosDeLocais);
			query.setLong("codIett", codIett);
			query.setMaxResults(1);

			Long valor = (Long)query.uniqueResult();
			
			return valor;
        
        } catch ( HibernateException e )
        {
            e.printStackTrace( System.out );
            this.logger.error( e );

            return 0l;
        }
*/
    }    

    /**
     * Verifica se a data inicial para emissão de um parecer foi atingida
     * Permite registrar informação somente a partir da data de início do acompanhamento
     * 
     * @param funcao
     * @param ari
     * @return
     * @throws ECARException
     */
    public boolean isDataInicialParecerAtingida(AcompReferenciaItemAri ari) throws ECARException{
    	Calendar dataAtual = Calendar.getInstance();
    	
    	dataAtual.clear(Calendar.HOUR);
    	dataAtual.clear(Calendar.HOUR_OF_DAY);
    	dataAtual.clear(Calendar.MINUTE);
    	dataAtual.clear(Calendar.SECOND);
    	dataAtual.clear(Calendar.MILLISECOND);
    	
    	Calendar dataInicioAcomp = Calendar.getInstance();
    	
    	dataInicioAcomp.setTime(ari.getDataInicioAri());
    	
    	dataInicioAcomp.clear(Calendar.HOUR);
    	dataInicioAcomp.clear(Calendar.HOUR_OF_DAY);
    	dataInicioAcomp.clear(Calendar.MINUTE);
    	dataInicioAcomp.clear(Calendar.SECOND);
    	dataInicioAcomp.clear(Calendar.MILLISECOND);
    	
    	return !dataAtual.before(dataInicioAcomp);
    }
    
}
