/*
 * Created on 06/10/2004
 *
 */
package ecar.taglib.util;

import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import ecar.dao.AcompReferenciaItemDao;
import ecar.dao.AcompRelatorioDao;
import ecar.dao.CorDao;
import ecar.dao.ItemEstUsutpfuacDao;
import ecar.dao.UsuarioDao;
import ecar.pojo.AcompReferenciaItemAri;
import ecar.pojo.AcompRelatorioArel;
import ecar.pojo.ItemEstUsutpfuacIettutfa;
import ecar.pojo.StatusRelatorioSrl;

/**
 * @author garten
 * 
 */
public class BarraSinaisTag extends TagSupport {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -3343172767572443615L;
	AcompReferenciaItemAri acompReferenciaItem;
    private String caminho;

    /**
     * Inicializa a montagem da tag para ser adicionada na tela de HTML.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return int
	 * @throws JspException
     */
    @Override
	public int doStartTag() throws JspException {
		JspWriter writer = this.pageContext.getOut();

		ItemEstUsutpfuacDao itemFuacDao = new ItemEstUsutpfuacDao(null);
		ItemEstUsutpfuacIettutfa itemFuac;
	    AcompRelatorioDao acompArelDao = new AcompRelatorioDao(null);
	    CorDao corDao = new CorDao(null);
	    AcompRelatorioArel acompArel;
	    List lItemFuac;
        StringBuffer sb = new StringBuffer();        
		
		try {
		    /* lista de fuac ordenada */
		    lItemFuac = itemFuacDao.getFuacEmitePosicaoOrderByFuncAcomp(getAcompReferenciaItem().getItemEstruturaIett());
		    UsuarioDao usuDao = new UsuarioDao(null);
		    
            StatusRelatorioSrl statusLiberado = (StatusRelatorioSrl) acompArelDao.
                                            buscar(StatusRelatorioSrl.class, Long.valueOf(AcompReferenciaItemDao.STATUS_LIBERADO));
            		    
		    Iterator it = lItemFuac.iterator();
		    sb.append("<table border=\"0\" class=\"texto\" cellpadding=\"0\" cellspacing=\"0\">");
		    while (it.hasNext()) {			
		        itemFuac = (ItemEstUsutpfuacIettutfa) it.next();
		        acompArel = acompArelDao.getAcompRelatorio(itemFuac.getTipoFuncAcompTpfa(), getAcompReferenciaItem());
		         
                                
		        sb.append("<tr valign=\"middle\">");
		                        
		        // if adicionado devido ao BUG 3490
		        if(acompArel == null) {
                    sb.append("<td>&nbsp;</td>");
		        }
		        else if(!"S".equals(acompArel.getIndLiberadoArel())){
                    sb.append("<td>N/L&nbsp;</td>");
		        }else if(getAcompReferenciaItem().getStatusRelatorioSrl() != null && 
                        getAcompReferenciaItem().getStatusRelatorioSrl().equals(statusLiberado)){
                    if (acompArel.getCor() != null) {
                        String sinal = corDao.getImagemSinal(acompArel.getCor(), itemFuac.getTipoFuncAcompTpfa());
                        sb.append("<td><img src=\"").append(caminho).append("/images/").append(sinal).append("\"></td>");
                    } else {                    
                        sb.append("<td>N/I&nbsp;</td>");
                    }                                             
                } else {
                    /* Se acompanhamento não estiver sido liberado */
                    sb.append("<td>N/L&nbsp;</td>");
                }
                
		        String titulo = "";
		        String telefone = "";
		        String atribuidoPor = "";
		        String posicao = "1";
				String imagem_inativa = "";
		        
		        if (itemFuac.getUsuarioUsu() != null){
		        	if(itemFuac.getUsuarioUsu().getComercDddUsu() != null && !"".equals(itemFuac.getUsuarioUsu().getComercDddUsu())){
			        	telefone += itemFuac.getUsuarioUsu().getComercDddUsu();
			        }
			        
			        if(itemFuac.getUsuarioUsu().getComercTelefoneUsu() != null && !"".equals(itemFuac.getUsuarioUsu().getComercTelefoneUsu())){
			        	if(!"".equals(telefone)){
			        		telefone += " ";
			        	}
			        	telefone += itemFuac.getUsuarioUsu().getComercTelefoneUsu();
			        }
			        
			        titulo += telefone;
			        
			        if (usuDao.getCelularByUsuario(itemFuac.getUsuarioUsu()) != null && !"".equals(usuDao.getCelularByUsuario(itemFuac.getUsuarioUsu()))){
				        titulo += " <br> ";
				        titulo += usuDao.getCelularByUsuario(itemFuac.getUsuarioUsu());
			        }
			        
			        if(itemFuac.getUsuarioUsu().getEmail1UsuSent() != null && !"".equals(itemFuac.getUsuarioUsu().getEmail1UsuSent())){
			        	if(!"".equals(titulo)){
			        		titulo += " <br> ";
			        	}
			        	titulo += itemFuac.getUsuarioUsu().getEmail1UsuSent();
			        }
			       
			        atribuidoPor = itemFuac.getUsuarioUsu().getNomeUsuSent();
			        
					imagem_inativa = "";
					
					if (!"S".equals(itemFuac.getUsuarioUsu().getIndAtivoUsu())){
						imagem_inativa="<img src=\"../images/icon_usuario_inativo.png\" title=\"Usuário Inativo\">";
					}
	                //sb.append("<td>" + itemFuac.getTipoFuncAcompTpfa().getLabelTpfa() + " atual:&nbsp;");
			        	
			        
			        
		        } else if (itemFuac.getSisAtributoSatb() != null){
		        	atribuidoPor = itemFuac.getSisAtributoSatb().getDescricaoSatb();
			        
		        }
		        
				if (acompArel != null){
					sb.append("<td class=\"item_InfoUsu_td\" onMouseOver=\"javascript:showInfoUsu(" + acompArel.getCodArel().toString() + ",'" + atribuidoPor + "','" + posicao + "')\" onMouseOut=\"javascript:hideInfoUsu(" + acompArel.getCodArel().toString() + ",'" + atribuidoPor + "','" + posicao + "')\" >").append(itemFuac.getTipoFuncAcompTpfa().getLabelTpfa()).append(":&nbsp;");
	                sb.append("<b>").append(atribuidoPor).append("</b>");
				} else
					sb.append("<td>&nbsp;</td>");
				
		        // if alterado devido ao BUG 3490
                if (acompArel != null && acompArel.getUsuarioUsuUltimaManutencao() != null && itemFuac.getUsuarioUsu() != null &&
                    acompArel.getUsuarioUsuUltimaManutencao().getCodUsu() != itemFuac.getUsuarioUsu().getCodUsu()){
                    sb.append("<br>").append(itemFuac.getTipoFuncAcompTpfa().getLabelPosicaoTpfa()).append(" atribuído por:&nbsp;").append(acompArel.getUsuarioUsuUltimaManutencao().getNomeUsuSent());
                }                   
                if(acompArel != null)
                	sb.append(" <span id=\"spanInfoUsu"+acompArel.getCodArel().toString()+"_"+atribuidoPor+"_"+posicao+"\" class=\"item_InfoUsu_span\" >"+titulo+"</span>");
                sb.append(imagem_inativa + "</td></tr>");
                
		       
		    }
		    sb.append("</table>");
            
            writer.println(sb.toString());
		    
		} catch (Exception e) {
        	org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
        	e.printStackTrace();
		}
		/* nao processa o conteudo do corpo da tag, porque nao existe */
		return Tag.SKIP_BODY;
	}

	/**
	 * Encerra Tag.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return int
	 * @throws JspException
	 */
    @Override
	public int doEndTag() throws JspException {
	    /* processa o restante da página jsp */
		return Tag.EVAL_PAGE;
	}

    /**
     * Retorna AcompReferenciaItemAri acompReferenciaItem.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return AcompReferenciaItemAri - (Returns the acompReferenciaItem)
     */
    public AcompReferenciaItemAri getAcompReferenciaItem() {
        return acompReferenciaItem;
    }
    
    /**
     * AcompReferenciaItemAri acompReferenciaItem.<br>
     * 
     * @param itemAri
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void setAcompReferenciaItem(AcompReferenciaItemAri itemAri) {
        this.acompReferenciaItem = itemAri;
    }

    /**
     * Retorna String caminho.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return String
     */
	public String getCaminho() {
		return caminho;
	}

	/**
	 * Atribui valor especificado para String caminho.<br>
	 * 
         * @param caminho
         * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}
}
