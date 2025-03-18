package br.com.petstore.objects;

import lombok.Data;
import java.util.List;

@Data
public class PetObject {
    private int id;
    private PetCategoryObject category;
    private String name;
    private List<String> photoUrls;
    private List<PetTagObject> tags;
    private String status;
}
