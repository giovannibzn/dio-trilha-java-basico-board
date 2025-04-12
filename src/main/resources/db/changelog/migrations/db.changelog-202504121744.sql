--liquibase formatted sql
--changeset giovanni:202504121744
--comment: set unblock_reason nullable

ALTER TABLE blocks MODIFY COLUMN unblock_reason VARCHAR(255) NULL;

--rollback ALTER TABLE blocks MODIFY COLUMN unblock_reason VARCHAR(255) NULL;