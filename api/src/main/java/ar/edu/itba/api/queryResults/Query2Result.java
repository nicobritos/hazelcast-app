package ar.edu.itba.api.queryResults;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Query2Result implements Comparable<Query2Result>{
    private final String neighbourhood;
    private final String street;
    private final long treesQty;

    public Query2Result(String neighbourhood, String street, long treesQty){
        this.neighbourhood = neighbourhood;
        this.street = street;
        this.treesQty = treesQty;
    }

    public String getNeighbourhood() {
        return neighbourhood;
    }

    public String getStreet() {
        return street;
    }

    public long getTreesQty() {
        return treesQty;
    }

    public static List<Query2Result> listFrom(Map<String, Long> map){
        List<Query2Result> list = new LinkedList<>();
        for (String key: map.keySet()){
            String[] s = key.split("-");
            if (s.length != 2){
                throw new IllegalArgumentException("Clave del mapa mal formada: " + key);
            }
            list.add(new Query2Result(s[0], s[1], map.get(key)));
        }
        return list;
    }


    @Override
    public int compareTo(Query2Result o) {
        return street.compareTo(o.street);
    }
}
