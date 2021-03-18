package kr.joljak.domain.IdeaBoard.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import kr.joljak.domain.common.entity.ExtendEntity;
import kr.joljak.domain.user.entity.User;
import kr.joljak.domain.user.entity.UserProjectRole;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "IdeaBoards")
public class IdeaBoard extends ExtendEntity {

    @JsonFormat(shape = Shape.STRING)
    public enum Status {
        complete, Ongoing
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Status status;

    @Column(nullable = false)
    @NotNull
    private String title;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<UserProjectRole> requiredPosiotion;

    @Builder
    public IdeaBoard(Status status, String title, String content, User user,
        List<UserProjectRole> requiredPosition) {
        this.status = status;
        this.title = title;
        this.content = content;
        this.user = user;
        this.requiredPosiotion = requiredPosition;
    }

}
