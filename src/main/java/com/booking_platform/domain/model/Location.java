package com.booking_platform.domain.model;

import com.booking_platform.domain.exceptions.property.InvalidLocationException;

public class Location {
    private  String country;
    private  String city;
    private String address;

    protected Location() {
        this.country = null;
        this.city = null;
        this.address = null;
    }

    public Location(String country, String city, String address) throws InvalidLocationException {

        if(country == null || country.isBlank()){
            throw new InvalidLocationException("El pais en la direccion debe de ser correcta.");
        }
        if(city == null || city.isBlank()){
            throw new InvalidLocationException("La ciudad en la direccion debe de ser correcta.");
        }
        if(address == null || address.isBlank()){
            throw new InvalidLocationException("La direccion debe de ser correcta.");
        }

       

        this.country = country;
        this.city = city;
        this.address = address;
    }


    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
       
        return String.format("%s, %s, %s", address, city, country);
    }

}
