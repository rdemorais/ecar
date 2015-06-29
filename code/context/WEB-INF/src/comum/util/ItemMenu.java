/*
 * Created on 25/04/2005
 *
 */
package comum.util;

import gov.pr.celepar.sentinela.comunicacao.SentinelaParam;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/** 
 * @author garten
 * @since 25/04/2005
 * @version N/C
 */
public class ItemMenu implements Serializable{
    

	private static final long serialVersionUID = 7212422172582344256L;
	private static final int COMANDO = 0;
    private static final int ITEM_MENU	 = 1;

    private Integer		codigo;
    private int 		hierarquia;
    private String		nome;
    private String 		descricao;  
    private String 		url;
    private ItemMenu	itemMenuPai;
    private List		itemMenuFilhos;
    private boolean		acessoPublico;
    private boolean		funcaoGenerica;
    
    

    /**
     * Construtor default, gera um item de menu.<br>
     * 
     * @param pai
     * @author N/C
     * @since N/C
     * @version N/C
     */
    public ItemMenu(ItemMenu pai) {
        setAcessoPublico(false);  // a interface do sentinela nao exporta esses parametros
        setFuncaoGenerica(false);
        setItemMenuPai(pai);
    }

    /**
     * Construtor completo.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param pai
     * @param codigo
     * @param nome
     * @param url
     */
    public ItemMenu(ItemMenu pai, Integer codigo, String nome, String url) {
        setCodigo(codigo);
        setHierarquia("".equals(url) ? ITEM_MENU : COMANDO);
        setNome(nome);
        setDescricao(""); 			// a interface do sentinela nao exporta esses parametros
        setUrl(url);
        setItemMenuPai(pai);
        setItemMenuFilhos(new ArrayList());
        setAcessoPublico(false);	// a interface do sentinela nao exporta esse parametros
        setFuncaoGenerica(false);	// a interface do sentinela nao exporta esse parametros
    }
    

    /**
     * Retorna uma lista de Item de Menu.<br>
     * Percorre o array fornecido pela interface do sentinela e agrupa os menus de uma forma estruturada.<br>
     * 
     * @param menuSentinela
     * @author N/C
     * @since N/C
     * @version N/C
     * @return List					- List de ItemMenu
     */
    public static List carregaMenu(SentinelaParam[] menuSentinela) {
        HashMap tabela = new HashMap();  //tabela hash com os itens de menu sem pai (menu(s) principal(ais))
        List lista = new ArrayList();
        
        Integer codigo;
        String nome;
        String url;
        Integer codigoPai;
        
		for(int i = 0; i < menuSentinela.length; i++) {
			long codAux = menuSentinela[i].getCodigo();
			
			codigo		= Integer.valueOf(Long.valueOf(codAux).intValue());
			
			nome		= menuSentinela[i].getNome();
			url			= menuSentinela[i].getParamAux()[0];
			codigoPai	= (menuSentinela[i].getParamAux()[1].trim().length() > 0) ? 
					Integer.valueOf(Integer.parseInt(menuSentinela[i].getParamAux()[1])) :
						Integer.valueOf(0);
		    
		    if(codigoPai.intValue() == 0) {
		        ItemMenu m = new ItemMenu(null, codigo, nome, url);
		        // coloca na tabela hash para pesquisar depois
		        if (!tabela.containsKey(codigo))
		            tabela.put(codigo, m);
		        lista.add(m);
		    } else {
		        // busca o pai
		        ItemMenu pai = (ItemMenu) tabela.get(codigoPai);
		        // seta o pai ao filho
		        ItemMenu m = new ItemMenu(pai, codigo, nome, url);
		        
		        if (pai != null){
			        if (pai.getItemMenuFilhos() == null){
			        	pai.setItemMenuFilhos(new ArrayList<ItemMenu>());
			        	
			        }
			        // adiciona o filho na lista de filhos do pai
			        pai.getItemMenuFilhos().add(m);
			        
			        //deve testar se o item de menu é um novo menu
			        if(m.getHierarquia() == ITEM_MENU)
				        if (!tabela.containsKey(codigo))
				            tabela.put(codigo, m);
			    }
		    }
		}
        return lista;
    }
    
