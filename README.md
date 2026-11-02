# Banking (OOST2, Aufgabe 8)

Several threads deposit into and withdraw from a shared bank account at the same time.

Requires JDK 21+ and Maven.

## IntelliJ setup

1. **File → Open…**, select the project folder (or `pom.xml`) and open it as a Maven project.
2. Install the **CheckStyle-IDEA** plugin when IntelliJ asks for it. The project's `checkstyle.xml` is preconfigured.
3. **File → Project Structure → Project**: set the SDK to JDK 21 or newer.

## Maven

```sh
mvn test       # compile, check style, run tests
mvn verify     # full build; must pass before you push
mvn compile && java -cp target/classes app.Main   # run the demo
```
