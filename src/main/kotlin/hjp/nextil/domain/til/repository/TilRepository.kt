package hjp.nextil.domain.til.repository

import hjp.nextil.domain.til.entity.TilEntity
import org.springframework.data.jpa.repository.JpaRepository

interface TilRepository: JpaRepository<TilEntity,Long> {

    fun  findByMemberId(memberId: Long): List<TilEntity>?

    fun deleteAllByMemberId(memberId: Long)
}