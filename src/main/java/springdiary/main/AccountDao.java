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

public class AccountDao {
    private JdbcTemplate jdbcTemplate;

    public void insert(final Account newAccount) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    @Override
                    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                        PreparedStatement pstmt = con.prepareStatement(
                                "insert into account (name, password) values (?,?)",
                                new String[]{"id"});
                        pstmt.setString(1, newAccount.getName());
                        pstmt.setString(2, newAccount.getPassword());
                        return pstmt;
                    }
                },
                keyHolder);
        Number keyValue = keyHolder.getKey();
        newAccount.setId(keyValue.longValue());
    }

    public void updatePassword(Account account){
        jdbcTemplate.update("update account set password = ? "
                + "where name = ?",
                account.getPassword(), account.getName());

    }

    public void updateUserName(Account account){
        jdbcTemplate.update("update account set userName = ? "
                        + "where name = ?",
                account.getUserName(), account.getName());

    }

    public void updatePhoneNumber(Account account){
        jdbcTemplate.update("update account set phoneNumber = ? "
                        + "where name = ?",
                account.getPhoneNumber(), account.getName());

    }

    public void updateAddress(Account account){
        jdbcTemplate.update("update account set address = ? "
                        + "where name = ?",
                account.getAddress(), account.getName());

    }

    public AccountDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Account selectByName(String name){
        List<Account> results = jdbcTemplate.query("select * from account where name = ?",
                new RowMapper<Account>() {
                    @Override
                    public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Account account = new Account(rs.getString("name"),
                                rs.getString("password"),
                                rs.getString("userName"),
                                rs.getString("phoneNumber"),
                                rs.getString("address"));
                        account.setId(rs.getLong("id"));
                        return account;
                    }
                }, name);
        return results.isEmpty() ? null : results.get(0);
    }

    public int count(){
        List<Integer> count = jdbcTemplate.query("select count(*) from account",
                new RowMapper<Integer>() {
                    @Override
                    public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return rs.getInt(1);
                    }
                });
        return count.get(0);
    }
}
