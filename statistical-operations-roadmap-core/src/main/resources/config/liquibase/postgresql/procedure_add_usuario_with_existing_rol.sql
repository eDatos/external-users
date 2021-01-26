CREATE OR REPLACE FUNCTION add_usuario_with_existing_rol(
        login tb_usuarios.login%TYPE, nombre tb_usuarios.nombre%TYPE, apellido1 tb_usuarios.apellido1%TYPE, 
        apellido2 tb_usuarios.apellido2%TYPE, email tb_usuarios.email%TYPE, codigoRol tb_usuarios_roles.rol%TYPE
    ) 
    RETURNS void AS $$
    DECLARE
        vNow TIMESTAMP;
    BEGIN
        vNow := now();
        INSERT INTO tb_usuarios(id, opt_lock, login, nombre, apellido1, apellido2, email, deletion_date, created_by, created_date, last_modified_by, last_modified_date)
            VALUES(nextval('seq_tb_usuarios'), 0, login, nombre, apellido1, apellido2, email, NULL, 'system', vNow, 'system', vNow);

        INSERT INTO tb_usuarios_roles(usuario_fk, rol)
            VALUES(currval('seq_tb_usuarios'), codigoRol);
    
        raise notice 'Done!';
    
    EXCEPTION
        WHEN unique_violation THEN
            raise notice 'Se ha violado una constraint única. ERROR -> %', SQLERRM;
        WHEN OTHERS THEN
            raise notice 'Algo ha ocurrido mal. ERROR -> %', SQLERRM;
    END;
    $$ LANGUAGE plpgsql;
    