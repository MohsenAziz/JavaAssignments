

import joinery.DataFrame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Joinery {

    public static void main(String[] args) {
        String path = "C:/users/hp/desktop/titanic.csv";
        try {
            DataFrame<Object> df = DataFrame.readCsv(path);
            String data = df.describe().toString();
            System.out.println(data);
            Double mean = (Double) df.describe().col("age").get(1);
            System.out.println(mean);
            System.out.println(df.length());
            DataFrame<Object> newDf = df.retain("pclass", "age");
            System.out.println(newDf.describe().toString());
            df = df.retain("pclass",  "fare", "body");
            System.out.println(df.describe().toString());

            DataFrame<Object> allDataFrameMean = df.mean();
            System.out.println(allDataFrameMean);
            DataFrame<Object> colMean=df.groupBy(row ->row.get(0)).mean();
            System.out.println(colMean);
            DataFrame<Object> allDataFrameMin = df.min();
            System.out.println(allDataFrameMin);
            DataFrame<Object> colMin=df.groupBy(row ->row.get(0)).min();
            System.out.println(colMin);
            DataFrame<Object> allDataFrameMax = df.max();
            System.out.println(allDataFrameMax);
            DataFrame<Object> colMax=df.groupBy(row ->row.get(0)).max();
            System.out.println(colMax);
            DataFrame<Object> allDataFrameStd = df.stddev();
            System.out.println(allDataFrameStd);
            DataFrame<Object> colStd=df.groupBy(row ->row.get(0)).stddev();
            System.out.println(colStd);




            DataFrame<Object> joinedDataFrame = new DataFrame();
            System.out.println(joinedDataFrame.join(df));
            System.out.println(df.describe().toString());
            List<Object> col = df.col(1);
            System.out.println(col);
            DataFrame<Object>dataWithPcalss1=df.select(objects -> ((Long) objects.get(0)).equals(Long.parseLong("1")));
            System.out.println(dataWithPcalss1.describe().toString());



        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static DataFrame addColumn(DataFrame df) {
        int rowCount = df.length();
        List<Double> list = new ArrayList<>();
        for (int i = 0; i < rowCount; i++) {
            list.add((double) (i + 1));
        }
        return df.add(list);
    }
}