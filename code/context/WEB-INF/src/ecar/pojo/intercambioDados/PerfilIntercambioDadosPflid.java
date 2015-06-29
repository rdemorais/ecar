package ecar.pojo.intercambioDados;

import java.io.Serializable;
import java.util.Date;

import ecar.pojo.SisAtributoSatb;
import ecar.pojo.TextosSiteTxt;
import ecar.pojo.UsuarioUsu;
import ecar.pojo.intercambioDados.funcionalidade.TipoFuncionalidadeEnum;
import ecar.pojo.intercambioDados.tecnologia.DadosTecnologiaPerfilDtp;

/** @author Hibernate CodeGenerator */
public abstract class PerfilIntercambioDadosPflid implements Serializable {

	/**
	 * Código do perfil gerado pelo sistema
	 * */
	private Long codPflid;
	/**
	 * Nome do perfil de configuração
	 */
	private String nomePflid;
	/**
	 * Identificador do tipo do serviço. Usado na validação do header do
	 * arquivo
	 */
	private Long codTipoServicoPflid;
	/**
	 * Nome do tipo do serviço.
	 */
	private String nomeTipoServicoPflid;

	/**
	 * Usuário a ser associado ao item importado selecionado a partir de
	 * lista.
	 */
	private String indUsuarioProcessamentoAssociacaoItemPflid;

	/**
	 * Usuário a ser associado ao item importado.
	 */
	private UsuarioUsu usuarioImportacao;
	/**
	 * Opção de envio de email
	 */
	private String indAtivoAvisoImpPflid;
	/**
	 * Grupo de acesso cujos usuários associados receberão email informando
	 * sobre evento de importação
	 */
	private SisAtributoSatb grupoAcessoEnvioEmailPflid;
	/**
	 * E-mail configurado
	 */
	private TextosSiteTxt composicaoEmailPflid;
	/**
	 * Modo de processamento permitido pelo perfil (Manual ou Automático)
	 */
	private String indModoProcessamentoPflid;
	/**
	 * Identificador do sistema destino dos dados
	 */
	private Long codSistemaDestinoPflid;
	/**
	 * Nome do sistema destino dos dados.
	 */
	private String nomeSistemaDestinoPflid;
	/**
	 * Identificador do sistema origem dos dados
	 */
	private Long codSistemaOrigemPflid;
	/**
	 * Nome do sistema origem dos dados.
	 */
	private String nomeSistemaOrigemPflid;

	/**
	 * Grupo de acesso cujos usuários associados receberão email informando
	 * sobre evento de importação
	 */
	private SisAtributoSatb sisAtributoSatbAcessoEnvioEmailImp;

	private DadosTecnologiaPerfilDtp dadosTecnologiaPerfilDtp;

	private Date dataInclusaoPflid;
	
	private UsuarioUsu usuarioInclusaoPflid;
	
	private Date dataAlteracaoPflid;
	
	private UsuarioUsu usuarioAlteracaoPflid;
	
	private String indAtivoPflid;
	
	
	public String getIndAtivoPflid() {
		return indAtivoPflid;
	}

	public void setIndAtivoPflid(String indAtivoPflid) {
		this.indAtivoPflid = indAtivoPflid;
	}

	/**
	 * 
	 * @return
	 */
	
	public Date getDataInclusaoPflid() {
		return dataInclusaoPflid;
	}

	public void setDataInclusaoPflid(Date dataInclusaoPflid) {
		this.dataInclusaoPflid = dataInclusaoPflid;
	}

	public UsuarioUsu getUsuarioInclusaoPflid() {
		return usuarioInclusaoPflid;
	}

	public void setUsuarioInclusaoPflid(UsuarioUsu usuarioInclusaoPflid) {
		this.usuarioInclusaoPflid = usuarioInclusaoPflid;
	}

	public Date getDataAlteracaoPflid() {
		return dataAlteracaoPflid;
	}

	public void setDataAlteracaoPflid(Date dataAlteracaoPflid) {
		this.dataAlteracaoPflid = dataAlteracaoPflid;
	}

