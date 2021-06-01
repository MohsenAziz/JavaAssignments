
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.csv.CSVFormat;
import smile.data.DataFrame;
import smile.data.formula.Formula;
import smile.data.measure.NominalScale;
import smile.data.vector.IntVector;
import smile.io.Read;
import smile.plot.swing.BarPlot;
import smile.plot.swing.Histogram;
import smile.regression.RandomForest;



public class Smile {

    public static void main(String[] args) {

        String file1Path = "C:/users/hp/desktop/titanic_train.csv";
        try {

            DataFrame trainDf = Read.csv(file1Path, CSVFormat.DEFAULT.withFirstRecordAsHeader());
            System.out.println(trainDf.schema());
            System.out.println(trainDf.summary());
            trainDf = trainDf.select("PassengerId", "Pclass", "Age", "SibSp", "Name", "Parch", "Sex", "Survived");
            Smile.encodeColumn(trainDf, "Pclass");
            trainDf= trainDf.merge(IntVector.of("Gender", encodeColumn(trainDf, "Sex")));
            trainDf = trainDf.merge(IntVector.of("PclassValues", encodeColumn(trainDf, "Pclass")));
            trainDf = trainDf.drop("Sex").drop("Pclass").drop("Name");
            Smile.startEda(trainDf);
            trainDf=trainDf.omitNullRows();
            System.out.println(trainDf.schema());
            System.out.println(trainDf.summary());

            RandomForest algorithm=RandomForest.fit(Formula.lhs("Survived"),trainDf);
            System.out.println(algorithm.metrics());




            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


            String file2Path = "C:/users/hp/desktop/titanic_test.csv";
            DataFrame testDf=Read.csv(file2Path,CSVFormat.DEFAULT.withFirstRecordAsHeader());
            System.out.println(testDf.summary());
            System.out.println(trainDf.schema());
            testDf=testDf.select("PassengerId", "Pclass", "Age", "SibSp", "Name", "Parch", "Sex");
            testDf = testDf.merge(IntVector.of("Gender", encodeColumn(testDf, "Sex")));
            testDf = testDf.drop("Sex").drop("Name");
            testDf=testDf.omitNullRows();
            System.out.println(testDf.summary());
            Arrays.stream(algorithm.predict(testDf)).forEach(System.out::println);

        } catch (IOException | URISyntaxException | InterruptedException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static int[] encodeColumn(DataFrame df, String columnName) {
        String[] values = df.stringVector(columnName).distinct().toArray(new String[]{});
        Arrays.stream(values).forEach(System.out::println);
        int[] newValues = df.stringVector(columnName).factorize(new NominalScale(values)).toIntArray();
        return newValues;
    }

    public static void startEda(DataFrame df) throws InterruptedException, InvocationTargetException {
        df = df.omitNullRows();
        DataFrame survivedData = DataFrame.of(df.stream().filter(t -> t.get("Survived").equals(1)));
        DataFrame notSurvivedData = DataFrame.of(df.stream().filter(t -> t.get("Survived").equals(0)));
        System.out.println(survivedData.summary());
        System.out.println(notSurvivedData.summary());
        Histogram.of(survivedData.doubleVector("Age").toDoubleArray())
                .canvas().setAxisLabel(0,"Age").setAxisLabel(1,"Count")
                .setTitle("Age frequencies among surviving passengers")
                .window();

        Map dictionary = survivedData.stream ()
                .collect (Collectors.groupingBy (t -> Double.valueOf (t.getDouble ("Age")).intValue (), Collectors.counting ()));

        double[] breaks = ((Collection<Integer>) dictionary.keySet ())
                .stream ()
                .mapToDouble (l -> Double.valueOf (l))
                .toArray ();

        double[] valuesInt = ((Collection<Long>) dictionary.values ())
                .stream ().mapToDouble (i -> i.intValue ())
                .toArray ();
        double[][]data=new double[][]{breaks,valuesInt};
        BarPlot.of(data,new String[]{"Ages","Freq"})   .canvas().setAxisLabel(0,"Age").setAxisLabel(1,"Count")
                .setTitle("Age frequencies among surviving passengers")
                .window();


    }
}
