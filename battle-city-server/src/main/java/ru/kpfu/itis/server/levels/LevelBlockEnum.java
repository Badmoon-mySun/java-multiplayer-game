package ru.kpfu.itis.server.levels;

import ru.kpfu.itis.others.EntityType;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */

public enum LevelBlockEnum {
    BRICK_BLOCK('1', EntityType.BRICK_BLOCK, -1),
    RIGHT_BRICK_BLOCK('2', EntityType.BRICK_BLOCK, 13107),
    BOTTOM_BRICK_BLOCK('3', EntityType.BRICK_BLOCK, 255),
    LEFT_BRICK_BLOCK('4', EntityType.BRICK_BLOCK, 52428),
    TOP_BRICK_BLOCK('5', EntityType.BRICK_BLOCK, 65280),
    LASTING_BLOCK('6', EntityType.LASTING_BLOCK, -1),
    RIGHT_LASTING_BLOCK('7', EntityType.LASTING_BLOCK, 13107),
    BOTTOM_LASTING_BLOCK('8', EntityType.LASTING_BLOCK, 255),
    LEFT_LASTING_BLOCK('9', EntityType.LASTING_BLOCK, 52428),
    TOP_LASTING_BLOCK('A', EntityType.LASTING_BLOCK, 65280);

    private char sign;
    private byte entityType;
    private int status;

    LevelBlockEnum(char sign, byte entityType, int status) {
        this.sign = sign;
        this.entityType = entityType;
        this.status = status;
    }

    public char getSign() {
        return sign;
    }

    public byte getEntityType() {
        return entityType;
    }

    public short getStatus() {
        return (short) status;
    }
}
