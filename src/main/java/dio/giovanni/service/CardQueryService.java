package dio.giovanni.service;

import dio.giovanni.dto.CardDetailsDTO;
import dio.giovanni.persistence.dao.CardDAO;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

@AllArgsConstructor
public class CardQueryService {

    private final Connection connection;

    public Optional<CardDetailsDTO> findById(final Long id) throws SQLException{
        var dao = new CardDAO(connection);
        return dao.findById(id);
    }
}
