package main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import config.AppCtx;
import spring.*;

public class Main{
	
	private static ApplicationContext ctx = null;
	
	public static void main(String[] args) throws IOException{
		ctx = new AnnotationConfigApplicationContext(AppCtx.class);
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		while(true) {
			System.out.println("명령어를 입력하세요:");
			String command = reader.readLine();
			if(command.startsWith("new ")) {
				processNewCommand(command.split(" "));
				continue;
            }else if(command.equalsIgnoreCase("exit")){
				System.out.println("종료합니다.");
				break;
			}else{
				printHelp();
			}
			
		}
		
	}
	private static void processNewCommand(String[] arg) {
		if(arg.length != 5) {
			printHelp();
			return;
		}
		MemberRegisterService regSvc = ctx.getBean("memberRegSvc", MemberRegisterService.class);
		RegisterRequest req = new RegisterRequest();
		req.setEmail(arg[1]);
		req.setName(arg[2]);
		req.setPassword(arg[3]);
		req.setConfirmPassword(arg[4]);
		
		if(!req.getPassword().equals(req.getConfirmPassword())) {
			System.out.println("암호와 확인이 일치하지 않습니다.\n");
			return;
		}
		try {
			regSvc.regist(req);
			System.out.println("등록했습니다.\n");
		}
		catch(DuplicateMemberException e){
			System.out.println("이미 존재하는 이메일입니다.\n");
		}
	}
	private static void printHelp() {
		System.out.println();
		System.out.println("잘못된 명령입니다. 아래 명령어 사용법을 확인하세요");
		System.out.println("명령어 사용법:");
		System.out.println("new 이메일 이름 암호 암호확인");
		System.out.println("change 이메일 현재비번 변경비번");
		System.out.println();
	}
}
