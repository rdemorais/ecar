/**
 * WsAcaoCadastro.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php;

import java.util.Date;

public class WsAcaoCadastro  implements java.io.Serializable {
    private int chvAcaoExterna;

    private int areaTematica;

    private int projeto;

    private int eixo;

    private java.lang.String nomeAcao;

    private java.lang.String planoInterno;

    private java.lang.String unidadeOrcamentaria;

    private br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsPessoaJuridica orgaoResponsavel;

    private br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsPessoaFisica gestorAcao;

    private br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsPessoaFisica monitorAcao;

    private java.lang.String dataInicioAcao="";

    private java.lang.String dataTerminoAcao="";

    private int situacaoAcao;

    private java.lang.Integer percExecucaoAcao;

    private int pesoAcao;

    private int prioridadeAcao;

    private int tipoAcao;

    private br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsCameraMonitoramento[] urlCameraMonitoramento;

    private java.lang.String populacaoAlvoAcao;

    private java.lang.String objetivoAcao;

    private boolean ativo;
    
    private java.lang.String descricaoProjeto;
    
    private java.lang.String descricaoEixo;

    public WsAcaoCadastro() {
    }

    public WsAcaoCadastro(
           int chvAcaoExterna,
           int areaTematica,
           int projeto,
           int eixo,
           java.lang.String nomeAcao,
           java.lang.String planoInterno,
           java.lang.String unidadeOrcamentaria,
           br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsPessoaJuridica orgaoResponsavel,
           br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsPessoaFisica gestorAcao,
           br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsPessoaFisica monitorAcao,
           java.lang.String dataInicioAcao,
           java.lang.String dataTerminoAcao,
           int situacaoAcao,
           java.lang.Integer percExecucaoAcao,
           int pesoAcao,
           int prioridadeAcao,
           int tipoAcao,
           br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsCameraMonitoramento[] urlCameraMonitoramento,
           java.lang.String populacaoAlvoAcao,
           java.lang.String objetivoAcao,
           boolean ativo) {
           this.chvAcaoExterna = chvAcaoExterna;
           this.areaTematica = areaTematica;
           this.projeto = projeto;
           this.eixo = eixo;
           this.nomeAcao = nomeAcao;
           this.planoInterno = planoInterno;
           this.unidadeOrcamentaria = unidadeOrcamentaria;
           this.orgaoResponsavel = orgaoResponsavel;
           this.gestorAcao = gestorAcao;
           this.monitorAcao = monitorAcao;
           this.dataInicioAcao = dataInicioAcao;
           this.dataTerminoAcao = dataTerminoAcao;
           this.situacaoAcao = situacaoAcao;
           this.percExecucaoAcao = percExecucaoAcao;
           this.pesoAcao = pesoAcao;
           this.prioridadeAcao = prioridadeAcao;
           this.tipoAcao = tipoAcao;
           this.urlCameraMonitoramento = urlCameraMonitoramento;
           this.populacaoAlvoAcao = populacaoAlvoAcao;
           this.objetivoAcao = objetivoAcao;
           this.ativo = ativo;
    }
    
	public WsAcaoCadastro(Long chvExterno, String codProj, String descProj, String codEixo, String descEixo,
			String nomeAcao, String numeroCnpj, String nomeEmpresa,
			Date dataInicioAcao, Date dataTerminoAcao, Long codSituacao, String descricaoSituacao,
			String numeroCpfGerenteAcao, String nomeGerenteAcao, String indAtivo) {
		this.chvAcaoExterna = Integer.parseInt(chvExterno.toString());
		this.areaTematica = 4;
		this.projeto = new Integer(codProj);
		this.eixo = new Integer(codEixo);
		this.nomeAcao = nomeAcao;
		this.orgaoResponsavel = new WsPessoaJuridica(numeroCnpj, nomeEmpresa);
		if(dataInicioAcao != null) {
			this.dataInicioAcao = dataInicioAcao.toString();
		}
		if(dataTerminoAcao != null) {
			this.dataTerminoAcao = dataTerminoAcao.toString();
		}
		//this.situacaoAcao = codSituacao.intValue();
		this.situacaoAcao = 2;
		this.gestorAcao = new br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsPessoaFisica(numeroCpfGerenteAcao, nomeGerenteAcao);
		this.monitorAcao = new br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsPessoaFisica(numeroCpfGerenteAcao, nomeGerenteAcao);
		
		if(indAtivo.equals("S")) {
			ativo = true;
		}else {
			ativo = false;
		}
		this.descricaoEixo = descEixo;
		this.descricaoProjeto = descProj;
		this.pesoAcao = 1;
		this.prioridadeAcao = 1;
		this.tipoAcao = 1;
	}  
	
	public java.lang.String getDescricaoProjeto() {
		return descricaoProjeto;
	}

	public void setDescricaoProjeto(java.lang.String descricaoProjeto) {
		this.descricaoProjeto = descricaoProjeto;
	}

	public java.lang.String getDescricaoEixo() {
		return descricaoEixo;
	}

	public void setDescricaoEixo(java.lang.String descricaoEixo) {
		this.descricaoEixo = descricaoEixo;
	}

	public String toStringCSV() {
		StringBuffer s = new StringBuffer();
		//s.append("|");
		s.append(this.getChvAcaoExterna());
		s.append("|");
		s.append(this.getDescricaoProjeto());
		s.append(" - ");
		s.append(this.getDescricaoEixo());
		s.append("|");
		s.append(this.getNomeAcao());
		s.append("|");
		s.append(this.getGestorAcao().getNome());
		s.append("|");
		s.append(this.getDataInicioAcao());
		s.append("|");
		s.append(this.getDataTerminoAcao());
		s.append("|");
		s.append(ativo);
		
		return s.toString();
	}


    /**
     * Gets the chvAcaoExterna value for this WsAcaoCadastro.
     * 
     * @return chvAcaoExterna
     */
    public int getChvAcaoExterna() {
        return chvAcaoExterna;
    }


    /**
     * Sets the chvAcaoExterna value for this WsAcaoCadastro.
     * 
     * @param chvAcaoExterna
     */
    public void setChvAcaoExterna(int chvAcaoExterna) {
        this.chvAcaoExterna = chvAcaoExterna;
    }


    /**
     * Gets the areaTematica value for this WsAcaoCadastro.
     * 
     * @return areaTematica
     */
    public int getAreaTematica() {
        return areaTematica;
    }


    /**
     * Sets the areaTematica value for this WsAcaoCadastro.
     * 
     * @param areaTematica
     */
    public void setAreaTematica(int areaTematica) {
        this.areaTematica = areaTematica;
    }


    /**
     * Gets the projeto value for this WsAcaoCadastro.
     * 
     * @return projeto
     */
    public int getProjeto() {
        return projeto;
    }


    /**
     * Sets the projeto value for this WsAcaoCadastro.
     * 
     * @param projeto
     */
    public void setProjeto(int projeto) {
        this.projeto = projeto;
    }


    /**
     * Gets the eixo value for this WsAcaoCadastro.
     * 
     * @return eixo
     */
    public int getEixo() {
        return eixo;
    }


    /**
     * Sets the eixo value for this WsAcaoCadastro.
     * 
     * @param eixo
     */
    public void setEixo(int eixo) {
        this.eixo = eixo;
    }


    /**
     * Gets the nomeAcao value for this WsAcaoCadastro.
     * 
     * @return nomeAcao
     */
    public java.lang.String getNomeAcao() {
        return nomeAcao;
    }


    /**
     * Sets the nomeAcao value for this WsAcaoCadastro.
     * 
     * @param nomeAcao
     */
    public void setNomeAcao(java.lang.String nomeAcao) {
        this.nomeAcao = nomeAcao;
    }


    /**
     * Gets the planoInterno value for this WsAcaoCadastro.
     * 
     * @return planoInterno
     */
    public java.lang.String getPlanoInterno() {
        return planoInterno;
    }


    /**
     * Sets the planoInterno value for this WsAcaoCadastro.
     * 
     * @param planoInterno
     */
    public void setPlanoInterno(java.lang.String planoInterno) {
        this.planoInterno = planoInterno;
    }


    /**
     * Gets the unidadeOrcamentaria value for this WsAcaoCadastro.
     * 
     * @return unidadeOrcamentaria
     */
    public java.lang.String getUnidadeOrcamentaria() {
        return unidadeOrcamentaria;
    }


    /**
     * Sets the unidadeOrcamentaria value for this WsAcaoCadastro.
     * 
     * @param unidadeOrcamentaria
     */
    public void setUnidadeOrcamentaria(java.lang.String unidadeOrcamentaria) {
        this.unidadeOrcamentaria = unidadeOrcamentaria;
    }


    /**
     * Gets the orgaoResponsavel value for this WsAcaoCadastro.
     * 
     * @return orgaoResponsavel
     */
    public br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsPessoaJuridica getOrgaoResponsavel() {
        return orgaoResponsavel;
    }


    /**
     * Sets the orgaoResponsavel value for this WsAcaoCadastro.
     * 
     * @param orgaoResponsavel
     */
    public void setOrgaoResponsavel(br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsPessoaJuridica orgaoResponsavel) {
        this.orgaoResponsavel = orgaoResponsavel;
    }


    /**
     * Gets the gestorAcao value for this WsAcaoCadastro.
     * 
     * @return gestorAcao
     */
    public br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsPessoaFisica getGestorAcao() {
        return gestorAcao;
    }


    /**
     * Sets the gestorAcao value for this WsAcaoCadastro.
     * 
     * @param gestorAcao
     */
    public void setGestorAcao(br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsPessoaFisica gestorAcao) {
        this.gestorAcao = gestorAcao;
    }


    /**
     * Gets the monitorAcao value for this WsAcaoCadastro.
     * 
     * @return monitorAcao
     */
    public br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsPessoaFisica getMonitorAcao() {
        return monitorAcao;
    }


    /**
     * Sets the monitorAcao value for this WsAcaoCadastro.
     * 
     * @param monitorAcao
     */
    public void setMonitorAcao(br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsPessoaFisica monitorAcao) {
        this.monitorAcao = monitorAcao;
    }


    /**
     * Gets the dataInicioAcao value for this WsAcaoCadastro.
     * 
     * @return dataInicioAcao
     */
    public java.lang.String getDataInicioAcao() {
        return dataInicioAcao;
    }


    /**
     * Sets the dataInicioAcao value for this WsAcaoCadastro.
     * 
     * @param dataInicioAcao
     */
    public void setDataInicioAcao(java.lang.String dataInicioAcao) {
        this.dataInicioAcao = dataInicioAcao;
    }


    /**
     * Gets the dataTerminoAcao value for this WsAcaoCadastro.
     * 
     * @return dataTerminoAcao
     */
    public java.lang.String getDataTerminoAcao() {
        return dataTerminoAcao;
    }


    /**
     * Sets the dataTerminoAcao value for this WsAcaoCadastro.
     * 
     * @param dataTerminoAcao
     */
    public void setDataTerminoAcao(java.lang.String dataTerminoAcao) {
        this.dataTerminoAcao = dataTerminoAcao;
    }


    /**
     * Gets the situacaoAcao value for this WsAcaoCadastro.
     * 
     * @return situacaoAcao
     */
    public int getSituacaoAcao() {
        return situacaoAcao;
    }


    /**
     * Sets the situacaoAcao value for this WsAcaoCadastro.
     * 
     * @param situacaoAcao
     */
    public void setSituacaoAcao(int situacaoAcao) {
        this.situacaoAcao = situacaoAcao;
    }


    /**
     * Gets the percExecucaoAcao value for this WsAcaoCadastro.
     * 
     * @return percExecucaoAcao
     */
    public java.lang.Integer getPercExecucaoAcao() {
        return percExecucaoAcao;
    }


    /**
     * Sets the percExecucaoAcao value for this WsAcaoCadastro.
     * 
     * @param percExecucaoAcao
     */
    public void setPercExecucaoAcao(java.lang.Integer percExecucaoAcao) {
        this.percExecucaoAcao = percExecucaoAcao;
    }


    /**
     * Gets the pesoAcao value for this WsAcaoCadastro.
     * 
     * @return pesoAcao
     */
    public int getPesoAcao() {
        return pesoAcao;
    }


    /**
     * Sets the pesoAcao value for this WsAcaoCadastro.
     * 
     * @param pesoAcao
     */
    public void setPesoAcao(int pesoAcao) {
        this.pesoAcao = pesoAcao;
    }


    /**
     * Gets the prioridadeAcao value for this WsAcaoCadastro.
     * 
     * @return prioridadeAcao
     */
    public int getPrioridadeAcao() {
        return prioridadeAcao;
    }


    /**
     * Sets the prioridadeAcao value for this WsAcaoCadastro.
     * 
     * @param prioridadeAcao
     */
    public void setPrioridadeAcao(int prioridadeAcao) {
        this.prioridadeAcao = prioridadeAcao;
    }


    /**
     * Gets the tipoAcao value for this WsAcaoCadastro.
     * 
     * @return tipoAcao
     */
    public int getTipoAcao() {
        return tipoAcao;
    }


    /**
     * Sets the tipoAcao value for this WsAcaoCadastro.
     * 
     * @param tipoAcao
     */
    public void setTipoAcao(int tipoAcao) {
        this.tipoAcao = tipoAcao;
    }


    /**
     * Gets the urlCameraMonitoramento value for this WsAcaoCadastro.
     * 
     * @return urlCameraMonitoramento
     */
    public br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsCameraMonitoramento[] getUrlCameraMonitoramento() {
        return urlCameraMonitoramento;
    }


    /**
     * Sets the urlCameraMonitoramento value for this WsAcaoCadastro.
     * 
     * @param urlCameraMonitoramento
     */
    public void setUrlCameraMonitoramento(br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsCameraMonitoramento[] urlCameraMonitoramento) {
        this.urlCameraMonitoramento = urlCameraMonitoramento;
    }


    /**
     * Gets the populacaoAlvoAcao value for this WsAcaoCadastro.
     * 
     * @return populacaoAlvoAcao
     */
    public java.lang.String getPopulacaoAlvoAcao() {
        return populacaoAlvoAcao;
    }


    /**
     * Sets the populacaoAlvoAcao value for this WsAcaoCadastro.
     * 
     * @param populacaoAlvoAcao
     */
    public void setPopulacaoAlvoAcao(java.lang.String populacaoAlvoAcao) {
        this.populacaoAlvoAcao = populacaoAlvoAcao;
    }


    /**
     * Gets the objetivoAcao value for this WsAcaoCadastro.
     * 
     * @return objetivoAcao
     */
    public java.lang.String getObjetivoAcao() {
        return objetivoAcao;
    }


    /**
     * Sets the objetivoAcao value for this WsAcaoCadastro.
     * 
     * @param objetivoAcao
     */
    public void setObjetivoAcao(java.lang.String objetivoAcao) {
        this.objetivoAcao = objetivoAcao;
    }


    /**
     * Gets the ativo value for this WsAcaoCadastro.
     * 
     * @return ativo
     */
    public boolean isAtivo() {
        return ativo;
    }


    /**
     * Sets the ativo value for this WsAcaoCadastro.
     * 
     * @param ativo
     */
    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WsAcaoCadastro)) return false;
        WsAcaoCadastro other = (WsAcaoCadastro) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.chvAcaoExterna == other.getChvAcaoExterna() &&
            this.areaTematica == other.getAreaTematica() &&
            this.projeto == other.getProjeto() &&
            this.eixo == other.getEixo() &&
            ((this.nomeAcao==null && other.getNomeAcao()==null) || 
             (this.nomeAcao!=null &&
              this.nomeAcao.equals(other.getNomeAcao()))) &&
            ((this.planoInterno==null && other.getPlanoInterno()==null) || 
             (this.planoInterno!=null &&
              this.planoInterno.equals(other.getPlanoInterno()))) &&
            ((this.unidadeOrcamentaria==null && other.getUnidadeOrcamentaria()==null) || 
             (this.unidadeOrcamentaria!=null &&
              this.unidadeOrcamentaria.equals(other.getUnidadeOrcamentaria()))) &&
            ((this.orgaoResponsavel==null && other.getOrgaoResponsavel()==null) || 
             (this.orgaoResponsavel!=null &&
              this.orgaoResponsavel.equals(other.getOrgaoResponsavel()))) &&
            ((this.gestorAcao==null && other.getGestorAcao()==null) || 
             (this.gestorAcao!=null &&
              this.gestorAcao.equals(other.getGestorAcao()))) &&
            ((this.monitorAcao==null && other.getMonitorAcao()==null) || 
             (this.monitorAcao!=null &&
              this.monitorAcao.equals(other.getMonitorAcao()))) &&
            ((this.dataInicioAcao==null && other.getDataInicioAcao()==null) || 
             (this.dataInicioAcao!=null &&
              this.dataInicioAcao.equals(other.getDataInicioAcao()))) &&
            ((this.dataTerminoAcao==null && other.getDataTerminoAcao()==null) || 
             (this.dataTerminoAcao!=null &&
              this.dataTerminoAcao.equals(other.getDataTerminoAcao()))) &&
            this.situacaoAcao == other.getSituacaoAcao() &&
            ((this.percExecucaoAcao==null && other.getPercExecucaoAcao()==null) || 
             (this.percExecucaoAcao!=null &&
              this.percExecucaoAcao.equals(other.getPercExecucaoAcao()))) &&
            this.pesoAcao == other.getPesoAcao() &&
            this.prioridadeAcao == other.getPrioridadeAcao() &&
            this.tipoAcao == other.getTipoAcao() &&
            ((this.urlCameraMonitoramento==null && other.getUrlCameraMonitoramento()==null) || 
             (this.urlCameraMonitoramento!=null &&
              java.util.Arrays.equals(this.urlCameraMonitoramento, other.getUrlCameraMonitoramento()))) &&
            ((this.populacaoAlvoAcao==null && other.getPopulacaoAlvoAcao()==null) || 
             (this.populacaoAlvoAcao!=null &&
              this.populacaoAlvoAcao.equals(other.getPopulacaoAlvoAcao()))) &&
            ((this.objetivoAcao==null && other.getObjetivoAcao()==null) || 
             (this.objetivoAcao!=null &&
              this.objetivoAcao.equals(other.getObjetivoAcao()))) &&
            this.ativo == other.isAtivo();
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
        _hashCode += getChvAcaoExterna();
        _hashCode += getAreaTematica();
        _hashCode += getProjeto();
        _hashCode += getEixo();
        if (getNomeAcao() != null) {
            _hashCode += getNomeAcao().hashCode();
        }
        if (getPlanoInterno() != null) {
            _hashCode += getPlanoInterno().hashCode();
        }
        if (getUnidadeOrcamentaria() != null) {
            _hashCode += getUnidadeOrcamentaria().hashCode();
        }
        if (getOrgaoResponsavel() != null) {
            _hashCode += getOrgaoResponsavel().hashCode();
        }
        if (getGestorAcao() != null) {
            _hashCode += getGestorAcao().hashCode();
        }
        if (getMonitorAcao() != null) {
            _hashCode += getMonitorAcao().hashCode();
        }
        if (getDataInicioAcao() != null) {
            _hashCode += getDataInicioAcao().hashCode();
        }
        if (getDataTerminoAcao() != null) {
            _hashCode += getDataTerminoAcao().hashCode();
        }
        _hashCode += getSituacaoAcao();
        if (getPercExecucaoAcao() != null) {
            _hashCode += getPercExecucaoAcao().hashCode();
        }
        _hashCode += getPesoAcao();
        _hashCode += getPrioridadeAcao();
        _hashCode += getTipoAcao();
        if (getUrlCameraMonitoramento() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getUrlCameraMonitoramento());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getUrlCameraMonitoramento(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getPopulacaoAlvoAcao() != null) {
            _hashCode += getPopulacaoAlvoAcao().hashCode();
        }
        if (getObjetivoAcao() != null) {
            _hashCode += getObjetivoAcao().hashCode();
        }
        _hashCode += (isAtivo() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WsAcaoCadastro.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("https://ondaswsdev.presidencia.gov.br/ws/server/PlanoAcaoService.php", "WsAcaoCadastro"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("chvAcaoExterna");
        elemField.setXmlName(new javax.xml.namespace.QName("", "chvAcaoExterna"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("areaTematica");
        elemField.setXmlName(new javax.xml.namespace.QName("", "areaTematica"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("projeto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "projeto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("eixo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "eixo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nomeAcao");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nomeAcao"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("planoInterno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "planoInterno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("unidadeOrcamentaria");
        elemField.setXmlName(new javax.xml.namespace.QName("", "unidadeOrcamentaria"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("orgaoResponsavel");
        elemField.setXmlName(new javax.xml.namespace.QName("", "orgaoResponsavel"));
        elemField.setXmlType(new javax.xml.namespace.QName("https://ondaswsdev.presidencia.gov.br/ws/server/PlanoAcaoService.php", "WsPessoaJuridica"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("gestorAcao");
        elemField.setXmlName(new javax.xml.namespace.QName("", "gestorAcao"));
        elemField.setXmlType(new javax.xml.namespace.QName("https://ondaswsdev.presidencia.gov.br/ws/server/PlanoAcaoService.php", "WsPessoaFisica"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("monitorAcao");
        elemField.setXmlName(new javax.xml.namespace.QName("", "monitorAcao"));
        elemField.setXmlType(new javax.xml.namespace.QName("https://ondaswsdev.presidencia.gov.br/ws/server/PlanoAcaoService.php", "WsPessoaFisica"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataInicioAcao");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataInicioAcao"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataTerminoAcao");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataTerminoAcao"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("situacaoAcao");
        elemField.setXmlName(new javax.xml.namespace.QName("", "situacaoAcao"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("percExecucaoAcao");
        elemField.setXmlName(new javax.xml.namespace.QName("", "percExecucaoAcao"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pesoAcao");
        elemField.setXmlName(new javax.xml.namespace.QName("", "pesoAcao"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("prioridadeAcao");
        elemField.setXmlName(new javax.xml.namespace.QName("", "prioridadeAcao"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoAcao");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoAcao"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("urlCameraMonitoramento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "urlCameraMonitoramento"));
        elemField.setXmlType(new javax.xml.namespace.QName("https://ondaswsdev.presidencia.gov.br/ws/server/PlanoAcaoService.php", "WsCameraMonitoramento"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("", "item"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("populacaoAlvoAcao");
        elemField.setXmlName(new javax.xml.namespace.QName("", "populacaoAlvoAcao"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("objetivoAcao");
        elemField.setXmlName(new javax.xml.namespace.QName("", "objetivoAcao"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ativo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ativo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
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
