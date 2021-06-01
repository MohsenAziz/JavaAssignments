import java.util.List;


public class Main {



    public static void main(String[] args) {


        PyramidCSVDAO dao = new PyramidCSVDAO();
        List<Pyramid> pyramids = dao.readPyramidFromCSV("c:\\users\\hp\\desktop\\Pyramids.csv");
        for(Pyramid p : pyramids) {
            System.out.println(p.toString());
        }

    }

}
