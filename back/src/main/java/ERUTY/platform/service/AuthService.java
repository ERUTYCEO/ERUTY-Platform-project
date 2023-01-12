package ERUTY.platform.service;

import ERUTY.platform.domain.AuthInfo;
import ERUTY.platform.domain.Member;
import ERUTY.platform.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;

    public AuthInfo authenticate(String email, String password) {
        Member member = memberRepository.findMemberByEmail(email);

        if(member == null) {
            throw new IllegalStateException("이메일을 다시 입력해주세요.");
        }
        if(!(password.equals(member.getPassword()))) {
            throw new IllegalStateException("비밀번호를 다시 입력해주세요.");
        }

        AuthInfo authInfo = new AuthInfo();
        authInfo.setId(member.getId());
        authInfo.setEmail(member.getEmail());

        return authInfo;
    }
}
