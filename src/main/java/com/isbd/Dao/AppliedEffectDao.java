package com.isbd.Dao;

import com.isbd.model.AppliedEffect;

import java.util.List;

public interface AppliedEffectDao extends Dao<AppliedEffect> {
    List<AppliedEffect> getByPlayer(long playerId);
}
