/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package claseCoche;

import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author oscartabora
 */
//1).Instancio DefaultHandler para sobrescribir tres metodos necesarios
//para el uso de SAX
public class CocheHandler extends DefaultHandler {

    //2).Inicializo un object coche de tipo Coche
    Coche coche;
    //3).Creo una List que reciba un object de tipo Coche
    List<Coche> list = new ArrayList<>();

    //4).Creo un object de tipo StrinBuilder que luego
    //Almacenare los datos de tipo Coche. tendre que llamar a sus metodos 
    //para manipular para que reciban los valores.
    StringBuilder sb = new StringBuilder();

    /**
     * 5).Este metodo luego lo instaciare para usar para almacenar los valores
     * del XML de. Recibe un parametro de tipo Coche.
     *
     * @return
     */
    public List<Coche> getList() {
        return list;
    }

    //6).Es un metodo de la clase DefaultHandler. Recibe solo los valores del XML
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        sb.append(ch, start, length);

    }

    //7).Es un metodo de la clase DefaultHandler. Recibe las etiquetas del archivo XML
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (qName) {
            case "marca":
                coche.setMarca(sb.toString());
                break;
            case "modelo":
                coche.setModelo(sb.toString());
                break;
            case "cilindrada":
                coche.setCilindrada(sb.toString());
                break;

        }

    }

    //8).Es un metodo de la clase DefaultHandler. Recibe los atribustos del archivo XML.
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        switch (qName) {
            case "coche":
                coche = new Coche();
                list.add(coche);
                coche.setId(Integer.parseInt(attributes.getValue("id")));
                break;
            case "marca":
                sb.delete(0, sb.length());

                break;
            case "modelo":
                sb.delete(0, sb.length());

                break;
            case "cilindrada":
                sb.delete(0, sb.length());

                break;

        }

    }

}
