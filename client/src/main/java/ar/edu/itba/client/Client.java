package ar.edu.itba.client;

import ar.edu.itba.api.City;
import ar.edu.itba.api.Tree;
import ar.edu.itba.client.queries.Query1;
import ar.edu.itba.client.utils.CommandUtils;
import com.hazelcast.core.DistributedObject;
import com.hazelcast.core.IList;
import com.hazelcast.core.IMap;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.config.GroupConfig;
import com.hazelcast.core.HazelcastInstance;

import java.util.*;
import java.util.concurrent.ExecutionException;

import static ar.edu.itba.client.utils.CommandUtils.JAVA_OPT;

public class Client {
    private static Logger logger = LoggerFactory.getLogger(Client.class);
    //all the option/parameters/properties
    private static final String QUERY_OPT = "query";
    private static final String CITY_OPT = "city";
    private static final String NODES_ADDRS_OPT = "addresses";
    private static final String IN_PATH_OPT = "inPath";
    private static final String OUT_PATH_OPT = "outPath";
    private static final String MIN_OPT = "min";
    private static final String N_OPT = "n";
    private static final String NAME_OPT = "name";

    public static void main(String[] args) throws ExecutionException, InterruptedException, ParseException{
        //TODO: Parsear y armar la query pedida
        //query = .....

        //get properties from command line
        //Properties props = parseCommandLine(args);
        Properties props = new Properties();
        props.setProperty(NODES_ADDRS_OPT,"127.0.0.1:5701");
        props.setProperty(QUERY_OPT,"1");

        //get addresses
        String[] clientAddresses = props.getProperty(NODES_ADDRS_OPT).split(";");

        // Config and get hazelcast instance
        logger.info("tp2hazelcast Client Starting ...");
        ClientConfig cfg = new ClientConfig();
        GroupConfig groupConfig = cfg.getGroupConfig();
        groupConfig.setName("tp2-g5");
        groupConfig.setPassword("angi"); //AgusNicoGuidoIgnacio --> ANGI
        ClientNetworkConfig clientNetworkConfig = cfg.getNetworkConfig();
        for (String addr : clientAddresses ) {
            clientNetworkConfig.addAddress(addr);
        }


        HazelcastInstance hz = HazelcastClient.newHazelcastClient(cfg);

        //TODO: Parsear csv

        //TODO:lo que nico provee
        List<Tree> treesList = new ArrayList<>();
        treesList.add(new Tree(City.CABA, "hola1","hola2","hola3",2.0));
        Map<String, Long> populationMap = new HashMap<>();
        populationMap.put("hola2",1L);


        //load tree list provided above
        IList<Tree> iTreeList = hz.getList("tree-list");
        iTreeList.addAll(treesList);

        //if query1 add the population map
        if(props.getProperty(QUERY_OPT).equals("1")){
            IMap<String, Long> populationsIMap = hz.getMap("populations-map");
            populationsIMap.putAll(populationMap);
        }

        //TODO: Generar resultados de la query
        Query1 query = new Query1(hz);
        query.getResult().forEach(query1Result -> {
            System.out.print("Neighbourhood:");
            System.out.print(query1Result.getNeighbourhood());
            System.out.print(" TreesPerPerson");
            System.out.println(query1Result.getTreesPerPerson());
        });

        //remove all added objects
        hz.getDistributedObjects()
                .forEach(DistributedObject::destroy);

        //TODO: Escritura de archivos de salida
    }
    //TODO: check this vs System.getProperty("property name");
    private static Properties parseCommandLine(String[] args) throws ParseException {
        // basic options needed
        Option queryOption = new Option(JAVA_OPT, "specifies the query to execute");
        queryOption.setArgName(QUERY_OPT);
        queryOption.setRequired(true);

        Option cityOption = new Option(JAVA_OPT, "specifies the city dataset used");
        cityOption.setArgName(CITY_OPT);
        cityOption.setRequired(true);

        Option addressesOption = new Option(JAVA_OPT, "specifies the ip addresses of the nodes");
        addressesOption.setArgName(NODES_ADDRS_OPT);
        addressesOption.setRequired(true);

        Option inPathOption = new Option(JAVA_OPT, "specifies the path to the input files");
        inPathOption.setArgName(IN_PATH_OPT);
        inPathOption.setRequired(true);

        Option outPathOption = new Option(JAVA_OPT, "specifies the path to the output files");
        outPathOption.setArgName(OUT_PATH_OPT);
        outPathOption.setRequired(true);

        //specific options
        Option minOpt = new Option(JAVA_OPT, "specifies the path to the output files");
        minOpt.setArgName(MIN_OPT);
        minOpt.setRequired(false);

        Option nOpt = new Option(JAVA_OPT, "specifies the path to the output files");
        nOpt.setArgName(N_OPT);
        nOpt.setRequired(false);

        Option nameOpt = new Option(JAVA_OPT, "specifies the path to the output files");
        nameOpt.setArgName(NAME_OPT);
        nameOpt.setRequired(false);

        //return the properties given
        return CommandUtils.parseCommandLine(
                args,
                queryOption, cityOption,addressesOption,inPathOption,outPathOption,
                minOpt,nameOpt,nOpt
        );
    }
}
