package com.havving.membership.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author HAVVING
 * @since 2021-09-22
 */
@ExtendWith(MockitoExtension.class)
public class RatePointServiceTest {

    @InjectMocks
    private RatePointService ratePointService;

    @Test
    public void _10000earned100() {
        // given
        final int price = 10000;

        // when
        final int result = ratePointService.calculateAmount(price);

        // then
        assertThat(result).isEqualTo(100);
    }

    @Test
    public void _20000earned200() {
        // given
        final int price = 20000;

        // when
        final int result = ratePointService.calculateAmount(price);

        // then
        assertThat(result).isEqualTo(200);
    }

    @Test
    public void _30000earned300() {
        // given
        final int price = 30000;

        // when
        final int result = ratePointService.calculateAmount(price);

        // then
        assertThat(result).isEqualTo(300);
    }

}
