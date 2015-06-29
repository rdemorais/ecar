package ecar.taglib.util;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import ecar.action.ActionHierarquia;
import ecar.dao.EstruturaDao;
import ecar.dao.ItemEstruturaDao;
import ecar.exception.ECARException;
import ecar.login.SegurancaECAR;
import ecar.permissao.ValidaPermissao;
import ecar.pojo.EstruturaEtt;
import ecar.pojo.Hierarchyable;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.NoArvoreEstrutura;
import ecar.util.Dominios;

/**
 * 
 */
public class EstruturaHierarquicaTag extends TagSupport {
	
    /**
     *
     */
    public final static int NAO_EXIBIR = 0;
        /**
         *
         */
        public final static int EXIBIR_DESABILITADO = 1;
        /**
         *
         */
        public final static int EXIBIR_HABILITADO = 2;
	
	private EstruturaDao estruturaDao;
	private boolean temCheckBox;
	private static final int VALORDESLOCAMENTO = 10;
	private List<Hierarchyable> lista;
	private String nomeCheckBox;
	private boolean marcarItensAssociadosEstruturaVirutal;
	private EstruturaEtt estruturaVirtual;
	private boolean exibirNomeEstrutura;
	private SegurancaECAR seguranca;
	
	private Logger logger = Logger.getLogger(this.getClass());
	JspWriter writer;
	
	@SuppressWarnings("unchecked")
	@Override
	public int doStartTag() throws JspException {
		writer = this.pageContext.getOut();		
		try {
			int deslocamento = 0;
			ActionHierarquia action = new ActionHierarquia();
			
			List<NoArvoreEstrutura> arvoreEstruturas = action.montarListaEstruturaHierarquizada(lista);
			
			writer.println("<table>");
			for (NoArvoreEstrutura no : arvoreEstruturas) {
				String html = montarNoArvore(no);
				
				if (!html.equals(Dominios.STRING_VAZIA)) { 
					writer.println("<tr>");
						writer.println("<td>");
							writer.println(html);
						writer.println("</td>");
					writer.println("</tr>");
				}
  
				if (no.getListaFilhos() != null && !no.getListaFilhos().isEmpty()){
					localizarFilhos(no.getListaFilhos(),deslocamento);
				}

			}
			writer.println("</table>");
			
		} catch (Exception e) {
			logger.error(e);
		}
		
		
		return Tag.SKIP_BODY; 
	}


