package com.caionastu.finance.domain.repository.predicate

import com.caionastu.finance.application.web.model.request.TransactionFilterRequest
import com.caionastu.finance.domain.entity.PaymentType
import com.caionastu.finance.domain.entity.QTransactionDocument
import com.caionastu.finance.domain.entity.TransactionDocument
import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.Predicate
import com.querydsl.core.types.dsl.PathBuilder
import java.time.LocalDate

class TransactionPredicate {

    companion object {
        private val transaction: QTransactionDocument = QTransactionDocument.transactionDocument
        private val query = PathBuilder(TransactionDocument::class.java, "entity")

        fun from(filterRequest: TransactionFilterRequest): Predicate =
            BooleanBuilder()
                .and(equalCategory(filterRequest.category))
                .and(equalType(filterRequest.type))
                .and(dateBetween(filterRequest.startDate, filterRequest.endDate))
                .and(equalReferenceId(filterRequest.referenceId))

        private fun equalCategory(category: String?): Predicate? =
            category?.let {
                query.get(transaction.category).containsIgnoreCase(category)
            }

        private fun equalType(type: PaymentType?): Predicate? =
            type?.let {
                query.get(transaction.type).eq(it)
            }

        private fun dateBetween(from: LocalDate?, to: LocalDate?): Predicate? {
            val datePath = query.get(transaction.date)

            if (from == null && to == null) {
                return datePath.loe(LocalDate.now())
            }

            val builder = BooleanBuilder()
            if (from != null) {
                builder.and(datePath.goe(from))
            }

            if (to != null) {
                builder.and(datePath.loe(to))
            }

            return builder.value
        }

        private fun equalReferenceId(referenceId: String?): Predicate? =
            referenceId?.let {
                query.get(transaction.referenceId).eq(referenceId)
            }

    }

}