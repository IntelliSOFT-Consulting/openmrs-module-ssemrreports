-- DROP Table if exists --

DROP TABLE IF EXISTS flat_encounter_tb_screening;


-- Create table tb_screening --

CREATE TABLE flat_encounter_tb_screening
(
    patient_id                               INT(15),
    visit_date                               DATE,
    current_cough                            VARCHAR(255),
    tb_screening_fever                       VARCHAR(255),
    tb_screening_weight_loss                 VARCHAR(255),
    close_contact_history_with_tb_patients   VARCHAR(255),
    geneexpert_result                        VARCHAR(255),
    bacteriology_sputum_for_afb_information  VARCHAR(255),
    sputum_for_afb_done                      VARCHAR(255),
    sputum_for_afb_result                    VARCHAR(255),
    radiology_crx_information                VARCHAR(255),
    crx_done                                 VARCHAR(255),
    cxr_results                              VARCHAR(255),
    fna_culture_ultrasound_done              VARCHAR(255),
    tb_diagnosed                             VARCHAR(255),
    hivtc_tb_type                            VARCHAR(255),
    location_id                              INT(15)
);
    
    SELECT "Successfully created flat_encounter_tb_screening table";