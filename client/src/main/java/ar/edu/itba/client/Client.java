package ar.edu.itba.client;

import ar.edu.itba.client.utils.CommandUtils;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.config.GroupConfig;
import com.hazelcast.core.HazelcastInstance;

import java.util.List;

import java.util.Properties;

public class Client {
    private static Logger logger = LoggerFactory.getLogger(Client.class);
    private static final String JAVA_OPT = "D";
    //all the option/parameters/properties
    private static final String QUERY_OPT = "query";
    private static final String CITY_OPT = "city";
    private static final String NODES_ADDRS_OPT = "addresses";
    private static final String IN_PATH_OPT = "inPath";
    private static final String OUT_PATH_OPT = "outPath";
    private static final String MIN_OPT = "min";
    private static final String N_OPT = "n";
    private static final String NAME_OPT = "name";


    private String city;
    private Integer queryNumber;
    private List<String> addresses;
    private String inputPath;
    private String outputPath;
    private Integer min; // Query2, Query 4
    private Integer n; //Query3
    private String speciesName; //Query4


    public String getCity() {
        return city;
    }

    public Integer getQueryNumber() {
        return queryNumber;
    }

    public List<String> getAddresses() {
        return addresses;
    }

    public String getInputPath() {
        return inputPath;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public Integer getMin() {
        return min;
    }

    public Integer getN() {
        return n;
    }

    public String getSpeciesName() {
        return speciesName;
    }

    public static void main(String[] args) {
        Client client = new Client();

        //TODO: Parsear y armar la query pedida
        //query = .....

        logger.info("tp2hazelcast Client Starting ...");
        ClientConfig cfg = new ClientConfig();
        GroupConfig groupConfig = cfg.getGroupConfig();
        groupConfig.setName("tp2-g5");
        groupConfig.setPassword("angi"); //AgusNicoGuidoIgnacio --> ANGI
        ClientNetworkConfig clientNetworkConfig = cfg.getNetworkConfig();
        client.addresses.forEach(clientNetworkConfig::addAddress);

        HazelcastInstance hz = HazelcastClient.newHazelcastClient(cfg);

        //TODO: Parsear csv

        //TODO: Generar resultados de la query

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
