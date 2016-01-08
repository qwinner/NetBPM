LIB_DIR=../lib
cd $LIB_DIR
for x in *.jar
do 
	CLASSPATH=$CLASSPATH:$LIB_DIR/$x
done
