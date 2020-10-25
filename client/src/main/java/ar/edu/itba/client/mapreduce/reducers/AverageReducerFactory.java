package ar.edu.itba.client.mapreduce.reducers;

import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

import java.util.concurrent.atomic.AtomicLong;

public class AverageReducerFactory implements ReducerFactory<String, Double, Double> {
    @Override
    public Reducer<Double, Double> newReducer(String key) {
        return new AverageReducerFactory.AverageReducer();
    }

    private class AverageReducer extends Reducer<Double, Double> {
        private AtomicLong diameterSum;
        private AtomicLong totalTrees;

        @Override
        public void beginReduce() {
            diameterSum = new AtomicLong(0);
            totalTrees = new AtomicLong(0);
        }

        @Override
        public void reduce(Double diameter) {
            diameterSum.addAndGet(Double.doubleToLongBits(diameter));
        }

        @Override
        public Double finalizeReduce() {
            return diameterSum.doubleValue() / totalTrees.get();
        }
    }
}
