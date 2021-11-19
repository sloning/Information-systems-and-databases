package com.isbd.service.effect;

import com.isbd.Dao.AppliedEffectDao;
import com.isbd.Dao.Dao;
import com.isbd.exception.EntityNotFoundException;
import com.isbd.exception.WrongCredentialsException;
import com.isbd.model.AppliedEffect;
import com.isbd.model.Effect;
import com.isbd.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppliedEffectServiceImpl implements AppliedEffectService {
    private final Dao<Effect> effectDao;
    private final AppliedEffectDao appliedEffectDao;
    private final AuthenticationFacade authenticationFacade;

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
    public List<AppliedEffect> getAppliedEffectsByPlayer(long playerId) {
        if (playerId != authenticationFacade.getPlayerId()) throw new WrongCredentialsException("Access blocked");
        deleteEndedEffectsOfPlayer(playerId);
        return appliedEffectDao.getByPlayer(playerId);
    }

    @Override
    public void save(AppliedEffect appliedEffect) {
        appliedEffectDao.save(appliedEffect);
    }

    @Override
    public void deleteEndedEffectsOfPlayer(long playerId) {
        appliedEffectDao.deleteEndedEffectsOfPlayer(playerId);
    }
}
