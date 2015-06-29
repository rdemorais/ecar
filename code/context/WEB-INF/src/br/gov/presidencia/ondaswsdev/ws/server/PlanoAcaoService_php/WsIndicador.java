/**
 * WsIndicador.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php;

public class WsIndicador  implements java.io.Serializable {
    private br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsIndicadorCadastro cadastroIndicador;

    private br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsIndicadorMeta[] dadosMetaIndicador;

    private br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsIndicadorResultado[] dadosResultadoIndicador;

    public WsIndicador() {
    }

    public WsIndicador(
           br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsIndicadorCadastro cadastroIndicador,
           br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsIndicadorMeta[] dadosMetaIndicador,
           br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsIndicadorResultado[] dadosResultadoIndicador) {
           this.cadastroIndicador = cadastroIndicador;
           this.dadosMetaIndicador = dadosMetaIndicador;
           this.dadosResultadoIndicador = dadosResultadoIndicador;
    }


    /**
     * Gets the cadastroIndicador value for this WsIndicador.
     * 
     * @return cadastroIndicador
     */
    public br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsIndicadorCadastro getCadastroIndicador() {
        return cadastroIndicador;
    }


    /**
     * Sets the cadastroIndicador value for this WsIndicador.
     * 
     * @param cadastroIndicador
     */
    public void setCadastroIndicador(br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsIndicadorCadastro cadastroIndicador) {
        this.cadastroIndicador = cadastroIndicador;
    }


    /**
     * Gets the dadosMetaIndicador value for this WsIndicador.
     * 
     * @return dadosMetaIndicador
     */
    public br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsIndicadorMeta[] getDadosMetaIndicador() {
        return dadosMetaIndicador;
    }


    /**
     * Sets the dadosMetaIndicador value for this WsIndicador.
     * 
     * @param dadosMetaIndicador
     */
    public void setDadosMetaIndicador(br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsIndicadorMeta[] dadosMetaIndicador) {
        this.dadosMetaIndicador = dadosMetaIndicador;
    }


    /**
     * Gets the dadosResultadoIndicador value for this WsIndicador.
     * 
     * @return dadosResultadoIndicador
     */
    public br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsIndicadorResultado[] getDadosResultadoIndicador() {
        return dadosResultadoIndicador;
    }


    /**
     * Sets the dadosResultadoIndicador value for this WsIndicador.
     * 
     * @param dadosResultadoIndicador
     */
    public void setDadosResultadoIndicador(br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsIndicadorResultado[] dadosResultadoIndicador) {
        this.dadosResultadoIndicador = dadosResultadoIndicador;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WsIndicador)) return false;
        WsIndicador other = (WsIndicador) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.cadastroIndicador==null && other.getCadastroIndicador()==null) || 
             (this.cadastroIndicador!=null &&
              this.cadastroIndicador.equals(other.getCadastroIndicador()))) &&
            ((this.dadosMetaIndicador==null && other.getDadosMetaIndicador()==null) || 
             (this.dadosMetaIndicador!=null &&
              java.util.Arrays.equals(this.dadosMetaIndicador, other.getDadosMetaIndicador()))) &&
            ((this.dadosResultadoIndicador==null && other.getDadosResultadoIndicador()==null) || 
             (this.dadosResultadoIndicador!=null &&
              java.util.Arrays.equals(this.dadosResultadoIndicador, other.getDadosResultadoIndicador())));
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
        if (getCadastroIndicador() != null) {
            _hashCode += getCadastroIndicador().hashCode();
        }
        if (getDadosMetaIndicador() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDadosMetaIndicador());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDadosMetaIndicador(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getDadosResultadoIndicador() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDadosResultadoIndicador());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDadosResultadoIndicador(), i);
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
        new org.apache.axis.description.TypeDesc(WsIndicador.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("https://ondaswsdev.presidencia.gov.br/ws/server/PlanoAcaoService.php", "WsIndicador"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cadastroIndicador");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cadastroIndicador"));
        elemField.setXmlType(new javax.xml.namespace.QName("https://ondaswsdev.presidencia.gov.br/ws/server/PlanoAcaoService.php", "WsIndicadorCadastro"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dadosMetaIndicador");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dadosMetaIndicador"));
        elemField.setXmlType(new javax.xml.namespace.QName("https://ondaswsdev.presidencia.gov.br/ws/server/PlanoAcaoService.php", "WsIndicadorMeta"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("", "item"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dadosResultadoIndicador");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dadosResultadoIndicador"));
        elemField.setXmlType(new javax.xml.namespace.QName("https://ondaswsdev.presidencia.gov.br/ws/server/PlanoAcaoService.php", "WsIndicadorResultado"));
        elemField.setNillable(true);
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
