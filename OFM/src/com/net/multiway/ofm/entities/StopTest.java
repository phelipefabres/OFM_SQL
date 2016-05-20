package com.net.multiway.ofm.entities;

public enum StopTest {

    CANCEL_TEST, TERMINATE_TEST;

    public int getControlMode() {
        switch (this) {
            case CANCEL_TEST:
                return 0x00000001;
            case TERMINATE_TEST:
                return 0x00000002;
            default:
                throw new AssertionError(this.name());

        }
    }
}
