package ar.edu.itba.server.query1;

import ar.edu.itba.api.Tree;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ICompletableFuture;
import com.hazelcast.core.IList;
import com.hazelcast.core.IMap;
import com.hazelcast.mapreduce.Job;
import com.hazelcast.mapreduce.JobTracker;
import com.hazelcast.mapreduce.KeyValueSource;

import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Query1 {
    private ICompletableFuture<Map<String, Double>> result;

    public Query1() throws ExecutionException, InterruptedException {
        final ClientConfig ccfg = new ClientConfig();
        final HazelcastInstance hz = HazelcastClient.newHazelcastClient(ccfg);
        JobTracker jobTracker = hz.getJobTracker("trees-per-person");
        final IMap<String, Long> populations = hz.getMap("populations");
        final IList<Tree> trees = hz.getList("tree-list");
        final KeyValueSource<String, Tree> source = KeyValueSource.fromList(trees);

        final Job<String, Tree> job = jobTracker.newJob(source);
        result = job
                .mapper(new TreeNeighbourMapper())
                .combiner(new NeighbourCountCombinerFactory())
                .reducer(new NeighbourCountReducerFactory())
                .submit(new TreesPerPersonCollator(populations));
    }

    public Map<String, Double> getResult() throws ExecutionException, InterruptedException {
        // Wait and retrieve the result
        return result.get();
    }
}
