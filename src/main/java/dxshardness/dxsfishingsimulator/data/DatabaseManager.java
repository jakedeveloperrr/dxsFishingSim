package dxshardness.dxsfishingsimulator.data;

import org.bukkit.entity.Player;

import java.sql.*;

public class DatabaseManager {
    public static DatabaseManager instance = new DatabaseManager();
    private final Connection connection;
    private final Statement statement;

    public DatabaseManager() {
        try {
            // подключаемся к бд
            connection = DriverManager.getConnection("jdbc:sqlite:server-db.sqlite");
            statement = connection.createStatement();
            // создаём таблицы в бд
            statement.execute(
                    "CREATE TABLE IF NOT EXISTS player_fishing_statistic(" +
                            "player_uuid TEXT, " +
                            "amount_of_catch INTEGER, " +
                            "amount_of_success_catch INTEGER, " +
                            "amount_of_fail_catch INTEGER, " +
                            "amount_of_legendary_rare_item_catch INTEGER" +
                            ")"
            );
            statement.execute("CREATE TABLE IF NOT EXISTS player_data (" +
                    "uuid TEXT PRIMARY KEY," +
                    "has_booster BOOLEAN" +
                    ")");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Методы добавляющие значения в БД
    public void incrementCatchCount(Player player) {
        try {
            statement.execute("UPDATE player_fishing_statistic SET amount_of_catch = amount_of_catch + 1 WHERE player_uuid = '" + player.getUniqueId().toString() + "';");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void incrementFailedCatchCount(Player player) {
        try {
            statement.execute("UPDATE player_fishing_statistic SET amount_of_fail_catch = amount_of_fail_catch + 1 WHERE player_uuid = '" + player.getUniqueId().toString() + "';");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void incrementSuccessCatchCount(Player player) {
        try {
            statement.execute("UPDATE player_fishing_statistic SET amount_of_success_catch = amount_of_success_catch + 1 WHERE player_uuid = '" + player.getUniqueId().toString() + "';");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void incrementLegendaryItemCatchCount(Player player) {
        try {
            statement.execute("UPDATE player_fishing_statistic SET amount_of_legendary_catch = amount_of_legendary_catch + 1 WHERE player_uuid = '" + player.getUniqueId().toString() + "';");
        } catch (SQLException e) {
            throw new  RuntimeException(e);
        }
    }

    // GETTERs
    public int getCatchCount(String playerUUID) {
        int count = 0;

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT amount_of_catch FROM player_fishing_statistic WHERE player_uuid = ?")) {
            preparedStatement.setString(1, playerUUID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    count = resultSet.getInt("amount_of_catch");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }
    public int getSuccessCatchCount(String playerUUID) {
        int successCount = 0;

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT amount_of_success_catch FROM player_fishing_statistic WHERE player_uuid = ?")) {
            preparedStatement.setString(1, playerUUID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    successCount = resultSet.getInt("amount_of_success_catch");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return successCount;
    }

    public int getFailedCatchCount(String playerUUID) {
        int failedCount = 0;

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT amount_of_fail_catch FROM player_fishing_statistic WHERE player_uuid = ?")) {
            preparedStatement.setString(1, playerUUID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    failedCount = resultSet.getInt("amount_of_fail_catch");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return failedCount;
    }

    public int getLegendaryItemCatchCount(String playerUUID) {
        int legendaryItemCount = 0;

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT amount_of_legendary_rare_item_catch FROM player_fishing_statistic WHERE player_uuid = ?")) {
            preparedStatement.setString(1, playerUUID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    legendaryItemCount = resultSet.getInt("amount_of_legendary_rare_item_catch");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return legendaryItemCount;
    }
}
