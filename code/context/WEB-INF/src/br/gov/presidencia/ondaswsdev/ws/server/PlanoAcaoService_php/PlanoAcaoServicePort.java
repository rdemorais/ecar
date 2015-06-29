/**
 * PlanoAcaoServicePort.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php;

public interface PlanoAcaoServicePort extends java.rmi.Remote {

    /**
     * Persiste uma lista de ações no SIM-PR.
     */
    public br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsRetorno[] cadastraListaAcao(br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsUsuarioAutenticacao usuario, br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsListaAcao listaAcao) throws java.rmi.RemoteException;

    /**
     * Persiste uma ação no SIM-PR
     */
    public br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsRetorno cadastraAcao(br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsUsuarioAutenticacao usuario, br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsAcaoCadastro acaoCadastro) throws java.rmi.RemoteException;

    /**
     * Persiste uma atividade (ou sub-atividade) no SIM-PR
     */
    public br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsRetorno cadastraAtividade(br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsUsuarioAutenticacao usuario, br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsAtividadeCadastro atividadeCadastro) throws java.rmi.RemoteException;

    /**
     * Cadastra indicador
     */
    public br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsRetorno cadastraIndicador(br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsIndicador indicador) throws java.rmi.RemoteException;
}
