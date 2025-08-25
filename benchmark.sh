#!/bin/bash

# Benchmark runner script
# Usage: ./benchmark.sh [benchmark_name]

set -e

# Create results directory
RESULTS_DIR="benchmark-results"
mkdir -p "$RESULTS_DIR"

# Get timestamp for this run
TIMESTAMP=$(date +"%Y%m%d_%H%M%S")
BENCHMARK_NAME=${1:-"MaxAvgSubarrayBenchmark"}

echo "Running benchmark: $BENCHMARK_NAME"
echo "Timestamp: $TIMESTAMP"

# Run the benchmark
cd puzzles
../gradlew jmh -Pjmh.include="$BENCHMARK_NAME" --no-daemon

# Copy results to results directory
cp -r build/jmh-results/* "../$RESULTS_DIR/"

# Create a summary file
SUMMARY_FILE="../$RESULTS_DIR/summary_${TIMESTAMP}.txt"
echo "Benchmark Results - $TIMESTAMP" > "$SUMMARY_FILE"
echo "================================" >> "$SUMMARY_FILE"
echo "" >> "$SUMMARY_FILE"

# Extract key metrics from JSON results
for json_file in build/jmh-results/*.json; do
    if [ -f "$json_file" ]; then
        echo "Results from: $(basename "$json_file")" >> "$SUMMARY_FILE"
        echo "----------------------------------------" >> "$SUMMARY_FILE"
        
        # Extract benchmark name, params, and score
        jq -r '.benchmarks[] | "\(.benchmark) | \(.params.arraySize) | \(.params.k) | \(.primaryMetric.score) \(.primaryMetric.scoreUnit)"' "$json_file" >> "$SUMMARY_FILE" 2>/dev/null || echo "Could not parse JSON results" >> "$SUMMARY_FILE"
        echo "" >> "$SUMMARY_FILE"
    fi
done

echo "Benchmark completed!"
echo "Results saved to: $RESULTS_DIR/"
echo "Summary: $SUMMARY_FILE"
