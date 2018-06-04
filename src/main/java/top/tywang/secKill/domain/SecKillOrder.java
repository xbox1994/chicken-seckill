package top.tywang.secKill.domain;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SecKillOrder {
    private Long id;
    private Long userId;
    private Long  orderId;
    private Long goodsId;
}
