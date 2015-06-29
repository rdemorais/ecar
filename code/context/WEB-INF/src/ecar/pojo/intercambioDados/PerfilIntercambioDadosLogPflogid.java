package ecar.pojo.intercambioDados;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * TODO
 *  BD
 *  Alterar tabela tb_perfil_intercambio_dados_pflid retirando os campos:
 *  	tipo_comunicacao_pflid
 *  	posicao_campo_associacao_pflid
 *  	posicao_campo_associacao_nivel_superior_pflid
 */
/** @author Hibernate CodeGenerator */
public class PerfilIntercambioDadosLogPflogid implements Serializable {
	
	
	/**
	 * CONSTANTES QUE IDENTIFICAM OS CAMPOS DO PERFIL A FIM DE GERAR O MÉTODO toStringLog()
	 */
	public static final String DADO_TEC_0 = "codSistemaDestinoPflid";
	public static final String DADO_TEC_1 = "codSistemaOrigemPflid";
	public static final String DADO_TEC_2 = "codTipoServicoPflid";
	public static final String DADO_TEC_3 = "separadorCamposDtpt";
	public static final String DADO_TEC_4 = "nomePflid";
	public static final String DADO_TEC_5 = "nomeSistemaDestinoPflid";
	public static final String DADO_TEC_6 = "nomeSistemaOrigemPflid";
	public static final String DADO_TEC_7 = "nomeTipoServicoPflid";
	public static final String DADO_TEC_8 = "encodeDtp";
	public static final String DADO_TEC_9 = "nomeEspecif";
	public static final String DADO_TEC_10 = "versaoEspecif";
	
	
	public static final String DADO_FUNC_0 = "grupoAcessoItensImportadosPidc";
	public static final String DADO_FUNC_1 = "estruturaBasePidc";
	public static final String DADO_FUNC_2 = "atributoBasePidc";
	public static final String DADO_FUNC_3 = "estruturaItemNivelSuperiorPidc";
	public static final String DADO_FUNC_4 = "atributoNivelSuperiorPidc";
	public static final String DADO_FUNC_5 = "estruturaCriacaoItemPidc";
	
	
	private Map<String, String> mapaTec = new HashMap<String, String>();
	private Map<String, String> mapaFunc = new HashMap<String, String>();
	/**
	 * 
	 */
	private static final long serialVersionUID = -694823115306078713L;

	/**
	 * Código do log gerado pelo banco
	 * */
    private Long codPflogid;
	
	/**
	 * Tipo da funcionalidade do perfil original
	 * */
	private String tipoFuncionalidade;
	
	
	/**
	 * Dados da funcionalidade do perfil original
	 * */
	private String dadosFuncionalidade;
	
	/**
	 * Tipo de Tecnologia do perfil original
	 */
	private String tipoTecnologia;
	
	
	/**
	 * Dados de Tecnologia do perfil original
	 */
	private String dadosTecnologia;

	public PerfilIntercambioDadosLogPflogid() {
		
	}
	

	public PerfilIntercambioDadosLogPflogid(Long codPflogid,
			String tipoFuncionalidade, String dadosFuncionalidade,
			String tipoTecnologia, String dadosTecnologia) {
		super();
		this.codPflogid = codPflogid;
		this.tipoFuncionalidade = tipoFuncionalidade;
		this.dadosFuncionalidade = dadosFuncionalidade;
		this.tipoTecnologia = tipoTecnologia;
		this.dadosTecnologia = dadosTecnologia;
	}


	public String getTipoFuncionalidade() {
		return tipoFuncionalidade;
	}


	public void setTipoFuncionalidade(String tipoFuncionalidade) {
		this.tipoFuncionalidade = tipoFuncionalidade;
	}


	public String getDadosFuncionalidade() {
		return dadosFuncionalidade;
	}


	public void setDadosFuncionalidade(String dadosFuncionalidade) {
		this.dadosFuncionalidade = dadosFuncionalidade;
	}


	public String getTipoTecnologia() {
		return tipoTecnologia;
	}


	public void setTipoTecnologia(String tipoTecnologia) {
		this.tipoTecnologia = tipoTecnologia;
	}


	public String getDadosTecnologia() {
		return dadosTecnologia;
	}


	public void setDadosTecnologia(String dadosTecnologia) {
		this.dadosTecnologia = dadosTecnologia;
	}


	public Long getCodPflogid() {
		return codPflogid;
	}


