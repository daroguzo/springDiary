package springdiary.main;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DiaryDao {
    private JdbcTemplate jdbcTemplate;

    public void insert(final Diary newDiary) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    @Override
                    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                        PreparedStatement pstmt = con.prepareStatement(
                                "insert into diary (owner, name, content, date) values (?,?,?,?)",
                                new String[]{"id"});
                        pstmt.setString(1, newDiary.getOwner());
                        pstmt.setString(2, newDiary.getName());
                        pstmt.setString(3, newDiary.getContent());
                        pstmt.setString(4, newDiary.getDate());
                        return pstmt;
                    }
                },
                keyHolder);
        Number keyValue = keyHolder.getKey();
        newDiary.setId(keyValue.longValue());
    }

    public void update(Diary diary){
        jdbcTemplate.update("update diary set name = ?, content = ? where id = ?",
                diary.getName(), diary.getContent(), diary.getId());

    }

    public void deleteDiary(String id){
        jdbcTemplate.update("delete from diary where id = ?",
                id);
    }


    public DiaryDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Diary> selectByOwner(String owner){
        List<Diary> results = jdbcTemplate.query("select * from diary where owner = ?",
                new RowMapper<Diary>() {
                    @Override
                    public Diary mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Diary diary = new Diary(rs.getString("owner"),
                                rs.getString("name"),
                                rs.getString("content"),
                                rs.getString("date"));
                        diary.setId(rs.getLong("id"));
                        return diary;
                    }
                }, owner);
        return results.isEmpty() ? null : results;
    }
    public Diary selectById(String id){
        List<Diary> results = jdbcTemplate.query("select * from diary where id = ?",
                new RowMapper<Diary>() {
                    @Override
                    public Diary mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Diary diary = new Diary(rs.getString("owner"),
                                rs.getString("name"),
                                rs.getString("content"),
                                rs.getString("date"));
                        diary.setId(rs.getLong("id"));
                        return diary;
                    }
                }, id);
        return results.isEmpty() ? null : results.get(0);
    }

    public int count(){
        List<Integer> count = jdbcTemplate.query("select count(*) from diary",
                new RowMapper<Integer>() {
                    @Override
                    public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return rs.getInt(1);
                    }
                });
        return count.get(0);
    }
}
