To run the output executable:

1. Run Maven goal exec:java
```batch
mvn exec:java
```
2. Run executable directly
```batch
java -jar target\example.log4j.jar
```
This is possible because we use 2 Maven plugins (see pom.xml) to copy the dependencies to `target/lib` and
to set the dependency class path prefix `lib/` in MANIFEST.MF.