/**
 * from
 * <https://medium.com/@alperkrtglu/spring-oauth2-with-keycloak-moving-from-scope-to-roles-34247f3ff78e>
 */
package world.array.springboot.restapi.jwt;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

public final class CustomJwtGrantedAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

  @SuppressWarnings("unchecked")
  @Override
  public Collection<GrantedAuthority> convert(Jwt jwt) {
    var realmAccess = (Map<String, List<String>>) jwt.getClaim("realm_access");

    return realmAccess.get("roles").stream()
        .map(role -> new SimpleGrantedAuthority(role))
        .collect(Collectors.toList());
  }

  @Override
  public <U> Converter<Jwt, U> andThen(Converter<? super Collection<GrantedAuthority>, ? extends U> after) {
    return Converter.super.andThen(after);
  }
}
