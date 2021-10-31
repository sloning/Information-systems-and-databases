package com.isbd.DAO;

import com.isbd.model.AppliedEffect;

import java.util.List;

public interface AppliedEffectDAO extends DAO<AppliedEffect> {
    List<AppliedEffect> getByPlayer(long playerId);
}
