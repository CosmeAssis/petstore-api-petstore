package br.com.petstore.objects;

import lombok.Data;
import java.util.List;

@Data
public class PetObject {
    private int id;
    private Category category;
    private String name;
    private List<String> photoUrls;
    private List<Tag> tags;
    private String status;

    @Data
    public static class Category {
        private int id;
        private String name;
    }

    @Data
    public static class Tag {
        private int id;
        private String name;
    }
}
