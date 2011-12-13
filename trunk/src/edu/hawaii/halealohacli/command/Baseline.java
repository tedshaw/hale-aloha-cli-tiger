package edu.hawaii.halealohacli.command;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

/**
 * Baseline class to hold baseline data as well as storage and retrieval method. Uses XML methods
 * described in http://totheriver.com/learn/xml/xmltutorial.html
 * 
 * @author Toy Lim
 */
public class Baseline {
  /**
   * TIME_PARTITIONS is a constant set to 24.
   */
  public static final int TIME_PARTITIONS = 24;
  private double[] powerBaseline;
  private String source;

  /**
   * Default constructor.
   */
  public Baseline() {
    powerBaseline = new double[TIME_PARTITIONS];
    source = "unknown";
  }

  /**
   * Constructor with source argument.
   * 
   * @param dataSource Name of baseline source
   */
  public Baseline(String dataSource) {
    powerBaseline = new double[TIME_PARTITIONS];
    source = dataSource;
  }

  /**
   * Get baseline value for the given time index.
   * 
   * @param timeIndex integer time index (0 to 23)
   * @return power baseline double value
   */
  public double getBaseline(int timeIndex) {
    if (timeIndex >= 0 && timeIndex < TIME_PARTITIONS) {
      return powerBaseline[timeIndex];
    }
    return 0.0;
  }

  /**
   * Set baseline value.
   * 
   * @param timeIndex integer time index (0 to 23)
   * @param power baseline double value
   */
  public void setBaseline(int timeIndex, double power) {
    if (timeIndex >= 0 && timeIndex < TIME_PARTITIONS) {
      powerBaseline[timeIndex] = power;
    }
  }

  /**
   * Set baseline value.
   * 
   * @param dataPoints one or more baseline values (assumes time index starts at 0)
   */
  public void setBaseline(double... dataPoints) {
    int index = 0;
    for (double data : dataPoints) {
      if (index < TIME_PARTITIONS) {
        powerBaseline[index] = data;
        index++;
      }
    }
    if (index < TIME_PARTITIONS) {
      System.err.format("Needs %d data points, only %d data points provided\n", TIME_PARTITIONS,
          index);
    }
  }

  /**
   * Store baseline data to file.
   * 
   * @param filename String file name
   * @return true if success, false otherwise
   */
  public boolean storeToFile(String filename) {
    Document dom;
    // get an instance of factory
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    try {
      // get an instance of builder
      DocumentBuilder db = dbf.newDocumentBuilder();
      // create an instance of DOM
      dom = db.newDocument();
    }
    catch (ParserConfigurationException pce) {
      System.out.println("Error while trying to instantiate DocumentBuilder " + pce);
      return false;
    }
    // create the root element
    Element rootElement = dom.createElement("BaselineDay");
    rootElement.setAttribute("source", source);
    dom.appendChild(rootElement);

    for (int index = 0; index < TIME_PARTITIONS; index++) {
      Element baselineElement = dom.createElement("BaselineHour");
      baselineElement.setAttribute("index", String.valueOf(index));
      // create and append baseline data
      Element powerElement = dom.createElement("Power");
      Text powerText = dom.createTextNode(String.valueOf(powerBaseline[index]));
      powerElement.appendChild(powerText);
      baselineElement.appendChild(powerElement);
      // append element to document root
      rootElement.appendChild(baselineElement);
    }

    Transformer tr;
    try {
      tr = TransformerFactory.newInstance().newTransformer();
      tr.setOutputProperty(OutputKeys.INDENT, "yes");
      tr.setOutputProperty(OutputKeys.METHOD, "xml");
      tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
      tr.transform(new DOMSource(dom), new StreamResult(new FileOutputStream(filename)));
    }
    catch (FileNotFoundException e) {
      // e.printStackTrace();
      System.err.format("Can't write to file: %s\n", filename);
      return false;
    }
    catch (TransformerException e) {
      // e.printStackTrace();
      System.out.println("Error while trying to instantiate Transformer " + e);
      return false;
    }
    return true;
  }

  /**
   * Retrieve baseline data from file.
   * 
   * @param filename file to be read from
   * @return true if success, false otherwise
   */
  public boolean retrieveFromFile(String filename) {
    //
    return true;
  }
}
