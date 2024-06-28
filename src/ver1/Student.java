package ver1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Student {
	public static final String ADD_STUDENT = " INSERT INTO students(name, age, email) VALUES( ?, ?, ?) ";
	public static final String VIEW_STUDENT = " SELECT * FROM students ";
	public static final String REVISE_STUDENT_NAME = " UPDATE students SET name = ? where name = ? ";
	public static final String REVISE_STUDENT_AGE = " UPDATE students SET age = ? where name = ? ";
	public static final String REVISE_STUDENT_EMAIL = " UPDATE students SET email = ? where name = ? ";
	public static final String DELETE_STUDENT = " DELETE FROM students where name = ? ";

	public static void main(String[] args) {
		try (Connection conn = DBConnectionManager.getConnection(); Scanner sc = new Scanner(System.in);) {
			while (true) {
				System.out.println();
				System.out.println("---------------------");
				System.out.println("1. 학생 정보 추가");
				System.out.println("2. 학생 정보 조회");
				System.out.println("3. 학생 정보 수정");
				System.out.println("4. 학생 정보 삭제");
				System.out.println("5. 종료");
				System.out.println();
				System.out.print("옵션을 선택 하세요 : ");

				int choice = sc.nextInt();
				if (choice == 1) {
					addStudent(conn, sc);
				} else if (choice == 2) {
					viewStudentInfo(conn);
				} else if (choice == 3) {
					reviseStudent(conn, sc);
				} else if (choice == 4) {
					deleteStudent(conn, sc);
				} else if (choice == 5) {
					System.out.println("종료합니다.");
					break;
				} else {
					System.out.println("지정된 숫자를 입력하시오.");
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void addStudent(Connection conn, Scanner sc) {
		System.out.println();
		System.out.print("학생 이름을 입력하세요 : ");
		String username = sc.next();
		System.out.print("나이 : ");
		int age = sc.nextInt();
		System.out.println("이메일 : ");
		String email = sc.next();

		try (PreparedStatement psmt = conn.prepareStatement(ADD_STUDENT)) {
			psmt.setString(1, username);
			psmt.setInt(2, age);
			psmt.setString(3, email);

			psmt.executeUpdate();
			
		} catch (Exception e) {
		}
	}

	private static void viewStudentInfo(Connection conn) {

		try {
			PreparedStatement psmt = conn.prepareStatement(VIEW_STUDENT);
			ResultSet rs = psmt.executeQuery();

			while (rs.next()) {
				System.out.println("이름 : " + rs.getString("name"));
				System.out.println("나이 : " + rs.getString("age"));
				System.out.println("email : " + rs.getString("email"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void reviseStudent(Connection conn, Scanner sc) {
		try {
			System.out.println("1. 이름 변경");
			System.out.println("2. 나이 변경");
			System.out.println("3. 이메일 변경");

			int choice = sc.nextInt();
			if (choice == 1) {
				PreparedStatement pr = conn.prepareStatement(REVISE_STUDENT_NAME);
				System.out.println();
				System.out.print("변경할 이름을 입력하시오 : ");
				String whereName = sc.next();
				System.out.print("이름을 무엇으로 바꾸시겠습니까? : ");
				String setName = sc.next();

				pr.setString(2, whereName);
				pr.setString(1, setName);

				pr.executeUpdate();
			} else if (choice == 2) {
				PreparedStatement pr = conn.prepareStatement(REVISE_STUDENT_AGE);
				System.out.println();
				System.out.print("변경할 이름을 입력하시오 : ");
				String whereName = sc.next();
				System.out.print("바꿀 나이는? : ");
				String setName = sc.next();

				
				pr.setString(2, whereName);
				pr.setString(1, setName);

				pr.executeUpdate();
				System.out.println("나이 바꾸기 완료");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void deleteStudent(Connection conn, Scanner sc) {
		try {
			PreparedStatement psmt = conn.prepareStatement(DELETE_STUDENT);
			System.out.println();
			System.out.print("삭제할 이름을 입력하시오 : ");
			String deleteName = sc.next();

			psmt.setString(1, deleteName);

			psmt.executeUpdate();
			System.out.println("이름에서 " + deleteName + "을 삭제하셨습니다.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
