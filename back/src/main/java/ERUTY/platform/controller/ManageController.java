package ERUTY.platform.controller;

import ERUTY.platform.domain.Member;
import ERUTY.platform.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ManageController {

    @Autowired
    private final MemberService memberService;

    @GetMapping("/mange")
    public ResponseEntity<List<Member>> getAllMember() {
        List<Member> memberList = memberService.getAllMember();

        return ResponseEntity.status(HttpStatus.OK).body(memberList);
    }
}
