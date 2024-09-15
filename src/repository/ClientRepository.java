package repository;


import config.Database;
import domain.entities.Client;
import repository.interfaces.ClientInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ClientRepository implements ClientInterface<Client> {

    private Connection connection;

    public ClientRepository(Connection connection) throws SQLException {
        this.connection = Database.getInstance().getConnection();
    }

    @Override
    public Client save(Client client) {

        try {
            connection.setAutoCommit(false);
            String query = "INSERT INTO clients (id , name, address, phone, isProfessional) VALUES (? , ?, ?, ?, ?);";
            var preparedStatement = connection.prepareStatement(query);
            UUID id = UUID.randomUUID();
            preparedStatement.setObject(1, id);
            preparedStatement.setString(2, client.getName());
            preparedStatement.setString(3, client.getaddress());
            preparedStatement.setString(4, client.getphone());
            preparedStatement.setBoolean(5, client.isProfessional());
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }

        return client;
    }

    @Override
    public Optional<Client> findById(Client client) {
        String query = "SELECT * FROM clients WHERE id = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setObject(1, client.getId());  // Use UUID

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Client foundClient = new Client();
                    foundClient.setId((UUID) resultSet.getObject("id"));
                    foundClient.setName(resultSet.getString("name"));
                    foundClient.setaddress(resultSet.getString("address"));
                    foundClient.setphone(resultSet.getString("phone"));
                    foundClient.setProfessional(resultSet.getBoolean("isProfessional"));

                    return Optional.of(foundClient);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Client> findAll() {
        try {
            connection.setAutoCommit(false);
            String query = "SELECT * FROM clients";
            var preparedStatement = connection.prepareStatement(query);
            var resultSet = preparedStatement.executeQuery();
            List<Client> clients = new ArrayList<>();
            connection.commit();
            while (resultSet.next()) {
                Client client = new Client();
                client.setId((UUID) resultSet.getObject("id"));
                client.setName(resultSet.getString("name"));
                client.setaddress(resultSet.getString("address"));
                client.setphone(resultSet.getString("phone"));
                client.setProfessional(resultSet.getBoolean("isProfessional"));
                clients.add(client);
            }
            return clients;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return List.of();
        }
    }

    @Override
    public Client update(Client client) {
        try {
            connection.setAutoCommit(false);
            String query = "UPDATE clients SET name = ?, address = ?, phone = ?, isProfessional = ? WHERE id = ?";
            var preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, client.getName());
            preparedStatement.setString(2, client.getaddress());
            preparedStatement.setString(3, client.getphone());
            preparedStatement.setBoolean(4, client.isProfessional());
            preparedStatement.setObject(5, client.getId());
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
        return client;
    }

    @Override
    public boolean delete(Client client) {
        try {
            connection.setAutoCommit(false);
            String query = "DELETE FROM clients WHERE id = ?";
            var preparedStatement = connection.prepareStatement(query);
            preparedStatement.setObject(1, client.getId());
            preparedStatement.executeUpdate();
            connection.commit();
            return true;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        }
    }
}
