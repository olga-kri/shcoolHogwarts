-- liquibase formatted sql

-- changeset okrivenko:1
    CREATE INDEX student_name_idx ON Student (name);

-- changeset okrivenko:2
    CREATE INDEX  faculty_name_color_idx ON Faculty (name,color);