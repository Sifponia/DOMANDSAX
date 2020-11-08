/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClaseDOM;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import lombok.Data;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Attr;

/**
 *
 * @author oscartabora
 */
@Data
public class DOM {

    //1).Atribustos de la clase DOM
    private File file;
    private DocumentBuilderFactory dbf;
    private DocumentBuilder db;
    private Document document;
    private NodeList nl;
    private Node node;

    /**
     * ************************************************************************
     * Buscar elemento
     * ************************************************************************
     * @param ruta
     * @param etiqueta
     * @return
     * @throws java.lang.Exception
     */
    public String getBuscar(String ruta, String etiqueta) throws Exception {

        //1).Estos dos variables recibiran los valores de elemA.getTextContent(). 
        String xml = " ";
        String auxiliar = "";

        //2).Crearemos un object de tipo DocumentBuilderFactory.
        this.dbf = DocumentBuilderFactory.newDefaultInstance();
        this.db = this.dbf.newDocumentBuilder();
        //ruta el un parametro del metodo ruta
        this.document = this.db.parse(ruta);

        //3).Creo un NdeList el cual recibe un patamtero String del metodo para buscar la referencia 
        //de la etiqueta.
        NodeList contents = this.document.getElementsByTagName(etiqueta);
        //contents = this.document.getElementsByTagName("coche");
        for (int i = 0; i < contents.getLength(); ++i) {
            Element elemA = (Element) contents.item(i);

            xml = etiqueta + " : " + elemA.getTextContent() + "\n";
            auxiliar += xml;
        }

        //4).Devuelvo el valor de la asignacion auxiliar+=xml. Tiene todo los valores buscado 
        //Por el ciclo for
        return auxiliar;
    }

    /**
     * ************************************************************************
     * Agregar un nuevo nodo
     * ***********************************************************************
     * @param marca
     * @param id
     * @param ruta
     * @param modelo
     * @param cilindrada
     * @throws java.lang.Exception
     */
    public void sertAgregar(String ruta, String id, String marca, String modelo, String cilindrada) throws Exception {

        //1).Crearemos un object de tipo DocumentBuilderFactory.
        this.dbf = DocumentBuilderFactory.newDefaultInstance();
        this.db = this.dbf.newDocumentBuilder();
        this.document = this.db.parse(ruta);

        // 2).Definimos el nodo que contendra los elementos
        Node root = this.document.getFirstChild();

        Element eCoche = this.document.createElement("coche");
        root.appendChild(eCoche);

        //3).Atributo para el nodo id
        Attr attr = this.document.createAttribute("id");
        attr.setValue(id);
        eCoche.setAttributeNode(attr);

        // 4).Definimos cada uno de los elementos y le asignamos un valor   
        Element eMarca = this.document.createElement("marca");
        eMarca.appendChild(this.document.createTextNode(marca));
        eCoche.appendChild(eMarca);

        Element eModelo = this.document.createElement("modelo");
        eModelo.appendChild(this.document.createTextNode(modelo));
        eCoche.appendChild(eModelo);

        Element eCilindrada = this.document.createElement("cilindrada");
        eCilindrada.appendChild(this.document.createTextNode(cilindrada));
        eCoche.appendChild(eCilindrada);

        DOMSource source = new DOMSource(document);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        StreamResult result = new StreamResult(ruta);
        transformer.transform(source, result);

    }

    /**
     * ************************************************************************
     * Eliminar Nodo Hijo
     * ***********************************************************************
     * @param id
     * @param file
     * @param tag
     * @throws java.lang.Exception
     */
    public void buscarYEliminar(String id, File file, String tag) throws Exception {
        //1).Crearemos un object de tipo DocumentBuilderFactory.
        this.dbf = DocumentBuilderFactory.newDefaultInstance();
        this.db = this.dbf.newDocumentBuilder();
        this.document = this.db.parse(file);

        // 2. buscar y eliminar el elemento <coche id="3"> de entre 
        //    muchos elementos <coche> ubicados en cualquier posicion del documento
        NodeList items = this.document.getElementsByTagName(tag);
        for (int ix = 0; ix < items.getLength(); ix++) {
            Element element = (Element) items.item(ix);
            // 3).elejir un elemento especifico por algun atributo
            if (element.getAttribute("id").equalsIgnoreCase(id)) {
                // borrar elemento
                element.getParentNode().removeChild(element);
            }
        }

        //4).Exportar nuevamente el XML
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        Result output = new StreamResult(file);
        Source input = new DOMSource(this.document);
        transformer.transform(input, output);

    }

