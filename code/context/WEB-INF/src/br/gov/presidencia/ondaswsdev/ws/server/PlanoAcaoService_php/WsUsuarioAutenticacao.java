/**
 * WsUsuarioAutenticacao.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php;

public class WsUsuarioAutenticacao  implements java.io.Serializable {
    private java.lang.String usuario;

    private java.lang.String senha;

    public WsUsuarioAutenticacao() {
    }

    public WsUsuarioAutenticacao(
           java.lang.String usuario,
           java.lang.String senha) {
           this.usuario = usuario;
           this.senha = senha;
    }


    /**
     * Gets the usuario value for this WsUsuarioAutenticacao.
     * 
     * @return usuario
     */
    public java.lang.String getUsuario() {
        return usuario;
    }


    /**
     * Sets the usuario value for this WsUsuarioAutenticacao.
     * 
     * @param usuario
     */
    public void setUsuario(java.lang.String usuario) {
        this.usuario = usuario;
    }


    /**
     * Gets the senha value for this WsUsuarioAutenticacao.
     * 
     * @return senha
     */
    public java.lang.String getSenha() {
        return senha;
    }


    /**
     * Sets the senha value for this WsUsuarioAutenticacao.
     * 
     * @param senha
     */
    public void setSenha(java.lang.String senha) {
        this.senha = senha;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WsUsuarioAutenticacao)) return false;
        WsUsuarioAutenticacao other = (WsUsuarioAutenticacao) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.usuario==null && other.getUsuario()==null) || 
             (this.usuario!=null &&
              this.usuario.equals(other.getUsuario()))) &&
            ((this.senha==null && other.getSenha()==null) || 
             (this.senha!=null &&
              this.senha.equals(other.getSenha())));
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
        if (getUsuario() != null) {
            _hashCode += getUsuario().hashCode();
        }
        if (getSenha() != null) {
            _hashCode += getSenha().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WsUsuarioAutenticacao.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("https://ondaswsdev.presidencia.gov.br/ws/server/PlanoAcaoService.php", "WsUsuarioAutenticacao"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("usuario");
        elemField.setXmlName(new javax.xml.namespace.QName("", "usuario"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("senha");
        elemField.setXmlName(new javax.xml.namespace.QName("", "senha"));
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
