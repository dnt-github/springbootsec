package AutoPki.RestCertifications;

import java.security.cert.X509Certificate;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.x509.SubjectDnX509PrincipalExtractor;
import org.springframework.security.web.authentication.preauth.x509.X509AuthenticationFilter;

@SpringBootApplication
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class RestCertificationsApplication extends WebSecurityConfigurerAdapter{

	public static void main(String[] args) {
		SpringApplication.run(RestCertificationsApplication.class, args);
	}
	
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated()
                .and()
                .x509()
                .x509PrincipalExtractor(extractor());              
                //.userDetailsService(userDetailsService());
        	
    }
	
	@Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                
                if (username.contains("BobDole")) {
                    return new User(username, "", AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER"));
                }
                throw new UsernameNotFoundException("User not found!");
            }
        };
    }
	
	@Bean 
	public SubjectDnX509PrincipalExtractor extractor() {
		return new SubjectDnX509PrincipalExtractor() {
			@Override 
			public Object extractPrincipal(X509Certificate cert) {
				
				String value = "";
				if(cert != null) {
					value = cert.getSubjectDN().getName();
					System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + value);
				}
				return value;
			}
			
		};
	}
	
	
		
}