    /**
     * ************************************************************************
     * Mostar XML
     * ***********************************************************************
     * @param file
     * @param etiqueta
     * @return
     * @throws java.lang.Exception
     */
    public String recorrer(File file, String etiqueta) throws Exception {
        //1).Crearemos un object de tipo DocumentBuilderFactory.
        //Convertimos un tipo File el document

        this.dbf = DocumentBuilderFactory.newDefaultInstance();
        this.db = this.dbf.newDocumentBuilder();
        this.document = this.db.parse(file);

        /* 2).La normalización es aplicable cuando necesita convertir caracteres con marcas diacríticas, 
        cambiar todas las mayúsculas y minúsculas, descomponer ligaduras o convertir caracteres katakana 
        de ancho medio en caracteres de ancho completo, etc.
         */
        this.document.getDocumentElement().normalize();

        //3).Imprimo la etiquta padre del nodo
        String raizs = "Elemento Raiz " + this.document.getDocumentElement().getNodeName();

        //4).Estos dos variables recibiran los valores element.getAttribute, 
        // element.getElementsByTagName("marca").item(0).getTextContent()
        //Se iran sumando a la misma variable con un salto de linea.
        String xml = " ";
        String auxiliar = "";

        NodeList nl = this.document.getElementsByTagName(etiqueta);

        for (int i = 0; i < nl.getLength(); i++) {

            Node nodee = nl.item(i);

            if (nodee.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) nodee;

                auxiliar += xml = "\n" + "Id " + element.getAttribute("id") + "\n"
                        + "Marca " + element.getElementsByTagName("marca").item(0).getTextContent()
                        + "\n" + "Modelo " + element.getElementsByTagName("modelo").item(0).getTextContent()
                        + "\n" + "cilindrada " + element.getElementsByTagName("cilindrada").item(0).getTextContent() + "\n";

            }

        }

        //5).Devuelvo el valor raizs que el el nombre de nodo padre y auxiliar que recibio 
        //Toda la imfomacion ya ordenada de archivo XML
        return raizs + auxiliar;

    }

}

/*


  /*
    
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(file);

                doc.getDocumentElement().normalize();

                NodeList nList = doc.getElementsByTagName(nodoAtribute);
                System.out.println("Número de coches: " + nList.getLength());
 */
 /*

  public String getLeerFicheroSinAsignarEtiquetas(File filee, String nodoAtributo) throws Exception {

        String stringList = " ";

        this.dbf = DocumentBuilderFactory.newDefaultInstance();
        this.db = this.dbf.newDocumentBuilder();
        this.document = this.db.parse(filee);

        this.document.getDocumentElement().normalize();

        this.nl = this.document.getElementsByTagName(nodoAtributo);

        for (int i = 0; i < nl.getLength(); i++) {
            Node node = nl.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) node;

                if (eElement.hasChildNodes()) {
                    NodeList nl = node.getChildNodes();
                    for (int j = 0; j < nl.getLength(); j++) {
                        Node nd = nl.item(j);
                        //System.out.println(nd.getTextContent());

                        String a = String.valueOf(nd.getTextContent());
                        stringList += a + "\n";

                    }
                }

            }

        }

        // JOptionPane.showMessageDialog(null, nodee);
        System.out.println("");
        //jt_area.setText("Número de coches: " + nList.getLength() + "\n" + nodee);

        return "Numero de Id " + nl.getLength() + "\n" + stringList;
    }




 /*
    //Constructor Vacio
    public DOM() {

    }

    //Constructor con parametros 
    public DOM(File file, DocumentBuilderFactory dbf, DocumentBuilder db, Document document, NodeList nl) {
        this.file = file;
        this.dbf = dbf;
        this.db = db;
        this.document = document;
        this.nl = nl;
    }

//Metodo Set and get
    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public DocumentBuilderFactory getDbf() {
        return dbf;
    }

    public void setDbf(DocumentBuilderFactory dbf) {
        this.dbf = dbf;
    }

    public DocumentBuilder getDb() {
        return db;
    }

    public void setDb(DocumentBuilder db) {
        this.db = db;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public NodeList getNl() {
        return nl;
    }

    public void setNl(NodeList nl) {
        this.nl = nl;
    }

    
    
    
    
    
 */
