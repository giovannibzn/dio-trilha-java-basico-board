package dio.giovanni.persistence.dao;

import com.mysql.cj.jdbc.StatementImpl;
import dio.giovanni.dto.BoardColumnDTO;
import dio.giovanni.persistence.entity.BoardColumnEntity;
import dio.giovanni.persistence.entity.CardEntity;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static dio.giovanni.persistence.entity.BoardColumnKindEnum.findByName;
import static java.util.Objects.isNull;

@RequiredArgsConstructor
public class BoardColumnDAO {

    private final Connection connection;

    public BoardColumnEntity insert(final BoardColumnEntity entity) throws SQLException{
        var sql = "INSERT INTO boards_columns(name, `order`, kind, board_id) VALUES (?, ?, ?, ?);";
        try (var statement = connection.prepareStatement(sql)){
            var i = 1;
            statement.setString(i++, entity.getName());
            statement.setInt(i++, entity.getOrder());
            statement.setString(i++, entity.getKind().name());
            statement.setLong(i, entity.getBoard().getId());
            statement.executeUpdate();
            if(statement instanceof StatementImpl impl){
                entity.setId(impl.getLastInsertID());
            }
            return entity;
        }
    }

    public List<BoardColumnEntity> findByBoardId(final Long boardId) throws SQLException{
        List<BoardColumnEntity> entities = new ArrayList<>();
        var sql = "SELECT id, name, `order`, kind FROM boards_columns WHERE board_id = ? ORDER BY `order`;";
        try (var statement = connection.prepareStatement(sql)){
            statement.setLong(1, boardId);
            statement.executeQuery();
            var resultSet = statement.getResultSet();
            while(resultSet.next()){
                var entity = new BoardColumnEntity();
                entity.setId(resultSet.getLong("id"));
                entity.setName(resultSet.getString("name"));
                entity.setOrder(resultSet.getInt("order"));
                entity.setKind(findByName(resultSet.getString("kind")));
                entities.add(entity);
            }
            return entities;
        }
    }

    public List<BoardColumnDTO> findByBoardIdWithDetails(final Long boardId) throws SQLException{
        List<BoardColumnDTO> dtos = new ArrayList<>();
        var sql =
                """
                        SELECT  bc.id,
                                bc.name,
                                bc.kind,
                                (SELECT COUNT(c.id)
                                FROM cards c
                                WHERE c.board_column_id = bc.id) cards_amount
                                FROM boards_columns bc
                                WHERE board_id = ?
                                ORDER BY `order`;
                        """;
        try (var statement = connection.prepareStatement(sql)){
            statement.setLong(1, boardId);
            statement.executeQuery();
            var resultSet = statement.getResultSet();
            while (resultSet.next()){
                var dto = new BoardColumnDTO(
                        resultSet.getLong("bc.id"),
                        resultSet.getString("bc.name"),
                        findByName(resultSet.getString("bc.kind")),
                        resultSet.getInt("cards_amount")
                );
                dtos.add(dto);
            }
            return dtos;
        }
    }

    public Optional<BoardColumnEntity> findById(final Long boardId) throws SQLException{
        var sql =
                """
                SELECT  bc.name,
                        bc.kind,
                        c.id,
                        c.title,
                        c.description
                        FROM boards_columns bc
                        LEFT JOIN cards c
                        ON c.board_column_id = bc.id
                        WHERE bc.id = ?;
                
                        """;
        try (var statement = connection.prepareStatement(sql)){
            statement.setLong(1, boardId);
            statement.executeQuery();
            var resultSert = statement.getResultSet();
            if(resultSert.next()){
                var entity = new BoardColumnEntity();
                entity.setName(resultSert.getString("bc.name"));
                entity.setKind(findByName(resultSert.getString("bc.kind")));
                do{
                    var card = new CardEntity();
                    if(isNull(resultSert.getString("c.title"))){
                        break;
                    }
                    card.setId(resultSert.getLong("c.id"));
                    card.setTitle(resultSert.getString("c.title"));
                    card.setDescription(resultSert.getString("c.description"));
                    entity.getCards().add(card);
                } while (resultSert.next());
                return Optional.of(entity);
            }
            return Optional.empty();
        }
    }
}
