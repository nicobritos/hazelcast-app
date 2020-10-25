package ar.edu.itba.client.queries;

import ar.edu.itba.api.Tree;
import ar.edu.itba.api.queryResults.Query2Result;
import ar.edu.itba.api.queryResults.Query3Result;
import ar.edu.itba.client.mapreduce.collators.MinQuantityCollator;
import ar.edu.itba.client.mapreduce.collators.TopNCollator;
import ar.edu.itba.client.mapreduce.combiners.StringCountCombinerFactory;
import ar.edu.itba.client.mapreduce.mappers.SpeciesDiameterMapper;
import ar.edu.itba.client.mapreduce.mappers.StreetCountMapper;
import ar.edu.itba.client.mapreduce.reducers.AverageReducerFactory;
import ar.edu.itba.client.mapreduce.reducers.NeighbourMaxStreetReducerFactory;
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

public class Query3 {
    private final ICompletableFuture<Map<String, Double>> result;

    public Query3 (long qtyToShow) {
        final ClientConfig ccfg = new ClientConfig();
        final HazelcastInstance hz = HazelcastClient.newHazelcastClient(ccfg);
        JobTracker jobTracker = hz.getJobTracker("biggest-tree-species");
        final IList<Tree> trees = hz.getList("tree-list");
        final KeyValueSource<String, Tree> source = KeyValueSource.fromList(trees);

        final Job<String, Tree> job = jobTracker.newJob(source);
        result = job
                .mapper(new SpeciesDiameterMapper())
                .reducer(new AverageReducerFactory())
                .submit(new TopNCollator(qtyToShow));
    }

    public List<Query3Result> getResult() throws ExecutionException, InterruptedException {
        // Wait and retrieve the result
        Map<String, Double> ans = result.get();

        List<Query3Result> list = Query3Result.listFrom(ans);
        Collections.sort(list);
        return list;
    }
}
