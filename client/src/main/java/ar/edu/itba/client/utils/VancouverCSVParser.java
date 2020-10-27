package ar.edu.itba.client.utils;

import ar.edu.itba.api.City;

import java.util.HashMap;
import java.util.Map;

public class VancouverCSVParser extends CSVParser {
    private static final Map<TreeHeaders, String> treeHeaders = new HashMap<>();
    private static final Map<CityHeaders, String> cityHeaders = new HashMap<>();
    static {
        {
            treeHeaders.put(TreeHeaders.CITY, "NEIGHBOURHOOD_NAME");
            treeHeaders.put(TreeHeaders.STREET, "STD_STREET");
            treeHeaders.put(TreeHeaders.SCIENTIFIC_NAME, "COMMON_NAME");
            treeHeaders.put(TreeHeaders.DIAMETER, "DIAMETER");

            cityHeaders.put(CityHeaders.NAME, "nombre");
            cityHeaders.put(CityHeaders.POPULATION, "habitantes");
        }
    }

    public void parseTrees(String filepath) {
        super.parseTrees(filepath, City.VANCOUVER, treeHeaders);
    }

    public void parseCities(String filepath) {
        super.parseCities(filepath, cityHeaders);
    }
}
