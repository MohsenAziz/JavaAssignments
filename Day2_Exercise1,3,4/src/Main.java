import java.util.*;
import java.util.stream.Collectors;


public class Main {




    public static void main(String[] args) {
        CountryDao countriesDAO = new  CountryDao();
       CityDao citiesDAO = new CityDao();


        //EXERCISE 1:-

        List<Country> countryList = countriesDAO.readAllCountries("C:/users/hp/desktop/countries.csv");
        List<City> cityList = citiesDAO.readAllCites("C:/users/hp/desktop/cities.csv");

        Map<Integer, List<City>> countryCities = new HashMap<>();

        for (City c : cityList) {
            if (!countryCities.containsKey(c.code)) {
                List<City> cities = new ArrayList<>();
                cities.add(c);
                countryCities.put(c.code, cities);
            }
            else {
                countryCities.get(c.code).add(c);
            }
        }

        countryCities.forEach((k, v) -> v.stream().map(City::getPopulation).sorted().collect(Collectors.toList()));




        //EXERCISE 3:-


        Map<Integer, List<City>> highPop1 = cityList.stream()
                .collect(Collectors.groupingBy(City::getCode));

        List<Optional<Double>> highestPopulationPerCounty = highPop1.values().stream().map(x-> x.stream().map(City::getPopulation)
                .max(Double::compare)
        ).collect(Collectors.toList());;


        // ANOTHER WAY

        highPop1.forEach((k,v) ->
                System.out.println( v.stream().map(City::getPopulation).max(Double::compare)));
        ///////////////////////////////////////////////////////////////////////////////////////////////////////

        Map<String, List<City>> highPop2 = cityList
                .stream()
                .collect(Collectors.groupingBy(City::getContinent));

        List<Optional<Double>> highestPopulationPerContinent = highPop2.values().stream().map(x -> x.stream().map(City::getPopulation)
                        .max(Double::compare)
                ).collect(Collectors.toList());

        // ANOTHER WAY

        highPop2.forEach((k,v) ->
                System.out.println( v.stream().map(City::getPopulation).max(Double::compare)));

        //////////////////////////////////////////////////////////////////////////////////////////////////

        Map<String, List<City>> highPop3 = countryList
                .stream()
                .collect(Collectors.groupingBy(Country::getCapital));


        Double highestPopulationCapital = highPop3.values()
                .stream()
                .map(City::getPopulation)
                .max()
                .getAsDouble();




        //EXERCISE 4:-

        double median, lowerQuartile, upperQuartile,average;

        List<Double> sortedCity = cityList.stream().map(City::getPopulation).sorted().collect(Collectors.toList());

        if (sortedCity.size() % 2 == 0) {
            median = ((double)sortedCity.get((sortedCity.size()/2)).population + (double)sortedCity.get((sortedCity.size())/2 + 1).population)/2;
            lowerQuartile = ((double)sortedCity.get(sortedCity.size()/4).population + (double)sortedCity.get((sortedCity.size()/4)+ 1).population)/2;
            upperQuartile = ((double)sortedCity.get((sortedCity.size()*.75)).population + (double)sortedCity.get(sortedCity.size()*.75+1).population)/2;
        }
        else {
            median = (double) sortedCity.get(sortedCity.size()/2).population;
            lowerQuartile = (double) sortedCity.get(sortedCity.size()/4).population;
            upperQuartile = (double) sortedCity.get((sortedCity.size()*.75)).population;
        }

        average=sortedCity.stream().mapToDouble(City::getPopulation).average().getAsDouble();



    }
}