    /**
     * Retorna boolean acessoPublico.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @return boolean				- Returns the acessoPublico.
     */
    public boolean isAcessoPublico() {
        return acessoPublico;
    }
    
    /**
     * Atribui valor especificado para boolean acessoPublico.<br>
     * E utilizado para definir se a funcao no menu pode ou nao ser visto sem uso de chave e senha.<br>
     * 
     * @param acessoPublico
     * @author N/C
     * @since N/C
     * @version N/C
     */
    public void setAcessoPublico(boolean acessoPublico) {
        this.acessoPublico = acessoPublico;
    }
    
    /**
     * Retorna integer codigo.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @return Integer				- Returns the codigo.
     */
    public Integer getCodigo() {
        return codigo;
    }
    
    /**
     * Atribui valor especificado para integer codigo.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param codigo
     */
    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }
    
    /**
     * Retorna String descricao.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @return String				- Returns the descricao.
     */
    public String getDescricao() {
        return descricao;
    }
    
    /**
     * Atribui valor especificado para a String descricao.<br>
     * 
     * @param descricao
     * @author N/C
     * @since N/C
     * @version N/C
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    /**
     * Retorna boolean funcaoGenerica.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @return boolean				- Returns the funcaoGenerica.
     */
    public boolean isFuncaoGenerica() {
        return funcaoGenerica;
    }
    
    /**
     * Atribui valor especificado para boolean funcaoGenerica.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param funcaoGenerica
     */
    public void setFuncaoGenerica(boolean funcaoGenerica) {
        this.funcaoGenerica = funcaoGenerica;
    }
    
    /**
     * Retorna int hierarquia.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @return int					- Returns the hierarquia.
     */
    public int getHierarquia() {
        return hierarquia;
    }
    
    /**
     * Atribui valor especificado para int hierarquia.<br>
     * 
     * @param hierarquia
     * @author N/C
     * @since N/C
     * @version N/C
     */
    public void setHierarquia(int hierarquia) {
        this.hierarquia = hierarquia;
    }
    
    /**
     * Retorna List itemMenuFilhos.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @return List					- Returns the itemMenuFilhos.
     */
    public List getItemMenuFilhos() {
        return itemMenuFilhos;
    }
    
    /**
     * Atribui valor especificado para List itemMenuFilhos.<br>
     * 
     * @param itemMenuFilhos
     * @author N/C
     * @since N/C
     * @version N/C
     */
    public void setItemMenuFilhos(List itemMenuFilhos) {
        this.itemMenuFilhos = itemMenuFilhos;
    }
    
    /**
     * Retorna itemMenuPai.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @return ItemMenu				- Returns the itemMenuPai.
     */
    public ItemMenu getItemMenuPai() {
        return itemMenuPai;
    }
    
    /**
     * Atribui valor especificado para ItemMenu ItemMenuPai
     * 
     * @param itemMenuPai
     * @author N/C
     * @since N/C
     * @version N/C
     */
    public void setItemMenuPai(ItemMenu itemMenuPai) {
        this.itemMenuPai = itemMenuPai;
    }
    
    /**
     * Retorna String nome.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @return String				- Returns the nome.
     */
    public String getNome() {
        return nome;
    }
    
    /**
     * Atribui valor especificado para String nome.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    /**
     * Retorna String url.<br>
     * 
     * @author N/C
     * @since N/C
     * @return String				- Returns the url.
     */
    public String getUrl() {
        return url;
    }
    
    /**
     * Atribui valor especificado para String url.<br>
     * 
     * @author N/C
     * @since N/C
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }
}
