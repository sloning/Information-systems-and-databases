package com.isbd.repository;

import com.isbd.model.AppliedEffect;

import java.util.List;

public interface AppliedEffectRepository {
    List<AppliedEffect> getByPlayer(long playerId);

    int save(AppliedEffect appliedEffect);

    int deleteEndedEffectsOfPlayer(long playerId);
}
