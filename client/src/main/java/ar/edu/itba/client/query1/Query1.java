package ar.edu.itba.client.query1;

import ar.edu.itba.api.Tree;
import ar.edu.itba.api.query1.Query1Result;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ICompletableFuture;
import com.hazelcast.core.IList;
import com.hazelcast.core.IMap;
import com.hazelcast.mapreduce.Job;
import com.hazelcast.mapreduce.JobTracker;
import com.hazelcast.mapreduce.KeyValueSource;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Query1 {
    private final ICompletableFuture<Map<String, Double>> result;

    public Query1() {
        final ClientConfig ccfg = new ClientConfig();
        final HazelcastInstance hz = HazelcastClient.newHazelcastClient(ccfg);
        JobTracker jobTracker = hz.getJobTracker("trees-per-person");
        final IMap<String, Long> populations = hz.getMap("populations-map");
        final IList<Tree> trees = hz.getList("tree-list");
        final KeyValueSource<String, Tree> source = KeyValueSource.fromList(trees);

        final Job<String, Tree> job = jobTracker.newJob(source);
        result = job
                .mapper(new TreeNeighbourMapper())
                .combiner(new NeighbourCountCombinerFactory())
                .reducer(new NeighbourCountReducerFactory())
                .submit(new TreesPerPersonCollator(populations));
    }

    public List<Query1Result> getResult() throws ExecutionException, InterruptedException {
        // Wait and retrieve the result
        Map<String, Double> ans = result.get();

        List<Query1Result> list = Query1Result.listFrom(ans);
        Collections.sort(list);
        return list;
    }
}
