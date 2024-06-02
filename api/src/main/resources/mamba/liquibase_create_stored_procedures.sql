USE ssemr_etl;

~

        
    
        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  fn_mamba_calculate_agegroup  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP FUNCTION IF EXISTS fn_mamba_calculate_agegroup;


~
CREATE FUNCTION fn_mamba_calculate_agegroup(age INT) RETURNS VARCHAR(15)
    DETERMINISTIC
BEGIN
    DECLARE agegroup VARCHAR(15);
    IF (age < 1) THEN
        SET agegroup = '<1';
    ELSEIF age between 1 and 4 THEN
        SET agegroup = '1-4';
    ELSEIF age between 5 and 9 THEN
        SET agegroup = '5-9';
    ELSEIF age between 10 and 14 THEN
        SET agegroup = '10-14';
    ELSEIF age between 15 and 19 THEN
        SET agegroup = '15-19';
    ELSEIF age between 20 and 24 THEN
        SET agegroup = '20-24';
    ELSEIF age between 25 and 29 THEN
        SET agegroup = '25-29';
    ELSEIF age between 30 and 34 THEN
        SET agegroup = '30-34';
    ELSEIF age between 35 and 39 THEN
        SET agegroup = '35-39';
    ELSEIF age between 40 and 44 THEN
        SET agegroup = '40-44';
    ELSEIF age between 45 and 49 THEN
        SET agegroup = '45-49';
    ELSEIF age between 50 and 54 THEN
        SET agegroup = '50-54';
    ELSEIF age between 55 and 59 THEN
        SET agegroup = '55-59';
    ELSEIF age between 60 and 64 THEN
        SET agegroup = '60-64';
    ELSE
        SET agegroup = '65+';
    END IF;

    RETURN (agegroup);
END~



        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  fn_mamba_get_obs_value_column  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP FUNCTION IF EXISTS fn_mamba_get_obs_value_column;


~
CREATE FUNCTION fn_mamba_get_obs_value_column(conceptDatatype VARCHAR(20)) RETURNS VARCHAR(20)
    DETERMINISTIC
BEGIN
    DECLARE obsValueColumn VARCHAR(20);
    IF (conceptDatatype = 'Text' OR conceptDatatype = 'Coded' OR conceptDatatype = 'N/A' OR
        conceptDatatype = 'Boolean') THEN
        SET obsValueColumn = 'obs_value_text';
    ELSEIF conceptDatatype = 'Date' OR conceptDatatype = 'Datetime' THEN
        SET obsValueColumn = 'obs_value_datetime';
    ELSEIF conceptDatatype = 'Numeric' THEN
        SET obsValueColumn = 'obs_value_numeric';
    END IF;

    RETURN (obsValueColumn);
END~



        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  fn_mamba_age_calculator  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP FUNCTION IF EXISTS fn_mamba_age_calculator;


~
CREATE FUNCTION fn_mamba_age_calculator(birthdate DATE, deathDate DATE) RETURNS Integer
    DETERMINISTIC
BEGIN
    DECLARE onDate DATE;
    DECLARE today DATE;
    DECLARE bday DATE;
    DECLARE age INT;
    DECLARE todaysMonth INT;
    DECLARE bdayMonth INT;
    DECLARE todaysDay INT;
    DECLARE bdayDay INT;
    DECLARE birthdateCheck VARCHAR(255) DEFAULT NULL;

    SET onDate = NULL;

    -- Check if birthdate is not null and not an empty string
    IF birthdate IS NULL OR TRIM(birthdate) = '' THEN
        RETURN NULL;
    ELSE
        SET today = CURDATE();

        -- Check if birthdate is a valid date using STR_TO_DATE &  -- Check if birthdate is not in the future
        SET birthdateCheck = STR_TO_DATE(birthdate, '%Y-%m-%d');
        IF birthdateCheck IS NULL OR birthdateCheck > today THEN
            RETURN NULL;
        END IF;

        IF onDate IS NOT NULL THEN
            SET today = onDate;
        END IF;

        IF deathDate IS NOT NULL AND today > deathDate THEN
            SET today = deathDate;
        END IF;

        SET bday = birthdate;
        SET age = YEAR(today) - YEAR(bday);
        SET todaysMonth = MONTH(today);
        SET bdayMonth = MONTH(bday);
        SET todaysDay = DAY(today);
        SET bdayDay = DAY(bday);

        IF todaysMonth < bdayMonth THEN
            SET age = age - 1;
        ELSEIF todaysMonth = bdayMonth AND todaysDay < bdayDay THEN
            SET age = age - 1;
        END IF;

        RETURN age;
    END IF;
END~



        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_xf_system_drop_all_functions_in_schema  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_xf_system_drop_all_stored_functions_in_schema;


~
CREATE PROCEDURE sp_xf_system_drop_all_stored_functions_in_schema(
    IN database_name CHAR(255) CHARACTER SET UTF8MB4
)
BEGIN
    DELETE FROM `mysql`.`proc` WHERE `type` = 'FUNCTION' AND `db` = database_name; -- works in mysql before v.8

END~



        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_xf_system_drop_all_stored_procedures_in_schema  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_xf_system_drop_all_stored_procedures_in_schema;


~
CREATE PROCEDURE sp_xf_system_drop_all_stored_procedures_in_schema(
    IN database_name CHAR(255) CHARACTER SET UTF8MB4
)
BEGIN

    DELETE FROM `mysql`.`proc` WHERE `type` = 'PROCEDURE' AND `db` = database_name; -- works in mysql before v.8

END~



        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_xf_system_drop_all_objects_in_schema  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_xf_system_drop_all_objects_in_schema;


~
CREATE PROCEDURE sp_xf_system_drop_all_objects_in_schema(
    IN database_name CHAR(255) CHARACTER SET UTF8MB4
)
BEGIN

    CALL sp_xf_system_drop_all_stored_functions_in_schema(database_name);
    CALL sp_xf_system_drop_all_stored_procedures_in_schema(database_name);
    CALL sp_xf_system_drop_all_tables_in_schema(database_name);
    # CALL sp_xf_system_drop_all_views_in_schema (database_name);

END~



        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_xf_system_drop_all_tables_in_schema  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_xf_system_drop_all_tables_in_schema;


-- CREATE PROCEDURE sp_xf_system_drop_all_tables_in_schema(IN database_name CHAR(255) CHARACTER SET UTF8MB4)
~
CREATE PROCEDURE sp_xf_system_drop_all_tables_in_schema()
BEGIN

    DECLARE tables_count INT;

    SET @database_name = (SELECT DATABASE());

    SELECT COUNT(1)
    INTO tables_count
    FROM information_schema.tables
    WHERE TABLE_TYPE = 'BASE TABLE'
      AND TABLE_SCHEMA = @database_name;

    IF tables_count > 0 THEN

        SET session group_concat_max_len = 20000;

        SET @tbls = (SELECT GROUP_CONCAT(@database_name, '.', TABLE_NAME SEPARATOR ', ')
                     FROM information_schema.tables
                     WHERE TABLE_TYPE = 'BASE TABLE'
                       AND TABLE_SCHEMA = @database_name
                       AND TABLE_NAME REGEXP '^(mamba_|dim_|fact_|flat_)');

        IF (@tbls IS NOT NULL) THEN

            SET @drop_tables = CONCAT('DROP TABLE IF EXISTS ', @tbls);

            SET foreign_key_checks = 0; -- Remove check, so we don't have to drop tables in the correct order, or care if they exist or not.
            PREPARE drop_tbls FROM @drop_tables;
            EXECUTE drop_tbls;
            DEALLOCATE PREPARE drop_tbls;
            SET foreign_key_checks = 1;

        END IF;

    END IF;

END~



        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_etl_execute  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_mamba_etl_execute;


