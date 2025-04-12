package dio.giovanni.persistence.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
public class BoardColumnEntity {

    private Long id;
    private String name;
    private int order;
    private BoardColumnKindEnum kind;
    private BoardEntitty board = new BoardEntitty();
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<CardEntity> cards = new ArrayList<>();
}
