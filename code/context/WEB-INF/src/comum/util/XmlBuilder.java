package comum.util;

import ecar.util.Dominios;

/**
 * Classe responsável pela geração de arquivos XML, obedecendo os respectivos padrões. 
 * @author rodrigo.hjort
 */
public class XmlBuilder {
	
    //nível no documento
    private int level = 0;
    
    //texto temporário para o XML
    private StringBuffer sb;
    
    /**
     * Construtor da classe.
     */
    public XmlBuilder() {
        sb = new StringBuffer();
        sb.append("<?xml version=\"1.0\" encoding=\"" + Dominios.ENCODING_DEFAULT + "\"?>\n");
    }
    
    /**
     * Retorna texto referente ao XML.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return StringBuffer 	- texto referente ao XML
     */
    public StringBuffer toStringBuffer() {
        return(sb);
    }

    /**
     * Inclui um novo nó, fechando-o em seguida. Ex:<p>
     * <blockquote><pre>
     *   &lt;periodo/&gt;
	 * </pre></blockquote>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
     * @param name
     */
    public void addClosedNode(String name) {
        sb.append(getTabs() + "<" + name + "/>\n");
    }
    
    /**
     * Inclui um novo nó com campos, fechando-o em seguida. Ex:<p>
     * <blockquote><pre>
     *   &lt;cabecalho data="03/01/2005" anoletivo="2005"/&gt;
	 * </pre></blockquote>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
     * @param name
     * @param fields
     */
    public void addClosedNode(String name, String fields) {
        sb.append(getTabs() + "<" + name + " " + fields + "/>\n");
    }
    
    /**
     * Inclui um novo nó e aumenta o nível no documento.<br>
     * Ex:<p>
     * <blockquote><pre>
     *   &lt;dados&gt;
	 * </pre></blockquote>
	 * 
     * @param name
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void addNode(String name) {
        sb.append(getTabs() + "<" + name + ">\n");
        level++;
    }

    /**
     * Inclui um novo nó com campos e aumenta o nível no documento. Ex:<p>
     * <blockquote><pre>
     *   &lt;turma codigo="3" nome="A"&gt;
	 * </pre></blockquote>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
     * @param name
     * @param fields
     */
    public void addNode(String name, String fields) {
        sb.append(getTabs() + "<" + name + " " + fields + ">\n");
        level++;
    }
    
    /**
     * Inclui um novo nó com valor, fechando-o em seguida. Ex:<p>
     * <blockquote><pre>
     *   &lt;curso&gt;ENS MEDIO - E.J.A. Pressencial&lt;/curso&gt;
	 * </pre></blockquote>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
     * @param name
     * @param value
     */
    public void addAndCloseNode(String name, String value) {
        sb.append(getTabs() + "<" + name + ">" + value + "</" + name + ">\n");
    }
    
    /**
     * Fecha um novo nó e diminui o nível no documento. Ex:<p>
     * <blockquote><pre>
     *   &lt;/dados&gt;
	 * </pre></blockquote>
	 * 
     * @param name
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void closeNode(String name) {
        level--;
        sb.append(getTabs() + "</" + name + ">\n");
    }
    
    /**
     * Uso interno.
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return	String - o número de tabulações necessário de acordo com o nível no documento
     */
    private String getTabs() {
        String ret = "";
        for (int ii = 0; ii < level; ii++)
            ret += "\t";
        return(ret);
    }
    
    /**
     * Normaliza Chars:<br>
	 * Ex.:<br>
	 * de '<' para "&lt;"<br>
	 * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param s
     * @param str
     * @param i
     */
	public void normalizeChars(String s, StringBuffer str, int i) {
		Util.normalizeChars(s, str, i);
	}

	/**
	 * Normaliza Enter.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param s
         * @param i
         * @param str
	 */
	public void normalizeEnter(String s, StringBuffer str, int i) {
		Util.normalizeEnter(s, str, i);
	}

	/** Normaliza o String para apresenta-lo em HTML sem retirar o retorno de linha.
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param s
	 * @return String
	 */ 
	public String normalize(String s) {
		return Util.normalize(s);
	}
    
}
