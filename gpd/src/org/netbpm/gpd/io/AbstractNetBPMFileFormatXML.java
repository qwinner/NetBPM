package org.netbpm.gpd.io;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.jdom.Attribute;
import org.jdom.Element;
import org.netbpm.gpd.model.ParameterVO;

public abstract class AbstractNetBPMFileFormatXML extends AbstractExport {

	/**
	 * @param action
	 * @param actionVo
	 */
	protected List createParameters(Element action) {
		List resultList=new LinkedList();
		List parameterlist = action.getChildren("parameter");
		Iterator it = parameterlist.iterator();
		while (it.hasNext()){
			Element parameter = (Element)it.next();
			createParameterDetail(parameter,resultList);			
		}
		return (resultList);
	}

	/**
	 * @param parameter
	 * @param parameterlist
	 */
	private void createParameterDetail(Element parameter, List parameterlist) {
		ParameterVO parameterVo = new ParameterVO();
		Attribute name = parameter.getAttribute("name");
		parameterVo.setName(name.getValue());
		parameterVo.setValue(parameter.getValue());
		parameterlist.add(parameterVo);
	}

}
