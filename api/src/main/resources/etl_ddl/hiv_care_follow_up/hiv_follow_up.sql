-- DROP the table if exists --
DROP TABLE IF EXISTS flat_encounter_hiv_care_follow_up;


-- Create table hiv_care_follow_up --

CREATE TABLE flat_encounter_hiv_care_follow_up
(
    date_of_death                                DATE,
    lost_to_follow_up                            VARCHAR(255),
    lost_follow_up_last_visit_date               DATE,
    transferred_out                              VARCHAR(255),
    hivtc_date_of_transfered_out                 DATE,
    hivtc_transferred_out_to                     VARCHAR(255),
    follow_up_scheduled                          VARCHAR(255),
    follow_up_date                               DATE,
    duration_in_months_since_first_starting      INT(15),
    weight_kg                                    INT(15),
    height_cm                                    INT(15),
    bmi                                          INT(15),
    muac_pregnancy_visit                         INT(15),
    current_on_fp                                VARCHAR(255),
    fp_method_used_by_the_patient                VARCHAR(255),
    edd                                          DATE,
    tb_status                                    DATE,
    side_effects                                 VARCHAR(255),
    new_io_other_problems                        VARCHAR(255),
    who_clinical_stage                           VARCHAR(255),
    contrimoxazole_dapstone                      VARCHAR(255),
    adherence_number_of_days                     INT(15),
    inh                                          VARCHAR(255),
    pills_dispensed                              VARCHAR(255),
    other_meds_dispensed                         VARCHAR(255),
    adhere_why                                   VARCHAR(255),
    regimen_dose                                 VARCHAR(255),
    number_of_days_dispensed                     INT(15),
    cd4                                          INT(15),
    date_vl_sample_collected                     DATE,
    vl_results                                   INT(15),
    rpr_hb_sputum_cxr_bepb                       VARCHAR(255),
    rfts_lfts_and_other_lab_tests                VARCHAR(255),
    number_of_days_hospitalized                  INT(15),
    clinician                                    VARCHAR(255)
);
    
    SELECT "Successfully created flat_encounter_hiv_care_follow_up table";