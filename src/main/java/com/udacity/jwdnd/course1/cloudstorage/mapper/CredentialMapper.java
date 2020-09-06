package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {

    @Select("SELECT * FROM CREDENTIALS WHERE userId = #{userId}")
    List<CredentialForm> getCredentialsByUserId(Integer userId);

    @Insert("INSERT INTO CREDENTIALS (url, userName, key, password, userId) VALUES (#{url}, #{userName}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int insertCredential(CredentialForm credentialForm);

    @Update("UPDATE CREDENTIALS SET url = #{url}, userName = #{userName}, key = #{key}, password = #{password}, userId = #{userId} WHERE credentialId = #{credentialId}")
    int updateCredential(CredentialForm credentialForm);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialId = #{credentialId}")
    int deleteCredentialById(Integer credentialId);
}