	public UsuarioUsu getUsuarioAlteracaoPflid() {
		return usuarioAlteracaoPflid;
	}

	public void setUsuarioAlteracaoPflid(UsuarioUsu usuarioAlteracaoPflid) {
		this.usuarioAlteracaoPflid = usuarioAlteracaoPflid;
	}

	public SisAtributoSatb getGrupoAcessoEnvioEmailPflid() {
		return grupoAcessoEnvioEmailPflid;
	}

	public TextosSiteTxt getComposicaoEmailPflid() {
		return composicaoEmailPflid;
	}

	public SisAtributoSatb getSisAtributoSatbAcessoEnvioEmailImp() {
		return sisAtributoSatbAcessoEnvioEmailImp;
	}

	public void setSisAtributoSatbAcessoEnvioEmailImp(
			SisAtributoSatb sisAtributoSatbAcessoEnvioEmailImp) {
		this.sisAtributoSatbAcessoEnvioEmailImp = sisAtributoSatbAcessoEnvioEmailImp;
	}

	/** default constructor */
	public PerfilIntercambioDadosPflid() {
	}

	/**
	 * 
	 * @return
	 */
	public Long getCodPflid() {
		return codPflid;
	}

	/**
	 * 
	 * @param codPflid
	 */
	public void setCodPflid(Long codPflid) {
		this.codPflid = codPflid;
	}

	/**
	 * 
	 * @return
	 */
	public String getNomePflid() {
		return nomePflid;
	}

	/**
	 * 
	 * @param nomePflid
	 */
	public void setNomePflid(String nomePflid) {
		this.nomePflid = nomePflid;
	}

	/**
	 * 
	 * @return
	 */
	public Long getCodTipoServicoPflid() {
		return codTipoServicoPflid;
	}

	/**
	 * 
	 * @param codTipoServicoPflid
	 */
	public void setCodTipoServicoPflid(Long codTipoServicoPflid) {
		this.codTipoServicoPflid = codTipoServicoPflid;
	}

	/**
	 * 
	 * @return
	 */
	public String getNomeTipoServicoPflid() {
		return nomeTipoServicoPflid;
	}

	/**
	 * 
	 * @param nomeTipoServicoPflid
	 */
	public void setNomeTipoServicoPflid(String nomeTipoServicoPflid) {
		this.nomeTipoServicoPflid = nomeTipoServicoPflid;
	}

	/**
	 * 
	 * @return
	 */
	public String getIndUsuarioProcessamentoAssociacaoItemPflid() {
		return indUsuarioProcessamentoAssociacaoItemPflid;
	}

	/**
	 * 
	 * @param indUsuarioProcessamentoAssociacaoItemPflid
	 */
	public void setIndUsuarioProcessamentoAssociacaoItemPflid(
			String indUsuarioProcessamentoAssociacaoItemPflid) {
		this.indUsuarioProcessamentoAssociacaoItemPflid = indUsuarioProcessamentoAssociacaoItemPflid;
	}

	/**
	 * 
	 * @return
	 */
	public UsuarioUsu getUsuarioImportacao() {
		return usuarioImportacao;
	}

	/**
	 * 
	 * @param usuarioUsuImportacao
	 */
	public void setUsuarioImportacao(UsuarioUsu usuarioImportacao) {
		this.usuarioImportacao = usuarioImportacao;
	}

	/**
	 * 
	 * @return
	 */
	public String getIndAtivoAvisoImpPflid() {
		return indAtivoAvisoImpPflid;
	}

	/**
	 * 
	 * @param indAtivoAvisoImpPflid
	 */
	public void setIndAtivoAvisoImpPflid(String indAtivoAvisoImpPflid) {
		this.indAtivoAvisoImpPflid = indAtivoAvisoImpPflid;
	}

