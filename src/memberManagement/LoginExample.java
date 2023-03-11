package memberManagement;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginExample {

	public static final int MEMBER_JOIN = 1, MEMBER_LOGIN = 2, MEMBER_SEARCH = 3, EXIT = 4;
	public static final int MODIFY_INFORMATION = 1, MEMBERSHIP_WITHDRAWAL = 2, MEMBER_LOGOUT = 3;
	public static final int MEMBER_ID = 1, MEMBER_PASSWORD = 2, MEMBER_NAME = 3, MEMBER_DATEBIRTH = 4, MEMBER_EMAIL = 5,
			MEMBER_PHONENUMBER = 6, MEMBER_EXIT = 7;
	public static Scanner sc = new Scanner(System.in);
	public static DBConnection dbCon = null;

	public static void main(String[] args) {
		boolean isRun = true;
		boolean isLogin = false;
		boolean isRunInfoSelect = true;
		boolean isRunInfoEdit = true;
		dbCon = new DBConnection();
		Login loginData = null;

		while (isRun) {
			if (isLogin) {
				System.out.println("========================================================================");
				System.out.println("\t           [1]회원정보 수정 | [2]회원탈퇴 | [3]로그아웃");
				System.out.println("========================================================================");
				System.out.print("원하시는 메뉴를 선택해 주세요. >> ");

				switch (Integer.parseInt(sc.nextLine())) {

				case MODIFY_INFORMATION:
					isRunInfoEdit = true;
					while (isRunInfoEdit) {
						System.out.println("========================================================================");
						System.out.println("[1]아이디 | [2]비밀번호 | [3]이름 | [4]생년월일 | [5]이메일 | [6]전화번호 | [7]회원정보 수정 화면 나가기");
						System.out.println("========================================================================");
						System.out.print("수정하실 항목을 선택해 주세요. >> ");

						switch (Integer.parseInt(sc.nextLine())) {
						case MEMBER_ID:
							String id = inputId();
							int rValue1 = dbCon.modify("id", loginData.getId(), id);
							if (rValue1 == 1) {
								System.out.println("아이디 수정 완료");
							} else {
								System.err.println("아이디 수정 실패");
							}
							break;

						case MEMBER_PASSWORD:
							String password = inputPassword();
							int rValue2 = dbCon.modify("password", loginData.getId(), password);
							if (rValue2 == 1) {
								System.out.println("비밀번호 수정 완료");
							} else {
								System.err.println("비밀번호 수정 실패");
							}
							break;

						case MEMBER_NAME:
							String name2 = inputName();
							int rValue3 = dbCon.modify("name", loginData.getId(), name2);
							if (rValue3 == 1) {
								System.out.println("이름 수정 완료");
							} else {
								System.err.println("이름 수정 실패");
							}
							break;

						case MEMBER_DATEBIRTH:
							String birth = inputDateBirth();
							int rValue4 = dbCon.modify("datebirth", loginData.getId(), birth);
							if (rValue4 == 1) {
								System.out.println("생년월일 수정 완료");
							} else {
								System.err.println("생년월일 수정 실패");
							}
							break;

						case MEMBER_EMAIL:
							String email = inputEmail();
							int rValue5 = dbCon.modify("email", loginData.getId(), email);
							if (rValue5 == 1) {
								System.out.println("이메일 수정 완료");
							} else {
								System.err.println("이메일 수정 실패");
							}
							break;

						case MEMBER_PHONENUMBER:
							String phoneNumber = inputPhoneNumber();
							int rValue6 = dbCon.modify("phonenumber", loginData.getId(), phoneNumber);
							if (rValue6 == 1) {
								System.out.println("전화번호 수정 완료");
							} else {
								System.err.println("전화번호 수정 실패");
							}
							break;
						case MEMBER_EXIT:
							isRunInfoEdit = false;
							break;
						default:
							System.err.println("메뉴를 확인해 주세요.");
							break;
						}
					}
					break;
				case MEMBERSHIP_WITHDRAWAL:
					int rValue7 = dbCon.membershipWithdrawal(loginData.getId(), loginData.getPassword());
					if (rValue7 == 1) {
						System.out.println("회원탈퇴가 완료되었습니다.\r\n" + "다음에 또 만나요.");
					} else {
						System.err.println("회원탈퇴 오류가 발생했습니다.\r\n" + "잠시 후 재시도 부탁드립니다.");
					}
					isLogin = false;
					break;
				case MEMBER_LOGOUT:
					System.out.println("로그아웃이 완료되었습니다.");
					isLogin = false;
					break;
				default:
					System.err.println("메뉴를 확인해 주세요.");
					break;
				}
			} else {
				System.out.println("========================================================================");
				System.out.println("\t      [1]회원가입 | [2]로그인 | [3]회원정보 찾기 | [4]종료");
				System.out.println("========================================================================");
				System.out.print("원하시는 메뉴를 선택해 주세요. >> ");

				switch (Integer.parseInt(sc.nextLine())) {
				case MEMBER_JOIN:
					System.out.println("정상적인 회원가입을 위해 아래 개인 정보를 빠짐없이 입력해 주세요.");
					loginData = inputJoinInfo();
					int rValue = dbCon.insert(loginData);
					if (rValue == 1) {
						System.out.println("회원가입 완료");
						isLogin = true;
					} else {
						System.err.println("회원가입 오류가 발생했습니다.\r\n" + "잠시 후 재시도 부탁드립니다.");
					}
					break;
				case MEMBER_LOGIN:
					System.out.println("아이디와 비밀번호를 입력해 주세요.");
					loginData = loginUser();
					ArrayList<Login> list2 = dbCon.infSearchSelect(loginData.getId(), loginData.getPassword());
					if (list2.size() >= 1) {
						System.out.println("로그인 성공");
						isLogin = true;
						break;
					} else {
						System.err.println("로그인 오류가 발생했습니다.\r\n" + "잠시 후 재시도 부탁드립니다.");
					}
					break;
				case MEMBER_SEARCH:
					isRunInfoSelect = true;
					while (isRunInfoSelect) {
						System.out.println("========================================================================");
						System.out.println("\t               [1]아이디 찾기 | [2]비밀번호 찾기 | [3]회원정보 찾기 화면 나가기");
						System.out.println("========================================================================");
						System.out.print("원하시는 메뉴를 선택해 주세요. >> ");
						int selectMenu = Integer.parseInt(sc.nextLine());
						switch (selectMenu) {
						case 1:
							System.out.print("회원정보에 등록하신 이름을 입력해주세요. >> ");
							String selectName1 = sc.nextLine();
							System.out.print("회원정보에 등록하신 전화번호(-포함)을 입력해주세요. >> ");
							String selectPhoneNumber1 = sc.nextLine();
							String searchId = dbCon.searchInfo1(selectName1, selectPhoneNumber1);
							if (searchId == null) {
								System.err.println("일치하는 회원정보가 없습니다.\r\n" + "입력하신 내용을 확인 후 재시도 부탁드립니다.");
							} else {
								System.out.println("찾으시는 아이디는 다음과 같습니다.\r\n" + searchId);
								isRunInfoSelect = false;
							}
							break;
						case 2:
							System.out.print("회원정보에 등록하신 아이디를 입력해주세요. >> ");
							String selectId = sc.nextLine();
							System.out.print("회원정보에 등록하신 이름을 입력해주세요. >> ");
							String selectName2 = sc.nextLine();
							System.out.print("회원정보에 등록하신 전화번호(-포함)을 입력해주세요. >> ");
							String selectPhoneNumber2 = sc.nextLine();
							String searchPw = dbCon.searchInfo2(selectId, selectName2, selectPhoneNumber2);
							if (searchPw == null) {
								System.err.println("일치하는 회원정보가 없습니다.\r\n" + "입력하신 내용을 확인 후 재시도 부탁드립니다.");
							} else {
								System.out.println("찾으시는 비밀번호는 다음과 같습니다.\r\n" + searchPw);
								isRunInfoSelect = false;
							}
							break;
						case 3:
							isRunInfoSelect = false;
							break;
						default:
							System.err.println("메뉴를 확인해 주세요.");
							break;
						}
					}
					break;
				case EXIT:
					System.out.println("프로그램 종료");
					isRun = false;
					break;
				default:
					System.err.println("메뉴를 확인해 주세요.");
					continue;
				}
			}
		}
	}

	private static Login loginUser() {
		String id = null;
		String password = null;

		while (true) {
			System.out.print("- 아이디 입력: ");
			id = sc.nextLine();
			System.out.print("- 비밀번호 입력: ");
			password = sc.nextLine();

			Login loginData = new Login(0, id, password, null, null, null, null, null);
			ArrayList<Login> list = dbCon.infSearchSelect(loginData.getId(), loginData.getPassword());
			if (list == null || list.isEmpty()) {
				System.err.println("아이디 또는 비밀번호를 잘못 입력했습니다.\r\n" + "입력하신 내용을 다시 확인해주세요.");
				continue;
			} else {
				return list.get(0);
			}
		}
	}

	private static Login inputJoinInfo() {
		String id = inputId();
		String password = inputPassword();
		String name = inputName();
		String datebirth = inputDateBirth();
		String gender = inputGender();
		String email = inputEmail();
		String phoneNumber = inputPhoneNumber();
		Login login = new Login(id, password, name, datebirth, gender, email, phoneNumber);
		return login;
	}

	private static String inputPhoneNumber() {
		String phoneNumber = null;

		while (true) {
			try {
				System.out.print("- 전화번호(-포함) 입력: ");
				phoneNumber = sc.nextLine();

				String returnPhoneNumber = dbCon.selectPhoneNumber(phoneNumber);

				if (returnPhoneNumber != null) {
					System.err.println("이미 가입된 번호입니다.");
					continue;
				}
				if (phoneNumber.isEmpty()) {
					System.err.println("!필수 입력 정보입니다.");
					continue;
				}
				Pattern pattern = Pattern.compile("^\\d{3}-\\d{4}-\\d{4}$");
				Matcher matcher = pattern.matcher(phoneNumber);
				if (matcher.find()) {
					break;
				} else {
					System.err.println("형식에 맞지 않는 번호입니다.");
				}
			} catch (Exception e) {
				System.err.println("전화번호 입력 오류");
				phoneNumber = null;
			}
		}
		return phoneNumber;
	}

	private static String inputEmail() {
		String email = null;

		while (true) {
			try {
				System.out.print("- 이메일 입력: ");
				email = sc.nextLine();
				if (email.isEmpty()) {
					System.err.println("!필수 입력 정보입니다.");
					continue;
				}
				Pattern pattern = Pattern
						.compile("^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$");
				Matcher matcher = pattern.matcher(email);
				if (matcher.find()) {
					break;
				} else {
					System.err.println("이메일 주소를 다시 확인해주세요.");
				}
			} catch (Exception e) {
				System.err.println("이메일 입력 오류");
				email = null;
			}
		}
		return email;
	}

	private static String inputGender() {
		String gender = null;

		while (true) {
			try {
				System.out.print("- 성별(남자|여자) 입력: ");
				gender = sc.nextLine();
				if (gender.isEmpty()) {
					System.err.println("!필수 입력 정보입니다.");
					continue;
				}
				if (gender.equals("남자") || gender.equals("여자")) {
					break;
				} else {
					System.err.println("형식에 맞게 입력해주세요.");
				}
			} catch (Exception e) {
				System.err.println("성별 입력 오류");
				gender = null;
			}
		}
		return gender;
	}

	private static String inputDateBirth() {
		String datebirth = null;

		while (true) {
			try {
				System.out.print("- 생년월일(YYYY-MM-DD) 입력: ");
				datebirth = sc.nextLine();
				if (datebirth.isEmpty()) {
					System.err.println("!필수 입력 정보입니다.");
					continue;
				}
				Pattern pattern = Pattern
						.compile("^(19[0-9][0-9]|20\\d{2})-(0[0-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$");
				Matcher matcher = pattern.matcher(String.valueOf(datebirth));
				if (matcher.find()) {
					break;
				} else {
					System.err.println("YYYY-MM-DD 형식으로 입력해주세요.");
				}
			} catch (Exception e) {
				System.err.println("생년월일 입력 오류");
				datebirth = null;
			}
		}
		return datebirth;
	}

	private static String inputName() {
		String name = null;

		while (true) {
			try {
				System.out.print("- 이름(한글) 입력: ");
				name = sc.nextLine();
				if (name.isEmpty()) {
					System.err.println("!필수 입력 정보입니다.");
					continue;
				}
				Pattern pattern = Pattern.compile("^[가-힣]{2,5}$");
				Matcher matcher = pattern.matcher(name);
				if (matcher.find()) {
					break;
				} else {
					System.err.println("2~5자리 이내 한글만 입력 가능합니다.");
				}
			} catch (Exception e) {
				System.err.println("이름 입력 오류");
				name = null;
			}
		}
		return name;
	}

	private static String inputPassword() {
		String password1 = null;
		String password2 = null;
		boolean flag = false;

		while (true) {
			try {
				System.out.print("- 비밀번호(숫자, 문자, 특수문자 포함 8~15자리 이내) 입력: ");
				password1 = sc.nextLine();
				if (password1.isEmpty()) {
					System.err.println("!필수 입력 정보입니다.");
					continue;
				}
				Pattern pattern = Pattern
						.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,15}$");
				Matcher matcher = pattern.matcher(password1);
				if (matcher.find()) {
					break;
				} else {
					System.err.println("8~15자리 이내 숫자, 문자, 특수문자만 사용 가능합니다.");
				}
			} catch (Exception e) {
				System.err.println("비밀번호 입력 오류");
				password1 = null;
			}
		}

		while (true) {
			try {
				System.out.print("- 비밀번호 재확인: ");
				password2 = sc.nextLine();
				if (password2.equals(password1)) {
					flag = true;
					break;
				}
				if (flag == false) {
					System.err.println("비밀번호가 일치하지 않습니다.");
					continue;
				}
			} catch (Exception e) {
				System.err.println("비밀번호 확인 오류");
			}
		}
		return password1;
	}

	private static String inputId() {
		String id = null;

		while (true) {
			try {
				System.out.print("- 아이디(영문, 숫자 포함 6~15자리 이내) 입력: ");
				id = sc.nextLine();
				if (id.isEmpty()) {
					System.err.println("!필수 입력 정보입니다.");
					continue;
				}

				String returnid = dbCon.selectId(id);

				if (returnid != null) {
					System.err.println("이미 존재하는 아이디입니다.");
					continue;
				}

				Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-zA-Z])[a-zA-Z0-9]{6,15}$");
				Matcher matcher = pattern.matcher(id);
				if (matcher.find()) {
					break;
				} else {
					System.err.println("6~15자리 이내 영문, 숫자만 사용 가능합니다.");
				}
			} catch (Exception e) {
				System.err.println("아이디 입력 오류");
				id = null;
			}
		}
		return id;
	}

}
