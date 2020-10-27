package ar.edu.itba.client.queries;

import ar.edu.itba.api.Tree;
import ar.edu.itba.api.queryResults.Query2Result;
import ar.edu.itba.api.mapreduce.collators.MinQuantityCollator;
import ar.edu.itba.api.mapreduce.combiners.StringCountCombinerFactory;
import ar.edu.itba.api.mapreduce.mappers.StreetCountMapper;
import ar.edu.itba.api.mapreduce.reducers.NeighbourMaxStreetReducerFactory;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ICompletableFuture;
import com.hazelcast.core.IList;
import com.hazelcast.mapreduce.Job;
import com.hazelcast.mapreduce.JobTracker;
import com.hazelcast.mapreduce.KeyValueSource;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Query2 {
    private final ICompletableFuture<Map<String, Long>> result;

    public Query2(HazelcastInstance hz,long min) {
        JobTracker jobTracker = hz.getJobTracker("most-trees-per-neighbourhood");
        final IList<Tree> trees = hz.getList("tree-list");
        final KeyValueSource<String, Tree> source = KeyValueSource.fromList(trees);

        final Job<String, Tree> job = jobTracker.newJob(source);
        result = job
                .mapper(new StreetCountMapper())
                .combiner(new StringCountCombinerFactory())
                .reducer(new NeighbourMaxStreetReducerFactory())
                .submit(new MinQuantityCollator(min));
    }

    public List<Query2Result> getResult() throws ExecutionException, InterruptedException {
        // Wait and retrieve the result
        Map<String, Long> ans = result.get();

        List<Query2Result> list = Query2Result.listFrom(ans);
        Collections.sort(list);
        return list;
    }
}
