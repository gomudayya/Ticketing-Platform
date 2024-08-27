package com.example.showservice.venue.exception;

import com.example.servicecommon.exception.core.CustomException;
import com.example.servicecommon.exception.core.ErrorCode;

public class SeatSectionUnmatchedException extends CustomException {
    public SeatSectionUnmatchedException() {
        super(ErrorCode.SEAT_SECTION_UNMATCHED);
    }
}
