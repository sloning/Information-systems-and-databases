package com.isbd.service;

import com.isbd.exception.EntityNotFoundException;
import com.isbd.exception.EntityNotSavedException;
import com.isbd.exception.WrongCredentialsException;
import com.isbd.model.AppliedEffect;
import com.isbd.model.Effect;
import com.isbd.repository.AppliedEffectRepository;
import com.isbd.repository.EffectRepository;
import com.isbd.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppliedEffectService {
    private final EffectRepository effectRepository;
    private final AppliedEffectRepository appliedEffectRepository;
    private final AuthenticationFacade authenticationFacade;

    public AppliedEffect applyEffect(int effectId, long playerId) {
        Effect effect = effectRepository.get(effectId).orElseThrow(() ->
                new EntityNotFoundException(String.format("Эффект с идентификатором %d не найден", effectId)));
        AppliedEffect appliedEffect = new AppliedEffect();
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

    public List<AppliedEffect> getAppliedEffectsByPlayer(long playerId) {
        if (playerId != authenticationFacade.getPlayerId()) {
            throw new WrongCredentialsException("У вас не прав на просмотр данной информации");
        }
        deleteEndedEffectsOfPlayer(playerId);
        return appliedEffectRepository.getByPlayer(playerId);
    }

    public void save(AppliedEffect appliedEffect) {
        if (appliedEffectRepository.save(appliedEffect) == 0) {
            throw new EntityNotSavedException(String.format("Эффект с идентификатором %d не применён",
                    appliedEffect.getId()));
        }
    }

    public void deleteEndedEffectsOfPlayer(long playerId) {
        appliedEffectRepository.deleteEndedEffectsOfPlayer(playerId);
    }
}
