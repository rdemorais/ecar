package ecar.action;

import java.util.Calendar;

import org.apache.log4j.Logger;

import comum.util.ConstantesECAR;
import comum.util.EcarCfg;
import comum.util.Util;

import ecar.exception.ECARException;
import ecar.util.Dominios;

/**
 *
 * @author 70744416353
 */
public class ActionSisAtributo {

    /**
     *
     */
    protected Logger logger = null;
	
        /**
         *
         */
        public ActionSisAtributo(){
		logger = Logger.getLogger(this.getClass());	
	}
	
	
        /**
         *
         * @param mascara
         * @throws ECARException
         */
        public void validaMascara(String mascara) throws ECARException {

		if (mascara != null && mascara.equals(Dominios.STRING_VAZIA)){
			throw new ECARException ("atributo.validacao.mascara.vazia");
		}
		
		this.validaMes (mascara);
	
		this.validaAno (mascara);
		
		this.validaCaracterIncremental (mascara);

	}

	/**
	 * Validação do caracter incremental 
	 * @param mascara
	 * @throws ECARException 
	 */
	private void validaCaracterIncremental(String mascara) throws ECARException {
		
		
		int primeiraOcorrenciaCaracterSequencial = mascara.indexOf(EcarCfg.getConfiguracao("caracter.mascara.sequencial"));
		int ultimaOcorrenciaCaracterSequencial = mascara.lastIndexOf(EcarCfg.getConfiguracao("caracter.mascara.sequencial"));
		
		if (primeiraOcorrenciaCaracterSequencial >= 0 ) {
			
			StringBuffer caracterIncrementalCorreto = new StringBuffer(); 
			
			//Os caracteres incrementais deve ser contínuos. Ex.: $$$ , a ocorrência ($$@ $) é inválida como máscara  
			for (int i = primeiraOcorrenciaCaracterSequencial; i <= ultimaOcorrenciaCaracterSequencial; i++) {
				caracterIncrementalCorreto.append(EcarCfg.getConfiguracao("caracter.mascara.sequencial"));
			}
			
			if (!caracterIncrementalCorreto.toString().equals(mascara.substring(primeiraOcorrenciaCaracterSequencial, ultimaOcorrenciaCaracterSequencial+1))){
				throw new ECARException ("caracter.mascara.incremental.invalida");
			}
			
		} else {
			throw new ECARException ("caracter.mascara.incremental.vazia");
		}
		
		
	}

	/**
	 * Validação da máscara para o ano!
	 * @param primeiraOcorrenciaCaracterAno
	 * @param ultimaOcorrenciaCaracterAno
	 * @throws ECARException 
	 */
	private void validaAno(String mascara) throws ECARException {

		int primeiraOcorrenciaCaracterAno = mascara.indexOf(EcarCfg.getConfiguracao("caracter.mascara.ano"));
		int ultimaOcorrenciaCaracterAno = mascara.lastIndexOf(EcarCfg.getConfiguracao("caracter.mascara.ano"));

		if (primeiraOcorrenciaCaracterAno >= 0){//Se primeiraOcorrenciaCaracterAno for maior que 0 é porque existe caracter @
			if (!(ultimaOcorrenciaCaracterAno == primeiraOcorrenciaCaracterAno+3)) {//verifica se há ultima ocorrencia do caracter @ é igual a primeira ocorrencia mais três posições, se for está correto senão a máscara está errada.
				throw new ECARException ("atributo.validacao.mascara.ano.invalida");
			} else if (!mascara.contains(EcarCfg.getConfiguracao("caracter.mascara.ano")+EcarCfg.getConfiguracao("caracter.mascara.ano")+EcarCfg.getConfiguracao("caracter.mascara.ano")+EcarCfg.getConfiguracao("caracter.mascara.ano"))) {//Verifica se há ocorrencia de 4 @ seguidos.
				throw new ECARException ("atributo.validacao.mascara.ano.invalida");
			}
		}
		
	}