	private String montarNoArvore(NoArvoreEstrutura no) throws IOException, ECARException {
		
		StringBuffer ret = new StringBuffer();
		ItemEstruturaDao itemDao = new ItemEstruturaDao(null);
		
		
		if (temCheckBox) { //Avalia se deve exibir checkbox
			if (marcarItensAssociadosEstruturaVirutal && no.getElemento() instanceof ItemEstruturaIett) { //Avalia se deve marcar itens já associados a estrutura virtual 
				
				ItemEstruturaIett item = null;
				
				if(no.getElemento() != null)
					item = (ItemEstruturaIett) itemDao.buscar(ItemEstruturaIett.class,((ItemEstruturaIett)no.getElemento()).getCodIett());
				EstruturaEtt estrutura = item.getEstruturaEtt();
				
				if (estruturaVirtual != null && itemDao.pesquisarEstruturasVirtuaisAssociadas((ItemEstruturaIett)no.getElemento()).contains(estruturaVirtual)) {//verifica se o item já foi associado a uma estrutura virtual, caso já tenha sido ele deve ser checado.
					ret.append("<input type='checkbox' value='"+no.getElemento().iGetCodigo()+"' name='"+nomeCheckBox+"' disabled=\"true\" checked=\"true\"/>");
					if (exibirNomeEstrutura) { //Verifica se deve exibir o nome da estrutura do item
						ret.append(no.getElemento().iGetEstrutura().iGetNome()+" - ");
					}
					
					ret.append(itemDao.criaColunaConteudoColunaArvoreAjax(item,estrutura));
				
				} else {//Não marca o checkbox.
					if (seguranca != null) {// Avalia se o item pode ser exibido para o usuário ou não.
						int status = validarAcessoItem(no);
						
						if (status == EXIBIR_HABILITADO) {
							ret.append("<input type='checkbox' value='"+no.getElemento().iGetCodigo()+"' name='"+nomeCheckBox+"' />");
							if (exibirNomeEstrutura) {//Verifica se deve exibir o nome da estrutura do item
								ret.append(no.getElemento().iGetEstrutura().iGetNome()+" - ");
							}
							
							ret.append(itemDao.criaColunaConteudoColunaArvoreAjax(item,estrutura));
						
						} else if (status == EXIBIR_DESABILITADO){
							ret.append("<input type='checkbox' value='"+no.getElemento().iGetCodigo()+"' name='"+nomeCheckBox+"' disabled=\"false\" />");
							if (exibirNomeEstrutura) {//Verifica se deve exibir o nome da estrutura do item
								ret.append(no.getElemento().iGetEstrutura().iGetNome()+" - ");
							}
						
							ret.append(itemDao.criaColunaConteudoColunaArvoreAjax(item,estrutura));
						}
					} else {// Não avalia a exibição do item quanto a segurança.
						ret.append("<input type='checkbox' value='"+no.getElemento().iGetCodigo()+"' name='"+nomeCheckBox+"' />");
						if (exibirNomeEstrutura) {//Verifica se deve exibir o nome da estrutura do item
							ret.append(no.getElemento().iGetEstrutura().iGetNome()+" - ");
						}
						
						ret.append(itemDao.criaColunaConteudoColunaArvoreAjax(item,estrutura));
					}
				}
			} else { // Não avalia se deve marcar itens já associados.
				if (seguranca != null) {// Avalia se o item pode ser exibido para o usuário ou não.
					int status = validarAcessoItem(no);
					
					if (status == EXIBIR_HABILITADO) {
						ret.append("<input type='checkbox' value='"+no.getElemento().iGetCodigo()+"' name='"+nomeCheckBox+"' />");
						if (exibirNomeEstrutura) {//Verifica se deve exibir o nome da estrutura do item
							ret.append(no.getElemento().iGetEstrutura().iGetNome()+" - ");
						}
						ret.append(no.getElemento().iGetNome());
					} else if (status == EXIBIR_DESABILITADO){
						ret.append("<input type='checkbox' value='"+no.getElemento().iGetCodigo()+"' name='"+nomeCheckBox+"' disabled=\"false\" />");
						if (exibirNomeEstrutura) {//Verifica se deve exibir o nome da estrutura do item
							ret.append(no.getElemento().iGetEstrutura().iGetNome()+" - ");
						}
						ret.append(no.getElemento().iGetNome());
					}
				} else {// Não avalia a exibição do item quanto a segurança.
					ret.append("<input type='checkbox' value='"+no.getElemento().iGetCodigo()+"' name='"+nomeCheckBox+"' />");
					if (exibirNomeEstrutura) {//Verifica se deve exibir o nome da estrutura do item
						ret.append(no.getElemento().iGetEstrutura().iGetNome()+" - ");
					}
					ret.append(no.getElemento().iGetNome());
				}
			}
		} else {
			if (exibirNomeEstrutura) {//Verifica se deve exibir o nome da estrutura do item
				ret.append(no.getElemento().iGetEstrutura().iGetNome()+" - ");
			}
			ret.append(no.getElemento().iGetNome());
		}
		
		return ret.toString();
	}


	private int validarAcessoItem(NoArvoreEstrutura no) throws ECARException {
		
		ValidaPermissao validaPermissao = null;
		boolean permissaoAcessoItem = false;
		boolean permissaoAcessoItensFilhos = false;
		int ret = NAO_EXIBIR;
		
		if (seguranca != null && no.getElemento() instanceof ItemEstruturaIett) {
			validaPermissao = new ValidaPermissao();
			validaPermissao.permissoesItem((ItemEstruturaIett)no.getElemento(), seguranca.getUsuario(), seguranca.getGruposAcesso());
			
			permissaoAcessoItem = validaPermissao.permissaoExcluirItem() || validaPermissao.permissaoConsultarItem();
			
			if(!permissaoAcessoItem){
				permissaoAcessoItensFilhos = validaPermissao.permissaoAcessoItensFilhos((ItemEstruturaIett)no.getElemento(), seguranca.getUsuario(), seguranca.getGruposAcesso());
			}
			
		}
		
		
		if(permissaoAcessoItem || permissaoAcessoItensFilhos){
			
			if (validaPermissao.permissaoConsultarItem()){
				ret = EXIBIR_HABILITADO;
			} else if (permissaoAcessoItensFilhos){
				ret = EXIBIR_DESABILITADO;
			}
		} 
	
		
		return ret;
	}


