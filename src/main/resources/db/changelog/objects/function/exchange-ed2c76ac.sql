CREATE OR REPLACE FUNCTION public.exchange(_offer_id bigint, _player_id bigint)
    RETURNS void
    LANGUAGE plpgsql
AS
$function$
DECLARE
    var_selling_item_id                    int;
    DECLARE amount_of_items_will_be_remove int;
    DECLARE has_amount_of_selling_items    int;
    DECLARE var_buying_item_id             int;
    DECLARE amount_of_items_will_be_added  int;
    DECLARE has_amount_of_buying_items     int;
BEGIN
    SELECT buying_item_id INTO var_selling_item_id FROM offer WHERE offer.offer_id = _offer_id;
    SELECT amount
    INTO has_amount_of_selling_items
    FROM inventory
    WHERE item_id = var_selling_item_id
      AND inventory.player_id = _player_id;
    SELECT amount_of_buying_items INTO amount_of_items_will_be_remove FROM offer WHERE offer.offer_id = _offer_id;

    UPDATE inventory
    SET amount = has_amount_of_selling_items - amount_of_items_will_be_remove
    WHERE player_id = _player_id
      AND item_id = var_selling_item_id;

    SELECT selling_item_id INTO var_buying_item_id FROM offer WHERE offer.offer_id = _offer_id;
    SELECT amount
    INTO has_amount_of_buying_items
    FROM inventory
    WHERE item_id = var_buying_item_id
      AND inventory.player_id = _player_id;
    SELECT amount_of_selling_items INTO amount_of_items_will_be_added FROM offer WHERE offer.offer_id = _offer_id;

    IF (has_amount_of_buying_items IS NULL) THEN
        has_amount_of_buying_items = 0;
    END IF;

    INSERT INTO inventory(player_id, item_id, amount)
    VALUES (_player_id, var_buying_item_id, has_amount_of_buying_items + amount_of_items_will_be_added)
    ON CONFLICT(player_id, item_id) DO UPDATE SET amount = has_amount_of_buying_items + amount_of_items_will_be_added;
END;
$function$
