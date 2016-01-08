package org.netbpm.gpd.tools.classinspector;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import org.jbpm.workflow.delegation.ActionHandler;
import org.jbpm.workflow.delegation.HtmlFormatter;
import org.jbpm.workflow.delegation.DecisionHandler;
import org.jbpm.workflow.delegation.AssignmentHandler;
import org.jbpm.workflow.delegation.Serializer;
import org.netbpm.gpd.Configuration;
import org.netbpm.gpd.exception.ClassInspectorlException;

public final class ServiceInspectorImpl extends ServiceInspector {
	private static Map serviceProviders;

	/**
	 * Einlesen aller von de.dbsystems.einkauf.server.serviceinspector.Inspectable
	 * abgeleiteten Klassen und Aufbau einer Map.
	 *
	 * @return java.util.Map
	 * @throws de.dbsystems.einkauf.server.exception.AppException
	 */
	private static final Map readAllServiceProvider() {
		try {
			Map result = new HashMap();
			loadPackage(result,Configuration.ACTION_PACKAGE,ActionHandler.class);
			loadPackage(result,Configuration.FORMATTER_PACKAGE,HtmlFormatter.class);
			loadPackage(result,Configuration.DECISION_PACKAGE,DecisionHandler.class);
			loadPackage(result,Configuration.ASSIGNMENT_PACKAGE,AssignmentHandler.class);
			loadPackage(result,Configuration.SERIALIZER_PACKAGE,Serializer.class);

			return Collections.unmodifiableMap(result);
		} catch (Throwable e) {
			e.printStackTrace();
			throw new ClassInspectorlException ("ERROR READ SUBCLASS_NAME"+ e.getMessage());
		}
	}

	private static void loadPackage(Map result,String configPara,Class classname)
		throws IOException {
		Collection subClasses = new LinkedList();
		Object conigObject=Configuration.getInstance().getProperty(configPara);
		if (conigObject instanceof Collection){
			Iterator it=((Collection)conigObject).iterator();
			while (it.hasNext()){
				String packageLocation=(String)it.next();
				subClasses.addAll(ClassInspector.getSubClasses(packageLocation,classname));
			}
		} else if (conigObject instanceof String){
			subClasses.addAll(ClassInspector.getSubClasses((String)conigObject,classname));
		}

		result.put(configPara,subClasses);
	}

	public final Collection getServiceProvider(String serviceProvider) {
		init();
		return (Collection) serviceProviders.get(serviceProvider);
	}

	public final Set getServiceProviders() {
		init();
		return serviceProviders.keySet();
	}

	public final void getTheClass(String classPath, String classDir) {
            try{
                ClassInspector.getTheClass(classPath, classDir);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }

        private void init() {
                if (serviceProviders == null) {
			synchronized (this) {
				if (serviceProviders == null) {
					serviceProviders = readAllServiceProvider();
                                }
			}
		}
	}
        
        public String getJarPath(String classPath) {
            try{
                return ClassInspector.getJarPath(classPath);
            } catch(Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        
}
