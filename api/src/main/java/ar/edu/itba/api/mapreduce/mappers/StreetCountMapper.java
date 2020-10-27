package ar.edu.itba.api.mapreduce.mappers;

import ar.edu.itba.api.Tree;
import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

public class StreetCountMapper implements Mapper<String, Tree, String, Long> {
    @Override
    public void map(String key, Tree tree, Context<String, Long> context) {
        context.emit(tree.getNeighbourhood() + '-' + tree.getStreet(), 1L);
    }
}
