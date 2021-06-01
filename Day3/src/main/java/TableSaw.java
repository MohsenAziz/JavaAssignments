import tech.tablesaw.aggregate.Summarizer;
import tech.tablesaw.api.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static tech.tablesaw.aggregate.AggregateFunctions.*;
public class TableSaw {

    public static void main(String[] args) {
        TableSaw tb = new TableSaw();
        Table df = null;
        try {
            df = Table.read().csv("C:/users/hp/desktop/titanic.csv");
            System.out.println(df.structure().toString());
            System.out.println(df.summary().toString());
            System.out.println(tb.addNewColumn(df).summary().toString());
            System.out.println(tb.addColumn(df).summary().toString());

            int colCount = df.columnCount();
            System.out.println(colCount);
            int rowCount=df.rowCount();
            System.out.println(rowCount);

            var summary = df.summarize("age", mean, sum, min, max,stdDev);
            System.out.println(summary);

            Table newDfLeft = df.select("name",  "survived", "sex");
            System.out.println(newDfLeft.structure().toString());
            Table newDfRight = df.select( "age", "sibsp", "parch");
            System.out.println(newDfLeft.summary().toString());

            System.out.println(newDfLeft.joinOn("name").leftOuter(newDfRight).summary().toString());

            int[] genders = df.stream().mapToInt(row -> {
                String gender = row.getString("sex");
                if (gender.equals("male"))
                    return 1;
                else
                    return 0;
            }).toArray();
            IntColumn genderColumn = IntColumn.create("gender", genders);
            df.addColumns(genderColumn);
            df.removeColumns("sex");
            System.out.println(df.summary().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public Table addNewColumn(Table df) {
        int rowCount = df.rowCount();
        Integer[] values = new Integer[rowCount];
        for (int i = 1; i <=rowCount; i++) {
            values[i] = i ;
        }
        IntColumn column = IntColumn.create("NewColumn", values);
        return df.addColumns(column);
    }

    public Table addColumn(Table df) {
        int rowCount = df.rowCount();
        List<LocalDate> colValues = new ArrayList<>();
        for (int i = 0; i < rowCount; i++) {
            colValues.add(LocalDate.of(2020, 3, i % 30 == 0 ? 1 : i % 30));
        }
        DateColumn column = DateColumn.create("NewColumn", colValues);
        return df.addColumns(column);
    }

}