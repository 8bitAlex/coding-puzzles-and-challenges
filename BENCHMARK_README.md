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

## Results

Benchmark results are saved in the `benchmark-results/` directory with timestamps:
- JSON format: Detailed JMH results
- Summary files: Human-readable summaries

## Benchmark Structure

### MaxAvgSubarrayBenchmark
Tests the `MaxAvgSubarray.solve()` method with different parameters:
- **Array sizes**: 100, 1,000, 10,000, 100,000 elements
- **Window sizes (k)**: 10, 100, 1,000
- **Metrics**: Average execution time in microseconds

## Configuration

JMH settings in `puzzles/build.gradle`:
- Warmup iterations: 3
- Measurement iterations: 5
- Fork count: 1
- Output format: JSON

## Adding New Benchmarks

1. Create a new benchmark class in `puzzles/src/jmh/java/`
2. Extend the existing benchmark pattern
3. Add to the benchmark script if needed

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
