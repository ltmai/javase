Using log4j2 in Java project
---

Configuration file log4j2.xml should be found in classpath or provided as a parameter.

# To run the output executable:

1. Run Maven goal exec:java

```batch
mvn exec:java [-Dlog4j.configurationFile=.\log4j2.xml]
```
2. Run executable directly

See script run.cmd:
```batch
java -jar target\example.log4j.jar [-Dlog4j.configurationFile=.\log4j2.xml]
```

This is possible because we use 2 Maven plugins (see pom.xml) to copy the dependencies to `target/lib` and
to set the dependency class path prefix `lib/` in MANIFEST.MF.