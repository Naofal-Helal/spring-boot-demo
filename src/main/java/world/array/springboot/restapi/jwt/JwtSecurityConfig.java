package world.array.springboot.restapi.jwt;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true)
public class JwtSecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, HandlerMappingIntrospector introspector)
      throws Exception {

    // h2-console is a servlet
    // https://github.com/spring-projects/spring-security/issues/12310
    return httpSecurity
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/authenticate").permitAll()
            .requestMatchers(PathRequest.toH2Console()).permitAll() // h2-console is a servlet and NOT recommended for a
            .requestMatchers("/swagger-ui/**").permitAll() // swagger
            .requestMatchers("/v3/api-docs/**").permitAll() // swagger
                                                            // production
            .requestMatchers(HttpMethod.OPTIONS, "/**")
            .permitAll()
            .anyRequest()
            .authenticated())
        .csrf(csrf -> csrf.disable())
        // .csrf(csrf -> {
        // csrf.ignoringRequestMatchers("/authenticate");
        // })
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .oauth2ResourceServer(
            o2rsc -> o2rsc.jwt(jwt -> jwt.jwtAuthenticationConverter(customJwtAuthenticationConverter())))
        .cors(
            Customizer.withDefaults())
        .httpBasic(
            Customizer.withDefaults())
        .headers(header -> {
          header.frameOptions(foc -> foc.sameOrigin());
        })
        .formLogin(Customizer.withDefaults())
        .build();
  }

  @Bean
  public AuthenticationManager authenticationManager(
      UserDetailsService userDetailsService,
      PasswordEncoder passwordEncoder) {
    var authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(userDetailsService);
    authenticationProvider.setPasswordEncoder(passwordEncoder);
    return new ProviderManager(authenticationProvider);
  }

  // @Bean
  // public UserDetailsService inMemoryUserDetailsService() {
  // UserDetails user = User.withUsername("user")
  // .password("{noop}123")
  // .authorities("read")
  // .roles("USER")
  // .build();
  //
  // return new InMemoryUserDetailsManager(user);
  // }

  @Bean
  @Qualifier("authDataSource")
  public DataSource authDataSource() {
    return new EmbeddedDatabaseBuilder()
        .setName("default")
        .setType(EmbeddedDatabaseType.H2)
        .addScript(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION)
        .build();
  }

  @Bean
  public UserDetailsService authDetailsService(
      @Qualifier("authDataSource") DataSource dataSource, PasswordEncoder passwordEncoder) {
    UserDetails user = User.withUsername("user")
        // .password("{noop}123")
        .password("123")
        .passwordEncoder(passwordEncoder::encode)
        .authorities("read")
        .roles("USER")
        .build();
    UserDetails admin = User.withUsername("admin")
        // .password("{noop}123")
        .password("123")
        .passwordEncoder(passwordEncoder::encode)
        .authorities("read")
        .roles("ADMIN", "USER")
        .build();
    var jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
    jdbcUserDetailsManager.createUser(user);
    jdbcUserDetailsManager.createUser(admin);
    return jdbcUserDetailsManager;
  }

  @Bean
  public PasswordEncoder bcryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public JWKSource<SecurityContext> jwkSource() {
    JWKSet jwkSet = new JWKSet(rsaKey());
    return (((jwkSelector, securityContext) -> jwkSelector.select(jwkSet)));
  }

  @Bean
  JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
    return new NimbusJwtEncoder(jwkSource);
  }

  @Bean
  JwtDecoder jwtDecoder() throws JOSEException {
    return NimbusJwtDecoder
        .withPublicKey(rsaKey().toRSAPublicKey())
        .build();
  }

  @Bean
  public JwtAuthenticationConverter customJwtAuthenticationConverter() {
    JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
    converter.setJwtGrantedAuthoritiesConverter(new CustomJwtGrantedAuthoritiesConverter());
    return converter;
  }

  @Bean
  public RSAKey rsaKey() {

    KeyPair keyPair = keyPair();

    return new RSAKey.Builder((RSAPublicKey) keyPair.getPublic())
        .privateKey((RSAPrivateKey) keyPair.getPrivate())
        .keyID(UUID.randomUUID().toString())
        .build();
  }

  @Bean
  public KeyPair keyPair() {
    try {
      var keyPairGenerator = KeyPairGenerator.getInstance("RSA");
      keyPairGenerator.initialize(2048);
      return keyPairGenerator.generateKeyPair();
    } catch (Exception e) {
      throw new IllegalStateException(
          "Unable to generate an RSA Key Pair", e);
    }
  }

  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedMethods("*")
            .allowedOrigins("http://localhost:3000");
      }
    };
  }

}
