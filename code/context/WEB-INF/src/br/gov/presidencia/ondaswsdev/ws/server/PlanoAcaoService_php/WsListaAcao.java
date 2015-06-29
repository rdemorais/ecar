/**
 * WsListaAcao.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php;

public class WsListaAcao  implements java.io.Serializable {
    private br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsAcaoCadastro[] listaAcaoCadastro;

    public WsListaAcao() {
    }

    public WsListaAcao(
           br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsAcaoCadastro[] listaAcaoCadastro) {
           this.listaAcaoCadastro = listaAcaoCadastro;
    }


    /**
     * Gets the listaAcaoCadastro value for this WsListaAcao.
     * 
     * @return listaAcaoCadastro
     */
    public br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsAcaoCadastro[] getListaAcaoCadastro() {
        return listaAcaoCadastro;
    }


    /**
     * Sets the listaAcaoCadastro value for this WsListaAcao.
     * 
     * @param listaAcaoCadastro
     */
    public void setListaAcaoCadastro(br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsAcaoCadastro[] listaAcaoCadastro) {
        this.listaAcaoCadastro = listaAcaoCadastro;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WsListaAcao)) return false;
        WsListaAcao other = (WsListaAcao) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.listaAcaoCadastro==null && other.getListaAcaoCadastro()==null) || 
             (this.listaAcaoCadastro!=null &&
              java.util.Arrays.equals(this.listaAcaoCadastro, other.getListaAcaoCadastro())));
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
        if (getListaAcaoCadastro() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getListaAcaoCadastro());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getListaAcaoCadastro(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WsListaAcao.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("https://ondaswsdev.presidencia.gov.br/ws/server/PlanoAcaoService.php", "WsListaAcao"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("listaAcaoCadastro");
        elemField.setXmlName(new javax.xml.namespace.QName("", "listaAcaoCadastro"));
        elemField.setXmlType(new javax.xml.namespace.QName("https://ondaswsdev.presidencia.gov.br/ws/server/PlanoAcaoService.php", "WsAcaoCadastro"));
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("", "item"));
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
