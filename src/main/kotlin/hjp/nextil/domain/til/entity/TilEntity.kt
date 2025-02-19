package hjp.nextil.domain.til.entity

import hjp.nextil.domain.member.entity.MemberEntity
import jakarta.persistence.*

@Entity
class TilEntity(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? =null,

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    val memberId: MemberEntity,

    @ElementCollection
    @CollectionTable(name = "til_keywords", joinColumns = [JoinColumn(name = "til_id")])
    @Column(name = "keyword")
    var tilKeyword: List<String> = mutableListOf()

) {
}