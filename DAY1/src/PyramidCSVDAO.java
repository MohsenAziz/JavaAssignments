import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

public class PyramidCSVDAO {

    public Pyramid createPyramid(String[] data){
        double height;
        if(data[9].equals(""))
            height=0;
        else{
            height = Double.valueOf(data[9]);
        }
        Pyramid p1 = new Pyramid(data[0], data[1], data[4], height);
        return p1;
    }
    public List<Pyramid> readPyramidFromCSV(String fileName){
        List<Pyramid> allPyramids = new ArrayList<>();
        File f1 = new File(fileName);
        List<String> lines = new ArrayList<>();
        try{
            lines = Files.readAllLines(f1.toPath());
        }
        catch(Exception e){
            System.out.println("something wrong happened/file not found"+ e);
        }
        for (int index = 1; index < lines.size(); index++) {
            String line = lines.get(index);
            String fields[] = line.split(",");
            allPyramids.add(createPyramid(fields));
        }
        return allPyramids;
    }
}
