-- DROP Table if exists --
DROP TABLE IF EXISTS flat_encounter_community_linkage;


-- Create table high_viral_load --

CREATE TABLE flat_encounter_community_linkage
(
    s_number                           INT(15),
    date_of_entry                      DATE,
    uan                                VARCHAR(255),
    category_of_client                 VARCHAR(255),
    name_of_cov_assigned               VARCHAR(255),
    client_met_cov_in_the_community    VARCHAR(255),
    date_client_met_cov                DATE,
    cov_knows_client_address           VARCHAR(255),
    comments_                          VARCHAR(255)
);

    SELECT "Successfully created flat_encounter_community_linkage table";