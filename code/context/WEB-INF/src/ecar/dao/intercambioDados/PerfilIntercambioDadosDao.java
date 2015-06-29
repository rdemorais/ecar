/*
 * Criado em 02/12/2004
 *
 */
package ecar.dao.intercambioDados;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import comum.database.Dao;
import comum.util.ConstantesECAR;
import comum.util.Pagina;
import comum.util.Util;

import ecar.dao.AtributoDao;
import ecar.exception.ECARException;
import ecar.pojo.AtributosAtb;
import ecar.pojo.EstruturaEtt;
import ecar.pojo.SisAtributoSatb;
import ecar.pojo.SituacaoSit;
import ecar.pojo.TextosSiteTxt;
import ecar.pojo.UsuarioUsu;
import ecar.pojo.intercambioDados.PerfilIntercambioDadosPflid;
import ecar.pojo.intercambioDados.funcionalidade.PerfilIntercambioDadosCadastroPidc;
import ecar.pojo.intercambioDados.funcionalidade.TipoFuncionalidadeEnum;
import ecar.pojo.intercambioDados.tecnologia.DadosTecnologiaPerfilTxtDtpt;
import ecar.pojo.intercambioDados.tecnologia.TipoTecnologiaEnum;
import ecar.util.Dominios;

/**
 *  
 */
public class PerfilIntercambioDadosDao extends Dao {
	
	public static final String ATRIBUTO_ATB_VALOR_ASSOCIACAO = "descricaoR1";
	public static final String ATRIBUTO_ATB_PADRAO_TIPO_EMPREENDIMENTO = "descricaoR3";
	
	public PerfilIntercambioDadosDao() {
		super();
	}
	
	
    /**
     * Construtor. Chama o Session factory do Hibernate
     * @param request
     */
    public PerfilIntercambioDadosDao( HttpServletRequest request )
    {
        super();
        this.request = request;
    }

