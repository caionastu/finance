package com.caionastu.finance.resources

import java.math.BigDecimal

fun BigDecimal.isPositive(): Boolean = this > BigDecimal.ZERO
