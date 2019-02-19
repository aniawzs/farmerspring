package pl.annawyszomirskaszmyd.farmerspring.models.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="barn")
@Data
public class BarnEntity {
    private @Id @GeneratedValue int id;
    private String name;
    private @Column(name="farmer_id") int farmerId;

    @OneToMany(mappedBy = "barnEntity", fetch = FetchType.LAZY, cascade ={})
    Set<AnimalEntity> animals;

    @ManyToOne
    @JoinColumn(name = "farmer_id", referencedColumnName = "id", insertable=false, updatable = false)
    private FarmerEntity farmerEntity;
}
