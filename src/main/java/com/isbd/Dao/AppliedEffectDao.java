package com.isbd.Dao;

import com.isbd.model.AppliedEffect;

import java.util.List;

public interface AppliedEffectDao {
    List<AppliedEffect> getByPlayer(long playerId);

    int save(AppliedEffect appliedEffect);
}
