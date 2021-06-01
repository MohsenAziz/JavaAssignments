import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.knowm.xchart.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Xchart{

    public static void main(String[] args) {
        ObjectMapper mapp = new ObjectMapper();

        try {
            FileInputStream fileInputStream = new FileInputStream("C:/users/hp/desktop/titanic.json");
            List<Passenger> passengers = mapp.readValue(fileInputStream, new TypeReference<List<Passenger>>() {
            });

            passengers.stream().forEach(System.out::println);
            Xchart.catagoryChart(passengers);
            Xchart.pieChart(passengers);
            Xchart.boxChart(passengers);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void catagoryChart(List<Passenger> allPassengers) {

        CategoryChart ch = new CategoryChartBuilder().width(1024)
                .height(1000)
                .title("Age ")
                .xAxisTitle("Names")
                .yAxisTitle("Age")
                .build();


        List<Float> allAges = allPassengers.stream().map(Passenger::getAge).limit(5).collect(Collectors.toList());
        List<String> allNames = allPassengers.stream().map(Passenger::getName).limit(5).collect(Collectors.toList());

        ch.addSeries("all ages ", allNames, allAges);
        new SwingWrapper(ch).displayChart();

    }


    public static void pieChart(List<Passenger> allPassengers) {
        PieChart chart2 = new PieChartBuilder().width(1000).height(1000).title("second chart").build();
        Map<Float, List<Passenger>> pclasses= allPassengers.stream()
                .collect(Collectors.groupingBy(Passenger::getPclass));

        pclasses.keySet().stream().forEach((key) -> chart2.addSeries("Pclass " + key, (Number) pclasses.get(key)));
        new SwingWrapper(chart2).displayChart();
    }

    public static void boxChart(List<Passenger> allPassengers) {
        BoxChart chart1 = new BoxChartBuilder().width(1000).height(1000).title("first chart").build();
        List<Float> ages = allPassengers.stream().map(Passenger::getAge).collect(Collectors.toList());
        chart1.addSeries("Passenger's Ages ", ages);
        new SwingWrapper(chart1).displayChart();

    }




}
