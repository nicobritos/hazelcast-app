package ar.edu.itba.server;

import ar.edu.itba.api.utils.CommandUtils;
import com.hazelcast.config.Config;
import com.hazelcast.config.InterfacesConfig;
import com.hazelcast.config.XmlConfigBuilder;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Properties;

import static ar.edu.itba.api.utils.CommandUtils.JAVA_OPT;

public class Server {
    private static final Logger logger = LoggerFactory.getLogger(Server.class);
    private static final String INTERFACES_OPT = "i";

    public static void main(String[] args) throws ParseException {
        logger.info("tp2-g5 Server Starting ...");

        Config config = (new XmlConfigBuilder()).build();
        setConfiguration(config, args);
        HazelcastInstance hz = Hazelcast.newHazelcastInstance(config);
    }

    private static void setConfiguration(Config parsedConfig, String[] args) throws ParseException {
        Properties properties = parseCommandLine(args);
        if (properties.getProperty(INTERFACES_OPT) != null) {
            InterfacesConfig interfacesConfig = new InterfacesConfig();
            interfacesConfig.setEnabled(true);

            Arrays.stream(properties.getProperty(INTERFACES_OPT).split(";")).forEach(interfacesConfig::addInterface);

            parsedConfig.getNetworkConfig().setInterfaces(interfacesConfig);
        }
    }

    //TODO: check this vs System.getProperty("property name");
    private static Properties parseCommandLine(String[] args) throws ParseException {
        Option interfaceOpt = new Option(JAVA_OPT, "specifies the interfaces to bind to ; separated");
        interfaceOpt.setArgName(INTERFACES_OPT);
        interfaceOpt.setRequired(false);

        //return the properties given
        return CommandUtils.parseCommandLine(
                args,
                interfaceOpt
        );
    }
}
