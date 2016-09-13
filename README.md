Recommender System Tester and Analyser
======================================
Requirements:
-------------
In order to compile this software you need this apps previously installed:
* Oracle Java JDK 1.7 (Java 7)
* Gradle >= 2.0
* At least a minimum RAM of 4GB

Build:
------
In order to build you can run this commands:
```bash
$ gradle build
```
The build can be found in `build/distributions`

Run
---
You can run this program using a previously built version or you can run using Gradle
```bash
$ gradle run
```

Info
----
This software uses the next Recomendation Algorithms:
* User-User KNN
* Item-Item KNN
* Slope One
* SVD Matrix Factorization

This software has custom Data Analysers (for means), and have some Unit Testing for custom components. Gradle runs the
tests in the building process, the results can be checked in `build\reports\tests\test\index.html`
