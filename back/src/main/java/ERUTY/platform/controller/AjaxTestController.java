package ERUTY.platform.controller;


import org.springframework.web.bind.annotation.*;

@RestController
public class AjaxTestController {
	
	@GetMapping("/getLikeById")
    /* inputName 파라미터를 받아, 미리 저장된 ageMap에서 해당 이름에 맵핑된 나이를 리턴해주는 메소드 */
	public Boolean getLikeById( @RequestParam String itemId ) {
		/* 인자로 넘어온 itemId가 좋아요 눌러진 item인데
		 현재 접속해있는 user랑 비교해서 
		 해당 user가 이미 좋아요를 누른 item이면 취소되는 거니까 
		 return false;

		 해당 user가 좋아요를 안누른 item이면 좋아요 되는 거니까
		 return true; 
		 해주면 됩니다 ! 
		 */
		return true;
	}

}