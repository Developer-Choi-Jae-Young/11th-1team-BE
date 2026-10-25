package org.example.knockin.global.auth.service;

import org.example.knockin.verification.entity.LoginProviderType;

public abstract class AbstractOauth2DeleteService implements Oauth2DeleteService{
    @Override
    public abstract boolean requestUnlink(String providerId);

    public abstract LoginProviderType getProviderType();
}
