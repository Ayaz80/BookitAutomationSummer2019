To run test from terminal:

mvn clean verify -q

clean -will delete target folder.
In target folder, you will find reports, compiled classes and project jar files

verify- goal from maven lyfecycle. Triggers cucumber runner class and report
-q -quite, to reduce output from maven in console.