	/**
	 * Validação da máscara para o mês!
	 * @param primeiraOcorrenciaCaracterMes
	 * @param ultimaOcorrenciaCaracterMes
	 * @throws ECARException
	 */
	private void validaMes(String mascara) throws ECARException {
		
		int primeiraOcorrenciaCaracterMes = mascara.indexOf(EcarCfg.getConfiguracao("caracter.mascara.mes"));
		int ultimaOcorrenciaCaracterMes = mascara.lastIndexOf(EcarCfg.getConfiguracao("caracter.mascara.mes"));
		
		if (primeiraOcorrenciaCaracterMes >= 0){//Se primeiraOcorrenciaCaracterMes for maior que 0 é porque existe caracter # 
			if (!(ultimaOcorrenciaCaracterMes == primeiraOcorrenciaCaracterMes+1)) {//verifica se há ultima ocorrencia do caracter # é igual a primeira ocorrencia mais uma posição, se for está correto senão a máscara está errada.
				throw new ECARException ("atributo.validacao.mascara.mes.invalida");
			} 
		}
		
	}

	/**
	 * Gera o conteudo baseado na máscara do atributo
	 * @param mascara
	 * @return
	 */
	public String geraConteudo(String mascara) {
		
		StringBuffer conteudo = new StringBuffer();
		char caracterMes = EcarCfg.getConfiguracao("caracter.mascara.mes").charAt(0);
		char caracterAno = EcarCfg.getConfiguracao("caracter.mascara.ano").charAt(0);

		
		try {
			this.validaMascara(mascara);
			
			
			for (int i = 0; i < mascara.length(); i++) {
				
				char caracter = mascara.charAt(i);
				
				if (caracter == caracterMes) {
					int mes = Calendar.getInstance().get(Calendar.MONTH)+1;
					if (mes < 10){
						conteudo.append("0");
					}
					conteudo.append(mes);
						
					i++;
				} else if (caracter == caracterAno) {
					conteudo.append(Calendar.getInstance().get(Calendar.YEAR));
					i = i+3;
				} else {
					conteudo.append(caracter);
				}
				
			}
			
			
		} catch (Exception e) {
			this.logger.error(e);
		}
		
		return conteudo.toString();
	}


	/**
	 * Identifica os caracteres incrementais do conteudo passado e os substitui pelo sequencial, criando um novo conteudo com o sequencial no local dos caracteres incrementais.
         * @param conteudo
         * @param sequencia
	 * @return
	 * @throws ECARException 
	 */
	public String formatarConteudoParteIncremental(String conteudo, Long sequencia) throws ECARException {
		
		char caracterSequencial = EcarCfg.getConfiguracao("caracter.mascara.sequencial").charAt(0);
		String novoConteudo;
		
		//Verifica se a quantidade de caracteres na mascara referente a parte incremental é menor que o sequencia gerado
		//Ex.: mascara = $$ e sequencial 100 não será permitido 
		this.validarValorIncrementalComBaseMascara(conteudo,sequencia);
		
		int posicaoPrimeiraOcorrencia = conteudo.indexOf(caracterSequencial);
		int posicaoUltimaOcorrencia = conteudo.lastIndexOf(caracterSequencial);
		int qtdeCaracteres = posicaoUltimaOcorrencia - posicaoPrimeiraOcorrencia + 1;
		StringBuffer parteIncrementalMascara = new StringBuffer(); 
		String parteIncrementalComValor;
		
		for (int i = 0; i < qtdeCaracteres; i++) {
			parteIncrementalMascara.append(caracterSequencial);
		}
		
		parteIncrementalComValor = obterValorParteIncremental(parteIncrementalMascara,sequencia);
		
		novoConteudo = conteudo.replace(parteIncrementalMascara, parteIncrementalComValor);
			
		
		return novoConteudo;
	}

	/**
	 * Valida o valor da parte incremental com base na máscara se o valor não for compatível com a máscara levanta uma Exceção.
	 * Ex.: Máscara $$ , sequencial 100, não é possível pois a máscara só possui dois caracteres e o sequencial informado possui três.
	 * @param conteudo
	 * @param sequencia
	 * @throws ECARException
	 */
	public void validarValorIncrementalComBaseMascara(String conteudo, Long sequencia) throws ECARException {
		
		char caracterSequencial = EcarCfg.getConfiguracao("caracter.mascara.sequencial").charAt(0);
		
		if (conteudo != null) { 
			int qtdeCaracterIncremental = Util.obterQuantidadeCaracterSequencial(conteudo,caracterSequencial);
			
			if (qtdeCaracterIncremental < sequencia.toString().length()){
				throw new ECARException ("sequencial.invalido"); 
			}
		}
		
	}


