package org.example.knockin.repository.board.row;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.knockin.entity.member.Gender;
import org.example.knockin.entity.room.Region;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class MyRoommateBoardRow {
    private Long boardId;
    private String title;
    private Integer deposit;
    private Integer monthlyRent;
    private Integer managementCost;
    private LocalDateTime comeableDate;
    private Long hits;
    private String roomTypeName;
    private String regionName;
    private String parentRegionName;
    private String grandParentRegionName;
    private Long memberId;
    private String memberName;
    private String memberProfileImageUrl;
    private LocalDate memberBirth;
    private Gender memberGender;
    private LocalDateTime createdAt;
}
