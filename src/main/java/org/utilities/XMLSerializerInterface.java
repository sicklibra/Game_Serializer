package org.utilities;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/*Authour Robert J.Hodges <sick_libra@hotmail.com>*/

public interface XMLSerializerInterface {
    //Use Xstream to hopefully serialize obj to XML
    //@param filename

    default void serializeXMLObject(String filename){
        OutputStreamWriter outfile=null;

        try{
            /*create the XML object to be written by Sending object to
            the converter to be changed to the custom format I set up.
             */
            XStream xstream = new XStream(new DomDriver());
            xstream.addPermission(AnyTypePermission.ANY);
            String xml = xstream.toXML(this).trim();
            xml = custimizeXML(xml).trim();

            //Write xml object to file
            outfile = new OutputStreamWriter(new FileOutputStream(filename), StandardCharsets.UTF_8);
            try(PrintWriter out = new PrintWriter(outfile)) {
                out.println(xml);
                out.flush();
            }
            catch(Exception e) {
                Logger.getLogger(XMLSerializerInterface.class.getName()).log(Level.SEVERE, "Could not write to specified file", e);
            }
            finally {
                if (outfile != null){
                    outfile.close();
                }
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    /*@param filename
    @param doValicate
    @return
     */
    default Object deserializeXMLObject(String filename,boolean doValidate){
        //Pull object from XML.
        Object myObject = null;

        try{
            InputStream bis =new ByteArrayInputStream(Files.readAllBytes(Paths.get(filename)));
            Reader reader = new InputStreamReader(bis, StandardCharsets.UTF_8);

            // Retrieve info via xstream
            XStream xstream = new XStream(new DomDriver());
            xstream.addPermission(AnyTypePermission.ANY);
            customizeXstream(xstream);
            myObject = xstream.fromXML(reader);
        }
        catch (Exception iOException){
            //assuming a placeholder?
        }
        return myObject;
    }
    /*
    @param xstream
     */
    void customizeXstream(XStream xstream);
    /*@param sml
    @return
     */
    default String custimizeXML(String xml){return xml;}
}
