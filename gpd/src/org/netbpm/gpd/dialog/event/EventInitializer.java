/*
 * $Source: /cvsroot-fuse/netbpm/gpd/src/org/netbpm/gpd/dialog/event/EventInitializer.java,v $
 * $Revision: 1.1 $
 * $Date: 2006/02/21 19:31:08 $
 *
 *
 * EventInitializer 1.0
 * See http://www.zookitec.com for the latest 'official' version.
 *
 * Copyright (c) 2001 Zooki Technologies. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. The name Zooki Technologies may not be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *    For written permission, please e-mail support@zookitec.com
 *
 * THIS SOFTWARE IS PROVIDED BY ZOOKI TECHNOLOGIES "AS IS" AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL ZOOKI TECHNOLOGIES BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *
 *
 */
package org.netbpm.gpd.dialog.event;

import java.awt.Component;
import java.awt.Container;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.EventListener;
import java.util.EventObject;
import java.util.Hashtable;

import org.netbpm.gpd.ExceptionHandler;
import org.netbpm.gpd.dialog.controller.Controller;

/**
 * EventInitializer is a utility class to automatically create and add event listeners to components
 * to call event handler methods in the container. All you need to do is name your event handler methods
 * as described below and call <CODE>EventInitializer.addEventListeners(this)</CODE> for each container.
 *
 * <BR><BR>
 * The following code shows a typical way to handle events without EventInitializer.
 * A trivial anonymous inner class is created to implement each required event listener
 * for each component; this class simply calls another method to actually handle the event.
 * In a typical application, this results in lots of unnecessary classes being implemented.
 *
 * <PRE>
 * private JButton button1 = new JButton("Button 1");
 * private JButton button2 = new JButton("Button 2");
 *
 * public void initUI() {
 *     add(button1);
 *     add(button2);
 *
 *     button1.addActionListener(new ActionListener() {
 *         public void actionPerformed(ActionEvent e) {
 *             button1_actionPerformed(e);
 *         }
 *     });
 *
 *     button2.addActionListener(new ActionListener() {
 *         public void actionPerformed(ActionEvent e) {
 *             button2_actionPerformed(e);
 *         }
 *     });
 * }
 *
 * private void button1_actionPerformed(ActionEvent e) {
 *    // code to do something when button1 is clicked.
 * }
 *
 * private void button2_actionPerformed(ActionEvent e) {
 *    // code to do something when button2 is clicked.
 * }
 *
 * </PRE>
 *
 * By using EventInitializer, you can replace all the code to create the anonymous inner classes
 * and add them to the components with a single call to the <CODE>addEventListeners</CODE>
 * method as follows:
 *
 * <PRE>
 * private JButton button1 = new JButton("Button 1");
 * private JButton button2 = new JButton("Button 2");
 *
 * public void initUI() {
 *     add(button1);
 *     add(button2);
 *     EventInitializer.addEventListeners(this);
 * }
 *
 * private void button1_actionPerformed(ActionEvent e) {
 *    // code to do something when button1 is clicked.
 * }
 *
 * private void button2_actionPerformed(ActionEvent e) {
 *    // code to do something when button2 is clicked.
 * }
 *
 * </PRE>
 *
 */
public class EventInitializer {

    /**
     * Set true to write debug message to System.err if a component field
     * or handler methods is not public and cannot be made accessible due
     * to security restrictions.
     */
    public static boolean DEBUG = false;

    /**
     * Maps each Component subclass to an array of add???Listener methods for that component.
     * This is just for performance, see getAddListenerMethods method.
     */
    private static Hashtable methodsCache = new Hashtable();

    private EventInitializer() {
    }


