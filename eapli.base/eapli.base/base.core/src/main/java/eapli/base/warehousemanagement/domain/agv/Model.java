package eapli.base.warehousemanagement.domain.agv;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;

@Embeddable
public class Model implements ValueObject {

    /**
     * Model
     */
    private String model;

    /**
     * Constructor of the model
     * With the business restrictions
     * @param model
     */
    public Model(String model) {
        this.setModel(model);
    }

    public Model() {

    }

    /**
     * Not empty and 50 char maximum
     * @param model
     */
    public void setModel(String model) {
        try{
            if(model.length() < 50 && !model.isEmpty()){
                this.model = model;
            }else{
                throw new IllegalArgumentException();
            }
        }catch (IllegalArgumentException illegalArgumentException){
            System.out.println("Should be less than 50 chars or not empty");
        }
    }

    @Override
    public String toString() {
        return "Model{" +
                "model='" + model + '\'' +
                '}';
    }
}