	/**
	 * 
	 * @param sisAtributoSatbAcessoEnvioEmailImp
	 */
	public void setGrupoAcessoEnvioEmailPflid(
			SisAtributoSatb grupoAcessoEnvioEmailPflid) {
		this.grupoAcessoEnvioEmailPflid = grupoAcessoEnvioEmailPflid;
	}

	/**
	 * @param textosSiteTxt
	 *            the textosSiteTxt to set
	 */
	public void setComposicaoEmailPflid(TextosSiteTxt composicaoEmailPflid) {
		this.composicaoEmailPflid = composicaoEmailPflid;
	}

	/**
	 * 
	 * @return
	 */
	public String getIndModoProcessamentoPflid() {
		return indModoProcessamentoPflid;
	}

	/**
	 * 
	 * @param indModoProcessamentoPflid
	 */
	public void setIndModoProcessamentoPflid(String indModoProcessamentoPflid) {
		this.indModoProcessamentoPflid = indModoProcessamentoPflid;
	}

	/**
	 * 
	 * @return
	 */
	public Long getCodSistemaDestinoPflid() {
		return codSistemaDestinoPflid;
	}

	/**
	 * 
	 * @param codSistemaDestinoPflid
	 */
	public void setCodSistemaDestinoPflid(Long codSistemaDestinoPflid) {
		this.codSistemaDestinoPflid = codSistemaDestinoPflid;
	}

	/**
	 * 
	 * @return
	 */
	public String getNomeSistemaDestinoPflid() {
		return nomeSistemaDestinoPflid;
	}

	/**
	 * 
	 * @param nomeSistemaDestinoPflid
	 */
	public void setNomeSistemaDestinoPflid(String nomeSistemaDestinoPflid) {
		this.nomeSistemaDestinoPflid = nomeSistemaDestinoPflid;
	}

	/**
	 * 
	 * @return
	 */
	public Long getCodSistemaOrigemPflid() {
		return codSistemaOrigemPflid;
	}

	/**
	 * 
	 * @param codSistemaOrigemPflid
	 */
	public void setCodSistemaOrigemPflid(Long codSistemaOrigemPflid) {
		this.codSistemaOrigemPflid = codSistemaOrigemPflid;
	}

	/**
	 * 
	 * @return
	 */
	public String getNomeSistemaOrigemPflid() {
		return nomeSistemaOrigemPflid;
	}