    /**
     * Adds event listeners to each component in the specified container,
     * and to the container itself, to invoke the event handler methods defined by the container.
     * <BR>
     * The event listener and handler methods are found using reflection and dynamic
     * proxy classes are created to implement each listener and execute the handler methods
     * as appropriate.<BR><BR>
     *
     * The names of handler methods for events on the container have the format:<BR
     * <CODE>this_<I>method</I></CODE><BR>
     * where <CODE><I>method</I></CODE> is the name of an EventListener method.
     * <BR><BR>
     *
     * The names of handler methods for events on a component have the format:<BR>
     * <CODE><I>component</I>_<I>method</I></CODE><BR>
     * where <CODE><I>component</I></CODE> is the identifier of the component field in the container
     * and <CODE><I>method</I></CODE> is the name of an EventListener method.
     * <BR><BR>
     *
     * Handler methods are expected to take a single parameter which
     * is a subclass of EventObject.<BR><BR>
     *
     * Due to the way handler methods are identified by name and parameter type,
     * if different methods in different EventListener interfaces for a particular component have the same
     * name and parameter type, the corresponding handler method will be called for
     * each of them.
     * Is this the case for any of the standard EventListeners? Is this a problem?<BR><BR>
     *
     * <B>Handler Method Examples</B><BR><BR>
     * The method <CODE>button1_actionPerformed(ActionEvent e)</CODE> will be invoked by
     * an ActionListener added to <CODE>button1</CODE>.<BR>
     *
     * The method <CODE>this_windowClosing(WindowEvent e)</CODE> will be invoked by a
     * WindowListener added to the container.<BR><BR>
     *
     * Note: If the security restrictions for your application or applet prevent access to
     * private fields and methods using Reflection, you will have to make the component
     * fields and handler methods public for this method to work as desired. Inaccessible fields
     * and methods will be ignored (See DEBUG flag).
     *
     */
    public static void addEventListeners(Container container,Controller controller) {

        Field [] compFields = getComponentFields(container.getClass());

        //add event listeners for the container itself.
        addEventListeners(container, "this", container,controller);

        //add event listener for each component field
        for (int c = 0; c < compFields.length; c++) {
            try {
                Component component = (Component)compFields[c].get(container);
                addEventListeners(container, compFields[c].getName(), component,controller);
            } catch (IllegalAccessException e) {
				ExceptionHandler.getInstance().handleException(e);
            }
        }
    }




    /**
     * Adds event listeners to the specified component to call handler methods in the container.
     * The component may be the container itself.
     */
    private static void addEventListeners(Container container, String identifier, Component component,Controller controller) {
        Method [] addMethods; //add listener methods for a particular component
        Hashtable handlerMethods; //handler methods for a particular component
        Hashtable handlerMap = new Hashtable(); //map listener method to handler method

//        handlerMethods = getHandlerMethods(container.getClass(), identifier);
		handlerMethods = getHandlerMethods(controller.getClass(), identifier);
        if (handlerMethods.size() == 0) {
            //no handler methods, so nothing to do
            return;
        }
        addMethods = getAddListenerMethods(component.getClass());
        for (int m = 0; m < addMethods.length; m++) {
            Class listenerClass = addMethods[m].getParameterTypes()[0];
            Method [] listenerMethods = listenerClass.getMethods();
            //for each listener method
            for (int l = 0; l < listenerMethods.length; l++) {
                Class [] params = listenerMethods[l].getParameterTypes();
                if (params.length == 1 && EventObject.class.isAssignableFrom(params[0])) {
                    Method handlerMethod;
                    String key = createMethodKey(listenerMethods[l].getName(), params[0]);
                    if ((handlerMethod = (Method)handlerMethods.get(key)) != null) {
                        handlerMap.put(listenerMethods[l], handlerMethod);
                    }
                }
            }
            //if any handler methods exist for this event listener
            if (handlerMap.size() > 0) {
                EventListener listener;
                ClassLoader cl = listenerClass.getClassLoader();
				MethodMapInvoker invoker = new MethodMapInvoker(controller, handlerMap);

                //create the event listener
                listener = (EventListener)Proxy.newProxyInstance(cl,
                                            new Class[]{listenerClass}, invoker);
                //add the event listener to the component
                try {
                    addMethods[m].invoke(component, new Object[]{listener});
                } catch (IllegalAccessException e) {
                    //should not be thrown because addMethods only container public methods.
					ExceptionHandler.getInstance().handleException(e);
                } catch (InvocationTargetException e) {
                	ExceptionHandler.getInstance().handleException(e);
                }

                handlerMap = new Hashtable();
            }
        }

    }

    /**
     * This class invokes the corresponding handler method on the target
     * for each event listener interface method based on the method mapping.
     */
    static class MethodMapInvoker implements InvocationHandler, Serializable {

        private Object target;
        private Hashtable methodMap;

        /**
         * Constructs a MethodMapInvoker.
         *
         * @param target the target container.
         * @param methodMap a hashtable mapping event listener interface methods to handler
         * methods in the target.
         */
        public MethodMapInvoker(Object target, Hashtable methodMap) {
            this.target = target;
            this.methodMap = methodMap;
        }

