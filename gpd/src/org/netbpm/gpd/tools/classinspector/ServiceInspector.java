package org.netbpm.gpd.tools.classinspector;

import java.util.Collection;
import java.util.Set;

public abstract class ServiceInspector {
	private static ServiceInspector singleton;

	public static ServiceInspector getServiceInspector() {
		if (singleton == null) {
			synchronized (ServiceInspectorImpl.class) {
				if (singleton == null) {
					singleton = new ServiceInspectorImpl();
				}
			}
		}

		return singleton;
	}

	/**
	 * Lifert einen ServiceProvider
	 *
	 * @param serviceProvider Eindeutiger Name eines ServiceProviders
	 * @return java.lang.Class
	 */
	public abstract Collection getServiceProvider(String serviceProvider);

	/**
	 * Liefert die eineutigen Namen aller verfügbaren ServiceProvider.
	 *
	 * @return java.util.Set {java.lang.String}
	 */
	public abstract Set getServiceProviders();

	/**
	 * Find the jar file of the respective classPath.
	 *
	 * @return String {java.lang.String}
	 */
	public abstract String getJarPath(String classPath);

	/**
	 * Find the Class code of the respective classPath.
	 * and save it under classDir
	 */
	public abstract void getTheClass(String classPath, String classDir);

}