    /**
     * Atribui os valores dos atributos passados por request a um objeto
     * perfilIntercambioDadospflid
     *
     * @param request
     * @param perfilIntercambioDadospflid
     * @throws ECARException
     */
    public void setPerfilIntercambioDadosPflid(HttpServletRequest request, PerfilIntercambioDadosPflid perfilIntercambioDados) throws ECARException { 	

    	perfilIntercambioDados.setCodPflid( (Long) request.getSession().getAttribute("codPflid"));
   		
    	String nomePflid = Pagina.getParamStr(request, "nomePflid");

    	if (nomePflid != null && !nomePflid.equals(Dominios.STRING_VAZIA)){
    		perfilIntercambioDados.setNomePflid(Pagina.getParamStr(request, "nomePflid"));
        }	
	
   		perfilIntercambioDados.setCodTipoServicoPflid(new Long(Dominios.PERFIL_IDENTIFICADOR_SERVICO));
   		perfilIntercambioDados.setNomeTipoServicoPflid(Dominios.PERFIL_NOME_SERVICO);
   		perfilIntercambioDados.setIndModoProcessamentoPflid(Dominios.PERFIL_MODO_PROCESSAMENTO_MANUAL);
   		perfilIntercambioDados.setCodSistemaDestinoPflid(Long.valueOf(Dominios.PERFIL_IDENTIFICADOR_SISTEMA_DESTINO));
    	perfilIntercambioDados.setNomeSistemaDestinoPflid(Dominios.PERFIL_NOME_SISTEMA_DESTINO);
   		perfilIntercambioDados.setCodSistemaOrigemPflid(Long.valueOf(Dominios.PERFIL_IDENTIFICADOR_SISTEMA_ORIGEM));
   		perfilIntercambioDados.setNomeSistemaOrigemPflid(Dominios.PERFIL_NOME_SISTEMA_ORIGEM);
   		
   		String indUsuProcessamentoAssociacaoItemPflid = Pagina.getParamStr(request, "indUsuProcessamentoAssociacaoItemPflid");
    	if (indUsuProcessamentoAssociacaoItemPflid != null && !indUsuProcessamentoAssociacaoItemPflid.equals(Dominios.STRING_VAZIA)){
    		perfilIntercambioDados.setIndUsuarioProcessamentoAssociacaoItemPflid(indUsuProcessamentoAssociacaoItemPflid);
        }

    	String idStrUsuarioImportacao = Pagina.getParamStr(request, "usuarioUsuImportacao");
    	if (idStrUsuarioImportacao != null && !idStrUsuarioImportacao.equals(Dominios.STRING_VAZIA)) {
            perfilIntercambioDados.setUsuarioImportacao((UsuarioUsu) this.buscar(UsuarioUsu.class, Long.valueOf(idStrUsuarioImportacao)));
        } else {
        	perfilIntercambioDados.setUsuarioImportacao(null);
        }
    	
    	String indAvisoEmailAtivo = Pagina.getParamStr(request, "indAtivoAvisoImpPflid");
    	if (indAvisoEmailAtivo != null && !indAvisoEmailAtivo.equals(Dominios.STRING_VAZIA)) {
    		perfilIntercambioDados.setIndAtivoAvisoImpPflid(indAvisoEmailAtivo);
        }
    	
    	String idStrGrupoAcessoEnvioEmail = Pagina.getParamStr(request, "sisAtributoSatbAcessoEnvioEmailImp");
    	if (idStrGrupoAcessoEnvioEmail != null && !idStrGrupoAcessoEnvioEmail.equals(Dominios.STRING_VAZIA)) {
    		SisAtributoSatb grupoAcessoEnvioEmail = (SisAtributoSatb) this.buscar(SisAtributoSatb.class, Long.valueOf(idStrGrupoAcessoEnvioEmail));
    		
    		perfilIntercambioDados.setGrupoAcessoEnvioEmailPflid(grupoAcessoEnvioEmail);
        } else {
        	perfilIntercambioDados.setGrupoAcessoEnvioEmailPflid(null);
        }
    	
    	
    	String idStrTextoEnvioEmail = Pagina.getParamStr(request, "textosSiteTxt");
    	if (idStrTextoEnvioEmail != null && !idStrTextoEnvioEmail.equals(Dominios.STRING_VAZIA)) {
    		TextosSiteTxt textoEmail = (TextosSiteTxt) this.buscar(TextosSiteTxt.class, Long.valueOf(idStrTextoEnvioEmail));
    		
    		perfilIntercambioDados.setComposicaoEmailPflid(textoEmail);
        } else {
        	perfilIntercambioDados.setComposicaoEmailPflid(null);
        }

    	perfilIntercambioDados.setIndAtivoPflid(Dominios.SIM);
    	
    	setDadosCadastroPerfil(request, perfilIntercambioDados);
	    	
    	setDadosTecnologiaPerfil(request, perfilIntercambioDados);

    	
    	
    } // fim setPerfilIntercambioDadosPflid


	private void setDadosTecnologiaPerfil(HttpServletRequest request,PerfilIntercambioDadosPflid perfilIntercambioDados) {
		//TipoTecnologiaEnum tipoTecnologia = TipoTecnologiaEnum.valueOf(Pagina.getParamInt(request, "tipoTecnologia"));
		TipoTecnologiaEnum tipoTecnologia = TipoTecnologiaEnum.ARQUIVOTEXTO_PACINTER;
    	
    	if (tipoTecnologia.equals(TipoTecnologiaEnum.ARQUIVOTEXTO_PACINTER)) {
    		//Obtem a tecnologia que o perfil possui.
    		DadosTecnologiaPerfilTxtDtpt tecnologiaTxt = (DadosTecnologiaPerfilTxtDtpt)perfilIntercambioDados.getDadosTecnologiaPerfilDtp();
    		
    		//Se a tecnologia for nula um novo objeto será criado.
    		if (tecnologiaTxt == null){
    			tecnologiaTxt = new DadosTecnologiaPerfilTxtDtpt();
    		}
    		
    		//Encoding
    		tecnologiaTxt.setEncodeDtp(Dominios.ENCODING_DEFAULT);
    		
    		//Rejeitar arquivo se nomenclatura diferente
    		tecnologiaTxt.setIndRejeitarNomenclaturaDiferenteDtpt(Dominios.NAO);
    		
    		//Separador de campos
    		String separadorCampos = Pagina.getParamStr(request,"separadorCamposPflid");
    		if (separadorCampos != null && !separadorCampos.equals(Dominios.STRING_VAZIA)) {
    			tecnologiaTxt.setSeparadorCamposDtpt(separadorCampos);
    		} else {
    			tecnologiaTxt.setSeparadorCamposDtpt(null);
    		}
    		
    		perfilIntercambioDados.setDadosTecnologiaPerfilDtp(tecnologiaTxt);
    		
    	} 
	}


