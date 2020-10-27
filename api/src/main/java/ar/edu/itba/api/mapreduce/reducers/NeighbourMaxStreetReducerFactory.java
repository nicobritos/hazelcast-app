package ar.edu.itba.api.mapreduce.reducers;

import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

import java.util.concurrent.atomic.AtomicLong;

public class NeighbourMaxStreetReducerFactory implements ReducerFactory<String, Long, Long> {

    @Override
    public Reducer<Long, Long> newReducer(String key) {
        return new NeighbourMaxStreetReducerFactory.NeighbourMaxReducer();
    }

    private class NeighbourMaxReducer extends Reducer<Long, Long>{
        private AtomicLong max;

        @Override
        public void beginReduce() {
            max = new AtomicLong(0);
        }

        @Override
        public void reduce(Long newResult) {
            if (max.get() < newResult){
                max.set(newResult);
            }
        }

        @Override
        public Long finalizeReduce() {
            return max.get();
        }
    }
}