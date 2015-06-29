
package ecar.webservices.pacinter;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ecar.webservices.pacinter package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetPtcTodosResponse_QNAME = new QName("http://webserviceecar.serpro.gov.br/", "getPtcTodosResponse");
    private final static QName _Versao_QNAME = new QName("http://webserviceecar.serpro.gov.br/", "versao");
    private final static QName _GetAptTodos_QNAME = new QName("http://webserviceecar.serpro.gov.br/", "getAptTodos");
    private final static QName _GetPtcId_QNAME = new QName("http://webserviceecar.serpro.gov.br/", "getPtcId");
    private final static QName _VersaoResponse_QNAME = new QName("http://webserviceecar.serpro.gov.br/", "versaoResponse");
    private final static QName _GetItemId_QNAME = new QName("http://webserviceecar.serpro.gov.br/", "getItemId");
    private final static QName _GetPtcTodos_QNAME = new QName("http://webserviceecar.serpro.gov.br/", "getPtcTodos");
    private final static QName _GetItemTodosResponse_QNAME = new QName("http://webserviceecar.serpro.gov.br/", "getItemTodosResponse");
    private final static QName _GetItemIdResponse_QNAME = new QName("http://webserviceecar.serpro.gov.br/", "getItemIdResponse");
    private final static QName _GetAptTodosResponse_QNAME = new QName("http://webserviceecar.serpro.gov.br/", "getAptTodosResponse");
    private final static QName _GetAptIdResponse_QNAME = new QName("http://webserviceecar.serpro.gov.br/", "getAptIdResponse");
    private final static QName _GetPtcIdResponse_QNAME = new QName("http://webserviceecar.serpro.gov.br/", "getPtcIdResponse");
    private final static QName _GetAptId_QNAME = new QName("http://webserviceecar.serpro.gov.br/", "getAptId");
    private final static QName _GetItemTodos_QNAME = new QName("http://webserviceecar.serpro.gov.br/", "getItemTodos");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ecar.webservices.pacinter
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetAptTodosResponse }
     * 
     */
    public GetAptTodosResponse createGetAptTodosResponse() {
        return new GetAptTodosResponse();
    }

    /**
     * Create an instance of {@link GetItemTodosResponse }
     * 
     */
    public GetItemTodosResponse createGetItemTodosResponse() {
        return new GetItemTodosResponse();
    }

    /**
     * Create an instance of {@link GetAptId }
     * 
     */
    public GetAptId createGetAptId() {
        return new GetAptId();
    }

    /**
     * Create an instance of {@link VersaoResponse }
     * 
     */
    public VersaoResponse createVersaoResponse() {
        return new VersaoResponse();
    }

    /**
     * Create an instance of {@link GetAptIdResponse }
     * 
     */
    public GetAptIdResponse createGetAptIdResponse() {
        return new GetAptIdResponse();
    }

    /**
     * Create an instance of {@link GetPtcIdResponse }
     * 
     */
    public GetPtcIdResponse createGetPtcIdResponse() {
        return new GetPtcIdResponse();
    }

    /**
     * Create an instance of {@link GetPtcId }
     * 
     */
    public GetPtcId createGetPtcId() {
        return new GetPtcId();
    }

    /**
     * Create an instance of {@link GetPtcTodosResponse }
     * 
     */
    public GetPtcTodosResponse createGetPtcTodosResponse() {
        return new GetPtcTodosResponse();
    }

    /**
     * Create an instance of {@link DtoItem }
     * 
     */
    public DtoItem createDtoItem() {
        return new DtoItem();
    }

    /**
     * Create an instance of {@link DtoPtc }
     * 
     */
    public DtoPtc createDtoPtc() {
        return new DtoPtc();
    }

    /**
     * Create an instance of {@link Versao }
     * 
     */
    public Versao createVersao() {
        return new Versao();
    }

    /**
     * Create an instance of {@link GetItemTodos }
     * 
     */
    public GetItemTodos createGetItemTodos() {
        return new GetItemTodos();
    }

    /**
     * Create an instance of {@link DtoApt }
     * 
     */
    public DtoApt createDtoApt() {
        return new DtoApt();
    }

    /**
     * Create an instance of {@link GetItemIdResponse }
     * 
     */
    public GetItemIdResponse createGetItemIdResponse() {
        return new GetItemIdResponse();
    }

    /**
     * Create an instance of {@link GetPtcTodos }
     * 
     */
    public GetPtcTodos createGetPtcTodos() {
        return new GetPtcTodos();
    }

    /**
     * Create an instance of {@link GetItemId }
     * 
     */
    public GetItemId createGetItemId() {
        return new GetItemId();
    }

    /**
     * Create an instance of {@link GetAptTodos }
     * 
     */
    public GetAptTodos createGetAptTodos() {
        return new GetAptTodos();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPtcTodosResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webserviceecar.serpro.gov.br/", name = "getPtcTodosResponse")
    public JAXBElement<GetPtcTodosResponse> createGetPtcTodosResponse(GetPtcTodosResponse value) {
        return new JAXBElement<GetPtcTodosResponse>(_GetPtcTodosResponse_QNAME, GetPtcTodosResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Versao }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webserviceecar.serpro.gov.br/", name = "versao")
    public JAXBElement<Versao> createVersao(Versao value) {
        return new JAXBElement<Versao>(_Versao_QNAME, Versao.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAptTodos }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webserviceecar.serpro.gov.br/", name = "getAptTodos")
    public JAXBElement<GetAptTodos> createGetAptTodos(GetAptTodos value) {
        return new JAXBElement<GetAptTodos>(_GetAptTodos_QNAME, GetAptTodos.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPtcId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webserviceecar.serpro.gov.br/", name = "getPtcId")
    public JAXBElement<GetPtcId> createGetPtcId(GetPtcId value) {
        return new JAXBElement<GetPtcId>(_GetPtcId_QNAME, GetPtcId.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VersaoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webserviceecar.serpro.gov.br/", name = "versaoResponse")
    public JAXBElement<VersaoResponse> createVersaoResponse(VersaoResponse value) {
        return new JAXBElement<VersaoResponse>(_VersaoResponse_QNAME, VersaoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetItemId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webserviceecar.serpro.gov.br/", name = "getItemId")
    public JAXBElement<GetItemId> createGetItemId(GetItemId value) {
        return new JAXBElement<GetItemId>(_GetItemId_QNAME, GetItemId.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPtcTodos }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webserviceecar.serpro.gov.br/", name = "getPtcTodos")
    public JAXBElement<GetPtcTodos> createGetPtcTodos(GetPtcTodos value) {
        return new JAXBElement<GetPtcTodos>(_GetPtcTodos_QNAME, GetPtcTodos.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetItemTodosResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webserviceecar.serpro.gov.br/", name = "getItemTodosResponse")
    public JAXBElement<GetItemTodosResponse> createGetItemTodosResponse(GetItemTodosResponse value) {
        return new JAXBElement<GetItemTodosResponse>(_GetItemTodosResponse_QNAME, GetItemTodosResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetItemIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webserviceecar.serpro.gov.br/", name = "getItemIdResponse")
    public JAXBElement<GetItemIdResponse> createGetItemIdResponse(GetItemIdResponse value) {
        return new JAXBElement<GetItemIdResponse>(_GetItemIdResponse_QNAME, GetItemIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAptTodosResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webserviceecar.serpro.gov.br/", name = "getAptTodosResponse")
    public JAXBElement<GetAptTodosResponse> createGetAptTodosResponse(GetAptTodosResponse value) {
        return new JAXBElement<GetAptTodosResponse>(_GetAptTodosResponse_QNAME, GetAptTodosResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAptIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webserviceecar.serpro.gov.br/", name = "getAptIdResponse")
    public JAXBElement<GetAptIdResponse> createGetAptIdResponse(GetAptIdResponse value) {
        return new JAXBElement<GetAptIdResponse>(_GetAptIdResponse_QNAME, GetAptIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPtcIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webserviceecar.serpro.gov.br/", name = "getPtcIdResponse")
    public JAXBElement<GetPtcIdResponse> createGetPtcIdResponse(GetPtcIdResponse value) {
        return new JAXBElement<GetPtcIdResponse>(_GetPtcIdResponse_QNAME, GetPtcIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAptId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webserviceecar.serpro.gov.br/", name = "getAptId")
    public JAXBElement<GetAptId> createGetAptId(GetAptId value) {
        return new JAXBElement<GetAptId>(_GetAptId_QNAME, GetAptId.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetItemTodos }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webserviceecar.serpro.gov.br/", name = "getItemTodos")
    public JAXBElement<GetItemTodos> createGetItemTodos(GetItemTodos value) {
        return new JAXBElement<GetItemTodos>(_GetItemTodos_QNAME, GetItemTodos.class, null, value);
    }

}
