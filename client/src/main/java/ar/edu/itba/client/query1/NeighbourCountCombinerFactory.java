package ar.edu.itba.client.query1;

import com.hazelcast.mapreduce.Combiner;
import com.hazelcast.mapreduce.CombinerFactory;

public class NeighbourCountCombinerFactory implements CombinerFactory<String, Long, Long> {
    @Override
    public Combiner<Long, Long> newCombiner(String s) {
        return new NeighbourCountCombiner();
    }

    private class NeighbourCountCombiner extends Combiner<Long, Long> {
        private long sum = 0;

        @Override
        public void combine(Long num) {
            sum += num;
        }

        @Override
        public Long finalizeChunk() {
            return sum;
        }

        @Override
        public void reset() {
            sum = 0;
        }
    }
}
