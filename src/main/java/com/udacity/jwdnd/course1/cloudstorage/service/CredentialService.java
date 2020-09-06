package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {

    private final CredentialMapper credentialMapper;

    public CredentialService(CredentialMapper credentialMapper) {
        this.credentialMapper = credentialMapper;
    }

    public List<CredentialForm> getCredentialsByUserId(Integer userId) {
        return credentialMapper.getCredentialsByUserId(userId);
    }

    public int insertUpdateCredential(CredentialForm credentialForm) {
        if (credentialForm.getCredentialId() != null) {
            return credentialMapper.updateCredential(credentialForm);
        } else {
            return credentialMapper.insertCredential(credentialForm);
        }
    }

    public int deleteCredentialById(Integer credentialId) {
        return credentialMapper.deleteCredentialById(credentialId);
    }
}
