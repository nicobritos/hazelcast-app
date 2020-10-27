package ar.edu.itba.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.config.GroupConfig;
import com.hazelcast.core.HazelcastInstance;

import java.util.List;

public class Client {
    private static Logger logger = LoggerFactory.getLogger(Client.class);

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
}
