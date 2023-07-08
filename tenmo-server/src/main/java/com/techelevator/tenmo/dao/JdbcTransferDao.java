package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Transfer getTransferById(int transferId) {
        String sql = "SELECT * FROM transfer " +
                "WHERE transfer_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferId);
        if (results.next()) {
            return mapRowToTransfer(results);
        } else {
            return null;
        }
    }
    @Override
    public Transfer createTransfer(Transfer transfer) {
        Transfer newTransfer = null;

        String sql = "INSERT INTO transfer VALUES (DEFAULT, ?, ?, (SELECT account_id from account WHERE user_id = ?), "
                +  "(SELECT account_id from account WHERE user_id = ?), ?) RETURNING transfer_id";
        try {
            Integer newTransferId = jdbcTemplate.queryForObject(sql, Integer.class,
                    transfer.getTransferTypeId(), transfer.getTransferStatusId(), transfer.getAccountFrom(),
                    transfer.getAccountTo(), transfer.getAmount());
            transfer.setTransferId(newTransferId);

        } catch (CannotGetJdbcConnectionException e) {
            System.out.println("Connection Error.");
        } catch (BadSqlGrammarException e) {
            System.out.println("Bad SQL Grammar");
        } catch (DataIntegrityViolationException e) {
            System.out.println("Date Integrity Violation.");
        }
        return transfer;
    }

    @Override
    public Transfer getTransferTypeId(int transferTypeId) {
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount FROM transfer " +
                "WHERE transfer_type_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferTypeId);
        if (results.next()) {
            return mapRowToTransfer(results);
        } else {
            return null;
        }
    }

    @Override
    public Transfer getTransferStatusId(int transferStatusId) {
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount FROM transfer " +
                "WHERE transfer_status_id = ?";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferStatusId);
        if (results.next()) {
            return mapRowToTransfer(results);
        } else {
            return null;
        }
    }

    @Override
    public Transfer getFromTransfer(int accountFromId) {
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount FROM transfer" +
                "WHERE account_from = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountFromId);
        if (results.next()) {
            return mapRowToTransfer(results);
        } else {
            return null;
        }

    }

    @Override
    public Transfer getAccountToId(int accountToId) {
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount FROM transfer " +
                "WHERE account_to = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountToId);
        if (results.next()) {
            return mapRowToTransfer(results);
        } else {
            return null;
        }
    }

    @Override
    public Transfer getTransferAmount(double transferAmount) {
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount FROM transfer " +
                "WHERE amount = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferAmount);
        if (results.next()) {
            return mapRowToTransfer(results);
        } else {

            return null;
        }
    }

    public List<Transfer> getAllTransfers() {
        List<Transfer> transfers = new ArrayList<>();
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount FROM transfer ";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            Transfer transfer = mapRowToTransfer(results);
            transfers.add(transfer);
        }
        return transfers;
    }

    public Transfer getTransferStatus(int transferStatusId) {
        String sql = "SELECT t.transfer_status_id, transfer_status_desc FROM transfer t " +
                "JOIN transfer_status ts ON t.transfer_status_id = ts.transfer_status_id WHERE ts.transfer_status_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferStatusId);
        if (results.next()) {
            return mapRowToTransfer(results);
        } else {
            return null;
        }

    }

    public Transfer getTransferType(int transferTypeId) {
        String sql = "SELECT * FROM transfer t\n" +
                "JOIN transfer_type tt ON tt.transfer_type_id = t.transfer_type_id\n" +
                "WHERE t.transfer_type_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferTypeId);
        if (results.next()) {
            return mapRowToTransfer(results);
        } else {
            return null;

        }

    }

    public Transfer getUserById(int accountId) {
        String sql = "SELECT * FROM transfer t\n" +
                "JOIN account a ON t.account_from = a.account_id\n" +
                "JOIN tenmo_user tu ON a.user_id = tu.user_id\n" +
                "WHERE a.user_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountId);
        if (results.next()) {
            return mapRowToTransfer(results);
        } else {
            return null;
        }
    }



    private Transfer mapRowToTransfer(SqlRowSet rs) {
        Transfer transfer = new Transfer();
        transfer.setTransferId(rs.getInt("transfer_id"));
        transfer.setTransferTypeId(rs.getInt("transfer_type_id"));
        transfer.setTransferStatusId(rs.getInt("transfer_status_id"));
        transfer.setAccountFrom(rs.getInt("account_from"));
        transfer.setAccountTo(rs.getInt("account_to"));
        transfer.setAmount(rs.getDouble("amount"));
        return transfer;
    }
}


