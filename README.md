# Coding Puzzles and Challenges

![GitHub Actions Workflow Status](https://img.shields.io/github/actions/workflow/status/8bitalex/coding-puzzles-and-challenges/gradle.yml)

Welcome to my curated collection of coding problems, data structures, and algorithmic challenges.

This repository serves as both a **portfolio** and a **technical interview prep resource**.  
It contains:
- Implementations of coding puzzles and algorithm challenges
- Executable examples in **Java 17** with **Gradle**
- Unit tests (JUnit) to validate correctness
- Occasional experiments with data structures and design patterns

Thank you for your interest in my work and I hope you find some insight and maybe a bit of fun.

You can find more information on my [Personal Website](https://www.alexsalerno.dev/) and [LinkedIn](https://www.linkedin.com/in/8bitalex/).

---

## Getting Started

Import into [IntelliJ IDEA](https://www.jetbrains.com/idea/download/) (Gradle project) to explore interactively

### Requirements
- Java 17+
- Gradle
- (Optional) IntelliJ IDEA for development

## Testing
This repository uses JUnit for unit testing.

### Run all unit tests
  ```bash
  ./gradlew test 
  ```

## Benchmarks
This repository uses JMH (Java Microbenchmark Harness) for performance testing and benchmarking.

### Run all benchmarks
  ```bash
  ./gradlew jmh
  ```

_Note:_ This may take a long time to run

### Run a specific benchmark
  ```bash
  ./gradlew jmh -Djmh.include=<BenchmarkName>
  ```

### Best Practices

- Run benchmarks on a quiet system
- Close other applications
- Run multiple times to ensure consistency
- Compare results across different hardware/Java versions
- Use the baseline benchmark to measure overhead