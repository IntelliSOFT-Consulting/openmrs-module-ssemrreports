-- DROP Table if exists --
DROP TABLE IF EXISTS flat_encounter_high_viral_load;


-- Create table high_viral_load --

CREATE TABLE flat_encounter_high_viral_load
(
    arv_regimen                               VARCHAR(255),
    date_of_initiation_of_current_regimen     DATE,
    previous_vl                               INT(15),
    recent_vl                                 INT(15),
    date_of_sample_collection                 DATE,
    current_who_t_staging                     VARCHAR(255),
    is_patient_currently_a_presumptive_tb     VARCHAR(255),
    history_of_chronic_diarrhea_or_vomiting   VARCHAR(255),
    other_oi_or_signs_of_immunosuppression    VARCHAR(255),
    history_of_side_effects_with_arvs         VARCHAR(255),
    patient_adherence_before_eac              VARCHAR(255),
    treatment_supporter_present               VARCHAR(255),
    adherence_date                            DATE,
    adherence                                 VARCHAR(255),
    adherence_barriers                        VARCHAR(255),
    interventions                             VARCHAR(255),
    first_eac_tools                           VARCHAR(255),
    pill_intake                               INT(15),
    behavioural                               VARCHAR(255),
    cognitive                                 VARCHAR(255),
    socio_economic                            VARCHAR(255),
    emotional                                 VARCHAR(255),
    identified_barriers_of_adherence_attended VARCHAR(255),
    comments_                                 VARCHAR(255),
    date_of_extra_session                     DATE,
    pill_intake_                              INT(15),
    agreed_plan_of_action                     VARCHAR(255),
    date_of_collection_of_repeat_vl           DATE,
    counsellor                                VARCHAR(255),
    date_of_assesment                         DATE,
    repeat_vl_result                          VARCHAR(255),
    repeat_vl_result_1000cml                  VARCHAR(255),
    repeat_vl_date                            DATE,
    plan_for_the_patient                      VARCHAR(255),
    new_regimen                               VARCHAR(255),
    date_                                     DATE,
    _comments_                                 VARCHAR(255),
    art_provider_name                         VARCHAR(255),
    art_provider_signature                    VARCHAR(255),
    _date_                                     DATE
);

    SELECT "Successfully created flat_encounter_high_viral_load table";