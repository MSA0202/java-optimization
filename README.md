# Pre-requisites
- Need to install Maven onto your system ( see maven-quick-start )
- You can then clone this repo and the maven configuration should be picked up from pom.xml
- navigate to same folder as pom.xml and run `mvn clean install`
- You can then run the Java file (through Intellij run/debug plugins) 
  - or manually:
    - Then, navigate to the generated target folder ( cd target )
    - Then run the command, `java -cp <jarname.jar> <packagename>.<javaclassname>`
    - Example: `java -cp java-optimization-code-1.0-SNAPSHOT.jar org.example.section<number>.<MainClass>`