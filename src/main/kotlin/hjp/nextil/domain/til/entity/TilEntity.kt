package hjp.nextil.domain.til.entity

import hjp.nextil.domain.member.entity.MemberEntity
import jakarta.persistence.*

@Entity
class TilEntity(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? =null,

    @Column(name = "member_id")
    val memberId: Long,

    @ElementCollection
    @CollectionTable(name = "til_keywords", joinColumns = [JoinColumn(name = "til_id")])
    @Column(name = "keyword")
    var tilKeyword: List<String> = mutableListOf()

) {
}