	private String obterValorParteIncremental(StringBuffer parteIncrementalMascara, Long sequencia) {
		
		int qtdeCaracteresParteIncremental = parteIncrementalMascara.length();
		int qtdeCaracteresSequencial = sequencia.toString().length();
		StringBuffer parteIncrementalComValor = new StringBuffer();

		int qtdeZerosEsquerda = qtdeCaracteresParteIncremental - qtdeCaracteresSequencial;
		
		
		for (int i = 0; i < qtdeZerosEsquerda; i++) {
			parteIncrementalComValor.append(ConstantesECAR.ZERO);
		}
		parteIncrementalComValor.append(sequencia.toString());
		
		
		return parteIncrementalComValor.toString();
	}


	/**
	 * Obtem o Mês do conteudo informado com base na máscara, se a mascara não for informada retorna o mês corrente, formatado com dois caracteres.
	 * @param conteudo
	 * @param mascara
	 * @return
	 * @throws ECARException
	 */
	public String obterMes(String conteudo, String mascara) throws ECARException {

		String mes = null;
		char caracterMes = EcarCfg.getConfiguracao("caracter.mascara.mes").charAt(0);
		boolean possuiCaracterMes = false;
		
		if (conteudo != null && !conteudo.equals(Dominios.STRING_VAZIA) &&
				mascara != null && !mascara.equals(Dominios.STRING_VAZIA)) {
			
			this.validaMascara(mascara);
			
			
			for (int i = 0; i < mascara.length(); i++) {
				
				char caracter = mascara.charAt(i);
				
				if (caracter == caracterMes) {
					mes = conteudo.substring(i, i+2);
					break;
				} 
				
			}
			if (!possuiCaracterMes){
				mes = formatarMes();
			}
		} else {
			mes = formatarMes();
		}
		
		return mes;
	}

	private String formatarMes() {

		String mes;
		
		int mesInteiro = (Calendar.getInstance().get(Calendar.MONTH)+1);
		if (mesInteiro < 10){
			mes = "0"+mesInteiro;
		} else {
			mes = mesInteiro+"";
		}

		return mes;
	}


	/**
	 * Obtem o Ano do conteudo informado com base na máscara, se a mascara não for informada retorna o ano corrente.
	 * @param conteudo
	 * @param mascara
	 * @return
	 * @throws ECARException
	 */
	public String obterAno(String conteudo, String mascara) throws ECARException {

		String ano = null;
		char caracterAno = EcarCfg.getConfiguracao("caracter.mascara.ano").charAt(0);
		boolean possuiCaracterAno = false;
		
		if (conteudo != null && !conteudo.equals(Dominios.STRING_VAZIA) &&
				mascara != null && !mascara.equals(Dominios.STRING_VAZIA)) {

			this.validaMascara(mascara);
			
			for (int i = 0; i < mascara.length(); i++) {
				
				char caracter = mascara.charAt(i);
				
				if (caracter == caracterAno) {
					ano = conteudo.substring(i, i+4);
					possuiCaracterAno = true;
					break;
				} 
				
			}
			if (!possuiCaracterAno){
				ano = Calendar.getInstance().get(Calendar.YEAR)+"";
			}
		} else {
			ano = Calendar.getInstance().get(Calendar.YEAR)+"";
			
		}
		
		return ano;
	}


        /**
         *
         * @param conteudo
         * @param mascara
         * @return
         * @throws ECARException
         */
        public String obterSequencial(String conteudo, String mascara) throws ECARException {

		String sequencial = null;
		char caracterSequencial = EcarCfg.getConfiguracao("caracter.mascara.sequencial").charAt(0);

		if (conteudo != null && !conteudo.equals(Dominios.STRING_VAZIA) &&
				mascara != null && !mascara.equals(Dominios.STRING_VAZIA)) {

			this.validaMascara(mascara);
			
			int primeiraOcorrencia = mascara.indexOf(caracterSequencial);
			int ultimaOcorrencia = mascara.lastIndexOf(caracterSequencial);

			sequencial = conteudo.substring(primeiraOcorrencia, ultimaOcorrencia+1);
		}
		
		return sequencial;
	}
	
}
