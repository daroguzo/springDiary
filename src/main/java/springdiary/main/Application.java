package springdiary.main;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;


public class Application {

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        AbstractApplicationContext ctx =
                new GenericXmlApplicationContext("classpath:appCtx.xml");

        AccountDao accountDao = ctx.getBean("accountDao", AccountDao.class);
        DiaryDao diaryDao = ctx.getBean("diaryDao", DiaryDao.class);
        Account loginAccount = new Account();


        int loginMenu = 0;
        int mainMenu = 0;
        int accountMenu = 0;
        int searchMenu = 0;
        boolean loginFlag = false;


        // 초기 화면
        while (true) {
            while (loginMenu != 1 && loginMenu != 2 && loginMenu != 9) {
                System.out.println("==== Diary ====");
                System.out.println("1. 로그인");
                System.out.println("2. 회원가입");
                System.out.println("9. 종료");
                System.out.println("===============");
                loginMenu = sc.nextInt();
                sc.nextLine();
            }
            // 로그인
            if(loginMenu == 1){
                while (mainMenu != 1 && mainMenu != 2 && mainMenu != 3 && mainMenu != 4 && mainMenu != 5 && mainMenu != 6 && mainMenu != 7 && mainMenu != 8 && mainMenu != 9){

                    // 로그인 인증
                    while (!loginFlag) {
                        try {
                            System.out.println("===============");
                            System.out.print("id 입력: ");
                            String id = sc.next();
                            sc.nextLine();
                            System.out.println(id);
                            Account account = accountDao.selectByName(id);
                            loginAccount.setName(id);
                            System.out.println("password 입력: ");
                            String password = sc.next();
                            sc.nextLine();
                            loginAccount.setPassword(password);
                            if (!account.getPassword().equals(password)) {
                                System.out.println("비밀번호가 맞지 않습니다!");
                            }else {
                                loginFlag = true;
                            }
                        } catch (Exception e) {
                            System.out.println("존재하지 않는 id 입니다!");
                            loginFlag = false;
                        }
                    }

                    System.out.println("=====Diary=====");
                    System.out.println("1. 비밀번호 변경");
                    System.out.println("2. 계정 정보 보기");
                    System.out.println("3. 계정 정보 수정");
                    System.out.println("4. 다이어리 추가");
                    System.out.println("5. 다이어리 보기");
                    System.out.println("6. 다이어리 수정");
                    System.out.println("7. 다이어리 삭제");
                    System.out.println("8. 검색");
                    System.out.println("9. 종료");
                    System.out.println("===============");
                    mainMenu = sc.nextInt();
                    sc.nextLine();

                    // 비밀번호 변경
                    if(mainMenu == 1){
                        System.out.println("===============");
                        System.out.println("새로운 password를 입력");
                        loginAccount.setPassword(sc.next());
                        sc.nextLine();
                        try{
                            accountDao.updatePassword(loginAccount);
                            System.out.println("비밀번호 변경 성공!");
                        }catch (Exception e){
                            System.out.println("유효하지 않는 password입니다.");
                        }
                        System.out.println("===============");
                        mainMenu = 0;
                    }

                    // 계정 정보 보기
                    if(mainMenu == 2){
                        try {
                            System.out.println("===============");
                            Account account = accountDao.selectByName(loginAccount.getName());
                            System.out.println("아이디: " + account.getName());
                            System.out.println("비밀번호: " + account.getPassword());
                            System.out.println("이름: " + account.getUserName());
                            System.out.println("전화번호: " + account.getPhoneNumber());
                            System.out.println("주소: " + account.getAddress());
                            System.out.println("===============");
                        } catch (Exception e) {
                            System.out.println("계정 정보 가져오기 실패!");
                        }
                        mainMenu = 0;
                    }

                    // 계정 정보 수정
                    if(mainMenu == 3){
                        while (accountMenu != 1 && accountMenu != 2 && accountMenu != 3 && accountMenu != 9){
                            System.out.println("=변경할 정보 선택!=");
                            System.out.println("1. 이름 변경");
                            System.out.println("2. 전화번호 변경");
                            System.out.println("3. 주소 변경");
                            System.out.println("9. 상위 메뉴로 복귀");
                            System.out.println("===============");
                            accountMenu = sc.nextInt();
                            sc.nextLine();

                            // 이름 변경
                            if(accountMenu == 1){
                                System.out.println("===============");
                                System.out.println("새로운 이름을 입력");
                                loginAccount.setUserName(sc.nextLine());
                                try{
                                    accountDao.updateUserName(loginAccount);
                                    System.out.println("이름 변경 성공!");
                                }catch (Exception e){
                                    System.out.println("유효하지 않는 이름입니다.");
                                }
                                System.out.println("===============");
                                accountMenu = 0;
                            }

                            // 전화번호 변경
                            if(accountMenu == 2){
                                System.out.println("===============");
                                System.out.println("새로운 전화번호를 입력(010-XXXX-XXXX)");
                                loginAccount.setPhoneNumber(sc.nextLine());
                                try{
                                    accountDao.updatePhoneNumber(loginAccount);
                                    System.out.println("전화번호 변경 성공!");
                                }catch (Exception e){
                                    System.out.println("유효하지 않는 전화번호입니다.");
                                }
                                System.out.println("===============");
                                accountMenu = 0;
                            }

                            // 주소 변경
                            if(accountMenu == 3){
                                System.out.println("===============");
                                System.out.println("새로운 주소를 입력");
                                loginAccount.setAddress(sc.nextLine());
                                try{
                                    accountDao.updateAddress(loginAccount);
                                    System.out.println("주소 변경 성공!");
                                }catch (Exception e){
                                    System.out.println("유효하지 않는 주소입니다.");
                                }
                                System.out.println("===============");
                                accountMenu = 0;
                            }

                            // 상위 메뉴로 복귀
                            if(accountMenu == 9){
                                accountMenu = 0;
                                break;
                            }
                        }
                        mainMenu = 0;
                    }

                    // 다이어리 추가
                    if(mainMenu == 4){
                        System.out.println("===============");
                        Diary newDiary = new Diary();
                        newDiary.setOwner(loginAccount.getName());
                        System.out.println("제목 입력");
                        newDiary.setName(sc.nextLine());
                        System.out.println("내용 입력");
                        newDiary.setContent(sc.nextLine());
                        // 날짜 입력
                        Date date = new Date();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        newDiary.setDate(simpleDateFormat.format(date));
                        try{
                            diaryDao.insert(newDiary);
                        }catch (Exception e){
                            System.out.println("오류 발생!");
                        }
                        System.out.println("다이어리 추가 성공!");
                        System.out.println("===============");
                        mainMenu = 0;
                    }

                    // 다이어리 보기
                    if(mainMenu == 5){
                        try {
                            System.out.println("===============");
                            List<Diary> diaryList = diaryDao.selectByOwner(loginAccount.getName());
                            for (int i = 0; i < diaryList.size(); i++) {
                                System.out.println("< " + diaryList.get(i).getId() + " >");
                                System.out.println("제목: " + diaryList.get(i).getName());
                                System.out.println("내용: " + diaryList.get(i).getContent());
                                System.out.println("날짜: " + diaryList.get(i).getDate());
                            }
                            System.out.println("===============");
                        } catch (Exception e) {
                            System.out.println("다이어리가 비어있습니다.");
                        }
                        mainMenu = 0;
                    }

                    // 다이어리 수정
                    if(mainMenu == 6){
                        System.out.println("===============");
                        System.out.print("수정할 다이어리 id를 입력: ");
                        try{
                            String findId = sc.nextLine();
                            Diary findDiary = diaryDao.selectById(findId);
                            if(findDiary.getOwner().equals(loginAccount.getName())) {
                                Diary diary = new Diary();
                                System.out.println("제목 입력");
                                diary.setName(sc.nextLine());
                                System.out.println("내용 입력");
                                diary.setContent(sc.nextLine());
                                diary.setId(Long.parseLong(findId));
                                diaryDao.update(diary);
                                System.out.println("다이어리 수정 성공!");
                            }
                            else
                                System.out.println("자신의 다이어리가 아닙니다.");
                        }catch (Exception e){
                            System.out.println("존재하지 않는 다이어리입니다.");
                        }
                        System.out.println("===============");
                        mainMenu = 0;
                    }

                    // 다이어리 삭제
                    if(mainMenu == 7){
                        System.out.println("===============");
                        System.out.print("삭제할 다이어리 id를 입력: ");
                        try{
                            String findId = sc.nextLine();
                            Diary findDiary = diaryDao.selectById(findId);
                            if(findDiary.getOwner().equals(loginAccount.getName())) {
                                diaryDao.deleteDiary(findId);
                                System.out.println("다이어리 삭제 성공!");
                            }
                            else
                                System.out.println("자신의 다이어리가 아닙니다.");
                        }catch (Exception e){
                            System.out.println("존재하지 않는 다이어리입니다.");
                        }
                        System.out.println("===============");
                        mainMenu = 0;
                    }

                    // 검색
                    if(mainMenu == 8){
                        while (searchMenu != 1 && searchMenu != 2 && searchMenu != 9){
                            System.out.println("====Search====");
                            System.out.println("1. 제목 키워드 검색");
                            System.out.println("2. 내용 키워드 검색");
                            System.out.println("3. 날짜 검색");
                            System.out.println("9. 상위 메뉴로 복귀");
                            System.out.println("===============");
                            searchMenu = sc.nextInt();
                            sc.nextLine();

                            // 제목 키워드 검색
                            if(searchMenu == 1){
                                System.out.println("===============");
                                int findNumber = 0;
                                System.out.print("찾을 제목 입력: ");
                                String findName = sc.nextLine();
                                try {
                                    System.out.println("===============");
                                    List<Diary> diaryList = diaryDao.selectByOwner(loginAccount.getName());
                                    for (int i = 0; i < diaryList.size(); i++) {
                                        if(diaryList.get(i).getName().contains(findName)) {
                                            findNumber++;
                                            System.out.println("< " + diaryList.get(i).getId() + " >");
                                            System.out.println("제목: " + diaryList.get(i).getName());
                                            System.out.println("내용: " + diaryList.get(i).getContent());
                                            System.out.println("날짜: " + diaryList.get(i).getDate());
                                        }
                                    }
                                    if(findNumber == 0)
                                        System.out.println("찾는 내용이 없습니다.");
                                    System.out.println("===============");
                                } catch (Exception e) {
                                    System.out.println("일치하는 다이어리가 없습니다.");
                                }
                                System.out.println("===============");
                                searchMenu = 0;
                            }

                            // 내용 키워드 검색
                            if(searchMenu == 2){
                                System.out.println("===============");
                                int findNumber = 0;
                                System.out.print("찾을 내용 입력: ");
                                String findContent = sc.nextLine();
                                try {
                                    System.out.println("===============");
                                    List<Diary> diaryList = diaryDao.selectByOwner(loginAccount.getName());
                                    for (int i = 0; i < diaryList.size(); i++) {
                                        if(diaryList.get(i).getContent().contains(findContent)) {
                                            findNumber++;
                                            System.out.println("< " + diaryList.get(i).getId() + " >");
                                            System.out.println("제목: " + diaryList.get(i).getName());
                                            System.out.println("내용: " + diaryList.get(i).getContent());
                                            System.out.println("날짜: " + diaryList.get(i).getDate());
                                        }
                                    }
                                    if(findNumber == 0)
                                        System.out.println("찾는 내용이 없습니다.");
                                    System.out.println("===============");
                                } catch (Exception e) {
                                    System.out.println("일치하는 다이어리가 없습니다.");
                                }
                                System.out.println("===============");
                                searchMenu = 0;
                            }

                            // 날짜 검색
                            if(searchMenu == 3){
                                System.out.println("===============");
                                int findNumber = 0;
                                System.out.print("찾을 날짜 입력(YYYY-MM-DD): ");
                                String findDate = sc.nextLine();
                                try {
                                    System.out.println("===============");
                                    List<Diary> diaryList = diaryDao.selectByOwner(loginAccount.getName());
                                    for (int i = 0; i < diaryList.size(); i++) {
                                        if(diaryList.get(i).getDate().contains(findDate)) {
                                            findNumber++;
                                            System.out.println("< " + diaryList.get(i).getId() + " >");
                                            System.out.println("제목: " + diaryList.get(i).getName());
                                            System.out.println("내용: " + diaryList.get(i).getContent());
                                            System.out.println("날짜: " + diaryList.get(i).getDate());
                                        }
                                    }
                                    if(findNumber == 0)
                                        System.out.println("찾는 내용이 없습니다.");
                                    System.out.println("===============");
                                } catch (Exception e) {
                                    System.out.println("일치하는 다이어리가 없습니다.");
                                }
                                System.out.println("===============");
                                searchMenu = 0;
                            }

                            // 상위 메뉴로 복귀
                            if(searchMenu == 9){
                                searchMenu = 0;
                                break;
                            }
                        }
                        mainMenu = 0;
                    }

                    // 종료
                    if(mainMenu == 9){
                        System.out.println("다이어리를 종료합니다.");
                        ctx.close();
                        return;
                    }
                }
            }

            // 회원가입
            if(loginMenu == 2){
                System.out.println("===============");
                System.out.println("사용할 id를 입력");
                Account newAccount = new Account();
                newAccount.setName(sc.next());
                sc.nextLine();
                System.out.println("사용할 password를 입력");
                newAccount.setPassword(sc.next());
                sc.nextLine();
                try{
                    accountDao.insert(newAccount);
                }catch (Exception e){
                    System.out.println("중복된 아이디 입니다.");
                }
                System.out.println("===============");
                loginMenu = 0;
            }

            // 종료
            if (loginMenu == 9) {
                System.out.println("다이어리를 종료합니다.");
                ctx.close();
                return;
            }
        }
    }
}
