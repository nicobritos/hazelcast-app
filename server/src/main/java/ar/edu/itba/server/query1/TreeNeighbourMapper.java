package ar.edu.itba.server.query1;

import ar.edu.itba.api.Tree;
import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

public class TreeNeighbourMapper implements Mapper<String, Tree, String, Long> {
    @Override
    public void map(String key, Tree tree, Context<String, Long> context) {
        context.emit(tree.getNeighbourhood(),1L);
    }
}
