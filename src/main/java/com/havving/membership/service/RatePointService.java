package com.havving.membership.service;

import com.havving.membership.impl.PointService;
import org.springframework.stereotype.Service;

/**
 * @author HAVVING
 * @since 2021-09-22
 */
@Service
public class RatePointService implements PointService {

    private static final int POINT_RATE = 1;

    @Override
    public int calculateAmount(final int price) {
        return price * POINT_RATE / 100;
    }
}
