# Graph Analysis Toolkit

Directed and undirected graph representations with traversal and path utilities.

## Technical depth

Breadth-first and depth-first search, distance queries, and Hamiltonian-cycle validation.

## Product perspective

This project supports technical product discussions about input contracts, edge cases, acceptance criteria, and the tradeoffs visible in the implementation. It demonstrates hands-on technical study, not a production deployment.

## Repository layout

- `src/`: application source and recovered tests, with neutral Java package names.
- `pom.xml`: Maven build and test configuration.
- Root data files, where present: recovered educational fixtures.
- `SOURCE-MANIFEST.json`: hashes of the source used to prepare this independent copy.

## Running and testing

Use GraphBuilder and GraphUtils; student_graph_test.txt is a recovered input fixture.

Run `mvn test` from the repository root with Maven and a JDK (the recovery run used JDK 24). Tests use JUnit; the audit used JUnit Platform Console Standalone 1.11.4. Open the chosen source folder as a Java project, add its required libraries and JUnit to the classpath, compile `src/`, and run the recovered test classes with that folder as the working directory. Test results are scoped to the selected files and fixtures.

## Validation status

The repackaged source compiled with JDK 24. The local JUnit run passed **4 tests**, with 0 failures. Maven configuration is provided for convenience; the reported run used javac and JUnit Console directly. Passing tests do not establish exhaustive correctness or production readiness.

## Provenance and publication

Recovered from Neira Ibrahimovic's local workspace. Author names and starter-code credits are retained in the source. Repository ownership does not establish sole authorship of every file. Documentation and explicitly labeled maintenance changes were prepared with AI assistance.

Publication approved by the owner. This repository is an independent copy and has no upstream synchronization. No new license is asserted. Original files remain preserved locally. School contact details and institutional package identifiers were omitted from this portfolio copy; contributor attribution is retained.
