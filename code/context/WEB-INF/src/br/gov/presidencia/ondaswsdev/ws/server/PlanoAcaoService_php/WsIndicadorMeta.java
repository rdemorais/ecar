/**
 * WsIndicadorMeta.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php;

public class WsIndicadorMeta  implements java.io.Serializable {
    private int codigoMeta;

    private java.lang.String dataMeta;

    private java.lang.Float valorMeta;

    private java.lang.String dataResultadoMeta;

    private java.lang.Float valorResultado;

    private java.lang.Integer criarMetasDesagregacao;

    private java.lang.Integer desagregacaoMeta;

    public WsIndicadorMeta() {
    }

    public WsIndicadorMeta(
           int codigoMeta,
           java.lang.String dataMeta,
           java.lang.Float valorMeta,
           java.lang.String dataResultadoMeta,
           java.lang.Float valorResultado,
           java.lang.Integer criarMetasDesagregacao,
           java.lang.Integer desagregacaoMeta) {
           this.codigoMeta = codigoMeta;
           this.dataMeta = dataMeta;
           this.valorMeta = valorMeta;
           this.dataResultadoMeta = dataResultadoMeta;
           this.valorResultado = valorResultado;
           this.criarMetasDesagregacao = criarMetasDesagregacao;
           this.desagregacaoMeta = desagregacaoMeta;
    }


    /**
     * Gets the codigoMeta value for this WsIndicadorMeta.
     * 
     * @return codigoMeta
     */
    public int getCodigoMeta() {
        return codigoMeta;
    }


    /**
     * Sets the codigoMeta value for this WsIndicadorMeta.
     * 
     * @param codigoMeta
     */
    public void setCodigoMeta(int codigoMeta) {
        this.codigoMeta = codigoMeta;
    }


    /**
     * Gets the dataMeta value for this WsIndicadorMeta.
     * 
     * @return dataMeta
     */
    public java.lang.String getDataMeta() {
        return dataMeta;
    }


    /**
     * Sets the dataMeta value for this WsIndicadorMeta.
     * 
     * @param dataMeta
     */
    public void setDataMeta(java.lang.String dataMeta) {
        this.dataMeta = dataMeta;
    }


    /**
     * Gets the valorMeta value for this WsIndicadorMeta.
     * 
     * @return valorMeta
     */
    public java.lang.Float getValorMeta() {
        return valorMeta;
    }


    /**
     * Sets the valorMeta value for this WsIndicadorMeta.
     * 
     * @param valorMeta
     */
    public void setValorMeta(java.lang.Float valorMeta) {
        this.valorMeta = valorMeta;
    }


    /**
     * Gets the dataResultadoMeta value for this WsIndicadorMeta.
     * 
     * @return dataResultadoMeta
     */
    public java.lang.String getDataResultadoMeta() {
        return dataResultadoMeta;
    }


    /**
     * Sets the dataResultadoMeta value for this WsIndicadorMeta.
     * 
     * @param dataResultadoMeta
     */
    public void setDataResultadoMeta(java.lang.String dataResultadoMeta) {
        this.dataResultadoMeta = dataResultadoMeta;
    }


    /**
     * Gets the valorResultado value for this WsIndicadorMeta.
     * 
     * @return valorResultado
     */
    public java.lang.Float getValorResultado() {
        return valorResultado;
    }


    /**
     * Sets the valorResultado value for this WsIndicadorMeta.
     * 
     * @param valorResultado
     */
    public void setValorResultado(java.lang.Float valorResultado) {
        this.valorResultado = valorResultado;
    }


    /**
     * Gets the criarMetasDesagregacao value for this WsIndicadorMeta.
     * 
     * @return criarMetasDesagregacao
     */
    public java.lang.Integer getCriarMetasDesagregacao() {
        return criarMetasDesagregacao;
    }


    /**
     * Sets the criarMetasDesagregacao value for this WsIndicadorMeta.
     * 
     * @param criarMetasDesagregacao
     */
    public void setCriarMetasDesagregacao(java.lang.Integer criarMetasDesagregacao) {
        this.criarMetasDesagregacao = criarMetasDesagregacao;
    }


    /**
     * Gets the desagregacaoMeta value for this WsIndicadorMeta.
     * 
     * @return desagregacaoMeta
     */
    public java.lang.Integer getDesagregacaoMeta() {
        return desagregacaoMeta;
    }


    /**
     * Sets the desagregacaoMeta value for this WsIndicadorMeta.
     * 
     * @param desagregacaoMeta
     */
    public void setDesagregacaoMeta(java.lang.Integer desagregacaoMeta) {
        this.desagregacaoMeta = desagregacaoMeta;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WsIndicadorMeta)) return false;
        WsIndicadorMeta other = (WsIndicadorMeta) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.codigoMeta == other.getCodigoMeta() &&
            ((this.dataMeta==null && other.getDataMeta()==null) || 
             (this.dataMeta!=null &&
              this.dataMeta.equals(other.getDataMeta()))) &&
            ((this.valorMeta==null && other.getValorMeta()==null) || 
             (this.valorMeta!=null &&
              this.valorMeta.equals(other.getValorMeta()))) &&
            ((this.dataResultadoMeta==null && other.getDataResultadoMeta()==null) || 
             (this.dataResultadoMeta!=null &&
              this.dataResultadoMeta.equals(other.getDataResultadoMeta()))) &&
            ((this.valorResultado==null && other.getValorResultado()==null) || 
             (this.valorResultado!=null &&
              this.valorResultado.equals(other.getValorResultado()))) &&
            ((this.criarMetasDesagregacao==null && other.getCriarMetasDesagregacao()==null) || 
             (this.criarMetasDesagregacao!=null &&
              this.criarMetasDesagregacao.equals(other.getCriarMetasDesagregacao()))) &&
            ((this.desagregacaoMeta==null && other.getDesagregacaoMeta()==null) || 
             (this.desagregacaoMeta!=null &&
              this.desagregacaoMeta.equals(other.getDesagregacaoMeta())));
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
        _hashCode += getCodigoMeta();
        if (getDataMeta() != null) {
            _hashCode += getDataMeta().hashCode();
        }
        if (getValorMeta() != null) {
            _hashCode += getValorMeta().hashCode();
        }
        if (getDataResultadoMeta() != null) {
            _hashCode += getDataResultadoMeta().hashCode();
        }
        if (getValorResultado() != null) {
            _hashCode += getValorResultado().hashCode();
        }
        if (getCriarMetasDesagregacao() != null) {
            _hashCode += getCriarMetasDesagregacao().hashCode();
        }
        if (getDesagregacaoMeta() != null) {
            _hashCode += getDesagregacaoMeta().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WsIndicadorMeta.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("https://ondaswsdev.presidencia.gov.br/ws/server/PlanoAcaoService.php", "WsIndicadorMeta"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoMeta");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codigoMeta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataMeta");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataMeta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("valorMeta");
        elemField.setXmlName(new javax.xml.namespace.QName("", "valorMeta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataResultadoMeta");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataResultadoMeta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("valorResultado");
        elemField.setXmlName(new javax.xml.namespace.QName("", "valorResultado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("criarMetasDesagregacao");
        elemField.setXmlName(new javax.xml.namespace.QName("", "criarMetasDesagregacao"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("desagregacaoMeta");
        elemField.setXmlName(new javax.xml.namespace.QName("", "desagregacaoMeta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