	private void setDadosCadastroPerfil(HttpServletRequest request,PerfilIntercambioDadosPflid perfilIntercambioDados)
			throws ECARException {
		//Inicio dos atributos gerais do cadastro
    	if (perfilIntercambioDados.getTipoFuncionalidade().equals(TipoFuncionalidadeEnum.CADASTRO)) {
	    	
    		//Situação não informada    		
	    	String idStrSituacaoNaoInformada = Pagina.getParamStr(request, "situacaoSitNaoInformadoImp");
	    	if (idStrSituacaoNaoInformada != null && !idStrSituacaoNaoInformada.equals(Dominios.STRING_VAZIA)) {
	    		SituacaoSit situacaoNaoInformada = (SituacaoSit) this.buscar(SituacaoSit.class, Long.valueOf(idStrSituacaoNaoInformada));
	    		
	    		((PerfilIntercambioDadosCadastroPidc)perfilIntercambioDados).setSituacaoNaoInformadaPidc(situacaoNaoInformada);
	        } else {
	        	((PerfilIntercambioDadosCadastroPidc)perfilIntercambioDados).setSituacaoNaoInformadaPidc(null);
	        }

	    	//Situação Sem Correspondente
	    	String idStrSituacaoSemCorrespondente = Pagina.getParamStr(request, "situacaoSitSemCorrespondenteImp");
	    	if (idStrSituacaoSemCorrespondente != null && !idStrSituacaoSemCorrespondente.equals(Dominios.STRING_VAZIA)) {
	    		SituacaoSit situacaoSemCorrespondente = (SituacaoSit) this.buscar(SituacaoSit.class, Long.valueOf(idStrSituacaoSemCorrespondente));
	    		
	    		((PerfilIntercambioDadosCadastroPidc)perfilIntercambioDados).setSituacaoSemCorrespondentePidc(situacaoSemCorrespondente);
            } else {
            	((PerfilIntercambioDadosCadastroPidc)perfilIntercambioDados).setSituacaoSemCorrespondentePidc(null);
            }


	    	//Estrutura Base
	    	String idStrEstruturaBase = Pagina.getParamStr(request, "estruturaEttBaseImp");
	    	if (idStrEstruturaBase != null && !idStrEstruturaBase.equals(Dominios.STRING_VAZIA)) {
	    		
	    		EstruturaEtt estruturaBase = (EstruturaEtt) this.buscar(EstruturaEtt.class, Long.valueOf(idStrEstruturaBase));
	    		
	    		((PerfilIntercambioDadosCadastroPidc)perfilIntercambioDados).setEstruturaBasePidc(estruturaBase);
            } else {
            	((PerfilIntercambioDadosCadastroPidc)perfilIntercambioDados).setEstruturaBasePidc(null);
            }

	    	//Atributo na estrutura base
	    	AtributoDao atributoDao = new AtributoDao(request);
	    	AtributosAtb atributosEstruturaBase = atributoDao.getAtributosAtbByNomeAtb(PerfilIntercambioDadosDao.ATRIBUTO_ATB_VALOR_ASSOCIACAO);
	    	
	    	((PerfilIntercambioDadosCadastroPidc)perfilIntercambioDados).setAtributoBasePidc(atributosEstruturaBase);
	    	
	    	//Estrutura Item Nivel Superior
	    	String idStrEstruturaItemNivelSuperior = Pagina.getParamStr(request, "estruturaEttItemNivelSuperiorImp");
        	if (idStrEstruturaItemNivelSuperior != null && !idStrEstruturaItemNivelSuperior.equals(Dominios.STRING_VAZIA)) {
        		EstruturaEtt estruturaItemNivelSuperior = (EstruturaEtt) this.buscar(EstruturaEtt.class, Long.valueOf(idStrEstruturaItemNivelSuperior));
        		
        		((PerfilIntercambioDadosCadastroPidc)perfilIntercambioDados).setEstruturaItemNivelSuperiorPidc(estruturaItemNivelSuperior);
            } else {
            	((PerfilIntercambioDadosCadastroPidc)perfilIntercambioDados).setEstruturaItemNivelSuperiorPidc(null);
            }

        	
        	//Atributo na estrutura do item de nivel superior
        	AtributosAtb atributosEstruturaItemnivelSuperior = atributoDao.getAtributosAtbByNomeAtb(PerfilIntercambioDadosDao.ATRIBUTO_ATB_PADRAO_TIPO_EMPREENDIMENTO);
        	
        	((PerfilIntercambioDadosCadastroPidc)perfilIntercambioDados).setAtributoNivelSuperiorPidc(atributosEstruturaItemnivelSuperior);

        	
        	//Estrutura criação de item
        	String idStrEstruturaCriacaoItem = Pagina.getParamStr(request, "estruturaEttCriacaoItemImp");
        	if (idStrEstruturaCriacaoItem != null && !idStrEstruturaCriacaoItem.equals(Dominios.STRING_VAZIA)) {
        		EstruturaEtt estruturaCriacaoItem = (EstruturaEtt) this.buscar(EstruturaEtt.class, Long.valueOf(idStrEstruturaCriacaoItem));
        		
        		((PerfilIntercambioDadosCadastroPidc)perfilIntercambioDados).setEstruturaCriacaoItemPidc(estruturaCriacaoItem);
            } else {
        		((PerfilIntercambioDadosCadastroPidc)perfilIntercambioDados).setEstruturaCriacaoItemPidc(null);            	
            }

        	
        	//Grupo de acesso aos itens incluidos
        	String idStrGrupoAcessoItensIncluidos = Pagina.getParamStr(request, "sisAtributoSatbAcessoPermItemImp");
        	if (idStrGrupoAcessoItensIncluidos != null && !idStrGrupoAcessoItensIncluidos.equals(Dominios.STRING_VAZIA)) {
        		SisAtributoSatb grupoAcessoItensIncluidos = (SisAtributoSatb) this.buscar(SisAtributoSatb.class, Long.valueOf(idStrGrupoAcessoItensIncluidos));
        		
        		((PerfilIntercambioDadosCadastroPidc)perfilIntercambioDados).setGrupoAcessoItensImportadosPidc(grupoAcessoItensIncluidos);
            } else {
            	((PerfilIntercambioDadosCadastroPidc)perfilIntercambioDados).setGrupoAcessoItensImportadosPidc(null);
            }

    	}
	}


