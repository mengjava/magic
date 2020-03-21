package com.haoqi.magic.auth.provider;

import cn.hutool.core.util.StrUtil;
import com.haoqi.magic.auth.client.SystemServiceClient;
import com.haoqi.magic.auth.dto.ClientDetailDTO;
import com.haoqi.rigger.core.Result;
import com.haoqi.rigger.core.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;

import java.util.Arrays;

/**
 * @author twg
 * @since 2019/2/27
 */
public class JdbcClientDetailsService implements ClientDetailsService {
    @Autowired
    private SystemServiceClient userServiceClient;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        Result<ClientDetailDTO> result = userServiceClient.clientDetail(clientId);
        if (!result.isSuccess()) {
            throw new UserNotFoundException(result.getMessage());
        }
        ClientDetailDTO clientDetail =  result.getData();
        BaseClientDetails clientDetails = new BaseClientDetails();
        clientDetails.setClientId(clientId);
        clientDetails.setClientSecret(passwordEncoder.encode(clientDetail.getClientSecret()));
        clientDetails.setScope(Arrays.asList(StrUtil.split(clientDetail.getScope(), StrUtil.COMMA)));
        clientDetails.setAuthorizedGrantTypes(Arrays.asList(StrUtil.split(clientDetail.getAuthorizedGrantTypes(), StrUtil.COMMA)));
        return clientDetails;
    }
}
