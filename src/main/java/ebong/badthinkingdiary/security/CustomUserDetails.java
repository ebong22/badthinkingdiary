package ebong.badthinkingdiary.security;

import ebong.badthinkingdiary.domain.Member;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class CustomUserDetails implements UserDetails {

    private Member member;
    private List<String> roles;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }


    @Override
    public String getPassword() {
        return member.getUserPw();
    }


    @Override
    public String getUsername() {
        return member.getUserId();
    }


    @Override
    public boolean isAccountNonExpired() {
        if (member.getStatus() == '1') {
            return true;
        }
        return false;
    }


    @Override
    public boolean isAccountNonLocked() {
        if (member.getStatus() == '1') {
            return true;
        }
        return false;
    }


    @Override
    public boolean isCredentialsNonExpired() {
        if(member.getStatus() == '1'){
            return true;
        }
        return false;
    }


    @Override
    public boolean isEnabled() {
        if(member.getStatus() == '1'){
            return true;
        }
        return false;
    }
}