        /**
         * Invokes the handler methods for the specified event listener method.
         */
        public Object invoke(Object proxy, Method method,  Object[] args) throws Throwable {
            Method handlerMethod = (Method)methodMap.get(method);
            if (handlerMethod != null) {
                return handlerMethod.invoke(target, args);
            } else {
                return null;
            }
        }

    }



   /**
     * Gets an array of fields from the specified container class which are an instance of component.
     */
    private static Field [] getComponentFields(Class containerClass) {
        Field [] fields = containerClass.getDeclaredFields();
        Field [] compFields;
        int count = 0;
        for (int i = 0; i < fields.length; i++) {
            if (Component.class.isAssignableFrom(fields[i].getType())) {
                //shuffle up component field
                fields[count] = fields[i];
                try {
                    if (Modifier.isPublic(fields[i].getModifiers())) {
                        count++;
                    } else {
                        fields[i].setAccessible(true);
                        count++;
                    }
                } catch (SecurityException e) {
                    if (DEBUG)
                        System.err.println("EventInitializer : field \"" + fields[i] + "\" is not accessible");
                }
            }
        }
        compFields = new Field[count];
        System.arraycopy(fields, 0, compFields, 0, count);
        return compFields;
    }


    /**
     * Gets an array of methods from the specified class where the
     * method name starts with "add" and the
     * method has a single parameter whose type is a subclass of EventListener.
     *
     */
    private static Method [] getAddListenerMethods(Class componentClass) {
        Method [] methods;
        Method [] addMethods;
        int count = 0;

        if ((addMethods = getMethods(componentClass)) == null) {
            methods = componentClass.getMethods();
            for (int i = 0; i < methods.length; i++) {
                if (methods[i].getName().startsWith("add")) {
                    Class [] params = methods[i].getParameterTypes();
                    if (params.length == 1 && EventListener.class.isAssignableFrom(params[0])) {
                        //shuffle up add method
                         methods[count] = methods[i];
                        count++;
                    }
                }
            }
            addMethods = new Method[count];
            System.arraycopy(methods, 0, addMethods, 0, count);
            putMethods(componentClass, addMethods);
        }
        return addMethods;
    }

    /**
     * Put the add???Listener method array for the specified class into cache.
     */
    private static synchronized void putMethods(Class c, Method [] methods) {
        methodsCache.put(c, methods);
    }


    /**
     * Get the add???Listener method array for the specified class from cache.
     */
    private static synchronized Method [] getMethods(Class c) {
        return (Method [])methodsCache.get(c);
    }



    /**
     * Creates a key for a method with the specified name and parameter
     * for use in the add???Listener method cache.
     */
    private static String createMethodKey(String name, Class param) {
        StringBuffer sb = new StringBuffer();
        sb.append(name).append("(").append(param.getName());
        return sb.toString();
    }

    /**
     * Gets a hashtable of methods from the specified class where the
     * method name starts with <I>componentIdentifier</I>_ and the
     * method has a single parameter whose type is a subclass of EventObject.
     *
     * This is used to get event handler methods in the container.
     *
     * The createMethodKey methods is used to create the hashtable key for each method.
     */
    private static Hashtable getHandlerMethods(Class containerClass, String componentIdentifier) {
        Method [] methods = containerClass.getDeclaredMethods();
        Hashtable handlerMethods = new Hashtable();
        String prefix = componentIdentifier + "_";
        int prefixLength = prefix.length();

        for (int i = 0; i < methods.length; i++) {
            String methodName = methods[i].getName();
            if (methodName.startsWith(prefix)) {
                Class [] params = methods[i].getParameterTypes();
                if (params.length == 1 && EventObject.class.isAssignableFrom(params[0])) {
                    String key = createMethodKey(methodName.substring(prefixLength),
                                                        params[0]);
                    try {
                        if (Modifier.isPublic(methods[i].getModifiers())) {
                            handlerMethods.put(key, methods[i]);
                        } else {
                            methods[i].setAccessible(true);
                            handlerMethods.put(key, methods[i]);
                        }
                    } catch (SecurityException e) {
                        if (DEBUG)
                            System.err.println("EventInitializer : method \"" + methods[i] + "\" is not accessible");
                    }
                }
            }
        }
        return handlerMethods;
    }

}