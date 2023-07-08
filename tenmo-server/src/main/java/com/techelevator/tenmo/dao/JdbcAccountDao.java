package com.techelevator.tenmo.dao;


import com.techelevator.tenmo.model.Account;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class JdbcAccountDao implements AccountDao{
    private static List<Account> accounts = new ArrayList<>();
    private final JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public Account getAccountById(int accountId) {
        String sql = "SELECT account_id, balance, user_id FROM account WHERE user_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountId);
        if (results.next()) {
            return mapRowToUser(results);
        } else {
            return null;
        }
    }

    @Override
    public Account updateBalances(Account accountTo, Account accountFrom) {
        return null;
    }

    @Override
    public Account updateToBalance(Account accountTo, Account accountFrom) {
        return null;
    }

    @Override
    public void updateToBalance(Account accountTo) {
        Account updatedAccountBalance = null;
        String updateToBalance = "SELECT * FROM account a\n" +
                "JOIN transfer t ON account_to = account_id;\n" +
                "UPDATE account SET balance = balance + (SELECT amount FROM transfer WHERE account_to = ?)\n" +
                "WHERE account_id = ?;";
        try {
            Integer results = jdbcTemplate.update(updateToBalance, accountTo.getAccountId(), accountTo.getAccountId());
            if (results == 0) {
                System.out.println("Now rows were updatead");
            }
            updatedAccountBalance = getAccountById(accountTo.getAccountId());

        } catch (CannotGetJdbcConnectionException e) {
            throw new RuntimeException("Unable to connect to server or database", e);
        } catch (BadSqlGrammarException e) {
            throw new RuntimeException("SQL code error", e);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Data integrity violation", e);
        }
    }

    @Override
    public void updateFromBalance(Account accountFrom) {
        Account updatedAccountBalance = null;
        String updateFromBalance = "SELECT * FROM account a\n" +
                "JOIN transfer t ON account_from = account_id;\n" +
                "UPDATE account SET balance = balance - (SELECT amount FROM transfer WHERE account_from = ?)\n" +
                "WHERE account_id = ?;";
        try {
            Integer results = jdbcTemplate.update(updateFromBalance, accountFrom.getAccountId(), accountFrom.getAccountId());
            if (results == 0) {
                System.out.println("Now rows were updated");
            }
            updatedAccountBalance = getAccountById(accountFrom.getAccountId());


        } catch (CannotGetJdbcConnectionException e) {
            throw new RuntimeException("Unable to connect to server or database", e);
        } catch (BadSqlGrammarException e) {
            throw new RuntimeException("SQL code error", e);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Data integrity violation", e);
        }
    }


    @Override
    public List<Account> getAccounts() {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT account_id, balance, user_id FROM account;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            Account account = mapRowToUser(results);
            accounts.add(account);
        }
        return accounts;
    }
    @Override
    public Account getBalance(int id) {
        String sql = "SELECT account_id, user_id, balance FROM account WHERE user_id = ?";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, id);
        if (rowSet.next()) {
            return mapRowToUser(rowSet);
        }
        return null;
    }

    @Override
    public Account getUserId(int id) {
        return null;
    }

    private Account mapRowToUser(SqlRowSet rs) {
        Account account = new Account();
        account.setAccountId(rs.getInt("account_id"));
        account.setUserId(rs.getInt("user_id"));
        account.setBalance(rs.getDouble("balance"));
        return account;
    }

}
