package ecar.pojo.intercambioDados.funcionalidade;

import ecar.pojo.AtributosAtb;
import ecar.pojo.AvaliaMetodo;
import ecar.pojo.EstruturaEtt;
import ecar.pojo.SisAtributoSatb;
import ecar.pojo.SituacaoSit;
import ecar.pojo.intercambioDados.LogIntercambioDadosLid;
import ecar.pojo.intercambioDados.PerfilIntercambioDadosLogPflogid;
import ecar.pojo.intercambioDados.PerfilIntercambioDadosPflid;
import ecar.pojo.intercambioDados.tecnologia.DadosTecnologiaPerfilTxtDtpt;

public class PerfilIntercambioDadosCadastroPidc extends PerfilIntercambioDadosPflid{

	
	/**
     * Grupo de acesso que receberá permissão ao item importado.
     */
    private SisAtributoSatb grupoAcessoItensImportadosPidc;
	
    /**
     * Situação para valor não informado no arquivo de importação
     */
    private SituacaoSit situacaoNaoInformadaPidc;

    /**
     * Situação para valor informado no arquivo, mas sem correspondência no e-CAR
     */
    private SituacaoSit situacaoSemCorrespondentePidc;

    /**
     * Estrutura base onde encontra-se o atributo de ligação para associação dos itens sendo importados.
     */
    private EstruturaEtt estruturaBasePidc;
    
    /**
     * Atributo de ligação na estrutura base, a partir do qual será feita a comparação do valor de ligação associado ao item  sendo importado.
     */
    private AtributosAtb atributoBasePidc;
    
    /**
     * Estrutura que possui o item a partir do qual será feita a associação do item importado.
     */
    private EstruturaEtt estruturaCriacaoItemPidc;
    
    /**
     * Estrutura do item de nível superior
     */
    private EstruturaEtt estruturaItemNivelSuperiorPidc;
    
    /**
     * Atributo de ligação na estrutura do item de nível superior, a partir do qual será feita a comparação do valor de ligação associado ao item  sendo importado.
     */
    private AtributosAtb atributoNivelSuperiorPidc;

    
	public PerfilIntercambioDadosCadastroPidc() {
		super();
	}
	
	public SisAtributoSatb getGrupoAcessoItensImportadosPidc() {
		return grupoAcessoItensImportadosPidc;
	}


	public void setGrupoAcessoItensImportadosPidc(SisAtributoSatb grupoAcessoItensImportadosPidc) {
		this.grupoAcessoItensImportadosPidc = grupoAcessoItensImportadosPidc;
	}

    public SituacaoSit getSituacaoNaoInformadaPidc() {
		return situacaoNaoInformadaPidc;
	}

	public void setSituacaoNaoInformadaPidc(SituacaoSit situacaoNaoInformadaPidc) {
		this.situacaoNaoInformadaPidc = situacaoNaoInformadaPidc;
	}
	
	public SituacaoSit getSituacaoSemCorrespondentePidc() {
		return situacaoSemCorrespondentePidc;
	}

	public void setSituacaoSemCorrespondentePidc(SituacaoSit situacaoSemCorrespondentePidc) {
		this.situacaoSemCorrespondentePidc = situacaoSemCorrespondentePidc;
	}

	public EstruturaEtt getEstruturaBasePidc() {
		return estruturaBasePidc;
	}

	public void setEstruturaBasePidc(EstruturaEtt estruturaBasePidc) {
		this.estruturaBasePidc = estruturaBasePidc;
	}

	public AtributosAtb getAtributoBasePidc() {
		return atributoBasePidc;
	}

	public void setAtributoBasePidc(AtributosAtb atributoBasePidc) {
		this.atributoBasePidc = atributoBasePidc;
	}

	public EstruturaEtt getEstruturaCriacaoItemPidc() {
		return estruturaCriacaoItemPidc;
	}

	public void setEstruturaCriacaoItemPidc(EstruturaEtt estruturaCriacaoItemPidc) {
		this.estruturaCriacaoItemPidc = estruturaCriacaoItemPidc;
	}

	public EstruturaEtt getEstruturaItemNivelSuperiorPidc() {
		return estruturaItemNivelSuperiorPidc;
	}

	public void setEstruturaItemNivelSuperiorPidc(EstruturaEtt estruturaItemNivelSuperiorPidc) {
		this.estruturaItemNivelSuperiorPidc = estruturaItemNivelSuperiorPidc;
	}

	public AtributosAtb getAtributoNivelSuperiorPidc() {
		return atributoNivelSuperiorPidc;
	}

	public void setAtributoNivelSuperiorPidc(AtributosAtb atributoNivelSuperiorPidc) {
		this.atributoNivelSuperiorPidc = atributoNivelSuperiorPidc;
	}
	

