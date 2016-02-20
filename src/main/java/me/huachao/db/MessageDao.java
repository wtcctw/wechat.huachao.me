package me.huachao.db;

import me.huachao.dto.message.input.BaseInputMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.annotation.Resource;
import java.sql.*;

/**
 * Created by huachao on 2/1/16.
 */
public class MessageDao {

    private static final Logger logger = LoggerFactory.getLogger(MessageDao.class);

    private final String TABLE_NAME = "WECHAT_INPUTMSG";

    @Resource
    private JdbcTemplate jdbcTemplate;

    public void init() {

        boolean tableExist = jdbcTemplate.execute(new ConnectionCallback<Boolean>(){
            public Boolean doInConnection(Connection con) {
                try {
                    DatabaseMetaData meta = con.getMetaData();
                    ResultSet results = meta.getTables(null, null, TABLE_NAME, null);
                    if (results.next()){
                        return true;
                    }
                } catch (SQLException e) {
                    logger.warn("connect to sqlite file error! {}", e);
                }
                return false;
            }
        });

        if (!tableExist) {
            createTable();
        }
    }

    private void createTable() {

        String sql = String.format("CREATE TABLE \"%s\" (\n" +
                "\t \"Id\" INTEGER PRIMARY KEY,\n" +
                "\t \"From\" varchar(50) NOT NULL,\n" +
                "\t \"To\" varchar(50) NOT NULL,\n" +
                "\t \"Type\" varchar(25) NOT NULL,\n" +
                "\t \"MsgId\" int NOT NULL,\n" +
                "\t \"CreateTime\" DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,\n" +
                "\t \"Content\" varchar(255),\n" +
                "\t \"Raw\" text\n" +
                ");", TABLE_NAME);

        jdbcTemplate.execute(sql);
    }

    public int insert(final BaseInputMessage input) {
        final String sql = String.format("insert into %s (\"From\", \"To\", \"Type\", \"MsgId\", \"Content\", \"Raw\") values (?,?,?,?,?,?)", TABLE_NAME);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int affectedRowCount = jdbcTemplate.update(
            new PreparedStatementCreator() {
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(sql, new String[]{"Id"});
                    ps.setString(1, input.getFrom());
                    ps.setString(2, input.getTo());
                    ps.setString(3, input.getType());
                    ps.setLong(4, input.getMsgId());
                    ps.setString(5, "content");
                    ps.setString(6, "raw");
                    return ps;
                }
            },
            keyHolder);
        return keyHolder.getKey().intValue();
    }


}
