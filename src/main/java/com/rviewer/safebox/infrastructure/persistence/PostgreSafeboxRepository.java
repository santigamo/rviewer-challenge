package com.rviewer.safebox.infrastructure.persistence;

import com.rviewer.safebox.domain.SafeBox;
import com.rviewer.safebox.domain.SafeBoxRepository;
import com.rviewer.safebox.domain.exceptions.AuthorizationException;
import com.rviewer.safebox.domain.exceptions.SafeboxAlreadyExistsException;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.Array;
import java.sql.Connection;
import java.util.List;

@Log4j2
@AllArgsConstructor
public class PostgreSafeboxRepository implements SafeBoxRepository {

    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public void save(SafeBox safeBox) {
        //? Save the new safebox
        var query = "INSERT INTO safebox (id, name, password) VALUES (:id, :name, :password)";
        var parameters = new MapSqlParameterSource()
                .addValue("id", safeBox.getId().getUuid())
                .addValue("name", safeBox.getName())
                .addValue("password", safeBox.getPassword());

        //? Execute query
        try {
            jdbcTemplate.update(
                    query,
                    parameters
            );
        } catch (Exception e) {
            log.error("Safebox {} already exists", safeBox.getName());
            throw new SafeboxAlreadyExistsException();
        }
        log.debug("Saved safebox with name: {} and id: {}", safeBox.getName(), safeBox.getId().getUuid());
    }

    @SneakyThrows
    @Override
    public List<String> findItemsBy(String id) {
        //? Get items from the given safebox
        val query = "SELECT items FROM safebox WHERE id = :id";
        val parameters = new MapSqlParameterSource()
                .addValue("id", id);

        //? Execute query
        var response = jdbcTemplate.queryForList(
                query,
                parameters,
                Array.class
        ).get(0);

        //? Parse the response
        String[] items = (String[]) response.getArray();
        log.debug("Listing {} items for safebox id: {}", items.length, id);

        return List.of(items);
    }

    @Override
    public void addItemsTo(String id, List<String> items) {
        //? Convert List to SQL Array
        val itemsArray = jdbcTemplate.getJdbcOperations().execute((Connection connection) ->
                connection.createArrayOf("text", items.toArray())
        );

        //? Add items to safebox
        val query = "UPDATE safebox SET items = items || :items WHERE id = :id";
        val parameters = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("items", itemsArray);

        //? Execute query
        jdbcTemplate.update(query, parameters);
        log.debug("Added {} items to safebox id: {}", items.size(), id);
    }

    @SneakyThrows
    @Override
    public boolean checkAuthentication(String name, String password) {
        //? Check if the given safebox exists
        val query = "SELECT * FROM safebox WHERE name = :name AND password = :password";
        val parameters = new MapSqlParameterSource()
                .addValue("name", name)
                .addValue("password", password);

        //? Execute query
        var response = jdbcTemplate.queryForList(query, parameters);
        if (response.isEmpty()) {
            log.error("User {} tried to access safebox with wrong password", name);
            throw new AuthorizationException();
        } else {
            return true;
        }
    }

    @SneakyThrows
    @Override
    public void checkAuthorization(String name, String id) {
        //? Check if user has access to the given safebox
        val query = "SELECT * FROM safebox WHERE name = :name AND id = :id";
        val parameters = new MapSqlParameterSource()
                .addValue("name", name)
                .addValue("id", id);

        //? Execute query
        var response = jdbcTemplate.queryForList(query, parameters);

        if (response.isEmpty()) {
            log.error("User {} tried to access safebox id: {} without authorization", name, id);
            throw new AuthorizationException();
        }
    }
}
