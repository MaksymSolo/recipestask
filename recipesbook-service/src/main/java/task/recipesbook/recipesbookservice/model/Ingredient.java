package task.recipesbook.recipesbookservice.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "ingredient")
@Entity
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ingredient_id")
    private Long ingredientId;

    @NotBlank(message = "Ingredient name is required")
    @Size(min = 2, max = 30, message = "Ingredient name must be between {min} and {max} characters")
    @Column(name = "name")
    private String name;

    @NotBlank(message = "Amount is required")
    @Size(min = 2, max = 25, message = "Amount must be between {min} and {max} characters")
    @Column(name = "amount")
    private String amount;
}
