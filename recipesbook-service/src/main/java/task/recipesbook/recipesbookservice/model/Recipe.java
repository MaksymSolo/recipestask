package task.recipesbook.recipesbookservice.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = "ingredients")
@Table(name = "recipe")
@Entity
public class Recipe {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_id")
    private Long recipeId;

    @NotBlank(message = "Recipe name is required")
    @Size(min = 2, max = 30, message = "Recipe name must be between {min} and {max} characters")
    @Column(name = "name")
    private String name;

    @NotBlank(message = "Short description is required")
    @Size(min = 2, max = 254, message = "Description must be between {min} and {max} characters")
    @Column(name = "short_description")
    private String shortDescription;

    @Size(max = 500, message = "URL can't be longer then {max}  characters. Left free for default image")
    @Column(name = "image_url", length = 500)
    private String imageUrl;

    @NotBlank(message = "Guide is required")
    @Size(min = 2, max = 800, message = "Guide must be between {min} and {max} characters")
    @Column(name = "guide", length = 800)
    private String guide;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "recipe_ingredient",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    private List<Ingredient> ingredients;
}
