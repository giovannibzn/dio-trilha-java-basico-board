	package dio.giovanni;


	import dio.giovanni.persistence.migration.MigrationStrategy;
	import dio.giovanni.ui.MainMenu;

	import java.sql.SQLException;

	import static dio.giovanni.persistence.config.ConnectionConfig.getConnection;

	public class Main {

		public static void main(String[] args) throws SQLException {
			try (var connection = getConnection()){
				new MigrationStrategy(connection).executeMigration();
			}
			new MainMenu().execute();
		}

	}