	private void localizarFilhos(List<NoArvoreEstrutura> filhos,int deslocamento) throws IOException, ECARException {
		
		for (NoArvoreEstrutura no : filhos) {
			String html = montarNoArvore(no);

			deslocamento+=VALORDESLOCAMENTO;

			if (!html.equals(Dominios.STRING_VAZIA)) {
				writer.println("<tr>");
					writer.println("<td>");
						writer.println(montarDeslocamento(deslocamento)+html);
					writer.println("</td>");
				writer.println("</tr>");
			}
			
			if (no.getListaFilhos() != null && !no.getListaFilhos().isEmpty()){
				localizarFilhos(no.getListaFilhos(),deslocamento);
			}
			
			deslocamento-=VALORDESLOCAMENTO;
		}
	}


	private String montarDeslocamento(int deslocamento) {
		
		StringBuffer ret = new StringBuffer();
		
		for (int i = 0; i < deslocamento; i++) {
			ret.append("&nbsp;");
			
		}
		
		return ret.toString();
	}


	
        /**
         *
         * @return
         */
        public boolean isTemCheckbox() {
		return temCheckBox;
	}


        /**
         *
         * @param temCheckbox
         */
        public void setTemCheckBox(boolean temCheckbox) {
		this.temCheckBox = temCheckbox;
	}


        /**
         *
         * @return
         */
        public EstruturaDao getEstruturaDao() {
		return estruturaDao;
	}


        /**
         *
         * @param estruturaDao
         */
        public void setEstruturaDao(EstruturaDao estruturaDao) {
		this.estruturaDao = estruturaDao;
	}

	
        /**
         *
         * @return
         */
        public List<Hierarchyable> getLista() {
		return lista;
	}


        /**
         *
         * @param lista
         */
        public void setLista(List<Hierarchyable> lista) {
		this.lista = lista;
	}


        /**
         *
         * @return
         */
        public String getNomeCheckBox() {
		return nomeCheckBox;
	}

        /**
         *
         * @param nomeCheckBox
         */
        public void setNomeCheckBox(String nomeCheckBox) {
		this.nomeCheckBox = nomeCheckBox;
	}

        /**
         *
         * @return
         */
        public boolean isMarcarItensAssociadosEstruturaVirutal() {
		return marcarItensAssociadosEstruturaVirutal;
	}


        /**
         *
         * @param marcarItensAssociadosEstruturaVirutal
         */
        public void setMarcarItensAssociadosEstruturaVirutal(boolean marcarItensAssociadosEstruturaVirutal) {
		this.marcarItensAssociadosEstruturaVirutal = marcarItensAssociadosEstruturaVirutal;
	}
	
        /**
         *
         * @return
         */
        public EstruturaEtt getEstruturaVirtual() {
		return estruturaVirtual;
	}


        /**
         *
         * @param estruturaVirtual
         */
        public void setEstruturaVirtual(EstruturaEtt estruturaVirtual) {
		this.estruturaVirtual = estruturaVirtual;
	}
	
        /**
         *
         * @return
         */
        public boolean isExibirNomeEstrutura() {
		return exibirNomeEstrutura;
	}


        /**
         *
         * @param exibirNomeEstrutura
         */
        public void setExibirNomeEstrutura(boolean exibirNomeEstrutura) {
		this.exibirNomeEstrutura = exibirNomeEstrutura;
	}

        /**
         *
         * @return
         */
        public SegurancaECAR getSeguranca() {
		return seguranca;
	}


        /**
         *
         * @param seguranca
         */
        public void setSeguranca(SegurancaECAR seguranca) {
		this.seguranca = seguranca;
	}
}
