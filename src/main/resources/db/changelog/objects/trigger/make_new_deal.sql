CREATE TRIGGER make_new_deal
    BEFORE INSERT
    ON public.deal
    FOR EACH ROW
EXECUTE FUNCTION make_new_deal()