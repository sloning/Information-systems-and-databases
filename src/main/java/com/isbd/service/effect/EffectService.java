package com.isbd.service.effect;

import com.isbd.model.AppliedEffect;

public interface EffectService {
    AppliedEffect applyEffect(int effectId, long playerId);

    void save(AppliedEffect appliedEffect);
}
