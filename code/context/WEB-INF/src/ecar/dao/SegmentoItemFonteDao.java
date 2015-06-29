/*
 * Criado em 29/04/2005
 *
 */
package ecar.dao;

import javax.servlet.http.HttpServletRequest;

import comum.database.Dao;

import ecar.exception.ECARException;
import ecar.pojo.SegmentoItemFonteSgtif;

/**
 * @author felipev
 *
 */
public class SegmentoItemFonteDao extends Dao{
    /**
     * Construtor. Chama o Session factory do Hibernate
     * @param request
     */
    public SegmentoItemFonteDao(HttpServletRequest request) {
		super();
		this.request = request;
    }
    
    /**
     * Exclui uma fonte, verificando antes se ela possui relação com outras tabelas. Neste caso, não permite
     * exclusão
     * @param fonte
     * @throws ECARException
     */
    public void excluir(SegmentoItemFonteSgtif fonte) throws ECARException {     
       try{
            boolean excluir = true;

            if(contar(fonte.getSegmentoItemSgtis()) > 0){ 
                excluir = false;
                throw new ECARException("fonte.exclusao.erro.segmentoItem");
            }                      
            if(excluir)
                super.excluir(fonte);    
       }catch(ECARException e){
    	   this.logger.error(e);
           throw e;
       }    
    }
    
    /**
     * Verifica duplicação depois salva
     * @param fonte
     * @throws ECARException
     */
    public void salvar(SegmentoItemFonteSgtif fonte) throws ECARException {
        if (pesquisarDuplos(fonte, new String[] {"descricaoSgtif"}, "codSgtif").size() > 0)
            throw new ECARException("fonte.validacao.registroDuplicado");
        super.salvar(fonte);
    }
    
    /**
     * 
     * Verifica duplicação depois altera
     * @param fonte
     * @throws ECARException
     */
    public void alterar(SegmentoItemFonteSgtif fonte) throws ECARException {
        if (pesquisarDuplos(fonte, new String[] {"descricaoSgtif"}, "codSgtif").size() > 0)
            throw new ECARException("fonte.validacao.registroDuplicado");
        super.alterar(fonte);
    }
}
