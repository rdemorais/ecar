/**
 * WsIndicadorResultado.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php;

public class WsIndicadorResultado  implements java.io.Serializable {
    private int desagregacaoResultado;

    private java.lang.String dataResultado;

    private java.lang.Float valorResultado;

    public WsIndicadorResultado() {
    }

    public WsIndicadorResultado(
           int desagregacaoResultado,
           java.lang.String dataResultado,
           java.lang.Float valorResultado) {
           this.desagregacaoResultado = desagregacaoResultado;
           this.dataResultado = dataResultado;
           this.valorResultado = valorResultado;
    }


    /**
     * Gets the desagregacaoResultado value for this WsIndicadorResultado.
     * 
     * @return desagregacaoResultado
     */
    public int getDesagregacaoResultado() {
        return desagregacaoResultado;
    }


    /**
     * Sets the desagregacaoResultado value for this WsIndicadorResultado.
     * 
     * @param desagregacaoResultado
     */
    public void setDesagregacaoResultado(int desagregacaoResultado) {
        this.desagregacaoResultado = desagregacaoResultado;
    }


    /**
     * Gets the dataResultado value for this WsIndicadorResultado.
     * 
     * @return dataResultado
     */
    public java.lang.String getDataResultado() {
        return dataResultado;
    }


    /**
     * Sets the dataResultado value for this WsIndicadorResultado.
     * 
     * @param dataResultado
     */
    public void setDataResultado(java.lang.String dataResultado) {
        this.dataResultado = dataResultado;
    }


    /**
     * Gets the valorResultado value for this WsIndicadorResultado.
     * 
     * @return valorResultado
     */
    public java.lang.Float getValorResultado() {
        return valorResultado;
    }


    /**
     * Sets the valorResultado value for this WsIndicadorResultado.
     * 
     * @param valorResultado
     */
    public void setValorResultado(java.lang.Float valorResultado) {
        this.valorResultado = valorResultado;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WsIndicadorResultado)) return false;
        WsIndicadorResultado other = (WsIndicadorResultado) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.desagregacaoResultado == other.getDesagregacaoResultado() &&
            ((this.dataResultado==null && other.getDataResultado()==null) || 
             (this.dataResultado!=null &&
              this.dataResultado.equals(other.getDataResultado()))) &&
            ((this.valorResultado==null && other.getValorResultado()==null) || 
             (this.valorResultado!=null &&
              this.valorResultado.equals(other.getValorResultado())));
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
        _hashCode += getDesagregacaoResultado();
        if (getDataResultado() != null) {
            _hashCode += getDataResultado().hashCode();
        }
        if (getValorResultado() != null) {
            _hashCode += getValorResultado().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WsIndicadorResultado.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("https://ondaswsdev.presidencia.gov.br/ws/server/PlanoAcaoService.php", "WsIndicadorResultado"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("desagregacaoResultado");
        elemField.setXmlName(new javax.xml.namespace.QName("", "desagregacaoResultado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataResultado");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataResultado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("valorResultado");
        elemField.setXmlName(new javax.xml.namespace.QName("", "valorResultado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
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
