/**
 * WsPessoaFisica.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php;

public class WsPessoaFisica  implements java.io.Serializable {
    private java.lang.String numeroCpf;

    private java.lang.String nome;

    private java.lang.String telefoneContato;

    private java.lang.String email;

    public WsPessoaFisica() {
    }

    public WsPessoaFisica(
           java.lang.String numeroCpf,
           java.lang.String nome,
           java.lang.String telefoneContato,
           java.lang.String email) {
           this.numeroCpf = numeroCpf;
           this.nome = nome;
           this.telefoneContato = telefoneContato;
           this.email = email;
    }
    
    public WsPessoaFisica(
            java.lang.String numeroCpf,
            java.lang.String nome) {
            this.numeroCpf = numeroCpf;
            this.nome = nome;
     }    


    /**
     * Gets the numeroCpf value for this WsPessoaFisica.
     * 
     * @return numeroCpf
     */
    public java.lang.String getNumeroCpf() {
        return numeroCpf;
    }


    /**
     * Sets the numeroCpf value for this WsPessoaFisica.
     * 
     * @param numeroCpf
     */
    public void setNumeroCpf(java.lang.String numeroCpf) {
        this.numeroCpf = numeroCpf;
    }


    /**
     * Gets the nome value for this WsPessoaFisica.
     * 
     * @return nome
     */
    public java.lang.String getNome() {
        return nome;
    }


    /**
     * Sets the nome value for this WsPessoaFisica.
     * 
     * @param nome
     */
    public void setNome(java.lang.String nome) {
        this.nome = nome;
    }


    /**
     * Gets the telefoneContato value for this WsPessoaFisica.
     * 
     * @return telefoneContato
     */
    public java.lang.String getTelefoneContato() {
        return telefoneContato;
    }


    /**
     * Sets the telefoneContato value for this WsPessoaFisica.
     * 
     * @param telefoneContato
     */
    public void setTelefoneContato(java.lang.String telefoneContato) {
        this.telefoneContato = telefoneContato;
    }


    /**
     * Gets the email value for this WsPessoaFisica.
     * 
     * @return email
     */
    public java.lang.String getEmail() {
        return email;
    }


    /**
     * Sets the email value for this WsPessoaFisica.
     * 
     * @param email
     */
    public void setEmail(java.lang.String email) {
        this.email = email;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WsPessoaFisica)) return false;
        WsPessoaFisica other = (WsPessoaFisica) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.numeroCpf==null && other.getNumeroCpf()==null) || 
             (this.numeroCpf!=null &&
              this.numeroCpf.equals(other.getNumeroCpf()))) &&
            ((this.nome==null && other.getNome()==null) || 
             (this.nome!=null &&
              this.nome.equals(other.getNome()))) &&
            ((this.telefoneContato==null && other.getTelefoneContato()==null) || 
             (this.telefoneContato!=null &&
              this.telefoneContato.equals(other.getTelefoneContato()))) &&
            ((this.email==null && other.getEmail()==null) || 
             (this.email!=null &&
              this.email.equals(other.getEmail())));
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
        if (getNumeroCpf() != null) {
            _hashCode += getNumeroCpf().hashCode();
        }
        if (getNome() != null) {
            _hashCode += getNome().hashCode();
        }
        if (getTelefoneContato() != null) {
            _hashCode += getTelefoneContato().hashCode();
        }
        if (getEmail() != null) {
            _hashCode += getEmail().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WsPessoaFisica.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("https://ondaswsdev.presidencia.gov.br/ws/server/PlanoAcaoService.php", "WsPessoaFisica"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroCpf");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroCpf"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nome");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nome"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("telefoneContato");
        elemField.setXmlName(new javax.xml.namespace.QName("", "telefoneContato"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("email");
        elemField.setXmlName(new javax.xml.namespace.QName("", "email"));
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
