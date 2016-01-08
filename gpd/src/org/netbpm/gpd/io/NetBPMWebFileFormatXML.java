/*
 * Created on 04.01.2004
 * Export of NetBPM Fileformat
 */
package org.netbpm.gpd.io;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;
import org.jgraph.JGraph;
import org.jgraph.graph.GraphModel;
import org.netbpm.gpd.GpdGraph;
import org.netbpm.gpd.cell.ActivityCell;
import org.netbpm.gpd.cell.DefaultGpdCell;
import org.netbpm.gpd.cell.StartCell;
import org.netbpm.gpd.exception.InvalidModelException;
import org.netbpm.gpd.model.ActivityStateVO;
import org.netbpm.gpd.model.FieldVO;
import org.netbpm.gpd.model.FormatterVO;
import org.netbpm.gpd.model.ParameterVO;
import org.netbpm.gpd.model.StartStateVO;
import org.netbpm.gpd.model.StateVO;

public class NetBPMWebFileFormatXML extends AbstractNetBPMFileFormatXML {
	private StringBuffer out=null;
	private String graphName="graph.jpg";
        private double scale=2.0;

	public NetBPMWebFileFormatXML() { super(); }
        /**
	 * @Constructor of the NetBPMWebFileFormat class
	 * @param name of the associated graphic
	 */
        public NetBPMWebFileFormatXML(String name) {
            super();
            this.graphName = name;
        }
        
        /**
	 * @see org.netbpm.gpd.io.GraphModelFileFormat#getFileExtension()
	 */
	public String getFileExtension() {
		return "xml";
	}

	/**
	 * Writes the graph as NetBPM file
	 * @see org.netbpm.gpd.io.GraphModelFileFormat#write(java.net.URL, java.util.Hashtable, org.jgraph.JGraph, org.jgraph.graph.GraphModel)
	 */
	public void write(
		URL file,
		Hashtable properties,
		GpdGraph gpGraph,
		GraphModel graphModel)
		throws Exception {
		
		init(gpGraph);

		OutputStream out = null;
		out = new FileOutputStream(file.getFile());
		out = new BufferedOutputStream(out);
		out.write(toString(gpGraph,graphName).getBytes());
		out.flush();
		out.close();

	}

	public String toString(JGraph graph) throws InvalidModelException {
            return toString(graph,"graph.jpg");
        }
	public String toString(JGraph graph, String graphName) throws InvalidModelException {
		//init
		scale=graph.getScale();
        out=new StringBuffer();
		
		out.append("<?xml version=\"1.0\"?>\n");

        Dimension d = graph.getCellBounds(graph.getRoots()).getSize();
        d.setSize( scale*d.width+10 , scale*d.height+10 );

        out.append("<web-interface>\n");
		out.append("  <!-- =================================== -->\n");
		out.append("  <!-- == PROCESS DEFINITION PROPERTIES == -->\n");
		out.append("  <!-- =================================== -->\n");
		out.append("  <image name=\"web/"+graphName+"\" mime-type=\"image/jpg\" width=\""+d.width+"\" height=\""+d.height+"\" />\n");
		out.append("  <!-- ================ -->\n");
		out.append("  <!-- == ACTIVITIES == -->\n");
		out.append("  <!-- ================ -->\n");
		int max = getGraphModel().getRootCount();
		for (int i = 0; i < max; i++) {
			Object cell = getGraphModel().getRootAt(i);
			if (cell instanceof StartCell){
				writeStartInfo((StartCell)cell);
			} else if (cell instanceof ActivityCell){
				writeActivityInfo((ActivityCell)cell);
			}
		}
		out.append("</web-interface>\n");
		return out.toString();
	}

	/**
	 * @param cell
	 */
	private void writeActivityInfo(ActivityCell cell) {
		ActivityStateVO model = cell.getModel();
		List fieldList = model.getFieldList();
		writeActivityStartInfo(cell, model.getName(), fieldList);
	}

	private void writeActivityStartInfo(
            Object cell, String name, List fieldList) {
                Rectangle offset = graph.getCellBounds(graph.getRoots());
                Rectangle cellBound = graph.getCellBounds(cell);

                out.append("  <activity-state name = \""+name+"\">"+"\n");
		out.append("		<image-coordinates>\n");
		out.append("			<x1>"+getNumber(cellBound.getMinX()-offset.x)+"</x1>\n");
		out.append("			<y1>"+getNumber(cellBound.getMinY()-offset.y)+"</y1>\n");
		out.append("			<x2>"+getNumber(cellBound.getMaxX()-offset.x, 10)+"</x2>\n");
		out.append("			<y2>"+getNumber(cellBound.getMaxY()-offset.y, 10)+"</y2>\n");
		out.append("		</image-coordinates>\n");

                writeFields(fieldList);
		out.append("  </activity-state>\n");
	}

