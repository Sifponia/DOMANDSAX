/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package claseSAX;

import claseCoche.Coche;
import claseCoche.CocheHandler;
import java.io.File;
import java.util.List;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 *
 * @author oscartabora
 */
public class Sax {

    //1).Creo dos object de tipo SAXParserFactory y SAXParser
    //Solo quedan inicializados.
    private SAXParserFactory spf;
    private SAXParser sp;

    /**
     * 2).Este metodo lo creo para luego usarlo con una GUI para recibir los
     * valores del archivo XML y guardalos en una variable.
     *
     * @param sFile
     * @return
     * @throws Exception
     */
    public String setparset(String sFile) throws Exception {

        //3).Estos dos variables recibiran los valores de una object Coche que usare
        //un casting para guaradar cada valor. 
        String auxiliar = " ";
        String a = " ";

        //4).Uso las dos atributos de punto numero 1). 
        //Creo una instancia.
        this.spf = SAXParserFactory.newDefaultInstance();
        this.sp = this.spf.newSAXParser();
        File file = new File(sFile);

        //5).LLamo a la clase CocheHandler  para poder usar usar 
        //un object que me solita en en parse.
        CocheHandler ch = new CocheHandler();
        this.sp.parse(file, ch);

        //6).Creo una List de tipo Coche y le asigno un metodo del object ch -> claseCocheHandler
        List<Coche> c = ch.getList();

        //7).Creo un for each para iterar una un object de tipo List y asignar 
        //valores a un nuevo object de tipo Coche.
        for (Coche coche : c) {

            //8).Convierto un object a un String
            auxiliar = String.valueOf(coche);
            //9).Añado los valores suman cada iteracion
            a += auxiliar;
        }

        //10).Devuelvo un String donde almacena todo la informacion ordenada 
        //del archivo XML.
        return a;
    }

}
