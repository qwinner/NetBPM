package org.netbpm.gpd.model;

import java.io.Serializable;

public abstract class AbstractVO implements Serializable{

	public String getShortClassname(String classname){
		if (classname!=null){
			int index=classname.lastIndexOf(".");
			if (index>0){
				return classname.substring(index+1);
			}
		}
		return classname;
	}
}
