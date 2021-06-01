public class Country {
    String name;
    int code;
    String capital;

    public Country(int code, String name) {
        this.name = name;
        this.code = code;
    }

    @Override
    public String toString() {
        return "Country [name=" + name + ", code=" + code + "]";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
    public String getCapital() {
        return capital;
    }
    public void setCapital(String capital) {
        this.capital = capital;
    }


}
