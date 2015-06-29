/**
 * WsIndicadorCadastro.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php;

public class WsIndicadorCadastro  implements java.io.Serializable {
    private int areaTematica;

    private java.lang.Integer projeto;

    private java.lang.Integer eixo;

    private java.lang.String chvAcaoExterna;

    private java.lang.String chvAtividadeExterna;

    private int chvIndicadorExterno;

    private java.lang.String nomeIndicador;

    private java.lang.String siglaIndicador;

    private int categoriaIndicador;

    private java.lang.String dataCriacao;

    private java.lang.String objetivoIndicador;

    private java.lang.String definicaoConceitoIndicador;

    private java.lang.String unidadeMedida;

    private java.lang.String fonteIndicador;

    private java.lang.String formaColetaIndicador;

    private int periodicidadeIndicador;

    private int periodicidadeParcialIndicador;

    private br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsPessoaJuridica orgaoResponsavel;

    private br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsPessoaFisica responsavelOrgao;

    private int tipoIndicador;

    private java.lang.String defasagemIndicador;

    private java.lang.String limitacoesRestricoesIndicador;

    private int polaridadeIndicador;

    private int abrangenciaGeografica;

    private int desagregacaoIndicador;

    private java.lang.String dadosFaltantes;

    private java.lang.Integer formaDisseminacao;

    private java.lang.String observacao;

    private int indicadorAcumulavel;

    private int possuiMetas;

    private java.lang.String formulaIndicador;

    public WsIndicadorCadastro() {
    }

    public WsIndicadorCadastro(
           int areaTematica,
           java.lang.Integer projeto,
           java.lang.Integer eixo,
           java.lang.String chvAcaoExterna,
           java.lang.String chvAtividadeExterna,
           int chvIndicadorExterno,
           java.lang.String nomeIndicador,
           java.lang.String siglaIndicador,
           int categoriaIndicador,
           java.lang.String dataCriacao,
           java.lang.String objetivoIndicador,
           java.lang.String definicaoConceitoIndicador,
           java.lang.String unidadeMedida,
           java.lang.String fonteIndicador,
           java.lang.String formaColetaIndicador,
           int periodicidadeIndicador,
           int periodicidadeParcialIndicador,
           br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsPessoaJuridica orgaoResponsavel,
           br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsPessoaFisica responsavelOrgao,
           int tipoIndicador,
           java.lang.String defasagemIndicador,
           java.lang.String limitacoesRestricoesIndicador,
           int polaridadeIndicador,
           int abrangenciaGeografica,
           int desagregacaoIndicador,
           java.lang.String dadosFaltantes,
           java.lang.Integer formaDisseminacao,
           java.lang.String observacao,
           int indicadorAcumulavel,
           int possuiMetas,
           java.lang.String formulaIndicador) {
           this.areaTematica = areaTematica;
           this.projeto = projeto;
           this.eixo = eixo;
           this.chvAcaoExterna = chvAcaoExterna;
           this.chvAtividadeExterna = chvAtividadeExterna;
           this.chvIndicadorExterno = chvIndicadorExterno;
           this.nomeIndicador = nomeIndicador;
           this.siglaIndicador = siglaIndicador;
           this.categoriaIndicador = categoriaIndicador;
           this.dataCriacao = dataCriacao;
           this.objetivoIndicador = objetivoIndicador;
           this.definicaoConceitoIndicador = definicaoConceitoIndicador;
           this.unidadeMedida = unidadeMedida;
           this.fonteIndicador = fonteIndicador;
           this.formaColetaIndicador = formaColetaIndicador;
           this.periodicidadeIndicador = periodicidadeIndicador;
           this.periodicidadeParcialIndicador = periodicidadeParcialIndicador;
           this.orgaoResponsavel = orgaoResponsavel;
           this.responsavelOrgao = responsavelOrgao;
           this.tipoIndicador = tipoIndicador;
           this.defasagemIndicador = defasagemIndicador;
           this.limitacoesRestricoesIndicador = limitacoesRestricoesIndicador;
           this.polaridadeIndicador = polaridadeIndicador;
           this.abrangenciaGeografica = abrangenciaGeografica;
           this.desagregacaoIndicador = desagregacaoIndicador;
           this.dadosFaltantes = dadosFaltantes;
           this.formaDisseminacao = formaDisseminacao;
           this.observacao = observacao;
           this.indicadorAcumulavel = indicadorAcumulavel;
           this.possuiMetas = possuiMetas;
           this.formulaIndicador = formulaIndicador;
    }


    /**
     * Gets the areaTematica value for this WsIndicadorCadastro.
     * 
     * @return areaTematica
     */
    public int getAreaTematica() {
        return areaTematica;
    }


    /**
     * Sets the areaTematica value for this WsIndicadorCadastro.
     * 
     * @param areaTematica
     */
    public void setAreaTematica(int areaTematica) {
        this.areaTematica = areaTematica;
    }


    /**
     * Gets the projeto value for this WsIndicadorCadastro.
     * 
     * @return projeto
     */
    public java.lang.Integer getProjeto() {
        return projeto;
    }


    /**
     * Sets the projeto value for this WsIndicadorCadastro.
     * 
     * @param projeto
     */
    public void setProjeto(java.lang.Integer projeto) {
        this.projeto = projeto;
    }


    /**
     * Gets the eixo value for this WsIndicadorCadastro.
     * 
     * @return eixo
     */
    public java.lang.Integer getEixo() {
        return eixo;
    }


    /**
     * Sets the eixo value for this WsIndicadorCadastro.
     * 
     * @param eixo
     */
    public void setEixo(java.lang.Integer eixo) {
        this.eixo = eixo;
    }


    /**
     * Gets the chvAcaoExterna value for this WsIndicadorCadastro.
     * 
     * @return chvAcaoExterna
     */
    public java.lang.String getChvAcaoExterna() {
        return chvAcaoExterna;
    }


    /**
     * Sets the chvAcaoExterna value for this WsIndicadorCadastro.
     * 
     * @param chvAcaoExterna
     */
    public void setChvAcaoExterna(java.lang.String chvAcaoExterna) {
        this.chvAcaoExterna = chvAcaoExterna;
    }


    /**
     * Gets the chvAtividadeExterna value for this WsIndicadorCadastro.
     * 
     * @return chvAtividadeExterna
     */
    public java.lang.String getChvAtividadeExterna() {
        return chvAtividadeExterna;
    }


    /**
     * Sets the chvAtividadeExterna value for this WsIndicadorCadastro.
     * 
     * @param chvAtividadeExterna
     */
    public void setChvAtividadeExterna(java.lang.String chvAtividadeExterna) {
        this.chvAtividadeExterna = chvAtividadeExterna;
    }


    /**
     * Gets the chvIndicadorExterno value for this WsIndicadorCadastro.
     * 
     * @return chvIndicadorExterno
     */
    public int getChvIndicadorExterno() {
        return chvIndicadorExterno;
    }


    /**
     * Sets the chvIndicadorExterno value for this WsIndicadorCadastro.
     * 
     * @param chvIndicadorExterno
     */
    public void setChvIndicadorExterno(int chvIndicadorExterno) {
        this.chvIndicadorExterno = chvIndicadorExterno;
    }


    /**
     * Gets the nomeIndicador value for this WsIndicadorCadastro.
     * 
     * @return nomeIndicador
     */
    public java.lang.String getNomeIndicador() {
        return nomeIndicador;
    }


    /**
     * Sets the nomeIndicador value for this WsIndicadorCadastro.
     * 
     * @param nomeIndicador
     */
    public void setNomeIndicador(java.lang.String nomeIndicador) {
        this.nomeIndicador = nomeIndicador;
    }


    /**
     * Gets the siglaIndicador value for this WsIndicadorCadastro.
     * 
     * @return siglaIndicador
     */
    public java.lang.String getSiglaIndicador() {
        return siglaIndicador;
    }


    /**
     * Sets the siglaIndicador value for this WsIndicadorCadastro.
     * 
     * @param siglaIndicador
     */
    public void setSiglaIndicador(java.lang.String siglaIndicador) {
        this.siglaIndicador = siglaIndicador;
    }


    /**
     * Gets the categoriaIndicador value for this WsIndicadorCadastro.
     * 
     * @return categoriaIndicador
     */
    public int getCategoriaIndicador() {
        return categoriaIndicador;
    }


    /**
     * Sets the categoriaIndicador value for this WsIndicadorCadastro.
     * 
     * @param categoriaIndicador
     */
    public void setCategoriaIndicador(int categoriaIndicador) {
        this.categoriaIndicador = categoriaIndicador;
    }


    /**
     * Gets the dataCriacao value for this WsIndicadorCadastro.
     * 
     * @return dataCriacao
     */
    public java.lang.String getDataCriacao() {
        return dataCriacao;
    }


    /**
     * Sets the dataCriacao value for this WsIndicadorCadastro.
     * 
     * @param dataCriacao
     */
    public void setDataCriacao(java.lang.String dataCriacao) {
        this.dataCriacao = dataCriacao;
    }


    /**
     * Gets the objetivoIndicador value for this WsIndicadorCadastro.
     * 
     * @return objetivoIndicador
     */
    public java.lang.String getObjetivoIndicador() {
        return objetivoIndicador;
    }


    /**
     * Sets the objetivoIndicador value for this WsIndicadorCadastro.
     * 
     * @param objetivoIndicador
     */
    public void setObjetivoIndicador(java.lang.String objetivoIndicador) {
        this.objetivoIndicador = objetivoIndicador;
    }


    /**
     * Gets the definicaoConceitoIndicador value for this WsIndicadorCadastro.
     * 
     * @return definicaoConceitoIndicador
     */
    public java.lang.String getDefinicaoConceitoIndicador() {
        return definicaoConceitoIndicador;
    }


    /**
     * Sets the definicaoConceitoIndicador value for this WsIndicadorCadastro.
     * 
     * @param definicaoConceitoIndicador
     */
    public void setDefinicaoConceitoIndicador(java.lang.String definicaoConceitoIndicador) {
        this.definicaoConceitoIndicador = definicaoConceitoIndicador;
    }


    /**
     * Gets the unidadeMedida value for this WsIndicadorCadastro.
     * 
     * @return unidadeMedida
     */
    public java.lang.String getUnidadeMedida() {
        return unidadeMedida;
    }


    /**
     * Sets the unidadeMedida value for this WsIndicadorCadastro.
     * 
     * @param unidadeMedida
     */
    public void setUnidadeMedida(java.lang.String unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }


    /**
     * Gets the fonteIndicador value for this WsIndicadorCadastro.
     * 
     * @return fonteIndicador
     */
    public java.lang.String getFonteIndicador() {
        return fonteIndicador;
    }


    /**
     * Sets the fonteIndicador value for this WsIndicadorCadastro.
     * 
     * @param fonteIndicador
     */
    public void setFonteIndicador(java.lang.String fonteIndicador) {
        this.fonteIndicador = fonteIndicador;
    }


    /**
     * Gets the formaColetaIndicador value for this WsIndicadorCadastro.
     * 
     * @return formaColetaIndicador
     */
    public java.lang.String getFormaColetaIndicador() {
        return formaColetaIndicador;
    }


    /**
     * Sets the formaColetaIndicador value for this WsIndicadorCadastro.
     * 
     * @param formaColetaIndicador
     */
    public void setFormaColetaIndicador(java.lang.String formaColetaIndicador) {
        this.formaColetaIndicador = formaColetaIndicador;
    }


    /**
     * Gets the periodicidadeIndicador value for this WsIndicadorCadastro.
     * 
     * @return periodicidadeIndicador
     */
    public int getPeriodicidadeIndicador() {
        return periodicidadeIndicador;
    }


    /**
     * Sets the periodicidadeIndicador value for this WsIndicadorCadastro.
     * 
     * @param periodicidadeIndicador
     */
    public void setPeriodicidadeIndicador(int periodicidadeIndicador) {
        this.periodicidadeIndicador = periodicidadeIndicador;
    }


    /**
     * Gets the periodicidadeParcialIndicador value for this WsIndicadorCadastro.
     * 
     * @return periodicidadeParcialIndicador
     */
    public int getPeriodicidadeParcialIndicador() {
        return periodicidadeParcialIndicador;
    }


    /**
     * Sets the periodicidadeParcialIndicador value for this WsIndicadorCadastro.
     * 
     * @param periodicidadeParcialIndicador
     */
    public void setPeriodicidadeParcialIndicador(int periodicidadeParcialIndicador) {
        this.periodicidadeParcialIndicador = periodicidadeParcialIndicador;
    }


    /**
     * Gets the orgaoResponsavel value for this WsIndicadorCadastro.
     * 
     * @return orgaoResponsavel
     */
    public br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsPessoaJuridica getOrgaoResponsavel() {
        return orgaoResponsavel;
    }


    /**
     * Sets the orgaoResponsavel value for this WsIndicadorCadastro.
     * 
     * @param orgaoResponsavel
     */
    public void setOrgaoResponsavel(br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsPessoaJuridica orgaoResponsavel) {
        this.orgaoResponsavel = orgaoResponsavel;
    }


    /**
     * Gets the responsavelOrgao value for this WsIndicadorCadastro.
     * 
     * @return responsavelOrgao
     */
    public br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsPessoaFisica getResponsavelOrgao() {
        return responsavelOrgao;
    }


    /**
     * Sets the responsavelOrgao value for this WsIndicadorCadastro.
     * 
     * @param responsavelOrgao
     */
    public void setResponsavelOrgao(br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsPessoaFisica responsavelOrgao) {
        this.responsavelOrgao = responsavelOrgao;
    }


    /**
     * Gets the tipoIndicador value for this WsIndicadorCadastro.
     * 
     * @return tipoIndicador
     */
    public int getTipoIndicador() {
        return tipoIndicador;
    }


    /**
     * Sets the tipoIndicador value for this WsIndicadorCadastro.
     * 
     * @param tipoIndicador
     */
    public void setTipoIndicador(int tipoIndicador) {
        this.tipoIndicador = tipoIndicador;
    }


    /**
     * Gets the defasagemIndicador value for this WsIndicadorCadastro.
     * 
     * @return defasagemIndicador
     */
    public java.lang.String getDefasagemIndicador() {
        return defasagemIndicador;
    }


    /**
     * Sets the defasagemIndicador value for this WsIndicadorCadastro.
     * 
     * @param defasagemIndicador
     */
    public void setDefasagemIndicador(java.lang.String defasagemIndicador) {
        this.defasagemIndicador = defasagemIndicador;
    }


    /**
     * Gets the limitacoesRestricoesIndicador value for this WsIndicadorCadastro.
     * 
     * @return limitacoesRestricoesIndicador
     */
    public java.lang.String getLimitacoesRestricoesIndicador() {
        return limitacoesRestricoesIndicador;
    }


    /**
     * Sets the limitacoesRestricoesIndicador value for this WsIndicadorCadastro.
     * 
     * @param limitacoesRestricoesIndicador
     */
    public void setLimitacoesRestricoesIndicador(java.lang.String limitacoesRestricoesIndicador) {
        this.limitacoesRestricoesIndicador = limitacoesRestricoesIndicador;
    }


    /**
     * Gets the polaridadeIndicador value for this WsIndicadorCadastro.
     * 
     * @return polaridadeIndicador
     */
    public int getPolaridadeIndicador() {
        return polaridadeIndicador;
    }


    /**
     * Sets the polaridadeIndicador value for this WsIndicadorCadastro.
     * 
     * @param polaridadeIndicador
     */
    public void setPolaridadeIndicador(int polaridadeIndicador) {
        this.polaridadeIndicador = polaridadeIndicador;
    }


    /**
     * Gets the abrangenciaGeografica value for this WsIndicadorCadastro.
     * 
     * @return abrangenciaGeografica
     */
    public int getAbrangenciaGeografica() {
        return abrangenciaGeografica;
    }


    /**
     * Sets the abrangenciaGeografica value for this WsIndicadorCadastro.
     * 
     * @param abrangenciaGeografica
     */
    public void setAbrangenciaGeografica(int abrangenciaGeografica) {
        this.abrangenciaGeografica = abrangenciaGeografica;
    }


    /**
     * Gets the desagregacaoIndicador value for this WsIndicadorCadastro.
     * 
     * @return desagregacaoIndicador
     */
    public int getDesagregacaoIndicador() {
        return desagregacaoIndicador;
    }


    /**
     * Sets the desagregacaoIndicador value for this WsIndicadorCadastro.
     * 
     * @param desagregacaoIndicador
     */
    public void setDesagregacaoIndicador(int desagregacaoIndicador) {
        this.desagregacaoIndicador = desagregacaoIndicador;
    }


    /**
     * Gets the dadosFaltantes value for this WsIndicadorCadastro.
     * 
     * @return dadosFaltantes
     */
    public java.lang.String getDadosFaltantes() {
        return dadosFaltantes;
    }


    /**
     * Sets the dadosFaltantes value for this WsIndicadorCadastro.
     * 
     * @param dadosFaltantes
     */
    public void setDadosFaltantes(java.lang.String dadosFaltantes) {
        this.dadosFaltantes = dadosFaltantes;
    }


    /**
     * Gets the formaDisseminacao value for this WsIndicadorCadastro.
     * 
     * @return formaDisseminacao
     */
    public java.lang.Integer getFormaDisseminacao() {
        return formaDisseminacao;
    }


    /**
     * Sets the formaDisseminacao value for this WsIndicadorCadastro.
     * 
     * @param formaDisseminacao
     */
    public void setFormaDisseminacao(java.lang.Integer formaDisseminacao) {
        this.formaDisseminacao = formaDisseminacao;
    }


    /**
     * Gets the observacao value for this WsIndicadorCadastro.
     * 
     * @return observacao
     */
    public java.lang.String getObservacao() {
        return observacao;
    }


    /**
     * Sets the observacao value for this WsIndicadorCadastro.
     * 
     * @param observacao
     */
    public void setObservacao(java.lang.String observacao) {
        this.observacao = observacao;
    }


    /**
     * Gets the indicadorAcumulavel value for this WsIndicadorCadastro.
     * 
     * @return indicadorAcumulavel
     */
    public int getIndicadorAcumulavel() {
        return indicadorAcumulavel;
    }


    /**
     * Sets the indicadorAcumulavel value for this WsIndicadorCadastro.
     * 
     * @param indicadorAcumulavel
     */
    public void setIndicadorAcumulavel(int indicadorAcumulavel) {
        this.indicadorAcumulavel = indicadorAcumulavel;
    }


    /**
     * Gets the possuiMetas value for this WsIndicadorCadastro.
     * 
     * @return possuiMetas
     */
    public int getPossuiMetas() {
        return possuiMetas;
    }


    /**
     * Sets the possuiMetas value for this WsIndicadorCadastro.
     * 
     * @param possuiMetas
     */
    public void setPossuiMetas(int possuiMetas) {
        this.possuiMetas = possuiMetas;
    }


    /**
     * Gets the formulaIndicador value for this WsIndicadorCadastro.
     * 
     * @return formulaIndicador
     */
    public java.lang.String getFormulaIndicador() {
        return formulaIndicador;
    }


    /**
     * Sets the formulaIndicador value for this WsIndicadorCadastro.
     * 
     * @param formulaIndicador
     */
    public void setFormulaIndicador(java.lang.String formulaIndicador) {
        this.formulaIndicador = formulaIndicador;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WsIndicadorCadastro)) return false;
        WsIndicadorCadastro other = (WsIndicadorCadastro) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.areaTematica == other.getAreaTematica() &&
            ((this.projeto==null && other.getProjeto()==null) || 
             (this.projeto!=null &&
              this.projeto.equals(other.getProjeto()))) &&
            ((this.eixo==null && other.getEixo()==null) || 
             (this.eixo!=null &&
              this.eixo.equals(other.getEixo()))) &&
            ((this.chvAcaoExterna==null && other.getChvAcaoExterna()==null) || 
             (this.chvAcaoExterna!=null &&
              this.chvAcaoExterna.equals(other.getChvAcaoExterna()))) &&
            ((this.chvAtividadeExterna==null && other.getChvAtividadeExterna()==null) || 
             (this.chvAtividadeExterna!=null &&
              this.chvAtividadeExterna.equals(other.getChvAtividadeExterna()))) &&
            this.chvIndicadorExterno == other.getChvIndicadorExterno() &&
            ((this.nomeIndicador==null && other.getNomeIndicador()==null) || 
             (this.nomeIndicador!=null &&
              this.nomeIndicador.equals(other.getNomeIndicador()))) &&
            ((this.siglaIndicador==null && other.getSiglaIndicador()==null) || 
             (this.siglaIndicador!=null &&
              this.siglaIndicador.equals(other.getSiglaIndicador()))) &&
            this.categoriaIndicador == other.getCategoriaIndicador() &&
            ((this.dataCriacao==null && other.getDataCriacao()==null) || 
             (this.dataCriacao!=null &&
              this.dataCriacao.equals(other.getDataCriacao()))) &&
            ((this.objetivoIndicador==null && other.getObjetivoIndicador()==null) || 
             (this.objetivoIndicador!=null &&
              this.objetivoIndicador.equals(other.getObjetivoIndicador()))) &&
            ((this.definicaoConceitoIndicador==null && other.getDefinicaoConceitoIndicador()==null) || 
             (this.definicaoConceitoIndicador!=null &&
              this.definicaoConceitoIndicador.equals(other.getDefinicaoConceitoIndicador()))) &&
            ((this.unidadeMedida==null && other.getUnidadeMedida()==null) || 
             (this.unidadeMedida!=null &&
              this.unidadeMedida.equals(other.getUnidadeMedida()))) &&
            ((this.fonteIndicador==null && other.getFonteIndicador()==null) || 
             (this.fonteIndicador!=null &&
              this.fonteIndicador.equals(other.getFonteIndicador()))) &&
            ((this.formaColetaIndicador==null && other.getFormaColetaIndicador()==null) || 
             (this.formaColetaIndicador!=null &&
              this.formaColetaIndicador.equals(other.getFormaColetaIndicador()))) &&
            this.periodicidadeIndicador == other.getPeriodicidadeIndicador() &&
            this.periodicidadeParcialIndicador == other.getPeriodicidadeParcialIndicador() &&
            ((this.orgaoResponsavel==null && other.getOrgaoResponsavel()==null) || 
             (this.orgaoResponsavel!=null &&
              this.orgaoResponsavel.equals(other.getOrgaoResponsavel()))) &&
            ((this.responsavelOrgao==null && other.getResponsavelOrgao()==null) || 
             (this.responsavelOrgao!=null &&
              this.responsavelOrgao.equals(other.getResponsavelOrgao()))) &&
            this.tipoIndicador == other.getTipoIndicador() &&
            ((this.defasagemIndicador==null && other.getDefasagemIndicador()==null) || 
             (this.defasagemIndicador!=null &&
              this.defasagemIndicador.equals(other.getDefasagemIndicador()))) &&
            ((this.limitacoesRestricoesIndicador==null && other.getLimitacoesRestricoesIndicador()==null) || 
             (this.limitacoesRestricoesIndicador!=null &&
              this.limitacoesRestricoesIndicador.equals(other.getLimitacoesRestricoesIndicador()))) &&
            this.polaridadeIndicador == other.getPolaridadeIndicador() &&
            this.abrangenciaGeografica == other.getAbrangenciaGeografica() &&
            this.desagregacaoIndicador == other.getDesagregacaoIndicador() &&
            ((this.dadosFaltantes==null && other.getDadosFaltantes()==null) || 
             (this.dadosFaltantes!=null &&
              this.dadosFaltantes.equals(other.getDadosFaltantes()))) &&
            ((this.formaDisseminacao==null && other.getFormaDisseminacao()==null) || 
             (this.formaDisseminacao!=null &&
              this.formaDisseminacao.equals(other.getFormaDisseminacao()))) &&
            ((this.observacao==null && other.getObservacao()==null) || 
             (this.observacao!=null &&
              this.observacao.equals(other.getObservacao()))) &&
            this.indicadorAcumulavel == other.getIndicadorAcumulavel() &&
            this.possuiMetas == other.getPossuiMetas() &&
            ((this.formulaIndicador==null && other.getFormulaIndicador()==null) || 
             (this.formulaIndicador!=null &&
              this.formulaIndicador.equals(other.getFormulaIndicador())));
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
        _hashCode += getAreaTematica();
        if (getProjeto() != null) {
            _hashCode += getProjeto().hashCode();
        }
        if (getEixo() != null) {
            _hashCode += getEixo().hashCode();
        }
        if (getChvAcaoExterna() != null) {
            _hashCode += getChvAcaoExterna().hashCode();
        }
        if (getChvAtividadeExterna() != null) {
            _hashCode += getChvAtividadeExterna().hashCode();
        }
        _hashCode += getChvIndicadorExterno();
        if (getNomeIndicador() != null) {
            _hashCode += getNomeIndicador().hashCode();
        }
        if (getSiglaIndicador() != null) {
            _hashCode += getSiglaIndicador().hashCode();
        }
        _hashCode += getCategoriaIndicador();
        if (getDataCriacao() != null) {
            _hashCode += getDataCriacao().hashCode();
        }
        if (getObjetivoIndicador() != null) {
            _hashCode += getObjetivoIndicador().hashCode();
        }
        if (getDefinicaoConceitoIndicador() != null) {
            _hashCode += getDefinicaoConceitoIndicador().hashCode();
        }
        if (getUnidadeMedida() != null) {
            _hashCode += getUnidadeMedida().hashCode();
        }
        if (getFonteIndicador() != null) {
            _hashCode += getFonteIndicador().hashCode();
        }
        if (getFormaColetaIndicador() != null) {
            _hashCode += getFormaColetaIndicador().hashCode();
        }
        _hashCode += getPeriodicidadeIndicador();
        _hashCode += getPeriodicidadeParcialIndicador();
        if (getOrgaoResponsavel() != null) {
            _hashCode += getOrgaoResponsavel().hashCode();
        }
        if (getResponsavelOrgao() != null) {
            _hashCode += getResponsavelOrgao().hashCode();
        }
        _hashCode += getTipoIndicador();
        if (getDefasagemIndicador() != null) {
            _hashCode += getDefasagemIndicador().hashCode();
        }
        if (getLimitacoesRestricoesIndicador() != null) {
            _hashCode += getLimitacoesRestricoesIndicador().hashCode();
        }
        _hashCode += getPolaridadeIndicador();
        _hashCode += getAbrangenciaGeografica();
        _hashCode += getDesagregacaoIndicador();
        if (getDadosFaltantes() != null) {
            _hashCode += getDadosFaltantes().hashCode();
        }
        if (getFormaDisseminacao() != null) {
            _hashCode += getFormaDisseminacao().hashCode();
        }
        if (getObservacao() != null) {
            _hashCode += getObservacao().hashCode();
        }
        _hashCode += getIndicadorAcumulavel();
        _hashCode += getPossuiMetas();
        if (getFormulaIndicador() != null) {
            _hashCode += getFormulaIndicador().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WsIndicadorCadastro.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("https://ondaswsdev.presidencia.gov.br/ws/server/PlanoAcaoService.php", "WsIndicadorCadastro"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("areaTematica");
        elemField.setXmlName(new javax.xml.namespace.QName("", "areaTematica"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("projeto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "projeto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("eixo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "eixo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("chvAcaoExterna");
        elemField.setXmlName(new javax.xml.namespace.QName("", "chvAcaoExterna"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("chvAtividadeExterna");
        elemField.setXmlName(new javax.xml.namespace.QName("", "chvAtividadeExterna"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("chvIndicadorExterno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "chvIndicadorExterno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nomeIndicador");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nomeIndicador"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("siglaIndicador");
        elemField.setXmlName(new javax.xml.namespace.QName("", "siglaIndicador"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("categoriaIndicador");
        elemField.setXmlName(new javax.xml.namespace.QName("", "categoriaIndicador"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataCriacao");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataCriacao"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("objetivoIndicador");
        elemField.setXmlName(new javax.xml.namespace.QName("", "objetivoIndicador"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("definicaoConceitoIndicador");
        elemField.setXmlName(new javax.xml.namespace.QName("", "definicaoConceitoIndicador"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("unidadeMedida");
        elemField.setXmlName(new javax.xml.namespace.QName("", "unidadeMedida"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fonteIndicador");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fonteIndicador"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("formaColetaIndicador");
        elemField.setXmlName(new javax.xml.namespace.QName("", "formaColetaIndicador"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("periodicidadeIndicador");
        elemField.setXmlName(new javax.xml.namespace.QName("", "periodicidadeIndicador"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("periodicidadeParcialIndicador");
        elemField.setXmlName(new javax.xml.namespace.QName("", "periodicidadeParcialIndicador"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("orgaoResponsavel");
        elemField.setXmlName(new javax.xml.namespace.QName("", "orgaoResponsavel"));
        elemField.setXmlType(new javax.xml.namespace.QName("https://ondaswsdev.presidencia.gov.br/ws/server/PlanoAcaoService.php", "WsPessoaJuridica"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("responsavelOrgao");
        elemField.setXmlName(new javax.xml.namespace.QName("", "responsavelOrgao"));
        elemField.setXmlType(new javax.xml.namespace.QName("https://ondaswsdev.presidencia.gov.br/ws/server/PlanoAcaoService.php", "WsPessoaFisica"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoIndicador");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoIndicador"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("defasagemIndicador");
        elemField.setXmlName(new javax.xml.namespace.QName("", "defasagemIndicador"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("limitacoesRestricoesIndicador");
        elemField.setXmlName(new javax.xml.namespace.QName("", "limitacoesRestricoesIndicador"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("polaridadeIndicador");
        elemField.setXmlName(new javax.xml.namespace.QName("", "polaridadeIndicador"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("abrangenciaGeografica");
        elemField.setXmlName(new javax.xml.namespace.QName("", "abrangenciaGeografica"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("desagregacaoIndicador");
        elemField.setXmlName(new javax.xml.namespace.QName("", "desagregacaoIndicador"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dadosFaltantes");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dadosFaltantes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("formaDisseminacao");
        elemField.setXmlName(new javax.xml.namespace.QName("", "formaDisseminacao"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("observacao");
        elemField.setXmlName(new javax.xml.namespace.QName("", "observacao"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("indicadorAcumulavel");
        elemField.setXmlName(new javax.xml.namespace.QName("", "indicadorAcumulavel"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("possuiMetas");
        elemField.setXmlName(new javax.xml.namespace.QName("", "possuiMetas"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("formulaIndicador");
        elemField.setXmlName(new javax.xml.namespace.QName("", "formulaIndicador"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
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
