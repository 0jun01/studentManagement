package ver2;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

// 싱글톤 패턴 - 단 하나의 객체만 필요함을 보장해야 된다면
// 싱글톤 패턴으로 설계 할 수 있다.

public class DBConnectionManager {

	// 자기 자신의 참조 주소값을 담을 변수 생성 단. private
	private static DBConnectionManager instance;
	private HikariDataSource dataSource;

	// 생성자를 <---- 외부에서 생성자를 호출 못 하게 막아야 한다.
	private DBConnectionManager() {
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl("jdbc:mysql://localhost:3306/demo3?serverTimezone=Asia/Seoul");
		config.setUsername("root");
		config.setPassword("asd123");
		config.setMaximumPoolSize(10);
		dataSource = new HikariDataSource(config);
	}

	// 외부에서 클래스이름.getxxxx 메서드를 만들어 주면 된다.
	// 한 번에 하나의 스레드만 접근하도록 동기화 적용
	public synchronized static DBConnectionManager getInstance() {
		if (instance == null) {
			instance = new DBConnectionManager();
		}
		return instance;
	}

	// Connection 객체를 반환(구현체 - 히카리)
	public Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}
}
