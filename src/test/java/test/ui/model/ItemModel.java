package test.ui.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemModel {
    String name;
    Double price;
}
