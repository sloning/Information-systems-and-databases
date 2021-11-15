CREATE TRIGGER clear_inventory
    AFTER INSERT
    ON public.withdrawal
    FOR EACH ROW
EXECUTE FUNCTION clear_inventory()