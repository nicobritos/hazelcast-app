package ar.edu.itba.server;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Server {
    private static Logger logger = LoggerFactory.getLogger(Server.class);

    public static void main(String[] args) {
        // get configuration
        Config config = new Config();

        //create new instance of hazelcast
        HazelcastInstance hz = Hazelcast.newHazelcastInstance(config);
    }
}
