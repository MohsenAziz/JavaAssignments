public class City {
    int code;
    String name, continent;
    double surfaceArea;
    static double population;

    public City(int code, String name, String continent, double surfaceArea, double population) {

        this.code = code;
        this.name = name;
        this.continent = continent;
        this.surfaceArea = surfaceArea;
        this.population = population;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "City [code=" + code + ", name=" + name + ", continent=" + continent + ", surfaceArea=" + surfaceArea
                + ", population=" + population + "]";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public double getSurfaceArea() {
        return surfaceArea;
    }

    public void setSurfaceArea(double surfaceArea) {
        this.surfaceArea = surfaceArea;
    }

    public double getPopulation() {
        return population;
    }

    public void setPopulation(double population) {
        this.population = population;
    }

    public void print(){
        System.out.println(name);
    }

    }

}