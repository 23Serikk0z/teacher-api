CREATE TABLE if not exists slots
(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    teacher_id      UUID NOT NULL,
    total_slots     BIGINT,
    available_slots BIGINT
);

ALTER TABLE slots
    ADD CONSTRAINT uc_slots_teacherid UNIQUE (teacher_id);