	@Override
	@AvaliaMetodo(valida=false)
	public TipoFuncionalidadeEnum getTipoFuncionalidade() {
		return TipoFuncionalidadeEnum.CADASTRO;
	}

	public String montarDadosFuncionalidadeLog() {
		
		String funcionalidade  = null;
		
		funcionalidade  =  
		PerfilIntercambioDadosLogPflogid.DADO_FUNC_0 + "=" + getGrupoAcessoItensImportadosPidc().getDescricaoSatb()
		+ LogIntercambioDadosLid.LOG_SEPARADOR + PerfilIntercambioDadosLogPflogid.DADO_FUNC_1 + "=" + getEstruturaBasePidc().getNomeEtt()
		+ LogIntercambioDadosLid.LOG_SEPARADOR + PerfilIntercambioDadosLogPflogid.DADO_FUNC_2 + "=" + getAtributoBasePidc().getNomeAtb()
		+ LogIntercambioDadosLid.LOG_SEPARADOR + PerfilIntercambioDadosLogPflogid.DADO_FUNC_3 + "=" + getEstruturaItemNivelSuperiorPidc().getNomeEtt()
		+ LogIntercambioDadosLid.LOG_SEPARADOR + PerfilIntercambioDadosLogPflogid.DADO_FUNC_4 + "=" + getAtributoNivelSuperiorPidc().getNomeAtb()
		+ LogIntercambioDadosLid.LOG_SEPARADOR + PerfilIntercambioDadosLogPflogid.DADO_FUNC_5 + "=" + getEstruturaCriacaoItemPidc().getNomeEtt();
	
		return funcionalidade;
	
	}
	
	public String montarDadosTecnologiaLog() {	
		
		String dadosTecnologia  = null;
		
		dadosTecnologia  = PerfilIntercambioDadosLogPflogid.DADO_TEC_0 + "=" + getCodSistemaDestinoPflid()
		+ LogIntercambioDadosLid.LOG_SEPARADOR + PerfilIntercambioDadosLogPflogid.DADO_TEC_1 + "=" + getCodSistemaOrigemPflid()
		+ LogIntercambioDadosLid.LOG_SEPARADOR + PerfilIntercambioDadosLogPflogid.DADO_TEC_2 + "=" + getCodTipoServicoPflid();
		
		if (getDadosTecnologiaPerfilDtp() instanceof DadosTecnologiaPerfilTxtDtpt) {
			dadosTecnologia += LogIntercambioDadosLid.LOG_SEPARADOR + PerfilIntercambioDadosLogPflogid.DADO_TEC_3 + "=" + ((DadosTecnologiaPerfilTxtDtpt)this.getDadosTecnologiaPerfilDtp()).getSeparadorCamposDtpt();
			if (((DadosTecnologiaPerfilTxtDtpt)this.getDadosTecnologiaPerfilDtp()).getTipoTecnologia()!=null) {
				dadosTecnologia += LogIntercambioDadosLid.LOG_SEPARADOR + PerfilIntercambioDadosLogPflogid.DADO_TEC_9 + "=" + ((DadosTecnologiaPerfilTxtDtpt)this.getDadosTecnologiaPerfilDtp()).getTipoTecnologia().getEspecificacao();
				dadosTecnologia += LogIntercambioDadosLid.LOG_SEPARADOR + PerfilIntercambioDadosLogPflogid.DADO_TEC_10 + "=" + ((DadosTecnologiaPerfilTxtDtpt)this.getDadosTecnologiaPerfilDtp()).getTipoTecnologia().getVersao();
			}
		}
		dadosTecnologia += LogIntercambioDadosLid.LOG_SEPARADOR + PerfilIntercambioDadosLogPflogid.DADO_TEC_4 + "=" + getNomePflid() 
		+ LogIntercambioDadosLid.LOG_SEPARADOR + PerfilIntercambioDadosLogPflogid.DADO_TEC_5 + "=" + getNomeSistemaDestinoPflid()
		+ LogIntercambioDadosLid.LOG_SEPARADOR + PerfilIntercambioDadosLogPflogid.DADO_TEC_6 + "=" + getNomeSistemaOrigemPflid()
		+ LogIntercambioDadosLid.LOG_SEPARADOR + PerfilIntercambioDadosLogPflogid.DADO_TEC_7 + "=" + getNomeTipoServicoPflid()
		+ LogIntercambioDadosLid.LOG_SEPARADOR + PerfilIntercambioDadosLogPflogid.DADO_TEC_8 + "=" + getDadosTecnologiaPerfilDtp().getEncodeDtp();

		return  dadosTecnologia;
		
	}
	
}
