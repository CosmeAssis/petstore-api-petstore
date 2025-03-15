package br.com.petstore.objects;

import lombok.Data;

@Data
public class OrderObject {
    private int id;
    private int petId;
    private int quantity;
}
