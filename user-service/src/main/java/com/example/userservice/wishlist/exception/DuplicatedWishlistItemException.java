package com.example.userservice.wishlist.exception;

import com.example.servicecommon.exception.core.CustomException;
import com.example.servicecommon.exception.core.ErrorCode;

public class DuplicatedWishlistItemException extends CustomException {
    public DuplicatedWishlistItemException() {
        super(ErrorCode.DUPLICATE_WISHLIST_ITEM);
    }
}
