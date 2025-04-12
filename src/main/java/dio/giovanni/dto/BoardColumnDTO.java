package dio.giovanni.dto;

import dio.giovanni.persistence.entity.BoardColumnKindEnum;

public record BoardColumnDTO(

        Long id,
        String name,
        BoardColumnKindEnum kind,
        int cardsAmount
){
}
