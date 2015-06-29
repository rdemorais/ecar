/*
 * Created on 17/12/2004
 * 
 * Classe que implementa a interface PopUpPesquisa que é utilizada pelo template jsp
 * popup_pesquisa.jsp
 * 
 * O template instancia essa classe utilizando os métodos definidos na interface.
 * Assim o template pode ser usado para pesquisar e navegar em várias classes sem 
 * precisar escrever um jsp específico para cada um.
 * Basta criar uma classe que implementa a interface PopUpPesquisa e passá-la como
 * parâmatro no momento de chamar a tela de pesquisa
 * 
 * A tela de pesquisa sempre retorna um código e uma descrição do que foi selecionado.
 *
 */
package ecar.popup;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import comum.database.Dao;
import comum.util.Pagina;

import ecar.dao.EntidadeDao;
import ecar.exception.ECARException;
import ecar.pojo.EntidadeEnt;

/**
* @author evandro
*
*/
public class PopUpEntidadeOrgao implements PopUpPesquisa {
   

   private String[] pesquisarEm;
   
   
   // declare o seu pojo e o Dao aqui
   private EntidadeEnt entidadeOrgao;
   private EntidadeDao entidadeDao;

   /**
    * @author N/C
    * @since N/C
    * @version N/C
    */
   public PopUpEntidadeOrgao(){
       entidadeOrgao = new EntidadeEnt();
       entidadeDao = new EntidadeDao(null);
       this.setPesquisarEm(new String[] {"Nome"});
   }
   
   /**
    * Devolve para o template jsp o Dao.<br>
    * 
    * @author N/C
    * @since N/C
    * @version N/C
    * @return Dao
    * @see ecar.popup.PopUpPesquisa#getDao()
    */
   public Dao getDao() {
       return entidadeDao;
   }

   /**
    * Recebe o argumento de pesquisa do template jsp e seta nos respectivos campos.<br>
    * String arg - a string do argumento d pesquisa.<br>
    * String[] pesquisarEm - um array de string com os nomes dos campos que devem ser pesquisados.<br>
    * 
    * @param request
    * @author N/C
    * @since N/C
    * @version N/C
    * @throws ECARException
    */
   public void setArgPesquisa(HttpServletRequest request) throws ECARException{
       String arg = Pagina.getParam(request, "hidArg");
       String[] pesquisarEm = request.getParameterValues("hidPesquisarEm");
       
       if (pesquisarEm != null)
            for (int i = 0; i < pesquisarEm.length; i++) {
                if ("0".equals(pesquisarEm[i]))
                    entidadeOrgao.setNomeEnt(arg);
                
            }
   }

   /**
    * Retorna List com entidades pesquisadas.<br>
    * 
    * @author N/C
    * @since N/C
    * @version N/C
    * @return List
    * @throws ECARException
    */
   public List pesquisar() throws ECARException {
       entidadeOrgao.setIndAtivoEnt("S");
       entidadeOrgao.setIndOrgaoEnt("S");
       
       return entidadeDao.pesquisar(entidadeOrgao, new String[] {"nomeEnt", "asc"});
   }
   
   /**
    * Devolve para o template o codigo do radio button.<br>
    * 
    * @author N/C
    * @since N/C
    * @version N/C
    * @return String
    */
   public String getCodigo() {
       return entidadeOrgao.getCodEnt().toString();
   }
   
   /**
    * Devolve para o template a descricao.<br>
    * 
    * @author N/C
    * @since N/C
    * @version N/C
    * @return String
    */
   public String getDescricao() {
       return entidadeOrgao.getNomeEnt();
   }
   
   /**
    * Recebe um objeto do template.<br>
    * 
    * @param o
    * @author N/C
    * @since N/C
    * @version N/C
    */
   public void setPojo(Object o) {
       entidadeOrgao = (EntidadeEnt) o;
   }

   /**
    * Retorna para o template um array com os nomes para montar os checkbox.<br>
    * 
    * @author N/C
    * @since N/C
    * @version N/C
    * @return String[] - (Returns the pesquisarEm)
    */
   public String[] getPesquisarEm() {
       return pesquisarEm;
   }

   /**
    * Atribui internamente um array com os campos possiveis para a pesquisa.<br>
    * 
    * @author N/C
    * @since N/C
    * @version N/C
    * @param String[] pesquisarEm - (The pesquisarEm to set)
    */
   private void setPesquisarEm(String[] pesquisarEm) {
       this.pesquisarEm = pesquisarEm;
   }

   /**
    * Retorna String "Entidade".<br>
    * 
    * @author N/C
    * @since N/C
    * @version N/C
    * @return String
    */
   public String getTitulo() {
       return "Orgão";
   }
}