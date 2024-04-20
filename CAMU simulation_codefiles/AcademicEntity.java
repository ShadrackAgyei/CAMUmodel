public abstract class AcademicEntity {

    private String ID;
    private String name;

    public AcademicEntity(){}

    public AcademicEntity(String ID, String name){
        this.ID = ID;
        this.name = name;
    }

    // Getters
    public String getID(){
        return this.ID;
    }

    public String getName(){
        return this.name;
    }

    // Setters
    public void setID(String ID){
        this.ID = ID;
    }

    public void setName(String name){
        this.name = name;
    }
}