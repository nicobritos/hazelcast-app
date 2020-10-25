package ar.edu.itba.client.mapreduce.reducers;

import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

import java.util.concurrent.atomic.AtomicLong;

public class NeighbourCountReducerFactory implements ReducerFactory<String, Long, Long> {

    @Override
    public Reducer<Long, Long> newReducer(String key) {
        return new NeighbourCountReducer();
    }

    private class NeighbourCountReducer extends Reducer<Long, Long>{
        private AtomicLong sum;

        @Override
        public void beginReduce() {
            sum = new AtomicLong(0);
        }

        @Override
        public void reduce(Long num) {
            sum.addAndGet(num);
        }

        @Override
        public Long finalizeReduce() {
            return sum.get();
        }
    }
}
