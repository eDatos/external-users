-- Create GET_NEXT_SEQUENCE_VALUE function

CREATE OR REPLACE FUNCTION GET_NEXT_SEQUENCE_VALUE(sequence_name_in varchar) RETURNS BIGINT AS $$
  DECLARE sequence_next_value_out BIGINT;
  BEGIN
    SELECT SEQUENCE_NEXT_VALUE INTO sequence_next_value_out
    FROM TB_SEQUENCES 
    WHERE SEQUENCE_NAME = sequence_name_in;
    RETURN (sequence_next_value_out);
  END;
$$ LANGUAGE plpgsql;
