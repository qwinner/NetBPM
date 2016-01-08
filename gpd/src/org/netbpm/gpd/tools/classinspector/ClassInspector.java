package org.netbpm.gpd.tools.classinspector;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

public abstract class ClassInspector {
	private static final String CLASS_SUFFIX = ".class";

	private static final Collection getAvailableClasses(String basePackage)
			throws IOException {
		Set classes = new HashSet();
		Collection classPathLibraries = getClassPathLibraries();

		for (Iterator i = classPathLibraries.iterator(); i.hasNext();) {
			String classPathLibrary = i.next().toString();
			File file = new File(classPathLibrary);

			if (file.exists()) {
				if (file.isDirectory()) {
					classes.addAll(getClassesFromDirectory(basePackage, file));
				} else {
					classes.addAll(getClassesFromZipFile(basePackage, file));
				}
			}
		}

		return classes;
	}

	private static final Collection getClassesFromDirectory(String basePackage,
			File directory) throws IOException {
		return getClassesFromDirectory(basePackage, directory
				.getCanonicalPath(), directory);
	}

	private static final Collection getClassesFromDirectory(String basePackage,
			String directory, File file) throws IOException {
		File[] files = file.listFiles();
		Set result = new HashSet();

		for (int i = 0; i < files.length; i++) {
			String name = files[i].getCanonicalPath();

			if (files[i].isDirectory()) {
				String packageName = getClassName(name.substring(directory
						.length() + 1));
				boolean goon;

				if (packageName.length() < basePackage.length()) {
					goon = packageName.equals(basePackage.substring(0,
							packageName.length()));
				} else {
					goon = basePackage.equals(packageName.substring(0,
							basePackage.length()));
				}

				if (goon) {
					result.addAll(getClassesFromDirectory(basePackage,
							directory, files[i]));
				}
			} else {
				if (isClassFile(name)) {
					String className = getClassName(name.substring(directory
							.length() + 1));

					if (className.startsWith(basePackage)) {
						result.add(className);
					}
				}
			}
		}

		return result;
	}
/*
	private static final Object getClassFromDirectory(String basePackage,
			File directory) throws IOException {
		return getClassFromDirectory(basePackage, directory.getCanonicalPath(),
				directory);
	}
	
	private static final Object getClassFromDirectory(String basePackage,
			String directory, File file) throws IOException {
		File[] files = file.listFiles();
		Set result = new HashSet();

		for (int i = 0; i < files.length; i++) {
			String name = files[i].getCanonicalPath();

			if (files[i].isDirectory()) {
				String packageName = getClassName(name.substring(directory
						.length() + 1));
				boolean goon;

				if (packageName.length() < basePackage.length()) {
					goon = packageName.equals(basePackage.substring(0,
							packageName.length()));
				} else {
					goon = basePackage.equals(packageName.substring(0,
							basePackage.length()));
				}
				if (goon) {
					return getClassFromDirectory(basePackage, directory,
							files[i]);
				}
			} else {
				if (isClassFile(name)) {
					String className = getClassName(name.substring(directory
							.length() + 1));
					if (className.startsWith(basePackage)) {
						FileInputStream fis = new FileInputStream(name);
						ObjectInputStream ois = new ObjectInputStream(fis);
						Object obj = null;
						try {
							obj = ois.readObject();
						} catch (ClassNotFoundException ce) {
							System.out.println(ce.toString());
						}
						ois.close();
						fis.close();
						return obj;
					}
				}
			}
		}
		return null;
	}
*/

