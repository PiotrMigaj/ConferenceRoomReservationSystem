package pl.migibud.conferenceroomreservationsystem.organisation;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@ToString
public class Organisation  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "{organisation.name.must.not.be.blank}")
    @Size(min=2,message = "{organisation.name.size.too_short}")
    @Size(max=20,message = "{organisation.name.size.too_long}")
    @Column(nullable = false,unique = true,length = 20)
    private String name;
    @Enumerated(EnumType.STRING)
    private Status status;

    public Organisation(String name) {
        this.name = name;
    }

    public enum Status{
        ACTIVE,INACTIVE
    }

}
