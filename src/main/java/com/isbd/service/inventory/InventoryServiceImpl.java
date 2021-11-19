package com.isbd.service.inventory;

import com.isbd.Dao.InventoryDao;
import com.isbd.exception.EntityNotFoundException;
import com.isbd.exception.WrongCredentialsException;
import com.isbd.model.InventoryItem;
import com.isbd.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
    private final InventoryDao inventoryDAO;
    private final AuthenticationFacade authenticationFacade;

    @Override
    public List<InventoryItem> getByPlayerId(long playerId) {
        if (playerId != authenticationFacade.getPlayerId()) throw new WrongCredentialsException("Access blocked");
        return inventoryDAO.get(playerId).orElseThrow(() ->
                new EntityNotFoundException(String.format("Inventory of user: %d was not found", playerId)));
    }

    @Override
    public void deleteByPlayerId(long playerId) {
        inventoryDAO.delete(playerId);
    }
}