~
CREATE PROCEDURE sp_mamba_etl_execute()
BEGIN
    DECLARE error_message VARCHAR(255) DEFAULT 'OK';
    DECLARE error_code CHAR(5) DEFAULT '00000';

    DECLARE start_time bigint;
    DECLARE end_time bigint;
    DECLARE start_date_time DATETIME;
    DECLARE end_date_time DATETIME;

    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
        BEGIN
            GET DIAGNOSTICS CONDITION 1
                error_code = RETURNED_SQLSTATE,
                error_message = MESSAGE_TEXT;

            -- SET @sql = CONCAT('SIGNAL SQLSTATE ''', error_code, ''' SET MESSAGE_TEXT = ''', error_message, '''');
            -- SET @sql = CONCAT('SET @signal = ''', @sql, '''');

            -- SET @sql = CONCAT('SIGNAL SQLSTATE ''', error_code, ''' SET MESSAGE_TEXT = ''', error_message, '''');
            -- PREPARE stmt FROM @sql;
            -- EXECUTE stmt;
            -- DEALLOCATE PREPARE stmt;

            INSERT INTO zzmamba_etl_tracker (initial_run_date,
                                             start_date,
                                             end_date,
                                             time_taken_microsec,
                                             completion_status,
                                             success_or_error_message,
                                             next_run_date)
            SELECT NOW(),
                   start_date_time,
                   NOW(),
                   (((UNIX_TIMESTAMP(NOW()) * 1000000 + MICROSECOND(NOW(6))) - @start_time) / 1000),
                   'ERROR',
                   (CONCAT(error_code, ' : ', error_message)),
                   NOW() + 5;
        END;

    -- Fix start time in microseconds
    SET start_date_time = NOW();
    SET @start_time = (UNIX_TIMESTAMP(NOW()) * 1000000 + MICROSECOND(NOW(6)));

    CALL sp_mamba_data_processing_etl();

    -- Fix end time in microseconds
    SET end_date_time = NOW();
    SET @end_time = (UNIX_TIMESTAMP(NOW()) * 1000000 + MICROSECOND(NOW(6)));

    -- Result
    SET @time_taken = (@end_time - @start_time) / 1000;
    SELECT @time_taken;


    INSERT INTO zzmamba_etl_tracker (initial_run_date,
                                     start_date,
                                     end_date,
                                     time_taken_microsec,
                                     completion_status,
                                     success_or_error_message,
                                     next_run_date)
    SELECT NOW(), start_date_time, end_date_time, @time_taken, 'SUCCESS', 'OK', NOW() + 5;

END~



        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_flat_encounter_table_create  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_mamba_flat_encounter_table_create;


~
CREATE PROCEDURE sp_mamba_flat_encounter_table_create(
    IN flat_encounter_table_name VARCHAR(255) CHARSET UTF8MB4
)
BEGIN

    SET session group_concat_max_len = 20000;
    SET @column_labels := NULL;

    SET @drop_table = CONCAT('DROP TABLE IF EXISTS `', flat_encounter_table_name, '`');

    SELECT GROUP_CONCAT(column_label SEPARATOR ' TEXT, ')
    INTO @column_labels
    FROM mamba_dim_concept_metadata
    WHERE flat_table_name = flat_encounter_table_name
      AND concept_datatype IS NOT NULL;

    IF @column_labels IS NULL THEN
        SET @create_table = CONCAT(
                'CREATE TABLE `', flat_encounter_table_name, '` (encounter_id INT NOT NULL, client_id INT NOT NULL, encounter_datetime DATETIME NOT NULL, INDEX idx_encounter_id (encounter_id), INDEX idx_client_id (client_id), INDEX idx_encounter_datetime (encounter_datetime));');
    ELSE
        SET @create_table = CONCAT(
                'CREATE TABLE `', flat_encounter_table_name, '` (encounter_id INT NOT NULL, client_id INT NOT NULL, encounter_datetime DATETIME NOT NULL, ', @column_labels, ' TEXT, INDEX idx_encounter_id (encounter_id), INDEX idx_client_id (client_id), INDEX idx_encounter_datetime (encounter_datetime));');
    END IF;


    PREPARE deletetb FROM @drop_table;
    PREPARE createtb FROM @create_table;

    EXECUTE deletetb;
    EXECUTE createtb;

    DEALLOCATE PREPARE deletetb;
    DEALLOCATE PREPARE createtb;

END~



        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_flat_encounter_table_create_all  ----------------------------
-- ---------------------------------------------------------------------------------------------

-- Flatten all Encounters given in Config folder
DROP PROCEDURE IF EXISTS sp_mamba_flat_encounter_table_create_all;


~
CREATE PROCEDURE sp_mamba_flat_encounter_table_create_all()
BEGIN

    DECLARE tbl_name CHAR(50) CHARACTER SET UTF8MB4;

    DECLARE done INT DEFAULT FALSE;

    DECLARE cursor_flat_tables CURSOR FOR
        SELECT DISTINCT(flat_table_name) FROM mamba_dim_concept_metadata;

    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

    OPEN cursor_flat_tables;
    computations_loop:
    LOOP
        FETCH cursor_flat_tables INTO tbl_name;

        IF done THEN
            LEAVE computations_loop;
        END IF;

        CALL sp_mamba_flat_encounter_table_create(tbl_name);

    END LOOP computations_loop;
    CLOSE cursor_flat_tables;

END~



        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_flat_encounter_table_insert  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_mamba_flat_encounter_table_insert;


~
CREATE PROCEDURE sp_mamba_flat_encounter_table_insert(
    IN flat_encounter_table_name CHAR(255) CHARACTER SET UTF8MB4
)
BEGIN

    SET session group_concat_max_len = 20000;
    SET @tbl_name = flat_encounter_table_name;

    SET @old_sql = (SELECT GROUP_CONCAT(COLUMN_NAME SEPARATOR ', ')
                    FROM INFORMATION_SCHEMA.COLUMNS
                    WHERE TABLE_NAME = @tbl_name
                      AND TABLE_SCHEMA = Database());

    SELECT
        GROUP_CONCAT(DISTINCT
            CONCAT(' MAX(CASE WHEN column_label = ''', column_label, ''' THEN ',
                fn_mamba_get_obs_value_column(concept_datatype), ' END) ', column_label)
            ORDER BY id ASC)
    INTO @column_labels
    FROM mamba_dim_concept_metadata
    WHERE flat_table_name = @tbl_name;

    SET @insert_stmt = CONCAT(
            'INSERT INTO `', @tbl_name, '` SELECT eo.encounter_id, eo.person_id, eo.encounter_datetime, ',
            @column_labels, '
            FROM mamba_z_encounter_obs eo
                INNER JOIN mamba_dim_concept_metadata cm
                ON IF(cm.concept_answer_obs=1, cm.concept_uuid=eo.obs_value_coded_uuid, cm.concept_uuid=eo.obs_question_uuid)
            WHERE cm.flat_table_name = ''', @tbl_name, '''
            AND eo.encounter_type_uuid = cm.encounter_type_uuid
            AND eo.row_num = cm.row_num AND eo.obs_group_id IS NULL
            GROUP BY eo.encounter_id, eo.person_id, eo.encounter_datetime;');

    PREPARE inserttbl FROM @insert_stmt;
    EXECUTE inserttbl;
    DEALLOCATE PREPARE inserttbl;

END~



        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_flat_encounter_table_insert_all  ----------------------------
-- ---------------------------------------------------------------------------------------------

-- Flatten all Encounters given in Config folder
DROP PROCEDURE IF EXISTS sp_mamba_flat_encounter_table_insert_all;


~
CREATE PROCEDURE sp_mamba_flat_encounter_table_insert_all()
BEGIN

    DECLARE tbl_name CHAR(50) CHARACTER SET UTF8MB4;

    DECLARE done INT DEFAULT FALSE;

    DECLARE cursor_flat_tables CURSOR FOR
        SELECT DISTINCT(flat_table_name) FROM mamba_dim_concept_metadata;

    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

    OPEN cursor_flat_tables;
    computations_loop:
    LOOP
        FETCH cursor_flat_tables INTO tbl_name;

        IF done THEN
            LEAVE computations_loop;
        END IF;

        CALL sp_mamba_flat_encounter_table_insert(tbl_name);

    END LOOP computations_loop;
    CLOSE cursor_flat_tables;

END~



        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_multiselect_values_update  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS `sp_mamba_multiselect_values_update`;


~
CREATE PROCEDURE `sp_mamba_multiselect_values_update`(
    IN table_to_update CHAR(100) CHARACTER SET UTF8MB4,
    IN column_names TEXT CHARACTER SET UTF8MB4,
    IN value_yes CHAR(100) CHARACTER SET UTF8MB4,
    IN value_no CHAR(100) CHARACTER SET UTF8MB4
)
BEGIN

    SET @table_columns = column_names;
    SET @start_pos = 1;
    SET @comma_pos = locate(',', @table_columns);
    SET @end_loop = 0;

    SET @column_label = '';

    REPEAT
        IF @comma_pos > 0 THEN
            SET @column_label = substring(@table_columns, @start_pos, @comma_pos - @start_pos);
            SET @end_loop = 0;
        ELSE
            SET @column_label = substring(@table_columns, @start_pos);
            SET @end_loop = 1;
        END IF;

        -- UPDATE fact_hts SET @column_label=IF(@column_label IS NULL OR '', new_value_if_false, new_value_if_true);

        SET @update_sql = CONCAT(
                'UPDATE ', table_to_update, ' SET ', @column_label, '= IF(', @column_label, ' IS NOT NULL, ''',
                value_yes, ''', ''', value_no, ''');');
        PREPARE stmt FROM @update_sql;
        EXECUTE stmt;
        DEALLOCATE PREPARE stmt;

        IF @end_loop = 0 THEN
            SET @table_columns = substring(@table_columns, @comma_pos + 1);
            SET @comma_pos = locate(',', @table_columns);
        END IF;
    UNTIL @end_loop = 1
        END REPEAT;

END~



        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_extract_report_metadata  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_mamba_extract_report_metadata;


~
CREATE PROCEDURE sp_mamba_extract_report_metadata(
    IN report_data MEDIUMTEXT CHARACTER SET UTF8MB4,
    IN metadata_table VARCHAR(255) CHARSET UTF8MB4
)
BEGIN

    SET session group_concat_max_len = 20000;

    SELECT JSON_EXTRACT(report_data, '$.flat_report_metadata') INTO @report_array;
    SELECT JSON_LENGTH(@report_array) INTO @report_array_len;

    SET @report_count = 0;
    WHILE @report_count < @report_array_len
        DO

            SELECT JSON_EXTRACT(@report_array, CONCAT('$[', @report_count, ']')) INTO @report;
            SELECT JSON_EXTRACT(@report, '$.report_name') INTO @report_name;
            SELECT JSON_EXTRACT(@report, '$.flat_table_name') INTO @flat_table_name;
            SELECT JSON_EXTRACT(@report, '$.encounter_type_uuid') INTO @encounter_type;
            SELECT JSON_EXTRACT(@report, '$.concepts_locale') INTO @concepts_locale;
            SELECT JSON_EXTRACT(@report, '$.table_columns') INTO @column_array;

            SELECT JSON_KEYS(@column_array) INTO @column_keys_array;
            SELECT JSON_LENGTH(@column_keys_array) INTO @column_keys_array_len;
            SET @col_count = 0;
            WHILE @col_count < @column_keys_array_len
                DO
                    SELECT JSON_EXTRACT(@column_keys_array, CONCAT('$[', @col_count, ']')) INTO @field_name;
                    SELECT JSON_EXTRACT(@column_array, CONCAT('$.', @field_name)) INTO @concept_uuid;

                    SET @tbl_name = '';
                    INSERT INTO mamba_dim_concept_metadata
                        (
                            report_name,
                            flat_table_name,
                            encounter_type_uuid,
                            column_label,
                            concept_uuid,
                            concepts_locale
                        )
                    VALUES (JSON_UNQUOTE(@report_name),
                            JSON_UNQUOTE(@flat_table_name),
                            JSON_UNQUOTE(@encounter_type),
                            JSON_UNQUOTE(@field_name),
                            JSON_UNQUOTE(@concept_uuid),
                            JSON_UNQUOTE(@concepts_locale));

                    SET @col_count = @col_count + 1;
                END WHILE;

            SET @report_count = @report_count + 1;
        END WHILE;

END~



        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_extract_report_definition_metadata  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_mamba_extract_report_definition_metadata;


~
CREATE PROCEDURE sp_mamba_extract_report_definition_metadata(
    IN report_definition_json JSON,
    IN metadata_table VARCHAR(255) CHARSET UTF8MB4
)
BEGIN

    IF report_definition_json IS NULL OR JSON_LENGTH(report_definition_json) = 0 THEN
        SIGNAL SQLSTATE '02000'
            SET MESSAGE_TEXT = 'Warn: report_definition_json is empty or null.';
    ELSE

        SET session group_concat_max_len = 20000;

        SELECT JSON_EXTRACT(report_definition_json, '$.report_definitions') INTO @report_array;
        SELECT JSON_LENGTH(@report_array) INTO @report_array_len;

        SET @report_count = 0;
        WHILE @report_count < @report_array_len
            DO

                SELECT JSON_EXTRACT(@report_array, CONCAT('$[', @report_count, ']')) INTO @report;
                SELECT JSON_UNQUOTE(JSON_EXTRACT(@report, '$.report_name')) INTO @report_name;
                SELECT JSON_UNQUOTE(JSON_EXTRACT(@report, '$.report_id')) INTO @report_id;
                SELECT CONCAT('sp_mamba_', @report_id, '_query') INTO @report_procedure_name;
                SELECT CONCAT('sp_mamba_', @report_id, '_columns_query') INTO @report_columns_procedure_name;
                SELECT CONCAT('mamba_dim_', @report_id) INTO @table_name;
                SELECT JSON_UNQUOTE(JSON_EXTRACT(@report, CONCAT('$.report_sql.sql_query'))) INTO @sql_query;
                SELECT JSON_EXTRACT(@report, CONCAT('$.report_sql.query_params')) INTO @query_params_array;

                INSERT INTO mamba_dim_report_definition(report_id,
                                                        report_procedure_name,
                                                        report_columns_procedure_name,
                                                        sql_query,
                                                        table_name,
                                                        report_name)
                VALUES (@report_id,
                        @report_procedure_name,
                        @report_columns_procedure_name,
                        @sql_query,
                        @table_name,
                        @report_name);

                -- Iterate over the "params" array for each report
                SELECT JSON_LENGTH(@query_params_array) INTO @total_params;

                SET @parameters := NULL;
                SET @param_count = 0;
                WHILE @param_count < @total_params
                    DO
                        SELECT JSON_EXTRACT(@query_params_array, CONCAT('$[', @param_count, ']')) INTO @param;
                        SELECT JSON_UNQUOTE(JSON_EXTRACT(@param, '$.name')) INTO @param_name;
                        SELECT JSON_UNQUOTE(JSON_EXTRACT(@param, '$.type')) INTO @param_type;
                        SET @param_position = @param_count + 1;

                        INSERT INTO mamba_dim_report_definition_parameters(report_id,
                                                                           parameter_name,
                                                                           parameter_type,
                                                                           parameter_position)
                        VALUES (@report_id,
                                @param_name,
                                @param_type,
                                @param_position);

                        SET @param_count = @param_position;
                    END WHILE;


--                SELECT GROUP_CONCAT(COLUMN_NAME SEPARATOR ', ')
--                INTO @column_names
--                FROM INFORMATION_SCHEMA.COLUMNS
--                -- WHERE TABLE_SCHEMA = 'alive' TODO: add back after verifying schema name
--                WHERE TABLE_NAME = @report_id;
--
--                SET @drop_table = CONCAT('DROP TABLE IF EXISTS `', @report_id, '`');
--
--                SET @createtb = CONCAT('CREATE TEMP TABLE AS SELECT ', @report_id, ';', CHAR(10),
--                                       'CREATE PROCEDURE ', @report_procedure_name, '(', CHAR(10),
--                                       @parameters, CHAR(10),
--                                       ')', CHAR(10),
--                                       'BEGIN', CHAR(10),
--                                       @sql_query, CHAR(10),
--                                       'END;', CHAR(10));
--
--                PREPARE deletetb FROM @drop_table;
--                PREPARE createtb FROM @create_table;
--
--               EXECUTE deletetb;
--               EXECUTE createtb;
--
--                DEALLOCATE PREPARE deletetb;
--                DEALLOCATE PREPARE createtb;

                --                SELECT GROUP_CONCAT(CONCAT('IN ', parameter_name, ' ', parameter_type) SEPARATOR ', ')
--                INTO @parameters
--                FROM mamba_dim_report_definition_parameters
--                WHERE report_id = @report_id
--                ORDER BY parameter_position;
--
--                SET @procedure_definition = CONCAT('DROP PROCEDURE IF EXISTS ', @report_procedure_name, ';', CHAR(10),
--                                                   'CREATE PROCEDURE ', @report_procedure_name, '(', CHAR(10),
--                                                   @parameters, CHAR(10),
--                                                   ')', CHAR(10),
--                                                   'BEGIN', CHAR(10),
--                                                   @sql_query, CHAR(10),
--                                                   'END;', CHAR(10));
--
--                PREPARE CREATE_PROC FROM @procedure_definition;
--                EXECUTE CREATE_PROC;
--                DEALLOCATE PREPARE CREATE_PROC;
--
                SET @report_count = @report_count + 1;
            END WHILE;

    END IF;

END~



        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_load_agegroup  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_mamba_load_agegroup;


~
CREATE PROCEDURE sp_mamba_load_agegroup()
BEGIN
    DECLARE age INT DEFAULT 0;
    WHILE age <= 120
        DO
            INSERT INTO mamba_dim_agegroup(age, datim_agegroup, normal_agegroup)
            VALUES (age, fn_mamba_calculate_agegroup(age), IF(age < 15, '<15', '15+'));
            SET age = age + 1;
        END WHILE;
END~



        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_get_report_column_names  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_mamba_get_report_column_names;


~
CREATE PROCEDURE sp_mamba_get_report_column_names(IN report_identifier VARCHAR(255))
BEGIN

    -- We could also pick the column names from the report definition table but it is in a comma-separated list (weigh both options)
    SELECT table_name
    INTO @table_name
    FROM mamba_dim_report_definition
    WHERE report_id = report_identifier;

    SELECT COLUMN_NAME
    FROM INFORMATION_SCHEMA.COLUMNS
    WHERE TABLE_NAME = @table_name;

END~



        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_generate_report_wrapper  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_mamba_generate_report_wrapper;


~
CREATE PROCEDURE sp_mamba_generate_report_wrapper(IN generate_columns_flag TINYINT(1),
                                                  IN report_identifier VARCHAR(255),
                                                  IN parameter_list JSON)
BEGIN

    DECLARE proc_name VARCHAR(255);
    DECLARE sql_args VARCHAR(1000);
    DECLARE arg_name VARCHAR(50);
    DECLARE arg_value VARCHAR(255);
    DECLARE tester VARCHAR(255);
    DECLARE done INT DEFAULT FALSE;

    DECLARE cursor_parameter_names CURSOR FOR
        SELECT DISTINCT (p.parameter_name)
        FROM mamba_dim_report_definition_parameters p
        WHERE p.report_id = report_identifier;

    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

    IF generate_columns_flag = 1 THEN
        SET proc_name = (SELECT DISTINCT (rd.report_columns_procedure_name)
                         FROM mamba_dim_report_definition rd
                         WHERE rd.report_id = report_identifier);
    ELSE
        SET proc_name = (SELECT DISTINCT (rd.report_procedure_name)
                         FROM mamba_dim_report_definition rd
                         WHERE rd.report_id = report_identifier);
    END IF;

    OPEN cursor_parameter_names;
    read_loop:
    LOOP
        FETCH cursor_parameter_names INTO arg_name;

        IF done THEN
            LEAVE read_loop;
        END IF;

        SET arg_value = IFNULL((JSON_EXTRACT(parameter_list, CONCAT('$[', ((SELECT p.parameter_position
                                                                            FROM mamba_dim_report_definition_parameters p
                                                                            WHERE p.parameter_name = arg_name
                                                                              AND p.report_id = report_identifier) - 1),
                                                                    '].value'))), 'NULL');
        SET tester = CONCAT_WS(', ', tester, arg_value);
        SET sql_args = IFNULL(CONCAT_WS(', ', sql_args, arg_value), NULL);

    END LOOP;

    CLOSE cursor_parameter_names;

    SET @sql = CONCAT('CALL ', proc_name, '(', IFNULL(sql_args, ''), ')');

    PREPARE stmt FROM @sql;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;

END~



        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_dim_location_create  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_mamba_dim_location_create;


~
CREATE PROCEDURE sp_mamba_dim_location_create()
BEGIN
-- $BEGIN

CREATE TABLE mamba_dim_location
(
    id              INT          NOT NULL AUTO_INCREMENT,
    location_id     INT          NOT NULL,
    name            VARCHAR(255) NOT NULL,
    description     VARCHAR(255) NULL,
    city_village    VARCHAR(255) NULL,
    state_province  VARCHAR(255) NULL,
    postal_code     VARCHAR(50)  NULL,
    country         VARCHAR(50)  NULL,
    latitude        VARCHAR(50)  NULL,
    longitude       VARCHAR(50)  NULL,
    county_district VARCHAR(255) NULL,
    address1        VARCHAR(255) NULL,
    address2        VARCHAR(255) NULL,
    address3        VARCHAR(255) NULL,
    address4        VARCHAR(255) NULL,
    address5        VARCHAR(255) NULL,
    address6        VARCHAR(255) NULL,
    address7        VARCHAR(255) NULL,
    address8        VARCHAR(255) NULL,
    address9        VARCHAR(255) NULL,
    address10       VARCHAR(255) NULL,
    address11       VARCHAR(255) NULL,
    address12       VARCHAR(255) NULL,
    address13       VARCHAR(255) NULL,
    address14       VARCHAR(255) NULL,
    address15       VARCHAR(255) NULL,

    PRIMARY KEY (id)
)
    CHARSET = UTF8MB4;

CREATE INDEX mamba_dim_location_location_id_index
    ON mamba_dim_location (location_id);

CREATE INDEX mamba_dim_location_name_index
    ON mamba_dim_location (name);

-- $END
END~


        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_dim_location_insert  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_mamba_dim_location_insert;


~
CREATE PROCEDURE sp_mamba_dim_location_insert()
BEGIN
-- $BEGIN

INSERT INTO mamba_dim_location (location_id,
                                name,
                                description,
                                city_village,
                                state_province,
                                postal_code,
                                country,
                                latitude,
                                longitude,
                                county_district,
                                address1,
                                address2,
                                address3,
                                address4,
                                address5,
                                address6,
                                address7,
                                address8,
                                address9,
                                address10,
                                address11,
                                address12,
                                address13,
                                address14,
                                address15)
SELECT location_id,
       name,
       description,
       city_village,
       state_province,
       postal_code,
       country,
       latitude,
       longitude,
       county_district,
       address1,
       address2,
       address3,
       address4,
       address5,
       address6,
       address7,
       address8,
       address9,
       address10,
       address11,
       address12,
       address13,
       address14,
       address15
FROM openmrs.location;

-- $END
END~


        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_dim_location_update  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_mamba_dim_location_update;


~
CREATE PROCEDURE sp_mamba_dim_location_update()
BEGIN
-- $BEGIN

-- $END
END~


        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_dim_location  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_mamba_dim_location;


~
CREATE PROCEDURE sp_mamba_dim_location()
BEGIN
-- $BEGIN

CALL sp_mamba_dim_location_create();
CALL sp_mamba_dim_location_insert();
CALL sp_mamba_dim_location_update();

-- $END
END~


        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_dim_patient_identifier_type_create  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_mamba_dim_patient_identifier_type_create;


~
CREATE PROCEDURE sp_mamba_dim_patient_identifier_type_create()
BEGIN
-- $BEGIN

CREATE TABLE mamba_dim_patient_identifier_type
(
    id                         INT         NOT NULL AUTO_INCREMENT,
    patient_identifier_type_id INT         NOT NULL,
    name                       VARCHAR(50) NOT NULL,
    description                TEXT        NULL,
    uuid                       CHAR(38)    NOT NULL,

    PRIMARY KEY (id)
)
    CHARSET = UTF8MB4;

CREATE INDEX mamba_dim_patient_identifier_type_id_index
    ON mamba_dim_patient_identifier_type (patient_identifier_type_id);

CREATE INDEX mamba_dim_patient_identifier_type_name_index
    ON mamba_dim_patient_identifier_type (name);

CREATE INDEX mamba_dim_patient_identifier_type_uuid_index
    ON mamba_dim_patient_identifier_type (uuid);

-- $END
END~


        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_dim_patient_identifier_type_insert  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_mamba_dim_patient_identifier_type_insert;


~
CREATE PROCEDURE sp_mamba_dim_patient_identifier_type_insert()
BEGIN
-- $BEGIN

INSERT INTO mamba_dim_patient_identifier_type (patient_identifier_type_id,
                                               name,
                                               description,
                                               uuid)
SELECT patient_identifier_type_id,
       name,
       description,
       uuid
FROM openmrs.patient_identifier_type;

-- $END
END~


        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_dim_patient_identifier_type_update  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_mamba_dim_patient_identifier_type_update;


~
CREATE PROCEDURE sp_mamba_dim_patient_identifier_type_update()
BEGIN
-- $BEGIN

-- $END
END~


        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_dim_patient_identifier_type  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_mamba_dim_patient_identifier_type;


~
CREATE PROCEDURE sp_mamba_dim_patient_identifier_type()
BEGIN
-- $BEGIN

CALL sp_mamba_dim_patient_identifier_type_create();
CALL sp_mamba_dim_patient_identifier_type_insert();
CALL sp_mamba_dim_patient_identifier_type_update();

-- $END
END~


        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_dim_concept_datatype_create  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_mamba_dim_concept_datatype_create;


~
CREATE PROCEDURE sp_mamba_dim_concept_datatype_create()
BEGIN
-- $BEGIN

CREATE TABLE mamba_dim_concept_datatype
(
    id                  INT          NOT NULL AUTO_INCREMENT,
    concept_datatype_id INT          NOT NULL,
    datatype_name       VARCHAR(255) NOT NULL,

    PRIMARY KEY (id)
)
    CHARSET = UTF8MB4;

CREATE INDEX mamba_dim_concept_datatype_concept_datatype_id_index
    ON mamba_dim_concept_datatype (concept_datatype_id);

-- $END
END~


        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_dim_concept_datatype_insert  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_mamba_dim_concept_datatype_insert;


~
CREATE PROCEDURE sp_mamba_dim_concept_datatype_insert()
BEGIN
-- $BEGIN

INSERT INTO mamba_dim_concept_datatype (concept_datatype_id,
                                        datatype_name)
SELECT dt.concept_datatype_id AS concept_datatype_id,
       dt.name                AS datatype_name
FROM openmrs.concept_datatype dt;
-- WHERE dt.retired = 0;

-- $END
END~


        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_dim_concept_datatype  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_mamba_dim_concept_datatype;


~
CREATE PROCEDURE sp_mamba_dim_concept_datatype()
BEGIN
-- $BEGIN

CALL sp_mamba_dim_concept_datatype_create();
CALL sp_mamba_dim_concept_datatype_insert();

-- $END
END~


        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_dim_concept_create  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_mamba_dim_concept_create;


~
CREATE PROCEDURE sp_mamba_dim_concept_create()
BEGIN
-- $BEGIN

CREATE TABLE mamba_dim_concept
(
    id          INT          NOT NULL AUTO_INCREMENT,
    concept_id  INT          NOT NULL,
    uuid        CHAR(38)     NOT NULL,
    datatype_id INT          NOT NULL, -- make it a FK
    datatype    VARCHAR(100) NULL,
    retired     TINYINT(1)   NOT NULL,

    PRIMARY KEY (id)
)
    CHARSET = UTF8MB4;

CREATE INDEX mamba_dim_concept_concept_id_index
    ON mamba_dim_concept (concept_id);

CREATE INDEX mamba_dim_concept_uuid_index
    ON mamba_dim_concept (uuid);

CREATE INDEX mamba_dim_concept_datatype_id_index
    ON mamba_dim_concept (datatype_id);

CREATE INDEX mamba_dim_concept_retired_index
    ON mamba_dim_concept (retired);

-- $END
END~


        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_dim_concept_insert  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_mamba_dim_concept_insert;


~
CREATE PROCEDURE sp_mamba_dim_concept_insert()
BEGIN
-- $BEGIN

INSERT INTO mamba_dim_concept (uuid,
                               concept_id,
                               datatype_id,
                               retired)
SELECT c.uuid        AS uuid,
       c.concept_id  AS concept_id,
       c.datatype_id AS datatype_id,
       c.retired
FROM openmrs.concept c;
-- WHERE c.retired = 0;

-- $END
END~


        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_dim_concept_update  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_mamba_dim_concept_update;


~
CREATE PROCEDURE sp_mamba_dim_concept_update()
BEGIN
-- $BEGIN

UPDATE mamba_dim_concept c
    INNER JOIN mamba_dim_concept_datatype dt
    ON c.datatype_id = dt.concept_datatype_id
SET c.datatype = dt.datatype_name
WHERE c.id > 0;

-- $END
END~


        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_dim_concept  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_mamba_dim_concept;


~
CREATE PROCEDURE sp_mamba_dim_concept()
BEGIN
-- $BEGIN

CALL sp_mamba_dim_concept_create();
CALL sp_mamba_dim_concept_insert();
CALL sp_mamba_dim_concept_update();

-- $END
END~


        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_dim_concept_answer_create  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_mamba_dim_concept_answer_create;


~
CREATE PROCEDURE sp_mamba_dim_concept_answer_create()
BEGIN
-- $BEGIN

CREATE TABLE mamba_dim_concept_answer
(
    id                INT NOT NULL AUTO_INCREMENT,
    concept_answer_id INT NOT NULL,
    concept_id        INT NOT NULL,
    answer_concept    INT,
    answer_drug       INT,

    PRIMARY KEY (id)
)
    CHARSET = UTF8MB4;

CREATE INDEX mamba_dim_concept_answer_concept_answer_id_index
    ON mamba_dim_concept_answer (concept_answer_id);

CREATE INDEX mamba_dim_concept_answer_concept_id_index
    ON mamba_dim_concept_answer (concept_id);

-- $END
END~


        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_dim_concept_answer_insert  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_mamba_dim_concept_answer_insert;


~
CREATE PROCEDURE sp_mamba_dim_concept_answer_insert()
BEGIN
-- $BEGIN

INSERT INTO mamba_dim_concept_answer (concept_answer_id,
                                      concept_id,
                                      answer_concept,
                                      answer_drug)
SELECT ca.concept_answer_id AS concept_answer_id,
       ca.concept_id        AS concept_id,
       ca.answer_concept    AS answer_concept,
       ca.answer_drug       AS answer_drug
FROM openmrs.concept_answer ca;

-- $END
END~


        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_dim_concept_answer  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_mamba_dim_concept_answer;


~
CREATE PROCEDURE sp_mamba_dim_concept_answer()
BEGIN
-- $BEGIN

CALL sp_mamba_dim_concept_answer_create();
CALL sp_mamba_dim_concept_answer_insert();

-- $END
END~


        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_dim_concept_name_create  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_mamba_dim_concept_name_create;


~
CREATE PROCEDURE sp_mamba_dim_concept_name_create()
BEGIN
-- $BEGIN

CREATE TABLE mamba_dim_concept_name
(
    id                INT          NOT NULL AUTO_INCREMENT,
    concept_name_id   INT          NOT NULL,
    concept_id        INT,
    name              VARCHAR(255) NOT NULL,
    locale            VARCHAR(50)  NOT NULL,
    locale_preferred  TINYINT,
    concept_name_type VARCHAR(255),

    PRIMARY KEY (id)
)
    CHARSET = UTF8MB4;

CREATE INDEX mamba_dim_concept_name_concept_name_id_index
    ON mamba_dim_concept_name (concept_name_id);

CREATE INDEX mamba_dim_concept_name_concept_id_index
    ON mamba_dim_concept_name (concept_id);

CREATE INDEX mamba_dim_concept_name_concept_name_type_index
    ON mamba_dim_concept_name (concept_name_type);

CREATE INDEX mamba_dim_concept_name_locale_index
    ON mamba_dim_concept_name (locale);

CREATE INDEX mamba_dim_concept_name_locale_preferred_index
    ON mamba_dim_concept_name (locale_preferred);

-- $END
END~


        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_dim_concept_name_insert  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_mamba_dim_concept_name_insert;


~
CREATE PROCEDURE sp_mamba_dim_concept_name_insert()
BEGIN
-- $BEGIN

INSERT INTO mamba_dim_concept_name (concept_name_id,
                                    concept_id,
                                    name,
                                    locale,
                                    locale_preferred,
                                    concept_name_type)
SELECT cn.concept_name_id,
       cn.concept_id,
       cn.name,
       cn.locale,
       cn.locale_preferred,
       cn.concept_name_type
FROM openmrs.concept_name cn
 WHERE cn.locale = 'en'
  AND cn.locale_preferred = 1
    AND cn.voided = 0;

-- $END
END~


        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_dim_concept_name  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_mamba_dim_concept_name;


~
CREATE PROCEDURE sp_mamba_dim_concept_name()
BEGIN
-- $BEGIN

CALL sp_mamba_dim_concept_name_create();
CALL sp_mamba_dim_concept_name_insert();

-- $END
END~


        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_dim_encounter_type_create  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_mamba_dim_encounter_type_create;


~
CREATE PROCEDURE sp_mamba_dim_encounter_type_create()
BEGIN
-- $BEGIN

CREATE TABLE mamba_dim_encounter_type
(
    id                INT         NOT NULL AUTO_INCREMENT,
    encounter_type_id INT         NOT NULL,
    uuid              CHAR(38)    NOT NULL,
    name              VARCHAR(50) NOT NULL,

    PRIMARY KEY (id)
)
    CHARSET = UTF8MB4;

CREATE INDEX mamba_dim_encounter_type_encounter_type_id_index
    ON mamba_dim_encounter_type (encounter_type_id);

CREATE INDEX mamba_dim_encounter_type_uuid_index
    ON mamba_dim_encounter_type (uuid);

-- $END
END~


        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_dim_encounter_type_insert  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_mamba_dim_encounter_type_insert;


~
CREATE PROCEDURE sp_mamba_dim_encounter_type_insert()
BEGIN
-- $BEGIN

INSERT INTO mamba_dim_encounter_type (encounter_type_id,
                                      uuid,
                                      name)
SELECT et.encounter_type_id,
       et.uuid,
       et.name
FROM openmrs.encounter_type et;
-- WHERE et.retired = 0;

-- $END
END~


        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_dim_encounter_type  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_mamba_dim_encounter_type;


~
CREATE PROCEDURE sp_mamba_dim_encounter_type()
BEGIN
-- $BEGIN

CALL sp_mamba_dim_encounter_type_create();
CALL sp_mamba_dim_encounter_type_insert();

-- $END
END~


        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_dim_encounter_create  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_mamba_dim_encounter_create;


~
CREATE PROCEDURE sp_mamba_dim_encounter_create()
BEGIN
-- $BEGIN

CREATE TABLE mamba_dim_encounter
(
    id                  INT      NOT NULL AUTO_INCREMENT,
    encounter_id        INT      NOT NULL,
    uuid                CHAR(38) NOT NULL,
    encounter_type      INT      NOT NULL,
    encounter_type_uuid CHAR(38) NULL,
    patient_id          INT      NOT NULL,
    encounter_datetime  DATETIME NOT NULL,
    date_created        DATETIME NOT NULL,
    voided              TINYINT  NOT NULL,
    visit_id            INT      NULL,

    CONSTRAINT encounter_encounter_id_index
        UNIQUE (encounter_id),

    CONSTRAINT encounter_uuid_index
        UNIQUE (uuid),

    PRIMARY KEY (id)
)
    CHARSET = UTF8MB4;

CREATE INDEX mamba_dim_encounter_encounter_id_index
    ON mamba_dim_encounter (encounter_id);

CREATE INDEX mamba_dim_encounter_encounter_type_index
    ON mamba_dim_encounter (encounter_type);

CREATE INDEX mamba_dim_encounter_uuid_index
    ON mamba_dim_encounter (uuid);

CREATE INDEX mamba_dim_encounter_encounter_type_uuid_index
    ON mamba_dim_encounter (encounter_type_uuid);

CREATE INDEX mamba_dim_encounter_patient_id_index
    ON mamba_dim_encounter (patient_id);

-- $END
END~


        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_dim_encounter_insert  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_mamba_dim_encounter_insert;


~
CREATE PROCEDURE sp_mamba_dim_encounter_insert()
BEGIN
-- $BEGIN

INSERT INTO mamba_dim_encounter (encounter_id,
                                 uuid,
                                 encounter_type,
                                 encounter_type_uuid,
                                 patient_id,
                                 encounter_datetime,
                                 date_created,
                                 voided,
                                 visit_id)
SELECT e.encounter_id,
       e.uuid,
       e.encounter_type,
       et.uuid,
       e.patient_id,
       e.encounter_datetime,
       e.date_created,
       e.voided,
       e.visit_id
FROM openmrs.encounter e
         INNER JOIN mamba_dim_encounter_type et
                    ON e.encounter_type = et.encounter_type_id
WHERE et.uuid
          IN (SELECT DISTINCT(md.encounter_type_uuid)
              FROM mamba_dim_concept_metadata md);

-- $END
END~


        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_dim_encounter_update  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_mamba_dim_encounter_update;


~
CREATE PROCEDURE sp_mamba_dim_encounter_update()
BEGIN
-- $BEGIN
-- $END
END~


        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_dim_encounter  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_mamba_dim_encounter;


~
CREATE PROCEDURE sp_mamba_dim_encounter()
BEGIN
-- $BEGIN

CALL sp_mamba_dim_encounter_create();
CALL sp_mamba_dim_encounter_insert();
CALL sp_mamba_dim_encounter_update();

-- $END
END~


        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_dim_concept_metadata_create  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_mamba_dim_concept_metadata_create;


~
CREATE PROCEDURE sp_mamba_dim_concept_metadata_create()
BEGIN
-- $BEGIN

CREATE TABLE mamba_dim_concept_metadata
(
    id                  INT          NOT NULL AUTO_INCREMENT,
    concept_id          INT          NULL,
    concept_uuid        CHAR(38)     NOT NULL,
    concept_name        VARCHAR(255) NULL,
    concepts_locale     VARCHAR(20)  NOT NULL,
    column_number       INT,
    column_label        VARCHAR(50)  NOT NULL,
    concept_datatype    VARCHAR(255) NULL,
    concept_answer_obs  TINYINT      NOT NULL DEFAULT 0,
    report_name         VARCHAR(255) NOT NULL,
    flat_table_name     VARCHAR(255) NULL,
    encounter_type_uuid CHAR(38)     NOT NULL,
    row_num             INT          NULL DEFAULT 1,

    PRIMARY KEY (id)
)
    CHARSET = UTF8MB4;

CREATE INDEX mamba_dim_concept_metadata_concept_id_index
    ON mamba_dim_concept_metadata (concept_id);

CREATE INDEX mamba_dim_concept_metadata_concept_uuid_index
    ON mamba_dim_concept_metadata (concept_uuid);

CREATE INDEX mamba_dim_concept_metadata_encounter_type_uuid_index
    ON mamba_dim_concept_metadata (encounter_type_uuid);

CREATE INDEX mamba_dim_concept_metadata_concepts_locale_index
    ON mamba_dim_concept_metadata (concepts_locale);

CREATE INDEX mamba_dim_concept_metadata_row_num_index
    ON mamba_dim_concept_metadata (row_num);

CREATE INDEX mamba_dim_concept_metadata_flat_table_name_index
    ON mamba_dim_concept_metadata (flat_table_name);

-- ALTER TABLE `mamba_dim_concept_metadata`
--     ADD COLUMN `encounter_type_id` INT NULL AFTER `output_table_name`,
--     ADD CONSTRAINT `fk_encounter_type_id`
--         FOREIGN KEY (`encounter_type_id`) REFERENCES `mamba_dim_encounter_type` (`encounter_type_id`);

-- $END
END~


        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_dim_concept_metadata_insert  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_mamba_dim_concept_metadata_insert;


~
CREATE PROCEDURE sp_mamba_dim_concept_metadata_insert()
BEGIN
      -- $BEGIN

      SET @report_data = '{"flat_report_metadata":[{
    "report_name": "Community Linkage Report",
    "flat_table_name": "ssemr_flat_encounter_community_linkage",
    "encounter_type_uuid": "3c2df02e-6856-11ee-8c99-0242ac120002",
    "concepts_locale": "en",
    "table_columns": {
        "s_no":"bd89293d-c32c-4d05-878d-f4472530c9ab",
        "date_of_entry":"c09e9674-4bca-40e6-b6d6-7dc790900b50",
        "uan":"e8d6ed35-dad7-4370-a2fb-017a758406f4",
        "category_of_client":"579c3be1-7012-4956-b25d-77b686b70081",
        "name_of_cov_assigned":"5b258179-1872-4339-8b29-5ecf618bd4d3",
        "client_met_cov_in_the_community":"9d38ebe3-85fe-486d-9b76-798ce890e99a",
        "date_client_met_cov":"fef6ef98-6d8d-4921-80f2-93255a7a5faf",
        "cov_knows_client_address":"fb4b5185-a416-42b0-b949-96438f7b99a8",
        "comments":"c3659bf9-3f10-11e4-adec-0800271c1b75"
    }
},{
    "report_name": "High Viral Load Report",
    "flat_table_name": "ssemr_flat_encounter_high_viral_load",
    "encounter_type_uuid": "81852aee-3f10-11e4-adec-0800271c1b75",
    "concepts_locale": "en",
    "table_columns": {
        "arv_regimen":"23322fd6-3dbb-410e-8bee-6210dfcd5f71",
        "date_of_initiation_of_current _regimen":"30f1f347-d72c-4920-9962-6d55d138e8e5",
        "previous_vl":"2007c7b9-d822-4ce8-8a93-10f5423d1ab0",
        "recent_vl":"9c49058e-b7ac-4e00-9bdc-954f229587e4",
        "date_of_sample_collection":"ed520e2d-acb4-4ea9-8ae5-16ca27ace96d",
        "current_who_t_staging":"e38e6ce2-92d9-4382-927b-5555ff9fa850",
        "is_patient_currently_a_presumptive_tb":"8ecd1e68-96ef-48d7-9503-4094fa477f90",
        "history_of_chronic_diarrhea_or_vomiting":"1ec1b86a-26fd-48cb-b16d-1e81647b160a",
        "other_oi_or_signs_of_immunosuppression":"ce727eb4-ebb6-4690-b5f8-eae2e9c6bdc4",
        "history_of_side_effects_with_arvs":"f265ed7d-e5b7-4efc-a5d3-03604b75e30d",
        "patient_adherence_before_eac":"5fa774ef-a6b3-4673-9817-206a4208d3a3",
        "treatment_supporter_present":"7afd9229-199b-4c8f-93a9-aa78757b0fdc",
        "adherence_date":"039704e4-7d5c-4062-b490-8252069ba142",
        "adherence":"6ad327aa-d514-4b31-b21c-294a62a6fb30",
        "adherence_barriers":"4d1b1fcb-2f88-4c54-b81c-b80faea42146",
        "interventions":"bd2f2773-e364-47c1-b824-07a5e6efbe06",
        "first_eac_tools":"8f856c55-6048-4a5d-97b7-4c1ee8e64424",
        "pill_intake":"7ccc6227-f42f-4c45-822a-e11961ada35a",
        "behavioural":"fb428d3c-fb82-4182-9eca-d864a317c785",
        "cognitive":"824ebb29-6640-4b3f-b1dd-6f8a6c5063f5",
        "socio-economic":"3891c093-0daf-42a7-a140-571224c43678",
        "emotional":"32c19f52-dbcc-49f5-a017-f5ce589b7216",
        "identified_barriers_of_adherence_attended":"c7369d4b-ff53-4cb5-b6e8-f053eb01c2cc",
        "comments":"c3659bf9-3f10-11e4-adec-0800271c1b75",
        "date_of_extra_session":"0fa5669b-041d-446d-9ae6-8e88a980858f",
        "pill_intake":"7ccc6227-f42f-4c45-822a-e11961ada35a",
        "agreed-plan_of_action":"b2e88abb-5e64-406d-8f01-1a80b7af22e6",
        "date_of_collection_of_repeat_vl":"c8ef861f-cdcb-4605-83ae-29a95b1d5e27",
        "counsellor":"7d6f2311-5eb3-49fe-acda-9d591447ceb0",
        "date_of_assesment":"71d64d77-f53f-4093-9651-2952c5777275",
        "repeat_vl_result":"4921d88d-4471-433c-8b6e-a15fae80a552",
        "repeat_vl_result_<1000c/ml_>1000c/ml":"a1b9b338-fd39-4a8d-9f3a-aef2a9b0bce4",
        "repeat_vl_date":"06cee3e6-daaa-48cf-aa53-296254ee61a3",
        "plan_for_the_patient":"63df6d3e-4bf0-4463-871d-caca8875d77f",
        "new_regimen":"1b7d3042-5cdb-4966-a606-e69d9e8524ac",
        "date":"e605731b-2e81-41a9-8446-2ed442c339e2",
        "comments":"c3659bf9-3f10-11e4-adec-0800271c1b75",
        "art_provider_name":"b726713f-b6a1-4af4-b688-b563adf59269",
        "art_provider_signature":"c0fff453-a905-4697-972d-29db4a6fd59b",
        "date":"e605731b-2e81-41a9-8446-2ed442c339e2"    
    }
},{
  "report_name": "HIV Care Enrolment Report",
  "flat_table_name": "ssemr_flat_encounter_hiv_care_enrolment",
  "encounter_type_uuid": "81852aee-3f10-11e4-adec-0800271c1b75",
  "concepts_locale": "en",
  "table_columns": {
    "date_first_tested_positive": "7482b976-56fe-44b0-b30f-1e957cc0cbb0",
    "place_first_tested_positive":"e559db34-2171-11ea-978f-2e728ce88125",
    "entry_point":"f1a35737-434c-46e9-8c46-4e17b98e305d",
    "date_of_hiv_retesting_before_art":"fee1095f-a0a7-4694-a352-4e88d6ce6c7c",
    "place_of_hiv_retesting_before_art":"13d6e54e-c8bb-41b2-902b-6ad193f77f40",
    "transferred_in":"735cd395-0ef1-4832-a58c-e8afb567d3b3",
    "name_of_previous_clinic":"14026b25-6642-4002-a5ed-2049fb82afb2",
    "date_tranferred_in":"2509d668-caa1-4223-8f70-b0b61eb5b39e",
    "literate":"159400AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA",
    "alcohol_intake":"f77b636b-21e7-4568-9719-bbdd67c3c736",
    "drug_allergies":"0ca3ffa7-0d29-4043-8b78-3dd40f5806d1",
    "marital_status":"fdcd966e-0870-4d5d-a506-121efc0c39fb",
    "occupation":"c221869a-3f10-11e4-adec-0800271c1b75",
    "family_member_names":"53b3f5b4-85bc-415f-8e14-5bfc35cb2e89",
    "family_member_unique_number_if_in_care":"1074cef5-e4f9-470d-aaea-a4d8d7436c49",
    "family_member_hiv_status":"160f21c0-9a96-4ffd-a72c-aabed02b6a53",
    "is_family_member_in_art_care":"48741238-7ad4-42f0-a1f4-d2bb6b355614",
    "family_member_art_number":"15c72e37-bd8f-47ef-8f12-96e69e241b1a",
    "family_member_age":"db25b3c5-10da-49a9-9137-cc8a2507585a",
    "family_member_sex":"d969948d-6671-4149-a234-7b991c70e1c1",
    "were_arvs_received":"4ba4ea3a-a4dc-41c0-94ab-89a1985f9354",
    "if_yes_arvs_recieved":"6d75a232-9d41-46b6-a986-0551ce0b99c1",
    "place_where_arvs_were_received":"368dfc46-41a0-4a43-b945-f10ede2ac683",
    "drugs_and_duration":"d2e0b171-8986-4cb3-9669-d9c40274a705",
    "drugs_and_duration_start_date":"f7514566-e818-4034-a40e-f9d992d2860a",
    "drugs_and_duration_stop_date":"7528b97c-ad0b-41c7-8bed-6c41acb20272",
    "ctx_or_dapstone_start_date":"1f228c68-9628-4426-845b-0eca70d003df",
    "milestones_at_start_of_art":"0fca1e93-c834-487d-8ad0-bd351ceed278",
    "who_stage":"e38e6ce2-92d9-4382-927b-5555ff9fa850",
    "weight":"ca055d08-6cd7-4331-83bb-1d27dc5aa61f",
    "height":"50c9bb3c-494c-4a74-8018-1430edc0dba9",
    "date":"e605731b-2e81-41a9-8446-2ed442c339e2",
    "cd4":"809dd0f5-ce54-441c-b835-a2a8b06a6140",
    "art_readiness_confirmed_by":"14a4cf16-3781-4d5b-8a50-8e095d6a7cc6",
    "art_readiness_confirmation_date":"436e5ca5-a5ad-42e3-9a73-4325b82693ca",
    "initial_art_regimen_for_adult":"d1f02073-460c-4cd4-89b0-c16766e06334",
    "initial_art_regimen_for_children":"4f921b9d-4f29-4a7f-b506-f4d55d04d29e",
    "cohort":"d0214403-61a6-46dd-b8b1-bd8e8dfacd38",
    "art_regimen":"691bd529-9a89-475f-a9ab-7cbe61ca2158",
    "hivtc_new_regimen":"1b7d3042-5cdb-4966-a606-e69d9e8524ac",
    "art_first_line_switch_date":"ad884e26-5e18-4ec3-b471-c1cd1374387f",
    "substitution_regimen_for_adults_first_line":"42cd702e-33b2-45e8-968e-f3918ac5c86b",
    "child_regimen_first_line":"4f97efba-0b28-46ff-8f2e-97ef3eee5814",
    "reason_for_regimen_change_first_line":"f0941906-3277-4ca0-891a-a58efb541c66",
    "other_reason_for_regimen_change_first_line":"b4d8805f-40ff-4c9d-9118-feeab45ad8be",
    "art_regimen_switch-to_second-line":"ea1c7482-ad23-49b7-9eee-c7b320d7cbd1",
    "art_second_line_switch_date":"fdb79be1-587a-4034-8865-318ef8dc4b0e",
    "substitution_regimen_for_adults_second_line":"2a48b138-13ae-481b-8716-a3700626460d",
    "children_second_line_regimens":"bed19764-a7aa-42b9-8248-5ef68cc102ab",
    "reason_for_regimen_change_second_line":"847b1998-5b2f-4818-a6c0-4284e2f85403",
    "other_reason_for_regimen_change_second_line":"b4d8805f-40ff-4c9d-9118-feeab45ad8be",
    "art_treatment_interruption":"4e350390-7055-4e71-924f-a80e2de4f192",
    "interruption-type":"2b19035d-46a7-4007-b63b-87753dd5b6d3",
    "art_treatment_stop_or_lost_date":"84c23dc4-40f4-4d9a-a2f5-ebeb4b4f3250",
    "art_treatment_reasons_for_stop":"a7f113ad-f18b-41fe-a1ea-5f468c911b1d",
    "date_if_restarted":"fa954f61-ffaf-426c-a70b-3da7fddafbf8"
  }
},{
    "report_name": "HIV Care Follow Up Report",
    "flat_table_name": "ssemr_flat_encounter_hiv_care_follow_up",
    "encounter_type_uuid": "e8481555-9dd1-4bb5-ba8c-cb721dafb166",
    "concepts_locale": "en",
    "table_columns": {
        "death":"041daad2-198b-4223-aa46-6b23e85776a9",
        "date_of_death":"bced5365-80f5-4a27-a287-60296c4e4ff9",
        "lost_to_follow_up":"7ca4f879-4862-4cd5-84b3-e1ead8ff54ff",
        "lost_follow_up_last_visit_date":"773cd838-7b21-4f34-9b33-0a071140f817",
        "transferred_out":"b949cd75-97cb-4de2-9553-e6d335696f07",
        "hivtc_date_of_transfered_out":"d0fa2ac1-efaa-4050-ba8a-a0b55ccc17b9",
        "hivtc_transferred_out_to":"d930b180-7899-42b4-98bb-8f49d6b46661",
        "follow_up_scheduled":"a226366c-01ca-4c6c-8817-15ccceba3263",
        "follow_up_date":"ee31f5fc-40b8-417d-8350-1a74431c220b",
        "duration_in_months_since_first_starting":"e0e2acbe-f88b-4710-adbd-08637d80ea97",
        "weight":"5089AAAAAAAAAAAAAAAAAAAAAAAAAAAA",
        "height":"50c9bb3c-494c-4a74-8018-1430edc0dba9",
        "bmi":"c367d9ee-3f10-11e4-adec-0800271c1b75",
        "muac_pregnancy_visit":"9f97c629-b1a0-42dc-bb2f-9227aad97d96",
        "current_on_fp":"8df413d9-e018-4a38-817f-fcf718f0df2f",
        "fp_method_used_by_the_patient":"471c15a8-4bb2-43b6-9dc2-3b3db2e29b24",
        "edd":"12118225-edad-45e2-9313-a5bca2cb4995",
        "tb_status":"ce828c13-dea2-43b7-85b9-975c5613b2db",
        "side_effects":"13498474-ba95-4fec-8010-c8c5e24748ff",
        "new_io_other_problems":"be8d8351-6dad-4f91-a5a1-40c56656bbf5",
        "who_clinical_stage":"e38e6ce2-92d9-4382-927b-5555ff9fa850",
        "contrimoxazole_dapstone":"74e61e07-fd72-4d67-ab85-b87ceec3b637",
        "adherence_number_of_days":"ac775264-a811-4b3e-a64f-0c20ae8f9b0c",
        "inh":"7ddb08f2-09e8-48ef-a08f-3744467fec48",
        "pills_dispensed":"ffa2244a-8729-47fc-9185-d62a7033b511",
        "other_meds_dispensed":"2ad448e9-69b8-4103-8e86-56e2b893100c",
        "adhere_why":"69983582-9845-40e6-b33e-2d6491efa1c8",
        "regimen_dose":"29fa186d-e282-479f-b2b7-66fb1e9721e4",
        "number_of_days_dispensed":"4ab28128-7ef3-452d-86d5-73561761b08a",
        "cd4":"809dd0f5-ce54-441c-b835-a2a8b06a6140",
        "date_vl_sample_collected":"fc684917-4deb-42cf-9245-8a13c6a232bc",
        "vl_results":"8b5ef5c4-3c88-49b8-87e5-cb8d30caa77d",
        "rpr_hb_sputum_cxr_bepb":"f5b60f09-9d8a-4bda-9318-9ef097c77945",
        "rfts_lfts_and_other_lab_tests":"1efadff1-daa0-45b8-a765-77f540e9f253",
        "number_of_days_hospitalized":"73aeaec5-4169-40a9-8b59-f799b28a548c",
        "clinician":"611d6c22-7c6d-47cf-a74e-190f9e3cdd47"    
     }
  }
  ,{
    "report_name": "TB Screening Form Children Report",
    "flat_table_name": "ssemr_flat_encounter_tb_screening_form_children",
    "encounter_type_uuid": "81fbaddd-3f10-11e4-adec-0800271c1b75",
    "concepts_locale": "en",
    "table_columns": {
        "date":"e605731b-2e81-41a9-8446-2ed442c339e2",
        "current_cough":"147b8be8-d657-4e46-8970-6f9983b27722",
        "tb_screening_fever":"f405d8ec-14d0-485a-b52d-bffdfeb26581",
        "tb_screening-weight_loss":"78f5bc62-ecbe-457f-87dd-def20dbee82e",
        "close_contact_history_with_tb_patients":"51b82f82-0c27-46e3-9f33-6d65cc1f6eed",
        "geneexpert_result":"c2f01b10-1b5e-485a-a352-05788359d881",
        "bacteriology_sputum_for_afb_information":"39fffef8-0e8b-4432-9ab4-d870b32ea3c6",
        "sputum_for_afb_done":"06fbce51-d4ff-4ad3-b911-def303f3d2e2",
        "sputum_for_afb-result":"fdaa7259-df0b-4e26-9d9c-de2ca68dff1d",
        "radiology_crx_information":"363f10c8-bce7-4627-8890-62fafd8edb43",
        "crx_done":"edf1eec7-f766-4371-814b-0c18024217bb",
        "cxr_results":"7c679f4f-cae6-43ba-bf18-0f3bd9f060a8",
        "fna_culture_ultrasound_done":"ad8ecb63-6b5d-49b2-9a9d-e16d7a0efc40",
        "tb_diagnosed":"1b4f4df7-dddc-4e73-ab18-304c4fd12953",
        "hivtc_tb_type":"3109983a-98af-4017-a16b-e3bac0c48d25"
    }
},{
    "report_name": "VL Laboratory Request Report",
    "flat_table_name": "ssemr_flat_encounter_vl_laboratory_request",
    "encounter_type_uuid": "82024e00-3f10-11e4-adec-0800271c1b75",
    "concepts_locale": "en",
    "table_columns": {
        "date_of_sample_collection":"ed520e2d-acb4-4ea9-8ae5-16ca27ace96d",
        "time_sample_collected":"9fc84874-ec1c-4f15-9e0d-5a73dd416667",
        "sample_type":"7b532bef-9232-4675-a359-7b1310b66d3a",
        "date_of_treatment_initiation":"44dba186-7507-41d4-811f-79eae073cdcc",
        "date_of_initial_current_regimen":"30f1f347-d72c-4920-9962-6d55d138e8e5",
        "client_pregnant":"235a6246-6179-4309-ba84-6f0ec337eb48",
        "client_breastfeeding":"e288fc7d-bbc5-479a-b94d-857e3819f926",
        "arv_adherence":"e126652c-8d02-4c4b-a49d-5bac03eb4d03",
        "last_vl_date":"0ffdbc2e-8883-4648-905a-68ceab83bb6f",
        "value":"db923866-cfc6-47a5-8ae4-1e37d75db195",
        "last_vl_date_repeat":"acfbb9f8-8b3c-4f97-b47a-51851b063367",
        "value_repeat":"5ad34c7c-3a92-496c-b7d7-2210aca554bd",
        "last_vl_date_sus_treatment_failure":"b3b195ff-0e6c-420f-bf60-483f24902066",
        "value_sus_treatment_failure":"7906cef9-4843-40f7-8fee-0e69cb00e7b8",
        "clinician_name":"611d6c22-7c6d-47cf-a74e-190f9e3cdd47",
        "clinician_phone":"4f968437-31cf-4cd9-bccf-c594618d12f5",
        "date_requested":"b853ddc3-3d8c-4b0b-99ac-497624c9904d",
        "vl_focal_person":"db73e6cd-57e7-4cca-83b7-7d4c532c5621",
        "vl_focal_person_phone":"dff0ffef-5c14-458a-a4db-8b106daf3876",
        "email_of_hf":"dfcedbb1-11fe-4e19-969c-f88539ebc31f",
        "date_sample_received_lab":"0bfdbae3-ea5d-4173-a4e1-7f42d50e0bc6",
        "date_results_dispatched":"4db52045-d459-4c3e-b0b4-b7d00b18ed1e",
        "rejection":"5986b9bb-b9e0-4c8a-bf16-6562fc3076a6"
    }
}]}';

      CALL sp_mamba_extract_report_metadata(@report_data, 'mamba_dim_concept_metadata');

      -- $END
END~


        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_dim_concept_metadata_update  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_mamba_dim_concept_metadata_update;


~
CREATE PROCEDURE sp_mamba_dim_concept_metadata_update()
BEGIN
-- $BEGIN

-- Update the Concept datatypes, concept_name and concept_id based on given locale
UPDATE mamba_dim_concept_metadata md
    INNER JOIN mamba_dim_concept c
    ON md.concept_uuid = c.uuid
    INNER JOIN mamba_dim_concept_name cn
    ON c.concept_id = cn.concept_id
SET md.concept_datatype = c.datatype,
    md.concept_id       = c.concept_id,
    md.concept_name     = cn.name
WHERE md.id > 0
  AND cn.locale = md.concepts_locale
  AND IF(cn.locale_preferred = 1, cn.locale_preferred = 1, cn.concept_name_type = 'FULLY_SPECIFIED');

-- Use locale preferred or Fully specified name

-- Update to True if this field is an obs answer to an obs Question
UPDATE mamba_dim_concept_metadata md
    INNER JOIN mamba_dim_concept_answer ca
    ON md.concept_id = ca.answer_concept
SET md.concept_answer_obs = 1
WHERE md.id > 0 AND
        md.concept_id IN (SELECT DISTINCT ca.concept_id
                          FROM  mamba_dim_concept_answer ca);

-- Update to for multiple selects/dropdowns/options this field is an obs answer to an obs Question
UPDATE mamba_dim_concept_metadata md
SET md.concept_answer_obs = 1
WHERE md.id > 0 and concept_datatype = 'N/A';

-- Update row number
SET @row_number = 0;

UPDATE mamba_dim_concept_metadata md
    INNER JOIN (
    SELECT id,
    (@row_number:=@row_number + 1) AS num
    FROM mamba_dim_concept_metadata
    ORDER BY flat_table_name, concept_id, id ASC
    ) m ON md.id = m.id
    SET md.row_num = m.num
WHERE md.id > 0;
-- $END
END~


        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_dim_concept_metadata  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_mamba_dim_concept_metadata;


~
CREATE PROCEDURE sp_mamba_dim_concept_metadata()
BEGIN
-- $BEGIN

CALL sp_mamba_dim_concept_metadata_create();
CALL sp_mamba_dim_concept_metadata_insert();
CALL sp_mamba_dim_concept_metadata_update();

-- $END
END~


        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_dim_report_definition_create  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_mamba_dim_report_definition_create;


~
CREATE PROCEDURE sp_mamba_dim_report_definition_create()
BEGIN
-- $BEGIN

CREATE TABLE mamba_dim_report_definition
(
    id                            INT          NOT NULL AUTO_INCREMENT,
    report_id                     VARCHAR(255) NOT NULL UNIQUE,
    report_procedure_name         VARCHAR(255) NOT NULL UNIQUE, -- should be derived from report_id??
    report_columns_procedure_name VARCHAR(255) NOT NULL UNIQUE,
    sql_query                     TEXT         NOT NULL,
    table_name                    VARCHAR(255) NOT NULL,        -- name of the table (will contain columns) of this query
    report_name                   VARCHAR(255) NULL,
    result_column_names           TEXT         NULL,            -- comma-separated column names

    PRIMARY KEY (id)
)
    CHARSET = UTF8MB4;

CREATE INDEX mamba_dim_report_definition_report_id_index
    ON mamba_dim_report_definition (report_id);


CREATE TABLE mamba_dim_report_definition_parameters
(
    id                 INT          NOT NULL AUTO_INCREMENT,
    report_id          VARCHAR(255) NOT NULL,
    parameter_name     VARCHAR(255) NOT NULL,
    parameter_type     VARCHAR(30)  NOT NULL,
    parameter_position INT          NOT NULL, -- takes order or declaration in JSON file

    PRIMARY KEY (id),
    FOREIGN KEY (`report_id`) REFERENCES `mamba_dim_report_definition` (`report_id`)
)
    CHARSET = UTF8MB4;

CREATE INDEX mamba_dim_report_definition_parameter_position_index
    ON mamba_dim_report_definition_parameters (parameter_position);

-- $END
END~


        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_dim_report_definition_insert  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_mamba_dim_report_definition_insert;


~
CREATE PROCEDURE sp_mamba_dim_report_definition_insert()
BEGIN
-- $BEGIN
-- $END
END~


        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_dim_report_definition_update  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_mamba_dim_report_definition_update;


~
CREATE PROCEDURE sp_mamba_dim_report_definition_update()
BEGIN
-- $BEGIN
-- $END
END~


        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_dim_report_definition  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_mamba_dim_report_definition;


~
CREATE PROCEDURE sp_mamba_dim_report_definition()
BEGIN
-- $BEGIN

CALL sp_mamba_dim_report_definition_create();
CALL sp_mamba_dim_report_definition_insert();
CALL sp_mamba_dim_report_definition_update();

-- $END
END~


        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_dim_person_create  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_mamba_dim_person_create;


~
CREATE PROCEDURE sp_mamba_dim_person_create()
BEGIN
-- $BEGIN

CREATE TABLE mamba_dim_person
(
    id                  INT          NOT NULL AUTO_INCREMENT,
    person_id           INT          NOT NULL,
    birthdate           DATE         NULL,
    birthdate_estimated TINYINT(1)   NOT NULL,
    age                 INT          NULL,
    dead                TINYINT(1)   NOT NULL,
    death_date          DATETIME     NULL,
    deathdate_estimated TINYINT      NOT NULL,
    gender              VARCHAR(50)  NULL,
    date_created        DATETIME     NOT NULL,
    person_name_short   VARCHAR(255) NULL,
    person_name_long    TEXT         NULL,
    uuid                CHAR(38)     NOT NULL,
    voided              TINYINT(1)   NOT NULL,

    PRIMARY KEY (id)
) CHARSET = UTF8MB4;

CREATE INDEX mamba_dim_person_person_id_index
    ON mamba_dim_person (person_id);

CREATE INDEX mamba_dim_person_uuid_index
    ON mamba_dim_person (uuid);

-- $END
END~


        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_dim_person_insert  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_mamba_dim_person_insert;


~
CREATE PROCEDURE sp_mamba_dim_person_insert()
BEGIN
-- $BEGIN

INSERT INTO mamba_dim_person (person_id,
                              birthdate,
                              birthdate_estimated,
                              age,
                              dead,
                              death_date,
                              deathdate_estimated,
                              gender,
                              date_created,
                              person_name_short,
                              person_name_long,
                              uuid,
                              voided)

SELECT psn.person_id,
       psn.birthdate,
       psn.birthdate_estimated,
       fn_mamba_age_calculator(birthdate, death_date)               AS age,
       psn.dead,
       psn.death_date,
       psn.deathdate_estimated,
       psn.gender,
       psn.date_created,
       CONCAT_WS(' ', prefix, given_name, middle_name, family_name) AS person_name_short,
       CONCAT_WS(' ', prefix, given_name, middle_name, family_name_prefix, family_name, family_name2,
                 family_name_suffix, degree)
                                                                    AS person_name_long,
       psn.uuid,
       psn.voided
FROM openmrs.person psn
         INNER JOIN mamba_dim_person_name pn
                    on psn.person_id = pn.person_id
WHERE pn.preferred = 1
  AND pn.voided = 0;

-- $END
END~


        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_dim_person_update  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_mamba_dim_person_update;


~
CREATE PROCEDURE sp_mamba_dim_person_update()
BEGIN
-- $BEGIN
-- $END
END~


        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_dim_person  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_mamba_dim_person;


~
CREATE PROCEDURE sp_mamba_dim_person()
BEGIN
-- $BEGIN

CALL sp_mamba_dim_person_create();
CALL sp_mamba_dim_person_insert();
CALL sp_mamba_dim_person_update();
-- $END
END~


        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_dim_patient_identifier_create  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_mamba_dim_patient_identifier_create;


~
CREATE PROCEDURE sp_mamba_dim_patient_identifier_create()
BEGIN
-- $BEGIN

CREATE TABLE mamba_dim_patient_identifier
(
    id                    INT         NOT NULL AUTO_INCREMENT,
    patient_identifier_id INT,
    patient_id            INT         NOT NULL,
    identifier            VARCHAR(50) NOT NULL,
    identifier_type       INT         NOT NULL,
    preferred             TINYINT     NOT NULL,
    location_id           INT         NULL,
    date_created          DATETIME    NOT NULL,
    uuid                  CHAR(38)    NOT NULL,
    voided                TINYINT     NOT NULL,

    PRIMARY KEY (id)
)
    CHARSET = UTF8MB4;

CREATE INDEX mamba_dim_patient_identifier_patient_identifier_id_index
    ON mamba_dim_patient_identifier (patient_identifier_id);

CREATE INDEX mamba_dim_patient_identifier_patient_id_index
    ON mamba_dim_patient_identifier (patient_id);

CREATE INDEX mamba_dim_patient_identifier_identifier_index
    ON mamba_dim_patient_identifier (identifier);

CREATE INDEX mamba_dim_patient_identifier_identifier_type_index
    ON mamba_dim_patient_identifier (identifier_type);

CREATE INDEX mamba_dim_patient_identifier_uuid_index
    ON mamba_dim_patient_identifier (uuid);

CREATE INDEX mamba_dim_patient_identifier_preferred_index
    ON mamba_dim_patient_identifier (preferred);

-- $END
END~


        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_dim_patient_identifier_insert  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_mamba_dim_patient_identifier_insert;


~
CREATE PROCEDURE sp_mamba_dim_patient_identifier_insert()
BEGIN
-- $BEGIN

INSERT INTO mamba_dim_patient_identifier (patient_id,
                                          identifier,
                                          identifier_type,
                                          preferred,
                                          location_id,
                                          date_created,
                                          uuid,
                                          voided)
SELECT patient_id,
       identifier,
       identifier_type,
       preferred,
       location_id,
       date_created,
       uuid,
       voided
FROM openmrs.patient_identifier;

-- $END
END~


        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_dim_patient_identifier_update  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_mamba_dim_patient_identifier_update;


~
CREATE PROCEDURE sp_mamba_dim_patient_identifier_update()
BEGIN
-- $BEGIN
-- $END
END~


        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_dim_patient_identifier  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_mamba_dim_patient_identifier;


~
CREATE PROCEDURE sp_mamba_dim_patient_identifier()
BEGIN
-- $BEGIN

CALL sp_mamba_dim_patient_identifier_create();
CALL sp_mamba_dim_patient_identifier_insert();
CALL sp_mamba_dim_patient_identifier_update();

-- $END
END~


        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_dim_person_name_create  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_mamba_dim_person_name_create;


~
CREATE PROCEDURE sp_mamba_dim_person_name_create()
BEGIN
-- $BEGIN

CREATE TABLE mamba_dim_person_name
(
    id                 INT         NOT NULL AUTO_INCREMENT,
    person_name_id     INT         NOT NULL,
    person_id          INT         NOT NULL,
    preferred          TINYINT     NOT NULL,
    prefix             VARCHAR(50) NULL,
    given_name         VARCHAR(50) NULL,
    middle_name        VARCHAR(50) NULL,
    family_name_prefix VARCHAR(50) NULL,
    family_name        VARCHAR(50) NULL,
    family_name2       VARCHAR(50) NULL,
    family_name_suffix VARCHAR(50) NULL,
    degree             VARCHAR(50) NULL,
    voided             TINYINT(1)  NOT NULL,

    PRIMARY KEY (id)
)
    CHARSET = UTF8MB4;

CREATE INDEX mamba_dim_person_name_person_name_id_index
    ON mamba_dim_person_name (person_name_id);

CREATE INDEX mamba_dim_person_name_person_id_index
    ON mamba_dim_person_name (person_id);

CREATE INDEX mamba_dim_person_name_voided_index
    ON mamba_dim_person_name (voided);

CREATE INDEX mamba_dim_person_name_preferred_index
    ON mamba_dim_person_name (preferred);

-- $END
END~


        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_dim_person_name_insert  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_mamba_dim_person_name_insert;


~
CREATE PROCEDURE sp_mamba_dim_person_name_insert()
BEGIN
-- $BEGIN
INSERT INTO mamba_dim_person_name(person_name_id,
                                  person_id,
                                  preferred,
                                  prefix,
                                  given_name,
                                  middle_name,
                                  family_name_prefix,
                                  family_name,
                                  family_name2,
                                  family_name_suffix,
                                  degree,
                                  voided)
SELECT pn.person_name_id,
       pn.person_id,
       pn.preferred,
       pn.prefix,
       pn.given_name,
       pn.middle_name,
       pn.family_name_prefix,
       pn.family_name,
       pn.family_name2,
       pn.family_name_suffix,
       pn.degree,
       pn.voided
FROM openmrs.person_name pn;
-- $END
END~


        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_dim_person_name  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_mamba_dim_person_name;


~
CREATE PROCEDURE sp_mamba_dim_person_name()
BEGIN
-- $BEGIN

CALL sp_mamba_dim_person_name_create();
CALL sp_mamba_dim_person_name_insert();

-- $END
END~


        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_dim_person_address_create  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_mamba_dim_person_address_create;


~
CREATE PROCEDURE sp_mamba_dim_person_address_create()
BEGIN
-- $BEGIN

CREATE TABLE mamba_dim_person_address
(
    id                INT          NOT NULL AUTO_INCREMENT,
    person_address_id INT          NOT NULL,
    person_id         INT          NULL,
    preferred         TINYINT      NOT NULL,
    address1          VARCHAR(255) NULL,
    address2          VARCHAR(255) NULL,
    address3          VARCHAR(255) NULL,
    address4          VARCHAR(255) NULL,
    address5          VARCHAR(255) NULL,
    address6          VARCHAR(255) NULL,
    city_village      VARCHAR(255) NULL,
    county_district   VARCHAR(255) NULL,
    state_province    VARCHAR(255) NULL,
    postal_code       VARCHAR(50)  NULL,
    country           VARCHAR(50)  NULL,
    latitude          VARCHAR(50)  NULL,
    longitude         VARCHAR(50)  NULL,

    PRIMARY KEY (id)
)
    CHARSET = UTF8MB4;

CREATE INDEX mamba_dim_person_address_person_address_id_index
    ON mamba_dim_person_address (person_address_id);

CREATE INDEX mamba_dim_person_address_person_id_index
    ON mamba_dim_person_address (person_id);

CREATE INDEX mamba_dim_person_address_preferred_index
    ON mamba_dim_person_address (preferred);

-- $END
END~


        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_dim_person_address_insert  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_mamba_dim_person_address_insert;


~
CREATE PROCEDURE sp_mamba_dim_person_address_insert()
BEGIN
-- $BEGIN

INSERT INTO mamba_dim_person_address (person_address_id,
                                      person_id,
                                      preferred,
                                      address1,
                                      address2,
                                      address3,
                                      address4,
                                      address5,
                                      address6,
                                      city_village,
                                      county_district,
                                      state_province,
                                      postal_code,
                                      country,
                                      latitude,
                                      longitude)
SELECT person_address_id,
       person_id,
       preferred,
       address1,
       address2,
       address3,
       address4,
       address5,
       address6,
       city_village,
       county_district,
       state_province,
       postal_code,
       country,
       latitude,
       longitude
FROM openmrs.person_address;

-- $END
END~


        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_dim_person_address  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_mamba_dim_person_address;


~
CREATE PROCEDURE sp_mamba_dim_person_address()
BEGIN
-- $BEGIN

CALL sp_mamba_dim_person_address_create();
CALL sp_mamba_dim_person_address_insert();

-- $END
END~


        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_dim_user_create  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_mamba_dim_user_create;


~
CREATE PROCEDURE sp_mamba_dim_user_create()
BEGIN
-- $BEGIN
    CREATE TABLE mamba_dim_users
    (
        id            INT          NOT NULL AUTO_INCREMENT,
        user_id       INT          NOT NULL,
        system_id     VARCHAR(50)  NOT NULL,
        username      VARCHAR(50)  NULL,
        creator       INT          NOT NULL,
        date_created  DATETIME     NOT NULL,
        changed_by    INT          NULL,
        date_changed  DATETIME     NULL,
        person_id     INT          NOT NULL,
        retired       TINYINT(1)   NOT NULL,
        retired_by    INT          NULL,
        date_retired  DATETIME     NULL,
        retire_reason VARCHAR(255) NULL,
        uuid          CHAR(38)     NOT NULL,
        email         VARCHAR(255) NULL,

        PRIMARY KEY (id)
    )
        CHARSET = UTF8MB4;

    CREATE INDEX mamba_dim_users_user_id_index
        ON mamba_dim_users (user_id);

-- $END
END~


        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_dim_user_insert  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_mamba_dim_user_insert;


~
CREATE PROCEDURE sp_mamba_dim_user_insert()
BEGIN
-- $BEGIN
    INSERT INTO mamba_dim_users
        (
            user_id,
            system_id,
            username,
            creator,
            date_created,
            changed_by,
            date_changed,
            person_id,
            retired,
            retired_by,
            date_retired,
            retire_reason,
            uuid,
            email
        )
        SELECT
            user_id,
            system_id,
            username,
            creator,
            date_created,
            changed_by,
            date_changed,
            person_id,
            retired,
            retired_by,
            date_retired,
            retire_reason,
            uuid,
            email
        FROM openmrs.users c;
-- $END
END~


        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_dim_user_update  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_mamba_dim_user_update;


~
CREATE PROCEDURE sp_mamba_dim_user_update()
BEGIN
-- $BEGIN

-- $END
END~


        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_dim_user  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_mamba_dim_user;


~
CREATE PROCEDURE sp_mamba_dim_user()
BEGIN
-- $BEGIN
    CALL sp_mamba_dim_user_create();
    CALL sp_mamba_dim_user_insert();
    CALL sp_mamba_dim_user_update();
-- $END
END~


        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_dim_relationship_create  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_mamba_dim_relationship_create;


~
CREATE PROCEDURE sp_mamba_dim_relationship_create()
BEGIN
-- $BEGIN
CREATE TABLE mamba_dim_relationship
(

    relationship_id INT          NOT NULL AUTO_INCREMENT,
    person_a        INT          NOT NULL,
    relationship    INT          NOT NULL,
    person_b        INT          NOT NULL,
    start_date      DATETIME     NULL,
    end_date        DATETIME     NULL,
    creator         INT          NOT NULL,
    date_created    DATETIME     NOT NULL,
    date_changed    DATETIME     NULL,
    changed_by      INT          NULL,
    voided          TINYINT(1)   NOT NULL,
    voided_by       INT          NULL,
    date_voided     DATETIME     NULL,
    void_reason     VARCHAR(255) NULL,
    uuid            CHAR(38)     NOT NULL,

    PRIMARY KEY (relationship_id)

) CHARSET = UTF8MB3;

CREATE INDEX mamba_dim_relationship_person_a_index
    ON mamba_dim_relationship (person_a);

CREATE INDEX mamba_dim_relationship_person_b_index
    ON mamba_dim_relationship (person_b);

-- $END
END~


        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_dim_relationship_insert  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_mamba_dim_relationship_insert;


~
CREATE PROCEDURE sp_mamba_dim_relationship_insert()
BEGIN
-- $BEGIN

INSERT INTO mamba_dim_relationship
    (
        relationship_id,
        person_a,
        relationship,
        person_b,
        start_date,
        end_date,
        creator,
        date_created,
        date_changed,
        changed_by,
        voided,
        voided_by,
        date_voided,
        void_reason,
        uuid
    )
SELECT
    relationship_id,
    person_a,
    relationship,
    person_b,
    start_date,
    end_date,
    creator,
    date_created,
    date_changed,
    changed_by,
    voided,
    voided_by,
    date_voided,
    void_reason,
    uuid
FROM openmrs.relationship;

-- $END
END~


        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_dim_relationship_update  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_mamba_dim_relationship_update;


~
CREATE PROCEDURE sp_mamba_dim_relationship_update()
BEGIN
-- $BEGIN

-- $END
END~


        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_dim_relationship  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_mamba_dim_relationship;


~
CREATE PROCEDURE sp_mamba_dim_relationship()
BEGIN
-- $BEGIN

CALL sp_mamba_dim_relationship_create();
CALL sp_mamba_dim_relationship_insert();
CALL sp_mamba_dim_relationship_update();

-- $END
END~


        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_dim_orders_create  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_mamba_dim_orders_create;


~
CREATE PROCEDURE sp_mamba_dim_orders_create()
BEGIN
-- $BEGIN

CREATE TABLE mamba_dim_orders
(
    id                     INT           NOT NULL AUTO_INCREMENT,
    order_id               INT           NOT NULL,
    uuid                   CHAR(38)      NOT NULL,
    order_type_id          INT           NOT NULL,
    concept_id             INT           NOT NULL,
    patient_id             INT           NOT NULL,
    encounter_id           INT           NOT NULL, -- links with encounter table
    accession_number       VARCHAR(255)  NULL,
    order_number           VARCHAR(50)   NOT NULL,
    orderer                INT           NOT NULL,
    instructions           TEXT          NULL,
    date_activated         DATETIME      NULL,
    auto_expire_date       DATETIME      NULL,
    date_stopped           DATETIME      NULL,
    order_reason           INT           NULL,
    creator                INT           NOT NULL,
    date_created           DATETIME      NOT NULL,
    voided                 TINYINT(1)    NOT NULL,
    voided_by              INT           NULL,
    date_voided            DATETIME      NULL,
    void_reason            VARCHAR(255)  NULL,
    order_reason_non_coded VARCHAR(255)  NULL,
    urgency                VARCHAR(50)   NOT NULL,
    previous_order_id      INT           NULL,
    order_action           VARCHAR(50)   NOT NULL,
    comment_to_fulfiller   VARCHAR(1024) NULL,
    care_setting           INT           NOT NULL,
    scheduled_date         DATETIME      NULL,
    order_group_id         INT           NULL,
    sort_weight            DOUBLE        NULL,
    fulfiller_comment      VARCHAR(1024) NULL,
    fulfiller_status       VARCHAR(50)   NULL,

    PRIMARY KEY (id)
)
    CHARSET = UTF8MB4;

CREATE INDEX mamba_dim_orders_order_id_index
    ON mamba_dim_orders (order_id);

CREATE INDEX mamba_dim_orders_uuid_index
    ON mamba_dim_orders (uuid);

CREATE INDEX mamba_dim_orders_order_type_id_index
    ON mamba_dim_orders (order_type_id);

CREATE INDEX mamba_dim_orders_concept_id_index
    ON mamba_dim_orders (concept_id);

CREATE INDEX mamba_dim_orders_patient_id_index
    ON mamba_dim_orders (patient_id);

CREATE INDEX mamba_dim_orders_encounter_id_index
    ON mamba_dim_orders (encounter_id);

-- $END
END~


        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_dim_orders_insert  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_mamba_dim_orders_insert;


~
CREATE PROCEDURE sp_mamba_dim_orders_insert()
BEGIN
-- $BEGIN

INSERT INTO mamba_dim_orders (order_id, uuid, order_type_id, concept_id, patient_id, encounter_id, accession_number,
                              order_number, orderer, instructions, date_activated, auto_expire_date, date_stopped,
                              order_reason, creator, date_created, voided, voided_by, date_voided, void_reason,
                              order_reason_non_coded, urgency, previous_order_id, order_action, comment_to_fulfiller,
                              care_setting, scheduled_date, order_group_id, sort_weight, fulfiller_comment,
                              fulfiller_status)
SELECT order_id,
       uuid,
       order_type_id,
       concept_id,
       patient_id,
       encounter_id,
       accession_number,
       order_number,
       orderer,
       instructions,
       date_activated,
       auto_expire_date,
       date_stopped,
       order_reason,
       creator,
       date_created,
       voided,
       voided_by,
       date_voided,
       void_reason,
       order_reason_non_coded,
       urgency,
       previous_order_id,
       order_action,
       comment_to_fulfiller,
       care_setting,
       scheduled_date,
       order_group_id,
       sort_weight,
       fulfiller_comment,
       fulfiller_status
FROM openmrs.orders;

-- $END
END~


        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_dim_orders_update  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_mamba_dim_orders_update;


~
CREATE PROCEDURE sp_mamba_dim_orders_update()
BEGIN
-- $BEGIN
-- $END
END~


        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_dim_orders  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_mamba_dim_orders;


~
CREATE PROCEDURE sp_mamba_dim_orders()
BEGIN
-- $BEGIN

CALL sp_mamba_dim_orders_create();
CALL sp_mamba_dim_orders_insert();
CALL sp_mamba_dim_orders_update();

-- $END
END~


        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_dim_agegroup_create  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_mamba_dim_agegroup_create;


~
CREATE PROCEDURE sp_mamba_dim_agegroup_create()
BEGIN
-- $BEGIN

CREATE TABLE mamba_dim_agegroup
(
    id              INT         NOT NULL AUTO_INCREMENT,
    age             INT         NULL,
    datim_agegroup  VARCHAR(50) NULL,
    datim_age_val   INT         NULL,
    normal_agegroup VARCHAR(50) NULL,
    normal_age_val   INT        NULL,

    PRIMARY KEY (id)
)
    CHARSET = UTF8MB4;

-- $END
END~


        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_dim_agegroup_insert  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_mamba_dim_agegroup_insert;


~
CREATE PROCEDURE sp_mamba_dim_agegroup_insert()
BEGIN
-- $BEGIN

-- Enter unknown dimension value (in case a person's date of birth is unknown)
CALL sp_mamba_load_agegroup();
-- $END
END~


        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_dim_agegroup_update  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_mamba_dim_agegroup_update;


~
CREATE PROCEDURE sp_mamba_dim_agegroup_update()
BEGIN
-- $BEGIN

-- update age_value b
UPDATE mamba_dim_agegroup a
SET datim_age_val =
    CASE
        WHEN a.datim_agegroup = '<1' THEN 1
        WHEN a.datim_agegroup = '1-4' THEN 2
        WHEN a.datim_agegroup = '5-9' THEN 3
        WHEN a.datim_agegroup = '10-14' THEN 4
        WHEN a.datim_agegroup = '15-19' THEN 5
        WHEN a.datim_agegroup = '20-24' THEN 6
        WHEN a.datim_agegroup = '25-29' THEN 7
        WHEN a.datim_agegroup = '30-34' THEN 8
        WHEN a.datim_agegroup = '35-39' THEN 9
        WHEN a.datim_agegroup = '40-44' THEN 10
        WHEN a.datim_agegroup = '45-49' THEN 11
        WHEN a.datim_agegroup = '50-54' THEN 12
        WHEN a.datim_agegroup = '55-59' THEN 13
        WHEN a.datim_agegroup = '60-64' THEN 14
        WHEN a.datim_agegroup = '65+' THEN 15
    END
WHERE a.datim_agegroup IS NOT NULL;

-- $END
END~


        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_dim_agegroup  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_mamba_dim_agegroup;


~
CREATE PROCEDURE sp_mamba_dim_agegroup()
BEGIN
-- $BEGIN

CALL sp_mamba_dim_agegroup_create();
CALL sp_mamba_dim_agegroup_insert();
CALL sp_mamba_dim_agegroup_update();
END~


        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_z_encounter_obs_create  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_mamba_z_encounter_obs_create;


~
CREATE PROCEDURE sp_mamba_z_encounter_obs_create()
BEGIN
-- $BEGIN

CREATE TABLE mamba_z_encounter_obs
(
    id                      INT           NOT NULL AUTO_INCREMENT,
    encounter_id            INT           NULL,
    person_id               INT           NOT NULL,
    encounter_datetime      DATETIME      NOT NULL,
    obs_datetime            DATETIME      NOT NULL,
    location_id             INT           NULL,
    obs_group_id            INT           NULL,
    obs_question_concept_id INT DEFAULT 0 NOT NULL,
    obs_value_text          TEXT          NULL,
    obs_value_numeric       DOUBLE        NULL,
    obs_value_coded         INT           NULL,
    obs_value_datetime      DATETIME      NULL,
    obs_value_complex       VARCHAR(1000) NULL,
    obs_value_drug          INT           NULL,
    obs_question_uuid       CHAR(38),
    obs_answer_uuid         CHAR(38),
    obs_value_coded_uuid    CHAR(38),
    encounter_type_uuid     CHAR(38),
    status                  VARCHAR(16)   NOT NULL,
    voided                  TINYINT       NOT NULL,
    row_num                 INT           NULL,

    PRIMARY KEY (id)
)
    CHARSET = UTF8MB4;

CREATE INDEX mamba_z_encounter_obs_encounter_id_type_uuid_person_id_index
    ON mamba_z_encounter_obs (encounter_id, person_id, encounter_datetime);

CREATE INDEX mamba_z_encounter_obs_encounter_id_index
    ON mamba_z_encounter_obs (encounter_id);

CREATE INDEX mamba_z_encounter_obs_encounter_type_uuid_index
    ON mamba_z_encounter_obs (encounter_type_uuid);

CREATE INDEX mamba_z_encounter_obs_question_concept_id_index
    ON mamba_z_encounter_obs (obs_question_concept_id);

CREATE INDEX mamba_z_encounter_obs_value_coded_index
    ON mamba_z_encounter_obs (obs_value_coded);

CREATE INDEX mamba_z_encounter_obs_value_coded_uuid_index
    ON mamba_z_encounter_obs (obs_value_coded_uuid);

CREATE INDEX mamba_z_encounter_obs_question_uuid_index
    ON mamba_z_encounter_obs (obs_question_uuid);

CREATE INDEX mamba_z_encounter_obs_status_index
    ON mamba_z_encounter_obs (status);

CREATE INDEX mamba_z_encounter_obs_voided_index
    ON mamba_z_encounter_obs (voided);

CREATE INDEX mamba_z_encounter_obs_row_num_index
    ON mamba_z_encounter_obs (row_num);

CREATE INDEX mamba_z_encounter_obs_encounter_datetime_index
    ON mamba_z_encounter_obs (encounter_datetime);

CREATE INDEX mamba_z_encounter_obs_person_id_index
    ON mamba_z_encounter_obs (person_id);

-- $END
END~


        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_z_encounter_obs_insert  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_mamba_z_encounter_obs_insert;


~
CREATE PROCEDURE sp_mamba_z_encounter_obs_insert()
BEGIN
-- $BEGIN

SET @row_number = 0;

INSERT INTO mamba_z_encounter_obs
(encounter_id,
 person_id,
 obs_datetime,
 encounter_datetime,
 encounter_type_uuid,
 obs_question_concept_id,
 obs_value_text,
 obs_value_numeric,
 obs_value_coded,
 obs_value_datetime,
 obs_value_complex,
 obs_value_drug,
 obs_question_uuid,
 obs_answer_uuid,
 obs_value_coded_uuid,
 status,
 voided,
 row_num)
SELECT o.encounter_id,
       o.person_id,
       o.obs_datetime,
       e.encounter_datetime,
       e.encounter_type_uuid,
       o.concept_id     AS obs_question_concept_id,
       o.value_text     AS obs_value_text,
       o.value_numeric  AS obs_value_numeric,
       o.value_coded    AS obs_value_coded,
       o.value_datetime AS obs_value_datetime,
       o.value_complex  AS obs_value_complex,
       o.value_drug     AS obs_value_drug,
       NULL             AS obs_question_uuid,
       NULL             AS obs_answer_uuid,
       NULL             AS obs_value_coded_uuid,
       o.status,
       o.voided,
       (@row_number:=@row_number + 1) AS row_num
FROM obs o
         INNER JOIN mamba_dim_encounter e
                    ON o.encounter_id = e.encounter_id
WHERE o.encounter_id IS NOT NULL
ORDER BY person_id, encounter_id, concept_id;

-- $END
END~


        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_z_encounter_obs_update  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_mamba_z_encounter_obs_update;


~
CREATE PROCEDURE sp_mamba_z_encounter_obs_update()
BEGIN
-- $BEGIN

-- update obs question UUIDs
UPDATE mamba_z_encounter_obs z
    INNER JOIN mamba_dim_concept_metadata md
    ON z.obs_question_concept_id = md.concept_id
SET z.obs_question_uuid = md.concept_uuid
WHERE TRUE;

-- update obs_value_coded (UUIDs & Concept value names)
UPDATE mamba_z_encounter_obs z
    INNER JOIN mamba_dim_concept_name cn
    ON z.obs_value_coded = cn.concept_id
    INNER JOIN mamba_dim_concept c
    ON c.concept_id = cn.concept_id
SET z.obs_value_text       = cn.name,
    z.obs_value_coded_uuid = c.uuid
WHERE z.obs_value_coded IS NOT NULL;


-- $END
END~


        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_z_encounter_obs  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_mamba_z_encounter_obs;


~
CREATE PROCEDURE sp_mamba_z_encounter_obs()
BEGIN
-- $BEGIN

CALL sp_mamba_z_encounter_obs_create();
CALL sp_mamba_z_encounter_obs_insert();
CALL sp_mamba_z_encounter_obs_update();

-- $END
END~


        
-- ---------------------------------------------------------------------------------------------
-- ----------------------  sp_mamba_data_processing_flatten  ----------------------------
-- ---------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sp_mamba_data_processing_flatten;


~
CREATE PROCEDURE sp_mamba_data_processing_flatten()
BEGIN
-- $BEGIN
-- CALL sp_xf_system_drop_all_tables_in_schema($target_database);
CALL sp_xf_system_drop_all_tables_in_schema();

CALL sp_mamba_dim_location;

CALL sp_mamba_dim_patient_identifier_type;

CALL sp_mamba_dim_concept_datatype;

CALL sp_mamba_dim_concept_answer;

CALL sp_mamba_dim_concept_name;

CALL sp_mamba_dim_concept;

CALL sp_mamba_dim_concept_metadata;

CALL sp_mamba_dim_report_definition;

CALL sp_mamba_dim_encounter_type;

CALL sp_mamba_dim_encounter;

CALL sp_mamba_dim_person_name;

CALL sp_mamba_dim_person;

CALL sp_mamba_dim_person_address;

CALL sp_mamba_dim_user;

CALL sp_mamba_dim_relationship;

CALL sp_mamba_dim_patient_identifier;

CALL sp_mamba_dim_orders;

CALL sp_mamba_dim_agegroup;

CALL sp_mamba_z_encounter_obs;

CALL sp_mamba_flat_encounter_table_create_all;

CALL sp_mamba_flat_encounter_table_insert_all;

-- CALL sp_mamba_flat_encounter_obs_group_table_create_all;

-- CALL sp_mamba_flat_encounter_obs_group_table_insert_all;
-- $END
END~


USE openmrs;
