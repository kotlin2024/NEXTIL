package hjp.nextil.domain.member.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "hjp_member")
class MemberEntity(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id:Long? = null,

    @Column(name = "user_name")
    val userName:String,

    @Column(name = "created_at")
    val createdAt: LocalDateTime,


)