	private static final Collection getClassesFromZipFile(String basePackage,
			File file) throws IOException {
		ZipFile zipFile = new ZipFile(file);
		Set result = new HashSet();
		Enumeration entriesEnum = zipFile.entries();

		while (entriesEnum.hasMoreElements()) {
			String name = ((ZipEntry) entriesEnum.nextElement()).getName();

			if (isClassFile(name)) {
				String className = getClassName(name);

				if (className.startsWith(basePackage)) {
					result.add(className);
				}
			}
		}

		return result;
	}

/* private static final Object getClassFromJarFile(String baseClass, File file)
			throws IOException {
		final int BUFFER = 1024;
		try {
			ObjectOutputStream objectClass = null;
			byte data[] = null;
			BufferedOutputStream dest = null;
			FileInputStream fis = new FileInputStream(file);
			JarInputStream jis = new JarInputStream(
					new BufferedInputStream(fis));
			JarEntry entry;
			while ((entry = jis.getNextJarEntry()) != null)
				if (baseClass.equals(entry.getName())) {
					int count;
					data = new byte[BUFFER];
					dest = new BufferedOutputStream(objectClass, 2048);
					while ((count = jis.read(data, 0, BUFFER)) != -1) {
						System.out.print(" Count:" + count);
						dest.write(data, 0, count);
					}
					System.out.print("\n");
					dest.flush();
					dest.close();
				}
			jis.close();
			fis.close();
			return objectClass;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
*/

	protected static final void getTheClass(String classPath, String classDir)
			throws IOException, NullPointerException {
		String jarPath = ServiceInspector.getServiceInspector().getJarPath(
				classPath);
		if (jarPath != null) {
			JarFile jarFile = new JarFile(jarPath);
			String classEntry = classPath.replace('.', '/').concat(".class");
			JarEntry entry = jarFile.getJarEntry(classEntry);
			if (entry == null)
				System.out.println("Can't find " + classEntry + " entry into "
						+ jarFile);
			InputStream in = new BufferedInputStream(jarFile
					.getInputStream(entry));
			File targetFile = new File(classDir + File.separator + classEntry);
			if (!targetFile.getParentFile().exists())
				targetFile.getParentFile().mkdirs();
			OutputStream out = new BufferedOutputStream(new FileOutputStream(
					targetFile));
			byte[] buffer = new byte[2048];
			int nBytes;
			while ((nBytes = in.read(buffer)) > 0)
				out.write(buffer, 0, nBytes);
			out.flush();
			out.close();
			in.close();
		}
	}

	protected static final String getJarPath(String basePackage)
			throws IOException, ZipException {
		String baseClass = basePackage.replace('.', '/').concat(".class");
		Collection classPathLibraries = getClassPathLibraries();
		for (Iterator i = classPathLibraries.iterator(); i.hasNext();) {
			File file = new File(i.next().toString());
			if (file.exists() && !file.isDirectory()) {
				if (new ZipFile(file).getEntry(baseClass) != null)
					return file.toString();
			}
		}
		System.out.println("Can't find " + basePackage + " in any JAR library");
		return null;
	}

	private static final String getClassName(String name) {
		if (name.indexOf(CLASS_SUFFIX) >= 0) {
			name = name.substring(0, name.length() - CLASS_SUFFIX.length());
		}

		name = name.replace('/', '.');
		name = name.replace('\\', '.');

		if (name.startsWith(".")) {
			name = name.substring(1);
		}

		return name;
	}

	private static final Collection getClassPathLibraries() {
		final String pathSeparator = System.getProperty("path.separator");
		final String classPath = System.getProperty("java.class.path");
		Set result = new HashSet();
		StringTokenizer s = new StringTokenizer(classPath, pathSeparator);

		while (s.hasMoreTokens()) {
			result.add(s.nextToken());
		}

		return result;
	}

	protected static final Collection getSubClasses(String basePackage,
			Class toClass) throws IOException {
		Collection availableClasses = getAvailableClasses(getClassName(basePackage));
		List result = new LinkedList();

		for (Iterator i = availableClasses.iterator(); i.hasNext();) {
			String className = i.next().toString();

			try {
				Class subClass = Class.forName(className, false, ClassLoader
						.getSystemClassLoader());

				if (subClass.isInterface()) {
					continue;
				} else if (subClass.isArray()) {
					continue;
				} else if (subClass.isPrimitive()) {
					continue;
				} else if (toClass.isAssignableFrom(subClass)) {
					result.add(subClass);
				}
			} catch (Throwable e) {
				// should not occur
				// e.printStackTrace();
			}
		}

		return result;
	}

	private static final boolean isClassFile(String name) {
		return name.endsWith(CLASS_SUFFIX) && (name.indexOf('$') < 0);
	}
}
