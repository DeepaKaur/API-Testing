package petclinic.model;

public class PetTypes {
    private int id;
    private String name;

    public PetTypes(){
    }

    public PetTypes(String name){
        setName(name);
    }
    public PetTypes(int id, String name){
        setName(name);
        setId(id);
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