	public void setCodPflogid(Long codPflogid) {
		this.codPflogid = codPflogid;
	}
	
	public String getNomeTipoServicoPflid() {
		return parserTecnologia().get(DADO_TEC_7);
	}
	
	public String getCodTipoServicoPflid() {
		return parserFuncionalidade().get(DADO_TEC_2);
	}

	public String getNomePflid() {
		return parserTecnologia().get(DADO_TEC_4);
	}
	
	public String getNomeSistemaOrigemPflid() {
		return parserTecnologia().get(DADO_TEC_6);
	}

	public String getNomeEspecificacaoDtp() {
		return parserTecnologia().get(DADO_TEC_9);
	}
	
	public String getVersaoEspecificacaoDtp() {
		return parserTecnologia().get(DADO_TEC_10);
	}
	
	public String getSeparadorCamposDtpt() {
		return parserTecnologia().get(DADO_TEC_3);
	}
	
	public String getEncodeDtp() {
		return parserTecnologia().get(DADO_TEC_8);
	}
	
	
	public String getGrupoAcessoItensImportadosPidc(){
		return parserFuncionalidade().get(DADO_FUNC_0);
	}
	
	public String getEstruturaBasePidc() {
		return parserFuncionalidade().get(DADO_FUNC_1);
	}
	
	public String getAtributoBasePidc() {
		return parserFuncionalidade().get(DADO_FUNC_2);
	}
	
	public String getEstruturaItemNivelSuperiorPidc() {
		return parserFuncionalidade().get(DADO_FUNC_3);
	}
	
	public String getAtributoNivelSuperiorPidc() {
		return parserFuncionalidade().get(DADO_FUNC_4);
	}
	
	public String getEstruturaCriacaoItemPidc() {
		return parserFuncionalidade().get(DADO_FUNC_5);
	}
	
	public Map<String, String> parserTecnologia() {
		
		if (mapaTec.size()==0 && dadosTecnologia!=null && dadosTecnologia.length()>0) {
			mapaTec = new HashMap<String, String>();
			StringTokenizer strToekn = new StringTokenizer(dadosTecnologia, LogIntercambioDadosLid.LOG_SEPARADOR);
			
			while(strToekn.hasMoreTokens()) {
				
				String par = strToekn.nextToken();
				// CHAVE, VALOR
				mapaTec.put(par.substring(0,par.indexOf("=")), par.substring(par.indexOf("=")+1, par.length()));
				
			}

		} 
		return mapaTec;
	}
	
	public Map<String, String> parserFuncionalidade() {
				
		if (mapaFunc.size()==0 && dadosFuncionalidade!=null && dadosFuncionalidade.length()>0) {
			mapaFunc = new HashMap<String, String>();
			StringTokenizer strToekn = new StringTokenizer(dadosFuncionalidade, LogIntercambioDadosLid.LOG_SEPARADOR);
					
			while(strToekn.hasMoreTokens()) {
						
				String par = strToekn.nextToken();
				// CHAVE, VALOR
				mapaFunc.put(par.substring(0,par.indexOf("=")), par.substring(par.indexOf("=")+1, par.length()));
						
			}
		
		} 
		return mapaFunc;
	}

	public static void main(String[] args) {
		PerfilIntercambioDadosLogPflogid p = new PerfilIntercambioDadosLogPflogid();
		p.setTipoFuncionalidade("Cadastro");
		p.setDadosTecnologia("codSistemaDestinoPflid=1,codSistemaOrigemPflid=2,codTipoServicoPflid=1,separadorCamposDtpt=###,nomeEspecif=Importação de dados de empreendimentos,versaoEspecif=1.0.1,nomePflid=Importação PACInter - Copa 2014,nomeSistemaDestinoPflid=e-CAR,nomeSistemaOrigemPflid=PACInter,nomeTipoServicoPflid=Importação,encodeDtp=ISO-8859-1");
//		p.setDadosFuncionalidade("grupoAcessoItensImportadosPidc=Grupo PACInter,estruturaBasePidc=Cidades da COPA 2014,atributoBasePidc=descricaoR1,estruturaItemNivelSuperiorPidc=Programa de Infra-Estrutura,atributoNivelSuperiorPidc=descricaoR3");
		System.out.println(p.getNomeTipoServicoPflid());
//		System.out.println(p.getTipoFuncionalidade().equals(TipoFuncionalidadeEnum.CADASTRO.getDescricao()));
	}

}
