CREATE TRIGGER give_kit_starter
    AFTER INSERT
    ON public.player
    FOR EACH ROW
EXECUTE FUNCTION give_kit_starter()