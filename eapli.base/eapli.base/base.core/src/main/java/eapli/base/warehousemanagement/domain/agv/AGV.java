package eapli.base.warehousemanagement.domain.agv;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.general.domain.model.Description;

import javax.persistence.*;

@Entity
@Embeddable
public class AGV implements AggregateRoot<AGVId> {

    /**
     * Id of the AGV
     */
    @Id
    private AGVId id;

    /**
     * A short description
     */
    private Description description;

    /**
     * Model of the AGV
     */
    private Model model;

    /**
     * MaxWeight it can carry
     */
    private MaxWeight maxWeight;

    /**
     * MaxVolume it can Carry
     */
    private MaxVolume maxVolume;

    /**
     * Status of the AGV
     */
    private Status status;

    /**
     * Position of the AGV
     */
    private Position position;

    /**
     * His autonomy in minutes
     */
    private AutonomyMin autonomyMin;

//    /**
//     * His AGV Dock where it charges
//     */
//    @OneToOne(mappedBy = "agvResp", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
//    private AGVDock agvDock;
//
//    /**
//     * His task
//     */
//    @OneToOne(mappedBy = "agvResp", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
//    private Task task;


    /**
     * AGV Constructor
     * @param id
     * @param description
     * @param model
     * @param maxWeight
     * @param maxVolume
     * @param status
     * @param position
     * @param autonomyHours
     */
    public AGV(AGVId id, Description description, Model model, MaxWeight maxWeight, MaxVolume maxVolume, Status status, Position position, AutonomyMin autonomyHours) {
        this.id = id;
        this.description = description;
        this.model = model;
        this.maxWeight = maxWeight;
        this.maxVolume = maxVolume;
        this.status = status;
        this.position = position;
        this.autonomyMin = autonomyHours;
    }

    /**
     * Empty Constructor
     */
    public AGV() {

    }

    @Override
    public boolean sameAs(Object other) {
        return false;
    }

    @Override
    public int compareTo(AGVId other) {
        return AggregateRoot.super.compareTo(other);
    }

    @Override
    public AGVId identity() {
        return this.id;
    }

    @Override
    public boolean hasIdentity(AGVId id) {
        return AggregateRoot.super.hasIdentity(id);
    }
}