    /**
     * Grava ou altera se já houver um registro gravado
     *
     * @param request
     * @param perfilIntercambioDadospflid
     * @throws ECARException
     * @throws HibernateException
     */
    @SuppressWarnings("unchecked")
	public void salvar(HttpServletRequest request, PerfilIntercambioDadosPflid perfilIntercambioDadosPflid) throws ECARException, HibernateException {
    	
    	verificarPerfilExistente(perfilIntercambioDadosPflid);
    	
    	UsuarioUsu usuariologado = ((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario();
    	
    	perfilIntercambioDadosPflid.setDataInclusaoPflid(new Date());
    	
    	perfilIntercambioDadosPflid.setUsuarioInclusaoPflid(usuariologado);
    	
       super.salvar(perfilIntercambioDadosPflid);
    	
    }


    /**
     * Caso haja perfil já exsitente levanta exceção. 
     * 
     * @param perfilIntercambioDadosPflid
     * @throws ECARException
     */
	private void verificarPerfilExistente(PerfilIntercambioDadosPflid perfilIntercambioDadosPflid)
		throws ECARException {
		
		PerfilIntercambioDadosPflid perfilIntercambioDadosFiltro = new PerfilIntercambioDadosCadastroPidc();
    	
    	perfilIntercambioDadosFiltro.setNomePflid(perfilIntercambioDadosPflid.getNomePflid());
	
    	List<PerfilIntercambioDadosPflid> listaPerfil = pesquisar(perfilIntercambioDadosFiltro);
    	
    	//Se houver outro perfil com o mesmo nome o fluxo deverá ser abortado, impedindo que o usuário inclua dois perfis com o mesmo nome.
    	for (PerfilIntercambioDadosPflid perfilIntercambioDadosInner : listaPerfil) {
    		//Se na lista houver um perfil com o mesmo nome e que seja diferente do perfil enviado como parâmetro a exceção deverá ser levanta 
    		//abortando o processo.
    		if (perfilIntercambioDadosInner.getNomePflid().equalsIgnoreCase(perfilIntercambioDadosPflid.getNomePflid()) && 
    				!perfilIntercambioDadosInner.equals(perfilIntercambioDadosPflid)){
    			throw new ECARException ("perfil.intercambioDados.inclusao.duplicado");
    		}
		}

    	getSession().flush();
	}


    /**
     * Retorna o objeto DadosTecnologiaPerfilDtp caso algum já tenha sido
     * gravado, caso contrário retorna null
     *
     * @return
     * @throws ECARException
     */
    public PerfilIntercambioDadosPflid getPerfilIntercambioDadospflid()
                                    throws ECARException
    {
        List pflid = this.listar( PerfilIntercambioDadosPflid.class, null );

        if ( ( pflid != null ) && ( pflid.size(  ) > 0 ) )
        {
            return (PerfilIntercambioDadosPflid) pflid.iterator(  ).next(  );
        } else{
            return null;
        }
    }
    
    
    public List<PerfilIntercambioDadosPflid> pesquisarPerfis(int tipoServico,String modoProcessamento) 
    	throws ECARException{
    	
    	List<PerfilIntercambioDadosPflid> listaPerfilImportacaoManual;
    	
    	try {
    		String hql = MessageFormat.format(Util.getHql(ConstantesECAR.PESQUISA_PERFIL, request.getSession().getServletContext()), tipoServico,modoProcessamento);
	    	
	    	Query query = getSession().createQuery(hql);
	    	
	    	listaPerfilImportacaoManual = query.list();
    	
	    } catch(IOException e){
			this.logger.error(e);
	        throw new ECARException(e);
	    }
    	
    	return listaPerfilImportacaoManual;
    }
    
 
    /**
     * Pesquisa perfis com base em um perfil exemplo. 
     * 
     * @param perfilIntercambioDados
     * @return
     * @throws ECARException
     */
	public List<PerfilIntercambioDadosPflid> pesquisar(PerfilIntercambioDadosPflid perfilIntercambioDados) throws ECARException {
		
		List<PerfilIntercambioDadosPflid> list = new ArrayList<PerfilIntercambioDadosPflid>();	// lista resultado
		
		StringBuffer hql = new StringBuffer();
		
		hql.append("select pflid from PerfilIntercambioDadosPflid pflid inner join pflid.dadosTecnologiaPerfilDtp dados where pflid.indAtivoPflid = '"+Dominios.SIM+"'");
		
		if (perfilIntercambioDados != null) {
			
			//Inicio de montagem da query
			if (perfilIntercambioDados.getNomePflid() != null){
				hql.append(" and lower(pflid.nomePflid) like :nomePflid");
			}
			
			if (perfilIntercambioDados.getIndUsuarioProcessamentoAssociacaoItemPflid() != null){
				hql.append(" and pflid.indUsuarioProcessamentoAssociacaoItemPflid = :indUsuarioProcessamentoAssociacao");
			}		
			
			if (perfilIntercambioDados.getUsuarioImportacao() != null){
				hql.append(" and pflid.usuarioImportacao = :usuarioImportacao");
			}
			
			if (perfilIntercambioDados.getIndAtivoAvisoImpPflid() != null){
				hql.append(" and pflid.indAtivoAvisoImpPflid = :ativoAvisoImportacao");
			}
	
			if (perfilIntercambioDados.getGrupoAcessoEnvioEmailPflid() != null){
				hql.append(" and pflid.grupoAcessoEnvioEmailPflid = :grupoAcessoEnvioEmail");
			}
			
			if (perfilIntercambioDados.getComposicaoEmailPflid() != null){
				hql.append(" and pflid.composicaoEmailPflid = :composicaoEmail");
			}
	
			if (((PerfilIntercambioDadosCadastroPidc)perfilIntercambioDados).getSituacaoNaoInformadaPidc() != null){
				hql.append(" and pflid.situacaoNaoInformadaPidc = :situacaoNaoInformada");
			}
			
			if (((PerfilIntercambioDadosCadastroPidc)perfilIntercambioDados).getSituacaoSemCorrespondentePidc() != null){
				hql.append(" and pflid.situacaoSemCorrespondentePidc = :situacaoSemCorrespondente");
			}
	
			if (((PerfilIntercambioDadosCadastroPidc)perfilIntercambioDados).getEstruturaBasePidc() != null){
				hql.append(" and pflid.estruturaBasePidc = :estruturaBase");
			}
	
			if (((PerfilIntercambioDadosCadastroPidc)perfilIntercambioDados).getEstruturaItemNivelSuperiorPidc() != null){
				hql.append(" and pflid.estruturaItemNivelSuperiorPidc = :estruturaItemNivelSuperior");
			}
			
			if (((PerfilIntercambioDadosCadastroPidc)perfilIntercambioDados).getEstruturaCriacaoItemPidc() != null){
				hql.append(" and pflid.estruturaCriacaoItemPidc = :estruturaCriacaoItem");
			}
			
			if (((PerfilIntercambioDadosCadastroPidc)perfilIntercambioDados).getGrupoAcessoItensImportadosPidc() != null){
				hql.append(" and pflid.grupoAcessoItensImportadosPidc = :grupoAcessoItensImportados");
			}
	
			if (perfilIntercambioDados.getDadosTecnologiaPerfilDtp() != null && ((DadosTecnologiaPerfilTxtDtpt)perfilIntercambioDados.getDadosTecnologiaPerfilDtp()).getSeparadorCamposDtpt() != null){
				hql.append(" and pflid.dadosTecnologiaPerfilDtp.separadorCamposDtpt like :separadorCampos");
			}
		}

		hql.append(" order by pflid.codPflid ");
		
		//Fim de montagem da query
		
		Query query = getSession().createQuery(hql.toString());
		
		if (perfilIntercambioDados != null) {

			//Inicio da definição dos parametros
			if (perfilIntercambioDados.getNomePflid() != null){
				query.setString("nomePflid", "%"+perfilIntercambioDados.getNomePflid().toLowerCase()+"%");
			}
			
			if (perfilIntercambioDados.getIndUsuarioProcessamentoAssociacaoItemPflid() != null){
				query.setString("indUsuarioProcessamentoAssociacao", perfilIntercambioDados.getIndUsuarioProcessamentoAssociacaoItemPflid());
			}
	
			if (perfilIntercambioDados.getUsuarioImportacao() != null){
				query.setParameter("usuarioImportacao", perfilIntercambioDados.getUsuarioImportacao());
			}
			
			if (perfilIntercambioDados.getIndAtivoAvisoImpPflid() != null){
				query.setParameter("ativoAvisoImportacao", perfilIntercambioDados.getIndAtivoAvisoImpPflid());			
			}
			
			if (perfilIntercambioDados.getGrupoAcessoEnvioEmailPflid() != null){
				query.setParameter("grupoAcessoEnvioEmail", perfilIntercambioDados.getGrupoAcessoEnvioEmailPflid());
			}
	
			if (perfilIntercambioDados.getComposicaoEmailPflid() != null){
				query.setParameter("composicaoEmail", perfilIntercambioDados.getComposicaoEmailPflid());
			}
	
			if (((PerfilIntercambioDadosCadastroPidc)perfilIntercambioDados).getSituacaoNaoInformadaPidc() != null){
				query.setParameter("situacaoNaoInformada", ((PerfilIntercambioDadosCadastroPidc)perfilIntercambioDados).getSituacaoNaoInformadaPidc());
			}

			if (((PerfilIntercambioDadosCadastroPidc)perfilIntercambioDados).getSituacaoSemCorrespondentePidc() != null){
				query.setParameter("situacaoSemCorrespondente", ((PerfilIntercambioDadosCadastroPidc)perfilIntercambioDados).getSituacaoSemCorrespondentePidc());
			}

			if (((PerfilIntercambioDadosCadastroPidc)perfilIntercambioDados).getEstruturaBasePidc() != null){
				query.setParameter("estruturaBase", ((PerfilIntercambioDadosCadastroPidc)perfilIntercambioDados).getEstruturaBasePidc());
			}
			
			if (((PerfilIntercambioDadosCadastroPidc)perfilIntercambioDados).getEstruturaItemNivelSuperiorPidc() != null){
				query.setParameter("estruturaItemNivelSuperior", ((PerfilIntercambioDadosCadastroPidc)perfilIntercambioDados).getEstruturaItemNivelSuperiorPidc());
			}
	
			if (((PerfilIntercambioDadosCadastroPidc)perfilIntercambioDados).getEstruturaCriacaoItemPidc() != null){
				query.setParameter("estruturaCriacaoItem", ((PerfilIntercambioDadosCadastroPidc)perfilIntercambioDados).getEstruturaCriacaoItemPidc());
			}
			
			if (((PerfilIntercambioDadosCadastroPidc)perfilIntercambioDados).getGrupoAcessoItensImportadosPidc() != null){
				query.setParameter("grupoAcessoItensImportados", ((PerfilIntercambioDadosCadastroPidc)perfilIntercambioDados).getGrupoAcessoItensImportadosPidc());
			}
	
			if (perfilIntercambioDados.getDadosTecnologiaPerfilDtp() != null && ((DadosTecnologiaPerfilTxtDtpt)perfilIntercambioDados.getDadosTecnologiaPerfilDtp()).getSeparadorCamposDtpt() != null){
				query.setParameter("separadorCampos", "%"+((DadosTecnologiaPerfilTxtDtpt)perfilIntercambioDados.getDadosTecnologiaPerfilDtp()).getSeparadorCamposDtpt()+"%");
			}
		}

		//Fim da definição dos parametros
		
		list = query.list();
		
		return list;
	}
	
	/**
	 * Exclusão lógica do perfil.
	 * 
	 * @param perfilIntercambioDados
	 * @throws ECARException
	 */
	public void excluir(PerfilIntercambioDadosPflid perfilIntercambioDados) throws ECARException {
		
		perfilIntercambioDados.setIndAtivoPflid(Dominios.NAO);
    	perfilIntercambioDados.setDataAlteracaoPflid(new Date());
    	
    	UsuarioUsu usuarioAlteracao = ((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario();
    	perfilIntercambioDados.setUsuarioAlteracaoPflid(usuarioAlteracao);

		super.salvarOuAlterar(perfilIntercambioDados);
		
	}

	/**
	 * Aletarção do perfil.
	 * @param perfilIntercambioDados
	 * @throws ECARException
	 */
	public void alterar (PerfilIntercambioDadosPflid perfilIntercambioDados) throws ECARException {
    	
		
    	verificarPerfilExistente(perfilIntercambioDados);
    	
		perfilIntercambioDados.setDataAlteracaoPflid(new Date());
    	
    	UsuarioUsu usuarioAlteracao = ((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario();
    	perfilIntercambioDados.setUsuarioAlteracaoPflid(usuarioAlteracao);

    	super.alterar(perfilIntercambioDados);
    	
	}
	
}
