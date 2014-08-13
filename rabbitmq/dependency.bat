call mvn -DoutputFile=dependency.txt dependency:resolve
call mvn -DoutputFile=dependency-tree.txt dependency:tree
call mvn -Doutput=effective-pom.xml help:effective-pom