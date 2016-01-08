for %%i in ("..\lib\*.jar") do call addtoclasspath.bat %%i
java org.jbpm.gpd.ProcessDesigner
