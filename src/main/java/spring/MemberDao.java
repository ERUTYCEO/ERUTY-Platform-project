package spring;

import java.util.HashMap;
import java.util.Map;
import java.util.Collection;

public class MemberDao {
	
	private Map<String, Member> map = new HashMap<>();
	
	public Member selectByEmail(String email) {
		return map.get(email);
	}
	public void insert(Member member) {
		map.put(member.getEmail(), member);
	}
	public void update(Member member) {
		map.put(member.getEmail(), member);
	}
	public Collection<Member> selectAll(){
		return map.values();
	}
}
