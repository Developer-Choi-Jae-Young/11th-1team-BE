-- block
CREATE INDEX idx_block_blocker_blocked_deleted
    ON block (blocker_id, blocked_id, is_deleted);

-- roommate board
CREATE INDEX idx_roommate_board_deleted_created_id
    ON roommate_board (is_deleted, created_at, id);