	/**
	 * @param d the double number
         * @return a string that rapresent the scaled integer number
	 */
	private String getNumber(double d) { return getNumber(d, 0); }
	/**
	 * @param d the double number
         * @param increase the value to add at 'd' number
	 * @return a string that rapresent the scaled integer number
	 */
	private String getNumber(double d, int increase) {
		String number=new Double( scale * d + increase ).toString(); 
                //scale of graphic graph.getScale(); increase points around the shape
		return number.substring(0,number.indexOf("."));
	}

	private void writeFields(List fieldList) {
		Iterator it=fieldList.iterator();
		while (it.hasNext()){
			FieldVO field= (FieldVO)it.next();
			if (field.getFormatter()!=null){
				out.append("		<field attribute = \""+field.getAttribute()+ "\">\n");
				out.append("		  <name>"+field.getName()+"</name>\n");
				out.append("		  <description>"+field.getDescription()+"</description>\n");
				out.append("		  <htmlformatter class=\"" +field.getFormatter().getFormaterclass()+"\">\n");
				Iterator parameterIt = field.getFormatter().getParameterList().iterator();
				while(parameterIt.hasNext()){
					ParameterVO parameter = (ParameterVO)parameterIt.next();
					out.append("      <parameter name=\""+parameter.getName()+"\" >"+parameter.getValue()+"</parameter>\n");
				}
				out.append("		  </htmlformatter>\n");
				out.append("		</field>\n" );
			}
		}
	}

	/**
	 * @param cell
	 */
	private void writeStartInfo(StartCell cell) {
		StartStateVO model = cell.getModel();
		List fieldList = model.getFieldList();
		writeActivityStartInfo(cell, model.getName(), fieldList);
	}

	/**
	/**
	 * @see org.netbpm.gpd.io.GraphModelFileFormat#read(java.net.URL, java.util.Hashtable, org.jgraph.JGraph)
	 */
	public GraphModel read(URL file, Hashtable properties, GpdGraph gpGraph)
		throws Exception {
			SAXBuilder builder = new SAXBuilder();
			builder.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
			Document doc = builder.build(file);

			init(gpGraph);
			createActivityState(gpGraph, doc);
			
			return graph.getModel();
	}

	private void createActivityState(GpdGraph gpGraph, Document doc)
		throws JDOMException, InvalidModelException {
		XPath activityPath = XPath.newInstance("/web-interface//activity-state");
		List activityStateList = activityPath.selectNodes(doc);
		Iterator it=activityStateList.iterator();
		while (it.hasNext()){
			Element activityState = (Element) it.next();
			createActivityStateDetail(activityState);
		}
	}


	private void createActivityStateDetail(Element activityState) throws InvalidModelException {
		String name=activityState.getAttribute("name").getValue();
		if (getCellByName(name) instanceof ActivityCell){
			ActivityCell cell=(ActivityCell)getCellByName(name);
			if (cell==null){
				cell=new ActivityCell();
				cell.getModel().setName(activityState.getAttribute("name").getValue());
				insert(new Point(10, 10), cell,new Dimension(150,25));
			}
		}
		DefaultGpdCell tempcell = getCellByName(name);
		StateVO model=null;
		if (tempcell instanceof ActivityCell){
			model = ((ActivityCell)tempcell).getModel();
		} else if (tempcell instanceof StartCell){
			model = ((StartCell)tempcell).getModel();
		} 
		createFields(model,activityState);
	}

	/**
	 * @param model
	 * @param activityState
	 */
	private void createFields(StateVO model, Element activityState) {
		List resultList=model.getFieldList();
		List fieldList=activityState.getChildren("field");
		Iterator it = fieldList.iterator();
		while (it.hasNext()){
			Element filed=(Element) it.next();
			String attribute=filed.getAttribute("attribute").getValue();
			FieldVO fieldvo = new FieldVO();
			fieldvo.setAttribute(attribute);
			
			if (resultList.indexOf(fieldvo)!=-1){
				fieldvo=(FieldVO)resultList.get(resultList.indexOf(fieldvo));
			} else {
				resultList.add(fieldvo);
			}

			Element nameElement = filed.getChild("name");
			if (nameElement!=null){
				fieldvo.setName(nameElement.getValue());
			} else if (filed.getAttribute("name")!=null){
				fieldvo.setName(filed.getAttribute("name").getValue());
			}
			
			Element descriptionElement = filed.getChild("description");
			if (descriptionElement!=null){
				fieldvo.setDescription(descriptionElement.getValue());
			}

			Element htmlformatterElement = filed.getChild("htmlformatter");
			if (htmlformatterElement!=null){
				FormatterVO formatter=new FormatterVO();
				formatter.setFormaterclass(htmlformatterElement.getAttributeValue("class"));
				fieldvo.setFormatter(formatter);
				
				formatter.setParameterList(createParameters(htmlformatterElement));
			}
		}
	}

}
