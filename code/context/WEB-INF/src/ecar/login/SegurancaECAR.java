/*
 * Created on 24/01/2005
 *
 */
package ecar.login;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import comum.database.SentinelaUtil;

import ecar.dao.ConfiguracaoDao;
import ecar.dao.UsuarioDao;
import ecar.exception.ECARException;
import ecar.pojo.ConfiguracaoCfg;
import ecar.pojo.SisGrupoAtributoSga;
import ecar.pojo.UsuarioAtributoUsua;
import ecar.pojo.UsuarioUsu;
import ecar.util.Dominios;
import gov.pr.celepar.sentinela.comunicacao.SentinelaInterface;

/**
 * @author garten
 */
public class SegurancaECAR {
    
    UsuarioUsu usuario;
    Set gruposAcesso;
    private boolean alterarSenha;
    private boolean autenticado;
    private String paginaInicialUsuario;
  
   
    /**
     * Construtor que Inicializa classe.<br>
     * 
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     */
    public SegurancaECAR() {
        setAutenticado(false);
        setUsuario(new UsuarioUsu());
        setGruposAcesso(new HashSet());
        setAlterarSenha(false);
    }


    /**
     * Retorna boolean alterarSenha.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @return boolean - Returns the alterarSenha.
     */
    public boolean isAlterarSenha() {
        return alterarSenha;
    }
    
    /**
     * Atribui valor especificado para bollean alterarSenha.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param alterarSenha
     */
    public void setAlterarSenha(boolean alterarSenha) {
        this.alterarSenha = alterarSenha;
    }
    
    
    /**
     * Retorna boolean autenticado.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @return boolean - Returns the autenticado.
     */
    public boolean isAutenticado() {
        return autenticado;
    }
    
    /**
     * Atribui valor especificado para boolean autenticado.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param autenticado The autenticado to set.
     */
    public void setAutenticado(boolean autenticado) {
        this.autenticado = autenticado;
    }
    
    /**
     * Retorna UsusarioUsu usuario.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @return UsuarioUsu - Returns the usuario.
     */
    public UsuarioUsu getUsuario() {
        return usuario;
    }
    
    /**
     * Atribui valor especificado para UsuarioUsu usuario.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param usuario
     */
    public void setUsuario(UsuarioUsu usuario) {
        this.usuario = usuario;
    }
    
    /**
     * Atribui valor especificado para String IdUsuarioUsu.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param login
     */
    public void setLogin(String login) {
        getUsuario().setIdUsuarioUsu(login);
    }
    
    /**
     * Retorna String IdUsuarioUsu.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @return String
     */
    public String getLogin() {
        return getUsuario().getIdUsuarioUsu();
    }
    
    /**
     * Atribui valor especificado para String SenhaUsu.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param senha
     */
    public void setSenha(String senha) {
        getUsuario().setSenhaUsu(senha);
    }
    
    /**
     * Retorna String SenhaUsu.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @return String
     */
    public String getSenha() {
        return getUsuario().getSenhaUsu();
    }
    
    /**
     * Atribui valor especificado para Long CodUsu.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param codigo
     */
    public void setCodUsu(Long codigo) {
        getUsuario().setCodUsu(codigo);
    }
    
    /**
     * Retorna Long CodUsu.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @return Long
     */
    public Long getCodUsu() {
        return getUsuario().getCodUsu();
    }
    
    /*public String getNomeUsu() {
        return getUsuario().getNomeUsuSent();
    }*/
    
    /*public void setNomeUsu(String nome) {
        getUsuario().setNomeUsu(nome);
    }*/
    /**
     * Retorna Ser gruposAcesso.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @return Set
     */
    public Set getGruposAcesso() {
        return gruposAcesso;
    }
    
    /**
     * Atribui valor especificado para Set gruposAcesso.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param gruposAcesso
     */
    public void setGruposAcesso(Set gruposAcesso) {
        this.gruposAcesso = gruposAcesso;
    }
    
    
    /**
     * Obter demais dados do usuário no banco de dados do ECAR através do código do usuário no sentinela.<br>
     * 
     * @param request
     * @author N/C
     * @since N/C
     * @version N/C
     * @throws ECARException
     */
    public void getDadosUsuario(HttpServletRequest request) throws ECARException {
    	// Pega a interface de comunicação do sentinela
    	SentinelaInterface com = SentinelaUtil.getSentinelaInterface();

    	UsuarioUsu u;
        UsuarioDao uDao = new UsuarioDao(request);
        ConfiguracaoCfg conf = (new ConfiguracaoDao(request)).getConfiguracao();
        
        //recuperar a página inicial do usuário, tendo como parâmetro as connfigurações do ambiente
        SisGrupoAtributoSga sisGrupo = conf.getSisGrupoAtributoSgaByCodSgaGrAtrPgIni();

        //recuperar dados do usuário pelo id do sentinela
        u = uDao.getUsuarioByIdDominio(String.valueOf(com.getCodUsuario()));
        
        if (u.getCodUsu() != null && Dominios.SIM.equals(u.getIndAtivoUsu())) {
            // carrega os grupos de acesso do usuario para aumentar o desempenho nos testes de permissao de acesso
            this.setGruposAcesso(uDao.getClassesAcessoUsuario(u));
    		List atributosUsuario = uDao.getAtributosUsuarioByGrupo(u, sisGrupo);  //List de SisAtributoSatb
    		if(atributosUsuario != null && atributosUsuario.size() > 0){
				UsuarioAtributoUsua usuAtrib = (UsuarioAtributoUsua)atributosUsuario.get(0);
				this.setPaginaInicialUsuario(usuAtrib.getSisAtributoSatb().getAtribInfCompSatb());
    		}
            // atribui o usuario para o objeto segurancaECAR
            setUsuario(u);           
            setAutenticado(true);
        } else
            setAutenticado(false);
    }

    /**
     * Retorna String paginaInicialUsuario.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @return String
     */
	public String getPaginaInicialUsuario() {
		return paginaInicialUsuario;
	}

	/**
	 * Retorna String paginaInicialUsuario.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
         * @param paginaInicialUsuario
	 */
	public void setPaginaInicialUsuario(String paginaInicialUsuario) {
		this.paginaInicialUsuario = paginaInicialUsuario;
	}

}
