package br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php;

public class PlanoAcaoServicePortProxy implements br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.PlanoAcaoServicePort {
  private String _endpoint = null;
  private br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.PlanoAcaoServicePort planoAcaoServicePort = null;
  
  public PlanoAcaoServicePortProxy() {
    _initPlanoAcaoServicePortProxy();
  }
  
  public PlanoAcaoServicePortProxy(String endpoint) {
    _endpoint = endpoint;
    _initPlanoAcaoServicePortProxy();
  }
  
  private void _initPlanoAcaoServicePortProxy() {
    try {
      planoAcaoServicePort = (new br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.PlanoAcaoServiceServiceLocator()).getPlanoAcaoServicePort();
      if (planoAcaoServicePort != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)planoAcaoServicePort)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)planoAcaoServicePort)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (planoAcaoServicePort != null)
      ((javax.xml.rpc.Stub)planoAcaoServicePort)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.PlanoAcaoServicePort getPlanoAcaoServicePort() {
    if (planoAcaoServicePort == null)
      _initPlanoAcaoServicePortProxy();
    return planoAcaoServicePort;
  }
  
  public br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsRetorno[] cadastraListaAcao(br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsUsuarioAutenticacao usuario, br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsListaAcao listaAcao) throws java.rmi.RemoteException{
    if (planoAcaoServicePort == null)
      _initPlanoAcaoServicePortProxy();
    return planoAcaoServicePort.cadastraListaAcao(usuario, listaAcao);
  }
  
  public br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsRetorno cadastraAcao(br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsUsuarioAutenticacao usuario, br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsAcaoCadastro acaoCadastro) throws java.rmi.RemoteException{
    if (planoAcaoServicePort == null)
      _initPlanoAcaoServicePortProxy();
    return planoAcaoServicePort.cadastraAcao(usuario, acaoCadastro);
  }
  
  public br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsRetorno cadastraAtividade(br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsUsuarioAutenticacao usuario, br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsAtividadeCadastro atividadeCadastro) throws java.rmi.RemoteException{
    if (planoAcaoServicePort == null)
      _initPlanoAcaoServicePortProxy();
    return planoAcaoServicePort.cadastraAtividade(usuario, atividadeCadastro);
  }
  
  public br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsRetorno cadastraIndicador(br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsIndicador indicador) throws java.rmi.RemoteException{
    if (planoAcaoServicePort == null)
      _initPlanoAcaoServicePortProxy();
    return planoAcaoServicePort.cadastraIndicador(indicador);
  }
  
  
}