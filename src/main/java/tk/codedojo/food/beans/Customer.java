package tk.codedojo.food.beans;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Size;

@Document
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
@Setter
public class Customer {
    @Id
    private String id;
    private String lastName;
    private String firstName;
    @NonNull
    @Size(min=6)
    private String userName;
}
