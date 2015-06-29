/**
 * PlanoAcaoServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php;

public class PlanoAcaoServiceServiceLocator extends org.apache.axis.client.Service implements br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.PlanoAcaoServiceService {

    public PlanoAcaoServiceServiceLocator() {
    }


    public PlanoAcaoServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public PlanoAcaoServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for PlanoAcaoServicePort
    private java.lang.String PlanoAcaoServicePort_address = "https://ondaswsdev.presidencia.gov.br/ws/server/PlanoAcaoService.php";

    public java.lang.String getPlanoAcaoServicePortAddress() {
        return PlanoAcaoServicePort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String PlanoAcaoServicePortWSDDServiceName = "PlanoAcaoServicePort";

    public java.lang.String getPlanoAcaoServicePortWSDDServiceName() {
        return PlanoAcaoServicePortWSDDServiceName;
    }

    public void setPlanoAcaoServicePortWSDDServiceName(java.lang.String name) {
        PlanoAcaoServicePortWSDDServiceName = name;
    }

    public br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.PlanoAcaoServicePort getPlanoAcaoServicePort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(PlanoAcaoServicePort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getPlanoAcaoServicePort(endpoint);
    }

    public br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.PlanoAcaoServicePort getPlanoAcaoServicePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.PlanoAcaoServiceBindingStub _stub = new br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.PlanoAcaoServiceBindingStub(portAddress, this);
            _stub.setPortName(getPlanoAcaoServicePortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setPlanoAcaoServicePortEndpointAddress(java.lang.String address) {
        PlanoAcaoServicePort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.PlanoAcaoServicePort.class.isAssignableFrom(serviceEndpointInterface)) {
                br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.PlanoAcaoServiceBindingStub _stub = new br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.PlanoAcaoServiceBindingStub(new java.net.URL(PlanoAcaoServicePort_address), this);
                _stub.setPortName(getPlanoAcaoServicePortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("PlanoAcaoServicePort".equals(inputPortName)) {
            return getPlanoAcaoServicePort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("https://ondaswsdev.presidencia.gov.br/ws/server/PlanoAcaoService.php", "PlanoAcaoServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("https://ondaswsdev.presidencia.gov.br/ws/server/PlanoAcaoService.php", "PlanoAcaoServicePort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("PlanoAcaoServicePort".equals(portName)) {
            setPlanoAcaoServicePortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
