package com.isbd.service.effect;

import com.isbd.Dao.AppliedEffectDao;
import com.isbd.Dao.Dao;
import com.isbd.exception.EntityNotFoundException;
import com.isbd.model.AppliedEffect;
import com.isbd.model.Effect;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EffectServiceImpl implements EffectService {
    private final Dao<Effect> effectDao;
    private final AppliedEffectDao appliedEffectDao;

    @Override
    public AppliedEffect applyEffect(int effectId, long playerId) {
        AppliedEffect appliedEffect = new AppliedEffect();
        Effect effect = effectDao.get(effectId).orElseThrow(() ->
                new EntityNotFoundException(String.format("Effect with id: %d was not found", effectId)));
        appliedEffect.setId(effect.getId());
        appliedEffect.setPlayerId(playerId);
        appliedEffect.setName(effect.getName());
        appliedEffect.setPower(effect.getPower());
        appliedEffect.setDuration(effect.getDuration());
        appliedEffect.setStartTime(LocalDateTime.now());
        appliedEffect.setEndTime(appliedEffect.getStartTime().plusMinutes(appliedEffect.getDuration()));
        save(appliedEffect);
        return appliedEffect;
    }

    @Override
    public void save(AppliedEffect appliedEffect) {
        appliedEffectDao.save(appliedEffect);
    }
}
