package ar.edu.itba.client;

import ar.edu.itba.client.utils.CommandUtils;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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


    public static void main(String[] args) {
        logger.info("tp2hazelcast Client Starting ...");
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
