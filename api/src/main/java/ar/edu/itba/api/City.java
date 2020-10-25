package ar.edu.itba.api;

public enum City {
    CABA,
    VANCOUVER;

    public City from(String city){
        return City.valueOf(city.toUpperCase());
    }
}
