public class Organizaciya {
    private String id;
    private String name;
    private String address;

    Organizaciya(String name, String address){
        this.name = name;
    }
    Organizaciya(){}
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
