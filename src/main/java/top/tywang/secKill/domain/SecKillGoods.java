package top.tywang.secKill.domain;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SecKillGoods {
    private Long id;
    private Long goodsId;
    private Integer stockCount;
    private Date startDate;
}