	/**
	 * 
	 * @param nomeSistemaOrigemPflid
	 */
	public void setNomeSistemaOrigemPflid(String nomeSistemaOrigemPflid) {
		this.nomeSistemaOrigemPflid = nomeSistemaOrigemPflid;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PerfilIntercambioDadosPflid [codPflid=" + codPflid
				+ ", codSistemaDestinoPflid=" + codSistemaDestinoPflid
				+ ", codSistemaOrigemPflid=" + codSistemaOrigemPflid
				+ ", codTipoServicoPflid=" + codTipoServicoPflid
				+ ", composicaoEmailPflid=" + composicaoEmailPflid
				+ ", dadosTecnologiaPerfilDtp=" + dadosTecnologiaPerfilDtp
				+ ", grupoAcessoEnvioEmailPflid=" + grupoAcessoEnvioEmailPflid
				+ ", indAtivoAvisoImpPflid=" + indAtivoAvisoImpPflid
				+ ", indModoProcessamentoPflid=" + indModoProcessamentoPflid
				+ ", indUsuarioProcessamentoAssociacaoItemPflid="
				+ indUsuarioProcessamentoAssociacaoItemPflid + ", nomePflid="
				+ nomePflid + ", nomeSistemaDestinoPflid="
				+ nomeSistemaDestinoPflid + ", nomeSistemaOrigemPflid="
				+ nomeSistemaOrigemPflid + ", nomeTipoServicoPflid="
				+ nomeTipoServicoPflid  
				+ ", sisAtributoSatbAcessoEnvioEmailImp="
				+ sisAtributoSatbAcessoEnvioEmailImp + ", composicaoEmail="
				+ composicaoEmailPflid + ", usuarioImportacao=" + usuarioImportacao
				+ getDadosTecnologiaPerfilDtp().toString() + "]";
	}
	
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof PerfilIntercambioDadosPflid)) {
			return false;
		}
		PerfilIntercambioDadosPflid other = (PerfilIntercambioDadosPflid) obj;
		if (codPflid == null) {
			if (other.codPflid != null) {
				return false;
			}
		} else if (!codPflid.equals(other.codPflid)) {
			return false;
		}
		if (codSistemaDestinoPflid == null) {
			if (other.codSistemaDestinoPflid != null) {
				return false;
			}
		} else if (!codSistemaDestinoPflid.equals(other.codSistemaDestinoPflid)) {
			return false;
		}
		if (codSistemaOrigemPflid == null) {
			if (other.codSistemaOrigemPflid != null) {
				return false;
			}
		} else if (!codSistemaOrigemPflid.equals(other.codSistemaOrigemPflid)) {
			return false;
		}
		if (codTipoServicoPflid == null) {
			if (other.codTipoServicoPflid != null) {
				return false;
			}
		} else if (!codTipoServicoPflid.equals(other.codTipoServicoPflid)) {
			return false;
		}

		if (composicaoEmailPflid == null) {
			if (other.composicaoEmailPflid != null) {
				return false;
			}
		} else if (!composicaoEmailPflid.equals(other.composicaoEmailPflid)) {
			return false;
		}
		if (grupoAcessoEnvioEmailPflid == null) {
			if (other.grupoAcessoEnvioEmailPflid != null) {
				return false;
			}
		} else if (!grupoAcessoEnvioEmailPflid
				.equals(other.grupoAcessoEnvioEmailPflid)) {
			return false;
		}
		if (indAtivoAvisoImpPflid == null) {
			if (other.indAtivoAvisoImpPflid != null) {
				return false;
			}
		} else if (!indAtivoAvisoImpPflid.equals(other.indAtivoAvisoImpPflid)) {
			return false;
		}
		if (indModoProcessamentoPflid == null) {
			if (other.indModoProcessamentoPflid != null) {
				return false;
			}
		} else if (!indModoProcessamentoPflid
				.equals(other.indModoProcessamentoPflid)) {
			return false;
		}
		if (indUsuarioProcessamentoAssociacaoItemPflid == null) {
			if (other.indUsuarioProcessamentoAssociacaoItemPflid != null) {
				return false;
			}
		} else if (!indUsuarioProcessamentoAssociacaoItemPflid
				.equals(other.indUsuarioProcessamentoAssociacaoItemPflid)) {
			return false;
		}
		if (nomePflid == null) {
			if (other.nomePflid != null) {
				return false;
			}
		} else if (!nomePflid.equals(other.nomePflid)) {
			return false;
		}
		if (nomeSistemaDestinoPflid == null) {
			if (other.nomeSistemaDestinoPflid != null) {
				return false;
			}
		} else if (!nomeSistemaDestinoPflid
				.equals(other.nomeSistemaDestinoPflid)) {
			return false;
		}
		if (nomeSistemaOrigemPflid == null) {
			if (other.nomeSistemaOrigemPflid != null) {
				return false;
			}
		} else if (!nomeSistemaOrigemPflid.equals(other.nomeSistemaOrigemPflid)) {
			return false;
		}
		if (nomeTipoServicoPflid == null) {
			if (other.nomeTipoServicoPflid != null) {
				return false;
			}
		} else if (!nomeTipoServicoPflid.equals(other.nomeTipoServicoPflid)) {
			return false;
		}
		if (sisAtributoSatbAcessoEnvioEmailImp == null) {
			if (other.sisAtributoSatbAcessoEnvioEmailImp != null) {
				return false;
			}
		} else if (!sisAtributoSatbAcessoEnvioEmailImp
				.equals(other.sisAtributoSatbAcessoEnvioEmailImp)) {
			return false;
		}
		if (composicaoEmailPflid == null) {
			if (other.composicaoEmailPflid != null) {
				return false;
			}
		} else if (!composicaoEmailPflid.equals(other.composicaoEmailPflid)) {
			return false;
		}
		if (usuarioImportacao == null) {
			if (other.usuarioImportacao != null) {
				return false;
			}
		} else if (!usuarioImportacao.equals(other.usuarioImportacao)) {
			return false;
		}

		if (dadosTecnologiaPerfilDtp == null) {
			if (other.dadosTecnologiaPerfilDtp != null) {
				return false;
			}
		} else {
			if (!dadosTecnologiaPerfilDtp
					.equals(other.dadosTecnologiaPerfilDtp)) {
				return false;
			}
		}

		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codPflid == null) ? 0 : codPflid.hashCode());
		result = prime
				* result
				+ ((codSistemaDestinoPflid == null) ? 0
						: codSistemaDestinoPflid.hashCode());
		result = prime
				* result
				+ ((codSistemaOrigemPflid == null) ? 0 : codSistemaOrigemPflid
						.hashCode());
		result = prime
				* result
				+ ((codTipoServicoPflid == null) ? 0 : codTipoServicoPflid
						.hashCode());
		result = prime
				* result
				+ ((composicaoEmailPflid == null) ? 0 : composicaoEmailPflid
						.hashCode());
		result = prime
				* result
				+ ((dadosTecnologiaPerfilDtp == null) ? 0
						: dadosTecnologiaPerfilDtp.hashCode());
		result = prime
				* result
				+ ((grupoAcessoEnvioEmailPflid == null) ? 0
						: grupoAcessoEnvioEmailPflid.hashCode());
		result = prime
				* result
				+ ((indAtivoAvisoImpPflid == null) ? 0 : indAtivoAvisoImpPflid
						.hashCode());
		result = prime
				* result
				+ ((indModoProcessamentoPflid == null) ? 0
						: indModoProcessamentoPflid.hashCode());
		result = prime
				* result
				+ ((indUsuarioProcessamentoAssociacaoItemPflid == null) ? 0
						: indUsuarioProcessamentoAssociacaoItemPflid.hashCode());
		result = prime * result
				+ ((nomePflid == null) ? 0 : nomePflid.hashCode());
		result = prime
				* result
				+ ((nomeSistemaDestinoPflid == null) ? 0
						: nomeSistemaDestinoPflid.hashCode());
		result = prime
				* result
				+ ((nomeSistemaOrigemPflid == null) ? 0
						: nomeSistemaOrigemPflid.hashCode());
		result = prime
				* result
				+ ((nomeTipoServicoPflid == null) ? 0 : nomeTipoServicoPflid
						.hashCode());
		result = prime
				* result
				+ ((sisAtributoSatbAcessoEnvioEmailImp == null) ? 0
						: sisAtributoSatbAcessoEnvioEmailImp.hashCode());
		result = prime * result
				+ ((composicaoEmailPflid == null) ? 0 : composicaoEmailPflid.hashCode());
		result = prime
				* result
				+ ((usuarioImportacao == null) ? 0 : usuarioImportacao
						.hashCode());

		result = prime
				* result
				+ this.getDadosTecnologiaPerfilDtp().hashCode();

		return result;
	}

	
	public abstract TipoFuncionalidadeEnum getTipoFuncionalidade();

	/**
	 * @return the dadosTecnologiaPerfilDtp
	 */
	public DadosTecnologiaPerfilDtp getDadosTecnologiaPerfilDtp() {
		return dadosTecnologiaPerfilDtp;
	}

	/**
	 * @param dadosTecnologiaPerfilDtp
	 *            the dadosTecnologiaPerfilDtp to set
	 */
	public void setDadosTecnologiaPerfilDtp(
			DadosTecnologiaPerfilDtp dadosTecnologiaPerfilDtp) {
		this.dadosTecnologiaPerfilDtp = dadosTecnologiaPerfilDtp;
	}

	public abstract String montarDadosFuncionalidadeLog();

	public abstract String montarDadosTecnologiaLog(); 
	
}
