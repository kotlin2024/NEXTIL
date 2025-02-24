package hjp.nextil.domain.member.repository

import hjp.nextil.domain.member.entity.MemberEntity
import org.springframework.data.jpa.repository.JpaRepository


interface MemberRepository: JpaRepository<MemberEntity, Long> {

    fun existsByUserName(userName: String): Boolean

    fun findByUserNameAndPassword(userName: String, password: String): MemberEntity?

    fun findByUserName(userName: String): MemberEntity?
}