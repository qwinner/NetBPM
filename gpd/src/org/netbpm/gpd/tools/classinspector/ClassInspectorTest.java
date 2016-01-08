package org.netbpm.gpd.tools.classinspector;

import java.util.Collection;
import java.util.Iterator;

import org.jbpm.workflow.delegation.ActionHandler;

import junit.framework.TestCase;
import junit.textui.TestRunner;


public class ClassInspectorTest extends TestCase {
	/**
	 * Constructor for ClassInspectorTest.
	 * @param name
	 */
	public ClassInspectorTest(String name) {
		super(name);
	}

	public static void main(String[] args) {
		TestRunner.run(ClassInspectorTest.class);
	}

	public void testGetSubClasses() throws Exception {
		long t1 = System.currentTimeMillis();
		Collection classes =
			ClassInspector.getSubClasses("de.dbsystems.einkauf",
										 ActionHandler.class);
		long t2 = System.currentTimeMillis();

		for (Iterator j = classes.iterator(); j.hasNext();) {
			System.out.println(j.next());
		}

		System.out.println("==> " + (t2 - t1) + " ms");
	}
}
