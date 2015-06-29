/**
 * WsRetorno.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php;

public class WsRetorno  implements java.io.Serializable {
    private boolean sucesso;

    private java.lang.String operacao;

    private java.lang.String chave;

    private java.lang.String mensagensErro;

    public WsRetorno() {
    }

    public WsRetorno(
           boolean sucesso,
           java.lang.String operacao,
           java.lang.String chave,
           java.lang.String mensagensErro) {
           this.sucesso = sucesso;
           this.operacao = operacao;
           this.chave = chave;
           this.mensagensErro = mensagensErro;
    }


    /**
     * Gets the sucesso value for this WsRetorno.
     * 
     * @return sucesso
     */
    public boolean isSucesso() {
        return sucesso;
    }


    /**
     * Sets the sucesso value for this WsRetorno.
     * 
     * @param sucesso
     */
    public void setSucesso(boolean sucesso) {
        this.sucesso = sucesso;
    }


    /**
     * Gets the operacao value for this WsRetorno.
     * 
     * @return operacao
     */
    public java.lang.String getOperacao() {
        return operacao;
    }


    /**
     * Sets the operacao value for this WsRetorno.
     * 
     * @param operacao
     */
    public void setOperacao(java.lang.String operacao) {
        this.operacao = operacao;
    }


    /**
     * Gets the chave value for this WsRetorno.
     * 
     * @return chave
     */
    public java.lang.String getChave() {
        return chave;
    }


    /**
     * Sets the chave value for this WsRetorno.
     * 
     * @param chave
     */
    public void setChave(java.lang.String chave) {
        this.chave = chave;
    }


    /**
     * Gets the mensagensErro value for this WsRetorno.
     * 
     * @return mensagensErro
     */
    public java.lang.String getMensagensErro() {
        return mensagensErro;
    }


    /**
     * Sets the mensagensErro value for this WsRetorno.
     * 
     * @param mensagensErro
     */
    public void setMensagensErro(java.lang.String mensagensErro) {
        this.mensagensErro = mensagensErro;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WsRetorno)) return false;
        WsRetorno other = (WsRetorno) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.sucesso == other.isSucesso() &&
            ((this.operacao==null && other.getOperacao()==null) || 
             (this.operacao!=null &&
              this.operacao.equals(other.getOperacao()))) &&
            ((this.chave==null && other.getChave()==null) || 
             (this.chave!=null &&
              this.chave.equals(other.getChave()))) &&
            ((this.mensagensErro==null && other.getMensagensErro()==null) || 
             (this.mensagensErro!=null &&
              this.mensagensErro.equals(other.getMensagensErro())));
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
        _hashCode += (isSucesso() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getOperacao() != null) {
            _hashCode += getOperacao().hashCode();
        }
        if (getChave() != null) {
            _hashCode += getChave().hashCode();
        }
        if (getMensagensErro() != null) {
            _hashCode += getMensagensErro().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WsRetorno.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("https://ondaswsdev.presidencia.gov.br/ws/server/PlanoAcaoService.php", "WsRetorno"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sucesso");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sucesso"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("operacao");
        elemField.setXmlName(new javax.xml.namespace.QName("", "operacao"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("chave");
        elemField.setXmlName(new javax.xml.namespace.QName("", "chave"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mensagensErro");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mensagensErro"));
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
