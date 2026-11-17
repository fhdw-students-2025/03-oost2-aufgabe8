# Banking (OOST2, Aufgabe 8)

Several threads deposit into and withdraw from a shared bank account at the same time.
Requires JDK 21+ and Maven.

```sh
mvn verify                                        # build, check style, run tests
java -cp target/classes app.MainUnsynchronized    # final balance is usually wrong
java -cp target/classes app.MainSynchronized      # final balance is always correct
```

Optional arguments: `[threadPairs] [repetitionsPerThread] [rounds]` (default `2 100000 5`).
