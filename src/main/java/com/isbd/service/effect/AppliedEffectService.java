package com.isbd.service.effect;

import com.isbd.model.AppliedEffect;

import java.util.List;

public interface AppliedEffectService {
    AppliedEffect applyEffect(int effectId, long playerId);

    List<AppliedEffect> getAppliedEffectsByPlayer(long playerId);

    void save(AppliedEffect appliedEffect);

    void deleteEndedEffectsOfPlayer(long playerId);
}
