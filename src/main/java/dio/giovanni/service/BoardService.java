package dio.giovanni.service;

import dio.giovanni.persistence.dao.BoardColumnDAO;
import dio.giovanni.persistence.dao.BoardDAO;
import dio.giovanni.persistence.entity.BoardEntitty;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;

@AllArgsConstructor
public class BoardService {

    private Connection connection;

    public BoardEntitty insert(final BoardEntitty entitty) throws SQLException{
        var dao = new BoardDAO(connection);
        var boardColumnDAO = new BoardColumnDAO(connection);
        try {
            dao.inset(entitty);
            var columns = entitty.getBoardColumns().stream().map(c -> {
                c.setBoard(entitty);
                return c;
            }).toList();
            for(var colum: columns){
                boardColumnDAO.insert(colum);
            }
            connection.commit();
        }catch (SQLException e){
            connection.rollback();
            throw e;
        }
        return entitty;
    }

    public boolean delete(final Long id) throws SQLException {
        var dao = new BoardDAO(connection);
        try{
            if(!dao.exists(id)){
                return false;
            }
            dao.delete(id);
            connection.commit();
            return true;
        } catch (SQLException e){
            connection.rollback();
            throw e;
        }
    }
}
