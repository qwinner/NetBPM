##################################
## getting started instructions ##
##################################
If you are using the source version you can run gpd by typing "ant run", otherwise start gpd.bat
in the folder bin.

##################
## Requirements ##
##################
GPD requires the JDK 1.4.

#############
## license ##
#############
The GPD software is distributed under the terms of the Apache Software License.
The complete text of this license can be found in 'doc/license/apache.license.txt'.

#############
## example ##
#############
An example is located under data\example.

###################
## configuration ##
###################
GPD searches for formatters, actions, decisions, assignments and serializers in the classpath.
For performance reasons GPD searches only in predefined packages. To add new packages,
change the file config.xml. If you are using the source version the file is located in 
the folder data/conf, otherwise it is located in gpd.jar

##############
## feedback ##
##############
For feedback, please use the user-forum : http://sourceforge.net/forum/forum.php?forum_id=504040
Or send an email to philipp@netbpm.org .
