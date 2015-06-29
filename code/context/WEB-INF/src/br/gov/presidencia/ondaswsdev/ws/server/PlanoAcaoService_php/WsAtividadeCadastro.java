/**
 * WsAtividadeCadastro.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php;

import java.util.Date;

import ecar.pojo.simpr.WsPessoaFisica;

public class WsAtividadeCadastro  implements java.io.Serializable {
    private int chvAtividadeExterna;

    private int chvAcaoExterna;

    private java.lang.Integer chvAtividadeMatrizExterna;

    private br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsPessoaJuridica orgaoResponsavel;

    private java.lang.String nomeAtividade;

    private int tipoAtividade;

    private java.lang.String valorAlcancado;

    private java.lang.Integer unidadeMedida;

    private br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsPessoaFisica gestorAtividade;

    private java.lang.String dataInicioAtividade;

    private java.lang.String dataTerminoAtividade;

    private java.lang.Integer percentualExecutado;

    private java.lang.Integer situacaoAtividade;

    private java.lang.String justificativaAtividade;

    private boolean ativo;

    private java.lang.String ordenacaoAtividade;

    public WsAtividadeCadastro() {
    }

    public WsAtividadeCadastro(
           int chvAtividadeExterna,
           int chvAcaoExterna,
           java.lang.Integer chvAtividadeMatrizExterna,
           br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsPessoaJuridica orgaoResponsavel,
           java.lang.String nomeAtividade,
           int tipoAtividade,
           java.lang.String valorAlcancado,
           java.lang.Integer unidadeMedida,
           br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsPessoaFisica gestorAtividade,
           java.lang.String dataInicioAtividade,
           java.lang.String dataTerminoAtividade,
           java.lang.Integer percentualExecutado,
           java.lang.Integer situacaoAtividade,
           java.lang.String justificativaAtividade,
           boolean ativo,
           java.lang.String ordenacaoAtividade) {
           this.chvAtividadeExterna = chvAtividadeExterna;
           this.chvAcaoExterna = chvAcaoExterna;
           this.chvAtividadeMatrizExterna = chvAtividadeMatrizExterna;
           this.orgaoResponsavel = orgaoResponsavel;
           this.nomeAtividade = nomeAtividade;
           this.tipoAtividade = tipoAtividade;
           this.valorAlcancado = valorAlcancado;
           this.unidadeMedida = unidadeMedida;
           this.gestorAtividade = gestorAtividade;
           this.dataInicioAtividade = dataInicioAtividade;
           this.dataTerminoAtividade = dataTerminoAtividade;
           this.percentualExecutado = percentualExecutado;
           this.situacaoAtividade = situacaoAtividade;
           this.justificativaAtividade = justificativaAtividade;
           this.ativo = ativo;
           this.ordenacaoAtividade = ordenacaoAtividade;
    }
    
	public WsAtividadeCadastro(
			Long chvExterno,
			String tipoTarefaCod, 
			String nomeTarefa,
			String unidadeMedidaDuracaoCod, 
			String responsavelCpf,
			String responsavelNome,
			Date dataHoraInicioPrevisto,
			Date dataHoraTerminoPrevisto,
			String ordenacao,
			Long codSit,
			Integer chaveAcaoExterna) {
		this.chvAtividadeExterna = Integer.parseInt(chvExterno.toString());
		this.tipoAtividade = Integer.valueOf(tipoTarefaCod);
		this.nomeAtividade = nomeTarefa;
		this.unidadeMedida = Integer.valueOf(unidadeMedidaDuracaoCod);
		this.gestorAtividade = new br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsPessoaFisica(responsavelCpf, responsavelNome);
		if(dataHoraInicioPrevisto != null) {
			this.dataInicioAtividade = dataHoraInicioPrevisto.toString();			
		}
		if(dataHoraTerminoPrevisto != null) {
			this.dataTerminoAtividade = dataHoraTerminoPrevisto.toString();			
		}
		this.ordenacaoAtividade = ordenacao;
		this.percentualExecutado = 0;
		if(codSit != null) {
			this.situacaoAtividade = codSit.intValue();			
		}
		this.orgaoResponsavel = new WsPessoaJuridica("00394544012787", "Ministerio da Saude");
		this.chvAcaoExterna = chaveAcaoExterna.intValue();
	}
	
	
	public String toStringCSV() {
		StringBuffer s = new StringBuffer();
		s.append("-->|");
		s.append(this.getChvAtividadeExterna());
		s.append("|");
		s.append(this.getNomeAtividade());
		s.append("|");
		s.append(this.getGestorAtividade().getNome());
		s.append("|");
		s.append(this.getDataInicioAtividade());
		s.append("|");
		s.append(this.getDataTerminoAtividade());
		s.append("|");
		s.append(ativo);
		
		return s.toString();
	}	


    /**
     * Gets the chvAtividadeExterna value for this WsAtividadeCadastro.
     * 
     * @return chvAtividadeExterna
     */
    public int getChvAtividadeExterna() {
        return chvAtividadeExterna;
    }


    /**
     * Sets the chvAtividadeExterna value for this WsAtividadeCadastro.
     * 
     * @param chvAtividadeExterna
     */
    public void setChvAtividadeExterna(int chvAtividadeExterna) {
        this.chvAtividadeExterna = chvAtividadeExterna;
    }


    /**
     * Gets the chvAcaoExterna value for this WsAtividadeCadastro.
     * 
     * @return chvAcaoExterna
     */
    public int getChvAcaoExterna() {
        return chvAcaoExterna;
    }


    /**
     * Sets the chvAcaoExterna value for this WsAtividadeCadastro.
     * 
     * @param chvAcaoExterna
     */
    public void setChvAcaoExterna(int chvAcaoExterna) {
        this.chvAcaoExterna = chvAcaoExterna;
    }


    /**
     * Gets the chvAtividadeMatrizExterna value for this WsAtividadeCadastro.
     * 
     * @return chvAtividadeMatrizExterna
     */
    public java.lang.Integer getChvAtividadeMatrizExterna() {
        return chvAtividadeMatrizExterna;
    }


    /**
     * Sets the chvAtividadeMatrizExterna value for this WsAtividadeCadastro.
     * 
     * @param chvAtividadeMatrizExterna
     */
    public void setChvAtividadeMatrizExterna(java.lang.Integer chvAtividadeMatrizExterna) {
        this.chvAtividadeMatrizExterna = chvAtividadeMatrizExterna;
    }


    /**
     * Gets the orgaoResponsavel value for this WsAtividadeCadastro.
     * 
     * @return orgaoResponsavel
     */
    public br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsPessoaJuridica getOrgaoResponsavel() {
        return orgaoResponsavel;
    }


    /**
     * Sets the orgaoResponsavel value for this WsAtividadeCadastro.
     * 
     * @param orgaoResponsavel
     */
    public void setOrgaoResponsavel(br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsPessoaJuridica orgaoResponsavel) {
        this.orgaoResponsavel = orgaoResponsavel;
    }


    /**
     * Gets the nomeAtividade value for this WsAtividadeCadastro.
     * 
     * @return nomeAtividade
     */
    public java.lang.String getNomeAtividade() {
        return nomeAtividade;
    }


    /**
     * Sets the nomeAtividade value for this WsAtividadeCadastro.
     * 
     * @param nomeAtividade
     */
    public void setNomeAtividade(java.lang.String nomeAtividade) {
        this.nomeAtividade = nomeAtividade;
    }


    /**
     * Gets the tipoAtividade value for this WsAtividadeCadastro.
     * 
     * @return tipoAtividade
     */
    public int getTipoAtividade() {
        return tipoAtividade;
    }


    /**
     * Sets the tipoAtividade value for this WsAtividadeCadastro.
     * 
     * @param tipoAtividade
     */
    public void setTipoAtividade(int tipoAtividade) {
        this.tipoAtividade = tipoAtividade;
    }


    /**
     * Gets the valorAlcancado value for this WsAtividadeCadastro.
     * 
     * @return valorAlcancado
     */
    public java.lang.String getValorAlcancado() {
        return valorAlcancado;
    }


    /**
     * Sets the valorAlcancado value for this WsAtividadeCadastro.
     * 
     * @param valorAlcancado
     */
    public void setValorAlcancado(java.lang.String valorAlcancado) {
        this.valorAlcancado = valorAlcancado;
    }


    /**
     * Gets the unidadeMedida value for this WsAtividadeCadastro.
     * 
     * @return unidadeMedida
     */
    public java.lang.Integer getUnidadeMedida() {
        return unidadeMedida;
    }


    /**
     * Sets the unidadeMedida value for this WsAtividadeCadastro.
     * 
     * @param unidadeMedida
     */
    public void setUnidadeMedida(java.lang.Integer unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }


    /**
     * Gets the gestorAtividade value for this WsAtividadeCadastro.
     * 
     * @return gestorAtividade
     */
    public br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsPessoaFisica getGestorAtividade() {
        return gestorAtividade;
    }


    /**
     * Sets the gestorAtividade value for this WsAtividadeCadastro.
     * 
     * @param gestorAtividade
     */
    public void setGestorAtividade(br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsPessoaFisica gestorAtividade) {
        this.gestorAtividade = gestorAtividade;
    }


    /**
     * Gets the dataInicioAtividade value for this WsAtividadeCadastro.
     * 
     * @return dataInicioAtividade
     */
    public java.lang.String getDataInicioAtividade() {
        return dataInicioAtividade;
    }


    /**
     * Sets the dataInicioAtividade value for this WsAtividadeCadastro.
     * 
     * @param dataInicioAtividade
     */
    public void setDataInicioAtividade(java.lang.String dataInicioAtividade) {
        this.dataInicioAtividade = dataInicioAtividade;
    }


    /**
     * Gets the dataTerminoAtividade value for this WsAtividadeCadastro.
     * 
     * @return dataTerminoAtividade
     */
    public java.lang.String getDataTerminoAtividade() {
        return dataTerminoAtividade;
    }


    /**
     * Sets the dataTerminoAtividade value for this WsAtividadeCadastro.
     * 
     * @param dataTerminoAtividade
     */
    public void setDataTerminoAtividade(java.lang.String dataTerminoAtividade) {
        this.dataTerminoAtividade = dataTerminoAtividade;
    }


    /**
     * Gets the percentualExecutado value for this WsAtividadeCadastro.
     * 
     * @return percentualExecutado
     */
    public java.lang.Integer getPercentualExecutado() {
        return percentualExecutado;
    }


    /**
     * Sets the percentualExecutado value for this WsAtividadeCadastro.
     * 
     * @param percentualExecutado
     */
    public void setPercentualExecutado(java.lang.Integer percentualExecutado) {
        this.percentualExecutado = percentualExecutado;
    }


    /**
     * Gets the situacaoAtividade value for this WsAtividadeCadastro.
     * 
     * @return situacaoAtividade
     */
    public java.lang.Integer getSituacaoAtividade() {
        return situacaoAtividade;
    }


    /**
     * Sets the situacaoAtividade value for this WsAtividadeCadastro.
     * 
     * @param situacaoAtividade
     */
    public void setSituacaoAtividade(java.lang.Integer situacaoAtividade) {
        this.situacaoAtividade = situacaoAtividade;
    }


    /**
     * Gets the justificativaAtividade value for this WsAtividadeCadastro.
     * 
     * @return justificativaAtividade
     */
    public java.lang.String getJustificativaAtividade() {
        return justificativaAtividade;
    }


    /**
     * Sets the justificativaAtividade value for this WsAtividadeCadastro.
     * 
     * @param justificativaAtividade
     */
    public void setJustificativaAtividade(java.lang.String justificativaAtividade) {
        this.justificativaAtividade = justificativaAtividade;
    }


    /**
     * Gets the ativo value for this WsAtividadeCadastro.
     * 
     * @return ativo
     */
    public boolean isAtivo() {
        return ativo;
    }


    /**
     * Sets the ativo value for this WsAtividadeCadastro.
     * 
     * @param ativo
     */
    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }


    /**
     * Gets the ordenacaoAtividade value for this WsAtividadeCadastro.
     * 
     * @return ordenacaoAtividade
     */
    public java.lang.String getOrdenacaoAtividade() {
        return ordenacaoAtividade;
    }


    /**
     * Sets the ordenacaoAtividade value for this WsAtividadeCadastro.
     * 
     * @param ordenacaoAtividade
     */
    public void setOrdenacaoAtividade(java.lang.String ordenacaoAtividade) {
        this.ordenacaoAtividade = ordenacaoAtividade;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WsAtividadeCadastro)) return false;
        WsAtividadeCadastro other = (WsAtividadeCadastro) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.chvAtividadeExterna == other.getChvAtividadeExterna() &&
            this.chvAcaoExterna == other.getChvAcaoExterna() &&
            ((this.chvAtividadeMatrizExterna==null && other.getChvAtividadeMatrizExterna()==null) || 
             (this.chvAtividadeMatrizExterna!=null &&
              this.chvAtividadeMatrizExterna.equals(other.getChvAtividadeMatrizExterna()))) &&
            ((this.orgaoResponsavel==null && other.getOrgaoResponsavel()==null) || 
             (this.orgaoResponsavel!=null &&
              this.orgaoResponsavel.equals(other.getOrgaoResponsavel()))) &&
            ((this.nomeAtividade==null && other.getNomeAtividade()==null) || 
             (this.nomeAtividade!=null &&
              this.nomeAtividade.equals(other.getNomeAtividade()))) &&
            this.tipoAtividade == other.getTipoAtividade() &&
            ((this.valorAlcancado==null && other.getValorAlcancado()==null) || 
             (this.valorAlcancado!=null &&
              this.valorAlcancado.equals(other.getValorAlcancado()))) &&
            ((this.unidadeMedida==null && other.getUnidadeMedida()==null) || 
             (this.unidadeMedida!=null &&
              this.unidadeMedida.equals(other.getUnidadeMedida()))) &&
            ((this.gestorAtividade==null && other.getGestorAtividade()==null) || 
             (this.gestorAtividade!=null &&
              this.gestorAtividade.equals(other.getGestorAtividade()))) &&
            ((this.dataInicioAtividade==null && other.getDataInicioAtividade()==null) || 
             (this.dataInicioAtividade!=null &&
              this.dataInicioAtividade.equals(other.getDataInicioAtividade()))) &&
            ((this.dataTerminoAtividade==null && other.getDataTerminoAtividade()==null) || 
             (this.dataTerminoAtividade!=null &&
              this.dataTerminoAtividade.equals(other.getDataTerminoAtividade()))) &&
            ((this.percentualExecutado==null && other.getPercentualExecutado()==null) || 
             (this.percentualExecutado!=null &&
              this.percentualExecutado.equals(other.getPercentualExecutado()))) &&
            ((this.situacaoAtividade==null && other.getSituacaoAtividade()==null) || 
             (this.situacaoAtividade!=null &&
              this.situacaoAtividade.equals(other.getSituacaoAtividade()))) &&
            ((this.justificativaAtividade==null && other.getJustificativaAtividade()==null) || 
             (this.justificativaAtividade!=null &&
              this.justificativaAtividade.equals(other.getJustificativaAtividade()))) &&
            this.ativo == other.isAtivo() &&
            ((this.ordenacaoAtividade==null && other.getOrdenacaoAtividade()==null) || 
             (this.ordenacaoAtividade!=null &&
              this.ordenacaoAtividade.equals(other.getOrdenacaoAtividade())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        _hashCode += getChvAtividadeExterna();
        _hashCode += getChvAcaoExterna();
        if (getChvAtividadeMatrizExterna() != null) {
            _hashCode += getChvAtividadeMatrizExterna().hashCode();
        }
        if (getOrgaoResponsavel() != null) {
            _hashCode += getOrgaoResponsavel().hashCode();
        }
        if (getNomeAtividade() != null) {
            _hashCode += getNomeAtividade().hashCode();
        }
        _hashCode += getTipoAtividade();
        if (getValorAlcancado() != null) {
            _hashCode += getValorAlcancado().hashCode();
        }
        if (getUnidadeMedida() != null) {
            _hashCode += getUnidadeMedida().hashCode();
        }
        if (getGestorAtividade() != null) {
            _hashCode += getGestorAtividade().hashCode();
        }
        if (getDataInicioAtividade() != null) {
            _hashCode += getDataInicioAtividade().hashCode();
        }
        if (getDataTerminoAtividade() != null) {
            _hashCode += getDataTerminoAtividade().hashCode();
        }
        if (getPercentualExecutado() != null) {
            _hashCode += getPercentualExecutado().hashCode();
        }
        if (getSituacaoAtividade() != null) {
            _hashCode += getSituacaoAtividade().hashCode();
        }
        if (getJustificativaAtividade() != null) {
            _hashCode += getJustificativaAtividade().hashCode();
        }
        _hashCode += (isAtivo() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getOrdenacaoAtividade() != null) {
            _hashCode += getOrdenacaoAtividade().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WsAtividadeCadastro.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("https://ondaswsdev.presidencia.gov.br/ws/server/PlanoAcaoService.php", "WsAtividadeCadastro"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("chvAtividadeExterna");
        elemField.setXmlName(new javax.xml.namespace.QName("", "chvAtividadeExterna"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("chvAcaoExterna");
        elemField.setXmlName(new javax.xml.namespace.QName("", "chvAcaoExterna"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("chvAtividadeMatrizExterna");
        elemField.setXmlName(new javax.xml.namespace.QName("", "chvAtividadeMatrizExterna"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("orgaoResponsavel");
        elemField.setXmlName(new javax.xml.namespace.QName("", "orgaoResponsavel"));
        elemField.setXmlType(new javax.xml.namespace.QName("https://ondaswsdev.presidencia.gov.br/ws/server/PlanoAcaoService.php", "WsPessoaJuridica"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nomeAtividade");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nomeAtividade"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoAtividade");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoAtividade"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("valorAlcancado");
        elemField.setXmlName(new javax.xml.namespace.QName("", "valorAlcancado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("unidadeMedida");
        elemField.setXmlName(new javax.xml.namespace.QName("", "unidadeMedida"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("gestorAtividade");
        elemField.setXmlName(new javax.xml.namespace.QName("", "gestorAtividade"));
        elemField.setXmlType(new javax.xml.namespace.QName("https://ondaswsdev.presidencia.gov.br/ws/server/PlanoAcaoService.php", "WsPessoaFisica"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataInicioAtividade");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataInicioAtividade"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataTerminoAtividade");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataTerminoAtividade"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("percentualExecutado");
        elemField.setXmlName(new javax.xml.namespace.QName("", "percentualExecutado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("situacaoAtividade");
        elemField.setXmlName(new javax.xml.namespace.QName("", "situacaoAtividade"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("justificativaAtividade");
        elemField.setXmlName(new javax.xml.namespace.QName("", "justificativaAtividade"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ativo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ativo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ordenacaoAtividade");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ordenacaoAtividade"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
