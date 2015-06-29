package ecar.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import comum.database.Dao;

import ecar.exception.ECARException;
import ecar.pojo.GrupoAtributoGatt;

/**
 * Classe de manipula��o de objetos da classe AtributoAtt.
 *
 * @author CodeGenerator - Esta classe foi gerada automaticamente
 * @since 1.0
 * @version 1.0, Fri Jan 27 07:54:28 BRST 2006
 *
 */
public class GrupoAtributoDao
    extends Dao
{
    /**
     * Construtor. Chama o Session factory do Hibernate
     *
     * @param request
     */
    public GrupoAtributoDao( HttpServletRequest request )
    {
        super(  );
        this.request = request;
    }

    /**
     *
     * @author n/c
     * @return List
     * @throws ECARException
     */
    public List getGruposAtributosCadastroUsuario(  )
                                           throws ECARException
    {
        GrupoAtributoGatt grupoAtributo = new GrupoAtributoGatt(  );
        grupoAtributo.setIndAtivoGatt( "S" );
        grupoAtributo.setIndSistemaGatt( "N" );

        return this.pesquisar( grupoAtributo,
                               new String[] { "seqApresentacaoGatt", "asc" } );
    }

    /**
     * Obtem um List dos grupos de atributos passando como par�metro o indicador
     * da tabela
     * @param indTabelaUso String
     * @return List
     * @throws ECARException
     */
    public List getGrupoAtributos( String indTabelaUso )
                           throws ECARException
    {
        GrupoAtributoGatt grupoAtributo = new GrupoAtributoGatt(  );
        grupoAtributo.setIndTabelaUsoGatt( indTabelaUso );

        return this.pesquisar( grupoAtributo,
                               new String[] { "seqApresentacaoGatt", "asc" } );
    }
}
