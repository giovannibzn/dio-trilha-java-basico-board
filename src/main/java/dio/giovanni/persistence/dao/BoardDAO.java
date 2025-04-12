package dio.giovanni.persistence.dao;

import com.mysql.cj.jdbc.StatementImpl;
import dio.giovanni.persistence.entity.BoardEntitty;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

@AllArgsConstructor
public class BoardDAO {

    private final Connection connection;

    public BoardEntitty inset(final BoardEntitty entity) throws SQLException{
        var sql= "INSERT INTO boards (name) VALUES (?);";
        try(var statement = connection.prepareStatement(sql)) {
            statement.setString(1, entity.getName());
            statement.executeQuery();
            if(statement instanceof StatementImpl impl){
                entity.setId(impl.getLastInsertID());
            }
        }
        return entity;
    }

    public void delete(final Long id) throws SQLException{
        var sql= "DELETE FROM boards WHERE id = ?;";
        try(var statement = connection.prepareStatement(sql)){
            statement.setLong(1, id);
            statement.executeUpdate();
        }
    }

    public Optional<BoardEntitty> findById(final Long id) throws SQLException {
        var sql = "SELECT id, name FROM boards WHERE id = ?;";
        try (var statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeQuery();
            var resultSet = statement.getResultSet();
            if (resultSet.next()) {
                var entity = new BoardEntitty();
                entity.setId(resultSet.getLong("id"));
                entity.setName(resultSet.getString("name"));
                return Optional.of(entity);
            }
            return Optional.empty();
        }
    }

    public boolean exists(final Long id) throws SQLException{
        var sql= "SELECT 1 FROM boards WHERE id = ?;";
        try(var statement = connection.prepareStatement(sql)){
            statement.setLong(1, id);
            statement.executeQuery();
            return statement.getResultSet().next();
        }
    }
}
