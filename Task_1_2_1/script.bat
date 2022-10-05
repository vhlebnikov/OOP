javadoc -d .\make\ -sourcepath .\src\main\java -subpackages ru.nsu.khlebnikov

javac -d .\make\bin\ -sourcepath .\src\main\java .\src\main\java\ru\nsu\khlebnikov\Stack.java

mkdir .\make\jar
jar cf .\make\jar\Stack.jar -C .\make\bin .

java -classpath .\make\jar\HeapSort.jar ru.nsu.khlebnikov.Stack