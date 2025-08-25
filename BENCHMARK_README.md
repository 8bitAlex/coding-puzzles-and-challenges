# Benchmarking Setup

This repository uses JMH (Java Microbenchmark Harness) for performance testing and benchmarking.

## Quick Start

### Run a benchmark:
```bash
./benchmark.sh MaxAvgSubarrayBenchmark
```

### Run all benchmarks:
```bash
cd puzzles
./gradlew jmh
```

### Quick benchmark with console output:
```bash
./benchmark.sh
```

## Results

Benchmark results are saved in the `benchmark-results/` directory with timestamps:
- JSON format: Detailed JMH results
- Summary files: Human-readable summaries

## Configuration

JMH settings in `puzzles/build.gradle`:
- Warmup iterations: 3
- Measurement iterations: 5
- Fork count: 1
- Output format: JSON

## Best Practices

- Run benchmarks on a quiet system
- Close other applications
- Run multiple times to ensure consistency
- Compare results across different hardware/Java versions
- Use the baseline benchmark to measure overhead

## Example Output

```
Benchmark Results - 20241201_143022
================================

Results from: jmh-result-20241201-143022.json
----------------------------------------
MaxAvgSubarrayBenchmark.maxAvgSubarray | 100 | 10 | 0.123 us/op
MaxAvgSubarrayBenchmark.maxAvgSubarray | 1000 | 10 | 1.456 us/op
MaxAvgSubarrayBenchmark.maxAvgSubarray | 10000 | 10 | 14.789 us/op
```
