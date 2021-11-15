CREATE OR REPLACE FUNCTION public.clear_inventory()
    RETURNS trigger
    LANGUAGE plpgsql
AS
$function$
DECLARE
    item record;
BEGIN
    FOR item IN SELECT item_id, amount FROM inventory WHERE player_id = new.player_id
        LOOP
            INSERT INTO withdrawal_composition(withdrawal_id, item_id, amount)
            VALUES (new.withdrawal_id, item.item_id, item.amount);
        END LOOP;
    DELETE FROM inventory WHERE player_id = new.player_id;
    RETURN new;
END;
$function$
